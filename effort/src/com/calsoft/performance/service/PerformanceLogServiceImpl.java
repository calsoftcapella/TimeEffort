package com.calsoft.performance.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.log4j.Logger;
import com.calsoft.performance.dao.PerformanceLogDao;
import com.calsoft.performance.daofactory.PerformanceLogDaoFactory;
import com.calsoft.performance.form.PerformanceLogForm;
import com.calsoft.performance.form.UserCommentForm;
import com.calsoft.user.form.UserForm;
import com.calsoft.user.model.Appraisal;
import com.calsoft.user.model.User;
import com.calsoft.util.EmailUtil;

public class PerformanceLogServiceImpl implements PerformanceLogService {
	private static final Logger logger = Logger.getLogger("PerformanceLogServiceImpl");
	@Override
	public PerformanceLogForm getPerformance(int userId, String period) {
		PerformanceLogForm performanceform = new PerformanceLogForm();
		PerformanceLogDao dao;
		dao = PerformanceLogDaoFactory.getPerformanceLogDao();
		Appraisal userPerformanceLog = new Appraisal();
		try {
			userPerformanceLog = dao.getPerformance(userId ,period);
			performanceform.setId(userPerformanceLog.getId_app());
			performanceform.setCommonObjective(userPerformanceLog.getComm_obj());
			performanceform.setSpecificObjective(userPerformanceLog.getSpec_obj());		
			performanceform.setUserId(userPerformanceLog.getUser().getUser_Id());	
		} catch (Exception e) {
			e.printStackTrace();
		}				
		return performanceform;
	}
	@Override
	public int accountManager(PerformanceLogForm performanceLogForm,int userId , int id){
		PerformanceLogDao dao = PerformanceLogDaoFactory.getPerformanceLogDao();
		int accountManager = dao.accountManager(performanceLogForm, userId, id);
		return accountManager;
	}
	@Override
	public List<PerformanceLogForm> getPeriodListForUser(int userId) {
		PerformanceLogDao dao;
		dao = PerformanceLogDaoFactory.getPerformanceLogDao();
		List<PerformanceLogForm> periodList = new ArrayList<PerformanceLogForm>();
		List<String> periodList1;
		try {
			periodList1 = dao.getPeriodListForUser(userId);
			Iterator<String> itr = periodList1.iterator();
			while (itr.hasNext()) {
				String period = itr.next();
				PerformanceLogForm form= new PerformanceLogForm();
				form.setPeriod(period);
				periodList.add(form);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return periodList;
	}

	@Override
	public boolean saveUserComment(PerformanceLogForm performanceLogForm, int userId) {
		PerformanceLogDao dao;
		dao = PerformanceLogDaoFactory.getPerformanceLogDao();
		boolean status = false;
		try {
			if(performanceLogForm != null){
				status = dao.saveUserComment(performanceLogForm, userId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			status = false;
		}
		return status;
	}

	@Override
	public List<UserCommentForm> getComments(int id,int userId, int admin_id_for_filtering_access_list) {
		PerformanceLogDao dao;
		dao = PerformanceLogDaoFactory.getPerformanceLogDao();
		List<UserCommentForm> commentsList = null;
		try {
			if(id!=0)
				commentsList = dao.getComments(id, userId, admin_id_for_filtering_access_list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return commentsList;
	}
	@Override
	public boolean deleteUserComment(int commentId) {
		PerformanceLogDao dao;
		dao = PerformanceLogDaoFactory.getPerformanceLogDao();
		boolean status;
		try {
			status =dao.deleteUserComment(commentId);
			if (status==true){
				return status;
			}
			else{
				return status;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean editUserComment(int commentId, String userComment) {
		PerformanceLogDao dao;
		dao = PerformanceLogDaoFactory.getPerformanceLogDao();
		boolean status;
		try {
			status =dao.editUserComment(commentId, userComment);
			if (status==true){
				return status;
			}
			else{
				return status;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	@Override
	public List<UserForm> getUsersListForPerformanceLogPage(int userId) {
		PerformanceLogDao dao;
		dao = PerformanceLogDaoFactory.getPerformanceLogDao();
		List<UserForm> usersList = null;
		try {
			usersList = dao.getUsersListForPerformanceLogPage(userId);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return usersList;
	}
	@Override
	public List<UserForm> getUsersListForPerformanceLogPageBasedOnResourceJoiningAndRelievingDate(int userId , Calendar cal, String objective_quarter) throws Exception{
		PerformanceLogDao dao;
		dao = PerformanceLogDaoFactory.getPerformanceLogDao();
		List<UserForm> usersList = new ArrayList<UserForm>();
		List<User> model_usersList = null;
		try {
			model_usersList = dao.getUsersListForPerformanceLogPageBasedOnResourceJoiningAndRelievingDate(userId, cal, objective_quarter);
			if(model_usersList != null)
				for (User user : model_usersList) {
					UserForm uf = getUserformFromModel(user);
					usersList.add(uf);
				}
		} catch (Exception e) {			
			e.printStackTrace();
		}
		return usersList;
	}
	private UserForm getUserformFromModel(User user) {
		UserForm uf = new UserForm();
		uf.setUserId(user.getUser_Id());
		uf.setUserName(user.getUser_name());
		return uf;
	}
	@Override
	public List<UserCommentForm> getRoleNames(int userId, int performanceUserID) {
		PerformanceLogDao dao;
		dao = PerformanceLogDaoFactory.getPerformanceLogDao();
		List<UserCommentForm> roleNames=new ArrayList<UserCommentForm>();
		Map<Integer, String> roleNames1=dao.getRoleNames(userId, performanceUserID);
		UserCommentForm form= new UserCommentForm();
		form.setRoleNames(roleNames1);
		roleNames.add(form);
		return roleNames;
	}
	@Override
	public String sendEmailNotification(PerformanceLogForm performanceLogForm, Properties props, List<String> mailDeatilsForCommentTo) {
		String message = "";
		PerformanceLogDao performanceDao = PerformanceLogDaoFactory.getPerformanceLogDao();
		try{
			String host = props.getProperty("host_name").trim();
			String port = props.getProperty("port_number").trim();
			String senderEmail = props.getProperty("admimEmail").trim();
			String userNameForsenderEmail = props.getProperty("adminEmailUsername").trim();
			String passeForSenderMail = props.getProperty("adminEmailPassword").trim();
			String cc1 = props.getProperty("account_manager_id").trim();	
			String bcc = props.getProperty("admimEmail").trim();
			String recipientMailId = performanceLogForm.getEmail();
			List<String> ccList = new ArrayList<String>();
			ccList.add(cc1);			
			List<String> allCCList = new ArrayList<String>();
			allCCList = performanceDao.getEmailInfoForAllAccessListAfterExcludingClient(mailDeatilsForCommentTo);
			allCCList.remove(recipientMailId);			
			if(!allCCList.isEmpty())
				ccList.addAll(allCCList);
			String username = performanceLogForm.getUserName();	
			String userNameForReceipient = "";
			if(username!=null && username.indexOf(" ")!=-1){
				userNameForReceipient = username.substring(0, username.indexOf(" "));
			}
			else if(username!=null){
				userNameForReceipient = username;
			}
			message = EmailUtil.sendEmailNotification(host, port, senderEmail, passeForSenderMail,userNameForsenderEmail, recipientMailId,
					" Comment added to account. ", "Hi "+userNameForReceipient+"," + "<br><br>"
							+ "&nbsp;Comment has been added to your timesheet account.<br>"
							+"Please visit <a href='http://pepq.calsoftlabs.com/effort'>http://pepq.calsoftlabs.com/effort</a> to view comment."
							+"<br>"
							+"<br>"
							+ "Regards" + "<br>" + "Raghavi", ccList, bcc);
			if(message.equalsIgnoreCase("") || message.equalsIgnoreCase("Sending Failed")){
				message = "Failed to send email notification to resource.";
				logger.info("Error While sending email to resource from EmailUtility");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			message = "Failed to send email notification to resource.";
		}
		return message;
	}
	public String sendEmailNotificationAfterResourceComment(PerformanceLogForm performanceLogForm, Properties props, List<String> mailDeatilsForCommentTo) {
		String message = "";
		PerformanceLogDao performanceDao = PerformanceLogDaoFactory.getPerformanceLogDao();
		try{
			String host = props.getProperty("host_name").trim();
			String port = props.getProperty("port_number").trim();
			String senderEmail = props.getProperty("admimEmail").trim();
			String userNameForsenderEmail = props.getProperty("adminEmailUsername").trim();
			String passeForSenderMail = props.getProperty("adminEmailPassword").trim();
			String recipientMailId = performanceLogForm.getEmail();
			String cc1 = props.getProperty("account_manager_id").trim();	
			String bcc = props.getProperty("admimEmail").trim();
			List<String> ccList = new ArrayList<String>();
			ccList.add(cc1);
			List<String> allCCList = new ArrayList<String>();
			allCCList = performanceDao.getEmailInfoForAllAccessListAfterExcludingClient(mailDeatilsForCommentTo);
			allCCList.remove(recipientMailId);
			if(!allCCList.isEmpty())
				ccList.addAll(allCCList);
			String username = performanceLogForm.getUserName();	
			String userNameForReceipient = "";
			if(username!=null && username.indexOf(" ")!=-1){
				userNameForReceipient = username.substring(0, username.indexOf(" "));
			}
			else if(username!=null){
				userNameForReceipient = username;
			}
			message = EmailUtil.sendEmailNotification(host, port, senderEmail, passeForSenderMail,userNameForsenderEmail, recipientMailId,
					" Comment added to Performance log. ", "Hi," + "<br><br>"
							+ "&nbsp;"+userNameForReceipient+" has added a comment to his/her performance log. Kindly view it in  their history table.<br>"
							+"Please visit <a href='http://pepq.calsoftlabs.com/effort'>http://pepq.calsoftlabs.com/effort</a> to view comment."
							+"<br>"
							+"<br>"
							+ "Regards" + "<br>" + "Raghavi", ccList, bcc);
			if(message.equalsIgnoreCase("") || message.equalsIgnoreCase("Sending Failed")){
				message = "Failed to send email notification to resource.";
				logger.info("Error While sending email to resource from EmailUtility");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			message = "Failed to send email notification to resource.";
		}
		return message;
	}
	@Override
	public List<PerformanceLogForm> checkPreviousWeekEntry(List<String> getAllDatesForPreviousWeek, List<UserForm> allocatedUserList) throws Exception{
		List<PerformanceLogForm> notEnteredUserList = new ArrayList<PerformanceLogForm>();
		PerformanceLogDao dao = PerformanceLogDaoFactory.getPerformanceLogDao();
		notEnteredUserList = dao.checkPreviousWeekEntry(getAllDatesForPreviousWeek, allocatedUserList);	
		return notEnteredUserList;
	}
	@Override
	public void sendNotificationEmail(String mailContent, Properties prop) {
		// Mailing details.		
		String host = prop.getProperty("host_name").trim();
		String port = prop.getProperty("port_number").trim();
		String userIds = prop.getProperty("adminEmailUsername").trim();
		String password = prop.getProperty("adminEmailPassword").trim();
		String from = prop.getProperty("admimEmail").trim();
		String cc1 = prop.getProperty("account_manager_id").trim();
		String bcc = prop.getProperty("admimEmail").trim();
		String to = prop.getProperty("dev_manager_email").trim();
		String subject = "Last Week Timesheet Report";
		List<String> ccList = new ArrayList<String>();
		ccList.add(cc1);
		try {
			EmailUtil.sendEmailNotification(host, port, userIds, password, from, to, subject, mailContent, ccList, bcc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void sendEmailAlertWhileDeletingComment(String commentDeletedBy, String commentDeletedFromUser, String period,
			String commentField, Properties props) throws Exception {
		// sending email notification after deleting added comment.
		String host = props.getProperty("host_name").trim();
		String port = props.getProperty("port_number").trim();
		String userIds = props.getProperty("adminEmailUsername").trim();
		String password = props.getProperty("adminEmailPassword").trim();
		String from = props.getProperty("admimEmail").trim();
		String cc1 = props.getProperty("account_manager_id").trim();
		String to = props.getProperty("admimEmail").trim();
		List<String> ccList = new ArrayList<String>();
		ccList.add(cc1);
		String subject = "Comment has been removed from PerformanceLog.";
		String text = "Hi," + "<br><br>"
				+ "&nbsp;&nbsp;Comment '"+commentField+"' added to "+commentDeletedFromUser+"'s account for period '"+period+"'<br>"
				+" has been removed by "+commentDeletedBy+"."
				+"<br>"
				+"<br>"
				+ "Thanks and Regards" + "<br>" + "Raghavi";
		EmailUtil.sendEmailNotificationWithOutAddingBCC(host, port, userIds, password, from, to, subject, text, ccList);	
	}
}
