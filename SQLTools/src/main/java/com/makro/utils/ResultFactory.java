package com.makro.utils;

public class ResultFactory {
	public static Result buildSuccessResult(Object data) {
		return buildResult(ResultCode.SUCCESS, "成功", data);
	}
	public static Result buildFailResult(Object data) {
		return buildResult(ResultCode.FAIL, "成功", data);
	}
	public static Result buildResult(ResultCode resultCode, String message, Object data) {
		return buildResult(resultCode.code, "成功", data);
	}
	public static Result buildResult(int resultCode, String message, Object data) {
		return new Result(resultCode, message, data);
	}
	
}
