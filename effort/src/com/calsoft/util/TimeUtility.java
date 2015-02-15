package com.calsoft.util;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.calsoft.task.model.Task;
import com.calsoft.user.model.User;
public class TimeUtility {
	@SuppressWarnings("deprecation")
	public static Double getYourTime(List<String> totalSum){		
		Iterator<String> itr = totalSum.iterator();
		double hrs = 0.0;
		double mint = 0.0;
		while(itr.hasNext()){
			String time1 = itr.next();
			Time time = Time.valueOf(time1);
			hrs = hrs+time.getHours();
			mint = mint+time.getMinutes();
		}
		if(mint/60 >=1){
			hrs=hrs+(int)mint/60;
		}
		hrs=hrs+mint%60/100;
		return hrs;
	}
	public static String getUrsMonth(Date date){
		DateFormat myFormat = new SimpleDateFormat("MMM-yyyy");
		String reformattedStr = myFormat.format(date);
		return reformattedStr;
	}
	public static String getUrsDay(Date date){
		DateFormat myFormat1 = new SimpleDateFormat("d");
		String reformattedStr1 = myFormat1.format(date);
		return reformattedStr1;
	}
	public static boolean isDateInCurrentWeek(Date date) {
		Calendar currentCalendar = Calendar.getInstance();
		int week = currentCalendar.get(Calendar.WEEK_OF_YEAR);
		int year = currentCalendar.get(Calendar.YEAR);
		Calendar targetCalendar = Calendar.getInstance();
		targetCalendar.setTime(date);
		int targetWeek = targetCalendar.get(Calendar.WEEK_OF_YEAR);
		int targetYear = targetCalendar.get(Calendar.YEAR);
		return week == targetWeek && year == targetYear;
	}
	public static List<Task> checkDateAndAddMissing(Date d1, List<Task> list2) throws ParseException {	
		List<Task> list1 = new ArrayList<Task>();
		DateFormat s1 = new SimpleDateFormat("dd");
		DateFormat s2 = new SimpleDateFormat("MM/dd/yyyy");
		Calendar c = Calendar.getInstance();
		c.setTime(d1);
		Integer i1 = Integer.parseInt(s1.format(d1));
		User u1 = new User();
		String name ="";
		if(!list2.isEmpty()){
			Task userTask = list2.get(0);
			u1 = userTask.getUser();
			name = u1.getUser_name();
		}
		for (Task userTask : list2) {					
			list1.add(userTask);
		}
		for(int i = i1 ; i >= 1 ; i--){     // For Today 23 Times Looping				
			Date dt = c.getTime();
			String dtString = s2.format(dt);
			Task tk = new Task();
			Date dt1 = s2.parse(dtString);
			tk.setBacklog_id("");
			tk.setStatus("");
			tk.setTask_description("Efforts not entered.");
			tk.setTime("00:00:00");
			tk.setTask_date(dt1);
			User u2 = new User();
			u2.setUser_name(name);
			tk.setUser(u2);
			if(c.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && c.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY){
				if(!list1.contains(tk)){
					list1.add(tk);
				}
			}
			c.add(Calendar.DAY_OF_MONTH, -1);
		}
		Collections.sort(list1,Collections.reverseOrder());
		return  list1;
	}
	public static List<String> getAllDatesForPreviousWeek(){		
		List<String> previousWeekDatesList = new ArrayList<String>();
		Calendar now = Calendar.getInstance();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		now.add(Calendar.WEEK_OF_YEAR, -1);// Add the -1 in the Calendar of WEEK_OF_YEAR for previous week
		int delta = -now.get(GregorianCalendar.DAY_OF_WEEK) + 2; //add 2 if your week start on monday
		now.add(Calendar.DAY_OF_MONTH, delta );
		for (int i = 0; i < 5; i++){     // Discarding Saturday and Sunday dates
			previousWeekDatesList.add(format.format(now.getTime()));
			now.add(Calendar.DAY_OF_MONTH, 1);
		}
		return previousWeekDatesList;
	}
	public static List<String> getAllWorkingDate(Calendar c){
		List<String> allDates = new ArrayList<String>();
		DateFormat s1 = new SimpleDateFormat("dd");
		Date d1 = c.getTime();
		Integer i1 = Integer.parseInt(s1.format(d1));
		for(int i = i1 ; i >= 1 ; i--){     // For Today 23 Times Looping				
			if(c.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && c.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY){
				allDates.add(s1.format(c.getTime()));
			}
			c.add(Calendar.DAY_OF_MONTH, -1);
		}
		Collections.sort(allDates);
		return allDates;
	}
	public static List<String> getAllWorkingDateIncludingResourceStartDate(Calendar c){
		List<String> allDates = new ArrayList<String>();
		SimpleDateFormat s1 = new SimpleDateFormat("dd");
		Date d1 = c.getTime();
		Integer i1 = Integer.parseInt(s1.format(d1));
		for(int i = i1 ; i <= Calendar.getInstance().get(Calendar.DAY_OF_MONTH); i++){			
			if(c.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && c.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY){
				allDates.add(s1.format(c.getTime()));
			}
			c.add(Calendar.DAY_OF_MONTH, 1);
		}
		Collections.sort(allDates);
		return allDates;
	}

	public static Map<String, String> getCurrentAndPrevoiusMonthDate(){
		Calendar now = Calendar.getInstance();
		DateFormat d_format = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, String> listCurrentAndPrevoiusMonthDate = new HashMap<String, String>();
		listCurrentAndPrevoiusMonthDate.put("currentMonth", d_format.format(now.getTime()));
		now.add(Calendar.MONTH, -1);
		now.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.getActualMinimum(Calendar.DAY_OF_MONTH));
		listCurrentAndPrevoiusMonthDate.put("previousMonth", d_format.format(now.getTime()));
		return listCurrentAndPrevoiusMonthDate;
	}
	public static List<String> getAllDatesForThisWeek(){
		List<String> listAllWeekDays = new ArrayList<String>();
		Calendar now = Calendar.getInstance();
		Calendar cal = Calendar.getInstance();
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		int delta = -now.get(GregorianCalendar.DAY_OF_WEEK)+2; //add 2 if your week start on monday
		now.add(Calendar.DAY_OF_MONTH, delta);
		if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
			cal.add(Calendar.WEEK_OF_YEAR, -1);
			cal.add(Calendar.DAY_OF_MONTH, 1);
			for (int i = 0; i < cal.get(Calendar.DAY_OF_WEEK); i++){
				if((Calendar.getInstance().getTime().after(cal.getTime())|| cal.getTime().equals(now.getTime())) && !(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY || cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY)){
					listAllWeekDays.add(format.format(cal.getTime()));
				}			
				cal.add(Calendar.DAY_OF_MONTH, 1);
			}
		}
		else{
			for (int i = 0; i < cal.get(Calendar.DAY_OF_WEEK); i++){
				if((cal.getTime().after(now.getTime())|| cal.getTime().equals(now.getTime())) && !(now.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY || now.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY)){
					listAllWeekDays.add(format.format(now.getTime()));
				}			
				now.add(Calendar.DAY_OF_MONTH, 1);
			}
		}
		Collections.sort(listAllWeekDays, Collections.reverseOrder());
		return listAllWeekDays;
	}
	public static List<String> getAllWorkingDatesForSelectedMonth(String monthYear) throws ParseException{
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");	
		Date dt = df.parse(monthYear);
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		Calendar currentMonthCal = Calendar.getInstance();
		if(cal.get(Calendar.MONTH) != currentMonthCal.get(Calendar.MONTH)){
			cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		}	
		else{
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		List<String> dateList = new ArrayList<String>();		
		int counter = cal.get(Calendar.DAY_OF_MONTH);
		for(int i = 0; i < counter; i++){
			if(cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY && cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY){
				dateList.add(format.format(cal.getTime()));
			}
			cal.add(Calendar.DAY_OF_WEEK, -1);
		}
		Collections.sort(dateList);
		return dateList;
	}

	public static List<String> getAllWorkingDatesForSelectedMonthIncludingStartDate(String monthYear) throws ParseException{
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");	
		Date dt = df.parse(monthYear);
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		List<String> dateList = new ArrayList<String>();
		int start_dt = cal.get(Calendar.DAY_OF_MONTH);
		int counter = 0;
		if(cal.get(Calendar.MONTH) == Calendar.getInstance().get(Calendar.MONTH)){
			counter = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) -1;			// -1 for taking upto previous date only in case of current month.
		}
		else{
			counter = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		}
		for(int i = start_dt; i <= counter; i++){
			if(cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY && cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY){
				dateList.add(format.format(cal.getTime()));
			}
			cal.add(Calendar.DAY_OF_WEEK, 1);
		}
		Collections.sort(dateList);
		return dateList;
	}
	public static String getFormattedDateWithRange(String dateString, String monthYear) throws ParseException{
		String formattedDateRange = "";
		List<String> final_list = new ArrayList<String>();
		String 	period = "";
		String start_date = "";
		String end_date = "";
		String breaked_date = "";
		String temp = "";
		List<Integer> dateList = new ArrayList<Integer>();
		String[] dateArray = dateString.split(", ");
		DateFormat df = new SimpleDateFormat("MMM");
		DateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");	
		Calendar cal = Calendar.getInstance();
		cal.setTime(df1.parse(monthYear));
		for (String date : dateArray) {
			dateList.add(Integer.parseInt(date));
		}
		for (int i = 0; i < dateArray.length; i++) {
			temp = dateArray[i];
			cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(temp));
			if(start_date == ""){
				if(dateList.contains(Integer.parseInt(temp)+1) || (cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY && dateList.contains(Integer.parseInt(temp)+3) ) ){
					start_date = temp;
				}
				else{
					breaked_date = temp;
					final_list.add(breaked_date+" "+df.format(cal.getTime()));
				}
			}else{
				if(end_date==""){
					end_date = dateArray[i];
					if(!dateList.contains(Integer.parseInt(temp)+1) && !(cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY && dateList.contains(Integer.parseInt(temp)+3))){
						end_date = temp;
						period = start_date+"-"+end_date;
						final_list.add(period+" "+df.format(cal.getTime()));
						start_date="";
						end_date = "";
					}
				}
				else{ 
					if(dateList.contains(Integer.parseInt(temp)-1) || (cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) && dateList.contains(Integer.parseInt(temp)-3)){
						if(!dateList.contains(Integer.parseInt(temp)+1) && !(cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY && dateList.contains(Integer.parseInt(temp)+3))){
							end_date = temp;
							period = start_date+"-"+end_date;
							final_list.add(period+" "+df.format(cal.getTime()));
							start_date="";
							end_date = "";
						}
						// okkkkkk
						else if(!(dateList.contains(Integer.parseInt(temp)+1) && dateList.contains(Integer.parseInt(temp)-1)) && (cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY && !dateList.contains(Integer.parseInt(temp)+3))){
							breaked_date = temp;
							final_list.add(breaked_date+" "+df.format(cal.getTime()));
						}
					}
					else if(!(dateList.contains(Integer.parseInt(temp)+1) && dateList.contains(Integer.parseInt(temp)-1)) && (cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY && !dateList.contains(Integer.parseInt(temp)+3))){
						breaked_date = temp;
						final_list.add(breaked_date+" "+df.format(cal.getTime()));
					}
				}
			}			
		}
		for (int i = 0; i < final_list.size(); i++) {
			if(i == final_list.size()-1){
				formattedDateRange = formattedDateRange+final_list.get(i)+".";
			}
			else{
				formattedDateRange = formattedDateRange+final_list.get(i)+", ";
			}
		}
		return formattedDateRange;
	}
	public static String getFormattedDateWithRangeForReminderMailFormat(String dateString){
		String formattedDateRange = "";
		List<String> final_list = new ArrayList<String>();
		String 	period = "";
		String start_date = "";
		String end_date = "";
		String breaked_date = "";
		String temp = "";
		List<Integer> dateList = new ArrayList<Integer>();
		String[] dateArray = dateString.split(", ");
		DateFormat df = new SimpleDateFormat("MMMM");
		Calendar cal = Calendar.getInstance();
		for (String date : dateArray) {
			dateList.add(Integer.parseInt(date));
		}
		for (int i = 0; i < dateArray.length; i++) {
			temp = dateArray[i];
			cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(temp));
			if(start_date == ""){
				if(dateList.contains(Integer.parseInt(temp)+1) || (cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY && dateList.contains(Integer.parseInt(temp)+3) ) ){
					start_date = temp;
				}
				else{
					breaked_date = temp;
					final_list.add(df.format(cal.getTime())+" "+breaked_date);
				}
			}else{
				if(end_date==""){
					end_date = dateArray[i];
					if(!dateList.contains(Integer.parseInt(temp)+1) && !(cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY && dateList.contains(Integer.parseInt(temp)+3))){
						end_date = temp;
						period = start_date+"-"+end_date;
						final_list.add(df.format(cal.getTime())+" "+period);
						start_date="";
						end_date = "";
					}
				}
				else{ 
					if(dateList.contains(Integer.parseInt(temp)-1) || (cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) && dateList.contains(Integer.parseInt(temp)-3)){
						if(!dateList.contains(Integer.parseInt(temp)+1) && !(cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY && dateList.contains(Integer.parseInt(temp)+3))){
							end_date = temp;
							period = start_date+"-"+end_date;
							final_list.add(df.format(cal.getTime())+" "+period);
							start_date="";
							end_date = "";
						}
						// okkkkkk
						else if(!(dateList.contains(Integer.parseInt(temp)+1) && dateList.contains(Integer.parseInt(temp)-1)) &&
								(cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY && !dateList.contains(Integer.parseInt(temp)+3))){
							breaked_date = temp;
							final_list.add(df.format(cal.getTime())+" "+breaked_date);
						}
					}
					else if(!(dateList.contains(Integer.parseInt(temp)+1) && dateList.contains(Integer.parseInt(temp)-1)) &&
							(cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY && !dateList.contains(Integer.parseInt(temp)+3))){
						breaked_date = temp;
						final_list.add(df.format(cal.getTime())+" "+breaked_date);
					}
				}
			}			
		}
		for (int i = 0; i < final_list.size(); i++) {
			if(i == final_list.size()-1){
				formattedDateRange = formattedDateRange+final_list.get(i)+".";
			}
			else{
				formattedDateRange = formattedDateRange+final_list.get(i)+", ";
			}
		}
		return formattedDateRange;
	}
	public static Double getNumberOfWorkingDays(String yearMonth) throws ParseException{
		int workingdays = 0;
		DateFormat format = new SimpleDateFormat("yyyy-MM");	
		Calendar cal = Calendar.getInstance();
		cal.setTime(format.parse(yearMonth));
		if(cal.get(Calendar.MONTH) == Calendar.getInstance().get(Calendar.MONTH)){
			//Setting current date for current month.
			cal.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
		}
		else{
			cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		}
		int counter = cal.get(Calendar.DAY_OF_MONTH);
		for(int i = 0; i <counter; i++){
			if(cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY && cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY){
				workingdays++;
			}
			cal.add(Calendar.DAY_OF_WEEK, -1);
		}	
		return Double.parseDouble(Integer.valueOf(workingdays).toString());
	}
	public static List<String> getAllWorkingDatesForThisMonth(){
		List<String> dateList = new ArrayList<String>();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		if(cal.get(Calendar.DAY_OF_MONTH) == 1){
			return dateList;
		}
		cal.add(Calendar.DAY_OF_MONTH, -1);
		int counter = cal.get(Calendar.DAY_OF_MONTH);
		for(int i = 0; i < counter; i++){
			if(cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY && cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY){
				dateList.add(format.format(cal.getTime()));
			}
			cal.add(Calendar.DAY_OF_WEEK, -1);
		}
		Collections.sort(dateList);
		return dateList;
	}
	public static Double getNumberOfWorkingDaysExcludingHoliday(String year_month, List<String> holidayList) throws ParseException {
		int workingdays = 0;
		DateFormat format = new SimpleDateFormat("yyyy-MM");	
		DateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");	
		Calendar cal = Calendar.getInstance();
		cal.setTime(format.parse(year_month));
		if(cal.get(Calendar.MONTH) == Calendar.getInstance().get(Calendar.MONTH)){
			//Setting current date for current month.
			cal.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
		}
		else{
			cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		}
		int counter = cal.get(Calendar.DAY_OF_MONTH);		
		for(int i = 0; i <counter; i++){
			if((cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY && cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) && !holidayList.contains(format1.format(cal.getTime()))){
				workingdays++;
			}
			cal.add(Calendar.DAY_OF_WEEK, -1);
		}	
		return Double.parseDouble(Integer.valueOf(workingdays).toString());
	}
	public static List<String> getAllWorkingDateIncludingResourceStartDateAfterMonthSelect(Calendar c, int length) {
		List<String> allDates = new ArrayList<String>();
		DateFormat s1 = new SimpleDateFormat("dd");
		Date d1 = c.getTime();
		Integer i1 = Integer.parseInt(s1.format(d1));
		for(int i = i1 ; i <= length; i++){
			if(c.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && c.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY){
				allDates.add(s1.format(c.getTime()));
			}
			c.add(Calendar.DAY_OF_MONTH, 1);
		}
		Collections.sort(allDates);
		return allDates;
	}
	
	public static String getAllWorkingDatesForCurrentMonthLastWorkingDate(Calendar cal, Date resource_joining_dt){
		StringBuffer sb = new StringBuffer();
		String dateInString = "";
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		int cal_start_dt = 1;
		if(resource_joining_dt != null){
			Calendar cal_with_resource_joining_dt = Calendar.getInstance();
			cal_with_resource_joining_dt.setTime(resource_joining_dt);
			cal_start_dt = cal_with_resource_joining_dt.get(Calendar.DAY_OF_MONTH);
		}
		int loop_iteration = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		for (int i = cal_start_dt; i <= loop_iteration; i++) {
			cal.set(Calendar.DAY_OF_MONTH, i);
			if(cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY){
				sb.append("'"+df.format(cal.getTime())+"',");
			}		
		}
		dateInString = sb.toString();
		if(sb.indexOf(",") != -1 && sb.length() > 10){
			dateInString = sb.substring(0, sb.lastIndexOf(","));
		}
		return dateInString;
	}
	public static List<String> getAllWorkingDatesForCurrentMonthLastWorkingDateInList(Calendar cal, Date resource_joining_dt){
		List<String> dayList = new ArrayList<String>();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		int cal_start_dt = 1;
		if(resource_joining_dt != null){
			Calendar cal_with_resource_joining_dt = Calendar.getInstance();
			cal_with_resource_joining_dt.setTime(resource_joining_dt);
			cal_start_dt = cal_with_resource_joining_dt.get(Calendar.DAY_OF_MONTH);
		}
		int loop_iteration = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		for (int i = cal_start_dt; i <= loop_iteration; i++) {
			cal.set(Calendar.DAY_OF_MONTH, i);
			if(cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY){
				dayList.add(df.format(cal.getTime()));
			}		
		}
		return dayList;
	}

	
	/*public static String getAllWorkingDatesForCurrentMonthUptoSecondLastDay(Calendar cal){
		StringBuffer sb = new StringBuffer();
		String dateInString = "";
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		int loop_iteration = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		for (int i = 1; i < loop_iteration; i++) {
			cal.set(Calendar.DAY_OF_MONTH, i);
			if(cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY){
				sb.append("'"+df.format(cal.getTime())+"',");
			}		
		}
		dateInString = sb.toString();
		if(sb.indexOf(",") != -1 && sb.length() > 10){
			dateInString = sb.substring(0, sb.lastIndexOf(","));
		}
		return dateInString;
	}
	public static List<String> getAllWorkingDatesForCurrentMonthUptoSecondLastDayInList(Calendar cal){
		List<String> dayList = new ArrayList<String>();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		int loop_iteration = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		for (int i = 1; i < loop_iteration; i++) {
			cal.set(Calendar.DAY_OF_MONTH, i);
			if(cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY){
				dayList.add(df.format(cal.getTime()));
			}		
		}
		return dayList;
	}*/
	/*public static List<String> getAllWorkingDatesForSelectedMonthBasedOnResourceJoiningDate(Calendar c1) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");	
		List<String> dateList = new ArrayList<String>();		
		int startDate = c1.get(Calendar.DAY_OF_MONTH);
		int endDate = c1.getActualMaximum(Calendar.DAY_OF_MONTH);
		for(int i = startDate; i <= endDate; i++){
			if(c1.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY && c1.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY){
				dateList.add(format.format(c1.getTime()));
			}
			c1.add(Calendar.DAY_OF_MONTH, 1);
		}
		Collections.sort(dateList);
		return dateList;
	}*/

	// Utility used for defaulter list functionality.
	public static List<String> getAllWorkingDatesForSelectedMonthIncludingStartEndDate(Calendar start_date_cal, Calendar exit_date_cal, Calendar selectedMonth) throws ParseException{
		List<String> dateList = new ArrayList<String>();
		DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar current_month_cal = Calendar.getInstance();
		Calendar cal_instance = Calendar.getInstance();
		cal_instance.setTime(selectedMonth.getTime());
		int startCount = 1;
		if(start_date_cal != null){
			startCount = start_date_cal.get(Calendar.DAY_OF_MONTH);
		}
		int endCount = 0;
		if(exit_date_cal != null){
			if( ((current_month_cal.get(Calendar.MONTH) == exit_date_cal.get(Calendar.MONTH)) && (current_month_cal.get(Calendar.YEAR) == exit_date_cal.get(Calendar.YEAR)))
					&& (current_month_cal.get(Calendar.DAY_OF_MONTH) <= exit_date_cal.get(Calendar.DAY_OF_MONTH))){
				endCount = current_month_cal.get(Calendar.DAY_OF_MONTH)-1;				
			}else{
				endCount = exit_date_cal.get(Calendar.DAY_OF_MONTH);
			}
		}else if((Calendar.getInstance().get(Calendar.MONTH) == cal_instance.get(Calendar.MONTH)) && (Calendar.getInstance().get(Calendar.YEAR) == cal_instance.get(Calendar.YEAR))){
			endCount = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - 1;
		}else{
			endCount = cal_instance.getActualMaximum(Calendar.DAY_OF_MONTH);
		}
		cal_instance.set(Calendar.DAY_OF_MONTH, startCount);
		for (int i = startCount; i <= endCount; i++) {			
			if(cal_instance.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY  && cal_instance.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY){
				dateList.add(dFormat.format(cal_instance.getTime()));
			}
			cal_instance.add(Calendar.DAY_OF_WEEK, 1);
		}
		Collections.sort(dateList);
		return dateList;
	}
	// Utility used in Task Action for 'Missing Entry' table.
	public static List<String> getAllWorkingDatesForSelectedMonthIncludingStartEndDateIn_dd_format(Calendar start_date_cal, Calendar exit_date_cal, Calendar selectedMonth) throws ParseException{
		List<String> dateList = new ArrayList<String>();
		DateFormat dFormat = new SimpleDateFormat("dd");
		Calendar current_month_cal = Calendar.getInstance();
		Calendar cal_instance = Calendar.getInstance();
		cal_instance.setTime(selectedMonth.getTime());
		int startCount = 1;
		if(start_date_cal != null){
			startCount = start_date_cal.get(Calendar.DAY_OF_MONTH);
		}
		int endCount = 0;
		if(exit_date_cal != null){
			if( ((current_month_cal.get(Calendar.MONTH) == exit_date_cal.get(Calendar.MONTH)) && (current_month_cal.get(Calendar.YEAR) == exit_date_cal.get(Calendar.YEAR)))
					&& (current_month_cal.get(Calendar.DAY_OF_MONTH) <= exit_date_cal.get(Calendar.DAY_OF_MONTH))){
				endCount = current_month_cal.get(Calendar.DAY_OF_MONTH);				
			}else{
				endCount = exit_date_cal.get(Calendar.DAY_OF_MONTH);
			}
		}else if((Calendar.getInstance().get(Calendar.MONTH) == cal_instance.get(Calendar.MONTH)) && (Calendar.getInstance().get(Calendar.YEAR) == cal_instance.get(Calendar.YEAR))){
			endCount = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		}else{
			endCount = cal_instance.getActualMaximum(Calendar.DAY_OF_MONTH);
		}
		cal_instance.set(Calendar.DAY_OF_MONTH, startCount);
		for (int i = startCount; i <= endCount; i++) {			
			if(cal_instance.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY  && cal_instance.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY){
				dateList.add(dFormat.format(cal_instance.getTime()));
			}
			cal_instance.add(Calendar.DAY_OF_WEEK, 1);
		}
		Collections.sort(dateList);
		return dateList;
	}
	// Used in Task Action for getting all previous month working dates for missing entry alert notification.
	public static List<String> getAllWorkingDatesForSelectedMonthIncludingStartEndDateForPreviousMonthEntryNotification(Calendar start_date_cal, Calendar exit_date_cal, Calendar previousMonthCal) throws ParseException{
		List<String> dateList = new ArrayList<String>();
		DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar current_month_cal = Calendar.getInstance();
		Calendar new_instance_for_previous_month_cal = Calendar.getInstance();
		new_instance_for_previous_month_cal.setTime(previousMonthCal.getTime());
		int startCount = 1;
		if(start_date_cal != null){
			if((new_instance_for_previous_month_cal.get(Calendar.MONTH) == start_date_cal.get(Calendar.MONTH)) && (new_instance_for_previous_month_cal.get(Calendar.YEAR) == start_date_cal.get(Calendar.YEAR))){
				startCount = start_date_cal.get(Calendar.DAY_OF_MONTH);
			}	
		}
		int endCount = 0;
		if(exit_date_cal != null){
			if( ((new_instance_for_previous_month_cal.get(Calendar.MONTH) == exit_date_cal.get(Calendar.MONTH)) && (new_instance_for_previous_month_cal.get(Calendar.YEAR) == exit_date_cal.get(Calendar.YEAR)))){
				endCount = exit_date_cal.get(Calendar.DAY_OF_MONTH);				
			}else if((current_month_cal.get(Calendar.YEAR) < exit_date_cal.get(Calendar.YEAR)) || 
					(current_month_cal.get(Calendar.MONTH) >= exit_date_cal.get(Calendar.MONTH) && current_month_cal.get(Calendar.YEAR) == exit_date_cal.get(Calendar.YEAR))){
				endCount = previousMonthCal.getActualMaximum(Calendar.DAY_OF_MONTH);
			}
		}else{
			endCount = new_instance_for_previous_month_cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		}
		new_instance_for_previous_month_cal.set(Calendar.DAY_OF_MONTH, startCount);
		for (int i = startCount; i <= endCount; i++) {			
			if(new_instance_for_previous_month_cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY  && new_instance_for_previous_month_cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY){
				dateList.add(dFormat.format(new_instance_for_previous_month_cal.getTime()));
			}
			new_instance_for_previous_month_cal.add(Calendar.DAY_OF_WEEK, 1);
		}
		Collections.sort(dateList);
		return dateList;
	}
	public static String getCurrentQuarter(Calendar cal){
		String current_quarter = "";
		DateFormat df = new SimpleDateFormat("MMM-yy");
		int cal_month = cal.get(Calendar.MONTH);
		if(cal_month % 3 == 0){
			cal_month = cal_month+2;
		}else if(cal_month % 3 == 1){
			cal_month = cal_month +1;
		}
		cal.set(Calendar.MONTH, cal_month); // Setting end-range from Current Quarter.
		String current_quarter_end_range = df.format(cal.getTime());		
		cal.add(Calendar.MONTH, -2);
		String current_quarter_start_range = df.format(cal.getTime());
		current_quarter = current_quarter_start_range+" to "+current_quarter_end_range;
		return current_quarter;
	}
	public static List<String> getObjectiveQuarters(){
		List<String> objectiveQuarterList = new ArrayList<String>();
		DateFormat df = new SimpleDateFormat("MMM-yyyy");
		Calendar cal = Calendar.getInstance();
		Calendar newCalInstance = Calendar.getInstance();
		for (int i = 3; i < 22; i = i+3) { // Getting Objective Quarter including last 7 Quarters.
			String current_quarter = "";
			newCalInstance.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
			int cal_month = newCalInstance.get(Calendar.MONTH);
			if(cal_month % 3 == 0){
				cal_month = cal_month+2;
			}else if(cal_month % 3 == 1){
				cal_month = cal_month +1;
			}
			newCalInstance.set(Calendar.MONTH, cal_month); // Setting end-range from Current Quarter.
			String current_quarter_end_range = df.format(newCalInstance.getTime());		
			newCalInstance.add(Calendar.MONTH, -2);
			String current_quarter_start_range = df.format(newCalInstance.getTime());
			current_quarter = current_quarter_start_range+" to "+current_quarter_end_range;
			objectiveQuarterList.add(current_quarter);
			cal.add(Calendar.MONTH, -3);
		}
		return objectiveQuarterList;
	}
}