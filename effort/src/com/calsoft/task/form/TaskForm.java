package com.calsoft.task.form;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import com.calsoft.task.service.SaveTaskService;
import com.calsoft.task.service.factory.SaveTaskServiceFactory;
import com.calsoft.user.model.User;

@SuppressWarnings({"serial","rawtypes","unused"})
public class TaskForm extends ActionForm{
	private List<TaskForm> taskFormList;
	private String userName;
	private int id;
	private String backlog_id;
	private String task_id;
	private String task_description;
	private String time;
	private String task_date;
	private String status;
	private User user;
	private List list;
	private int userId;	
	private String month;
	private String msg;
	private String newEntry;
	private Pattern pattern;
	private Matcher matcher;
	private String work_status ;
	private String entry_time;

	private static final String TIME24HOURS_PATTERN = "([01]?[0-9]|2[0-3]):([0-5][0-9]|[0-5])";  //([01]?[0-9]|2[0-3]):[0-5][0-9]|([01]?[0-9]|2[0-3] ).[0-5][0-9]
	private static final String TIME24HOURS_PATTERN1 = "([01]?[0-9]|2[0-3]).([0-5][0-9]|[0-5])";  //([01]?[0-9]|2[0-3]):[0-5][0-9]|([01]?[0-9]|2[0-3] ).[0-5][0-9]

	public List<TaskForm> getTaskFormList() {
		return taskFormList;
	}
	public void setTaskFormList(List<TaskForm> taskFormList) {
		this.taskFormList = taskFormList;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBacklog_id() {
		return backlog_id;
	}
	public void setBacklog_id(String backlog_id) {
		this.backlog_id = backlog_id;
	}
	public String getTask_id() {
		return task_id;
	}
	public void setTask_id(String task_id) {
		this.task_id = task_id;
	}
	public String getTask_description() {
		return task_description;
	}
	public void setTask_description(String task_description) {
		this.task_description = task_description;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getTask_date() {
		return task_date;
	}
	public void setTask_date(String task_date) {
		this.task_date = task_date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getNewEntry() {
		return newEntry;
	}
	public void setNewEntry(String newEntry) {
		this.newEntry = newEntry;
	}
	public String getWork_status() {
		return work_status;
	}
	public void setWork_status(String work_status) {
		this.work_status = work_status;
	}
	public String getEntry_time() {
		return entry_time;
	}
	public void setEntry_time(String entry_time) {
		this.entry_time = entry_time;
	}
	public void reset(ActionMapping mapping,HttpServletRequest request){
		this.backlog_id=null;
		this.task_id=null;
		this.task_description=null;
		this.time=null;
		this.task_date=null;
		this.status=null;
		List list=null;
		int id=0;
	}
	public boolean validate(final String time)
	{
		pattern = Pattern.compile(TIME24HOURS_PATTERN);
		matcher = pattern.matcher(time);
		boolean bol = false;
		bol = matcher.matches();
		if(!bol)
		{
			pattern = Pattern.compile(TIME24HOURS_PATTERN1);
			matcher = pattern.matcher(time);
			bol = matcher.matches();
		}
		return bol;
	}	

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) 
	{	   
		ActionErrors errors = new ActionErrors();
		String jsonString = request.getParameter("taskJson");
		if(jsonString!=null){     
           return errors;           //  Skipping form validation for Save all 
		}		
		String statusVal = getStatus();
		String timeVal = getTime();
		String backlogVal = getBacklog_id();
		String tasklogVal = getTask_id();
		String taskDateVal = getTask_date();

		String check="susi";
		request.setAttribute("check", check);
		request.setAttribute("monthForm", getMonth());
		request.setAttribute("stat", statusVal);
		String regex = "^[!@#$%^&*()_+/<>?;'\"~,|?`-[a-z][A-Z]]*";
		
		if(statusVal!=null && (statusVal.equalsIgnoreCase("Leave")||statusVal.equalsIgnoreCase("Public holiday")||statusVal.equalsIgnoreCase("Comp off")||statusVal.equalsIgnoreCase("Half Day")||statusVal.equalsIgnoreCase("Travel")||statusVal.equalsIgnoreCase("Meeting")||statusVal.equalsIgnoreCase("Down Time")))
		{	
			if(backlogVal == null || backlogVal == ""){
				setBacklog_id("NA");
			}
			if(tasklogVal == null || tasklogVal == ""){
				setTask_id("NA");
			}
			try{
				if(timeVal==null || timeVal=="" || timeVal==" "){
					errors.add("time", new ActionMessage("error.time.requiredHere"));
				}
				else if(timeVal.matches(regex)){
					errors.add("time", new ActionMessage("error.time.required"));
				}
				else if( timeVal.matches("([01]?[0-9]|2[0-3])")){
				}	    
				else if(timeVal!=null && timeVal!="" && timeVal.length()<=2 && timeVal.indexOf('.')==-1 && timeVal.charAt(0)!='-'&& timeVal.indexOf(':')==-1){
					if(Double.parseDouble(timeVal)>23){
						errors.add("time", new ActionMessage("error.time.requiredMAX23"));
					}
					else if(!(validate(timeVal))){	
						errors.add("time", new ActionMessage("error.time.required"));
					}
				}
				else if(timeVal!=null && timeVal!=""&& timeVal.length()>=2){
					if(timeVal.charAt(1)=='.' || timeVal.charAt(1)==':' || (timeVal.indexOf('.')!=-1 && timeVal.charAt(2)=='.') || (timeVal.indexOf(':')!=-1 &&timeVal.charAt(2)==':'))
					{						
						if(!(validate(timeVal))){
							errors.add("time", new ActionMessage("error.time.required"));
						}
						else{
						}						
					}
					else if(Double.parseDouble(timeVal)>23){
						errors.add("time", new ActionMessage("error.time.requiredMAX23"));
					}
				}
				else if(true){
				}								
			}
			catch (Exception e) {
				errors.add("time", new ActionMessage("error.time.required"));
			}
			if (taskDateVal == null ||taskDateVal.length() < 1) {
				errors.add("task_date", new ActionMessage("error.task_date.required"));
			}
			SaveTaskService saveTaskService = SaveTaskServiceFactory.getSaveTaskService();
			int userId = Integer.parseInt(request.getSession().getAttribute("user_id").toString());
			List<TaskForm> tList = null;
			try {
				tList = saveTaskService.getTaskDetails(month, userId);
			} 
			catch (Exception e){
				e.printStackTrace();
			}
			request.setAttribute("tList", tList);
			return errors;
		}
		else{
			if (backlogVal == null || backlogVal.length() < 1) {
				errors.add("backlog_id", new ActionMessage("error.backlog_id.required"));
			}
			if (tasklogVal == null || tasklogVal.length() < 1) {
				errors.add("task_id", new ActionMessage("error.task_id.required"));
			}
			if (statusVal == null || statusVal.length() < 1){
				errors.add("status", new ActionMessage("error.status.required"));
			}
			else{
			}
			try{
				if(timeVal==null || timeVal=="" || timeVal==" "){
					errors.add("time", new ActionMessage("error.time.requiredHere"));
				}
				else if( timeVal.matches(regex)){
					errors.add("time", new ActionMessage("error.time.required"));
				}
				else if( timeVal.matches("([01]?[0-9]|2[0-3])")){
				}	    
				else if(timeVal!=null && timeVal!="" && timeVal.length()<=2 && timeVal.indexOf('.')==-1 && timeVal.charAt(0)!='-'&&timeVal.indexOf(':')==-1){
					if(Double.parseDouble(timeVal)>23){
						errors.add("time", new ActionMessage("error.time.requiredMAX23"));
					}
					else if(!(validate(timeVal))){
						errors.add("time", new ActionMessage("error.time.required"));
					}
				}
				else if(timeVal!=null && timeVal!=""&&timeVal.length()>=2){
					if(timeVal.charAt(1)=='.' || timeVal.charAt(1)==':' || (timeVal.indexOf('.')!=-1 && timeVal.charAt(2)=='.') || (timeVal.indexOf(':')!=-1 &&timeVal.charAt(2)==':')){						
						if(!(validate(timeVal))){
							errors.add("time", new ActionMessage("error.time.required"));
						}
						else{}						
					}
					else if(Double.parseDouble(timeVal)>23){
						errors.add("time", new ActionMessage("error.time.requiredMAX23"));
					}
				}
				else if(true){}
			}
			catch (Exception e) {
				errors.add("time", new ActionMessage("error.time.required"));
			}											
			if (taskDateVal == null ||taskDateVal.length() < 1 ) {
				errors.add("task_date", new ActionMessage("error.task_date.required"));
			}
			SaveTaskService saveTaskService = SaveTaskServiceFactory.getSaveTaskService();
			int userId = Integer.parseInt(request.getSession().getAttribute("user_id").toString());
			List<TaskForm> tList = null;
			if(month!=null && userId!=0){
				try {
					tList = saveTaskService.getTaskDetails(month, userId);
				} 
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			request.setAttribute("tList", tList);
			return errors;
		}
	}
}
