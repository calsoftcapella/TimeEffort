package com.calsoft.task.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.calsoft.user.model.User;
@Entity
@Table(name="employee_task_detail")
public class Task implements Comparable<Task>{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	@Column(name="backlog_id")
	private String backlog_id;
	@Column(name="task_id")
	private String task_id;
	@Column(name="task_description")
	private String task_description;
	@Column(name="time")
	private String time;
	@Column(name="task_date")
	private Date task_date;
	@Column(name="status")
	private String status;
	@Column(name="work_status")
	private String work_status;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user;
	@Column(name="entry_time")
	private Date entry_time;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getBacklog_id() {
		return backlog_id;
	}
	public void setBacklog_id(String backlog_id) {
		this.backlog_id = backlog_id;
	}
	public String getTask_id() {
		return task_id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public void setTask_id(String task_id) {
		this.task_id = task_id;
	}
	public String getTask_description() {
		return task_description;
	}
	public void setTask_description(String task_description) {
		this.task_description = task_description;
	}
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Date getTask_date() {
		return task_date;
	}
	public void setTask_date(Date task_date) {
		this.task_date = task_date;
	}
	public String getStatus() {
		return status;
	}
	public String getWork_status() {
		return work_status;
	}
	public void setWork_status(String work_status) {
		this.work_status = work_status;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((task_date == null) ? 0 : task_date.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;
		if (task_date == null) {
			if (other.task_date != null)
				return false;
		} else if (!task_date.equals(other.task_date))
			return false;
		return true;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public int compareTo(Task o) {
		return getTask_date().compareTo(o.getTask_date());
	}
	public Date getEntry_time() {
		return entry_time;
	}
	public void setEntry_time(Date entry_time) {
		this.entry_time = entry_time;
	}
		
}
