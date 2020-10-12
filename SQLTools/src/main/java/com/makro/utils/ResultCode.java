package com.makro.utils;

public enum ResultCode {
	SUCCESS(200),
	FAIL(400),
	UNAUTHORIZED(401),
	NOTFOUND(404),
	INTERNAL_SERVER_ERROR(500);
	public int code;
	ResultCode(int code) {
		this.code = code;
	}
}
