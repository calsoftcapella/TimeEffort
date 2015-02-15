package com.calsoft.leave.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.calsoft.factory.Factory;
import com.calsoft.leave.model.Leave;
import com.calsoft.report.model.Report;
import com.calsoft.task.model.Task;
import com.calsoft.user.dao.UserDao;
import com.calsoft.user.model.User;
import com.calsoft.util.HbnUtil;
import com.calsoft.util.TimeUtility;

/*Dao Implementation class for LeaveEntry Module*/
@ SuppressWarnings({"unchecked","rawtypes"})
public class LeaveDaoImpl implements LeaveDao {
	private static final Logger logger = Logger.getLogger("name");
	/* saveLeave() method :Performing the save operation of Leave Entry */
	HttpServletRequest request=null;
	@Override
	public void saveLeave(List leaveList,String[] checkDateNew, int userId, String selectMonthYear) throws Exception {
		Session session = HbnUtil.getSession();
		Transaction leaveTx = session.beginTransaction();
		String delete="delete from Leave AS leave where user_id="+userId+" and leave.leave_month Like '"+selectMonthYear+"%'";
		Query query=session.createQuery(delete);
		int deleted=query.executeUpdate();
		logger.info("Printing from saveLeave Method."+deleted);
		DateFormat dfor1 = new SimpleDateFormat("MMM-yyyy");
		DateFormat dfor2 = new SimpleDateFormat("yyyy-MM");
		DateFormat sm1 = new SimpleDateFormat("MMM-yyyy");
		DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
		DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sm2 = new SimpleDateFormat("d");
		Date dt = dfor1.parse(selectMonthYear);		
		Iterator<Leave> itr=leaveList.iterator();
		while(itr.hasNext()){
			Leave leave=itr.next();
			String date = leave.getLeave_date();
			String month = leave.getLeave_month();
			String dateMonth = date+'-'+month;
			Date f_date = df.parse(dateMonth);
			String formatedDate = df1.format(f_date);

			Task task = new Task();
			User user = new User();
			user.setUser_Id(userId);
			task.setUser(user);
			task.setBacklog_id("NA");
			task.setTask_id("NA");
			task.setStatus("Leave");
			task.setTime("00:00");
			task.setWork_status("home");
			task.setTask_date(f_date);
			task.setTask_description("Availed leave today.");
			task.setEntry_time(Calendar.getInstance().getTime());

			Query queryString = session.createSQLQuery("Select SEC_TO_TIME(SUM(TIME_TO_SEC(time)))as TotalHrs FROM employee_task_detail where user_id = "+userId+" and task_date='"+formatedDate+"'");
			List<Object> list = queryString.list();
			List<String> hrsList = new ArrayList<String>();			
			if(list!=null && list.get(0)!=null){
				hrsList.add(list.get(0).toString());
				Double total = TimeUtility.getYourTime(hrsList);
				if(total<14){
					Query q1 = session.createQuery("from Task task where (user_id="+userId+" and task.task_date='"+formatedDate+"') and "
							+"(task.status='Leave' or task.status='Comp off' or task.status='Half Day')");
					List<Task> taskList = q1.list();
					if(taskList.isEmpty()){						
						session.save(task);
						session.save(leave);
					}
				}
			}
			else{
				session.save(task);
				session.save(leave);
			}			
		}
		//Checking time entry page for leave entry and registering to Leave Calender.
		List<Task> taskList = session.createQuery("from Task task where user_id="+userId+" and(task.task_date like '%"+dfor2.format(dt)+"%' and "
				+"(task.status='Leave' or task.status='Comp off' or task.status='Half Day'))").list();
		if(!taskList.isEmpty()){
			Iterator<Task> it = taskList.iterator();
			while (it.hasNext()) {
				Task t = (Task) it.next();
				Date task_date =  t.getTask_date();
				List<Leave> leaveListDetail = session.createQuery("from Leave leave where user_id="+userId+" and "
						+"(leave.leave_month='"+sm1.format(task_date)+"' and leave.leave_date='"+sm2.format(task_date)+"')").list();
				if(leaveListDetail.isEmpty()){
					Leave leave = new Leave();
					User u1 = new User();
					u1.setUser_Id(userId);
					leave.setUser(u1);
					leave.setLeave_month(sm1.format(task_date));
					leave.setLeave_date(sm2.format(task_date));
					leave.setUpdated_on(Calendar.getInstance().getTime());	
					session.save(leave);
				}				
			}
		}		
		leaveTx.commit();
		session.close();
	}

	/*
	 * getAllLeave() method :Retriving the all records for selected month of
	 * Leave Entry
	 */

	@Override
	public List<Leave> getAllLeave(String selectMonth,HttpServletRequest request) throws Exception{
		Session session = HbnUtil.getSession();
		int userId = Integer.parseInt(request.getSession().getAttribute("user_id").toString());
		String getLeave = "FROM Leave AS leave where user_id=" + userId
				+ " and leave_month Like '" + selectMonth
				+ "%' ORDER BY leave_date ASC";
		Query query = session.createQuery(getLeave);
		List leaveList = query.list();
		Iterator it = leaveList.iterator();
		while (it.hasNext()){
			Leave halfLeave = (Leave) it.next();
			String dateH = halfLeave.getLeave_date();
			String monthH = halfLeave.getLeave_month();
			String leaveDate = dateH+'-'+monthH;
			SimpleDateFormat sf = new SimpleDateFormat("dd-MMM-yyyy");
			SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd");
			Date date1 = sf.parse(leaveDate);
			String formattedDate = sf1.format(date1);
			String sqlForHalfLeave = "select status from employee_task_detail where task_date='"+formattedDate+"' and status='Half Day' and user_id="+userId+"";
			Query queryLeave = session.createSQLQuery(sqlForHalfLeave);
			List halfLeaveList = queryLeave.list();
			if(halfLeaveList!=null && !halfLeaveList.isEmpty()){
				String month = halfLeave.getLeave_month();
				halfLeave.setLeave_month(month+" (H)");
			}
		}
		return leaveList;
	}

	/*
	 * getLeaveDate() method :Retriving only dates for selected month of Leave
	 * Entry
	 */

	public List<String> getLeaveDate(String selectMonth, HttpServletRequest request) throws Exception {
		Session session = HbnUtil.getSession();
		int userId = Integer.parseInt(request.getSession().getAttribute("user_id").toString());
		String getLeave = "select leave_date FROM Leave AS leave where user_id="+ userId+" and leave_month Like '"+selectMonth+"%' ORDER BY leave_date ASC";
		Query query = session.createQuery(getLeave);
		List<String> leaveList = query.list();
		return leaveList;
	}

	/*
	 * getDashBoardList() method :Retriving specific records for selected month
	 * of Leave Entry
	 */

	public List<Leave> getDashBoardList(String selectMonthYear, HttpServletRequest request) throws Exception {
		List<Leave> leaveList = null;
		Session session = HbnUtil.getSession();
		Transaction tx = session.beginTransaction();
		int userId = Integer.parseInt(request.getSession().getAttribute("user_id").toString());
		String getLeave = "from Leave AS leave where user_id=" + userId
				+ " and leave_month Like '" + selectMonthYear
				+ "%' group by leave.leave_month ORDER BY leave_date ASC";
		Query query = session.createQuery(getLeave);
		leaveList = query.list();
		tx.commit();
		session.close();
		return leaveList;
	}

	/*
	 * clearCheck() method :Removing records which are checked for selected
	 * month of Leave Entry
	 */

	public void clearCheck(List leaveList) throws Exception {
		Session session = HbnUtil.getSession();	
		Iterator<Leave> itr =leaveList.iterator();
		while(itr.hasNext()){
			Leave leave=itr.next();
			Transaction leaveTx = session.beginTransaction();
			//String checkMonth = leave.getLeave_month();
			String checkDate = leave.getLeave_date();
			int userId = leave.getUser().getUser_Id();
			String leave_month = leave.getLeave_month();			
			String monthYear = checkDate+'-'+leave_month;
			DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
			DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
			Date f_date = df.parse(monthYear);
			String formatedDate = df1.format(f_date);
			Query taskQuery = session.createQuery("from Task task where (user_id="+userId+" and task.task_date='"+formatedDate+"') and "
					+"(task.status='Leave' or task.status='Comp off' or task.status='Half Day')");
			List<Task> task_list = taskQuery.list();
			if(!task_list.isEmpty()){
				Iterator<Task> itTask = task_list.iterator();
				while (itTask.hasNext()) {
					Task task = (Task) itTask.next();
					int task_id = task.getId();
					Query delete_qy = session.createQuery("delete from Task task where task.id="+task_id);
					delete_qy.executeUpdate();					
				}
			}		
			String clearDate = "delete from Leave  where user_id=" + userId+ " and (leave_date='"+checkDate+"' and leave_month='"+leave_month+"')";
			Query query = session.createQuery(clearDate);	
			query.executeUpdate();
			leaveTx.commit();
		}
		session.close();
	}

	@Override
	public List<List<Leave>> getLeaveDetailsUser(String[] allocatedResource,String dateString) {
		List<List<Leave>> superLeaveList = new ArrayList<List<Leave>>();
		try {
			Session session = HbnUtil.getSession();
			for (int i = 0; i < allocatedResource.length; i++) {
				List<Leave> leaveList = new ArrayList<Leave>();
				int userId = Integer.parseInt(allocatedResource[i]);
				String sqlQuery = "select id,leave_month,leave_date,updated_on,user_id from  leave_detail ld where " +
						"ld.user_id="+userId+" and  ld.leave_month like '"+dateString+"%'";
				Query query = session.createSQLQuery(sqlQuery);
				Iterator<String[]> it = query.list().iterator();
				UserDao userDao = Factory.getDao();
				while (it.hasNext()) {
					Leave leave = new Leave();
					Object[] obj = (Object[]) it.next();					
					String leaveDate = (String) obj[2]+'-'+(String) obj[1];
					DateFormat sf = new SimpleDateFormat("dd-MMM-yyyy");
					DateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd");
					Date date1 = sf.parse(leaveDate);
					String formattedDate = sf1.format(date1);
					String sqlForLeave = "select status from employee_task_detail where (task_date='"+formattedDate+"' and user_id="+userId+") and (status='Half Day' or status= 'Comp off')";
					Query queryLeave = session.createSQLQuery(sqlForLeave);
					List<String> halfLeaveList = queryLeave.list();
					if(halfLeaveList!=null && !halfLeaveList.isEmpty()){
						String leaveStatus = halfLeaveList.get(0);
						if(leaveStatus.equalsIgnoreCase("Half Day")){
							leave.setLeave_month((String) obj[2]+'-'+(String) obj[1]+" (H)");
						}
						else if(leaveStatus.equalsIgnoreCase("Comp off")){
							leave.setLeave_month((String) obj[2]+'-'+(String) obj[1]+" (C)");
						}
					}
					else{
						leave.setLeave_month((String) obj[2]+'-'+(String) obj[1]);
					}
					leave.setLeave_date((String) obj[2]);
					leave.setUpdated_on((Date) obj[3]);
					int userid = ((Integer) obj[4]).intValue();
					User user = userDao.getUser(userid);
					String userName = user.getUser_name();
					User user1 = new User();
					user1.setUser_name(userName);
					leave.setUser(user1);
					leaveList.add(leave);
					Collections.sort(leaveList, Collections.reverseOrder(new SortBasedOnLeaveDate()));
				}
				superLeaveList.add(leaveList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return superLeaveList;
	}

	public static class SortBasedOnLeaveDate implements Comparator<Leave>{
		@Override
		public int compare(Leave o1, Leave o2) {
			return (Integer.parseInt((o1.getLeave_date()))>Integer.parseInt(o2.getLeave_date()) ? -1 : 
				(Integer.parseInt((o1.getLeave_date()))==Integer.parseInt((o2.getLeave_date())) ? 0 : 1));
		}		
	}
	/*
	 * getTeamLeaveDetail() method :Performing the get Team Leave Detail operation from
	 * getTeamLeaveDetail page of Leave Entry
	 */

	@Override
	public List<List<Leave>> getTeamLeaveDetail(List allocatedResource,String dateString)throws Exception {
		logger.info("getTeamLeaveDetail LeaveDao");
		List<List<Leave>> superLeaveList = new ArrayList<List<Leave>>();
		DateFormat sf = new SimpleDateFormat("dd-MMM-yyyy");
		DateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd");		
		try {
			Session session = HbnUtil.getSession();
			Iterator<Report> itr=allocatedResource.iterator();
			while(itr.hasNext()){
				List<Leave> leaveList = new ArrayList<Leave>();
				int userId = itr.next().getUserId();
				String sqlQuery = "select id,leave_month,leave_date,updated_on,user_id from  leave_detail ld where ld.user_id="
						+userId+" and  ld.leave_month like '"+dateString+"%'";
				Query query = session.createSQLQuery(sqlQuery);
				Iterator<String[]> it = query.list().iterator();
				while (it.hasNext()) {
					Leave leave = new Leave();
					Object[] obj = (Object[]) it.next();
					leave.setLeave_month((String) obj[1]);
					leave.setLeave_date((String) obj[2]);
					String leaveDate = leave.getLeave_date()+'-'+leave.getLeave_month();					
					Date date1 = sf.parse(leaveDate);
					String formattedDate = sf1.format(date1);
					String sqlForHalfLeave = "select status from employee_task_detail where task_date='"+formattedDate+"' and status='Half Day' and user_id="+userId+"";
					Query queryLeave = session.createSQLQuery(sqlForHalfLeave);
					List halfLeaveList = queryLeave.list();
					if(halfLeaveList!=null && !halfLeaveList.isEmpty()){
						String month = leave.getLeave_month();
						leave.setLeave_month(month+" (H)");
					}
					leave.setUpdated_on((Date) obj[3]);
					int userid = ((Integer) obj[4]).intValue();
					User user = (User) session.load(User.class, userid);
					leave.setUser(user);
					leaveList.add(leave);
				}
				if(!leaveList.isEmpty()){
					Collections.sort(leaveList, Collections.reverseOrder(new Leave.SortBasedOnLeaveDate()));
					superLeaveList.add(leaveList);
				}			
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return superLeaveList;
	}

	@Override
	public List<List<Leave>> getLeaveDetailsTeamWise(String[] teamDetails, String dateString, int user_id_from_session) throws Exception {
		List<List<Leave>> superLeaveList = new ArrayList<List<Leave>>();
		Calendar cal = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("MMM-yyyy");			
		DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");			
		cal.setTime(df.parse(dateString));
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
		try {				
			for (int i = 0; i < teamDetails.length; i++){
				Query q1 = session.createSQLQuery(query_string).setParameter("resource_team", teamDetails[i]);
				List<Integer> user_list = q1.list();
				Iterator<Integer> itr = user_list.iterator();
				while(itr.hasNext()){
					int user_id = itr.next();
					List<Leave> leaveList = new ArrayList<Leave>();
					String sqlQuery = "select id,leave_month,leave_date,updated_on,user_id from  leave_detail ld where ld.user_id="+user_id+" and  ld.leave_month"
							+" like '"+dateString+"%'";
					Query query = session.createSQLQuery(sqlQuery);
					Iterator<String[]> it = query.list().iterator();
					UserDao userDao = Factory.getDao();
					while (it.hasNext()){
						Leave leave = new Leave();
						Object[] obj = (Object[]) it.next();					
						String leaveDate = (String) obj[2]+'-'+(String) obj[1];
						DateFormat sf = new SimpleDateFormat("dd-MMM-yyyy");
						Date date1 = sf.parse(leaveDate);
						String formattedDate = df1.format(date1);						
						String sqlForLeave = "select status from employee_task_detail where (task_date='"+formattedDate+"' and user_id="+user_id+") "
								+"and (status='Half Day' or status= 'Comp off')";
						Query queryLeave = session.createSQLQuery(sqlForLeave);
						List<String> halfLeaveList = queryLeave.list();
						if(halfLeaveList!=null && !halfLeaveList.isEmpty()){
							String leaveStatus = halfLeaveList.get(0);
							if(leaveStatus.equalsIgnoreCase("Half Day")){
								leave.setLeave_month((String) obj[2]+'-'+(String) obj[1]+" (H)");
							}
							else if(leaveStatus.equalsIgnoreCase("Comp off")){
								leave.setLeave_month((String) obj[2]+'-'+(String) obj[1]+" (C)");
							}
						}
						else{
							leave.setLeave_month((String) obj[2]+'-'+(String) obj[1]);
						}
						leave.setLeave_date((String) obj[2]);
						leave.setUpdated_on((Date) obj[3]);
						int userid = ((Integer) obj[4]).intValue();
						User user = userDao.getUser(userid);
						String userName = user.getUser_name();
						User user1 = new User();
						user1.setUser_name(userName);
						leave.setUser(user1);
						leaveList.add(leave);	
						Collections.sort(leaveList, Collections.reverseOrder(new SortBasedOnLeaveDate()));
					}	
					superLeaveList.add(leaveList);
				}
			}
			tx.commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return superLeaveList;
	}

	@Override
	public List<Object[]> getAllLeaveForYear(String year, int user_id) throws Exception {
		// Get all leave for selected Year
		List<Object[]> complete_leave_list = new ArrayList<Object[]>();
		Session session = HbnUtil.getSession();
		String queryToGetLeave = "select u_out.user_name,DATE_FORMAT(et.task_date,'%Y-%m-%d') as wr_dt, et.status from "
								+"(select u.user_id,u.user_name, DATE_FORMAT(STR_TO_DATE(CONCAT(l.leave_date,'-',l.leave_month),'%e-%b-%Y'),'%Y-%m-%d') as leave_month_yr"
								+" from users u inner join leave_detail l on u.user_id=l.user_id and (u.user_id in("
								+" select urs.user_id from users urs inner join report_mapping rm where urs.user_id=rm.report_map_id and rm.user_id="+user_id
								+" ) and l.leave_month like '%"+year+"%')"
								+") u_out inner join employee_task_detail et "
								+"on u_out.user_id=et.user_id and u_out.leave_month_yr=et.task_date where et.status in('Half Day','Leave','Comp off')"
								+" order by u_out.user_name, et.task_date LIMIT 0, 50000";
		try {			
			Transaction tx = session.beginTransaction();
			complete_leave_list = session.createSQLQuery(queryToGetLeave).list();
			tx.commit();
		}
		catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new Exception();
		}finally{
			session.close();
		}
		return complete_leave_list;
	}
}
