package com.calsoft.leave.form;

import java.util.Date;
import java.util.List;

import org.apache.struts.action.ActionForm;

import com.calsoft.user.model.User;

/*Form class for LeaveEntry Module*/
@SuppressWarnings("serial")
public class LeaveForm extends ActionForm {
	private int id;
	private String userName;
	private String selectMonth;
	private String checkDate;
	private User user;
	private int user_id;
	private Date updated_on;
	private List<LeaveForm> leaveFormList;
	private String updatedDateString;
	private String work_category;

	public String getUpdatedDateString() {
		return updatedDateString;
	}

	public void setUpdatedDateString(String updatedDateString) {
		this.updatedDateString = updatedDateString;
	}

	public int getId() {
		return id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<LeaveForm> getLeaveFormList() {
		return leaveFormList;
	}

	public void setLeaveFormList(List<LeaveForm> leaveFormList) {
		this.leaveFormList = leaveFormList;
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

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSelectMonth() {
		return selectMonth;
	}

	public void setSelectMonth(String selectMonth) {
		this.selectMonth = selectMonth;
	}

	public String getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}

	public String getWork_category() {
		return work_category;
	}

	public void setWork_category(String work_category) {
		this.work_category = work_category;
	}
	
}
