package com.calsoft.report.dao;

public class ReportDaoFactory {
	static	HbnReportDAO reportDaoImpl;
	static
	{
		reportDaoImpl=new HbnReportDAO();
	}
	public static ReportDAO getReportDao()
	{
		if(reportDaoImpl==null)
		reportDaoImpl=new HbnReportDAO();
		return reportDaoImpl;
	}
}
