package com.calsoft.report.dao;

import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import com.calsoft.report.model.Report;
import com.calsoft.task.model.Task;
import com.calsoft.user.form.UserForm;
import com.calsoft.user.model.User;
public interface ReportDAO {
	public List<Report> getReportData(String year,String month, String[] allocatedResource)throws Exception;

	public List<List<Report>> getUserReportAllocation(int userId)throws Exception;

	public void addResources(String[] allocatedResource, int userId)throws Exception;

	public void deleteResources(String[] allocatedResource, int userId)throws Exception;

	public List<List<Report>> getUserReportAllocation()throws Exception;

	public List<Report> getReportDataTeamWise(String year, String month, String[] detailedTeam, Calendar cal, int user_id_from_session) throws Exception;

	public List<Report> getExceptionDashboard(String year, String month, List<User> modelUser) throws Exception;
	
	public List<List<Report>> getReportDataForReminderMail(String monthYear, List<UserForm> allocatedUserList) throws Exception;

	public List<Report> getDefaulterListDetails(String[] allocatedResource, Calendar cal) throws Exception;

	public List<String> getManagerDetailForResource(int user_id) throws Exception;
	
	public List<Report> getCompOffReport(String[] allocatedResource, String year, String month, Properties props) throws Exception;

	public String freezeTimesheet(String[] allocatedResource, Calendar cal) throws Exception;

	public String unfreezeTimesheet(String[] allocatedResource, String reportMonth) throws Exception;

	public String checkFreezingEntryStatusForSelctedResource(String[] selectedResource, Calendar calForSelectedMonth);

	public List<Task> getTimestampForTimeEntries(String[] allocatedResource, Calendar cal) throws Exception;

	public List<Task> getAllUnassignedTaskDetailsForPreviousDay(List<Integer> user_ids, String previousDate) throws Exception;

	public List<Report> getResourceDetailWhoMissedEntry(List<User> allocatedUserListForDb, int account_manager_id, int admin_user_id) throws Exception;;

	/*public String saveWeeklyStatus(UserEvent userEvent) throws Exception;

	public UserEvent getEditableWeeklyForm(UserEvent userEvent) throws Exception;*/
}
