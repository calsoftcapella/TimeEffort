<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<html>
<head>
<link rel="icon" type="image/jpg" href="img/calsoftNew.jpg" />
<style type="text/css">
body {
	font: normal 14px Calibri, Calibri, Calibri;
}
a.LINK {
	color: #003CCD
}
</style>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/user_header.js"></script>
<script type="text/javascript">	
</script>
<style type="text/css">
a.testLink:link {
	color: #003CCD;
	text-decoration: underline;
}
a.testLink:active {
	color: #003CCD;
	text-decoration: underline;
}
a.testLink:hover {
	color: #871F69;
	text-decoration: none;
}
a.testLink2:link {
	color: #871F69;
	text-decoration: none;
}
a.testLink2:active {
	color: #871F69;
	text-decoration: none;
}
a.testLink2:visited {
	color: #871F69;
	text-decoration: none;
}
a.testLink2:hover {
	color: #871F69;
	text-decoration: none;
}
</style>
<script type="text/javascript">
	window.history.forward();
	$(document).unbind('keydown')
			.bind(
					'keydown',
					function(event) {
						var doPrevent = false;
						if (event.keyCode === 8) {
							var d = event.srcElement || event.target;
							if ((d.tagName.toUpperCase() === 'INPUT' && (d.type
									.toUpperCase() === 'TEXT' || d.type
									.toUpperCase() === 'PASSWORD'))
									|| d.tagName.toUpperCase() === 'TEXTAREA') {
								doPrevent = d.readOnly || d.disabled;
							} else {
								doPrevent = true;
							}
						}
						if (doPrevent) {
							event.preventDefault();
						}
					});
</script>
</head>
<body>
	<logic:present name="clientRole" scope="session">
		<input type="hidden" name="access_info" id="access_info_id"
			value='<bean:write name="clientRole"/>' />
	</logic:present>
	<table border="0" width="100%" height="100%">
		<tr>
			<td valign="top">
				<table border="0">
					<tr>
						<td valign="top" height="40%" align="left"><img
							src="img/img_logo_CalsoftLabs.jpg" width="150" height="90" /></td>
					</tr>
				</table>
			</td>
			<td valign="top" align="right">
				<table align="right" border="0" width="73%" height="100%">
					<tr valign="top" height="60">
						<td>
							<table align="right" border="0" width="100%" height="100%">
								<tr>
									<td align="right" valign="top">
										<div align="right"
											style="font: cursor; vertical-align: bottom;">
											<logic:notEmpty scope="session" name="userName">
												<bean:write name="userName" scope="session" />
											</logic:notEmpty>
										</div>
										<div id="date_time"
											style="font-family: normal 14px Calibri, Calibri, Calibri; font-weight: normal; color: #8A0829;">
											<script type="text/javascript">
												DateAndTime();
												setInterval(function() {
													DateAndTime();
												}, 1000);
											</script>
										</div> <a href="AddUserAction2.do?method=signOut##."
										style="color: #44157D; font-weight: bolder;" id="log"
										onclick="submitReport('AddUserAction2.do?method=signOut');">Log
											Out</a>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td valign="bottom" height="30" width="750px"><div
								align="right">
								<logic:notPresent name="manageUserAcess" scope="session">
									<logic:present name="clientRole" scope="session">
										<table>
											<tr valign="bottom">
												<td><a
													href="displayReport.do?method=getUserReportDetails" id="a3"
													onclick="changeLinkCSS(this);submitReport('displayReport.do?method=getUserReportDetails');"
													class="testLink" style="color: #003CCD">Report</a> |</td>
												<td><a href="AddUserAction3.do?method=goChangePassword"
													id="a4" onclick="changeLinkCSS(this)" class="testLink"
													style="color: #003CCD">My Profile</a> |</td>
												<td><a href="faquestion.do?method=getFaquestion"
													id="a6"
													onclick="changeLinkCSS(this);onClickLeave('faquestion.do?method=getFaquestion');"
													class="testLink" style="color: #003CCD">FAQ</a> |</td>
												<td><a
													href="performanceLog.do?method=getSelectedperiodList"
													id="a7"
													onclick="changeLinkCSS(this);submitReport('performanceLog.do?method=getSelectedperiodList');"
													class="testLink" style="color: #003CCD;">Performance
														Log</a></td>
											</tr>
										</table>
									</logic:present>
									<logic:notPresent name="clientRole" scope="session">
										<table>
											<tr valign="bottom">
												<td><a href="userHomeAction.do?method=getHomeContent"
													id="a1"
													onclick="changeLinkCSS(this);submitReport('userHomeAction.do?method=getHomeContent');"
													class="testLink" style="color: #003CCD;">Home</a> |</td>
												<td><a href="taskAction1.do?method=onClickTask" id="a2"
													onclick="changeLinkCSS(this);onClickTask('taskAction1.do?method=onClickTask');"
													class="testLink" style="color: #003CCD">Time Entry</a> |</td>
												<td><a
													href="displayReport.do?method=getUserReportDetails" id="a3"
													onclick="changeLinkCSS(this);submitReport('displayReport.do?method=getUserReportDetails');"
													class="testLink" style="color: #003CCD">Report</a> |</td>
												<td><a href="AddUserAction3.do?method=goChangePassword"
													id="a4" onclick="changeLinkCSS(this)" class="testLink"
													style="color: #003CCD">My Profile</a> |</td>
												<td><a href="LeaveAction.do?method=getLeaveDashBoard"
													id="a5"
													onclick="changeLinkCSS(this);onClickLeave('LeaveAction.do?method=getLeaveDashBoard');"
													class="testLink" style="color: #003CCD">Leave Plan</a> |</td>
												<td><a href="faquestion.do?method=getFaquestion"
													id="a6"
													onclick="changeLinkCSS(this);onClickLeave('faquestion.do?method=getFaquestion');"
													class="testLink" style="color: #003CCD">FAQ</a> |</td>
												<td><a
													href="performanceLog.do?method=getSelectedperiodList"
													id="a7"
													onclick="changeLinkCSS(this);submitReport('performanceLog.do?method=getSelectedperiodList');"
													class="testLink" style="color: #003CCD;">Performance
														Log</a></td>
											</tr>
										</table>
									</logic:notPresent>
								</logic:notPresent>
								<logic:present name="manageUserAcess" scope="session">
									<table width="615px;">
										<tr valign="bottom">
											<td><a href="userHomeAction.do?method=getHomeContent"
												id="a1"
												onclick="changeLinkCSS(this);submitReport('userHomeAction.do?method=getHomeContent');"
												class="testLink" style="color: #003CCD;">Home</a> |</td>
											<td><a href="taskAction1.do?method=onClickTask" id="a2"
												onclick="changeLinkCSS(this);onClickTask('taskAction1.do?method=onClickTask');"
												class="testLink" style="color: #003CCD">Time Entry</a> |</td>
											<td><a
												href="displayReport.do?method=getUserReportDetails" id="a3"
												onclick="changeLinkCSS(this);submitReport('displayReport.do?method=getUserReportDetails');"
												class="testLink" style="color: #003CCD">Report</a> |</td>
											<td><a href="AddUserAction3.do?method=goChangePassword"
												id="a4" onclick="changeLinkCSS(this)" class="testLink"
												style="color: #003CCD">My Profile</a> |</td>
											<td><a href="LeaveAction.do?method=getLeaveDashBoard"
												id="a5"
												onclick="changeLinkCSS(this);onClickLeave('LeaveAction.do?method=getLeaveDashBoard');"
												class="testLink" style="color: #003CCD">Leave Plan</a> |</td>
											<td><a href="faquestion.do?method=getFaquestion" id="a6"
												onclick="changeLinkCSS(this);onClickLeave('faquestion.do?method=getFaquestion');"
												class="testLink" style="color: #003CCD">FAQ</a> |</td>
											<td><a
												href="performanceLog.do?method=getSelectedperiodList"
												id="a7"
												onclick="changeLinkCSS(this);submitReport('performanceLog.do?method=getSelectedperiodList');"
												class="testLink" style="color: #003CCD;">Performance Log</a>
												|</td>
											<td><a
												href="reportmanagement.do?method=getUserAccessDetails"
												id="a8" class="testLink"
												onclick="changeLinkCSS(this);submitReport('reportmanagement.do?method=getUserAccessDetails');"
												style="color: #003CCD">Resource Access Mgmt</a></td>
										</tr>
									</table>
								</logic:present>
							</div></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>
