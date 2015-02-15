package com.calsoft.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="contact")
public class Contact
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	@Column(name="contact_no")
	private String contact_no;	
	@Column(name="team")
	private String team;	
	@Column(name="apo_mang")
	private String apo_mang;
	@Column(name="sk_id")
	private String sk_id;
	@Column(name="location")
	private String location;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user;	

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getApo_mang() {
		return apo_mang;
	}

	public void setApo_mang(String apo_mang) {
		this.apo_mang = apo_mang;
	}

	public String getSk_id() {
		return sk_id;
	}

	public void setSk_id(String sk_id) {
		this.sk_id = sk_id;
	}

	public int getId(){
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContact_no(){
		return contact_no;
	}

	public void setContact_no(String contact_no) {
		this.contact_no = contact_no;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
}
