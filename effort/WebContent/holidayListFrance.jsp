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
	<form name="Holiday_View">
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
								<option value="L104"
									style="font-family: verdana, arial, sans-serif; font-size: 11px;">Fremont</option>
								<option selected="selected" value="L105"
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
					<tr style="background-color: #C0C0C0; font-weight: bolder;">
						<td><span>Holiday Date</span></td>
						<td><span>Day</span></td>
						<td><span>Holiday Name</span></td>
						<td><span>Optional</span></td>
					</tr>
					<tr style="font-weight: bolder;height: 120px;vertical-align: top;">
						<td colspan="4"><span style="color: red;">No Data to display.</span></td>
					</tr>
				</tbody>
			</table>
		</div>
	</form>
</body>
</html>