package com.sjtu.contact.converter;

import java.util.Map;

import com.sjtu.contact.pojo.Organization;

public class OrganizationConverter extends BaseConverter {

	@Override
	protected Object toObj(Map objMap) {
		Organization o = new Organization();
		
		o.setId(Integer.valueOf((String)objMap.get("id")));
		o.setName((String)objMap.get("name"));
		
		return o;
		
	}

}
