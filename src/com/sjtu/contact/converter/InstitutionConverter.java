package com.sjtu.contact.converter;

import java.util.Map;

import com.sjtu.contact.pojo.Institution;

public class InstitutionConverter extends BaseConverter {

	@Override
	protected Object toObj(Map objMap) {
		Institution i = new Institution();
		
		i.setId(Integer.valueOf((String)objMap.get("id")));
		i.setOrgId(Integer.valueOf((String)objMap.get("orgid")));
		i.setName((String)objMap.get("name"));
		
		return i;
	}

}
