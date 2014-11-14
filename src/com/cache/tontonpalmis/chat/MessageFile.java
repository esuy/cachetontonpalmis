package com.cache.tontonpalmis.chat;

import java.io.Serializable;

public class MessageFile extends Message implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2608752757538706248L;
	private Long id;
	private String type;
	private int size;
	private String filename;
	private String path;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	

}
