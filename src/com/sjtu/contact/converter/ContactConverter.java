package com.sjtu.contact.converter;

import java.util.Map;

import com.sjtu.contact.pojo.Contact;

public class ContactConverter extends BaseConverter {

//	@Override
//	public Response doConvert(String jsonStr) {
//		Map retMap = JSONUtil.getMapFromJson(jsonStr);
//		Response r = toResponse( retMap );
//		
//		//if success, convert pojo
//		if( r.getErrorCode() == 0)
//		{
//			r.setObj( toContact( (Map)r.getObj()) );
//		}
//		return r;
//	}
	
//	@Override
//	public Response doConvert2List(String jsonStr) {
//		Map retMap = JSONUtil.getMapFromJson(jsonStr);
//		Response r = toResponse( retMap );
//		
//		if( r.getErrorCode() == 0)
//		{
//			List cmList = (List) r.getObj();
//			
//			ArrayList<Contact> cList = new ArrayList<Contact>();
//			for (int i = 0; i < cmList.size(); i++) {
//				Map cMap = (Map) cmList.get(i);
//				Contact c = toContact(cMap);
//				cList.add(c);
//			}
//			
//			r.setObj(cList);
//		}
//		
//		return r;
//	}

	
	
//	private Contact toContact(Map cMap)
//	{
//		Contact c = new Contact();
//		c.setDept((String)cMap.get("dept"));
//		c.setDeptid(Integer.valueOf((String) cMap.get("deptid")));
//		c.setEmail((String)cMap.get("email"));
//		c.setFax((String)cMap.get("fax"));
//		c.setHomePhone((String)cMap.get("home_phone"));
//		c.setId(Integer.valueOf((String)cMap.get("id")));
//		c.setInst((String)cMap.get("inst"));
//		c.setInstid(Integer.valueOf((String)cMap.get("instid")));
//		c.setMobilePhone((String)cMap.get("mobile_phone"));
//		c.setName((String)cMap.get("name"));
//		c.setNameFirstLetter((String)cMap.get("first_letter"));
//		c.setNamePingYing((String)cMap.get("pinyin"));
//		c.setOfficePhone((String)cMap.get("office_phone"));
//		c.setOrg((String)cMap.get("org"));
//		c.setOrgid(Integer.valueOf((String)cMap.get("orgid")));
//		c.setPosition((String)cMap.get("position"));
//		c.setRemark((String)cMap.get("remarks"));
//		c.setSubPhone((String)cMap.get("sub_phone"));
//		c.setSwitchBoard((String)cMap.get("switchboard"));
//		
//		return c;
//	}

	@Override
	protected Object toObj(Map cMap) {
		Contact c = new Contact();
		c.setDept(toStr(cMap.get("dept")));
		Object c2 = cMap.get("deptid");
		c.setDeptid(StrToInt(toStr(c2)));
		c.setEmail(toStr(cMap.get("email")));
		c.setFax(toStr(cMap.get("fax")));
		c.setHomePhone(toStr(cMap.get("home_phone")));
		c.setId(StrToInt(toStr(cMap.get("id"))));
		c.setInst(toStr(cMap.get("inst")));
		c.setInstid(StrToInt(toStr(cMap.get("instid"))));
		c.setMobilePhone(toStr(cMap.get("mobile_phone")));
		c.setName(toStr(cMap.get("name")));
		c.setNameFirstLetter(toStr(cMap.get("first_letter")));
		c.setNamePingYing(toStr(cMap.get("pinyin")));
		c.setOfficePhone(toStr(cMap.get("office_phone")));
		c.setOrg(toStr(cMap.get("org")));
		c.setOrgid(StrToInt(toStr(cMap.get("orgid"))));
		c.setPosition(toStr(cMap.get("position")));
		c.setRemark(toStr(cMap.get("remarks")));
		c.setSubPhone(toStr(cMap.get("sub_phone")));
		c.setSwitchBoard(toStr(cMap.get("switchboard")));
		
		return c;
	}
	
	
	private int StrToInt(String intStr)
	{
		if(intStr == null)
			return -1;
		if(intStr.equals(""))
			return -1;
		if(intStr.equals("null"))
			return -1;
		else
			return Integer.valueOf(intStr);
	}
	
	private String toStr(Object obj)
	{
		if(obj == null)
			return null;
		else
			return obj.toString();
	}



	
}
