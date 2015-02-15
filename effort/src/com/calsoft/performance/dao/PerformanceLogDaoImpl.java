package com.calsoft.performance.dao;

import java.util.Date;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.calsoft.performance.form.PerformanceLogForm;
import com.calsoft.performance.form.UserCommentForm;
import com.calsoft.performance.model.UserAccessMapping;
import com.calsoft.performance.model.UserComment;
import com.calsoft.task.model.Task;
import com.calsoft.user.form.UserForm;
import com.calsoft.user.model.Appraisal;
import com.calsoft.user.model.User;
import com.calsoft.util.HbnUtil;

@SuppressWarnings({"unchecked","deprecation"})
public class PerformanceLogDaoImpl implements PerformanceLogDao {
	private static final Logger logger = Logger.getLogger("PerformanceLogDaoImpl");
	@Override
	public  Appraisal getPerformance(int userId ,String period) throws Exception{
		String startDate =null;
		String endDate =null;
		Date convertedStartDate= null;
		Date convertedEndDate=null;
		Calendar calendar = null;
		String appraisalStartDate=null;
		String appraisalEndDate=null;
		SimpleDateFormat sm = new SimpleDateFormat("MMM-yyyy");
		if(period!=null && period!=""){
			String[] allString = period.split(" to ");
			startDate = allString[0];//+"-"+allString[2];
			endDate = allString[1];//+"-"+allString[2];
			String[] endDateYear=endDate.split("-");
			convertedStartDate=sm.parse(startDate);	
			convertedEndDate=sm.parse(endDate);	
			SimpleDateFormat sm2 = new SimpleDateFormat("yyyy-MM-dd");
			appraisalStartDate=sm2.format(convertedStartDate);
			calendar = new GregorianCalendar(Integer.parseInt(endDateYear[1]),convertedEndDate.getMonth()  , Calendar.DAY_OF_MONTH);
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			appraisalEndDate=sm2.format(calendar.getTime());
		}		
		Appraisal userperformance=new Appraisal();
		List<Appraisal> performancelist =new ArrayList<Appraisal>();
		Session session = HbnUtil.getSession();
		Transaction tx = session.beginTransaction();
		performancelist = session.createQuery("FROM Appraisal app where user_id = "+userId+" AND app.start_date = '"+appraisalStartDate+"' AND app.end_date ='"+appraisalEndDate +"'").list();
		if(performancelist.size()==0){
			userperformance.setComm_obj("Not given for this period to this user.");
			userperformance.setSpec_obj("Not given for this period to this user.");
		}
		else{
			Iterator<Appraisal> itr = performancelist.iterator();
			while(itr.hasNext()) {
				userperformance	=itr.next();
			}
			tx.commit();
		}
		User u1 = new User();
		u1.setUser_Id(userId);
		userperformance.setUser(u1);
		session.close();
		return userperformance;
	}

	/*Not using this method for getting period list .
	 */
	@Override
	public List<String> getPeriodListForUser(int userId) {
		List<String> periodList=new ArrayList<String>();
		Session session = HbnUtil.getSession();
		Transaction tx = session.beginTransaction();
		try{
			List<Date> statusObjects= session.createQuery("select distinct(start_date)  FROM Appraisal ").list();
			List<Date> statusObjects2= session.createQuery("select distinct(end_date) FROM Appraisal ").list();
			Iterator<Date> itr = statusObjects.iterator();
			Iterator<Date> itr2 = statusObjects2.iterator();
			while(itr.hasNext()) {
				Date date=itr.next();
				Date date2=itr2.next();
				DateFormat df = new SimpleDateFormat("yyyy");
				String dt =df.format(date2);
				int startDate=date.getMonth();
				int endDate=date2.getMonth();
				String startDateName=getMonth(startDate);
				String endDateName=getMonth(endDate);
				String period=startDateName.substring(0, 3)+"-"+endDateName.substring(0, 3)+" "+dt;
				periodList.add(period.trim());
			}
			tx.commit();
			return periodList;
		}
		catch(Exception e){
			logger.error("Exception raised in getPeriodListForUser");
			e.printStackTrace();
			return null;
		}finally{
			session.close();
		}
	}

	public String getMonth(int month) {
		return new DateFormatSymbols().getMonths()[month];
	}

	@Override
	public int accountManager(PerformanceLogForm performanceLogForm,int userId , int id){
		int acc_Manager_Id = 0;
		Session session = HbnUtil.getSession();
		Transaction transaction = session.beginTransaction();
		List<Integer> query = session.createSQLQuery("SELECT user_id FROM users where user_id in " +
				"(SELECT user_id FROM user_role where role_id=1005 and user_id in" +
				" (SELECT user_id FROM report_mapping where report_map_id="+performanceLogForm.getUserId()+"))" ).list();
		if(query.size()!=0)
			acc_Manager_Id = query.get(0);
		transaction.commit();
		session.close();
		return acc_Manager_Id;
	}
	@Override
	public boolean saveUserComment(PerformanceLogForm performanceLogForm, int userId) {
		boolean status = false;
		Session session = HbnUtil.getSession();
		Transaction transaction = session.beginTransaction();
		try{
			if(performanceLogForm.getRoleNames()!=null){
				String[] all_users_access = performanceLogForm.getRoleNames();
				DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
				Calendar cal = Calendar.getInstance();
				String current_date = df.format(cal.getTime());
				Integer ap_id = performanceLogForm.getId();
				String given_comment = performanceLogForm.getUserComments();
				Integer logged_by = userId;
				UserComment user_comment = new UserComment();
				Appraisal a1 = (Appraisal) session.load(Appraisal.class, ap_id);
				user_comment.setAppraisal(a1);
				user_comment.setDate(current_date);
				user_comment.setComment_logged_by(logged_by);
				user_comment.setUser_Comment(given_comment);						
				session.save(user_comment);
				for (String comment_access : all_users_access) {
					UserAccessMapping user_acc_map = new UserAccessMapping();
					if(comment_access != null) {
						user_acc_map.setValid_user_id(Integer.parseInt(comment_access));
						user_acc_map.setUser_comment(user_comment);
						session.save(user_acc_map);
					}
				}
				transaction.commit();
				status = true;
			}
		}
		catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
			status = false; 
		}
		finally{
			session.close();			
		}
		return status;
	}

	@Override
	public boolean editUserComment(int commentId, String userComment) {
		Session session = HbnUtil.getSession();
		Transaction transaction = session.beginTransaction();
		try{
			java.util.Date today = new java.util.Date();
			java.sql.Date sqlToday = new java.sql.Date(today.getTime());
			SimpleDateFormat sm2 = new SimpleDateFormat("MM-dd-yyyy");
			String commentDate=sm2.format(sqlToday);
			List<UserComment> commentEdit= session.createQuery(" FROM UserComment where commentId="+commentId).list();
			UserComment updatedcomments =commentEdit.get(0);
			updatedcomments.setUser_Comment(userComment);
			updatedcomments.setDate(commentDate);
			session.save(updatedcomments);
			transaction.commit();
			return true;
		}catch(Exception e){
			logger.error("exception raised in editing user comment ");	
			e.printStackTrace();
			return false;
		}finally{
			session.close();
		}
	}

	@Override
	public List<UserCommentForm> getComments(int id,int userId, int admin_id_for_filtering_access_list) {
		List<UserCommentForm>  commentList = new ArrayList<UserCommentForm>();
		Session session = HbnUtil.getSession();
		Transaction transaction = session.beginTransaction();
		try{
			Appraisal a1 = (Appraisal) session.load(Appraisal.class, id);				
			Set<UserComment> uc = a1.getUserComment();
			for (UserComment userComment : uc) {
				UserCommentForm commentForm =new UserCommentForm();				
				commentForm.setId(a1.getId_app());
				commentForm.setCommentId(userComment.getCommentId());
				commentForm.setDate(userComment.getDate());
				commentForm.setUser_Comment(userComment.getUser_Comment());
				commentForm.setUser_id(userComment.getComment_logged_by());
				User user_object = (User) session.load(User.class, userComment.getComment_logged_by());
				commentForm.setUser_Name(user_object.getUser_name());
				List<String> commentAccessList = new ArrayList<String>();
				Set<UserAccessMapping> acc_info_set = userComment.getComment_mapping();
				List<Integer> list_for_allowed_user = new ArrayList<Integer>();
				for (UserAccessMapping userAccessMapping : acc_info_set) {
					user_object = (User) session.load(User.class, userAccessMapping.getValid_user_id());
					commentAccessList.add(user_object.getUser_name());
					list_for_allowed_user.add(user_object.getUser_Id());
				}
				commentForm.setCommentAccessList(commentAccessList);
				if(list_for_allowed_user.contains(userId) || userId == admin_id_for_filtering_access_list)
					commentList.add(commentForm);
			}
			transaction.commit();
		}catch(Exception e){
			logger.error("exception raised in getComments ");
			e.printStackTrace();
		}finally{
			session.close();
		}
		return commentList;
	}

	@Override
	public boolean deleteUserComment(int commentId) {
		Session session = HbnUtil.getSession();
		Transaction transaction = session.beginTransaction();
		try{
			Query quryDel = session.createQuery("delete from UserComment where commentId="+commentId);
			quryDel.executeUpdate();
			transaction.commit();
			return true;
		}
		catch(Exception e){
			logger.error("exception raised in deleteUserComment ");	
			e.printStackTrace();
			return false;
		}finally{
			session.close();
		}
	}

	@Override
	public List<UserForm> getUsersListForPerformanceLogPage(int userId) {
		List<UserForm>  userformList = new ArrayList<UserForm>();
		Session session = HbnUtil.getSession();
		Transaction transaction = session.beginTransaction();
		try{
			Query query = session.createSQLQuery("select distinct(u.user_id),u.user_name from users u inner join report_mapping rm " +
					"where u.user_id=rm.report_map_id and rm.user_id="+userId+" order by u.user_name" );
			Iterator<String[]> it = query.list().iterator();
			while (it.hasNext()){
				User user=new User();
				Object[] obj = (Object[]) it.next();
				Integer userIdTemp=(Integer)obj[0];
				int userIdValue=userIdTemp.intValue();
				user.setUser_Id(userIdValue);
				user.setUser_name((String) obj[1]);
				UserForm form =new UserForm();
				form.setUserId(user.getUser_Id());
				form.setUserName(user.getUser_name());
				userformList.add(form);
			}
			transaction.commit();
		}
		catch(Exception e){
			logger.error("exception raised in getUsersListForPerformanceLogPage ");		
			e.printStackTrace();	
		}finally{
			session.close();
		}
		return userformList;
	}

	@Override
	public List<User> getUsersListForPerformanceLogPageBasedOnResourceJoiningAndRelievingDate(int userId, Calendar cal, String objective_quarter) throws Exception {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat df1 = new SimpleDateFormat("MMM-yy");
		String quater_part1 = "";
		String quater_part2 = "";	
		if(objective_quarter == null){
			quater_part1 = df.format(cal.getTime());
			quater_part2 = df.format(cal.getTime());
		}else if(objective_quarter != null){
			String st_pos = objective_quarter.substring(0, objective_quarter.indexOf(' '));
			String end_pos = objective_quarter.substring(objective_quarter.lastIndexOf(' ')+1, objective_quarter.length());
			Calendar c1 = Calendar.getInstance();
			c1.setTime(df1.parse(st_pos));
			c1.set(Calendar.DAY_OF_MONTH, c1.getActualMinimum(Calendar.DAY_OF_MONTH));
			Calendar c2 = Calendar.getInstance();
			c2.setTime(df1.parse(end_pos));
			c2.set(Calendar.DAY_OF_MONTH, c2.getActualMaximum(Calendar.DAY_OF_MONTH));
			quater_part1 = df.format(c1.getTime());
			quater_part2 = df.format(c2.getTime());
		}
		List<User>  userformList = new ArrayList<User>();
		Session session = HbnUtil.getSession();
		Transaction transaction = session.beginTransaction();
		try{
			Query query = session.createSQLQuery("select distinct(u.user_id),u.user_name from users u inner join "
					+"report_mapping rm where u.user_id=rm.report_map_id and (rm.user_id="+userId+" and"
					+"((u.start_date is NULL and u.exit_date is NULL)||(u.start_date is NULL && u.exit_date >= '"+quater_part1+"')"
					+"||(u.start_date <= '"+quater_part2+"' && u.exit_date is NULL) || (u.start_date <= '"+quater_part2+"' "
					+"&& u.exit_date >= '"+quater_part1+"'))) order by u.user_name" );
			Iterator<Object[]> it = query.list().iterator();
			while (it.hasNext()){
				User user=new User();
				Object[] obj = (Object[]) it.next();
				user.setUser_Id((Integer)obj[0]);
				user.setUser_name((String) obj[1]);
				userformList.add(user);
			}
			transaction.commit();
		}
		catch(Exception e){
			logger.error("exception raised in getUsersListForPerformanceLogPageSelectedUserOnTop ");		
			e.printStackTrace();	
		}finally{
			session.close();
		}
		return userformList;
	}
	
	@Override
	public Map<Integer, String> getRoleNames(int userId, int performanceUserID) {
		Session session = HbnUtil.getSession();
		Transaction tx = session.getTransaction();
		Map<Integer, String> roleNames = new HashMap<Integer, String>();
		try{
			String query_for_access_list = "select u1.user_name,u1.user_id from users u1 "
					+"inner join user_role ur on u1.user_id=ur.user_id where (ur.role_id in(1003,1004,1005)|| ur.user_id="+performanceUserID+") and "
					+"u1.user_id in(select u2.user_id from users u2 inner join report_mapping rm on u2.user_id=rm.user_id where rm.report_map_id="+performanceUserID+")";			
			List<Object[]> query = session.createSQLQuery(query_for_access_list).list();
			Iterator<Object[]> it = query.iterator();
			while(it.hasNext()){
				Object[] objAry = it.next();
				String user_name = (String) objAry[0];
				Integer user_id = (Integer) objAry[1];
				roleNames.put(user_id, user_name);
			}if(performanceUserID == 0){
				roleNames.put(0,"NotAssgined");	
			}			
			tx.commit();
		}
		catch(Exception e){
			StringWriter stack = new StringWriter();
			e.printStackTrace(new PrintWriter(stack));
			logger.error("exception raised in getRoleNames ");
		}finally{
			session.close();
		}
		return roleNames;
	}

	@Override
	public List<PerformanceLogForm> checkPreviousWeekEntry(List<String> getAllDatesForPreviousWeek, List<UserForm> allocatedUserList) throws ParseException {
		// Checking last week time entry for resources.
		List<PerformanceLogForm> usersNotFilledEntry = new ArrayList<PerformanceLogForm>();
		Session s1 = HbnUtil.getSession();
		Transaction tx = s1.beginTransaction();
		String mon_Date = "";
		String fri_Date = "";
		String period = "-";
		DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat df2 = new SimpleDateFormat("MMM dd");
		if(!getAllDatesForPreviousWeek.isEmpty()){
			String firstDateOfWeek = getAllDatesForPreviousWeek.get(0);
			String lastDate = getAllDatesForPreviousWeek.get(4);
			try {
				Date d1 = df1.parse(firstDateOfWeek);
				Date d2 = df1.parse(lastDate);
				mon_Date = df2.format(d1);          // Getting Mon date.
				fri_Date = df2.format(d2);			// Getting Fri date.
			} catch (ParseException e1) {
				e1.printStackTrace();
			}		
		}
		for (UserForm userForm : allocatedUserList) {
			int userId = userForm.getUserId();
			int countForMissingEntry = 0;
			String single_date = "";
			for (String date : getAllDatesForPreviousWeek) {
				List<Task> taskList = s1.createQuery("from Task t where user_id="+userId+" and t.task_date='"+date+"'").list();
				if(taskList.isEmpty()){	
					String formatted_date = "";
					Date dateOnWhichEntryNotFound = df1.parse(date);
					formatted_date = df2.format(dateOnWhichEntryNotFound);					
					if(single_date == ""){
						single_date = single_date+""+formatted_date;
					}
					else{
						single_date = single_date+", "+formatted_date;
					}
					countForMissingEntry++;
				}
			}
			if(countForMissingEntry==5){
				period = mon_Date+"-"+fri_Date;
			}
			else{
				period = single_date;
			}
			if(period != ""){
				PerformanceLogForm p_form = new PerformanceLogForm();
				p_form.setUserName(userForm.getUserName());
				p_form.setPeriod(period);
				usersNotFilledEntry.add(p_form);
			}
		}	
		tx.commit();
		s1.close();		
		return usersNotFilledEntry;
	}

	@Override
	public boolean addCommentAndSendMail(User userToWhomCommentwillAdd, User userLoggedComment, List<String> getAllDatesForPreviousWeek) {
		// Checking resource objective is defined or not for this period 
		boolean status = false;
		String mon_Date = "-";
		String fri_Date = "-";
		DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
		if(!getAllDatesForPreviousWeek.isEmpty()){
			String firstDateOfWeek = getAllDatesForPreviousWeek.get(0);
			String lastDate = getAllDatesForPreviousWeek.get(4);
			try {
				Date d1 = df1.parse(firstDateOfWeek);
				Date d2 = df1.parse(lastDate);
				DateFormat df2 = new SimpleDateFormat("MMM dd");
				mon_Date = df2.format(d1);
				fri_Date = df2.format(d2);			
			} catch (ParseException e1) {
				e1.printStackTrace();
			}		
		}
		Calendar cal = Calendar.getInstance();
		Date currentDate = cal.getTime();
		String dateperiod = df1.format(currentDate);
		Session session = HbnUtil.getSession();
		Transaction tx = session.beginTransaction();
		List<Appraisal> statusObjects= session.createQuery(" FROM Appraisal app where user_id="+userToWhomCommentwillAdd.getUser_Id()+" and (app.start_date<'"+dateperiod+"' and app.end_date>'"+dateperiod+"')").list();
		if(!statusObjects.isEmpty()){
			Appraisal appraisal = statusObjects.get(0);
			DateFormat df2 = new SimpleDateFormat("MM-dd-yyyy");
			String dateForComment = df2.format(currentDate);
			String account_manager = "no";
			List<String> acc_manager_list = session.createSQLQuery("SELECT user_name FROM users where user_id in (SELECT user_id FROM user_role where role_id=1005"
					+" and user_id in (SELECT user_id FROM report_mapping where report_map_id="+userToWhomCommentwillAdd.getUser_Id()+"))").list();
			if(!acc_manager_list.isEmpty()){
				account_manager = acc_manager_list.get(0);
			}			
			UserComment comments=new UserComment();
			comments.setDate(dateForComment);
			comments.setUser_Name(userLoggedComment.getUser_name());
			comments.setUser_Comment("Timesheet not filled for "+mon_Date+" To "+fri_Date+".");
			comments.setAppraisal(appraisal);
			comments.setClient("no");
			comments.setManager("no");
			comments.setAccountManager(account_manager);
			comments.setUser(userToWhomCommentwillAdd.getUser_name());
			session.save(comments);
			status = true;
		}
		tx.commit();
		session.close();
		return status;
	}
	@Override
	public List<String> getEmailInfoForAllAccessListAfterExcludingClient(List<String> mailDeatilsForCommentTo) {
		// Getting email details for mailling
		List<String> maillingList = new ArrayList<String>();
		Session session = HbnUtil.getSession();
		Transaction tx = session.beginTransaction();
		for (String string : mailDeatilsForCommentTo) {
			List<Object[]> user_info = session.createSQLQuery("select u.email, u.apollo_id from users u inner join user_role ur on" +
					" u.user_id=ur.user_id where u.user_id="+string+" and ur.role_id NOT IN(1001,1003,1005,1006)").list();
			if(!user_info.isEmpty() && user_info.get(0) != null){
				Iterator<Object[]> itr = user_info.iterator();
				while (itr.hasNext()) {
					Object[] user_details = (Object[]) itr.next();
					if(user_details[1] != null && user_details[1].toString().length() > 5){
						maillingList.add(user_details[1].toString());
					}else{
						maillingList.add(user_details[0].toString());
					}		
				}
			}
		}
		tx.commit();
		session.close();
		return maillingList;
	}
}
