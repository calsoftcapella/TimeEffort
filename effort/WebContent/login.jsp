<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="icon" type="image/jpg" href="img/calsoftNew.jpg" />
<title>Calsoft Labs Timesheet-Login Page</title>
<link href="css/page.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">
	function submitForm(url) {
		var submitForm = document.forms[0];
		submitForm.action = url;
		submitForm.submit();
	}
</script>
<style type="text/css">
body {
	font: normal 14px Calibri, Calibri, Calibri;
}
</style>

<style type="text/css">
.boxhelp {
	background-color: #A9CDDB;
	border: 1px solid #FFFFFF;
	height: 15px;
	width: 150px;
	padding: 5px;
	display: none;
	position: absolute;
	text-align: left;
}
</style>
<style type="text/css">
.boxhelp11 {
	background-color: #A9CDDB;
	border: 1px solid #FFFFFF;
	height: 15px;
	width: 100px;
	padding: 5px;
	display: none;
	position: absolute;
	text-align: left;
}
</style>

<style type="text/css">
div.box {
	border: solid 2px #C7BEBE;
	background-color: #FFFFFF;
	display: table;
	padding: 0px;
	width: 350px;
	align: center;
	margin-left: 0cm;
	text-align: center;
	border-top-left-radius: 1em;
	border-bottom-right-radius: 1em;
}
</style>

<script type="text/javascript" language="JavaScript">
	var cX = 0;
	var cY = 0;
	var rX = 0;
	var rY = 0;
	function UpdateCursorPosition(e) {
		cX = e.pageX;
		cY = e.pageY;
	}
	function UpdateCursorPositionDocAll(e) {
		cX = event.clientX;
		cY = event.clientY;
	}
	if (document.all) {
		document.onmousemove = UpdateCursorPositionDocAll;
	} else {
		document.onmousemove = UpdateCursorPosition;
	}
	function AssignPosition(d) {
		if (self.pageYOffset) {
			rX = self.pageXOffset;
			rY = self.pageYOffset;
		} else if (document.documentElement
				&& document.documentElement.scrollTop) {
			rX = document.documentElement.scrollLeft;
			rY = document.documentElement.scrollTop;
		} else if (document.body) {
			rX = document.body.scrollLeft;
			rY = document.body.scrollTop;
		}
		if (document.all) {
			cX += rX;
			cY += rY;
		}
		d.style.left = (cX + 10) + "px";
		d.style.top = (cY + 10) + "px";
	}
	function HideText(d) {
		if (d.length < 1) {
			return;
		}
		document.getElementById(d).style.display = "none";
	}
	function ShowText(d) {
		if (d.length < 1) {
			return;
		}
		var dd = document.getElementById(d);
		AssignPosition(dd);
		dd.style.display = "block";
	}
	function ReverseContentDisplay(d) {
		if (d.length < 1) {
			return;
		}
		var dd = document.getElementById(d);
		AssignPosition(dd);
		if (dd.style.display == "none") {
			dd.style.display = "block";
		} else {
			dd.style.display = "none";
		}
	}
</script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<style type="text/css">
.myStyle {
	font: 13px Calibri;
	color: #000000;
}
</style>

<style type="text/css">
a:LINK {
	text-decoration: underline;
}

a:HOVER {
	color: red;
	text-decoration: none;
}
input:focus
{
border-color: #ffaa00;
} 
</style>

<script type="text/javascript">
 function focusScriptOnLoad() {
	 $('#usId').focus();
 }
 </script>
<!--  <script src="js/snowstorm.js"></script> -->
</head>
<body style="margin-top: 0%; height: 700px; vertical-align: top;"
	bgcolor="#A6A3A2" onload="focusScriptOnLoad()">
	<html:form action="userLoginAction" method="post">

		<!-- onsubmit="return validateFormOnSubmit(this)"> -->


		<table border="0" align="center"
			style="border-width: 3px; border-style: solid; border-color: gray;"
			bgcolor="white" height="862" width="860">
			<tr>
				<td valign="top" align="left" bgcolor="white"><img
					src="img/img_logo_CalsoftLabs.jpg" width="150" height="90" />
					<table align="right">
						<tr height="100" valign="baseline">
							<td width="70%" style="height: 1000" align="right"
								valign="bottom"><a href="genPassWord.jsp"
								style="vertical-align: bottom; color: #003CCD;"> Forgot
									Password ? </a></td>
						</tr>
					</table>
					<div style="margin-top: 5mm;">
						<hr color="red" size="3%" width="100%" />
					</div></td>
			</tr>
			<tr>
				<td>
					<table align="center" height="550" bgcolor="white">
						<tr>
							<td valign="top" align="center"><br>
							<br>
								<table align="center">
									<tr>
										<td align="left"><html:errors /> <font color='red'
											style="font-family: 14px Calibri;"> <logic:notEmpty
													name="signoutMessage" scope="request">
													<bean:write name="signoutMessage" scope="request" />
												</logic:notEmpty>
										</font></td>
									</tr>
								</table>

								<div class="box">
									<table width="40%" height="50%" align="center">
										<tr>
											<td height="170">
												<table align="center">
													<br>
													<tr>
														<td>Username:</td>
														<td><html:text property="mail" size="37"
																styleClass="myStyle" styleId="usId" 
																/> <!-- <input type="text" name="mail" size="37" class="myStyle"/> -->
														</td>
														<td><img src="./img/help.gif"
															onmouseover="ShowText('Message'); return true;"
															onmouseout="HideText('Message'); return true;"
															href="javascript:ShowText('Message')">
															<div id="Message" class="boxhelp">
																<font color="#00004B">Enter your Email ID here.</font>
															</div> <!-- <input type="text" name="mail" size="37" class="myStyle"/> -->
														</td>

													</tr>
													<tr>
														<td>Password:</td>
														<td><html:password property="passWord" value=""
																size="37" styleClass="myStyle" styleId="passId" 
																 /> <!-- <input type="password" name="passWord" size="37" class="myStyle"/>  -->
														</td>
														<td><img src="./img/help.gif"
															onmouseover="ShowText('Message1'); return true;"
															onmouseout="HideText('Message1'); return true;"
															href="javascript:ShowText('Message1')">
															<div id="Message1" class="boxhelp11">
																<font color="#00004B"> Enter password. </font>
															</div></td>
													</tr>
													<tr>
														<td></td>
														<td height="60" align="left"><input
															style="background-color: #ADC4E2;" type="image"
															src="./img/log_in.gif" width="25%"
															onclick="submitForm('userLoginAction.do?method=Login')" />
															<!-- <input type="submit" name="method" value="Login"/> -->
														</td>
														<td></td>
													</tr>
												</table> <br>
												<table align="center">
													<tr>
														<td valign="bottom"><a href="genPassWord.jsp"
															style="color: #003CCD;"> Can't access your account?</a></td>
													</tr>
												</table>

											</td>
										</tr>
									</table>
								</div></td>
						</tr>
					</table>
			<tr>
				<td height="45" width="850" bgcolor="white">
					<div align="center">&copy; Calsoft Labs 2012</div>
				</td>
			</tr>
		</table>
	</html:form>
</body>
</html>
