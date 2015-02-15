package com.calsoft.task.service;

import java.util.Calendar;
import java.util.List;

import com.calsoft.task.form.TaskForm;

public interface SaveTaskService {
	public String[] saveTask(TaskForm taskForm, int userId)throws Exception;

	//public List getTaskDetails(int userId);
	public List<TaskForm> getTaskDetails(String month, int userId) throws Exception;
	
	public boolean doDelete(int taskId)throws Exception;

	public List<TaskForm> editTask(int id)throws Exception;

	public String editsaveTask(TaskForm taskForm, int id, int userId)throws Exception;

	public List<TaskForm> getTaskDetailsUser(String[] allocatedResource, String year, String month)throws Exception;

	public List<String> saveAllTask(List<TaskForm> listForm, int userId)throws Exception;

	public List<TaskForm> getTaskDetailsTeamWise(String[] detailsTeam, String year, String month, int user_id) throws Exception;

	public String getResourceLocation(int userId)throws Exception;

	public List<String> getMissingDateEntry(List<TaskForm> tList, int userId) throws Exception;

	public String freezeTimesheet(Integer resource_id, Calendar cal) throws Exception;

	public String unfreezeTimesheet(Integer resource_id, Calendar cal) throws Exception;

	public List<String> getEnteredTimesheetDateForPreviousMonth(Calendar c1, int userId);

}
