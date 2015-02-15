package com.calsoft.task.service.factory;

import com.calsoft.task.service.SaveTaskServiceImpl;

public class SaveTaskServiceFactory {
	public static SaveTaskServiceImpl saveTaskServiceImp;
	static
	{
		saveTaskServiceImp=new SaveTaskServiceImpl();
	}
	public static SaveTaskServiceImpl getSaveTaskService() 
	{
		if(saveTaskServiceImp==null)
			saveTaskServiceImp=new SaveTaskServiceImpl();
		return saveTaskServiceImp;
	}
	

}
