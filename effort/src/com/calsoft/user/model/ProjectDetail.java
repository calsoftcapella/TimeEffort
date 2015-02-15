package com.calsoft.user.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@javax.persistence.Entity
@javax.persistence.Table(name="project_detail")
public class ProjectDetail {
	@Id
	@GeneratedValue
	@Column(name="project_id")
	private int project_id;
	@Column(name="project_name")
	private String project_name;
	@Column(name="project_start_date")
	private Date project_start_date;
	@Column(name="project_completion_date")
	private Date project_completion_date;
	public int getProject_id() {
		return project_id;
	}
	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}
	public String getProject_name() {
		return project_name;
	}
	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}
	public Date getProject_start_date() {
		return project_start_date;
	}
	public void setProject_start_date(Date project_start_date) {
		this.project_start_date = project_start_date;
	}
	public Date getProject_completion_date() {
		return project_completion_date;
	}
	public void setProject_completion_date(Date project_completion_date) {
		this.project_completion_date = project_completion_date;
	}
	
}
