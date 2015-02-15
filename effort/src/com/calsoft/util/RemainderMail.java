package com.calsoft.util;

import java.util.List;
import com.calsoft.report.form.ReportForm;

public class RemainderMail {
	
	private List<ReportForm> reportFormList;
	private List<String> dayList;
	private List<String> dt;
	public List<ReportForm> getReportFormList() {
		return reportFormList;
	}
	public void setReportFormList(List<ReportForm> reportFormList) {
		this.reportFormList = reportFormList;
	}
	public List<String> getDayList() {
		return dayList;
	}
	public void setDayList(List<String> dayList) {
		this.dayList = dayList;
	}
	public List<String> getDt() {
		return dt;
	}
	public void setDt(List<String> dt) {
		this.dt = dt;
	}
	
}
