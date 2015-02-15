package com.calsoft.performance.servicefactory;

import org.apache.log4j.Logger;
import com.calsoft.performance.dao.PerformanceLogDao;
import com.calsoft.performance.dao.PerformanceLogDaoImpl;
import com.calsoft.performance.service.PerformanceLogService;
import com.calsoft.performance.service.PerformanceLogServiceImpl;

public class PerformanceServiceFactory {
	private static final Logger logger = Logger.getLogger("name");
	static PerformanceLogService  serviceImpl; 
	static PerformanceLogDaoImpl daoImpl;
	static{
		serviceImpl=new PerformanceLogServiceImpl();
		daoImpl=new PerformanceLogDaoImpl();
	}
	public static PerformanceLogService  getPerformanceService(){		
		if(serviceImpl==null)
			serviceImpl=new PerformanceLogServiceImpl();
		logger.info("Printing from Performance Service Factory.");
		return serviceImpl;		
	}
	public static PerformanceLogDao getDao(){
		if(daoImpl==null)
			daoImpl=new PerformanceLogDaoImpl();
		return daoImpl;
	}
}
