package com.calsoft.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.calsoft.factory.Factory;
import com.calsoft.report.form.ReportForm;
import com.calsoft.report.service.ReportService;
import com.calsoft.report.service.ReportServiceFactory;
import com.calsoft.user.form.UserForm;
import com.calsoft.user.service.UserService;

public class MonthlyReminderMail implements Job{
	private static final Logger logger = Logger.getLogger("MonthlyReminderMail");
	private static String glassfishInstanceRootPropertyName = "com.sun.aas.instanceRoot";
	private static String glassfishDomainConfigurationFolderName = "/applications/effort";
	//private static String glassfishDomainConfigurationFolderName = "/eclipseApps/effort";

	@Override
	public void execute(JobExecutionContext context)throws JobExecutionException{
		// Method for sending monthly reminder on every month
		try{
			File f1 = readFileFromGlassfishDomainConfigFolder("getInformation.properties");
			logger.info("check file exist or not from MonthlyReminderMail "+f1.exists());
			UserService userService=Factory.getUserService();
			if(f1.exists()){
				Properties prop = new Properties();
				prop.load(new FileInputStream(f1));
				int userId = userService.getAdminUserId();
				String email = prop.getProperty("admimEmail");
				String pass = prop.getProperty("adminEmailPassword");
				if(userId!=0 && email!=null && pass!=null){
					DateFormat df = new SimpleDateFormat("yyyy-MM");
					DateFormat df1 = new SimpleDateFormat("E d");					
					Calendar cal = Calendar.getInstance();		
					String monthYear = df.format(cal.getTime());
					List<UserForm> allocatedUserList = userService.getAllocatedResourcesDetailsForMonthlyReminder(userId, cal);
					List<String> dayList = new ArrayList<String>();

					cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
					int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
					for(int i = 1; i<= days; i++){	
						cal.set(Calendar.DAY_OF_MONTH , i);
						dayList.add(df1.format(cal.getTime()));			
					}					
					File xmlTempFile = readFileFromGlassfishDomainConfigFolder("template.xslt");
					logger.info("from MonthlyReminderMail File Path is: "+xmlTempFile +" Checking existance "+xmlTempFile.exists());				
					ReportService reportservice = ReportServiceFactory.getReportService();
					if(xmlTempFile.exists()){
						List<List<ReportForm>> reportList = reportservice.getReportDataForReminderMail(monthYear, allocatedUserList);						
						reportservice.sendReminderMail(reportList, dayList, xmlTempFile, cal, prop);	
					}
				}
			}
		}
		catch(Exception e){
			StringWriter stack = new StringWriter();
			e.printStackTrace(new PrintWriter(stack));
			logger.info("Exception occured while executing Monthly reminder mail "+stack.toString());
		}
	}	
	private static File readFileFromGlassfishDomainConfigFolder( final String fileName ) throws FileNotFoundException{
		final String instanceRoot = System.getProperty( glassfishInstanceRootPropertyName );	    
		logger.info(" instanceRoot Value from MonthlyReminderMail "+instanceRoot);    
		if (instanceRoot == null){
			throw new FileNotFoundException( "Cannot find Glassfish instanceRoot. Is the com.sun.aas.instanceRoot system property set?" );
		}
		File configurationFolder = new File( instanceRoot + File.separator + glassfishDomainConfigurationFolderName );    
		File configFile = new File( configurationFolder, fileName );
		return configFile;
	}
}
