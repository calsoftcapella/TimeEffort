package com.calsoft.performance.form;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

@SuppressWarnings("serial")
public class PerformanceLogForm extends ActionForm {

	private int id;
	private int userId; 
	private String period;
	private String commonObjective;
	private String specificObjective;
	private String userComments;
	private String[] roleNames;
	private String currentDate;
	private String period2;
	private String userName;
	private String email;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCurrentDate() {
		return currentDate;
	}
	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}
	public String getPeriod2() {
		return period2;
	}
	public void setPeriod2(String period2) {
		this.period2 = period2;
	}

	public int getId() {
		return id;
	}
	public String[] getRoleNames() {
		return roleNames;
	}
	public void setRoleNames(String[] roleNames) {
		this.roleNames = roleNames;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getCommonObjective() {
		return commonObjective;
	}
	public void setCommonObjective(String commonObjective) {
		this.commonObjective = commonObjective;
	}
	public String getSpecificObjective() {
		return specificObjective;
	}
	public void setSpecificObjective(String specificObjective) {
		this.specificObjective = specificObjective;
	}
	public String getUserComments() {
		return userComments;
	}
	public void setUserComments(String userComments) {
		this.userComments = userComments;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getPeriod() {
		return period;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Override
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		String status = request.getParameter("validate");
		if(status != null && status.equalsIgnoreCase("true")){
			String user_comment = getUserComments();
			String[] role_name = getRoleNames();
			if(user_comment==null || user_comment ==""){
				errors.add(user_comment, new ActionMessage("error.user.comment"));
			}
			if(role_name == null){
				errors.add("roleName", new ActionMessage("error.role.select"));
			}
		}
		return errors;
	}
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		this.userComments = null;
		this.roleNames = null;
	}
}