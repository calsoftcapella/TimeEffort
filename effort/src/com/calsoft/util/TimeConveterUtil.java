package com.calsoft.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeConveterUtil{
	public static String timeConveter(String time){
		StringBuffer formatted_time = new StringBuffer();
		DateFormat df = new SimpleDateFormat("HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		try{
			if(time.indexOf(':')==-1 && time.indexOf('.') != -1){
				String hourOfDay = time.substring(0, time.indexOf('.'));
				String minute = time.substring(time.indexOf('.')+1, time.length());
				int mintInInt = Integer.parseInt(minute);
				// Checking minute.length() for blocking entries like 5.05.
				if(minute.length() == 1 && mintInInt < 6) {
					mintInInt = mintInInt*10; 	// Multiplying by 10 for entry like 5.3 to get 5.30 Hrs.
				}
				cal.set(0, 0, 0, Integer.parseInt(hourOfDay), mintInInt, 0);
				formatted_time.append(df.format(cal.getTime()));

			}else{
				formatted_time.append("00:00:00");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			formatted_time.append("00:00:00");
		}
		return formatted_time.toString();
	}
}
