package com.sjtu.contact.service;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import com.sjtu.contact.config.ProjectConfig;
import com.sjtu.contact.converter.ConverterFactory;
import com.sjtu.contact.pojo.Response;
import com.sjtu.contact.pojo.User;
import com.sjtu.contact.util.HttpRequestUtil;
import com.sjtu.contact.util.JSONUtil;
import com.sjtu.contact.util.Logger;

public class UserService extends BaseService{
	
	private static UserService instance;
	
	private UserService(){}
	
	public static UserService getInstance()
	{
		if(instance == null)
			instance = new UserService();
		
		return instance;
	}
	
	public Response doLogin(String username, String passwd)
	{
		String serviceUrl = ProjectConfig.SERVICE_URL_USER_LOGIN;
		Map<String,String> params = new HashMap<String,String>();
		params.put("username", username);
		params.put("passwd", passwd);
		String retJson ="";
		try
		{	
			retJson = HttpRequestUtil.doGet(serviceUrl, params);
			retJson = URLDecoder.decode(retJson, ProjectConfig.URL_DECODING_TYPE);
			Logger.log("JSON: "+retJson);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		Map retMap = JSONUtil.getMapFromJson(retJson);
		
		return (ConverterFactory.getConverter(ConverterFactory.CONVERTER_USER)).doConvert(retJson);
	}
	
	public Response doLogin(String username, String passwd, String platform)
	{
		String serviceUrl = ProjectConfig.SERVICE_URL_USER_LOGIN;
		Map<String,String> params = new HashMap<String,String>();
		params.put("username", username);
		params.put("passwd", passwd);
		params.put("platform", platform);
		String retJson ="";
		try
		{	
			retJson = HttpRequestUtil.doGet(serviceUrl, params);
			retJson = URLDecoder.decode(retJson, ProjectConfig.URL_DECODING_TYPE);
			Logger.log("JSON: "+retJson);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		Map retMap = JSONUtil.getMapFromJson(retJson);
		
		return (ConverterFactory.getConverter(ConverterFactory.CONVERTER_USER)).doConvert(retJson);
	}
}
