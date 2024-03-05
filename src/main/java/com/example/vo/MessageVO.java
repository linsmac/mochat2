package com.example.vo;

public class MessageVO {

	private String name;
	private String message;
	private String timestamp;

	public MessageVO(String name, String message, String timestamp) {
		this.name = name;
		this.message = message;
		this.timestamp = timestamp;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	

}
