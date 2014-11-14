package com.cache.tontonpalmis.chat;

import java.util.List;

import com.cache.tontonpalmis.profil.Profil;


public class RowItem {
	List<Message> messages;
    Profil profil;
    public RowItem(List<Message> messages,Profil profil) {
        this.messages = messages;
        this.profil = profil;
    }

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public Profil getProfil() {
		return profil;
	}

	public void setProfil(Profil profil) {
		this.profil = profil;
	}
    
}