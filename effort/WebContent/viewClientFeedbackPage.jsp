<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<html>
<head>
<link rel="icon" type="image/jpg" href="img/calsoftNew.jpg" />
<style type="text/css">
body {
	font: normal 14px Calibri, Calibri, Calibri;
}
table.feedback_table {
	font-family: verdana, arial, sans-serif;
	font-size: 11px;
	color: #333333;
	width: 550px;
	height: 350px;
}
table.feedback_table tr {
	
}
table.feedback_table td {
	text-align: left;
	vertical-align: top;
}
textarea {
	border-color: #DCDCFF;
	border-style: solid;
}
input[type=text] {
	font-family: verdana, arial, sans-serif;
	font-size: 11px;
	border-color: #DCDCFF;
	border-style: solid;
}
input[type=text]:focus {
	border-color: #FFD000
}
textarea:focus {
	border-color: #FFD000
}
#error_message {
	font-family: verdana, arial, sans-serif;
	font-size: 11px;
	text-align: center;
	color: red;
}
input[type=submit]{
	font-family: verdana, arial, sans-serif;
	font-size: 11px;
	border-color: #DCDCFF;
	border-style: solid;
}
input[type=submit]:focus {
	border-color: #FFD000
}
input[type=file] {
	font-family: verdana, arial, sans-serif;
	font-size: 12px;
	color: blue;
	cursor: pointer;
}
</style>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">
	function checkFieldInput() {
		$('#error_message').html("");
		$('#error_message').show();
		var to_list = $('#to_list').val();
		var subject_line = $('#subject_line').val();
		var image_file = $('#uploded_file_id').val();
		var body_content = $('#body_content_id').val();
		if (to_list == "") {
			$("#error_message").html("Please specify email in 'To' field.");
			$("#to_list").focus();
			$('#error_message').delay(2000).fadeOut();
			return false;
		}
		else if( to_list.indexOf('@') == -1 || to_list.indexOf('.') == -1 || ( to_list.indexOf('.') != -1 && to_list.lastIndexOf('.')+2 >= to_list.length)){
			$("#error_message").html("Please specify valid email ids in 'To' field.");
			$("#to_list").focus();
			$('#error_message').delay(2000).fadeOut();
			return false;
		}
		if (subject_line == "") {
			$("#error_message").html("Please enter subject line.");
			$("#subject_line").focus();
			$('#error_message').delay(2000).fadeOut();
			return false;
		} 
		if(image_file == ""){
			$("#error_message").html("Please select image file for sharing comment.");
			$('#error_message').delay(2000).fadeOut();
			return false;
		}
		else if(image_file != ""){
			var file_extension = image_file.substring(image_file.lastIndexOf('.')+1, image_file.length);
			if((file_extension != "jpg" && file_extension != "png") && (file_extension != "JPG" && file_extension != "PNG")){
				$("#error_message").html("Please select .png and .jpg file for sharing comment.");
				$('#error_message').delay(2000).fadeOut();
				return false;
			}				
		}
		if (body_content == "") {
			$("#error_message").html("Please add feedback summary.");
			$("#body_content_id").focus();
			$('#error_message').delay(2000).fadeOut();
			return false;
		}
		return true;
	}
	$(document).ready(function() {
        var formSubmit = window.opener.document.forms[0];
		formSubmit.action = "userHomeAction.do?method=getHomeContent";
		formSubmit.submit();
	});
</script>
</head>
<body>
	<div id="CommentForm" style="margin-left: 30px;">
		<div id="error_message"></div>
		<div id = "error_message_from_server" style="font-family: verdana, arial, sans-serif;font-size: 11px;color: red;text-align: center;">
		  <logic:present name="message_for_uploading" scope="request">
		      <%--  <bean:write name="message_for_uploading" scope="request" /> --%>
		       <%=request.getAttribute("message_for_uploading") %>
		  </logic:present>
		</div>
		<fieldset>
			<legend style="font-weight: bolder;">Share A Feedback</legend>
			<html:form action="clientFeedback.do?method=shareClientFeedback" method="post" enctype="multipart/form-data">
				<table class="feedback_table" style="border-collapse:separate; border-spacing: 10px;">
					<tbody>
						<tr>
							<td style="font-weight: bolder;"><span style="color: red;font-weight: bolder;">*</span>To:</td>
							<td>							
							    <html:textarea property ="to_list" cols="1" rows="1" title="Email To"
									styleId="to_list" style="width: 650px; height: 80px;" 
									 value="<%= request.getAttribute("usermailingList") == null?"":request.getAttribute("usermailingList").toString() %>">							
								</html:textarea></td>
						</tr>
						<tr>
							<td style="font-weight: bolder;"><span style="color: red;font-weight: bolder;">*</span>Subject:</td>
							<td><html:text property="subject_line" title="Email Subject"
									styleId="subject_line" maxlength="100" style="width: 650px;" /></td>
						</tr>
						<tr>
						  <td style="font-weight: bolder;padding-top: 15px;"><span style="color: red;font-weight: bolder;">*</span>Attachment:</td><td><span style="color: gray;">Select image file to share feedback</span> 
									 <br> 
									 <input type="file" name="uploded_file" size="80" id="uploded_file_id" title="JPG, PNG only." />
									 <br>
									 <span style="color: red;padding-left: 500px;">[ jpg, png only.]</span>
							</td>
						</tr>
						<tr>
							<td style="font-weight: bolder;"><span style="color: red;font-weight: bolder;">*</span>Feedback &nbsp;&nbsp;Summary:</td>
							<td><html:textarea property="body_content" styleId="body_content_id" title="Add comment here." 
									cols="50" rows="10" style="height: 200px; width: 650px;" value=""></html:textarea></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td><html:submit property="share_button" value="Share" title="Share" styleId="share_button"
									style="font-weight: bolder;" onclick="return checkFieldInput();" ></html:submit></td>
						</tr>
					</tbody>
				</table>
			</html:form>
		</fieldset>
	</div>
</body>
</html>