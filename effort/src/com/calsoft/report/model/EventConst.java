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
@Table(name="evnt_const")
public class EventConst {
	
	@Id
	@GeneratedValue()
	@Column(name="const_id")
	private int const_id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="event_id")
	private UserEvent userEvent;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user;
	
	@Column(name="ondate_const")
	private String ondate_const;
	@Column(name="detail_const")
	private String detail_const;
	@Column(name="owner_const")
	private String owner_const;
	@Column(name="remark_const")
	private String remark_const;
	@Column(name="eta_const")
	private String eta_const;
	public int getConst_id() {
		return const_id;
	}
	public void setConst_id(int const_id) {
		this.const_id = const_id;
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
	public String getOndate_const() {
		return ondate_const;
	}
	public void setOndate_const(String ondate_const) {
		this.ondate_const = ondate_const;
	}
	public String getDetail_const() {
		return detail_const;
	}
	public void setDetail_const(String detail_const) {
		this.detail_const = detail_const;
	}
	public String getOwner_const() {
		return owner_const;
	}
	public void setOwner_const(String owner_const) {
		this.owner_const = owner_const;
	}
	public String getRemark_const() {
		return remark_const;
	}
	public void setRemark_const(String remark_const) {
		this.remark_const = remark_const;
	}
	public String getEta_const() {
		return eta_const;
	}
	public void setEta_const(String eta_const) {
		this.eta_const = eta_const;
	}
	

}
