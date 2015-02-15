<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html>
<head>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.1/jquery.js"></script>
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.14/jquery-ui.min.js"></script>
<link rel="stylesheet" type="text/css" media="screen"
	href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.14/themes/base/jquery-ui.css">
<script type="text/javascript">
	$(function() {
		var startDate;
		var endDate;

		var selectCurrentWeek = function() {
			window.setTimeout(function() {
				$('.week-picker').find('.ui-datepicker-current-day a')
						.addClass('ui-state-active')
			}, 1);
		}

		$('.week-picker')
				.datepicker(
						{
							showOtherMonths : true,
							selectOtherMonths : true,
							onSelect : function(dateText, inst) {
								var date = $(this).datepicker('getDate');
								startDate = new Date(date.getFullYear(), date
										.getMonth(), date.getDate()
										- date.getDay());
								endDate = new Date(date.getFullYear(), date
										.getMonth(), date.getDate()
										- date.getDay() + 6);
								var dateFormat = inst.settings.dateFormat
										|| $.datepicker._defaults.dateFormat;
								$('#startDate').text(
										$.datepicker.formatDate(dateFormat,
												startDate, inst.settings));
								$('#endDate').text(
										$.datepicker.formatDate(dateFormat,
												endDate, inst.settings));
								$('#myId')
										.html(
												"<input type=\"button\" value=\"Generate Weekly Status\" onclick=\"callScript()\" style=\"font-family: verdana, arial, sans-serif;font-size: 11px;\"/>");

								selectCurrentWeek();
							},
							beforeShowDay : function(date) {
								var cssClass = '';
								if (date >= startDate && date <= endDate)
									cssClass = 'ui-datepicker-current-day';
								return [ true, cssClass ];
							},
							onChangeMonthYear : function(year, month, inst) {
								selectCurrentWeek();
							}
						});

		$('.week-picker .ui-datepicker-calendar tr').live('mousemove',
				function() {
					$(this).find('td a').addClass('ui-state-hover');
				});
		$('.week-picker .ui-datepicker-calendar tr').live('mouseleave',
				function() {
					$(this).find('td a').removeClass('ui-state-hover');
				});
	});
</script>
<script type="text/javascript">
	function callScript() {
		var startDate = document.getElementById("startDate").textContent;
		var endDate = document.getElementById("endDate").textContent;
		var formSubmit = document.forms[0];
		formSubmit.action = "\displayReport.do?method=getWeeklyStatusForm&startDate="+startDate+"&endDate="+endDate+"";
		formSubmit.submit();

	}
</script>


</head>
<body>
	<div
		style="margin-left: 50px; font-family: verdana, arial, sans-serif; font-size: 11px;">
		<html:form action="displayReport" method="post">
			<div class="week-picker"></div>
			<br />
			<br />
			<label>Week :</label>
			<span id="startDate" style="color: maroon; font-weight: bolder;"></span> - <span
				id="endDate" style="color: maroon; font-weight: bolder;"></span>
			<br />
			<div id="myId">
				<blink>Select Week from Calendar</blink>
			</div>
			<br></br>
			<logic:present name="saveStatus" scope="request">
			   <div style="font-size: 12px;color: blue;"> <input type="button" value="View and Send Via Mail" onclick="callScriptAgain()" style="font-family: verdana, arial, sans-serif; font-size: 11px;"/>
			   <bean:write name="saveStatus" />
			  
			   </div>
			</logic:present>
	   </html:form>
	</div>
</body>
</html:html>
