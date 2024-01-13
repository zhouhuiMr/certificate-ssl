package com.common.result;


public class R<T>{
	
	private String code = "";
	
	private T data;
	
	private String message = "";

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setResultEnum(ResultEnum result) {
		this.code = result.getCode();
		this.message = result.getMessage();
	}
	
	public static <T> R<T> ok(T data) {
		R<T> json = new R<>();
		json.setResultEnum(ResultEnum.SUCCESS);
		json.setData(data);
		return json;
	}
	
	public static <T> R<T> ok() {
		R<T> json = new R<>();
		json.setResultEnum(ResultEnum.SUCCESS);
		json.setData(null);
		return json;
	}
	
	public static <T> R<T> fail(T data) {
		R<T> json = new R<>();
		json.setResultEnum(ResultEnum.ERROR);
		json.setData(data);
		return json;
	}
	
	public static <T> R<T> fail(T data, ResultEnum resultEnum) {
		R<T> json = new R<>();
		json.setResultEnum(resultEnum);
		json.setData(data);
		return json;
	}
	
	public static <T> R<T> fail(T data, String message) {
		R<T> json = new R<>();
		json.setResultEnum(ResultEnum.ERROR);
		json.setMessage(message);
		json.setData(data);
		return json;
	}
	
	public static <T> R<T> fail(T data, String message, String errorCode) {
		R<T> json = new R<>();
		json.setCode(errorCode);
		json.setMessage(message);
		json.setData(data);
		return json;
	}
}
