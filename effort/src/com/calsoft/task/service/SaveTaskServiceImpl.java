package com.calsoft.task.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import com.calsoft.task.dao.SaveTaskDao;
import com.calsoft.task.dao.factory.SaveTaskDaoFactory;
import com.calsoft.task.form.TaskForm;
import com.calsoft.task.model.Task;
import com.calsoft.user.model.User;
import com.calsoft.util.TimeConveterUtil;
import com.calsoft.util.TimeUtility;

@SuppressWarnings({"rawtypes", "deprecation"})
public class SaveTaskServiceImpl implements SaveTaskService {

	@Override
	public String[] saveTask (TaskForm taskForm,int userid) throws Exception{
		Task task=populateTaskFromForm(taskForm,userid);
		SaveTaskDao saveTaskDao=SaveTaskDaoFactory.getSaveTaskDao();
		String[] entryStatus = saveTaskDao.doSaveTask(task);
		return entryStatus;
	}
	public Task populateTaskFromForm(TaskForm taskForm, int userid)throws Exception {
		Task task = null;
		task = new Task();
		task.setBacklog_id(taskForm.getBacklog_id());
		task.setStatus(taskForm.getStatus());	
		task.setId(taskForm.getId());
		DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		Date date = (Date) formatter.parse(taskForm.getTask_date());
		task.setTask_date(date);
		task.setTask_description(taskForm.getTask_description());
		User user = new User();
		user.setUser_Id(userid);
		task.setUser(user);
		task.setTask_id(taskForm.getTask_id());
		String time=taskForm.getTime();
		String afterConvertTime=TimeConveterUtil.timeConveter(time);
		task.setTime(afterConvertTime);
		task.setWork_status(taskForm.getWork_status());
		task.setEntry_time(Calendar.getInstance().getTime());
		return task;
	}
	/*public Task populateTaskFromForm1(TaskForm taskForm)throws Exception{
		Task task=null;
		task=new Task();
		DateFormat	formatter = new SimpleDateFormat("MM/dd/yyyy");
		Date date = (Date)formatter.parse(taskForm.getTask_date()); 
		task.setTask_date(date);
		task.setStatus(taskForm.getStatus());
		task.setTask_description(taskForm.getTask_description());
		task.setBacklog_id(taskForm.getBacklog_id());
		task.setId(taskForm.getId());
		//task.setUser(taskForm.getUser());
		task.setTask_id(taskForm.getTask_id());
		String time=TimeConveterUtil.timeConveter(taskForm.getTime());
		task.setTime(time);
		task.setWork_status(taskForm.getWork_status());
		User user=new User();  
		user.setUser_Id(taskForm.getUserId());
		task.setUser(user);
		task.setEntry_time(Calendar.getInstance().getTime());
		return task;
	}*/
	public TaskForm populateTaskFormFromTask(Task task)throws Exception{
		TaskForm taskForm=new TaskForm();
		taskForm.setId(task.getId());
		taskForm.setBacklog_id(task.getBacklog_id());
		taskForm.setStatus(task.getStatus());
		taskForm.setUser(task.getUser());
		DateFormat	formatter = new SimpleDateFormat("MM/dd/yyyy");
		String date = formatter.format(task.getTask_date());  
		taskForm.setTask_date(date);
		taskForm.setTask_description(task.getTask_description().replace("`", "'"));   // Modification Done by Prem for replacing ` to ' for displaying.
		taskForm.setTask_id(task.getTask_id());
		taskForm.setTime(task.getTime());
		taskForm.setWork_status(task.getWork_status());
		if(task.getUser()!=null){
			if(task.getUser().getUser_name()!=null&&!(task.getUser().getUser_name().equals(""))){
				taskForm.setUserName(task.getUser().getUser_name());
			}
		}
		return taskForm;
	}
	public TaskForm populateTaskFormFromTaskNew(Task task)throws Exception{
		TaskForm taskForm=new TaskForm();
		taskForm.setId(task.getId());
		taskForm.setBacklog_id(task.getBacklog_id());
		taskForm.setStatus(task.getStatus());
		taskForm.setUser(task.getUser());
		DateFormat	formatter = new SimpleDateFormat("MM/dd/yyyy");
		String date = formatter.format(task.getTask_date());  
		taskForm.setTask_date(date);
		taskForm.setTask_description(task.getTask_description().replace("`", "'"));   // Modification Done by Prem for replacing ` to ' for displaying.
		taskForm.setTask_id(task.getTask_id());				
		String time = task.getTime().substring(0,task.getTime().lastIndexOf(':'));     // Modification Done by Prem 				
		taskForm.setTime(time.replace(':', '.'));
		taskForm.setWork_status(task.getWork_status());
		if(task.getUser()!=null){
			if(task.getUser().getUser_name()!=null&&!(task.getUser().getUser_name().equals(""))){				
				taskForm.setUserName(task.getUser().getUser_name());
			}
		}
		return taskForm;
	}
	public List<TaskForm> getTaskDetails(String month,int userId) throws Exception {
		List<TaskForm> taskFormList=new ArrayList<TaskForm>();
		SaveTaskDao saveTaskDao=SaveTaskDaoFactory.getSaveTaskDao();
		List<Task> eList = saveTaskDao.getTaskDetails(month,userId);
		Iterator<Task> itr=eList.iterator();
		while(itr.hasNext()){
			Task task = itr.next();
			TaskForm taskForm = populateTaskFormFromTask(task);
			taskFormList.add(taskForm);
		}
		return taskFormList;
	}
	public boolean doDelete(int taskId) {
		boolean flag = false;
		try {
			SaveTaskDao saveTaskDao=SaveTaskDaoFactory.getSaveTaskDao();
			flag = saveTaskDao.doDelete(taskId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	@Override
	public List<TaskForm> editTask(int id) throws Exception{
		List<TaskForm> taskFormList=new ArrayList<TaskForm>();
		SaveTaskDao saveTaskDao=SaveTaskDaoFactory.getSaveTaskDao();
		List<Task> taskList=saveTaskDao.editTask(id);
		Iterator itr=taskList.iterator();
		while(itr.hasNext())
		{
			Task task=(Task)itr.next();
			TaskForm taskForm=populateTaskFormFromTask(task);
			taskFormList.add(taskForm);
		}
		return taskFormList;
	}

	@Override
	public String editsaveTask(TaskForm taskForm,int id,int userId) throws Exception{
		String entryStatus = null;
		Task task = populateTaskFromForm(taskForm, userId);
		SaveTaskDao saveTaskDao=SaveTaskDaoFactory.getSaveTaskDao();
		entryStatus = saveTaskDao.editSaveTask(task,id,userId);
		return entryStatus;
	}
	@Override
	public List<TaskForm> getTaskDetailsUser(String[] allocatedResource,String year,String month) {
		List<List<Task>> supertasklist = new ArrayList<List<Task>>();
		List<TaskForm> superTaskFormList = new ArrayList<TaskForm>();
		DateFormat myFormat = new SimpleDateFormat("MM/yyyy");
		try{
			SaveTaskDao saveTaskDao = SaveTaskDaoFactory.getSaveTaskDao();
			supertasklist = saveTaskDao.getTaskDetailsUser(allocatedResource,year,month);
			Date dt = myFormat.parse(month+"/"+year);
			Date d1 = new Date();
			Iterator<List<Task>> itr = supertasklist.iterator();
			while(itr.hasNext()){
				List<TaskForm> taskFormList = new ArrayList<TaskForm>();
				TaskForm taskForm = new TaskForm();
				List<Task> taskList = itr.next();			
				if(!taskList.isEmpty()){					
					if(dt.getMonth() == d1.getMonth()){
						List<Task> taskListMissing = TimeUtility.checkDateAndAddMissing(d1, taskList);
						Iterator<Task> itr1 = taskListMissing.iterator();
						while(itr1.hasNext()){				
							TaskForm taskform1 = populateTaskFormFromTaskNew(itr1.next());
							taskFormList.add(taskform1);
						}
						taskForm.setTaskFormList(taskFormList);
						if(taskFormList.size() > 0){
							superTaskFormList.add(taskForm);
						}
					}
					else{
						Calendar calendar = new GregorianCalendar(Integer.parseInt(year),Integer.parseInt(month)-1 , Calendar.DAY_OF_MONTH);
						calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
						List<Task> taskListMissing = TimeUtility.checkDateAndAddMissing(calendar.getTime(), taskList);
						Iterator<Task> itr1=taskListMissing.iterator();
						while(itr1.hasNext()){				
							TaskForm taskform1 = populateTaskFormFromTaskNew(itr1.next());
							taskFormList.add(taskform1);
						}
						taskForm.setTaskFormList(taskFormList);
						if(taskFormList.size()>0){
							superTaskFormList.add(taskForm);
						}
					}
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return superTaskFormList;
	}
	@Override
	public List<String> saveAllTask(List<TaskForm> listForm, int userId) throws Exception {
		//iterate the form and convert into list of pojo
		List<String> mess = new ArrayList<String>();
		List<Task> taskList = new ArrayList<Task>();
		if(listForm!=null){
			for (TaskForm taskForm : listForm) {
				Task task = populateTaskFromForm(taskForm, userId);
				taskList.add(task);
			}
		}
		SaveTaskDao saveTaskDao=SaveTaskDaoFactory.getSaveTaskDao();
		mess = saveTaskDao.saveAllTask(taskList);
		return mess;
	}
	@Override
	public List<TaskForm> getTaskDetailsTeamWise(String[] detailsTeam, String year, String month, int user_id) throws Exception {
		List<List<Task>> supertasklist=new ArrayList<List<Task>>();
		List<TaskForm> superTaskFormList=new ArrayList<TaskForm>();
		try{
			SaveTaskDao saveTaskDao = SaveTaskDaoFactory.getSaveTaskDao();
			supertasklist = saveTaskDao.getTaskDetailsTeamWise(detailsTeam, year, month, user_id);
			Iterator<List<Task>> itr=supertasklist.iterator();
			while(itr.hasNext()){
				List<TaskForm> taskFormList=new ArrayList<TaskForm>();
				TaskForm taskForm=new TaskForm();
				List<Task> taskList=itr.next();
				DateFormat myFormat = new SimpleDateFormat("MM/yyyy");
				Date dt = myFormat.parse(month+"/"+year);
				if(!taskList.isEmpty()){
					Date d1 = new Date();
					if(dt.getMonth()==d1.getMonth()){
						List<Task> taskListMissing = TimeUtility.checkDateAndAddMissing(d1, taskList);
						Iterator<Task> itr1=taskListMissing.iterator();
						while(itr1.hasNext()){					
							TaskForm taskform1=populateTaskFormFromTaskNew(itr1.next());
							taskFormList.add(taskform1);
						}
						taskForm.setTaskFormList(taskFormList);
						if(taskFormList.size()>0){
							superTaskFormList.add(taskForm);
						}
					}
					else{
						Calendar calendar = new GregorianCalendar(Integer.parseInt(year),Integer.parseInt(month)-1 , Calendar.DAY_OF_MONTH);
						calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
						List<Task> taskListMissing = TimeUtility.checkDateAndAddMissing(calendar.getTime(), taskList);
						Iterator<Task> itr1=taskListMissing.iterator();
						while(itr1.hasNext()){					
							TaskForm taskform1=populateTaskFormFromTaskNew(itr1.next());
							taskFormList.add(taskform1);
						}
						taskForm.setTaskFormList(taskFormList);
						if(taskFormList.size()>0){
							superTaskFormList.add(taskForm);
						}
					}
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return superTaskFormList;
	}

	@Override
	public String getResourceLocation(int userId) throws Exception {
		// For getting resource location details.
		String locDetail = "";
		SaveTaskDao saveTaskDao=SaveTaskDaoFactory.getSaveTaskDao();
		locDetail = saveTaskDao.getResourceLocation(userId);		
		return locDetail;
	}
	@Override
	public List<String> getMissingDateEntry(List<TaskForm> tList, int userId) throws Exception {
		List<String> listOfDate = new ArrayList<String>();
		DateFormat df = new SimpleDateFormat("dd");
		List<Task> taskList = new ArrayList<Task>();
		for (TaskForm form : tList) {
			Task t1 = populateTaskFromForm(form, userId);
			taskList.add(t1);
		}
		List<Task> missingDateList = TimeUtility.checkDateAndAddMissing(Calendar.getInstance().getTime(), taskList);
		for (Task task : missingDateList) {
			if(task.getTask_description().equalsIgnoreCase("Efforts not entered.")){
				listOfDate.add(df.format(task.getTask_date()));
			}
		}
		return listOfDate;
	}
	@Override
	public String freezeTimesheet(Integer resource_id, Calendar cal) throws Exception {
		// Freezing timesheet entry for current month.
		String freezingStatus = null;
		SaveTaskDao saveTaskDao = SaveTaskDaoFactory.getSaveTaskDao();
		freezingStatus = saveTaskDao.freezeTimesheet(resource_id, cal);
		return freezingStatus;
	}
	@Override
	public String unfreezeTimesheet(Integer resource_id, Calendar cal) throws Exception {
		// unfreezing time entry for current month and all next coming month.
		String unfreezingStatus = null;
		SaveTaskDao saveTaskDao = SaveTaskDaoFactory.getSaveTaskDao();
		unfreezingStatus = saveTaskDao.unfreezeTimesheet(resource_id, cal);
		return unfreezingStatus;
	}
	@Override
	public List<String> getEnteredTimesheetDateForPreviousMonth(Calendar c1, int user_id) {
		// All dates from previous months timesheet entry.
		List<String> allEnteredDates = new ArrayList<String>();
		SaveTaskDao saveTaskDao = SaveTaskDaoFactory.getSaveTaskDao();
		allEnteredDates = saveTaskDao.getEnteredTimesheetDateForPreviousMonth(c1, user_id);
		return allEnteredDates;
	}
}
