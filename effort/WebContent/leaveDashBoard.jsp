<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    	               "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ page language="java" import="java.util.*,java.text.*"%>


<!-- LEAVE-JSP page after clicking Leave Plan Link -->

<%!public int nullIntconv(String inv) {
		int conv = 0;

		try {
			conv = Integer.parseInt(inv);
		} catch (Exception e) {
		}
		return conv;
	}%>
<%
	int iYear = nullIntconv(request.getParameter("iYear"));
	int iMonth = nullIntconv(request.getParameter("iMonth"));
	Calendar ca = new GregorianCalendar();
	int iTDay = ca.get(Calendar.DATE);
	int iTYear = ca.get(Calendar.YEAR)-2;
	int iTMonth = ca.get(Calendar.MONTH);
	if (iYear == 0) {
		iYear = ca.get(Calendar.YEAR);
		iMonth = ca.get(Calendar.MONTH);
	}
	GregorianCalendar cal = new GregorianCalendar(iYear, iMonth, 1);
	int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	int weekStartDay = cal.get(Calendar.DAY_OF_WEEK);
	cal = new GregorianCalendar(iYear, iMonth, days);
	int iTotalweeks = cal.get(Calendar.WEEK_OF_MONTH);
%>

<html>
<head>
<script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
<meta http-equiv="refresh">
<link rel="icon" type="image/jpg" href="img/calsoftNew.jpg" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Calsoft Labs-Timesheet-Leave Plan DashBoard Page</title>

<script>
	function goToMonth(url) {
		document.frm.submit();
		var submitForm = document.forms[0];
		submitForm.action = url;
		submitForm.submit();
	}
	function goToYear() {
		document.frm.submit();
	}
</script>
<script type="text/javascript">
	function submitReport1(url) {
		var formSubmit = document.forms[0];
		formSubmit.action = url;
		formSubmit.submit();
	}
</script>

<style>
body {
	font: normal 14px Calibri, Calibri, Calibri;
}
.dsb {
	background-color: #EEEEEE
}
</style>

<script type="text/javascript">
	function submitForm(url) {
		var submitForm = document.forms[0];
		submitForm.action = url;
		submitForm.submit();
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
	function submitReport(url) {
		var formSubmit = document.forms[0];
		formSubmit.action = url;
		formSubmit.submit();

	}
	function getLeaveData(url){		
		var iYear = $('#iYear option:selected').text();
		var iMonth = $('#iMonth option:selected').text();
		var formSubmit = document.forms[0];
		formSubmit.action = url+'&iYearComplete='+iYear+"&iMonthComplete="+iMonth;
		formSubmit.submit();
	}
</script>
<style type="text/css">
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
	padding: 8px;
	border-style: solid;
	border-color: #a9c6c9;
}

table.hovertable tr {
	background-color: #FFFFFF;
}

table.hovertable td {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #a9c6c9;
}
.changeButtonUI:hover {
	font-family: verdana, arial, sans-serif;
	font-size: 11px;
	color: #ffffff;
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
		), color-stop(1, #1e62d0) ); background : -moz-linear-gradient( center
	top, #3d94f6 5%, #1e62d0 100%); filter : progid :
	DXImageTransform.Microsoft.gradient ( startColorstr = '#3d94f6',
	endColorstr = '#1e62d0');
	height: 23px;
	background: -moz-linear-gradient(center top, #3d94f6 5%, #1e62d0 100%);
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#3d94f6',
		endColorstr='#1e62d0' );
}
</style>
</head>
<body style="margin: 17%; margin-top: 0%;">
	<form name="frm" method="post">
		<div style="margin-left: 40px;" id="container">
			<%
				if (request.getAttribute("leaveDate") != null)
				{
					int date = 0;
					int dateNew = 0;
					List leaveDateList = (List) request.getAttribute("leaveDate");
					//List leaveDateList1 = (List) request.getAttribute("leaveDate");
					int x = leaveDateList.size();
			%>
			<!-- Displaying Calendar -->

			<table width="130%" height="120%">
				
				
				<tr>
					<td width="80%"><table align="left" width="130%" height="100%" border="0"cellspacing="0" cellpadding="0" style="font-family: verdana, arial, sans-serif;font-size: 11px;">
							<tr></tr>
							<tr>
								<td><table width="100%" height="100%" border="0"cellspacing="0" cellpadding="0" class="hovertable1">
										<tr>
												<td>Year&nbsp;<select name="iYear" id="iYear"
														onChange="goToMonth('LeaveAction.do?method=getLeaveDashBoard')">
															<%
																// start year and end year in combo box to change year in calendar
																	for (int iy = iTYear; iy <= iTYear + 13; iy++) {
																		if (iy == iYear) {
															%>
															<option value="<%=iy%>" selected="selected"><%=iy%></option>
															<%
																} else {
															%>
															<option value="<%=iy%>"><%=iy%></option>
															<%
																}
																	}
															%>
													</select>&nbsp;&nbsp; Month&nbsp;<select name="iMonth" id="iMonth"
														onchange="goToMonth('LeaveAction.do?method=getLeaveDashBoard')">
															<%
																// print month in combo box to change month in calendar
																	for (int im = 0; im <= 11; im++) {
																		if (im == iMonth) {
															%>
															<option value="<%=im%>" selected="selected"><%=new SimpleDateFormat("MMMM").format(new Date(2008, im, 01))%></option>
															<%
																} else {
															%>
															<option value="<%=im%>"><%=new SimpleDateFormat("MMMM").format(new Date(2008, im, 01))%></option>
															<%
																}
																	}
															%>															
													</select> &nbsp;&nbsp;<a href="##." style="font-weight: bolder;" onclick="getLeaveData('LeaveAction.do?method=getLeaveDashBoard')">Get leaves for this year.</a>
												</td>
											
										</tr>
										<tr>
											<td width="75%" align="left" style="color: blue"><h3><%=new SimpleDateFormat("MMMM").format(new Date(2008,iMonth, 01))%>
													<%=iYear%></h3>
											</td>
											<td></td>

										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td width="100%">
								<table align="left" border="1" cellpadding="0" cellspacing="0" width="30%" height="100%" style="font-family: verdana, arial, sans-serif;font-size: 11px;">
										<tbody style="width: 100%; height: 100%;">
											<tr>
												<th bgcolor="LightPink" width="8%">Sun</th>
												<th bgcolor="LightGray" width="8%">Mon</th>
												<th bgcolor="LightGray" width="8%">Tue</th>
												<th bgcolor="LightGray" width="8%">Wed</th>
												<th bgcolor="LightGray" width="8%">Thu</th>
												<th bgcolor="LightGray" width="8%">Fri</th>
												<th bgcolor="LightPink" width="8%">Sat</th>
											</tr>
											<%
												int cnt = 1;
													for (int i = 1; i <= iTotalweeks; i++) {
											%>
											<tr>
												<%
													for (int j = 1; j <= 7; j++) {
																if (cnt < weekStartDay
																		|| (cnt - weekStartDay + 1) > days) {
												%>
												<td align="center" height="35" class="dsb">&nbsp;</td>
												<%
													} else {
												%>
												<%
													String g = new Integer(cnt - weekStartDay + 1)
																			.toString();
																	if (leaveDateList.contains(g)) {
												%>
												<td height="35" id="day_<%=(cnt - weekStartDay + 1)%>"><span><input
														type="checkbox" value="<%=(cnt - weekStartDay + 1)%>"
														name="checkDate" checked="checked" /><%=(cnt - weekStartDay + 1)%></span>
												</td>
												<%
													} else {
												%>
												<td height="35" id="day_<%=(cnt - weekStartDay + 1)%>"><span><input
														type="checkbox" value="<%=(cnt - weekStartDay + 1)%>"
														name="checkDate" /><%=(cnt - weekStartDay + 1)%></span></td>

												<%
													}
												%>
												<%
													}
																cnt++;
															}
												%>
											</tr>
											<%
												}
											%>
										</tbody>
									</table>									
									 <iframe src="holidayListBangalore.jsp" width="40%" height="180px" style="border-style: hidden;vertical-align: top;"></iframe>																	
						</table>
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td width="65%">
					<logic:present name="disablingSaveAndClear" scope="request">
					   	<input type="button" value="Save" disabled="disabled" style="font-family: verdana, arial, sans-serif;font-size: 11px;cursor: not-allowed;"/> 
						<input type="submit" value="Clear" disabled="disabled" style="font-family: verdana, arial, sans-serif;font-size: 11px;cursor: not-allowed;" />
					</logic:present>
					<logic:notPresent  name="disablingSaveAndClear" scope="request">
						<input type="button" class="changeButtonUI" value="Save" onclick="submitForm('LeaveAction.do?method=saveDashBoard')" style="font-family: verdana, arial, sans-serif;font-size: 11px;cursor: pointer;"/> 
						<input type="submit" class="changeButtonUI" value="Clear" onclick="onClickLeave('LeaveAction.do?method=clearCheckedDateDashBoard')"style="font-family: verdana, arial, sans-serif;font-size: 11px;cursor: pointer;" />					
					</logic:notPresent>
											
						<input type="button" class="changeButtonUI" value="Get Details" onclick="submitForm('LeaveAction.do?method=getLeaveDetails')"style="font-family: verdana, arial, sans-serif;font-size: 11px;cursor: pointer;" />
					<logic:present name="validForTeamLeaveDetail" scope="request">
						<input type="button" class="changeButtonUI" value="Get Team Leave Details" onclick="submitForm('LeaveAction.do?method=getTeamLeaveDetail')" style="font-family: verdana, arial, sans-serif;font-size: 11px;cursor: pointer;"/>
					</logic:present>
					</td>
				</tr>
			</table>
			<%
				}
			%>
			&nbsp;&nbsp;
			<hr style="color: #999" size="4%" width="810px">
			<br> <br> 
			<logic:notEmpty name="leaveList" scope="request">

				<!-- List of Dash Board -->

				<table align="left" class="hovertable" width="55%">

					<tr onmouseover="this.style.backgroundColor='#FFFFFF';"
						onmouseout="this.style.backgroundColor='#FFFFFF';">
						<th align="center">Month</th>
						<th align="center">Leave Date</th>
					</tr>

					<logic:iterate id="LeaveForm" name="leaveList" scope="request"
						type="com.calsoft.leave.model.Leave">
						<tr onmouseover="this.style.backgroundColor='#ffff66';"
							onmouseout="this.style.backgroundColor='#FFFFFF';">
							<td width="3%"><bean:write name="LeaveForm"
									property="leave_month" /></td>
							<td width="5%"><bean:write name="LeaveForm"
									property="leave_date" /></td>
						</tr>
					</logic:iterate>
				</table>
			</logic:notEmpty>
			<logic:present name="leaveListForYear" scope="request">
			<logic:empty name="leaveListForYear" scope="request">
				<span style="padding-left: 350px;color: red; text-align: center; text-shadow: green; font-family: verdana, arial, sans-serif; font-size: 11px;">No Data Found.</span>
			</logic:empty>
			<logic:notEmpty name="leaveListForYear" scope="request">
			<div style="text-align: right; width: 460px; margin-bottom: 1mm;">
					<a href="##." style="text-decoration: none; size: 10px; font-weight: bold;"
						onclick="submitReport('LeaveAction.do?method=generateCompleteLeaveReport');">Export
						To Excel</a> <img src="img/getI.png" style="height: 20px;" />
				</div>
			<div style="height: 550px;overflow: auto;width: 470px;">
			 <table align="left" class="hovertable" width="450px">
					<tr onmouseover="this.style.backgroundColor='#FFFFFF';"
						onmouseout="this.style.backgroundColor='#FFFFFF';">
						<th style="width: 110px;">Resource Name</th>
						<th style="width: 50px;">Leave Date</th>
						<th style="width: 50px;">Type</th>
					</tr>
					<logic:iterate id="leaveForm" name="leaveListForYear" scope="request"
						type="com.calsoft.leave.form.LeaveForm">
						<tr onmouseover="this.style.backgroundColor='#ffff66';"
							onmouseout="this.style.backgroundColor='#FFFFFF';">
							<td><bean:write name="leaveForm"
									property="userName" /></td>
							<td><bean:write name="leaveForm"
									property="updatedDateString" /></td>
							<td><bean:write name="leaveForm"
									property="work_category" /></td>
						</tr>
					</logic:iterate>
				</table>	
				</div>		
			</logic:notEmpty>	
			</logic:present>							
		</div>
	</form>
</body>
</html>