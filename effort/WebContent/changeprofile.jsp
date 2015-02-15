<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="html" uri="/WEB-INF/struts-html.tld"%>
<%@taglib prefix="logic" uri="/WEB-INF/struts-logic.tld"%>
<%@taglib prefix="bean" uri="/WEB-INF/struts-bean.tld"%>
<html>
<head>
<style type="text/css">
body {
	font: normal 14px Calibri, Calibri, Calibri;
}

a.LINK {
	color: #003CCD
}

table.hovertable1 {
	font-family: verdana, arial, sans-serif;
	font-size: 11px;
	color: #333333;
	align: center;
}
</style>
<style type="text/css">
.boxhelp {
	background-color: #A9CDDB;
	border: 1px solid #FFFFFF;
	height: 15px;
	width: 300px;
	padding: 5px;
	display: none;
	position: absolute;
	text-align: left;
}

.changeButtonUI:hover {
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
	DXImageTransform.Microsoft.gradient(   startColorstr=   '#3d94f6',
		endColorstr=   '#1e62d0' );
	height: 23px;
	background: -moz-linear-gradient(center top, #3d94f6 5%, #1e62d0 100%);
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#3d94f6',
		endColorstr='#1e62d0' );
}
.textBoxStyle{
 	background-color: #F2F2F2;
}
.classForVerify{
	color: blue;cursor: pointer;font-weight: bold;
	border: solid;border-color: #ADD8E6;border-width: 3px;
	border-style: outset;
	border-collapse:collapse;
	padding: 2px;width:51px;text-align: center;text-decoration: underline;
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
<script type="text/javascript">
	function submitReport(url) {
		var formSubmit = document.forms[0];
		formSubmit.action = url;
		formSubmit.submit();
	}
	function checkValidationBeforeSubmit(url) {
		$("#validation_newpass_id").html('');
		$("#validation_confpass_id").html('');
		var new_pass = $("#new_pass_txt").val();
		var confirm_pass = $("#conf_pass_txt").val();
		var error = '';
		if(new_pass == ''){
			error = error+'newpass_error';
			$("#validation_newpass_id").html("<span style='border: solid;border-color: red;border-width: 2px;padding: 3px;width:200px;color: red;'>Please enter new password.</span>");
		}else if(!(new_pass.length >= 5 && new_pass.length <= 12)){
			error = error+'newpass_length_error';
			$("#validation_newpass_id").html("<span style='border: solid;border-color: red;border-width: 2px;padding: 3px;width:200px;color: red;'>"
												+"Entered password should 5 to 12 character long.</span>");
		}
		if(confirm_pass == ''){
			error = error+'confpass_error';
			$("#validation_confpass_id").html("<span style='border: solid;border-color: red;border-width: 2px;padding: 3px;width:200px;color: red;'>"
					+"Please Re-enter new password.</span>");
		}else if(new_pass != confirm_pass){
			error = error+'confpass_length_error';
			$("#validation_confpass_id").html("<span style='border: solid;border-color: red;border-width: 2px;padding: 3px;width:200px;color: red;'>"
					+"New password and confirm password are not same.</span>");
		}
		if(error == ''){
			$("#resource_pass_id").attr("disabled", false);
			var formSubmit = document.forms[0];
			formSubmit.action = url;
			formSubmit.submit();
		}
	}
	function validateUserPassword(){
		var currentPass = $("#resource_pass_id").val();
		if(currentPass != ''){
			$.ajax({
				type: "POST",
				url: "./ajaxCallForResourcePassValidation.do?method=validateResourceExistingPassword",
				data: 'currentPassword='+currentPass,
				success: function(response){
					if(response == "Password Matched."){
						$("#new_pass_txt").attr("disabled", false);
						$("#conf_pass_txt").attr("disabled", false);
						$("#resource_pass_id").attr("disabled", true);
						$("#new_pass_txt").removeClass("textBoxStyle");
						$("#conf_pass_txt").removeClass("textBoxStyle");
						$("#resource_pass_id").addClass("textBoxStyle");
					
						$("#displayImage").html("<div style='border: solid;border-color: green;border-width: 2px;padding: 3px;width:100px;'>"
										+"<img src='./img/pass_match.png' style='height: 15px;'/>"
										+"<span style='vertical-align: top;font-weight: bold;color: green;padding-bottom: 5px;'> Matched.</span></div>");						
						$("#changeButtonId").attr("disabled", false);
						$("#changeButtonId").addClass("changeButtonUI");
					}else if(response == "Incorrect Password."){
						$("#displayImage").html("<div style='border: solid;border-color: red;border-width: 2px;padding: 3px;width:200px;' onchange ='validateUserPassword();'>"
												+"<img src='./img/delete1.png'/>"
													+"<span style='vertical-align: top;font-weight: bold;color: red;padding-bottom: 5px;'> Wrong Password."
														+"<span style='vertical-align: top;color: blue;font-weight: bold;cursor: pointer;padding-left: 10px;text-decoration: underline;' onclick='validateUserPassword();'>"
															+"Verify</span></span></div>");					
						$("#new_pass_txt").attr("disabled", true);
						$("#conf_pass_txt").attr("disabled", true);
						$("#new_pass_txt").addClass("textBoxStyle");
						$("#conf_pass_txt").addClass("textBoxStyle");					
					}else{
						$("#displayImage").html("");
					}
				},
				beforeSend: function(){
					$("#displayImage").html("<img src='./img/button_loading.gif' style='vertical-align: bottom;height: 30px;'/>");
				},
				error: function(e){  
					$("#displayImage").html("");
				}
				
			});
		}else if(currentPass == ''){
			$("#displayImage").html("<div style='border: solid;border-color: red;border-width: 2px;padding: 3px;width:200px;'>"
					+"<span style='vertical-align: top;font-weight: bold;color: red;padding-bottom: 5px;'>"
					+" Enter your password.<span style='color: blue;font-weight: bold;cursor: pointer;padding-left: 10px;text-decoration: underline;' onclick='validateUserPassword();'>"
					+"Verify</span></span></div>");
		}
	}
</script>
</head>
<body>
	<div style="margin-left: 40px;">
		<br>
		<table class="hovertable1">
			<tr>
				<td>Name: <logic:notEmpty name="profilename" scope="request">
						<b><bean:write name="profilename" scope="request" /></b>
					</logic:notEmpty>
				</td>
			</tr>
		</table>
		<br>
		<div style="color: #616D7E; text-decoration: underline; font-stretch: expanded; text-align: center; font-size: 16px; height: 30px; font-weight: bold;">Change
			Your Password</div>

		<table>
			<tr>
				<td align="left">
					<div style="margin-left: 130px;">
						<html:errors />
					</div>
				</td>
			</tr>
			<tr>
				<td align="left">
					<html:form action="AddUserAction" method="post">
						<html:hidden property="userId" value="1" styleId="hidden_user_id"/>
						<table class="hovertable1">
							<tr height="40">
								<td align="left"><FONT color="#FF0000">*</FONT>Current
									Password:</td>
								<td><html:password property="passWord" value="" styleId="resource_pass_id" size="35"></html:password>
								</td>
								<td><div id="displayImage" style="padding-left: 18px;">
								    <p class="classForVerify" onclick="validateUserPassword()">Verify</p></div></td>
							</tr>
							<tr height="40">
								<td align="left"><FONT color="#FF0000">*</FONT>New
									Password:</td>
								<td><html:password property="newPassword" value="" styleId="new_pass_txt" styleClass="textBoxStyle" disabled="true"
										size="35"></html:password></td>
								<td colspan="2"><img src="./img/help.gif" onmouseover="ShowText('Message'); return true;" onmouseout="HideText('Message'); return true;">
									<span id="Message" class="boxhelp">
										<font color="#00004B"> Please type new password upto 5 to 12 character </font>
									</span>
									<span id="validation_newpass_id"></span>
								</td>
							</tr>
							<tr height="40">
								<td align="left"><FONT color="#FF0000">*</FONT>Confirm
									Password:</td>
								<td><html:password property="confirmPassword" value="" styleId="conf_pass_txt" styleClass="textBoxStyle" disabled="true"
										size="35"></html:password></td>
								<td colspan="2">
									<img src="./img/help.gif" onmouseover="ShowText('Message1'); return true;" onmouseout="HideText('Message1'); return true;">
										<span id="Message1" class="boxhelp">
											<font color="#00004B"> Please Re-type your new password here </font>
										   </span>
									<span id="validation_confpass_id"></span>	    
							   </td>
							</tr>
							<tr height="20">
								<td></td>
								<td align="left" colspan="1">
									<input type="button" id="changeButtonId" disabled="disabled" value="Change" onclick="checkValidationBeforeSubmit('AddUserAction444.do?method=passwordChanged');" style="font-family: verdana, arial, sans-serif; font-size: 11px;" />
									<input type="button" class="changeButtonUI" value="Back" onclick="submitReport('AddUserAction3.do?method=goChangePassword');" style="font-family: verdana, arial, sans-serif; font-size: 11px;" />
								</td>
							</tr>
						</table>
					</html:form>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>