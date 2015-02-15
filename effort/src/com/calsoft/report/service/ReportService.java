package com.calsoft.report.service;

import java.io.File;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import com.calsoft.report.form.ReportForm;
import com.calsoft.report.model.Report;
import com.calsoft.task.form.TaskForm;
import com.calsoft.user.form.UserForm;

@SuppressWarnings("rawtypes")
public interface ReportService {

	List getReportData(String year, String month, String[] allocatedResource)throws Exception;

	List<List<Report>> getUserReportAllocation(int userId)throws Exception;

	void addResources(String[] allocatedResource, int userId)throws Exception;

	void deleteResources(String[] allocatedResource, int userId)throws Exception;

	List<List<Report>> getUserReportAllocation()throws Exception;

	List<ReportForm> getReportDataTeamWise(String year, String month, String[] detailedTeam, Calendar cal, int user_id_from_session) throws Exception;

	List<ReportForm> getExceptionDashboard(String year, String month, List<UserForm> allocatedUserList) throws Exception;
	
	List<List<ReportForm>> getReportDataForReminderMail(String monthYear, List<UserForm> allocatedUserList) throws Exception;

	void sendReminderMail(List<List<ReportForm>> reportList, List<String> dayList, File xmlTempFile, Calendar cal, Properties prop) throws Exception;

	List<ReportForm> getDefaulterListDetails(String[] allocatedResource, Calendar cal)throws Exception;

	List<ReportForm> getResourceDetailWhoMissedEntry(List<UserForm> allocatedUserList)throws Exception;

	String sendWeeklyReminderMail(List<ReportForm> reportList, Calendar cal, Properties prop);
	
	List<ReportForm> getCompOffReport(String[] allocatedResource, String year, String month, Properties props) throws Exception;

	String freezeTimesheet(String[] allocatedResource, Calendar cal) throws Exception;

	String unfreezeTimesheet(String[] allocatedResource, String reportMonthYear) throws Exception;

	String checkFreezingEntryStatusForSelctedResource(String[] selectedResource, Calendar cal);

	List<TaskForm> getTimestampForTimeEntries(String[] allocatedResource, Calendar cal) throws Exception;

	List<TaskForm> getAllUnassignedTaskDetailsForPreviousDay(List<Integer> user_ids, String previousDate) throws Exception;

	List<String> sendIdleTimeReport(List<TaskForm> taskFormList, String email_content, Properties prop) throws Exception;
	
	/*String saveWeeklyStatus(WeeklyForm form, int userId, String startDate, String endDate, List<Deliverables> deliv, List<Constraints> constr, List<Recruitment_status> recrut )throws Exception;

	WeeklyForm getEditableWeeklyForm(String startDate, String endDate,int userId) throws Exception;*/
}
