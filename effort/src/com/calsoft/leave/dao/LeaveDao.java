package com.calsoft.leave.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import com.calsoft.leave.model.Leave;

/*Dao class for LeaveEntry Module*/
@SuppressWarnings("rawtypes")
public interface LeaveDao {
	public void saveLeave(List leave,String[] checkDateNew,int userId,String selectMonth) throws Exception;
	public List<Leave> getAllLeave(String selectMonth,HttpServletRequest request) throws Exception;
	public List<String> getLeaveDate(String selectMonth,HttpServletRequest request) throws Exception;
	public List<List<Leave>> getLeaveDetailsUser(String[] allocatedResource,String year) throws Exception;
	public List<Leave> getDashBoardList(String selectMonthYear,HttpServletRequest request) throws Exception;
	public void clearCheck(List leave) throws Exception;
	public List<List<Leave>> getTeamLeaveDetail(List allocatedResource,String dateString) throws Exception;
	public List<List<Leave>> getLeaveDetailsTeamWise(String[] teamDetails, String dateString, int user_id_from_session) throws Exception;
	public List<Object[]> getAllLeaveForYear(String year, int user_id) throws Exception;
	
}
