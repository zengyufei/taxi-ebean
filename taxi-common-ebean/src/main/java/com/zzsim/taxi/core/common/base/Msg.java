package com.zzsim.taxi.core.common.base;

import java.io.Serializable;

public class Msg<T> implements Serializable {

	private static final long serialVersionUID = 3951279211183213561L;

	private String code;
	private String msg;
	private T result;
	
	public Msg(){}
	
	@SuppressWarnings("unchecked")
	public Msg(String code, String msg){
		this.code = code;
		this.msg = msg;
		this.result =  (T)"";
	}

	public Msg(String code){
		this.code = code;
	}
	
	public Msg(String code,String msg,T result){
		this.code = code;
		this.msg = msg;
		this.result = result;
	}

	public Msg(T result){
		this.result = result;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

}
