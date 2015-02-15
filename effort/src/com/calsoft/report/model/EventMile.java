package com.calsoft.report.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.calsoft.user.model.User;

@Entity
@Table(name="evnt_mile")
public class EventMile{

	@Id
	@GeneratedValue()
	@Column(name="id_mile")
	private int id_mile;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_event")
	private UserEvent userEvent;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user;
	
	@Column(name="detail_mile")
	private String detail_mile;	
	@Column(name="owner_mile")
	private String owner_mile;	
	@Column(name="remark_mile")
	private String remark_mile;
	public int getId_mile() {
		return id_mile;
	}
	public void setId_mile(int id_mile) {
		this.id_mile = id_mile;
	}
	public UserEvent getUserEvent() {
		return userEvent;
	}
	public void setUserEvent(UserEvent userEvent) {
		this.userEvent = userEvent;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getDetail_mile() {
		return detail_mile;
	}
	public void setDetail_mile(String detail_mile) {
		this.detail_mile = detail_mile;
	}
	public String getOwner_mile() {
		return owner_mile;
	}
	public void setOwner_mile(String owner_mile) {
		this.owner_mile = owner_mile;
	}
	public String getRemark_mile() {
		return remark_mile;
	}
	public void setRemark_mile(String remark_mile) {
		this.remark_mile = remark_mile;
	}
	
}
