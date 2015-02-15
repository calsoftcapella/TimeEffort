package com.calsoft.task.dao;

import java.util.Calendar;
import java.util.List;

import com.calsoft.task.model.Task;

public interface SaveTaskDao {
	public String[] doSaveTask(Task task)throws Exception;

	public List<Task> getTaskDetails(String month, int userId) throws Exception;
	
	public boolean doDelete(int taskId)throws Exception;
	
	public List<Task> editTask(int id)throws Exception;
	
	public String editSaveTask(Task task, int id, int userId)throws Exception;
	
	public List<List<Task>> getTaskDetailsUser(String[] allocatedResource, String year, String month)throws Exception;
	
	public List<String> saveAllTask(List<Task> taskList) throws Exception;

	public List<List<Task>> getTaskDetailsTeamWise(String[] detailsTeam, String year, String month, int user_id) throws Exception;

	public String getResourceLocation(int userId)throws Exception;

	public String freezeTimesheet(Integer resource_id, Calendar cal) throws Exception;

	public String unfreezeTimesheet(Integer resource_id, Calendar cal) throws Exception;

	public List<String> getEnteredTimesheetDateForPreviousMonth(Calendar c1, int user_id);;

}
