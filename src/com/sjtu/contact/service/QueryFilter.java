package com.sjtu.contact.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.sjtu.contact.config.ProjectConfig;

public class QueryFilter {
	
	private HashMap<String, Object> filters;
	
	public QueryFilter()
	{
		filters = new HashMap<String,Object>();
	}
	
	public void AddFilter(String filterName, Object filterValue)
	{
		try {
			filters.put(filterName, URLEncoder.encode((String)filterValue, ProjectConfig.URL_DECODING_TYPE));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			filters.put(filterName, filterValue);
		}
	}
	
	public void AddFirstPageConfig()
	{
		AddFilter("offset",0 + "");
		AddFilter("limit",10 + "");
	}
	
	
	public Map getFilterMap()
	{
		return filters;
	}
}
