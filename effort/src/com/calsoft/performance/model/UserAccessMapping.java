package com.calsoft.performance.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="comment_access_mapping")
public class UserAccessMapping {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="comment_access_id")
	private int comment_access_id;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name="comment_Id")
	private UserComment user_comment;
	
	@Column(name="valid_user")
	private int  valid_user_id;

	public int getComment_access_id() {
		return comment_access_id;
	}
	public void setComment_access_id(int comment_access_id) {
		this.comment_access_id = comment_access_id;
	}
	public UserComment getUser_comment() {
		return user_comment;
	}
	public void setUser_comment(UserComment user_comment) {
		this.user_comment = user_comment;
	}
	public int getValid_user_id() {
		return valid_user_id;
	}
	public void setValid_user_id(int valid_user_id) {
		this.valid_user_id = valid_user_id;
	}	
}
