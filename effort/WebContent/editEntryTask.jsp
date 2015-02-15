<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.List"%>
<%@page import="java.util.StringTokenizer"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.calsoft.task.form.TaskForm"%>
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
table.hovertable1 {
	font-family: verdana, arial, sans-serif;
	font-size: 11px;
	color: #333333;	
	align: center;
}
table.hovertable {
	font-family: verdana, arial, sans-serif;
	font-size: 11px;
	color: #333333;
	border-width: 1px;
	border-color: #999999;
	align: center;
	border-collapse: collapse;
}
table.hovertable th {
	background-color: #A5A1A0;
	border-width: 1px;
	padding: 0px;
	border-style: solid;
	border-color: #a9c6c9;
}
/* table.hovertable tr:nth-child(odd) 
  { background-color:#eee; }                                                  For differentiating Row Color.  
table.hovertable tr:nth-child(even)    { background-color:#fff; } */
table.hovertable td {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #a9c6c9;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>CalsoftLabs/Timesheet/Time Entry Page</title>
<script type="text/javascript">
	function onClickLeave(url) {
		var submitForm = document.forms[0];
		submitForm.action = url;
		submitForm.submit();
	}
</script>
<script type="text/javascript">
	function onDelete(url) {
		var where_to = confirm("Want to delete the entry??");
		if (where_to == true) {
			var submitForm = document.forms[0];
			submitForm.action = url;
			submitForm.submit();
		}
	}
</script>
<script type="text/javascript">
function changetextbox()
{
	var a=document.getElementById("disable").value ;
    if (a =="Leave"){    	
        document.getElementById("disableBacklog").disabled='true';       
        document.getElementById("disableTask").disabled='true';
    } 
    else  if (a =="Public holiday"){    	
        document.getElementById("disableBacklog").disabled='true';        
        document.getElementById("disableTask").disabled='true';
    }     
    else  if (a =="Half Day"){ 	
     document.getElementById("disableBacklog").disabled='true';     
     document.getElementById("disableTask").disabled='true';
    } 
    else  if (a =="Comp off" || a == "Down Time"){ 	
     document.getElementById("disableBacklog").disabled='true';     
     document.getElementById("disableTask").disabled='true';
    } 
    else  if (a =="Travel"){ 	
     document.getElementById("disableBacklog").disabled='true';     
     document.getElementById("disableTask").disabled='true';
    }     
    else if( document.getElementById("disableBacklog").disabled){    	          	
     document.getElementById("disableBacklog").disabled=false;           
     document.getElementById("disableTask").disabled=false;
    } 
}
</script>
<script type="text/javascript">
	function onClickLeave(url) {
		var submitForm = document.forms[0];
		submitForm.action = url;
		submitForm.submit();
	}
</script>
<script type="text/javascript">
	function onEdit(url) {
		var formSubmit = document.forms[0];
		formSubmit.action = url;
		var selectedUserId = document.getElementById('disable').value;
		if (selectedUserId == 'Leave'||selectedUserId == 'Public holiday'||selectedUserId =='Half Day'||selectedUserId =='Comp off'||selectedUserId =='Travel') 
		{
			var time = document.getElementById('time').value;
			var date = document.getElementById('popupDatepicker').value;			
			if((time=='')||(date==''))
				{				
			       if(date=='')
					{				
				      document.getElementById('checkUser').innerHTML = "<font style='color:red;font-family:verdana,arial,sans-serif;font-size: 11px;'>"
						+ 'Enter date' + "</font>";
					}
			       if(time=='')
		    	   {
		      document.getElementById('checkUser').innerHTML = "<font style='color:red;font-family:verdana,arial,sans-serif;font-size: 11px;'>"
				+ 'Enter time' + "</font>";
		    	   }		       		       			      
				}
			else if(time!='')
				{				
				var numaric = time;
				 var x=0;
				for(var j=0; j<numaric.length; j++)
					{
					 
					  var alphaa = numaric.charAt(j);
					  var hh = alphaa.charCodeAt(0);
					  if((hh > 64 && hh<91) || (hh > 96 && hh<123))
					  {
						  document.getElementById('checkUser').innerHTML = "<font style='color:red;font-family:verdana,arial,sans-serif;font-size: 11px;'>"
								+ 'Enter  valid time' + "</font>";
							x=x+1; 
					  }					  
					  else if((hh==46))
						  {						  
						  var dec=numaric.split(".");
						  var cc=dec[1];						  
						  var check=cc.charAt(0);						 
						  if(check>5)
							  {
							  document.getElementById('checkUser').innerHTML = "<font style='color:red;font-family:verdana,arial,sans-serif;font-size: 11px;'>"
									+ 'Enter  valid time' + "</font>";
								x=x+1; 
							  }						  
						  }
					 else
						  {
						    formSubmit.submit();}					
			 		      }				
				}							
			else 
			{
				formSubmit.submit();
			}
		} 				
		 if (selectedUserId != 'Leave'||selectedUserId != 'Public holiday'||selectedUserId!= 'Half Day'||selectedUserId !='Comp off'||selectedUserId !='Travel') 
		  {
			var time = document.getElementById('time').value;
			var date = document.getElementById('popupDatepicker').value;
			var taskId = document.getElementById('disableTask').value;
			var backlogId = document.getElementById('disableBacklog').value;
			var timeError='';var dateError='';
			var timeError1='';
			var timeError2='';
			var taskError='';
			var backLogError='';
			if((date=='')||(taskId=='')||(backlogId==''))
				{										    
			     if(date=='')
		    	 {		    	
		    	 dateError="<font style='color:red' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"	+ 'Enter Date' + "</font>";
		      document.getElementById('checkUser').innerHTML = "<font style='color:red;font-family:verdana,arial,sans-serif;font-size: 11px;'>"
				+ 'Enter Date' + "</font>";
		    	 }			     
			     if(time=='')
		    	 {		    	
		    	 timeError="<font style='color:red;font-family:verdana,arial,sans-serif;font-size: 11px;' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"	+ 'Enter time' + "</font>";
		      document.getElementById('checkUser').innerHTML = dateError+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font style='color:red;font-family:verdana,arial,sans-serif;font-size: 11px;'>"
				+ 'Enter time' + "</font>";
		    	 }
			     if(time!='')
					{					
						var numaric = time;
						 var x=0;
						for(var j=0; j<numaric.length; j++)
							{							 
							  var alphaa = numaric.charAt(j);
							  var hh = alphaa.charCodeAt(0);
							  if((hh > 64 && hh<91) || (hh > 96 && hh<123))
							  {
								  timeError1="<font style='color:red;font-family:verdana,arial,sans-serif;font-size: 11px;' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"	+ 'Enter  valid time' + "</font>";
								  document.getElementById('checkUser').innerHTML = "<font style='color:red;font-family:verdana,arial,sans-serif;font-size: 11px;'>"
										+ 'Enter  valid time' + "</font>";
									x=x+1; 
							  }							  
							  else if((hh==46))
								  {								  
								  var dec=numaric.split(".");
								  var cc=dec[1];								  
								  var check=cc.charAt(0);								 
								  if(check>5)
									  {
									  timeError2="<font style='color:red;font-family:verdana,arial,sans-serif;font-size: 11px;' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"	+ 'Enter  valid time' + "</font>";
									  document.getElementById('checkUser').innerHTML = "<font style='color:red;font-family:verdana,arial,sans-serif;font-size: 11px;'>"
											+ 'Enter  valid time' + "</font>";
										x=x+1; 
									  }								  
								  }							
					 		}											  
					}				      				
				if(taskId=='')
				{
					taskError="<font style='color:red;font-family:verdana,arial,sans-serif;font-size: 11px;'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+'Enter taskId' + "</font>";					
			      document.getElementById('checkUser').innerHTML = "<font style='color:red;font-family:verdana,arial,sans-serif;font-size: 11px;'>"
					+ 'Enter taskId' + "</font>"+dateError+timeError+timeError1+timeError2;
				}				
				if(backlogId=='')
				{
				 backLogError="<font style='color:red;font-family:verdana,arial,sans-serif;font-size: 11px;'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+ 'Enter backlogId' + "</font>";
			
			      document.getElementById('checkUser').innerHTML = "<font style='color:red;font-family:verdana,arial,sans-serif;font-size: 11px;'>"
					+ 'Enter backlogId' + "</font>"+taskError+dateError+timeError+timeError1+timeError2;
				}								
				}						
			else if((date!='')&&(taskId!='')&&(backlogId!='')&&(time==''))
				{				 
				 document.getElementById('checkUser').innerHTML = "<font style='color:red;font-family:verdana,arial,sans-serif;font-size: 11px;'>"
						+ 'Enter time' + "</font>"				 
				}
			else if((date!='')&&(taskId!='')&&(backlogId!='')&&(time!=''))
				{								
				var numaric = time;
				var x=0;
				for(var j=0; j<numaric.length; j++)
					{					 
					  var alphaa = numaric.charAt(j);
					  var hh = alphaa.charCodeAt(0);
					  if((hh > 64 && hh<91) || (hh > 96 && hh<123))
					  {
						  timeError1="<font style='color:red;font-family:verdana,arial,sans-serif;font-size: 11px;' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"	+ 'Enter  valid time' + "</font>";
						  document.getElementById('checkUser').innerHTML = "<font style='color:red;font-family:verdana,arial,sans-serif;font-size: 11px;'>"
								+ 'Enter  valid time' + "</font>";
							x=x+1; 
					  }					  
					  else if((hh==46))
						  {						  
						  var dec=numaric.split(".");
						  var cc=dec[1];						  
						  var check=cc.charAt(0);						 
						  if(check>5)
							  {
							  timeError2="<font style='color:red;font-family:verdana,arial,sans-serif;font-size: 11px;' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"	+ 'Enter  valid time' + "</font>";
							  document.getElementById('checkUser').innerHTML = "<font style='color:red;font-family:verdana,arial,sans-serif;font-size: 11px;'>"
									+ 'Enter  valid time' + "</font>";
								x=x+1; 
							  }						  
						  }					
			 		}				
				if(x==0)formSubmit.submit();				
				}			
			else {
				formSubmit.submit();
			     }	
		} 	
	}	
</script>
<script type="text/javascript">
	function onEntry(url) {
		var submitForm = document.forms[0];
		submitForm.action = url;
		submitForm.submit();
	}
</script>
<script type="text/javascript">
	function onSave(url) {
		var submitForm = document.forms[0];
		submitForm.action = url;
		submitForm.submit();
	}
</script>
<script type="text/javascript">
	function onGet(url) {
		var submitForm = document.forms[0];
		submitForm.action = url;
		submitForm.submit();
	}
	function clearForm()
	{
	 document.forms[0].task_date.value="";
	 document.forms[0].backlog_id.value="";
	 document.forms[0].task_id.value="";
	 document.forms[0].task_description.value="";
	 document.forms[0].time.value="";
	 document.forms[0].id.value=0;
	
	}	
</script>
<script type="text/javascript">
	function submitForm(url) {
		var submitForm = document.forms[0];
		submitForm.action = url;
		submitForm.submit();
	}
</script>
<script type="text/javascript">
function submitReport(url)
{	
	var formSubmit=document.forms[0];
	formSubmit.action=url;
	formSubmit.submit();	
	}
</script>
<script type="text/javascript">
	function onReset(url) {
		var submitForm = document.forms[0];
		submitForm.action = url;
		submitForm.submit();
	}
</script>
<style type="text/css">
@import "flora.datepick.css";
</style>
<script type="text/javascript" src="jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="jquery.datepick.js"></script>
<script type="text/javascript">
$(function() {
	$('#popupDatepicker').datepick();	
});
</script>
</head>
<body style="margin: 17%;margin-top: 0%">
<div style="margin-left: 40px;">
	<html:form action="taskAction" method="POST">		
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
			<table  style="font-family: verdana, arial, sans-serif;font-size: 11px;">
			<tr><td>
			<div style="font-family: verdana, arial, sans-serif;font-size: 11px;">
			    Select Month:
			</div></td>
			    <td><html:select property="month" onchange="onGet('taskAction1.do?method=getDetails')" style="font-family: verdana, arial, sans-serif;font-size: 11px;">
			<%
					Iterator<String> itrat1 = list.iterator(); // List containing date format like Jan-2013
					Iterator<String> itrat2 = list1.iterator(); //  List containing date fomate like 2013-01
					while (itrat1.hasNext() && itrat2.hasNext()) {
						String firstDate = itrat1.next();
						String secondDate = itrat2.next();
			 %>
			    <option value="<%=secondDate%>"><%=firstDate%></option>
				<%}%>
			</html:select>
			</td>
			 <td><input type="button" value="New Entry" onclick="onEntry('taskAction1.do?method=entry')" name="newEntry" style="font-family: verdana, arial, sans-serif;font-size: 11px;"" /></td>
			</tr>
			</table>			
			</logic:notPresent>						
			<logic:present name="month" scope="request">
			<table  style="font-family: verdana, arial, sans-serif;font-size: 11px;"><tr><td>Select Month:</td>
			<td> <html:select property="month" onchange="onGet('taskAction1.do?method=getDetails')" style="font-family: verdana, arial, sans-serif;font-size: 11px;" >
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
				<option value="<%=optionValue%>"><%=optionDate%></option>
			<%}%>
			</html:select>			
			</td>
			<td> <input type="button"  value="New Entry" onclick="onEntry('taskAction1.do?method=entry')" name="newEntry" style="font-family: verdana, arial, sans-serif;font-size: 11px;" /></td>
			 </tr>
			</table>			 			
			</logic:present>		
			<center>
			<%
			String msg=(String)request.getAttribute("msg");
		    %> 
			<logic:notEmpty name="msg" scope="request">
			<div id="short-time-msg">  
                <%=msg%>
           <!--  <a href="#" onclick="hideAfterSomeTime(-1)">Hide</a>   -->
            <script>hideAfterSomeTime()</script>  
            </div>  
			</logic:notEmpty>
			</center>			
		<%
			TaskForm taskform = (TaskForm) request.getAttribute("taskList");
				int id = taskform.getId();
				String idValue = new Integer(id).toString();
		%>
		<center><table cellpadding="20" style="font-family: verdana, arial, sans-serif;font-size: 11px;"><tr><td><html:errors property="status"/></td>
		<td><div id="checkUser" align="center" style="margin-left: 160px;"></div></td>
			<td><div id="checkUser1" align="center"></div></td><td><div id="checkUser2" align="center"></div></td>
			<td><div id="checkUser3" align="center"></div></td></tr></table>
		</center>
<table  width="100%"style="font-family: verdana, arial, sans-serif;font-size: 11px;"><tr align="center" valign="middle"><td align="left"><b>Edit Time Entry</b></td></tr>
</table>
<br>
<br>
       <logic:empty name="displayTaskEntry" scope="request">
		<table  style="font-family: verdana, arial, sans-serif;font-size: 11px;" cellpadding="5px">
		<TR><TD><input type="hidden" name="userIDValue" id="userIDValue" value="<%=taskform.getId() %>"> Category:
		                  <html:select styleId="disable" value="<%=taskform.getStatus() %>"  property="status" 
		                      onchange="changetextbox();"  title="This is type of task you are doing" style="font-family: verdana, arial, sans-serif;font-size: 11px;width: 147px;" >
			                <option value="<%=taskform.getStatus() %>"><%=taskform.getStatus() %></option>
							<option value="Code Review">Code Review</option>
							<option value="Comp off">Comp off</option>
							<option value="Development">Development</option>
							<option value="Down Time">Down Time</option>
							<option value="Half Day">Half Day</option>
							<option value="Leave">Leave</option>
							<option value="Maintenance/Release support">Maintenance/Release support</option>
							<option value="Meeting">Meeting</option>
							<option value="Pre-Project Training">Pre-Project Training</option>								
							<option value="Project Management">Project Management</option>																				
							<option value="Public holiday">Public holiday</option>
							<option value="Task unassigned/Idle">Task unassigned/Idle</option>	
							<option value="Team Activity">Team Activity</option>
							<option value="Testing">Testing</option>														
							<option value="Training">Training</option>													
							<option value="Travel">Travel</option>
						</html:select>&nbsp;&nbsp;&nbsp;   </TD>						
		<%
		if(taskform.getStatus().equalsIgnoreCase("leave") || taskform.getStatus().equalsIgnoreCase("Public holiday") ||
				taskform.getStatus().equalsIgnoreCase("Half Day") || taskform.getStatus().equalsIgnoreCase("Comp off") ||
				taskform.getStatus().equalsIgnoreCase("Travel") || taskform.getStatus().equalsIgnoreCase("Down Time") )
		{
		%>				
<TD >Backlog ID:<html:text disabled="true" size="6" property="backlog_id" value="<%=taskform.getBacklog_id() %>"  title=" This entry is for theBacklogId of task that you working." styleId="disableBacklog" style="font-family: verdana, arial, sans-serif;font-size: 11px;" ></html:text></TD>						
<TD>Task ID:<html:text disabled="true" size="6" property="task_id" value="<%=taskform.getTask_id() %>" title=" This entry is for the TaskID of task that you working."  styleId="disableTask" style="font-family: verdana, arial, sans-serif;font-size: 11px;"></html:text></TD>
		<%}
		else
		{
		%>							
<TD>Backlog ID:<html:text  size="6" property="backlog_id" value="<%=taskform.getBacklog_id() %>"  title=" This entry is for theBacklogId of task that you working." styleId="disableBacklog" style="font-family: verdana, arial, sans-serif;font-size: 11px;" ></html:text></TD>						
<TD>Task ID:<html:text  size="6" property="task_id" value="<%=taskform.getTask_id() %>" title=" This entry is for the TaskID of task that you working."  styleId="disableTask" style="font-family: verdana, arial, sans-serif;font-size: 11px;"></html:text></TD>
		<%} 		
		String timeFor = taskform.getTime();
		if(timeFor.length()>7)
		{
			timeFor=timeFor.substring(0, timeFor.lastIndexOf(':'));
		}		
		%>
		<TD>Date:<html:text  size="9" property="task_date" value="<%=taskform.getTask_date() %>"  title="The date of task" readonly="true" styleId="popupDatepicker" style="font-family: verdana, arial, sans-serif;font-size: 11px;" ></html:text></TD>
<TD>Time in hrs:<html:text  size="6"  property="time" value="<%=timeFor%>" styleId="time" title="The duration of task" style="font-family: verdana, arial, sans-serif;font-size: 11px;"></html:text></TD></TR>
<tr><td></td><td></td><td></td><td></td><td align="right" style="padding-top: 0px;margin-top: 0px;color: #808080;">(eg:HH:MM,HH.MM)</td></tr>
	</table>
<table class="hovertable1">
<tr><td></td>
<td>

 <%if(taskform.getWork_status()==null || taskform.getWork_status() == ""){ %>
   <input type="radio" name="work_status" value="office" checked> Office
   <input type="radio" name="work_status" value="home">Home

<%} else if(taskform.getWork_status().equalsIgnoreCase("office")){ %>
  <input type="radio" name="work_status" value="office" checked> Office
  <input type="radio" name="work_status" value="home">Home
  <%}else if(taskform.getWork_status().equalsIgnoreCase("home")){ %>
   <input type="radio" name="work_status" value="office"> Office
   <input type="radio" name="work_status" value="home" checked>Home
  <%} %>
</td></tr>
<TR><TD >Description:</td><td>
<html:textarea rows="5" cols="80" property="task_description" title="A description for this timesheet entry." value="<%=taskform.getTask_description() %>" style="font-family: verdana, arial, sans-serif;font-size: 11px;width: 675px;"/>
</TD></TR>
</table>
<br>
<table align="center">
<tr>
<td><input type="button" value="Save" onclick="onEdit('taskAction.do?method=saveEdit')" id="mybutton" height="15px" style="font-family: verdana, arial, sans-serif;font-size: 11px;"/></td>
<td><input type="button" value="Clear" onclick="return clearForm();" height="15px" style="font-family: verdana, arial, sans-serif;font-size: 11px;"/></td>
<td><input type="button" value="Back" onClick="location.href='taskAction1.do?method=onClickTask'" height="15px"  style="font-family: verdana, arial, sans-serif;font-size: 11px;"/></td>
</tr>
</table>
</logic:empty>
<br>
<br>
<logic:notEmpty name="tList" scope="request">
			<%
				if (request.getAttribute("tList") != null) {
			%>
			<div
				style="width: 780px; height: 400px; overflow: scroll; overflow: auto;">
				<!-- <div style="width: 1250px; height: 400px; overflow: scroll;"> -->
				<table class="hovertable" border="10" width="760px">
					<tr>
						<th><h4>Date</h4></th>
						<th><h4>Category</h4></th>
						<th style="width:5pc"><h4>BackLog ID</h4></th>
						<th style="width:0pc"><h4>Task ID</h4></th>
						<th style="width:13pc"><h4>Description</h4></th>
						<th><h4>Efforts From</h4></th>
						<th style="width:7pc"><h4>Time In hrs</h4></th>
						<th><h4>Edit</h4></th>
						<th><h4>Delete</h4></th>
					</tr>
					<logic:iterate id="tList" name="tList" scope="request"
						type="com.calsoft.task.form.TaskForm">
						<tr onmouseover="this.style.backgroundColor='#ffff66';"
							onmouseout="this.style.backgroundColor='#FFFFFF'">
							<%-- <bean:define id="recordId">
							<bean:write name="tList" property="id" />
						</bean:define>
						<td><html:radio property="id" value="<%=recordId%>" /></td> --%>
						<logic:equal name="tList" property="status" value="Comp off">
						   <td><font color="#008000"><bean:write name="tList" property="task_date" /></font></td>
						   <td><font color="#008000"><bean:write name="tList" property="status" /></font></td>
						   <td><font color="#008000"><bean:write name="tList" property="backlog_id" /></font></td>
						   <td><font color="#008000"><bean:write name="tList" property="task_id" /></font></td>
						   <td><font color="#008000"><bean:write name="tList" property="task_description" /></font></td>
						   <td>
						     <logic:equal name="tList"  property="work_status" value="home"><font color="#008000">Home</font></logic:equal>
						      <logic:notEqual name="tList"  property="work_status" value="home">
						        <logic:equal name="tList"  property="work_status" value="office"><font color="#008000">Office</font></logic:equal>
						        <logic:notEqual name="tList"  property="work_status" value="office"></logic:notEqual>
						      </logic:notEqual>
						   </td>
						   <td><font color="#008000"><bean:write name="tList" property="time" /></font></td>
						</logic:equal>
						<logic:notEqual name="tList" property="status" value="Comp off">												
							<td><bean:write name="tList" property="task_date" /></td>
							<td><bean:write name="tList" property="status" /></td>
							<td><bean:write name="tList" property="backlog_id" /></td>
							<td style="width:3pc"><bean:write name="tList" property="task_id" /></td>
							<td style="width:13pc"><bean:write name="tList" property="task_description" /></td>
							<td>
						        <logic:equal name="tList"  property="work_status" value="home">Home</logic:equal>
						        <logic:notEqual name="tList"  property="work_status" value="home">
						          <logic:equal name="tList"  property="work_status" value="office">Office</logic:equal>
						          <logic:notEqual name="tList"  property="work_status" value="office"></logic:notEqual>
						        </logic:notEqual>
						    </td>																					
						<td style="width:7pc">							
							<logic:equal name="tList"  property="status" value="Half Day" >
							   <font color="#FF0000"><bean:write name="tList" property="time" /></font>
							</logic:equal>							
							<logic:notEqual name="tList"  property="status" value="Half Day">								
							        <logic:notEqual name="tList"  property="status" value="Leave" >
							  			<%--  <bean:write name="tList"  property="time" /> --%>
							  			<logic:equal name="tList"  property="status" value="Public holiday">
							  			    <font color="#0000CD"><bean:write name="tList"  property="time" /></font>
							  			</logic:equal>
							  			<logic:notEqual name="tList" property="status"
												value="Public holiday">												
												 <bean:write name="tList" property="time" />																								
										</logic:notEqual>							  										  			
									</logic:notEqual>
							   		<logic:equal name="tList"  property="status" value="Leave" >
							      			<font color="#FF0000"><bean:write name="tList"  property="time"  /></font>
							        </logic:equal>																																							
						    </logic:notEqual>														
							</td>
						</logic:notEqual>
							<td><a
								onclick="onEdit('taskAction1.do?method=edit&id=<bean:write name="tList" property="id" />')"><img
									src="edit.png" style="cursor: pointer;"/></a>
							<td><a
								onclick="onDelete('taskAction1.do?method=delete1&id=<bean:write name="tList" property="id" />')"><img
									src="delete1.png" style="cursor: pointer;"/></a></td>
						</tr>
					</logic:iterate>
				</table>
			</div>
			<%
				}
			%>
		</logic:notEmpty>
		<!-- </div> -->
	</html:form>
</div>
</body>
</html>