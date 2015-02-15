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
@Table(name="evnt_role")
public class EventRole {

	@Id
	@GeneratedValue()
	@Column(name="role_id")
	private int role_id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="event_id")
	private UserEvent userEvent;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user;
	
	@Column(name="role_evnt")
	private String role_evnt;
	@Column(name="startpos")
	private String startpos;
	@Column(name="internal_view")
	private String internal_view;
	@Column(name="apollo_view")
	private String apollo_view;
	@Column(name="selected_of")
	private String selected_of;
	@Column(name="joined")
	private String joined;
	public int getRole_id() {
		return role_id;
	}
	public void setRole_id(int role_id) {
		this.role_id = role_id;
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
	public String getRole_evnt() {
		return role_evnt;
	}
	public void setRole_evnt(String role_evnt) {
		this.role_evnt = role_evnt;
	}
	public String getStartpos() {
		return startpos;
	}
	public void setStartpos(String startpos) {
		this.startpos = startpos;
	}
	public String getInternal_view() {
		return internal_view;
	}
	public void setInternal_view(String internal_view) {
		this.internal_view = internal_view;
	}
	public String getApollo_view() {
		return apollo_view;
	}
	public void setApollo_view(String apollo_view) {
		this.apollo_view = apollo_view;
	}
	public String getSelected_of() {
		return selected_of;
	}
	public void setSelected_of(String selected_of) {
		this.selected_of = selected_of;
	}
	public String getJoined() {
		return joined;
	}
	public void setJoined(String joined) {
		this.joined = joined;
	}
}
