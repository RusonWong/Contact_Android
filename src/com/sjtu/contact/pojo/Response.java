package com.sjtu.contact.pojo;

public class Response {
	private int errorCode;
	private Object obj;
	private String errorMsg;
	
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String msg) {
		this.errorMsg = msg;
	}
	
}
