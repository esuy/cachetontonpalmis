package com.cache.tontonpalmis.chat;

import java.io.Serializable;
import java.util.List;

public class ContainerMessage  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5611202513474959145L;
	private List<Message> messages;
	
	public ContainerMessage(List<Message> messages){
		this.messages = messages;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	
}
