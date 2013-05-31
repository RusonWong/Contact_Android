package com.sjtu.contact.service;

import java.util.Map;

import com.sjtu.contact.pojo.User;

public class BaseService {
	
	protected void fillInToken(Map map)
	{
		User curUser = GlobalVar.getCurrentUser();
		if( curUser == null )
		{
			map.put("token", "notlogin");
		}
		else
		{
			map.put("token", GlobalVar.getCurrentUser().getToken());
		}
	}
	
}
