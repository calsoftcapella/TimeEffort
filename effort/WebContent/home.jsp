<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<html>
<head>
<style type="text/css">
body {
	font: normal 14px Calibri, Calibri, Calibri;
}
</style>
<link href="css/page.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="theme_roller/main.css">

<link media="screen" type="text/css" href="theme_roller/contentflow.css" rel="stylesheet">
<script type="text/javascript" src="theme_roller/jquery-1.js"></script>
<script type="text/javascript" src="theme_roller/jquery-ui-1.js"></script>
<script type="text/javascript" src="theme_roller/contentflow.js"></script>

<script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="js/popup_page.js"></script>
<script type="text/javascript">
	function submitReport(url) {
		var formSubmit = document.forms[0];
		formSubmit.action = url;
		formSubmit.submit();
	}
	function blinkFont() {
		document.getElementById("blinkIt").style.color = "red";
		setTimeout("setblinkFont()", 1000);
	}
	function setblinkFont() {
		document.getElementById("blinkIt").style.color = "";
		setTimeout("blinkFont()", 1000);
	}	
	function blinkFontFeedback() {
		document.getElementById("feedbackId").style.color = "red";
		setTimeout("setblinkFontFeedback()", 1000);
	}
	function setblinkFontFeedback() {
		document.getElementById("feedbackId").style.color = "";
		setTimeout("blinkFontFeedback()", 1000);
	}
</script>
</head>
<body text="#2B1B17">
	<div style="margin-left: 40px; margin-right: 30px;">
		<div>
			<logic:present name="feedbackListCount" scope="request">
			<logic:present name="viewClientFeedback" scope="request">
				<div id="feedbackId" style="font-size: 16px;font-weight: bolder;font-family: cursive;padding-left: 3px; ">
						Shared Comments:
						<input type="button" id="viewCommentAdded" style="color: rgb(0, 60, 205);" value="<%=request.getAttribute("feedbackListCount") %>"/>
						<div id="element_to_pop_up1" style="display: none;width: 770px;height: 660px;">
							<span style="padding-left: 745px;padding-top: 2px;"><img class="b-close" src="img/del_open_pos.png" style="text-align: right;vertical-align: top;border: 2px;border-style: solid;border-color: black;"/></span>
						<div style="height: 620px;width: 750px;overflow: auto;">
						  <table id="table_for_feedback_list">					     
						    <tbody>
						       <logic:present name="feedbackList" scope="request">
						       <%int cmt = 1; %>
						       <logic:iterate id="feedback" name="feedbackList" scope="request" type="com.calsoft.user.form.ClientFeedbackForm">
						       <tr><td style="padding-bottom: 40px;border: solid;border-style: double;">
						              <input type="hidden"  name="feedbackId" id="<%=feedback.getFeedbackId()%>"/>
						           <span style="font-family: fantasy;font-size: 13px;font-weight: bolder;background-color: yellow;">
						             <%=cmt++ %>.&nbsp;<ins>Comment Shared By: &nbsp;&nbsp;<bean:write name="feedback" property="username"/></ins>
						           </span>
						           <span>
						               <img class="b-close" src='<%=feedback.getFile_loc() %>' style="width: 100%;height: 100%;"/>
						           </span>
						           <div style="padding: 10px;border: solid;border-color: green;font-size: 16px;font-family: cursive;width: 95%;background-color: #DFFFFF;">
						                <bean:write name="feedback" property="body_content"/>
						           </div>
						        </td></tr>  
						       </logic:iterate>
						     </logic:present>					     
						     </tbody>
						  </table>
						</div>						     
						</div>				 
		  	   </div>  
		  	    <script type="text/javascript">
					blinkFontFeedback();
				</script> 
				</logic:present> 
		    </logic:present>	           
		  
			<table align="left"
				style="font-family: verdana, arial, sans-serif; font-size: 11px; color: #000000;">
				<tr>
					<td><div id="blinkIt" style="font-size: 14px;font-weight: bolder;font-family: cursive; ">New Openings:</div>
								<script type="text/javascript">
									blinkFont();
								</script>
							<ul>
								<li><a href=".##" id="open_position_link" style="color: rgb(0, 60, 205);"> Apollo ODC-Open Positions</a></li>
							</ul>
							 <div id="element_to_pop_up" style="padding-left: 40px;display: none;">
							 <table style="width: 760px;">
							 <tr>
							  <td width="550px"><img src="img/img_logo_CalsoftLabs.jpg" style="height: 80px;width: 130px;"/></td>
							  <td style="width: 190px;text-align: right;vertical-align: top;">
							    <img class="b-close" src="img/del_open_pos.png" style="text-align: right;vertical-align: top;border: 2px;border-style: solid;border-color: black; "/>
							  </td>
							 </tr>
							 </table>
							     <div>
							     <hr style="color: red;font: bolder;">
		<div style="height: 560px;width: 760px;overflow: auto;">	
			<table class="job_desc" width="740px" height="650px;" align="center">
				<thead>
					<tr>
						<th colspan="4" style="padding-bottom: 10px;">Apollo ODC-Open
							Position</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td><font face="Calibri" size="2"><strong>Position</strong></font></td>
						<td><font face="Calibri" size="2"><strong>Location & Experience
							</strong></font></td>
						<td><strong><font face="Calibri" size="2">Job
									Description</font></strong></td>
					</tr>
					<tr>
						<td><font face="Calibri" size="2">Back End Developer - Java</font></td>
						<td><font face="Calibri" size="2">Bangalore-3 to 7 years</font></td>
						<td><font face="Calibri" size="2"> <b>Key skills:</b><br><br>
								Java,J2EE,Struts,Spring,Hibernate,Web Services (SOAP or RestFUL). <br>
						<br><b>Position Summary:</b><br><br>
							Designs and develops platform components.<br>
						<br><b>Requirements:</b><br><br>
							Technical skills required include java development experience, including:
							<ul>
								<li>Expertise in all aspects of large scale Java web application development (coding, testing, deployment & maintenance).</li>
								<li>Strong Java, Servlets, Spring, SOAP, RESTful API Struts & Hibernate programming experience.</li>
								<li>Proficiency in various Libraries, Frameworks & Tools used for Java development including Maven 2, Subversion, Eclipse & JUnit.</li>
								<li>Hands on experience implementing Service Oriented Architecture (SOA) using SOAP and/or RESTful APIs.</li>
								<li>Ability to work across various functional groups within an organization and delivering complex projects on time.</li>
								<li>Experience working with Development methodologies like Agile & Scrum.</li>
							</ul>
						   Technology skills which are a plus:
						    <ul>
								<li>Cloud Computing Platforms like Amazon Web Services.</li>
								<li>OSGi, Experience with noSQL stores [Riak, Cassandra etc.], Solr, Lucene.</li>	
							</ul>						
						</font></td>
					</tr>									
					<tr>
						<td><font face="Calibri" size="2">Back End Lead</font></td>
						<td><font face="Calibri" size="2">Bangalore-7 to 12 years</font></td>
						<td><font face="Calibri" size="2"> <b>Key skills:</b><br><br>
								Java,J2EE,Struts,Spring,Hibernate,Web Services (SOAP or RestFUL).<br>
								<br> <b>Position Summary:</b><br>
								<ul>
									<li>Designs, plans and builds major sub systems that combines several platform components.</li>
									<li>Provides technical support and training and assists the Project Manager in planning,
									   organizing and controlling the activities and in the development of the overall project plans and timetables.</li>
									<li>Helps review project architectures for compliance to company policy and standards and for best use of standard technologies.</li>
									<li>Advises on systems development opportunities that can be utilized or adapted to meet business area objectives .</li>
								</ul>
							<b>Requirements:</b><br><br>
  								Technical skills required include java development experience, including:
  								<ul>
									<li>Expertise in all aspects of large scale Java web application development (coding, testing, deployment & maintenance).</li>
									<li>Strong Java, Servlets, Spring, SOAP, RESTful API Struts & Hibernate programming experience.</li>
									<li>Proficiency in various Libraries, Frameworks & Tools used for Java development including Maven 2, Subversion, Eclipse & JUnit.</li>
									<li>Hands on experience implementing Service Oriented Architecture (SOA) using SOAP and/or RESTful APIs.</li>
									<li>Ability to work across various functional groups within an organization and delivering complex projects on time.</li>
									<li>Experience working with Development methodologies like Agile & Scrum.</li>
									</ul>
							   Technology skills which are a plus:
							   <ul>
								    <li>Cloud Computing Platforms like Amazon Web Services.</li>
								    <li>OSGi, Experience with noSQL stores [Riak, Cassandra etc.], Solr, Lucene.</li>
							  </ul>	
						</font></td>
					</tr>								
					<tr>
						<td><font face="Calibri" size="2">Test engineer - Selenium</font></span></td>
						<td><font face="Calibri" size="2">Bangalore/Chennai-<br>3 to 6 years.</font></td>
						<td><font face="Calibri" size="2"> <b>Key skills:</b><br><br>
										Selenium RC, Selenium Grid, Java, TestNG, JUNIT. <br>
									<br><b>Position Summary:</b><br><br>
										Work on automation of products using Java and Selenium.<br>
									<br><b>Requirements:</b><br>
									<ul>
										<li>Qualification: B.E, B.Tech, M.E, M.Tech, MCA.</li>
										<li>Must have worked with Automation Testing.</li>
										<li>Experience and Expertise with Selenium RC, Selenium Grid, Java, TestNG, JUNIT etc.</li>
										<li>Good in STLC.</li>
									</ul>	
						</font></td>
					</tr>									
					<tr>
						<td><font face="Calibri" size="2">Performance Tester</font></td>
						<td><font face="Calibri" size="2">Bangalore/Chennai-<br>3 to 6 years.</font></td>
						<td><font face="Calibri" size="2"> <b>Key skills:</b><br><br>
									Load runner, Websevices.<br>
								<br><b>Position Summary:</b><br><br>
									Work on testing using Load runner Requirements:
									<ul>
									  <li>Qualification: B.E, B.Tech, M.E, M.Tech, MCA.</li>
									  <li>Strong Load runner experience; Good in load runner scripting using c (not record and play back).</li>
									  <li>REST and web services.</li>
									  <li>Good Understand of multi tire architecture.</li>
									  <li>Identifying application bottleneck.</li>
									  <li>Experience on diagnostic tool like New Relic , jconsole , VisualVm, splunk,wily ..etc.</li>
									  <li>Tradeoff between hits/sec , throughput ,response time and server resource usage.</li>
									  <li>Understanding of JVM performance parameters.</li>
									</ul>  							
						</font></td>
					</tr>				
					<tr>
						<td><font face="Calibri" size="2">Test engineer - Java</font></span></td>
						<td><font face="Calibri" size="2">Bangalore/Chennai-<br>3 to 8 years.</font></span></td>
						<td><font face="Calibri" size="2"> <b>Key skills:</b><br><br>
									Java, TestNG, REST/SOAP Webservices. <br>
								<br><b>Position Summary:</b><br><br>
									Work on automation of products using Java and Selenium.<br>
								<br><b>Requirements:</b>
								  <ul>
									<li>Qualification: B.E, B.Tech, M.E, M.Tech, MCA.</li>
									<li>Must have worked with Automation Testing.</li>
									<li>Experience and Expertise with Java, TestNG, REST/SOAP Webservices etc.</li>
									<li>Good in STLC.</li>
								  </ul>
						</font></td>
					</tr>				
					<tr>
						<td><font face="Calibri" size="2">Front End Senior<br> Developer</font></td>
						<td><font face="Calibri" size="2">Chennai-5 to 12 years</font></td>
						<td><font face="Calibri" size="2"> <b>Key skills:</b><br><br>
							Java, JavaScript, Ajax, HTML, CSS, Web2.0, OOAD, GWT, GXT, Ext GWT, SmartGWT, Widgets, XML, JSON, SOAP/RESTFUL Web services, Oracle. <br>

						<br><b>Position Summary:</b>
						<ul>
							<li>Develops program specifications/detail design documents.</li>
							<li>Codes, tests and debugs standalone platform (service) components.</li>
							<li>Participate in the development of platform components (web services, SOAP/REST etc.).</li>
							<li>Codes, tests and debugs components.</li>
							<li>Assists users in testing, training and preparation for operations.</li>
							<li>Demonstrates an understanding of object-oriented development tools and techniques, has worked on multiple platforms and/or with multiple methodologies.</li>
							<li>Responsible for coding, testing and analyzing UI application software.</li>
						</ul>
					   <b>Requirements:</b>
							<ul>
							   <li>Qualification: B.E / B.Tech / M.E / M.Tech / MCA / M.Sc.</li>
							   <li>Minimum 3+ yrs. of experience in UI Design & Development in Core Java / Swing / JFaces / AWT.</li>
							   <li>Hands on experience in Object oriented JavaScript.</li>
							   <li>Experience in Web 2.0 technologies like HTML5, CSS, DHTML, XHTML, AJAX, Cross Domain Ajax, JavaScript etc.</li>
							   <li>Strong hands on experience in Front End Design & Development using Java & JavaScript Framework like GWT (Type of GWT Framework & its Widgets. To know how internals of GWT Works, Compiles, events, deferred binding, Code Splitting etc.).</li>
							   <li>Good hands on experience in JSNI.</li>
							   <li>If hands on experience in Web2.0 Technologies (Nice to have), Agile/Scrum Methodologies (Nice to have), .Net exposure (Nice to have), desktop application experience (Nice to have), some JUNIT and Test Driven Development experience (ice to have).</li>
							   <li>Holding US B1 Visa with multiple entries is an added advantage.</li>
							</ul>
						</font></td>
					</tr>									
					<tr>
						<td><font face="Calibri" size="2">Front End Developer</font></td>
						<td><font face="Calibri" size="2">Chennai-3 to 5 years</font></td>
						<td><font face="Calibri" size="2"> <b>Key skills:</b><br><br>
										Java, JavaScript, Ajax, HTML, CSS, Web2.0, OOAD, GWT, GXT, Ext GWT, SmartGWT, Widgets, XML, JSON, SOAP/RESTFUL Web services, Oracle. <br>
							<br><b>Position Summary:</b>
							      <ul>
										<li>Develops program specifications/detail design documents.</li>
										<li>Codes, tests and debugs standalone platform (service) components.</li>
										<li>Participate in the development of platform components (web services, SOAP/REST etc.).</li>
										<li>Codes, tests and debugs components.</li>
										<li>Assists users in testing, training and preparation for operations.</li>
										<li>Demonstrates an understanding of object-oriented development tools and techniques, has worked on multiple platforms and/or with multiple methodologies.</li>
										<li>Responsible for coding, testing and analyzing UI application software.</li>
								  </ul>
							<b>Requirements:</b>
							     <ul>
										<li>Qualification: B.E / B.Tech / M.E / M.Tech / MCA / M.Sc.</li>
										<li>Minimum 3+ yrs. of experience in UI Design & Development in Core Java / Swing / JFaces / AWT.</li>
										<li>Hands on experience in Object oriented JavaScript.</li>
										<li>Experience in Web 2.0 technologies like HTML5, CSS, DHTML, XHTML, AJAX, Cross Domain Ajax, JavaScript etc.</li>
										<li>Strong hands on experience in Front End Design & Development using Java & JavaScript Framework like GWT (Type of GWT Framework & its Widgets. To know how internals of GWT Works, Compiles, events, deferred binding, Code Splitting etc.).</li>
										<li>Good hands on experience in JSNI.</li>
										<li>If hands on experience in Web2.0 Technologies (Nice to have), Agile/Scrum Methodologies (Nice to have), .Net exposure (Nice to have), desktop application experience (Nice to have), some JUNIT and Test Driven Development experience (ice to have).</li>
										<li>Holding US B1 Visa with multiple entries is an added advantage.</li>
								</ul>
						</font></td>
					</tr>					
					<tr>
						<td><font face="Calibri" size="2">Development Manager</font></td>
						<td><font face="Calibri" size="2">Chennai-12+ years</font></td>
						<td><font face="Calibri" size="2"> <b>Key skills:</b>
						   <ul>
								<li>Java, JavaScript, Ajax, HTML, CSS, Web2.0, OOAD, Widgets, XML, JSON, SOAP/RESTFUL Web services, Oracle.</li>
								<li>Qualification: B.E, B.Tech, M.E, M.Tech, MCA.</li>
						   </ul>		
						<b>Requirements:</b>
						 <ul>
								<li>Minimum of 5 years of management/ leadership experience in a software development organization required.</li>
								<li>Must have at least 7 years experience working through the design, development, release cycle, and delivering software products to market.</li>
								<li>Experience in Java programming and J2EE/JBoss application server software development (EJB,JMS, JDBC, JTA).</li>
								<li>Relevant experience with web services and REST.</li>
								<li>Experience with EJB 3 and/or Spring frameworks.</li>
								<li>Understanding of relational databases (MSSQL,MySQL).</li>
								<li>Knowledge of Hibernate framework.</li>
								<li>Willingness to dive into software environments and technical issues .</li>
								<li>Experience with Scrum methodology and Agile practices.</li>
						 </ul>				
						</font></td>
					</tr>					
					<tr>
						<td><font face="Calibri" size="2">Front End Architect </font></td>
						<td><font face="Calibri" size="2">Chennai-10+ years</font></td>
						<td><font face="Calibri" size="2"> <b>Requirements:</b>		
						        <ul>			   
									<li>Must be very strong in Data structures and algorithms â  should be able to explain different data structures, computation time, scalability and able to measure/code the algorithms efficiently.</li>
									<li>Must have at least 10 year of GWT experience.</li>
									<li>Must have good knowledge in JavaScript, including OOPS concepts such as prototypes.</li>
									<li>Must have good knowledge in Web 2.0 (Ajax), and latest trends and technologies in web.</li>
									<li>Agile experience, Usage of mock frameworks, Browser based application development, CSS.</li>
									</ul>
						</font></td>
					</tr>
				</tbody>

			</table>
			</div>
		</div>						 
							 </div>						
							<logic:present name="userObjective" scope="request">
							<logic:iterate id="any" name="userObjective" scope="request"
								type="com.calsoft.user.form.AppraisalForm">
								<table width="708px"
									style="border: solid 3px #a9c6c9; border-style: dotted; font-family: verdana, arial, sans-serif; font-size: 11px; background-color: #FFFFFF;">
									<thead style="font-weight: bold;">Objectives</thead>
									<tr>
										<td><b>Common Objective :</b><textarea  readonly="readonly" cols="1" rows="4"><bean:write name="any" property="commObjective" /></textarea></td>
									</tr>
									<tr>
										<td><b>Role Specific Objective: </b><textarea  readonly="readonly" cols="1" rows="4"><bean:write name="any" property="specObjective" /></textarea></td>
									</tr>
								</table>
							</logic:iterate>
						</logic:present> <logic:notPresent name="userObjective" scope="request">
							<table width="708px"
								style="border: solid 3px #a9c6c9; border-style: dotted; font-family: verdana, arial, sans-serif; font-size: 11px; background-color: #FFFFFF;">
								<thead style="font-weight: bold;">Objectives</thead>
								<tr>
									<td><b>General</b>
										<ul>
											<li>Ownership at work.</li>
											<li>Planning and executing tasks.</li>
											<li>Communicate effectively with internal and external
												teams.</li>
											<li>Reporting status and issues.</li>
											<li>Update leave plan, VersionOne and timesheet
												regularly.</li>
											<li>Understand big picture.</li>
											<li>Good documentation with all necessary details that
												helps in implementation, integration and support.</li>
										</ul></td>
								</tr>
								<tr>
									<td><b>Role Specific </b>
									<ul>
											<li>To be defined.</li>
										</ul></td>
								</tr>
							</table>
						</logic:notPresent></td>
				</tr>

				<tr>
					<th align="left" style="padding-top: 10px;"><b>Information</b></th>
				</tr>
				<tr>
					<td align="left">

						<div class="boxMess">
							<img src="img/pointing.png" style="height: 50px; width: 60px"
								align="right" />
							<ul>
								<li>Enter timesheets regularly.</li>
								<li>Enter actual effort spent in hours.</li>
								<li>Add sufficient details to substantiate the effort spent
									against a task.</li>
								<li>Enter Backlog ID and Task ID for project related tasks.
									Mark 'NA' for not applicable and 'NIL' for not available.</li>
								<li>Don't enter status of the task in timesheet like
									In-Progress or completed.</li>
								<li>Enter what is done today related to the task.</li>
								<li>Make entries for leaves as well with <b>0</b> hrs as
									Timespent.</li>
								<li>Non-entries would be considered as leaves.</li>
								<li>Maximum time limit for a day is <b>23</b> hours.</li>
							</ul>
						</div> <br>
						<%-- <div
							style="font-family: verdana, arial, sans-serif; font-size: 11px;">
							<font style="font-weight: bold;">Useful links:</font>
							<ul>
								<li><a href="http://intranet.calsoftlabs.com/"
									target="_blank">Office connect</a></li>
								<li><a href="http://intranet.calsoftlabs.com/travel/"
									target="_blank">Travel Planner</a></li>
								<li><a href="http://www.calsoftlabs.com/" target="_blank">Calsoft
										Labs Website</a></li>
								<li><a href="https://wiki.apollogrp.edu/display/NGP/Calsoft+Consulting+-+Home" target="_blank">Calsoft-Apollo ODC</a></li>
								<logic:present name="viewClientFeedback" scope="request">
								     <li><a href="clientFeedback.do?method=viewClientFeedback" target="_blank">Client feedback page</a></li>
								</logic:present>
							</ul>
						</div> --%>
						<div id="knowledge-centre-section">
							<div
								style="height: 140px; border: dotted 3px #a9c6c9; padding: 0px; width: 700px;"
								id="contentFlow" class="ContentFlow ContentFlowAddOn_DEFAULT">
								<!-- should be place before flow so that contained images will be loaded first -->
								<div style="visibility: visible;" class="flow km-bg">
									<div style="display: block; left: 290.666px; top: 207.188px; height: 123.094px; width: 121.875px; z-index: 32764; visibility: visible;"
										class="item ">
										<img origproportion="NaN" class="content landscape"
											href="http://intranet.calsoftlabs.com/travel/"
											src="theme_roller/calsoft_travel.png" target="_blank">
										<div class="caption">
											<div class="kc-detail">Travel Planner.</div>
										</div>
									</div>
									<logic:present name="viewClientFeedback" scope="request">
									<div
										style="display: block; left: 378.125px; top: 146.25px; height: 246.188px; width: 243.75px; z-index: 32768; visibility: visible;"
										class="item ">
										<img origproportion="NaN" class="content landscape"
											href="./clientFeedback.do?method=viewClientFeedback"
											src="theme_roller/Client_Feedback.png" target="_blank">
										<div class="caption">
											<div class="kc-detail">Go to Client Feedback Page.</div>
										</div>
									</div>
									</logic:present>
									<div
										style="display: block; left: 240.88px; top: 227.5px; height: 82.0625px; width: 81.25px; z-index: 32761; visibility: visible;"
										class="item ">
										<img origproportion="NaN" class="content landscape"
											href="http://www.calsoftlabs.com/"
											src="theme_roller/calsoft_labs_site.png" target="_blank">
										<div class="caption">
											<div class="kc-detail">Go to Calsoft Labs Website.</div>
										</div>
									</div>	
									<div
										style="display: block; left: 217.925px; top: 237.656px; height: 61.5469px; width: 60.9375px; z-index: 32757; visibility: visible;"
										class="item ">
										<img origproportion="NaN" class="content landscape"
											href="https://linmail.calsoftlabs.com/"
											src="theme_roller/alinmail_snap.png" target="_blank">
										<div class="caption">
											<div class="kc-detail">Check Calsoft Labs Mail.</div>
										</div>

									</div>
									<div
										style="display: block; left: 240.88px; top: 227.5px; height: 82.0625px; width: 81.25px; z-index: 32761; visibility: visible;"
										class="item ">
										<img origproportion="NaN" class="content landscape"
											href="http://intranet.calsoftlabs.com/Login.aspx"
											src="theme_roller/office_connect.png" target="_blank">
										<div class="caption">
											<div class="kc-detail">Go to Office Connect Website.</div>
										</div>
									</div>
									<div
										style="display: block; left: 240.88px; top: 227.5px; height: 82.0625px; width: 81.25px; z-index: 32761; visibility: visible;"
										class="item ">
										<img origproportion="NaN" class="content landscape"
											href="https://wiki.apollogrp.edu/display/NGP/Calsoft+Consulting+-+Home"
											src="theme_roller/apollo_odc.png" target="_blank">
										<div class="caption">
											<div class="kc-detail">Calsoft Apollo ODC.</div>
										</div>
									</div>															
								</div>
								<div class="globalCaption"><div class="caption"></div></div>
								<div class="mouseoverCheckElement"></div>	
							</div>
						</div>
		
					</td>
				</tr>

			</table>
		</div>

		<div style="width: 100%; margin-left: -10px;">
			<table align="left"
				style="font-family: verdana, arial, sans-serif; font-size: 11px; color: #000000;">
				<tr>
					<td width="100%">
						<form action="LeaveAction" method="POST">
							<table align="left" height="100"
								style="width: 113%; font-family: verdana, arial, sans-serif; font-size: 11px;">
								<tr>
									<td valign="top">
										<table align="left">
											<tr>
												<td align="left" valign="top" width="400"><logic:notEmpty
														name="lessTask" scope="request">
														<table>
															<tr>
																<td>
																	<table>
																		<tr align="left">
																			<td align="left" valign="top"><b>Messages</b></td>
																		</tr>
																	</table>
																</td>
															</tr>
															<tr>
																<td>
																	<div style="height: 5cm; width: 85mm; overflow: auto;">
																		<table align="left"
																			style="font-family: verdana, arial, sans-serif; font-size: 11px;">
																			<tr>
																				<td align="center">
																					<div class="box">
																						<table
																							style="font-family: verdana, arial, sans-serif; font-size: 11px;">
																							<logic:notEmpty scope="request" name="errorSheet">
																								<tr>
																									<td height="15px"><bean:write
																											name="errorSheet" scope="request" /></td>
																								</tr>
																							</logic:notEmpty>
																							<logic:notEmpty scope="request" name="lessTask">
																								<logic:iterate name="lessTask" id="lessTaskId"
																									scope="request">
																									<tr>
																										<td width="100%" height="15px"><bean:write
																												name="lessTaskId" /></td>
																									</tr>
																								</logic:iterate>
																							</logic:notEmpty>
																						</table>
																					</div>
																				</td>
																			</tr>
																		</table>
																	</div>
																</td>
															</tr>
														</table>
													</logic:notEmpty> <logic:empty name="lessTask" scope="request">
														<logic:notEmpty scope="request" name="errorSheet">
															<table>
																<tr>
																	<td>
																		<table>
																			<tr align="left">
																				<td align="left" valign="top"><b>Messages</b></td>
																			</tr>
																		</table>
																	</td>
																</tr>
																<tr>
																	<td><logic:notEmpty scope="request"
																			name="errorSheet">
																			<div
																				style="height: 5.5cm; width: 85mm; overflow: auto;">
																				<table align="left"
																					style="font-family: verdana, arial, sans-serif; font-size: 11px;">
																					<tr>
																						<td align="center">

																							<div class="box">
																								<table
																									style="font-family: verdana, arial, sans-serif; font-size: 11px;">

																									<tr>
																										<td height="15px"><bean:write
																												name="errorSheet" scope="request" /></td>
																									</tr>

																								</table>
																							</div>
																						</td>

																					</tr>
																				</table>
																			</div>
																		</logic:notEmpty></td>
																</tr>
															</table>
														</logic:notEmpty>
													</logic:empty></td>
												<td width="270" align="left" valign="top"><logic:notEmpty
														name="userlist" scope="request">
														<table style="margin-left: 10px;">
															<thead>
																<tr>
																	<b>&nbsp;&nbsp;&nbsp;&nbsp;Timesheet Dashboard</b>
																</tr>
															</thead>
															<tr>
																<td>
																	<div
																		style="height: 5.5cm; width: 80mm; overflow: auto;">
																		<table class="hovertable" align="left">
																			<tr>
																				<th align="center">Date</th>
																				<th align="center">Time Spent in Hours</th>
																			</tr>
																			<logic:present name="userlist" scope="request">
																				<logic:iterate id="userd" name="userlist" scope="request" type="com.calsoft.task.form.TaskForm">
																					<tr>
																						<td width="50%" align="center"><bean:write
																								name="userd" property="task_date" /></td>
																						<td width="50%" align="center">
																						<logic:notEqual name="userd" property="status" value="Leave">
																								<logic:equal name="userd" property="status" value="Half Day">
																									<font color="#FF0000"><bean:write
																											name="userd" property="time" /> (H)</font>
																								</logic:equal>
																								<logic:notEqual name="userd" property="status" value="Half Day">
																									<%-- <bean:write name="userd"  property="time" /> --%>
																									<logic:equal name="userd" property="status" value="Public holiday">
																										<font color="#0000CD"><bean:write
																												name="userd" property="time" /></font>
																									</logic:equal>
																									<logic:notEqual name="userd" property="status" value="Public holiday">
																										<logic:equal name="userd" property="status" value="Comp off">
																											<font color="#228b22"><bean:write name="userd" property="time" />(C)</font>
																									   </logic:equal>
																									   <logic:notEqual name="userd" property="status" value="Comp off">
																										     <bean:write name="userd" property="time" />
																									  </logic:notEqual>
																									</logic:notEqual>
																								</logic:notEqual>
																						</logic:notEqual> 
																						<logic:equal name="userd" property="status" value="Leave">
																								<font color="#FF0000"><bean:write name="userd" property="time" /></font>
																						</logic:equal></td>
																					</tr>
																				</logic:iterate>
																			</logic:present>

																		</table>
																	</div>
																</td>
															</tr>
														</table>
													</logic:notEmpty></td>
											</tr>

										</table>
									</td>
								</tr>
							</table>
						</form>
					</td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>