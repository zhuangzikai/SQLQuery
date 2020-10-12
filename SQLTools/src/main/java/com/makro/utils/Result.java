package com.makro.utils;

public class Result {
	private int code;
	private String message;
	private Object date;
	public Result() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Result(int code, String message, Object date) {
		super();
		this.code = code;
		this.message = message;
		this.date = date;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getDate() {
		return date;
	}
	public void setDate(Object date) {
		this.date = date;
	}
	
}
