package com.sjtu.contact.converter;

import java.util.Map;

import com.sjtu.contact.pojo.Department;


public class DepartmentConverter extends BaseConverter{

	@Override
	protected Object toObj(Map objMap) {

		Department d = new Department();
		
		d.setId(Integer.valueOf((String)objMap.get("id")));
		d.setInstId(Integer.valueOf((String)objMap.get("instid")));
		d.setName((String)objMap.get("name"));
		
		return d;
		
	}

	
}
