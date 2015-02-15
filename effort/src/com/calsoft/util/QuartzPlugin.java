package com.calsoft.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.ServletException;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.calsoft.report.action.ReportAction;

public class QuartzPlugin implements PlugIn {
	private static final Logger logger = Logger.getLogger("QuartzPlugin");
	@Override
	public void init(ActionServlet servlet, ModuleConfig config)throws ServletException {
		JobKey jobKeyA = new JobKey("jobA", "group1");
    	JobDetail jobA = JobBuilder.newJob(MonthlyReminderMail.class).withIdentity(jobKeyA).build();
    	 	
    	JobKey jobKeyB = new JobKey("jobB", "group2");
    	JobDetail jobB = JobBuilder.newJob(ReportAction.class).withIdentity(jobKeyB).build();
    	
    	JobKey jobKeyC = new JobKey("jobC", "group3");
    	JobDetail jobC = JobBuilder.newJob(DailyRemindar.class).withIdentity(jobKeyC).build();
		
		try {
			// Firing cron on every last working day of a month.
			Trigger trigger1 = TriggerBuilder.newTrigger().withIdentity("monthlyRemainderTrigger", "group1").withSchedule(CronScheduleBuilder.cronSchedule("0 30 10 LW * ?")).build();
			
			// Firing cron on every Friday 
			Trigger trigger2 = TriggerBuilder.newTrigger().withIdentity("weeklyRemainderTrigger", "group2").withSchedule(CronScheduleBuilder.cronSchedule("0 0 10 ? * FRI")).build();
			
			// Firing cron from Monday to Friday.
			Trigger trigger3 = TriggerBuilder.newTrigger().withIdentity("dailyRemindarMail", "group3").withSchedule(CronScheduleBuilder.cronSchedule("0 30 9 ? * MON-FRI")).build();
			
			Scheduler scheduler = new StdSchedulerFactory().getScheduler();
			scheduler.start();			
			scheduler.scheduleJob(jobA, trigger1);
			scheduler.scheduleJob(jobB, trigger2);
			scheduler.scheduleJob(jobC, trigger3);
		} 
		catch (SchedulerException e) {
			StringWriter stack = new StringWriter();
			e.printStackTrace(new PrintWriter(stack));
			logger.info("Exception occured while executing Monthly reminder mail "+stack.toString());
		}
	}
	@Override
	public void destroy() {
	}
}