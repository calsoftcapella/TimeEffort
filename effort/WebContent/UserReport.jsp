<%@page import="java.util.Calendar"%>
<%@page import="com.calsoft.user.form.UserForm"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.calsoft.task.form.TaskForm"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fn.tld" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Calsoft Labs Timesheet User Report</title>
<link href="css/user_report_stylesheet.css" rel="stylesheet" type="text/css" />
<style type="text/css">
body {
	font: normal 14px Calibri, Calibri, Calibri;
}
</style>
<script src="js/jquery.js"></script>
<script src="js/jquery.monthpicker.js"></script>
<script src="js/shared.prototype.js"></script>
<script type="text/javascript" src="js/fxHeader_0.6.min.js"></script>
<script type="text/javascript" src="js/report_page_script.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$('#month-default').monthPicker();
		$('#month-settings_id').monthPicker({
			'numberOfYears' : 5,
			'startYearOffset' : -5,
			'dayOfMonth' : 20
		});
	});
</script>
</head>
<body style="margin: 17%; margin-top: 0%; outline-color: blue;">
	<div style="margin-left: 40px;">
		<div id="checkUser" align="center"></div>
		<div id="messageIds"
			style="color: red; text-align: center; text-shadow: green; font-family: verdana, arial, sans-serif; font-size: 11px;">
			<logic:present name="freezingMessage" scope="request">
				<bean:write name="freezingMessage" scope="request" />
			</logic:present>

			<logic:present name="taskFormList" scope="request">
				<logic:empty name="taskFormList" scope="request">
					No Data Found					
		       </logic:empty>
			</logic:present>

			<logic:present name="defaulterListDetails" scope="request">
				<logic:empty name="defaulterListDetails" scope="request">
					No Data Found					
		       </logic:empty>
			</logic:present>
			
			<logic:notPresent name="defaulterListDetails" scope="request">
			<logic:present name="invalidRequest" scope="request">
					Request is not valid for this month.
			</logic:present>
			</logic:notPresent>

			<logic:present name="CompOffReport" scope="request">
				<logic:empty name="CompOffReport" scope="request">
					No Data Found					
		       </logic:empty>
			</logic:present>

			<logic:present name="leaveFormList" scope="request">
				<logic:empty name="leaveFormList" scope="request">
				No Data Found 
			</logic:empty>
			</logic:present>
			<logic:present name="time_entry_updateStatus" scope="request">
			 <logic:empty name="time_entry_updateStatus" scope="request">
			 	No Data Found 
			 </logic:empty>
			</logic:present>			
		</div>
		<script type="text/javascript">
		     $('#messageIds').delay(2000).fadeOut();
		</script>
		<logic:present name="selectedAllocatedResource" scope="request">
			<div style="display: none;" id="pickSelectedResourceId">
		  		<logic:iterate id="resourceId" name="selectedAllocatedResource"><p><bean:write name="resourceId"/></p></logic:iterate>
		   </div>
		</logic:present>
			
		<logic:present name="locationDetail" scope="request">
			<input type="hidden" name="locFromAction" id="locFromAction"
				value='<bean:write name="locationDetail" scope="request"/>' />
		</logic:present>
		<logic:present name="selectedClientResource" scope="request">
		   <input type="hidden" name="selectedClientResource" id="hidded_client_id"
				value='<bean:write name="selectedClientResource" scope="request"/>' />
		</logic:present>
		<html:form action="displayReport" method="post">
			<table border="0" height="100%" class="hovertable1">
				<tr>
					<td>
						<!--Team Wise Report  --> <logic:present name="conList"
							scope="session">
							<table class="hovertable1">
								<tr>
									<td>Select Month & Year: <logic:notEmpty name="selectedDate"
											scope="session">
											<input type="text" id="month-settings_id" name="month-settings"
												value="<bean:write name="selectedDate" scope="session"/>" />
										</logic:notEmpty> <logic:empty name="selectedDate" scope="session">
											<input type="text" id="month-settings_id" name="month-settings" />
										</logic:empty>
									</td>
								</tr>
								<tr>
									<td>Team Wise Report: <a href="##."
										style="color: #44157D; text-decoration: none; background-color: #F2FFFF;"
										onclick="listbox_selectall('team', true)">All</a> <a
										href="##."
										style="color: #44157D; text-decoration: none; background-color: #F2FFFF;"
										onclick="listbox_selectall('team', false)">None</a>
									</td>
									<td colspan="2" align="right" style="padding-right: 30px;">Resource Wise Report: <a
										href="##."
										style="color: #44157D; text-decoration: none; background-color: #F2FFFF;"
										onclick="listbox_selectall('userId', true)">All</a> <a
										href="##."
										style="color: #44157D; text-decoration: none; background-color: #F2FFFF;"
										onclick="listbox_selectall('userId', false)">None</a>
									</td>
								</tr>
								<tr>
									<td><select name="team" id="team" size="10"
										multiple="multiple"
										style="width: 180px; height: 210px; font-family: verdana, arial, sans-serif; font-size: 11px;">
											<logic:iterate id="con" name="conList" scope="session">
												<option value="<bean:write name="con"/>">
													<bean:write name="con" />
												</option>
											</logic:iterate>
									</select></td>

									<td style="padding-left: 15px; padding-right: 20px;">
										<table class="hovertable1">
											<tr>
												<td><input type="button"
													onclick="submitReportManager('displayReport.do?method=getLeaveReport');"
													value="Get Leave Report" class="changeButtonUI"
													style="width: 200px; font-family: verdana, arial, sans-serif; font-size: 11px;" /></td>
											</tr>
											<tr>
												<td><input type="button"
													onclick="submitReportManager('displayReport.do?method=displayReport');"
													value="Get Timesheet Dashboard" class="changeButtonUI"
													style="width: 200px; font-family: verdana, arial, sans-serif; font-size: 11px;" /></td>
											</tr>
											<tr>
												<td><input type="button"
													onclick="submitReportManager('displayReport.do?method=getDetailedTimesheet');"
													value="Get Detailed Timesheet" class="changeButtonUI"
													style="width: 200px; font-family: verdana, arial, sans-serif; font-size: 11px;" /></td>
											</tr>
											<tr>
												<td><input type="button"
													onclick="submitReport('displayReport.do?method=getContactNumbers');"
													value="Get Coordinates" class="changeButtonUI"
													style="width: 200px; font-family: verdana, arial, sans-serif; font-size: 11px;" /></td>
											</tr>
											<tr>
												<td><input type="button"
													onclick="submitReport('displayReport.do?method=getExceptionDashboard');"
													value="Exception Dashboard" class="changeButtonUI"
													style="width: 200px; font-family: verdana, arial, sans-serif; font-size: 11px;" /></td>
											</tr>
											<!-- Added Timesheet Freezing functionality -->
											<tr>
												<td><input type="button" id="freezeButtonId"
														onclick="submitReportForFreezing('displayReport.do?method=freezeTimesheet');"
														value="Freeze Timesheet" class="changeButtonUI"
														style="width: 200px; font-family: verdana, arial, sans-serif; font-size: 11px;" />
												<img alt="1" id="loading_image_for_button" src="img/button_loading.gif" style="vertical-align: bottom;display: none;height: 30px;padding-left: 0px;">	
														</td>
											</tr>
											<!-- Updates ends here. -->
										</table>
									</td>
									<!--Resource Wise Report  -->
									<td style="padding-right: 30px;">
									<html:select property="userId" styleId="userId" onchange ="checkFreezingStatusForSelectedResource();"
												multiple="true" size="10"
												style="width: 180px;height: 210px;font-family: verdana, arial, sans-serif;font-size: 11px;">									
										<logic:notEmpty name="userList" scope="session">											
												<logic:iterate name="userList" id="userList" scope="session">
													<option
														value="<bean:write name="userList" property="userId" />">
														<bean:write name="userList" property="userName" />
													</option>
												</logic:iterate>											
										</logic:notEmpty>
									</html:select>	
										</td>
								</tr>
							</table>
						</logic:present> <logic:notPresent name="conList" scope="session">
							<table class="hovertable1">
								<tr>
									<td
										style="margin-top: 10px; font-family: verdana, arial, sans-serif; font-size: 11px;">Select
										Month & Year: <logic:notEmpty name="selectedDate" scope="session">
											<input type="text" id="month-settings_id" name="month-settings"
												value="<bean:write name="selectedDate" scope="session"/>" />
										</logic:notEmpty> <logic:empty name="selectedDate" scope="session">
											<input type="text" id="month-settings_id" name="month-settings" />
										</logic:empty>
									</td>
									<!-- Added code here for Filtering resource based on location detail  -->
									<logic:present name="allowedForLocationWiseReport"
										scope="session">
										<td
											style="margin-top: 10px; font-family: verdana, arial, sans-serif; font-size: 11px;padding-left: 70px;">Select
											Location:
											<div>
												<select name="location" id="location"
													onchange="getDetailsBasedOnLocation()"
													style="font-family: verdana, arial, sans-serif; font-size: 11px;">
													<option value="">Select</option>
													<option value="Bangalore">Bangalore</option>
													<option value="Chennai">Chennai</option>
													<option value="Onsite">Onsite</option>
												</select>
												<script type="text/javascript">
													pickLocationFromAction();
												</script>
											</div>
										</td>
										<td
											style="margin-top: 10px; font-family: verdana, arial, sans-serif; font-size: 11px;padding-left: 100px;">Select
											Client:
											<div>
												<select name ="client_resource_ids" id="client_option_id" onchange="getDetailsBasedSelectedClient(this)"
													style="font-family: verdana, arial, sans-serif; font-size: 11px;width: 200px;">
													<option value="0">Select</option>
													<logic:present name="listForClientResourceDetail" scope="session">
													  <logic:notEmpty name="listForClientResourceDetail" scope="session">
													     <logic:iterate id="client_res" name="listForClientResourceDetail" scope="session" type="com.calsoft.report.form.ReportForm">
													         <option value="<bean:write name="client_res" property="client_resource_ids"/>">
													         	<bean:write name="client_res" property="userName"/>
													         </option>
													     </logic:iterate>
													  </logic:notEmpty>
													</logic:present>
												</select>
											</div>
										</td>
									</logic:present>
									<!-- Updates ends here!!! -->
								</tr>
								<tr>
									<td>Select Resources: <a href="##."
										style="color: #44157D; text-decoration: none; background-color: #F2FFFF;"
										onclick="listbox_selectall('userId', true)">All</a> <a
										href="##."
										style="color: #44157D; text-decoration: none; background-color: #F2FFFF;"
										onclick="listbox_selectall('userId', false)">None</a></td>
								</tr>
								<tr>
									<td>
										<html:select property="userId" styleId="userId"
												multiple="true" size="10" onchange ="checkFreezingStatusForSelectedResource();"
												style="width: 180px;height: 210px;font-family: verdana, arial, sans-serif;font-size: 11px;">									
										<logic:notEmpty name="userList" scope="session">											
												<logic:iterate name="userList" id="userList" scope="session">
													<option
														value="<bean:write name="userList" property="userId"/>"
														selected="selected">
														<bean:write name="userList" property="userName" />
													</option>
												</logic:iterate>											
										</logic:notEmpty>
										</html:select>
									</td>
									<td>
										<table class="hovertable1">
											<tr>
												<td><input type="button"
													onclick="submitReport1('displayReport.do?method=getLeaveReport');"
													value="Get Leave Report" class="changeButtonUI"
													style="width: 200px; font-family: verdana, arial, sans-serif; font-size: 11px;" /></td>
											</tr>
											<tr>
												<td><input type="button" id="dash_id"
													onclick="submitReport1('displayReport.do?method=displayReport');"
													value="Get Timesheet Dashboard" class="changeButtonUI"
													style="width: 200px; font-family: verdana, arial, sans-serif; font-size: 11px;" /></td>
											</tr>
											<tr>
												<td><input type="button"
													onclick="submitReport1('displayReport.do?method=getDetailedTimesheet');"
													value="Get Detailed Timesheet" class="changeButtonUI"
													style="width: 200px; font-family: verdana, arial, sans-serif; font-size: 11px;" /></td>
											</tr>
											<tr>
												<td><input type="button"
													onclick="submitReport('displayReport.do?method=getContactNumbers');"
													value="Get Coordinates" class="changeButtonUI"
													style="width: 200px; font-family: verdana, arial, sans-serif; font-size: 11px;" /></td>
											</tr>
											<!-- Option to get defaulter List  -->
											<logic:present name="allowedForLocationWiseReport"
												scope="session">
												<tr>
													<td><input type="button"
														onclick="submitReport1('displayReport.do?method=getDefaulterListDetails');"
														value="Get Defaulter List" class="changeButtonUI"
														style="width: 200px; font-family: verdana, arial, sans-serif; font-size: 11px;" /></td>
												</tr>
												<tr>
													<td><input type="button"
														onclick="submitReport1('displayReport.do?method=getCompOffReport');"
														value="Get CompOff Report" class="changeButtonUI"
														style="width: 200px; font-family: verdana, arial, sans-serif; font-size: 11px;" /></td>
												</tr>
												<!-- Added Timesheet Freezing functionality -->
												<tr>
													<td><input type="button" id="freezeButtonId"
															onclick="submitReportForFreezing('displayReport.do?method=freezeTimesheet');"
															value="Freeze Timesheet" class="changeButtonUI"
															style="width: 200px; font-family: verdana, arial, sans-serif; font-size: 11px;" />
														<span style="width: 10px;position: fixed;">
															<img alt="1" id="loading_image_for_button" src="img/button_loading.gif" style="vertical-align: bottom;display: none;height: 30px;padding-left: 0px;">
														</span>		
													</td>
												</tr>
												
												<!-- Added time entry time_status functionality -->
												<tr>
													<td><input type="button"
															onclick="submitReport1('displayReport.do?method=getTimestampForTimeEntries');"
															value="Get Time Entry Timestamp" class="changeButtonUI"
															style="width: 200px; font-family: verdana, arial, sans-serif; font-size: 11px;" />
													</td>
												</tr>
												<!-- Updates ends here. -->
											</logic:present>

										</table>
									</td>
								</tr>
							</table>
						</logic:notPresent>
					</td>
				</tr>
			</table>
			<logic:present name="reportList" scope="request">
			<div id = "timesheet_dash">
				<div style="text-align: right; width: 748px; margin-bottom: 1mm;">
					<a href="###."
						style="text-decoration: none; size: 10px; font-weight: bold;"
						onclick="submitReport('displayReport.do?method=generateReportTimeSheetDashBoard');">Export
						To Excel</a> <img src="img/getI.png" style="height: 20px;" />
				</div>
				<div style="margin-left: 2mm;">
					<table id="dataTable1" width="750px" class="hovertableP">
						<tr>
							<th>&nbsp;Day</th>
							<%
								int dCount = 0;
							%>
							<logic:iterate id="dayList" name="dayList" scope="request">
								<logic:match value="Sat" name="dayList">
									<th style="background: LightPink;"><b><bean:write
												name="dayList" /></b></th>
								</logic:match>
								<logic:notMatch value="Sat" name="dayList">
									<logic:match value="Sun" name="dayList">
										<th style="background: LightPink;"><b><bean:write
													name="dayList" /></b></th>
									</logic:match>
									<logic:notMatch value="Sun" name="dayList">
										<th style="background: #A5A1A0;"><b> <bean:write
													name="dayList" /></b></th>
									</logic:notMatch>
								</logic:notMatch>
								<%
									dCount++;
								%>
							</logic:iterate>
							<th
								style="border-bottom-color: #A5A1A0; margin-bottom: 0px; padding: 0px; vertical-align: bottom;">Total
								Time</th>
						</tr>
						<tr>
							<th>&nbsp;Username&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
							<logic:iterate id="dVal" name="dayListForDates" scope="request">
								<logic:match value=" " name="dVal">
									<th style="background: LightPink;"><b> <bean:write
												name="dVal" />
									</b></th>
								</logic:match>
								<logic:notMatch value=" " name="dVal">
									<th style="background: #A5A1A0;"><b> <bean:write
												name="dVal" /></b></th>
								</logic:notMatch>
							</logic:iterate>
							<th
								style="background-color: #A5A1A0; margin-top: 0px; padding: 0px;"
								align="center" valign="top"><b> in Hrs. </b></th>

						</tr>
						<logic:present name="reportList" scope="request">
							<logic:iterate id="reportListId" name="reportList"
								scope="request">

								<tr>
									<td style="color: #000000; background-color: #EBEBEB;"><bean:write
											name="reportListId" property="userName" /></td>

									<td><logic:match name="reportListId" property="time1"
											value=" ">
											<logic:match name="reportListId" property="time1" value="  ">


												<logic:match name="reportListId" property="time1"
													value="   ">
													<font color="#228b22"> <bean:write
															name="reportListId" property="time1" /></font>
												</logic:match>
												<logic:notMatch name="reportListId" property="time1"
													value="   ">
													<font color="#0000CD"> <bean:write
															name="reportListId" property="time1" /></font>
												</logic:notMatch>





											</logic:match>

											<logic:notMatch name="reportListId" property="time1"
												value="  ">
												<font color="#FF0000"> <bean:write
														name="reportListId" property="time1" /></font>
											</logic:notMatch>
										</logic:match> <logic:notMatch name="reportListId" property="time1"
											value=" ">
											<bean:write name="reportListId" property="time1" />
										</logic:notMatch></td>
									<td><logic:match name="reportListId" property="time2"
											value=" ">
											<logic:match name="reportListId" property="time2" value="  ">


												<logic:match name="reportListId" property="time2"
													value="   ">
													<font color="#228b22"> <bean:write
															name="reportListId" property="time2" /></font>
												</logic:match>
												<logic:notMatch name="reportListId" property="time2"
													value="   ">
													<font color="#0000CD"> <bean:write
															name="reportListId" property="time2" /></font>
												</logic:notMatch>





											</logic:match>

											<logic:notMatch name="reportListId" property="time2"
												value="  ">
												<font color="#FF0000"> <bean:write
														name="reportListId" property="time2" /></font>
											</logic:notMatch>

										</logic:match> <logic:notMatch name="reportListId" property="time2"
											value=" ">
											<bean:write name="reportListId" property="time2" />
										</logic:notMatch></td>
									<td><logic:match name="reportListId" property="time3"
											value=" ">
											<logic:match name="reportListId" property="time3" value="  ">


												<logic:match name="reportListId" property="time3"
													value="   ">
													<font color="#228b22"> <bean:write
															name="reportListId" property="time3" /></font>
												</logic:match>
												<logic:notMatch name="reportListId" property="time3"
													value="   ">
													<font color="#0000CD"> <bean:write
															name="reportListId" property="time3" /></font>
												</logic:notMatch>





											</logic:match>

											<logic:notMatch name="reportListId" property="time3"
												value="  ">
												<font color="#FF0000"> <bean:write
														name="reportListId" property="time3" /></font>
											</logic:notMatch>

										</logic:match> <logic:notMatch name="reportListId" property="time3"
											value=" ">
											<bean:write name="reportListId" property="time3" />
										</logic:notMatch></td>
									<td><logic:match name="reportListId" property="time4"
											value=" ">
											<logic:match name="reportListId" property="time4" value="  ">


												<logic:match name="reportListId" property="time4"
													value="   ">
													<font color="#228b22"> <bean:write
															name="reportListId" property="time4" /></font>
												</logic:match>
												<logic:notMatch name="reportListId" property="time4"
													value="   ">
													<font color="#0000CD"> <bean:write
															name="reportListId" property="time4" /></font>
												</logic:notMatch>





											</logic:match>

											<logic:notMatch name="reportListId" property="time4"
												value="  ">
												<font color="#FF0000"> <bean:write
														name="reportListId" property="time4" /></font>
											</logic:notMatch>
										</logic:match> <logic:notMatch name="reportListId" property="time4"
											value=" ">
											<bean:write name="reportListId" property="time4" />
										</logic:notMatch></td>
									<td><logic:match name="reportListId" property="time5"
											value=" ">
											<logic:match name="reportListId" property="time5" value="  ">

												<logic:match name="reportListId" property="time5"
													value="   ">
													<font color="#228b22"> <bean:write
															name="reportListId" property="time5" /></font>
												</logic:match>
												<logic:notMatch name="reportListId" property="time5"
													value="   ">
													<font color="#0000CD"> <bean:write
															name="reportListId" property="time5" /></font>
												</logic:notMatch>




											</logic:match>

											<logic:notMatch name="reportListId" property="time5"
												value="  ">
												<font color="#FF0000"> <bean:write
														name="reportListId" property="time5" /></font>
											</logic:notMatch>

										</logic:match> <logic:notMatch name="reportListId" property="time5"
											value=" ">
											<bean:write name="reportListId" property="time5" />
										</logic:notMatch></td>
									<td><logic:match name="reportListId" property="time6"
											value=" ">
											<logic:match name="reportListId" property="time6" value="  ">

												<logic:match name="reportListId" property="time6"
													value="   ">
													<font color="#228b22"> <bean:write
															name="reportListId" property="time6" /></font>
												</logic:match>
												<logic:notMatch name="reportListId" property="time6"
													value="   ">
													<font color="#0000CD"> <bean:write
															name="reportListId" property="time6" /></font>
												</logic:notMatch>





											</logic:match>

											<logic:notMatch name="reportListId" property="time6"
												value="  ">
												<font color="#FF0000"> <bean:write
														name="reportListId" property="time6" /></font>
											</logic:notMatch>

										</logic:match> <logic:notMatch name="reportListId" property="time6"
											value=" ">
											<bean:write name="reportListId" property="time6" />
										</logic:notMatch></td>
									<td><logic:match name="reportListId" property="time7"
											value=" ">
											<logic:match name="reportListId" property="time7" value="  ">


												<logic:match name="reportListId" property="time7"
													value="   ">
													<font color="#228b22"> <bean:write
															name="reportListId" property="time7" /></font>
												</logic:match>
												<logic:notMatch name="reportListId" property="time7"
													value="   ">
													<font color="#0000CD"> <bean:write
															name="reportListId" property="time7" /></font>
												</logic:notMatch>




											</logic:match>

											<logic:notMatch name="reportListId" property="time7"
												value="  ">
												<font color="#FF0000"> <bean:write
														name="reportListId" property="time7" /></font>
											</logic:notMatch>
										</logic:match> <logic:notMatch name="reportListId" property="time7"
											value=" ">
											<bean:write name="reportListId" property="time7" />
										</logic:notMatch></td>
									<td><logic:match name="reportListId" property="time8"
											value=" ">
											<logic:match name="reportListId" property="time8" value="  ">


												<logic:match name="reportListId" property="time8"
													value="   ">
													<font color="#228b22"> <bean:write
															name="reportListId" property="time8" /></font>
												</logic:match>
												<logic:notMatch name="reportListId" property="time8"
													value="   ">
													<font color="#0000CD"> <bean:write
															name="reportListId" property="time8" /></font>
												</logic:notMatch>



											</logic:match>

											<logic:notMatch name="reportListId" property="time8"
												value="  ">
												<font color="#FF0000"> <bean:write
														name="reportListId" property="time8" /></font>
											</logic:notMatch>

										</logic:match> <logic:notMatch name="reportListId" property="time8"
											value=" ">
											<bean:write name="reportListId" property="time8" />
										</logic:notMatch></td>
									<td><logic:match name="reportListId" property="time9"
											value=" ">
											<logic:match name="reportListId" property="time9" value="  ">


												<logic:match name="reportListId" property="time9"
													value="   ">
													<font color="#228b22"> <bean:write
															name="reportListId" property="time9" /></font>
												</logic:match>
												<logic:notMatch name="reportListId" property="time9"
													value="   ">
													<font color="#0000CD"> <bean:write
															name="reportListId" property="time9" /></font>
												</logic:notMatch>


											</logic:match>

											<logic:notMatch name="reportListId" property="time9"
												value="  ">
												<font color="#FF0000"> <bean:write
														name="reportListId" property="time9" /></font>
											</logic:notMatch>

										</logic:match> <logic:notMatch name="reportListId" property="time9"
											value=" ">
											<bean:write name="reportListId" property="time9" />
										</logic:notMatch></td>
									<td><logic:match name="reportListId" property="time10"
											value=" ">
											<logic:match name="reportListId" property="time10" value="  ">

												<logic:match name="reportListId" property="time10"
													value="   ">
													<font color="#228b22"> <bean:write
															name="reportListId" property="time10" /></font>
												</logic:match>
												<logic:notMatch name="reportListId" property="time10"
													value="   ">
													<font color="#0000CD"> <bean:write
															name="reportListId" property="time10" /></font>
												</logic:notMatch>



											</logic:match>

											<logic:notMatch name="reportListId" property="time10"
												value="  ">
												<font color="#FF0000"> <bean:write
														name="reportListId" property="time10" /></font>
											</logic:notMatch>

										</logic:match> <logic:notMatch name="reportListId" property="time10"
											value=" ">
											<bean:write name="reportListId" property="time10" />
										</logic:notMatch></td>
									<td><logic:match name="reportListId" property="time11"
											value=" ">
											<logic:match name="reportListId" property="time11" value="  ">


												<logic:match name="reportListId" property="time11"
													value="   ">
													<font color="#228b22"> <bean:write
															name="reportListId" property="time11" /></font>
												</logic:match>
												<logic:notMatch name="reportListId" property="time11"
													value="   ">
													<font color="#0000CD"> <bean:write
															name="reportListId" property="time11" /></font>
												</logic:notMatch>




											</logic:match>

											<logic:notMatch name="reportListId" property="time11"
												value="  ">
												<font color="#FF0000"> <bean:write
														name="reportListId" property="time11" /></font>
											</logic:notMatch>

										</logic:match> <logic:notMatch name="reportListId" property="time11"
											value=" ">
											<bean:write name="reportListId" property="time11" />
										</logic:notMatch></td>
									<td><logic:match name="reportListId" property="time12"
											value=" ">
											<logic:match name="reportListId" property="time12" value="  ">

												<logic:match name="reportListId" property="time12"
													value="   ">
													<font color="#228b22"> <bean:write
															name="reportListId" property="time12" /></font>
												</logic:match>
												<logic:notMatch name="reportListId" property="time12"
													value="   ">
													<font color="#0000CD"> <bean:write
															name="reportListId" property="time12" /></font>
												</logic:notMatch>




											</logic:match>

											<logic:notMatch name="reportListId" property="time12"
												value="  ">
												<font color="#FF0000"> <bean:write
														name="reportListId" property="time12" /></font>
											</logic:notMatch>
										</logic:match> <logic:notMatch name="reportListId" property="time12"
											value=" ">
											<bean:write name="reportListId" property="time12" />
										</logic:notMatch></td>
									<td><logic:match name="reportListId" property="time13"
											value=" ">
											<logic:match name="reportListId" property="time13" value="  ">


												<logic:match name="reportListId" property="time13"
													value="   ">
													<font color="#228b22"> <bean:write
															name="reportListId" property="time13" /></font>
												</logic:match>
												<logic:notMatch name="reportListId" property="time13"
													value="   ">
													<font color="#0000CD"> <bean:write
															name="reportListId" property="time13" /></font>
												</logic:notMatch>



											</logic:match>

											<logic:notMatch name="reportListId" property="time13"
												value="  ">
												<font color="#FF0000"> <bean:write
														name="reportListId" property="time13" /></font>
											</logic:notMatch>

										</logic:match> <logic:notMatch name="reportListId" property="time13"
											value=" ">
											<bean:write name="reportListId" property="time13" />
										</logic:notMatch></td>
									<td><logic:match name="reportListId" property="time14"
											value=" ">
											<logic:match name="reportListId" property="time14" value="  ">

												<logic:match name="reportListId" property="time14"
													value="   ">
													<font color="#228b22"> <bean:write
															name="reportListId" property="time14" /></font>
												</logic:match>
												<logic:notMatch name="reportListId" property="time14"
													value="   ">
													<font color="#0000CD"> <bean:write
															name="reportListId" property="time14" /></font>
												</logic:notMatch>




											</logic:match>

											<logic:notMatch name="reportListId" property="time14"
												value="  ">
												<font color="#FF0000"> <bean:write
														name="reportListId" property="time14" /></font>
											</logic:notMatch>

										</logic:match> <logic:notMatch name="reportListId" property="time14"
											value=" ">
											<bean:write name="reportListId" property="time14" />
										</logic:notMatch></td>
									<td><logic:match name="reportListId" property="time15"
											value=" ">
											<logic:match name="reportListId" property="time15" value="  ">

												<logic:match name="reportListId" property="time15"
													value="   ">
													<font color="#228b22"> <bean:write
															name="reportListId" property="time15" /></font>
												</logic:match>
												<logic:notMatch name="reportListId" property="time15"
													value="   ">
													<font color="#0000CD"> <bean:write
															name="reportListId" property="time15" /></font>
												</logic:notMatch>




											</logic:match>

											<logic:notMatch name="reportListId" property="time15"
												value="  ">
												<font color="#FF0000"> <bean:write
														name="reportListId" property="time15" /></font>
											</logic:notMatch>

										</logic:match> <logic:notMatch name="reportListId" property="time15"
											value=" ">
											<bean:write name="reportListId" property="time15" />
										</logic:notMatch></td>
									<td><logic:match name="reportListId" property="time16"
											value=" ">
											<logic:match name="reportListId" property="time16" value="  ">

												<logic:match name="reportListId" property="time16"
													value="   ">
													<font color="#228b22"> <bean:write
															name="reportListId" property="time16" /></font>
												</logic:match>
												<logic:notMatch name="reportListId" property="time16"
													value="   ">
													<font color="#0000CD"> <bean:write
															name="reportListId" property="time16" /></font>
												</logic:notMatch>




											</logic:match>

											<logic:notMatch name="reportListId" property="time16"
												value="  ">
												<font color="#FF0000"> <bean:write
														name="reportListId" property="time16" /></font>
											</logic:notMatch>

										</logic:match> <logic:notMatch name="reportListId" property="time16"
											value=" ">
											<bean:write name="reportListId" property="time16" />
										</logic:notMatch></td>
									<td><logic:match name="reportListId" property="time17"
											value=" ">
											<logic:match name="reportListId" property="time17" value="  ">

												<logic:match name="reportListId" property="time17"
													value="   ">
													<font color="#228b22"> <bean:write
															name="reportListId" property="time17" /></font>
												</logic:match>
												<logic:notMatch name="reportListId" property="time17"
													value="   ">
													<font color="#0000CD"> <bean:write
															name="reportListId" property="time17" /></font>
												</logic:notMatch>



											</logic:match>

											<logic:notMatch name="reportListId" property="time17"
												value="  ">
												<font color="#FF0000"> <bean:write
														name="reportListId" property="time17" /></font>
											</logic:notMatch>

										</logic:match> <logic:notMatch name="reportListId" property="time17"
											value=" ">
											<bean:write name="reportListId" property="time17" />
										</logic:notMatch></td>
									<td><logic:match name="reportListId" property="time18"
											value=" ">
											<logic:match name="reportListId" property="time18" value="  ">

												<logic:match name="reportListId" property="time18"
													value="   ">
													<font color="#228b22"> <bean:write
															name="reportListId" property="time18" /></font>
												</logic:match>
												<logic:notMatch name="reportListId" property="time18"
													value="   ">
													<font color="#0000CD"> <bean:write
															name="reportListId" property="time18" /></font>
												</logic:notMatch>



											</logic:match>

											<logic:notMatch name="reportListId" property="time18"
												value="  ">
												<font color="#FF0000"> <bean:write
														name="reportListId" property="time18" /></font>
											</logic:notMatch>

										</logic:match> <logic:notMatch name="reportListId" property="time18"
											value=" ">
											<bean:write name="reportListId" property="time18" />
										</logic:notMatch></td>
									<td><logic:match name="reportListId" property="time19"
											value=" ">
											<logic:match name="reportListId" property="time19" value="  ">

												<logic:match name="reportListId" property="time19"
													value="   ">
													<font color="#228b22"> <bean:write
															name="reportListId" property="time19" /></font>
												</logic:match>
												<logic:notMatch name="reportListId" property="time19"
													value="   ">
													<font color="#0000CD"> <bean:write
															name="reportListId" property="time19" /></font>
												</logic:notMatch>


											</logic:match>

											<logic:notMatch name="reportListId" property="time19"
												value="  ">
												<font color="#FF0000"> <bean:write
														name="reportListId" property="time19" /></font>
											</logic:notMatch>

										</logic:match> <logic:notMatch name="reportListId" property="time19"
											value=" ">
											<bean:write name="reportListId" property="time19" />
										</logic:notMatch></td>
									<td><logic:match name="reportListId" property="time20"
											value=" ">
											<logic:match name="reportListId" property="time20" value="  ">


												<logic:match name="reportListId" property="time20"
													value="   ">
													<font color="#228b22"> <bean:write
															name="reportListId" property="time20" /></font>
												</logic:match>
												<logic:notMatch name="reportListId" property="time20"
													value="   ">
													<font color="#0000CD"> <bean:write
															name="reportListId" property="time20" /></font>
												</logic:notMatch>



											</logic:match>

											<logic:notMatch name="reportListId" property="time20"
												value="  ">
												<font color="#FF0000"> <bean:write
														name="reportListId" property="time20" /></font>
											</logic:notMatch>
										</logic:match> <logic:notMatch name="reportListId" property="time20"
											value=" ">
											<bean:write name="reportListId" property="time20" />
										</logic:notMatch></td>
									<td><logic:match name="reportListId" property="time21"
											value=" ">
											<logic:match name="reportListId" property="time21" value="  ">

												<logic:match name="reportListId" property="time21"
													value="   ">
													<font color="#228b22"> <bean:write
															name="reportListId" property="time21" /></font>
												</logic:match>
												<logic:notMatch name="reportListId" property="time21"
													value="   ">
													<font color="#0000CD"> <bean:write
															name="reportListId" property="time21" /></font>
												</logic:notMatch>



											</logic:match>

											<logic:notMatch name="reportListId" property="time21"
												value="  ">
												<font color="#FF0000"> <bean:write
														name="reportListId" property="time21" /></font>
											</logic:notMatch>

										</logic:match> <logic:notMatch name="reportListId" property="time21"
											value=" ">
											<bean:write name="reportListId" property="time21" />
										</logic:notMatch></td>
									<td><logic:match name="reportListId" property="time22"
											value=" ">
											<logic:match name="reportListId" property="time22" value="  ">

												<logic:match name="reportListId" property="time22"
													value="   ">
													<font color="#228b22"> <bean:write
															name="reportListId" property="time22" /></font>
												</logic:match>
												<logic:notMatch name="reportListId" property="time22"
													value="   ">
													<font color="#0000CD"> <bean:write
															name="reportListId" property="time22" /></font>
												</logic:notMatch>



											</logic:match>

											<logic:notMatch name="reportListId" property="time22"
												value="  ">
												<font color="#FF0000"> <bean:write
														name="reportListId" property="time22" /></font>
											</logic:notMatch>

										</logic:match> <logic:notMatch name="reportListId" property="time22"
											value=" ">
											<bean:write name="reportListId" property="time22" />
										</logic:notMatch></td>
									<td><logic:match name="reportListId" property="time23"
											value=" ">

											<logic:match name="reportListId" property="time23" value="  ">


												<logic:match name="reportListId" property="time23"
													value="   ">
													<font color="#228b22"> <bean:write
															name="reportListId" property="time23" /></font>
												</logic:match>
												<logic:notMatch name="reportListId" property="time23"
													value="   ">
													<font color="#0000CD"> <bean:write
															name="reportListId" property="time23" /></font>
												</logic:notMatch>



											</logic:match>

											<logic:notMatch name="reportListId" property="time23"
												value="  ">
												<font color="#FF0000"> <bean:write
														name="reportListId" property="time23" /></font>
											</logic:notMatch>

										</logic:match> <logic:notMatch name="reportListId" property="time23"
											value=" ">
											<bean:write name="reportListId" property="time23" />
										</logic:notMatch></td>

									<td><logic:match name="reportListId" property="time24"
											value=" ">
											<logic:match name="reportListId" property="time24" value="  ">


												<logic:match name="reportListId" property="time24"
													value="   ">
													<font color="#228b22"> <bean:write
															name="reportListId" property="time24" /></font>
												</logic:match>
												<logic:notMatch name="reportListId" property="time24"
													value="   ">
													<font color="#0000CD"> <bean:write
															name="reportListId" property="time24" /></font>
												</logic:notMatch>



											</logic:match>

											<logic:notMatch name="reportListId" property="time24"
												value="  ">
												<font color="#FF0000"> <bean:write
														name="reportListId" property="time24" /></font>
											</logic:notMatch>

										</logic:match> <logic:notMatch name="reportListId" property="time24"
											value=" ">
											<bean:write name="reportListId" property="time24" />
										</logic:notMatch></td>
									<td><logic:match name="reportListId" property="time25"
											value=" ">
											<logic:match name="reportListId" property="time25" value="  ">

												<logic:match name="reportListId" property="time25"
													value="   ">
													<font color="#228b22"> <bean:write
															name="reportListId" property="time25" /></font>
												</logic:match>
												<logic:notMatch name="reportListId" property="time25"
													value="   ">
													<font color="#0000CD"> <bean:write
															name="reportListId" property="time25" /></font>
												</logic:notMatch>


											</logic:match>

											<logic:notMatch name="reportListId" property="time25"
												value="  ">
												<font color="#FF0000"> <bean:write
														name="reportListId" property="time25" /></font>
											</logic:notMatch>

										</logic:match> <logic:notMatch name="reportListId" property="time25"
											value=" ">
											<bean:write name="reportListId" property="time25" />
										</logic:notMatch></td>
									<td><logic:match name="reportListId" property="time26"
											value=" ">
											<logic:match name="reportListId" property="time26" value="  ">

												<logic:match name="reportListId" property="time26"
													value="   ">
													<font color="#228b22"> <bean:write
															name="reportListId" property="time26" /></font>
												</logic:match>
												<logic:notMatch name="reportListId" property="time26"
													value="   ">
													<font color="#0000CD"> <bean:write
															name="reportListId" property="time26" /></font>
												</logic:notMatch>



											</logic:match>

											<logic:notMatch name="reportListId" property="time26"
												value="  ">
												<font color="#FF0000"> <bean:write
														name="reportListId" property="time26" /></font>
											</logic:notMatch>

										</logic:match> <logic:notMatch name="reportListId" property="time26"
											value=" ">
											<bean:write name="reportListId" property="time26" />
										</logic:notMatch></td>
									<td><logic:match name="reportListId" property="time27"
											value=" ">
											<logic:match name="reportListId" property="time27" value="  ">

												<logic:match name="reportListId" property="time27"
													value="   ">
													<font color="#228b22"> <bean:write
															name="reportListId" property="time27" /></font>
												</logic:match>
												<logic:notMatch name="reportListId" property="time27"
													value="   ">
													<font color="#0000CD"> <bean:write
															name="reportListId" property="time27" /></font>
												</logic:notMatch>
											</logic:match>

											<logic:notMatch name="reportListId" property="time27"
												value="  ">
												<font color="#FF0000"> <bean:write
														name="reportListId" property="time27" /></font>
											</logic:notMatch>

										</logic:match> <logic:notMatch name="reportListId" property="time27"
											value=" ">
											<bean:write name="reportListId" property="time27" />
										</logic:notMatch></td>
									<%
										if (dCount == 28) {
									%>
									<td><logic:match name="reportListId" property="time28"
											value=" ">
											<logic:match name="reportListId" property="time28" value="  ">

												<logic:match name="reportListId" property="time28"
													value="   ">
													<font color="#228b22"> <bean:write
															name="reportListId" property="time28" /></font>
												</logic:match>
												<logic:notMatch name="reportListId" property="time28"
													value="   ">
													<font color="#0000CD"> <bean:write
															name="reportListId" property="time28" /></font>
												</logic:notMatch>
											</logic:match>

											<logic:notMatch name="reportListId" property="time28"
												value="  ">
												<font color="#FF0000"> <bean:write
														name="reportListId" property="time28" /></font>
											</logic:notMatch>

										</logic:match> <logic:notMatch name="reportListId" property="time28"
											value=" ">
											<bean:write name="reportListId" property="time28" />
										</logic:notMatch></td>
									<%
										} else if (dCount == 29) {
									%>
									<td><logic:match name="reportListId" property="time28"
											value=" ">
											<logic:match name="reportListId" property="time28" value="  ">

												<logic:match name="reportListId" property="time28"
													value="   ">
													<font color="#228b22"> <bean:write
															name="reportListId" property="time28" /></font>
												</logic:match>
												<logic:notMatch name="reportListId" property="time28"
													value="   ">
													<font color="#0000CD"> <bean:write
															name="reportListId" property="time28" /></font>
												</logic:notMatch>
											</logic:match>

											<logic:notMatch name="reportListId" property="time28"
												value="  ">
												<font color="#FF0000"> <bean:write
														name="reportListId" property="time28" /></font>
											</logic:notMatch>

										</logic:match> <logic:notMatch name="reportListId" property="time28"
											value=" ">
											<bean:write name="reportListId" property="time28" />
										</logic:notMatch></td>
									<td><logic:match name="reportListId" property="time29"
											value=" ">
											<logic:match name="reportListId" property="time29" value="  ">


												<logic:match name="reportListId" property="time29"
													value="   ">
													<font color="#228b22"> <bean:write
															name="reportListId" property="time29" /></font>
												</logic:match>
												<logic:notMatch name="reportListId" property="time29"
													value="   ">
													<font color="#0000CD"> <bean:write
															name="reportListId" property="time29" /></font>
												</logic:notMatch>
											</logic:match>

											<logic:notMatch name="reportListId" property="time29"
												value="  ">
												<font color="#FF0000"> <bean:write
														name="reportListId" property="time29" /></font>
											</logic:notMatch>

										</logic:match> <logic:notMatch name="reportListId" property="time29"
											value=" ">
											<bean:write name="reportListId" property="time29" />
										</logic:notMatch></td>
									<%
										} else if (dCount == 30) {
									%>
									<td><logic:match name="reportListId" property="time28"
											value=" ">
											<logic:match name="reportListId" property="time28" value="  ">

												<logic:match name="reportListId" property="time28"
													value="   ">
													<font color="#228b22"> <bean:write
															name="reportListId" property="time28" /></font>
												</logic:match>
												<logic:notMatch name="reportListId" property="time28"
													value="   ">
													<font color="#0000CD"> <bean:write
															name="reportListId" property="time28" /></font>
												</logic:notMatch>
											</logic:match>

											<logic:notMatch name="reportListId" property="time28"
												value="  ">
												<font color="#FF0000"> <bean:write
														name="reportListId" property="time28" /></font>
											</logic:notMatch>

										</logic:match> <logic:notMatch name="reportListId" property="time28"
											value=" ">
											<bean:write name="reportListId" property="time28" />
										</logic:notMatch></td>
									<td><logic:match name="reportListId" property="time29"
											value=" ">
											<logic:match name="reportListId" property="time29" value="  ">


												<logic:match name="reportListId" property="time29"
													value="   ">
													<font color="#228b22"> <bean:write
															name="reportListId" property="time29" /></font>
												</logic:match>
												<logic:notMatch name="reportListId" property="time29"
													value="   ">
													<font color="#0000CD"> <bean:write
															name="reportListId" property="time29" /></font>
												</logic:notMatch>
											</logic:match>

											<logic:notMatch name="reportListId" property="time29"
												value="  ">
												<font color="#FF0000"> <bean:write
														name="reportListId" property="time29" /></font>
											</logic:notMatch>

										</logic:match> <logic:notMatch name="reportListId" property="time29"
											value=" ">
											<bean:write name="reportListId" property="time29" />
										</logic:notMatch></td>
									<td><logic:match name="reportListId" property="time30"
											value=" ">
											<logic:match name="reportListId" property="time30" value="  ">

												<logic:match name="reportListId" property="time30"
													value="   ">
													<font color="#228b22"> <bean:write
															name="reportListId" property="time30" /></font>
												</logic:match>
												<logic:notMatch name="reportListId" property="time30"
													value="   ">
													<font color="#0000CD"> <bean:write
															name="reportListId" property="time30" /></font>
												</logic:notMatch>


											</logic:match>

											<logic:notMatch name="reportListId" property="time30"
												value="  ">
												<font color="#FF0000"> <bean:write
														name="reportListId" property="time30" /></font>
											</logic:notMatch>

										</logic:match> <logic:notMatch name="reportListId" property="time30"
											value=" ">
											<bean:write name="reportListId" property="time30" />
										</logic:notMatch></td>
									<%
										} else {
									%>
									<td><logic:match name="reportListId" property="time28"
											value=" ">
											<logic:match name="reportListId" property="time28" value="  ">

												<logic:match name="reportListId" property="time28"
													value="   ">
													<font color="#228b22"> <bean:write
															name="reportListId" property="time28" /></font>
												</logic:match>
												<logic:notMatch name="reportListId" property="time28"
													value="   ">
													<font color="#0000CD"> <bean:write
															name="reportListId" property="time28" /></font>
												</logic:notMatch>
											</logic:match>

											<logic:notMatch name="reportListId" property="time28"
												value="  ">
												<font color="#FF0000"> <bean:write
														name="reportListId" property="time28" /></font>
											</logic:notMatch>

										</logic:match> <logic:notMatch name="reportListId" property="time28"
											value=" ">
											<bean:write name="reportListId" property="time28" />
										</logic:notMatch></td>
									<td><logic:match name="reportListId" property="time29"
											value=" ">
											<logic:match name="reportListId" property="time29" value="  ">


												<logic:match name="reportListId" property="time29"
													value="   ">
													<font color="#228b22"> <bean:write
															name="reportListId" property="time29" /></font>
												</logic:match>
												<logic:notMatch name="reportListId" property="time29"
													value="   ">
													<font color="#0000CD"> <bean:write
															name="reportListId" property="time29" /></font>
												</logic:notMatch>
											</logic:match>

											<logic:notMatch name="reportListId" property="time29"
												value="  ">
												<font color="#FF0000"> <bean:write
														name="reportListId" property="time29" /></font>
											</logic:notMatch>

										</logic:match> <logic:notMatch name="reportListId" property="time29"
											value=" ">
											<bean:write name="reportListId" property="time29" />
										</logic:notMatch></td>
									<td><logic:match name="reportListId" property="time30"
											value=" ">
											<logic:match name="reportListId" property="time30" value="  ">

												<logic:match name="reportListId" property="time30"
													value="   ">
													<font color="#228b22"> <bean:write
															name="reportListId" property="time30" /></font>
												</logic:match>
												<logic:notMatch name="reportListId" property="time30"
													value="   ">
													<font color="#0000CD"> <bean:write
															name="reportListId" property="time30" /></font>
												</logic:notMatch>


											</logic:match>

											<logic:notMatch name="reportListId" property="time30"
												value="  ">
												<font color="#FF0000"> <bean:write
														name="reportListId" property="time30" /></font>
											</logic:notMatch>

										</logic:match> <logic:notMatch name="reportListId" property="time30"
											value=" ">
											<bean:write name="reportListId" property="time30" />
										</logic:notMatch></td>
									<td><logic:match name="reportListId" property="time31"
											value=" ">
											<logic:match name="reportListId" property="time31" value="  ">
												<logic:match name="reportListId" property="time31"
													value="   ">
													<font color="#228b22"> <bean:write
															name="reportListId" property="time31" /></font>
												</logic:match>
												<logic:notMatch name="reportListId" property="time31"
													value="   ">
													<font color="#0000CD"> <bean:write
															name="reportListId" property="time31" /></font>
												</logic:notMatch>
											</logic:match>

											<logic:notMatch name="reportListId" property="time31"
												value="  ">
												<font color="#FF0000"> <bean:write
														name="reportListId" property="time31" /></font>
											</logic:notMatch>

										</logic:match> <logic:notMatch name="reportListId" property="time31"
											value=" ">
											<bean:write name="reportListId" property="time31" />
										</logic:notMatch></td>
									<%
										}
									%>
									<td><bean:write name="reportListId" property="totalTime" /></td>
								</tr>
							</logic:iterate>

						</logic:present>
					</table>
				</div>
				<script type="text/javascript">
					datatable_checker();
					fxheaderInit('dataTable1', 400, 2, 1);
					/*  fxheaderInit('dataTable2',200,2,1);
						 fxheaderInit('dataTable3',200,1,0); */
					fxheader();
				</script>
			</div>
			</logic:present>



			<!-- This is Exception DashBoard  -->

			<logic:notPresent name="reportList" scope="request">
				<logic:present name="reportListException" scope="request">
					<div style="text-align: right; width: 745px; margin-bottom: 1mm;">
						<a href="###."
							style="text-decoration: none; size: 10px; font-weight: bold;"
							onclick="submitReport('displayReport.do?method=generateReportExceptionDashBoard');">Export
							To Excel</a> <img src="img/getI.png" style="height: 20px;" />
					</div>
					<div style="margin-left: 2mm;">
						<table id="dataTable2" width="750" class="hovertableP">
							<tr>
								<th>&nbsp;Day</th>
								<%
									int day_count = 0;
								%>
								<logic:iterate id="dayList" name="dayList" scope="request">
									<logic:match value="Sat" name="dayList">
										<th style="background: LightPink;"><b><bean:write
													name="dayList" /></b></th>
									</logic:match>
									<logic:notMatch value="Sat" name="dayList">
										<logic:match value="Sun" name="dayList">
											<th style="background: LightPink;"><b><bean:write
														name="dayList" /></b></th>
										</logic:match>
										<logic:notMatch value="Sun" name="dayList">
											<th style="background: #A5A1A0;"><b> <bean:write
														name="dayList" /></b></th>
										</logic:notMatch>
									</logic:notMatch>
									<%
										day_count++;
									%>
								</logic:iterate>
							</tr>
							<tr>
								<th>&nbsp;Username&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
								<logic:iterate id="dVal" name="dayListForDates" scope="request">
									<logic:match value=" " name="dVal">
										<th style="background: LightPink;"><b> <bean:write
													name="dVal" />
										</b></th>
									</logic:match>
									<logic:notMatch value=" " name="dVal">
										<th style="background: #A5A1A0;"><b> <bean:write
													name="dVal" /></b></th>
									</logic:notMatch>
								</logic:iterate>
							</tr>
							<logic:present name="reportListException" scope="request">
								<logic:iterate id="reportListId" name="reportListException"
									scope="request">
									<tr>
										<td style="color: #000000; background-color: #EBEBEB;"><bean:write
												name="reportListId" property="userName" /></td>

										<td><logic:match name="reportListId" property="time1"
												value=" ">
												<logic:match name="reportListId" property="time1" value="  ">

													<logic:match name="reportListId" property="time1"
														value="   ">
														<font color="#228b22"> <bean:write
																name="reportListId" property="time1" /></font>
													</logic:match>
													<logic:notMatch name="reportListId" property="time1"
														value="   ">
														<font color="#0000CD"> <bean:write
																name="reportListId" property="time1" /></font>
													</logic:notMatch>

												</logic:match>

												<logic:notMatch name="reportListId" property="time1"
													value="  ">
													<font color="#FF0000"> <bean:write
															name="reportListId" property="time1" /></font>
												</logic:notMatch>
											</logic:match> <logic:notMatch name="reportListId" property="time1"
												value=" ">
												<bean:write name="reportListId" property="time1" />
											</logic:notMatch></td>
										<td><logic:match name="reportListId" property="time2"
												value=" ">
												<logic:match name="reportListId" property="time2" value="  ">


													<logic:match name="reportListId" property="time2"
														value="   ">
														<font color="#228b22"> <bean:write
																name="reportListId" property="time2" /></font>
													</logic:match>
													<logic:notMatch name="reportListId" property="time2"
														value="   ">
														<font color="#0000CD"> <bean:write
																name="reportListId" property="time2" /></font>
													</logic:notMatch>
												</logic:match>

												<logic:notMatch name="reportListId" property="time2"
													value="  ">
													<font color="#FF0000"> <bean:write
															name="reportListId" property="time2" /></font>
												</logic:notMatch>

											</logic:match> <logic:notMatch name="reportListId" property="time2"
												value=" ">
												<bean:write name="reportListId" property="time2" />
											</logic:notMatch></td>
										<td><logic:match name="reportListId" property="time3"
												value=" ">
												<logic:match name="reportListId" property="time3" value="  ">


													<logic:match name="reportListId" property="time3"
														value="   ">
														<font color="#228b22"> <bean:write
																name="reportListId" property="time3" /></font>
													</logic:match>
													<logic:notMatch name="reportListId" property="time3"
														value="   ">
														<font color="#0000CD"> <bean:write
																name="reportListId" property="time3" /></font>
													</logic:notMatch>
												</logic:match>
												<logic:notMatch name="reportListId" property="time3"
													value="  ">
													<font color="#FF0000"> <bean:write
															name="reportListId" property="time3" /></font>
												</logic:notMatch>
											</logic:match> <logic:notMatch name="reportListId" property="time3"
												value=" ">
												<bean:write name="reportListId" property="time3" />
											</logic:notMatch></td>
										<td><logic:match name="reportListId" property="time4"
												value=" ">
												<logic:match name="reportListId" property="time4" value="  ">
													<logic:match name="reportListId" property="time4"
														value="   ">
														<font color="#228b22"> <bean:write
																name="reportListId" property="time4" /></font>
													</logic:match>
													<logic:notMatch name="reportListId" property="time4"
														value="   ">
														<font color="#0000CD"> <bean:write
																name="reportListId" property="time4" /></font>
													</logic:notMatch>
												</logic:match>
												<logic:notMatch name="reportListId" property="time4"
													value="  ">
													<font color="#FF0000"> <bean:write
															name="reportListId" property="time4" /></font>
												</logic:notMatch>
											</logic:match> <logic:notMatch name="reportListId" property="time4"
												value=" ">
												<bean:write name="reportListId" property="time4" />
											</logic:notMatch></td>
										<td><logic:match name="reportListId" property="time5"
												value=" ">
												<logic:match name="reportListId" property="time5" value="  ">

													<logic:match name="reportListId" property="time5"
														value="   ">
														<font color="#228b22"> <bean:write
																name="reportListId" property="time5" /></font>
													</logic:match>
													<logic:notMatch name="reportListId" property="time5"
														value="   ">
														<font color="#0000CD"> <bean:write
																name="reportListId" property="time5" /></font>
													</logic:notMatch>
												</logic:match>
												<logic:notMatch name="reportListId" property="time5"
													value="  ">
													<font color="#FF0000"> <bean:write
															name="reportListId" property="time5" /></font>
												</logic:notMatch>
											</logic:match> <logic:notMatch name="reportListId" property="time5"
												value=" ">
												<bean:write name="reportListId" property="time5" />
											</logic:notMatch></td>
										<td><logic:match name="reportListId" property="time6"
												value=" ">
												<logic:match name="reportListId" property="time6" value="  ">

													<logic:match name="reportListId" property="time6"
														value="   ">
														<font color="#228b22"> <bean:write
																name="reportListId" property="time6" /></font>
													</logic:match>
													<logic:notMatch name="reportListId" property="time6"
														value="   ">
														<font color="#0000CD"> <bean:write
																name="reportListId" property="time6" /></font>
													</logic:notMatch>
												</logic:match>
												<logic:notMatch name="reportListId" property="time6"
													value="  ">
													<font color="#FF0000"> <bean:write
															name="reportListId" property="time6" /></font>
												</logic:notMatch>
											</logic:match> <logic:notMatch name="reportListId" property="time6"
												value=" ">
												<bean:write name="reportListId" property="time6" />
											</logic:notMatch></td>
										<td><logic:match name="reportListId" property="time7"
												value=" ">
												<logic:match name="reportListId" property="time7" value="  ">
													<logic:match name="reportListId" property="time7"
														value="   ">
														<font color="#228b22"> <bean:write
																name="reportListId" property="time7" /></font>
													</logic:match>
													<logic:notMatch name="reportListId" property="time7"
														value="   ">
														<font color="#0000CD"> <bean:write
																name="reportListId" property="time7" /></font>
													</logic:notMatch>
												</logic:match>
												<logic:notMatch name="reportListId" property="time7"
													value="  ">
													<font color="#FF0000"> <bean:write
															name="reportListId" property="time7" /></font>
												</logic:notMatch>
											</logic:match> <logic:notMatch name="reportListId" property="time7"
												value=" ">
												<bean:write name="reportListId" property="time7" />
											</logic:notMatch></td>
										<td><logic:match name="reportListId" property="time8"
												value=" ">
												<logic:match name="reportListId" property="time8" value="  ">
													<logic:match name="reportListId" property="time8"
														value="   ">
														<font color="#228b22"> <bean:write
																name="reportListId" property="time8" /></font>
													</logic:match>
													<logic:notMatch name="reportListId" property="time8"
														value="   ">
														<font color="#0000CD"> <bean:write
																name="reportListId" property="time8" /></font>
													</logic:notMatch>
												</logic:match>
												<logic:notMatch name="reportListId" property="time8"
													value="  ">
													<font color="#FF0000"> <bean:write
															name="reportListId" property="time8" /></font>
												</logic:notMatch>
											</logic:match> <logic:notMatch name="reportListId" property="time8"
												value=" ">
												<bean:write name="reportListId" property="time8" />
											</logic:notMatch></td>
										<td><logic:match name="reportListId" property="time9"
												value=" ">
												<logic:match name="reportListId" property="time9" value="  ">
													<logic:match name="reportListId" property="time9"
														value="   ">
														<font color="#228b22"> <bean:write
																name="reportListId" property="time9" /></font>
													</logic:match>
													<logic:notMatch name="reportListId" property="time9"
														value="   ">
														<font color="#0000CD"> <bean:write
																name="reportListId" property="time9" /></font>
													</logic:notMatch>
												</logic:match>
												<logic:notMatch name="reportListId" property="time9"
													value="  ">
													<font color="#FF0000"> <bean:write
															name="reportListId" property="time9" /></font>
												</logic:notMatch>
											</logic:match> <logic:notMatch name="reportListId" property="time9"
												value=" ">
												<bean:write name="reportListId" property="time9" />
											</logic:notMatch></td>
										<td><logic:match name="reportListId" property="time10"
												value=" ">
												<logic:match name="reportListId" property="time10"
													value="  ">

													<logic:match name="reportListId" property="time10"
														value="   ">
														<font color="#228b22"> <bean:write
																name="reportListId" property="time10" /></font>
													</logic:match>
													<logic:notMatch name="reportListId" property="time10"
														value="   ">
														<font color="#0000CD"> <bean:write
																name="reportListId" property="time10" /></font>
													</logic:notMatch>
												</logic:match>
												<logic:notMatch name="reportListId" property="time10"
													value="  ">
													<font color="#FF0000"> <bean:write
															name="reportListId" property="time10" /></font>
												</logic:notMatch>
											</logic:match> <logic:notMatch name="reportListId" property="time10"
												value=" ">
												<bean:write name="reportListId" property="time10" />
											</logic:notMatch></td>
										<td><logic:match name="reportListId" property="time11"
												value=" ">
												<logic:match name="reportListId" property="time11"
													value="  ">
													<logic:match name="reportListId" property="time11"
														value="   ">
														<font color="#228b22"> <bean:write
																name="reportListId" property="time11" /></font>
													</logic:match>
													<logic:notMatch name="reportListId" property="time11"
														value="   ">
														<font color="#0000CD"> <bean:write
																name="reportListId" property="time11" /></font>
													</logic:notMatch>
												</logic:match>
												<logic:notMatch name="reportListId" property="time11"
													value="  ">
													<font color="#FF0000"> <bean:write
															name="reportListId" property="time11" /></font>
												</logic:notMatch>

											</logic:match> <logic:notMatch name="reportListId" property="time11"
												value=" ">
												<bean:write name="reportListId" property="time11" />
											</logic:notMatch></td>
										<td><logic:match name="reportListId" property="time12"
												value=" ">
												<logic:match name="reportListId" property="time12"
													value="  ">

													<logic:match name="reportListId" property="time12"
														value="   ">
														<font color="#228b22"> <bean:write
																name="reportListId" property="time12" /></font>
													</logic:match>
													<logic:notMatch name="reportListId" property="time12"
														value="   ">
														<font color="#0000CD"> <bean:write
																name="reportListId" property="time12" /></font>
													</logic:notMatch>
												</logic:match>
												<logic:notMatch name="reportListId" property="time12"
													value="  ">
													<font color="#FF0000"> <bean:write
															name="reportListId" property="time12" /></font>
												</logic:notMatch>
											</logic:match> <logic:notMatch name="reportListId" property="time12"
												value=" ">
												<bean:write name="reportListId" property="time12" />
											</logic:notMatch></td>
										<td><logic:match name="reportListId" property="time13"
												value=" ">
												<logic:match name="reportListId" property="time13"
													value="  ">
													<logic:match name="reportListId" property="time13"
														value="   ">
														<font color="#228b22"> <bean:write
																name="reportListId" property="time13" /></font>
													</logic:match>
													<logic:notMatch name="reportListId" property="time13"
														value="   ">
														<font color="#0000CD"> <bean:write
																name="reportListId" property="time13" /></font>
													</logic:notMatch>
												</logic:match>
												<logic:notMatch name="reportListId" property="time13"
													value="  ">
													<font color="#FF0000"> <bean:write
															name="reportListId" property="time13" /></font>
												</logic:notMatch>
											</logic:match> <logic:notMatch name="reportListId" property="time13"
												value=" ">
												<bean:write name="reportListId" property="time13" />
											</logic:notMatch></td>
										<td><logic:match name="reportListId" property="time14"
												value=" ">
												<logic:match name="reportListId" property="time14"
													value="  ">

													<logic:match name="reportListId" property="time14"
														value="   ">
														<font color="#228b22"> <bean:write
																name="reportListId" property="time14" /></font>
													</logic:match>
													<logic:notMatch name="reportListId" property="time14"
														value="   ">
														<font color="#0000CD"> <bean:write
																name="reportListId" property="time14" /></font>
													</logic:notMatch>
												</logic:match>
												<logic:notMatch name="reportListId" property="time14"
													value="  ">
													<font color="#FF0000"> <bean:write
															name="reportListId" property="time14" /></font>
												</logic:notMatch>

											</logic:match> <logic:notMatch name="reportListId" property="time14"
												value=" ">
												<bean:write name="reportListId" property="time14" />
											</logic:notMatch></td>
										<td><logic:match name="reportListId" property="time15"
												value=" ">
												<logic:match name="reportListId" property="time15"
													value="  ">

													<logic:match name="reportListId" property="time15"
														value="   ">
														<font color="#228b22"> <bean:write
																name="reportListId" property="time15" /></font>
													</logic:match>
													<logic:notMatch name="reportListId" property="time15"
														value="   ">
														<font color="#0000CD"> <bean:write
																name="reportListId" property="time15" /></font>
													</logic:notMatch>
												</logic:match>

												<logic:notMatch name="reportListId" property="time15"
													value="  ">
													<font color="#FF0000"> <bean:write
															name="reportListId" property="time15" /></font>
												</logic:notMatch>

											</logic:match> <logic:notMatch name="reportListId" property="time15"
												value=" ">
												<bean:write name="reportListId" property="time15" />
											</logic:notMatch></td>
										<td><logic:match name="reportListId" property="time16"
												value=" ">
												<logic:match name="reportListId" property="time16"
													value="  ">

													<logic:match name="reportListId" property="time16"
														value="   ">
														<font color="#228b22"> <bean:write
																name="reportListId" property="time16" /></font>
													</logic:match>
													<logic:notMatch name="reportListId" property="time16"
														value="   ">
														<font color="#0000CD"> <bean:write
																name="reportListId" property="time16" /></font>
													</logic:notMatch>




												</logic:match>

												<logic:notMatch name="reportListId" property="time16"
													value="  ">
													<font color="#FF0000"> <bean:write
															name="reportListId" property="time16" /></font>
												</logic:notMatch>

											</logic:match> <logic:notMatch name="reportListId" property="time16"
												value=" ">
												<bean:write name="reportListId" property="time16" />
											</logic:notMatch></td>
										<td><logic:match name="reportListId" property="time17"
												value=" ">
												<logic:match name="reportListId" property="time17"
													value="  ">

													<logic:match name="reportListId" property="time17"
														value="   ">
														<font color="#228b22"> <bean:write
																name="reportListId" property="time17" /></font>
													</logic:match>
													<logic:notMatch name="reportListId" property="time17"
														value="   ">
														<font color="#0000CD"> <bean:write
																name="reportListId" property="time17" /></font>
													</logic:notMatch>



												</logic:match>

												<logic:notMatch name="reportListId" property="time17"
													value="  ">
													<font color="#FF0000"> <bean:write
															name="reportListId" property="time17" /></font>
												</logic:notMatch>

											</logic:match> <logic:notMatch name="reportListId" property="time17"
												value=" ">
												<bean:write name="reportListId" property="time17" />
											</logic:notMatch></td>
										<td><logic:match name="reportListId" property="time18"
												value=" ">
												<logic:match name="reportListId" property="time18"
													value="  ">

													<logic:match name="reportListId" property="time18"
														value="   ">
														<font color="#228b22"> <bean:write
																name="reportListId" property="time18" /></font>
													</logic:match>
													<logic:notMatch name="reportListId" property="time18"
														value="   ">
														<font color="#0000CD"> <bean:write
																name="reportListId" property="time18" /></font>
													</logic:notMatch>



												</logic:match>

												<logic:notMatch name="reportListId" property="time18"
													value="  ">
													<font color="#FF0000"> <bean:write
															name="reportListId" property="time18" /></font>
												</logic:notMatch>

											</logic:match> <logic:notMatch name="reportListId" property="time18"
												value=" ">
												<bean:write name="reportListId" property="time18" />
											</logic:notMatch></td>
										<td><logic:match name="reportListId" property="time19"
												value=" ">
												<logic:match name="reportListId" property="time19"
													value="  ">

													<logic:match name="reportListId" property="time19"
														value="   ">
														<font color="#228b22"> <bean:write
																name="reportListId" property="time19" /></font>
													</logic:match>
													<logic:notMatch name="reportListId" property="time19"
														value="   ">
														<font color="#0000CD"> <bean:write
																name="reportListId" property="time19" /></font>
													</logic:notMatch>


												</logic:match>

												<logic:notMatch name="reportListId" property="time19"
													value="  ">
													<font color="#FF0000"> <bean:write
															name="reportListId" property="time19" /></font>
												</logic:notMatch>

											</logic:match> <logic:notMatch name="reportListId" property="time19"
												value=" ">
												<bean:write name="reportListId" property="time19" />
											</logic:notMatch></td>
										<td><logic:match name="reportListId" property="time20"
												value=" ">
												<logic:match name="reportListId" property="time20"
													value="  ">


													<logic:match name="reportListId" property="time20"
														value="   ">
														<font color="#228b22"> <bean:write
																name="reportListId" property="time20" /></font>
													</logic:match>
													<logic:notMatch name="reportListId" property="time20"
														value="   ">
														<font color="#0000CD"> <bean:write
																name="reportListId" property="time20" /></font>
													</logic:notMatch>



												</logic:match>

												<logic:notMatch name="reportListId" property="time20"
													value="  ">
													<font color="#FF0000"> <bean:write
															name="reportListId" property="time20" /></font>
												</logic:notMatch>
											</logic:match> <logic:notMatch name="reportListId" property="time20"
												value=" ">
												<bean:write name="reportListId" property="time20" />
											</logic:notMatch></td>
										<td><logic:match name="reportListId" property="time21"
												value=" ">
												<logic:match name="reportListId" property="time21"
													value="  ">

													<logic:match name="reportListId" property="time21"
														value="   ">
														<font color="#228b22"> <bean:write
																name="reportListId" property="time21" /></font>
													</logic:match>
													<logic:notMatch name="reportListId" property="time21"
														value="   ">
														<font color="#0000CD"> <bean:write
																name="reportListId" property="time21" /></font>
													</logic:notMatch>



												</logic:match>

												<logic:notMatch name="reportListId" property="time21"
													value="  ">
													<font color="#FF0000"> <bean:write
															name="reportListId" property="time21" /></font>
												</logic:notMatch>

											</logic:match> <logic:notMatch name="reportListId" property="time21"
												value=" ">
												<bean:write name="reportListId" property="time21" />
											</logic:notMatch></td>
										<td><logic:match name="reportListId" property="time22"
												value=" ">
												<logic:match name="reportListId" property="time22"
													value="  ">

													<logic:match name="reportListId" property="time22"
														value="   ">
														<font color="#228b22"> <bean:write
																name="reportListId" property="time22" /></font>
													</logic:match>
													<logic:notMatch name="reportListId" property="time22"
														value="   ">
														<font color="#0000CD"> <bean:write
																name="reportListId" property="time22" /></font>
													</logic:notMatch>



												</logic:match>

												<logic:notMatch name="reportListId" property="time22"
													value="  ">
													<font color="#FF0000"> <bean:write
															name="reportListId" property="time22" /></font>
												</logic:notMatch>

											</logic:match> <logic:notMatch name="reportListId" property="time22"
												value=" ">
												<bean:write name="reportListId" property="time22" />
											</logic:notMatch></td>
										<td><logic:match name="reportListId" property="time23"
												value=" ">

												<logic:match name="reportListId" property="time23"
													value="  ">


													<logic:match name="reportListId" property="time23"
														value="   ">
														<font color="#228b22"> <bean:write
																name="reportListId" property="time23" /></font>
													</logic:match>
													<logic:notMatch name="reportListId" property="time23"
														value="   ">
														<font color="#0000CD"> <bean:write
																name="reportListId" property="time23" /></font>
													</logic:notMatch>



												</logic:match>

												<logic:notMatch name="reportListId" property="time23"
													value="  ">
													<font color="#FF0000"> <bean:write
															name="reportListId" property="time23" /></font>
												</logic:notMatch>

											</logic:match> <logic:notMatch name="reportListId" property="time23"
												value=" ">
												<bean:write name="reportListId" property="time23" />
											</logic:notMatch></td>

										<td><logic:match name="reportListId" property="time24"
												value=" ">
												<logic:match name="reportListId" property="time24"
													value="  ">


													<logic:match name="reportListId" property="time24"
														value="   ">
														<font color="#228b22"> <bean:write
																name="reportListId" property="time24" /></font>
													</logic:match>
													<logic:notMatch name="reportListId" property="time24"
														value="   ">
														<font color="#0000CD"> <bean:write
																name="reportListId" property="time24" /></font>
													</logic:notMatch>



												</logic:match>

												<logic:notMatch name="reportListId" property="time24"
													value="  ">
													<font color="#FF0000"> <bean:write
															name="reportListId" property="time24" /></font>
												</logic:notMatch>

											</logic:match> <logic:notMatch name="reportListId" property="time24"
												value=" ">
												<bean:write name="reportListId" property="time24" />
											</logic:notMatch></td>
										<td><logic:match name="reportListId" property="time25"
												value=" ">
												<logic:match name="reportListId" property="time25"
													value="  ">

													<logic:match name="reportListId" property="time25"
														value="   ">
														<font color="#228b22"> <bean:write
																name="reportListId" property="time25" /></font>
													</logic:match>
													<logic:notMatch name="reportListId" property="time25"
														value="   ">
														<font color="#0000CD"> <bean:write
																name="reportListId" property="time25" /></font>
													</logic:notMatch>


												</logic:match>

												<logic:notMatch name="reportListId" property="time25"
													value="  ">
													<font color="#FF0000"> <bean:write
															name="reportListId" property="time25" /></font>
												</logic:notMatch>

											</logic:match> <logic:notMatch name="reportListId" property="time25"
												value=" ">
												<bean:write name="reportListId" property="time25" />
											</logic:notMatch></td>
										<td><logic:match name="reportListId" property="time26"
												value=" ">
												<logic:match name="reportListId" property="time26"
													value="  ">

													<logic:match name="reportListId" property="time26"
														value="   ">
														<font color="#228b22"> <bean:write
																name="reportListId" property="time26" /></font>
													</logic:match>
													<logic:notMatch name="reportListId" property="time26"
														value="   ">
														<font color="#0000CD"> <bean:write
																name="reportListId" property="time26" /></font>
													</logic:notMatch>



												</logic:match>

												<logic:notMatch name="reportListId" property="time26"
													value="  ">
													<font color="#FF0000"> <bean:write
															name="reportListId" property="time26" /></font>
												</logic:notMatch>

											</logic:match> <logic:notMatch name="reportListId" property="time26"
												value=" ">
												<bean:write name="reportListId" property="time26" />
											</logic:notMatch></td>
										<td><logic:match name="reportListId" property="time27"
												value=" ">
												<logic:match name="reportListId" property="time27"
													value="  ">

													<logic:match name="reportListId" property="time27"
														value="   ">
														<font color="#228b22"> <bean:write
																name="reportListId" property="time27" /></font>
													</logic:match>
													<logic:notMatch name="reportListId" property="time27"
														value="   ">
														<font color="#0000CD"> <bean:write
																name="reportListId" property="time27" /></font>
													</logic:notMatch>
												</logic:match>

												<logic:notMatch name="reportListId" property="time27"
													value="  ">
													<font color="#FF0000"> <bean:write
															name="reportListId" property="time27" /></font>
												</logic:notMatch>

											</logic:match> <logic:notMatch name="reportListId" property="time27"
												value=" ">
												<bean:write name="reportListId" property="time27" />
											</logic:notMatch></td>
										<%
											if (day_count == 28) {
										%>
										<td><logic:match name="reportListId" property="time28"
												value=" ">
												<logic:match name="reportListId" property="time28"
													value="  ">

													<logic:match name="reportListId" property="time28"
														value="   ">
														<font color="#228b22"> <bean:write
																name="reportListId" property="time28" /></font>
													</logic:match>
													<logic:notMatch name="reportListId" property="time28"
														value="   ">
														<font color="#0000CD"> <bean:write
																name="reportListId" property="time28" /></font>
													</logic:notMatch>
												</logic:match>

												<logic:notMatch name="reportListId" property="time28"
													value="  ">
													<font color="#FF0000"> <bean:write
															name="reportListId" property="time28" /></font>
												</logic:notMatch>

											</logic:match> <logic:notMatch name="reportListId" property="time28"
												value=" ">
												<bean:write name="reportListId" property="time28" />
											</logic:notMatch></td>
										<%
											} else if (day_count == 29) {
										%>
										<td><logic:match name="reportListId" property="time28"
												value=" ">
												<logic:match name="reportListId" property="time28"
													value="  ">

													<logic:match name="reportListId" property="time28"
														value="   ">
														<font color="#228b22"> <bean:write
																name="reportListId" property="time28" /></font>
													</logic:match>
													<logic:notMatch name="reportListId" property="time28"
														value="   ">
														<font color="#0000CD"> <bean:write
																name="reportListId" property="time28" /></font>
													</logic:notMatch>
												</logic:match>

												<logic:notMatch name="reportListId" property="time28"
													value="  ">
													<font color="#FF0000"> <bean:write
															name="reportListId" property="time28" /></font>
												</logic:notMatch>

											</logic:match> <logic:notMatch name="reportListId" property="time28"
												value=" ">
												<bean:write name="reportListId" property="time28" />
											</logic:notMatch></td>

										<td><logic:match name="reportListId" property="time29"
												value=" ">
												<logic:match name="reportListId" property="time29"
													value="  ">
													<logic:match name="reportListId" property="time29"
														value="   ">
														<font color="#228b22"> <bean:write
																name="reportListId" property="time29" /></font>
													</logic:match>
													<logic:notMatch name="reportListId" property="time29"
														value="   ">
														<font color="#0000CD"> <bean:write
																name="reportListId" property="time29" /></font>
													</logic:notMatch>
												</logic:match>

												<logic:notMatch name="reportListId" property="time29"
													value="  ">
													<font color="#FF0000"> <bean:write
															name="reportListId" property="time29" /></font>
												</logic:notMatch>

											</logic:match> <logic:notMatch name="reportListId" property="time29"
												value=" ">
												<bean:write name="reportListId" property="time29" />
											</logic:notMatch></td>
										<%
											} else if (day_count == 30) {
										%>

										<td><logic:match name="reportListId" property="time28"
												value=" ">
												<logic:match name="reportListId" property="time28"
													value="  ">

													<logic:match name="reportListId" property="time28"
														value="   ">
														<font color="#228b22"> <bean:write
																name="reportListId" property="time28" /></font>
													</logic:match>
													<logic:notMatch name="reportListId" property="time28"
														value="   ">
														<font color="#0000CD"> <bean:write
																name="reportListId" property="time28" /></font>
													</logic:notMatch>
												</logic:match>

												<logic:notMatch name="reportListId" property="time28"
													value="  ">
													<font color="#FF0000"> <bean:write
															name="reportListId" property="time28" /></font>
												</logic:notMatch>

											</logic:match> <logic:notMatch name="reportListId" property="time28"
												value=" ">
												<bean:write name="reportListId" property="time28" />
											</logic:notMatch></td>

										<td><logic:match name="reportListId" property="time29"
												value=" ">
												<logic:match name="reportListId" property="time29"
													value="  ">
													<logic:match name="reportListId" property="time29"
														value="   ">
														<font color="#228b22"> <bean:write
																name="reportListId" property="time29" /></font>
													</logic:match>
													<logic:notMatch name="reportListId" property="time29"
														value="   ">
														<font color="#0000CD"> <bean:write
																name="reportListId" property="time29" /></font>
													</logic:notMatch>
												</logic:match>

												<logic:notMatch name="reportListId" property="time29"
													value="  ">
													<font color="#FF0000"> <bean:write
															name="reportListId" property="time29" /></font>
												</logic:notMatch>

											</logic:match> <logic:notMatch name="reportListId" property="time29"
												value=" ">
												<bean:write name="reportListId" property="time29" />
											</logic:notMatch></td>

										<td><logic:match name="reportListId" property="time30"
												value=" ">
												<logic:match name="reportListId" property="time30"
													value="  ">

													<logic:match name="reportListId" property="time30"
														value="   ">
														<font color="#228b22"> <bean:write
																name="reportListId" property="time30" /></font>
													</logic:match>
													<logic:notMatch name="reportListId" property="time30"
														value="   ">
														<font color="#0000CD"> <bean:write
																name="reportListId" property="time30" /></font>
													</logic:notMatch>
												</logic:match>
												<logic:notMatch name="reportListId" property="time30"
													value="  ">
													<font color="#FF0000"> <bean:write
															name="reportListId" property="time30" /></font>
												</logic:notMatch>

											</logic:match> <logic:notMatch name="reportListId" property="time30"
												value=" ">
												<bean:write name="reportListId" property="time30" />
											</logic:notMatch></td>
										<%
											} else {
										%>
										<td><logic:match name="reportListId" property="time28"
												value=" ">
												<logic:match name="reportListId" property="time28"
													value="  ">

													<logic:match name="reportListId" property="time28"
														value="   ">
														<font color="#228b22"> <bean:write
																name="reportListId" property="time28" /></font>
													</logic:match>
													<logic:notMatch name="reportListId" property="time28"
														value="   ">
														<font color="#0000CD"> <bean:write
																name="reportListId" property="time28" /></font>
													</logic:notMatch>
												</logic:match>

												<logic:notMatch name="reportListId" property="time28"
													value="  ">
													<font color="#FF0000"> <bean:write
															name="reportListId" property="time28" /></font>
												</logic:notMatch>

											</logic:match> <logic:notMatch name="reportListId" property="time28"
												value=" ">
												<bean:write name="reportListId" property="time28" />
											</logic:notMatch></td>

										<td><logic:match name="reportListId" property="time29"
												value=" ">
												<logic:match name="reportListId" property="time29"
													value="  ">
													<logic:match name="reportListId" property="time29"
														value="   ">
														<font color="#228b22"> <bean:write
																name="reportListId" property="time29" /></font>
													</logic:match>
													<logic:notMatch name="reportListId" property="time29"
														value="   ">
														<font color="#0000CD"> <bean:write
																name="reportListId" property="time29" /></font>
													</logic:notMatch>
												</logic:match>

												<logic:notMatch name="reportListId" property="time29"
													value="  ">
													<font color="#FF0000"> <bean:write
															name="reportListId" property="time29" /></font>
												</logic:notMatch>

											</logic:match> <logic:notMatch name="reportListId" property="time29"
												value=" ">
												<bean:write name="reportListId" property="time29" />
											</logic:notMatch></td>

										<td><logic:match name="reportListId" property="time30"
												value=" ">
												<logic:match name="reportListId" property="time30"
													value="  ">

													<logic:match name="reportListId" property="time30"
														value="   ">
														<font color="#228b22"> <bean:write
																name="reportListId" property="time30" /></font>
													</logic:match>
													<logic:notMatch name="reportListId" property="time30"
														value="   ">
														<font color="#0000CD"> <bean:write
																name="reportListId" property="time30" /></font>
													</logic:notMatch>
												</logic:match>
												<logic:notMatch name="reportListId" property="time30"
													value="  ">
													<font color="#FF0000"> <bean:write
															name="reportListId" property="time30" /></font>
												</logic:notMatch>

											</logic:match> <logic:notMatch name="reportListId" property="time30"
												value=" ">
												<bean:write name="reportListId" property="time30" />
											</logic:notMatch></td>
										<td><logic:match name="reportListId" property="time31"
												value=" ">
												<logic:match name="reportListId" property="time31"
													value="  ">
													<logic:match name="reportListId" property="time31"
														value="   ">
														<font color="#228b22"> <bean:write
																name="reportListId" property="time31" /></font>
													</logic:match>
													<logic:notMatch name="reportListId" property="time31"
														value="   ">
														<font color="#0000CD"> <bean:write
																name="reportListId" property="time31" /></font>
													</logic:notMatch>
												</logic:match>

												<logic:notMatch name="reportListId" property="time31"
													value="  ">
													<font color="#FF0000"> <bean:write
															name="reportListId" property="time31" /></font>
												</logic:notMatch>

											</logic:match> <logic:notMatch name="reportListId" property="time31"
												value=" ">
												<bean:write name="reportListId" property="time31" />
											</logic:notMatch></td>
										<%
											}
										%>
									</tr>
								</logic:iterate>
							</logic:present>
						</table>
					</div>
					<script type="text/javascript">
						fxheaderInit('dataTable2', 400, 2, 1);
						/*  fxheaderInit('dataTable2',200,2,1);
							 fxheaderInit('dataTable3',200,1,0); */
						fxheader();
					</script>
				</logic:present>
			</logic:notPresent>

			<!-- Get the Task List  -->

			<logic:notEmpty name="taskFormList" scope="request">
				<div style="text-align: right; width: 755px; margin-bottom: 1mm;">
					<a href="##."
						style="text-decoration: none; size: 10px; font-weight: bold;"
						onclick="submitReport('displayReport.do?method=generateDetailedTimesheetReport');">Export
						To Excel</a> <img src="img/getI.png" style="height: 20px;" />
				</div>
				<div
					style="width: 760px; height: 500px; overflow: auto; margin-left: 2mm;">
					<logic:iterate id="taskFormList" name="taskFormList"
						scope="request">
						<logic:notEmpty name="taskFormList" property="taskFormList">
							<table border="1" class="hovertable"
								style="width: 740px; margin-bottom: 2mm;">
								<tr onmouseover="this.style.backgroundColor='#ffff66';"
									onmouseout="this.style.backgroundColor='#ffffff';">
									<th style="height: 5px;"><b> Username </b></th>
									<th style="height: 5px;"><b> Date </b></th>
									<th style="height: 5px;"><b> Category </b></th>
									<th style="height: 5px;"><b> Backlog ID </b></th>
									<th style="height: 5px;"><b> Task ID </b></th>
									<th style="height: 5px;"><b> Description </b></th>
									<th style="height: 5px;"><b> Efforts From</b></th>
									<th style="height: 5px;"><b> Time Spent in Hrs</b></th>
								</tr>
								<logic:iterate id="taskFormList1" name="taskFormList"
									property="taskFormList">
									<logic:equal name="taskFormList1" property="status"
										value="Comp off">
										<tr onmouseover="this.style.backgroundColor='#ffff66';"
											onmouseout="this.style.backgroundColor='#ffffff';"
											style="color: #228b22;">
											<td><bean:write name="taskFormList1" property="userName" /></td>
											<td><bean:write name="taskFormList1"
													property="task_date" /></td>
											<td><bean:write name="taskFormList1" property="status" /></td>
											<td><logic:equal value="null" name="taskFormList1"
													property="backlog_id"></logic:equal> <logic:notEqual
													value="null" name="taskFormList1" property="backlog_id">
													<bean:write name="taskFormList1" property="backlog_id" />
												</logic:notEqual></td>
											<td><logic:equal value="null" name="taskFormList1"
													property="task_id"></logic:equal> <logic:notEqual
													value="null" name="taskFormList1" property="task_id">
													<bean:write name="taskFormList1" property="task_id" />
												</logic:notEqual></td>
											<td><bean:write name="taskFormList1"
													property="task_description" /></td>
											<td><logic:equal name="taskFormList1"
													property="work_status" value="home">Home</logic:equal> <logic:notEqual
													name="taskFormList1" property="work_status" value="home">
													<logic:equal name="taskFormList1" property="work_status"
														value="office">Office</logic:equal>
													<logic:notEqual name="taskFormList1" property="work_status"
														value="office"></logic:notEqual>
												</logic:notEqual></td>
											<td><logic:equal name="taskFormList1" property="status"
													value="Half Day">
													<font color="#FF0000"><bean:write
															name="taskFormList1" property="time" /></font>
												</logic:equal> <logic:notEqual name="taskFormList1" property="status"
													value="Half Day">

													<logic:equal name="taskFormList1" property="status"
														value="Leave">
														<font color="#FF0000"><bean:write
																name="taskFormList1" property="time" /></font>
													</logic:equal>
													<logic:notEqual name="taskFormList1" property="status"
														value="Leave">
														<%-- <bean:write name="taskFormList1" property="time" /> --%>
														<logic:equal name="taskFormList1" property="status"
															value="Public holiday">
															<font color="#0000CD"><bean:write
																	name="taskFormList1" property="time" /></font>
														</logic:equal>
														<logic:notEqual name="taskFormList1" property="status"
															value="Public holiday">
															<bean:write name="taskFormList1" property="time" />
														</logic:notEqual>
													</logic:notEqual>
												</logic:notEqual></td>
										</tr>
									</logic:equal>
									<logic:notEqual name="taskFormList1" property="status"
										value="Comp off">
										<tr onmouseover="this.style.backgroundColor='#ffff66';"
											onmouseout="this.style.backgroundColor='#ffffff';">
											<td><bean:write name="taskFormList1" property="userName" /></td>
											<td><bean:write name="taskFormList1"
													property="task_date" /></td>
											<td><bean:write name="taskFormList1" property="status" /></td>
											<td><logic:equal value="null" name="taskFormList1"
													property="backlog_id"></logic:equal> <logic:notEqual
													value="null" name="taskFormList1" property="backlog_id">
													<bean:write name="taskFormList1" property="backlog_id" />
												</logic:notEqual></td>
											<td><logic:equal value="null" name="taskFormList1"
													property="task_id"></logic:equal> <logic:notEqual
													value="null" name="taskFormList1" property="task_id">
													<bean:write name="taskFormList1" property="task_id" />
												</logic:notEqual></td>
											<td><bean:write name="taskFormList1"
													property="task_description" /></td>

											<td><logic:equal name="taskFormList1"
													property="work_status" value="home">Home</logic:equal> <logic:notEqual
													name="taskFormList1" property="work_status" value="home">
													<logic:equal name="taskFormList1" property="work_status"
														value="office">Office</logic:equal>
													<logic:notEqual name="taskFormList1" property="work_status"
														value="office"></logic:notEqual>
												</logic:notEqual></td>
											<td><logic:equal name="taskFormList1" property="status"
													value="Half Day">
													<font color="#FF0000"><bean:write
															name="taskFormList1" property="time" /></font>
												</logic:equal> <logic:notEqual name="taskFormList1" property="status"
													value="Half Day">

													<logic:equal name="taskFormList1" property="status"
														value="Leave">
														<font color="#FF0000"><bean:write
																name="taskFormList1" property="time" /></font>
													</logic:equal>
													<logic:notEqual name="taskFormList1" property="status"
														value="Leave">
														<%-- <bean:write name="taskFormList1" property="time" /> --%>
														<logic:equal name="taskFormList1" property="status"
															value="Public holiday">
															<font color="#0000CD"><bean:write
																	name="taskFormList1" property="time" /></font>
														</logic:equal>
														<logic:notEqual name="taskFormList1" property="status"
															value="Public holiday">
															<bean:write name="taskFormList1" property="time" />
														</logic:notEqual>
													</logic:notEqual>
												</logic:notEqual></td>
										</tr>
									</logic:notEqual>
								</logic:iterate>
							</table>
						</logic:notEmpty>
					</logic:iterate>
				</div>
			</logic:notEmpty>
			<!-- For Leave Report  -->
			<!-- For Leave Report  -->
			<logic:notEmpty name="leaveFormList" scope="request">
				<div style="text-align: right; width: 450px; margin-bottom: 1mm;">
					<a href="##."
						style="text-decoration: none; size: 10px; font-weight: bold;"
						onclick="submitReport('displayReport.do?method=generateLeaveReportExcel');">Export
						To Excel</a> <img src="img/getI.png" style="height: 20px;" />
				</div>

				<div
					style="width: 471px; height: 500px; overflow: auto; margin-left: 2mm;">

					<logic:iterate id="leaveFormList" name="leaveFormList"
						scope="request">
						<logic:notEmpty name="leaveFormList" property="leaveFormList">
							<table class="hovertable"
								style="margin-bottom: 2mm; width: 450px;">
								<tr onmouseover="this.style.backgroundColor='#ffff66';"
									onmouseout="this.style.backgroundColor='#ffffff';">
									<th><b> Resource Name </b></th>
									<th><b> Leave Availed On </b></th>
									<!-- <th><b> Leave Date </b>
								</th> -->
									<th><b> Updated On </b></th>
								</tr>
								<logic:iterate id="leaveFormList1" name="leaveFormList"
									property="leaveFormList">
									<tr onmouseover="this.style.backgroundColor='#ffff66';"
										onmouseout="this.style.backgroundColor='#ffffff';">
										<td><bean:write name="leaveFormList1" property="userName" /></td>
										<td><bean:write name="leaveFormList1"
												property="selectMonth" /></td>
										<%-- <td><bean:write name="leaveFormList1" property="checkDate" /></td> --%>
										<td><bean:write name="leaveFormList1"
												property="updatedDateString" /></td>
									</tr>
								</logic:iterate>

							</table>
						</logic:notEmpty>
					</logic:iterate>
				</div>
			</logic:notEmpty>

			<!-- Added for User Contact Detail Table  -->
			<logic:notEmpty name="listContact" scope="request">
				<div
					style="text-align: right; width: 710px; margin-bottom: 1mm; margin-top: 8mm;">
					<a href="##."
						style="text-decoration: none; size: 10px; font-weight: bold;"
						onclick="submitReport('displayReport.do?method=generateContactReportExcel');">Export
						To Excel</a> <img src="img/getI.png" style="height: 20px;" />
				</div>

				<div style="width: 735px; height: 450px; overflow: auto;">
					<table border="1" class="hovertable"
						style="width: 710px; margin-bottom: 2mm; margin-left: 1mm;">
						<tr>
							<th><b> Resource </b></th>
							<th><b> Team </b></th>
							<th><b> Apollo Manager </b></th>
							<th><b> Mobile Number </b></th>
							<th><b> Skype ID </b></th>
						</tr>
						<%
							List<?> listC = (List<?>) request.getAttribute("listContact");
									Iterator<?> itr = listC.iterator();
									while (itr.hasNext()) {
										List<?> list = (List<?>) itr.next();
										if (list.size() == 5) {
											String[] str = (String[]) list.get(1);
						%>
						<tr>
							<td><%=list.get(0)%></td>
							<td><%=list.get(2)%></td>
							<td><%=list.get(3)%></td>
							<td><%=str[0]%><br> <%
 									if (str[1] != null) {
										 %> <%=str[1]%> <%
 									}
 								%></td>
							<td><%=list.get(4)%></td>
						</tr>
						<%
							} else if (list.size() == 1) {
						%>
						<tr>
							<td><%=list.get(0)%></td>
							<td>---</td>
							<td>---</td>
							<td>---</td>
							<td>---</td>
						</tr>
						<%
							}
									}
						%>
					</table>
				</div>
			</logic:notEmpty>

			<!-- Added for Defaulter List Table  -->
			<logic:present name="defaulterListDetails" scope="request">
				<logic:notEmpty name="defaulterListDetails" scope="request">
					<div style="text-align: right; width: 540px;">
						<a href="##."
							style="text-decoration: none; size: 10px; font-weight: bold;"
							onclick="submitReport('displayReport.do?method=generateDefaulterReportExcel');">Export
							To Excel</a> <img src="img/getI.png" style="height: 20px;" />
					</div>
					<div style="width: 560px; height: 550px; overflow: auto;">
						<table class="defaulterCss" style="width: 540px;">
							<tr>
								<th style="text-align: left;padding-left: 5px;">Resource Name</th>
								<th style="text-align: left;padding-left: 5px;">Period</th>
							</tr>
							<logic:iterate id="defaultId" name="defaulterListDetails"
								scope="request">
								<tr>
									<td><bean:write name="defaultId" property="userName" /></td>
									<td><bean:write name="defaultId" property="period" /></td>
								</tr>
							</logic:iterate>
						</table>
					</div>
				</logic:notEmpty>
			</logic:present>
			<logic:present name="CompOffReport" scope="request">
				<div style="text-align: right; width: 740px;">
					<a href="##."
						style="text-decoration: none; size: 10px; font-weight: bold;"
						onclick="submitReport('displayReport.do?method=generateCompOffReportExcel');">Export
						To Excel</a> <img src="img/getI.png" style="height: 20px;" />
				</div>
				<div style="width: 760px; height: 660px; overflow: auto;">
					<logic:notEmpty name="CompOffReport" scope="request">
						<table class="defaulterCss" width="740px"
							style="padding-top: 20px;">
							<thead>
								<tr>
									<th>Username</th>
									<th>Total Hours Spent</th>
									<th>Billed Hours=No. working days inthe month * 8</th>
									<th>Additional Hours Spent= Total Hours - Billed Hours</th>
									<th>Comp off availed in hours</th>
									<th>Balance credits in hours</th>
									<th>Total Down Time</th>
								</tr>
							</thead>
							<tbody>
								<logic:iterate id="comp_report" name="CompOffReport"
									scope="request">
									<tr>
										<td><bean:write name="comp_report" property="userName" /></td>
										<td><bean:write name="comp_report" property="totalTime" /></td>
										<td><bean:write name="comp_report"
												property="billed_hours" /></td>
										<td><bean:write name="comp_report"
												property="additional_hours" /></td>
										<td><bean:write name="comp_report"
												property="compOff_availed_hrs" /></td>
										<td><bean:write name="comp_report" property="comp_bal" /></td>
										<td><bean:write name="comp_report" property="total_down_time" /></td>
									</tr>
								</logic:iterate>
							</tbody>
						</table>
					</logic:notEmpty>
				</div>
			</logic:present>
						
			<logic:present name="time_entry_updateStatus" scope="request">
			 <logic:notEmpty name="time_entry_updateStatus" scope="request">
				<div style="text-align: right; width: 700px;">
					<a href="##." style="text-decoration: none; size: 10px; font-weight: bold;" onclick="submitReport('displayReport.do?method=generateExcelForTimeEntryTracking');">
					Export To Excel</a> 
					<img src="img/getI.png" style="height: 20px;" />
			   </div>			
			   <div style="width: 705px; height: 660px; overflow: auto;padding-left: 5px;">
				   <table class="defaulterCss" width="700px">
			 		<thead>
			 			<tr>
			 				<th style="text-align: left;padding-left: 5px;">Username</th>
			 				<th style="text-align: left;padding-left: 5px;">Task Date</th>
			 				<th style="text-align: left;padding-left: 5px;">Category</th>
			 				<th style="text-align: left;padding-left: 5px;">Task Description</th>
			 				<th style="text-align: left;padding-left: 5px;">Task Added/Updated On</th>
			 			</tr>
			 		</thead>
			 		<tbody>
			 			<logic:iterate id="track_time" name="time_entry_updateStatus" type="com.calsoft.task.form.TaskForm">
			 			<tr>
			 					<td><bean:write name="track_time" property="userName" /></td>
			 					<td><bean:write name="track_time" property="task_date" /></td>
			 					<td><bean:write name="track_time" property="status" /></td>
			 					<td><bean:write name="track_time" property="task_description" /></td>
			 					<logic:equal name="track_time" property="status" value="Comp off">			 				
			 						<td style="color: #228b22;"><bean:write name="track_time" property="entry_time" /></td>			 						 			
			 					</logic:equal>
			 					<logic:notEqual name="track_time" property="status" value="Comp off">
			 			  			<logic:equal name="track_time" property="status" value="Public holiday">
			 							<td style="color: #0000CD;"><bean:write name="track_time" property="entry_time" /></td>			 			
			 				    	</logic:equal>
			 				   		<logic:notEqual name="track_time" property="status" value="Public holiday">
						  		   		<logic:equal name="track_time" property="status" value="Half Day">
			 								<td style="color: #FF0000;"><bean:write name="track_time" property="entry_time" /> (H)</td>
						  		   		</logic:equal>
						  	   			<logic:notEqual name="track_time" property="status" value="Half Day">
						  	 				<logic:equal name="track_time" property="status" value="Leave">
			 									<td style="color: #FF0000;"><bean:write name="track_time" property="entry_time" /> (L)</td>
						  	        		</logic:equal>
						  	   				<logic:notEqual name="track_time" property="status" value="Leave">
			 									<td><bean:write name="track_time" property="entry_time" /></td>
						  					</logic:notEqual>
						  				</logic:notEqual>
			 			      		</logic:notEqual>
			 			       </logic:notEqual>
			 			</tr>
			 			</logic:iterate>			 							 
			 	   </tbody>			
			      </table>
			</div>
			</logic:notEmpty>
		</logic:present>						
		</html:form>
	</div>
</body>
<script type="text/javascript">
$( document ).ready(function() {
   resourceSelectionScript();
   checkFreezingStatusForSelectedResource();
   pickSelectedClientResourceFromDropDown();
});
</script>
</html>