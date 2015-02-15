package com.calsoft.util;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.xerces.dom.DocumentImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import com.calsoft.task.form.TaskForm;

public class DailyMailTemplate {
	public static String getHtmlMailContent(List<TaskForm> taskList, File xmlTempFile, String previousDate){
		String mailContent = "";
		Document xmlDocument = getXML(taskList, previousDate);
		mailContent = applyXsltTemplate(xmlDocument, xmlTempFile);	
		return mailContent;
	}
	public static String applyXsltTemplate(Document xmldoc, File xsltFile){
		try{
			StringWriter writer = new StringWriter();
			PrintWriter out = new PrintWriter(writer);
			Source xmlSource = new DOMSource(xmldoc);
			Source xsltSource = new StreamSource(xsltFile);
			Result result = new StreamResult(out);
			TransformerFactory transFact = TransformerFactory.newInstance();
			Transformer trans = transFact.newTransformer(xsltSource);
			try {
				trans.transform(xmlSource, result);
			} catch (TransformerException trEx) {
				String errorMessage = "Exception occured while transforming xml";
				trEx.printStackTrace();
				return errorMessage;
			}
			String htmlMailString = writer.toString();
			return htmlMailString;
		}catch(Exception e){
			String errorMessage = "Exception occured in applyXsltTemplate";
			e.printStackTrace();
			return errorMessage;
		}
	}
	public static Document getXML(List<TaskForm> taskFormDetail, String previousDate){
		Document xmldoc = new DocumentImpl();
		Element root = xmldoc.createElement("IdleTimeEntryReport");
		Element e_current_date = xmldoc.createElement("current_date");
		Node n_current_dat = xmldoc.createTextNode(previousDate);
		e_current_date.appendChild(n_current_dat);
		root.appendChild(e_current_date);
		Element e = null;
		for (TaskForm taskForm : taskFormDetail) {
			e = xmldoc.createElement("idleReportData");
			Element e1 = xmldoc.createElement("user_name");
			Node n1 = xmldoc.createTextNode(taskForm.getUserName());				
			e1.appendChild(n1);
			
			Element e2 = xmldoc.createElement("task_date");
			Node n2 = xmldoc.createTextNode(taskForm.getTask_date());				
			e2.appendChild(n2);
			
			Element e3 = xmldoc.createElement("task_status");
			Node n3 = xmldoc.createTextNode(taskForm.getStatus());				
			e3.appendChild(n3);
			
			Element e4 = xmldoc.createElement("task_description");
			Node n4 = xmldoc.createTextNode(taskForm.getTask_description());				
			e4.appendChild(n4);
			
			Element e5 = xmldoc.createElement("task_time");
			Node n5 = xmldoc.createTextNode(taskForm.getTime());				
			e5.appendChild(n5);
			
			e.appendChild(e1);
			e.appendChild(e2);
			e.appendChild(e3);
			e.appendChild(e4);
			e.appendChild(e5);
			
			root.appendChild(e);
		}	
		xmldoc.appendChild(root);
		return xmldoc;
	}
}
