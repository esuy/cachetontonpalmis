package com.cache.tontonpalmis.chat;

import java.io.Serializable;

public class MessageText extends Message implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5727496769661876521L;
	private Long id;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
