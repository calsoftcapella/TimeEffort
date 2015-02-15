package com.calsoft.report.decorator;

import org.displaytag.decorator.TableDecorator;

import com.calsoft.report.model.Report;

public class UserReportAccessMappingDecorator  extends TableDecorator{
	
	 public String getUserName()
	    {
	        Report reportData = (Report)getCurrentRowObject();
	        return reportData.getUserName();
	    }
	 public String getParentUserName()
	    {
	        Report reportData = (Report)getCurrentRowObject();
	        return reportData.getParentUserName();
	    }
	 
	 
	

}
