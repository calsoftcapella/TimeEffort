package com.calsoft.task.dao.factory;
import com.calsoft.task.dao.SaveTaskDaoImpl;

public class SaveTaskDaoFactory {
	public static SaveTaskDaoImpl saveTaskDaoImpl;
	static
	{
		saveTaskDaoImpl=new SaveTaskDaoImpl();
	}
	public static SaveTaskDaoImpl getSaveTaskDao(){
		if(saveTaskDaoImpl==null)
			saveTaskDaoImpl=new SaveTaskDaoImpl();
		return saveTaskDaoImpl;
	}
}
