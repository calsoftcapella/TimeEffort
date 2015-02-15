<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="html" uri="/WEB-INF/struts-html.tld"%>
<%@taglib prefix="logic" uri="/WEB-INF/struts-logic.tld"%>
<%@taglib prefix="bean" uri="/WEB-INF/struts-bean.tld"%>
<%@taglib uri="http://displaytag.sf.net" prefix="display" %>
<html>
<head>
 <style type="text/css">
table.hovertable {
	font-family: verdana,arial,sans-serif;
	font-size:11px;
	color:#333333;
	border-width: 1px;
	border-color: #999999;
	border-collapse: collapse;
}
table.hovertable th {
	background-color:#A5A1A0;
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #a9c6c9;
}
table.hovertable tr {
	background-color:#ffffff;
}
table.hovertable td {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #a9c6c9;
}
.changeButtonUI:hover {
	font-family: verdana,arial,sans-serif;
	font-size:11px;
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
<script type="text/javascript">
function submitReport(url){
	var formSubmit=document.forms[0];
	formSubmit.action=url;
	formSubmit.submit();	
}
</script>
<style type="text/css">
body {
	font: normal 14px Calibri, Calibri, Calibri;
}
</style>
</head>
<body>
<div style="margin-left: 40px">

<html:form action="AddUserAction3">
<br>

<table style="font-family:verdana,arial,sans-serif;font-size: 11px; ">
<tr>
<td width="350">
Name:
<logic:notEmpty name="profilename" scope="request">
<b><bean:write name="profilename" scope="request"/></b>
</logic:notEmpty>
</td>
<td width="150">
<input type="button" class="changeButtonUI" value="Change Password" onclick="submitReport('AddUserAction3.do?method=changePassword');" style="font-family:verdana,arial,sans-serif;font-size: 11px; " />
</td>
<td width="100">
<input type="button" class="changeButtonUI" value="User Access Mapping" onclick="submitReport('AddUserAction3.do?method=getUserAccessMapping');" style="font-family:verdana,arial,sans-serif;font-size: 11px; "/>
</td>
</tr>
</table><br><br><center>
<logic:notEmpty name="passwordmsg" scope="request">
&nbsp;&nbsp;&nbsp;&nbsp;<font color="red" size="3px"><bean:write name="passwordmsg" scope="request"/></font>
</logic:notEmpty>
<logic:notEmpty name="passwordmsg1" scope="request">
&nbsp;&nbsp;&nbsp;&nbsp;<font color="red" size="3px"><bean:write name="passwordmsg1" scope="request"/></font>
</logic:notEmpty></center>
 <logic:notEmpty name="updatedallocatedList" scope="request">
<div style="width: 250px; height: 450px; overflow: auto;">
	<display:table   id="data" name="requestScope.updatedallocatedList" requestURI="AddUserAction3.do?method=getUserAccessMapping" export="false" decorator="com.calsoft.report.decorator.UserReportAccessMappingDecorator" class="hovertable" >
	
	<display:column property="parentUserName" title="User"  />
            <display:column  property="userName" title="Access To" />
          
          
                     </display:table>
</div>

 </logic:notEmpty> 
</html:form>
</div>
</body>
</html>