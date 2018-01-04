package com.whb.imageProject.controller;

import java.awt.image.BufferedImage;
import java.io.*;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;

import com.whb.imageProject.configuration.ImagePathProperties;
import com.whb.imageProject.exception.BusinessCode;
import com.whb.imageProject.exception.BusinessException;
import com.whb.imageProject.service.ImageHandler;
import com.whb.imageProject.service.ImageHandlerBuilder;
import com.whb.imageProject.service.iface.IImageEffectsCacheService;

/**
 * 这个测试图片特效显示效果
 * @author yinwenjie
 */
@Controller
public class ImageViewController extends BaseController {
	/**
	 * 日志
	 * */
	private static final Log LOGGER = LogFactory.getLog(ImageViewController.class);

	/**
	 * 图片操作相关的配置信息
	 * */
	@Autowired
	private ImagePathProperties imagePathProperties;
	
	/**
	 * 缓存操作服务
	 */
	@Autowired
	private IImageEffectsCacheService imageEffectsCacheService;

	/**
	 * 这个方法测试有参数的图片访问方式<br/>
	 * 这里留了几个测试参数：<br/>
	 * ?special=zoomimage%7Cwidth%3D300%7Cheight%3D300->markimage%7CmarkValue%3Dwwwyinwenjienet.222<br/>
	 * ?special=zoomimage%7Cratio%3D0.1->markimage%7CmarkValue%3Dwww.com.whb.imageProject.net<br/>
	 * ?special=cutimage%7Cratio%3D0.1->markimage%7CmarkValue%3Dwww.com.whb.imageProject.net<br/>
	 * ?special=cutimage%7Cwidth%3D300%7Cheight%3D300->markimage%7CmarkValue%3Dwww.com.whb.imageProject.net<br/>
	 * */
	@RequestMapping(path = {"/imageQuery/{folder}/{imageFile}.{prefix}","/imageQuery/{imageFile}.{prefix}"} , method = RequestMethod.GET)
	public void imageQuery(HttpServletResponse response , @PathVariable("folder") String folder , @PathVariable("imageFile") String imageFile , @PathVariable("prefix") String prefix , String special) throws Exception {
		String imageRoot = imagePathProperties.getImageRoot();
		LOGGER.info("image service 8080 accept!");
		/*
		 * 缓存操作 ============
		 * 1、使用图片相对路径（包括存储目录的）和输入的原始特效参数作为缓存系统的key值
		 * （虽然有点长，会相对增加一些hash计算时间，但比起去做磁盘操作所耗费的时间来说就太少了）
		 * 要注意，如果没有特效参数，则直接进行磁盘操作，因为缓存中没有存储原始的图片数据
		 * 2、试图从缓存中拿到数据，如果拿到则直接返回，如果没有拿到，进行后续的磁盘操作
		 * 
		 * 磁盘操作 =============
		 * 1、根据imageRoot和imageFile的信息在文件系统上提取原始信息，如果没有提取到则不再处理
		 * 2、根据既定的特效命令格式，分析请求者要求的特效情况
		 * 3、更具这个特效情况，使用特效构造者构造特效
		 * 4、进行特效处理的结果读取并存储到缓存
		 * （缓存默认的过期时间为5分钟，当然可以更改配置文件进行调整，建议可以调整的更长些，例如6小时）
		 * 5、显示到页面上
		 * */
		// 1、========
		String relativePath = folder + "/" + imageFile + "." + prefix;
		String caheKey = relativePath + special;
		if(!StringUtils.isEmpty(special)) {
			byte[] results = this.imageEffectsCacheService.queryCache(caheKey);
			// 2、======== 如果条件成立，说明从缓存中读到了信息
			if(results != null && results.length > 0) {
				// 输出到页面，就可以结束了
				this.writeResponseGif(response, results);
				return;
			}
		}
		
		// 1、========
		File imFile = null;
		imFile = new File(imageRoot + "/" + relativePath);
		byte[] fileBytes = this.queryOriginalPicture(imFile);
		if(fileBytes == null) {
			return;
		}

		// 2、=======
		// 如果没有特效要求，就不进行特效处理，直接显示原图了
		if(StringUtils.isEmpty(special)) {
			this.writeResponseGif(response , fileBytes);
			return;
		}
		BufferedImage bufferedImage = null;
		try {
			bufferedImage = javax.imageio.ImageIO.read(new ByteArrayInputStream(fileBytes));
		} catch (IOException e) {
			LOGGER.error(e.getMessage() , e);
		}
		String[] specialSteps = special.split("\\-\\>");
		if(specialSteps.length == 0) {
			return;
		}

		// 3、=======
		// 图片处理器创建者
		ImageHandlerBuilder.Builder builder = new ImageHandlerBuilder.Builder();
		for (String specialStep : specialSteps) {
			String[] params = specialStep.split("\\|");
			//
			if(StringUtils.equals(params[0] , "cutimage")) {
				this.builderCutAndZoomImageHandle(params , builder , "cutimage");
			} else if(StringUtils.equals(params[0] , "zoomimage")) {
				this.builderCutAndZoomImageHandle(params , builder , "zoomimage");
			} else if(StringUtils.equals(params[0] , "markimage")) {
				this.builderMarkImageHandle(params , builder);
			}
		}

		// 4、=======
		ImageHandler imageHandle = builder.build();
		BufferedImage imageResults = imageHandle.dispose(bufferedImage);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		javax.imageio.ImageIO.write(imageResults , prefix , out);
		byte[] imageBytes = out.toByteArray();
		// 存储到缓存
		this.imageEffectsCacheService.saveCache(caheKey, imageBytes);
		
		// 5、=======显示到页面上
		this.writeResponseGif(response , imageBytes);
	}
	
	/**
	 * 这个私有方法用于创建水印图片的特效
	 * @param params 水印图片的特效参数（实际上只有一个markValue）
	 * @param builder 特效创建器
	 * */
	private void builderMarkImageHandle(String[] params , ImageHandlerBuilder.Builder builder) throws BusinessException {
		String markValue = null;
		for(String param : params) {
			String[] paramVars = param.split("=");
			if(StringUtils.equals(paramVars[0] , "markValue")) {
				markValue = paramVars[1];
			}
		}

		// 对参数的正确选定进行判断
		if(StringUtils.isEmpty(markValue)) {
			throw new BusinessException("markValue must be set value!" , BusinessCode._404);
		}

		// 设定到创建器
		builder.createMarkHandler(markValue);
	}

	/**
	 * 这个私有方法用于创建裁剪图片/缩放图片的特效
	 * @param params 裁剪图片/缩放图片的特效参数
	 * @param builder 特效创建器
	 * */
	private void builderCutAndZoomImageHandle(String[] params , ImageHandlerBuilder.Builder builder , String opType) throws BusinessException {
		Integer width = null,height = null;
		Float ratio = null;
		for(String param : params) {
			String[] paramVars = param.split("=");
			if(StringUtils.equals(paramVars[0] , "width")) {
				width = Integer.parseInt(paramVars[1]);
 			} else if(StringUtils.equals(paramVars[0] , "height")) {
				height = Integer.parseInt(paramVars[1]);
			} else if(StringUtils.equals(paramVars[0] , "ratio")) {
				ratio = Float.parseFloat(paramVars[1]);
			}
		}

		// 对参数的正确选定进行判断
		if(ratio != null && (ratio <= 0f || ratio >= 1f)) {
			throw new BusinessException("ratio must between from 0 to 1!" , BusinessCode._404);
		} else if(ratio == null && (width == null || height == null)) {
			throw new BusinessException("width and height must be set value!" , BusinessCode._404);
		}

		// 设定到创建器
		if(ratio != null && StringUtils.equals(opType , "cutimage")) {
			builder.createCutHandler(ratio);
		} else if(ratio != null && StringUtils.equals(opType , "zoomimage")) {
			builder.createZoomHandler(ratio);
		} else if(ratio == null && StringUtils.equals(opType , "cutimage")) {
			builder.createCutHandler(width , height);
		} else if(ratio == null && StringUtils.equals(opType , "zoomimage")) {
			builder.createZoomHandler(width , height);
		}
	}

	/**
	 * 这个私有方法用于在磁盘上查询原始文件
	 * */
	private byte[] queryOriginalPicture(File imFile) throws IOException{
		// 如果不存在这个文件，就不需要处理咯
		// 生产环境下要显示一张默认的404图片
		if(!imFile.exists()) {
			return null;
		}
		InputStream in = new FileInputStream(imFile);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int len = 8192;
		int realLen;
		byte[] contents = new byte[8192];
		while((realLen = in.read(contents, 0, len)) != -1) {
			out.write(contents, 0, realLen);
		}
		in.close();
		byte[] imageBytes = out.toByteArray();
		out.close();

		return imageBytes;
	}
}