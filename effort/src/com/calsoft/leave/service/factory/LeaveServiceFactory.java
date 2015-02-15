package com.calsoft.leave.service.factory;

import com.calsoft.leave.service.LeaveServiceImpl;

/*Service Factory class for LeaveEntry Module*/

public class LeaveServiceFactory{
	public static LeaveServiceImpl leaveServiceImpl ;	
	static{
		leaveServiceImpl = new LeaveServiceImpl();
	}
	public static LeaveServiceImpl getLeaveService() {
		if(leaveServiceImpl==null){
			leaveServiceImpl = new LeaveServiceImpl();
		}
		return leaveServiceImpl;
	}
}
