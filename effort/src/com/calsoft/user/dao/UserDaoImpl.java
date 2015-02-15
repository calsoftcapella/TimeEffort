package com.calsoft.user.dao;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.calsoft.exeception.DuplicateUser;
import com.calsoft.report.model.Report;
import com.calsoft.user.model.Appraisal;
import com.calsoft.user.model.ClientFeedback;
import com.calsoft.user.model.Contact;
import com.calsoft.user.model.ModelContact;
import com.calsoft.user.model.ProjectDetail;
import com.calsoft.user.model.Role;
import com.calsoft.user.model.User;
import com.calsoft.user.model.UserModel;
import com.calsoft.user.model.UserRole;
import com.calsoft.user.model.UserTask;
import com.calsoft.user.service.UserService;
import com.calsoft.util.HbnUtil;
import com.calsoft.util.Mycomparator;
import com.calsoft.util.TimeUtility;

@SuppressWarnings({"unchecked","rawtypes","deprecation"})
public class UserDaoImpl implements UserDao{
	private static final Logger logger = Logger.getLogger("name");
	UserService userService;
	private ArrayList<String> taskListMessage = null;
	static UserDaoImpl daoImpl;
	@Override
	public boolean addUser(User user, Session session, int roleId) throws Exception{
		try {
			int id = user.getUser_Id();
			if(id != 0){
				User user1 = (User)session.load(User.class,id);
				user1.setUser_name(user.getUser_name());
				user1.setMail(user.getMail());
				user1.setStatus(user.getStatus());
				user1.setProject_id(user.getProject_id());
				session.update(user1);				
				List<String> userRoleObj = session.createSQLQuery("select role_id from user_role where user_id="+id).list();
				if(!userRoleObj.isEmpty() && roleId != 0){
					String existing_role = userRoleObj.get(0);
					session.createSQLQuery("update user_role set role_id="+roleId+" where user_id="+id+" and role_id="+existing_role).executeUpdate();
				}	
				session.flush();
			}
			else if(roleId != 0){
				session.save(user);
				UserRole role=new UserRole();
				role.setRoleId(roleId);
				role.setUserId(user.getUser_Id());
				session.save(role);
			}
			else {
				session.save(user);
			}
			return true;
		} 
		catch (org.hibernate.exception.ConstraintViolationException e) {
			e.printStackTrace();
			throw new DuplicateUser();
		}
	}
	public  List <User> getUserName() throws Exception{
		List<User> userList=new ArrayList<User>();
		Session session = HbnUtil.getSession();
		Transaction tx = session.beginTransaction();
		List<User> userObjects = session.createQuery("FROM User").list();
		for (Iterator<User> iterator = userObjects.iterator(); iterator.hasNext();) {
			User user = iterator.next();
			userList.add(user);
		}
		tx.commit();
		Collections.sort(userList,new Mycomparator());
		return userList;
	}
	public List<User> getUser() throws Exception{
		List<User> userList=null;
		Session session=HbnUtil.getSession();
		session.beginTransaction();
		Query query=session.createQuery("from User order by user_name");
		userList=query.list();
		return userList;
	}
	@Override
	public User getUsernameFromId(int userId) {
		User user = new User();
		try{
			Session session=HbnUtil.getSession();
			session.beginTransaction();
			user = (User) session.get(User.class, userId);
			session.getTransaction().commit();
			session.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return user;
	}
	public boolean deActivateUser(User user) throws Exception{
		Session session=HbnUtil.getSession();
		session.beginTransaction();
		user = (User) session.load(User.class,user.getUser_Id());
		user.setStatus("InActive");
		session.update(user);
		session.getTransaction().commit();
		return true;
	}
	public boolean globalUser(User user, Properties props) throws Exception{
		Session session=HbnUtil.getSession();
		session.beginTransaction();
		user = (User) session.load(User.class,user.getUser_Id());		
		boolean globalPass = false;
		String def_pass = props.getProperty("default_password") != null ? props.getProperty("default_password") : "";
		if(user!=null && user.getDefpass().equalsIgnoreCase(def_pass)){	
			user.setDefpass("NotGlobal");	
			globalPass = true;
		}
		else if(user!=null && !user.getDefpass().equalsIgnoreCase(def_pass) || user.getDefpass() == ""){
			user.setDefpass(def_pass);
			globalPass = true;
		}
		session.update(user);
		session.getTransaction().commit();
		return globalPass;
	}
	@Override
	public User getUser(int id)throws Exception {
		Session session = HbnUtil.getSession();
		User user=(User)session.load(User.class,id);
		return user;
	}

	@Override
	public String matchUser(User user, HttpServletRequest request) throws Exception{
		String status = null;	
		Session session=HbnUtil.getSession();
		session.beginTransaction();       
		List<String> activeList = session.createSQLQuery("select status from users where email='"+user.getMail()+"'").list();		
		if(activeList!=null && !activeList.isEmpty()){
			status = activeList.get(0);
		}
		if(status!=null && status.equalsIgnoreCase("Active")){
			List<Object[]> list = session.createSQLQuery("select user_name,user_id from users where email='"+user.getMail()+"' and (password='"+user.getPassWord()+"' or defpass='"+user.getPassWord()+"')").list();
			String userName = null;
			if(list!=null && !list.isEmpty()){
				Object[] obj = list.get(0);
				userName = (String)obj[0];
				int user_id  = (Integer)obj[1];
				HttpSession session1 = request.getSession();
				session1.setAttribute("user_id", user_id);
				session1.setAttribute("userName", "Welcome, "+userName);
				String sql2 ="select distinct(role.role_id) from role inner join user_role on role.role_id= (select role_id from user_role where user_role.user_id=(select users.user_id from users where email='"+user.getMail()+"' and (password='"+user.getPassWord()+"' or defpass='"+user.getPassWord()+"')));";
				try{
					status = (String)session.createSQLQuery(sql2).list().get(0);
				}
				catch (IndexOutOfBoundsException e){
					return status = "errorOnAddUser";
				}
				return status;
			}	
			else{
				status=null;  // User is active but password not matches with DB password.
			}
		}
		session.getTransaction().commit();
		return status;
	}
	@Override
	public String storePassword(User user)  throws Exception{
		Session session=HbnUtil.getSession();
		session.beginTransaction();
		String status = null;       
		if(session.createQuery("from User where email='"+user.getMail()+"'").list()!=null){					
			List<String> activeList = session.createSQLQuery("select status from users where email='"+user.getMail()+"'").list();
			if(!activeList.isEmpty()){
				status = activeList.get(0);
			}			
			if(status!=null && status.equalsIgnoreCase("Active")){									
				SQLQuery query = session.createSQLQuery("update users set password='"+user.getPassWord()+"' where email='"+user.getMail()+"'");
				query.executeUpdate();
				session.getTransaction().commit();
			}
		}
		return status;
	}
	@Override
	public List<UserTask> getUserTask(int user_id) throws Exception{
		Session session=HbnUtil.getSession();
		session.beginTransaction();
		ArrayList<UserTask> taskList = new ArrayList<UserTask>();
		Map<String, String> getCurrentAndPrevoiusMonthDate = TimeUtility.getCurrentAndPrevoiusMonthDate();
		String current_month_date = getCurrentAndPrevoiusMonthDate.get("currentMonth");
		String previous_month_date = getCurrentAndPrevoiusMonthDate.get("previousMonth");
		String sql = "select et.task_date, SEC_TO_TIME(SUM(TIME_TO_SEC(et.time))) as time,et.status from employee_task_detail et where "
				+"(et.user_id="+user_id+" and et.task_date BETWEEN '"+previous_month_date+"' AND '"+current_month_date+"') group by et.task_date order by task_date DESC";
		List list = session.createSQLQuery(sql).list();
		Iterator<Object[]> iterator = list.iterator();
		taskListMessage = new ArrayList<String>();
		UserTask userTask = null;
		DateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat fr1 = new SimpleDateFormat("MM/dd/yyyy");
		while(iterator.hasNext()) {
			userTask = new UserTask();
			Object[] obj = (Object[]) iterator.next();		 
			Date cd = (Date)obj[0];
			String formated_date = myFormat.format(cd);
			List<String> statusList = session.createSQLQuery("select status from employee_task_detail where user_id="+user_id+" and task_date='"+formated_date+"'").list();
			if(!statusList.isEmpty() && statusList.contains("Public holiday")){
				userTask.setStatus("Public holiday");
			}
			else if(statusList.contains("Half Day")){
				userTask.setStatus("Half Day");
			}
			else if(statusList.contains("Leave")){
				userTask.setStatus("Leave");
			}
			else if(statusList.contains("Comp off")){
				userTask.setStatus("Comp off");
			}
			else{
				userTask.setStatus("");
			}
			userTask.setTask_date(fr1.format(cd));			
			String DBtime = obj[1].toString();
			userTask.setTime(DBtime.substring(0,DBtime.lastIndexOf(':')));   // MODIFICATION 31-07
			String time = userTask.getTime();			
			Date dateFromDB = null;			
			dateFromDB = fr1.parse(userTask.getTask_date());
			Calendar cal = GregorianCalendar.getInstance();
			Date currentMonth = new Date();
			cal.setTime(currentMonth);
			String currentMonthAsSting = fr1.format(cal.getTime());
			cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)-1);
			String prevMonthAsString = fr1.format(cal.getTime());
			//System.out.println("nextMonthAsString"+prevMonthAsString);
			Date currentMonthDate = null;
			Date prevMonthDate = null;
			currentMonthDate = fr1.parse(currentMonthAsSting);
			prevMonthDate = fr1.parse(prevMonthAsString);
			if(dateFromDB!=null && dateFromDB.getMonth()==currentMonthDate.getMonth()||dateFromDB.getMonth()==prevMonthDate.getMonth()){
				String lessTime = time.substring(0,time.indexOf(':'));	//MODIFICATION 31-07		
				if(Double.parseDouble(lessTime)<8 && Double.parseDouble(lessTime) >0){
					String dtt = userTask.getTask_date();
					if(!taskListMessage.contains(" "+dtt+" : You worked only for "+time+" hrs.")){
						taskListMessage.add(" "+dtt+" : You worked only for "+time+" hrs.");
					}	
				}
			}
			Date myDate = null;
			myDate = fr1.parse(userTask.getTask_date());
			if((myDate.getMonth()==new Date().getMonth()|| myDate.getMonth()+1==new Date().getMonth()) && TimeUtility.isDateInCurrentWeek(myDate)){			
				taskList.add(userTask);	
			}
		}
		List<String> allWeekDaysFromUtility = TimeUtility.getAllDatesForThisWeek();
		if(!taskList.isEmpty()){
			List<String> allDatesList = new ArrayList<String>();
			for (UserTask user_task : taskList) {
				allDatesList.add(user_task.getTask_date());
			}
			for (String weekDays : allWeekDaysFromUtility) {
				if(!allDatesList.contains(weekDays)){
					UserTask user_task = new UserTask();
					user_task.setTask_date(weekDays);
					user_task.setTime("Not Entered.");
					user_task.setStatus("");
					taskList.add(user_task);	
				}
			}			
		}
		else{
			for (String weekDays : allWeekDaysFromUtility) {
				if(TimeUtility.isDateInCurrentWeek(fr1.parse(weekDays))){
					UserTask user_task = new UserTask();
					user_task.setTask_date(weekDays);
					user_task.setTime("Not Entered.");
					user_task.setStatus("");
					taskList.add(user_task);	
				}
			}			
		}
		Collections.sort(taskList, Collections.reverseOrder(new UserTask()));
		session.getTransaction().commit();
		return taskList;
	}
	public ArrayList<String> getTaskMessageList() throws Exception{		
		return this.taskListMessage;
	}

	@Override
	public List<User> getAllocatedResources(int userId) {
		List<User> userList=new ArrayList<User>();
		try{
			Session s1 = HbnUtil.getSession();
			Transaction tx = s1.beginTransaction();
			Query query = s1.createSQLQuery("select distinct(u.user_id),u.user_name from users u inner join report_mapping rm where u.user_id=rm.report_map_id and rm.user_id="+userId+" order by u.user_name" );
			Iterator<String[]> it = query.list().iterator();
			while (it.hasNext()){
				User user=new User();
				Object[] obj = (Object[]) it.next();
				Integer userIdTemp=(Integer)obj[0];
				int userIdValue=userIdTemp.intValue();
				user.setUser_Id(userIdValue);
				user.setUser_name((String) obj[1]);
				userList.add(user);
			}
			tx.commit();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return userList;
	}
	@Override
	public List<User> getAllocatedResourcesDetailsBasedOnRelievingDate(int userId, Calendar cal) {
		List<User> userList=new ArrayList<User>();
		try{
			Session s1 = HbnUtil.getSession();
			Transaction tx = s1.beginTransaction();
			Query query = s1.createSQLQuery("select u.user_id,u.user_name,u.email from users u inner join report_mapping rm"
					+" where u.user_id=rm.report_map_id and (rm.user_id="+userId+" and(u.exit_date >= CURDATE() || u.exit_date is null)) order by u.user_name" );
			Iterator<String[]> it = query.list().iterator();
			while (it.hasNext()){
				User user=new User();
				Object[] obj = (Object[]) it.next();
				Integer userIdTemp=(Integer)obj[0];
				int userIdValue=userIdTemp.intValue();
				user.setUser_Id(userIdValue);
				user.setUser_name((String) obj[1]);
				user.setMail(obj[2].toString());
				userList.add(user);
			}
			tx.commit();
			s1.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return userList;
	}

	@Override
	public List<User> getAllocatedResourcesDetailsForRemindarMailBasedOnRelievingDate(int userId, Calendar cal) {
		List<User> userList=new ArrayList<User>();
		try{
			Session s1 = HbnUtil.getSession();
			Transaction tx = s1.beginTransaction();
			Query query = s1.createSQLQuery("select u.user_id,u.user_name,u.email,u.start_date from users u inner join report_mapping rm"
					+" where u.user_id=rm.report_map_id and (rm.user_id="+userId+" and ((u.exit_date >= CURDATE() || u.exit_date is null) and u.status = 'Active'))"
					+" order by u.user_name" );
			Iterator<String[]> it = query.list().iterator();
			while (it.hasNext()){
				User user=new User();
				Object[] obj = (Object[]) it.next();
				Integer userIdTemp=(Integer)obj[0];
				int userIdValue=userIdTemp.intValue();
				user.setUser_Id(userIdValue);
				user.setUser_name((String) obj[1]);
				user.setMail(obj[2].toString());
				user.setStart_date((Date) obj[3]);
				userList.add(user);
			}
			tx.commit();
			s1.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return userList;
	}

	@Override
	public List<UserModel> getAllocatedResourcesDetailsForMonthlyReminder(int userId, Calendar cal) {
		List<UserModel> userList = new ArrayList<UserModel>();
		try{
			Session s1 = HbnUtil.getSession();
			Transaction tx = s1.beginTransaction();
			/*Query query = s1.createSQLQuery("select distinct(u.user_id), u.user_name, u.email, u.apollo_id, u.start_date from users u inner join report_mapping rm"
						+" where u.user_id=rm.report_map_id and (rm.user_id="+userId+" and ((u.exit_date >= CURDATE() || u.exit_date is null) and u.status = 'Active'))"
						+" order by u.user_name" );*/
			String sql_query = "select u1.user_id, u1.user_name, u1.email, u1.apollo_id, u1.start_date, ur.role_id from users u1 inner join user_role ur "
					+"on u1.user_id=ur.user_id where (ur.role_id = 1004 and u1.user_id in("
					+"select u.user_id from users u inner join report_mapping rm where u.user_id=rm.report_map_id and rm.user_id="+userId+")||"
					+"u1.user_id in("
					+"select u_out.user_id from users u_out where u_out.user_id not in"
					+"(select rmx.report_map_id from users usx inner join report_mapping rmx "
					+"on usx.user_id=rmx.report_map_id inner join user_role urx on rmx.user_id=urx.user_id and urx.role_id = 1004"
					+" group by usx.user_name)"
					+"and u_out.user_id in("
					+"select usx1.user_id from users usx1 inner join report_mapping rm2 where usx1.user_id=rm2.report_map_id and rm2.user_id="+userId
					+")"
					+")) and ((u1.exit_date >= CURDATE() || u1.exit_date is null) and u1.status = 'Active')order by u1.user_name";

			Query query = s1.createSQLQuery(sql_query);
			Iterator<String[]> it = query.list().iterator();
			while (it.hasNext()){
				UserModel user=new UserModel();
				Object[] obj = (Object[]) it.next();
				user.setUser_id((Integer)obj[0]);
				user.setUser_name((String) obj[1]);
				user.setCalsoft_id((String) obj[2]);
				user.setApollo_id((String) obj[3]);
				user.setJoining_date((Date) obj[4]);
				String role_id = (String) obj[5];
				user.setRole_id(role_id != null ? Integer.parseInt(role_id) : 0);
				userList.add(user);
			}
			tx.commit();
			s1.close();
		}
		catch(Exception e){
			StringWriter stack = new StringWriter();
			e.printStackTrace(new PrintWriter(stack));
			logger.info("Exception occured while executing Monthly reminder mail from DAO"+stack.toString());
		}
		return userList;
	}
	@Override
	public boolean changePassword(User user, String newPassword)throws Exception {
		boolean b=false;
		try {
			Session session=HbnUtil.getSession();
			User user2=(User)session.get(User.class,user.getUser_Id());
			if(user2.getPassWord().equals(user.getPassWord())){
				session.beginTransaction();
				user2.setPassWord(newPassword);
				session.update(user2);
				session.getTransaction().commit();
				b=true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
		return b;
	}
	@Override
	public List<List<Report>> getUserReportAllocation(int userId) throws Exception {
		List<Report> allocatedList=new ArrayList<Report>();
		List<Report> unallocatedList=new ArrayList<Report>();
		List<List<Report>> combinedList=new ArrayList<List<Report>>();
		String allocatedQueryString="select user_name,user_id  from users where user_id  in( select u.user_id from users u inner join report_mapping rm on u.user_id=rm.report_map_id and rm.user_id="+userId+")"; 
		Session s1 = HbnUtil.getSession();
		Transaction tx = s1.beginTransaction();
		Query allocatedQuery = s1.createSQLQuery(allocatedQueryString);
		Iterator it1=allocatedQuery.list().iterator();
		while (it1.hasNext()) {
			Report report=new Report();
			Object[] obj = (Object[]) it1.next();
			String userName=(String)obj[0];
			Integer  userIdValue=(Integer)obj[1];
			int userIdValue1=userIdValue.intValue();
			report.setUserName(userName);
			report.setUserId(userIdValue1);
			allocatedList.add(report);
		}
		String unallocatedQueryString="select user_name,user_id  from users where user_id not in( select u.user_id from users u inner join report_mapping rm on u.user_id=rm.report_map_id and rm.user_id="+userId+")";
		Query unallocatedQuery = s1.createSQLQuery(unallocatedQueryString);
		Iterator it2=unallocatedQuery.list().iterator();
		while (it2.hasNext()) {
			Report report=new Report();
			Object[] obj = (Object[]) it2.next();
			String userName=(String)obj[0];
			Integer  userIdValue=(Integer)obj[1];
			int userIdValue1=userIdValue.intValue();
			report.setUserName(userName);
			report.setUserId(userIdValue1);
			unallocatedList.add(report);
		}
		combinedList.add(allocatedList);
		combinedList.add(unallocatedList);
		tx.commit();
		s1.close();
		return combinedList;
	}

	// Retrieving Contact from DB and sending list 
	@Override
	public List<Object> getContactDetails(List<User> allocatedUserList) throws Exception {		
		List<Object> masterList = new ArrayList<Object>();		
		Session s1 = HbnUtil.getSession();
		s1.beginTransaction();
		SQLQuery sqlQuery =  s1.createSQLQuery("SELECT contact_no,team,apo_mang,sk_id FROM contact where user_id= :user_id");
		for (User user : allocatedUserList) {
			String userName = user.getUser_name();
			int userId = user.getUser_Id();
			List<Object> list = new ArrayList<Object>();
			list.add(userName);
			sqlQuery.setParameter("user_id", userId);
			List<Object[]> listContact = sqlQuery.list();
			Iterator<Object[]> itr = listContact.iterator();
			String[] conDetail = new String[3];
			String team = "";
			String manager = "";
			String sky ="";
			int count = 0;
			if(!listContact.isEmpty() && listContact.size()==2){
				while (itr.hasNext()){
					Object[] con = (Object[])itr.next();					  
					conDetail[count] = (String)con[0];
					team = (String) con[1];
					manager = (String) con[2];
					sky = (String) con[3];
					count++;
				}
				list.add(conDetail);
				list.add(team);
				list.add(manager);
				list.add(sky);
			}		
			else if(!listContact.isEmpty()){					
				if(itr.hasNext()){
					Object[] con = (Object[])itr.next();					  
					conDetail[0] = (String)con[0];
					team = (String) con[1];
					manager = (String) con[2];
					sky = (String) con[3];
				}
				list.add(conDetail);
				list.add(team);
				list.add(manager);
				list.add(sky);
			}												
			masterList.add(list);			
		}	
		s1.getTransaction().commit();
		s1.close();
		return masterList;
	}

	@Override
	public List<Role> getAllRoles() throws Exception {
		Session s1 = HbnUtil.getSession();
		s1.beginTransaction();
		List<Role> roleList  = s1.createQuery("from Role order by roleName").list();
		s1.getTransaction().commit();
		s1.close();
		return roleList;
	}

	@Override
	public List<String> getTeamFromContact() throws Exception {
		Session s1 = HbnUtil.getSession();
		s1.beginTransaction();
		String sql = "select distinct(con.team) from contact con inner join users u on con.user_id=u.user_id order by con.team";		
		List<String> conList = s1.createSQLQuery(sql).list();	
		while (conList.contains(null)) {
			conList.remove(null);
		}
		while (conList.contains("")) {
			conList.remove("");
		}
		s1.getTransaction().commit();
		s1.close();
		return conList;
	}

	@Override
	public List<User> getAllocatedResourcesTeamWise(int userId, String team) throws Exception {
		List<User> userList=new ArrayList<User>();
		try{
			Session s1 = HbnUtil.getSession();
			Transaction tx = s1.beginTransaction();
			Query query = s1.createSQLQuery("select distinct(u.user_id),u.user_name from users u inner join report_mapping rm, contact con"
					+" where u.user_id=rm.report_map_id and u.user_id=con.user_id  and (rm.user_id="+userId+" and team='"+team+"') order by u.user_name ");
			Iterator<String[]> it = query.list().iterator();
			while (it.hasNext()){
				User user=new User();
				Object[] obj = (Object[]) it.next();
				Integer userIdTemp=(Integer)obj[0];
				int userIdValue=userIdTemp.intValue();
				user.setUser_Id(userIdValue);
				user.setUser_name((String) obj[1]);
				userList.add(user);
			}
			tx.commit();
			s1.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return userList;
	}

	@Override
	public List<Appraisal> getObjective(int user_id, String month) throws Exception {
		// Added for getting Objective for User.
		Session s1 = HbnUtil.getSession();
		Transaction tx = s1.beginTransaction();
		List<Appraisal> list = new ArrayList<Appraisal>();
		try{
			String qr = "from Appraisal app where (app.start_date<'"+month+"' and app.end_date>'"+month+"') and user_id="+user_id+"";
			List<Appraisal> objList = s1.createQuery(qr).list();
			Iterator<Appraisal> itr = objList.iterator();
			if(itr.hasNext()) {
				Appraisal objApp =  itr.next();
				if(objApp.getComm_obj()==null){
					objApp.setComm_obj("");
				}
				if(objApp.getSpec_obj()==null){
					objApp.setSpec_obj("");
				}
				list.add(objApp);
			}
			tx.commit();
			s1.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public List<User> getAllocatedResourcesBasedOnStartAndExitDate(int userId, Calendar cal) throws Exception {
		List<User> userList=new ArrayList<User>();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");	
		try{
			cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
			String month_start_date = df.format(cal.getTime());
			cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
			String month_last_date = df.format(cal.getTime());
			Session s1 = HbnUtil.getSession();
			Transaction tx = s1.beginTransaction();
			Query query = s1.createSQLQuery("select u.user_id,u.user_name,u.email,u.apollo_id from users u inner join report_mapping rm"
					+" on u.user_id=rm.report_map_id where rm.user_id="+userId+" and("
					+"(u.start_date is NULL and u.exit_date is NULL)||(u.start_date is NULL && u.exit_date >= '"+month_start_date+"')"
					+"||(u.start_date <= '"+month_last_date+"' && u.exit_date is NULL) || (u.start_date <= '"+month_last_date+"' && u.exit_date >= '"+month_start_date+"'))order by u.user_name" );
			Iterator<Object[]> it = query.list().iterator();
			while (it.hasNext()){
				User user=new User();
				Object[] obj = (Object[]) it.next();
				user.setUser_Id((Integer)obj[0]);
				user.setUser_name((String) obj[1]);
				user.setMail(obj[2].toString());
				user.setApollo_id((String) obj[3]);
				userList.add(user);
			}
			tx.commit();
			s1.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return userList;
	}
	@Override
	public List<User> getAllocatedResourcesBasedOnExitDateAndLocation(int userId, Calendar cal, String location) throws Exception {
		// Getting mapped resource based on Resource Location.
		List<User> userList=new ArrayList<User>();
		try{
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
			String month_start_date = df.format(cal.getTime());
			cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
			String month_last_date = df.format(cal.getTime());
			Session s1 = HbnUtil.getSession();
			Transaction tx = s1.beginTransaction();
			Query query = s1.createSQLQuery("select u2.user_id,u2.user_name from users u2 inner join contact con on u2.user_id = con.user_id where u2.user_id in"
					+"(select u.user_id from users u inner join report_mapping rm"
					+" on u.user_id=rm.report_map_id where rm.user_id="+userId+" and("
					+"(u.start_date is NULL and u.exit_date is NULL)||(u.start_date is NULL && u.exit_date >= '"+month_start_date+"')"
					+"||(u.start_date <= '"+month_last_date+"' && u.exit_date is NULL) || (u.start_date <= '"+month_last_date+"' && u.exit_date >= '"+month_start_date+"')))"
					+" and con.location like '%"+location+"%' group by u2.user_id order by u2.user_name" );
			logger.info("Printing query info from getAllocatedResourcesBasedOnExitDateAndLocation method \n"+query);
			Iterator<String[]> it = query.list().iterator();
			while (it.hasNext()){
				User user=new User();
				Object[] obj = (Object[]) it.next();
				int userIdValue = (Integer)obj[0];
				user.setUser_Id(userIdValue);
				user.setUser_name((String) obj[1]);							
				userList.add(user);						
			}
			tx.commit();
			s1.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return userList;
	}
	@Override
	public void saveFeedbackInfo(int user_Id, ClientFeedback c_feedback) {
		Session s1 = HbnUtil.getSession();
		Transaction tx = s1.beginTransaction();
		User user = new User();
		user.setUser_Id(user_Id);
		c_feedback.setUser(user);
		c_feedback.setViewed_users("none");
		s1.save(c_feedback);
		s1.close();
		tx.commit();
	}
	@Override
	public List<ClientFeedback> getAllAddedFeedback(Timestamp ts1, Timestamp ts2, int user_id) {
		List<ClientFeedback> updatedFeedbackList = new ArrayList<ClientFeedback>();
		try{
			Session s1 = HbnUtil.getSession();
			Transaction tx = s1.beginTransaction();
			List<ClientFeedback> feedbackList = s1.createQuery("from ClientFeedback cf where cf.time_stamp BETWEEN '"+ts2+"' and '"+ts1+"'").list(); 
			for (ClientFeedback clientFeedback : feedbackList) {
				ClientFeedback cf = (ClientFeedback) s1.get(ClientFeedback.class, clientFeedback.getFeedbackId());
				if(cf != null){
					if(!cf.getViewed_users().contains(Integer.toString(user_id))){
						updatedFeedbackList.add(clientFeedback);
					}
				}
			}
			s1.close();
			tx.commit();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return updatedFeedbackList;
	}
	@Override
	public String setViewStatus(List<Integer> idList, int user_id) {
		// Setting viewStatus for shared feedback.
		String status = "Error while setting status";
		try{
			Session s1 = HbnUtil.getSession();
			Transaction tx = s1.beginTransaction();
			for (Integer id : idList) {
				ClientFeedback cf = (ClientFeedback) s1.get(ClientFeedback.class, id);
				if(cf != null){
					String viewed_users = cf.getViewed_users();					
					if(viewed_users != null && viewed_users.trim().equalsIgnoreCase("none")){
						cf.setViewed_users(Integer.toString(user_id));
					}
					else if(!viewed_users.contains(Integer.toString(user_id))){
						cf.setViewed_users(viewed_users+","+Integer.toString(user_id));
					}
					s1.update(cf);
					s1.flush();
				}
			}	
			s1.close();
			tx.commit();
			status = "Status updated successfully.";
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	@Override
	public Map<Object, Object> getUserDetailForUserId(int userId)throws Exception {
		// Retrieving User info
		Map<Object, Object> userDetailInMap = new HashMap<Object, Object>();
		Session s1 = HbnUtil.getSession();
		Transaction tx = s1.beginTransaction();
		try{
			User user = (User) s1.get(User.class, userId);
			List<Object[]> contactList = s1.createSQLQuery("select * from contact con where con.user_id="+userId+" order by con.id").list();
			List<Integer> userRole = s1.createSQLQuery("select r.role_id from role r inner join user_role ur on ur.role_id=r.role_id where ur.user_id="+userId).list();
			List<Role> allRolesFromDb = s1.createCriteria(Role.class).addOrder(Order.asc("roleName")).list();
			Iterator<Object[]> itr = contactList.iterator();
			ModelContact m_con = new ModelContact();
			int i = 0;
			while (itr.hasNext()) {
				Object[] contactObj = (Object[]) itr.next();
				if(i == 0){
					m_con.setContact_no1(contactObj[2].toString());
				}
				else if(i ==1) {
					m_con.setContact_no2(contactObj[2].toString());
				}
				if(contactObj[3] == null){
					m_con.setTeam("");
				}
				else{
					m_con.setTeam(contactObj[3].toString());
				}
				if(contactObj[4] == null){
					m_con.setApollo_manager("");
				}
				else{
					m_con.setApollo_manager(contactObj[4].toString());
				}
				if(contactObj[5] == null){
					m_con.setSkype_id("");
				}
				else{
					m_con.setSkype_id(contactObj[5].toString());
				}
				if(contactObj[6] == null){
					m_con.setLocation("");
				}
				else{
					m_con.setLocation(contactObj[6].toString());
				}
				i++;
			}
			userDetailInMap.put("UserDetail", user);
			userDetailInMap.put("UserContact", m_con);
			if(!userRole.isEmpty()){
				userDetailInMap.put("UserRole", userRole.get(0));	
			}if(!allRolesFromDb.isEmpty()){
				userDetailInMap.put("allRolesFromDb", allRolesFromDb);
			}
			tx.commit();
			s1.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
		return userDetailInMap;
	}
	@Override
	public String saveResourceInProfile(User u, String user_role_id, List<Contact> conList) throws Exception {
		// save resource information after update
		String status = "Error while updating resource detail.";
		Session s1 = HbnUtil.getSession();
		Transaction tx = s1.beginTransaction();
		try{
			User user = (User) s1.get(User.class, u.getUser_Id());
			if(user != null){
				user.setUser_name(u.getUser_name());
				user.setPassWord(u.getPassWord());
				user.setDefpass(u.getDefpass());
				user.setStatus(u.getStatus());
				user.setExit_date(u.getExit_date());
				user.setStart_date(u.getStart_date());
				user.setFreeze_timesheet(u.getFreeze_timesheet());
				user.setApollo_id(u.getApollo_id());
				user.setProject_id(u.getProject_id());
				s1.update(user);
				List<String> userRoleObj = s1.createSQLQuery("select role_id from user_role where user_id="+u.getUser_Id()).list();
				if(!userRoleObj.isEmpty()){
					String existing_role = userRoleObj.get(0);
					s1.createSQLQuery("update user_role set role_id="+user_role_id+" where user_id="+u.getUser_Id()+" and role_id="+existing_role).executeUpdate();
				}				
				s1.createSQLQuery("delete from contact  where user_id="+u.getUser_Id()).executeUpdate();
				for (Contact contact : conList) {
					s1.save(contact);
				}	
			}
			status = u.getUser_name()+"'s account has been updated successfully.";
			tx.commit();
			s1.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw new Exception();
		}
		return status;
	}
	@Override
	public String getUserRoleId(int uid) {
		String role_id = "0";
		Session s1 = HbnUtil.getSession();
		Transaction tx = s1.beginTransaction();
		List<String> userRoleObj = s1.createSQLQuery("select role_id from user_role where user_id="+uid).list();
		if(!userRoleObj.isEmpty()){
			role_id = userRoleObj.get(0);
		}
		tx.commit();
		s1.close();
		return role_id;
	}
	@Override
	public String validateResourcePassword(Integer resourceId, String resourcePass) {
		String status = "Incorrect Password.";
		Session s1 = HbnUtil.getSession();
		Transaction tx = s1.beginTransaction();
		User resourcebj = (User) s1.load(User.class, resourceId);
		if(resourcebj.getPassWord().equals(resourcePass)){
			status = "Password Matched.";
		}
		tx.commit();
		s1.close();
		return status;
	}
	@Override
	public List<User> getAllClientInformationForClientBasedReport() throws Exception {
		// Getting All Client Manager detail from DB.
		List<User> clientResourceList = new ArrayList<User>();
		Session s1 = HbnUtil.getSession();
		Transaction tx = s1.beginTransaction();
		try{
			List<Object[]> clientInfoList = s1.createSQLQuery("select u.user_id, u.user_name from users u inner join user_role ur on u.user_id=ur.user_id and ur.role_id=1006 order by u.user_name").list();
			if(!clientInfoList.isEmpty()){
				for (Object[] clientResourceObj : clientInfoList) {
					User u1 = new User();
					u1.setUser_Id((Integer)clientResourceObj[0]);
					u1.setUser_name((String) clientResourceObj[1]);
					clientResourceList.add(u1);
				}
			}
			tx.commit();
		}catch (Exception e) {
			StringWriter stack = new StringWriter();
			e.printStackTrace(new PrintWriter(stack));
			logger.info("Exception occured while executing getAllClientInformationForClientBasedReport "+stack.toString());
			throw new Exception();
		}finally{
			s1.close();
		}
		return clientResourceList;
	}
	@Override
	public int getAdminUserId() throws Exception {
		int admin_user_id = 0;
		Session s1 = HbnUtil.getSession();
		Transaction tx = s1.beginTransaction();
		Criteria crt = s1.createCriteria(UserRole.class).add(Restrictions.eq("roleId", 1007));
		ProjectionList proList = Projections.projectionList();
		proList.add(Projections.property("userId"));
		List<Integer> admin_user_id_list = crt.setProjection(proList).list();
		admin_user_id = !admin_user_id_list.isEmpty() ? admin_user_id_list.get(0) : 0;
		tx.commit();
		s1.close();
		return admin_user_id;
	}
	@Override
	public int getAccountManagerId() throws Exception {
		int admin_user_id = 0;
		Session s1 = HbnUtil.getSession();
		Transaction tx = s1.beginTransaction();
		Criteria crt = s1.createCriteria(UserRole.class).add(Restrictions.eq("roleId", 1005));
		ProjectionList proList = Projections.projectionList();
		proList.add(Projections.property("userId"));
		List<Integer> admin_user_id_list = crt.setProjection(proList).list();
		admin_user_id = !admin_user_id_list.isEmpty() ? admin_user_id_list.get(0) : 0;
		tx.commit();
		s1.close();
		return admin_user_id;
	}
	@Override
	public List<ProjectDetail> getAllProjectInfo() throws Exception {
		List<ProjectDetail> project_info_list = new ArrayList<ProjectDetail>();
		Session s1 = HbnUtil.getSession();
		Transaction tx = s1.beginTransaction();
		project_info_list = s1.createCriteria(ProjectDetail.class).addOrder(Order.asc("project_name")).list();
		tx.commit();
		s1.close();
		return project_info_list;
	}
}
