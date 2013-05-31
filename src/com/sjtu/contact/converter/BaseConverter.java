package com.sjtu.contact.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sjtu.contact.pojo.Contact;
import com.sjtu.contact.pojo.Response;
import com.sjtu.contact.util.JSONUtil;

public abstract class BaseConverter {
	
	 public  Response doConvert(String jsonStr)
	 {
		 	Map retMap = JSONUtil.getMapFromJson(jsonStr);
			Response r = toResponse( retMap );
			
			//if success, convert pojo
			if( r.getErrorCode() == 0)
			{
				r.setObj( toObj( (Map)r.getObj()) );
			}
			return r;
	 }
	 
	 public  Response doConvert2List(String jsonStr)
	 {
		 	Map retMap = JSONUtil.getMapFromJson(jsonStr);
			Response r = toResponse( retMap );
			
			if( r.getErrorCode() == 0)
			{
				List cmList = (List) r.getObj();
				
				ArrayList<Object> cList = new ArrayList<Object>();
				for (int i = 0; i < cmList.size(); i++) {
					Map objMap = (Map) cmList.get(i);
					Object c = toObj(objMap);
					cList.add(c);
				}
				
				r.setObj(cList);
			}
			
			return r;
	 }
	 
	 //convert a map obj  to a pojo obj
	 protected abstract Object toObj(Map objMap);
	 
	 
	 protected Response toResponse(Map retMap)
	 {
		 Response result = new Response();
		 
		 result.setErrorCode((Integer)retMap.get("error"));
		 if(result.getErrorCode() == 0)
		 {
			 result.setObj(retMap.get("result"));
			 result.setErrorMsg("");
		 }
		 else 
		 {
			 result.setObj(null);
			 result.setErrorMsg((String)retMap.get("msg"));
		 }
		 
		 return result;
	 }
	 
	 
	
}
