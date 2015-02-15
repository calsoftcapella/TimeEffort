package com.calsoft.performance.form;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;
import com.calsoft.user.model.Appraisal;
@SuppressWarnings("serial")
public class UserCommentForm extends ValidatorForm {
	private int commentId;
	private int appraisal_Id;
	private String user_Comment;
	private String date;
	private String user_Name;        // username who added comment for given objective.
	private Appraisal userPerformanceLog;
	private int id;
	private Map<Integer, String> roleNames;	
	private String manager;
	private String client;
	private String user;
	private String accountManager;
	private int user_id;
	private List<String> commentAccessList; 
	private boolean commentOwnership;
		
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping,request);
		this.user_Comment = null;		
		}	
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getAccountManager() {
		return accountManager;
	}
	public void setAccountManager(String accountManager) {
		this.accountManager = accountManager;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date2) {
		this.date = date2;
	}
	public Appraisal getUserPerformanceLog() {
		return userPerformanceLog;
	}
	public void setUserPerformanceLog(Appraisal userPerformanceLog) {
		this.userPerformanceLog = userPerformanceLog;
	}
	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public int getAppraisal_Id() {
		return appraisal_Id;
	}

	public void setAppraisal_Id(int appraisal_Id) {
		this.appraisal_Id = appraisal_Id;
	}

	public String getUser_Comment() {
		return user_Comment;
	}

	public void setUser_Comment(String user_Comment) {
		this.user_Comment = user_Comment;
	}

	public String getUser_Name() {
		return user_Name;
	}

	public void setUser_Name(String user_Name) {
		this.user_Name = user_Name;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public Map<Integer, String> getRoleNames() {
		return roleNames;
	}
	public void setRoleNames(Map<Integer, String> roleNames) {
		this.roleNames = roleNames;
	}
	public List<String> getCommentAccessList() {
		return commentAccessList;
	}
	public void setCommentAccessList(List<String> commentAccessList) {
		this.commentAccessList = commentAccessList;
	}
	public boolean isCommentOwnership() {
		return commentOwnership;
	}
	public void setCommentOwnership(boolean commentOwnership) {
		this.commentOwnership = commentOwnership;
	}
	
}
