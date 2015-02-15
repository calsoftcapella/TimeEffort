package com.calsoft.performance.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.calsoft.user.model.Appraisal;

@Entity
@Table(name="user_comments")
public class UserComment {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="comment_Id")
	private int commentId;		
	@Column(name="user_comment")
	private String user_Comment;
	@Column(name="date")
	private String date;
	@Column(name="user_name")
	private String user_Name;	
	@Column(name="manager")
	private String manager;
	@Column(name="client")
	private String client;
	@Column(name="user")
	private String user;
	@Column(name="acc_Manager")
	private String accountManager;
			
	@ManyToOne(cascade = CascadeType.ALL )
	@JoinColumn(name="userPerformanceLog_id" )
   	private Appraisal appraisal;
	
	@OneToMany(mappedBy="user_comment")
	private Set<UserAccessMapping> comment_mapping;
	
	@Column(name="comment_logged_by")
	private int comment_logged_by;
	
	public String getAccountManager() {
		return accountManager;
	}
	public void setAccountManager(String accountManager) {
		this.accountManager = accountManager;
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
	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int commentId) {
		this.commentId = commentId;
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
	public Appraisal getAppraisal() {
		return appraisal;
	}
	public void setAppraisal(Appraisal appraisal) {
		this.appraisal = appraisal;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Set<UserAccessMapping> getComment_mapping() {
		return comment_mapping;
	}
	public void setComment_mapping(Set<UserAccessMapping> comment_mapping) {
		this.comment_mapping = comment_mapping;
	}
	public int getComment_logged_by() {
		return comment_logged_by;
	}
	public void setComment_logged_by(int comment_logged_by) {
		this.comment_logged_by = comment_logged_by;
	}
	
}
