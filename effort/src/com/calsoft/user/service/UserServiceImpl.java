package com.calsoft.user.service;

import java.io.File;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.calsoft.exeception.DuplicateUser;
import com.calsoft.factory.Factory;
import com.calsoft.report.form.ReportForm;
import com.calsoft.report.model.Report;
import com.calsoft.task.form.TaskForm;
import com.calsoft.user.dao.UserDao;
import com.calsoft.user.form.AppraisalForm;
import com.calsoft.user.form.ClientFeedbackForm;
import com.calsoft.user.form.UserForm;
import com.calsoft.user.form.UserRoleForm;
import com.calsoft.user.model.Appraisal;
import com.calsoft.user.model.ClientFeedback;
import com.calsoft.user.model.Contact;
import com.calsoft.user.model.ModelContact;
import com.calsoft.user.model.ProjectDetail;
import com.calsoft.user.model.ProjectDetailModel;
import com.calsoft.user.model.Role;
import com.calsoft.user.model.User;
import com.calsoft.user.model.UserModel;
import com.calsoft.user.model.UserTask;
import com.calsoft.util.EmailUtil;
import com.calsoft.util.HbnUtil;
import com.calsoft.util.PasswordGenerator;

@SuppressWarnings("unchecked")
public class UserServiceImpl implements UserService {
	private static final Logger logger = Logger.getLogger("name");
	String passWord;
	List<User> prevlist;
	ArrayList<String> taskString = new ArrayList<String>();
	public List<UserForm> getUserNames() throws Exception {
		UserDao dao;
		List<UserForm> userFormList = new ArrayList<UserForm>();
		dao = Factory.getDao();
		List<User> userList = dao.getUserName();
		Iterator<User> itr = userList.iterator();
		while (itr.hasNext()) {
			User user = itr.next();
			UserForm userform = getUserForm(user);
			userFormList.add(userform);
		}
		return userFormList;
	}

	public String addUser(UserForm form, Properties props) throws Exception{
		Session session = HbnUtil.getSession();
		Transaction transaction = session.beginTransaction();
		String default_pass = props.getProperty("default_password") != null ? props.getProperty("default_password") :"";
		UserDao dao;
		String status = "password not sent";
		User user = getUser(form);
		int roleId = 0;
		if(form.getUser_role() != null && form.getUser_role() !=""){
			roleId = Integer.parseInt(form.getUser_role());		
		}
		if (user.getUser_Id() == 0) {
			passWord = PasswordGenerator.generatePassword();
			user.setPassWord(passWord);
			user.setDefpass(default_pass);
		}
		dao = Factory.getDao();
		boolean c = dao.addUser(user, session, roleId);
		if (c == true && (form.getUserId() == 0 || user.getStatus().equals("Active"))) {
			if (form.getUserId() == 0)
				status = "User added successfully";
		} 	
		else if(!c){
			throw new DuplicateUser();
		}
		transaction.commit();
		session.close();
		return status;
	}

	public User getUser(UserForm form) {
		User user = new User();
		user.setUser_Id(form.getUserId());
		user.setUser_name(form.getUserName());
		user.setPassWord(form.getPassWord());
		user.setMail(form.getMail());
		user.setStatus(form.getStatus());
		user.setApollo_id(form.getApollo_id());
		user.setProject_id(form.getProject_id());
		if(form.getDefpass()==null){
			user.setDefpass("any");
		}
		user.setDefpass(form.getDefpass()); //Global
		String feeze_date  = form.getFreeze_timesheet_entry();
		if(feeze_date != null && feeze_date != ""){
			try {
				user.setFreeze_timesheet(new SimpleDateFormat("dd/MM/yyyy").parse(feeze_date));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}	
		String exit_date = form.getExit_date();
		if(exit_date != null && exit_date != ""){
			try {
				user.setExit_date(new SimpleDateFormat("MM/dd/yyyy").parse(exit_date));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}	
		String start_date  = form.getStart_dateString();
		if(start_date != null && start_date != ""){
			try {
				user.setStart_date(new SimpleDateFormat("MM/dd/yyyy").parse(start_date));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}	
		return user;
	}

	public UserForm getUserForm(User user) {
		UserForm form = new UserForm();
		form.setUserId(user.getUser_Id());
		form.setUserName(user.getUser_name());
		form.setMail(user.getMail());
		form.setStatus(user.getStatus());
		form.setDefpass(user.getDefpass());
		form.setFreeze_timesheet(user.getFreeze_timesheet());
		if(user.getStart_date() != null){
			form.setStart_date(user.getStart_date());
		}
		if(user.getExit_date() != null){
			form.setExit_date(new SimpleDateFormat("yyyy-MM-dd").format(user.getExit_date()));
		}
		form.setApollo_id(user.getApollo_id());
		return form;
	}

	@Override
	public UserForm getUsernameFromId(int userId) {
		UserDao dao;
		UserForm userForm = null;
		try {
			dao = Factory.getDao();
			User user = dao.getUsernameFromId(userId);
			userForm = getUserForm(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userForm;
	}

	@Override
	public String matchUser(UserForm userForm, HttpServletRequest request)throws Exception {
		UserDao dao;
		String status = null;
		User user = getUser(userForm);
		dao = Factory.getDao();
		status = dao.matchUser(user, request);
		return status;
	}

	@Override
	public String getPassword(UserForm userForm, Properties prop) throws Exception {
		String status = null;
		User user = getUser(userForm);
		String passWord = PasswordGenerator.generatePassword();
		user.setPassWord(passWord);
		UserDao dao = Factory.getDao();
		status = dao.storePassword(user);	
		String host = prop.getProperty("host_name").trim();		
		String port = prop.getProperty("port_number").trim();
		String userIds = prop.getProperty("adminEmailUsername").trim();
		String password = prop.getProperty("adminEmailPassword").trim();			
		String from = userIds;
		String bcc = from;
		String to = user.getMail();
		String subject = "Check Your Password";		
		String mail_conetent  = "Hi," + "<br>" + "<br>"
				+ "  &nbsp;&nbsp;&nbsp;Your username is <b><u>"+user.getMail()+"</u></b> and password is  <b><u>"+user.getPassWord()+"</u></b> for yours Timesheet Account." 
				+ "<br>" + "<br>"
				+ "Thanks and Regards" + "<br>" + "Raghavi";			
		if (status != null && status.equalsIgnoreCase("Active")) {
			status = EmailUtil.sendEmail(host, port, userIds, password, from, to, subject, mail_conetent, bcc);
		}
		return status;
	}

	@Override
	public boolean delete(UserForm form) throws Exception {
		UserDao dao = Factory.getDao();
		User user = getUser(form);
		dao.deActivateUser(user);
		prevlist = Factory.getDao().getUserName();
		return true;
	}

	public boolean deleteGlobal(UserForm form, Properties props) throws Exception {
		UserDao dao = Factory.getDao();
		User user = getUser(form);
		boolean global = dao.globalUser(user, props);
		prevlist = Factory.getDao().getUserName();
		return global;
	}

	@Override
	public User getUserDetail(int id) throws Exception {
		UserDao dao = Factory.getDao();
		User user = dao.getUser(id);
		return user;
	}

	@Override
	public List<TaskForm> getUserTask(int user_id) throws Exception{
		UserDao dao;
		List<TaskForm> taskList = new ArrayList<TaskForm>();
		dao = Factory.getDao();
		List<UserTask> task = dao.getUserTask(user_id);
		taskString = dao.getTaskMessageList();
		Iterator<UserTask> itr = task.iterator();
		while (itr.hasNext()){
			UserTask userTask = itr.next();
			TaskForm taskform = getTaskForm(userTask);
			taskList.add(taskform);
		}
		return taskList;
	}

	@Override
	public List<UserForm> getAllocatedResources(int userId) {
		List<User> allocatedResourceList = new ArrayList<User>();
		List<UserForm> allocatedResourceFormList = new ArrayList<UserForm>();
		try {
			UserDao userDao = Factory.getDao();
			allocatedResourceList = userDao.getAllocatedResources(userId);
			Iterator<User> itr = allocatedResourceList.iterator();
			while (itr.hasNext()) {
				UserForm userform = getUserForm(itr.next());
				allocatedResourceFormList.add(userform);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return allocatedResourceFormList;
	}

	@Override
	public List<UserForm> getAllocatedResourcesDetailsBasedOnRelievingDate(int userId, Calendar cal) {
		List<User> allocatedResourceList = new ArrayList<User>();
		List<UserForm> allocatedResourceFormList = new ArrayList<UserForm>();
		try {
			UserDao userDao = Factory.getDao();
			allocatedResourceList = userDao.getAllocatedResourcesDetailsBasedOnRelievingDate(userId, cal);
			Iterator<User> itr = allocatedResourceList.iterator();
			while (itr.hasNext()) {
				UserForm userform = getUserForm(itr.next());
				allocatedResourceFormList.add(userform);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return allocatedResourceFormList;
	}
	
	@Override
	public List<UserForm> getAllocatedResourcesDetailsForRemindarMailBasedOnRelievingDate(int userId, Calendar cal) {
		List<User> allocatedResourceList = new ArrayList<User>();
		List<UserForm> allocatedResourceFormList = new ArrayList<UserForm>();
		try {
			UserDao userDao = Factory.getDao();
			allocatedResourceList = userDao.getAllocatedResourcesDetailsForRemindarMailBasedOnRelievingDate(userId, cal);
			Iterator<User> itr = allocatedResourceList.iterator();
			while (itr.hasNext()) {
				UserForm userform = getUserForm(itr.next());
				allocatedResourceFormList.add(userform);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return allocatedResourceFormList;
	}
	
	@Override
	public List<UserForm> getAllocatedResourcesDetailsForMonthlyReminder(int userId, Calendar cal) {
		List<UserForm> allocatedResourceList = new ArrayList<UserForm>();;
		List<UserModel> allocatedResourceFormList = new ArrayList<UserModel>();
		try {
			UserDao userDao = Factory.getDao();
			allocatedResourceFormList = userDao.getAllocatedResourcesDetailsForMonthlyReminder(userId, cal);
			Iterator<UserModel> itr = allocatedResourceFormList.iterator();
			while (itr.hasNext()) {
				UserModel user_model = itr.next();
				UserForm uf = getUserFormFromUserModelObject(user_model);
				allocatedResourceList.add(uf);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return allocatedResourceList;
	}
	private UserForm getUserFormFromUserModelObject(UserModel user_model) {
		UserForm uf = new UserForm();
		uf.setUserId(user_model.getUser_id());
		uf.setUserName(user_model.getUser_name());
		uf.setMail(user_model.getCalsoft_id());
		uf.setApollo_id(user_model.getApollo_id());
		uf.setUser_role_id(user_model.getRole_id());
		uf.setStart_date(user_model.getJoining_date());
		return uf;
	}

	private TaskForm getTaskForm(UserTask userTask) {
		TaskForm form = new TaskForm();
		form.setTask_date(userTask.getTask_date().toString());
		form.setTime(userTask.getTime());
		form.setStatus(userTask.getStatus());
		return form;
	}

	public ArrayList<String> getTaskMessage() throws Exception {
		return this.taskString;
	}
	@Override
	public boolean changePassword(UserForm form) throws Exception {
		UserDao userDao = Factory.getDao();
		boolean b = false;
		try {
			String newPassword = form.getNewPassword();
			User user = getUser(form);
			b = userDao.changePassword(user, newPassword);
		} catch (Exception e) {
			throw new Exception();
		}
		return b;
	}

	@Override
	public List<List<Report>> getUserReportAllocation(int userId) throws Exception {
		List<List<Report>> combinedReportList = new ArrayList<List<Report>>();
		UserDao userDao = Factory.getDao();
		combinedReportList = userDao.getUserReportAllocation(userId);
		return combinedReportList;
	}

	// Added for Contact detail Info
	@Override
	public List<Object> getContactDetails(List<UserForm> allocatedUserList) throws Exception
	{
		List<Object> list = null;
		List<User> userList = new ArrayList<User>();
		for (UserForm userForm : allocatedUserList) {
			User u1 = getUser(userForm);
			userList.add(u1);		
		}
		UserDao userDao = Factory.getDao();	
		list = userDao.getContactDetails(userList);			
		return list;
	}

	@Override
	public List<UserRoleForm> getAllRole() throws Exception {
		List<UserRoleForm> listRoleForm = new ArrayList<UserRoleForm>();
		UserDao userDao = Factory.getDao();		
		List<Role> allRole = userDao.getAllRoles();	
		if(!allRole.isEmpty())
			for (Role role : allRole) {
				UserRoleForm uf = new UserRoleForm();
				uf.setRoleId(role.getRoleId());
				uf.setRoleName(capitalize(role.getRoleName()));
				listRoleForm.add(uf);
			}
		return listRoleForm;
	}
	
	private static String capitalize(String line)	{
		return Character.toUpperCase(line.charAt(0)) + line.substring(1);
	}

	@Override 
	public List<String> getTeamFromContact() throws Exception {
		List<String> conList = null;
		UserDao userDao = Factory.getDao();	
		conList = userDao.getTeamFromContact();
		return conList;
	}

	@Override
	public List<UserForm> getAllocatedResourcesTeamWise(int userId, ReportForm form) throws Exception {
		List<User> allocatedResourceList = new ArrayList<User>();
		List<UserForm> allocatedResourceFormList = new ArrayList<UserForm>();
		try {
			UserDao userDao = Factory.getDao();
			String team = form.getTeam();
			if(team!=null){
				allocatedResourceList = userDao.getAllocatedResourcesTeamWise(userId, team);
				Iterator<User> itr = allocatedResourceList.iterator();
				while (itr.hasNext()) {
					UserForm userform = getUserForm(itr.next());
					allocatedResourceFormList.add(userform);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return allocatedResourceFormList;
	}
	@Override
	public List<AppraisalForm> getObjective(int user_id) throws Exception {
		// Getting defined Objective for This Resource.
		List<AppraisalForm> objListForm = new ArrayList<AppraisalForm>();
		try {
			UserDao userDao = Factory.getDao();
			Date d1 = new Date();
			DateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
			String month = sm.format(d1);
			List<Appraisal> listApp = userDao.getObjective(user_id, month);
			if(listApp!=null && !listApp.isEmpty()){
				Appraisal a1 = listApp.get(0);
				AppraisalForm af = new AppraisalForm();
				af.setCommObjective(a1.getComm_obj().trim());
				af.setSpecObjective(a1.getSpec_obj().trim());
				objListForm.add(af);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
		return objListForm;
	}

	@Override
	public List<UserForm> getAllocatedResourcesBasedOnStartAndExitDate(int userId, Calendar cal) throws Exception {
		// Getting Resource based on exit date.
		List<User> allocatedResourceList = new ArrayList<User>();
		List<UserForm> allocatedResourceFormList = new ArrayList<UserForm>();
		try {
			UserDao userDao = Factory.getDao();
			allocatedResourceList = userDao.getAllocatedResourcesBasedOnStartAndExitDate(userId, cal);
			Iterator<User> itr = allocatedResourceList.iterator();
			while (itr.hasNext()) {
				UserForm userform = getUserForm(itr.next());
				allocatedResourceFormList.add(userform);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return allocatedResourceFormList;
	}
	@Override
	public List<UserForm> getAllocatedResourcesBasedOnExitDateAndLocation(int userId, Calendar cal, String location) throws Exception {
		// For getting resource detail based on Location.
		List<User> allocatedResourceList = new ArrayList<User>();
		List<UserForm> allocatedResourceFormList = new ArrayList<UserForm>();
		try {
			UserDao userDao = Factory.getDao();
			allocatedResourceList = userDao.getAllocatedResourcesBasedOnExitDateAndLocation(userId, cal, location);
			Iterator<User> itr = allocatedResourceList.iterator();
			while (itr.hasNext()) {
				UserForm userform = getUserForm(itr.next());
				allocatedResourceFormList.add(userform);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return allocatedResourceFormList;
	}

	@Override
	public String setNotificationForSharedFeedback(String[] toListArray, File newFile, UserForm userFormForUsername, ClientFeedbackForm clientForm, String homeLoc, Properties props) throws Exception {
		// Sending email for shared feedback notification.
		String messageStatus = "Failed to send mail please try again later.";
		String host = props.getProperty("host_name").trim();
		String port = props.getProperty("port_number").trim();
		String senderEmail = props.getProperty("admimEmail").trim();
		String passeForSenderMail = props.getProperty("adminEmailPassword").trim();
		String bcc = senderEmail;
		String subject = clientForm.getSubject_line();
		logger.info("Printing file path "+newFile+" appContext "+homeLoc);
		String mess = "<div>Hi Team,</div><br><div style='padding-left: 10px;'>"
				+userFormForUsername.getUserName()+" has added message on client feedback page. You can view it in PEPQ home page."
				+"</div><br>Thanks and Regards,<br><div>Raghavi</div></div>";	
		messageStatus = EmailUtil.sendEmailWithAttachment(host, port, senderEmail, passeForSenderMail, senderEmail, toListArray, subject, mess, bcc, null);
		if(messageStatus.equalsIgnoreCase("Mail sent successfully.")){
			UserDao userDao = Factory.getDao();
			int user_Id = userFormForUsername.getUserId();	
			Time tm = new Time(new Date().getTime());
			Timestamp ts = new Timestamp(tm.getTime());			
			String messageWithRelativePath = homeLoc+File.separator+"client_feedback"+File.separator+newFile.getName();
			ClientFeedback c_feedback = getClientFeedbackPojo(messageWithRelativePath, ts, clientForm);
			userDao.saveFeedbackInfo(user_Id, c_feedback);
		}
		return messageStatus;
	}

	private ClientFeedback  getClientFeedbackPojo(String image_lcation, Timestamp ts, ClientFeedbackForm clientForm){
		ClientFeedback cf = new ClientFeedback();
		cf.setImage_loc(image_lcation);
		cf.setFeedback_summary(clientForm.getBody_content());
		cf.setTime_stamp(ts);
		return cf;
	}

	@Override
	public List<ClientFeedbackForm> getAllAddedFeedback(int user_id) {
		List<ClientFeedbackForm> list = new ArrayList<ClientFeedbackForm>();
		Calendar cal = Calendar.getInstance();
		Timestamp ts1 = new Timestamp(cal.getTime().getTime());
		cal.add(Calendar.DAY_OF_WEEK, -1);
		Timestamp ts2 = new Timestamp(cal.getTime().getTime());
		UserDao userDao = Factory.getDao();
		List<ClientFeedback> feedbackList = userDao.getAllAddedFeedback(ts1, ts2, user_id);
		if(feedbackList!=null)
			for (ClientFeedback clientFeedback : feedbackList) {
				ClientFeedbackForm form = new ClientFeedbackForm();
				form.setBody_content(clientFeedback.getFeedback_summary());
				form.setFile_loc(clientFeedback.getImage_loc());
				form.setUsername(clientFeedback.getUser().getUser_name());
				form.setFeedbackId(clientFeedback.getFeedbackId());
				list.add(form);
			}
		return list;
	}

	@Override
	public String setViewStatus(List<Integer> idList, int user_id) {
		// setting viewStatus for shared feedback.
		String status = "Error while setting status";
		UserDao userDao = Factory.getDao();
		status = userDao.setViewStatus(idList, user_id);
		return status;
	}

	@Override
	public UserForm getUserDetailForUserId(int user_id)throws Exception {
		// Getting User infromation from DB.
		UserForm user_form = new UserForm();
		UserDao userDao = Factory.getDao();
		Map<Object, Object> userDetail = new HashMap<Object, Object>();
		userDetail = userDao.getUserDetailForUserId(user_id);
		User u = (User) userDetail.get("UserDetail");
		ModelContact modelCon  = (ModelContact) userDetail.get("UserContact");
		List<Role> allRolesFromDb = (List<Role>) userDetail.get("allRolesFromDb");
		user_form.setUserId(u.getUser_Id());
		user_form.setUserName(u.getUser_name());
		user_form.setMail(u.getMail());
		user_form.setPassWord(u.getPassWord());
		user_form.setStatus(u.getStatus());
		user_form.setDefpass(u.getDefpass());
		user_form.setApollo_id(u.getApollo_id());
		user_form.setUser_role(userDetail.get("UserRole").toString());
		DateFormat df1 = new SimpleDateFormat("MM/dd/yyyy");
		DateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
		if(u.getExit_date() != null)
			user_form.setExit_date(df1.format(u.getExit_date()));
		if(u.getFreeze_timesheet() != null)
			user_form.setFreeze_timesheet_entry(df2.format(u.getFreeze_timesheet()));	
		if(u.getStart_date() != null){
			user_form.setStart_dateString(df1.format(u.getStart_date()));
		}
		if(modelCon !=null){
			user_form.setContact_no1(modelCon.getContact_no1());
			user_form.setContact_no2(modelCon.getContact_no2());
			user_form.setTeam(modelCon.getTeam());
			user_form.setApollo_manager(modelCon.getApollo_manager());
			user_form.setSkypeId(modelCon.getSkype_id());
			user_form.setLocation(modelCon.getLocation());
		}
		if(!allRolesFromDb.isEmpty()){
			List<UserRoleForm> all_role_form = new ArrayList<UserRoleForm>();
			UserRoleForm urf = null;
			for (Role role : allRolesFromDb) {
				urf = new UserRoleForm();
				urf.setRoleId(role.getRoleId());
				urf.setRoleName(role.getRoleName());
				all_role_form.add(urf);
			}
			user_form.setAll_roles(all_role_form);
		}
		user_form.setProject_id(u.getProject_id());
		return user_form;
	}

	@Override
	public String saveResourceInProfile(UserForm user_form) throws Exception {
		// Saving resource profile after update.
		String status = "Error while updating resource detail.";
		UserDao userDao = Factory.getDao();
		User u = getUser(user_form);
		String user_role_id  = Integer.toString(user_form.getUser_role_id());
		List<Contact> conList = new ArrayList<Contact>();
		if(user_form.getContact_no2() != ""){
			Contact model_con1 = new Contact();
			model_con1.setUser(u);
			model_con1.setContact_no(user_form.getContact_no2());
			model_con1.setApo_mang(user_form.getApollo_manager());
			model_con1.setSk_id(user_form.getSkypeId());
			model_con1.setTeam(user_form.getTeam());
			model_con1.setLocation(user_form.getLocation());
			conList.add(model_con1);
		}
		if(user_form.getContact_no1() != ""){
			Contact model_con = new Contact();
			model_con.setUser(u);
			model_con.setContact_no(user_form.getContact_no1());
			model_con.setApo_mang(user_form.getApollo_manager());
			model_con.setSk_id(user_form.getSkypeId());
			model_con.setTeam(user_form.getTeam());
			model_con.setLocation(user_form.getLocation());			
			if(conList.size() == 0){
				conList.add(model_con);
			} else if(conList.size() >0){
				conList.add(0, model_con);
			}
		}
		else if(conList.size() == 0){
			Contact model_con = new Contact();
			model_con.setUser(u);
			model_con.setContact_no("");
			model_con.setApo_mang(user_form.getApollo_manager());
			model_con.setSk_id(user_form.getSkypeId());
			model_con.setTeam(user_form.getTeam());
			model_con.setLocation(user_form.getLocation());
			conList.add(model_con);
		}
		status = userDao.saveResourceInProfile(u, user_role_id, conList); 
		return status;
	}

	@Override
	public String getUserRoleId(int uid) {
		UserDao dao = Factory.getDao();
		String  role_id = dao.getUserRoleId(uid);		
		return role_id;
	}

	@Override
	public String validateResourcePassword(Integer resourceId, String resourcePass) {
		String status = "Incorrect Password.";
		UserDao dao = Factory.getDao();
		status = dao.validateResourcePassword(resourceId, resourcePass);
		return status;
	}

	@Override
	public List<ReportForm> getAllClientInformationForClientBasedReport() throws Exception {
		// Method to get all client manager for client based reporting.
		List<ReportForm> clientList = new ArrayList<ReportForm>();
		List<User> clientModelList = null;
		UserDao dao = Factory.getDao();
		clientModelList = dao.getAllClientInformationForClientBasedReport();
		for (User user : clientModelList) {
			ReportForm rf = new ReportForm();
			rf.setClient_resource_ids(user.getUser_Id());
			rf.setUserName(user.getUser_name());
			clientList.add(rf);
		}
		return clientList;
	}

	@Override
	public int getAdminUserId() throws Exception {
		int admin_user = 0;
		UserDao dao = Factory.getDao();
		admin_user = dao.getAdminUserId();
		return admin_user;
	}

	@Override
	public int getAccountManagerId() throws Exception {
		int admin_user = 0;
		UserDao dao = Factory.getDao();
		admin_user = dao.getAccountManagerId();
		return admin_user;
	}

	@Override
	public List<ProjectDetailModel> getAllProjectInfo() throws Exception {
		List<ProjectDetailModel> project_detail_list = new ArrayList<ProjectDetailModel>();
		List<ProjectDetail> project_detail_from_db = new ArrayList<ProjectDetail>();
		UserDao dao = Factory.getDao();
		project_detail_from_db = dao.getAllProjectInfo();
		ProjectDetailModel  project_detail_model = null;
		for (ProjectDetail projectDetail : project_detail_from_db) {
			project_detail_model = new ProjectDetailModel();
			project_detail_model.setProject_id(projectDetail.getProject_id());
			project_detail_model.setProject_name(projectDetail.getProject_name());
			project_detail_list.add(project_detail_model);
		}
		return project_detail_list;
	}

}
