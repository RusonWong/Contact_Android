package com.sjtu.contact.converter;

import java.util.Map;

import com.sjtu.contact.pojo.User;

public class UserConverter extends BaseConverter{

//	@Override
//	public Response doConvert(String jsonStr) {
//		Map retMap = JSONUtil.getMapFromJson(jsonStr);
//		Response r = toResponse( retMap );
//		
//		//if success, convert pojo
//		if( r.getErrorCode() == 0)
//		{
//			r.setObj( toUser( (Map)r.getObj()) );
//		}
//		return r;
//	}
	
//	private User toUser(Map uMap)
//	{
//		User u = new User();
//		u.setRole(Integer.valueOf((String)uMap.get("role")));
//		u.setDisplayName((String)uMap.get("name"));
//		u.setToken((String)uMap.get("token"));
//		return u;
//	}

	@Override
	protected Object toObj(Map uMap) {
		User u = new User();
		u.setRole(Integer.valueOf((String)uMap.get("role")));
		u.setDisplayName((String)uMap.get("name"));
		u.setToken((String)uMap.get("token"));
		return u;
	}


}
