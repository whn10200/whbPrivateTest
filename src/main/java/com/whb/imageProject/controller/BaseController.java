package com.whb.imageProject.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.whb.imageProject.exception.BusinessCode;
import com.whb.imageProject.exception.BusinessException;
import com.whb.imageProject.pojo.JsonPojo;
import com.whb.imageProject.utils.JSONUtils;

/**
 * 所有controller的父级类，里面的工具方法可以是开发人员轻松的构建control层的代码。特别是基于json格式的http接口
 * @author yinwenjie
 */
public class BaseController {
	/**
	 * 向response中写入字符串json,一般返回错误信息的时候，会使用该打印方法
	 * @param response HttpServletResponse
	 * @param exception 写入的异常信息
	 */
	protected void writeExceptionResponseMsg(HttpServletResponse response, BusinessException exception) {
		// 构造错误信息
		JsonPojo errorPojo = new JsonPojo("", exception.getMessage(), exception.getBusinessCode());
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		String jsonStr = JSONUtils.toString(errorPojo);
		out.print(jsonStr);
	}
	
	/**
	 * 向response中写入字符串
	 * @param response HttpServletResponse
	 * @param result 写入的对象信息
	 */
	protected void writeResponseMsg(HttpServletResponse response, Object result) {
		// 构造正确信息
		JsonPojo msgPojo = new JsonPojo(result, "", BusinessCode._200);
		
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		String jsonstr = JSONUtils.toString(msgPojo); 
		out.print(jsonstr);
	}
	
	/**
	 * 向response中写入giftupian
	 * @param response HttpServletResponse
	 * @param result 写入的byte信息
	 */
	protected void writeResponseGif(HttpServletResponse response, byte[] result) {
		response.setContentType("image/jpeg;charset=utf-8");
		OutputStream out = null;
		try {
			out = response.getOutputStream();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		 
		try {
			out.write(result);
		} catch (IOException e) {
			e.printStackTrace(System.out);
		}
	}
	
	/**
	 * 向response中写入字符串
	 * @param response HttpServletResponse
	 * @param result 结果对象信息
	 * @param filterProperties 需要进行过滤的，即不进行页面显示的json属性
	 * @throws IOException
	 */
	protected void writeResponseMsg(HttpServletResponse response, Object result , String... filterProperties) {
		// 构造正确信息
		JsonPojo mesPojo = new JsonPojo(result, "", BusinessCode._200);
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		String jsonStr = JSONUtils.toString(mesPojo , filterProperties);
		out.print(jsonStr);
	}
	
	/**
	 * 从request获取入参的工具方法
	 * @throws BusinessException 抛出这个异常
	 */
	protected String queryInputData(HttpServletRequest request) throws BusinessException {
		StringBuilder sb = new StringBuilder();
		try {
			InputStream in = request.getInputStream();
			int realLen;
			int maxLen = 2048;
			byte[] contexts = new byte[2048];
		    while ((realLen = in.read(contexts, 0, maxLen)) != -1) {
		        sb.append(new String(contexts , 0 , realLen));
		    }
		    in.close();
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), BusinessCode._501);
		}
		
		if(StringUtils.isEmpty(sb.toString())){
			throw new BusinessException("input is null",BusinessCode._501);
		}
		return sb.toString().trim();
	}
}