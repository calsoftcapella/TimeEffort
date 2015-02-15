<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    	               "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://displaytag.sf.net" prefix="display" %>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 <link href="css/style1.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" media="all" href="jsDatePick_ltr.min.css" />
<script type="text/javascript" src="jquery.1.4.2.js"></script>
<script type="text/javascript" src="jsDatePick.jquery.min.1.3.js"></script>


<script type="text/javascript">
	window.onload = function(){
		new JsDatePick({
			useMode:2,
			target:"inputField",
			dateFormat:"%d-%M-%Y"
		
		});
	};
</script>
<script type="text/javascript">
function show_prompt() {
	  var name = prompt("Enter Name", "");
	  
	  if (name != null && name != "") 
	  {
	     
	   location.href = "addUser.jsp?param=" + name + "";
	  }

	  else {
	   alert("Name is Not a valid One");
	   show_prompt();
	  }
	 }
</script>


</head>
<body bgcolor="wheat">
	<center>
		<h1>Welcome To Calsoft TimeSheet</h1>
	</center>
	<html:form action="taskAction" method="POST">
		<table border="0" align="center" id="dataTable">
			<tr>
				<td></td>
				<td></td>
				<td>Name: <html:select property="userId" styleId="userId">
						
						<logic:iterate name="list" id="list" scope="request">
							<option value="<bean:write name="list" property="userId" />">
								<bean:write name="list" property="userName" />
							</option>
						</logic:iterate>
					</html:select>
				<td><html:submit value="GetDetails" property="method"></html:submit>
				</td>
				<td><input type="button" value="Add User"
					 onclick="show_prompt()" />
				</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td><a href="report.jsp">View Report</a>
				</td>
			</tr>
			<tr></tr>
			<tr></tr>
			<tr></tr>
			<tr></tr>
			<tr></tr>
			<tr></tr>
			<tr></tr>
			<tr></tr>
			<tr></tr>
			<tr></tr>
			<tr></tr>
			<tr></tr>
			<!-- </table> -->
			<!-- <table> -->
			<tr>
				<td></td>
				<td>DayType</td>
				<td>BackLogId</td>
				<td>TaskId</td>
				<td>Description</td>
				<td>Time</td>
				<td>Date</td>
			</tr>
			<tr></tr>
			<tr></tr>
			<tr></tr>
			<tr></tr>
			<tr></tr>
			<tr></tr>
			<tr>
				<td></td>
				<td><html:select property="status">

						<html:option value="Select">-Select-</html:option>
						<html:option value="Workday">Workday</html:option>
						<html:option value="Halfday">Halfday</html:option>
						<html:option value="Holiday">Holiday</html:option>
						<html:option value="Leave">Leave</html:option>

					</html:select>
				</td>
				<td><html:text property="backlog_id" />
				</td>
				<td><html:text property="task_id" />
				</td>
				<td><html:textarea property="task_description"></html:textarea>
				</td>
				<td><html:text property="time" />
				</td>
				<td><html:text property="task_date" styleId="inputField" />
				</td>
			</tr>
			<tr></tr>
			<tr></tr>
			<tr></tr>
			<tr></tr>
			<tr></tr>
			<tr></tr>
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td><html:submit property="method" value="save"></html:submit>
				</td>
			</tr>

		</table>

		<%-- <table border="1" WIDTH="100%">

			<%
				if (request.getAttribute("tList") != null) {
			%>
			<tr>
				<td></td>
				<td><h4>Day Type</h4>
				</td>
				<td><h4>BackLogId</h4>
				</td>
				<td><h4>TaskId</h4>
				</td>
				<td><h4>Description</h4>
				</td>
				<td><h4>Time</h4>
				</td>
				<td><h4>Date</h4>
				</td>
			</tr>
			<logic:iterate id="TaskForm" name="tList" scope="request"
				type="com.calsoft.task.form.TaskForm">
				<tr>
					<bean:define id="recordId">
						<bean:write name="TaskForm" property="id" />
					</bean:define>
					<td><html:radio property="id" value="<%=recordId%>" />
					</td>
					<td><bean:write name="TaskForm" property="status" />
					</td>
					<td><bean:write name="TaskForm" property="backlog_id" />
					</td>
					<td><bean:write name="TaskForm" property="task_id" />
					</td>
					<td><bean:write name="TaskForm" property="task_description" />
					</td>
					<td><bean:write name="TaskForm" property="time" />
					</td>
					<td><bean:write name="TaskForm" property="task_date" />
					</td>
				</tr>
			</logic:iterate>
			<html:submit value="Edit" property="method"></html:submit>
			<html:submit value="Delete" property="method"></html:submit>

			<%
				}
			%>
		</table> --%>
		
		
		
	
<logic:notEmpty name="tList" scope="request" >

	<display:table id="data" name="requestScope.tList" requestURI="taskAction.do?method=GetDetails" pagesize="5"  decorator="com.calsoft.report.decorator.DisplayDecorator">

            <display:column property="id"  sortable="true" />
            <display:column property="status"  sortable="true" />
            <display:column property="backlog_id"  sortable="true" />
            <display:column property="task_id"  sortable="true" />
            <display:column property="task_description" sortable="true" />
            <display:column property="time" sortable="true" />
            <display:column property="task_date"  sortable="true"/>
            
           <html:submit value="Edit" property="method"></html:submit>
		   <html:submit value="Delete" property="method"></html:submit>
			
    </display:table>
</logic:notEmpty>
	
		
	</html:form>
</body>
</html>
