<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>

<html>
<head>
<!-- <link href="css/page.css" rel="stylesheet" type="text/css" /> -->
<style type="text/css">
body {
	font: normal 14px Calibri, Calibri, Calibri;
}
</style>
<style type="text/css">
table.hovertable {
	font-family: verdana, arial, sans-serif;
	font-size: 11px;
	color: #333333;
	border-width: 1px;
	border-color: #999999;
	border-collapse: collapse;
}

table.hovertable th {
	background-color: #A5A1A0;
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #a9c6c9;
}

table.hovertable tr {
	background-color: #ffffff;
}

table.hovertable td {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #a9c6c9;
}

input[type=button]:hover {
	font-family: Courier New;
	color: #ffffff;
	font-size: 12px;
	text-decoration: none;
	-webkit-border-radius: 4px;
	-moz-border-radius: 4px;
	border-radius: 4px;
	-webkit-box-shadow: 0px 2px 9px #666666;
	-moz-box-shadow: 0px 2px 9px #666666;
	box-shadow: 0px 2px 9px #666666;
	text-shadow: 0px 0px 5px #666666;
	border: solid #121011 1px;
	background: -webkit-gradient(linear, left top, left bottom, color-stop(0.05, #3d94f6
		), color-stop(1, #1e62d0) );
	background: -moz-linear-gradient(center top, #3d94f6 5%, #1e62d0 100%);
	filter: progid: 
	DXImageTransform.Microsoft.gradient(  startColorstr=  '#3d94f6',
		endColorstr=  '#1e62d0' );
	height: 23px;
	width: 100%;
	background: -moz-linear-gradient(center top, #3d94f6 5%, #1e62d0 100%);
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#3d94f6',
		endColorstr='#1e62d0' );
}
</style>

<script type="text/javascript">
	function submitReport(url) {
		var formSubmit = document.forms[0];
		formSubmit.action = url;
		formSubmit.submit();
	}
	function submitReport1(url) {
		var formSubmit = document.forms[0];
		formSubmit.action = url;
		formSubmit.submit();
	}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Calsoft Labs Timesheet Admin Report Mgmt Page</title>
</head>

<body style="margin: 17%; margin-top: 0%;">
	<div style="margin-left: 40px">
		<div id="container">

			<html:form action="displayReport" method="post">
				<table align="left"
					style="font-family: verdana, arial, sans-serif; font-size: 11px;">
					<tr>
						<td>Select User: <logic:notEmpty name="list" scope="request">

								<html:select property="userId" styleId="userId"
									onchange="submitReport1('reportmanagement.do?method=getAllocationList');"
									style="width: 150px;font-family: verdana, arial, sans-serif;font-size: 11px;">

									<logic:iterate name="list" id="list" scope="request">
										<option value="<bean:write name="list" property="userId" />"
											style="font-family: verdana, arial, sans-serif; font-size: 11px;">
											<bean:write name="list" property="userName" />
										</option>
									</logic:iterate>
								</html:select>
							</logic:notEmpty>
						</td>
					</tr>
					<tr>
						<td>
							<table style="width: 220px;">
								<tr>
									<td><logic:notPresent name="displaySelectBox"
											scope="request">
											<select name="userIdValueAdd" multiple="multiple" size="10"
												style="width: 150px; font-family: verdana, arial, sans-serif; font-size: 11px;">
												<logic:notEmpty name="unallocatedList" scope="request">
													<logic:iterate name="unallocatedList" id="unallocatedList"
														scope="request">
														<option
															value="<bean:write name="unallocatedList" property="userId" />"
															style="font-family: verdana, arial, sans-serif; font-size: 11px;">
															<bean:write name="unallocatedList" property="userName" />
														</option>
													</logic:iterate>
												</logic:notEmpty>
											</select>
										</logic:notPresent></td>
									<td width="10%"><logic:notEmpty name="combinedList"
											scope="request">
											<input type="button" value=">>"
												onclick="submitReport1('reportmanagement.do?method=addResources');">
										</logic:notEmpty> <logic:notEmpty name="combinedList" scope="request">
											<input type="button"
												onclick="submitReport1('reportmanagement.do?method=deleteResources')"
												value="<<">
										</logic:notEmpty></td>
								</tr>
							</table>
						</td>
						<td>
							<table>
								<tr>
									<td><logic:notPresent name="displaySelectBox"
											scope="request">
											<select name="userIdValueRemove" multiple="multiple"
												size="10"
												style="width: 150px; font-family: verdana, arial, sans-serif; font-size: 11px;">
												<logic:notEmpty name="allocatedList" scope="request">
													<logic:iterate name="allocatedList" id="allocatedList"
														scope="request">
														<option
															value="<bean:write name="allocatedList" property="userId" />">
															<bean:write name="allocatedList" property="userName" />
														</option>
													</logic:iterate>
												</logic:notEmpty>
											</select>
										</logic:notPresent></td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td></td>
					</tr>
					<tr>

						<logic:notEmpty name="combinedList" scope="request">
							<td><input type="button" value="Get User Access Mapping"
								onclick="submitReport1('reportmanagement.do?method=getUserAccessMapping');"
								style="width: 170px; font-family: verdana, arial, sans-serif; font-size: 11px;">
							</td>
							<td><input type="button" value="Get All User Access Mapping"
								onclick="submitReport1('reportmanagement.do?method=getAllUserAccessMapping');"
								style="width: 190px; font-family: verdana, arial, sans-serif; font-size: 11px;">
							</td>
							<td><input type="button"
								value="Export All User Mapping To Excel"
								onclick="submitReport1('reportmanagement.do?method=exportAllUserAccessMapping');"
								style="width: 220px; font-family: verdana, arial, sans-serif; font-size: 11px;">
							</td>
						</logic:notEmpty>


					</tr>
					<tr>
						<td></td>
					</tr>
					<tr>
						<td></td>
					</tr>
					<tr>
						<td></td>
					</tr>

					<tr>
						<td><logic:notEmpty name="updatedallocatedList"
								scope="request">
								<div style="width: 250px; height: 670px; overflow: auto;">
									<display:table id="data"
										name="requestScope.updatedallocatedList"
										requestURI="reportmanagement.do?method=getUserAccessMapping"
										export="true"
										decorator="com.calsoft.report.decorator.UserReportAccessMappingDecorator"
										class="hovertable">
										<display:column property="parentUserName" title="User" />
										<display:column property="userName" title="Access To" />
										<display:setProperty name="export.excel.filename"
											value="userAccessMapping.xlsx" />
										<display:setProperty name="export.pdf.filename"
											value="userAccessMapping.pdf" />
										<display:setProperty name="export.pdf" value="true" />
									</display:table>

								</div>

							</logic:notEmpty> <logic:notEmpty name="updatedAllocationListAllUser"
								scope="request">

								<div style="width: 250px; height: 683px; overflow: auto;">
									<table border="1" class="hovertable">
										<tr>
											<th><b>User</b></th>
											<th><b>Access To</b></th>
										</tr>
										<logic:iterate id="updatedAllocationListAllUser"
											name="updatedAllocationListAllUser" scope="request">
											<logic:iterate id="listAllUser"
												name="updatedAllocationListAllUser"
												property="allUserAllocationList">
												<tr onmouseover="this.style.backgroundColor='#ffff66';"
													onmouseout="this.style.backgroundColor='#ffffff';">
													<td><b><bean:write name="listAllUser"
																property="parentUserName" /></b></td>
													<td><bean:write name="listAllUser" property="userName" />
													</td>
												</tr>

											</logic:iterate>

										</logic:iterate>


									</table>
								</div>
							</logic:notEmpty></td>
					</tr>

				</table>

			</html:form>
		</div>
	</div>
</body>

</html>
