package com.calsoft.report.decorator;

import org.displaytag.decorator.TableDecorator;
import com.calsoft.task.form.TaskForm;

public class DisplayDecorator extends TableDecorator
{
  public int id()
   {
	TaskForm reportData = (TaskForm)getCurrentRowObject();
    return reportData.getId();
    }
	
   public String backlog_id()
    {
	TaskForm reportData = (TaskForm)getCurrentRowObject();
    return reportData.getBacklog_id();
    }
   public String status()
   {
	TaskForm reportData = (TaskForm)getCurrentRowObject();
    return reportData.getStatus();
   }
   
   public String task_description()
   {
	   
	   TaskForm reportData = (TaskForm)getCurrentRowObject();
	    return reportData.getTask_description();
   }
    
   
   public String task_id()
   {
	   TaskForm reportData = (TaskForm)getCurrentRowObject();
	    return reportData.getTask_id();  
   }
   
   public String time()
   {
	   TaskForm reportData = (TaskForm)getCurrentRowObject();
	    return reportData.getTime();
   }
   
   public String task_date()
   {
	   TaskForm reportData = (TaskForm)getCurrentRowObject();
	    return reportData.getTask_date();
   }
}
