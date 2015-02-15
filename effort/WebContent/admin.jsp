<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<html>
<head>
<link href="css/page.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="css/jquery-ui.css" />
<style type="text/css">
@import "flora.datepick.css";
body {
	font: normal 14px Calibri, Calibri, Calibri;
}
table.userInfoTableId {
	font-family: verdana, arial, sans-serif;
	font-size: 11px;
	width: 850px;
	background-color: gray;
}
table.userInfoTableId tr {
	
}
table.userInfoTableId td {
	font-family: verdana, arial, sans-serif;
	font-size: 11px;
}
input {
	font-family: verdana, arial, sans-serif;
	font-size: 11px;
	width: 350px;
	border-color: #DCDCFF;
	border-style: solid;
}
select {
	font-family: verdana, arial, sans-serif;
	font-size: 11px;
	border-color: #DCDCFF;
	border-style: solid;
}
:focus {
	border-color: #FFD000
}
table.hovertable {
	font-family: verdana, arial, sans-serif;
	font-size: 11px;
	color: #333333;
	border-width: 1px;
	border-color: #DCDCFF;
	border-collapse: collapse;
	width: 750px;
}
table.hovertable th {	
}
table.hovertable tr {
	background-color: #ffffff;
}
table.hovertable td {
	border-width: 1px;
	padding: 5px;
	border-style: solid;
	border-color: #DCDCFF;
	height: 10mm;
	width: 12mm;
}
.ui-autocomplete {
	max-height: 200px;
	overflow-y: auto;
	overflow-x: hidden;
	padding-right: 10px;
	font-family: verdana, arial, sans-serif;
	font-size: 11px;
}
.ui-tooltip {
	font-family: verdana, arial, sans-serif;
	font-size: 11px;
	padding: 5px;
	position: absolute;
	z-index: 9999;
	max-width: 300px;
	-webkit-box-shadow: 0 0 5px #aaa;
	box-shadow: 0 0 5px #aaa;
}
.changeFont{
 	color: red;
 	font-style: italic;
}
</style>
<script src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="js/jquery.datepick.js"></script>
<script type="text/javascript" src="js/admin_script.js"></script>
<script type="text/javascript" src="js/jquery-ui.js"></script>
<script type="text/javascript">
	$(function() {
		$('#popupDatepicker1').datepick();
		$('#popupDatepicker2').datepick();
		$('#popupDatepicker3').datepick();
		var location_list = [ "Bangalore", "Chennai", "Mysore", "Onsite" ];
		var team_details = [ 'ALE-QA', 'BE-Curriculum', 'BE-Gradebook',
				'Classroom', 'Curriculum', 'ALE-QA', 'Classroom-BE',
				'Classroom-Buffer', 'Classroom-FE', 'Classroom-FE/QA',
				'Classroom-QA', 'Classroom-QA (FE Automation)',
				'Classroom-QA FE/BE', 'Classroom-QA(FE /BE)',
				'Classroom-QA/FE', 'Curriculium-FE', 'Curriculum Builder-QA',
				'Curriculum-BE', 'Curriculum-BE & FE', 'Curriculum-FE',
				'Curriculum-QA', 'ecampus-.NET', 'Gradebook -BE',
				'Gradebook -FE', 'Gradebook-BE', 'Gradebook-FE',
				'Gradebook-QA', 'PETs Calendar-QA', 'PETS-BE', 'PETs-BE/FE',
				'PETs-QA', 'Phoenix Connect Application-BE', 'PM',
				'Project Management', 'QA', 'QA-Syllabus', 'Tutor services -FE' ];
		$("#location_id").autocomplete({
			source : location_list
		});
		$("#team_id").autocomplete({
			source : team_details
		});
		$("input[type=button]").button().click(function(event) {
			event.preventDefault();
		});
		$(document).tooltip();
	});
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body text="#2B1B17"
	style="margin: 17%; margin-top: 0%; outline-color: blue;">
	<div style="padding-left: 40px;">
		<html:form action="adminHomeAction" method="post">
			<logic:present name="UpdateStatus" scope="request">
				<div id="updateMessageId"
					style="color: blue; text-align: center; font-family: verdana, arial, sans-serif; font-size: 11px; padding-bottom: 30px; font-weight: bolder;">
					<bean:write name="UpdateStatus" />
				</div>
			</logic:present>
			<div style="padding-left: 10px; padding-bottom: 35px;">
				<span
					style="font-family: verdana, arial, sans-serif; font-size: 11px; padding-right: 35px; font-weight: bolder;">Select
					User:</span>
				<html:select property="userId" style="width: 250px;"
					styleId="user_id"
					onchange="submitForm('adminHomeAction.do?method=getSelectedResourceDetailForUpdate');">
					<html:option value="">Select</html:option>
					<logic:present name="timesheetUserList" scope="request">
						<logic:notEmpty name="timesheetUserList" scope="request">
							<logic:iterate id="us_id" name="timesheetUserList"
								type="com.calsoft.user.form.UserForm">
								<option value="<bean:write name="us_id" property="userId"/>">
									<bean:write name="us_id" property="userName" />
								</option>
							</logic:iterate>
						</logic:notEmpty>
					</logic:present>
				</html:select>
			</div>
			<logic:present name="updateUserDetails" scope="request">
				<html:hidden name="updateUserDetails" property="userId"
					styleId="hidden_userId" />
				<html:hidden name="updateUserDetails" property="user_role"
					styleId="hidden_role_id" />	
				<%-- <html:hidden name="updateUserDetails" property="project_id"
					styleId="hidden_project_id" /> --%>
				<input type="hidden" name="hidden_pro_text" id="hidden_project_id" value='<bean:write name="updateUserDetails" property="project_id"/>'/>	
				<table id="userInfoTableId" cellpadding="5px" class="hovertable">
					<thead>
						<tr>
							<th colspan="2" align="left"><ins>Update User
									Information:-</ins></th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>Username:</td>
							<td><html:text name="updateUserDetails" property="userName"
									styleId="userNameId" title="Edit Username" maxlength="40" /> <span
								id="username_error"
								style="font-family: verdana, arial, sans-serif; font-size: 11px; color: red;"></span>
							</td>
						</tr>
						<tr>
							<td>Email:</td>
							<td><html:text name="updateUserDetails" property="mail"
									styleId="emailId" readonly="true" title="Email" /></td>
						</tr>
						<tr>
							<td>Apollo Id:</td>
							<td><html:text name="updateUserDetails" property="apollo_id"
									styleId="apolloId" title="Apollo EmailId" />
								<span id="apolloid_error" style="font-family: verdana, arial, sans-serif; font-size: 11px; color: red;"></span>	
									</td>
						</tr>
						<tr>
							<td>Password:</td>
							<td><html:password name="updateUserDetails"
									property="passWord" styleId="passWordId" title="Edit Password"
									maxlength="20" /> <span id="password_error"
								style="font-family: verdana, arial, sans-serif; font-size: 11px; color: red;"></span>
							</td>
						</tr>
						<tr>
							<td>Status:</td>
							<td><html:select name="updateUserDetails" property="status"
									styleId="status_id" title="Edit Status" style="width: 250px;">
									<html:option value="">Select</html:option>
									<html:option value="Active">Active</html:option>
									<html:option value="InActive">InActive</html:option>
								</html:select> <span id="status_error"
								style="font-family: verdana, arial, sans-serif; font-size: 11px; color: red;"></span>

							</td>
						</tr>
						<tr>
							<td>Role In Timesheet:</td>
							<td><html:select name="updateUserDetails"
									property="user_role_id" styleId="role_id" title="Edit Role"
									style="width: 250px;">
									<html:option value="">Select</html:option>
									<logic:notEmpty property="all_roles" name="updateUserDetails">
										<logic:iterate id="all_roleIds" property="all_roles" name="updateUserDetails" type="com.calsoft.user.form.UserRoleForm">
											<option value="<bean:write name="all_roleIds" property="roleId"/>"><bean:write name="all_roleIds" property="roleName"/></option>
										</logic:iterate>
									</logic:notEmpty>
								</html:select> <span id="role_error"
								style="font-family: verdana, arial, sans-serif; font-size: 11px; color: red;"></span>
							</td>
						</tr>
						<tr>
							<td>Default Password:</td>
							<td><html:password name="updateUserDetails"
									property="defpass" styleId="defpassId"
									title="Edit Global Password" maxlength="20" /> <span
								id="defpass_error"
								style="font-family: verdana, arial, sans-serif; font-size: 11px; color: red;"></span>
							</td>
						</tr>
						<tr>
							<td>Start Date:</td>
							<td><html:text name="updateUserDetails" property="start_dateString"
									styleId="popupDatepicker3" readonly="true"
									title="Edit Start Date" maxlength="10" /></td>
						</tr>
						<tr>
							<td>Exit Date:</td>
							<td><html:text name="updateUserDetails" property="exit_date"
									styleId="popupDatepicker1" readonly="true"
									title="Edit Reliving Date" maxlength="10" /></td>
						</tr>
						<tr>
							<td>Project Detail:</td>
							<td>
								<html:select name="updateUserDetails"
									property="project_id" styleId="project_id_field" title="Edit Project Information"
									style="width: 250px;">
									<html:option value="0">Select</html:option>
									<logic:notEmpty property="project_id_list" name="updateUserDetails" scope="request">
										<logic:iterate id="project_ids_info" property="project_id_list" name="updateUserDetails" type="com.calsoft.user.model.ProjectDetailModel">
											<option value="<bean:write name="project_ids_info" property="project_id"/>"><bean:write name="project_ids_info" property="project_name"/></option>
										</logic:iterate>
									</logic:notEmpty>
								</html:select>
								<span id="project_error"
								style="font-family: verdana, arial, sans-serif; font-size: 11px; color: red;"></span>
							</td>
						</tr>						
						<tr>
							<td>Timesheet Freezing Date:</td>
							<td><html:text name="updateUserDetails"
									property="freeze_timesheet_entry" styleId="popupDatepicker2"
									readonly="true" maxlength="10"
									title="Edit freezing date for timesheet." /></td>
						</tr>
						<tr>
							<td>Contact:</td>
							<td><html:text name="updateUserDetails"
									property="contact_no1" styleId="contactId1"
									title="Edit Contact1" maxlength="14" /> <span
								style="font-size: 10px; color: green; font-weight: bolder;">Contact1</span><br>
								<span id="contact1_error"
								style="font-family: verdana, arial, sans-serif; font-size: 11px; color: red;"></span><br>
								<html:text name="updateUserDetails" property="contact_no2"
									styleId="contactId2" title="Edit Contact2" maxlength="14" /> <span
								style="font-size: 10px; color: green; font-weight: bolder;">Contact2</span><br>
								<span id="contact2_error"
								style="font-family: verdana, arial, sans-serif; font-size: 11px; color: red;"></span>
							</td>
						</tr>
						<tr>
							<td>Team:</td>
							<td><html:text name="updateUserDetails" property="team"
									styleId="team_id" title="Edit Team" maxlength="20" /></td>
						</tr>
						<tr>
							<td>Apollo Manager:</td>
							<td><html:text name="updateUserDetails"
									property="apollo_manager" styleId="apollo_manager_id"
									title="Edit Apollo Manager" maxlength="25" /></td>
						</tr>
						<tr>
							<td>Skype Id:</td>
							<td><html:text name="updateUserDetails" property="skypeId"
									styleId="skype_id" title="Edit Skpe Id" maxlength="25" /></td>
						</tr>
						<tr>
							<td>Location:</td>
							<td><html:text name="updateUserDetails" property="location"
									styleId="location_id" title="Edit Resource Location"
									maxlength="25" /></td>
						</tr>
						<tr>
							<td></td>
							<td><input type="button" value="Update"
								style="width: 150px; font: 13px; font-weight: bolder; cursor: pointer;"
								onclick="doFieldValidationAndSubmit('adminHomeAction.do?method=saveResourceInformationDetail');" /></td>
						</tr>
					</tbody>
				</table>
			</logic:present>
			<logic:notPresent name="updateUserDetails" scope="request">
				<logic:present name="userDetailsAfterSaving" scope="request">
					<html:hidden name="userDetailsAfterSaving" property="userId"
						styleId="hidden_userId" />
					<html:hidden name="userDetailsAfterSaving" property="user_role"
						styleId="hidden_role_id" />
					<html:hidden name="userDetailsAfterSaving" property="project_id"
						styleId="hidden_project_id" />		
					<table id="userInfoTableId" class="hovertable" cellpadding="5px">
						<thead>
							<tr>
								<th colspan="2" align="left"><ins>User Information:-</ins></th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>Username:</td>
								<td><bean:write name="userDetailsAfterSaving"
										property="userName" /></td>
							</tr>
							<tr>
								<td>Email:</td>
								<td><bean:write name="userDetailsAfterSaving"
										property="mail" /></td>
							</tr>
							<tr>
								<td>Apollo Id:</td>
								<td><logic:equal name="userDetailsAfterSaving"
										property="apollo_id" value="">
            							<span class="changeFont"><span class="changeFont">Not Added.</span></span>
         							</logic:equal> 
      
        							<logic:notEqual name="userDetailsAfterSaving"
										property="apollo_id" value="">
										<bean:write name="userDetailsAfterSaving" property="apollo_id" />
									</logic:notEqual></td>
							</tr>
							<tr>
								<td>Password:</td>
								<td><bean:write name="userDetailsAfterSaving"
										property="passWord" /></td>
							</tr>
							<tr>
								<td>Status:</td>
								<td><html:select name="userDetailsAfterSaving"
										property="status" styleId="status_id" title="Status"
										style="width: 250px;">
										<%-- <html:option value="">Select</html:option> --%>
										<html:option value="Active">Active</html:option>
										<html:option value="InActive">InActive</html:option>
									</html:select></td>
							</tr>
							<tr>
								<td>Role In Timesheet:</td>
								<td><html:select name="userDetailsAfterSaving"
										property="user_role" styleId="role_id" title="Role"
										style="width: 250px;">
										<html:option value="">Select</html:option>
											<logic:notEmpty property="all_roles" name="userDetailsAfterSaving">
												<logic:iterate id="all_roleIds" property="all_roles" name="userDetailsAfterSaving" type="com.calsoft.user.form.UserRoleForm">
													<option value="<bean:write name="all_roleIds" property="roleId"/>"><bean:write name="all_roleIds" property="roleName"/></option>
												</logic:iterate>
											</logic:notEmpty>									
									</html:select></td>
							</tr>
							<tr>
								<td>Default Password:</td>
								<td><bean:write name="userDetailsAfterSaving"
										property="defpass" /></td>
							</tr>
							<tr>
								<td>Start Date:</td>
								<td>
									<logic:equal name="userDetailsAfterSaving" property="start_dateString" value="">
            							<span class="changeFont">Not Added.</span>
         							</logic:equal> 
         							<logic:notEqual name="userDetailsAfterSaving" property="start_dateString" value="">
										<bean:write name="userDetailsAfterSaving" property="start_dateString" />
									</logic:notEqual>
								</td>
							</tr>
							<tr>
								<td>Exit Date:</td>
								<td>
									<logic:equal name="userDetailsAfterSaving" property="exit_date" value="">
            							<span class="changeFont">Not Added.</span>
         							</logic:equal> 
         							<logic:notEqual name="userDetailsAfterSaving" property="exit_date" value="">
										<bean:write name="userDetailsAfterSaving" property="exit_date" />
									</logic:notEqual>
								</td>
							</tr>
							
							<tr>
								<td>Project Detail:</td>
								<td>
									<html:select name="userDetailsAfterSaving"
										property="project_id" styleId="project_id_field" title="Project Information"
										style="width: 250px;">
										<html:option value="0">Select</html:option>
										<logic:notEmpty property="project_id_list" name="userDetailsAfterSaving" scope="request">
											<logic:iterate id="project_ids_info" property="project_id_list" name="userDetailsAfterSaving" type="com.calsoft.user.model.ProjectDetailModel">
												<option value="<bean:write name="project_ids_info" property="project_id"/>"><bean:write name="project_ids_info" property="project_name"/></option>
											</logic:iterate>
										</logic:notEmpty>
									</html:select>
								</td>
							</tr>					
							<tr>
								<td>Timesheet Freezing Date:</td>
								<td><logic:equal name="userDetailsAfterSaving"
										property="freeze_timesheet_entry" value="">
            <span class="changeFont">Not Added.</span>
         </logic:equal> <logic:notEqual name="userDetailsAfterSaving"
										property="freeze_timesheet_entry" value="">
										<bean:write name="userDetailsAfterSaving"
											property="freeze_timesheet_entry" />
									</logic:notEqual></td>
							</tr>
							<tr>
								<td>Contact:</td>
								<td><logic:equal name="userDetailsAfterSaving"
										property="contact_no1" value="">
            <span class="changeFont">Not Added.</span>
         </logic:equal> <logic:notEqual name="userDetailsAfterSaving"
										property="contact_no1" value="">
										<bean:write name="userDetailsAfterSaving"
											property="contact_no1" />
									</logic:notEqual> <br> <br> <logic:equal name="userDetailsAfterSaving"
										property="contact_no2" value="">
            <span class="changeFont">Not Added.</span>
         </logic:equal> <logic:notEqual name="userDetailsAfterSaving"
										property="contact_no2" value="">
										<bean:write name="userDetailsAfterSaving"
											property="contact_no2" />
									</logic:notEqual></td>
							</tr>
							<tr>
								<td>Team:</td>
								<td><logic:equal name="userDetailsAfterSaving"
										property="team" value="">
            <span class="changeFont">Not Added.</span>
         </logic:equal> <logic:notEqual name="userDetailsAfterSaving" property="team"
										value="">
										<bean:write name="userDetailsAfterSaving" property="team" />
									</logic:notEqual></td>
							</tr>
							<tr>
								<td>Apollo Manager:</td>
								<td><logic:equal name="userDetailsAfterSaving"
										property="apollo_manager" value="">
            <span class="changeFont">Not Added.</span>
         </logic:equal> <logic:notEqual name="userDetailsAfterSaving"
										property="apollo_manager" value="">
										<bean:write name="userDetailsAfterSaving"
											property="apollo_manager" />
									</logic:notEqual></td>
							</tr>
							<tr>
								<td>Skype Id:</td>
								<td><logic:equal name="userDetailsAfterSaving"
										property="skypeId" value="">
            <span class="changeFont">Not Added.</span>
         </logic:equal> <logic:notEqual name="userDetailsAfterSaving"
										property="skypeId" value="">
										<bean:write name="userDetailsAfterSaving" property="skypeId" />
									</logic:notEqual></td>
							</tr>
							<tr>
								<td>Location:</td>
								<td><logic:equal name="userDetailsAfterSaving"
										property="location" value="">
            <span class="changeFont">Not Added.</span>
         </logic:equal> <logic:notEqual name="userDetailsAfterSaving"
										property="location" value="">
										<bean:write name="userDetailsAfterSaving" property="location" />
									</logic:notEqual></td>
							</tr>
						</tbody>
					</table>
				</logic:present>
			</logic:notPresent>
		</html:form>
	</div>
</body>
</html>