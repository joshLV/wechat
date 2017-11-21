package com.bingosoft.models.msg;

import java.io.Serializable;


public class ResponseMessage<T> extends BaseMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public ResponseMessage(){
		super();
	}
	
	public ResponseMessage(int code,boolean success,String message){
		super(code,success,message);
	}
	
	
	public ResponseMessage(int code,boolean success,String message,T obj){
		super(code,success,message);
		this.response = obj;
	}
	
	private T response;

	public T getResponse() {
		return response;
	}

	public void setResponse(T response) {
		this.response = response;
	}

}
