package com.calsoft.performance.dao;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.calsoft.performance.form.PerformanceLogForm;
import com.calsoft.performance.form.UserCommentForm;
import com.calsoft.user.form.UserForm;
import com.calsoft.user.model.Appraisal;
import com.calsoft.user.model.User;


public interface PerformanceLogDao {
	
	public Appraisal getPerformance(int userId , String period) throws Exception;
	public List<String> getPeriodListForUser(int userId)throws Exception;	
	public boolean saveUserComment(PerformanceLogForm performanceLogForm,int userId)throws Exception;
	public List<UserCommentForm> getComments(int id,int userId, int admin_id_for_filtering_access_list) throws Exception;
	public boolean deleteUserComment(int commentId);
	public boolean editUserComment(int commentId , String userComment);
	public List<UserForm> getUsersListForPerformanceLogPage(int userId);
	public Map<Integer, String> getRoleNames(int userId,int performanceUserID);
	public int accountManager(PerformanceLogForm performanceLogForm, int userId,int id);
	public List<PerformanceLogForm> checkPreviousWeekEntry(List<String> getAllDatesForPreviousWeek,List<UserForm> allocatedUserList) throws Exception;
	public boolean addCommentAndSendMail(User user, User userWhoLoggedComment, List<String> getAllDatesForPreviousWeek);
	//public boolean editUserComment(PerformanceLogForm performanceLogForm, int userId);
	public List<String> getEmailInfoForAllAccessListAfterExcludingClient(List<String> mailDeatilsForCommentTo);
	public List<User> getUsersListForPerformanceLogPageBasedOnResourceJoiningAndRelievingDate(int userId, Calendar cal, String objective_quarter) throws Exception;
}
