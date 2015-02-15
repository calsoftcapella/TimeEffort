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
import com.calsoft.report.service.ReportService;
import com.calsoft.report.service.ReportServiceFactory;
import com.calsoft.task.form.TaskForm;
import com.calsoft.user.form.UserForm;
import com.calsoft.user.service.UserService;

public class DailyRemindar implements Job{
	private static final Logger logger = Logger.getLogger("name");
	private static String glassfishInstanceRootPropertyName = "com.sun.aas.instanceRoot";
	private static String glassfishDomainConfigurationFolderName = "/applications/effort";
	//private static String glassfishDomainConfigurationFolderName = "/eclipseApps/effort";
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// Daily remindar mail for ideal time entry.
		logger.info("Printing message from Daily Remindar execute method ");
		File f1 = null;
		UserService userService = Factory.getUserService();
		try {
			f1 = readFileFromGlassfishDomainConfigFolder("getInformation.properties");
			Properties prop = new Properties();
			prop.load(new FileInputStream(f1));
			int userId = userService.getAccountManagerId();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();	
			cal.add(Calendar.DAY_OF_MONTH, -1);
			String previousDate = df.format(cal.getTime());	
			List<UserForm> allocatedUserList = userService.getAllocatedResourcesDetailsForRemindarMailBasedOnRelievingDate(userId, cal);
			List<Integer> user_ids = new ArrayList<Integer>();
			for (UserForm userForm : allocatedUserList) {
				user_ids.add(userForm.getUserId());
			}
			ReportService reportservice = ReportServiceFactory.getReportService();					
			List<TaskForm> taskFormList = reportservice.getAllUnassignedTaskDetailsForPreviousDay(user_ids, previousDate);
			File xsltFile = readFileFromGlassfishDomainConfigFolder("daily_remindar.xslt");
			if(xsltFile.exists() && !taskFormList.isEmpty()){
				// Sending email after getting task list containing idle task details
				String email_content = DailyMailTemplate.getHtmlMailContent(taskFormList, xsltFile, previousDate);
				List<String> messageStatus =  reportservice.sendIdleTimeReport(taskFormList, email_content, prop);				
				logger.info("Printing email content here and email status "+email_content+" \n"+messageStatus);
			}			
		} catch (Exception e) {
			e.printStackTrace();
			StringWriter stack = new StringWriter();
			e.printStackTrace(new PrintWriter(stack));
			logger.error("Exception while running daily remindar "+stack.toString());
		}
	}
	private static File readFileFromGlassfishDomainConfigFolder( final String fileName ) throws FileNotFoundException{
		final String instanceRoot = System.getProperty( glassfishInstanceRootPropertyName );	    
		logger.info(" instanceRoot Value from DailyRemindar "+instanceRoot);    
		if (instanceRoot == null){
			throw new FileNotFoundException( "Cannot find Glassfish instanceRoot. Is the com.sun.aas.instanceRoot system property set?" );
		}
		File configurationFolder = new File( instanceRoot + File.separator + glassfishDomainConfigurationFolderName );    
		File configFile = new File( configurationFolder, fileName );
		return configFile;
	}
}
