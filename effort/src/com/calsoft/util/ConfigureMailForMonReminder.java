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

import com.calsoft.performance.form.PerformanceLogForm;

public class ConfigureMailForMonReminder {
	public static String getHtmlMailContentForMonReminder(List<PerformanceLogForm> getNotEnteredResourceList, File f2) {
		String mailContent = "";	
		Document xmlDocument = getXML(getNotEnteredResourceList);
		mailContent = applyXsltTemplate(xmlDocument, f2);	
		return mailContent;
	}
	public static Document getXML(List<PerformanceLogForm> performanceLogFormList){
		Element e = null;
		Document xmldoc = new DocumentImpl();
		Element root = xmldoc.createElement("LastWeekTimesheetReport");		
		for (PerformanceLogForm p_form : performanceLogFormList) {
			e = xmldoc.createElement("userNotFilledTimesheet");
			Element e1 = xmldoc.createElement("userName");	
			Element e2 = xmldoc.createElement("period");	
			Node n1 = xmldoc.createTextNode(p_form.getUserName());
			Node n2 = xmldoc.createTextNode(p_form.getPeriod());
			e1.appendChild(n1);
			e2.appendChild(n2);
			e.appendChild(e1);
			e.appendChild(e2);
			root.appendChild(e);			
		}		
		xmldoc.appendChild(root);
		return xmldoc;
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
}
