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


 table {  table-layout: fixed; }
 table th, table td { overflow: hidden; }

</style>
<style type="text/css">
table.hovertable {
	font-family: verdana, arial, sans-serif;
	font-size: 11px;
	color: #333333;
	border-width: 1px;
	border-color: #999999;
	border-collapse: collapse;
	width: 1600px;
}

hovertable2 select {
	background: transparent;
	width: 268px;
	padding: 5px;
	font-size: 16px;
	border: 1px solid #ccc;
	height: 34px;
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
	width: 20px;
}

table.hovertable td {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #a9c6c9;
	
}
</style>
<style type="text/css">
body {
	font: normal 14px Calibri, Calibri, Calibri;
}
</style>

<script type="text/javascript">
	function submiturl(url) {
		var formSubmit = document.forms[0];
		formSubmit.action = url;
		formSubmit.submit();

	}
</script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Users Details page</title>
</head>

<body style="margin: 17%" onload="noBack();">
	<div
		style="margin-left: 40px; font-family: verdana, arial, sans-serif; font-size: 11px;">

		<br>
		<html:form action="fetchUserDetailAction" method="post">
			<logic:notEmpty name="userlist" scope="request">

				<br>
				<div
					style="width: 820px; height: 700px; overflow: scroll; overflow: auto">
					<table class="hovertable" >

						


						<thead>
							<tr>
								<th style="width: 12%;"><b>Username</b>
								</th>
								<th style="width: 21%;"><b>E-mail ID</b>
								</th>
								<th style="width: 10%;">Status <logic:notEmpty
										name="statusList" scope="request">
										<html:select property="status" styleId="status"
											onchange="submiturl('fetchUserDetailAction.do?method=getSelectedStatusUserList');">
											<html:option value="null">select status</html:option>
											<logic:iterate name="statusList" id="statusList"
												scope="request">
												<option
													value="<bean:write name='statusList' property='status' />"
													style="font-family: verdana, arial, sans-serif; font-size: 11px;">
													<bean:write name="statusList" property="status" />

												</option>

											</logic:iterate>
										</html:select>
									</logic:notEmpty></th>
								<th style="width: 8%;"><b>D.O.J in Calsoft (YYYY-MM-DD)</b>
								</th>
								<th style="width: 8%;"><b>D.O.J in Apollo (YYYY-MM-DD)</b>
								</th>
								<th style="width: 13%;">Team Manager <logic:notEmpty
										name="managerList" scope="request">
										<html:select property="teamManager" styleId="status"
											onchange="submiturl('fetchUserDetailAction.do?method=getSelectedManagerUserList');">
											<html:option value="null">select Manager</html:option>
											<logic:iterate name="managerList" id="managerList"
												scope="request">
												<option
													value="<bean:write name='managerList' property='teamManager' />"
													style="font-family: verdana, arial, sans-serif; font-size: 11px;">
													<bean:write name="managerList" property="teamManager" />
												</option>
											</logic:iterate>
										</html:select>
									</logic:notEmpty>
								</th>


								<th style="width: 13%;">Team Name <logic:notEmpty
										name="teamList" scope="request">
										<html:select property="teamName" styleId="status"
											onchange="submiturl('fetchUserDetailAction.do?method=getSelectedTeamUserList');">
											<html:option value="">select Team</html:option>
											<logic:iterate name="teamList" id="teamList" scope="request">
												<option
													value="<bean:write name='teamList' property='teamName' />"
													style="font-family: verdana, arial, sans-serif; font-size: 11px;">
													<bean:write name="teamList" property="teamName" />
												</option>
											</logic:iterate>
										</html:select>
									</logic:notEmpty>
								</th>
								<th style="width: 8%;"><b>Billing Start Date &nbsp; &nbsp;&nbsp;&nbsp;(YYYY-MM-DD)</b>
								</th>
								<th style="width: 8%;"><b>Billing End Date (YYYY-MM-DD)</b>
								</th>
								<th style="width: 8%;"><b>Date of Reliving from Apollo (YYYY-MM-DD)</b>
								</th>
							</tr>
						</thead>
						<tbody>
							<logic:iterate id="userdetail" name="userlist" scope="request"
								type="com.calsoft.user.form.UserForm">
								<!-- <tr onmouseover="this.style.backgroundColor='#ffff66';" onmouseout="this.style.backgroundColor='#ffffff';"> -->
								<tr>
									<td style="width: 50px;"><bean:write name="userdetail"
											property="userName" />
									</td>
									<td style="width: 155px;"><bean:write name="userdetail"
											property="mail" />
									</td>
									<td style="width: 50px;"><bean:write name="userdetail"
											property="status" />
									</td>
									<td style="width: 35px;"><bean:write name="userdetail"
											property="startDate" />
									</td>
									<td style="width: 35px;"><bean:write name="userdetail"
											property="endDate" />
									</td>
									<td style="width: 50px;"><bean:write name="userdetail"
											property="teamManager" />
									</td>
									<td style="width: 50px;"><bean:write name="userdetail"
											property="teamName" />
									</td>
									<td style="width: 35px;"><bean:write name="userdetail"
											property="billingStartDate" /></td>
									<td style="width: 35px;"><bean:write name="userdetail"
											property="billingEndDate" /></td>
									<td style="width: 35px;"><bean:write name="userdetail"
											property="dateOfReliving" /></td>
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
