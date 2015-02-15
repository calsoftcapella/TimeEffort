package com.calsoft.user.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import com.calsoft.performance.model.UserComment;
import com.calsoft.user.model.User;

@Entity
@Table(name="appraisal")
public class Appraisal {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id_app")
	private int id_app;
	@Column(name="start_date")
	private Date start_date;
	@Column(name="end_date")
	private Date end_date;
	@Column(name="comm_obj")
	private String comm_obj;
	@Column(name="spec_obj")
	private String spec_obj;
	@Column(name="apollo_manager")
	private String apollo_manager;
	@Column(name="objective_role")
	private String objective_role;
	
	@OneToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@OneToMany(mappedBy="appraisal")
	private Set<UserComment> userComment;
	
	public int getId_app() {
		return id_app;
	}
	public void setId_app(int id_app) {
		this.id_app = id_app;
	}
	public Date getStart_date() {
		return start_date;
	}
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}
	public Date getEnd_date() {
		return end_date;
	}
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}
	public String getComm_obj() {
		return comm_obj;
	}
	public void setComm_obj(String comm_obj) {
		this.comm_obj = comm_obj;
	}
	public String getSpec_obj() {
		return spec_obj;
	}
	public void setSpec_obj(String spec_obj) {
		this.spec_obj = spec_obj;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getApollo_manager() {
		return apollo_manager;
	}
	public void setApollo_manager(String apollo_manager) {
		this.apollo_manager = apollo_manager;
	}
	public String getObjective_role() {
		return objective_role;
	}
	public void setObjective_role(String objective_role) {
		this.objective_role = objective_role;
	}
	public Set<UserComment> getUserComment() {
		return userComment;
	}
	public void setUserComment(Set<UserComment> userComment) {
		this.userComment = userComment;
	}	
	
}
