<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Calsoft TimeSheet</title>
 <link href="css/style.css" rel="stylesheet" type="text/css" />
</head>
<body bgcolor="">
	<center>
		<h1>Report</h1>
	</center>
	<html:form action="displayReport.do?method=displayReport">
		<table border="0" align="center">
			<tr></tr>
			<tr></tr>
			<tr></tr>
			<tr></tr>
			<tr>
				<td></td>
			</tr>
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td bordercolor="white"><h3>Month:</h3>
				</td>
				<td bordercolor="white"><select id="monthData" name="monthData">
						<option value="0">Select</option>
						<option value="01">Jan</option>
						<option value="02">Feb</option>
						<option value="03">March</option>
						<option value="04">Apr</option>
						<option value="05">May</option>
						<option value="06">June</option>
						<option value="07">July</option>
						<option value="08">Aug</option>
						<option value="09">Sept</option>
						<option value="10">Oct</option>
						<option value="11">Nov</option>
						<option value="12">Dec</option>
				</select>
				</td>
				<td></td>
				<td bordercolor="white"><h3>Year:</h3>
				</td>
				<td bordercolor="white"><select id="yearData" name="yearData">
						<option value="0">Select</option>
						<option value="2013">2013</option>
						<option value="2012">2012</option>
						<option value="2011">2011</option>
						<option value="2010">2010</option>
				</select></td>
				<td></td>
				<td bordercolor="white"><input type="submit"
					value="View Report" /></td>
				<td></td>
				
			</tr>
			</table>
			<table border="1">
			<!-- <tr>
			<td colspan="5">Name</td>
				<td>1</td>
				<td>2</td>
				<td>3</td>
				<td>4</td>
				<td>5</td>
				<td>6</td>
				<td>7</td>
				<td>8</td>
				<td>9</td>
				<td>10</td>
				<td>11</td>
				<td>12</td>
				<td>13</td>
				<td>14</td>
				<td>15</td>
				<td>16</td>
				<td>17</td>
				<td>18</td>
				<td>19</td>
				<td>20</td>
				<td>21</td>
				<td>22</td>
				<td>23</td>
				<td>24</td>
				<td>25</td>
				<td>26</td>
				<td>27</td>
				<td>28</td>
				<td>29</td>
				<td>30</td>
				<td>31</td>


			</tr>
 -->			<!--
			<logic:present name="reportList" scope="request">
			
			 
				 <logic:iterate id="reportList" name="reportList" scope="request"
					type="com.calsoft.report.form.ReportForm">
					<tr>
					    <td colspan="5"><bean:write  name="reportList" property="userName"></bean:write></td>
						<td><bean:write  name="reportList" property="time1"></bean:write></td>
						<td><bean:write  name="reportList" property="time2"></bean:write></td>
						<td><bean:write  name="reportList" property="time3"></bean:write></td>
						<td><bean:write  name="reportList" property="time4"></bean:write></td>
						<td><bean:write  name="reportList" property="time5"></bean:write></td>
						<td><bean:write  name="reportList" property="time6"></bean:write></td>
						<td><bean:write  name="reportList" property="time7"></bean:write></td>
						<td><bean:write  name="reportList" property="time8"></bean:write></td>
						<td><bean:write  name="reportList" property="time9"></bean:write></td>
						<td><bean:write  name="reportList" property="time10"></bean:write></td>
						<td><bean:write  name="reportList" property="time11"></bean:write></td>
						<td><bean:write  name="reportList" property="time12"></bean:write></td>
						<td><bean:write  name="reportList" property="time13"></bean:write></td>
						<td><bean:write  name="reportList" property="time14"></bean:write></td>
						<td><bean:write  name="reportList" property="time15"></bean:write></td>
						<td><bean:write  name="reportList" property="time16"></bean:write></td>
						<td><bean:write  name="reportList" property="time17"></bean:write></td>
						<td><bean:write  name="reportList" property="time18"></bean:write></td>
						<td><bean:write  name="reportList" property="time19"></bean:write></td>
						<td><bean:write  name="reportList" property="time20"></bean:write></td>
						<td><bean:write  name="reportList" property="time21"></bean:write></td>
						<td><bean:write  name="reportList" property="time22"></bean:write></td>
						<td><bean:write  name="reportList" property="time23"></bean:write></td>
						<td><bean:write  name="reportList" property="time24"></bean:write></td>
						<td><bean:write  name="reportList" property="time25"></bean:write></td>
						<td><bean:write  name="reportList" property="time26"></bean:write></td>
						<td><bean:write  name="reportList" property="time27"></bean:write></td>
						<td><bean:write  name="reportList" property="time28"></bean:write></td>
						<td><bean:write  name="reportList" property="time29"></bean:write></td>
                        <td><bean:write  name="reportList" property="time30"></bean:write></td>
                        <td><bean:write  name="reportList" property="time31"></bean:write></td>
					</tr>

				</logic:iterate>

			</logic:present>
					-->
				</table>
	<display:table id="data" name="requestScope.reportList" requestURI="displayReport.do?method=displayReport" pagesize="5" export="true"  decorator="com.calsoft.report.decorator.ReportDecorator" >
            <display:column property="userName" title="User Name" sortable="true" />
            <display:column property="time1" title="Day 1" sortable="true" />
            <display:column property="time2" title="Day 2" sortable="true" />
            <display:column property="time3" title="Day 3" sortable="true" />
            <display:column property="time4" title="Day 4" sortable="true" />
            <display:column property="time5" title="Day 5" sortable="true" />
            <display:column property="time6" title="Day 6"  sortable="true"/>
            <display:column property="time7" title="Day 7"  sortable="true"/>
            <display:column property="time8" title="Day 8"  sortable="true"/>
           <display:column property="time9" title="Day 9" sortable="true" />
            <display:column property="time10" title="Day 10" sortable="true" />
            <display:column property="time11" title="Day 11" sortable="true" />
            <display:column property="time12" title="Day 12" sortable="true" />
              <display:column property="time13" title="Day 13" sortable="true" />
            <display:column property="time14" title="Day 14" sortable="true" />
            <display:column property="time15" title="Day 15"  sortable="true"/>
            <display:column property="time16" title="Day 16" sortable="true"/>
            <display:column property="time17" title="Day 17" sortable="true" />
            <display:column property="time18" title="Day 18" sortable="true" />
            <display:column property="time19" title="Day 19" sortable="true" />
            <display:column property="time20" title="Day 20" sortable="true" />
            <display:column property="time21" title="Day 21" sortable="true" />
            <display:column property="time22" title="Day 22" sortable="true" />
            <display:column property="time23" title="Day 23" sortable="true" />
            <display:column property="time24" title="Day 24" sortable="true" />
           <display:column property="time25" title="Day 25" sortable="true" />
            <display:column property="time26" title="Day 26" sortable="true" />
            <display:column property="time27" title="Day 27" sortable="true" />
            <display:column property="time28" title="Day 28" sortable="true" />
              <display:column property="time29" title="Day 29" sortable="true" />
            <display:column property="time30" title="Day 30" sortable="true" />
            <display:column property="time31" title="Day 31" sortable="true" />
            <%--   <display:setProperty name="export.excel.filename" value="ReportDetails.xls"/>
              <display:setProperty name="export.csv.filename" value="ReportDetails.csv"></display:setProperty>
              --%>
            <display:setProperty name="export.excel.filename" value="ActorDetails.xls"/>
            <display:setProperty name="export.pdf.filename" value="ActorDetails.pdf"/>
            <display:setProperty name="export.pdf" value="true" />
                    </display:table>
		
		
	</html:form>
</body>
</html>