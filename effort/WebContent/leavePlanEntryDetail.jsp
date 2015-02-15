<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    	               "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ page language="java" import="java.util.*,java.text.*"%>

<!-- LEAVE-JSP page after clicking Get Detail Button -->

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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Calsoft Labs-Timesheet-Leave Plan Detail Page</title>
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
<style type="text/css">
.dsb {
	background-color: #EEEEEE
}
table.hovertable {
	font-family: verdana, arial, sans-serif;
	font-size: 11px;
	color: #333333;
	border-width: 1px;
	border-color:  #a9c6c9;
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
<style type="text/css">
body {
	font: normal 14px Calibri, Calibri, Calibri;
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
</head>
<body style="margin: 17%; margin-top: 0%;">
		<form name="frm" method="post">
		<div style=" margin-left: 40px;" id="container">
			<%
				if (request.getAttribute("leaveDateList") != null) {
					int date = 0;
					int dateNew = 0;
					List leaveDateList = (List) request
							.getAttribute("leaveDateList");
					List leaveDateList1 = (List) request
							.getAttribute("leaveDateList");
					int x = leaveDateList.size();
			%>

			<!-- Displaying Calendar -->

			<table width="130%" height="120%">
				
				<tr>
					<td width="80%">
					<table align="left" width="130%" height="100%" border="0"cellspacing="0" cellpadding="0" style="font-family: verdana, arial, sans-serif;font-size: 11px;">
							<tr></tr>
							<tr>
								<td><table width="100%" height="100%" border="0"
										cellspacing="0" cellpadding="0" style="font-family: verdana, arial, sans-serif;font-size: 11px;">
										<tr>									
												<td>Year&nbsp;<select name="iYear" id="iYear"
														onChange="goToMonth('LeaveAction.do?method=getLeaveDashBoard')" >
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
															<option value="<%=im%>" selected="selected"><%=new SimpleDateFormat("MMMM").format(new Date(
								2008, im, 01))%></option>
															<%
																} else {
															%>
															<option value="<%=im%>"><%=new SimpleDateFormat("MMMM").format(new Date(
								2008, im, 01))%></option>
															<%
																}
																	}
															%>
													</select>&nbsp;&nbsp;<a href="##." style="font-weight: bolder;" onclick="getLeaveData('LeaveAction.do?method=getLeaveDashBoard')">Get leaves for this year.</a>
												</td>
											
										</tr>
										<tr>
											<td width="75%" align="left" style="color: blue"><h3><%=new SimpleDateFormat("MMMM").format(new Date(2008,
						iMonth, 01))%>
													<%=iYear%></h3></td>
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
												<td align="center" height="35" class="dsb" style="font-family: verdana, arial, sans-serif;font-size: 11px;">&nbsp;</td>
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
														name="checkDate" checked="checked" style="font-family: verdana, arial, sans-serif;font-size: 11px;"/><%=(cnt - weekStartDay + 1)%></span>
												</td>
												<%
													} else {
												%>
												<td height="35" id="day_<%=(cnt - weekStartDay + 1)%>"><span><input
														type="checkbox" value="<%=(cnt - weekStartDay + 1)%>"
														name="checkDate" style="font-family: verdana, arial, sans-serif;font-size: 11px;"/><%=(cnt - weekStartDay + 1)%></span>
												</td>



												<%
													}
																}
												%>
												<%
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
									
									<br> <br>
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
					<td width="45%">
					<logic:present name="disablingSaveAndClear" scope="request">
						<input type="button" value="Save" disabled="disabled" style="font-family: verdana, arial, sans-serif;font-size: 11px;cursor: not-allowed;"/> 
						<input type="submit" value="Clear" disabled="disabled" style="font-family: verdana, arial, sans-serif;font-size: 11px;cursor: not-allowed;" />
					</logic:present>
					<logic:notPresent name="disablingSaveAndClear" scope="request">
						<input type="button" class="changeButtonUI" value="Save"onclick="submitForm('LeaveAction.do?method=saveDetail')" style="font-family: verdana, arial, sans-serif;font-size: 11px;cursor: pointer;"/> 
						<input type="submit" class="changeButtonUI" value="Clear" onclick="onClickLeave('LeaveAction.do?method=clearCheckedDate')" style="font-family: verdana, arial, sans-serif;font-size: 11px;cursor: pointer;" />
					</logic:notPresent>
						<input type="button" class="changeButtonUI" value="Get Details" onclick="submitForm('LeaveAction.do?method=getLeaveDetails')" style="font-family: verdana, arial, sans-serif;font-size: 11px;cursor: pointer;" />
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
			<br>
             <br>
              <logic:empty name="leaveFormList" scope="request"><div style="font-family: verdana, arial, sans-serif;font-size: 11px;color: red;text-align: center;">No Data to Display</div> </logic:empty>
			<div style="width: 355px; height: 300px; overflow: auto;">
				<logic:notEmpty name="leaveFormList" scope="request">

					<!-- List of Leave -->

					<table class="hovertable" width="100%" border="1">
						<tr></tr>
						<tr></tr>
						<tr></tr>
						<tr></tr>
						<tr></tr>
						<tr onmouseover="this.style.backgroundColor='#FFFFFF';"
							onmouseout="this.style.backgroundColor='#FFFFFF';">
							<!-- <th align="center">Date</th>
							<th align="center">Leave Date</th> -->
							<th align="center">Leave Availed On</th>
							<th align="center">Updated On</th>
						</tr>
						<logic:iterate id="leaveFormList" name="leaveFormList"
							scope="request" type="com.calsoft.leave.form.LeaveForm">
							<tr onmouseover="this.style.backgroundColor='#ffff66';"
								onmouseout="this.style.backgroundColor='#FFFFFF';">
								<td><bean:write name="leaveFormList" property="checkDate" />-<bean:write name="leaveFormList" property="selectMonth" /></td>
								<%-- <td><bean:write name="leaveFormList" property="checkDate" />
								</td> --%>
								<td><bean:write name="leaveFormList"
										property="updatedDateString" /></td>
							</tr>
						</logic:iterate>

					</table>
				</logic:notEmpty>
				<logic:notEmpty name="leaveListForYear" scope="request">
			<div style="text-align: right; width: 650px; margin-bottom: 1mm;">
					<a href="##." style="text-decoration: none; size: 10px; font-weight: bold;"
						onclick="submitReport('LeaveAction.do?method=generateCompleteLeaveReport');">Export
						To Excel</a> <img src="img/getI.png" style="height: 20px;" />
				</div>
			<div style="height: 550px;overflow: auto;width: 650px;">
			 <table align="left" class="hovertable" width="630px">
					<tr onmouseover="this.style.backgroundColor='#FFFFFF';"
						onmouseout="this.style.backgroundColor='#FFFFFF';">
						<th align="center">Resource Name</th>
						<th align="center">Leave Date</th>
					</tr>

					<logic:iterate id="leaveForm" name="leaveListForYear" scope="request"
						type="com.calsoft.leave.form.LeaveForm">
						<tr onmouseover="this.style.backgroundColor='#ffff66';"
							onmouseout="this.style.backgroundColor='#FFFFFF';">
							<td width="3%"><bean:write name="leaveForm"
									property="userName" /></td>
							<td width="5%"><bean:write name="leaveForm"
									property="updatedDateString" /></td>

						</tr>
					</logic:iterate>
				</table>	
				</div>		
			</logic:notEmpty>		
			</div>
              
</div>
		</form>
</body>
</html>