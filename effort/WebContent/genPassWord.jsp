<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>

<%@page isErrorPage="true"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>

<!-- <link rel="shortcut icon" href="http://www.calsoftlabs.com/favicon.ico"
	type="image/x-icon" /> -->
	
	<link rel="icon" type="image/jpg" href="img/calsoftNew.jpg"/>
	
<title>Calsoft Labs Forget Password Page!</title>
<!-- <link href="css/page.css" rel="stylesheet" type="text/css" /> -->

<style type="text/css">
.myStyle {
	font: 13px Calibri;
	color: #000000;
}
</style>
<style type="text/css">
body {
	font: normal 14px Calibri, Calibri, Calibri;
}
</style>
<style type="text/css">
div.box {
    font-family: verdana,arial,sans-serif;
    font-size:11px;
	border: solid 2px #C7BEBE;
	background: #FFFFFF;
	display: table;
	padding: 0px;
	width: 350px;
	align: center;
	text-align: center;
	border-top-left-radius: 1em;
	border-bottom-right-radius: 1em;
}
</style>
<style type="text/css">
div.BOXX {
    font-family: verdana,arial,sans-serif;
    font-size:11px;
	display: table;
	padding: 0px;
	width: 350px;
	align: center;
	margin-left: 0cm;
	text-align: center;
}
</style>

<script type="text/javascript">
var clicks = 0;
	function validateFormOnSubmit(theForm) {
		var reason = "";
		reason += validateEmail(theForm.mail);

		if (reason != "") {
			document.getElementById('checkEmail').innerHTML = "\n" + "\n"
					+ "\n" + reason;
			// alert("ErrorInfo:\n"+"\n"+"\n" + reason);
			return false;
		}
        if(clicks==1)
        	{
		theForm.action = 'genNewPass.do?method=GenerateNewPassword';
		theForm.submit();
        	}
		return true;
	}
	function trim(s) {
		return s.replace(/^\s+|\s+$/, '');
	}
	function validateEmail(fld) {
		var error = "";

		var filter = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
		if (fld.value == "") {
			fld.style.background = '#F5EEBC';
			error = "Enter your email address.\n" + "\n";
		} else if (!filter.test(fld.value)) {
			fld.style.background = '#F5EEBC';
			error = "Please enter valid email address.\n" + "\n";
			return error;
		} else {
			fld.style.background = 'White';

		}
		return error;
	}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<body style="margin-top: 0%; height: 700px; vertical-align: top;" bgcolor="#A6A3A2">
  
	<form  method="POST" onsubmit="clicks++;return validateFormOnSubmit(this)">
		<table border="0" align="center"
			style="border-width: 3px; border-style: solid; border-color: gray;"
			bgcolor="white" height="862" width="860">
			<tr>
				<td valign="top" align="left" bgcolor="white"><img
					src="img/img_logo_CalsoftLabs.jpg" width="150" height="90">
				</td>
				<td valign="bottom" width="150" height="90">
					<div align="right" style="vertical-align: bottom;">
						<input type="button" value="Log in"
							onclick="window.location.href='login.jsp'" style="font-family: verdana,arial,sans-serif;font-size:11px;margin-right: 3px;"/>
					</div>
				</td>
			</tr>
			<tr align="center">
				<td valign="top" align="center" colspan="2">
					<hr color="red" size="3%" width="100%" />
				</td>
			</tr>

			<tr align="center">
				<td align="center" width="110%" colspan="2">
					<table align="center" height="550" bgcolor="white"
						style="margin-left: 700">
						<tr>
							<td valign="top" align="center">

								<table align="center">
									<tr>
										<td><br> <br>
											<center>
												<html:errors />
												<div class="BOXX" id="checkEmail" align="center"
													style="color: red; font-family: verdana,arial,sans-serif;font-size:11px;"></div>
											</center>
										</td>
									</tr>
								</table> <br> <br>
								<div class="box">
									<table align="center">
										<tr>
											<td height="80">Email ID:<html:text value=""
													property="mail" size="35" styleClass="myStyle" /></td>
										</tr>
										<tr>
											<td align="center" width="100%">
											<html:submit property="method" value="Generate New Password" style="font-family: verdana,arial,sans-serif;font-size:11px;"/>
											</td>
										</tr>
									</table>
								</div>
							</td>
						</tr>

					</table>
			
			<tr>
				<td height="45" width="850" bgcolor="white" colspan="2">
					<div align="center">&copy; Calsoft Labs 2012</div></td>
			</tr>
		</table>
	</form>
</body>

</html>


