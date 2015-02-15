package com.calsoft.util;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

public class EmailUtil {

	private static final Logger logger = Logger.getLogger("EmailUtil");
	public static String sendEmail(String host,String port,String userId, String password, String from, String to, String subject, String text, String bcc) throws Exception {
		String status = "";
		try {
			Properties props = System.getProperties();
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", host);
			props.setProperty("mail.transport.protocol", "smtps");
			props.put("mail.smtp.user", userId);
			props.put("mail.smtp.password", password);
			props.put("mail.smtp.port", port);
			props.put("mail.smtps.auth", "true");
			props.put("mail.smtps.ssl.checkserveridentity", "false");
		    props.put("mail.smtps.ssl.trust", "*");
			Session session = Session.getDefaultInstance(props, null);
			MimeMessage message = new MimeMessage(session);
			InternetAddress fromAddress = new InternetAddress(from);
			InternetAddress toAddress = new InternetAddress(to);
			InternetAddress bccAddress = new InternetAddress(bcc);
			message.setFrom(fromAddress);
			message.setRecipient(RecipientType.TO, toAddress);
			message.setRecipient(RecipientType.BCC, bccAddress);
			message.setSubject(subject);
			message.setText(text);
			message.setContent(text, "text/html");
			Transport transport = session.getTransport("smtps");			
			transport.connect(host, userId, password);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			status = "Password sent.";
		}
		catch (Exception e) {
			e.printStackTrace();
			status = "Error while sending email.";
		}
		return status;
	}
	public static String sendEmailToMultiple(String host,String port,String userId, String password,
			String from, List<String> toList, String subject, String text, String cc, String bcc) throws Exception {
		String errorMessage = "";
		String status = "";
		try {
			Properties props = System.getProperties();
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", host);
			props.setProperty("mail.transport.protocol", "smtps");
			props.put("mail.smtp.user", userId);
			props.put("mail.smtp.password", password);
			props.put("mail.smtp.port", port);
			props.put("mail.smtps.auth", "true");
			props.put("mail.smtps.ssl.checkserveridentity", "false");
		    props.put("mail.smtps.ssl.trust", "*");
			Session session = Session.getDefaultInstance(props, null);
			MimeMessage message = new MimeMessage(session);		
			InternetAddress fromAddress = null;	
			InternetAddress[] addressTo = null;
			InternetAddress bccAddress = null;
			InternetAddress ccAddress = null;
			if(cc == null){	
				fromAddress = new InternetAddress(from);
				addressTo = new InternetAddress[toList.size()];  
				bccAddress = new InternetAddress(bcc);
				int counter = 0;  
				for (String recipient : toList) {  
					addressTo[counter] = new InternetAddress(recipient);  
					counter++;  
				}  
				message.setRecipients(Message.RecipientType.TO, addressTo);  					
				message.setFrom(fromAddress);
				message.setRecipient(RecipientType.BCC, bccAddress);
				message.setSubject(subject);
				message.setText(text);
				message.setContent(text, "text/html");
			}
			else{				
				fromAddress = new InternetAddress(from);
				ccAddress = new InternetAddress(cc);			
				addressTo = new InternetAddress[toList.size()];  
				bccAddress = new InternetAddress(bcc);
				int counter = 0;  
				for (String recipient : toList) {  
					addressTo[counter] = new InternetAddress(recipient);  
					counter++;  
				}  
				message.setRecipients(Message.RecipientType.TO, addressTo);  					
				message.setFrom(fromAddress);
				message.setRecipient(RecipientType.CC, ccAddress);
				message.setRecipient(RecipientType.BCC, bccAddress);
				message.setSubject(subject);
				message.setText(text);
				message.setContent(text, "text/html");
			}	
			Transport transport = session.getTransport("smtps");
			transport.connect(host, userId, password);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		}
		catch (MessagingException e) {
			StringWriter stack = new StringWriter();
			e.printStackTrace(new PrintWriter(stack));
			errorMessage += "Sending Failed";
			logger.error("Exception occured while executing sendEmailToMultiple method"+stack.toString());
		}
		if ("".equals(errorMessage)) {
			status += "Sending successful";
		}
		else {
			status += errorMessage;
		}
		return status;
	}
	public static String sendEmailNotification(String host,String port,String userId, String password,
			String from, String to, String subject, String text, List<String> ccList, String bcc) throws Exception {
		String errorMessage = "";
		String status = "";
		try {
			Properties props = System.getProperties();
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", host);
			props.setProperty("mail.transport.protocol", "smtps");
			props.put("mail.smtp.user", userId);
			props.put("mail.smtp.password", password);
			props.put("mail.smtp.port", port);
			props.put("mail.smtps.auth", "true");
			props.put("mail.smtps.ssl.checkserveridentity", "false");
		    props.put("mail.smtps.ssl.trust", "*");
			Session session = Session.getDefaultInstance(props, null);
			MimeMessage message = new MimeMessage(session);
			InternetAddress fromAddress = null;
			InternetAddress toAddress = null;
			InternetAddress[] ccAddress = null;
			InternetAddress bccAddress = null;

			fromAddress = new InternetAddress(from);
			toAddress = new InternetAddress(to);
			ccAddress = new InternetAddress[ccList.size()];
			bccAddress = new InternetAddress(bcc);

			int counter = 0;  
			for (String ccMail : ccList) {  
				ccAddress[counter] = new InternetAddress(ccMail);  
				counter++;  
			}  

			message.setFrom(fromAddress);
			message.setRecipient(RecipientType.TO, toAddress);
			message.setRecipients(Message.RecipientType.CC, ccAddress);
			message.setRecipient(RecipientType.BCC, bccAddress);
			message.setSubject(subject);
			message.setText(text);
			message.setContent(text, "text/html");
			Transport transport = session.getTransport("smtps");			
			transport.connect(host, userId, password);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		}
		catch (MessagingException e) {
			errorMessage += "Sending Failed";
			StringWriter stack = new StringWriter();
			e.printStackTrace(new PrintWriter(stack));
			logger.error("Printing exception detail from sendEmailNotification "+stack.toString());
			throw new Exception();
		}
		if ("".equals(errorMessage)) {
			status += "Comment added successfully and email notification has been sent to resource.";
		}
		else {
			status += errorMessage;
		}
		return status;
	}
	public static String sendEmailNotificationWithOutAddingBCC(String host,String port,String userId, String password,
			String from, String to, String subject, String text, List<String> ccList) throws Exception {
		String errorMessage = "";
		String status = "";
		try {
			Properties props = System.getProperties();
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", host);
			props.setProperty("mail.transport.protocol", "smtps");
			props.put("mail.smtp.user", userId);
			props.put("mail.smtp.password", password);
			props.put("mail.smtp.port", port);
			props.put("mail.smtps.auth", "true");
			props.put("mail.smtps.ssl.checkserveridentity", "false");
		    props.put("mail.smtps.ssl.trust", "*");
			Session session = Session.getDefaultInstance(props, null);
			MimeMessage message = new MimeMessage(session);
			InternetAddress fromAddress = null;
			InternetAddress toAddress = null;
			InternetAddress[] ccAddress = null;			
			fromAddress = new InternetAddress(from);
			toAddress = new InternetAddress(to);
			ccAddress = new InternetAddress[ccList.size()];

			int counter = 0;  
			for (String ccMail : ccList) {  
				ccAddress[counter] = new InternetAddress(ccMail);  
				counter++;  
			}  		
			message.setFrom(fromAddress);
			message.setRecipient(RecipientType.TO, toAddress);
			message.setRecipients(Message.RecipientType.CC, ccAddress);
			message.setSubject(subject);
			message.setText(text);
			message.setContent(text, "text/html");
			Transport transport = session.getTransport("smtps");			
			transport.connect(host, userId, password);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		}
		catch (MessagingException e) {
			errorMessage += "Sending Failed";
			StringWriter stack = new StringWriter();
			e.printStackTrace(new PrintWriter(stack));
			logger.error("Exception occured while executing sendEmailNotificationWithOutAddingBCC method"+stack.toString());
		}
		if ("".equals(errorMessage)) {
			status += "Comment deleted successfully and email notification has been sent to resource.";
		}
		else {
			status += errorMessage;
		}
		return status;
	}
	public static String sendEmailWithAttachment(String host, String port, String userId, String password,
			String from, String[] toList, String subject, String mess, String bcc, File f1) throws Exception{
		String messageStatus = "Failed to send mail please try again later.";
		try {
			Properties props = System.getProperties();
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", host);
			props.setProperty("mail.transport.protocol", "smtps");
			props.put("mail.smtp.user", userId);
			props.put("mail.smtp.password", password);
			props.put("mail.smtp.port", port);
			props.put("mail.smtps.auth", "true");
			props.put("mail.smtps.ssl.checkserveridentity", "false");
		    props.put("mail.smtps.ssl.trust", "*");
			Session session = Session.getDefaultInstance(props, null);
			MimeMessage message = new MimeMessage(session);
			InternetAddress fromAddress = null;
			InternetAddress[] toAddress = null;
			InternetAddress bccAddress = null;

			fromAddress = new InternetAddress(from);
			toAddress = new InternetAddress[toList.length];  
			bccAddress = new InternetAddress(bcc);
			int counter = 0;  
			for (String recipient : toList) {  
				toAddress[counter] = new InternetAddress(recipient);  
				counter++;  
			}  		
			message.setFrom(fromAddress);
			message.setRecipients(Message.RecipientType.TO, toAddress);  	
			message.setRecipient(RecipientType.BCC, bccAddress);
			message.setSubject(subject);
			Multipart multipart = new MimeMultipart();
			if(f1 != null){
				MimeBodyPart messageBodyPart1 = new MimeBodyPart();	
				MimeBodyPart messageBodyPart2 = new MimeBodyPart();
				DataSource source = new FileDataSource(f1);
				messageBodyPart1.setDataHandler(new DataHandler(source));
				messageBodyPart1.setFileName(f1.getName());
				messageBodyPart2.setText(mess);
				messageBodyPart2.addHeader("Content-Type", "text/html");
				multipart.addBodyPart(messageBodyPart1);
				multipart.addBodyPart(messageBodyPart2);
				message.setContent(multipart, "text/html");
			}
			else{
				message.setText(mess);
				message.setContent(mess, "text/html");
			}			
			Transport transport = session.getTransport("smtps");			
			transport.connect(host, userId, password);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			messageStatus = "Mail sent successfully.";
		}
		catch (MessagingException e) {
			e.printStackTrace();
			StringWriter stack = new StringWriter();
			e.printStackTrace(new PrintWriter(stack));
			logger.info("Printing exception detail from sendEmailWithAttachment "+stack.toString());
		}
		return messageStatus;
	}
	public static String sendEmailForIdleReport(String host,String port,String userId, String password,
			String from, String to, String subject, String text , String bcc) throws Exception {
		String errorMessage = "";
		String status = "";
		try {
			Properties props = System.getProperties();
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", host);
			props.setProperty("mail.transport.protocol", "smtps");
			props.put("mail.smtp.user", userId);
			props.put("mail.smtp.password", password);
			props.put("mail.smtp.port", port);
			props.put("mail.smtps.auth", "true");
			props.put("mail.smtps.ssl.checkserveridentity", "false");
		    props.put("mail.smtps.ssl.trust", "*");
			Session session = Session.getDefaultInstance(props, null);
			MimeMessage message = new MimeMessage(session);
			InternetAddress fromAddress = null;
			//InternetAddress ccAddress = null;	
			InternetAddress addressTo = null;
			InternetAddress bccAddress = null;

			fromAddress = new InternetAddress(from);
			//ccAddress = new InternetAddress(cc);			
			addressTo = new InternetAddress(to);
			bccAddress = new InternetAddress(bcc);

			message.setRecipient(Message.RecipientType.TO, addressTo);  					
			message.setFrom(fromAddress);
			//message.setRecipient(RecipientType.CC, ccAddress);
			message.setRecipient(RecipientType.BCC, bccAddress);
			message.setSubject(subject);
			message.setText(text);
			message.setContent(text, "text/html");
			Transport transport = session.getTransport("smtps");
			transport.connect(host, userId, password);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		}
		catch (MessagingException e) {
			errorMessage += "Sending Failed";
			StringWriter stack = new StringWriter();
			e.printStackTrace(new PrintWriter(stack));
			logger.error("Exception occured while executing sendEmailForIdleReport method"+stack.toString());
		}
		if ("".equals(errorMessage)) {
			status += "Idle Report Sent Successfully.";
		}
		else {
			status += errorMessage;
		}
		return status;
	}
}
