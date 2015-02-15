<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
		<html>
			<body>
				Team,
				<br></br>
				<br>
					<div style="padding-left: 10px;">
						Please find below your PEPQ report for <xsl:value-of select="TimesheetReport/currentMonth/value" /> . 
						Kindly close your timesheet for the month by eod.
						<br><a href="http://pepq.calsoftlabs.com/effort/">http://pepq.calsoftlabs.com/effort/</a></br>
					</div>
				</br>
				<h2>Timesheet Report <xsl:value-of select="TimesheetReport/currentMonth/value" /></h2>
				<table
					style="font-family: verdana, arial,sans-serif;font-size: 11px;color: #333333;border-width: 1px;border-color: #999999;border-collapse: collapse;">
					<tr>    <!-- bgcolor="#808080" -->
						<th
							style="border-width: 1px;padding: 8px;border-style: solid;background-color: #A5A1A0;border-color: #a9c6c9;height: 10mm;width: 12mm;">Day</th>
						<xsl:for-each select="TimesheetReport/monthDays">
							<!-- <th align="left"> -->
							<!-- <xsl:value-of select="date"/> -->
							<xsl:choose>
								<xsl:when test="days = 'Sat '">
									<th
										style="border-width: 1px;padding: 8px;border-style: solid;background-color: #FFB6C1;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="days" />
									</th>
								</xsl:when>
								<xsl:when test="days = 'Sun '">
									<th
										style="border-width: 1px;padding: 8px;border-style: solid;background-color: #FFB6C1;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="days" />
									</th>
								</xsl:when>
								<xsl:otherwise>
									<th
										style="border-width: 1px;padding: 8px;border-style: solid;background-color: #A5A1A0;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="days" />
									</th>
								</xsl:otherwise>
							</xsl:choose>
						</xsl:for-each>
						<th align="left" rowspan="2" bgcolor="#A5A1A0">Total Time In Hrs.</th>
					</tr>
					<tr bgcolor="#A5A1A0">
						<th
							style="border-width: 1px;padding: 8px;border-style: solid;background-color: #A5A1A0;border-color: #a9c6c9;height: 10mm;width: 12mm;">Username</th>
						<xsl:for-each select="TimesheetReport/dateCount">
							<xsl:choose>
								<xsl:when test="date_seq = '1 '">
									<th
										style="border-width: 1px;padding: 8px;border-style: solid;background-color: #FFB6C1;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="date_seq" />
									</th>
								</xsl:when>
								<xsl:when test="date_seq = '2 '">
									<th
										style="border-width: 1px;padding: 8px;border-style: solid;background-color: #FFB6C1;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="date_seq" />
									</th>
								</xsl:when>
								<xsl:when test="date_seq = '3 '">
									<th
										style="border-width: 1px;padding: 8px;border-style: solid;background-color: #FFB6C1;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="date_seq" />
									</th>
								</xsl:when>
								<xsl:when test="date_seq = '4 '">
									<th
										style="border-width: 1px;padding: 8px;border-style: solid;background-color: #FFB6C1;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="date_seq" />
									</th>
								</xsl:when>
								<xsl:when test="date_seq = '5 '">
									<th
										style="border-width: 1px;padding: 8px;border-style: solid;background-color: #FFB6C1;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="date_seq" />
									</th>
								</xsl:when>
								<xsl:when test="date_seq = '6 '">
									<th
										style="border-width: 1px;padding: 8px;border-style: solid;background-color: #FFB6C1;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="date_seq" />
									</th>
								</xsl:when>
								<xsl:when test="date_seq = '7 '">
									<th
										style="border-width: 1px;padding: 8px;border-style: solid;background-color: #FFB6C1;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="date_seq" />
									</th>
								</xsl:when>
								<xsl:when test="date_seq = '8 '">
									<th
										style="border-width: 1px;padding: 8px;border-style: solid;background-color: #FFB6C1;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="date_seq" />
									</th>
								</xsl:when>
								<xsl:when test="date_seq = '9 '">
									<th
										style="border-width: 1px;padding: 8px;border-style: solid;background-color: #FFB6C1;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="date_seq" />
									</th>
								</xsl:when>
								<xsl:when test="date_seq = '10 '">
									<th
										style="border-width: 1px;padding: 8px;border-style: solid;background-color: #FFB6C1;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="date_seq" />
									</th>
								</xsl:when>
								<xsl:when test="date_seq = '11 '">
									<th
										style="border-width: 1px;padding: 8px;border-style: solid;background-color: #FFB6C1;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="date_seq" />
									</th>
								</xsl:when>
								<xsl:when test="date_seq = '12 '">
									<th
										style="border-width: 1px;padding: 8px;border-style: solid;background-color: #FFB6C1;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="date_seq" />
									</th>
								</xsl:when>
								<xsl:when test="date_seq = '13 '">
									<th
										style="border-width: 1px;padding: 8px;border-style: solid;background-color: #FFB6C1;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="date_seq" />
									</th>
								</xsl:when>
								<xsl:when test="date_seq = '14 '">
									<th
										style="border-width: 1px;padding: 8px;border-style: solid;background-color: #FFB6C1;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="date_seq" />
									</th>
								</xsl:when>
								<xsl:when test="date_seq = '15 '">
									<th
										style="border-width: 1px;padding: 8px;border-style: solid;background-color: #FFB6C1;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="date_seq" />
									</th>
								</xsl:when>
								<xsl:when test="date_seq = '16 '">
									<th
										style="border-width: 1px;padding: 8px;border-style: solid;background-color: #FFB6C1;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="date_seq" />
									</th>
								</xsl:when>
								<xsl:when test="date_seq = '17 '">
									<th
										style="border-width: 1px;padding: 8px;border-style: solid;background-color: #FFB6C1;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="date_seq" />
									</th>
								</xsl:when>
								<xsl:when test="date_seq = '18 '">
									<th
										style="border-width: 1px;padding: 8px;border-style: solid;background-color: #FFB6C1;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="date_seq" />
									</th>
								</xsl:when>
								<xsl:when test="date_seq = '19 '">
									<th
										style="border-width: 1px;padding: 8px;border-style: solid;background-color: #FFB6C1;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="date_seq" />
									</th>
								</xsl:when>
								<xsl:when test="date_seq = '20 '">
									<th
										style="border-width: 1px;padding: 8px;border-style: solid;background-color: #FFB6C1;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="date_seq" />
									</th>
								</xsl:when>
								<xsl:when test="date_seq = '21 '">
									<th
										style="border-width: 1px;padding: 8px;border-style: solid;background-color: #FFB6C1;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="date_seq" />
									</th>
								</xsl:when>
								<xsl:when test="date_seq = '22 '">
									<th
										style="border-width: 1px;padding: 8px;border-style: solid;background-color: #FFB6C1;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="date_seq" />
									</th>
								</xsl:when>
								<xsl:when test="date_seq = '23 '">
									<th
										style="border-width: 1px;padding: 8px;border-style: solid;background-color: #FFB6C1;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="date_seq" />
									</th>
								</xsl:when>
								<xsl:when test="date_seq = '24 '">
									<th
										style="border-width: 1px;padding: 8px;border-style: solid;background-color: #FFB6C1;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="date_seq" />
									</th>
								</xsl:when>
								<xsl:when test="date_seq = '25 '">
									<th
										style="border-width: 1px;padding: 8px;border-style: solid;background-color: #FFB6C1;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="date_seq" />
									</th>
								</xsl:when>
								<xsl:when test="date_seq = '26 '">
									<th
										style="border-width: 1px;padding: 8px;border-style: solid;background-color: #FFB6C1;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="date_seq" />
									</th>
								</xsl:when>
								<xsl:when test="date_seq = '27 '">
									<th
										style="border-width: 1px;padding: 8px;border-style: solid;background-color: #FFB6C1;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="date_seq" />
									</th>
								</xsl:when>
								<xsl:when test="date_seq = '28 '">
									<th
										style="border-width: 1px;padding: 8px;border-style: solid;background-color: #FFB6C1;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="date_seq" />
									</th>
								</xsl:when>
								<xsl:when test="date_seq = '29 '">
									<th
										style="border-width: 1px;padding: 8px;border-style: solid;background-color: #FFB6C1;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="date_seq" />
									</th>
								</xsl:when>
								<xsl:when test="date_seq = '30 '">
									<th
										style="border-width: 1px;padding: 8px;border-style: solid;background-color: #FFB6C1;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="date_seq" />
									</th>
								</xsl:when>
								<xsl:when test="date_seq = '31 '">
									<th
										style="border-width: 1px;padding: 8px;border-style: solid;background-color: #FFB6C1;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="date_seq" />
									</th>
								</xsl:when>
								<xsl:otherwise>
									<th
										style="border-width: 1px;padding: 8px;border-style: solid;background-color: #A5A1A0;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="date_seq" />
									</th>
								</xsl:otherwise>
							</xsl:choose>
						</xsl:for-each>
						<!-- <th align="left">Total Time In Hrs.</th> -->
					</tr>
					<xsl:for-each select="TimesheetReport/reportData">
						<tr>
							<td
								style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;background-color: #EBEBEB;">
								<xsl:value-of select="user_name" />
							</td>
							<xsl:choose>
								<xsl:when test="contains(time1, '  ')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: blue;">
										<xsl:value-of select="time1" />
									</td>
     							</xsl:when>
     							<xsl:when test="contains(time1, ' ')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: red;">
										<xsl:value-of select="time1" />
									</td>
     							</xsl:when>
     							<xsl:when test="contains(time1, '(C)')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: green;">
										<xsl:value-of select="time1" />
									</td>
     							</xsl:when>
     							<xsl:otherwise>
									<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="time1" />
							   	</td>
     							</xsl:otherwise>
     						</xsl:choose>		
							<xsl:choose>
								<xsl:when test="contains(time2, '  ')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: blue;">
										<xsl:value-of select="time2" />
									</td>
     							</xsl:when>
     							<xsl:when test="contains(time2, ' ')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: red;">
										<xsl:value-of select="time2" />
									</td>
     							</xsl:when>
     							<xsl:when test="contains(time2, '(C)')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: green;">
										<xsl:value-of select="time2" />
									</td>
     							</xsl:when>
     							<xsl:otherwise>
									<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="time2" />
							   	</td>
     							</xsl:otherwise>
     						</xsl:choose>
							<xsl:choose>
								<xsl:when test="contains(time3, '  ')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: blue;">
										<xsl:value-of select="time3" />
									</td>
     							</xsl:when>
     							<xsl:when test="contains(time3, ' ')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: red;">
										<xsl:value-of select="time3" />
									</td>
     							</xsl:when>
     							<xsl:when test="contains(time3, '(C)')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: green;">
										<xsl:value-of select="time3" />
									</td>
     							</xsl:when>
     							<xsl:otherwise>
									<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="time3" />
							   	</td>
     							</xsl:otherwise>
     						</xsl:choose>
     						<xsl:choose>
								<xsl:when test="contains(time4, '  ')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: blue;">
										<xsl:value-of select="time4" />
									</td>
     							</xsl:when>
     							<xsl:when test="contains(time4, ' ')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: red;">
										<xsl:value-of select="time4" />
									</td>
     							</xsl:when>
     							<xsl:when test="contains(time4, '(C)')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: green;">
										<xsl:value-of select="time4" />
									</td>
     							</xsl:when>
     							<xsl:otherwise>
									<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="time4" />
							   	</td>
     							</xsl:otherwise>
     						</xsl:choose>
							<xsl:choose>
								<xsl:when test="contains(time5, '  ')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: blue;">
										<xsl:value-of select="time5" />
									</td>
     							</xsl:when>
     							<xsl:when test="contains(time5, ' ')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: red;">
										<xsl:value-of select="time5" />
									</td>
     							</xsl:when>
     							<xsl:when test="contains(time5, '(C)')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: green;">
										<xsl:value-of select="time5" />
									</td>
     							</xsl:when>
     							<xsl:otherwise>
									<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="time5" />
							   	</td>
     							</xsl:otherwise>
     						</xsl:choose>
							<xsl:choose>
								<xsl:when test="contains(time6, '  ')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: blue;">
										<xsl:value-of select="time6" />
									</td>
     							</xsl:when>
     							<xsl:when test="contains(time6, ' ')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: red;">
										<xsl:value-of select="time6" />
									</td>
     							</xsl:when>
     							<xsl:when test="contains(time6, '(C)')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: green;">
										<xsl:value-of select="time6" />
									</td>
     							</xsl:when>
     							<xsl:otherwise>
									<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="time6" />
							   	</td>
     							</xsl:otherwise>
     						</xsl:choose>
							<xsl:choose>
								<xsl:when test="contains(time7, '  ')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: blue;">
										<xsl:value-of select="time7" />
									</td>
     							</xsl:when>
     							<xsl:when test="contains(time7, ' ')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: red;">
										<xsl:value-of select="time7" />
									</td>
     							</xsl:when>
     							<xsl:when test="contains(time7, '(C)')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: green;">
										<xsl:value-of select="time7" />
									</td>
     							</xsl:when>
     							<xsl:otherwise>
									<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="time7" />
							   	</td>
     							</xsl:otherwise>
     						</xsl:choose>
							<xsl:choose>
								<xsl:when test="contains(time8, '  ')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: blue;">
										<xsl:value-of select="time8" />
									</td>
     							</xsl:when>
     							<xsl:when test="contains(time8, ' ')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: red;">
										<xsl:value-of select="time8" />
									</td>
     							</xsl:when>
     							<xsl:when test="contains(time8, '(C)')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: green;">
										<xsl:value-of select="time8" />
									</td>
     							</xsl:when>
     							<xsl:otherwise>
									<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="time8" />
							   	</td>
     							</xsl:otherwise>
     						</xsl:choose>
							<xsl:choose>
								<xsl:when test="contains(time9, '  ')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: blue;">
										<xsl:value-of select="time9" />
									</td>
     							</xsl:when>
     							<xsl:when test="contains(time9, ' ')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: red;">
										<xsl:value-of select="time9" />
									</td>
     							</xsl:when>
     							<xsl:when test="contains(time9, '(C)')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: green;">
										<xsl:value-of select="time9" />
									</td>
     							</xsl:when>
     							<xsl:otherwise>
									<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="time9" />
							   	</td>
     							</xsl:otherwise>
     						</xsl:choose>
							<xsl:choose>
								<xsl:when test="contains(time10, '  ')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: blue;">
										<xsl:value-of select="time10" />
									</td>
     							</xsl:when>
     							<xsl:when test="contains(time10, ' ')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: red;">
										<xsl:value-of select="time10" />
									</td>
     							</xsl:when>
     							<xsl:when test="contains(time10, '(C)')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: green;">
										<xsl:value-of select="time10" />
									</td>
     							</xsl:when>
     							<xsl:otherwise>
									<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="time10" />
							   	</td>
     							</xsl:otherwise>
     						</xsl:choose>							
							<xsl:choose>
								<xsl:when test="contains(time11, '  ')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: blue;">
										<xsl:value-of select="time11" />
									</td>
     							</xsl:when>
     							<xsl:when test="contains(time11, ' ')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: red;">
										<xsl:value-of select="time11" />
									</td>
     							</xsl:when>
     							<xsl:when test="contains(time11, '(C)')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: green;">
										<xsl:value-of select="time11" />
									</td>
     							</xsl:when>
     							<xsl:otherwise>
									<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="time11" />
							   	</td>
     							</xsl:otherwise>
     						</xsl:choose>	
							<xsl:choose>
								<xsl:when test="contains(time12, '  ')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: blue;">
										<xsl:value-of select="time12" />
									</td>
     							</xsl:when>
     							<xsl:when test="contains(time12, ' ')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: red;">
										<xsl:value-of select="time12" />
									</td>
     							</xsl:when>
     							<xsl:when test="contains(time12, '(C)')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: green;">
										<xsl:value-of select="time12" />
									</td>
     							</xsl:when>
     							<xsl:otherwise>
									<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="time12" />
							   	</td>
     							</xsl:otherwise>
     						</xsl:choose>
							<xsl:choose>
								<xsl:when test="contains(time13, '  ')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: blue;">
										<xsl:value-of select="time13" />
									</td>
     							</xsl:when>
     							<xsl:when test="contains(time13, ' ')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: red;">
										<xsl:value-of select="time13" />
									</td>
     							</xsl:when>
     							<xsl:when test="contains(time13, '(C)')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: green;">
										<xsl:value-of select="time13" />
									</td>
     							</xsl:when>
     							<xsl:otherwise>
									<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="time13" />
							   	</td>
     							</xsl:otherwise>
     						</xsl:choose>
							<xsl:choose>
								<xsl:when test="contains(time14, '  ')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: blue;">
										<xsl:value-of select="time14" />
									</td>
     							</xsl:when>
     							<xsl:when test="contains(time14, ' ')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: red;">
										<xsl:value-of select="time14" />
									</td>
     							</xsl:when>
     							<xsl:when test="contains(time14, '(C)')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: green;">
										<xsl:value-of select="time14" />
									</td>
     							</xsl:when>
     							<xsl:otherwise>
									<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="time14" />
							   	</td>
     							</xsl:otherwise>
     						</xsl:choose>
							<xsl:choose>
								<xsl:when test="contains(time15, '  ')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: blue;">
										<xsl:value-of select="time15" />
									</td>
     							</xsl:when>
     							<xsl:when test="contains(time15, ' ')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: red;">
										<xsl:value-of select="time15" />
									</td>
     							</xsl:when>
     							<xsl:when test="contains(time15, '(C)')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: green;">
										<xsl:value-of select="time15" />
									</td>
     							</xsl:when>
     							<xsl:otherwise>
									<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="time15" />
							   	</td>
     							</xsl:otherwise>
     						</xsl:choose>
							<xsl:choose>
								<xsl:when test="contains(time16, '  ')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: blue;">
										<xsl:value-of select="time16" />
									</td>
     							</xsl:when>
     							<xsl:when test="contains(time16, ' ')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: red;">
										<xsl:value-of select="time16" />
									</td>
     							</xsl:when>
     							<xsl:when test="contains(time16, '(C)')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: green;">
										<xsl:value-of select="time16" />
									</td>
     							</xsl:when>
     							<xsl:otherwise>
									<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="time16" />
							   	</td>
     							</xsl:otherwise>
     						</xsl:choose>
							<xsl:choose>
								<xsl:when test="contains(time17, '  ')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: blue;">
										<xsl:value-of select="time17" />
									</td>
     							</xsl:when>
     							<xsl:when test="contains(time17, ' ')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: red;">
										<xsl:value-of select="time17" />
									</td>
     							</xsl:when>
     							<xsl:when test="contains(time17, '(C)')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: green;">
										<xsl:value-of select="time17" />
									</td>
     							</xsl:when>
     							<xsl:otherwise>
									<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="time17" />
							   	</td>
     							</xsl:otherwise>
     						</xsl:choose>
							<xsl:choose>
								<xsl:when test="contains(time18, '  ')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: blue;">
										<xsl:value-of select="time18" />
									</td>
     							</xsl:when>
     							<xsl:when test="contains(time18, ' ')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: red;">
										<xsl:value-of select="time18" />
									</td>
     							</xsl:when>
     							<xsl:when test="contains(time18, '(C)')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: green;">
										<xsl:value-of select="time18" />
									</td>
     							</xsl:when>
     							<xsl:otherwise>
									<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="time18" />
							   	</td>
     							</xsl:otherwise>
     						</xsl:choose>
							<xsl:choose>
								<xsl:when test="contains(time19, '  ')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: blue;">
										<xsl:value-of select="time19" />
									</td>
     							</xsl:when>
     							<xsl:when test="contains(time19, ' ')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: red;">
										<xsl:value-of select="time19" />
									</td>
     							</xsl:when>
     							<xsl:when test="contains(time19, '(C)')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: green;">
										<xsl:value-of select="time19" />
									</td>
     							</xsl:when>
     							<xsl:otherwise>
									<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="time19" />
							   	</td>
     							</xsl:otherwise>
     						</xsl:choose>
							<xsl:choose>
								<xsl:when test="contains(time20, '  ')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: blue;">
										<xsl:value-of select="time20" />
									</td>
     							</xsl:when>
     							<xsl:when test="contains(time20, ' ')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: red;">
										<xsl:value-of select="time20" />
									</td>
     							</xsl:when>
     							<xsl:when test="contains(time20, '(C)')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: green;">
										<xsl:value-of select="time20" />
									</td>
     							</xsl:when>
     							<xsl:otherwise>
									<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="time20" />
							   	</td>
     							</xsl:otherwise>
     						</xsl:choose>
     						<xsl:choose>
								<xsl:when test="contains(time21, '  ')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: blue;">
										<xsl:value-of select="time21" />
									</td>
     							</xsl:when>
     							<xsl:when test="contains(time21, ' ')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: red;">
										<xsl:value-of select="time21" />
									</td>
     							</xsl:when>
     							<xsl:when test="contains(time21, '(C)')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: green;">
										<xsl:value-of select="time21" />
									</td>
     							</xsl:when>
     							<xsl:otherwise>
									<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="time21" />
							   	</td>
     							</xsl:otherwise>
     						</xsl:choose>
     						<xsl:choose>
								<xsl:when test="contains(time22, '  ')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: blue;">
										<xsl:value-of select="time22" />
									</td>
     							</xsl:when>
     							<xsl:when test="contains(time22, ' ')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: red;">
										<xsl:value-of select="time22" />
									</td>
     							</xsl:when>
     							<xsl:when test="contains(time22, '(C)')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: green;">
										<xsl:value-of select="time22" />
									</td>
     							</xsl:when>
     							<xsl:otherwise>
									<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="time22" />
							   	</td>
     							</xsl:otherwise>
     						</xsl:choose>
     						<xsl:choose>
								<xsl:when test="contains(time23, '  ')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: blue;">
										<xsl:value-of select="time23" />
									</td>
     							</xsl:when>
     							<xsl:when test="contains(time23, ' ')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: red;">
										<xsl:value-of select="time23" />
									</td>
     							</xsl:when>
     							<xsl:when test="contains(time23, '(C)')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: green;">
										<xsl:value-of select="time23" />
									</td>
     							</xsl:when>
     							<xsl:otherwise>
									<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="time23" />
							   	</td>
     							</xsl:otherwise>
     						</xsl:choose>
     						<xsl:choose>
								<xsl:when test="contains(time24, '  ')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: blue;">
										<xsl:value-of select="time24" />
									</td>
     							</xsl:when>
     							<xsl:when test="contains(time24, ' ')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: red;">
										<xsl:value-of select="time24" />
									</td>
     							</xsl:when>
     							<xsl:when test="contains(time24, '(C)')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: green;">
										<xsl:value-of select="time24" />
									</td>
     							</xsl:when>
     							<xsl:otherwise>
									<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="time24" />
							   	</td>
     							</xsl:otherwise>
     						</xsl:choose>
							<xsl:choose>
								<xsl:when test="contains(time25, '  ')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: blue;">
										<xsl:value-of select="time25" />
									</td>
     							</xsl:when>
     							<xsl:when test="contains(time25, ' ')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: red;">
										<xsl:value-of select="time25" />
									</td>
     							</xsl:when>
     							<xsl:when test="contains(time25, '(C)')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: green;">
										<xsl:value-of select="time25" />
									</td>
     							</xsl:when>
     							<xsl:otherwise>
									<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="time25" />
							   	</td>
     							</xsl:otherwise>
     						</xsl:choose>
							<xsl:choose>
								<xsl:when test="contains(time26, '  ')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: blue;">
										<xsl:value-of select="time26" />
									</td>
     							</xsl:when>
     							<xsl:when test="contains(time26, ' ')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: red;">
										<xsl:value-of select="time26" />
									</td>
     							</xsl:when>
     							<xsl:when test="contains(time26, '(C)')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: green;">
										<xsl:value-of select="time26" />
									</td>
     							</xsl:when>
     							<xsl:otherwise>
									<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="time26" />
							   	</td>
     							</xsl:otherwise>
     						</xsl:choose>
     						<xsl:choose>
								<xsl:when test="contains(time27, '  ')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: blue;">
										<xsl:value-of select="time27" />
									</td>
     							</xsl:when>
     							<xsl:when test="contains(time27, ' ')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: red;">
										<xsl:value-of select="time27" />
									</td>
     							</xsl:when>
     							<xsl:when test="contains(time27, '(C)')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: green;">
										<xsl:value-of select="time27" />
									</td>
     							</xsl:when>
     							<xsl:otherwise>
									<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="time27" />
							   	</td>
     							</xsl:otherwise>
     						</xsl:choose>
     						<xsl:choose>
								<xsl:when test="contains(time28, '  ')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: blue;">
										<xsl:value-of select="time28" />
									</td>
     							</xsl:when>
     							<xsl:when test="contains(time28, ' ')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: red;">
										<xsl:value-of select="time28" />
									</td>
     							</xsl:when>
     							<xsl:when test="contains(time28, '(C)')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: green;">
										<xsl:value-of select="time28" />
									</td>
     							</xsl:when>
     							<xsl:otherwise>
									<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="time28" />
							   	</td>
     							</xsl:otherwise>
     						</xsl:choose>  						
							<xsl:if test="time29!='empty'">
							<xsl:choose>
								<xsl:when test="contains(time29, '  ')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: blue;">
										<xsl:value-of select="time29" />
									</td>
     							</xsl:when>
     							<xsl:when test="contains(time29, ' ')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: red;">
										<xsl:value-of select="time29" />
									</td>
     							</xsl:when>
     							<xsl:when test="contains(time29, '(C)')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: green;">
										<xsl:value-of select="time29" />
									</td>
     							</xsl:when>
     							<xsl:otherwise>
									<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="time29" />
							   	</td>
     							</xsl:otherwise>
     						</xsl:choose>
							</xsl:if>
							<xsl:if test="time30!='empty'">
							<xsl:choose>
								<xsl:when test="contains(time30, '  ')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: blue;">
										<xsl:value-of select="time30" />
									</td>
     							</xsl:when>
     							<xsl:when test="contains(time30, ' ')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: red;">
										<xsl:value-of select="time30" />
									</td>
     							</xsl:when>
     							<xsl:when test="contains(time30, '(C)')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: green;">
										<xsl:value-of select="time30" />
									</td>
     							</xsl:when>
     							<xsl:otherwise>
									<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="time30" />
							   	</td>
     							</xsl:otherwise>
     						</xsl:choose>
							</xsl:if>
							<xsl:if test="time31!='empty'">
							<xsl:choose>
								<xsl:when test="contains(time31, '  ')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: blue;">
										<xsl:value-of select="time31" />
									</td>
     							</xsl:when>
     							<xsl:when test="contains(time31, ' ')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: red;">
										<xsl:value-of select="time31" />
									</td>
     							</xsl:when>
     							<xsl:when test="contains(time31, '(C)')">     						
     								<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;color: green;">
										<xsl:value-of select="time31" />
									</td>
     							</xsl:when>
     							<xsl:otherwise>
									<td style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;">
										<xsl:value-of select="time31" />
							   	</td>
     							</xsl:otherwise>
     						</xsl:choose>
							</xsl:if>
							<td
								style="border-width: 1px;padding: 8px;border-style: solid;border-color: #a9c6c9;height: 10mm;width: 12mm;">
								<xsl:choose>
									<xsl:when test="total_time = 0.0">
									</xsl:when>
									<xsl:otherwise>
										<xsl:value-of select="total_time" />
									</xsl:otherwise>
								</xsl:choose>
							</td>
						</tr>
					</xsl:for-each>
				</table>
				<br></br>
				<h4>Note: Please ignore the email if its completed.</h4>
				<br></br>
				<br>
					Thanks and Regards
				</br>
				<div>Raghavi</div>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>