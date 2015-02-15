package com.calsoft.user.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@javax.persistence.Entity
@javax.persistence.Table(name="client_feedback")
public class ClientFeedback {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="c_fd_id")
	private int feedbackId;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user;	
	@Column(name="image_loc")
	private String image_loc;
	@Column(name="time_of_creation")
	private Timestamp time_stamp;
	@Column(name="viewed_users")
	private String viewed_users;
	@Column(name="feedback_summary")
	private String feedback_summary;
	public int getFeedbackId() {
		return feedbackId;
	}
	public void setFeedbackId(int feedbackId) {
		this.feedbackId = feedbackId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getImage_loc() {
		return image_loc;
	}
	public void setImage_loc(String image_loc) {
		this.image_loc = image_loc;
	}
	public Timestamp getTime_stamp() {
		return time_stamp;
	}
	public void setTime_stamp(Timestamp time_stamp) {
		this.time_stamp = time_stamp;
	}
	public String getViewed_users() {
		return viewed_users;
	}
	public void setViewed_users(String viewed_users) {
		this.viewed_users = viewed_users;
	}
	public String getFeedback_summary() {
		return feedback_summary;
	}
	public void setFeedback_summary(String feedback_summary) {
		this.feedback_summary = feedback_summary;
	}
	
}
