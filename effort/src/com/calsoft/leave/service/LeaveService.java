package com.calsoft.leave.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import com.calsoft.leave.form.LeaveForm;
import com.calsoft.leave.model.Leave;

/*Service class for LeaveEntry Module*/
@SuppressWarnings("rawtypes")
public interface LeaveService {
	public void saveLeave(LeaveForm leaveForm,HttpServletRequest request,String[] checkDateNew,String selectMonth)throws Exception;
	public List<LeaveForm> getLeave(String selectMonth,HttpServletRequest request)throws Exception;
	public List<String> getLeaveDate(String selectMonth,HttpServletRequest request)throws Exception;
	public List<LeaveForm> getLeaveDetailsUser(String[] allocatedResource,String year)throws Exception;
	public List<Leave> getDashBoardList(String selectMonthYear,HttpServletRequest request)throws Exception;
	public void clearCheck(LeaveForm leaveForm,HttpServletRequest request,String[] checkDate)throws Exception;
	public List<LeaveForm> getTeamLeaveDetail(List allocatedResource,String dateString)throws Exception;
	public List<LeaveForm> getLeaveDetailsTeamWise(String[] teamDetails, String dateString, int user_id_from_session) throws Exception;
	public List<LeaveForm> getDashBoardList(String selectMonthYear, int user_id) throws Exception;
	
}
