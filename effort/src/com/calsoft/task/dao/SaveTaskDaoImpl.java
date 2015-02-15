package com.calsoft.task.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.calsoft.leave.model.Leave;
import com.calsoft.report.model.Report;
import com.calsoft.task.dao.factory.SaveTaskDaoFactory;
import com.calsoft.task.model.Task;
import com.calsoft.user.model.User;
import com.calsoft.util.HbnUtil;
import com.calsoft.util.TimeUtility;

@SuppressWarnings("all")
public class SaveTaskDaoImpl implements SaveTaskDao {
	private static final Logger logger = Logger.getLogger("name");
	@Override
	public String[] doSaveTask(Task task) throws Exception {
		String[] idMessage = new String[2];
		String entrySatus = null;
		Session session = HbnUtil.getSession();
		Transaction taskTx = session.beginTransaction();
		DateFormat	formatter = new SimpleDateFormat("yyyy-MM-dd");
		String date = formatter.format(task.getTask_date()); 
		int user_id = task.getUser().getUser_Id();
		Query query = null;
		try{
			query = session.createSQLQuery("Select SEC_TO_TIME(SUM(TIME_TO_SEC(time)))as TotalHrs FROM employee_task_detail where user_id = "+user_id+" and task_date='"+date+"'");
			List<Object> list = query.list();
			List<String> totalTimeList = new ArrayList<String>();
			if(list!=null && list.get(0)!=null){
				String getUserTime = list.get(0).toString();
				totalTimeList.add(getUserTime);
				totalTimeList.add(task.getTime());	
				Double total = TimeUtility.getYourTime(totalTimeList);
				if(total>23){
					session.close();
					entrySatus = "Invalid entry for Time";
					idMessage[0] = entrySatus;
				}
				else{
					String status = task.getStatus();
					if(status.equalsIgnoreCase("Leave")||status.equalsIgnoreCase("Half Day")||status.equalsIgnoreCase("Comp off")){
						try{
							DateFormat dateFormat = new SimpleDateFormat("MMMM dd yyyy");
							Date date1 = new Date();
							String d1 = dateFormat.format(date1);
							Date sysDate = (Date) dateFormat.parse(d1);
							Leave leave = new Leave();
							SimpleDateFormat sm11 = new SimpleDateFormat("MMM-yyyy");
							Date d22 = formatter.parse(date);
							String any = sm11.format(d22);
							SimpleDateFormat sm22 = new SimpleDateFormat("d");
							List listCh = session.createSQLQuery("select *From leave_detail  where leave_date='"+sm22.format(d22)+"' and leave_month='"+any+"' and user_id="+user_id).list();
							if(listCh!=null && listCh.isEmpty()){
								leave.setLeave_date(sm22.format(d22));
								leave.setLeave_month(any);
								leave.setUpdated_on(sysDate);					
								User user = new User();
								user.setUser_Id(user_id);
								leave.setUser(user);
								session.save(leave);
							}
						}
						catch (Exception e){
							e.printStackTrace();
							throw new Exception();
						}
					}
					session.save(task);
					taskTx.commit();
					entrySatus ="Data entered sucessfully";
					idMessage[0] = entrySatus;
					idMessage[1] = Integer.valueOf(task.getId()).toString();
				}
			}
			else{
				String status = task.getStatus();
				if(status.equalsIgnoreCase("Leave")||status.equalsIgnoreCase("Half Day")||status.equalsIgnoreCase("Comp off")){
					try{
						DateFormat dateFormat = new SimpleDateFormat("MMMM dd yyyy");
						Date date1 = new Date();
						String d1 = dateFormat.format(date1);
						Date sysDate = (Date) dateFormat.parse(d1);
						Leave leave = new Leave();
						SimpleDateFormat sm11 = new SimpleDateFormat("MMM-yyyy");
						Date d22 = formatter.parse(date);
						String any = sm11.format(d22);
						SimpleDateFormat sm22 = new SimpleDateFormat("d");						
						List listCh = session.createSQLQuery("select *From leave_detail  where leave_date='"+sm22.format(d22)+"' and leave_month='"+any+"' and user_id="+user_id ).list();
						if(listCh!=null && listCh.isEmpty()){
							leave.setLeave_date(sm22.format(d22));
							leave.setLeave_month(any);
							leave.setUpdated_on(sysDate);					
							User user = new User();
							user.setUser_Id(user_id);
							leave.setUser(user);
							session.save(leave);
						}
					}
					catch (Exception e){
						e.printStackTrace();
						throw new Exception();
					}
				}
				session.save(task);
				taskTx.commit();
				entrySatus ="Data entered sucessfully";
				idMessage[0] = entrySatus;
				idMessage[1] = Integer.valueOf(task.getId()).toString();
			}		
		}		 
		catch (Exception e){
			session.close();
			entrySatus = "Invalid entry for Time";
			idMessage[0] = entrySatus;
			e.printStackTrace();
			logger.error(e);
		}
		return idMessage;
	}

	public List<Task> getTaskDetails(String month,int userId) throws Exception {
		List<Task> taskList = new ArrayList<Task>();
		List<Object> tList = null;
		Session session = HbnUtil.getSession();
		session.beginTransaction();
		String sqlQuery ="select id,backlog_id,task_id,task_description,ROUND(time/10000,2) as time,task_date,status,user_id,work_status from employee_task_detail where user_id="+userId+" and  task_date like '"+ month+"%' ORDER BY task_date DESC";
		Query query = session.createSQLQuery(sqlQuery);
		tList = query.list();
		Iterator<Object> itr = tList.iterator();
		while (itr.hasNext()) {
			Task task = new Task();
			Object[] obj = (Object[]) itr.next();
			int id = ((Integer) obj[0]).intValue();
			String backlogId = (String) obj[1];
			String taskId = (String) obj[2];
			String taskDescription = (String) obj[3];
			Double time = (Double) obj[4];
			String timeString = time.toString();
			Date date = (Date) obj[5];
			String status = (String) obj[6];
			//int userid = ((Integer) obj[7]).intValue();
			String work_status = (String) obj[8];
			task.setId(id);
			task.setBacklog_id(backlogId);
			task.setStatus(status);
			task.setTask_date(date);
			task.setTask_description(taskDescription);
			task.setTask_id(taskId);
			if(work_status==null){
				work_status = "";
			}
			task.setWork_status(work_status);		
			task.setTime(timeString);
			taskList.add(task);
		}
		session.getTransaction().commit();
		session.close();
		return taskList;
	}

	public boolean doDelete(int id) throws Exception{
		Session session = HbnUtil.getSession();
		boolean deleteFlag = false;
		Transaction tx = session.beginTransaction();
		String taskSt = "from Task task where task.id = " + id;
		List<Task> querySt = session.createQuery(taskSt).list();
		Iterator<Task> itrTask = querySt.iterator();
		if(itrTask.hasNext()){
			Task task = (Task) itrTask.next();
			String taskStatus = task.getStatus();
			Date taskDate = task.getTask_date();
			SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
			String formatedDate = sm.format(taskDate);
			int user_id = task.getUser().getUser_Id();
			if(taskStatus.equalsIgnoreCase("Leave")||taskStatus.equalsIgnoreCase("Half Day")||taskStatus.equalsIgnoreCase("Comp off")){
				Query query = session.createQuery("from Task where user_id="+user_id+" and task_date='"+formatedDate+"' and (status = 'Leave' or status= 'Half Day' or status= 'Comp off')");
				List<Task> list = query.list();
				if(!list.isEmpty() && list.size()==1){
					SimpleDateFormat sm11 = new SimpleDateFormat("MMM-yyyy");
					String any = sm11.format(taskDate);
					SimpleDateFormat sm22 = new SimpleDateFormat("d");
					String date = sm22.format(taskDate);					
					List qurList = session.createSQLQuery("select id from leave_detail where leave_month='"+any+"' and leave_date='"+date+"' and user_id="+user_id).list();
					if(!qurList.isEmpty()){
						Iterator<Integer> itrId = qurList.iterator();
						if(itrId.hasNext()){
							int idForLeave = (Integer)itrId.next();
							Query quryDel = session.createQuery("delete from Leave where id="+idForLeave);
							quryDel.executeUpdate();
						}
					}
				}
			}
		}				
		///////////////////////////  Above code for delete 
		String hql = "delete from Task task where task.id = " + id;
		Query query = session.createQuery(hql);
		int row = query.executeUpdate();
		if (row > 0){
			deleteFlag = true;
		}
		tx.commit();
		return deleteFlag;
	}
	@Override
	public List<Task> editTask(int id) throws Exception{
		List tList = null;
		Session session = HbnUtil.getSession();
		String hqlQuery = "From Task task where task.id="+id;
		Query query = session.createQuery(hqlQuery);
		tList = query.list();
		return tList;
	}
	@Override
	public String editSaveTask(Task task,int id,int user_id) throws Exception{
		String entrySatus = null;
		DateFormat	formatter = new SimpleDateFormat("yyyy-MM-dd");
		String date = formatter.format(task.getTask_date());  
		Session session=HbnUtil.getSession();
		Transaction taskTx = session.beginTransaction();
		Query query = null;
		DateFormat sm11 = new SimpleDateFormat("MMM-yyyy");
		Date d22 = formatter.parse(date);
		String any = sm11.format(d22);
		DateFormat sm22 = new SimpleDateFormat("d");
		String dString = sm22.format(d22);
		try{			
			Task checkDateForTask = (Task) session.get(Task.class, id);
			String dateFromDb = formatter.format(checkDateForTask.getTask_date());
			if(checkDateForTask!=null){
				// Checking entered date with existing date for Leave Calendar changes.
				if(!checkDateForTask.getTask_date().equals(date)){
					List<String> statusList = session.createSQLQuery("select status from employee_task_detail where (user_id="+user_id+" and id!="+id+") and task_date='"+dateFromDb+"'").list();
					if(statusList.isEmpty() ||(!statusList.isEmpty() && !(statusList.contains("Leave") || statusList.contains("Half Day") || statusList.contains("Comp off"))) ){
						Date dateForLeaveCal = formatter.parse(dateFromDb);
						String monthForLeave = sm11.format(dateForLeaveCal);
						String dayForLeave = sm22.format(dateForLeaveCal);
						List qurList = session.createSQLQuery("select id from leave_detail where leave_month='"+monthForLeave+"' and leave_date='"+dayForLeave+"' and user_id="+user_id).list();
						if(qurList!=null && !qurList.isEmpty()){
							Integer leaveId = (Integer) qurList.get(0);
							// Deleting previous leave entry from leave calendar 
							Query qr1 = session.createSQLQuery("delete from leave_detail where id="+leaveId);
							qr1.executeUpdate();
						}
					}
				}
			}			
			query = session.createSQLQuery("Select SEC_TO_TIME(SUM(TIME_TO_SEC(time)))as TotalHrs FROM employee_task_detail where user_id = "+user_id+" and task_date='"+date+"' and id!="+id+"");
			List<Object> list = query.list();
			List<String> totalTimeList = new ArrayList<String>();
			if(list!=null && list.get(0)!=null){
				String getUserTime = list.get(0).toString();
				totalTimeList.add(getUserTime);
				totalTimeList.add(task.getTime());	
				Double total = TimeUtility.getYourTime(totalTimeList);
				if(total>23){
					session.close();
					entrySatus = "Invalid entry for Time";
				}
				else{
					Query qr = session.createSQLQuery("select status from employee_task_detail where user_id="+user_id+" and "
							+"(status='Leave' or status='Half Day' or status= 'Comp off') and task_date='"+date+"'");
					List listStatus = qr.list();
					if(!listStatus.isEmpty() && !(task.getStatus().equalsIgnoreCase("Leave")||task.getStatus().equalsIgnoreCase("Half Day")||task.getStatus().equalsIgnoreCase("Comp off")) && listStatus.size()==1){
						Iterator<String> itr = listStatus.iterator();
						if (itr.hasNext()){
							List qurList = session.createSQLQuery("select id from leave_detail where leave_month='"+any+"' and leave_date='"+dString+"' and user_id="+user_id).list();
							if(!qurList.isEmpty()){
								int leave_id = 0;
								Iterator<Integer> itr1= qurList.iterator();
								if(itr1.hasNext()){								
									leave_id = 	(Integer) itr1.next();
								}
								Query qr1 = session.createSQLQuery("delete from leave_detail where id="+leave_id);
								qr1.executeUpdate();
							}							
						}																		
					}
					else if(!listStatus.isEmpty() && (task.getStatus().equalsIgnoreCase("Leave")||task.getStatus().equalsIgnoreCase("Half Day")||task.getStatus().equalsIgnoreCase("Comp off")) && listStatus.size() > 0){
						Iterator<String> itr = listStatus.iterator();
						if (itr.hasNext()){
							List qurList = session.createSQLQuery("select id from leave_detail where leave_month='"+any+"' and leave_date='"+dString+"' and user_id="+user_id).list();
							if(qurList.isEmpty()){
								Leave leave = new Leave();
								leave.setLeave_date(dString);
								leave.setLeave_month(any);
								leave.setUpdated_on(Calendar.getInstance().getTime());
								User u1 = new User();
								u1.setUser_Id(user_id);
								leave.setUser(u1);
								session.save(leave);
							}							
						}																		
					}			
					//Ended Here!!! Before update entry check status and update Leave Calendar.
					else if((listStatus.isEmpty()||listStatus.size()==0) && (task.getStatus().equalsIgnoreCase("Leave")||task.getStatus().equalsIgnoreCase("Half Day")||task.getStatus().equalsIgnoreCase("Comp off"))){
						Leave leave = new Leave();					
						List listCh = session.createSQLQuery("select *From leave_detail  where leave_date='"+dString+"' and leave_month='"+any+"' and user_id="+user_id).list();
						DateFormat dateFormat = new SimpleDateFormat("MMMM dd yyyy");
						Date date1 = new Date();
						String d1 = dateFormat.format(date1);
						Date sysDate = (Date) dateFormat.parse(d1);
						if(listCh!=null && listCh.isEmpty()){
							leave.setLeave_date(dString);
							leave.setLeave_month(any);
							leave.setUpdated_on(sysDate);					
							User user = new User();
							user.setUser_Id(user_id);
							leave.setUser(user);
							session.save(leave);
						}
					}
					String task_disc = task.getTask_description().replace("'", "`");		
					Task tk = (Task) session.get(Task.class, id);
					tk.setTask_date(task.getTask_date());
					tk.setStatus(task.getStatus());
					tk.setBacklog_id(task.getBacklog_id());
					tk.setTask_id(task.getTask_id());
					tk.setTask_description(task_disc);
					tk.setTime(task.getTime());
					tk.setWork_status(task.getWork_status());
					tk.setEntry_time(Calendar.getInstance().getTime());
					session.update(tk);
					//String sqlQuery = "update employee_task_detail set backlog_id='"+task.getBacklog_id()+"',task_id='"+task.getTask_id()+"',task_description='"+task_disc+"',time='"+task.getTime()+"',task_date='"+date+"',status='"+task.getStatus()+"',work_status='"+task.getWork_status()+"' where id='"+id+"' ";
					//query = session.createSQLQuery(sqlQuery);
					//query.executeUpdate();
					taskTx.commit();
					session.flush();
					session.close();
					entrySatus ="Data edited sucessfully";
				}
			}
			else{
				// Single entry for that day than single id.			
				Query qr = session.createSQLQuery("select status from employee_task_detail where id="+id+" and (status='Leave' or status='Half Day' or status= 'Comp off')");
				List listStatus = qr.list();
				if(!listStatus.isEmpty() && !(task.getStatus().equalsIgnoreCase("Leave")||task.getStatus().equalsIgnoreCase("Half Day")||task.getStatus().equalsIgnoreCase("Comp off"))){
					Iterator<String> itr = listStatus.iterator();
					if (itr.hasNext()){
						List qurList = session.createSQLQuery("select id from leave_detail where leave_month='"+any+"' and leave_date='"+dString+"' and user_id="+user_id).list();
						if(!qurList.isEmpty()){
							int leave_id = 0;
							Iterator<Integer> itr1= qurList.iterator();
							if(itr1.hasNext()){								
								leave_id = 	(Integer) itr1.next();
							}
							Query qr1 = session.createSQLQuery("delete from leave_detail where id="+leave_id);
							qr1.executeUpdate();
						}							
					}																		
				}
				else if((task.getStatus().equalsIgnoreCase("Leave")||task.getStatus().equalsIgnoreCase("Half Day")||task.getStatus().equalsIgnoreCase("Comp off"))){
					Leave leave = new Leave();					
					List listCh = session.createSQLQuery("select *From leave_detail  where leave_date='"+dString+"' and leave_month='"+any+"' and user_id="+user_id).list();
					DateFormat dateFormat = new SimpleDateFormat("MMMM dd yyyy");
					Date date1 = new Date();
					String d1 = dateFormat.format(date1);
					Date sysDate = (Date) dateFormat.parse(d1);
					if(listCh!=null && listCh.isEmpty()){
						leave.setLeave_date(dString);
						leave.setLeave_month(any);
						leave.setUpdated_on(sysDate);					
						User user = new User();
						user.setUser_Id(user_id);
						leave.setUser(user);
						session.save(leave);
					}
				}
				String task_disc = task.getTask_description().replace("'", "`");		
				//String sqlQuery = "update  employee_task_detail set backlog_id='"+task.getBacklog_id()+"',task_id='"+task.getTask_id()+"',task_description='"+task_disc+"',time='"+task.getTime()+"',task_date='"+date+"',status='"+task.getStatus()+"',work_status='"+task.getWork_status()+"' where id='"+id+"' ";
				//query = session.createSQLQuery(sqlQuery);
				//query.executeUpdate();
				Task tk = (Task) session.get(Task.class, id);
				tk.setTask_date(task.getTask_date());
				tk.setStatus(task.getStatus());
				tk.setBacklog_id(task.getBacklog_id());
				tk.setTask_id(task.getTask_id());
				tk.setTask_description(task_disc);
				tk.setTime(task.getTime());
				tk.setWork_status(task.getWork_status());
				tk.setEntry_time(Calendar.getInstance().getTime());
				session.update(tk);
				taskTx.commit();
				session.flush();
				session.close();
				entrySatus ="Data edited sucessfully";
			}		
		}		 
		catch (Exception e) {
			session.close();
			entrySatus = "Invalid entry for Time";
			e.printStackTrace();
		}		
		return entrySatus;
	}

	@Override
	public List<List<Task>> getTaskDetailsUser(String[] allocatedResource, String year, String month) throws Exception{
		List<List<Task>> superTaskList = new ArrayList<List<Task>>();
		Session session = HbnUtil.getSession();
		Transaction taskTx = session.beginTransaction();
		for (int i = 0; i < allocatedResource.length; i++) 
		{
			List<Task> taskList = new ArrayList<Task>();
			int userId = Integer.parseInt(allocatedResource[i]);
			String sqlQuery = "from Task where user_id="
					+ userId
					+ " and task_date like '"
					+ year
					+ "-"
					+ month
					+ "%" + "' order by task_date DESC";
			Query query = session.createQuery(sqlQuery);
			taskList =query.list();			
			if(taskList.isEmpty()){
				Task task = new Task();
				List<User> userNameList = session.createQuery("from User u where u.user_Id="+userId+"").list();
				if(!userNameList.isEmpty()){
					Iterator<User> iterateUser = userNameList.iterator();
					if(iterateUser.hasNext()){
						User u1 = iterateUser.next();
						task.setUser(u1);
					}
				}
				Calendar calendar = new GregorianCalendar(Integer.parseInt(year), Integer.parseInt(month)-1, Calendar.DAY_OF_MONTH);
				calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));									
				task.setTask_date(calendar.getTime());
				task.setBacklog_id("");
				task.setStatus("");
				task.setTask_id("");
				task.setTask_description("Efforts not entered.");
				task.setTime("00:00:00");
				task.setWork_status("");
				taskList.add(task);
			}			
			superTaskList.add(taskList);	 
		}
		taskTx.commit();
		return superTaskList;	
	}

	@Override
	public List<String> saveAllTask(List<Task> taskList) throws Exception {
		List<String> stringList = new ArrayList<String>();
		DateFormat	formatter = new SimpleDateFormat("yyyy-MM-dd");
		Session session = HbnUtil.getSession();		
		if(taskList!=null)
		{
			for (Task task : taskList) {
				String date = formatter.format(task.getTask_date()); 
				int user_id = task.getUser().getUser_Id();
				Transaction taskTx = session.beginTransaction();
				Query query = null;
				try{
					query = session.createSQLQuery("Select SEC_TO_TIME(SUM(TIME_TO_SEC(time)))as TotalHrs FROM employee_task_detail where user_id = "+user_id+" and task_date='"+date+"'");
					List<Object> list = query.list();
					List<String> totalTimeList = new ArrayList<String>();
					if(list!=null && list.get(0)!=null){
						String getUserTime = list.get(0).toString();
						totalTimeList.add(getUserTime);
						totalTimeList.add(task.getTime());	
						Double total = TimeUtility.getYourTime(totalTimeList);
						if(total>23){
							stringList.add("0");
						}
						else{
							String status = task.getStatus();
							if(status.equalsIgnoreCase("Leave")||status.equalsIgnoreCase("Half Day")||status.equalsIgnoreCase("Comp off")){
								try{
									DateFormat dateFormat = new SimpleDateFormat("MMMM dd yyyy");
									Date date1 = new Date();
									String d1 = dateFormat.format(date1);
									Date sysDate = (Date) dateFormat.parse(d1);
									Leave leave = new Leave();
									SimpleDateFormat sm11 = new SimpleDateFormat("MMM-yyyy");
									Date d22 = formatter.parse(date);
									String any = sm11.format(d22);
									SimpleDateFormat sm22 = new SimpleDateFormat("d");
									List listCh = session.createSQLQuery("select *From leave_detail  where leave_date='"+sm22.format(d22)+"' and leave_month='"+any+"' and user_id="+user_id).list();
									if(listCh!=null && listCh.isEmpty()){
										leave.setLeave_date(sm22.format(d22));
										leave.setLeave_month(any);
										leave.setUpdated_on(sysDate);					
										User user = new User();
										user.setUser_Id(user_id);
										leave.setUser(user);
										session.save(leave);
									}
								}
								catch (Exception e){
									e.printStackTrace();
									throw new Exception();
								}

							}
							session.save(task);	
							stringList.add(Integer.valueOf(task.getId()).toString());
						}
					}
					else{
						String status = task.getStatus();
						if(status.equalsIgnoreCase("Leave")||status.equalsIgnoreCase("Half Day")||status.equalsIgnoreCase("Comp off")){
							try{
								DateFormat dateFormat = new SimpleDateFormat("MMMM dd yyyy");
								Date date1 = new Date();
								String d1 = dateFormat.format(date1);
								Date sysDate = (Date) dateFormat.parse(d1);
								Leave leave = new Leave();
								SimpleDateFormat sm11 = new SimpleDateFormat("MMM-yyyy");
								Date d22 = formatter.parse(date);
								String any = sm11.format(d22);
								SimpleDateFormat sm22 = new SimpleDateFormat("d");						
								List listCh = session.createSQLQuery("select *From leave_detail  where leave_date='"+sm22.format(d22)+"' and leave_month='"+any+"' and user_id="+user_id ).list();
								if(listCh!=null && listCh.isEmpty()){
									leave.setLeave_date(sm22.format(d22));
									leave.setLeave_month(any);
									leave.setUpdated_on(sysDate);					
									User user = new User();
									user.setUser_Id(user_id);
									leave.setUser(user);
									session.save(leave);
								}
							}
							catch (Exception e){
								e.printStackTrace();
								throw new Exception();
							}
						}
						session.save(task);	
						stringList.add(Integer.valueOf(task.getId()).toString());				
					}	
					taskTx.commit();
				}		 
				catch (Exception e) {
					e.printStackTrace();
					stringList.add("0");
				}			
			}
		}	
		return stringList;
	}
	@Override
	public List<List<Task>> getTaskDetailsTeamWise(String[] detailsTeam, String year, String month, int user_id_from_session) throws Exception {
		List<List<Task>> superTaskList = new ArrayList<List<Task>>();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, Integer.parseInt(month)-1);
		cal.set(Calendar.YEAR, Integer.parseInt(year));		
		DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		String month_start_date = df1.format(cal.getTime());
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		String month_last_date = df1.format(cal.getTime());
		Session session = HbnUtil.getSession();
		Transaction tx = session.beginTransaction();	

		String query_string = "select u2.user_id from users u2 inner join contact con on u2.user_id = con.user_id where u2.user_id in"
				+"(select u.user_id from users u inner join report_mapping rm on u.user_id=rm.report_map_id where rm.user_id="+user_id_from_session+" and("
				+"(u.start_date is NULL and u.exit_date is NULL)||(u.start_date is NULL && u.exit_date >= '"+month_start_date+"')"
				+"||(u.start_date <= '"+month_last_date+"' && u.exit_date is NULL) || (u.start_date <= '"+month_last_date+"' && u.exit_date >= '"+month_start_date+"')))"
				+" and con.team =:resource_team group by u2.user_id order by u2.user_name";

		for (int i = 0; i < detailsTeam.length; i++) {
			Query q1 = session.createSQLQuery(query_string).setParameter("resource_team", detailsTeam[i]);
			List<Integer> user_list = q1.list();
			Iterator<Integer> itr = user_list.iterator();
			while(itr.hasNext()){
				int user_id = itr.next();
				User u1 = (User) session.get(User.class, user_id);
				List<Task> taskList = new ArrayList<Task>();
				String sqlQuery = "from Task where user_id="+user_id+" and task_date like '"+year+"-"+ month+"%' order by task_date DESC";
				Query query = session.createQuery(sqlQuery);
				taskList = query.list();				
				if(taskList.isEmpty()){
					Task task = new Task();
					task.setUser(u1);
					Calendar calendar = new GregorianCalendar(Integer.parseInt(year), Integer.parseInt(month)-1, Calendar.DAY_OF_MONTH);
					calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));									
					task.setTask_date(calendar.getTime());
					task.setBacklog_id("");
					task.setStatus("");
					task.setTask_id("");
					task.setTask_description("Efforts not entered.");
					task.setTime("00:00:00");
					task.setWork_status("");
					taskList.add(task);
				}				
				superTaskList.add(taskList);
			}
		}
		tx.commit();
		session.close();
		return superTaskList;	
	}
	//Getting resource detail from DB
	@Override
	public String getResourceLocation(int userId) throws Exception {
		String locDetail = "";
		Session session = HbnUtil.getSession();
		Transaction taskTx = session.beginTransaction();
		if(userId!=0){
			List<Object> locList =  session.createSQLQuery("Select distinct(location) from contact con where user_id="+userId).list();			
			if(!locList.isEmpty()){
				locDetail = locList.get(0).toString();
			}
		}
		taskTx.commit();
		session.close();
		return locDetail;
	}

	@Override
	public String freezeTimesheet(Integer resource_id, Calendar cal)throws Exception {
		// freezing time entry for current month
		String freezingStatus = null;
		Session s1 = HbnUtil.getSession();
		Transaction tx = s1.beginTransaction();
		try{
			User u1 = (User) s1.get(User.class, resource_id);
			if(u1 != null){
				u1.setFreeze_timesheet(cal.getTime());
				s1.update(u1);
			}
			s1.flush();
			tx.commit();		
			freezingStatus = "Timesheet freezed successfully.";
		}
		catch (Exception e) {
			logger.error("Printing exception detail from freezeTimesheet: "+e);
			freezingStatus = "Error while freezing timesheet.";
		}
		return freezingStatus;
	}

	@Override
	public String unfreezeTimesheet(Integer resource_id, Calendar cal) throws Exception {
		logger.info("Printing from SaveTaskDaoImpl unfreezeTimesheet method");
		String unfreezeTimesheetStatus = "";
		Session s1 = HbnUtil.getSession();
		Transaction tx = s1.beginTransaction();
		cal.add(Calendar.MONTH, -1);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		Query query = null;			
		try{
			User u1 = (User) s1.get(User.class, resource_id);
			u1.setFreeze_timesheet(cal.getTime());
			s1.update(u1);
			s1.flush();
			tx.commit();
			s1.close();
			unfreezeTimesheetStatus = "Timesheet entry unfreezed for currrent month.";
		}
		catch (Exception e) {
			e.printStackTrace();
			unfreezeTimesheetStatus = "Error while unfreezing timesheet.";
		}
		return unfreezeTimesheetStatus;
	}

	@Override
	public List<String> getEnteredTimesheetDateForPreviousMonth(Calendar c1, int user_id) {
		// All dates from previous months timesheet entry.
		List<String> allEnteredDates = new ArrayList<String>();
		DateFormat df = new SimpleDateFormat("yyyy-MM");
		String yearMonth = df.format(c1.getTime());
		String sqlQuery = "select DATE_FORMAT(t.task_date, '%Y-%m-%d')as task_date from employee_task_detail t where " +
				"user_id = "+user_id+" and t.task_date like '%"+yearMonth+"%' group by t.task_date";
		Session s1 = HbnUtil.getSession();
		Transaction tx = s1.beginTransaction();
		allEnteredDates = s1.createSQLQuery(sqlQuery).list();	
		tx.commit();
		s1.close();
		return allEnteredDates;
	}
}
