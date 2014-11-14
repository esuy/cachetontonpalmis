package com.cache.tontonpalmis.chat;

import java.io.Serializable;

import com.cache.tontonpalmis.profil.Profil;

public class Message {
	
	private Profil sender;
	private Profil receiver;
	private String time;
	private boolean left;
	protected Profil getSender() {
		return sender;
	}
	protected void setSender(Profil sender) {
		this.sender = sender;
	}
	protected Profil getReceiver() {
		return receiver;
	}
	protected void setReceiver(Profil receiver) {
		this.receiver = receiver;
	}
	protected String getTime() {
		return time;
	}
	protected void setTime(String time) {
		this.time = time;
	}
	protected boolean isLeft() {
		return left;
	}
	protected void setLeft(boolean left) {
		this.left = left;
	}
	
	
}
