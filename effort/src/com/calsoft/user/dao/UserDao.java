package com.calsoft.user.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import org.hibernate.Session;
import com.calsoft.report.model.Report;
import com.calsoft.user.model.Appraisal;
import com.calsoft.user.model.ClientFeedback;
import com.calsoft.user.model.Contact;
import com.calsoft.user.model.ProjectDetail;
import com.calsoft.user.model.Role;
import com.calsoft.user.model.User;
import com.calsoft.user.model.UserModel;
import com.calsoft.user.model.UserTask;

public interface UserDao
{
	public boolean addUser(User user,Session session, int roleId) throws Exception;
	public List<User> getUserName()throws Exception;
	public User getUsernameFromId(int userId);		
	public String storePassword(User user) throws Exception;
	public boolean deActivateUser(User user)throws Exception;
	public User getUser(int id)throws Exception;
	public String matchUser(User user, HttpServletRequest request) throws Exception;
	public List<User> getAllocatedResources(int userId);
	public List<UserTask> getUserTask(int user_id) throws Exception ;	
	public ArrayList<String> getTaskMessageList() throws Exception;
    public boolean changePassword(User user,String newPassword)throws Exception;
	public List<User> getUser()throws Exception;
	public List<List<Report>> getUserReportAllocation(int userId) throws Exception;
    public boolean globalUser(User user, Properties props) throws Exception;
	public List<Object> getContactDetails(List<User> userList) throws Exception;
	public List<Role> getAllRoles() throws Exception;
	public List<String> getTeamFromContact() throws Exception;
	public List<User> getAllocatedResourcesTeamWise(int userId, String team) throws Exception;
	public List<Appraisal> getObjective(int user_id, String month) throws Exception;
	public List<User> getAllocatedResourcesDetailsBasedOnRelievingDate(int userId, Calendar cal)  throws Exception;
	public List<User> getAllocatedResourcesBasedOnExitDateAndLocation(int userId, Calendar cal, String location)throws Exception;
	public List<ClientFeedback> getAllAddedFeedback(Timestamp ts1, Timestamp ts2, int user_id);
	public String setViewStatus(List<Integer> idList, int user_id);
	public void saveFeedbackInfo(int user_Id, ClientFeedback c_feedback);
	public Map<Object, Object> getUserDetailForUserId(int userId) throws Exception;
	public String saveResourceInProfile(User u, String user_role, List<Contact> conList) throws Exception;
	public String getUserRoleId(int uid);
	public List<User> getAllocatedResourcesBasedOnStartAndExitDate(int userId, Calendar cal) throws Exception;
	public List<UserModel> getAllocatedResourcesDetailsForMonthlyReminder(int userId, Calendar cal)throws Exception;
	public String validateResourcePassword(Integer resourceId, String resourcePass);
	public List<User> getAllocatedResourcesDetailsForRemindarMailBasedOnRelievingDate(int userId, Calendar cal) throws Exception;
	public List<User> getAllClientInformationForClientBasedReport() throws Exception;
	public int getAdminUserId() throws Exception;
	public int getAccountManagerId() throws Exception;
	public List<ProjectDetail> getAllProjectInfo() throws Exception;
}
