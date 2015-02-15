package com.calsoft.leave.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import com.calsoft.leave.dao.LeaveDao;
import com.calsoft.leave.dao.factory.LeaveDaoFactory;
import com.calsoft.leave.form.LeaveForm;
import com.calsoft.leave.model.Leave;
import com.calsoft.user.model.User;
import com.calsoft.user.service.UserService;

/*Service Implementation class for LeaveEntry Module*/
@SuppressWarnings({"unchecked","rawtypes"})
public class LeaveServiceImpl implements LeaveService {
	UserService service;
	private static final Logger logger = Logger.getLogger("name");
	/* saveLeave() method :Performing the save operation of Leave Entry */

	@Override
	public void saveLeave(LeaveForm leaveForm, HttpServletRequest request, String[] checkDateNew, String selectMonthYear)throws Exception{
		int userId = Integer.parseInt(request.getSession().getAttribute("user_id").toString());
		List leaveList = populateLeaveFromForm(leaveForm, userId,checkDateNew);
		LeaveDao leaveDao = LeaveDaoFactory.getLeaveDao();
		logger.info("checkDateNewDao="+checkDateNew);
		leaveDao.saveLeave(leaveList,checkDateNew,userId,selectMonthYear);
	}
	/*
	 * populateLeaveFromForm() method :Populating the data for selected month
	 * while saving records to database of Leave Entry
	 */

	public List populateLeaveFromForm(LeaveForm leaveForm, int userId, String[] checkDateNew) throws Exception{
		Leave leave = null;
		List leaveList=new ArrayList<Leave>();
		//service = Factory.getUserService();
		if(checkDateNew!=null)
		{
			for (int j = 0; j < checkDateNew.length; j++) {				
				leaveForm.setCheckDate(checkDateNew[j]);
				DateFormat dateFormat = new SimpleDateFormat("MMMM dd yyyy");
				Date date = new Date();
				String d1 = dateFormat.format(date);
				Date sysDate = (Date) dateFormat.parse(d1);
				leave = new Leave();
				leave.setId(leaveForm.getId());
				leave.setLeave_date(leaveForm.getCheckDate());
				leave.setLeave_month(leaveForm.getSelectMonth());
				leave.setUpdated_on(sysDate);
				leave.setUser(leaveForm.getUser());
				User user = new User();
				user.setUser_Id(userId);
				leave.setUser(user);
				leaveList.add(leave);
			}
		}
		return leaveList;
	}

	/*
	 * populateLeaveForm() method :Populating the data for selected month while
	 * retriving records from database of Leave Entry
	 */

	public LeaveForm populateLeaveForm(Leave leave) throws Exception{
		LeaveForm leaveForm = null;
		//service = Factory.getUserService();
		leaveForm = new LeaveForm();
		//DateFormat dateFormat = new SimpleDateFormat("dd-mmm-yyyy");
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("dd-MMM-yyyy");
		Date date = format1.parse(leave.getUpdated_on().toString());
		leaveForm.setUpdatedDateString(format2.format(date));
		leaveForm.setId(leave.getId());
		leaveForm.setCheckDate(leave.getLeave_date());
		leaveForm.setSelectMonth(leave.getLeave_month());
		leaveForm.setUser(leave.getUser());
		leaveForm.setUserName(leave.getUser().getUser_name());
		return leaveForm;
	}

	/*
	 * getLeave() method :Retriving the all records for selected month of Leave
	 * Entry
	 */

	@Override
	public List<LeaveForm> getLeave(String selectMonth,HttpServletRequest request) throws Exception{
		List<Leave> baseLeaveList = new ArrayList<Leave>();
		List<LeaveForm> leaveFormList = new ArrayList<LeaveForm>();
		LeaveDao leaveDao = LeaveDaoFactory.getLeaveDao();
		baseLeaveList = leaveDao.getAllLeave(selectMonth, request);
		Collections.sort(baseLeaveList,  Collections.reverseOrder(new Leave.SortBasedOnLeaveDate()));
		Iterator<Leave> iterator = baseLeaveList.iterator();
		//LeaveForm leaveForm = new LeaveForm();
		while (iterator.hasNext()) {
			Leave list = iterator.next();
			LeaveForm leaveformNew = populateLeaveForm(list);
			leaveFormList.add(leaveformNew);
		}
		return leaveFormList;
	}

	/*
	 * getLeaveDate() method :Retriving only dates for selected month of Leave
	 * Entry
	 */

	public List<String> getLeaveDate(String selectMonth, HttpServletRequest request) throws Exception{
		LeaveDao leaveDao = LeaveDaoFactory.getLeaveDao();
		List<String> leaveDate = leaveDao.getLeaveDate(selectMonth, request);
		return leaveDate;
	}

	/*
	 * getDashBoardList() method :Retriving specific records for selected month
	 * of Leave Entry
	 */

	public List<Leave> getDashBoardList(String selectMonthYear,
			HttpServletRequest request) throws Exception{
		LeaveDao leaveDao = LeaveDaoFactory.getLeaveDao();
		List<Leave> date = leaveDao.getDashBoardList(selectMonthYear, request);
		return date;
	}

	/*
	 * clearCheck() method :Removing records which are checked for selected
	 * month of Leave Entry
	 */

	public void clearCheck(LeaveForm leaveForm, HttpServletRequest request,String[] checkDateNew) throws Exception{
		int userId = Integer.parseInt(request.getSession().getAttribute("user_id").toString());
		List leave = populateLeaveFromForm(leaveForm, userId,checkDateNew);
		LeaveDao leaveDao = LeaveDaoFactory.getLeaveDao();
		leaveDao.clearCheck(leave);
	}
	/*
	 * getLeaveDetailsUser() method :Performing the get detail operation for report
	 */


	@Override
	public List<LeaveForm> getLeaveDetailsUser(String[] allocatedResource,String dateString){
		List<List<Leave>> superLeavelist = new ArrayList<List<Leave>>();
		List<LeaveForm> superTaskFormList = new ArrayList<LeaveForm>();
		try{
			LeaveDao leaveDao = LeaveDaoFactory.getLeaveDao();
			superLeavelist = leaveDao.getLeaveDetailsUser(allocatedResource,dateString);
			Iterator<List<Leave>> itr = superLeavelist.iterator();
			while (itr.hasNext()){
				List<LeaveForm> leaveFormList = new ArrayList<LeaveForm>();
				LeaveForm leaveForm = new LeaveForm();
				List<Leave> taskList = itr.next();
				Iterator<Leave> itr1 = taskList.iterator();
				while (itr1.hasNext()){
					LeaveForm leaveform1 = populateLeaveForm(itr1.next());
					leaveFormList.add(leaveform1);
				}
				leaveForm.setLeaveFormList(leaveFormList);
				if(leaveFormList.size()>0){
					superTaskFormList.add(leaveForm);
				}
			}
		} 
		catch (Exception e){
			e.printStackTrace();
		}
		return superTaskFormList;
	}

	/*
	 * getTeamLeaveDetail() method :Performing the get Team Leave Detail operation from
	 * getTeamLeaveDetail page of Leave Entry
	 */

	public List<LeaveForm> getTeamLeaveDetail(List allocatedResource,String dateString)throws Exception 
	{
		List<List<Leave>> superLeavelist = new ArrayList<List<Leave>>();
		List<LeaveForm> superTaskFormList = new ArrayList<LeaveForm>();
		try {
			LeaveDao leaveDao = LeaveDaoFactory.getLeaveDao();
			superLeavelist = leaveDao.getTeamLeaveDetail(allocatedResource,dateString);
			Iterator<List<Leave>> itr = superLeavelist.iterator();
			while (itr.hasNext()) {
				List<LeaveForm> leaveFormList = new ArrayList<LeaveForm>();
				LeaveForm leaveForm = new LeaveForm();
				List<Leave> taskList = itr.next();
				Iterator<Leave> itr1 = taskList.iterator();
				while (itr1.hasNext()) {
					LeaveForm leaveform1 = populateLeaveForm(itr1.next());
					leaveFormList.add(leaveform1);
				}
				leaveForm.setLeaveFormList(leaveFormList);
				superTaskFormList.add(leaveForm);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return superTaskFormList;
	}
	@Override
	public List<LeaveForm> getLeaveDetailsTeamWise(String[] teamDetails, String dateString, int user_id_from_session) throws Exception {
		List<List<Leave>> superLeavelist = new ArrayList<List<Leave>>();
		List<LeaveForm> superTaskFormList = new ArrayList<LeaveForm>();
		try{
			LeaveDao leaveDao = LeaveDaoFactory.getLeaveDao();
			superLeavelist = leaveDao.getLeaveDetailsTeamWise(teamDetails, dateString, user_id_from_session);
			Iterator<List<Leave>> itr = superLeavelist.iterator();
			while (itr.hasNext()){
				List<LeaveForm> leaveFormList = new ArrayList<LeaveForm>();
				LeaveForm leaveForm = new LeaveForm();
				List<Leave> taskList = itr.next();
				Iterator<Leave> itr1 = taskList.iterator();
				while (itr1.hasNext()){
					LeaveForm leaveform1 = populateLeaveForm(itr1.next());
					leaveFormList.add(leaveform1);
				}
				leaveForm.setLeaveFormList(leaveFormList);
				if(leaveFormList.size()>0){
					superTaskFormList.add(leaveForm);
				}
			}
		} 
		catch (Exception e){
			e.printStackTrace();
		}
		return superTaskFormList;
	}


	@Override
	public List<LeaveForm> getDashBoardList(String year, int user_id) throws Exception {
		// Getting entire Leave list for complete year
		List<LeaveForm> list = new ArrayList<LeaveForm>();
		LeaveDao leaveDao = LeaveDaoFactory.getLeaveDao();
		List<Object[]> leaveList = leaveDao.getAllLeaveForYear(year, user_id);
		LeaveForm l1 = null;
		for (Object[] leaveInfo : leaveList) {
			l1 = new LeaveForm();	
			l1.setUserName((String)leaveInfo[0]);
			l1.setUpdatedDateString((String)leaveInfo[1]);
			l1.setWork_category((String)leaveInfo[2]);
			list.add(l1);			
		}
		return list;
	}
}
