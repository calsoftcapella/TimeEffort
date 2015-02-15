package com.calsoft.performance.service;

import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import com.calsoft.performance.form.PerformanceLogForm;
import com.calsoft.performance.form.UserCommentForm;
import com.calsoft.user.form.UserForm;

public interface PerformanceLogService {
	public PerformanceLogForm getPerformance(int userId , String period);
	public List<PerformanceLogForm> getPeriodListForUser(int userId);
	public boolean saveUserComment(PerformanceLogForm performanceLogForm , int userId);
	public List<UserCommentForm> getComments(int id,int userId, int admin_id_for_filtering_access_list) ;
	public boolean deleteUserComment(int commentId);
	public List<UserForm> getUsersListForPerformanceLogPage(int userId);
	public boolean editUserComment(int parseInt, String userComment);
	public List<UserCommentForm> getRoleNames(int userId,int performanceUserID);
	public int accountManager(PerformanceLogForm performanceLogForm, int userId,int id);
	public String sendEmailNotification(PerformanceLogForm performanceLogForm, Properties prop, List<String> role_check);
	public List<PerformanceLogForm> checkPreviousWeekEntry(List<String> getAllDatesForPreviousWeek, List<UserForm> allocatedUserList) throws Exception;
	public void sendNotificationEmail(String mailContent, Properties prop);
	public String sendEmailNotificationAfterResourceComment(PerformanceLogForm performanceLogForm, Properties prop, List<String> maillingList);
	public void sendEmailAlertWhileDeletingComment(String commentDeletedBy, String trim, String trim2, String commentField, Properties prop) throws Exception;
	public List<UserForm> getUsersListForPerformanceLogPageBasedOnResourceJoiningAndRelievingDate(int userId, Calendar cal, String objective_period) throws Exception;;
}
