package com.calsoft.factory;

import com.calsoft.user.dao.UserDao;
import com.calsoft.user.dao.UserDaoImpl;
import com.calsoft.user.service.UserService;
import com.calsoft.user.service.UserServiceImpl;

public class Factory {
	static UserServiceImpl  serviceImpl; 
	static UserDaoImpl daoImpl;
	static{
		serviceImpl=new UserServiceImpl();
		daoImpl=new UserDaoImpl();
	}	
	public static UserService  getUserService(){		
		if(serviceImpl==null)
			serviceImpl=new UserServiceImpl();
		return serviceImpl;
		
	}
	public static UserDao getDao(){
		if(daoImpl==null)
			daoImpl=new UserDaoImpl();
		return daoImpl;
	}
}
