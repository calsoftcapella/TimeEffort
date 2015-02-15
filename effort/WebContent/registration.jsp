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
table.hovertable {
	font-family: verdana, arial, sans-serif;
	font-size: 11px;
	color: #333333;
	border-width: 1px;
	border-color: #999999;
	border-collapse: collapse;
	width: 680px;
}

table.hovertable th {
	background-color: grey;
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #a9c6c9;
}

table.hovertable tr {
	background-color: white;
}

table.hovertable td {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #a9c6c9;
}
</style>
<script type="text/javascript">
	function noBack() {
		window.history.forward(1);
	}
	function popup(url) {
		var agree = confirm("Are You Sure Want To Delete");
		if (agree) {
			var formSubmit = document.forms[0];
			formSubmit.action = url;
			formSubmit.submit();
		}
	}
	function clearForm() {
		document.forms[0].userName.value = '';
		document.forms[0].mail.value = '';
		document.forms[0].userId.value = 0;
		document.forms[0].user_role.value = '1002'; // Setting user as default option while clearing fields
		document.getElementById("mybutton").value = "Add";
	}
	function submitReport(url) {
		var formSubmit = document.forms[0];
		formSubmit.action = url;
		formSubmit.submit();
	}
	$(document).ready(function(){
		if(document.forms[0].userId.value == '0'){
			document.getElementById("mybutton").value = "Add";
		}
		if(typeof $("#hidden_role_Id").val() != 'undefined'){
			var role_id = $("#hidden_role_Id").val();
		    var selectOption = document.getElementById("role_select");
		    for (var i = 0; i < selectOption.length; i++){
		      if(selectOption.options[i].value == role_id){
		 	    selectOption.options[i].selected = "true";
		      }
		    }
		}
		else{
			var select_option = document.getElementById("role_select");
		    for (var i=0; i<select_option.length; i++){
		      if(select_option.options[i].value == '1002'){
		    	  select_option.options[i].selected="true";
		      }
		    }
		}
		if(typeof $("#hidden_project_id").val() != 'undefined'){
			var project_id = $("#hidden_project_id").val();
		    var selectOption = document.getElementById("project_id_for_select");
		    for (var j = 0; j<selectOption.length; j++){
		      if(selectOption.options[j].value == project_id){
		    	  selectOption.options[j].selected = "true";
		      }
		    }
		}
	});
</script>
</head>

<body style="margin: 17%" onload="noBack();">
	<div style="margin-left: 40px; font-family: verdana, arial, sans-serif; font-size: 11px;">
	<logic:present name="hidden_role_id" scope="request">
		<input type="hidden" name="hiddenRole" value='<bean:write name="hidden_role_id"/>' id="hidden_role_Id"/>
	</logic:present>
	<logic:present name="hidden_project_id" scope="request">
		<input type="hidden"  value='<bean:write name="hidden_project_id"/>' id="hidden_project_id" name="hidden_project_name_id"/>
	</logic:present>	
		<br>
		<html:form action="AddUserAction100" method="post">

			<%-- <html:form action="/addUser1" method="post"> --%>
			<b>Add/Edit User</b> :&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font
				color="blue"><logic:notEmpty name="passwordMessage"
					scope="request">
					<b><bean:write name="passwordMessage" scope="request" /></b>
				</logic:notEmpty></font>
			<br>
			<html:errors />
			<br>
			<font color="red"><logic:notEmpty name="errorMessage"
					scope="request">
					<bean:write name="errorMessage" scope="request" />
				</logic:notEmpty></font>
			<html:hidden property="userId" />
			<html:hidden property="password" value="null" />
			<table>
				<tr height="30px;">
					<td>Username:</td>
					<td><html:text property="userName" size="35"
							style="font-family: verdana,arial,sans-serif;font-size:11px;width: 240px;"></html:text></td>
				</tr>
				<tr height="30px;">
					<td>E-mail ID:</td>
					<td><html:text property="mail" size="35"
							style="font-family: verdana,arial,sans-serif;font-size:11px;width: 240px;"></html:text></td>
				</tr>
				<tr height="30px;">
					<td>Status:</td>
					<td><html:select property="status"
							style="font-family: verdana,arial,sans-serif;font-size:11px;width: 240px;">
							<html:option value="Active"
								style="font-family: verdana,arial,sans-serif;font-size:11px;">Active</html:option>
							<html:option value="InActive"
								style="font-family: verdana,arial,sans-serif;font-size:11px;">InActive</html:option>
						</html:select></td>
				</tr>
				<tr height="30px;">
					<td>Role:</td>
					<td><logic:notEmpty name="allRoleName" scope="session">
							<select name="user_role"
								style="font-family: verdana, arial, sans-serif; font-size: 11px;width: 240px;"
								id="role_select">
								<logic:iterate name="allRoleName" id="role_name" scope="session"
									type="com.calsoft.user.form.UserRoleForm">
										<option
											value="<bean:write name="role_name" property="roleId"/>"
											style="font-family: verdana, arial, sans-serif; font-size: 11px;">
											<bean:write name="role_name" property="roleName" />
										</option>
								</logic:iterate>
							</select>
						</logic:notEmpty></td>
				</tr>
				<tr height="30px;">
					<td>Project Information:</td>
					<td><html:select property="project_id" styleId="project_id_for_select"
							style="font-family: verdana,arial,sans-serif;font-size:11px;width: 240px;">
							<logic:present name="project_detail_info" scope="session">
							  <logic:notEmpty name="project_detail_info" scope="session">
							     <logic:iterate id="projectInfo" name="project_detail_info" scope="session" type="com.calsoft.user.model.ProjectDetailModel">
							     		<option value="<bean:write name="projectInfo" property="project_id"/>" style="font-family: verdana,arial,sans-serif;font-size:11px;">
							     			<bean:write name="projectInfo" property="project_name"/>
							     		</option>
							     </logic:iterate>
							  </logic:notEmpty>
							</logic:present>
						</html:select></td>
				</tr>
			</table>
			<table cellspacing="20">
				<tr>
					<td><input type="button" value="Save"
						onclick="submitReport('AddUserAction.do?method=addUser');"
						id="mybutton"
						style="font-family: verdana, arial, sans-serif; font-size: 11px;" />
					</td>
					<td><input type="button" value="Clear"
						onclick="return clearForm();"
						style="font-family: verdana, arial, sans-serif; font-size: 11px;">
					</td>
				</tr>
			</table>
			<hr color="#a9c6c9" align="center" style="margin-right: 35px;" />
			<br>
			<logic:notEmpty name="userlist" scope="request">
				<u><b>User Information</b></u>
				<br>
				<%-- </html:form> --%>
				<div
					style="width: 700px; height: 500px; overflow: scroll; overflow: auto">
					<table class="hovertable">
						<thead>
							<tr>
								<th><b>Username</b></th>
								<th><b>E-mail ID</b></th>
								<th><b>Status</b></th>
								<th><b>Edit</b></th>
								<th><b>Delete</b></th>
								<th><b>Global Access</b></th>
								<!-- <th><b>Role</b></th> -->
							</tr>
						</thead>
						<tbody>
							<logic:iterate id="userdetail" name="userlist" scope="request"
								type="com.calsoft.user.form.UserForm">
								<!-- <tr onmouseover="this.style.backgroundColor='#ffff66';" onmouseout="this.style.backgroundColor='#ffffff';"> -->
								<tr>
									<td><bean:write name="userdetail" property="userName" /></td>
									<td><bean:write name="userdetail" property="mail" /></td>
									<td><bean:write name="userdetail" property="status" /></td>
									<td><a
										href="AddUserAction2.do?method=edit&id=<bean:write name="userdetail" property="userId"/>"><img
											src="./img/edit.png" style="border-color: white;" /></a></td>
									<td align="center"><img src="./img/delete1.png"
										onclick=" popup('AddUserAction2.do?method=delete&id=<bean:write name="userdetail" property="userId"/>');">
									</td>
									<td align="center"><logic:equal name="userdetail"
											property="defpass" value="timesheet">
											<a
												href="AddUserAction2.do?method=editGlobal&id=<bean:write name="userdetail" property="userId"/>">
												<img src="img/correct.jpg" height="25px"
												style="border-color: white;" />
											</a>
										</logic:equal> <logic:notEqual name="userdetail" property="defpass"
											value="timesheet">
											<a
												href="AddUserAction2.do?method=editGlobal&id=<bean:write name="userdetail" property="userId"/>">
												<img src="img/disabled.jpg" height="25px"
												style="border-color: white;" />
											</a>
										</logic:notEqual></td>
									<%--  <td><logic:equal name="userdetail" property="role" value="user">User</logic:equal>
                      <logic:equal name="userdetail" property="role" value="client">Client</logic:equal>
                      <logic:equal name="userdetail" property="role" value="admin">Admin</logic:equal>
                   </td> --%>

								</tr>
							</logic:iterate>
						</tbody>
					</table>
				</div>
			</logic:notEmpty>
		</html:form>
	</div>
</body>
</html>
