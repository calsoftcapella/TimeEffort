<%@page import="java.util.Collections"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashSet"%>
<%@page import="java.util.StringTokenizer"%>
<%@page import="com.calsoft.task.form.TaskForm"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
body {
	font: normal 14px Calibri, Calibri, Calibri;
}
</style>
<style type="text/css">
    @import "flora.datepick.css";
</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>CalsoftLabs/Timesheet/Time Entry Page</title>
<link rel="stylesheet" type="text/css" href="css/time_entry_page_style.css" >
<link rel="stylesheet" href="css/jquery-ui.css" />		<!--  Stylesheet for Help text -->        
<link rel="stylesheet" type="text/css" href="jquery.confirm/jquery.confirm.css" />
<link href="css/jquery.alerts.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="jquery.confirm/jquery.confirm.js"></script>

<script type="text/javascript" src="js/time_entry_script.js"></script>
<script type="text/javascript" src="js/jquery.datepick.js"></script>
<script type="text/javascript" src="js/jquery.blockUI.js"></script>

<!-- <script type="text/javascript" src="js/sortable.js"></script>        Use this script to sort your table  -->

<script src="js/jquery.ui.draggable.js" type="text/javascript"></script>
<script src="js/jquery.alerts.js" type="text/javascript"></script>

<script type="text/javascript">
	$(function() {
		$('#popupDatepicker').datepick();
	});
	function hideLoadingImageWhileLoading(){
		 $('#loading_image').hide();
	}
</script>
<script type="text/javascript">
	function hideTableView(){
		  document.getElementById('myDiv').style.visibility = "hidden";
		  document.getElementById('ajax_res').innerHTML="<div style=\"color: red;text-align: center;font-family: verdana, arial, sans-serif;font-size: 11px;\">*No records found for this month please click on New Entry button to add a entry.</div>";
		}
	function onGet(url) {
		var submitForm = document.forms[0];
		submitForm.action = url;
		submitForm.submit();
	}
	function pickSelectedResourceFromDropDown(){
		var selectedUserId = document.getElementById('selectedResourceUserId').value;
	    var selectOption = document.getElementById('userId');
	    for (var i=0; i<selectOption.length; i++){
	      if(selectOption.options[i].value==selectedUserId){
	 	    selectOption.options[i].selected="true";
	      }
	    }
	}
</script>
<script type="text/javascript">
	// (a, b) will sort dates in ascending order.
	// (b, a) sorting data in descending order.
	function custom_sort(b, a) {
	    return new Date(a.row_date).getTime() - new Date(b.row_date).getTime();
	}
	 function callScriptForDisplayingCalender(object){
		var currentRow = object.parentNode.parentNode;
		var currentTd = currentRow.firstChild;
		var inputTag = currentTd.firstChild;
       $('#'+inputTag.id).datepick();       
	} 
</script>
<script type="text/javascript">
//configure interval btw flash (1000=1 second)
	var speed=500;
	function flashit(){
		var crosstable=document.getElementById? document.getElementById("myMissingDates") : document.all? document.all.myMissingDates : "";
		if (crosstable){
			if (crosstable.style.borderColor.indexOf("green")!=-1)
			  crosstable.style.borderColor="red";
		    else
			  crosstable.style.borderColor="green";
		}
    }
   setInterval("flashit()", speed);
</script>
<script type="text/javascript">
 function scriptForFreezingTimesheet(){
	 var task_month = $("#selectedMonthId").val();
	 var resource_id = $("#userId").val();
	 if((task_month != '' && task_month != null) && (resource_id != '' && resource_id != null)){
		 var formSubmit = document.forms[0];
		 formSubmit.action = "taskAction1.do?method=freezeTimesheet&task_month="+task_month+"&resource_id="+resource_id;
		 formSubmit.submit();
	 }
 }
 $(document).ready(function() {
		if( $("#notificationId").length != 0) {
			jAlert('Please complete your previous month timesheet entry.', '<img src=\"img/warning.png\" width=\"20px\" height=\"20px\"/> Notification',
				function(r) {
					var submitForm = document.forms[0];
					submitForm.action = 'taskAction1.do?method=getDetailsNotification';
					submitForm.submit();
				});
		 }
	});
</script>
</head>
<body style="margin: 17%; margin-top: 0%; outline-color: blue;">
	<div style="margin-left: 40px;">
			<logic:present name="showAlertNotification" scope="request">
				 <input type="hidden" id="notificationId" value="<bean:write name="showAlertNotification" scope="request"/>" style="width: 0px; height: 0px;">	
			</logic:present>
	        <logic:present name="selectResourceBasedOnUserId" scope="request"> 
			   <input type="hidden" id="selectedResourceUserId" value="<bean:write name="selectResourceBasedOnUserId" scope="request"/>" style="width: 0px; height: 0px;">		
			</logic:present>	
		<html:form action="taskAction1" method="POST">
			<!-- Below script for adding Select Month -->
			<%
					Calendar cal = Calendar.getInstance();
					List<String> list = new ArrayList<String>();
					List<String> list1 = new ArrayList<String>();
					DateFormat df = new SimpleDateFormat("MMM-yyyy");
					DateFormat df1 = new SimpleDateFormat("yyyy-MM");
					list.add(df.format(cal.getTime()));
					list1.add(df1.format(cal.getTime()));
					for (int i = 1; i < 11; ++i) {
						cal.add(Calendar.MONTH, -i);
						list.add(df.format(cal.getTime()));
						list1.add(df1.format(cal.getTime()));
						cal.add(Calendar.MONTH, i);
					}
			%>
			<logic:notPresent name="month" scope="request">
				<table class="sortable1" width="800px;">
					<tr>
						<td style="width: 180px;vertical-align: top;">Select Month:<html:select property="month" styleId="selectedMonthId"
								onchange="onGet('taskAction1.do?method=getDetails')"
								style="font-family: verdana, arial, sans-serif;font-size: 11px;">
								<%
											Iterator<String> itrat1 = list.iterator(); // List containing date format like Jan-2013
											Iterator<String> itrat2 = list1.iterator(); //  List containing date fomate like 2013-01
											while (itrat1.hasNext() && itrat2.hasNext()) {
												String firstDate = itrat1.next();
												String secondDate = itrat2.next();
							%>
								<option value="<%=secondDate%>">
									<%=firstDate%>
								</option>
								<%
								}
							%>
							</html:select></td>
							
							<!-- Resource based Timesheet deatils for Raghavi.  -->
							<logic:present name="userListSelection" scope="request">
							<td style="padding-left: 20px;width: 295px;vertical-align: top;position: absolute;">Select Resource:<html:select property="userId" styleId="userId" onchange="onGet('taskAction1.do?method=getDetailsBasedOnResourceName')" style="width: 180px;font-family: verdana, arial, sans-serif;font-size: 11px;">	
							      <logic:notEmpty name="userListSelection" scope="request">						      
							         <logic:iterate name="userListSelection" id="listForUser" scope="request">						
										<option value="<bean:write name="listForUser" property="userId" />">
											<bean:write name="listForUser" property="userName" />
										</option>
						            </logic:iterate>
						          </logic:notEmpty>							      							      
							</html:select>	
							<script type="text/javascript">
							      pickSelectedResourceFromDropDown();
							</script>				
							</td>							
							<td style="width: 280px;vertical-align: top;">						    						    
						    			<!-- Updates for Missing Entry Table  -->		
						      <logic:present name="allmissingDateList" scope="request">
						       <logic:notEmpty name="allmissingDateList" scope="request">
							    <div style="text-align: right;padding-left: 10px;">
								  <table border="0" width="280" id="myMissingDates" 
				       				style="border:2px dotted green;border-top-left-radius: 0.5em;border-bottom-right-radius: 0.5em;font-family: verdana, arial, sans-serif;font-size: 11px;color: #333333;">
   									<thead>
   										<tr>
   											<th style="text-align: left;background-color: #C0C0C0;">Missing/Shortfall Entries
   											</th>
   										</tr> 
   									</thead>
   									<tbody> 
										<tr>
   											<td style="text-align: left;" id="missingDateTdId">
   												<logic:iterate id="missingDateId" name="allmissingDateList" scope="request" indexId="dConter">
   						    						<a href="##." id="<%=dConter++ %>" onclick="addRowForCurrentDate(this);" style="font-weight: bold;"><bean:write name="missingDateId"/></a>
   						 						</logic:iterate> 	
   						 							<a href="##." onclick="addAllMissingDate(this);"><br><br>Fill All</a>					     							
											</td>
										</tr>
									</tbody>
							    </table>
		   				      </div>	
		    		          </logic:notEmpty>
		    		         </logic:present>	    							
										<!-- Updates for Missing Entry Table  -->											    
						    </td>						  														
							</logic:present> 
							 <!-- Resource based Timesheet deatils for Raghavi.  --> 
						   <logic:notPresent name="userListSelection" scope="request">						    						    
						    <!-- Updates for Missing Entry Table  -->		
						      <logic:present name="allmissingDateList" scope="request">
						       <logic:notEmpty name="allmissingDateList" scope="request">
						       <td style="padding-left: 320px;width: 280px;">
							    <div style="text-align: right;padding-left: 10px;">
								  <table border="0" width="280" id="myMissingDates" 
				       				style="border:2px dotted green;border-top-left-radius: 0.5em;border-bottom-right-radius: 0.5em;font-family: verdana, arial, sans-serif;font-size: 11px;color: #333333;">
   									<thead>
   										<tr>
   											<th style="text-align: left;background-color: #C0C0C0;">Missing/Shortfall Entries</th>
   										</tr> 
   									</thead>
   									<tbody> 
										<tr>
   											<td style="text-align: left;" id="missingDateTdId">
   												<logic:iterate id="missingDateId" name="allmissingDateList" scope="request" indexId="dConter">
   						    						<a href="##." id="<%=dConter++ %>" onclick="addRowForCurrentDate(this);" style="font-weight: bold;"><bean:write name="missingDateId"/></a>
   						 						</logic:iterate> 	
   						 							<a href="##." onclick="addAllMissingDate(this);"><br><br>Fill All</a>					     							
											</td>
										</tr>
									</tbody>
							    </table>
		   				      </div>	
		   				       </td>	
		    		          </logic:notEmpty>
		    		         </logic:present>	    							
										<!-- Updates for Missing Entry Table  -->											    
						 </logic:notPresent>					  
					</tr>
				</table>
			</logic:notPresent>
			<logic:present name="month" scope="request">
				<table class="sortable1" width="800px;">
					<tr>
						<td style="width: 180px;vertical-align: top;">Select Month:<html:select property="month" styleId="selectedMonthId"
								onchange="onGet('taskAction1.do?method=getDetails')"
								style='font-family: verdana, arial, sans-serif;font-size: 11px;'>
								<%											
											String s1 = (String) request.getAttribute("month");
											DateFormat dform1 = new SimpleDateFormat("yyyy-MM");
											DateFormat dform2 = new SimpleDateFormat("MMM-yyyy");
											Date dateOption = dform1.parse(s1);
											String dtOption = dform2.format(dateOption);
											list1.remove(s1); //  List containing date fomate like 2013-01
											list.remove(dtOption); //  List containing date fomate like Jan-2013
											list1.add(0, s1);
											list.add(0, dtOption);
											Iterator<String> itrat11 = list.iterator(); // List containing date format like Jan-2013
											Iterator<String> itrat22 = list1.iterator(); //  List containing date fomate like 2013-01
											while (itrat11.hasNext() && itrat22.hasNext()) {
												String optionDate = itrat11.next();
												String optionValue = itrat22.next();
							%>
								<option value="<%=optionValue%>"><%=optionDate%>
								</option>
								<%
								}
							%>
							</html:select></td>							
							<!-- Resource based Timesheet deatils for Raghavi.  -->
							<logic:present name="userListSelection" scope="request">
							<td style="padding-left: 20px;width: 295px;vertical-align: top;position: absolute;">Select Resource:<html:select property="userId" styleId="userId" onchange="onGet('taskAction1.do?method=getDetailsBasedOnResourceName')" style="width: 180px;font-family: verdana, arial, sans-serif;font-size: 11px;">	
							      <logic:notEmpty name="userListSelection" scope="request">						      
							         <logic:iterate name="userListSelection" id="listForUser" scope="request">						
										<option value="<bean:write name="listForUser" property="userId" />">
											<bean:write name="listForUser" property="userName" />
										</option>
						            </logic:iterate>
						          </logic:notEmpty>							      							      
							</html:select>
							<script type="text/javascript">
							      pickSelectedResourceFromDropDown();
							</script>								
							</td>																		    						    
						    			<!-- Updates for Missing Entry Table  -->		
						      <logic:present name="allmissingDateList" scope="request">
						       <logic:notEmpty name="allmissingDateList" scope="request">
						       <td style="width: 280px;vertical-align: top;">	
							    <div style="text-align: right;padding-left: 10px;">
								  <table border="0" width="280" id="myMissingDates" 
				       				style="border:2px dotted green;border-top-left-radius: 0.5em;border-bottom-right-radius: 0.5em;font-family: verdana, arial, sans-serif;font-size: 11px;color: #333333;">
   									<thead>
   										<tr>
   											<th style="text-align: left;background-color: #A5A1A0;">Missing/Shortfall Entries
   											</th>
   										</tr> 
   									</thead>
   									<tbody> 
										<tr>
   											<td style="text-align: left;" id="missingDateTdId">
   												<logic:iterate id="missingDateId" name="allmissingDateList" scope="request" indexId="dConter">
   						    						<a href="##." id="<%=dConter++ %>" onclick="addRowForCurrentDate(this);" style="font-weight: bold;"><bean:write name="missingDateId"/></a>
   						 						</logic:iterate> 	
   						 							<a href="##." onclick="addAllMissingDate(this);"><br><br>Fill All</a>					     							
											</td>
										</tr>
									</tbody>
							    </table>
		   				      </div>	
		   				      </td>	
		    		          </logic:notEmpty>
		    		         </logic:present>	    							
										<!-- Updates for Missing Entry Table  -->											    
						
							</logic:present>  
						    <!-- Resource based Timesheet deatils for Raghavi.  -->
						    <logic:notPresent name="userListSelection" scope="request">
						    		<!-- Updates for Missing Entry Table  -->		
						      <logic:present name="allmissingDateList" scope="request">
						       <logic:notEmpty name="allmissingDateList" scope="request">
						       <td style="padding-left: 320px;width: 280px;">
							    <div style="text-align: right;padding-left: 10px;">
								  <table border="0" width="280" id="myMissingDates" 
				       				style="border:2px dotted green;border-top-left-radius: 0.5em;border-bottom-right-radius: 0.5em;font-family: verdana, arial, sans-serif;font-size: 11px;color: #333333;">
   									<thead>
   										<tr>
   											<th style="text-align: left;background-color: #A5A1A0;">Missing/Shortfall Entries
   											</th>
   										</tr> 
   									</thead>
   									<tbody> 
										<tr>
   											<td style="text-align: left;" id="missingDateTdId">
   												<logic:iterate id="missingDateId" name="allmissingDateList" scope="request" indexId="dConter">
   						    						<a href="##." id="<%=dConter++ %>" onclick="addRowForCurrentDate(this);" style="font-weight: bold;"><bean:write name="missingDateId"/></a>
   						 						</logic:iterate> 	
   						 							<a href="##." onclick="addAllMissingDate(this);"><br><br>Fill All</a>					     							
											</td>
										</tr>
									</tbody>
							    </table>
		   				      </div>
		   				      </td>	
		    		          </logic:notEmpty>
		    		         </logic:present>	 
		    		         </logic:notPresent>   							
										<!-- Updates for Missing Entry Table  -->
										
					</tr>
				</table>
			</logic:present>
			<div id="ajax_res"></div>
			<logic:present name="resourceLocation" scope="request">
				<input type="hidden" id="resourceLocationId"
					value="<bean:write name="resourceLocation"/>"
					style="width: 0px; height: 0px;">
			</logic:present>
			<div style="text-align: center;">
				<img width="100%" height="100%" id="loading_image" src="img/loader.gif" />
				<script>hideLoadingImageWhileLoading();</script>
			</div>
			<table style="padding-top: 10px;">
				<tr>
					<td
						style='font-family: verdana, arial, sans-serif; font-size: 11px;'>
						<input type="button" onclick="addRow('dailyEntryTable','')" class="changeButtonUI"
						id="new_entry_buttonId" value="New Entry" title="Add time entry."
						style="font-family: verdana, arial, sans-serif; font-size: 11px; text-align: left; cursor: pointer;" />
					</td>
					<td style="text-align: left;"><input type="button"
						id="save_id_buttonId" value="Save All" onclick="saveAllEntry();" class="changeButtonUI"
						title="Click to save all new entries."
						style="font-family: verdana, arial, sans-serif; font-size: 11px; text-align: left; cursor: pointer;" />

					</td>
					<logic:present name="userListSelection" scope="request">
						<td style="text-align: left;"><input type="button"
							id="freeze_entry_id" value="Freeze Entry" onclick="scriptForFreezingTimesheet();" class="changeButtonUI"
							title="Click to freeze timesheet entry for current and all previous months."
							style="font-family: verdana, arial, sans-serif; font-size: 11px; text-align: left; cursor: pointer;" />
						</td>
					</logic:present>
				</tr>
			</table>
			<div id="myDiv"
				style="width: 800px; height: 800px; overflow: auto;">
				<!-- <div style="width: 1250px; height: 400px; overflow: scroll;"> -->
				<table id="dailyEntryTable" class="sortable" border="3"
					width="781px">
					<tr class="head">
						<th>Date</th>
						<th>Category</th>
						<th style="width: 5pc">Backlog ID</th>
						<th style="width: 0pc">Task ID</th>
						<th style="width: 13pc">Description</th>
						<th>Efforts From</th>
						<th style="width: 7pc">Time in Hrs</th>
						<th>Edit<br>/Save</th>
						<th>Delete</th>
					</tr>
					<logic:empty name="tList" scope="request">
						<div id="hide_table">
							<script type="text/javascript"> hideTableView();</script>
						</div>
					</logic:empty>
					<logic:notEmpty name="tList" scope="request">
						<logic:iterate id="tList" name="tList" scope="request"
							type="com.calsoft.task.form.TaskForm">
							<tr id="row_id<bean:write name="tList" property="id" />">
								<logic:equal name="tList" property="status" value="Comp off">
									<td style="color: #008000;"><bean:write name="tList"
											property="task_date" /></td>
									<td style="color: #008000;"><bean:write name="tList"
											property="status" /></td>
									<td style="color: #008000;"><bean:write name="tList"
											property="backlog_id" /></td>
									<td style="color: #008000;"><bean:write name="tList"
											property="task_id" /></td>
									<td style="color: #008000;"><bean:write name="tList"
											property="task_description" /></td>
									<td style="color: #008000;"><logic:equal name="tList"
											property="work_status" value="home">Home</logic:equal> <logic:notEqual
											name="tList" property="work_status" value="home">
											<logic:equal name="tList" property="work_status"
												value="office">Office</logic:equal>
											<logic:notEqual name="tList" property="work_status"
												value="office"></logic:notEqual>
										</logic:notEqual></td>
									<td style="color: #008000;"><bean:write name="tList"
											property="time" /></td>
								</logic:equal>
								<logic:notEqual name="tList" property="status" value="Comp off">
									<td><bean:write name="tList" property="task_date" /></td>
									<td><bean:write name="tList" property="status" /></td>
									<td><bean:write name="tList" property="backlog_id" /></td>
									<td style="width: 3pc"><bean:write name="tList"
											property="task_id" /></td>
									<td style="width: 13pc"><bean:write name="tList"
											property="task_description" /></td>
									<td><logic:equal name="tList" property="work_status"
											value="home">Home</logic:equal> <logic:notEqual name="tList"
											property="work_status" value="home">
											<logic:equal name="tList" property="work_status"
												value="office">Office</logic:equal>
											<logic:notEqual name="tList" property="work_status"
												value="office"></logic:notEqual>
										</logic:notEqual></td>
									<logic:equal name="tList" property="status" value="Half Day">
										<td style="width: 7pc; color: #FF0000;"><bean:write
												name="tList" property="time" /></td>
									</logic:equal>
									<logic:notEqual name="tList" property="status" value="Half Day">
										<logic:equal name="tList" property="status" value="Leave">
											<td style="width: 7pc; color: #FF0000;"><bean:write
													name="tList" property="time" /></td>
										</logic:equal>
										<logic:notEqual name="tList" property="status" value="Leave">
											<logic:equal name="tList" property="status"
												value="Public holiday">
												<td style="width: 7pc; color: #0000CD;"><bean:write
														name="tList" property="time" /></td>
											</logic:equal>
											<logic:notEqual name="tList" property="status"
												value="Public holiday">
												<td><bean:write name="tList" property="time" /></td>
											</logic:notEqual>
										</logic:notEqual>
									</logic:notEqual>
								</logic:notEqual>
								<td>
								 	<img src="img/edit.png" style="cursor: pointer;" title="Click to edit entry." 
								         onclick="editRowInline(this,<bean:write name="tList" property="id" />);"/>
								</td>
								<td>
									<img src="img/delete1.png" style="cursor: pointer;" title="Click to delete entry." 
										 onclick="onDelete('taskAction1.do?method=delete&id=<bean:write name="tList" property="id" />', this)"/>
								</td>
							</tr>
						</logic:iterate>
					</logic:notEmpty>
				</table>
			</div>
		</html:form>
	</div>
</body>
    <script type="text/javascript">
		hideEnteredMissingdate();
	</script>
</html>