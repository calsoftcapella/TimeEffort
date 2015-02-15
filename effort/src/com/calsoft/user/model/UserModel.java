package com.calsoft.user.model;

import java.util.Date;

public class UserModel {
	private int user_id;
	private String user_name;
	private int role_id;
	private Date joining_date;
	private Date releiving_date;
	private String calsoft_id;
	private String apollo_id;
	
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public int getRole_id() {
		return role_id;
	}
	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}
	public Date getJoining_date() {
		return joining_date;
	}
	public void setJoining_date(Date joining_date) {
		this.joining_date = joining_date;
	}
	public Date getReleiving_date() {
		return releiving_date;
	}
	public void setReleiving_date(Date releiving_date) {
		this.releiving_date = releiving_date;
	}
	public String getCalsoft_id() {
		return calsoft_id;
	}
	public void setCalsoft_id(String calsoft_id) {
		this.calsoft_id = calsoft_id;
	}
	public String getApollo_id() {
		return apollo_id;
	}
	public void setApollo_id(String apollo_id) {
		this.apollo_id = apollo_id;
	}
	
}
