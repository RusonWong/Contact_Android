package com.sjtu.contact.service;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import com.sjtu.contact.config.ProjectConfig;
import com.sjtu.contact.converter.BaseConverter;
import com.sjtu.contact.converter.ConverterFactory;
import com.sjtu.contact.pojo.Response;
import com.sjtu.contact.util.HttpRequestUtil;
import com.sjtu.contact.util.Logger;

public class LocationService extends BaseService {
	
	private static LocationService instance;
	
	private LocationService()
	{
		
	}
	
	public static LocationService getInstance()
	{
		if(instance == null)
		{
			instance = new LocationService();
		}
		
		return instance;
	}
	
	public Response queryOrgs( )
	{
		String serviceUrl = ProjectConfig.SERVICE_URL_ORGS_SEARCH;
		
		Map filters = new HashMap<String,String>();
		fillInToken(filters);
		
		String retJson ="";
		try
		{	
			retJson = HttpRequestUtil.doGet(serviceUrl, filters);
			retJson = URLDecoder.decode(retJson, ProjectConfig.URL_DECODING_TYPE);
			Logger.log(retJson);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		
		BaseConverter cvt = ConverterFactory.getConverter(ConverterFactory.CONVERTER_ORGANIZATION);
		
		return cvt.doConvert2List(retJson);
	}
	
	public Response queryInsts( QueryFilter filter )
	{
		String serviceUrl = ProjectConfig.SERVICE_URL_INST_SEARCH;
		
		Map filters = filter.getFilterMap();
		fillInToken(filters);
		
		String retJson ="";
		try
		{	
			retJson = HttpRequestUtil.doGet(serviceUrl, filters);
			retJson = URLDecoder.decode(retJson, ProjectConfig.URL_DECODING_TYPE);
			Logger.log(retJson);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		
		BaseConverter cvt = ConverterFactory.getConverter(ConverterFactory.CONVERTER_INSTITUTION);
		
		return cvt.doConvert2List(retJson);
	}
	

	public Response queryDepts( QueryFilter filter )
	{
		String serviceUrl = ProjectConfig.SERVICE_URL_DEPT_SEARCH;
		
		Map filters = filter.getFilterMap();
		fillInToken(filters);
		
		String retJson ="";
		try
		{	
			retJson = HttpRequestUtil.doGet(serviceUrl, filters);
			retJson = URLDecoder.decode(retJson, ProjectConfig.URL_DECODING_TYPE);
			Logger.log(retJson);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		
		BaseConverter cvt = ConverterFactory.getConverter(ConverterFactory.CONVERTER_DEPARTMENT);
		
		return cvt.doConvert2List(retJson);
	}
	
}
