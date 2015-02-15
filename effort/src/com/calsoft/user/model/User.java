package com.calsoft.user.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import com.calsoft.task.model.Task;

@SuppressWarnings("serial")
@javax.persistence.Entity
@javax.persistence.Table(name="users")
public class User implements Serializable{
	@Id
	@GeneratedValue()
	@Column(name="user_id")
	private int user_Id;
	@Column(name="user_name")
	private String user_name;
	@Column(name="password")
	private String passWord;
	@Column(name="email")
	private String mail;
	@Column(name="status")
	private String status;	
	@Column(name="defpass")
	private String defpass;
	@Column(name="exit_date")
	private Date exit_date;
	@Column(name="freeze_timesheet")
	private Date freeze_timesheet;
	@Column(name="apollo_id")
	private String apollo_id;
	@Column(name="start_date")
	private Date start_date;
	@Column(name="project_id")
	private int project_id;
	
	@OneToMany(mappedBy="user")
	private Set<Task> task;
	@OneToMany(mappedBy="user")
	private Set<ClientFeedback> clientFeedback;
	@OneToMany(mappedBy="user")
	private Set<Contact> contact;
	
	public User(){}	
	public User(Object id ,Object name){
		user_Id=(Integer)id;
		user_name=(String)name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getUser_Id() {
		return user_Id;
	}
	public void setUser_Id(int user_Id) {
		this.user_Id = user_Id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public Set<Task> getTask() {
		return task;
	}
	public void setTask(Set<Task> task) {
		this.task = task;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getDefpass() {
		return defpass;
	}
	public void setDefpass(String defpass) {
		this.defpass = defpass;
	}
	public Date getExit_date() {
		return exit_date;
	}
	public void setExit_date(Date exit_date) {
		this.exit_date = exit_date;
	}
	public Date getFreeze_timesheet() {
		return freeze_timesheet;
	}
	public void setFreeze_timesheet(Date freeze_timesheet) {
		this.freeze_timesheet = freeze_timesheet;
	}
	public Set<ClientFeedback> getClientFeedback() {
		return clientFeedback;
	}
	public void setClientFeedback(Set<ClientFeedback> clientFeedback) {
		this.clientFeedback = clientFeedback;
	}
	public String getApollo_id() {
		return apollo_id;
	}
	public void setApollo_id(String apollo_id) {
		this.apollo_id = apollo_id;
	}
	public Date getStart_date() {
		return start_date;
	}
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}
	public Set<Contact> getContact() {
		return contact;
	}
	public void setContact(Set<Contact> contact) {
		this.contact = contact;
	}
	public int getProject_id() {
		return project_id;
	}
	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}
	
}
