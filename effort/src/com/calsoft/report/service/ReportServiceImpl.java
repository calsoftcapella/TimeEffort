package com.calsoft.report.service;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;

import com.calsoft.factory.Factory;
import com.calsoft.report.dao.ReportDAO;
import com.calsoft.report.dao.ReportDaoFactory;
import com.calsoft.report.form.ReportForm;
import com.calsoft.report.model.Report;
import com.calsoft.task.form.TaskForm;
import com.calsoft.task.model.Task;
import com.calsoft.user.dao.UserDao;
import com.calsoft.user.form.UserForm;
import com.calsoft.user.model.User;
import com.calsoft.util.ConfigureMailContent;
import com.calsoft.util.EmailUtil;
public class ReportServiceImpl implements ReportService{
	private static final Logger logger = Logger.getLogger("ReportServiceImpl");
	@Override
	public List<ReportForm> getReportData(String year, String month,String[] allocatedResource ) throws Exception{
		List<ReportForm> reportFormList=new ArrayList<ReportForm>();
		ReportDAO reportDao=ReportDaoFactory.getReportDao();
		List<Report> reportList=reportDao.getReportData(year, month,allocatedResource);
		Iterator<Report> itr=reportList.iterator();
		while(itr.hasNext()){
			Report report=itr.next();
			ReportForm reportform=populateReportForm(report);			
			reportFormList.add(reportform);
		}
		return reportFormList;
	}

	public ReportForm populateReportForm(Report report){
		ReportForm reportForm=new ReportForm();
		reportForm.setUserName(report.getUserName());
		reportForm.setTime1(report.getTime1());
		reportForm.setTime2(report.getTime2());
		reportForm.setTime3(report.getTime3());
		reportForm.setTime4(report.getTime4());
		reportForm.setTime5(report.getTime5());
		reportForm.setTime6(report.getTime6());
		reportForm.setTime7(report.getTime7());
		reportForm.setTime8(report.getTime8());
		reportForm.setTime9(report.getTime9());
		reportForm.setTime10(report.getTime10());
		reportForm.setTime11(report.getTime11());
		reportForm.setTime12(report.getTime12());
		reportForm.setTime13(report.getTime13());
		reportForm.setTime14(report.getTime14());
		reportForm.setTime15(report.getTime15());
		reportForm.setTime16(report.getTime16());
		reportForm.setTime17(report.getTime17());
		reportForm.setTime18(report.getTime18());
		reportForm.setTime19(report.getTime19());
		reportForm.setTime20(report.getTime20());
		reportForm.setTime21(report.getTime21());
		reportForm.setTime22(report.getTime22());
		reportForm.setTime23(report.getTime23());
		reportForm.setTime24(report.getTime24());
		reportForm.setTime25(report.getTime25());
		reportForm.setTime26(report.getTime26());
		reportForm.setTime27(report.getTime27());
		reportForm.setTime28(report.getTime28());
		reportForm.setTime29(report.getTime29());
		reportForm.setTime30(report.getTime30());
		reportForm.setTime31(report.getTime31());
		reportForm.setStatus(report.getStatus());
		reportForm.setTotalTime(report.getTotalTime());
		reportForm.setEmailid(report.getEmailId());
		reportForm.setPeriod(report.getPeriod());
		reportForm.setUserId(report.getUserId());
		reportForm.setBilled_hours(report.getBilled_hours());
		reportForm.setAdditional_hours(report.getAdditional_hours());
		reportForm.setCompOff_availed_hrs(report.getCompOff_availed_hrs());
		reportForm.setComp_bal(report.getComp_bal());
		reportForm.setTotal_down_time(report.getTotal_down_time());
		reportForm.setApolloId(report.getApolloId());
		return reportForm;
	}


	@Override
	public List<List<Report>> getUserReportAllocation(int userId)throws Exception {
		List<List<Report>> combinedReportList=new ArrayList<List<Report>>();
		ReportDAO reportDao=ReportDaoFactory.getReportDao();
		combinedReportList=reportDao.getUserReportAllocation(userId);
		return combinedReportList;
	}

	@Override
	public void addResources(String[] allocatedResource,int userId)throws Exception {
		ReportDAO reportDao=ReportDaoFactory.getReportDao();
		reportDao.addResources(allocatedResource,userId);

	}


	@Override
	public void deleteResources(String[] allocatedResource, int userId) throws Exception{
		ReportDAO reportDao=ReportDaoFactory.getReportDao();
		reportDao.deleteResources(allocatedResource,userId);					

	}
	@Override
	public List<List<Report>> getUserReportAllocation() throws Exception {
		List<List<Report>> combinedReportList=new ArrayList<List<Report>>();
		ReportDAO reportDao=ReportDaoFactory.getReportDao();
		combinedReportList=reportDao.getUserReportAllocation();
		return combinedReportList;
	}


	@Override
	public List<ReportForm> getReportDataTeamWise(String year, String month, String[] detailedTeam, Calendar cal, int user_id_from_session) throws Exception {
		List<ReportForm> reportFormList=new ArrayList<ReportForm>();
		ReportDAO reportDao=ReportDaoFactory.getReportDao();
		List<Report> reportList=reportDao.getReportDataTeamWise(year, month, detailedTeam, cal, user_id_from_session);
		Iterator<Report> itr=reportList.iterator();
		while(itr.hasNext()){
			Report report=itr.next();
			ReportForm reportform=populateReportForm(report);			
			reportFormList.add(reportform);
		}
		return reportFormList;
	}

	@Override
	public List<ReportForm> getExceptionDashboard(String year, String month, List<UserForm> allocatedUserList) throws Exception {
		//Method for Account Manager Role Exception Dashboard
		List<ReportForm> reportFormList=new ArrayList<ReportForm>();
		ReportDAO reportDao=ReportDaoFactory.getReportDao();		
		List<User> modelUser = new ArrayList<User>();
		for(UserForm u_form : allocatedUserList){
			User u1 = null;
			u1 = getUserFromForm(u_form);
			modelUser.add(u1);
		}		
		List<Report> reportList=reportDao.getExceptionDashboard(year, month,modelUser);
		Iterator<Report> itr=reportList.iterator();
		while(itr.hasNext())
		{
			Report report=itr.next();
			ReportForm reportform=populateReportForm(report);			
			reportFormList.add(reportform);
		}
		return reportFormList;
	}
	private User getUserFromForm(UserForm u_form) {
		// Getting User from Form
		User u1 = new User();
		u1.setUser_Id(u_form.getUserId());
		return u1;
	}
	@Override
	public List<List<ReportForm>> getReportDataForReminderMail(String monthYear, List<UserForm> allocatedUserList) throws Exception {
		List<List<ReportForm>> reportFormList = new ArrayList<List<ReportForm>>();
		ReportDAO reportDao = ReportDaoFactory.getReportDao();
		List<List<Report>> reportListFromDAO = reportDao.getReportDataForReminderMail(monthYear, allocatedUserList);
		Iterator<List<Report>> reportIterator = reportListFromDAO.iterator();
		while (reportIterator.hasNext()) {
			List<ReportForm> reportList = new ArrayList<ReportForm>();			
			List<Report> list_report = reportIterator.next();
			for (Report report : list_report) {					
				ReportForm reportform = populateReportForm(report);			
				reportList.add(reportform);
			}
			reportFormList.add(reportList);
		}
		return reportFormList;
	}
	@Override
	public void sendReminderMail(List<List<ReportForm>> reportList, List<String> dayList, File xmlTempFile, Calendar cal, Properties prop) throws Exception {
		String host = prop.getProperty("host_name").trim();		
		String port = prop.getProperty("port_number").trim();
		String userIds = prop.getProperty("adminEmailUsername").trim();
		String password = prop.getProperty("adminEmailPassword").trim();
		String from = prop.getProperty("admimEmail").trim();
		String cc = prop.getProperty("account_manager_id").trim();
		DateFormat df = new SimpleDateFormat("MMMM yyyy");
		String emailSubjectTxt = "Reminder "+df.format(cal.getTime())+" Timesheet";
		String bcc = userIds;
		String mailStatus = "";
		if(!reportList.isEmpty()){
			for (int i = 0; i < reportList.size(); i++) {
				List<String>  toList = new ArrayList<String>();
				List<ReportForm> managerWiseReportList = (List<ReportForm>) reportList.get(i);						
				if(!managerWiseReportList.isEmpty()){
					String getMailContent = ConfigureMailContent.getHtmlMailContent(managerWiseReportList, dayList, xmlTempFile);
					for (ReportForm reportForm : managerWiseReportList) {						
						if(reportForm.getApolloId() != null && reportForm.getApolloId().trim().length() >5 ){
							if(!toList.contains(reportForm.getApolloId()) && !toList.contains(reportForm.getEmailid()))
								toList.add(reportForm.getApolloId());			// Adding resource mapped under manager for EMAIL TO list.
						}else{
							if(!toList.contains(reportForm.getApolloId()) && !toList.contains(reportForm.getEmailid()))
								toList.add(reportForm.getEmailid());			// Adding resource mapped under manager for EMAIL TO list.
						}
					}
					if(toList.contains(cc)){
						mailStatus= EmailUtil.sendEmailToMultiple(host, port, userIds, password, from, toList, emailSubjectTxt, getMailContent, null, bcc);
					}else{
						mailStatus= EmailUtil.sendEmailToMultiple(host, port, userIds, password, from, toList, emailSubjectTxt, getMailContent, cc, bcc);
					}
					logger.info("Printing mail details from Service  "+toList+" email content "+getMailContent+" Status "+mailStatus);
					mailStatus = "";
				}
			}
		}	
	}
	@Override
	public List<ReportForm> getDefaulterListDetails(String[] allocatedResource, Calendar cal) throws Exception {
		List<ReportForm> listOfDefaullter = new ArrayList<ReportForm>();
		ReportDAO reportDao=ReportDaoFactory.getReportDao();
		List<Report> reportListForDefaulter = new ArrayList<Report>();
		reportListForDefaulter = reportDao.getDefaulterListDetails(allocatedResource, cal);
		if(!reportListForDefaulter.isEmpty()){
			for (Report report : reportListForDefaulter) {
				ReportForm reportForm = getReportFromReportForm(report);
				listOfDefaullter.add(reportForm);
			}
		}		
		return listOfDefaullter;
	}
	private ReportForm getReportFromReportForm(Report report) {
		ReportForm r_form = new ReportForm();
		if(report!=null){
			r_form.setUserName(report.getUserName());
			r_form.setPeriod(report.getPeriod());
		}
		return r_form;
	}
	@Override
	public List<ReportForm> getResourceDetailWhoMissedEntry(List<UserForm> allocatedUserList) throws Exception {
		// Method for weekly reminder mail.
		List<ReportForm> defaulterListReport = new ArrayList<ReportForm>();
		List<Report> reportForDefaulters = new ArrayList<Report>();
		List<User> allocatedUserListForDb = new ArrayList<User>();
		for (UserForm u_form : allocatedUserList) {
			User u1 = getUserFromForm(u_form);
			allocatedUserListForDb.add(u1);
		}
		ReportDAO reportDao=ReportDaoFactory.getReportDao();
		UserDao userDao = Factory.getDao();
		int account_manager_id = userDao.getAccountManagerId();
		int admin_user_id = userDao.getAdminUserId();
		reportForDefaulters = reportDao.getResourceDetailWhoMissedEntry(allocatedUserListForDb, account_manager_id, admin_user_id);
		for (Report report : reportForDefaulters) {
			ReportForm r_form = populateReportForm(report);
			defaulterListReport.add(r_form);
		}
		return defaulterListReport;
	}

	@Override
	public String sendWeeklyReminderMail(List<ReportForm> reportList, Calendar cal, Properties props) {
		// Sending email notification for missing entry.
		String message = "";
		try{
			String host = props.getProperty("host_name").trim();
			String port = props.getProperty("port_number").trim();
			String senderEmail = props.getProperty("admimEmail").trim();
			String userNameForsenderEmail = props.getProperty("admimEmail").trim();
			String passForSenderMail = props.getProperty("adminEmailPassword").trim();
			String cc1 = props.getProperty("account_manager_id").trim();	
			String bcc = props.getProperty("admimEmail").trim();
			String mailContent = "";
			String subject = "PEPQ weekly reminder.";
			List<String> ccList = null;
			ReportDAO reportDao = ReportDaoFactory.getReportDao();
			for (ReportForm  report_form : reportList) {
				ccList = new ArrayList<String>();
				String email_to = "";
				String apollo_id = report_form.getApolloId();
				String calsoft_id = report_form.getEmailid();								
				if(apollo_id == null || (apollo_id != null && apollo_id.length() < 5)){
					email_to = calsoft_id;
				}
				else if(apollo_id != null && apollo_id.length() > 5){
					email_to = apollo_id;
				}
				String user_name = report_form.getUserName();
				int user_id = report_form.getUserId();
				if(user_name.indexOf(' ')!=-1){
					user_name = user_name.substring(0, user_name.indexOf(' '));
				}
				ccList = reportDao.getManagerDetailForResource(user_id);
				ccList.add(0, cc1);
				mailContent = "Hi "+user_name+"," + "<br><br>"
						+ "&nbsp;Your timesheet is due "+report_form.getPeriod()+"&nbsp;"
						+"Please close it "+"<br>"+" at the earliest."
						+"<br>"
						+"<br>"
						+ "Thanks & Regards" + "<br>" + "Raghavi";
				try{										
					EmailUtil.sendEmailNotification(host, port, userNameForsenderEmail, passForSenderMail, senderEmail, email_to, subject, mailContent, ccList, bcc);
				}
				catch (Exception e) {
					// Handling exception while sending mail to resources.
					StringWriter stack = new StringWriter();
					e.printStackTrace(new PrintWriter(stack));
					logger.info("Exception occured while sending mail to "+user_name+" : from weekly Reminder "+stack.toString());
				}
				logger.info("Username details "+user_name+" Mail "+report_form.getPeriod());
				message = message+email_to+ccList+bcc+"\n";
			}
		}
		catch (Exception e) {
			StringWriter stack = new StringWriter();
			e.printStackTrace(new PrintWriter(stack));
			logger.error("Printing error Info while sending weekly Reminder Mail: " +stack.toString());
		}
		return message;
	}
	@Override
	public List<ReportForm> getCompOffReport(String[] allocatedResource, String year, String month, Properties props) throws Exception {
		//Interating DB for getting CompOffReport.
		List<ReportForm> compOffReportList = new ArrayList<ReportForm>();
		ReportDAO reportDao=ReportDaoFactory.getReportDao();
		List<Report> reportList=reportDao.getCompOffReport(allocatedResource, year, month, props);
		Iterator<Report> itr=reportList.iterator();
		while(itr.hasNext()){
			Report report=itr.next();
			ReportForm reportform=populateReportForm(report);			
			compOffReportList.add(reportform);
		}

		return compOffReportList;
	}

	@Override
	public String freezeTimesheet(String[] allocatedResource, Calendar cal)throws Exception {
		// Freezing timesheet for selected resource.
		String freezingStatus = null;
		ReportDAO reportDao=ReportDaoFactory.getReportDao();
		freezingStatus = reportDao.freezeTimesheet(allocatedResource, cal);
		return freezingStatus;
	}
	@Override
	public String unfreezeTimesheet(String[] allocatedResource, String reportMonth) throws Exception {
		// Freezing timesheet for selected resource.
		String unfreezingStatus = null;
		ReportDAO reportDao=ReportDaoFactory.getReportDao();
		unfreezingStatus = reportDao.unfreezeTimesheet(allocatedResource, reportMonth);
		return unfreezingStatus;
	}
	@Override
	public String checkFreezingEntryStatusForSelctedResource(String[] splittingUserId, Calendar calForSelectedMonth) {
		// Checking time entry freezing functionality for selected resources.
		String status = "";
		ReportDAO reportDao = ReportDaoFactory.getReportDao();
		status = reportDao.checkFreezingEntryStatusForSelctedResource(splittingUserId, calForSelectedMonth);
		return status;
	}

	@Override
	public List<TaskForm> getTimestampForTimeEntries(String[] allocatedResource, Calendar cal) throws Exception {
		//Added for time entry timestamp field.
		List<TaskForm> taskform_list = new ArrayList<TaskForm>();
		List<Task> task_list = new ArrayList<Task>();
		ReportDAO reportDao = ReportDaoFactory.getReportDao();
		task_list = reportDao.getTimestampForTimeEntries(allocatedResource, cal);
		for (Task task : task_list) {
			TaskForm tf = populateTaskFormFromTask(task);
			taskform_list.add(tf);
		}
		return taskform_list;
	}
	public TaskForm populateTaskFormFromTask(Task task)throws Exception{
		DateFormat formatter1 = new SimpleDateFormat("MM/dd/yyyy");
		DateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		TaskForm taskForm=new TaskForm();
		taskForm.setId(task.getId());
		taskForm.setBacklog_id(task.getBacklog_id());
		taskForm.setStatus(task.getStatus());
		String date = formatter1.format(task.getTask_date());  
		taskForm.setTask_date(date);
		taskForm.setTask_description(task.getTask_description().replace("`", "'"));   // Modification Done by Prem for replacing ` to ' for displaying.
		taskForm.setTask_id(task.getTask_id());
		taskForm.setTime(task.getTime());
		if(task.getUser()!=null){
			if(task.getUser().getUser_name()!=null&&!(task.getUser().getUser_name().equals(""))){
				taskForm.setUserName(task.getUser().getUser_name());
			}
		}
		if(task.getEntry_time() != null){
			taskForm.setEntry_time(formatter2.format(task.getEntry_time()));
		}
		return taskForm;
	}

	@Override
	public List<TaskForm> getAllUnassignedTaskDetailsForPreviousDay(List<Integer> user_ids, String previousDate) throws Exception {
		// Getting all Time entry filled with Unassigned Task category.
		List<TaskForm> task_list = new ArrayList<TaskForm>();
		List<Task> task_list_for_previousDay = new ArrayList<Task>();
		ReportDAO reportDao = ReportDaoFactory.getReportDao();
		task_list_for_previousDay = reportDao.getAllUnassignedTaskDetailsForPreviousDay(user_ids, previousDate);
		for (Task task : task_list_for_previousDay) {
			TaskForm tf = populateTaskFormFromTask(task);
			task_list.add(tf);
		}
		return task_list;
	}

	@Override
	public List<String> sendIdleTimeReport(List<TaskForm> taskFormList, String email_content, Properties prop) throws Exception {
		// Sending idle time entry report
		List<String> list = new ArrayList<String>();		
		String host = prop.getProperty("host_name").trim();
		String port = prop.getProperty("port_number").trim();
		String userIds = prop.getProperty("adminEmailUsername").trim();
		String password = prop.getProperty("adminEmailPassword").trim();
		String from = prop.getProperty("admimEmail").trim();
		String account_manager_userId = prop.getProperty("account_manager_id").trim();
		String to = account_manager_userId;	
		String emailSubjectTxt = "Idle Time Entry Report";
		String bcc = from;		
		String emailStatus = EmailUtil.sendEmailForIdleReport(host, port, userIds, password, from, to, emailSubjectTxt, email_content, bcc);	
		list.add(emailStatus);
		return list;
	}
	/*  
	@Override
	public String saveWeeklyStatus(WeeklyForm form, int userId,String startDate, String endDate, List<Deliverables> deliv,List<Constraints> constr, List<Recruitment_status> recrut)
			throws Exception {
		String message = null;
		UserEvent userEvent = populateWeeklyForm(form, userId,startDate,endDate, deliv, constr,recrut); 
		ReportDAO reportDao=ReportDaoFactory.getReportDao();
		message = reportDao.saveWeeklyStatus(userEvent);
		return message;
	}


	private UserEvent populateWeeklyForm(WeeklyForm form, int userId,String startDate, String endDate, List<Deliverables> deliv,
			List<Constraints> constr, List<Recruitment_status> recrut) {

		User user = new User();
		user.setUser_Id(userId);
		UserEvent userEvent = new UserEvent();
		userEvent.setUser(user);
		userEvent.setStart_date(startDate);
		userEvent.setEnd_date(endDate);
		if(form!=null){
			userEvent.setStatus_date(form.getStatusDate());
			userEvent.setEvent_info(form.getEventInfo());
			userEvent.setAchieve(form.getAchievements());
		}
		Set<EventMile> setMile = new HashSet<EventMile>();		
		Set<EventConst> setCont = new HashSet<EventConst>();		
		Set<EventRole> setRole = new HashSet<EventRole>();

		if(!(deliv==null && constr==null && recrut==null)){
			for (Deliverables  d1: deliv) {
				EventMile eMile = new EventMile();
				eMile.setUser(user);
				eMile.setUserEvent(userEvent);
				eMile.setDetail_mile(d1.getDetails_miles());
				eMile.setOwner_mile(d1.getOwner_mile());
				eMile.setRemark_mile(d1.getRemark_mile());
				setMile.add(eMile);		
			}		
			for (Constraints  c1: constr) {			
				EventConst eConst = new EventConst();
				eConst.setUser(user);
				eConst.setUserEvent(userEvent);
				eConst.setOndate_const(c1.getOnDate_const());
				eConst.setDetail_const(c1.getDetail_const());
				eConst.setOwner_const(c1.getOwner_const());
				eConst.setRemark_const(c1.getRemark_const());
				eConst.setEta_const(c1.getEta_const());
				setCont.add(eConst);		
			}

			for (Recruitment_status  r1: recrut) {		
				EventRole eRole = new EventRole();
				eRole.setUser(user);
				eRole.setUserEvent(userEvent);
				eRole.setRole_evnt(r1.getRole_status());
				eRole.setStartpos(r1.getPosition());
				eRole.setInternal_view(r1.getInternalInter());
				eRole.setApollo_view(r1.getApolloInter());
				eRole.setSelected_of(r1.getSelectedOff());
				eRole.setJoined(r1.getJoined());
				setRole.add(eRole);
			}
		}		
		userEvent.setEventMile(setMile);
		userEvent.setEventConst(setCont);
		userEvent.setEventRole(setRole);
		return userEvent;
	}


	@Override
	public WeeklyForm getEditableWeeklyForm(String startDate, String endDate,int userId) throws Exception {
		WeeklyForm weeklyForm = new WeeklyForm();
		UserEvent userEvent = populateWeeklyForm(null,userId,startDate,endDate,null,null,null);
		ReportDAO reportDao=ReportDaoFactory.getReportDao();
		userEvent = reportDao.getEditableWeeklyForm(userEvent);
		weeklyForm = populateaUserEventToWeeklyForm(userEvent);
		return weeklyForm;
	}


	private WeeklyForm populateaUserEventToWeeklyForm(UserEvent userEvent) {
		WeeklyForm weeklyForm = new WeeklyForm();
		if(userEvent!=null)
		{
			weeklyForm.setEvent_id(userEvent.getId());
			weeklyForm.setStatusDate(userEvent.getStatus_date());
			weeklyForm.setEventInfo(userEvent.getEvent_info());
			weeklyForm.setAchievements(userEvent.getAchieve());
			Set<EventMile> eventMile = userEvent.getEventMile();
			Set<Deliverables> setDeliv = new HashSet<Deliverables>();
			for (EventMile eventMile2 : eventMile) {
				Deliverables d1 = new Deliverables();
				d1.setDetails_miles(eventMile2.getDetail_mile());
				d1.setOwner_mile(eventMile2.getOwner_mile());
				d1.setRemark_mile(eventMile2.getRemark_mile());
				setDeliv.add(d1);
			}			
			weeklyForm.setDeliList(setDeliv);

			Set<EventConst> eventCont = userEvent.getEventConst();
			Set<Constraints> setConst = new HashSet<Constraints>();
			for (EventConst eventConst2 : eventCont) {
				Constraints  c1 = new Constraints();
				c1.setOnDate_const(eventConst2.getOndate_const());
				c1.setDetail_const(eventConst2.getDetail_const());
				c1.setOwner_const(eventConst2.getOwner_const());
				c1.setRemark_const(eventConst2.getRemark_const());
				c1.setEta_const(eventConst2.getEta_const());
				setConst.add(c1);
			}
			weeklyForm.setConstList(setConst);

			Set<EventRole> eventRole = userEvent.getEventRole();
			Set<Recruitment_status> setRecrt = new HashSet<Recruitment_status>();
			for (EventRole eventRole2 : eventRole) {
				Recruitment_status r1 = new Recruitment_status();
				r1.setRole_status(eventRole2.getRole_evnt());
				r1.setPosition(eventRole2.getStartpos());
				r1.setInternalInter(eventRole2.getInternal_view());
				r1.setApolloInter(eventRole2.getApollo_view());
				r1.setSelectedOff(eventRole2.getSelected_of());
				r1.setJoined(eventRole2.getJoined());
				setRecrt.add(r1);
			}
			weeklyForm.setRecrList(setRecrt);											
		}

		return weeklyForm;
	}*/
}
