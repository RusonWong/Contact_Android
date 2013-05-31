package com.sjtu.contact.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	
	public static boolean isEmail(String strEmail) {
	     String strPattern = "[a-zA-Z0-9_-]+@\\w+\\.[a-z]+(\\.[a-z]+)*";
	     Pattern p = Pattern.compile(strPattern);
	     Matcher m = p.matcher(strEmail);
	     return m.matches();
	    }
	
	public static boolean isPhone(String phoneNumber)
	{
		 String strPattern = "([0-9]{3,4}-)?[0-9]{4,12}";
	     Pattern p = Pattern.compile(strPattern);
	     Matcher m = p.matcher(phoneNumber);
	     return m.matches();
	}
	
	public static void main(String args[])
	{
		String phone = "12345";
		if(isPhone(phone))
		{
			System.out.println("true");
		}
		else
		{
			System.out.println("false");
		}
	}
}
