package com.calsoft.performance.daofactory;


import com.calsoft.performance.dao.PerformanceLogDao;
import com.calsoft.performance.dao.PerformanceLogDaoImpl;



public class PerformanceLogDaoFactory {
	
	static	PerformanceLogDao performanceLogDaoImpl;
	static{
		performanceLogDaoImpl = new PerformanceLogDaoImpl();
	}
	public static PerformanceLogDao getPerformanceLogDao(){
		if(performanceLogDaoImpl == null)
			performanceLogDaoImpl = new PerformanceLogDaoImpl();		
		return performanceLogDaoImpl;
	}
	
}
