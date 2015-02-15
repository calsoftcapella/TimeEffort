package com.calsoft.report.service;

public class ReportServiceFactory {

static	ReportServiceImpl reportServiceImpl=null;
	ReportServiceFactory(){}
	public static ReportServiceImpl getReportService()
	{
		reportServiceImpl=new ReportServiceImpl();
		return reportServiceImpl;
	}
	
}
