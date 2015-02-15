package com.calsoft.user.model;

import java.util.Comparator;

public class UserTask implements Comparator<UserTask>{
	private String time;
	private String task_date;
	private String status;
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getTask_date() {
		return task_date;
	}
	public void setTask_date(String task_date) {
		this.task_date = task_date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
		UserTask other = (UserTask) obj;
		if (task_date == null) {
			if (other.task_date != null)
				return false;
		} else if (!task_date.equals(other.task_date))
			return false;
		return true;
	}
	@Override
	public int compare(UserTask o1, UserTask o2) {		
		return o1.getTask_date().compareTo(o2.getTask_date());
	}
	
}
