<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Calsoft Labs Timesheet Weekly Status Report</title>
<style type="text/css">
body {
	font: normal 14px Calibri, Calibri, Calibri;
}
</style>
<style type="text/css">
table.hovertable {
	font-family: verdana, arial, sans-serif;
	font-size: 11px;
	color: #333333;
	border-width: 1px;
	border-color: black;
	align: center;
	border-collapse: collapse;
}

table.hovertable th {
	background-color: #A5A1A0;
	border-width: 1px;
	padding: 0px;
	border-style: solid;
	border-color: #a9c6c9;
}

table.hovertable tr:nth-child(odd) {
	background-color: #eee;
}

table.hovertable tr:nth-child(even) {
	background-color: #fff;
}

table.hovertable td {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #a9c6c9;
}
table.hovertable1 {
	font-family: verdana, arial, sans-serif;
	font-size: 11px;
	color: #333333;
	border-width: 1px;
	border-color: black;
	align: center;
	border-collapse: collapse;
}

table.hovertable1 th {
	background-color: #A5A1A0;
	border-width: 1px;
	padding: 0px;
	border-style: solid;
	border-color: #a9c6c9;
}

table.hovertable1 tr:nth-child(odd) {
	background-color: #FFFFFA;
}

table.hovertable1 tr:nth-child(even) {
	background-color: #fff;
}

table.hovertable1 td {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #a9c6c9;
	text-align: center;
}
table.hovertable2 {
	font-family: verdana, arial, sans-serif;
	font-size: 11px;
	color: #333333;
	border-width: 1px;
	border-color: black;
	align: center;
	border-collapse: collapse;
}

table.hovertable2 th {
	background-color: #A5A1A0;
	border-width: 1px;
	padding: 0px;
	border-style: solid;
	border-color: #a9c6c9;
}

table.hovertable2 tr:nth-child(odd) {
	background-color: #FFFFFA;
}

table.hovertable2 tr:nth-child(even) {
	background-color: #fff;
}

table.hovertable2 td {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #a9c6c9;
}
table.hovertable3 {
	font-family: verdana, arial, sans-serif;
	font-size: 11px;
	color: #333333;
	border-width: 1px;
	border-color: black;
	align: center;
	border-collapse: collapse;
}

table.hovertable3 th {
	background-color: #A5A1A0;
	border-width: 1px;
	padding: 0px;
	border-style: solid;
	border-color: #a9c6c9;
}

table.hovertable3 tr:nth-child(odd) {
	background-color: #FFFFFA;
}

table.hovertable3 tr:nth-child(even) {
	background-color: #fff;
}

table.hovertable3 td {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #a9c6c9;
}

.button
 {
  style:font-family: verdana, arial, sans-serif; font-size: 11px;
  color:white;
  background-color: #1B00A1;
 } 
</style>
<script type="text/javascript">
var count = "1";
function addRow(tableID)
{ 
    var tRow = document.getElementById(tableID).insertRow(1);
    tRow.id=count+"id";
    var td1 = tRow.insertCell(0);
    var strHtml1 = "<textarea rows=\"1\" cols=\"1\" name=\"detail_mile\" style=\"width: 100%\">";
    td1.innerHTML = strHtml1;
    var td2 = tRow.insertCell(1);
    var strHtml2 = "<input type=\"text\" name=\"owner_mile\" style=\"width: 70%\">";
    td2.innerHTML = strHtml2;
    var td3 = tRow.insertCell(2);
    var strHtml3 = "<img alt=\"1\" src=\"img/minus.png\" SIZE=\"5\" title=\"Delete\" onClick='deleteRow(this);'><textarea rows=\"1\" cols=\"1\" name=\"remark_mile\" style=\"width: 100%\"></textarea>";
    td3.innerHTML = strHtml3;
    count++;    
}  
function deleteRow(r)
{
    try 
    {   	 
      var i=r.parentNode.parentNode.rowIndex;    
      document.getElementById('dataTable').deleteRow(i);            	
    }
    catch(e) {
        alert(e);
    }

}
function submitForm()
{
	var formSubmit = document.forms[0];
	formSubmit.action = "\displayReport1.do?method=saveWeeklyForm";
	formSubmit.submit();		
}

</script>
</head>
<body>
	<div style="margin-left: 60px;">
	<html:form action="displayReport1" method="post">
	
	<logic:present name="weeklyObject" scope="request">
	  <table class="hovertable" width="710px" >
			<tr>
				<td colspan="2"  align="right" style="background-color: #DCFFFF;">				
				<input type="button" value="Edit" class="button" />
				<!-- <input type="button" value="Save" class="button" onclick="submitForm()"/> -->
				<input type="button" value="Send Via Email" class="button"/></td>
			</tr>
			<tr>
				<td width="100px">Status date & by</td>
				<td><bean:write name="weeklyObject" property="statusDate"/></td>
			</tr>
			<tr>
				<td width="100px">Events / Information</td>
				<td ><bean:write name="weeklyObject" property="eventInfo"/></td>
			</tr>
			<tr>
				<td width="100px">Deliverables / Milestones</td>
				<td >
					<div>
					 <img alt="1" height="15" src="img/plus.png" onclick="addRow('dataTable')"/>
						<table class="hovertable1" width="590px" id="dataTable">
							<thead>
								<tr>
									<th >Detail</th>
									<th >Owner</th>
									<th >Remark</th>
								</tr>
							</thead>														 																				
							<tbody>
							<logic:iterate id="myMilesId" name="weeklyObject" property="deliList" type="com.calsoft.report.model.Deliverables">
								<tr>
									<td> <bean:write name="myMilesId" property="details_miles" /></td>
									<td align="center"><bean:write name="myMilesId" property="owner_mile" /></td>
									<td ><bean:write name="myMilesId" property="remark_mile" /></td>
								</tr>
							</logic:iterate>
							</tbody>							
						</table>
					</div>
				</td>
			</tr>
			<tr>
				<td width="100px">Constraints/ Issues/ Escalation</td>
				<td width="600px">
					<div style="width: 610px;">
						<table class="hovertable2" width="600px">
							<thead>
								<tr>
									<th>On</th>
									<th>Detail</th>
									<th>Owner</th>
									<th>Remark</th>
									<th>ETA</th> 
								</tr>
							</thead>
							<tbody>
							<logic:iterate id="myConstId" name="weeklyObject" property="constList" type="com.calsoft.report.model.Constraints">
								<tr>
									<td width="15%"><bean:write name="myConstId" property="onDate_const" /></td>
									<td width="25%"><bean:write name="myConstId" property="detail_const" /></td>
									<td width="20%"><bean:write name="myConstId" property="owner_const" /></td>
								    <td width="20%"><bean:write name="myConstId" property="remark_const" /></td>
									<td width="20%"><bean:write name="myConstId" property="eta_const" /></td>
								</tr>
							</logic:iterate>								
							</tbody>
						</table>
					</div>
				</td>
			</tr>
			<tr>
				<td width="150px">Achievements/ Learning</td>
				<td width="350px"><bean:write name="weeklyObject" property="achievements"/></td>
			</tr>
			<tr>
				<td width="150px" height="40px" colspan="2" style="border: none;">Recruitment
					status</td>
			</tr>

			<tr>
				<td  colspan="2">
					<table class="hovertable3" align="left" width="100%">
						<tr>
							<th align="left" width="150px" height="30px">Role</th>
							<logic:iterate id="myRoleId" name="weeklyObject" property="recrList" type="com.calsoft.report.model.Recruitment_status">
							<td><bean:write name="myRoleId" property="role_status" /></td>
							</logic:iterate>														
						</tr>
						<tr>
							<th align="left" width="150px" height="30px"># of open position at start</th>
							<logic:iterate id="myRoleId" name="weeklyObject" property="recrList" type="com.calsoft.report.model.Recruitment_status">
							<td><bean:write name="myRoleId" property="position" /></td>
							</logic:iterate>
						</tr>
						<tr>
							<th align="left" width="150px" height="30px">Internal interviews</th>
							<logic:iterate id="myRoleId" name="weeklyObject" property="recrList" type="com.calsoft.report.model.Recruitment_status">
							<td><bean:write name="myRoleId" property="internalInter" /></td>
							</logic:iterate>
						</tr>
						<tr>
							<th align="left" width="150px" height="30px">Apollo interviews</th>
							<logic:iterate id="myRoleId" name="weeklyObject" property="recrList" type="com.calsoft.report.model.Recruitment_status">
							<td><bean:write name="myRoleId" property="apolloInter" /></td>
							</logic:iterate>		
						</tr>
						<tr>
							<th align="left" width="150px" height="30px">Selected & Offered</th>
							<logic:iterate id="myRoleId" name="weeklyObject" property="recrList" type="com.calsoft.report.model.Recruitment_status">
							<td><bean:write name="myRoleId" property="selectedOff" /></td>
							</logic:iterate>					
						</tr>
						<tr>
							<th align="left" width="150px" height="30px">Joined</th>
							<logic:iterate id="myRoleId" name="weeklyObject" property="recrList" type="com.calsoft.report.model.Recruitment_status">
							<td><bean:write name="myRoleId" property="joined" /></td>
							</logic:iterate>					
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</logic:present>
	<logic:notPresent name="weeklyObject" scope="request"> 	
		<table class="hovertable" width="710px" >
			<tr>
				<td colspan="2"  align="right" style="background-color: #DCFFFF;">				
				<!-- <input type="button" value="Edit" class="button" /> -->
				<input type="button" value="Save" class="button" onclick="submitForm()"/>
				<!-- <input type="button" value="Send Via Email" class="button"/></td> -->
			</tr>
			<tr>
				<td width="100px">Status date & by</td>
				<td><html:textarea property="statusDate"  rows="1" cols="1"  style="width: 100%"/></td>
			</tr>
			<tr>
				<td width="100px">Events / Information</td>
				<td ><html:textarea property="eventInfo"  rows="1" cols="1"  style="width: 100%"/></td>
			</tr>
			<tr>
				<td width="100px">Deliverables / Milestones</td>
				<td >
					<div>
					 <img alt="1" height="15" src="img/plus.png" onclick="addRow('dataTable')"/>
						<table class="hovertable1" width="590px" id="dataTable">
							<thead>
								<tr>
									<th >Detail</th>
									<th >Owner</th>
									<th >Remark</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td><html:textarea property="detail_mile"  rows="1" cols="1"  style="width: 100%"/></td>
									<td align="center"><html:text property="owner_mile" style="width: 70%"/></td>
									<td ><html:textarea property="remark_mile"  rows="1" cols="1"  style="width: 100%"/></td>
								</tr>
							</tbody>
						</table>
					</div>
				</td>
			</tr>

			<tr>
				<td width="100px">Constraints/ Issues/ Escalation</td>
				<td width="600px">
					<div style="width: 610px;">
						<table class="hovertable2" width="600px">
							<thead>
								<tr>
									<th>On</th>
									<th>Detail</th>
									<th>Owner</th>
									<th>Remark</th>
									<th>ETA</th> 
								</tr>
							</thead>
							<tbody>
								<tr>
									<td width="15%"><html:text property="onDate_const"  size="10"/></td>
									<td width="25%"><html:textarea property="detail_const" rows="1" cols="2"  style="size: 30px;width: 100%"/></td>
									<td width="20%"><html:text property="owner_const" size="10"/></td>
								    <td width="20%"><html:text property="remark_const" size="10"/></td>
									<td width="20%"><html:text property="eta_const" size="10"/></td>
								</tr>
								<tr>
									<td width="15%"><html:text property="onDate_const"  size="10"/></td>
									<td width="25%"><html:textarea property="detail_const" rows="1" cols="2"  style="size: 30px;width: 100%"/></td>
									<td width="20%"><html:text property="owner_const" size="10"/></td>
								    <td width="20%"><html:text property="remark_const" size="10"/></td>
									<td width="20%"><html:text property="eta_const" size="10"/></td>
								</tr><tr>
									<td width="15%"><html:text property="onDate_const"  size="10"/></td>
									<td width="25%"><html:textarea property="detail_const" rows="1" cols="2"  style="size: 30px;width: 100%"/></td>
									<td width="20%"><html:text property="owner_const" size="10"/></td>
								    <td width="20%"><html:text property="remark_const" size="10"/></td>
									<td width="20%"><html:text property="eta_const" size="10"/></td>
								</tr>
							</tbody>
						</table>
					</div>
				</td>
			</tr>
			<tr>
				<td width="150px">Achievements/ Learning</td>
				<td width="350px"><html:textarea property="achievements" rows="1" cols="1" style="width: 100%"/></td>
			</tr>
			<tr>
				<td width="150px" height="40px" colspan="2" style="border: none;">Recruitment
					status</td>
			</tr>

			<tr>
				<td  colspan="2">
					<table class="hovertable3" align="left" width="100%">
						<tr>
							<th align="left" width="150px" height="30px">Role</th>
							<td><html:text property="role_status" size="42"/></td>
							<td><html:text property="role_status" size="42"/></td>														
						</tr>
						<tr>
							<th align="left" width="150px" height="30px"># of open position at start</th>
							<td><html:text property="position" size="42"/></td>
							<td><html:text property="position" size="42"/></td>
						</tr>
						<tr>
							<th align="left" width="150px" height="30px">Internal interviews</th>
							<td><html:text property="internalInter" size="42"/></td>
							<td><html:text property="internalInter" size="42"/></td>
						</tr>
						<tr>
							<th align="left" width="150px" height="30px">Apollo interviews</th>
							<td><html:text property="apolloInter" size="42"/></td>
							<td><html:text property="apolloInter" size="42"/></td>			
						</tr>
						<tr>
							<th align="left" width="150px" height="30px">Selected & Offered</th>
							<td><html:text property="selectedOff" size="42"/></td>	
							<td><html:text property="selectedOff" size="42"/></td>					
						</tr>
						<tr>
							<th align="left" width="150px" height="30px">Joined</th>
							<td><html:text property="joined" size="42"/></td>
							<td><html:text property="joined" size="42"/></td>						
						</tr>
					</table>
				</td>
			</tr>
		</table>
		</logic:notPresent>
		</html:form>
	</div>
</body>
</html:html>