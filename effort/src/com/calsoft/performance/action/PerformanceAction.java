package com.calsoft.performance.action;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import com.calsoft.factory.Factory;
import com.calsoft.performance.form.PerformanceLogForm;
import com.calsoft.performance.form.UserCommentForm;
import com.calsoft.performance.service.PerformanceLogService;
import com.calsoft.performance.servicefactory.PerformanceServiceFactory;
import com.calsoft.user.form.UserForm;
import com.calsoft.user.service.UserService;
import com.calsoft.util.TimeUtility;

public class PerformanceAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger("PerformanceAction");
	PerformanceLogService service;
	UserService userService;

	public ActionForward giveComment(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception{
		HttpSession session = request.getSession();
		PerformanceLogForm performanceLogForm=(PerformanceLogForm)form;
		if(session.getAttribute("user_id")!=null){
			service = PerformanceServiceFactory.getPerformanceService();
			int userId = (Integer) session.getAttribute("user_id");
			boolean status = false;
			status =service.saveUserComment(performanceLogForm, userId);			
			int selectedResourceId = performanceLogForm.getUserId();			
			UserService userService = Factory.getUserService();
			if(selectedResourceId!=0){
				UserForm user_form = userService.getUsernameFromId(selectedResourceId);
				performanceLogForm.setUserName(user_form.getUserName());
				String user_mail = "";
				if(user_form.getApollo_id() != null && user_form.getApollo_id().length() > 5){
					user_mail = user_form.getApollo_id();
				}
				else{
					user_mail = user_form.getMail();
				}
				performanceLogForm.setEmail(user_mail);
				request.setAttribute("forDisplay", performanceLogForm);
			}
			String meassage = null;
			if(status==true && selectedResourceId!=0){
				logger.info("comment inserted successfully");
				meassage = "Comment given to this objectives successfully.";
				Properties prop = new Properties();					 
				prop.load(new FileInputStream(request.getServletContext().getRealPath("getInformation.properties")));
				String[] roles = performanceLogForm.getRoleNames();
				List<String> role_check = new ArrayList<String>();
				for (String userInfo : roles) {
					role_check.add(userInfo);
				}
				if(userId!=selectedResourceId){
					if(!role_check.isEmpty()){
						// Sending email notification to resource.
						meassage = service.sendEmailNotification(performanceLogForm, prop, role_check);
						request.setAttribute("emailStatus", meassage);
					}
				}
				else{
					meassage = service.sendEmailNotificationAfterResourceComment(performanceLogForm, prop, role_check);
					request.setAttribute("emailStatus", meassage);
				}
				request.setAttribute("commentStatus", meassage);
			}
			else{
				logger.info("comment not inserted successfully");
				meassage = "Failed to save comment for this objective.";
				request.setAttribute("commentFail", meassage);
			}
			PerformanceLogForm userPerformance = service.getPerformance(selectedResourceId, performanceLogForm.getPeriod());
			Properties prop = new Properties();
			File admin_info_file = new File(request.getServletContext().getRealPath("getInformation.properties"));	
			if(admin_info_file.exists()){
				prop.load(new FileInputStream(admin_info_file));
			}
			int admin_id_for_filtering_access_list = prop!=null ? Integer.parseInt(prop.getProperty("userInfo_userId")) : 0 ;
			
			int selectedUserId=performanceLogForm.getUserId();
			String objective_period = performanceLogForm.getPeriod();
			List<UserForm> usersList = new ArrayList<UserForm>();
			if(objective_period != null){
				 usersList = service.getUsersListForPerformanceLogPageBasedOnResourceJoiningAndRelievingDate(userId, Calendar.getInstance(), objective_period);
			}	
			List<UserCommentForm> listObj = service.getRoleNames(userId, performanceLogForm.getUserId());
			int accountManagerId = service.accountManager(performanceLogForm, userId,  performanceLogForm.getId());
			List<UserCommentForm> updatedCommentList = new ArrayList<UserCommentForm>();
			List<UserCommentForm> commentList = service.getComments(userPerformance.getId(),userId, admin_id_for_filtering_access_list);
			for (UserCommentForm userCommentForm : commentList) {
				int userIdForCommentLoggedIn = userCommentForm.getUser_id();
				if(userIdForCommentLoggedIn == userId || admin_id_for_filtering_access_list == userId || accountManagerId == userId){
					userCommentForm.setCommentOwnership(true);
				}
				updatedCommentList.add(userCommentForm);
			}
			request.setAttribute("listObj", listObj);
			request.setAttribute("updatedUsersList", usersList);
			request.setAttribute("commentList", updatedCommentList);
			request.setAttribute("userPerformance", userPerformance);
			request.setAttribute("selectedUserIdValue", selectedUserId);
		}
		return mapping.findForward("getUserDetailsSuccess");
	}
	public ActionForward performanceLogPage(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception{
		logger.info("printing from PerformanceAction performanceLogPage Method. ");
		HttpSession session = request.getSession();
		if(session.getAttribute("userName")!=null){
			int userId =(Integer) session.getAttribute("user_id");
			try {
				service = PerformanceServiceFactory.getPerformanceService();
				PerformanceLogForm performanceLogForm = (PerformanceLogForm)form;
				String objective_period = performanceLogForm.getPeriod();				
				int selectedUserId = performanceLogForm.getUserId();
				Calendar cal = Calendar.getInstance();
				List<UserForm> usersList = new ArrayList<UserForm>();
				if(objective_period != null){
					usersList = service.getUsersListForPerformanceLogPageBasedOnResourceJoiningAndRelievingDate(userId, cal, objective_period);
				}
				PerformanceLogForm userPerformance = new PerformanceLogForm();
				UserForm user_form = new UserForm();
				user_form.setUserId(selectedUserId);
				userService = Factory.getUserService(); 
				if(selectedUserId != 0 && usersList.contains(user_form)){
					user_form = userService.getUsernameFromId(selectedUserId);
					userPerformance = service.getPerformance(performanceLogForm.getUserId(), objective_period);
					int admin_id_for_filtering_access_list = userService.getAdminUserId();
					List<UserCommentForm> listObj= service.getRoleNames(userId, performanceLogForm.getUserId());
					int accountManagerId = service.accountManager(performanceLogForm, userId,  performanceLogForm.getId());
					performanceLogForm.setUserName(user_form.getUserName());				
					if(userPerformance.getId() != 0){
						List<UserCommentForm> commentList = service.getComments(userPerformance.getId(), userId, admin_id_for_filtering_access_list);
						List<UserCommentForm> updatedCommentList = new ArrayList<UserCommentForm>();
						for (UserCommentForm userCommentForm : commentList) {
							int userIdForCommentLoggedIn = userCommentForm.getUser_id();
							if(userIdForCommentLoggedIn == userId || admin_id_for_filtering_access_list == userId || accountManagerId == userId){
								userCommentForm.setCommentOwnership(true);
							}
							updatedCommentList.add(userCommentForm);
						}
						request.setAttribute("commentList", updatedCommentList);						
					}
					request.setAttribute("forDisplay", performanceLogForm);
					request.setAttribute("listObj", listObj);
					request.setAttribute("selectedUserIdValue", selectedUserId);
				}else{
					userPerformance.setCommonObjective("Select period and user  to  get Common Objective. ");
					userPerformance.setSpecificObjective("Select period and user to get Specific Objective. ");
				}				
				request.setAttribute("updatedUsersList", usersList);		
				request.setAttribute("userPerformance", userPerformance);
			}
			catch (Exception e) {
				e.printStackTrace();
				logger.error(e);
			}
			return mapping.findForward("getUserDetailsSuccess");
		}
		else{
			throw new Exception();
		}
	}

	public ActionForward getSelectedperiodList(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception{
		logger.info("From PEPQ Performance log page getSelectedperiodList Method");
		HttpSession session = request.getSession();
		session.removeAttribute("selectedDate");
		session.removeAttribute("conList");
		session.removeAttribute("userList");
		session.removeAttribute("conListUpdate");
		if(session.getAttribute("userName")!=null){
			int userId =(Integer) session.getAttribute("user_id");
			Calendar cal = Calendar.getInstance();		
			try {
				service = PerformanceServiceFactory.getPerformanceService();
				PerformanceLogForm userPerformance = new PerformanceLogForm();
				userPerformance.setCommonObjective("Select period and user  to  get Common Objective. ");
				userPerformance.setSpecificObjective("Select period and user to get Specific Objective. ");				
				List<UserForm> usersList = service.getUsersListForPerformanceLogPageBasedOnResourceJoiningAndRelievingDate(userId, cal, TimeUtility.getCurrentQuarter(cal));
				List<String> allObjectiveQuarters = TimeUtility.getObjectiveQuarters();
				request.setAttribute("usersList", usersList);
				request.setAttribute("userPerformance", userPerformance);
				session.setAttribute("objectiveQuarters", allObjectiveQuarters);
			}
			catch (Exception e) {
				logger.error(e);
			}
			return mapping.findForward("getUserDetailsSuccess");
		}
		else{
			throw new Exception();
		}
	}

	public void deleteComment(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception{
		logger.info("from deleteComment method ");
		HttpSession session = request.getSession();
		if(session.getAttribute("user_id")!=null){
			String commentid= request.getParameter("commentId");
			String commentDeletedFromUser = request.getParameter("commentDeletedFromUser");
			String period = request.getParameter("seletedPeriod");
			String commentField = request.getParameter("commentField");
			Integer userId = Integer.parseInt(session.getAttribute("user_id").toString());
			try {
				service = PerformanceServiceFactory.getPerformanceService();
				boolean status = service.deleteUserComment(Integer.parseInt(commentid));
				String filePath = getServlet().getServletContext().getRealPath("getInformation.properties");
				File f1 = new File(filePath);
				Properties prop = null;
				if(status && f1.exists()){	
					prop = new Properties();
					prop.load(new FileInputStream(f1));
					userService = Factory.getUserService();
					UserForm user_form = userService.getUsernameFromId(userId);
					String commentDeletedBy = user_form.getUserName();
					if((commentDeletedFromUser !=null && period!="") && commentField!=null){
						service.sendEmailAlertWhileDeletingComment(commentDeletedBy, commentDeletedFromUser.trim(), period.trim(), commentField, prop);
					}
					logger.info("comment deleted ");
				}
				else{
					logger.info("comment not deleted ");
				}
			}
			catch (Exception e) {
				logger.error(e);
			}
		}
		else {
			throw new Exception();
		}
	}

	public void editComment(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception{
		HttpSession session = request.getSession();
		if(session.getAttribute("userName")!=null){
			String commentid= request.getParameter("commentId");
			String comment= request.getParameter("user_Comment");
			try {
				boolean status = service.editUserComment(Integer.parseInt(commentid),comment);
				if(status){
					logger.info("comment edited successfully ");
				}
				else{
					logger.info("comment not  edited successfully ");
				}
			}
			catch (Exception e) {
				logger.error(e);
			}
		}
		else {
			throw new Exception();
		}
	}
}