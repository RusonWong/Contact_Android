package com.sjtu.contact.converter;

import com.sjtu.contact.util.Logger;

public class ConverterFactory {
	public static final String 	CONVERTER_CONTACT = "CONVERTER_CONTACT";
	public static final String  CONVERTER_USER	=	"CONVERTER_USER";
	public static final String CONVERTER_DEPARTMENT = "CONVERTER_DEPARTMENT";
	public static final String CONVERTER_INSTITUTION = "CONVERTER_INSTITUTION";
	public static final String CONVERTER_ORGANIZATION = "CONVERTER_ORGANIZATION";
	
	
	private static BaseConverter contactConverter;
	private static BaseConverter deptConverter;
	private static BaseConverter instConverter;
	private static BaseConverter orgConverter;
	
	
	
	public static BaseConverter getConverter( String ct)
	{
		if(ct.equals(CONVERTER_CONTACT))
		{
			if( contactConverter == null )
			{
				contactConverter = new ContactConverter();
			}
			
			return new ContactConverter();
		}
		
		else if(ct.equals(CONVERTER_USER))
		{
			return new UserConverter();
		}
		
		else if(ct.equals(CONVERTER_DEPARTMENT))
		{
			if( deptConverter == null )
			{
				deptConverter = new DepartmentConverter();
			}
			
			return deptConverter;
		}
		else if(ct.equals(CONVERTER_INSTITUTION))
		{
			if(instConverter == null)
			{
				instConverter = new InstitutionConverter();
			}
			
			return instConverter;
		}
		else if(ct.equals(CONVERTER_ORGANIZATION))
		{
			if(orgConverter == null)
			{
				orgConverter = new OrganizationConverter();
			}
			return orgConverter;
		}
		
		Logger.log("can not find the converter "+ct);
		return null;
	}
}
