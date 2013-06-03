package com.sjtu.contact.config;

public class ProjectConfig {
	
	public final static String URL_DECODING_TYPE = "utf-8";
	
//	public final static String SERVICE_URL_BASE = "218.193.187.182/index.php";
	public final static String SERVICE_URL_BASE = "202.120.40.97:8383/index.php";
//	public final static String SERVICE_URL_BASE = "202.120.63.178:8881/index.php";
	
	//contact service config
	public final static String SERVICE_URL_CONTACT = SERVICE_URL_BASE + "/contact";
	public final static String SERVICE_URL_CONTACT_GET = SERVICE_URL_CONTACT + "/get";
	public final static String SERVICE_URL_CONTACT_SEARCH = SERVICE_URL_CONTACT + "/search";
	
	//user service config
	public static final String SERVICE_URL_USER = SERVICE_URL_BASE + "/user";
	public static final String SERVICE_URL_USER_LOGIN = SERVICE_URL_USER + "/login";

	
	//org inst dept
	public static final String SERVICE_URL_DEPT_SEARCH = SERVICE_URL_BASE+"/contact/get_depts";
	public static final String SERVICE_URL_INST_SEARCH = SERVICE_URL_BASE+"/contact/get_insts";
	public static final String SERVICE_URL_ORGS_SEARCH = SERVICE_URL_BASE+"/contact/get_orgs";
}
