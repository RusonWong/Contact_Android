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


/*
 * Singleton
 */
public class ContactService extends BaseService{
	
	public static final String FILTER_INSTID = "instid";
	public static final String FILTER_ORGID = "orgid";
	public static final String FILTER_DEPTID = "deptid";
	
	public static final String FILTER_WD = "wd";
	
	private static ContactService instance;
	
	private ContactService()
	{
		
	}
	
	public static ContactService getInstance()
	{
		if(instance == null)
		{
			instance = new ContactService();
		}
		
		return instance;
	}
	
	public Response getContact( int cid )
	{
		String serviceUrl = ProjectConfig.SERVICE_URL_CONTACT_GET;
		Map<String,String> params = new HashMap<String,String>();
		
		params.put("id", ""+cid);
		//add token
		fillInToken(params);
		
		String retJson ="";
		try
		{	
			retJson = HttpRequestUtil.doGet(serviceUrl, params);
			retJson = URLDecoder.decode(retJson, ProjectConfig.URL_DECODING_TYPE);
			Logger.log(retJson);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		
		return (ConverterFactory.getConverter(ConverterFactory.CONVERTER_CONTACT)).doConvert(retJson);
		
	}
	
	
	public Response queryContacts(QueryFilter filter)
	{
		String serviceUrl = ProjectConfig.SERVICE_URL_CONTACT_SEARCH;
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
		
		BaseConverter cvt = ConverterFactory.getConverter(ConverterFactory.CONVERTER_CONTACT);
		
		return cvt.doConvert2List(retJson);
		
	}
	
	
	
	
	
}
