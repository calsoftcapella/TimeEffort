package com.calsoft.report.dao;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import com.calsoft.factory.Factory;
import com.calsoft.report.model.Report;
import com.calsoft.report.model.ReportMapping;
import com.calsoft.task.model.Task;
import com.calsoft.user.dao.UserDao;
import com.calsoft.user.form.UserForm;
import com.calsoft.user.model.User;
import com.calsoft.util.HbnUtil;
import com.calsoft.util.TimeUtility;

@SuppressWarnings({"unchecked","rawtypes"})
public class HbnReportDAO implements ReportDAO {
	private static final Logger logger = Logger.getLogger("name");
	@Override
	public List<Report> getReportData(String year, String month,String[] allocatedResource) throws Exception{
		List<Report> reportList = new ArrayList<Report>();	
		if(allocatedResource!=null){
			for(int i=0;i<allocatedResource.length;i++){
				int userId=Integer.parseInt(allocatedResource[i]);
				//get the user name from user id
				UserDao userDao=Factory.getDao();
				User user=userDao.getUsernameFromId(userId);
				String userName=user.getUser_name();
				Session s1 = HbnUtil.getSession();
				Transaction tx = s1.beginTransaction();
				String sql="select u.user_name,SEC_TO_TIME(SUM(TIME_TO_SEC(time))) as time,et.task_date ,et.status  from users u inner join employee_task_detail et where u.user_id=et.user_id and et.task_date like '"
						+ year
						+ "-"
						+ month
						+ "%"
						+ "'"
						+ " and u.user_id='"+userId+"' group by et.task_date,u.user_id ";				
				Query query1 = s1.createSQLQuery(sql);
				Double totalTime = 0.0;
				List<String[]> queryList1 = query1.list();
				Report report1 = new Report();
				report1.setUserName(userName);
				Iterator<String[]> it = queryList1.iterator();
				List<String> totalSum = new ArrayList<String>();
				Calendar cal = null;
				while (it.hasNext()) {
					Object[] obj = (Object[]) it.next();
					String timeFromDb = obj[1].toString();
					Date date = (Date) obj[2];	
					cal = Calendar.getInstance();
					cal.setTime(date);
					String formated_date = new SimpleDateFormat("yyyy-MM-dd").format(date);		
					List<String> statusList = s1.createSQLQuery("select status from employee_task_detail where user_id="+userId+" and task_date='"+formated_date+"'").list();
					String timeDB = timeFromDb.substring(0, timeFromDb.lastIndexOf(':'));  // Getting Date from DB		
					totalSum.add(timeFromDb);
					String newTime1 = timeDB.replace(':', '.');
					String newTime2 = newTime1.substring(0, newTime1.length());										
					// Modification
					Double time=Double.parseDouble(newTime2);
					String status =(String)obj[3];
					String timeString = "";
					timeString = time.toString();
					String sqlNow = "SELECT * FROM leave_detail where leave_detail.leave_month='"+TimeUtility.getUrsMonth(date)+"' and leave_date="+TimeUtility.getUrsDay(date)+" and leave_detail.user_id="+userId+" ";
					List listDate = s1.createSQLQuery(sqlNow).list();
					if(!statusList.isEmpty()){
						if(!listDate.isEmpty() && statusList.contains("Leave")){
							timeString = time.toString()+" ";
						}
						else if(!listDate.isEmpty() && statusList.contains("Half Day")){
							timeString = time.toString()+"(H) ";
						}
						else if(!listDate.isEmpty() && statusList.contains("Comp off")){
							timeString = time.toString()+"   ";
						}				
						if(statusList.contains("Public holiday")){
							timeString = time.toString()+"  ";
						}	
					}
					report1.setStatus(status);
					if (cal.get(Calendar.DAY_OF_MONTH) == 1) {
						report1.setTime1(timeString);
					}
					else if (cal.get(Calendar.DAY_OF_MONTH) == 2) {
						report1.setTime2(timeString);
					}
					else if (cal.get(Calendar.DAY_OF_MONTH) == 3) {
						report1.setTime3(timeString);
					}
					else if (cal.get(Calendar.DAY_OF_MONTH) == 4) {
						report1.setTime4(timeString);
					}
					else if (cal.get(Calendar.DAY_OF_MONTH) == 5) {						
						report1.setTime5(timeString);
					}
					else if (cal.get(Calendar.DAY_OF_MONTH) == 6) {
						report1.setTime6(timeString);
					}
					else if (cal.get(Calendar.DAY_OF_MONTH) == 7) {
						report1.setTime7(timeString);
					}
					else if (cal.get(Calendar.DAY_OF_MONTH) == 8) {
						report1.setTime8(timeString);
					}
					else if (cal.get(Calendar.DAY_OF_MONTH) == 9) {
						report1.setTime9(timeString);
					}
					else if (cal.get(Calendar.DAY_OF_MONTH) == 10) {
						report1.setTime10(timeString);
					}
					else if (cal.get(Calendar.DAY_OF_MONTH) == 11) {
						report1.setTime11(timeString);
					}
					else if (cal.get(Calendar.DAY_OF_MONTH) == 12) {
						report1.setTime12(timeString);
					}
					else if (cal.get(Calendar.DAY_OF_MONTH) == 13) {
						report1.setTime13(timeString);
					}
					else if (cal.get(Calendar.DAY_OF_MONTH) == 14) {
						report1.setTime14(timeString);
					}
					else if (cal.get(Calendar.DAY_OF_MONTH) == 15) {
						report1.setTime15(timeString);
					}
					else if (cal.get(Calendar.DAY_OF_MONTH) == 16) {
						report1.setTime16(timeString);
					}
					else if (cal.get(Calendar.DAY_OF_MONTH) == 17) {
						report1.setTime17(timeString);
					}
					else if (cal.get(Calendar.DAY_OF_MONTH) == 18) {
						report1.setTime18(timeString);
					}
					else if (cal.get(Calendar.DAY_OF_MONTH) == 19) {
						report1.setTime19(timeString);
					}
					else if (cal.get(Calendar.DAY_OF_MONTH) == 20) {
						report1.setTime20(timeString);
					}
					else if (cal.get(Calendar.DAY_OF_MONTH) == 21) {
						report1.setTime21(timeString);
					}
					else if (cal.get(Calendar.DAY_OF_MONTH) == 22) {
						report1.setTime22(timeString);
					}
					else if (cal.get(Calendar.DAY_OF_MONTH) == 23) {
						report1.setTime23(timeString);
					}
					else if (cal.get(Calendar.DAY_OF_MONTH) == 24) {
						report1.setTime24(timeString);
					}
					else if (cal.get(Calendar.DAY_OF_MONTH) == 25) {
						report1.setTime25(timeString);
					}
					else if (cal.get(Calendar.DAY_OF_MONTH) == 26) {
						report1.setTime26(timeString);
					}
					else if (cal.get(Calendar.DAY_OF_MONTH) == 27) {
						report1.setTime27(timeString);
					}
					else if (cal.get(Calendar.DAY_OF_MONTH) == 28) {
						report1.setTime28(timeString);
					}
					else if (cal.get(Calendar.DAY_OF_MONTH) == 29) {
						report1.setTime29(timeString);
					}
					else if (cal.get(Calendar.DAY_OF_MONTH) == 30) {
						report1.setTime30(timeString);
					}
					else if (cal.get(Calendar.DAY_OF_MONTH) == 31) {
						report1.setTime31(timeString);
					}
				}
				totalTime = TimeUtility.getYourTime(totalSum);
				if(totalSum!=null && !totalSum.isEmpty()&&totalTime>0){
					report1.setTotalTime(totalTime);
				}
				reportList.add(report1);
				tx.commit();
				s1.close();
			}
		}
		return reportList;
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
		tx.commit();
		s1.close();
		combinedList.add(allocatedList);
		combinedList.add(unallocatedList);
		return combinedList;
	}

	@Override
	public void addResources(String[] allocatedResource,int userId) throws Exception{
		Session session = HbnUtil.getSession();
		Transaction tx = session.beginTransaction();
		if(allocatedResource!=null){
			for(int i=0;i<allocatedResource.length;i++){
				ReportMapping reportmapping=new ReportMapping();
				int mapId=Integer.parseInt(allocatedResource[i]);
				reportmapping.setUserId(userId);
				reportmapping.setReportMapId(mapId);
				session.save(reportmapping);
			}
		}
		tx.commit();
		session.close();
	}

	@Override
	public void deleteResources(String[] allocatedResource, int userId)throws Exception {
		Session session = HbnUtil.getSession();
		Transaction tx = session.beginTransaction();
		if(allocatedResource!=null){
			for(int i=0;i<allocatedResource.length;i++){
				int mapId=Integer.parseInt(allocatedResource[i]);
				String hql = "delete from ReportMapping rm where rm.userId = " + userId+" and rm.reportMapId="+mapId;
				Query query = session.createQuery(hql);
				query.executeUpdate();
			}
		}
		tx.commit();
		session.close();
	}

	@Override
	public List<List<Report>> getUserReportAllocation() throws Exception {
		List<Report> allocatedList=null;
		List<List<Report>> combinedList=new ArrayList<List<Report>>();
		UserDao	dao=Factory.getDao();
		List<User> userList=dao.getUser();
		Iterator<User> userIterator=userList.iterator();
		while(userIterator.hasNext()){
			User user=userIterator.next();
			String userNameValue=user.getUser_name();
			int userId=user.getUser_Id();
			allocatedList=new ArrayList<Report>();
			String allocatedQueryString="select user_name,user_id  from users where user_id  in( select u.user_id from users u inner join report_mapping rm on u.user_id=rm.report_map_id and rm.user_id="+userId+")"; 
			Session s1 = HbnUtil.getSession();
			Transaction tx = s1.beginTransaction();
			Query allocatedQuery = s1.createSQLQuery(allocatedQueryString);
			Iterator it1=allocatedQuery.list().iterator();
			int count=1;
			while (it1.hasNext()) {
				Report report=new Report();
				Object[] obj = (Object[]) it1.next();
				String userName=(String)obj[0];
				Integer  userIdValue=(Integer)obj[1];
				int userIdValue1=userIdValue.intValue();
				report.setUserName(userName);
				if(count==1){
					report.setParentUserName(userNameValue);
				}
				report.setUserId(userIdValue1);
				allocatedList.add(report);
				count++;
			}
			combinedList.add(allocatedList);
			tx.commit();
			s1.close();
		}
		return combinedList;
	}

	@Override
	public List<Report> getReportDataTeamWise(String year, String month, String[] detailedTeam, Calendar cal_month, int user_id_from_session) throws Exception {
		List<Report> reportList = new ArrayList();
		DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
		cal_month.set(Calendar.DAY_OF_MONTH, cal_month.getActualMinimum(Calendar.DAY_OF_MONTH));
		String month_start_date = df1.format(cal_month.getTime());
		cal_month.set(Calendar.DAY_OF_MONTH, cal_month.getActualMaximum(Calendar.DAY_OF_MONTH));
		String month_last_date = df1.format(cal_month.getTime());
		Session s1 = HbnUtil.getSession();
		Transaction tx = s1.beginTransaction();
		String query_string = "select u2.user_id, u2.user_name from users u2 inner join contact con on u2.user_id = con.user_id where u2.user_id in"
				+"(select u.user_id from users u inner join report_mapping rm on u.user_id=rm.report_map_id where rm.user_id="+user_id_from_session+" and("
				+"(u.start_date is NULL and u.exit_date is NULL)||(u.start_date is NULL && u.exit_date >= '"+month_start_date+"')"
				+"||(u.start_date <= '"+month_last_date+"' && u.exit_date is NULL) || (u.start_date <= '"+month_last_date+"' && u.exit_date >= '"+month_start_date+"')))"
				+" and con.team =:resource_team group by u2.user_id order by u2.user_name";

		if(detailedTeam != null){
			// Iterating Team for getting all resource team wise.
			for(int i=0;i<detailedTeam.length;i++){				
				List<Object[]> user_list = s1.createSQLQuery(query_string).setParameter("resource_team", detailedTeam[i]).list();
				Iterator<Object[]> itr = user_list.iterator();
				// Getting Resource entry for reporting.
				while(itr.hasNext()){
					Object[] userId_detail = (Object[]) itr.next();				
					int user_id = (Integer) userId_detail[0];
					String userName =  (String) userId_detail[1];
					String sql="select u.user_name,SEC_TO_TIME(SUM(TIME_TO_SEC(time))) as time,et.task_date ,et.status  from users u inner  join employee_task_detail et"
							+" where u.user_id=et.user_id and et.task_date like '"+year+"-"+ month+"%' and u.user_id='"+user_id+"' group by et.task_date,u.user_id ";
					Query query1 = s1.createSQLQuery(sql);
					Double totalTime = 0.0;
					List<String[]> queryList1 = query1.list();
					Report report1 = new Report();
					report1.setUserName(userName);
					Iterator<String[]> it = queryList1.iterator();
					List<String> totalSum = new ArrayList<String>();
					Calendar cal = null;
					// Getting report data into Report Object.
					while (it.hasNext()) {
						Object[] obj = (Object[]) it.next();
						String timeFromDb = obj[1].toString();
						Date date = (Date) obj[2];
						cal = Calendar.getInstance();
						cal.setTime(date);
						String formated_date = new SimpleDateFormat("yyyy-MM-dd").format(date);							
						List<String> statusList = s1.createSQLQuery("select status from employee_task_detail where user_id="+user_id+" and task_date='"+formated_date+"'").list();
						String timeDB = timeFromDb.substring(0, timeFromDb.lastIndexOf(':'));  // Getting Date from DB														
						totalSum.add(timeFromDb);							
						String newTime1 = timeDB.replace(':', '.');
						String newTime2 = newTime1.substring(0, newTime1.length());					
						Double time=Double.parseDouble(newTime2);
						String status =(String)obj[3];
						String timeString = "";
						timeString = time.toString();
						String sqlNow = "SELECT * FROM leave_detail where leave_detail.leave_month='"+TimeUtility.getUrsMonth(date)+"' and leave_date="+TimeUtility.getUrsDay(date)+" and leave_detail.user_id="+user_id+" ";
						List listDate = s1.createSQLQuery(sqlNow).list();
						if(!statusList.isEmpty()){
							if(!listDate.isEmpty() && statusList.contains("Leave")){
								timeString = time.toString()+" ";
							}
							else if(!listDate.isEmpty() && statusList.contains("Half Day")){
								timeString = time.toString()+"(H) ";
							}
							else if(!listDate.isEmpty() && statusList.contains("Comp off")){
								timeString = time.toString()+"   ";
							}				
							if(statusList.contains("Public holiday")){
								timeString = time.toString()+"  ";
							}	
						}
						report1.setStatus(status);
						if (cal.get(Calendar.DAY_OF_MONTH) == 1) {
							report1.setTime1(timeString);
						}
						else if (cal.get(Calendar.DAY_OF_MONTH) == 2) {
							report1.setTime2(timeString);
						}
						else if (cal.get(Calendar.DAY_OF_MONTH) == 3) {
							report1.setTime3(timeString);
						}
						else if (cal.get(Calendar.DAY_OF_MONTH) == 4) {
							report1.setTime4(timeString);
						}
						else if (cal.get(Calendar.DAY_OF_MONTH) == 5) {						
							report1.setTime5(timeString);
						}
						else if (cal.get(Calendar.DAY_OF_MONTH) == 6) {
							report1.setTime6(timeString);
						}
						else if (cal.get(Calendar.DAY_OF_MONTH) == 7) {
							report1.setTime7(timeString);
						}
						else if (cal.get(Calendar.DAY_OF_MONTH) == 8) {
							report1.setTime8(timeString);
						}
						else if (cal.get(Calendar.DAY_OF_MONTH) == 9) {
							report1.setTime9(timeString);
						}
						else if (cal.get(Calendar.DAY_OF_MONTH) == 10) {
							report1.setTime10(timeString);
						}
						else if (cal.get(Calendar.DAY_OF_MONTH) == 11) {
							report1.setTime11(timeString);
						}
						else if (cal.get(Calendar.DAY_OF_MONTH) == 12) {
							report1.setTime12(timeString);
						}
						else if (cal.get(Calendar.DAY_OF_MONTH) == 13) {
							report1.setTime13(timeString);
						}
						else if (cal.get(Calendar.DAY_OF_MONTH) == 14) {
							report1.setTime14(timeString);
						}
						else if (cal.get(Calendar.DAY_OF_MONTH) == 15) {
							report1.setTime15(timeString);
						}
						else if (cal.get(Calendar.DAY_OF_MONTH) == 16) {
							report1.setTime16(timeString);
						}
						else if (cal.get(Calendar.DAY_OF_MONTH) == 17) {
							report1.setTime17(timeString);
						}
						else if (cal.get(Calendar.DAY_OF_MONTH) == 18) {
							report1.setTime18(timeString);
						}
						else if (cal.get(Calendar.DAY_OF_MONTH) == 19) {
							report1.setTime19(timeString);
						}
						else if (cal.get(Calendar.DAY_OF_MONTH) == 20) {
							report1.setTime20(timeString);
						}
						else if (cal.get(Calendar.DAY_OF_MONTH) == 21) {
							report1.setTime21(timeString);
						}
						else if (cal.get(Calendar.DAY_OF_MONTH) == 22) {
							report1.setTime22(timeString);
						}
						else if (cal.get(Calendar.DAY_OF_MONTH) == 23) {
							report1.setTime23(timeString);
						}
						else if (cal.get(Calendar.DAY_OF_MONTH) == 24) {
							report1.setTime24(timeString);
						}
						else if (cal.get(Calendar.DAY_OF_MONTH) == 25) {
							report1.setTime25(timeString);
						}
						else if (cal.get(Calendar.DAY_OF_MONTH) == 26) {
							report1.setTime26(timeString);
						}
						else if (cal.get(Calendar.DAY_OF_MONTH) == 27) {
							report1.setTime27(timeString);
						}
						else if (cal.get(Calendar.DAY_OF_MONTH) == 28) {
							report1.setTime28(timeString);
						}
						else if (cal.get(Calendar.DAY_OF_MONTH) == 29) {
							report1.setTime29(timeString);
						}
						else if (cal.get(Calendar.DAY_OF_MONTH) == 30) {
							report1.setTime30(timeString);
						}
						else if (cal.get(Calendar.DAY_OF_MONTH) == 31) {
							report1.setTime31(timeString);
						}
					}	// while loop::	Getting report data into Report Object.
					totalTime = TimeUtility.getYourTime(totalSum);
					if(totalSum!=null && !totalSum.isEmpty()&&totalTime>0){
						report1.setTotalTime(totalTime);
					}
					reportList.add(report1);
				}	//	while loop::	Getting Resource entry for reporting.
			}		// Closing for FOR LOOP:	Iterating Team for getting all resource team wise.	
		}			// Closing for IF BLOCK: after checking Teamdeatils Object for NULL.
		tx.commit();
		s1.close();
		return reportList;
	}

	@Override
	public List<Report> getExceptionDashboard(String year, String month, List<User> allocatedUserList) throws Exception {
		List<Report> reportList = new ArrayList();	
		if(allocatedUserList!=null)
		{
			Session s1 = HbnUtil.getSession();
			Iterator<User> it  = allocatedUserList.iterator();
			while (it.hasNext()) {
				User us = (User) it.next();
				//User user=itr.next();
				int userId=us.getUser_Id();
				//System.out.println("selected user id is:"+userId);
				//get the user name from user id
				UserDao userDao=Factory.getDao();
				User user=userDao.getUsernameFromId(userId);
				String userName=user.getUser_name();
				Transaction tx = s1.beginTransaction();
				String sql="select u.user_name,SEC_TO_TIME(SUM(TIME_TO_SEC(time))) as time,et.task_date ,et.status  from users u inner  join employee_task_detail et where u.user_id=et.user_id and et.task_date like '"
						+ year
						+ "-"
						+ month
						+ "%"
						+ "'"
						+ " and u.user_id='"+userId+"' group by et.task_date,u.user_id ";
				Query query1 = s1.createSQLQuery(sql);
				List<Object[]> list = query1.list();
				Report r1 = new Report();
				boolean myChecker = false;
				if(list.isEmpty()){
					r1.setUserName(userName);
					reportList.add(r1);       		// Adding Resource not having a Single entry
				}else if(!list.isEmpty()){
					for (Object[] obj : list) {
						String timeDB = obj[1].toString().substring(0, obj[1].toString().lastIndexOf(':'));  // Getting Date from DB
						String newTime1 = timeDB.replace(':', '.');
						String newTime2 = newTime1.substring(0, newTime1.length());					
						Double timeCheck=Double.parseDouble(newTime2);
						if(timeCheck<8){
							myChecker = true;
							break;
						}
					}
					if(myChecker){
						r1.setUserName(userName);
						Iterator<Object[]> myItr = list.iterator();
						Calendar cal = null;
						while (myItr.hasNext()) {
							Object[] listObj = (Object[]) myItr.next();
							Date date = (Date) listObj[2];
							cal = Calendar.getInstance();
							cal.setTime(date);
							String timeDb = listObj[1].toString().substring(0, listObj[1].toString().lastIndexOf(':'));  // Getting Date from DB
							String new_Time1 = timeDb.replace(':', '.');
							String new_Time2 = new_Time1.substring(0, new_Time1.length());					
							Double time=Double.parseDouble(new_Time2);
							if(time<8){
								String timeString = null;
								timeString = time.toString();
								if (cal.get(Calendar.DAY_OF_MONTH) == 1) {
									r1.setTime1(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 2) {
									r1.setTime2(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 3) {
									r1.setTime3(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 4) {
									r1.setTime4(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 5) {						
									r1.setTime5(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 6) {
									r1.setTime6(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 7) {
									r1.setTime7(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 8) {
									r1.setTime8(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 9) {
									r1.setTime9(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 10) {
									r1.setTime10(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 11) {
									r1.setTime11(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 12) {
									r1.setTime12(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 13) {
									r1.setTime13(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 14) {
									r1.setTime14(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 15) {
									r1.setTime15(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 16) {
									r1.setTime16(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 17) {
									r1.setTime17(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 18) {
									r1.setTime18(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 19) {
									r1.setTime19(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 20) {
									r1.setTime20(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 21) {
									r1.setTime21(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 22) {
									r1.setTime22(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 23) {
									r1.setTime23(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 24) {
									r1.setTime24(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 25) {
									r1.setTime25(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 26) {
									r1.setTime26(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 27) {
									r1.setTime27(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 28) {
									r1.setTime28(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 29) {
									r1.setTime29(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 30) {
									r1.setTime30(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 31) {
									r1.setTime31(timeString);
								}		
							} // Closing for if time<8						
							else{
								String timeString = "k";
								if (cal.get(Calendar.DAY_OF_MONTH) == 1) {
									r1.setTime1(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 2) {
									r1.setTime2(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 3) {
									r1.setTime3(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 4) {
									r1.setTime4(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 5) {						
									r1.setTime5(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 6) {
									r1.setTime6(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 7) {
									r1.setTime7(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 8) {
									r1.setTime8(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 9) {
									r1.setTime9(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 10) {
									r1.setTime10(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 11) {
									r1.setTime11(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 12) {
									r1.setTime12(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 13) {
									r1.setTime13(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 14) {
									r1.setTime14(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 15) {
									r1.setTime15(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 16) {
									r1.setTime16(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 17) {
									r1.setTime17(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 18) {
									r1.setTime18(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 19) {
									r1.setTime19(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 20) {
									r1.setTime20(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 21) {
									r1.setTime21(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 22) {
									r1.setTime22(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 23) {
									r1.setTime23(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 24) {
									r1.setTime24(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 25) {
									r1.setTime25(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 26) {
									r1.setTime26(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 27) {
									r1.setTime27(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 28) {
									r1.setTime28(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 29) {
									r1.setTime29(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 30) {
									r1.setTime30(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 31) {
									r1.setTime31(timeString);
								}	
							} // Closing for else

						}	// Closing for While
						reportList.add(r1);
					}
				}
				tx.commit();
			}
			s1.close();
		}
		return reportList;
	}
	@Override
	public List<List<Report>> getReportDataForReminderMail(String monthYear, List<UserForm> allocatedUserList) throws Exception {	
		List<List<Report>> listForReminderMailDetail = new ArrayList<List<Report>>();
		Session s1 = HbnUtil.getSession();
		Transaction tx = s1.beginTransaction();
		DateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		List<String> timeEntryStatusList = new ArrayList<String>();
		List<String> allListDatesForMonth = TimeUtility.getAllWorkingDatesForCurrentMonthLastWorkingDateInList(Calendar.getInstance(), null);
		List<String> allListDatesForMonth1 = null;
		String queryToGetAllMappedResourceUnderManager = "select u2.user_id from users u2 inner join user_role ur on u2.user_id = ur.user_id where ur.user_id in"
				+"(select u.user_id from users u inner join report_mapping rm where u.user_id=rm.report_map_id and rm.user_id=:user_id) and (ur.role_id in"
				+"(1002,1004) and (u2.exit_date >= CURDATE() || u2.exit_date is NUll)) and u2.status='Active' order by u2.user_name";

		String sqlForTimeEntryStatus = "select DATE_FORMAT(t.task_date, '%Y-%m-%d')as task_date from employee_task_detail t where " +
				"user_id = :user_id and t.task_date like '%"+monthYear+"%' group by t.task_date order by t.task_date";

		String sqlForTimeEntryDetail = "select SEC_TO_TIME(SUM(TIME_TO_SEC(time))) as time, et.task_date from users u inner  join employee_task_detail et "
				+"where u.user_id=et.user_id and et.task_date like '%"+monthYear+"%' and u.user_id=:user_id group by et.task_date,u.user_id ";
		
		String queryToGetDayEntryStatus = "select status from employee_task_detail where user_id=:user_id and task_date=:task_entry_date";

		String leaveStatusQuery = "SELECT * FROM leave_detail where leave_detail.leave_month=:leave_month_info and leave_date=:leave_date_info and leave_detail.user_id=:user_id_info";
		
		for (UserForm u_form : allocatedUserList) {
			int user_id = u_form.getUserId();
			String calsoftId = u_form.getMail();
			String apolloId = u_form.getApollo_id();
			String username = u_form.getUserName();
			Date resource_joining_dt = u_form.getStart_date();
			if(resource_joining_dt != null && compareResourceJoingDateWithCurrentMonth(resource_joining_dt)){
				allListDatesForMonth1 = TimeUtility.getAllWorkingDatesForCurrentMonthLastWorkingDateInList(Calendar.getInstance(), resource_joining_dt);
			}else{
				allListDatesForMonth1 = allListDatesForMonth;
			}
			int user_role_id = u_form.getUser_role_id();
			List<Report> reportData = new ArrayList<Report>();
			// Excluding Special User A/C with role_id 1002 .*/
			if(user_role_id == 1002 || user_role_id == 1005){
				// If Manager is not assigned then proceed.
				timeEntryStatusList = s1.createSQLQuery(sqlForTimeEntryStatus).setParameter("user_id", user_id).list();
				if(timeEntryStatusList.isEmpty() || (!timeEntryStatusList.isEmpty() && !timeEntryStatusList.containsAll(allListDatesForMonth1))){
					Query query1 = s1.createSQLQuery(sqlForTimeEntryDetail).setParameter("user_id", user_id);
					Double totalTime = 0.0;
					List<String[]> queryList1 = query1.list();
					Report report1 = new Report();
					report1.setUserId(user_id);
					report1.setUserName(username);
					report1.setEmailId(calsoftId);
					report1.setApolloId(apolloId);
					Iterator<String[]> it = queryList1.iterator();
					List<String> totalSum = new ArrayList<String>();
					Calendar cal = null;
					List<String> statusList = null;
					while (it.hasNext()){
						Object[] obj = (Object[]) it.next();
						Date date = (Date) obj[1];	
						cal = Calendar.getInstance();
						cal.setTime(date);
						String formated_date = myFormat.format(date);
						statusList = s1.createSQLQuery(queryToGetDayEntryStatus).setParameter("user_id", user_id).setParameter("task_entry_date", formated_date).list();
						// Modification
						String timeDB = obj[0].toString().substring(0, obj[0].toString().lastIndexOf(':'));  // Getting Time from DB
						totalSum.add(obj[0].toString());
						String newTime1 = timeDB.replace(':', '.');
						String newTime2 = newTime1.substring(0, newTime1.length());					
						// Modification
						Double time=Double.parseDouble(newTime2);
						String timeString = null;
						timeString = time.toString();					
						List listDate = s1.createSQLQuery(leaveStatusQuery).setParameter("leave_month_info", TimeUtility.getUrsMonth(date)).
								setParameter("leave_date_info", TimeUtility.getUrsDay(date)).setParameter("user_id_info", user_id).list();
						if(statusList!=null && !statusList.isEmpty()){
							if(!listDate.isEmpty() && statusList.contains("Leave")){
								timeString = time.toString()+" ";
							}
							else if(!listDate.isEmpty() && statusList.contains("Half Day")){
								timeString = time.toString()+"(H) ";
							}
							else if(!listDate.isEmpty() && statusList.contains("Comp off")){
								timeString = time.toString()+"(C)";
							}				
							if(statusList.contains("Public holiday")){
								timeString = time.toString()+"  ";
							}	
						}
						if (cal.get(Calendar.DAY_OF_MONTH) == 1) {
							report1.setTime1(timeString);
						}
						else if (cal.get(Calendar.DAY_OF_MONTH) == 2) {
							report1.setTime2(timeString);
						}
						else if (cal.get(Calendar.DAY_OF_MONTH) == 3) {
							report1.setTime3(timeString);
						}
						else if (cal.get(Calendar.DAY_OF_MONTH) == 4) {
							report1.setTime4(timeString);
						}
						else if (cal.get(Calendar.DAY_OF_MONTH) == 5) {						
							report1.setTime5(timeString);
						}
						else if (cal.get(Calendar.DAY_OF_MONTH) == 6) {
							report1.setTime6(timeString);
						}
						else if (cal.get(Calendar.DAY_OF_MONTH) == 7) {
							report1.setTime7(timeString);
						}
						else if (cal.get(Calendar.DAY_OF_MONTH) == 8) {
							report1.setTime8(timeString);
						}
						else if (cal.get(Calendar.DAY_OF_MONTH) == 9) {
							report1.setTime9(timeString);
						}
						else if (cal.get(Calendar.DAY_OF_MONTH) == 10) {
							report1.setTime10(timeString);
						}
						else if (cal.get(Calendar.DAY_OF_MONTH) == 11) {
							report1.setTime11(timeString);
						}
						else if (cal.get(Calendar.DAY_OF_MONTH) == 12) {
							report1.setTime12(timeString);
						}
						else if (cal.get(Calendar.DAY_OF_MONTH) == 13) {
							report1.setTime13(timeString);
						}
						else if (cal.get(Calendar.DAY_OF_MONTH) == 14) {
							report1.setTime14(timeString);
						}
						else if (cal.get(Calendar.DAY_OF_MONTH) == 15) {
							report1.setTime15(timeString);
						}
						else if (cal.get(Calendar.DAY_OF_MONTH) == 16) {
							report1.setTime16(timeString);
						}
						else if (cal.get(Calendar.DAY_OF_MONTH) == 17) {
							report1.setTime17(timeString);
						}
						else if (cal.get(Calendar.DAY_OF_MONTH) == 18) {
							report1.setTime18(timeString);
						}
						else if (cal.get(Calendar.DAY_OF_MONTH) == 19) {
							report1.setTime19(timeString);
						}
						else if (cal.get(Calendar.DAY_OF_MONTH) == 20) {
							report1.setTime20(timeString);
						}
						else if (cal.get(Calendar.DAY_OF_MONTH) == 21) {
							report1.setTime21(timeString);
						}
						else if (cal.get(Calendar.DAY_OF_MONTH) == 22) {
							report1.setTime22(timeString);
						}
						else if (cal.get(Calendar.DAY_OF_MONTH) == 23) {
							report1.setTime23(timeString);
						}
						else if (cal.get(Calendar.DAY_OF_MONTH) == 24) {
							report1.setTime24(timeString);
						}
						else if (cal.get(Calendar.DAY_OF_MONTH) == 25) {
							report1.setTime25(timeString);
						}
						else if (cal.get(Calendar.DAY_OF_MONTH) == 26) {
							report1.setTime26(timeString);
						}
						else if (cal.get(Calendar.DAY_OF_MONTH) == 27) {
							report1.setTime27(timeString);
						}
						else if (cal.get(Calendar.DAY_OF_MONTH) == 28) {
							report1.setTime28(timeString);
						}
						else if (cal.get(Calendar.DAY_OF_MONTH) == 29) {
							report1.setTime29(timeString);
						}
						else if (cal.get(Calendar.DAY_OF_MONTH) == 30) {
							report1.setTime30(timeString);
						}
						else if (cal.get(Calendar.DAY_OF_MONTH) == 31) {
							report1.setTime31(timeString);
						}
					}  // Closing for inner while loop
					totalTime = TimeUtility.getYourTime(totalSum);
					if(totalSum!=null && !totalSum.isEmpty()&&totalTime>0){
						report1.setTotalTime(totalTime);
					}
					else{
						report1.setTotalTime(0.0);
					}
					reportData.add(report1);
					listForReminderMailDetail.add(reportData);
				}	// Close for timesheet entry status .	
			}else if(user_role_id == 1004){
				// Get all maaped resource under manager.
				List<Integer> resourceListUnderManager = s1.createSQLQuery(queryToGetAllMappedResourceUnderManager).setParameter("user_id", user_id).list();
				if(resourceListUnderManager != null && !resourceListUnderManager.isEmpty()){
					for (Integer resource_id : resourceListUnderManager) {
						User resource_obj = (User) s1.load(User.class, resource_id);
						String resource_name = resource_obj.getUser_name();
						String resource_calsoftId = resource_obj.getMail();
						String resource_apolloId = resource_obj.getApollo_id();
						timeEntryStatusList = s1.createSQLQuery(sqlForTimeEntryStatus).setParameter("user_id", resource_id).list();	
						if(timeEntryStatusList.isEmpty() || (!timeEntryStatusList.isEmpty() && !timeEntryStatusList.containsAll(allListDatesForMonth1))){
							Query query1 = s1.createSQLQuery(sqlForTimeEntryDetail).setParameter("user_id", resource_id);
							Double totalTime = 0.0;
							List<String[]> queryList1 = query1.list();
							Report report1 = new Report();							
							report1.setUserId(resource_id);
							report1.setUserName(resource_name);
							report1.setEmailId(resource_calsoftId);
							report1.setApolloId(resource_apolloId);
							Iterator<String[]> it = queryList1.iterator();
							List<String> totalSum = new ArrayList<String>();
							Calendar cal = null;
							List<String> statusList = null;
							while (it.hasNext()){
								Object[] obj = (Object[]) it.next();
								Date date = (Date) obj[1];	
								cal = Calendar.getInstance();
								cal.setTime(date);
								String formated_date = myFormat.format(date);
								statusList = s1.createSQLQuery(queryToGetDayEntryStatus).setParameter("user_id", resource_id).setParameter("task_entry_date", formated_date).list();
								// Modification
								String timeDB = obj[0].toString().substring(0, obj[0].toString().lastIndexOf(':'));  // Getting Time from DB
								totalSum.add(obj[0].toString());
								String newTime1 = timeDB.replace(':', '.');
								String newTime2 = newTime1.substring(0, newTime1.length());					
								// Modification
								Double time=Double.parseDouble(newTime2);
								String timeString = null;
								timeString = time.toString();
								List listDate = s1.createSQLQuery(leaveStatusQuery).setParameter("leave_month_info", TimeUtility.getUrsMonth(date)).
										setParameter("leave_date_info", TimeUtility.getUrsDay(date)).setParameter("user_id_info", resource_id).list();
								if(statusList!=null && !statusList.isEmpty()){
									if(!listDate.isEmpty() && statusList.contains("Leave")){
										timeString = time.toString()+" ";
									}
									else if(!listDate.isEmpty() && statusList.contains("Half Day")){
										timeString = time.toString()+"(H) ";
									}
									else if(!listDate.isEmpty() && statusList.contains("Comp off")){
										timeString = time.toString()+"(C)";
									}				
									if(statusList.contains("Public holiday")){
										timeString = time.toString()+"  ";
									}	
								}
								if (cal.get(Calendar.DAY_OF_MONTH) == 1) {
									report1.setTime1(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 2) {
									report1.setTime2(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 3) {
									report1.setTime3(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 4) {
									report1.setTime4(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 5) {						
									report1.setTime5(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 6) {
									report1.setTime6(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 7) {
									report1.setTime7(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 8) {
									report1.setTime8(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 9) {
									report1.setTime9(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 10) {
									report1.setTime10(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 11) {
									report1.setTime11(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 12) {
									report1.setTime12(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 13) {
									report1.setTime13(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 14) {
									report1.setTime14(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 15) {
									report1.setTime15(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 16) {
									report1.setTime16(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 17) {
									report1.setTime17(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 18) {
									report1.setTime18(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 19) {
									report1.setTime19(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 20) {
									report1.setTime20(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 21) {
									report1.setTime21(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 22) {
									report1.setTime22(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 23) {
									report1.setTime23(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 24) {
									report1.setTime24(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 25) {
									report1.setTime25(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 26) {
									report1.setTime26(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 27) {
									report1.setTime27(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 28) {
									report1.setTime28(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 29) {
									report1.setTime29(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 30) {
									report1.setTime30(timeString);
								}
								else if (cal.get(Calendar.DAY_OF_MONTH) == 31) {
									report1.setTime31(timeString);
								}
							}  // Closing for inner while loop
							totalTime = TimeUtility.getYourTime(totalSum);
							if(totalSum!=null && !totalSum.isEmpty()&&totalTime>0){
								report1.setTotalTime(totalTime);
							}
							else{
								report1.setTotalTime(0.0);
							}
							reportData.add(report1);
						} // Closing for IF block :: timeentry status for resource.
					}	// Closing for FOR LOOP :: for iterating resources.
					if(!reportData.isEmpty())
						listForReminderMailDetail.add(reportData);
				} // Closing for IF BLOCK :: after checking ListForManagerResource.
			}	// Closing for ELSE IF BLOCK :: for Manager role 
		}	// Closing for FOR LOOP :: For iterating resource Mapped special user.
		tx.commit();
		s1.close();
		return listForReminderMailDetail;
	}
	private static boolean  compareResourceJoingDateWithCurrentMonth(Date dt){
		boolean matchStatus = false;
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c2.setTime(dt);
		if(c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)){
			matchStatus = true;
		}
		return matchStatus;
	}	
	@Override
	public List<Report> getDefaulterListDetails(String[] allocatedResource, Calendar cal) throws Exception {
		// Getting defaulter list details for previous week
		List<Report> defaulterList = new ArrayList<Report>();
		Session s1 = HbnUtil.getSession();
		Transaction tx = s1.beginTransaction();
		DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat df2 = new SimpleDateFormat("dd");
		DateFormat df3 = new SimpleDateFormat("dd/MM/yyyy");
		DateFormat df4 = new SimpleDateFormat("yyyy-MM");	
		try{		
			Query q1 = s1.createSQLQuery("select t.task_date from employee_task_detail t where user_id=:user_id and t.task_date like '%"+df4.format(cal.getTime())+"%'");
			for (String userId : allocatedResource) {
				String single_date = "";
				List<java.sql.Date> listForWholeMonthEntry = q1.setParameter("user_id", userId).list();
				User userInfo = (User) s1.get(User.class, Integer.parseInt(userId));
				Date start_date =  userInfo.getStart_date();
				Date end_date = userInfo.getExit_date();
				List<String> getAllWorkingDateBeforeDay = new ArrayList<String>();
				if(start_date != null && end_date != null){
					Calendar start_dt_cal = Calendar.getInstance();
					start_dt_cal.setTime(start_date);					
					Calendar end_dt_cal = Calendar.getInstance();
					end_dt_cal.setTime(end_date);					
					if((start_dt_cal.get(Calendar.MONTH) == cal.get(Calendar.MONTH)) && (start_dt_cal.get(Calendar.YEAR) == cal.get(Calendar.YEAR)) && (end_dt_cal.get(Calendar.MONTH) == cal.get(Calendar.MONTH)) && (end_dt_cal.get(Calendar.YEAR) == cal.get(Calendar.YEAR))){						
						getAllWorkingDateBeforeDay = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDate(start_dt_cal, end_dt_cal, cal);						
					}else if((start_dt_cal.get(Calendar.MONTH) == cal.get(Calendar.MONTH)) && (start_dt_cal.get(Calendar.YEAR) == cal.get(Calendar.YEAR)) && !(end_dt_cal.get(Calendar.MONTH) == cal.get(Calendar.MONTH)) && (end_dt_cal.get(Calendar.YEAR) == cal.get(Calendar.YEAR))){
						getAllWorkingDateBeforeDay = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDate(start_dt_cal, null, cal);	
					}else if(!(start_dt_cal.get(Calendar.MONTH) == cal.get(Calendar.MONTH)) && (start_dt_cal.get(Calendar.YEAR) == cal.get(Calendar.YEAR)) && (end_dt_cal.get(Calendar.MONTH) == cal.get(Calendar.MONTH)) && (end_dt_cal.get(Calendar.YEAR) == cal.get(Calendar.YEAR))){
						getAllWorkingDateBeforeDay = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDate(null, end_dt_cal, cal);						
					}
				}else if(start_date != null && end_date == null){
					Calendar start_dt_cal = Calendar.getInstance();
					start_dt_cal.setTime(start_date);

					if((start_dt_cal.get(Calendar.MONTH) == cal.get(Calendar.MONTH)) && (start_dt_cal.get(Calendar.YEAR) == cal.get(Calendar.YEAR))){						
						getAllWorkingDateBeforeDay = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDate(start_dt_cal, null, cal);						
					}else{
						getAllWorkingDateBeforeDay = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDate(null, null, cal);	
					}
				}else if(start_date == null && end_date != null){	
					Calendar end_dt_cal = Calendar.getInstance();
					end_dt_cal.setTime(end_date);
					if((end_dt_cal.get(Calendar.MONTH) == cal.get(Calendar.MONTH)) && (end_dt_cal.get(Calendar.YEAR) == cal.get(Calendar.YEAR))){						
						getAllWorkingDateBeforeDay = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDate(null, end_dt_cal, cal);						
					}else{
						getAllWorkingDateBeforeDay = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDate(null, null, cal);	
					}
				}else if(start_date == null && end_date == null){					
					getAllWorkingDateBeforeDay = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDate(null, null, cal);	
				}
				if(listForWholeMonthEntry.isEmpty()){
					single_date = "No entry found.";
				}
				else{
					java.sql.Date dt = null;
					for (String date : getAllWorkingDateBeforeDay) {	
						dt = new java.sql.Date(df1.parse(date).getTime());
						if(!listForWholeMonthEntry.contains(dt)){
							String formatted_date = "";
							formatted_date = df2.format(df1.parse(date));					
							if(single_date == ""){
								single_date = single_date+""+formatted_date;
							}
							else{
								single_date = single_date+", "+formatted_date;
							}
						}
					}
				}
				if(single_date!=""){					
					Report r1 = new Report();
					if(!single_date.equalsIgnoreCase("No entry found.")){
						single_date = TimeUtility.getFormattedDateWithRange(single_date, df3.format(cal.getTime()));
					}						
					r1.setPeriod(single_date);
					r1.setUserName(userInfo.getUser_name());
					defaulterList.add(r1);
				}
			}	
			tx.commit();
			s1.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
		return defaulterList;
	}

	@Override
	public List<Report> getResourceDetailWhoMissedEntry(List<User> allocatedUserList, int userId_for_accountManager, int userId_for_admin) throws Exception {
		// For sending missing time entry mail for current month.
		List<Report> defaulterListForMailing = new ArrayList<Report>();
		Session s1 = HbnUtil.getSession();
		Transaction tx = s1.beginTransaction();
		DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat df2 = new SimpleDateFormat("dd");
		DateFormat df3 = new SimpleDateFormat("MMMM d, yyyy");
		DateFormat df4 = new SimpleDateFormat("dd/MM/yyyy");
		try{
			for (User u : allocatedUserList) {
				int user_id = u.getUser_Id();
				Date user_start_date = u.getStart_date();
				if(user_id!= userId_for_accountManager && user_id!= userId_for_admin){
					String single_date = "";
					List<String> allWorkingDatesForThisMonth = null;
					if(user_start_date != null){						
						Calendar start_dt_cal = Calendar.getInstance();
						start_dt_cal.setTime(user_start_date);
						if((start_dt_cal.get(Calendar.MONTH) == Calendar.getInstance().get(Calendar.MONTH)) && (start_dt_cal.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR))){
							allWorkingDatesForThisMonth =  TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartDate(df4.format(user_start_date));
						}
						else{
							allWorkingDatesForThisMonth = TimeUtility.getAllWorkingDatesForThisMonth();
						}
					}
					else{
						allWorkingDatesForThisMonth = TimeUtility.getAllWorkingDatesForThisMonth();
					}
					if(!allWorkingDatesForThisMonth.isEmpty()){
						String startDateFromList = allWorkingDatesForThisMonth.get(0);
						String endDate = allWorkingDatesForThisMonth.get(allWorkingDatesForThisMonth.size()-1);
						List<java.sql.Date> listForWholeMonthEntry = s1.createSQLQuery("select t.task_date from employee_task_detail t where user_id="+user_id+" and  t.task_date BETWEEN '"+startDateFromList+"' AND '"+endDate+"'").list();
						if(listForWholeMonthEntry.isEmpty() && !startDateFromList.equals(endDate)){
							Date formatted_date = df1.parse(startDateFromList);
							startDateFromList = df3.format(formatted_date);
							single_date = "for "+startDateFromList+" till date.";
						}
						else{
							java.sql.Date dt = null;
							for (String date : allWorkingDatesForThisMonth) {	
								dt = new java.sql.Date(df1.parse(date).getTime());
								if(!listForWholeMonthEntry.contains(dt)){
									String formatted_date = "";
									formatted_date = df2.format(df1.parse(date));					
									if(single_date == ""){
										single_date = single_date+""+formatted_date;
									}
									else{
										single_date = single_date+", "+formatted_date;
									}
								}
							}
						}
						if(single_date!=""){
							List<User> u_list = s1.createQuery("from User u where u.user_Id="+user_id).list();
							User u1 = u_list.get(0);
							Report r1 = new Report();
							if(!single_date.contains("for")){
								single_date = TimeUtility.getFormattedDateWithRangeForReminderMailFormat(single_date);
								r1.setPeriod("for "+single_date); // Modifing period for mailing
							}
							else{
								r1.setPeriod(single_date);
							}
							r1.setUserId(user_id);
							r1.setUserName(u1.getUser_name());
							r1.setEmailId(u1.getMail());
							r1.setApolloId(u1.getApollo_id());
							defaulterListForMailing.add(r1);
						}
					}
				}				
			}	
			tx.commit();
			s1.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured from DAO while getting time entry status list "+e);
			throw new Exception();
		}
		return defaulterListForMailing;
	}

	@Override
	public List<String> getManagerDetailForResource(int user_id)throws Exception {
		// For retrieving all lead details 
		List<String> leadListInfo = new ArrayList<String>();
		Session s1 = HbnUtil.getSession();
		Transaction tx = s1.beginTransaction();		
		Query q1 = s1.createSQLQuery("select u1.user_id, u1.email, u1.apollo_id from users u1 inner join user_role ur on u1.user_id=ur.user_id "
				+"where ur.role_id = 1004 and u1.user_id in"
				+"(select rm.user_id from report_mapping rm where rm.report_map_id="+user_id+" and rm.user_id!="+user_id+")"
				+"order by u1.user_name");
		List<Object[]> leadList = q1.list();
		if(leadList!=null && !leadList.isEmpty()){
			for (Iterator<Object[]> iterator = leadList.iterator(); iterator.hasNext();) {
				Object[] objArray = (Object[]) iterator.next();
				if(objArray[2] != null && objArray[2].toString().length() > 5){
					leadListInfo.add(objArray[2].toString());
				}
				else{
					leadListInfo.add(objArray[1].toString());
				}	
			}
		}					
		tx.commit();
		s1.close();
		return leadListInfo;
	}

	@Override
	public List<Report> getCompOffReport(String[] allocatedResource, String year, String month, Properties props) throws Exception {
		// creating CompOff Report here.
		List<Report> compOffReportList = new ArrayList<Report>();
		Session s1 = HbnUtil.getSession();
		Transaction tx = s1.beginTransaction();
		String year_month = year+"-"+month;
		SQLQuery query = null;
		try{
			for (String userId : allocatedResource) {
				Double total_hrs = 0.0;
				Double billed_hrs = 0.0;
				Double additiona_hrs = 0.0;
				Double compOff_availed_hrs = 0.0;
				Double comp_bal = 0.0;
				Double totalDownTime = 0.0;
				String username = "";
				Report r1 = new Report();
				query = s1.createSQLQuery("select u.user_name, SUM(TIME_TO_SEC(time)) as time from users u inner  join employee_task_detail et"
						+" where u.user_id=et.user_id and ((et.task_date like '"+year_month+"%' and (et.status!= 'Public holiday' and et.status!= 'Leave')) and u.user_id="+userId+")");
				List<Object[]> tolatHrsList = query.list();
				Object[] totalTaskList = tolatHrsList.get(0);
				username = totalTaskList[0].toString();			
				List<String> locationList = s1.createSQLQuery("select con.location from contact con where con.user_id="+userId+" group by con.user_id").list();
				List<String> holidayList = null;
				if(locationList.isEmpty()){
					billed_hrs = TimeUtility.getNumberOfWorkingDays(year_month)*8;	
				}
				else{
					if(locationList.contains("Bangalore")){
						String holiday = props.getProperty("holidayListForBangalore");
						holidayList = new ArrayList<String>();
						String[] holidayArray = holiday.split(", ");
						for (String days : holidayArray) {
							holidayList.add(days);
						}
						billed_hrs = TimeUtility.getNumberOfWorkingDaysExcludingHoliday(year_month, holidayList)*8;
					}
					else if(locationList.contains("Chennai")){
						String holiday = props.getProperty("holidayListForChennai");
						holidayList = new ArrayList<String>();
						String[] holidayArray = holiday.split(", ");
						for (String days : holidayArray) {
							holidayList.add(days);
						}
						billed_hrs = TimeUtility.getNumberOfWorkingDaysExcludingHoliday(year_month, holidayList)*8;
					}
					else{
						billed_hrs = TimeUtility.getNumberOfWorkingDays(year_month)*8;
					}
				}	
				if(totalTaskList[1] != null){
					int totalHrsInSecond =  Integer.parseInt(totalTaskList[1].toString());
					int hour = totalHrsInSecond/3600;
					int restHour = totalHrsInSecond%3600;
					int minutes = restHour/60;
					total_hrs = Double.parseDouble(hour+"."+minutes);
					SQLQuery innerQuery = s1.createSQLQuery("select SUM(TIME_TO_SEC(time)) FROM employee_task_detail etd where" 
							+" etd.task_date like '%"+year_month+"%' and (user_id="+userId+" and etd.status ='Comp off')");						
					List<Object> compOffSum = innerQuery.list();
					if(compOffSum!=null && compOffSum.get(0)!=null){
						int compOffInSecond = Integer.parseInt(compOffSum.get(0).toString());
						int hourForCompOfSum = compOffInSecond/3600;
						int restHourForCompOfSum = compOffInSecond%3600;
						int minutesForCompOfSum = restHourForCompOfSum/60;
						compOff_availed_hrs = Double.parseDouble(hourForCompOfSum+"."+minutesForCompOfSum);
					}	
					if(total_hrs >= 0){
						BigDecimal a = BigDecimal.valueOf(total_hrs - billed_hrs);
						BigDecimal roundOff = a.setScale(2, BigDecimal.ROUND_HALF_EVEN);
						additiona_hrs = Double.parseDouble(roundOff.toString());
					}
					else{
						BigDecimal a = BigDecimal.valueOf(total_hrs - (-billed_hrs));
						BigDecimal roundOff = a.setScale(2, BigDecimal.ROUND_HALF_EVEN);
						additiona_hrs = Double.parseDouble(roundOff.toString());
					}

					if(additiona_hrs >= 0){
						BigDecimal bd = BigDecimal.valueOf(additiona_hrs - compOff_availed_hrs);
						BigDecimal roff = bd.setScale(2, BigDecimal.ROUND_HALF_EVEN);
						comp_bal = Double.parseDouble(roff.toString());
					}
					else{
						BigDecimal bd = BigDecimal.valueOf(additiona_hrs - (-compOff_availed_hrs));
						BigDecimal roff = bd.setScale(2, BigDecimal.ROUND_HALF_EVEN);
						comp_bal = Double.parseDouble(roff.toString());
					}
					// Added for calculating Down time Hrs.
					SQLQuery queryForDownTime = s1.createSQLQuery("select SUM(TIME_TO_SEC(time)) FROM employee_task_detail etd where" 
							+" etd.task_date like '%"+year_month+"%' and (user_id="+userId+" and etd.status ='Down Time')");						
					List<Object> down_time_sum = queryForDownTime.list();
					if(down_time_sum!=null && down_time_sum.get(0)!=null){					
						int down_time_total_sec = Integer.parseInt(down_time_sum.get(0).toString());
						int down_time_hrs = down_time_total_sec/3600;
						int rest_down_time = down_time_total_sec%3600;
						int mints_in_down_time = rest_down_time/60;				
						totalDownTime = Double.parseDouble(down_time_hrs+"."+mints_in_down_time);
					}	
				}
				else{
					total_hrs = 0.0;
					additiona_hrs = 0.0;
					compOff_availed_hrs = 0.0;
					comp_bal = 0.0;
					totalDownTime = 0.0;
				}
				r1.setUserName(username);
				r1.setTotalTime(total_hrs);
				r1.setBilled_hours(billed_hrs);
				r1.setAdditional_hours(additiona_hrs);
				r1.setCompOff_availed_hrs(compOff_availed_hrs);
				r1.setComp_bal(comp_bal);
				r1.setTotal_down_time(totalDownTime);
				compOffReportList.add(r1);
			}
			tx.commit();
			s1.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
		return compOffReportList;
	}

	@Override
	public String freezeTimesheet(String[] allocatedResource, Calendar cal) throws Exception {
		// Freezing timesheet for selected resource.
		logger.info("Printing from ReportDAO freezeTimesheet method");
		String freezingStatus = null;
		Session s1 = HbnUtil.getSession();
		Transaction tx = s1.beginTransaction();
		try{
			for (String userId : allocatedResource) {
				User u1 = (User) s1.get(User.class, Integer.parseInt(userId));
				if(u1 != null){
					u1.setFreeze_timesheet(cal.getTime());
					s1.update(u1);
				}
			}				
			freezingStatus = "Timesheet freezed successfully.";
			tx.commit();
		}
		catch (Exception e) {
			logger.error("Printing exception detail from freezeTimesheet: "+e);
			freezingStatus = "Error while freezing timesheet.";
		}finally{
			s1.close();
		}
		return freezingStatus;
	}

	@Override
	public String unfreezeTimesheet(String[] allocatedResource, String reportMonthYear) throws Exception {
		// UnfreezeTimesheet for selected resource.
		logger.info("Printing from ReportDAO unfreezeTimesheet method");
		String unfreezeTimesheetStatus = null;
		Session s1 = HbnUtil.getSession();
		Transaction tx = s1.beginTransaction();
		Calendar cal = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		if(reportMonthYear != null){
			cal.setTime(df.parse(reportMonthYear));
			cal.add(Calendar.MONTH, -1);
			cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));			
			try{
				for (String userId : allocatedResource) {
					User u1 = (User) s1.get(User.class, Integer.parseInt(userId));
					u1.setFreeze_timesheet(cal.getTime());
					s1.update(u1);
				}	
				tx.commit();
				s1.close();
			}
			catch (Exception e) {
				e.printStackTrace();
				unfreezeTimesheetStatus = "Error while unfreezing timesheet.";
			}
		}
		else{
			unfreezeTimesheetStatus = "Timesheet unfreezed successfully.";
		}
		return unfreezeTimesheetStatus;
	}

	@Override
	public String checkFreezingEntryStatusForSelctedResource(String[] selectedResource, Calendar selectedMonthCal) {
		// Checking time entry freezing status for selected resource.
		String freezing_status = "unfreezed";
		Session s1 = HbnUtil.getSession();
		Transaction tx = s1.beginTransaction();
		Calendar calendarWithFreezeDate = Calendar.getInstance();
		Query query = null;
		try{
			query = s1.createQuery("from User user  where user.user_Id = :user_Id");
			outerloop:for (String userId : selectedResource) {
				User u1 = (User) query.setParameter("user_Id", Integer.parseInt(userId)).list().get(0);
				if(u1.getFreeze_timesheet() != null){
					calendarWithFreezeDate.setTime(u1.getFreeze_timesheet());
					if(selectedMonthCal.before(calendarWithFreezeDate) || selectedMonthCal.equals(calendarWithFreezeDate)){
						freezing_status = "freezed";
						break outerloop;
					}
				}
			}
			tx.commit();
			s1.close();
		}
		catch (Exception e) {
			logger.error("Printing exception detail from checkFreezingEntryStatusForSelctedResource: "+e);
		}
		return freezing_status;
	}

	@Override
	public List<Task> getTimestampForTimeEntries(String[] allocatedResource, Calendar cal) throws Exception {
		// Getting time entry updated/entered time.
		List<Task> task_list = new ArrayList<Task>();
		DateFormat df = new SimpleDateFormat("yyyy-MM");		
		List<String> category_list = new ArrayList<String>();
		category_list.add("Half Day");
		category_list.add("Leave");
		category_list.add("Public holiday");
		category_list.add("Comp off");		
		Session s1 = HbnUtil.getSession();
		Transaction tx = s1.beginTransaction();
		try{				
			for (String user_ids : allocatedResource) {
				int user_id = Integer.parseInt(user_ids);
				/*User u1 = (User) s1.get(User.class, user_id);
				SQLQuery qr = s1.createSQLQuery("select user_id, t.task_date, t.status, t.task_description, t.entry_time from employee_task_detail t " +
							"where (user_id="+user_id+" and  t.task_date like '%"+df.format(cal.getTime())+"%') and (t.status in('Half Day','Public holiday', 'Leave', 'Comp off')" +
							" or (WEEKDAY(t.task_date) =5 or WEEKDAY(t.task_date) =6)) order by t.task_date");*/
				Criteria crt = s1.createCriteria(Task.class);
				crt.add(Restrictions.eq("user.user_Id", user_id)).add(Restrictions.sqlRestriction("task_date like '%"+df.format(cal.getTime())+"%'"))
				.add(Restrictions.or(Restrictions.in("status", category_list), Restrictions.sqlRestriction("WEEKDAY(task_date) =5 or WEEKDAY(task_date) =6")))
				.addOrder(Order.asc("task_date"));
				List<Task> taskList = crt.list();
				if(!taskList.isEmpty()){
					for (Task task : taskList) {
						task_list.add(task);
					}
				}
			}	
			tx.commit();
			s1.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error(e.fillInStackTrace());
		}		
		return task_list;
	}

	@Override
	public List<Task> getAllUnassignedTaskDetailsForPreviousDay(List<Integer> user_ids, String previousDate) throws Exception {
		// Getting all unassigned task entry for previous date
		List<Task> task_list = new ArrayList<Task>();
		Session s1 = HbnUtil.getSession();
		Transaction tx = s1.beginTransaction();
		try{
			Criteria crt = s1.createCriteria(Task.class);
			crt.add(Restrictions.in("user.user_Id", user_ids)).add(Restrictions.sqlRestriction("task_date like '%"+previousDate+"%'")).add(Restrictions.eq("status", "Task unassigned/Idle"));
			task_list = crt.list();
			tx.commit();
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while from getAllUnassignedTaskDetailsForPreviousDay while running HbnReportDAO class "+e.getCause());
		}finally{
			s1.close();
		}
		return task_list;
	}

	/*@Override
	public String saveWeeklyStatus(UserEvent userEvent) throws Exception{
		String message = "Form saved successfully for";
		Session s1 = HbnUtil.getSession();
		Transaction tx = s1.beginTransaction();
		try
		{			
			Set<EventMile> eventMile = userEvent.getEventMile();
			Set<EventConst> eventConst = userEvent.getEventConst();
			Set<EventRole> eventRoles = userEvent.getEventRole();								
			s1.save(userEvent);
			for (EventMile m1 : eventMile) {
				s1.save(m1);
			}
			for (EventConst c1 : eventConst) {
				s1.save(c1);
			}
			for (EventRole r1 : eventRoles) {
				s1.save(r1);
			}
			s1.flush();
			tx.commit();
			s1.close();

		}
		catch (Exception e) {
			message = "Form not saved";
		}
		return message;
	}

	/*@Override
	public String saveWeeklyStatus(UserEvent userEvent) throws Exception{
		String message = "Form saved successfully for";
		Session s1 = HbnUtil.getSession();
		Transaction tx = s1.beginTransaction();
		try
		{			
			Set<EventMile> eventMile = userEvent.getEventMile();
			Set<EventConst> eventConst = userEvent.getEventConst();
			Set<EventRole> eventRoles = userEvent.getEventRole();								
			s1.save(userEvent);
			for (EventMile m1 : eventMile) {
				s1.save(m1);
			}
			for (EventConst c1 : eventConst) {
				s1.save(c1);
			}
			for (EventRole r1 : eventRoles) {
				s1.save(r1);
			}
			s1.flush();
			tx.commit();
			s1.close();

		}
		catch (Exception e) {
			message = "Form not saved";
		}
		return message;
	}

	@Override
	public UserEvent getEditableWeeklyForm(UserEvent userEvent)throws Exception {
		UserEvent userEventFromDB = null;
		Session s1 = HbnUtil.getSession();
		Transaction tx = s1.beginTransaction();
		String hqlQuery = "select Max(usEv.id) from UserEvent usEv where(usEv.start_date='"+userEvent.getStart_date()+"' and usEv.end_date='"+userEvent.getEnd_date()+"' and user_id="+userEvent.getUser().getUser_Id()+")";
		List<Integer> listId = s1.createQuery(hqlQuery).list();
		List<UserEvent> listUserEvent = null;
		if(listId!= null && !listId.isEmpty() &&  listId.get(0)!=null)
		{
			//userEventFromDB.setId(listId.get(0));
			int event_id = listId.get(0);
			listUserEvent = s1.createQuery("from UserEvent userEvent where userEvent.id="+event_id+"").list();
			if(listUserEvent!= null && !listUserEvent.isEmpty() &&  listUserEvent.get(0)!=null){
			   userEventFromDB = listUserEvent.get(0);
			}
			List<EventMile> listMile = s1.createQuery("from EventMile eventMile where id_event="+event_id+"").list();
			List<EventConst> listConst = s1.createQuery("from EventConst eventConst where event_id="+event_id+"").list();
			List<EventRole> listRole = s1.createQuery("from EventRole eventRole where event_id="+event_id+"").list();
			Set<EventMile> setMile = new HashSet<EventMile>();
			Set<EventConst> setConst = new HashSet<EventConst>();
			Set<EventRole>  setRole = new HashSet<EventRole>();
			for (EventMile eventMile : listMile) {
				setMile.add(eventMile);
			}
			for (EventConst eventConst : listConst) {
				setConst.add(eventConst);
			}
			for (EventRole eventRole : listRole) {
				setRole.add(eventRole);
			}			
			userEventFromDB.setEventMile(setMile);
			userEventFromDB.setEventConst(setConst);
			userEventFromDB.setEventRole(setRole);
		}

		return userEventFromDB;
	}*/


}
