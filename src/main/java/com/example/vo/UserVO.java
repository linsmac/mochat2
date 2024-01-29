package com.example.vo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")
public class UserVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)	
	private String userId;
	private String account;
	private String passWord;
	private String name;
	private String channel;
	
//    public UserVO(String userId, String account, String passWord) {
//        this.userId = userId;
//        this.account = account;
//        this.passWord = passWord;
//    }
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	

}
