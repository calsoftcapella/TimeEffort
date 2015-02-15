package com.calsoft.leave.dao.factory;

import com.calsoft.leave.dao.LeaveDaoImpl;

/*Dao Factory class for LeaveEntry Module*/

public class LeaveDaoFactory {
	public static LeaveDaoImpl leaveDaoImpl ;	
	static
	{
		leaveDaoImpl = new LeaveDaoImpl();
	}
	public static LeaveDaoImpl getLeaveDao()
	{
		if(leaveDaoImpl==null)
			leaveDaoImpl = new LeaveDaoImpl();
		return leaveDaoImpl;
	}

}
