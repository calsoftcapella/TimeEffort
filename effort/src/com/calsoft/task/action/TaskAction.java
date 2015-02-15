package com.calsoft.task.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import com.calsoft.factory.Factory;
import com.calsoft.task.form.TaskForm;
import com.calsoft.task.service.SaveTaskService;
import com.calsoft.task.service.factory.SaveTaskServiceFactory;
import com.calsoft.user.form.UserForm;
import com.calsoft.user.service.UserService;
import com.calsoft.util.TimeUtility;

public class TaskAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger("TaskAction");
	UserService userService = null;
	static int ID;

	// Method for saving single entry.
	public void saveAjax(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {	
		logger.info("You are inside saveAjax method");
		HttpSession session = request.getSession();
		String jsonString = request.getParameter("taskJson");
		if(session.getAttribute("user_id")!=null)  {
			int userId = Integer.parseInt(session.getAttribute("user_id").toString());
			int userIdFormJson = 0;
			try{
				TaskForm taskForm = (TaskForm) form;
				if(jsonString!=null){
					JSONArray object = JSONArray.fromObject(jsonString);
					for(int i = 0; i < object.size(); i++){
						JSONObject jObj = object.getJSONObject(i);	        	 						
						taskForm.setStatus(jObj.get("status").toString());
						taskForm.setBacklog_id(jObj.get("backlog_id").toString());
						taskForm.setTask_id(jObj.get("task_id").toString());
						taskForm.setTask_description(jObj.get("task_description").toString());
						taskForm.setTask_date(jObj.get("date").toString());
						taskForm.setWork_status(jObj.get("work_status").toString());
						taskForm.setTime(jObj.get("time").toString());
						userIdFormJson = Integer.parseInt(jObj.get("userId").toString());
					}		   
				}
				SaveTaskService saveTaskService = SaveTaskServiceFactory.getSaveTaskService();	
				String[] msgArray = new String[2];
				if(userIdFormJson!=0){
					msgArray = saveTaskService.saveTask(taskForm, userIdFormJson);	
				}
				else{
					msgArray = saveTaskService.saveTask(taskForm, userId);	
				}							
				if(msgArray[0].equalsIgnoreCase("Invalid entry for Time")){				
					response.getWriter().print("Invalid time Entry");
				}	
				else{
					response.getWriter().print(msgArray[1]);
				}
			}
			catch(Exception e){
				e.printStackTrace();
				response.getWriter().print("session expired");		
			}
		}
		else
			response.getWriter().print("session expired");
	}
	// Method for saving all new entry together.
	public void saveAllAjax(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {	
		logger.info("You are inside saveAllAjax method.");
		HttpSession session = request.getSession();
		String jsonString = request.getParameter("taskJson");			
		if(session.getAttribute("user_id")!=null) {
			int userId = Integer.parseInt(session.getAttribute("user_id").toString());	
			int userIdFormJson = 0;
			try{
				if(jsonString!=null){
					JSONArray object = JSONArray.fromObject(jsonString); 
					List<TaskForm> listForm = new ArrayList<TaskForm>();
					SaveTaskService saveTaskService = SaveTaskServiceFactory.getSaveTaskService();
					for(int i = 0; i < object.size(); i++){
						JSONObject jObj = object.getJSONObject(i);	        	 
						TaskForm tf = new TaskForm();
						tf.setStatus(jObj.get("status").toString());
						tf.setBacklog_id(jObj.get("backlog_id").toString());
						tf.setTask_id(jObj.get("task_id").toString());
						tf.setTask_description(jObj.get("task_description").toString());
						tf.setTask_date(jObj.get("date").toString());
						tf.setWork_status(jObj.get("work_status").toString());
						tf.setTime(jObj.get("time").toString());
						userIdFormJson = Integer.parseInt(jObj.get("userId").toString());
						listForm.add(tf);
					}		          
					if(listForm!=null){
						List<String> message = null;
						if(userIdFormJson!=0){
							message = saveTaskService.saveAllTask(listForm,userIdFormJson);
						}
						else{
							message = saveTaskService.saveAllTask(listForm,userId);
						}
						response.getWriter().print(message);
					}
				}				
			}
			catch(Exception e){
				e.printStackTrace();
				response.getWriter().print("session expired");			
			}
		}
		else
			response.getWriter().print("session expired");
	}
	// Method for getting task detail month wise after month onChange.
	public ActionForward getDetails(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception{
		String forwardedPage = "";
		HttpSession session = request.getSession();
		if(session.getAttribute("userName")!=null){
			SaveTaskService saveTaskService = SaveTaskServiceFactory.getSaveTaskService();
			int userId = Integer.parseInt(session.getAttribute("user_id").toString());
			TaskForm taskForm = (TaskForm) form;
			DateFormat df = new SimpleDateFormat("yyyy-MM");
			List<TaskForm> tList = null;
			String month = taskForm.getMonth();
			Calendar cal = Calendar.getInstance();			
			try {
				tList = saveTaskService.getTaskDetails(month, userId);
				request.setAttribute("tList", tList);					
				cal.setTime(df.parse(month));
				cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
				userService = Factory.getUserService();
				try{
					int userManagerId = userService.getAdminUserId();
					List<UserForm> listForm = null;
					if(userManagerId==userId){
						// Write logic
						listForm = userService.getAllocatedResourcesBasedOnStartAndExitDate(userId, cal);
						request.setAttribute("selectResourceBasedOnUserId", userManagerId);
						request.setAttribute("userListSelection", listForm);
					}
				}
				catch(Exception e){
					e.printStackTrace();
				}
			} catch(Exception e){
				e.printStackTrace();	
				throw new Exception();
			}
			// Added for missing entry reminder Table	
			UserForm userDetail = userService.getUsernameFromId(userId);
			Date resource_start_dt = userDetail.getStart_date();
			String ressource_exit_dt_in_string = userDetail.getExit_date();								
			Calendar cal_for_resource_start_dt = null;	
			if(resource_start_dt != null){
				cal_for_resource_start_dt = Calendar.getInstance();
				cal_for_resource_start_dt.setTime(resource_start_dt);
			}
			Calendar cal_for_resource_end_dt = null;
			if(ressource_exit_dt_in_string != null){
				cal_for_resource_end_dt = Calendar.getInstance();
				cal_for_resource_end_dt.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(ressource_exit_dt_in_string));
			}
			Calendar cal_for_current_dt  = Calendar.getInstance();
			if(month != null){
				cal_for_current_dt.setTime(df.parse(month));
			}
			List<String> allmissingDateList = new ArrayList<String>();
			if(cal_for_resource_start_dt == null && cal_for_resource_end_dt == null){
				allmissingDateList = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDateIn_dd_format(null, null, cal_for_current_dt);
			}else if(cal_for_resource_start_dt != null && cal_for_resource_end_dt == null){
				if(compareCalendarEqualityBasedOnMonthAndYear(cal_for_current_dt, cal_for_resource_start_dt)){
					allmissingDateList = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDateIn_dd_format(cal_for_resource_start_dt, null, cal_for_current_dt);
				}else if((cal_for_current_dt.get(Calendar.YEAR) > cal_for_resource_start_dt.get(Calendar.YEAR)) 
						|| cal_for_current_dt.get(Calendar.MONTH) >= cal_for_resource_start_dt.get(Calendar.MONTH) && cal_for_current_dt.get(Calendar.YEAR) == cal_for_resource_start_dt.get(Calendar.YEAR) ){
					allmissingDateList = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDateIn_dd_format(null, null, cal_for_current_dt);
				}				
			}else if(cal_for_resource_start_dt == null && cal_for_resource_end_dt != null){
				if(compareCalendarEqualityBasedOnMonthAndYear(cal_for_current_dt, cal_for_resource_end_dt)){
					allmissingDateList = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDateIn_dd_format(null, cal_for_resource_end_dt, cal_for_current_dt);
				}else if((cal_for_current_dt.get(Calendar.YEAR) < cal_for_resource_end_dt.get(Calendar.YEAR)) 
						|| cal_for_current_dt.get(Calendar.MONTH) <= cal_for_resource_end_dt.get(Calendar.MONTH) && cal_for_current_dt.get(Calendar.YEAR) == cal_for_resource_end_dt.get(Calendar.YEAR) ){
					allmissingDateList = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDateIn_dd_format(null, null, cal_for_current_dt);
				}		
			}else if(cal_for_resource_start_dt != null && cal_for_resource_end_dt != null){
				if( (compareCalendarEqualityBasedOnMonthAndYear(cal_for_current_dt, cal_for_resource_start_dt)  
						&& compareCalendarEqualityBasedOnMonthAndYear(cal_for_current_dt, cal_for_resource_end_dt)) ){

					allmissingDateList = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDateIn_dd_format(cal_for_resource_start_dt, cal_for_resource_end_dt, cal_for_current_dt);
				}else if(!(compareCalendarEqualityBasedOnMonthAndYear(cal_for_current_dt, cal_for_resource_start_dt))
						&& (compareCalendarEqualityBasedOnMonthAndYear(cal_for_current_dt, cal_for_resource_end_dt))){

					allmissingDateList = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDateIn_dd_format(null, cal_for_resource_end_dt, cal_for_current_dt);
				}else if((compareCalendarEqualityBasedOnMonthAndYear(cal_for_current_dt, cal_for_resource_start_dt))
						&& !(compareCalendarEqualityBasedOnMonthAndYear(cal_for_current_dt, cal_for_resource_end_dt)) ){

					allmissingDateList = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDateIn_dd_format(cal_for_resource_start_dt, null, cal_for_current_dt);
				}else if( comapareCalendarMonthAndYearBasedOnMonthAndYear(cal_for_current_dt, cal_for_resource_start_dt, null)
						&& comapareCalendarMonthAndYearBasedOnMonthAndYear(cal_for_current_dt, null, cal_for_resource_end_dt) ){						
					allmissingDateList = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDateIn_dd_format(null, null, cal_for_current_dt);
				}			
			}
			logger.info("Printing missing date list "+allmissingDateList);
			request.setAttribute("allmissingDateList", allmissingDateList);
			String locDetail = saveTaskService.getResourceLocation(userId);
			Date freezeDate = userDetail.getFreeze_timesheet();
			if(freezeDate != null){
				Calendar calWithFreezingDate = Calendar.getInstance();
				calWithFreezingDate.setTime(freezeDate);
				cal.setTime(df.parse(month));
				//Checking if freezing date exceeds than selectedMonth then blocking new entry.
				if(calWithFreezingDate.after(cal) || calWithFreezingDate.equals(cal)){
					forwardedPage = "timeEntryFreezedPage";
				}
				else{
					forwardedPage = "getDetailSuccess";
				}
			}
			else{
				forwardedPage = "getDetailSuccess";
			}	

			/* Update for Alert Box changes in time entry page */		
			//Checking previous month time entry for alert Notification on time-entry page
			Calendar c1 = Calendar.getInstance();			
			c1.add(Calendar.MONTH, -1);
			List<String> taskFormList = new ArrayList<String>();
			List<String> previousMonthWorkingDates = new ArrayList<String>();
			Calendar month_cal = Calendar.getInstance();
			if(df.format(month_cal.getTime()).equals(month) ){
				taskFormList = saveTaskService.getEnteredTimesheetDateForPreviousMonth(c1, userId);
				if(cal_for_resource_start_dt == null && cal_for_resource_end_dt == null){
					previousMonthWorkingDates = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDateForPreviousMonthEntryNotification(null, null, c1);
				}else if(cal_for_resource_start_dt != null && cal_for_resource_end_dt == null && !( compareCalendarEqualityBasedOnMonthAndYear(cal_for_resource_start_dt, Calendar.getInstance()) )  ){
					previousMonthWorkingDates = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDateForPreviousMonthEntryNotification(cal_for_resource_start_dt, null, c1);
				}else if(cal_for_resource_start_dt == null && cal_for_resource_end_dt != null){
					previousMonthWorkingDates = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDateForPreviousMonthEntryNotification(null, cal_for_resource_end_dt, c1);
				}else if(cal_for_resource_start_dt != null && cal_for_resource_end_dt != null && ( !compareCalendarEqualityBasedOnMonthAndYear(cal_for_resource_start_dt, Calendar.getInstance()) && comapareCalendarMonthAndYearBasedOnMonthAndYear(c1, null, cal_for_resource_end_dt))  ){
					previousMonthWorkingDates = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDateForPreviousMonthEntryNotification(cal_for_resource_start_dt, cal_for_resource_end_dt, c1);
				}				
				if(!previousMonthWorkingDates.isEmpty() && (taskFormList.isEmpty() || !taskFormList.containsAll(previousMonthWorkingDates))){
					request.setAttribute("showAlertNotification", "showAlertNotification");
				}
			}
			/*Update for alert box ends here.*/		
			request.setAttribute("resourceLocation", locDetail);
			request.setAttribute("month", month);
			return mapping.findForward(forwardedPage);
		}
		else
			throw new Exception();
	}
	// Method for deleting entry.
	public void delete(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception{
		HttpSession session = request.getSession();
		if(session.getAttribute("userName")!=null){
			try{
				SaveTaskService saveTaskService = SaveTaskServiceFactory.getSaveTaskService();
				int id = Integer.parseInt(request.getParameter("id"));
				saveTaskService.doDelete(id);
			}
			catch(Exception e){
				e.printStackTrace();
				response.getWriter().print("session expired");
			}
		}
		else
			response.getWriter().print("session expired");
	}
	// Method for saving entry after editing.
	public void saveEditAjax(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception{
		HttpSession session = request.getSession();
		if(session.getAttribute("user_id")!=null)   {
			try{
				String jsonString = request.getParameter("taskJson");					
				TaskForm taskForm = (TaskForm) form;	
				SaveTaskService saveTaskService = SaveTaskServiceFactory.getSaveTaskService();
				int userId = Integer.parseInt(session.getAttribute("user_id").toString());
				int id = 0;
				int userIdFormJson = 0;
				if(jsonString!=null){
					JSONArray object = JSONArray.fromObject(jsonString); 
					for(int i = 0; i < object.size(); i++){
						JSONObject jObj = object.getJSONObject(i);	        	 
						taskForm.setStatus(jObj.get("status").toString());
						taskForm.setBacklog_id(jObj.get("backlog_id").toString());
						taskForm.setTask_id(jObj.get("task_id").toString());
						taskForm.setTask_description(jObj.get("task_description").toString());
						taskForm.setTask_date(jObj.get("date").toString());
						taskForm.setWork_status(jObj.get("work_status").toString());
						taskForm.setTime(jObj.get("time").toString());
						taskForm.setId((Integer)jObj.get("id"));
						userIdFormJson = Integer.parseInt(jObj.get("userId").toString());
						id = taskForm.getId();
					}	
				}
				String msg = "";
				if(userIdFormJson!=0){
					msg = saveTaskService.editsaveTask(taskForm, id, userIdFormJson);
				}
				else{
					msg = saveTaskService.editsaveTask(taskForm, id, userId);
				}
				if(msg.equalsIgnoreCase("Invalid entry for Time")){
					response.getWriter().print("Invalid time Entry");
				}
				else if(msg.equalsIgnoreCase("Data edited sucessfully")){
					response.getWriter().print("Data edited sucessfully");
				}
			}
			catch (Exception e){
				e.printStackTrace();
				response.getWriter().print("session expired");
			}
		}
		else{
			response.getWriter().print("session expired");
		}
	}
	// Method for getting time entry page.
	public ActionForward onClickTask(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {		
		String forwardedPage = "";
		HttpSession s1 = request.getSession();
		s1.removeAttribute("selectedDate");
		s1.removeAttribute("conList");
		s1.removeAttribute("userList");
		s1.removeAttribute("conListUpdate");
		if(s1.getAttribute("userName")!=null){
			try{
				SaveTaskService saveTaskService = SaveTaskServiceFactory.getSaveTaskService();
				Calendar cal = Calendar.getInstance();
				DateFormat df = new SimpleDateFormat("yyyy-MM");
				String year_month = df.format(cal.getTime());
				List<TaskForm> tList = null;
				int userId = Integer.parseInt(s1.getAttribute("user_id").toString());
				tList = saveTaskService.getTaskDetails(year_month, userId);
				String locDetail = saveTaskService.getResourceLocation(userId);			
				userService = Factory.getUserService();
				try{
					int userManagerId = userService.getAdminUserId();
					List<UserForm> listForm = null;
					if(userManagerId==userId){
						// Write logic here for resource wise time entry page.
						listForm = userService.getAllocatedResourcesBasedOnStartAndExitDate(userId, cal);
						request.setAttribute("selectResourceBasedOnUserId", userManagerId);											
						request.setAttribute("userListSelection", listForm);
					}
				}
				catch(Exception e){
					e.printStackTrace();
				}
				// Added for missing entry reminder Table
				UserForm userDetail = userService.getUsernameFromId(userId);
				Date resource_start_dt = userDetail.getStart_date();
				String ressource_exit_dt_in_string = userDetail.getExit_date();								
				Calendar cal_for_resource_start_dt = null;	
				if(resource_start_dt != null){
					cal_for_resource_start_dt = Calendar.getInstance();
					cal_for_resource_start_dt.setTime(resource_start_dt);
				}
				Calendar cal_for_resource_end_dt = null;
				if(ressource_exit_dt_in_string != null){
					cal_for_resource_end_dt = Calendar.getInstance();
					cal_for_resource_end_dt.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(ressource_exit_dt_in_string));
				}
				Calendar cal_for_current_dt  = Calendar.getInstance();
				List<String> allmissingDateList = new ArrayList<String>();
				if(cal_for_resource_start_dt == null && cal_for_resource_end_dt == null){
					allmissingDateList = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDateIn_dd_format(null, null, cal_for_current_dt);
				}else if(cal_for_resource_start_dt != null && cal_for_resource_end_dt == null){
					if(compareCalendarEqualityBasedOnMonthAndYear(cal_for_current_dt, cal_for_resource_start_dt)){
						allmissingDateList = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDateIn_dd_format(cal_for_resource_start_dt, null, cal_for_current_dt);
					}else if((cal_for_current_dt.get(Calendar.YEAR) > cal_for_resource_start_dt.get(Calendar.YEAR)) 
							|| cal_for_current_dt.get(Calendar.MONTH) >= cal_for_resource_start_dt.get(Calendar.MONTH) && cal_for_current_dt.get(Calendar.YEAR) == cal_for_resource_start_dt.get(Calendar.YEAR) ){
						allmissingDateList = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDateIn_dd_format(null, null, cal_for_current_dt);
					}				
				}else if(cal_for_resource_start_dt == null && cal_for_resource_end_dt != null){
					if(compareCalendarEqualityBasedOnMonthAndYear(cal_for_current_dt, cal_for_resource_end_dt)){
						allmissingDateList = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDateIn_dd_format(null, cal_for_resource_end_dt, cal_for_current_dt);
					}else if((cal_for_current_dt.get(Calendar.YEAR) < cal_for_resource_end_dt.get(Calendar.YEAR)) 
							|| cal_for_current_dt.get(Calendar.MONTH) <= cal_for_resource_end_dt.get(Calendar.MONTH) && cal_for_current_dt.get(Calendar.YEAR) == cal_for_resource_end_dt.get(Calendar.YEAR) ){
						allmissingDateList = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDateIn_dd_format(null, null, cal_for_current_dt);
					}		
				}else if(cal_for_resource_start_dt != null && cal_for_resource_end_dt != null){
					if( (compareCalendarEqualityBasedOnMonthAndYear(cal_for_current_dt, cal_for_resource_start_dt)  
							&& compareCalendarEqualityBasedOnMonthAndYear(cal_for_current_dt, cal_for_resource_end_dt)) ){

						allmissingDateList = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDateIn_dd_format(cal_for_resource_start_dt, cal_for_resource_end_dt, cal_for_current_dt);
					}else if(!(compareCalendarEqualityBasedOnMonthAndYear(cal_for_current_dt, cal_for_resource_start_dt))
							&& (compareCalendarEqualityBasedOnMonthAndYear(cal_for_current_dt, cal_for_resource_end_dt))){

						allmissingDateList = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDateIn_dd_format(null, cal_for_resource_end_dt, cal_for_current_dt);
					}else if((compareCalendarEqualityBasedOnMonthAndYear(cal_for_current_dt, cal_for_resource_start_dt))
							&& !(compareCalendarEqualityBasedOnMonthAndYear(cal_for_current_dt, cal_for_resource_end_dt)) ){

						allmissingDateList = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDateIn_dd_format(cal_for_resource_start_dt, null, cal_for_current_dt);
					}else if( comapareCalendarMonthAndYearBasedOnMonthAndYear(cal_for_current_dt, cal_for_resource_start_dt, null)
							&& comapareCalendarMonthAndYearBasedOnMonthAndYear(cal_for_current_dt, null, cal_for_resource_end_dt) ){						
						allmissingDateList = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDateIn_dd_format(null, null, cal_for_current_dt);
					}			
				}
				request.setAttribute("allmissingDateList", allmissingDateList);
				// Check for freezing status				
				Date freezeDate = userDetail.getFreeze_timesheet();
				if(freezeDate != null){
					Calendar calWithFreezingDate = Calendar.getInstance();
					calWithFreezingDate.setTime(freezeDate);	
					cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
					cal.set(Calendar.HOUR_OF_DAY, 0);
					cal.set(Calendar.MINUTE, 0);
					cal.set(Calendar.SECOND, 0);
					cal.set(Calendar.MILLISECOND, 0);
					//Checking if freezing date exceeds than currentDate then blocking new entry.
					if(calWithFreezingDate.after(cal) || calWithFreezingDate.equals(cal)){
						forwardedPage = "timeEntryFreezedPage";
					}
					else{						
						forwardedPage = "onClickTask";
					}
				}
				else{
					forwardedPage = "onClickTask";
				}
				// Checking previous month time entry for alert Notification on time-entry page
				Calendar c1 = Calendar.getInstance();
				c1.add(Calendar.MONTH, -1);
				List<String> previousMonthWorkingDates = new ArrayList<String>();
				List<String> taskFormList = saveTaskService.getEnteredTimesheetDateForPreviousMonth(c1, userId);
				if(cal_for_resource_start_dt == null && cal_for_resource_end_dt == null){
					previousMonthWorkingDates = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDateForPreviousMonthEntryNotification(null, null, c1);
				}else if(cal_for_resource_start_dt != null && cal_for_resource_end_dt == null && !( compareCalendarEqualityBasedOnMonthAndYear(cal_for_resource_start_dt, Calendar.getInstance()) )  ){
					previousMonthWorkingDates = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDateForPreviousMonthEntryNotification(cal_for_resource_start_dt, null, c1);
				}else if(cal_for_resource_start_dt == null && cal_for_resource_end_dt != null){
					previousMonthWorkingDates = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDateForPreviousMonthEntryNotification(null, cal_for_resource_end_dt, c1);
				}else if(cal_for_resource_start_dt != null && cal_for_resource_end_dt != null && ( !compareCalendarEqualityBasedOnMonthAndYear(cal_for_resource_start_dt, Calendar.getInstance()) && comapareCalendarMonthAndYearBasedOnMonthAndYear(c1, null, cal_for_resource_end_dt))  ){
					previousMonthWorkingDates = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDateForPreviousMonthEntryNotification(cal_for_resource_start_dt, cal_for_resource_end_dt, c1);
				}
				logger.info("Printing two list content from TaskAction onclick Method"+taskFormList+" list 2 \n"+previousMonthWorkingDates);				
				if(!previousMonthWorkingDates.isEmpty() && (taskFormList.isEmpty() || !taskFormList.containsAll(previousMonthWorkingDates))){
					request.setAttribute("showAlertNotification", "showAlertNotification");
				}
				// Updates ends here.
				request.setAttribute("resourceLocation", locDetail);
				request.setAttribute("tList", tList);
			}
			catch(Exception e){
				e.printStackTrace();	
				throw new Exception();
			}
			return mapping.findForward(forwardedPage);
		}
		else
			throw new Exception();
	}

	private boolean compareCalendarEqualityBasedOnMonthAndYear(Calendar c1, Calendar c2){
		boolean comapreStatus = false;
		if((c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)) && (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR))){
			comapreStatus = true;
		}
		return comapreStatus;
	}

	private boolean comapareCalendarMonthAndYearBasedOnMonthAndYear(Calendar currentCal, Calendar startCal, Calendar exitCal){
		boolean statusBol = false;
		if(startCal != null){
			if((startCal.get(Calendar.YEAR) < currentCal.get(Calendar.YEAR)) ||
					(currentCal.get(Calendar.MONTH) >= startCal.get(Calendar.MONTH) && currentCal.get(Calendar.YEAR) == startCal.get(Calendar.YEAR))){
				statusBol = true;
			}
		}else if(exitCal != null){
			if((exitCal.get(Calendar.YEAR) > currentCal.get(Calendar.YEAR)) ||
					(currentCal.get(Calendar.MONTH) <= exitCal.get(Calendar.MONTH) && currentCal.get(Calendar.YEAR) == exitCal.get(Calendar.YEAR))){
				statusBol = true;
			}
		}
		return statusBol;
	}

	// Getting time entry details resource wise.
	public ActionForward getDetailsBasedOnResourceName(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception{
		String forwardedPage = "";
		HttpSession s1 = request.getSession();
		if(s1.getAttribute("userName")!=null){
			TaskForm taskForm = (TaskForm) form;
			String month = taskForm.getMonth();
			int selectedUserId = taskForm.getUserId();
			int userIdFromSession = Integer.parseInt(s1.getAttribute("user_id").toString());
			List<TaskForm> tList = null;
			SaveTaskService saveTaskService = SaveTaskServiceFactory.getSaveTaskService();
			tList = saveTaskService.getTaskDetails(month, selectedUserId);
			request.setAttribute("tList", tList);
			Calendar cal = Calendar.getInstance();
			DateFormat df = new SimpleDateFormat("yyyy-MM");
			cal.setTime(df.parse(month));
			cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
			userService = Factory.getUserService();
			try{
				int userManagerId = userService.getAdminUserId();
				List<UserForm> listForm = null;
				if(userManagerId==userIdFromSession){
					listForm = userService.getAllocatedResourcesBasedOnStartAndExitDate(userIdFromSession, cal);
					request.setAttribute("selectResourceBasedOnUserId", selectedUserId);
					request.setAttribute("userListSelection", listForm);
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			// Added for missing entry reminder Table
			List<String> allmissingDateList = new ArrayList<String>();
			if(selectedUserId != 0){
				UserForm userDetail = userService.getUsernameFromId(selectedUserId);
				Date resource_start_dt = userDetail.getStart_date();
				String ressource_exit_dt_in_string = userDetail.getExit_date();								
				Calendar cal_for_resource_start_dt = null;	
				if(resource_start_dt != null){
					cal_for_resource_start_dt = Calendar.getInstance();
					cal_for_resource_start_dt.setTime(resource_start_dt);
				}
				Calendar cal_for_resource_end_dt = null;
				if(ressource_exit_dt_in_string != null){
					cal_for_resource_end_dt = Calendar.getInstance();
					cal_for_resource_end_dt.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(ressource_exit_dt_in_string));
				}
				Calendar cal_for_current_dt  = Calendar.getInstance();
				cal_for_current_dt.setTime(df.parse(month));
				if(cal_for_resource_start_dt == null && cal_for_resource_end_dt == null){
					allmissingDateList = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDateIn_dd_format(null, null, cal_for_current_dt);
				}else if(cal_for_resource_start_dt != null && cal_for_resource_end_dt == null){
					if(compareCalendarEqualityBasedOnMonthAndYear(cal_for_current_dt, cal_for_resource_start_dt)){
						allmissingDateList = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDateIn_dd_format(cal_for_resource_start_dt, null, cal_for_current_dt);
					}else if((cal_for_current_dt.get(Calendar.YEAR) > cal_for_resource_start_dt.get(Calendar.YEAR)) 
							|| cal_for_current_dt.get(Calendar.MONTH) >= cal_for_resource_start_dt.get(Calendar.MONTH) && cal_for_current_dt.get(Calendar.YEAR) == cal_for_resource_start_dt.get(Calendar.YEAR) ){
						allmissingDateList = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDateIn_dd_format(null, null, cal_for_current_dt);
					}				
				}else if(cal_for_resource_start_dt == null && cal_for_resource_end_dt != null){
					if(compareCalendarEqualityBasedOnMonthAndYear(cal_for_current_dt, cal_for_resource_end_dt)){
						allmissingDateList = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDateIn_dd_format(null, cal_for_resource_end_dt, cal_for_current_dt);
					}else if((cal_for_current_dt.get(Calendar.YEAR) < cal_for_resource_end_dt.get(Calendar.YEAR)) 
							|| cal_for_current_dt.get(Calendar.MONTH) <= cal_for_resource_end_dt.get(Calendar.MONTH) && cal_for_current_dt.get(Calendar.YEAR) == cal_for_resource_end_dt.get(Calendar.YEAR) ){
						allmissingDateList = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDateIn_dd_format(null, null, cal_for_current_dt);
					}		
				}else if(cal_for_resource_start_dt != null && cal_for_resource_end_dt != null){
					if( (compareCalendarEqualityBasedOnMonthAndYear(cal_for_current_dt, cal_for_resource_start_dt)  
							&& compareCalendarEqualityBasedOnMonthAndYear(cal_for_current_dt, cal_for_resource_end_dt)) ){

						allmissingDateList = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDateIn_dd_format(cal_for_resource_start_dt, cal_for_resource_end_dt, cal_for_current_dt);
					}else if(!(compareCalendarEqualityBasedOnMonthAndYear(cal_for_current_dt, cal_for_resource_start_dt))
							&& (compareCalendarEqualityBasedOnMonthAndYear(cal_for_current_dt, cal_for_resource_end_dt))){

						allmissingDateList = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDateIn_dd_format(null, cal_for_resource_end_dt, cal_for_current_dt);
					}else if((compareCalendarEqualityBasedOnMonthAndYear(cal_for_current_dt, cal_for_resource_start_dt))
							&& !(compareCalendarEqualityBasedOnMonthAndYear(cal_for_current_dt, cal_for_resource_end_dt)) ){

						allmissingDateList = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDateIn_dd_format(cal_for_resource_start_dt, null, cal_for_current_dt);
					}else if( comapareCalendarMonthAndYearBasedOnMonthAndYear(cal_for_current_dt, cal_for_resource_start_dt, null)
							&& comapareCalendarMonthAndYearBasedOnMonthAndYear(cal_for_current_dt, null, cal_for_resource_end_dt) ){						
						allmissingDateList = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDateIn_dd_format(null, null, cal_for_current_dt);
					}			
				}
			}
			request.setAttribute("allmissingDateList", allmissingDateList);

			// Update for freezing timesheet.
			UserForm userDetail = userService.getUsernameFromId(selectedUserId);
			Date freezeDate = userDetail.getFreeze_timesheet();
			if(freezeDate != null){
				Calendar calWithFreezingDate = Calendar.getInstance();
				calWithFreezingDate.setTime(freezeDate);
				cal.setTime(df.parse(month));
				//Checking if freezing date exceeds than selectedMonth then blocking new entry.
				if(calWithFreezingDate.after(cal) || calWithFreezingDate.equals(cal)){
					forwardedPage = "timeEntryFreezedPage";
				}
				else{
					forwardedPage = "getDetailSuccess";
				}
			}
			else{
				forwardedPage = "getDetailSuccess";
			}	
			String locDetail = saveTaskService.getResourceLocation(selectedUserId);
			request.setAttribute("resourceLocation", locDetail);
			request.setAttribute("month", month);
			return mapping.findForward(forwardedPage);
		}
		else
			throw new Exception();
	}
	// Checking session before providing dynamic row to resources for adding entry.
	public void checkSessionValidation(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception{
		HttpSession s1 = request.getSession();
		logger.info("Printing from checkSessionValidation Method");
		if(s1.getAttribute("userName")==null){
			response.getWriter().print("session expired");
		}
	}
	public ActionForward freezeTimesheet(ActionMapping map,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		// Freezing timesheet for selected resources.
		logger.info("Printing from Task Action freezeTimesheet Method.");
		String forwardedPage = "";
		HttpSession s1 = request.getSession();
		SaveTaskService saveTaskService = SaveTaskServiceFactory.getSaveTaskService();
		if(s1.getAttribute("userName")!=null){
			try{				
				String task_month_year = request.getParameter("task_month");		
				Integer resource_id = Integer.parseInt(request.getParameter("resource_id"));		
				DateFormat df = new SimpleDateFormat("yyyy-MM");
				Calendar cal = Calendar.getInstance();
				cal.setTime(df.parse(task_month_year));
				cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
				saveTaskService.freezeTimesheet(resource_id, cal);
				int userIdFromSession = Integer.parseInt(s1.getAttribute("user_id").toString());
				List<TaskForm> tList = null;
				tList = saveTaskService.getTaskDetails(task_month_year, resource_id);
				request.setAttribute("tList", tList);
				userService = Factory.getUserService();
				try{
					List<UserForm> listForm = null;
					listForm = userService.getAllocatedResourcesBasedOnStartAndExitDate(userIdFromSession, cal);
					request.setAttribute("userListSelection", listForm);
					request.setAttribute("selectResourceBasedOnUserId", resource_id);
				}
				catch(Exception e){
					e.printStackTrace();
				}
				// Added for missing entry reminder Table
				List<String> allmissingDateList = new ArrayList<String>();
				UserForm userDetail = userService.getUsernameFromId(resource_id);
				Date resource_start_dt = userDetail.getStart_date();
				String ressource_exit_dt_in_string = userDetail.getExit_date();								
				Calendar cal_for_resource_start_dt = null;	
				if(resource_start_dt != null){
					cal_for_resource_start_dt = Calendar.getInstance();
					cal_for_resource_start_dt.setTime(resource_start_dt);
				}
				Calendar cal_for_resource_end_dt = null;
				if(ressource_exit_dt_in_string != null){
					cal_for_resource_end_dt = Calendar.getInstance();
					cal_for_resource_end_dt.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(ressource_exit_dt_in_string));
				}
				Calendar cal_for_current_dt  = Calendar.getInstance();
				cal_for_current_dt.setTime(df.parse(task_month_year));
				if(cal_for_resource_start_dt == null && cal_for_resource_end_dt == null){
					allmissingDateList = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDateIn_dd_format(null, null, cal_for_current_dt);
				}else if(cal_for_resource_start_dt != null && cal_for_resource_end_dt == null){
					if(compareCalendarEqualityBasedOnMonthAndYear(cal_for_current_dt, cal_for_resource_start_dt)){
						allmissingDateList = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDateIn_dd_format(cal_for_resource_start_dt, null, cal_for_current_dt);
					}else if((cal_for_current_dt.get(Calendar.YEAR) > cal_for_resource_start_dt.get(Calendar.YEAR)) 
							|| cal_for_current_dt.get(Calendar.MONTH) >= cal_for_resource_start_dt.get(Calendar.MONTH) && cal_for_current_dt.get(Calendar.YEAR) == cal_for_resource_start_dt.get(Calendar.YEAR) ){
						allmissingDateList = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDateIn_dd_format(null, null, cal_for_current_dt);
					}				
				}else if(cal_for_resource_start_dt == null && cal_for_resource_end_dt != null){
					if(compareCalendarEqualityBasedOnMonthAndYear(cal_for_current_dt, cal_for_resource_end_dt)){
						allmissingDateList = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDateIn_dd_format(null, cal_for_resource_end_dt, cal_for_current_dt);
					}else if((cal_for_current_dt.get(Calendar.YEAR) < cal_for_resource_end_dt.get(Calendar.YEAR)) 
							|| cal_for_current_dt.get(Calendar.MONTH) <= cal_for_resource_end_dt.get(Calendar.MONTH) && cal_for_current_dt.get(Calendar.YEAR) == cal_for_resource_end_dt.get(Calendar.YEAR) ){
						allmissingDateList = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDateIn_dd_format(null, null, cal_for_current_dt);
					}		
				}else if(cal_for_resource_start_dt != null && cal_for_resource_end_dt != null){
					if( (compareCalendarEqualityBasedOnMonthAndYear(cal_for_current_dt, cal_for_resource_start_dt)  
							&& compareCalendarEqualityBasedOnMonthAndYear(cal_for_current_dt, cal_for_resource_end_dt)) ){

						allmissingDateList = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDateIn_dd_format(cal_for_resource_start_dt, cal_for_resource_end_dt, cal_for_current_dt);
					}else if(!(compareCalendarEqualityBasedOnMonthAndYear(cal_for_current_dt, cal_for_resource_start_dt))
							&& (compareCalendarEqualityBasedOnMonthAndYear(cal_for_current_dt, cal_for_resource_end_dt))){

						allmissingDateList = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDateIn_dd_format(null, cal_for_resource_end_dt, cal_for_current_dt);
					}else if((compareCalendarEqualityBasedOnMonthAndYear(cal_for_current_dt, cal_for_resource_start_dt))
							&& !(compareCalendarEqualityBasedOnMonthAndYear(cal_for_current_dt, cal_for_resource_end_dt)) ){

						allmissingDateList = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDateIn_dd_format(cal_for_resource_start_dt, null, cal_for_current_dt);
					}else if( comapareCalendarMonthAndYearBasedOnMonthAndYear(cal_for_current_dt, cal_for_resource_start_dt, null)
							&& comapareCalendarMonthAndYearBasedOnMonthAndYear(cal_for_current_dt, null, cal_for_resource_end_dt) ){						
						allmissingDateList = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDateIn_dd_format(null, null, cal_for_current_dt);
					}			
				}
				request.setAttribute("allmissingDateList", allmissingDateList);

				// Update for freezing timesheet.
				Date freezeDate = userDetail.getFreeze_timesheet();
				if(freezeDate != null){
					Calendar calWithFreezingDate = Calendar.getInstance();
					calWithFreezingDate.setTime(freezeDate);
					cal.setTime(df.parse(task_month_year));
					//Checking if freezing date exceeds than selectedMonth then blocking new entry.
					if(calWithFreezingDate.after(cal) || calWithFreezingDate.equals(cal)){
						forwardedPage = "timeEntryFreezedPage";
					}
					else{
						forwardedPage = "getDetailSuccess";
					}
				}
				else{
					forwardedPage = "getDetailSuccess";
				}	
				String locDetail = saveTaskService.getResourceLocation(resource_id);
				request.setAttribute("resourceLocation", locDetail);
				request.setAttribute("month", task_month_year);
			}
			catch (Exception e) {
				logger.error(e);
				throw new Exception();
			}
		}
		else{
			throw new Exception();
		}
		return map.findForward(forwardedPage);
	}

	public ActionForward unfreezeTimesheet(ActionMapping map,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		// unfreezeTimesheet timesheet for selected resources.
		logger.info("Printing from TaskAction unfreezeTimesheet Method.");
		String forwardedPage = "";
		HttpSession s1 = request.getSession();
		SaveTaskService saveTaskService = SaveTaskServiceFactory.getSaveTaskService();
		if(s1.getAttribute("userName")!=null){
			try{				
				String task_month_year = request.getParameter("task_month");		
				Integer resource_id = Integer.parseInt(request.getParameter("resource_id"));		
				DateFormat df = new SimpleDateFormat("yyyy-MM");
				Calendar cal = Calendar.getInstance();
				cal.setTime(df.parse(task_month_year));
				saveTaskService.unfreezeTimesheet(resource_id, cal);				
				Calendar cal2 = Calendar.getInstance();
				cal2.setTime(df.parse(task_month_year));

				int userIdFromSession = Integer.parseInt(s1.getAttribute("user_id").toString());
				List<TaskForm> tList = null;
				tList = saveTaskService.getTaskDetails(task_month_year, resource_id);
				request.setAttribute("tList", tList);
				userService = Factory.getUserService();
				try{
					List<UserForm> listForm = new ArrayList<UserForm>();
					listForm = userService.getAllocatedResourcesBasedOnStartAndExitDate(userIdFromSession, cal2);
					request.setAttribute("userListSelection", listForm);
					request.setAttribute("selectResourceBasedOnUserId", resource_id);
				}
				catch(Exception e){
					e.printStackTrace();
				}
				// Added for missing entry reminder Table
				List<String> allmissingDateList = new ArrayList<String>();
				UserForm userDetail = userService.getUsernameFromId(resource_id);
				Date resource_start_dt = userDetail.getStart_date();
				String ressource_exit_dt_in_string = userDetail.getExit_date();								
				Calendar cal_for_resource_start_dt = null;	
				if(resource_start_dt != null){
					cal_for_resource_start_dt = Calendar.getInstance();
					cal_for_resource_start_dt.setTime(resource_start_dt);
				}
				Calendar cal_for_resource_end_dt = null;
				if(ressource_exit_dt_in_string != null){
					cal_for_resource_end_dt = Calendar.getInstance();
					cal_for_resource_end_dt.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(ressource_exit_dt_in_string));
				}
				Calendar cal_for_current_dt  = Calendar.getInstance();
				cal_for_current_dt.setTime(df.parse(task_month_year));
				if(cal_for_resource_start_dt == null && cal_for_resource_end_dt == null){
					allmissingDateList = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDateIn_dd_format(null, null, cal_for_current_dt);
				}else if(cal_for_resource_start_dt != null && cal_for_resource_end_dt == null){
					if(compareCalendarEqualityBasedOnMonthAndYear(cal_for_current_dt, cal_for_resource_start_dt)){
						allmissingDateList = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDateIn_dd_format(cal_for_resource_start_dt, null, cal_for_current_dt);
					}else if((cal_for_current_dt.get(Calendar.YEAR) > cal_for_resource_start_dt.get(Calendar.YEAR)) 
							|| cal_for_current_dt.get(Calendar.MONTH) >= cal_for_resource_start_dt.get(Calendar.MONTH) && cal_for_current_dt.get(Calendar.YEAR) == cal_for_resource_start_dt.get(Calendar.YEAR) ){
						allmissingDateList = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDateIn_dd_format(null, null, cal_for_current_dt);
					}				
				}else if(cal_for_resource_start_dt == null && cal_for_resource_end_dt != null){
					if(compareCalendarEqualityBasedOnMonthAndYear(cal_for_current_dt, cal_for_resource_end_dt)){
						allmissingDateList = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDateIn_dd_format(null, cal_for_resource_end_dt, cal_for_current_dt);
					}else if((cal_for_current_dt.get(Calendar.YEAR) < cal_for_resource_end_dt.get(Calendar.YEAR)) 
							|| cal_for_current_dt.get(Calendar.MONTH) <= cal_for_resource_end_dt.get(Calendar.MONTH) && cal_for_current_dt.get(Calendar.YEAR) == cal_for_resource_end_dt.get(Calendar.YEAR) ){
						allmissingDateList = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDateIn_dd_format(null, null, cal_for_current_dt);
					}		
				}else if(cal_for_resource_start_dt != null && cal_for_resource_end_dt != null){
					if( (compareCalendarEqualityBasedOnMonthAndYear(cal_for_current_dt, cal_for_resource_start_dt)  
							&& compareCalendarEqualityBasedOnMonthAndYear(cal_for_current_dt, cal_for_resource_end_dt)) ){

						allmissingDateList = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDateIn_dd_format(cal_for_resource_start_dt, cal_for_resource_end_dt, cal_for_current_dt);
					}else if(!(compareCalendarEqualityBasedOnMonthAndYear(cal_for_current_dt, cal_for_resource_start_dt))
							&& (compareCalendarEqualityBasedOnMonthAndYear(cal_for_current_dt, cal_for_resource_end_dt))){

						allmissingDateList = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDateIn_dd_format(null, cal_for_resource_end_dt, cal_for_current_dt);
					}else if((compareCalendarEqualityBasedOnMonthAndYear(cal_for_current_dt, cal_for_resource_start_dt))
							&& !(compareCalendarEqualityBasedOnMonthAndYear(cal_for_current_dt, cal_for_resource_end_dt)) ){

						allmissingDateList = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDateIn_dd_format(cal_for_resource_start_dt, null, cal_for_current_dt);
					}else if( comapareCalendarMonthAndYearBasedOnMonthAndYear(cal_for_current_dt, cal_for_resource_start_dt, null)
							&& comapareCalendarMonthAndYearBasedOnMonthAndYear(cal_for_current_dt, null, cal_for_resource_end_dt) ){						
						allmissingDateList = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDateIn_dd_format(null, null, cal_for_current_dt);
					}			
				}
				request.setAttribute("allmissingDateList", allmissingDateList);
				// Update for unfreezing timesheet.
				Date freezeDate = userDetail.getFreeze_timesheet();
				if(freezeDate != null){
					Calendar calWithFreezingDate = Calendar.getInstance();
					calWithFreezingDate.setTime(freezeDate);
					cal.setTime(df.parse(task_month_year));
					//Checking if freezing date exceeds than selectedMonth then blocking new entry.
					if(calWithFreezingDate.before(cal) || calWithFreezingDate.equals(cal)){
						forwardedPage = "getDetailSuccess";				
					}
					else{
						forwardedPage = "timeEntryFreezedPage";
					}
				}
				else{
					forwardedPage = "getDetailSuccess";
				}	
				String locDetail = saveTaskService.getResourceLocation(resource_id);
				request.setAttribute("resourceLocation", locDetail);
				request.setAttribute("month", task_month_year);
			}
			catch (Exception e) {
				logger.error(e);
				throw new Exception();
			}
		}
		else{
			throw new Exception();
		}
		return map.findForward(forwardedPage);
	}
	public ActionForward getDetailsNotification(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception{
		String forwardedPage = "";
		HttpSession session = request.getSession();
		if(session.getAttribute("userName")!=null){
			SaveTaskService saveTaskService = SaveTaskServiceFactory.getSaveTaskService();
			int userId = Integer.parseInt(session.getAttribute("user_id").toString());
			TaskForm taskForm = (TaskForm) form;
			DateFormat df = new SimpleDateFormat("yyyy-MM");
			List<TaskForm> tList = null;
			String month = taskForm.getMonth();

			// Checking previous month time entry for alert Notification on time-entry page
			Calendar c1 = Calendar.getInstance();
			c1.add(Calendar.MONTH, -1);		
			month = df.format(c1.getTime());			
			Calendar cal = Calendar.getInstance();			
			try {
				tList = saveTaskService.getTaskDetails(month, userId);
				request.setAttribute("tList", tList);					
				cal.setTime(df.parse(month));
				cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
				userService = Factory.getUserService();
				try{
					int userManagerId = userService.getAdminUserId();
					List<UserForm> listForm = null;
					if(userManagerId==userId){
						// Write logic
						listForm = userService.getAllocatedResourcesBasedOnStartAndExitDate(userId, cal);
						request.setAttribute("selectResourceBasedOnUserId", userManagerId);
						request.setAttribute("userListSelection", listForm);
					}
				}
				catch(Exception e){
					e.printStackTrace();
				}			
			} catch(Exception e){
				e.printStackTrace();	
				throw new Exception();
			}
			// Added for missing entry reminder Table			
			UserForm userDetail = userService.getUsernameFromId(userId);
			Date resource_start_dt = userDetail.getStart_date();
			String ressource_exit_dt_in_string = userDetail.getExit_date();								
			Calendar cal_for_resource_start_dt = null;	
			if(resource_start_dt != null){
				cal_for_resource_start_dt = Calendar.getInstance();
				cal_for_resource_start_dt.setTime(resource_start_dt);
			}
			Calendar cal_for_resource_end_dt = null;
			if(ressource_exit_dt_in_string != null){
				cal_for_resource_end_dt = Calendar.getInstance();
				cal_for_resource_end_dt.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(ressource_exit_dt_in_string));
			}
			Calendar cal_for_current_dt  = Calendar.getInstance();
			if(month != null){
				cal_for_current_dt.setTime(df.parse(month));
			}
			List<String> allmissingDateList = new ArrayList<String>();
			if(cal_for_resource_start_dt == null && cal_for_resource_end_dt == null){
				allmissingDateList = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDateIn_dd_format(null, null, cal_for_current_dt);
			}else if(cal_for_resource_start_dt != null && cal_for_resource_end_dt == null){
				if(compareCalendarEqualityBasedOnMonthAndYear(cal_for_current_dt, cal_for_resource_start_dt)){
					allmissingDateList = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDateIn_dd_format(cal_for_resource_start_dt, null, cal_for_current_dt);
				}else if((cal_for_current_dt.get(Calendar.YEAR) > cal_for_resource_start_dt.get(Calendar.YEAR)) 
						|| cal_for_current_dt.get(Calendar.MONTH) >= cal_for_resource_start_dt.get(Calendar.MONTH) && cal_for_current_dt.get(Calendar.YEAR) == cal_for_resource_start_dt.get(Calendar.YEAR) ){
					allmissingDateList = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDateIn_dd_format(null, null, cal_for_current_dt);
				}				
			}else if(cal_for_resource_start_dt == null && cal_for_resource_end_dt != null){
				if(compareCalendarEqualityBasedOnMonthAndYear(cal_for_current_dt, cal_for_resource_end_dt)){
					allmissingDateList = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDateIn_dd_format(null, cal_for_resource_end_dt, cal_for_current_dt);
				}else if((cal_for_current_dt.get(Calendar.YEAR) < cal_for_resource_end_dt.get(Calendar.YEAR)) 
						|| cal_for_current_dt.get(Calendar.MONTH) <= cal_for_resource_end_dt.get(Calendar.MONTH) && cal_for_current_dt.get(Calendar.YEAR) == cal_for_resource_end_dt.get(Calendar.YEAR) ){
					allmissingDateList = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDateIn_dd_format(null, null, cal_for_current_dt);
				}		
			}else if(cal_for_resource_start_dt != null && cal_for_resource_end_dt != null){
				if( (compareCalendarEqualityBasedOnMonthAndYear(cal_for_current_dt, cal_for_resource_start_dt)  
						&& compareCalendarEqualityBasedOnMonthAndYear(cal_for_current_dt, cal_for_resource_end_dt)) ){

					allmissingDateList = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDateIn_dd_format(cal_for_resource_start_dt, cal_for_resource_end_dt, cal_for_current_dt);
				}else if(!(compareCalendarEqualityBasedOnMonthAndYear(cal_for_current_dt, cal_for_resource_start_dt))
						&& (compareCalendarEqualityBasedOnMonthAndYear(cal_for_current_dt, cal_for_resource_end_dt))){

					allmissingDateList = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDateIn_dd_format(null, cal_for_resource_end_dt, cal_for_current_dt);
				}else if((compareCalendarEqualityBasedOnMonthAndYear(cal_for_current_dt, cal_for_resource_start_dt))
						&& !(compareCalendarEqualityBasedOnMonthAndYear(cal_for_current_dt, cal_for_resource_end_dt)) ){

					allmissingDateList = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDateIn_dd_format(cal_for_resource_start_dt, null, cal_for_current_dt);
				}else if( comapareCalendarMonthAndYearBasedOnMonthAndYear(cal_for_current_dt, cal_for_resource_start_dt, null)
						&& comapareCalendarMonthAndYearBasedOnMonthAndYear(cal_for_current_dt, null, cal_for_resource_end_dt) ){						
					allmissingDateList = TimeUtility.getAllWorkingDatesForSelectedMonthIncludingStartEndDateIn_dd_format(null, null, cal_for_current_dt);
				}			
			}
			request.setAttribute("allmissingDateList", allmissingDateList);
			String locDetail = saveTaskService.getResourceLocation(userId);

			Date freezeDate = userDetail.getFreeze_timesheet();
			if(freezeDate != null){
				Calendar calWithFreezingDate = Calendar.getInstance();
				calWithFreezingDate.setTime(freezeDate);
				cal.setTime(df.parse(month));
				//Checking if freezing date exceeds than selectedMonth then blocking new entry.
				if(calWithFreezingDate.after(cal) || calWithFreezingDate.equals(cal)){
					forwardedPage = "timeEntryFreezedPage";
				}
				else{
					forwardedPage = "getDetailSuccess";
				}
			}
			else{
				forwardedPage = "getDetailSuccess";
			}	
			request.setAttribute("resourceLocation", locDetail);
			request.setAttribute("month", month);
			return mapping.findForward(forwardedPage);
		}
		else
			throw new Exception();
	}
}
