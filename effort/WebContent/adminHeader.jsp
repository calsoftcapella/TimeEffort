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
<script type="text/javascript">
	$(document).ready(
			function() {
				var A = document.links.length;
				var currURL = window.location;
				var str = currURL.toString();
				var strForCurURL = str.substring(0, str.lastIndexOf('.'));
				for ( var i = 0; i <= A; i++) {
					var el = document.links[i];
					if(typeof document.links[i] != "undefined") {
						var currHREF = el.href;
						el.style.color = "#003CCD";
						el.getAttribute("id");
						if (el.getAttribute("id") == 'log') {
							el.style.color = "#44157D";
						}
						var hrstr = currHREF.toString();
						var strForCurHREF = hrstr.substring(0, hrstr
								.lastIndexOf('.'));
						var myVar = str.substring(str.lastIndexOf("/") + 1,
								str.length);
						if (myVar == 'userLoginAction.do?method=Login') {
							var strURL = 'adminHomeAction';
							var myStrForCurHREF = strForCurHREF.substring(strForCurHREF.lastIndexOf("/")+1, strForCurHREF.length);
							if (strURL == myStrForCurHREF) {
								el.style.color = "#B28C81";
								el.style.textDecoration = "none";
								el.style.cursor = 'text';
							}
						}
						if (strForCurURL == strForCurHREF || strForCurURL == strForCurHREF.replace('100','')+"2" || strForCurURL == strForCurHREF.replace('100','')) {
							el.style.color = "#B28C81";
							el.style.textDecoration = "none";
							el.style.cursor = 'text';
							if (el.getAttribute("id") == null) {
								el.style.color = "#003CCD";
								el.style.textDecoration = "none";
								el.style.cursor = 'pointer';
							}
						}
					}
				}
			});
	function changeLinkCSS(el) {
		var newClass = 'testLink2';
		if (!(el.className == newClass)) {
			el.className = newClass;
		}
	}
	function DateAndTime() {
		var currentDate = new Date();
		var day = currentDate.getDate();
		var month = currentDate.getMonth() + 1;
		var year = currentDate.getFullYear();
		//document.write();		  
		var currentTime = new Date();
		var hours = currentTime.getHours();
		var minutes = currentTime.getMinutes();
		var sec = currentTime.getSeconds();
		var suffix = "AM";
		if (hours >= 12) {
			suffix = "PM";
			hours = hours - 12;
		}
		if (hours == 0) {
			hours = 12;
		}
		if (minutes < 10)
			minutes = "0" + minutes;
		if (sec < 10)
			sec = "0" + sec;
		$('#date_time').html(
				"<b>" + day + "/" + month + "/" + year + "</b>,   " + "<b>"
						+ hours + ":" + minutes + ":" + sec + " " + suffix
						+ "</b>");
	}
</script>
<style type="text/css">
.testLink {
	
}

.testLink2 {
	
}

a.testLink:link {
	color: #003CCD;
	text-decoration: underline;
}

a.testLink:active {
	text-decoration: underline;
}

a.testLink:hover {
	color: #871F69;
	text-decoration: none;
}

a.testLink2:link {
	color: grey;
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
</script>
</head>
<body>
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

			<td align="right" valign="top">
				<table align="right" border="0" width="100%" height="100%">
					<tr valign="top" height="60">
						<td align="right">
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
										</div> <a href="##." style="color: #44157D; font-weight: bolder;"
										id="log"
										onclick="submitReport('AddUserAction2.do?method=signOut');">Log
											Out</a>


									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td valign="bottom" height="30">
							<div align="right">
								<table>
									<tr>
										<td valign="bottom"><a
											href="adminHomeAction.do?method=getAdminHome" id="a1"
											class="testLink"
											onclick="changeLinkCSS(this);submitReport('adminHomeAction.do?method=getAdminHome')"
											style="color: #003CCD">Home</a> |</td>
										<td valign="bottom"><a
											href="AddUserAction100.do?method=userManage" id="a2"
											class="testLink"
											onclick="changeLinkCSS(this);submitReport('AddUserAction100.do?method=userManage');"
											style="color: #003CCD">User Mgmt</a> |</td>
										<td valign="bottom"><a
											href="reportmanagement.do?method=getUserDetails" id="a3"
											class="testLink"
											onclick="changeLinkCSS(this);submitReport('reportmanagement.do?method=getUserDetails');"
											style="color: #003CCD">Report Mgmt</a></td>
									</tr>
								</table>
							</div>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>
