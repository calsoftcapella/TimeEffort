<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link href="css/page.css" rel="stylesheet" type="text/css" />
<style type="text/css">
body{font: normal 14px Calibri, Calibri, Calibri;}
</style>
<style type="text/css">
table.hovertable {
 font-family: verdana,arial,sans-serif;
 font-size:11px;
 color:#333333;
 border-width: 1px;
 border-color: #999999;
 border-collapse: collapse;
 margin-left: 1mm;
}
table.hovertable th {
 background-color:#A5A1A0;
 border-width: 1px;
 padding: 8px;
 border-style: solid;
 border-color: #a9c6c9;
}
table.hovertable tr {
 background-color:#FFFFFF;
}
table.hovertable td {
 border-width: 1px;
 padding: 8px;
 border-style: solid;
 border-color: #a9c6c9;
}
</style>
<style type="text/css">
p {
	margin-left: 13cm;
	margin-top: 2cm;
}

div.box {
	border: solid 1px #a9c6c9;
	background-color: #FFFFFF;
	display: table;
	padding: 0px;
	width: 290px;
	align: left;
	margin-left: 0cm;
	margin-top: 0cm;
	text-align: left;
	font-family: verdana,arial,sans-serif;
    font-size:11px;
}
div.boxMess {
    border: solid 3px #a9c6c9;
	border-style: dotted;
	background-color: #FFFFFF;
	display: table;
	padding: 0px;
	width: 695px;
	hight:0px;
	align: left;
	margin-left: 2px;
	margin-top: 0px;
	margin-bottom: 0px;
	text-align: left;
	color:#F50000;
	font-family: verdana,arial,sans-serif;
    font-size:11px;
}
</style>


</head>
<body>
<div style="font-family:verdana,arial,sans-serif;font-size: 11px; ">
                               
                                
                                <div>
                                    <table>
                                        <tbody><tr>
                                           <!--  <td align="left" width="200">
                                                <input name="imgbtnExport" id="imgbtnExport" title="Export" src="../Images/export.png" style="border-width: 0px;" type="image">
                                            </td> -->
                                            
                                           <!--  <td align="left" width="100%"> -->
                                                 
                                                <!-- <select name="drpYear" onchange="javascript:setTimeout('__doPostBack(\'drpYear\',\'\')', 0)" id="drpYear">
		<option value="2011">2011</option>
		<option selected="selected" value="2012">2012</option>

	</select> -->
                                            
                                               
                                                
                                                <!--  <span id="lblLocation">Location</span>
                                                <select name="drpLocation" onchange="javascript:setTimeout('__doPostBack(\'drpLocation\',\'\')', 0)" id="drpLocation" style="font-size: 10pt; height: 22px;">
		<option selected="selected" value="Bengaluru">Bengaluru</option>
		<option value="L100">Chennai</option>
		<option value="L102">Mysore</option>
		<option value="L105">Boston</option>
		<option value="L113">Fremont</option>
		<option value="L121">France</option>

	</select>
                                            </td> -->
                                        </tr>
                                    </tbody></table>
                                </div>
                                <div class="clear">
                                </div>
                                <div id="HomeGrid" style="background: none repeat scroll 0% 0% transparent;">
                                    <table rules="all" id="dgrHolidayView" style="width: 100%; border-collapse: collapse;" align="left" border="1" cellspacing="0">
		<tbody><tr class="bgh">
			<td style="background-color: gray;">
                                                    <span id="dgrHolidayView_ctl01_lblHolidayDate" >Holiday Date</span>
                                                </td><td style="background-color: gray;">
                                                    <span id="dgrHolidayView_ctl01_lblHolidayDay">Day</span>
                                                </td><td style="background-color: gray;">
                                                    <span id="dgrHolidayView_ctl01_lblHolidayName">Holiday Name</span>
                                                </td><!--<td>
                                                     <span id="dgrHolidayView_ctl01_lblLocationName">Location Name</span>
                                                </td><td>
                                                    <span id="dgrHolidayView_ctl01_lblOptional">Optional</span>
                                                </td> -->
		</tr><tr class="data" style="height: 25px;">
			<td>
                                                    <span id="dgrHolidayView_ctl02_GrdHolidayDate" class="DgItems">26th January 2012</span>
                                                </td><td>
                                                    <span id="dgrHolidayView_ctl02_GrdHolidayDay" class="DgItems">Thursday</span>
                                                </td><td>
                                                    <span id="dgrHolidayView_ctl02_GrdHolidayName" class="DgItems">Republic Day</span>
                                                </td><!-- <td>
                                                    <span id="dgrHolidayView_ctl02_GrdLocationName" class="DgItems">Bengaluru</span>
                                                </td><td>
                                                    <span id="dgrHolidayView_ctl02_GrdOptional" class="DgItems"></span>
                                                </td> -->
		</tr><tr class="data" style="background-color: rgb(248, 248, 248); height: 25px;">
			<td>
                                                    <span id="dgrHolidayView_ctl03_GrdHolidayDate" class="DgItems">20th February 2012</span>
                                                </td><td>
                                                    <span id="dgrHolidayView_ctl03_GrdHolidayDay" class="DgItems">Monday</span>
                                                </td><td>
                                                    <span id="dgrHolidayView_ctl03_GrdHolidayName" class="DgItems">MahaSivarathri</span>
                                                </td><!-- <td>
                                                    <span id="dgrHolidayView_ctl03_GrdLocationName" class="DgItems">Bengaluru</span>
                                                </td><td>
                                                    <span id="dgrHolidayView_ctl03_GrdOptional" class="DgItems">Optional</span>
                                                </td> -->
		</tr><tr class="data" style="height: 25px;">
			<td>
                                                    <span id="dgrHolidayView_ctl04_GrdHolidayDate" class="DgItems">9th March 2012</span>
                                                </td><td>
                                                    <span id="dgrHolidayView_ctl04_GrdHolidayDay" class="DgItems">Friday</span>
                                                </td><td>
                                                    <span id="dgrHolidayView_ctl04_GrdHolidayName" class="DgItems">Holi</span>
                                                </td><!-- <td>
                                                    <span id="dgrHolidayView_ctl04_GrdLocationName" class="DgItems">Bengaluru</span>
                                                </td><td>
                                                    <span id="dgrHolidayView_ctl04_GrdOptional" class="DgItems">Optional</span>
                                                </td> -->
		</tr><tr class="data" style="background-color: rgb(248, 248, 248); height: 25px;">
			<td>
                                                    <span id="dgrHolidayView_ctl05_GrdHolidayDate" class="DgItems">23rd March 2012</span>
                                                </td><td>
                                                    <span id="dgrHolidayView_ctl05_GrdHolidayDay" class="DgItems">Friday</span>
                                                </td><td>
                                                    <span id="dgrHolidayView_ctl05_GrdHolidayName" class="DgItems">Telugu New Year /Chandramana Ugadi</span>
                                                </td><!-- <td>
                                                    <span id="dgrHolidayView_ctl05_GrdLocationName" class="DgItems">Bengaluru</span>
                                                </td><td>
                                                    <span id="dgrHolidayView_ctl05_GrdOptional" class="DgItems"></span>
                                                </td> -->
		</tr><tr class="data" style="height: 25px;">
			<td>
                                                    <span id="dgrHolidayView_ctl06_GrdHolidayDate" class="DgItems">6th April 2012</span>
                                                </td><td>
                                                    <span id="dgrHolidayView_ctl06_GrdHolidayDay" class="DgItems">Friday</span>
                                                </td><td>
                                                    <span id="dgrHolidayView_ctl06_GrdHolidayName" class="DgItems">Good Friday</span>
                                                </td><!-- <td>
                                                    <span id="dgrHolidayView_ctl06_GrdLocationName" class="DgItems">Bengaluru</span>
                                                </td><td>
                                                    <span id="dgrHolidayView_ctl06_GrdOptional" class="DgItems"></span>
                                                </td> -->
		</tr><tr class="data" style="background-color: rgb(248, 248, 248); height: 25px;">
			<td>
                                                    <span id="dgrHolidayView_ctl07_GrdHolidayDate" class="DgItems">1st May 2012</span>
                                                </td><td>
                                                    <span id="dgrHolidayView_ctl07_GrdHolidayDay" class="DgItems">Tuesday</span>
                                                </td><td>
                                                    <span id="dgrHolidayView_ctl07_GrdHolidayName" class="DgItems">May Day</span>
                                                </td><!-- <td>
                                                    <span id="dgrHolidayView_ctl07_GrdLocationName" class="DgItems">Bengaluru</span>
                                                </td><td>
                                                    <span id="dgrHolidayView_ctl07_GrdOptional" class="DgItems"></span>
                                                </td> -->
		</tr><tr class="data" style="height: 25px;">
			<td>
                                                    <span id="dgrHolidayView_ctl08_GrdHolidayDate" class="DgItems">15th August 2012</span>
                                                </td><td>
                                                    <span id="dgrHolidayView_ctl08_GrdHolidayDay" class="DgItems">Wednesday</span>
                                                </td><td>
                                                    <span id="dgrHolidayView_ctl08_GrdHolidayName" class="DgItems">Independence Day</span>
                                                </td><!-- <td>
                                                    <span id="dgrHolidayView_ctl08_GrdLocationName" class="DgItems">Bengaluru</span>
                                                </td><td>
                                                    <span id="dgrHolidayView_ctl08_GrdOptional" class="DgItems"></span>
                                                </td> -->
		</tr><tr class="data" style="background-color: rgb(248, 248, 248); height: 25px;">
			<td>
                                                    <span id="dgrHolidayView_ctl09_GrdHolidayDate" class="DgItems">20th August 2012</span>
                                                </td><td>
                                                    <span id="dgrHolidayView_ctl09_GrdHolidayDay" class="DgItems">Monday</span>
                                                </td><td>
                                                    <span id="dgrHolidayView_ctl09_GrdHolidayName" class="DgItems">Ramzan</span>
                                                </td><!-- <td>
                                                    <span id="dgrHolidayView_ctl09_GrdLocationName" class="DgItems">Bengaluru</span>
                                                </td><td>
                                                    <span id="dgrHolidayView_ctl09_GrdOptional" class="DgItems">Optional</span>
                                                </td> -->
		</tr><tr class="data" style="height: 25px;">
			<td>
                                                    <span id="dgrHolidayView_ctl10_GrdHolidayDate" class="DgItems">29th August 2012</span>
                                                </td><td>
                                                    <span id="dgrHolidayView_ctl10_GrdHolidayDay" class="DgItems">Wednesday</span>
                                                </td><td>
                                                    <span id="dgrHolidayView_ctl10_GrdHolidayName" class="DgItems">Onam</span>
                                                </td><!-- <td>
                                                    <span id="dgrHolidayView_ctl10_GrdLocationName" class="DgItems">Bengaluru</span>
                                                </td><td>
                                                    <span id="dgrHolidayView_ctl10_GrdOptional" class="DgItems">Optional</span>
                                                </td> -->
		</tr><tr class="data" style="background-color: rgb(248, 248, 248); height: 25px;">
			<td>
                                                    <span id="dgrHolidayView_ctl11_GrdHolidayDate" class="DgItems">19th September 2012</span>
                                                </td><td>
                                                    <span id="dgrHolidayView_ctl11_GrdHolidayDay" class="DgItems">Wednesday</span>
                                                </td><td>
                                                    <span id="dgrHolidayView_ctl11_GrdHolidayName" class="DgItems">Vinayagar Chathurthi / Ganesh Chaturthi</span>
                                                </td><!-- <td>
                                                    <span id="dgrHolidayView_ctl11_GrdLocationName" class="DgItems">Bengaluru</span>
                                                </td><td>
                                                    <span id="dgrHolidayView_ctl11_GrdOptional" class="DgItems"></span>
                                                </td> -->
		</tr><tr class="data" style="height: 25px;">
			<td>
                                                    <span id="dgrHolidayView_ctl12_GrdHolidayDate" class="DgItems">2nd October 2012</span>
                                                </td><td>
                                                    <span id="dgrHolidayView_ctl12_GrdHolidayDay" class="DgItems">Tuesday</span>
                                                </td><td>
                                                    <span id="dgrHolidayView_ctl12_GrdHolidayName" class="DgItems">Gandhi Jayanthi</span>
                                                </td><!-- <td>
                                                    <span id="dgrHolidayView_ctl12_GrdLocationName" class="DgItems">Bengaluru</span>
                                                </td><td>
                                                    <span id="dgrHolidayView_ctl12_GrdOptional" class="DgItems"></span>
                                                </td> -->
		</tr><tr class="data" style="background-color: rgb(248, 248, 248); height: 25px;">
			<td>
                                                    <span id="dgrHolidayView_ctl13_GrdHolidayDate" class="DgItems">24th October 2012</span>
                                                </td><td>
                                                    <span id="dgrHolidayView_ctl13_GrdHolidayDay" class="DgItems">Wednesday</span>
                                                </td><td>
                                                    <span id="dgrHolidayView_ctl13_GrdHolidayName" class="DgItems">Vijayadhasami</span>
                                                </td><!-- <td>
                                                    <span id="dgrHolidayView_ctl13_GrdLocationName" class="DgItems">Bengaluru</span>
                                                </td><td>
                                                    <span id="dgrHolidayView_ctl13_GrdOptional" class="DgItems"></span>
                                                </td> -->
		</tr><tr class="data" style="height: 25px;">
			<td>
                                                    <span id="dgrHolidayView_ctl14_GrdHolidayDate" class="DgItems">1st November 2012</span>
                                                </td><td>
                                                    <span id="dgrHolidayView_ctl14_GrdHolidayDay" class="DgItems">Thursday</span>
                                                </td><td>
                                                    <span id="dgrHolidayView_ctl14_GrdHolidayName" class="DgItems">Karnataka Rajyotsava</span>
                                                </td><!-- <td>
                                                    <span id="dgrHolidayView_ctl14_GrdLocationName" class="DgItems">Bengaluru</span>
                                                </td><td>
                                                    <span id="dgrHolidayView_ctl14_GrdOptional" class="DgItems"></span>
                                                </td> -->
		</tr><tr class="data" style="background-color: rgb(248, 248, 248); height: 25px;">
			<td>
                                                    <span id="dgrHolidayView_ctl15_GrdHolidayDate" class="DgItems">14th November 2012</span>
                                                </td><td>
                                                    <span id="dgrHolidayView_ctl15_GrdHolidayDay" class="DgItems">Wednesday</span>
                                                </td><td>
                                                    <span id="dgrHolidayView_ctl15_GrdHolidayName" class="DgItems">Bali Padyami, Deepavali</span>
                                                </td><!-- <td>
                                                    <span id="dgrHolidayView_ctl15_GrdLocationName" class="DgItems">Bengaluru</span>
                                                </td><td>
                                                    <span id="dgrHolidayView_ctl15_GrdOptional" class="DgItems"></span>
                                                </td> -->
		</tr><tr class="data" style="height: 25px;">
			<td>
                                                    <span id="dgrHolidayView_ctl16_GrdHolidayDate" class="DgItems">25th December 2012</span>
                                                </td><td>
                                                    <span id="dgrHolidayView_ctl16_GrdHolidayDay" class="DgItems">Tuesday</span>
                                                </td><td>
                                                    <span id="dgrHolidayView_ctl16_GrdHolidayName" class="DgItems">Christmas</span>
                                                </td><!-- <td>
                                                    <span id="dgrHolidayView_ctl16_GrdLocationName" class="DgItems">Bengaluru</span>
                                                </td><td>
                                                    <span id="dgrHolidayView_ctl16_GrdOptional" class="DgItems"></span>
                                                </td> -->
		</tr>
	</tbody></table>
                                </div>
                            </div>


</div>
</body>
</html>