package com.calsoft.report.model;

import javax.persistence.*;

@Entity
@Table(name="report_mapping")
public class ReportMapping {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
   private int id;
	@Column(name="user_id")
   private int userId;
	@Column(name="report_map_id")
   private int reportMapId;
	public int getId() {
		return id;
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
	public int getReportMapId() {
		return reportMapId;
	}
	public void setReportMapId(int reportMapId) {
		this.reportMapId = reportMapId;
	}
	
	
}
