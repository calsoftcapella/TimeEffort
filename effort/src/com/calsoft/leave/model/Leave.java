package com.calsoft.leave.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
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
@Table(name = "leave_detail")

/*POJo for Leave Module*/

public class Leave {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@Column(name = "leave_month")
	private String leave_month;
	@Column(name = "leave_date")
	private String leave_date;
	@Column(name = "updated_on")
	private Date updated_on;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLeave_month() {
		return leave_month;
	}

	public void setLeave_month(String leave_month) {
		this.leave_month = leave_month;
	}

	public String getLeave_date() {
		return leave_date;
	}

	public void setLeave_date(String leave_date) {
		this.leave_date = leave_date;
	}

	public Date getUpdated_on() {
		return updated_on;
	}

	public void setUpdated_on(Date updated_on) {
		this.updated_on = updated_on;
	}

	public User getUser() {
		
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}	
	
	public static class SortBasedOnLeaveDate implements Comparator<Leave>{
		@Override
		public int compare(Leave o1, Leave o2) {
			DateFormat df = new SimpleDateFormat("d-MMM-yyyy");
			Calendar c1 = Calendar.getInstance();
			Calendar c2 = Calendar.getInstance();
			String dateFromFirstObj1 = o1.getLeave_date()+"-"+o1.getLeave_month();
			String dateFromFirstObj2 = o2.getLeave_date()+"-"+o2.getLeave_month();
			try{
				c1.setTime(df.parse(dateFromFirstObj1));
			}catch (Exception e) {}
			try{
				c2.setTime(df.parse(dateFromFirstObj2));
			}catch (Exception e) {}
			int compare = 0;
			if(c1.after(c2)){compare = 1;}
			else if(c1.before(c2)){ compare = -1;}
			else{compare = 0;}
			return compare;
		}	
	}
}
