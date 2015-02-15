package com.calsoft.util;

import java.util.Comparator;

import com.calsoft.user.model.User;

@SuppressWarnings("rawtypes")
public class Mycomparator implements Comparator{
	@Override
	public int compare(Object o1, Object o2) {
		User user=(User)o1;
		User user2=(User)o2;
		String name=user.getUser_name();
		String name2=user2.getUser_name();
		return name.compareToIgnoreCase(name2);
	}
}
