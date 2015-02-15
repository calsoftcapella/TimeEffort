package com.calsoft.user.action;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.filechooser.FileSystemView;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;
import com.calsoft.exeception.DuplicateUser;
import com.calsoft.factory.Factory;
import com.calsoft.report.form.ReportForm;
import com.calsoft.report.model.Report;
import com.calsoft.report.service.ReportService;
import com.calsoft.report.service.ReportServiceFactory;
import com.calsoft.task.form.TaskForm;
import com.calsoft.user.form.AppraisalForm;
import com.calsoft.user.form.ClientFeedbackForm;
import com.calsoft.user.form.UserForm;
import com.calsoft.user.form.UserRoleForm;
import com.calsoft.user.model.ProjectDetailModel;
import com.calsoft.user.model.User;
import com.calsoft.user.service.UserService;

@SuppressWarnings({"unchecked", "deprecation"})
public class UserAction extends DispatchAction{
	private static final Logger logger = Logger.getLogger("UserAction");
	UserService service;

	public ActionForward Login(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception{
		logger.info("You are Inside login method of user action");
		try{
			UserForm userForm=(UserForm)form;  
			String status = null;
			service = Factory.getUserService();
			HttpSession s1 = request.getSession();
			Enumeration<String> en = s1.getAttributeNames();
			while (en.hasMoreElements()){
				String sessionAttribute = (String) en.nextElement();
				s1.removeAttribute(sessionAttribute);	// Clearing session object.			
			}						
			s1.setAttribute("service", service);
			s1.setMaxInactiveInterval(2*60*60);   // Setted max-time interval			
			status = service.matchUser(userForm, request);
			int user_id = 0;
			if (s1.getAttribute("user_id") != null) {
				user_id = (Integer) s1.getAttribute("user_id");
			}
			if (status != null && status.equalsIgnoreCase("InActive")) {
				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("error.login.status"));
				saveErrors(request, errors);
				return mapping.findForward("loginFail");
			}
			else if (status != null && status.equals("1001")) {
				List<UserForm> userFormList = service.getUserNames();
				request.setAttribute("timesheetUserList", userFormList);
				return mapping.findForward("successForAdmin");
			} 
			else if(status != null && (status.equals("1003") || status.equals("1006"))){
				List<ReportForm> reportList = new ArrayList<ReportForm>();
				List<String> dayListForDates = new ArrayList<String>();
				List<String> dayList = new ArrayList<String>();
				ReportService reportservice = ReportServiceFactory.getReportService();
				Calendar cal = Calendar.getInstance();				
				DateFormat df = new SimpleDateFormat("yyyy-MM");
				DateFormat df2 = new SimpleDateFormat("M/d");	
				String month_year = df.format(cal.getTime());
				String[] st = month_year.split("-");
				String year = st[0];
				String month = st[1];
				List<UserForm> allocatedUserList = service.getAllocatedResourcesBasedOnStartAndExitDate(user_id, cal);
				String[] allocatedResources = new String[allocatedUserList.size()];
				int i = 0;
				for (UserForm userForm2 : allocatedUserList) {
					allocatedResources[i++] = Integer.toString(userForm2.getUserId());
				}	
				reportList = reportservice.getReportData(year,month, allocatedResources);					
				cal.set(Integer.parseInt(year), Integer.parseInt(month)-1, cal.getMinimum(Calendar.DAY_OF_MONTH));
				int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
				for(int k = 1; k <= days; k++){
					cal.set(Integer.parseInt(year), Integer.parseInt(month)-1, k);
					dayList.add(cal.getTime().toString().substring(0,3));
				}			
				for(int j = 1; j <= days; j++){
					cal.set(Integer.parseInt(year), Integer.parseInt(month)-1, j);
					if(!(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)){
						dayListForDates.add(df2.format(cal.getTime()));
					}
					else{
						dayListForDates.add(df2.format(cal.getTime())+" ");
					}
				} 			
				s1.setAttribute("reportListForExcel", reportList);
				request.setAttribute("reportList", reportList);
				request.setAttribute("dayList", dayList);					// Setted for JSP
				request.setAttribute("dayListForDates", dayListForDates);	// Setted for JSP
				s1.setAttribute("dayList", dayList);						// Setted for excel
				s1.setAttribute("dayListForDates", dayListForDates);		// Setted for excel 
				s1.setAttribute("selectedDate", month_year);
				s1.setAttribute("userList",allocatedUserList);
				s1.setAttribute("clientRole", "client_access");			
				return mapping.findForward("displayReport");
			}
			else if ((status != null && !status.equals("1001")) && (status != null && !status.equalsIgnoreCase("errorOnAddUser"))) {
				if(status.equals("1004") || status.equals("1005")){
					s1.setAttribute("manageUser", "manageUser");
				}
				if(status.equals("1005")){
					s1.setAttribute("manageUserAcess", "manageUserAcess");
				}
				List<TaskForm> userFormList = service.getUserTask(user_id);
				if (userFormList != null && userFormList.size() > 0) {
					String date = userFormList.get(0).getTask_date();
					int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
					DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
					String nextDate = format.format(format.parse(date).getTime()+ MILLIS_IN_DAY);
					if (nextDate.equals(format.format(new Date()))) {
						request.setAttribute("errorSheet", " " + nextDate+ " : Enter your timesheet today.");
					}
				}
				// Added for Client feedback link.
				if(status.equals("1005") || status.equals("1007")){
					request.setAttribute("viewClientFeedback", "viewClientFeedback");	
				}
				//Providing link for shared feedback
				List<ClientFeedbackForm> feedbackList = service.getAllAddedFeedback(user_id);
				List<ClientFeedbackForm> newFeedbackList = new ArrayList<ClientFeedbackForm>();
				if(feedbackList != null)
					for (ClientFeedbackForm clientFeedbackForm : feedbackList) {
						String completeFileName = clientFeedbackForm.getFile_loc().trim();
						ClientFeedbackForm c1 = new ClientFeedbackForm();
						c1.setUsername(clientFeedbackForm.getUsername());
						c1.setFeedbackId(clientFeedbackForm.getFeedbackId());
						c1.setBody_content(clientFeedbackForm.getBody_content());
						try{
							String fileName = completeFileName.substring(completeFileName.lastIndexOf(File.separatorChar)+1, completeFileName.length());
							String folderLoc = FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath();
							File f1 = new File(folderLoc+File.separatorChar+"client_feedback"+File.separator+fileName);
							if(f1.exists()){
								String filePath = getServlet().getServletContext().getRealPath("/") +"temp_feedback_loc";							 
								//create the upload folder if not exists
								File folder = new File(filePath);
								logger.info("Checking folder status "+!folder.isDirectory()+" folder detail"+folder);
								if(!folder.isDirectory()){
									folder.mkdirs();
								}
								BufferedImage image = ImageIO.read(f1);
								if(completeFileName.contains(".png") || completeFileName.contains(".PNG")){
									ImageIO.write(image, "png",new File(""+folder+File.separatorChar+fileName));
									c1.setFile_loc("temp_feedback_loc/"+fileName);
									newFeedbackList.add(c1);										
								}
								else if(completeFileName.contains(".jpg") || completeFileName.contains(".JPG")){
									ImageIO.write(image, "jpg",new File(""+folder+File.separatorChar+fileName));
									c1.setFile_loc("temp_feedback_loc/"+fileName);
									newFeedbackList.add(c1);
								}
							}
						}
						catch (Exception e) {
							logger.info("Error while displaying shared image");
						}
					}
				logger.info("Printing feedback list size "+newFeedbackList.size());
				if(!newFeedbackList.isEmpty()){
					request.setAttribute("feedbackList", newFeedbackList);
					request.setAttribute("feedbackListCount", newFeedbackList.size());
				}	

				//Get objective for User			
				List<AppraisalForm> obj =  service.getObjective(user_id);
				if(obj!=null && !obj.isEmpty()){
					request.setAttribute("userObjective", obj);
				}				
				ArrayList<String> str1 = service.getTaskMessage();
				request.setAttribute("lessTask", str1);
				request.setAttribute("userlist", userFormList);
				return mapping.findForward("successForEmp");
			}
			else if (status != null && status.equalsIgnoreCase("errorOnAddUser")){
				ActionErrors errors = new ActionErrors();
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("error.while.addingUser"));
				saveErrors(request, errors);
				return mapping.findForward("loginFail");
			}
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("error.login"));
			saveErrors(request, errors);
		}
		catch (Exception e){
			e.printStackTrace();
			throw new Exception();
		}
		return mapping.findForward("loginFail");
	}

	public  ActionForward GenerateNewPassword(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception{
		try{
			UserForm userForm=(UserForm)form;    
			String status = null;
			UserService service=Factory.getUserService();
			Properties prop = new Properties();					 
			prop.load(new FileInputStream(request.getServletContext().getRealPath("getInformation.properties")));
			status = service.getPassword(userForm, prop);
			ActionErrors errors = new ActionErrors();
			if(status == null){
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("email.error")); 
				saveErrors(request, errors);
			}		
			else if(status!=null && status.equalsIgnoreCase("Password sent.")){
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("email.send"));
				saveErrors(request, errors);
			}
			else if(status!=null && status.equalsIgnoreCase("InActive")){
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("error.login.status"));
				saveErrors(request, errors);
			}
			else if(status!=null && status.equalsIgnoreCase("Error while sending email.")){
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("error.sending.failed"));
				saveErrors(request, errors);
			}
		} 
		catch (Exception e) {
			throw new Exception();
		}
		return mapping.findForward("generatePassword");
	}

	public ActionForward userManage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {
		HttpSession s1 = request.getSession();
		if(s1.getAttribute("userName")!=null){
			logger.info("userManage information...........");
			try {
				service = Factory.getUserService();
				List<UserForm> list = service.getUserNames();				
				List<UserRoleForm> allRoles = service.getAllRole();		
				List<ProjectDetailModel> project_id_list = service.getAllProjectInfo();
				request.setAttribute("userlist", list);	
				s1.setAttribute("allRoleName", allRoles);	
				s1.setAttribute("project_detail_info", project_id_list);	
				UserForm userForm=(UserForm)form;
				userForm.setUserId(0);
				userForm.setUserName("");
				userForm.setMail("");
				userForm.setStatus("");
			} catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
				throw new Exception();
			}
			return mapping.findForward("userManageSuccess");
		}
		else {
			throw new Exception();
		}
	}

	public ActionForward addUser(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception{
		HttpSession s1 = request.getSession();
		logger.info("Printing from addUser method from UserAction.");
		if(s1.getAttribute("userName")!=null){
			String message = null;
			UserForm userForm = (UserForm)form;
			boolean flag = false;
			try {
				service = Factory.getUserService();
				Properties props = new Properties();	
				File f1 = new File(request.getServletContext().getRealPath("getInformation.properties"));
				props.load(new FileInputStream(f1));
				message = service.addUser(userForm, props);
				flag = true;
				if (message.equals("User added successfully")||message.equals("User activated successfully")) {
					String resource_role_id = userForm.getUser_role();
					request.setAttribute("hidden_role_id", resource_role_id);
					request.setAttribute("passwordMessage", message);
				}
				else{
					message="User details updated successfully";
					request.setAttribute("passwordMessage", message);
					if(userForm.getUserId() != 0){
						request.setAttribute("hidden_role_id", service.getUserRoleId(userForm.getUserId()));
						request.setAttribute("hidden_project_id", userForm.getProject_id());
					}
				}
				List<UserForm> list = service.getUserNames();
				request.setAttribute("userlist", list);	
			} 
			catch (DuplicateUser e){
				throw new DuplicateUser();
			}
			catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
				throw new Exception();
			}
			if (flag)
				return mapping.findForward("success");
			else
				return mapping.findForward("fail");
		}
		else
			throw new Exception();
	}

	public ActionForward signOut(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		session.removeAttribute("selectedDate");
		String signOutMessage = "You Are Successfully Logged Out";
		request.setAttribute("signoutMessage", signOutMessage);
		session.invalidate();
		return mapping.findForward("signout");
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		HttpSession session = request.getSession();
		if(session.getAttribute("userName")!=null)
		{
			logger.info("delete user  information...........");
			int uid =0;
			UserForm userForm = (UserForm) form;
			try {
				uid = Integer.parseInt(request.getParameter("id"));

				userForm.setUserId(uid);
				service = Factory.getUserService();
				if (service.delete(userForm)) {
					service = Factory.getUserService();
					List<UserForm> list = service.getUserNames();
					request.setAttribute("userlist", list);
					return mapping.findForward("deleted");
				}
			} catch (Exception e) {
				logger.error(e);
				throw new Exception();
			}
			return mapping.findForward(null);
		}
		else
			throw new Exception();
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		if(session.getAttribute("userName")!=null){
			logger.info("Printing from edit method UserAction");
			int uid = 0;
			UserForm userForm = (UserForm) form;
			try {
				uid = Integer.parseInt(request.getParameter("id"));
				service = Factory.getUserService();
				User user = service.getUserDetail(uid);
				String user_role_id = service.getUserRoleId(uid);
				userForm.setUserName(user.getUser_name());
				userForm.setMail(user.getMail());
				userForm.setStatus(user.getStatus());
				userForm.setUserId(user.getUser_Id());
				userForm.setPassWord(user.getPassWord());
				userForm.setProject_id(user.getProject_id());
				request.setAttribute("hidden_role_id", user_role_id);
				request.setAttribute("hidden_project_id", userForm.getProject_id());
				List<UserForm> list = service.getUserNames();
				request.setAttribute("userlist", list);
				request.setAttribute("userform", userForm);
			} catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
				throw new Exception();
			}
			return mapping.findForward("editable");
		}
		else
			throw new Exception();
	}

	public synchronized ActionForward getHomeContent(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {
		logger.info("You are Inside getHomeContent method of user action");
		int user_id = 0;
		HttpSession s1 = request.getSession();
		try{
			if (s1.getAttribute("user_id") != null){
				user_id = (Integer) s1.getAttribute("user_id");
				s1.removeAttribute("selectedDate");
				s1.removeAttribute("conList");
				s1.removeAttribute("userList");
				s1.removeAttribute("conListUpdate");
			}
			service = Factory.getUserService();
			if(service!=null && s1.getAttribute("userName")!=null){
				List<TaskForm> userFormList = service.getUserTask(user_id);
				if (userFormList != null && userFormList.size() > 0){
					String date = userFormList.get(0).getTask_date();
					int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
					DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
					String nextDate = format.format(format.parse(date).getTime()+ MILLIS_IN_DAY);
					if (nextDate.equals(format.format(new Date()))) {
						request.setAttribute("errorSheet", " " + nextDate+ " : Enter your timesheet today.");
					}
				}
				//Get objective for User			
				List<AppraisalForm> obj =  service.getObjective(user_id);
				if(obj!=null && !obj.isEmpty()){
					request.setAttribute("userObjective", obj);
				}
				// Added for Client feedback link.
				String resource_role_id = service.getUserRoleId(user_id);
				if(resource_role_id.equals("1005")  || resource_role_id.equals("1007")){
					request.setAttribute("viewClientFeedback", "viewClientFeedback");
				}	
				//Providing link for shared feedback
				List<ClientFeedbackForm> feedbackList = service.getAllAddedFeedback(user_id);
				List<ClientFeedbackForm> newFeedbackList = new ArrayList<ClientFeedbackForm>();
				if(feedbackList != null)
					for (ClientFeedbackForm clientFeedbackForm : feedbackList) {
						String completeFileName = clientFeedbackForm.getFile_loc().trim();
						ClientFeedbackForm c1 = new ClientFeedbackForm();
						c1.setUsername(clientFeedbackForm.getUsername());
						c1.setFeedbackId(clientFeedbackForm.getFeedbackId());
						c1.setBody_content(clientFeedbackForm.getBody_content());
						try{
							String fileName = completeFileName.substring(completeFileName.lastIndexOf(File.separatorChar)+1, completeFileName.length());
							String folderLoc = FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath();
							File f1 = new File(folderLoc+File.separatorChar+"client_feedback"+File.separator+fileName);
							if(f1.exists()){
								String filePath = getServlet().getServletContext().getRealPath("/") +"temp_feedback_loc";							 
								//create the upload folder if not exists
								File folder = new File(filePath);
								logger.info("Checking folder status "+!folder.isDirectory()+" folder detail"+folder);
								if(!folder.isDirectory()){
									folder.mkdirs();
								}
								BufferedImage image = ImageIO.read(f1);
								if(completeFileName.contains(".png") || completeFileName.contains(".PNG")){
									ImageIO.write(image, "png",new File(""+folder+File.separatorChar+fileName));
									c1.setFile_loc("temp_feedback_loc/"+fileName);
									newFeedbackList.add(c1);										
								}
								else if(completeFileName.contains(".jpg") || completeFileName.contains(".JPG")){
									ImageIO.write(image, "jpg",new File(""+folder+File.separatorChar+fileName));
									c1.setFile_loc("temp_feedback_loc/"+fileName);
									newFeedbackList.add(c1);
								}
							}
						}
						catch (Exception e) {
							logger.info("Error while displaying shared image");
						}
					}
				logger.info("Printing feedback list size "+newFeedbackList.size());
				if(!newFeedbackList.isEmpty()){
					request.setAttribute("feedbackList", newFeedbackList);
					request.setAttribute("feedbackListCount", newFeedbackList.size());
				}
				List<String> str1 = service.getTaskMessage();
				request.setAttribute("lessTask", str1);
				request.setAttribute("userlist", userFormList);
			} 
			else{
				throw new Exception();
			}
		}
		catch (Exception e){
			logger.error(e.getMessage());
			throw new Exception();
		}
		return mapping.findForward("successForEmp");
	}
	public ActionForward goChangePassword(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession s1=request.getSession();
		if(s1.getAttribute("userName")!=null) {
			s1.removeAttribute("selectedDate");
			s1.removeAttribute("conList");
			s1.removeAttribute("userList");
			s1.removeAttribute("conListUpdate");
			String welcome=(String)s1.getAttribute("userName");
			String replace=welcome.replaceFirst("Welcome, ", "");
			request.setAttribute("profilename",replace);
			return mapping.findForward("gochange");
		}
		else
			throw new Exception();
	}
	public ActionForward changePassword(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session=request.getSession();
		if(session.getAttribute("userName")!=null){
			String welcome=(String)session.getAttribute("userName");
			String replace=welcome.replaceFirst("Welcome, ", "");
			request.setAttribute("profilename",replace);
			return mapping.findForward("change");
		}
		else
			throw new Exception();
	}
	public ActionForward passwordChanged(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session1=request.getSession();
		if(session1.getAttribute("userName")!=null){			
			String welcome=(String)session1.getAttribute("userName");
			String replace=welcome.replaceFirst("Welcome, ", "");
			request.setAttribute("profilename",replace);
			UserForm userForm = (UserForm) form;
			boolean b=false;
			int userId=0;
			try {
				service = Factory.getUserService();
				userId=Integer.parseInt(session1.getAttribute("user_id").toString());
				userForm.setUserId(userId);
				b=service.changePassword(userForm);

			} catch (Exception e) {
				throw new Exception();
			}
			if(b){
				String msg="Your password changed successfully";
				request.setAttribute("passwordmsg",msg);
				return mapping.findForward("passwordchanged");
			}
			else{
				String msg="Password change failed";
				request.setAttribute("passwordmsg1",msg);
				return mapping.findForward("changfail");
			}
		}
		else
			throw new Exception();
	}
	public void validateResourceExistingPassword(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("Printing from validateResourceExistingPassword Method ");
		HttpSession s1 = request.getSession();			
		Integer resourceId = (Integer) s1.getAttribute("user_id");
		String resourcePass = request.getParameter("currentPassword");
		UserService userService = Factory.getUserService();
		String validationStatus = userService.validateResourcePassword(resourceId, resourcePass);
		response.getWriter().print(validationStatus);
	}

	public ActionForward getUserAccessMapping(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession s1=request.getSession();
		if(s1.getAttribute("userName")!=null) {
			try {
				String welcome=(String)s1.getAttribute("userName");
				String replace=welcome.replaceFirst("Welcome, ", "");
				request.setAttribute("profilename",replace);
				int userId=0;
				userId=Integer.parseInt(s1.getAttribute("user_id").toString());
				service = Factory.getUserService();
				List<List<Report>> combinedList =service.getUserReportAllocation(userId);
				Iterator<List<Report>> itr = combinedList.iterator();
				int count = 1;
				List<Report> unallocatedList = null;
				List<Report> allocatedList = null;
				while (itr.hasNext()) {
					if (count == 1) {
						allocatedList = itr.next();
					}
					if (count == 2) {
						unallocatedList = itr.next();
					}
					count++;
				}
				UserService userService;
				userService = Factory.getUserService();
				UserForm userForm = userService.getUsernameFromId(userId);
				String userName = userForm.getUserName();
				UserForm userformNew = new UserForm();
				userformNew.setUserId(userId);
				userformNew.setUserName(userName);
				List<UserForm> userFormList = userService.getUserNames();
				Iterator<UserForm> itr1 = userFormList.iterator();
				List<UserForm> userFormUpdatedList = new ArrayList<UserForm>();
				userFormUpdatedList.add(userformNew);
				while (itr1.hasNext()) {
					UserForm userFormValue = itr1.next();
					if (!(userFormValue.getUserName().equals(userName))) {
						userFormUpdatedList.add(userFormValue);
					}
				}
				// get the list of users with the list of resources
				Iterator<Report> allocatedIterator = allocatedList.iterator();
				int counter = 1;
				List<Report> updatedAllocatedList = new ArrayList<Report>();
				while (allocatedIterator.hasNext()) {
					Report report = allocatedIterator.next();
					if (counter == 1) {
						report.setParentUserName(userName);
						//System.out.println("parent user name is:"+ report.getParentUserName());
					}
					updatedAllocatedList.add(report);
					counter++;
				}
				request.setAttribute("list", userFormUpdatedList);
				request.setAttribute("combinedList", combinedList);
				request.setAttribute("allocatedList", allocatedList);
				request.setAttribute("updatedallocatedList", updatedAllocatedList);
				request.setAttribute("unallocatedList", unallocatedList);
			} 
			catch (Exception e) {
				e.printStackTrace();
				throw new Exception();
			}
			return mapping.findForward("getUserAccessMapping");
		}
		else
			throw new Exception();
	}


	public ActionForward editGlobal(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception{
		HttpSession s1=request.getSession();
		if(s1.getAttribute("userName")!=null) {
			logger.info("GlobalEdit user  information...........");
			int uid =0;
			UserForm userForm = (UserForm) form;
			try {
				uid = Integer.parseInt(request.getParameter("id"));
				userForm.setUserId(uid);
				Properties prop = new Properties();
				File f1 = new File(request.getServletContext().getRealPath("getInformation.properties"));
				prop.load(new FileInputStream(f1));
				service = Factory.getUserService();
				if (service!=null && service.deleteGlobal(userForm, prop)){
					List<UserForm> list = service.getUserNames();
					request.setAttribute("userlist", list);
					return mapping.findForward("deleted");
				}
			} catch (Exception e) {
				logger.error(e);
				throw new Exception();
			}
			return mapping.findForward(null);
		}
		else
			throw new Exception();
	}

	public ActionForward getAdminHome(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception{
		HttpSession s1=request.getSession();
		if(s1.getAttribute("userName")!=null) {
			UserService userService;
			userService = Factory.getUserService();
			List<UserForm> userFormList = userService.getUserNames();
			request.setAttribute("timesheetUserList", userFormList);
			return mapping.findForward("successForAdmin");
		}
		else
			throw new Exception();
	}

	public ActionForward viewClientFeedback(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception{
		HttpSession s1=request.getSession();
		if(s1.getAttribute("userName")!=null){		
			UserService userService = Factory.getUserService();
			Calendar cal = Calendar.getInstance();
			try{
				int admin_id = userService.getAdminUserId();
				List<UserForm> listForm = userService.getAllocatedResourcesBasedOnStartAndExitDate(admin_id, cal);
				String mailList = "";
				for (UserForm userForm : listForm) {
					if(mailList == ""){
						mailList = mailList+userForm.getMail();
					}
					else{
						mailList = mailList+", "+userForm.getMail();
					}								
				}
				request.setAttribute("usermailingList", mailList);					
			}
			catch(Exception e){
				e.printStackTrace();
			}	
			return mapping.findForward("viewClientFeedbackPage");
		}
		else
			throw new Exception();
	}

	public ActionForward getOpenPosition(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception{
		return mapping.findForward("getOpenPosition");
	}

	public ActionForward shareClientFeedback(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception{
		// Method for sharing comment to all given by client.
		logger.info("Printing from UserAction shareClientFeedback method.");
		HttpSession s1=request.getSession();
		if(s1.getAttribute("user_id")!=null){	
			int userId = Integer.parseInt(s1.getAttribute("user_id").toString());
			ClientFeedbackForm clickFeedbackForm = (ClientFeedbackForm) form;
			String toList = clickFeedbackForm.getTo_list();
			String[] toListArray = null;
			if(toList != null && toList.length() > 0){
				toListArray = toList.split(", ");
			}
			FormFile file = clickFeedbackForm.getUploded_file();
			Properties prop = new Properties();	
			File admin_info_file = new File(getServlet().getServletContext().getRealPath("/")+"getInformation.properties");	
			logger.info("Printing real path "+admin_info_file);
			if(admin_info_file.exists()){
				prop.load(new FileInputStream(admin_info_file));
			}
			UserService userService = Factory.getUserService();
			Calendar cal = Calendar.getInstance();							
			String filePath = FileSystemView.getFileSystemView().getHomeDirectory()+File.separator+"client_feedback";
			String homeLoc = FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath();

			//////////////////	Checking values here	////////////////////
			FormFile fileAttached = (FormFile) PropertyUtils.getSimpleProperty(form, "uploded_file");			
			logger.info("Printing file detail PropertyUtils "+fileAttached+" from request "+request.getContentType()+" encoding "+request.getCharacterEncoding() );
			ClientFeedbackForm cf1 = (ClientFeedbackForm) request.getAttribute("clientFeedbackForm");			
			logger.info("Getting image file from request object "+cf1.getUploded_file());		
			logger.info("Printing uploaded file existance "+file+" filePath:: "+filePath+" homeLoc:: "+homeLoc);
			//////////////////	Checking values here	////////////////////

			File folder = new File(filePath);	
			service = Factory.getUserService();
			if(!folder.isDirectory()){
				folder.mkdir();
			}	    	    
			try {
				UserForm userFormForUsername = userService.getUsernameFromId(userId);
				if(file == null){
					request.setAttribute("message_for_uploading", "Error while uploading file try again later.");
				}
				else if(file != null && file.getFileName() != ""){
					File newFile = new File(filePath, file.getFileName());	
					if(!newFile.exists()){
						FileOutputStream fos = new FileOutputStream(newFile);
						fos.write(file.getFileData());  // Saving uploaded file to local.
						fos.flush();
						fos.close();
						String messageStatus = "Failed to send mail please try again later.";
						if(!prop.isEmpty() && toListArray != null){
							messageStatus = service.setNotificationForSharedFeedback(toListArray, newFile, userFormForUsername, clickFeedbackForm, homeLoc, prop);
						}						
						request.setAttribute("message_for_uploading", messageStatus);
					} 
					else if(newFile.exists()){
						File checkFile = new File(filePath, file.getFileName());
						String completeFileName = file.getFileName();
						String fileNameWithoutExtension = completeFileName.substring(0, completeFileName.lastIndexOf('.'));
						String fileExtension = completeFileName.substring(completeFileName.lastIndexOf('.'), completeFileName.length());						
						int count = 1;
						while(checkFile.exists()){
							checkFile = new File(filePath, fileNameWithoutExtension+" ("+count+++")"+fileExtension);
						}
						FileOutputStream fos = new FileOutputStream(checkFile);
						fos.write(file.getFileData());  // Saving uploaded file to local.
						fos.flush();
						fos.close();
						String messageStatus = "Failed to send mail please try again later.";
						if(!prop.isEmpty() && toListArray != null){
							messageStatus = service.setNotificationForSharedFeedback(toListArray, checkFile, userFormForUsername, clickFeedbackForm, homeLoc, prop);
						}						
						request.setAttribute("message_for_uploading", messageStatus);
					}
				}
				// Displaying emailIds in TO field for sharing feedback.
				int admin_id = userService.getAdminUserId();
				List<UserForm> listForm = userService.getAllocatedResourcesBasedOnStartAndExitDate(admin_id, cal);	
				String mailList = "";
				for (UserForm userForm : listForm) {
					if(mailList == ""){
						mailList = mailList+userForm.getMail();
					}
					else{
						mailList = mailList+", "+userForm.getMail();
					}								
				}
				request.setAttribute("usermailingList", mailList);
			} catch (Exception ex) {
				ex.printStackTrace();
			}		
			return mapping.findForward("viewClientFeedbackPage");
		}
		else
			throw new Exception();
	}
	public void setViewStatus(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception{
		logger.info("Printing from user action setViewStatus method");
		HttpSession s1=request.getSession();
		if(s1.getAttribute("user_id")!=null){
			String jsonString = request.getParameter("feedbackIdsInJson");
			List<Integer> idList = new ArrayList<Integer>();
			service = Factory.getUserService();
			if(jsonString!=null){
				JSONArray object = JSONArray.fromObject(jsonString);
				for(int i = 0; i < object.size(); i++){
					JSONObject jObj = object.getJSONObject(i);	        	 						
					try{
						idList.add(Integer.parseInt(jObj.get("feedbackId").toString()));
					}catch (Exception e) {
						logger.info("Printing message while parsing feedbackId for setViewStatus::: "+e.getMessage());
					}
				}
			}
			int user_id = Integer.parseInt(s1.getAttribute("user_id").toString());
			service.setViewStatus(idList, user_id);
		}
		else
			response.getWriter().print("Session Expired relogin to view shared comment.");
	}

	public ActionForward getSelectedResourceDetailForUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception{
		logger.info("Printing from user action getSelectedResourceDetailForUpdate method");
		HttpSession s1=request.getSession();
		if(s1.getAttribute("user_id")!=null && request.getParameter("selectedUserId") != null){	
			int user_id = Integer.parseInt(request.getParameter("selectedUserId"));			
			service = Factory.getUserService();
			UserForm userInfoDetails = service.getUserDetailForUserId(user_id);
			List<ProjectDetailModel> project_id_list = service.getAllProjectInfo();
			userInfoDetails.setProject_id_list(project_id_list);
			request.setAttribute("updateUserDetails", userInfoDetails);	
			List<UserForm> userFormList = service.getUserNames();	
			request.setAttribute("timesheetUserList", userFormList);
			return mapping.findForward("successForAdmin");
		}
		else
			throw new Exception();
	}
	public ActionForward saveResourceInformationDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception{
		logger.info("Printing from user action saveResourceInformationDetail method");
		HttpSession s1=request.getSession();
		if(s1.getAttribute("user_id")!=null ){
			UserForm user_form = (UserForm) form;		
			service = Factory.getUserService();	
			String status = "Error while updating resource detail.";
			logger.info("Printing from saveResourceInformationDetail "+user_form.getProject_id());
			try{
				status = service.saveResourceInProfile(user_form);	
				request.setAttribute("UpdateStatus", status);			
				UserForm userInfoDetails = service.getUserDetailForUserId(user_form.getUserId());
				List<ProjectDetailModel> project_id_list = service.getAllProjectInfo();
				userInfoDetails.setProject_id_list(project_id_list);
				request.setAttribute("userDetailsAfterSaving", userInfoDetails);
				List<UserForm> userFormList = service.getUserNames();	
				request.setAttribute("timesheetUserList", userFormList);
			}
			catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("UpdateStatus", status);
			}
			return mapping.findForward("successForAdmin");
		}
		else
			throw new Exception();
	}
	/*public void getCurrentStatusForSharedComment(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception{
		logger.info("Printing from user action getCurrentStatusForSharedComment method");
		HttpSession s1=request.getSession();
		if(s1.getAttribute("user_id")!=null){
			//Providing link for shared feedback
			service = Factory.getUserService();
			Integer user_id = Integer.parseInt(s1.getAttribute("user_id").toString());
			List<ClientFeedbackForm> feedbackList = service.getAllAddedFeedback(user_id);
			List<ClientFeedbackForm> newFeedbackList = new ArrayList<ClientFeedbackForm>();
			if(feedbackList != null)
				for (ClientFeedbackForm clientFeedbackForm : feedbackList) {
					String completeFileName = clientFeedbackForm.getBody_content().trim();
					ClientFeedbackForm c1 = new ClientFeedbackForm();
					c1.setUsername(clientFeedbackForm.getUsername());
					c1.setFeedbackId(clientFeedbackForm.getFeedbackId());
					try{
						if(completeFileName.contains(".png") || completeFileName.contains(".jpg")){
							String fileName = completeFileName.substring(completeFileName.lastIndexOf(File.separatorChar)+1, completeFileName.length());
							String folderLoc = FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath();
							File f1 = new File(folderLoc+File.separatorChar+"client_feedback"+File.separator+fileName);
							if(f1.exists()){
								String filePath = getServlet().getServletContext().getRealPath("/") +"temp_feedback_loc";							 
								//create the upload folder if not exists
								File folder = new File(filePath);
								if(!folder.isDirectory()){
									folder.mkdirs();
								}
								BufferedImage image = ImageIO.read(f1);
								if(completeFileName.contains(".png")){
									ImageIO.write(image, "png",new File(""+folder+File.separatorChar+fileName));
									c1.setBody_content("temp_feedback_loc/"+fileName);
									newFeedbackList.add(c1);										
								}
								else if(completeFileName.contains(".jpg")){
									ImageIO.write(image, "jpg",new File(""+folder+File.separatorChar+fileName));
									c1.setBody_content("temp_feedback_loc/"+fileName);
									newFeedbackList.add(c1);
								}
							}
						}
						else{
							c1.setBody_content(completeFileName);
							newFeedbackList.add(c1);
						}
					}
					catch (Exception e) {
						logger.info("Error while displaying shared image");
					}
				}
			logger.info("Printing feedback list size "+newFeedbackList.size());
			if(!newFeedbackList.isEmpty()){
				request.setAttribute("feedbackList", newFeedbackList);
				request.setAttribute("feedbackListCount", newFeedbackList.size());
			}	
			 response.getWriter().print(newFeedbackList.size());
		}
		else
			 response.getWriter().print("Session Expired relogin to view shared comment.");
	}*/
	// Cron Job Scheduler
	/*public void execute(JobExecutionContext context)throws JobExecutionException
	{
		service = Factory.getUserService();
		try
		{
			List<UserForm> allUser = service.getUserNamesMappedUnderAccountManager();
			service.sendEmailToAll(allUser);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}	
	}*/

}