package com.calsoft.util;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.calsoft.report.form.ReportForm;

public class ConfigureMailContent {
	private static List<RemainderMail> remainderList;

	public static String getHtmlMailContent(List<ReportForm> reportList, List<String> daysList, File xmlTempFile){
		String mailContent = null;
		remainderList = new ArrayList<RemainderMail>();
		RemainderMail remainder = new RemainderMail();
		remainder.setReportFormList(reportList);

		List<String> dy = new ArrayList<String>();
		List<String> dt = new ArrayList<String>();

		for (String string : daysList) {			
			String dy1 = string.substring(0, string.indexOf(' '));
			String dt1 = string.substring(string.indexOf(' ')+1, string.length());
			if(dy1.equalsIgnoreCase("Sun")||dy1.equalsIgnoreCase("Sat")){
				dy1 = dy1+" ";
				dt1 = dt1+" ";
			}
			dy.add(dy1);
			dt.add(dt1);			
		}		
		remainder.setDayList(dy);
		remainder.setDt(dt);
		remainderList.add(remainder);
		Document xmlDocument = getXML(remainderList);
		mailContent = applyXsltTemplate(xmlDocument, xmlTempFile);	
		return mailContent;
	}
	public static Document getXML(List<RemainderMail> remainderList){
		Element e = null;
		Document xmldoc = new DocumentImpl();
		Element root = xmldoc.createElement("TimesheetReport");
		Calendar cal = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("MMMM yyyy");
		e = xmldoc.createElement("currentMonth");
		Element e_month = xmldoc.createElement("value");	
		Node n_for_month = xmldoc.createTextNode(df.format(cal.getTime()));
		e_month.appendChild(n_for_month);
		e.appendChild(e_month);
		root.appendChild(e);	
		for (RemainderMail rem_list : remainderList) {
			List<String> dayList = rem_list.getDayList();
			for (String days : dayList) {
				e = xmldoc.createElement("monthDays");
				Element e1 = xmldoc.createElement("days");	
				Node n1 = xmldoc.createTextNode(days);
				e1.appendChild(n1);
				e.appendChild(e1);
				root.appendChild(e);
			}
			List<String> date_count = rem_list.getDt();
			for (String string : date_count) {
				e = xmldoc.createElement("dateCount");
				Element e_date = xmldoc.createElement("date_seq");		
				Node n_date = xmldoc.createTextNode(string);							
				e_date.appendChild(n_date);
				e.appendChild(e_date);
				root.appendChild(e);
			}
			/*e = xmldoc.createElement("calenderLength");
			Element e_date = xmldoc.createElement("totalDateSize");
			Node n_date = xmldoc.createTextNode(Integer.toString(date_count.size()));				
			e_date.appendChild(n_date);
			e.appendChild(e_date);
			root.appendChild(e);*/

			List<ReportForm> report_detail = rem_list.getReportFormList();
			for (ReportForm reportForm : report_detail) {
				e = xmldoc.createElement("reportData");
				Element e11 = xmldoc.createElement("user_name");
				Node n11 = xmldoc.createTextNode(reportForm.getUserName());				
				e11.appendChild(n11);

				Element time1 = xmldoc.createElement("time1");
				Node n_time1 = xmldoc.createTextNode(reportForm.getTime1());
				time1.appendChild(n_time1);

				Element time2 = xmldoc.createElement("time2");
				Node n_time2 = xmldoc.createTextNode(reportForm.getTime2());
				time2.appendChild(n_time2);

				Element time3 = xmldoc.createElement("time3");
				Node n_time3 = xmldoc.createTextNode(reportForm.getTime3());
				time3.appendChild(n_time3);

				Element time4 = xmldoc.createElement("time4");
				Node n_time4 = xmldoc.createTextNode(reportForm.getTime4());
				time4.appendChild(n_time4);

				Element time5 = xmldoc.createElement("time5");
				Node n_time5 = xmldoc.createTextNode(reportForm.getTime5());
				time5.appendChild(n_time5);

				Element time6 = xmldoc.createElement("time6");
				Node n_time6 = xmldoc.createTextNode(reportForm.getTime6());
				time6.appendChild(n_time6);

				Element time7 = xmldoc.createElement("time7");
				Node n_time7 = xmldoc.createTextNode(reportForm.getTime7());
				time7.appendChild(n_time7);

				Element time8 = xmldoc.createElement("time8");
				Node n_time8 = xmldoc.createTextNode(reportForm.getTime8());
				time8.appendChild(n_time8);

				Element time9 = xmldoc.createElement("time9");
				Node n_time9 = xmldoc.createTextNode(reportForm.getTime9());
				time9.appendChild(n_time9);

				Element time10 = xmldoc.createElement("time10");
				Node n_time10 = xmldoc.createTextNode(reportForm.getTime10());
				time10.appendChild(n_time10);

				Element time11 = xmldoc.createElement("time11");
				Node n_time11 = xmldoc.createTextNode(reportForm.getTime11());
				time11.appendChild(n_time11);

				Element time12 = xmldoc.createElement("time12");
				Node n_time12 = xmldoc.createTextNode(reportForm.getTime12());
				time12.appendChild(n_time12);

				Element time13 = xmldoc.createElement("time13");
				Node n_time13 = xmldoc.createTextNode(reportForm.getTime13());
				time13.appendChild(n_time13);

				Element time14 = xmldoc.createElement("time14");
				Node n_time14 = xmldoc.createTextNode(reportForm.getTime14());
				time14.appendChild(n_time14);

				Element time15 = xmldoc.createElement("time15");
				Node n_time15 = xmldoc.createTextNode(reportForm.getTime15());
				time15.appendChild(n_time15);

				Element time16 = xmldoc.createElement("time16");
				Node n_time16 = xmldoc.createTextNode(reportForm.getTime16());
				time16.appendChild(n_time16);

				Element time17 = xmldoc.createElement("time17");
				Node n_time17 = xmldoc.createTextNode(reportForm.getTime17());
				time17.appendChild(n_time17);

				Element time18 = xmldoc.createElement("time18");
				Node n_time18 = xmldoc.createTextNode(reportForm.getTime18());
				time18.appendChild(n_time18);

				Element time19 = xmldoc.createElement("time19");
				Node n_time19 = xmldoc.createTextNode(reportForm.getTime19());
				time19.appendChild(n_time19);

				Element time20 = xmldoc.createElement("time20");
				Node n_time20 = xmldoc.createTextNode(reportForm.getTime20());
				time20.appendChild(n_time20);

				Element time21 = xmldoc.createElement("time21");
				Node n_time21 = xmldoc.createTextNode(reportForm.getTime21());
				time21.appendChild(n_time21);

				Element time22 = xmldoc.createElement("time22");
				Node n_time22 = xmldoc.createTextNode(reportForm.getTime22());
				time22.appendChild(n_time22);

				Element time23 = xmldoc.createElement("time23");
				Node n_time23 = xmldoc.createTextNode(reportForm.getTime23());
				time23.appendChild(n_time23);

				Element time24 = xmldoc.createElement("time24");
				Node n_time24 = xmldoc.createTextNode(reportForm.getTime24());
				time24.appendChild(n_time24);

				Element time25 = xmldoc.createElement("time25");
				Node n_time25 = xmldoc.createTextNode(reportForm.getTime25());
				time25.appendChild(n_time25);

				Element time26 = xmldoc.createElement("time26");
				Node n_time26 = xmldoc.createTextNode(reportForm.getTime26());
				time26.appendChild(n_time26);

				Element time27 = xmldoc.createElement("time27");
				Node n_time27 = xmldoc.createTextNode(reportForm.getTime27());
				time27.appendChild(n_time27);

				Element time28 = xmldoc.createElement("time28");
				Node n_time28 = xmldoc.createTextNode(reportForm.getTime28());
				time28.appendChild(n_time28);

				e.appendChild(time1);
				e.appendChild(time2);
				e.appendChild(time3);
				e.appendChild(time4);
				e.appendChild(time5);
				e.appendChild(time6);
				e.appendChild(time7);
				e.appendChild(time8);
				e.appendChild(time9);
				e.appendChild(time10);
				e.appendChild(time11);
				e.appendChild(time12);
				e.appendChild(time13);
				e.appendChild(time14);
				e.appendChild(time15);
				e.appendChild(time16);
				e.appendChild(time17);
				e.appendChild(time18);
				e.appendChild(time19);
				e.appendChild(time20);
				e.appendChild(time21);
				e.appendChild(time22);
				e.appendChild(time23);
				e.appendChild(time24);
				e.appendChild(time25);
				e.appendChild(time26);
				e.appendChild(time27);
				e.appendChild(time28);

				RemainderMail reminderObject = remainderList.get(0);
				List<String> dateCount = reminderObject.getDt();

				Element time29 = xmldoc.createElement("time29");
				Node n_time29 = null;
				if(dateCount.size()==28 ){
					n_time29 = xmldoc.createTextNode("empty");
				}
				else{
					n_time29 = xmldoc.createTextNode(reportForm.getTime29());
				}
				time29.appendChild(n_time29);
				e.appendChild(time29);

				Element time30 = xmldoc.createElement("time30");
				Node n_time30 = null;
				if(dateCount.size()==28 || dateCount.size()==29){
					n_time30 = xmldoc.createTextNode("empty");
				}
				else{
					n_time30 = xmldoc.createTextNode(reportForm.getTime30());
				}
				time30.appendChild(n_time30);
				e.appendChild(time30);

				Element time31 = xmldoc.createElement("time31");
				Node n_time31 = null;
				if(dateCount.size()==28 || dateCount.size()==29 || dateCount.size()==30){
					n_time31 = xmldoc.createTextNode("empty");
				}
				else{
					n_time31 = xmldoc.createTextNode(reportForm.getTime31());
				}
				time31.appendChild(n_time31);
				e.appendChild(time31);

				Element e33 = xmldoc.createElement("total_time");
				Node n33 = xmldoc.createTextNode(reportForm.getTotalTime().toString());				
				e33.appendChild(n33);
				e.appendChild(e11);
				e.appendChild(e33);
				root.appendChild(e);
			}
			/*Element e3 = xmldoc.createElement("quantity");
					Node n3 = xmldoc.createTextNode(item.getQuantity() + "");
					e3.appendChild(n3);
					Element e2 = xmldoc.createElement("price");
					Node n2 = xmldoc.createTextNode("" + item.getPrice());
					e2.appendChild(n2);
					e.appendChild(e1);
					e.appendChild(e3);
					e.appendChild(e2);
			total +=item.getPrice();*/
		}
		/*e = xmldoc.createElement("total");
		n = xmldoc.createTextNode(total+"");
		e.appendChild(n);
		root.appendChild(e);
		e = xmldoc.createElement("thanksText");
		n = xmldoc.createTextNode("Thanks");
		e.appendChild(n);
		root.appendChild(e);*/
		xmldoc.appendChild(root);
		return xmldoc;
	}
	public static String applyXsltTemplate(Document xmldoc, File xsltFile){
		//String fileName = FacesUtils.getGeekPropertyValue(fileNameProperty);
		try{
			StringWriter writer = new StringWriter();
			PrintWriter out = new PrintWriter(writer);
			Source xmlSource = new DOMSource(xmldoc);
			Source xsltSource = new StreamSource(xsltFile);
			Result result = new StreamResult(out);
			// create an instance of TransformerFactory
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
