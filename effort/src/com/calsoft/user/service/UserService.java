package com.calsoft.user.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import com.calsoft.report.form.ReportForm;
import com.calsoft.report.model.Report;
import com.calsoft.task.form.TaskForm;
import com.calsoft.user.form.AppraisalForm;
import com.calsoft.user.form.ClientFeedbackForm;
import com.calsoft.user.form.UserForm;
import com.calsoft.user.form.UserRoleForm;
import com.calsoft.user.model.ProjectDetailModel;
import com.calsoft.user.model.User;

public interface UserService {

	public String addUser(UserForm form, Properties props)throws Exception;
	public  List<UserForm> getUserNames()throws Exception;
	public UserForm getUsernameFromId(int userId);
	public String getPassword(UserForm userForm, Properties prop)throws Exception;
	public boolean delete(UserForm form)throws Exception;
	public boolean deleteGlobal(UserForm form, Properties prop)throws Exception;  //Global*/
	public User getUserDetail(int id)throws Exception;
	public String matchUser(UserForm userForm, HttpServletRequest request) throws Exception;
	public List<TaskForm> getUserTask(int user_id) throws Exception;
	public ArrayList<String> getTaskMessage() throws Exception;
	public List<UserForm> getAllocatedResources(int userId);
	public boolean changePassword(UserForm form)throws Exception;
	public List<List<Report>> getUserReportAllocation(int userId)throws Exception;
	public List<Object> getContactDetails(List<UserForm> allocatedUserList) throws Exception;
	public List<UserRoleForm> getAllRole()  throws Exception;
	public List<String> getTeamFromContact() throws Exception;
	public List<UserForm> getAllocatedResourcesTeamWise(int userId, ReportForm form) throws Exception;
	public List<AppraisalForm> getObjective(int user_id) throws Exception;
	public List<UserForm> getAllocatedResourcesDetailsBasedOnRelievingDate(int userId, Calendar cal) throws Exception;
	public List<UserForm> getAllocatedResourcesBasedOnExitDateAndLocation(int userId, Calendar cal, String location)throws Exception;
	public String setNotificationForSharedFeedback(String[] toListArray, File newFile, UserForm userFormForUsername, ClientFeedbackForm clickFeedbackForm, String appContext, Properties prop)throws Exception;
	public List<ClientFeedbackForm> getAllAddedFeedback(int user_id);
	public String setViewStatus(List<Integer> idList, int user_id);
	public UserForm getUserDetailForUserId(int user_id) throws Exception;
	public String saveResourceInProfile(UserForm user_form) throws Exception;
	public String getUserRoleId(int uid);
	public List<UserForm> getAllocatedResourcesBasedOnStartAndExitDate(int userId, Calendar cal) throws Exception;
	public List<UserForm> getAllocatedResourcesDetailsForMonthlyReminder(int userId, Calendar calendar) throws Exception;
	public String validateResourcePassword(Integer resourceId, String resourcePass);	
	public List<UserForm> getAllocatedResourcesDetailsForRemindarMailBasedOnRelievingDate(int userId, Calendar cal) throws Exception;
	public List<ReportForm> getAllClientInformationForClientBasedReport() throws Exception;
	public int getAdminUserId() throws Exception;
	public int getAccountManagerId() throws Exception;
	public List<ProjectDetailModel> getAllProjectInfo() throws Exception;
}
