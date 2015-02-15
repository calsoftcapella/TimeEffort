package com.calsoft.report.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.calsoft.user.model.User;

@Entity
@Table(name="user_evnt")
public class UserEvent {
	
	@Id
	@GeneratedValue()
	@Column(name="id")
	private int id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user;
	
	@Column(name="start_date")
	private String start_date;
	@Column(name="end_date")
	private String end_date;
	@Column(name="status_date")
	private String status_date;
	@Column(name="event_info")
	private String event_info;
	@Column(name="achieve")
	private String achieve;
	
	@OneToMany(mappedBy="userEvent")
	private Set<EventMile> eventMile;
	  
	  @OneToMany(mappedBy="userEvent")
	private Set<EventConst> eventConst;
	  @OneToMany(mappedBy="userEvent")
	private Set<EventRole> eventRole;
		
	public Set<EventMile> getEventMile() {
		return eventMile;
	}
	public void setEventMile(Set<EventMile> eventMile) {
		this.eventMile = eventMile;
	}
	public Set<EventConst> getEventConst() {
		return eventConst;
	}
	public void setEventConst(Set<EventConst> eventConst) {
		this.eventConst = eventConst;
	}
	public Set<EventRole> getEventRole() {
		return eventRole;
	}
	public void setEventRole(Set<EventRole> eventRole) {
		this.eventRole = eventRole;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public String getStatus_date() {
		return status_date;
	}
	public void setStatus_date(String status_date) {
		this.status_date = status_date;
	}
	public String getEvent_info() {
		return event_info;
	}
	public void setEvent_info(String event_info) {
		this.event_info = event_info;
	}
	public String getAchieve() {
		return achieve;
	}
	public void setAchieve(String achieve) {
		this.achieve = achieve;
	}
	
	
	

}
