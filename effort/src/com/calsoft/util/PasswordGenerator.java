package com.calsoft.util;

import java.util.Random;

public class PasswordGenerator {
	public PasswordGenerator() {
		// TODO Auto-generated constructor stub
	}
	PasswordGenerator generator;
	public static String generatePassword() throws Exception
	{
		Random random=new Random();
		String passWord="";
		char[] character={'a','s','d','f','g','h','j','k','3','2','9','6','1','5','l','@','#','$','*','q','w','e','r','t','y','u','i','o','p','z','x','c','v','b','n','m','0','4','7','8'};
		for(int i=0;i<7;i++)
		{
			int charNo=random.nextInt(character.length);
			passWord=passWord+character[charNo];
		}
		return passWord;
	}
}
