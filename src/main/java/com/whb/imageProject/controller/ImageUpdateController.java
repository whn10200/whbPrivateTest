package com.whb.imageProject.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.whb.imageProject.configuration.ImagePathProperties;
import com.whb.imageProject.exception.BusinessCode;
import com.whb.imageProject.exception.BusinessException;

/**
 * 这个controller用于接受一个新的文件，支持jpg、gif、png图片
 * @author yinwenjie
 */
@Controller
public class ImageUpdateController extends BaseController {
	
	/**
	 * 保存文件的根路径描述
	 */
	@Autowired
	private ImagePathProperties imagePathProperties;
	
	/**
	 * 这个方法用于上传单张图片使用
	 * @param file 上传的文件信息
	 * @param response
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping(value = "/imageUpload")
	public void imageUpload(@RequestParam("file") MultipartFile file, HttpServletResponse response) throws BusinessException {
		/*
		 * 处理过程为：
		 * 1、首先判断文件后缀格式是否为支持的格式。 支持jpg、png、gif格式的图片上传
		 * 2、为了保证网络畅通，要控制文件大小在1MB以下，所以也要进行控制（当然也可以通过spring mvc的配置实现限制）
		 * 
		 * 3、开始保存文件，注意，文件都要重命名。
		 * 为了简单起见重命名使用java自带的UUID工具完成即可
		 * 
		 * 4、正式写入文件，如果以上所有步骤都成功，则向上传者返回文件存储的提示信息
		 * 
		 * 注意，这些文件在上传时，由于不清楚这些图片在显示时会使用什么样的特效
		 * 所以在文件上传时就进行redis缓存存储是没有意义的。
		 * 
		 * 最后，本工程没有提供上传的测试页面，测试是使用postman等软件完成的
		 * */
		// 1、2都在这里=======
		String originalFilename = file.getOriginalFilename();
		long fileSize = file.getSize();
		String prefix = null;
		int prefixIndex = originalFilename.lastIndexOf(".");
		if(prefixIndex != -1) {
			prefix = originalFilename.substring(prefixIndex + 1);
			prefix = prefix.toLowerCase();
		}
		// 如果条件成立，说明大于1MB了
		if(fileSize > 1024 * 1024) {
			throw new BusinessException("image file should be less than 1MB!", BusinessCode._502);
		}
		// 如果条件成立说明不是支持的图片类型
		// 虽然bmp也被java image I/O API 原生支持，但它不适合做互联网应用，就不进行支持了
		if(StringUtils.isEmpty(prefix)
			|| (!StringUtils.equals(prefix, "png") && !StringUtils.equals(prefix, "gif")
				&& !StringUtils.equals(prefix, "jpg") && !StringUtils.equals(prefix, "jpeg"))) {
			throw new BusinessException("image prefix not be support it(jpg/png/gif)!", BusinessCode._503);
		}
		
		//3、======
		String imageRoot = imagePathProperties.getImageRoot();
		String renameImage = UUID.randomUUID().toString();
		// 可以使用日期作为文件夹的名字
		Date nowDate = new Date();
		String folderName = new SimpleDateFormat("yyyyMMdd").format(nowDate);
		File folderFile = new File(imageRoot + "/" + folderName);
		// 如果不存在这个目录则进行创建。
		// 为了保证高并发时不会重复创建目录，要进行线程锁定
		// 使用悲观锁就行了
		if(!folderFile.exists()) {
			synchronized (ImageUpdateController.class) {
				while(!folderFile.exists()) {
					folderFile.mkdirs();
				}
			}
		}
		// 以下就是这个即将创建的文件的完整路径了
		String relativePath = folderName + "/" + renameImage + "." + prefix;
		String fullImagePath = imageRoot + "/" + relativePath;
		
		// 4、====
		try {
			file.transferTo(new File(fullImagePath));
		} catch (IllegalStateException | IOException e) {
			throw new BusinessException(e.getMessage(), BusinessCode._504);
		}
		// 构造返回
		this.writeResponseMsg(response, "file done:you can query the file by " + relativePath);
	}
}