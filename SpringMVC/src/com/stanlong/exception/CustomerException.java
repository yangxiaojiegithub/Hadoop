package com.stanlong.exception;

/**
 * 对不同的异常类型定义异常类
 * @author 矢量
 *
 */
public class CustomerException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String message;
	
	public CustomerException(String message){
		super(message);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
