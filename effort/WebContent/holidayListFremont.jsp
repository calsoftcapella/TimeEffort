<html>
<head>
<script type="text/javascript">
	function doPage() {
		var e = document.getElementById("drpLocation");
		var strUser = e.options[e.selectedIndex].value;
		if (strUser == "L101") {
			document.Holiday_View.action = 'holidayListBangalore.jsp';
			document.Holiday_View.submit();
		} else if (strUser == "L100") {
			document.Holiday_View.action = 'holidayListChennai.jsp';
			document.Holiday_View.submit();
		} else if (strUser == "L102") {
			document.Holiday_View.action = 'holidayListMysore.jsp';
			document.Holiday_View.submit();
		} else if (strUser == "L103") {
			document.Holiday_View.action = 'holidayListBoston.jsp';
			document.Holiday_View.submit();
		} else if (strUser == "L104") {
			document.Holiday_View.action = 'holidayListFremont.jsp';
			document.Holiday_View.submit();
		} else if (strUser == "L105") {
			document.Holiday_View.action = 'holidayListFrance.jsp';
			document.Holiday_View.submit();
		}
	}
</script>
</head>
<body>
	<form name="Holiday_View" method="post">
		<div
			style="font-family: verdana, arial, sans-serif; font-size: 12px; font-weight: bold; color: #0000FF; text-align: left; position: absolute;">List
			of Holidays 2014</div>
		<div>
			<table>
				<tbody>
					<tr>
						<td align="right" width="800"><span id="lblLocation"
							style="font-family: verdana, arial, sans-serif; font-size: 11px;">Location</span>
							<select name="drpLocation" onchange="doPage();" id="drpLocation"
							style="font-family: verdana, arial, sans-serif; font-size: 11px;">
								<option value="L101"
									style="font-family: verdana, arial, sans-serif; font-size: 11px;">Bengaluru</option>
								<option value="L100"
									style="font-family: verdana, arial, sans-serif; font-size: 11px;">Chennai</option>
								<option value="L102"
									style="font-family: verdana, arial, sans-serif; font-size: 11px;">Mysore</option>
								<option value="L103"
									style="font-family: verdana, arial, sans-serif; font-size: 11px;">Boston</option>
								<option selected="selected" value="L104"
									style="font-family: verdana, arial, sans-serif; font-size: 11px;">Fremont</option>
								<option value="L105"
									style="font-family: verdana, arial, sans-serif; font-size: 11px;">France</option>

						</select></td>
					</tr>
				</tbody>
			</table>
		</div>
		<div>
			<table
				style="width: 100%; border-style: solid; border-width: 0.5px; border-collapse: collapse; font-family: verdana, arial, sans-serif; font-size: 11px;"
				border="1">
				<tbody>
					<tr style="background-color: #C0C0C0;font-weight: bolder;">
						<td><span>Holiday Date</span></td>
						<td><span>Day</span></td>
						<td><span>Holiday Name</span></td>
						<td><span>Optional</span></td>
					</tr>
					<tr style="height: 25px;">
						<td><span>1st January 2014</span></td>
						<td><span>Wednesday</span></td>
						<td><span>New Year's Day</span></td>
						<td><span></span></td>
					</tr>
					<tr style="background-color: #F8F8F8; height: 25px;">
						<td><span>20th January 2014</span></td>
						<td><span>Monday</span></td>
						<td><span>Martin Luther King Day</span></td>
						<td><span></span></td>
					</tr>
					<tr style="height: 25px;">
						<td><span>17th February 2014</span></td>
						<td><span>Monday</span></td>
						<td><span>Presidents Day and Washington's Birthday</span></td>
						<td><span></span></td>
					</tr>
					<tr style="background-color: #F8F8F8; height: 25px;">
						<td><span>18th April 2014</span></td>
						<td><span>Friday</span></td>
						<td><span>Good Friday</span></td>
						<td><span></span></td>
					</tr>
					<tr style="height: 25px;">
						<td><span>26th May 2014</span></td>
						<td><span>Monday</span></td>
						<td><span>Memorial Day</span></td>
						<td><span></span></td>
					</tr>
					<tr style="background-color: #F8F8F8; height: 25px;">
						<td><span>4th July 2014</span></td>
						<td><span>Friday</span></td>
						<td><span>Independence Day</span></td>
						<td><span></span></td>
					</tr>
					<tr style="height: 25px;">
						<td><span>1st September 2014</span></td>
						<td><span>Monday</span></td>
						<td><span>Labor Day</span></td>
						<td><span></span></td>
					</tr>
					<tr style="background-color: #F8F8F8; height: 25px;">
						<td><span>27th November 2014</span></td>
						<td><span>Thursday</span></td>
						<td><span>Thanksgiving</span></td>
						<td><span></span></td>
					</tr>
					<tr style="height: 25px;">
						<td><span>28th November 2014</span></td>
						<td><span>Friday</span></td>
						<td><span>Day after Thanksgiving</span></td>
						<td><span></span></td>
					</tr>
					<tr style="background-color: #F8F8F8; height: 25px;">
						<td><span>25th December 2014</span></td>
						<td><span>Thursday</span></td>
						<td><span>Christmas Day</span></td>
						<td><span></span></td>
					</tr>
				</tbody>
			</table>
		</div>
	</form>
</body>
</html>