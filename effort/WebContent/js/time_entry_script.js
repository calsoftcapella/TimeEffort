/*
 *Script added for displaying help text  
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
}*/

var count = 1;
var count_calendar_id = 1;
function addRow(tableID, missingDate){ 
	if(checkSessionValidationBeforeAction()){
		return;
	}
	var currentDate = new Date();
	var current_date = currentDate.getDate();
	var current_month = currentDate.getMonth()+1; 
	var current_year = currentDate.getFullYear();
	if(current_date<10){
		current_date = "0"+current_date;
	}
	if(current_month<10){
		current_month = "0"+current_month;
	}
	document.getElementById('myDiv').style.visibility = "visible";
	document.getElementById('ajax_res').innerHTML= "";
	var tRow = document.getElementById(tableID).insertRow(1);
	tRow.id=count+"id";
	var td1 = tRow.insertCell(0);
	td1.id="td_dt";
	var strHtml1 = '';
	var strHtml5 = '';
	if(missingDate==''){
		strHtml1 = "<INPUT TYPE=\"text\" NAME=\"task_date\" title=\"Click to pick date from calendar.\" id=\"popupDatepicker\" readonly=\"true\" SIZE=\"5\" MAXLENGTH=\"10\" "
			+" value=\""+current_month+"/"+current_date+"/"+current_year+"\" "
			+" STYLE=\"height:4;width: 65px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\">";

		strHtml5 = "<textarea name=\"task_description\" title=\"Add description here.\" id=\"des_id"+count+"\" rows=\"2\" cols=\"2\" value=\"\" "
		+" STYLE=\"width: 160px;height: 100px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\"></textarea>";
	}
	else{
		strHtml1 = "<INPUT TYPE=\"text\" NAME=\"task_date\" title=\"Click to pick date from calendar.\" id=\"popupDatepicker\" readonly=\"true\" SIZE=\"5\" MAXLENGTH=\"10\" "
			+" value=\""+missingDate+"\" STYLE=\"height:4;width: 65px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\">";

		strHtml5 = "<textarea name=\"task_description\" title=\"Add description here.\" id=\"des_id"+count+"\" rows=\"2\" cols=\"2\" value=\"\" "
		+" STYLE=\"width: 160px;height: 100px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\">Effort not entered.</textarea>";
	}
	td1.innerHTML = strHtml1;
	// create table cell 2
	var td2 = tRow.insertCell(1);
	td2.id = "td_st";
	var strHtml2 = "<SELECT NAME=\"status\" id=\"status"+count+"\" title=\"Select appropriate category.\" onchange=\"changeDynamicTextBox(this);\" style=\"width: 120px;font-family: verdana, arial, sans-serif;font-size: 11px;\">"
	+"<option value=\"\">Select</option>"
	+"<option value=\"Code Review\">Code Review</option>"
	+"<option value=\"Comp off\">Comp off</option>"
	+"<option value=\"Development\">Development</option>"
	+"<option value=\"Down Time\">Down Time</option>"
	+"<option value=\"Half Day\">Half Day</option>"
	+"<option value=\"KT Session\">KT Session</option>"
	+"<option value=\"Leave\">Leave</option>"
	+"<option value=\"Maintenance/Release support\">Maintenance/Release support</option>"
	+"<option value=\"Meeting\">Meeting</option>"
	+"<option value=\"Pre-Project Training\">Pre-Project Training</option>"								
	+"<option value=\"Project Management\">Project Management</option>"																				
	+"<option value=\"Public holiday\">Public holiday</option>"
	+"<option value=\"Task unassigned/Idle\">Task unassigned/Idle</option>"	
	+"<option value=\"Team Activity\">Team Activity</option>"
	+"<option value=\"Testing\">Testing</option>"														
	+"<option value=\"Training\">Training</option>"													
	+"<option value=\"Travel\">Travel</option>"
	+"</SELECT>";         
	td2.innerHTML = strHtml2;         
	/* var defaultStatus = document.getElementById('defaultCategory').value;
           var selectOption = document.getElementById("status"+count);
           for (var i=0; i<selectOption.length; i++){
           if(selectOption.options[i].value==defaultStatus){
        	   selectOption.options[i].selected="true";
           }
         }      */ 
	// create table cell 3
	var td3 = tRow.insertCell(2);
	td3.id="td_back";
	var strHtml3 = "<INPUT TYPE=\"text\" id=\"backlog_id"+count+"\" value=\"NA\" title=\"Enter BacklogId.\" NAME=\"backlog_id\" SIZE=\"5\" MAXLENGTH=\"20\" "
	+" STYLE=\"height:4;width: 60px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\">";
	td3.innerHTML = strHtml3;
	// create table cell 4
	var td4 = tRow.insertCell(3);
	td4.id="td_task";
	var strHtml4 = "<INPUT TYPE=\"text\" id=\"task_id"+count+"\" value=\"NA\" NAME=\"task_id\" title=\"Enter TaskId.\" SIZE=\"5\" MAXLENGTH=\"20\" "
	+" STYLE=\"height:4;width: 60px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\" >";
	td4.innerHTML = strHtml4;
	// create table cell 5
	var td5 = tRow.insertCell(4);
	td5.id="td_des";
	/* var strHtml5 = "<textarea name=\"task_description\" title=\"Add description here.\" id=\"des_id"+count+"\" rows=\"2\" cols=\"2\" value=\"\" "
    	                +" STYLE=\"width: 160px;height: 100px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\"></textarea>";*/
	td5.innerHTML = strHtml5; 
	// create table cell 6
	var td6 = tRow.insertCell(5);
	td6.id="td_work_status"+count;
	var strHtml6 = "<input type=\"radio\" title=\"Pick Effort From\"  name=\"work_status"+count+"\" id=\"work_status1"+count+"\" value=\"office\" checked> Office"
	+"<input type=\"radio\" title=\"Pick Effort From\"  name=\"work_status"+count+"\" id=\"work_status2"+count+"\" value=\"home\">Home";
	td6.innerHTML = strHtml6;
	// create table cell 7
	var td7 = tRow.insertCell(6);
	var strHtml7 = "<INPUT TYPE=\"text\" NAME=\"time\" title=\"eg:HH.MM, HH:MM\" id=\"time"+count+"\"  SIZE=\"5\" MAXLENGTH=\"5\" value=\"\" "
	+" STYLE=\"height:4;width: 40px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\">";
	td7.innerHTML = strHtml7;
	// create table cell 8
	var td8 = tRow.insertCell(7);
	var strHtml8 = "<img alt=\"1px\" src=\"img/save.gif\" id=\"edit"+count+"\" title=\"Click to save entry.\" SIZE=\"5\"  onClick=\"callAjax(this)\" "
	+" style=\"height: 20px;width: 20px;font-family: verdana, arial, sans-serif;font-size: 11px;cursor: pointer;\">";   	
	td8.innerHTML = strHtml8;

	var td9 = tRow.insertCell(8);
	var strHtml9 = "<img alt=\"1\" src=\"img/minus.png\" SIZE=\"5\" onClick='deleteRow(this);' "
		+" style=\"height: 20px;width: 20px;font-family: verdana, arial, sans-serif;font-size: 11px;cursor: pointer;\">";   	
	td9.innerHTML = strHtml9;
	document.getElementById("status"+count).focus();
	$('#popupDatepicker').datepick();
	count++;
}
function checkForPublicHoliday(currentObj){
	var currentRow = document.getElementById(currentObj.id).parentNode.parentNode;
	var dateTd = currentRow.getElementsByTagName('td')[0];
	var selectedDate = dateTd.firstChild.value;
	var holidayArray = null;
	var optionalHolidayDates = null;
	var location = $('#resourceLocationId').val();
	if(location=='Bangalore' ||location=='Chennai' ){
		if(location=='Bangalore'){
			holidayArray = new Array("01/01/2014","01/14/2014","01/26/2014","03/31/2014","04/18/2014","05/01/2014",
					"08/15/2014","08/29/2014","10/02/2014","10/03/2014","10/22/2014","11/01/2014","12/25/2014");
			
			optionalHolidayDates = new Array("01/13/2014","03/17/2014","07/29/2014","09/08/2014");
		}
		else if(location=='Chennai'){
			holidayArray = new Array("01/01/2014","01/14/2014","01/26/2014","04/14/2014",
					"04/18/2014","05/01/2014","08/15/2014","08/29/2014","10/02/2014","10/03/2014","10/22/2014","12/25/2014");
			
			optionalHolidayDates = new Array("01/13/2014","03/17/2014","07/29/2014","09/08/2014");
		}
		for ( var i = 0; i < holidayArray.length; i++) {
			var checkDate = holidayArray[i];
			if (selectedDate == checkDate) {
				var descriptionTd = currentRow.getElementsByTagName('td')[4];
				descriptionTd.firstChild.value = "It is public holiday.";
				return true;
			}
		}
		for ( var i = 0; i < optionalHolidayDates.length; i++) {
			var checkDate = optionalHolidayDates[i];
			if (selectedDate == checkDate) {
				var descriptionTd = currentRow.getElementsByTagName('td')[4];
				descriptionTd.firstChild.value = "It is optional holiday.";
				return true;
			}
		}
	}
	return false;
}
function hideLoadingImage(){
	$.unblockUI();
}
function showLoadingImage(){
	$.blockUI({
		message : $('#loading_image'),
		css : {
			top : ($(window).height() - 400) / 2 + 'px',
			left : ($(window).width() - 400) / 2 + 'px',
			width : '25%',
			height : '25%',
			opacity : '.9',
			cursor : 'wait'
		}
	});
}
function changeDynamicTextBox(object){  
	var i=object.parentNode.parentNode.rowIndex;  
	var table = document.getElementById('dailyEntryTable');
	var tableTr = table.getElementsByTagName('tr')[i];
	var nCell = tableTr.getElementsByTagName('td');        
	var status = nCell[1].firstChild.value;
	var backLog = nCell[2].firstChild.value;
	var taskLog = nCell[3].firstChild.value;
	var description_text = nCell[4].firstChild.value; 

	//Changed Radio button to Home	    
	var radioLength = nCell[5].getElementsByTagName('input').length;       	
	// Update ends here	    
	var backLogID = nCell[2].firstChild.id;
	var taskLogID = nCell[3].firstChild.id;
	var description_textID = nCell[4].firstChild.id;     	     	     	
	var timeID = nCell[6].firstChild.id;    	
	document.getElementById("ajax_res").innerHTML ="";
	// Checking Public Holiday status with function 'checkForPublicHoliday' and updating description field.
	if((!checkForPublicHoliday(object)) && 
			(description_text=='Please mention the additional date that you worked to take Comp off.'|| description_text=='It is public holiday.' || description_text == 'It is optional holiday.')){
		document.getElementById(description_textID).value="";
	}
	if(status == "Leave" || status == "Half Day" || status == "Travel" || status == "Down Time" || status == "Public holiday" ) {
		if(backLog==''){
			document.getElementById(backLogID).value = 'NA';
		}
		if(taskLog==''){
			document.getElementById(taskLogID).value = 'NA';
		}
		if(status == "Public holiday"){
			document.getElementById(description_textID).value = 'It is public holiday.';
			document.getElementById(timeID).value = '0.0';
			document.getElementById(description_textID).disabled = 'true';
			document.getElementById(timeID).disabled = 'true';
			//Change made for selecting radio button for Home option
			for (var p = 0; p < radioLength; p++){
				var work_status = nCell[5].getElementsByTagName('input')[p].value;
				var work_statusId = nCell[5].getElementsByTagName('input')[p].id;
				if(work_status=='office'){
					$("#"+work_statusId).removeAttr('checked');
					$("#"+work_statusId).attr('disabled', 'disabled');
				}
				else{
					$("#"+work_statusId).attr('checked', 'true');
				}
			}
			/*Update ends here.*/
		}
		else if(status == "Leave"){
			document.getElementById(timeID).value = '0.0';
		}
		else if(status == "Half Day"){
			document.getElementById(timeID).value = '4.0';
		}
		else if(status == "Travel" || status == "Down Time"){
			if(document.getElementById(timeID).value == ''){
				document.getElementById(timeID).value = '';
			}
		}
		if(status == "Leave" || status == "Half Day" || status == "Travel" || status == "Down Time"){
			document.getElementById(description_textID).disabled = false;
			if(status == "Leave"){
				document.getElementById(timeID).disabled = true;
			}
			else{
				document.getElementById(timeID).disabled = false;
			}
			if(status == "Leave" || status == "Half Day"){
				//Change made for selecting radio button for Home option
				for (var p = 0; p < radioLength; p++){
					var workSt = nCell[5].getElementsByTagName('input')[p].value;
					var workStId = nCell[5].getElementsByTagName('input')[p].id;
					if(workSt=='office'){
						if(nCell[5].getElementsByTagName('input')[p].checked){		     			
							$("#"+workStId).removeAttr('checked');
						}
					}
					else{
						if(!nCell[5].getElementsByTagName('input')[p].checked){
							$("#"+workStId).removeAttr('disabled');
							$("#"+workStId).attr('checked', 'true');
						}
					}
				}
			}
			else if(status == "Travel" || status == "Down Time"){
				//Change made for selecting radio button for Office option
				for (var p = 0; p < radioLength; p++){
					var workSt = nCell[5].getElementsByTagName('input')[p].value;
					var workStId = nCell[5].getElementsByTagName('input')[p].id;
					if(workSt=='home'){
						if(nCell[5].getElementsByTagName('input')[p].checked){		     			
							$("#"+workStId).removeAttr('checked');
						}
					}
					else{
						if(!nCell[5].getElementsByTagName('input')[p].checked){
							$("#"+workStId).removeAttr('disabled');
							$("#"+workStId).attr('checked', 'true');
						}
					}
				}
			}			
		}
		document.getElementById(backLogID).disabled = 'true';
		document.getElementById(taskLogID).disabled = 'true';
	}
	else if(status == "Comp off"){
		if(backLog==''){
			document.getElementById(backLogID).value = 'NA';
		}
		if(taskLog==''){
			document.getElementById(taskLogID).value = 'NA';
		}
		document.getElementById(backLogID).disabled = 'true';
		document.getElementById(taskLogID).disabled = 'true';
		document.getElementById(timeID).disabled = false;
		document.getElementById(timeID).value = '';
		//Change made for selecting radio button for Home option
		for (var p = 0; p < radioLength; p++){
			var workSt = nCell[5].getElementsByTagName('input')[p].value;
			var workStId = nCell[5].getElementsByTagName('input')[p].id;
			if(workSt=='home'){
				if(!nCell[5].getElementsByTagName('input')[p].checked){
					$("#"+workStId).removeAttr('disabled');
					$("#"+workStId).attr('checked', 'true');
				}
			}
			else{
				if(nCell[5].getElementsByTagName('input')[p].checked){
					$("#"+workStId).attr('disabled', 'disabled');
					$("#"+workStId).removeAttr('checked');				    
				}
			}
		}		
		/*Updates ends here.*/		
		alert("Please get approval email from your onsite lead for Comp offs and send it to Hem. It would be treated as leave if it is not approved by your onsite lead.");
		document.getElementById(description_textID).value = 'Please mention the additional date that you worked to take Comp off.';		
		$('#'+description_textID).focus();
	}
	else{
		if(backLog==''){
			document.getElementById(backLogID).value = 'NA';
		}
		if(taskLog==''){
			document.getElementById(taskLogID).value = 'NA';
		}
		if(document.getElementById(timeID).value=='0.0' || document.getElementById(timeID).value=='4.0'){
			document.getElementById(timeID).value = '';
		}
		document.getElementById(backLogID).disabled = false;
		document.getElementById(taskLogID).disabled = false;
		document.getElementById(timeID).disabled = false;
		document.getElementById(description_textID).disabled = false;
		//Change made for selecting radio button for Office option
		for (var p = 0; p < radioLength; p++){
			var workSt = nCell[5].getElementsByTagName('input')[p].value;
			var workStId = nCell[5].getElementsByTagName('input')[p].id;
			if(workSt=='home'){
				if(nCell[5].getElementsByTagName('input')[p].checked){		     			
					$("#"+workStId).removeAttr('checked');
				}
			}
			else{
				if(!nCell[5].getElementsByTagName('input')[p].checked){
					$("#"+workStId).removeAttr('disabled');
					$("#"+workStId).attr('checked', 'true');
				}
			}
		}
		/*Updates ends here.*/		
	}
	document.getElementById(backLogID).focus();
}
function onDelete(url, currentObj){
	$('#ajax_res').html("");
	var table = currentObj.parentNode.parentNode.parentNode;
	var row = table.getElementsByTagName('tr');	
	var trid = $(currentObj).closest('tr').attr('id');	
	$("#"+trid).css("background-color","#FF9696");	
	/*var check = confirm("Do you want to delete this entry ?");*/	
	$.confirm({
		'title'		: 'Delete Confirmation',
		'message'	: 'You are want to delete this entry ?',
		'buttons'	: {
			'Yes'	: {
				'class'	: 'blue',
				'action': function(){
					$.ajax({
						type: "POST",
						url: url,
						data: "",
						success: function(response){
							if(response=='session expired'){
								document.getElementById('myDiv').style.visibility = "hidden";
								document.getElementById('save_id_buttonId').style.visibility = "hidden";
								document.getElementById('new_entry_buttonId').style.visibility = "hidden";
								document.getElementById('myMissingDates').style.visibility = "hidden";
								if($("#freeze_entry_id").length != 0){document.getElementById('freeze_entry_id').style.visibility = "hidden";}
								document.getElementById("ajax_res").innerHTML ="<div style='font-family: verdana, arial, sans-serif;font-size: 13px;color: red;text-align: center;'>"
									+"Your Session has been expired please <a href='http://pepq.calsoftlabs.com/effort/' target='_blank'>relogin</a> to retain your session.</div>"; 					        		   
							}
							else if(row.length==2){
								document.getElementById("dailyEntryTable").deleteRow(1);
								document.getElementById('myDiv').style.visibility = "hidden";
								document.getElementById('ajax_res').innerHTML="<div style=\"color: red;text-align: center;font-family: verdana, arial, sans-serif;font-size: 11px;\">"
									+"*Please click on New Entry button to add an entry.</div>";
							}
							else{
								document.getElementById("dailyEntryTable").deleteRow(currentObj.parentNode.parentNode.rowIndex);
								document.getElementById('ajax_res').innerHTML="<div style=\"color: blue;text-align: center;font-family: verdana, arial, sans-serif;font-size: 11px;\">"
									+"Data deleted succesfully.</div>";
							}
						},
						beforeSend: function(){
							showLoadingImage();
						},			
						complete: function(){
							hideLoadingImage();
							hideEnteredMissingdate();
						}, 
						error: function(e){  
							document.getElementById('myDiv').style.visibility = "hidden";
							document.getElementById('save_id_buttonId').style.visibility = "hidden";
							document.getElementById('new_entry_buttonId').style.visibility = "hidden";
							document.getElementById('myMissingDates').style.visibility = "hidden";
							if($("#freeze_entry_id").length != 0){document.getElementById('freeze_entry_id').style.visibility = "hidden";}
							document.getElementById("ajax_res").innerHTML ="<div style='font-family: verdana, arial, sans-serif;font-size: 13px;color: red;text-align: center;'>"
								+"Your Session has been expired please <a href='http://pepq.calsoftlabs.com/effort/' target='_blank'>relogin</a> to retain your session.</div>"; 					        		   
						}
					});
				}
			},
			'No'	: {
				'class'	: 'gray',
				'action': function(){
					setTimeout( function(){$("table[id=dailyEntryTable] tr:nth-child(even)").css({"background": "#ffffff"});
					$("table[id=dailyEntryTable] tr:nth-child(odd)").css({"background": "#eeeeee"});}, 500 );					
				}	// Nothing to do in this case. You can as well omit the action property.
			}
		}
	});
}
function getParsedTime(time){
	time = time.replace(":", ".");   	
	var timeLength = time.length;	
	if (timeLength > 4) {
		if(time.indexOf('.')==1){
			if(time.charAt(3) == '0' && time.charAt(4)=='0'){
				time = time.charAt(0)+""+time.charAt(1)+""+time.charAt(2);
			}
			else if(time.charAt(4) != '0'){
				time = time.charAt(0)+""+time.charAt(1)+""+time.charAt(2)+""+time.charAt(3);
			}
		}
		else if(time.indexOf('.')==2){
			if(time.charAt(0) == '0' && time.charAt(4)=='0'){
				time = time.charAt(1)+""+time.charAt(2)+""+time.charAt(3);
			}
			else if(time.charAt(0) == '0' && time.charAt(4)!='0'){
				time = time.charAt(1)+""+time.charAt(2)+""+time.charAt(3)+""+time.charAt(4);
			}
		}					
	}
	else if(timeLength > 3){
		if(time.indexOf('.')==1){
			if(time.charAt(3) == '0'){
				time = time.charAt(0)+""+time.charAt(1)+""+time.charAt(2);
			}
		}
		else if(time.indexOf('.')==2){
			if(time.charAt(0) == '0'){
				time = time.charAt(1)+""+time.charAt(2)+""+time.charAt(3);
			}
		}
	}			
	if(time.indexOf('.')==-1){	
		if(time.charAt(0) == '0' & time.length == 1){
			time = time.charAt(0)+".0";
		}
		else if(time.charAt(0) == '0' & time.length > 1){
			time = time.charAt(1)+".0";
		}
		else if(time.charAt(0) != '0'){
			time = time.charAt(0)+""+time.charAt(1)+".0";
		}
	}
	return time;
}
function deleteRow(currentObject){
	try{                
		var i=currentObject.parentNode.parentNode.rowIndex;
		var table = currentObject.parentNode.parentNode.parentNode;
		var row = table.getElementsByTagName('tr');	
		if(row.length==2){
			document.getElementById("dailyEntryTable").deleteRow(i);
			document.getElementById('myDiv').style.visibility = "hidden";
		}
		else{
			document.getElementById('dailyEntryTable').deleteRow(i);    
		}
	}
	catch(e){
		alert(e);
	}
	count--;
}
function callAjax(currentObject){  
	document.getElementById("ajax_res").innerHTML ="";
	var i=currentObject.parentNode.parentNode.rowIndex;  
	var idVal = document.getElementById('dailyEntryTable').rows[i].id;
	var table = document.getElementById('dailyEntryTable');
	var tableTr = table.getElementsByTagName('tr')[i];
	var nCell = tableTr.getElementsByTagName('td');        
	var date = nCell[0].firstChild.value;
	var status = nCell[1].firstChild.value;
	var backlog_id = nCell[2].firstChild.value;
	var task_id = nCell[3].firstChild.value;
	var task_description = nCell[4].firstChild.value;     	
	var work_status = '';     	
	var radioLength = nCell[5].getElementsByTagName('input').length;   
	var taskJson = new Array();	
	for (var p = 0; p < radioLength; p++){
		if(nCell[5].getElementsByTagName('input')[p].checked){
			work_status = nCell[5].getElementsByTagName('input')[p].value;
		}
	}
	var capitalizeWorkStatus = null;
	if(work_status=='home'){
		capitalizeWorkStatus = 'Home';
	}
	else if(work_status=='office'){
		capitalizeWorkStatus = 'Office';
	}      	
	var timeValue = nCell[6].firstChild.value; 
	if(doValidation(date, status, backlog_id, task_id, timeValue, idVal)) {  	
		timeValue = getParsedTime(timeValue);     		
		var selectOption = document.getElementById("userId"); 
		var userId = 0;
		if(selectOption!=null){
			userId = $('#userId option:selected').val();
		}   
		taskJson.push({ "date": date, "status": status, "backlog_id": backlog_id, "task_id" :task_id,"task_description":task_description,"work_status":work_status, "time":timeValue, "userId":userId});
		$.ajax({
			type: "POST",
			url: 'taskActionAfterAjaxSave.do?method=saveAjax',
			//data: 'task_date='+date+'&status='+status+'&backlog_id='+backlog_id+'&task_id='+task_id+'&time='+timeValue+'&task_description='+task_description+'&work_status='+work_status,
			data: { taskJson: JSON.stringify(taskJson)},
			success: function(response){
				if(response=="Invalid time Entry"){
					document.getElementById("ajax_res").innerHTML ="<div style='font-family: verdana, arial, sans-serif;font-size: 11px;color: red;text-align: center;'>Invalid time Entry, Time should not more than 23Hrs.</div>";
				}
				else if(response=="session expired"){
					//document.getElementById('myDiv').style.visibility = "hidden";
					document.getElementById('save_id_buttonId').style.visibility = "hidden";
					document.getElementById('new_entry_buttonId').style.visibility = "hidden";
					document.getElementById('myMissingDates').style.visibility = "hidden";
					if($("#freeze_entry_id").length != 0){if($("#freeze_entry_id").length != 0){document.getElementById('freeze_entry_id').style.visibility = "hidden";}}
					document.getElementById("ajax_res").innerHTML ="<div style='font-family: verdana, arial, sans-serif;font-size: 13px;color: red;text-align: center;'>"
						+"Your Session has been expired please <a href='http://pepq.calsoftlabs.com/effort/' target='_blank'>relogin</a> to retain your session.</div>"; 
				}
				else
				{
					if(status=="Leave"||status=="Half Day"){
						$('#'+idVal).html("<td>"+date+"</td><td>"+status+"</td><td>"+backlog_id+"</td><td>"+task_id+"</td><td>"+task_description+"</td><td>"+capitalizeWorkStatus+"</td>"
								+"<td style=\"color: #FF0000;\">"+timeValue+"</td><td><img src='img/edit.png' title=\"Click to edit entry.\" onclick='editRowEntry(this,"+response+")' style='cursor: pointer;'/></td><td>"
								+"<img src='img/delete1.png' title=\"Click to delete entry.\" onclick='onDelete(\"taskAction1.do?method=delete&id="+response+"\", this)' style='cursor: pointer;' /></td>");        		 
					}
					else if(status=="Public holiday"){
						$('#'+idVal).html("<td>"+date+"</td><td>"+status+"</td><td>"+backlog_id+"</td><td>"+task_id+"</td><td>"+task_description+"</td><td>"+capitalizeWorkStatus+"</td>"
								+"<td style=\"color: #0000CD;\">"+timeValue+"</td><td><img src='img/edit.png' title=\"Click to edit entry.\" onclick='editRowEntry(this,"+response+")' style='cursor: pointer;'/></td>"
								+"<td><img src='img/delete1.png' title=\"Click to delete entry.\" onclick='onDelete(\"taskAction1.do?method=delete&id="+response+"\", this)' style='cursor: pointer;' /></td>");
					}
					else if(status=="Comp off"){
						$('#'+idVal).html("<td style=\"color: #008000;\">"+date+"</td><td style=\"color: #008000;\">"+status+"</td><td style=\"color: #008000;\">"+backlog_id+"</td>"
								+"<td style=\"color: #008000;\">"+task_id+"</td><td style=\"color: #008000;\">"+task_description+"</td><td style=\"color: #008000;\">"+capitalizeWorkStatus+"</td>"
								+"<td style=\"color: #008000;\">"+timeValue+"</td><td><img src='img/edit.png' title=\"Click to edit entry.\" onclick='editRowEntry(this,"+response+")' style='cursor: pointer;'/></td>"
								+"<td><img src='img/delete1.png' title=\"Click to delete entry.\" onclick='onDelete(\"taskAction1.do?method=delete&id="+response+"\", this)' style='cursor: pointer;' /></td>");
					}
					else {
						$('#'+idVal).html("<td>"+date+"</td><td>"+status+"</td><td>"+backlog_id+"</td><td>"+task_id+"</td><td>"+task_description+"</td><td>"+capitalizeWorkStatus+"</td>"
								+"<td>"+timeValue+"</td>"
								+"<td><img src='img/edit.png' title=\"Click to edit entry.\" onclick='editRowEntry(this,"+response+")' style='cursor: pointer;'/></td>"
								+"<td><img src='img/delete1.png' title=\"Click to delete entry.\" onclick='onDelete(\"taskAction1.do?method=delete&id="+response+"\", this)' style='cursor: pointer;' /></td>");
					}
					$("#"+idVal).css("background-color","#96FFFF");
					
					setTimeout( function(){
						$("table[id=dailyEntryTable] tr:nth-child(even)").css({"background": "#ffffff"});
						$("table[id=dailyEntryTable] tr:nth-child(odd)").css({"background": "#eeeeee"});}, 1500 );
					
					$('#ajax_res').html("<div style='font-family: verdana, arial, sans-serif;font-size: 11px;color: blue;text-align: center;'>Data entered successfully.</div>");        		 
				} 
			},
			beforeSend: function(){
				showLoadingImage();
			},			
			complete: function(){
				hideLoadingImage();
				hideEnteredMissingdate();
				//Sort table here.
				sortMyTable(table);
			}, 
			error: function(e){
				//document.getElementById('myDiv').style.visibility = "hidden";
				document.getElementById('save_id_buttonId').style.visibility = "hidden";
				document.getElementById('new_entry_buttonId').style.visibility = "hidden";
				document.getElementById('myMissingDates').style.visibility = "hidden";
				if($("#freeze_entry_id").length != 0){document.getElementById('freeze_entry_id').style.visibility = "hidden";}
				alert("Check attribute "+$("#freeze_entry_id").length());
				document.getElementById("ajax_res").innerHTML ="<div style='font-family: verdana, arial, sans-serif;font-size: 13px;color: red;text-align: center;'>"
					+"Your Session has been expired please <a href='http://pepq.calsoftlabs.com/effort/' target='_blank'>relogin</a> to retain your session.</div>"; 
			}
		});  
	}   	
}
function checkDateForPublicHoliday(selectedDate){
	var holidayArray = null;
	var location = $('#resourceLocationId').val();
	if(location=='Bangalore' ||location=='Chennai' ){
		if(location=='Bangalore'){
			holidayArray = new Array("01/01/2014","01/13/2014","01/14/2014","01/26/2014","03/17/2014","03/31/2014",
					"04/18/2014","05/01/2014","07/29/2014","08/15/2014","08/29/2014","09/08/2014","10/02/2014",
					"10/03/2014","10/22/2014","11/01/2014","12/25/2014");
		}
		else if(location=='Chennai'){
			holidayArray = new Array("01/01/2014","01/13/2014","01/14/2014","01/26/2014","03/17/2014","04/14/2014",
					"04/18/2014","05/01/2014","07/29/2014","08/15/2014",
					"08/29/2014","09/08/2014","10/02/2014","10/03/2014","10/22/2014","12/25/2014");
		}
		for ( var i = 0; i < holidayArray.length; i++) {
			var checkDate = holidayArray[i];
			if (selectedDate == checkDate) {
				return true;
			}
		}
	}
	return false;
}

function hideEnteredMissingdate(){
	$('#myMissingDates').show("slow");
	var historyTable = document.getElementById("dailyEntryTable");
	if(historyTable!=null){
		var totalRow = historyTable.getElementsByTagName('tr').length;
		var datesFromHistory = new Array();
		var datesFromMissingTable = new Array();
		var json_entry_data = new Array();
		var dCont = 0 ;		
		for(var r1 = 1 ; r1<totalRow; r1++){
			var curr_row = historyTable.getElementsByTagName('tr')[r1];
			if(curr_row.cells[0].innerHTML.length==10){
				var fullDate = curr_row.cells[0].innerHTML;
				var entry_status = curr_row.cells[1].innerHTML;
				var entry_hrs = parseFloat(curr_row.cells[6].innerHTML);
				datesFromHistory[dCont] = fullDate.substring(fullDate.indexOf('/')+1, fullDate.lastIndexOf('/'));
				for(var y = r1+1; y <totalRow; y++){
					var curr_row_val = historyTable.getElementsByTagName('tr')[y];
					if(curr_row_val.cells[0].innerHTML.length==10 && curr_row_val.cells[0].innerHTML == fullDate){
						var add_time = parseFloat(curr_row_val.cells[6].innerHTML);
						entry_hrs = entry_hrs+add_time;
					}
				}
				// Checking entry for day 																																// Excluding Entry less than 8 Hrs in case of Public Holiday.
				if(( ($.inArray(datesFromHistory[dCont], json_entry_data))==-1) && (entry_hrs >= "7.6"  || (entry_status =='Leave' || entry_status == 'Public holiday' || checkDateForPublicHoliday(fullDate)) )){
					json_entry_data[dCont] = fullDate.substring(fullDate.indexOf('/')+1, fullDate.lastIndexOf('/'));
				}
			}
			/* else{    // Incase if validation falls 
				var fullDate = curr_row.cells[0].innerHTML;
				datesFromHistory[dCont] = fullDate.substring(fullDate.indexOf('/')+1, fullDate.lastIndexOf('/'));
			 }*/
			dCont++;
		}
		var missingDateTable = document.getElementById("myMissingDates");
		var curr_row = missingDateTable.getElementsByTagName('tr')[1];					 
		var allHrefDate = curr_row.cells[0].id;
		var td = document.getElementById(allHrefDate);
		var hrefLength = td.getElementsByTagName('a').length;
		dCount = 0;
		// Discarding last HREF so loop started from (hrefLength-2)
		for(var p = hrefLength-2; p>=0 ; p--){			   
			datesFromMissingTable[dCount++] = 	td.getElementsByTagName('a')[p].innerHTML;
		}
		var counterForHidingMissingEntryTable = 0;
		for(var ck = 0; ck < datesFromMissingTable.length; ck++){
			// Dates contains in History table or not.
			if(($.inArray(datesFromMissingTable[ck], json_entry_data))!=-1){
				// Discarding last HREF so loop started from (hrefLength-2)
				for(var p = hrefLength-2; p>=0 ; p--){
					if(td.getElementsByTagName('a')[p].innerHTML == datesFromMissingTable[ck]){
						$('#'+td.getElementsByTagName('a')[p].id).hide();
					}
				}
				counterForHidingMissingEntryTable++;
			}
			else{
				for(var p = hrefLength-2; p>=0 ; p--){				// Discarding last HREF	
					if(td.getElementsByTagName('a')[p].innerHTML == datesFromMissingTable[ck]){
						$('#'+td.getElementsByTagName('a')[p].id).show("slow");
					}
				}
			}
		}
		counterForHidingMissingEntryTable++;
		if(counterForHidingMissingEntryTable==hrefLength){
			$('#myMissingDates').hide();
		}	
	}
}
function sortMyTable(table){
	var totalRowInTable = table.getElementsByTagName('tr').length;
	var date_row_json = new Array();
	for(var r1 = 1 ; r1<totalRowInTable; r1++){
		var curr_row = table.getElementsByTagName('tr')[r1];
		if(curr_row.cells[0].innerHTML.length==10){
			date_row_json.push({"row_date":curr_row.cells[0].innerHTML, "row_id":curr_row.id});
		}
		else{    // Incase if validation falls 
			date_row_json.push({"row_date":curr_row.cells[0].firstChild.value, "row_id":curr_row.id});;
		}
	}						
	date_row_json.sort(custom_sort);					
	for(var dt = 0 ; dt<date_row_json.length;dt++){
		document.getElementById(date_row_json[dt].row_id).parentNode.insertBefore(document.getElementById(date_row_json[dt].row_id), document.getElementById(date_row_json[date_row_json.length-1].row_id));					
	}			
}	 

function editRowInline(r,id)
{  
	$('#ajax_res').html("");
	var i=r.parentNode.parentNode.rowIndex;  		
	var table = document.getElementById('dailyEntryTable');
	var idVal = document.getElementById('dailyEntryTable').rows[i].id;  	
	var editFieldStatus = table.getElementsByTagName('input').length;
	if(editFieldStatus>0){
		var row = table.getElementsByTagName('input')[0].parentNode.parentNode;
		document.getElementById("ajax_res").innerHTML ="<div style='font-family: verdana, arial, sans-serif;font-size: 11px;color: red;text-align: center;'>"
			+"*Please save entry:"+row.rowIndex+" and proceed for edit.</div>";
		var saveTd = row.getElementsByTagName('td')[7];  
		var mess = row.getElementsByTagName('td')[7].innerHTML;
		mess = mess.replace("*Save", "");
		row.getElementsByTagName('td')[7].innerHTML = mess+"<div style='color: red;text-align: left;'>*Save</div>";
		saveTd.style.backgroundColor="yellow";
	}
	else{
		var tableTr = table.getElementsByTagName('tr')[i];
		var nCell = tableTr.getElementsByTagName('td'); 
		var date = nCell[0].innerHTML;
		var status = nCell[1].innerHTML;
		var backlog_id = nCell[2].innerHTML;
		var task_id = nCell[3].innerHTML;
		var task_description = nCell[4].innerHTML;
		var workStatus = nCell[5].innerHTML;
		var time = nCell[6].innerHTML; 
		if(status=='Half Day'||status=='Down Time'||status=='Comp off'||status=='Travel'){
			if(workStatus.trim()=='Home'){
				$('#'+idVal).html("<td id=\"td_dt\" width=\"80px\"><INPUT TYPE=\"text\" NAME=\"task_date\" title=\"Click to pick date from calendar.\" id=\"popupDatepicker\" readonly=\"true\" SIZE=\"5\" MAXLENGTH=\"10\" value='"+date+"' STYLE=\"height:4;width: 65px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\"></td>"
						+"<td id=\"td_st\">"
						+"<SELECT NAME=\"status\" id=\"status\" title=\"Select appropriate category.\" onchange=\"changeDynamicTextBox(this);\" style=\"width: 120px;font-family: verdana, arial, sans-serif;font-size: 11px;\">"
						+"<option value=\""+status+"\">"+status+"</option>"
						+"<option value=\"Code Review\">Code Review</option>"
						+"<option value=\"Comp off\">Comp off</option>"
						+"<option value=\"Development\">Development</option>"
						+"<option value=\"Down Time\">Down Time</option>"
						+"<option value=\"Half Day\">Half Day</option>"
						+"<option value=\"KT Session\">KT Session</option>"
						+"<option value=\"Leave\">Leave</option>"
						+"<option value=\"Maintenance/Release support\">Maintenance/Release support</option>"
						+"<option value=\"Meeting\">Meeting</option>"
						+"<option value=\"Pre-Project Training\">Pre-Project Training</option>"								
						+"<option value=\"Project Management\">Project Management</option>"																				
						+"<option value=\"Public holiday\">Public holiday</option>"
						+"<option value=\"Task unassigned/Idle\">Task unassigned/Idle</option>"	
						+"<option value=\"Team Activity\">Team Activity</option>"
						+"<option value=\"Testing\">Testing</option>"														
						+"<option value=\"Training\">Training</option>"													
						+"<option value=\"Travel\">Travel</option>"
						+"</SELECT></td>"
						+"<td id=\"td_back\"><INPUT TYPE=\"text\" id=\"backlog_id\" disabled=\"disabled\" title=\"Enter BacklogId.\" NAME=\"backlog_id\" SIZE=\"5\" MAXLENGTH=\"20\" STYLE=\"height:4;width: 60px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\" value="+backlog_id+"></td>"
						+"<td id=\"td_task\"><INPUT TYPE=\"text\" id=\"task_id\" disabled=\"disabled\" NAME=\"task_id\" title=\"Enter TaskId.\" SIZE=\"5\" MAXLENGTH=\"20\" STYLE=\"height:4;width: 60px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\" value="+task_id+" ></td>"
						+"<td id=\"td_des\"><textarea name=\"task_description\" title=\"Add description here.\" id=\"des_id\" value="+task_description+" rows=\"2\" cols=\"2\" STYLE=\"width: 160px;height: 100px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\">"+task_description+"</textarea></td>"
						+"<td id=\"td_workStatus\" style=\"height: 100px;width: 200px;font-family: verdana, arial, sans-serif;font-size: 11px;\"><input type=\"radio\" id=\"work_status1"+count+"\" title=\"Pick Effort From\" name=\"work_status\" value=\"office\"> Office"
						+"<input type=\"radio\" id=\"work_status2"+count+"\" title=\"Pick Effort From\" name=\"work_status\" value=\"home\" checked>Home</td>"
						+"<td id=\"td_tm\"><INPUT TYPE=\"text\" NAME=\"time\" title=\"eg:HH.MM, HH:MM\" id=\"time\"  SIZE=\"5\" MAXLENGTH=\"5\" STYLE=\"height:4;width: 40px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\" value="+time+"></td>"
						+"<td id=\"ed_id\"><img alt=\"1px\" id=\""+id+"\" src=\"img/save.gif\" id=\"edit\" title=\"Click to save entry.\" SIZE=\"5\"  onClick=\"callAjaxEditSave(this,"+id+")\" style=\"height: 20px;width: 20px;font-family: verdana, arial, sans-serif;font-size: 11px;cursor: pointer;\"></td>"
						+"<td><img src='img/delete1.png' title=\"Click to delete entry.\" onclick='onDelete(\"taskAction1.do?method=delete&id="+id+"\", this)' style='cursor: pointer;' /></td>");    
			}
			else{
				$('#'+idVal).html("<td id=\"td_dt\" width=\"80px\"><INPUT TYPE=\"text\" NAME=\"task_date\" title=\"Click to pick date from calendar.\" id=\"popupDatepicker\" readonly=\"true\" SIZE=\"5\" MAXLENGTH=\"10\" value='"+date+"' STYLE=\"height:4;width: 65px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\"></td>"
						+"<td id=\"td_st\">"
						+"<SELECT NAME=\"status\" id=\"status\" title=\"Select appropriate category.\" onchange=\"changeDynamicTextBox(this);\" style=\"width: 120px;font-family: verdana, arial, sans-serif;font-size: 11px;\">"
						+"<option value=\""+status+"\">"+status+"</option>"
						+"<option value=\"Code Review\">Code Review</option>"
						+"<option value=\"Comp off\">Comp off</option>"
						+"<option value=\"Development\">Development</option>"
						+"<option value=\"Down Time\">Down Time</option>"
						+"<option value=\"Half Day\">Half Day</option>"
						+"<option value=\"KT Session\">KT Session</option>"
						+"<option value=\"Leave\">Leave</option>"
						+"<option value=\"Maintenance/Release support\">Maintenance/Release support</option>"
						+"<option value=\"Meeting\">Meeting</option>"
						+"<option value=\"Pre-Project Training\">Pre-Project Training</option>"								
						+"<option value=\"Project Management\">Project Management</option>"																				
						+"<option value=\"Public holiday\">Public holiday</option>"
						+"<option value=\"Task unassigned/Idle\">Task unassigned/Idle</option>"	
						+"<option value=\"Team Activity\">Team Activity</option>"
						+"<option value=\"Testing\">Testing</option>"														
						+"<option value=\"Training\">Training</option>"													
						+"<option value=\"Travel\">Travel</option>"
						+"</SELECT></td>"
						+"<td id=\"td_back\"><INPUT TYPE=\"text\" id=\"backlog_id\" disabled=\"disabled\" title=\"Enter BacklogId.\" NAME=\"backlog_id\" SIZE=\"5\" MAXLENGTH=\"20\" STYLE=\"height:4;width: 60px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\" value="+backlog_id+"></td>"
						+"<td id=\"td_task\"><INPUT TYPE=\"text\" id=\"task_id\" disabled=\"disabled\" NAME=\"task_id\" title=\"Enter TaskId.\" SIZE=\"5\" MAXLENGTH=\"20\" STYLE=\"height:4;width: 60px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\" value="+task_id+" ></td>"
						+"<td id=\"td_des\"><textarea name=\"task_description\" title=\"Add description here.\" id=\"des_id\" value="+task_description+" rows=\"2\" cols=\"2\" STYLE=\"width: 160px;height: 100px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\">"+task_description+"</textarea></td>"
						+"<td id=\"td_workStatus\" style=\"font-family: verdana, arial, sans-serif;font-size: 11px;\"><input type=\"radio\" id=\"work_status1"+count+"\" title=\"Pick Effort From\" name=\"work_status\" value=\"office\" checked> Office"
						+"<input type=\"radio\" id=\"work_status2"+count+"\" title=\"Pick Effort From\" name=\"work_status\" value=\"home\">Home</td>"
						+"<td id=\"td_tm\"><INPUT TYPE=\"text\" NAME=\"time\" title=\"eg:HH.MM, HH:MM\" id=\"time\"  SIZE=\"5\" MAXLENGTH=\"5\" STYLE=\"height:4;width: 40px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\" value="+time+"></td>"
						+"<td id=\"ed_id\"><img alt=\"1px\" src=\"img/save.gif\" id=\"edit\" title=\"Click to save entry.\" SIZE=\"5\"  onClick=\"callAjaxEditSave(this,"+id+")\" style=\"height: 20px;width: 20px;font-family: verdana, arial, sans-serif;font-size: 11px;cursor: pointer;\"></td>"
						+"<td><img src='img/delete1.png' title=\"Click to delete entry.\" onclick='onDelete(\"taskAction1.do?method=delete&id="+id+"\", this)' style='cursor: pointer;' /></td>");    
			}
		}
		else if(status=='Public holiday'){
			if(workStatus.trim()=='Home'){
				$('#'+idVal).html("<td id=\"td_dt\" width=\"80px\"><INPUT TYPE=\"text\" NAME=\"task_date\" title=\"Click to pick date from calendar.\" id=\"popupDatepicker\" readonly=\"true\" SIZE=\"5\" MAXLENGTH=\"10\" value='"+date+"' STYLE=\"height:4;width: 65px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\"></td>"
						+"<td id=\"td_st\">"
						+"<SELECT NAME=\"status\" id=\"status\" title=\"Select appropriate category.\" onchange=\"changeDynamicTextBox(this);\" style=\"width: 120px;font-family: verdana, arial, sans-serif;font-size: 11px;\">"
						+"<option value=\""+status+"\">"+status+"</option>"
						+"<option value=\"Code Review\">Code Review</option>"
						+"<option value=\"Comp off\">Comp off</option>"
						+"<option value=\"Development\">Development</option>"
						+"<option value=\"Down Time\">Down Time</option>"
						+"<option value=\"Half Day\">Half Day</option>"
						+"<option value=\"KT Session\">KT Session</option>"
						+"<option value=\"Leave\">Leave</option>"
						+"<option value=\"Maintenance/Release support\">Maintenance/Release support</option>"
						+"<option value=\"Meeting\">Meeting</option>"
						+"<option value=\"Pre-Project Training\">Pre-Project Training</option>"								
						+"<option value=\"Project Management\">Project Management</option>"																				
						+"<option value=\"Public holiday\">Public holiday</option>"
						+"<option value=\"Task unassigned/Idle\">Task unassigned/Idle</option>"	
						+"<option value=\"Team Activity\">Team Activity</option>"
						+"<option value=\"Testing\">Testing</option>"														
						+"<option value=\"Training\">Training</option>"													
						+"<option value=\"Travel\">Travel</option>"
						+"</SELECT></td>"
						+"<td id=\"td_back\"><INPUT TYPE=\"text\" id=\"backlog_id\" disabled=\"disabled\" title=\"Enter BacklogId.\" NAME=\"backlog_id\" SIZE=\"5\" MAXLENGTH=\"20\" STYLE=\"height:4;width: 60px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\" value="+backlog_id+"></td>"
						+"<td id=\"td_task\"><INPUT TYPE=\"text\" id=\"task_id\" disabled=\"disabled\" NAME=\"task_id\" title=\"Enter TaskId.\" SIZE=\"5\" MAXLENGTH=\"20\" STYLE=\"height:4;width: 60px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\" value="+task_id+" ></td>"
						+"<td id=\"td_des\"><textarea name=\"task_description\" title=\"Add description here.\" id=\"des_id\" disabled=\"disabled\" value="+task_description+" rows=\"2\" cols=\"2\" STYLE=\"width: 160px;height: 100px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\">"+task_description+"</textarea></td>"
						+"<td id=\"td_workStatus\" style=\"height: 100px;width: 200px;font-family: verdana, arial, sans-serif;font-size: 11px;\"><input type=\"radio\" id=\"work_status1"+count+"\" title=\"Pick Effort From\" name=\"work_status\" value=\"office\"> Office"
						+"<input type=\"radio\" id=\"work_status2"+count+"\" title=\"Pick Effort From\" name=\"work_status\" value=\"home\" checked>Home</td>"
						+"<td id=\"td_tm\"><INPUT TYPE=\"text\" NAME=\"time\" title=\"eg:HH.MM, HH:MM\" id=\"time\" disabled=\"disabled\" SIZE=\"5\" MAXLENGTH=\"5\" STYLE=\"height:4;width: 40px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\" value="+time+"></td>"
						+"<td id=\"ed_id\"><img alt=\"1px\" id=\""+id+"\" src=\"img/save.gif\" id=\"edit\" title=\"Click to save entry.\" SIZE=\"5\"  onClick=\"callAjaxEditSave(this,"+id+")\" style=\"height: 20px;width: 20px;font-family: verdana, arial, sans-serif;font-size: 11px;cursor: pointer;\"></td>"
						+"<td><img src='img/delete1.png' title=\"Click to delete entry.\" onclick='onDelete(\"taskAction1.do?method=delete&id="+id+"\", this)' style='cursor: pointer;' /></td>");    
			}
			else{
				$('#'+idVal).html("<td id=\"td_dt\" width=\"80px\"><INPUT TYPE=\"text\" NAME=\"task_date\" title=\"Click to pick date from calendar.\" id=\"popupDatepicker\" readonly=\"true\" SIZE=\"5\" MAXLENGTH=\"10\" value='"+date+"' STYLE=\"height:4;width: 65px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\"></td>"
						+"<td id=\"td_st\">"
						+"<SELECT NAME=\"status\" id=\"status\" title=\"Select appropriate category.\" onchange=\"changeDynamicTextBox(this);\" style=\"width: 120px;font-family: verdana, arial, sans-serif;font-size: 11px;\">"
						+"<option value=\""+status+"\">"+status+"</option>"
						+"<option value=\"Code Review\">Code Review</option>"
						+"<option value=\"Comp off\">Comp off</option>"
						+"<option value=\"Development\">Development</option>"
						+"<option value=\"Down Time\">Down Time</option>"
						+"<option value=\"Half Day\">Half Day</option>"
						+"<option value=\"KT Session\">KT Session</option>"
						+"<option value=\"Leave\">Leave</option>"
						+"<option value=\"Maintenance/Release support\">Maintenance/Release support</option>"
						+"<option value=\"Meeting\">Meeting</option>"
						+"<option value=\"Pre-Project Training\">Pre-Project Training</option>"								
						+"<option value=\"Project Management\">Project Management</option>"																				
						+"<option value=\"Public holiday\">Public holiday</option>"
						+"<option value=\"Task unassigned/Idle\">Task unassigned/Idle</option>"	
						+"<option value=\"Team Activity\">Team Activity</option>"
						+"<option value=\"Testing\">Testing</option>"														
						+"<option value=\"Training\">Training</option>"													
						+"<option value=\"Travel\">Travel</option>"
						+"</SELECT></td>"
						+"<td id=\"td_back\"><INPUT TYPE=\"text\" id=\"backlog_id\" disabled=\"disabled\" title=\"Enter BacklogId.\" NAME=\"backlog_id\" SIZE=\"5\" MAXLENGTH=\"20\" STYLE=\"height:4;width: 60px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\" value="+backlog_id+"></td>"
						+"<td id=\"td_task\"><INPUT TYPE=\"text\" id=\"task_id\" disabled=\"disabled\" NAME=\"task_id\" title=\"Enter TaskId.\" SIZE=\"5\" MAXLENGTH=\"20\" STYLE=\"height:4;width: 60px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\" value="+task_id+" ></td>"
						+"<td id=\"td_des\"><textarea name=\"task_description\" title=\"Add description here.\" id=\"des_id\" disabled=\"disabled\" value="+task_description+" rows=\"2\" cols=\"2\" STYLE=\"width: 160px;height: 100px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\">"+task_description+"</textarea></td>"
						+"<td id=\"td_workStatus\" style=\"font-family: verdana, arial, sans-serif;font-size: 11px;\"><input type=\"radio\" id=\"work_status1"+count+"\" title=\"Pick Effort From\" name=\"work_status\" value=\"office\" checked> Office"
						+"<input type=\"radio\" id=\"work_status2"+count+"\" title=\"Pick Effort From\" name=\"work_status\" value=\"home\">Home</td>"
						+"<td id=\"td_tm\"><INPUT TYPE=\"text\" NAME=\"time\" title=\"eg:HH.MM, HH:MM\" id=\"time\" disabled=\"disabled\" SIZE=\"5\" MAXLENGTH=\"5\" STYLE=\"height:4;width: 40px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\" value="+time+"></td>"
						+"<td id=\"ed_id\"><img alt=\"1px\" src=\"img/save.gif\" id=\"edit\" title=\"Click to save entry.\" SIZE=\"5\"  onClick=\"callAjaxEditSave(this,"+id+")\" style=\"height: 20px;width: 20px;font-family: verdana, arial, sans-serif;font-size: 11px;cursor: pointer;\"></td>"
						+"<td><img src='img/delete1.png' title=\"Click to delete entry.\" onclick='onDelete(\"taskAction1.do?method=delete&id="+id+"\", this)' style='cursor: pointer;' /></td>");    
			}
		}
		else if(status=='Leave'){
			if(workStatus.trim()=='Home'){
				$('#'+idVal).html("<td id=\"td_dt\" width=\"80px\"><INPUT TYPE=\"text\" NAME=\"task_date\" title=\"Click to pick date from calendar.\" id=\"popupDatepicker\" readonly=\"true\" SIZE=\"5\" MAXLENGTH=\"10\" value='"+date+"' STYLE=\"height:4;width: 65px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\"></td>"
						+"<td id=\"td_st\">"
						+"<SELECT NAME=\"status\" id=\"status\" title=\"Select appropriate category.\" onchange=\"changeDynamicTextBox(this);\" style=\"width: 120px;font-family: verdana, arial, sans-serif;font-size: 11px;\">"
						+"<option value=\""+status+"\">"+status+"</option>"
						+"<option value=\"Code Review\">Code Review</option>"
						+"<option value=\"Comp off\">Comp off</option>"
						+"<option value=\"Development\">Development</option>"
						+"<option value=\"Down Time\">Down Time</option>"
						+"<option value=\"Half Day\">Half Day</option>"
						+"<option value=\"KT Session\">KT Session</option>"
						+"<option value=\"Leave\">Leave</option>"
						+"<option value=\"Maintenance/Release support\">Maintenance/Release support</option>"
						+"<option value=\"Meeting\">Meeting</option>"
						+"<option value=\"Pre-Project Training\">Pre-Project Training</option>"								
						+"<option value=\"Project Management\">Project Management</option>"																				
						+"<option value=\"Public holiday\">Public holiday</option>"
						+"<option value=\"Task unassigned/Idle\">Task unassigned/Idle</option>"	
						+"<option value=\"Team Activity\">Team Activity</option>"
						+"<option value=\"Testing\">Testing</option>"														
						+"<option value=\"Training\">Training</option>"													
						+"<option value=\"Travel\">Travel</option>"
						+"</SELECT></td>"
						+"<td id=\"td_back\"><INPUT TYPE=\"text\" id=\"backlog_id\" disabled=\"disabled\" title=\"Enter BacklogId.\" NAME=\"backlog_id\" SIZE=\"5\" MAXLENGTH=\"20\" STYLE=\"height:4;width: 60px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\" value="+backlog_id+"></td>"
						+"<td id=\"td_task\"><INPUT TYPE=\"text\" id=\"task_id\" disabled=\"disabled\" NAME=\"task_id\" title=\"Enter TaskId.\" SIZE=\"5\" MAXLENGTH=\"20\" STYLE=\"height:4;width: 60px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\" value="+task_id+" ></td>"
						+"<td id=\"td_des\"><textarea name=\"task_description\" title=\"Add description here.\" id=\"des_id\" value="+task_description+" rows=\"2\" cols=\"2\" STYLE=\"width: 160px;height: 100px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\">"+task_description+"</textarea></td>"
						+"<td id=\"td_workStatus\" style=\"height: 100px;width: 200px;font-family: verdana, arial, sans-serif;font-size: 11px;\"><input type=\"radio\" id=\"work_status1"+count+"\" title=\"Pick Effort From\" name=\"work_status\" value=\"office\"> Office"
						+"<input type=\"radio\" id=\"work_status2"+count+"\" title=\"Pick Effort From\" name=\"work_status\" value=\"home\" checked>Home</td>"
						+"<td id=\"td_tm\"><INPUT TYPE=\"text\" NAME=\"time\" title=\"eg:HH.MM, HH:MM\" id=\"time\" disabled=\"disabled\" SIZE=\"5\" MAXLENGTH=\"5\" STYLE=\"height:4;width: 40px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\" value="+time+"></td>"
						+"<td id=\"ed_id\"><img alt=\"1px\" id=\""+id+"\" src=\"img/save.gif\" id=\"edit\" title=\"Click to save entry.\" SIZE=\"5\"  onClick=\"callAjaxEditSave(this,"+id+")\" style=\"height: 20px;width: 20px;font-family: verdana, arial, sans-serif;font-size: 11px;cursor: pointer;\"></td>"
						+"<td><img src='img/delete1.png' title=\"Click to delete entry.\" onclick='onDelete(\"taskAction1.do?method=delete&id="+id+"\", this)' style='cursor: pointer;' /></td>");    
			}
			else{
				$('#'+idVal).html("<td id=\"td_dt\" width=\"80px\"><INPUT TYPE=\"text\" NAME=\"task_date\" title=\"Click to pick date from calendar.\" id=\"popupDatepicker\" readonly=\"true\" SIZE=\"5\" MAXLENGTH=\"10\" value='"+date+"' STYLE=\"height:4;width: 65px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\"></td>"
						+"<td id=\"td_st\">"
						+"<SELECT NAME=\"status\" id=\"status\" title=\"Select appropriate category.\" onchange=\"changeDynamicTextBox(this);\" style=\"width: 120px;font-family: verdana, arial, sans-serif;font-size: 11px;\">"
						+"<option value=\""+status+"\">"+status+"</option>"
						+"<option value=\"Code Review\">Code Review</option>"
						+"<option value=\"Comp off\">Comp off</option>"
						+"<option value=\"Development\">Development</option>"
						+"<option value=\"Down Time\">Down Time</option>"
						+"<option value=\"Half Day\">Half Day</option>"
						+"<option value=\"KT Session\">KT Session</option>"
						+"<option value=\"Leave\">Leave</option>"
						+"<option value=\"Maintenance/Release support\">Maintenance/Release support</option>"
						+"<option value=\"Meeting\">Meeting</option>"
						+"<option value=\"Pre-Project Training\">Pre-Project Training</option>"								
						+"<option value=\"Project Management\">Project Management</option>"																				
						+"<option value=\"Public holiday\">Public holiday</option>"
						+"<option value=\"Task unassigned/Idle\">Task unassigned/Idle</option>"	
						+"<option value=\"Team Activity\">Team Activity</option>"
						+"<option value=\"Testing\">Testing</option>"														
						+"<option value=\"Training\">Training</option>"													
						+"<option value=\"Travel\">Travel</option>"
						+"</SELECT></td>"
						+"<td id=\"td_back\"><INPUT TYPE=\"text\" id=\"backlog_id\" disabled=\"disabled\" title=\"Enter BacklogId.\" NAME=\"backlog_id\" SIZE=\"5\" MAXLENGTH=\"20\" STYLE=\"height:4;width: 60px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\" value="+backlog_id+"></td>"
						+"<td id=\"td_task\"><INPUT TYPE=\"text\" id=\"task_id\" disabled=\"disabled\" NAME=\"task_id\" title=\"Enter TaskId.\" SIZE=\"5\" MAXLENGTH=\"20\" STYLE=\"height:4;width: 60px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\" value="+task_id+" ></td>"
						+"<td id=\"td_des\"><textarea name=\"task_description\" title=\"Add description here.\" id=\"des_id\" value="+task_description+" rows=\"2\" cols=\"2\" STYLE=\"width: 160px;height: 100px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\">"+task_description+"</textarea></td>"
						+"<td id=\"td_workStatus\" style=\"font-family: verdana, arial, sans-serif;font-size: 11px;\"><input type=\"radio\" id=\"work_status1"+count+"\" title=\"Pick Effort From\" name=\"work_status\" value=\"office\" checked> Office"
						+"<input type=\"radio\" id=\"work_status2"+count+"\" title=\"Pick Effort From\" name=\"work_status\" value=\"home\">Home</td>"
						+"<td id=\"td_tm\"><INPUT TYPE=\"text\" NAME=\"time\" title=\"eg:HH.MM, HH:MM\" id=\"time\" disabled=\"disabled\" SIZE=\"5\" MAXLENGTH=\"5\" STYLE=\"height:4;width: 40px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\" value="+time+"></td>"
						+"<td id=\"ed_id\"><img alt=\"1px\" src=\"img/save.gif\" id=\"edit\" title=\"Click to save entry.\" SIZE=\"5\"  onClick=\"callAjaxEditSave(this,"+id+")\" style=\"height: 20px;width: 20px;font-family: verdana, arial, sans-serif;font-size: 11px;cursor: pointer;\"></td>"
						+"<td><img src='img/delete1.png' title=\"Click to delete entry.\" onclick='onDelete(\"taskAction1.do?method=delete&id="+id+"\", this)' style='cursor: pointer;' /></td>");    
			}
		}	
		else{
			if(workStatus.trim()=='Home'){
				$('#'+idVal).html("<td id=\"td_dt\" width=\"80px\"><INPUT TYPE=\"text\" NAME=\"task_date\" title=\"Click to pick date from calendar.\" id=\"popupDatepicker\" readonly=\"true\" SIZE=\"5\" MAXLENGTH=\"10\" value='"+date+"' STYLE=\"height:4;width: 65px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\"></td>"
						+"<td id=\"td_st\">"
						+"<SELECT NAME=\"status\" id=\"status\" title=\"Select appropriate category.\" onchange=\"changeDynamicTextBox(this);\" style=\"width: 120px;font-family: verdana, arial, sans-serif;font-size: 11px;\">"
						+"<option value=\""+status+"\">"+status+"</option>"
						+"<option value=\"Code Review\">Code Review</option>"
						+"<option value=\"Comp off\">Comp off</option>"
						+"<option value=\"Development\">Development</option>"
						+"<option value=\"Down Time\">Down Time</option>"
						+"<option value=\"Half Day\">Half Day</option>"
						+"<option value=\"KT Session\">KT Session</option>"
						+"<option value=\"Leave\">Leave</option>"
						+"<option value=\"Maintenance/Release support\">Maintenance/Release support</option>"
						+"<option value=\"Meeting\">Meeting</option>"
						+"<option value=\"Pre-Project Training\">Pre-Project Training</option>"								
						+"<option value=\"Project Management\">Project Management</option>"																				
						+"<option value=\"Public holiday\">Public holiday</option>"
						+"<option value=\"Task unassigned/Idle\">Task unassigned/Idle</option>"	
						+"<option value=\"Team Activity\">Team Activity</option>"
						+"<option value=\"Testing\">Testing</option>"														
						+"<option value=\"Training\">Training</option>"													
						+"<option value=\"Travel\">Travel</option>"
						+"</SELECT></td>"
						+"<td id=\"td_back\"><INPUT TYPE=\"text\" id=\"backlog_id\" title=\"Enter BacklogId.\" NAME=\"backlog_id\" SIZE=\"5\" MAXLENGTH=\"20\" STYLE=\"height:4;width: 60px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\" value="+backlog_id+"></td>"
						+"<td id=\"td_task\"><INPUT TYPE=\"text\" id=\"task_id\" NAME=\"task_id\" title=\"Enter TaskId.\" SIZE=\"5\" MAXLENGTH=\"20\" STYLE=\"height:4;width: 60px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\" value="+task_id+" ></td>"
						+"<td id=\"td_des\"><textarea name=\"task_description\" title=\"Add description here.\" id=\"des_id\" value="+task_description+" rows=\"2\" cols=\"2\" STYLE=\"width: 160px;height: 100px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\">"+task_description+"</textarea></td>"
						+"<td id=\"td_workStatus\" style=\"height: 100px;width: 200px;font-family: verdana, arial, sans-serif;font-size: 11px;\"><input type=\"radio\" id=\"work_status1"+count+"\" title=\"Pick Effort From\" name=\"work_status\" value=\"office\"> Office"
						+"<input type=\"radio\" id=\"work_status2"+count+"\" title=\"Pick Effort From\" name=\"work_status\" value=\"home\" checked>Home</td>"
						+"<td id=\"td_tm\"><INPUT TYPE=\"text\" NAME=\"time\" title=\"eg:HH.MM, HH:MM\" id=\"time\"  SIZE=\"5\" MAXLENGTH=\"5\" STYLE=\"height:4;width: 40px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\" value="+time+"></td>"
						+"<td id=\"ed_id\"><img alt=\"1px\" src=\"img/save.gif\" id=\"edit\" title=\"Click to save entry.\" SIZE=\"5\"  onClick=\"callAjaxEditSave(this,"+id+")\" style=\"height: 20px;width: 20px;font-family: verdana, arial, sans-serif;font-size: 11px;cursor: pointer;\"></td>"
						+"<td><img src='img/delete1.png' title=\"Click to delete entry.\" onclick='onDelete(\"taskAction1.do?method=delete&id="+id+"\", this)' style='cursor: pointer;' /></td>");    
			}
			else{
				$('#'+idVal).html("<td id=\"td_dt\" width=\"80px\"><INPUT TYPE=\"text\" NAME=\"task_date\" title=\"Click to pick date from calendar.\" id=\"popupDatepicker\" readonly=\"true\" SIZE=\"5\" MAXLENGTH=\"10\" value='"+date+"' STYLE=\"height:4;width: 65px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\"></td>"
						+"<td id=\"td_st\">"
						+"<SELECT NAME=\"status\" id=\"status\" title=\"Select appropriate category.\" onchange=\"changeDynamicTextBox(this);\" style=\"width: 120px;font-family: verdana, arial, sans-serif;font-size: 11px;\">"
						+"<option value=\""+status+"\">"+status+"</option>"
						+"<option value=\"Code Review\">Code Review</option>"
						+"<option value=\"Comp off\">Comp off</option>"
						+"<option value=\"Development\">Development</option>"
						+"<option value=\"Down Time\">Down Time</option>"
						+"<option value=\"Half Day\">Half Day</option>"
						+"<option value=\"KT Session\">KT Session</option>"
						+"<option value=\"Leave\">Leave</option>"
						+"<option value=\"Maintenance/Release support\">Maintenance/Release support</option>"
						+"<option value=\"Meeting\">Meeting</option>"
						+"<option value=\"Pre-Project Training\">Pre-Project Training</option>"								
						+"<option value=\"Project Management\">Project Management</option>"																				
						+"<option value=\"Public holiday\">Public holiday</option>"
						+"<option value=\"Task unassigned/Idle\">Task unassigned/Idle</option>"	
						+"<option value=\"Team Activity\">Team Activity</option>"
						+"<option value=\"Testing\">Testing</option>"														
						+"<option value=\"Training\">Training</option>"													
						+"<option value=\"Travel\">Travel</option>"
						+"</SELECT></td>"
						+"<td id=\"td_back\"><INPUT TYPE=\"text\" id=\"backlog_id\" title=\"Enter BacklogId.\" NAME=\"backlog_id\" SIZE=\"5\" MAXLENGTH=\"20\" STYLE=\"height:4;width: 60px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\" value="+backlog_id+"></td>"
						+"<td id=\"td_task\"><INPUT TYPE=\"text\" id=\"task_id\" NAME=\"task_id\" title=\"Enter TaskId.\" SIZE=\"5\" MAXLENGTH=\"20\" STYLE=\"height:4;width: 60px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\" value="+task_id+" ></td>"
						+"<td id=\"td_des\"><textarea name=\"task_description\" title=\"Add description here.\" id=\"des_id\" value="+task_description+" rows=\"2\" cols=\"2\" STYLE=\"width: 160px;height: 100px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\">"+task_description+"</textarea></td>"
						+"<td id=\"td_workStatus\" style=\"font-family: verdana, arial, sans-serif;font-size: 11px;\"><input type=\"radio\" id=\"work_status1"+count+"\" title=\"Pick Effort From\" name=\"work_status\" value=\"office\" checked> Office"
						+"<input type=\"radio\" id=\"work_status2"+count+"\" title=\"Pick Effort From\" name=\"work_status\" value=\"home\">Home</td>"
						+"<td id=\"td_tm\"><INPUT TYPE=\"text\" NAME=\"time\" title=\"eg:HH.MM, HH:MM\" id=\"time\"  SIZE=\"5\" MAXLENGTH=\"5\" STYLE=\"height:4;width: 40px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\" value="+time+"></td>"
						+"<td id=\"ed_id\"><img alt=\"1px\" src=\"img/save.gif\" id=\"edit\" title=\"Click to save entry.\" SIZE=\"5\"  onClick=\"callAjaxEditSave(this,"+id+")\" style=\"height: 20px;width: 20px;font-family: verdana, arial, sans-serif;font-size: 11px;cursor: pointer;\"></td>"
						+"<td><img src='img/delete1.png' title=\"Click to delete entry.\" onclick='onDelete(\"taskAction1.do?method=delete&id="+id+"\", this)' style='cursor: pointer;' /></td>");    
			}
		}
		count++;
		$('#des_id').focus();
		$('#popupDatepicker').datepick();
	}

} 	
function callAjaxEditSave(r,id){ 
	var i=r.parentNode.parentNode.rowIndex;  
	var idVal = document.getElementById('dailyEntryTable').rows[i].id;
	var table = document.getElementById('dailyEntryTable');
	var tableTr = table.getElementsByTagName('tr')[i];
	var nCell = tableTr.getElementsByTagName('td');          	
	var date = nCell[0].firstChild.value;
	var status = nCell[1].firstChild.value;          	
	var backlog_id = nCell[2].firstChild.value;
	var task_id = nCell[3].firstChild.value;
	var task_description = nCell[4].firstChild.value;   	
	var taskJson = new Array();
	var workStatus = nCell[5].firstChild.value;
	var radioLength = nCell[5].getElementsByTagName('input').length;    	
	for (var p = 0; p < radioLength; p++){
		if(nCell[5].getElementsByTagName('input')[p].checked){
			workStatus = nCell[5].getElementsByTagName('input')[p].value;
		}
	}
	var capitalizeWorkStatus = null;
	if(workStatus=='home'){
		capitalizeWorkStatus = 'Home';
	}
	else if(workStatus=='office'){
		capitalizeWorkStatus = 'Office';
	}
	var time = nCell[6].firstChild.value;
	if(doValidation(date, status, backlog_id, task_id, time, idVal)) {  	
		time = getParsedTime(time);  
		var selectOption = document.getElementById("userId"); 
		var userId = 0;
		if(selectOption!=null){
			userId = $('#userId option:selected').val();
		}
		taskJson.push({ "date": date, "status": status, "backlog_id": backlog_id, "task_id" :task_id,"task_description":task_description,
			"work_status":workStatus, "time":time,"id":id, "userId":userId});
		$.ajax({
			type: "GET",
			url: 'taskActionAfterAjaxSave.do?method=saveEditAjax',
			//data: 'task_date='+date+'&status='+status+'&backlog_id='+backlog_id+'&task_id='+task_id+'&time='+time+'&task_description='+task_description+'&id='+id+'&work_status='+workStatus,
			data: { taskJson: JSON.stringify(taskJson)},
			success: function(response){
				// alert(response);
				if(response=="Invalid time Entry"){
					document.getElementById("ajax_res").innerHTML ="<div style='font-family: verdana, arial, sans-serif;font-size: 11px;color: red;text-align: center;'>Invalid time Entry, Time should not more than 23Hrs.</div>";
				}
				else if(response=="Data edited sucessfully"){
					if(status=="Leave"||status=="Half Day"){
						$('#'+idVal).html("<td>"+date+"</td><td>"+status+"</td><td>"+backlog_id+"</td><td>"+task_id+"</td><td>"+task_description+"</td><td>"+capitalizeWorkStatus+"</td>"
								+"<td style=\"color: #FF0000;\">"+time+"</td><td><img src='img/edit.png' title=\"Click to edit entry.\" onclick='editRowEntry(this,"+id+")' style='cursor: pointer;'/></td>"
								+"<td><img src='img/delete1.png' title=\"Click to delete entry.\" onclick='onDelete(\"taskAction1.do?method=delete&id="+id+"\", this)' style='cursor: pointer;' /></td>");        		 
					}
					else if(status=="Public holiday"){
						$('#'+idVal).html("<td>"+date+"</td><td>"+status+"</td><td>"+backlog_id+"</td><td>"+task_id+"</td><td>"+task_description+"</td><td>"+capitalizeWorkStatus+"</td>"
								+"<td style=\"color: #0000CD;\">"+time+"</td><td><img src='img/edit.png' title=\"Click to edit entry.\" onclick='editRowEntry(this,"+id+")' style='cursor: pointer;'/></td>"
								+"<td><img src='img/delete1.png' title=\"Click to delete entry.\" onclick='onDelete(\"taskAction1.do?method=delete&id="+id+"\", this)' style='cursor: pointer;' /></td>");
					}
					else if(status=="Comp off"){
						$('#'+idVal).html("<td style=\"color: #008000;\">"+date+"</td><td style=\"color: #008000;\">"+status+"</td><td style=\"color: #008000;\">"+backlog_id+"</td><td style=\"color: #008000;\">"+task_id+"</td><td style=\"color: #008000;\">"+task_description+"</td><td style=\"color: #008000;\">"+capitalizeWorkStatus+"</td>"
								+"<td style=\"color: #008000;\">"+time+"</td><td><img src='img/edit.png' title=\"Click to edit entry.\" onclick='editRowEntry(this,"+id+")' style='cursor: pointer;'/></td>"
								+"<td><img src='img/delete1.png' title=\"Click to delete entry.\" onclick='onDelete(\"taskAction1.do?method=delete&id="+id+"\", this)' style='cursor: pointer;' /></td>");
					}
					else
					{
						$('#'+idVal).html("<td>"+date+"</td><td>"+status+"</td><td>"+backlog_id+"</td><td>"+task_id+"</td><td>"+task_description+"</td><td>"+capitalizeWorkStatus+"</td>"
								+"<td>"+time+"</td><td><img src='img/edit.png' title=\"Click to edit entry.\" onclick='editRowEntry(this,"+id+")' style='cursor: pointer;'/></td>"
								+"<td><img src='img/delete1.png' title=\"Click to delete entry.\" onclick='onDelete(\"taskAction1.do?method=delete&id="+id+"\", this)' style='cursor: pointer;' /></td>");
					}

					$('#ajax_res').html("<div style='font-family: verdana, arial, sans-serif;font-size: 11px;color: blue;text-align: center;'>Data edited sucessfully.</div>");        		 
				} 
				else if(response=="session expired"){
					//document.getElementById('myDiv').style.visibility = "hidden";
					document.getElementById('save_id_buttonId').style.visibility = "hidden";
					document.getElementById('new_entry_buttonId').style.visibility = "hidden";
					document.getElementById('myMissingDates').style.visibility = "hidden";
					if($("#freeze_entry_id").length != 0){document.getElementById('freeze_entry_id').style.visibility = "hidden";}
					document.getElementById("ajax_res").innerHTML ="<div style='font-family: verdana, arial, sans-serif;font-size: 13px;color: red;text-align: center;'>"
						+"Your Session has been expired please <a href='http://pepq.calsoftlabs.com/effort/' target='_blank'>relogin</a> to retain your session.</div>";
				}
			},
			beforeSend: function(){
				showLoadingImage();
			},			
			complete: function(){
				hideLoadingImage();				
				//Sort table here.
				sortMyTable(table);
				hideEnteredMissingdate();
				$('#'+idVal).css("background-color","#96FFFF");	
				setTimeout( function(){$("table[id=dailyEntryTable] tr:nth-child(even)").css({"background": "#ffffff"});
				$("table[id=dailyEntryTable] tr:nth-child(odd)").css({"background": "#eeeeee"});}, 1500 );
			}, 
			error: function(e){
				//document.getElementById('myDiv').style.visibility = "hidden";
				document.getElementById('save_id_buttonId').style.visibility = "hidden";
				document.getElementById('new_entry_buttonId').style.visibility = "hidden";
				document.getElementById('myMissingDates').style.visibility = "hidden";
				if($("#freeze_entry_id").length != 0){document.getElementById('freeze_entry_id').style.visibility = "hidden";}
				document.getElementById("ajax_res").innerHTML ="<div style='font-family: verdana, arial, sans-serif;font-size: 13px;color: red;text-align: center;'>"
					+"Your Session has been expired please <a href='http://pepq.calsoftlabs.com/effort/' target='_blank'>relogin</a> to retain your session.</div>"; 					        		   
			}
		}); 
	}
} 
function editRowEntry(r,id){ 		 
	document.getElementById("ajax_res").innerHTML="";
	var i=r.parentNode.parentNode.rowIndex;  
	var idVal = document.getElementById('dailyEntryTable').rows[i].id;
	var table = document.getElementById('dailyEntryTable');
	var editFieldStatus = table.getElementsByTagName('input').length;
	if(editFieldStatus>0){
		var row = table.getElementsByTagName('input')[0].parentNode.parentNode;
		document.getElementById("ajax_res").innerHTML ="<div style='font-family: verdana, arial, sans-serif;font-size: 11px;color: red;text-align: center;'>"
			+"*Please save entry:"+row.rowIndex+" from top and proceed for edit.</div>";
		var saveTd = row.getElementsByTagName('td')[7];
		var mess = row.getElementsByTagName('td')[7].innerHTML;
		mess = mess.replace("*Save", "");
		row.getElementsByTagName('td')[7].innerHTML = mess+"<div style='color: red;text-align: left;'>*Save</div>";
		saveTd.style.backgroundColor="yellow";
	}
	else{
		var table = document.getElementById('dailyEntryTable');
		var tableTr = table.getElementsByTagName('tr')[i];
		var nCell = tableTr.getElementsByTagName('td'); 
		var date = nCell[0].innerHTML;
		var status = nCell[1].innerHTML;
		var backlog_id = nCell[2].innerHTML;
		var task_id = nCell[3].innerHTML;
		var task_description = nCell[4].innerHTML;
		var workStatus =nCell[5].innerHTML;
		var time = nCell[6].innerHTML;
		if(status=='Half Day'||status=='Down Time'||status=='Comp off'||status=='Travel'){
			if(workStatus.trim()=='Home'){
				$('#'+idVal).html("<td id=\"td_dt\" width=\"80px\"><INPUT TYPE=\"text\" NAME=\"task_date\" title=\"Click to pick date from calendar.\" id=\"popupDatepicker\" readonly=\"true\" SIZE=\"5\" MAXLENGTH=\"10\" value='"+date+"' STYLE=\"height:4;width: 65px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\"></td>"
						+"<td id=\"td_st\">"
						+"<SELECT NAME=\"status\" id=\"status\" title=\"Select appropriate category.\" onchange=\"changeDynamicTextBox(this);\" style=\"width: 120px;font-family: verdana, arial, sans-serif;font-size: 11px;\">"
						+"<option value=\""+status+"\">"+status+"</option>"
						+"<option value=\"Code Review\">Code Review</option>"
						+"<option value=\"Comp off\">Comp off</option>"
						+"<option value=\"Development\">Development</option>"
						+"<option value=\"Down Time\">Down Time</option>"
						+"<option value=\"Half Day\">Half Day</option>"
						+"<option value=\"KT Session\">KT Session</option>"
						+"<option value=\"Leave\">Leave</option>"
						+"<option value=\"Maintenance/Release support\">Maintenance/Release support</option>"
						+"<option value=\"Meeting\">Meeting</option>"
						+"<option value=\"Pre-Project Training\">Pre-Project Training</option>"								
						+"<option value=\"Project Management\">Project Management</option>"																				
						+"<option value=\"Public holiday\">Public holiday</option>"
						+"<option value=\"Task unassigned/Idle\">Task unassigned/Idle</option>"	
						+"<option value=\"Team Activity\">Team Activity</option>"
						+"<option value=\"Testing\">Testing</option>"														
						+"<option value=\"Training\">Training</option>"													
						+"<option value=\"Travel\">Travel</option>"
						+"</SELECT></td>"
						+"<td id=\"td_back\"><INPUT TYPE=\"text\" id=\"backlog_id\" disabled=\"disabled\" title=\"Enter BacklogId.\" NAME=\"backlog_id\" SIZE=\"5\" MAXLENGTH=\"20\" STYLE=\"height:4;width: 60px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\" value="+backlog_id+"></td>"
						+"<td id=\"td_task\"><INPUT TYPE=\"text\" id=\"task_id\" disabled=\"disabled\" NAME=\"task_id\" title=\"Enter TaskId.\" SIZE=\"5\" MAXLENGTH=\"20\" STYLE=\"height:4;width: 60px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\" value="+task_id+" ></td>"
						+"<td id=\"td_des\"><textarea name=\"task_description\" title=\"Add description here.\" id=\"des_id\" value="+task_description+" rows=\"2\" cols=\"2\" STYLE=\"width: 160px;height: 100px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\">"+task_description+"</textarea></td>"
						+"<td id=\"td_workStatus\" style=\"height: 100px;width: 200px;font-family: verdana, arial, sans-serif;font-size: 11px;\"><input type=\"radio\" id=\"work_status1"+count+"\" title=\"Pick Effort From\" name=\"work_status\" value=\"office\"> Office"
						+"<input type=\"radio\" id=\"work_status2"+count+"\" title=\"Pick Effort From\" name=\"work_status\" value=\"home\" checked>Home</td>"
						+"<td id=\"td_tm\"><INPUT TYPE=\"text\" NAME=\"time\" title=\"eg:HH.MM, HH:MM\" id=\"time\"  SIZE=\"5\" MAXLENGTH=\"5\" STYLE=\"height:4;width: 40px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\" value="+time+"></td>"
						+"<td id=\"ed_id\"><img alt=\"1px\" src=\"img/save.gif\" id=\"edit\" title=\"Click to save entry.\" SIZE=\"5\"  onClick=\"callAjaxEditSave(this,"+id+")\" style=\"height: 20px;width: 20px;font-family: verdana, arial, sans-serif;font-size: 11px;cursor: pointer;\"></td>"
						+"<td><img src='img/delete1.png' title=\"Click to delete entry.\" onclick='onDelete(\"taskAction1.do?method=delete&id="+id+"\", this)' style='cursor: pointer;' /></td>");  
			}
			else{
				$('#'+idVal).html("<td id=\"td_dt\" width=\"80px\"><INPUT TYPE=\"text\" NAME=\"task_date\" title=\"Click to pick date from calendar.\" id=\"popupDatepicker\" readonly=\"true\" SIZE=\"5\" MAXLENGTH=\"10\" value='"+date+"' STYLE=\"height:4;width: 65px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\"></td>"
						+"<td id=\"td_st\">"
						+"<SELECT NAME=\"status\" id=\"status\" title=\"Select appropriate category.\" onchange=\"changeDynamicTextBox(this);\" style=\"width: 120px;font-family: verdana, arial, sans-serif;font-size: 11px;\">"
						+"<option value=\""+status+"\">"+status+"</option>"
						+"<option value=\"Code Review\">Code Review</option>"
						+"<option value=\"Comp off\">Comp off</option>"
						+"<option value=\"Development\">Development</option>"
						+"<option value=\"Down Time\">Down Time</option>"
						+"<option value=\"Half Day\">Half Day</option>"
						+"<option value=\"KT Session\">KT Session</option>"
						+"<option value=\"Leave\">Leave</option>"
						+"<option value=\"Maintenance/Release support\">Maintenance/Release support</option>"
						+"<option value=\"Meeting\">Meeting</option>"
						+"<option value=\"Pre-Project Training\">Pre-Project Training</option>"								
						+"<option value=\"Project Management\">Project Management</option>"																				
						+"<option value=\"Public holiday\">Public holiday</option>"
						+"<option value=\"Task unassigned/Idle\">Task unassigned/Idle</option>"	
						+"<option value=\"Team Activity\">Team Activity</option>"
						+"<option value=\"Testing\">Testing</option>"														
						+"<option value=\"Training\">Training</option>"													
						+"<option value=\"Travel\">Travel</option>"
						+"</SELECT></td>"
						+"<td id=\"td_back\"><INPUT TYPE=\"text\" id=\"backlog_id\" disabled=\"disabled\" title=\"Enter BacklogId.\" NAME=\"backlog_id\" SIZE=\"5\" MAXLENGTH=\"20\" STYLE=\"height:4;width: 60px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\" value="+backlog_id+"></td>"
						+"<td id=\"td_task\"><INPUT TYPE=\"text\" id=\"task_id\" disabled=\"disabled\" NAME=\"task_id\" title=\"Enter TaskId.\" SIZE=\"5\" MAXLENGTH=\"20\" STYLE=\"height:4;width: 60px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\" value="+task_id+" ></td>"
						+"<td id=\"td_des\"><textarea name=\"task_description\" title=\"Add description here.\" id=\"des_id\" value="+task_description+" rows=\"2\" cols=\"2\" STYLE=\"width: 160px;height: 100px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\">"+task_description+"</textarea></td>"
						+"<td id=\"td_workStatus\" style=\"height: 100px;width: 200px;font-family: verdana, arial, sans-serif;font-size: 11px;\"><input type=\"radio\" id=\"work_status1"+count+"\" title=\"Pick Effort From\" name=\"work_status\" value=\"office\" checked> Office"
						+"<input type=\"radio\" id=\"work_status2"+count+"\" title=\"Pick Effort From\" name=\"work_status\" value=\"home\">Home</td>"
						+"<td id=\"td_tm\"><INPUT TYPE=\"text\" NAME=\"time\" title=\"eg:HH.MM, HH:MM\" id=\"time\"  SIZE=\"5\" MAXLENGTH=\"5\" STYLE=\"height:4;width: 40px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\" value="+time+"></td>"
						+"<td id=\"ed_id\"><img alt=\"1px\" src=\"img/save.gif\" id=\"edit\" title=\"Click to save entry.\" SIZE=\"5\"  onClick=\"callAjaxEditSave(this,"+id+")\" style=\"height: 20px;width: 20px;font-family: verdana, arial, sans-serif;font-size: 11px;cursor: pointer;\"></td>"
						+"<td><img src='img/delete1.png' title=\"Click to delete entry.\" onclick='onDelete(\"taskAction1.do?method=delete&id="+id+"\", this)' style='cursor: pointer;' /></td>");  
			}
		}
		else if(status=='Public holiday'){
			if(workStatus.trim()=='Home'){
				$('#'+idVal).html("<td id=\"td_dt\" width=\"80px\"><INPUT TYPE=\"text\" NAME=\"task_date\" title=\"Click to pick date from calendar.\" id=\"popupDatepicker\" readonly=\"true\" SIZE=\"5\" MAXLENGTH=\"10\" value='"+date+"' STYLE=\"height:4;width: 65px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\"></td>"
						+"<td id=\"td_st\">"
						+"<SELECT NAME=\"status\" id=\"status\" title=\"Select appropriate category.\" onchange=\"changeDynamicTextBox(this);\" style=\"width: 120px;font-family: verdana, arial, sans-serif;font-size: 11px;\">"
						+"<option value=\""+status+"\">"+status+"</option>"
						+"<option value=\"Code Review\">Code Review</option>"
						+"<option value=\"Comp off\">Comp off</option>"
						+"<option value=\"Development\">Development</option>"
						+"<option value=\"Down Time\">Down Time</option>"
						+"<option value=\"Half Day\">Half Day</option>"
						+"<option value=\"KT Session\">KT Session</option>"
						+"<option value=\"Leave\">Leave</option>"
						+"<option value=\"Maintenance/Release support\">Maintenance/Release support</option>"
						+"<option value=\"Meeting\">Meeting</option>"
						+"<option value=\"Pre-Project Training\">Pre-Project Training</option>"								
						+"<option value=\"Project Management\">Project Management</option>"																				
						+"<option value=\"Public holiday\">Public holiday</option>"
						+"<option value=\"Task unassigned/Idle\">Task unassigned/Idle</option>"	
						+"<option value=\"Team Activity\">Team Activity</option>"
						+"<option value=\"Testing\">Testing</option>"														
						+"<option value=\"Training\">Training</option>"													
						+"<option value=\"Travel\">Travel</option>"
						+"</SELECT></td>"
						+"<td id=\"td_back\"><INPUT TYPE=\"text\" id=\"backlog_id\" disabled=\"disabled\" title=\"Enter BacklogId.\" NAME=\"backlog_id\" SIZE=\"5\" MAXLENGTH=\"20\" STYLE=\"height:4;width: 60px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\" value="+backlog_id+"></td>"
						+"<td id=\"td_task\"><INPUT TYPE=\"text\" id=\"task_id\" disabled=\"disabled\" NAME=\"task_id\" title=\"Enter TaskId.\" SIZE=\"5\" MAXLENGTH=\"20\" STYLE=\"height:4;width: 60px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\" value="+task_id+" ></td>"
						+"<td id=\"td_des\"><textarea name=\"task_description\" title=\"Add description here.\" id=\"des_id\" disabled=\"disabled\" value="+task_description+" rows=\"2\" cols=\"2\" STYLE=\"width: 160px;height: 100px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\">"+task_description+"</textarea></td>"
						+"<td id=\"td_workStatus\" style=\"height: 100px;width: 200px;font-family: verdana, arial, sans-serif;font-size: 11px;\"><input type=\"radio\" id=\"work_status1"+count+"\" title=\"Pick Effort From\" name=\"work_status\" value=\"office\"> Office"
						+"<input type=\"radio\" id=\"work_status2"+count+"\" title=\"Pick Effort From\" name=\"work_status\" value=\"home\" checked>Home</td>"
						+"<td id=\"td_tm\"><INPUT TYPE=\"text\" NAME=\"time\" title=\"eg:HH.MM, HH:MM\" id=\"time\" disabled=\"disabled\" SIZE=\"5\" MAXLENGTH=\"5\" STYLE=\"height:4;width: 40px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\" value="+time+"></td>"
						+"<td id=\"ed_id\"><img alt=\"1px\" id=\""+id+"\" src=\"img/save.gif\" id=\"edit\" title=\"Click to save entry.\" SIZE=\"5\"  onClick=\"callAjaxEditSave(this,"+id+")\" style=\"height: 20px;width: 20px;font-family: verdana, arial, sans-serif;font-size: 11px;cursor: pointer;\"></td>"
						+"<td><img src='img/delete1.png' title=\"Click to delete entry.\" onclick='onDelete(\"taskAction1.do?method=delete&id="+id+"\", this)' style='cursor: pointer;' /></td>");    
			}
			else{
				$('#'+idVal).html("<td id=\"td_dt\" width=\"80px\"><INPUT TYPE=\"text\" NAME=\"task_date\" title=\"Click to pick date from calendar.\" id=\"popupDatepicker\" readonly=\"true\" SIZE=\"5\" MAXLENGTH=\"10\" value='"+date+"' STYLE=\"height:4;width: 65px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\"></td>"
						+"<td id=\"td_st\">"
						+"<SELECT NAME=\"status\" id=\"status\" title=\"Select appropriate category.\" onchange=\"changeDynamicTextBox(this);\" style=\"width: 120px;font-family: verdana, arial, sans-serif;font-size: 11px;\">"
						+"<option value=\""+status+"\">"+status+"</option>"
						+"<option value=\"Code Review\">Code Review</option>"
						+"<option value=\"Comp off\">Comp off</option>"
						+"<option value=\"Development\">Development</option>"
						+"<option value=\"Down Time\">Down Time</option>"
						+"<option value=\"Half Day\">Half Day</option>"
						+"<option value=\"KT Session\">KT Session</option>"
						+"<option value=\"Leave\">Leave</option>"
						+"<option value=\"Maintenance/Release support\">Maintenance/Release support</option>"
						+"<option value=\"Meeting\">Meeting</option>"
						+"<option value=\"Pre-Project Training\">Pre-Project Training</option>"								
						+"<option value=\"Project Management\">Project Management</option>"																				
						+"<option value=\"Public holiday\">Public holiday</option>"
						+"<option value=\"Task unassigned/Idle\">Task unassigned/Idle</option>"	
						+"<option value=\"Team Activity\">Team Activity</option>"
						+"<option value=\"Testing\">Testing</option>"														
						+"<option value=\"Training\">Training</option>"													
						+"<option value=\"Travel\">Travel</option>"
						+"</SELECT></td>"
						+"<td id=\"td_back\"><INPUT TYPE=\"text\" id=\"backlog_id\" disabled=\"disabled\" title=\"Enter BacklogId.\" NAME=\"backlog_id\" SIZE=\"5\" MAXLENGTH=\"20\" STYLE=\"height:4;width: 60px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\" value="+backlog_id+"></td>"
						+"<td id=\"td_task\"><INPUT TYPE=\"text\" id=\"task_id\" disabled=\"disabled\" NAME=\"task_id\" title=\"Enter TaskId.\" SIZE=\"5\" MAXLENGTH=\"20\" STYLE=\"height:4;width: 60px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\" value="+task_id+" ></td>"
						+"<td id=\"td_des\"><textarea name=\"task_description\" title=\"Add description here.\" id=\"des_id\" disabled=\"disabled\" value="+task_description+" rows=\"2\" cols=\"2\" STYLE=\"width: 160px;height: 100px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\">"+task_description+"</textarea></td>"
						+"<td id=\"td_workStatus\" style=\"font-family: verdana, arial, sans-serif;font-size: 11px;\"><input type=\"radio\" id=\"work_status1"+count+"\" title=\"Pick Effort From\" name=\"work_status\" value=\"office\" checked> Office"
						+"<input type=\"radio\" id=\"work_status2"+count+"\" title=\"Pick Effort From\" name=\"work_status\" value=\"home\">Home</td>"
						+"<td id=\"td_tm\"><INPUT TYPE=\"text\" NAME=\"time\" title=\"eg:HH.MM, HH:MM\" id=\"time\" disabled=\"disabled\" SIZE=\"5\" MAXLENGTH=\"5\" STYLE=\"height:4;width: 40px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\" value="+time+"></td>"
						+"<td id=\"ed_id\"><img alt=\"1px\" src=\"img/save.gif\" id=\"edit\" title=\"Click to save entry.\" SIZE=\"5\"  onClick=\"callAjaxEditSave(this,"+id+")\" style=\"height: 20px;width: 20px;font-family: verdana, arial, sans-serif;font-size: 11px;cursor: pointer;\"></td>"
						+"<td><img src='img/delete1.png' title=\"Click to delete entry.\" onclick='onDelete(\"taskAction1.do?method=delete&id="+id+"\", this)' style='cursor: pointer;' /></td>");    
			}
		}
		else if(status=='Leave'){
			if(workStatus.trim()=='Home'){
				$('#'+idVal).html("<td id=\"td_dt\" width=\"80px\"><INPUT TYPE=\"text\" NAME=\"task_date\" title=\"Click to pick date from calendar.\" id=\"popupDatepicker\" readonly=\"true\" SIZE=\"5\" MAXLENGTH=\"10\" value='"+date+"' STYLE=\"height:4;width: 65px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\"></td>"
						+"<td id=\"td_st\">"
						+"<SELECT NAME=\"status\" id=\"status\" title=\"Select appropriate category.\" onchange=\"changeDynamicTextBox(this);\" style=\"width: 120px;font-family: verdana, arial, sans-serif;font-size: 11px;\">"
						+"<option value=\""+status+"\">"+status+"</option>"
						+"<option value=\"Code Review\">Code Review</option>"
						+"<option value=\"Comp off\">Comp off</option>"
						+"<option value=\"Development\">Development</option>"
						+"<option value=\"Down Time\">Down Time</option>"
						+"<option value=\"Half Day\">Half Day</option>"
						+"<option value=\"KT Session\">KT Session</option>"
						+"<option value=\"Leave\">Leave</option>"
						+"<option value=\"Maintenance/Release support\">Maintenance/Release support</option>"
						+"<option value=\"Meeting\">Meeting</option>"
						+"<option value=\"Pre-Project Training\">Pre-Project Training</option>"								
						+"<option value=\"Project Management\">Project Management</option>"																				
						+"<option value=\"Public holiday\">Public holiday</option>"
						+"<option value=\"Task unassigned/Idle\">Task unassigned/Idle</option>"	
						+"<option value=\"Team Activity\">Team Activity</option>"
						+"<option value=\"Testing\">Testing</option>"														
						+"<option value=\"Training\">Training</option>"													
						+"<option value=\"Travel\">Travel</option>"
						+"</SELECT></td>"
						+"<td id=\"td_back\"><INPUT TYPE=\"text\" id=\"backlog_id\" disabled=\"disabled\" title=\"Enter BacklogId.\" NAME=\"backlog_id\" SIZE=\"5\" MAXLENGTH=\"20\" STYLE=\"height:4;width: 60px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\" value="+backlog_id+"></td>"
						+"<td id=\"td_task\"><INPUT TYPE=\"text\" id=\"task_id\" disabled=\"disabled\" NAME=\"task_id\" title=\"Enter TaskId.\" SIZE=\"5\" MAXLENGTH=\"20\" STYLE=\"height:4;width: 60px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\" value="+task_id+" ></td>"
						+"<td id=\"td_des\"><textarea name=\"task_description\" title=\"Add description here.\" id=\"des_id\" value="+task_description+" rows=\"2\" cols=\"2\" STYLE=\"width: 160px;height: 100px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\">"+task_description+"</textarea></td>"
						+"<td id=\"td_workStatus\" style=\"height: 100px;width: 200px;font-family: verdana, arial, sans-serif;font-size: 11px;\"><input type=\"radio\" id=\"work_status1"+count+"\" title=\"Pick Effort From\" name=\"work_status\" value=\"office\"> Office"
						+"<input type=\"radio\" id=\"work_status2"+count+"\" title=\"Pick Effort From\" name=\"work_status\" value=\"home\" checked>Home</td>"
						+"<td id=\"td_tm\"><INPUT TYPE=\"text\" NAME=\"time\" title=\"eg:HH.MM, HH:MM\" id=\"time\" disabled=\"disabled\" SIZE=\"5\" MAXLENGTH=\"5\" STYLE=\"height:4;width: 40px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\" value="+time+"></td>"
						+"<td id=\"ed_id\"><img alt=\"1px\" id=\""+id+"\" src=\"img/save.gif\" id=\"edit\" title=\"Click to save entry.\" SIZE=\"5\"  onClick=\"callAjaxEditSave(this,"+id+")\" style=\"height: 20px;width: 20px;font-family: verdana, arial, sans-serif;font-size: 11px;cursor: pointer;\"></td>"
						+"<td><img src='img/delete1.png' title=\"Click to delete entry.\" onclick='onDelete(\"taskAction1.do?method=delete&id="+id+"\", this)' style='cursor: pointer;' /></td>");    
			}
			else{
				$('#'+idVal).html("<td id=\"td_dt\" width=\"80px\"><INPUT TYPE=\"text\" NAME=\"task_date\" title=\"Click to pick date from calendar.\" id=\"popupDatepicker\" readonly=\"true\" SIZE=\"5\" MAXLENGTH=\"10\" value='"+date+"' STYLE=\"height:4;width: 65px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\"></td>"
						+"<td id=\"td_st\">"
						+"<SELECT NAME=\"status\" id=\"status\" title=\"Select appropriate category.\" onchange=\"changeDynamicTextBox(this);\" style=\"width: 120px;font-family: verdana, arial, sans-serif;font-size: 11px;\">"
						+"<option value=\""+status+"\">"+status+"</option>"
						+"<option value=\"Code Review\">Code Review</option>"
						+"<option value=\"Comp off\">Comp off</option>"
						+"<option value=\"Development\">Development</option>"
						+"<option value=\"Down Time\">Down Time</option>"
						+"<option value=\"Half Day\">Half Day</option>"
						+"<option value=\"KT Session\">KT Session</option>"
						+"<option value=\"Leave\">Leave</option>"
						+"<option value=\"Maintenance/Release support\">Maintenance/Release support</option>"
						+"<option value=\"Meeting\">Meeting</option>"
						+"<option value=\"Pre-Project Training\">Pre-Project Training</option>"								
						+"<option value=\"Project Management\">Project Management</option>"																				
						+"<option value=\"Public holiday\">Public holiday</option>"
						+"<option value=\"Task unassigned/Idle\">Task unassigned/Idle</option>"	
						+"<option value=\"Team Activity\">Team Activity</option>"
						+"<option value=\"Testing\">Testing</option>"														
						+"<option value=\"Training\">Training</option>"													
						+"<option value=\"Travel\">Travel</option>"
						+"</SELECT></td>"
						+"<td id=\"td_back\"><INPUT TYPE=\"text\" id=\"backlog_id\" disabled=\"disabled\" title=\"Enter BacklogId.\" NAME=\"backlog_id\" SIZE=\"5\" MAXLENGTH=\"20\" STYLE=\"height:4;width: 60px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\" value="+backlog_id+"></td>"
						+"<td id=\"td_task\"><INPUT TYPE=\"text\" id=\"task_id\" disabled=\"disabled\" NAME=\"task_id\" title=\"Enter TaskId.\" SIZE=\"5\" MAXLENGTH=\"20\" STYLE=\"height:4;width: 60px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\" value="+task_id+" ></td>"
						+"<td id=\"td_des\"><textarea name=\"task_description\" title=\"Add description here.\" id=\"des_id\" value="+task_description+" rows=\"2\" cols=\"2\" STYLE=\"width: 160px;height: 100px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\">"+task_description+"</textarea></td>"
						+"<td id=\"td_workStatus\" style=\"font-family: verdana, arial, sans-serif;font-size: 11px;\"><input type=\"radio\" id=\"work_status1"+count+"\" title=\"Pick Effort From\" name=\"work_status\" value=\"office\" checked> Office"
						+"<input type=\"radio\" id=\"work_status2"+count+"\" title=\"Pick Effort From\" name=\"work_status\" value=\"home\">Home</td>"
						+"<td id=\"td_tm\"><INPUT TYPE=\"text\" NAME=\"time\" title=\"eg:HH.MM, HH:MM\" id=\"time\" disabled=\"disabled\" SIZE=\"5\" MAXLENGTH=\"5\" STYLE=\"height:4;width: 40px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\" value="+time+"></td>"
						+"<td id=\"ed_id\"><img alt=\"1px\" src=\"img/save.gif\" id=\"edit\" title=\"Click to save entry.\" SIZE=\"5\"  onClick=\"callAjaxEditSave(this,"+id+")\" style=\"height: 20px;width: 20px;font-family: verdana, arial, sans-serif;font-size: 11px;cursor: pointer;\"></td>"
						+"<td><img src='img/delete1.png' title=\"Click to delete entry.\" onclick='onDelete(\"taskAction1.do?method=delete&id="+id+"\", this)' style='cursor: pointer;' /></td>");    
			}
		}	
		else{
			if(workStatus.trim()=='Home'){
				$('#'+idVal).html("<td id=\"td_dt\" width=\"80px\"><INPUT TYPE=\"text\" NAME=\"task_date\" title=\"Click to pick date from calendar.\" id=\"popupDatepicker\" readonly=\"true\" SIZE=\"5\" MAXLENGTH=\"10\" value='"+date+"' STYLE=\"height:4;width: 65px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\"></td>"
						+"<td id=\"td_st\">"
						+"<SELECT NAME=\"status\" id=\"status\" title=\"Select appropriate category.\" onchange=\"changeDynamicTextBox(this);\" style=\"width: 120px;font-family: verdana, arial, sans-serif;font-size: 11px;\">"
						+"<option value=\""+status+"\">"+status+"</option>"
						+"<option value=\"Code Review\">Code Review</option>"
						+"<option value=\"Comp off\">Comp off</option>"
						+"<option value=\"Development\">Development</option>"
						+"<option value=\"Down Time\">Down Time</option>"
						+"<option value=\"Half Day\">Half Day</option>"
						+"<option value=\"KT Session\">KT Session</option>"
						+"<option value=\"Leave\">Leave</option>"
						+"<option value=\"Maintenance/Release support\">Maintenance/Release support</option>"
						+"<option value=\"Meeting\">Meeting</option>"
						+"<option value=\"Pre-Project Training\">Pre-Project Training</option>"								
						+"<option value=\"Project Management\">Project Management</option>"																				
						+"<option value=\"Public holiday\">Public holiday</option>"
						+"<option value=\"Task unassigned/Idle\">Task unassigned/Idle</option>"	
						+"<option value=\"Team Activity\">Team Activity</option>"
						+"<option value=\"Testing\">Testing</option>"														
						+"<option value=\"Training\">Training</option>"													
						+"<option value=\"Travel\">Travel</option>"
						+"</SELECT></td>"
						+"<td id=\"td_back\"><INPUT TYPE=\"text\" id=\"backlog_id\" title=\"Enter BacklogId.\" NAME=\"backlog_id\" SIZE=\"5\" MAXLENGTH=\"20\" STYLE=\"height:4;width: 60px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\" value="+backlog_id+"></td>"
						+"<td id=\"td_task\"><INPUT TYPE=\"text\" id=\"task_id\" NAME=\"task_id\" title=\"Enter TaskId.\" SIZE=\"5\" MAXLENGTH=\"20\" STYLE=\"height:4;width: 60px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\" value="+task_id+" ></td>"
						+"<td id=\"td_des\"><textarea name=\"task_description\" title=\"Add description here.\" id=\"des_id\" value="+task_description+" rows=\"2\" cols=\"2\" STYLE=\"width: 160px;height: 100px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\">"+task_description+"</textarea></td>"
						+"<td id=\"td_workStatus\" style=\"height: 100px;width: 200px;font-family: verdana, arial, sans-serif;font-size: 11px;\"><input type=\"radio\" id=\"work_status1"+count+"\" title=\"Pick Effort From\" name=\"work_status\" value=\"office\"> Office"
						+"<input type=\"radio\" id=\"work_status2"+count+"\" title=\"Pick Effort From\" name=\"work_status\" value=\"home\" checked>Home</td>"
						+"<td id=\"td_tm\"><INPUT TYPE=\"text\" NAME=\"time\" title=\"eg:HH.MM, HH:MM\" id=\"time\"  SIZE=\"5\" MAXLENGTH=\"5\" STYLE=\"height:4;width: 40px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\" value="+time+"></td>"
						+"<td id=\"ed_id\"><img alt=\"1px\" src=\"img/save.gif\" id=\"edit\" title=\"Click to save entry.\" SIZE=\"5\"  onClick=\"callAjaxEditSave(this,"+id+")\" style=\"height: 20px;width: 20px;font-family: verdana, arial, sans-serif;font-size: 11px;cursor: pointer;\"></td>"
						+"<td><img src='img/delete1.png' title=\"Click to delete entry.\" onclick='onDelete(\"taskAction1.do?method=delete&id="+id+"\", this)' style='cursor: pointer;' /></td>");  
			}
			else{
				$('#'+idVal).html("<td id=\"td_dt\" width=\"80px\"><INPUT TYPE=\"text\" NAME=\"task_date\" title=\"Click to pick date from calendar.\" id=\"popupDatepicker\" readonly=\"true\" SIZE=\"5\" MAXLENGTH=\"10\" value='"+date+"' STYLE=\"height:4;width: 65px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\"></td>"
						+"<td id=\"td_st\">"
						+"<SELECT NAME=\"status\" id=\"status\" title=\"Select appropriate category.\" onchange=\"changeDynamicTextBox(this);\" style=\"width: 120px;font-family: verdana, arial, sans-serif;font-size: 11px;\">"
						+"<option value=\""+status+"\">"+status+"</option>"
						+"<option value=\"Code Review\">Code Review</option>"
						+"<option value=\"Comp off\">Comp off</option>"
						+"<option value=\"Development\">Development</option>"
						+"<option value=\"Down Time\">Down Time</option>"
						+"<option value=\"Half Day\">Half Day</option>"
						+"<option value=\"KT Session\">KT Session</option>"
						+"<option value=\"Leave\">Leave</option>"
						+"<option value=\"Maintenance/Release support\">Maintenance/Release support</option>"
						+"<option value=\"Meeting\">Meeting</option>"
						+"<option value=\"Pre-Project Training\">Pre-Project Training</option>"								
						+"<option value=\"Project Management\">Project Management</option>"																				
						+"<option value=\"Public holiday\">Public holiday</option>"
						+"<option value=\"Task unassigned/Idle\">Task unassigned/Idle</option>"	
						+"<option value=\"Team Activity\">Team Activity</option>"
						+"<option value=\"Testing\">Testing</option>"														
						+"<option value=\"Training\">Training</option>"													
						+"<option value=\"Travel\">Travel</option>"
						+"</SELECT></td>"
						+"<td id=\"td_back\"><INPUT TYPE=\"text\" id=\"backlog_id\" title=\"Enter BacklogId.\" NAME=\"backlog_id\" SIZE=\"5\" MAXLENGTH=\"20\" STYLE=\"height:4;width: 60px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\" value="+backlog_id+"></td>"
						+"<td id=\"td_task\"><INPUT TYPE=\"text\" id=\"task_id\" NAME=\"task_id\" title=\"Enter TaskId.\" SIZE=\"5\" MAXLENGTH=\"20\" STYLE=\"height:4;width: 60px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\" value="+task_id+" ></td>"
						+"<td id=\"td_des\"><textarea name=\"task_description\" title=\"Add description here.\" id=\"des_id\" value="+task_description+" rows=\"2\" cols=\"2\" STYLE=\"width: 160px;height: 100px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\">"+task_description+"</textarea></td>"
						+"<td id=\"td_workStatus\" style=\"height: 100px;width: 200px;font-family: verdana, arial, sans-serif;font-size: 11px;\"><input type=\"radio\" id=\"work_status1"+count+"\" title=\"Pick Effort From\" name=\"work_status\" value=\"office\" checked> Office"
						+"<input type=\"radio\" id=\"work_status2"+count+"\" title=\"Pick Effort From\" name=\"work_status\" value=\"home\">Home</td>"
						+"<td id=\"td_tm\"><INPUT TYPE=\"text\" NAME=\"time\" title=\"eg:HH.MM, HH:MM\" id=\"time\"  SIZE=\"5\" MAXLENGTH=\"5\" STYLE=\"height:4;width: 40px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\" value="+time+"></td>"
						+"<td id=\"ed_id\"><img alt=\"1px\" src=\"img/save.gif\" id=\"edit\" title=\"Click to save entry.\" SIZE=\"5\"  onClick=\"callAjaxEditSave(this,"+id+")\" style=\"height: 20px;width: 20px;font-family: verdana, arial, sans-serif;font-size: 11px;cursor: pointer;\"></td>"
						+"<td><img src='img/delete1.png' title=\"Click to delete entry.\" onclick='onDelete(\"taskAction1.do?method=delete&id="+id+"\", this)' style='cursor: pointer;' /></td>");  
			}
		}
		if (status =="Leave"||status =="Public holiday"||status =="Half Day"||status =="Comp off") {    	
			document.getElementById("backlog_id").disabled='true';        
			document.getElementById("task_id").disabled='true';
		}  
		document.getElementById("des_id").focus();
		$('#popupDatepicker').datepick(); 
		count++;
	}
} 

function doValidation(date, status, backlog_id, task_id, time, currentRowId){
	var dateFromMonthSelection = $('#selectedMonthId').val();
	var modifiedEnteredDate = "";
	if(date != ""){
		var splittedDate = date.split("/");
		modifiedEnteredDate = splittedDate[2]+"-"+splittedDate[0];
	}
	if(date == ""){
		document.getElementById(currentRowId).getElementsByTagName('td')[0].innerHTML ="<INPUT TYPE=\"text\" NAME=\"task_date\" title=\"Click to pick date from calendar.\" " +
				"id=\"popupDatepicker"+count_calendar_id+"\" readonly=\"true\" SIZE=\"5\" MAXLENGTH=\"10\" value=\""+date+"\" " +
				"STYLE=\"height:4;width: 65px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;border: 1 solid;border-color:red;background-color:#FFFFE7;\">"+
				"<div style='color: red'>*Date</div>";	
		$('#popupDatepicker'+count_calendar_id).datepick();
		count_calendar_id++;
		return false;
	}
	else if(dateFromMonthSelection != modifiedEnteredDate){
		document.getElementById(currentRowId).getElementsByTagName('td')[0].innerHTML ="<INPUT TYPE=\"text\" NAME=\"task_date\" title=\"Click to pick date from calendar.\" " +
			"id=\"popupDatepicker"+count_calendar_id+"\" readonly=\"true\" SIZE=\"5\" MAXLENGTH=\"10\" value=\""+date+"\" " +
			"STYLE=\"height:4;width: 65px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;border: 1 solid;border-color:red;background-color:#FFFFE7;\">"+
			"<div style='color: red'>*Date</div>";
		$('#popupDatepicker'+count_calendar_id).datepick();
		document.getElementById("ajax_res").innerHTML ="<div style='font-family: verdana, arial, sans-serif;font-size: 11px;color: red;text-align: center;'>"
			+"*Please select date from selected month <b>"+$('#selectedMonthId option:selected').text()+"</b>.</div>";
		count_calendar_id++;
		return false;
	}
	else if( status==""){
		document.getElementById(currentRowId).getElementsByTagName('td')[0].innerHTML ="<INPUT TYPE=\"text\" NAME=\"task_date\" title=\"Click to pick date from calendar.\" " +
			"id=\"popupDatepicker"+count_calendar_id+"\" readonly=\"true\" SIZE=\"5\" MAXLENGTH=\"10\" value=\""+date+"\" " +
			"STYLE=\"height:4;width: 65px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\">";
		$('#popupDatepicker'+count_calendar_id).datepick();
		count_calendar_id++;
		
		document.getElementById(currentRowId).getElementsByTagName('td')[1].innerHTML="<SELECT NAME=\"status\" id=\"status\" title=\"Select appropriate category.\" onchange=\"changeDynamicTextBox(this);\" style=\"width: 120px;font-family: verdana, arial, sans-serif;font-size: 11px;border: 1 solid;border-color:red;background-color:#FFFFE7;\">"
			+"<option value=\"\">Select</option>"
			+"<option value=\"Code Review\">Code Review</option>"
			+"<option value=\"Comp off\">Comp off</option>"
			+"<option value=\"Development\">Development</option>"
			+"<option value=\"Down Time\">Down Time</option>"
			+"<option value=\"Half Day\">Half Day</option>"
			+"<option value=\"KT Session\">KT Session</option>"
			+"<option value=\"Leave\">Leave</option>"
			+"<option value=\"Maintenance/Release support\">Maintenance/Release support</option>"
			+"<option value=\"Meeting\">Meeting</option>"
			+"<option value=\"Pre-Project Training\">Pre-Project Training</option>"								
			+"<option value=\"Project Management\">Project Management</option>"																				
			+"<option value=\"Public holiday\">Public holiday</option>"
			+"<option value=\"Task unassigned/Idle\">Task unassigned/Idle</option>"	
			+"<option value=\"Team Activity\">Team Activity</option>"
			+"<option value=\"Testing\">Testing</option>"														
			+"<option value=\"Training\">Training</option>"													
			+"<option value=\"Travel\">Travel</option>"
			+"</SELECT>";
		document.getElementById("ajax_res").innerHTML ="<div style='font-family: verdana, arial, sans-serif;font-size: 11px;color: red;text-align: center;'>"
			+"*Please select category.</div>";
		return false;
	}		
	else if(status!="" &&  backlog_id==""){		
		document.getElementById(currentRowId).getElementsByTagName('td')[0].innerHTML ="<INPUT TYPE=\"text\" NAME=\"task_date\" title=\"Click to pick date from calendar.\" " +
			"id=\"popupDatepicker"+count_calendar_id+"\" readonly=\"true\" SIZE=\"5\" MAXLENGTH=\"10\" value=\""+date+"\" " +
			"STYLE=\"height:4;width: 65px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\">";
		$('#popupDatepicker'+count_calendar_id).datepick();
		count_calendar_id++;
		
		document.getElementById(currentRowId).getElementsByTagName('td')[1].innerHTML="<SELECT NAME=\"status\" id=\"status\" title=\"Select appropriate category.\" onchange=\"changeDynamicTextBox(this);\" style=\"width: 120px;font-family: verdana, arial, sans-serif;font-size: 11px;border: 1 solid;border-color: #3C3CFF;;\">"
			+"<option value=\""+status+"\">"+status+"</option>"
			+"<option value=\"Code Review\">Code Review</option>"
			+"<option value=\"Comp off\">Comp off</option>"
			+"<option value=\"Development\">Development</option>"
			+"<option value=\"Down Time\">Down Time</option>"
			+"<option value=\"Half Day\">Half Day</option>"
			+"<option value=\"KT Session\">KT Session</option>"
			+"<option value=\"Leave\">Leave</option>"
			+"<option value=\"Maintenance/Release support\">Maintenance/Release support</option>"
			+"<option value=\"Meeting\">Meeting</option>"
			+"<option value=\"Pre-Project Training\">Pre-Project Training</option>"								
			+"<option value=\"Project Management\">Project Management</option>"																				
			+"<option value=\"Public holiday\">Public holiday</option>"
			+"<option value=\"Task unassigned/Idle\">Task unassigned/Idle</option>"	
			+"<option value=\"Team Activity\">Team Activity</option>"
			+"<option value=\"Testing\">Testing</option>"														
			+"<option value=\"Training\">Training</option>"													
			+"<option value=\"Travel\">Travel</option>"
			+"</SELECT>";
		document.getElementById(currentRowId).getElementsByTagName('td')[2].innerHTML="<INPUT TYPE=\"text\" id=\"backlog_id\" title=\"Enter BacklogId.\" "
			+" NAME=\"backlog_id\" SIZE=\"5\" MAXLENGTH=\"20\" STYLE=\"height:4;width: 60px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;border-color:red;background-color:#FFFFE7;\" "
			+" value="+backlog_id+" >"
			+"<div style='color: red'>*Backlog</div>";
		return false;
	}
	else if((status!="" &&  backlog_id!="") && task_id==""){	
		document.getElementById(currentRowId).getElementsByTagName('td')[0].innerHTML ="<INPUT TYPE=\"text\" NAME=\"task_date\" title=\"Click to pick date from calendar.\" " +
			"id=\"popupDatepicker"+count_calendar_id+"\" readonly=\"true\" SIZE=\"5\" MAXLENGTH=\"10\" value=\""+date+"\" " +
			"STYLE=\"height:4;width: 65px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\">";
		$('#popupDatepicker'+count_calendar_id).datepick();
		count_calendar_id++;
		
		document.getElementById(currentRowId).getElementsByTagName('td')[1].innerHTML="<SELECT NAME=\"status\" id=\"status\" title=\"Select appropriate category.\" onchange=\"changeDynamicTextBox(this);\" style=\"width: 120px;font-family: verdana, arial, sans-serif;font-size: 11px;border: 1 solid;border-color: #3C3CFF;;\">"
			+"<option value=\""+status+"\">"+status+"</option>"
			+"<option value=\"Code Review\">Code Review</option>"
			+"<option value=\"Comp off\">Comp off</option>"
			+"<option value=\"Development\">Development</option>"
			+"<option value=\"Down Time\">Down Time</option>"
			+"<option value=\"Half Day\">Half Day</option>"
			+"<option value=\"KT Session\">KT Session</option>"
			+"<option value=\"Leave\">Leave</option>"
			+"<option value=\"Maintenance/Release support\">Maintenance/Release support</option>"
			+"<option value=\"Meeting\">Meeting</option>"
			+"<option value=\"Pre-Project Training\">Pre-Project Training</option>"								
			+"<option value=\"Project Management\">Project Management</option>"																				
			+"<option value=\"Public holiday\">Public holiday</option>"
			+"<option value=\"Task unassigned/Idle\">Task unassigned/Idle</option>"	
			+"<option value=\"Team Activity\">Team Activity</option>"
			+"<option value=\"Testing\">Testing</option>"														
			+"<option value=\"Training\">Training</option>"													
			+"<option value=\"Travel\">Travel</option>"
			+"</SELECT>";
		document.getElementById(currentRowId).getElementsByTagName('td')[2].innerHTML="<INPUT TYPE=\"text\" id=\"backlog_id\" title=\"Enter BacklogId.\" "
			+" NAME=\"backlog_id\" SIZE=\"5\" MAXLENGTH=\"20\" STYLE=\"height:4;width: 60px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\" "
			+" value="+backlog_id+">";
		document.getElementById(currentRowId).getElementsByTagName('td')[3].innerHTML="<INPUT TYPE=\"text\" id=\"task_id\" NAME=\"task_id\" title=\"Enter TaskId.\" "
			+" SIZE=\"5\" MAXLENGTH=\"20\" STYLE=\"height:4;width: 60px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;border-color:red;background-color:#FFFFE7;\" value="+task_id+" >"
			+"<div style='color: red'>*Task Id</div>";
		return false;
	}
	else if((status!="" && backlog_id!="") && task_id!=""){		
		document.getElementById(currentRowId).getElementsByTagName('td')[0].innerHTML ="<INPUT TYPE=\"text\" NAME=\"task_date\" title=\"Click to pick date from calendar.\" " +
			"id=\"popupDatepicker"+count_calendar_id+"\" readonly=\"true\" SIZE=\"5\" MAXLENGTH=\"10\" value=\""+date+"\" " +
			"STYLE=\"height:4;width: 65px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\">";
		$('#popupDatepicker'+count_calendar_id).datepick();
		count_calendar_id++;
		
		document.getElementById(currentRowId).getElementsByTagName('td')[1].innerHTML="<SELECT NAME=\"status\" id=\"status\" title=\"Select appropriate category.\" onchange=\"changeDynamicTextBox(this);\" style=\"width: 120px;font-family: verdana, arial, sans-serif;font-size: 11px;border: 1 solid;border-color: #3C3CFF;;\">"
			+"<option value=\""+status+"\">"+status+"</option>"
			+"<option value=\"Code Review\">Code Review</option>"
			+"<option value=\"Comp off\">Comp off</option>"
			+"<option value=\"Development\">Development</option>"
			+"<option value=\"Down Time\">Down Time</option>"
			+"<option value=\"Half Day\">Half Day</option>"
			+"<option value=\"KT Session\">KT Session</option>"
			+"<option value=\"Leave\">Leave</option>"
			+"<option value=\"Maintenance/Release support\">Maintenance/Release support</option>"
			+"<option value=\"Meeting\">Meeting</option>"
			+"<option value=\"Pre-Project Training\">Pre-Project Training</option>"								
			+"<option value=\"Project Management\">Project Management</option>"																				
			+"<option value=\"Public holiday\">Public holiday</option>"
			+"<option value=\"Task unassigned/Idle\">Task unassigned/Idle</option>"	
			+"<option value=\"Team Activity\">Team Activity</option>"
			+"<option value=\"Testing\">Testing</option>"														
			+"<option value=\"Training\">Training</option>"													
			+"<option value=\"Travel\">Travel</option>"
			+"</SELECT>";
		document.getElementById(currentRowId).getElementsByTagName('td')[2].innerHTML="<INPUT TYPE=\"text\" id=\"backlog_id\" title=\"Enter BacklogId.\" NAME=\"backlog_id\" SIZE=\"5\" "
			+" MAXLENGTH=\"20\" STYLE=\"height:4;width: 60px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\" value="+backlog_id+">";
		document.getElementById(currentRowId).getElementsByTagName('td')[3].innerHTML="<INPUT TYPE=\"text\" id=\"task_id\" NAME=\"task_id\" title=\"Enter TaskId.\" SIZE=\"5\" "
			+" MAXLENGTH=\"20\" STYLE=\"height:4;width: 60px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\" value="+task_id+" >";
		var timeValue = time;
		if(timeValue == ""|| timeValue.indexOf('-')>=0 || ( timeValue.length>2 && (timeValue.indexOf('.')<0  && timeValue.indexOf(':')<0 ))||
				(timeValue.indexOf('.')==0 || timeValue.indexOf(':')==0) || (timeValue.lastIndexOf('.')==timeValue.length-1|| timeValue.lastIndexOf(':')==timeValue.length-1)){
			document.getElementById(currentRowId).getElementsByTagName('td')[6].innerHTML= "<INPUT TYPE=\"text\" NAME=\"time\" title=\"eg:HH.MM, HH:MM\" id=\"time\" "
				+" SIZE=\"5\" MAXLENGTH=\"5\" STYLE=\"height:4;width: 40px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;border-color:red;background-color:#FFFFE7;\" value="+time+">"
				+"<div style='color: red'>*Time</div>";
			$('#'+timeId).focus();
			return false;
		}
		else if(((timeValue!= "" && timeValue.length>0) && timeValue.indexOf('.')==-1) && ((timeValue!= "" && timeValue.length>0) && timeValue.indexOf(':')==-1) ){
			// incase of only Hrs entered no mints specified.							    	
			if(timeValue <24){
				return true;
			}
			else{	
				document.getElementById(currentRowId).getElementsByTagName('td')[6].innerHTML= "<INPUT TYPE=\"text\" NAME=\"time\" title=\"eg:HH.MM, HH:MM\" id=\"time\" "
					+" SIZE=\"5\" MAXLENGTH=\"5\" STYLE=\"height:4;width: 40px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;border-color:red;background-color:#FFFFE7;\" value="+time+">"
					+"<div style='color: red'>*Time</div>";
				$('#'+timeId).focus();
				return false;
			}
		}
		else {
			if(timeValue.indexOf(".")!=-1){
				var sHours = timeValue.split('.')[0];
				var sMinutes = timeValue.split('.')[1]; 							      
				if(sHours == "" || isNaN(sHours) || parseInt(sHours)>23){
					document.getElementById(currentRowId).getElementsByTagName('td')[6].innerHTML= "<INPUT TYPE=\"text\" NAME=\"time\" title=\"eg:HH.MM, HH:MM\" id=\"time\" "
						+" SIZE=\"5\" MAXLENGTH=\"5\" STYLE=\"height:4;width: 40px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;border-color:red;background-color:#FFFFE7;\" value="+time+">"
						+"<div style='color: red'>*Time</div>";
					$('#'+timeId).focus();
					return false;
				}
				else if(sHours.length>2){
					document.getElementById(currentRowId).getElementsByTagName('td')[6].innerHTML= "<INPUT TYPE=\"text\" NAME=\"time\" title=\"eg:HH.MM, HH:MM\" id=\"time\" "
						+" SIZE=\"5\" MAXLENGTH=\"5\" STYLE=\"height:4;width: 40px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;border-color:red;background-color:#FFFFE7;\" value="+time+">"
						+"<div style='color: red'>*Time</div>";
					$('#'+timeId).focus();
					return false;
				}
				if(sMinutes == "" || isNaN(sMinutes) || parseInt(sMinutes)>59|| parseInt(sMinutes.charAt(0))>6){
					document.getElementById(currentRowId).getElementsByTagName('td')[6].innerHTML= "<INPUT TYPE=\"text\" NAME=\"time\" title=\"eg:HH.MM, HH:MM\" id=\"time\" "
						+" SIZE=\"5\" MAXLENGTH=\"5\" STYLE=\"height:4;width: 40px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;border-color:red;background-color:#FFFFE7;\" value="+time+">"
						+"<div style='color: red'>*Time</div>";
					$('#'+timeId).focus();
					return false;
				} 
				else if(sMinutes.length>2){
					document.getElementById(currentRowId).getElementsByTagName('td')[6].innerHTML= "<INPUT TYPE=\"text\" NAME=\"time\" title=\"eg:HH.MM, HH:MM\" id=\"time\" "
						+" SIZE=\"5\" MAXLENGTH=\"5\" STYLE=\"height:4;width: 40px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;border-color:red;background-color:#FFFFE7;\" value="+time+">"
						+"<div style='color: red'>*Time</div>";
					$('#'+timeId).focus();
					return false;
				}
				time = sHours + "." + sMinutes;  
			}
			if(timeValue.indexOf(":")!=-1){
				var sHours = timeValue.split(':')[0];
				var sMinutes = timeValue.split(':')[1]; 
				if(sHours == "" || isNaN(sHours) || parseInt(sHours)>23){
					document.getElementById(currentRowId).getElementsByTagName('td')[6].innerHTML= "<INPUT TYPE=\"text\" NAME=\"time\" title=\"eg:HH.MM, HH:MM\" id=\"time\" "
						+" SIZE=\"5\" MAXLENGTH=\"5\" STYLE=\"height:4;width: 40px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;border-color:red;background-color:#FFFFE7;\" value="+time+">"
						+"<div style='color: red'>*Time</div>";
					$('#'+timeId).focus();
					return false;
				}
				else if(sHours.length>2){
					document.getElementById(currentRowId).getElementsByTagName('td')[6].innerHTML= "<INPUT TYPE=\"text\" NAME=\"time\" title=\"eg:HH.MM, HH:MM\" id=\"time\" "
						+" SIZE=\"5\" MAXLENGTH=\"5\" STYLE=\"height:4;width: 40px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;border-color:red;background-color:#FFFFE7;\" value="+time+">"
						+"<div style='color: red'>*Time</div>";
					$('#'+timeId).focus();
					return false;
				}
				if(sMinutes == "" || isNaN(sMinutes) || parseInt(sMinutes)>59|| parseInt(sMinutes.charAt(0))>6) {
					document.getElementById(currentRowId).getElementsByTagName('td')[6].innerHTML= "<INPUT TYPE=\"text\" NAME=\"time\" title=\"eg:HH.MM, HH:MM\" id=\"time\" "
						+" SIZE=\"5\" MAXLENGTH=\"5\" STYLE=\"height:4;width: 40px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;border-color:red;background-color:#FFFFE7;\" value="+time+">"
						+"<div style='color: red'>*Time</div>";
					$('#'+timeId).focus();
					return false;
				}
				else if(sMinutes.length>2){
					document.getElementById(currentRowId).getElementsByTagName('td')[6].innerHTML= "<INPUT TYPE=\"text\" NAME=\"time\" title=\"eg:HH.MM, HH:MM\" id=\"time\" "
						+" SIZE=\"5\" MAXLENGTH=\"5\" STYLE=\"height:4;width: 40px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;border-color:red;background-color:#FFFFE7;\" value="+time+">"
						+"<div style='color: red'>*Time</div>";
					$('#'+timeId).focus();
					return false;
				}                                       
				time = sHours + ":" + sMinutes; 							    									    		
			}
		}
		return true;															
	}
	return true;
}

function saveAllEntry() {
	document.getElementById("ajax_res").innerHTML="";
	var table = document.getElementById("dailyEntryTable");
	if(table.getElementsByTagName("input").length==0){
		return;
	}
	var totalRow = table.getElementsByTagName('tr').length-1;							 
	var taskJson = new Array();	
	/* if(!processing){ */
	for(var i=0;i<=totalRow;i++) {
		var tableRow = table.getElementsByTagName('tr')[i];								   									   
		var input = tableRow.getElementsByTagName('input');
		if(input.length=='6'){
			var nCell = tableRow.getElementsByTagName('td');  
			var currentRowId = tableRow.getAttribute("id");	
			/*var dateId = table.getElementsByTagName('tr')[i].getElementsByTagName('td')[2].getAttribute("id");
			var backId = table.getElementsByTagName('tr')[i].getElementsByTagName('td')[2].getAttribute("id");		
			var taskId = table.getElementsByTagName('tr')[i].getElementsByTagName('td')[3].getAttribute("id");		
			var work_statusId = table.getElementsByTagName('tr')[i].getElementsByTagName('td')[5].getAttribute("id");
			var timeId = table.getElementsByTagName('tr')[i].getElementsByTagName('td')[6].getAttribute("id");*/
			var edit_id = table.getElementsByTagName('tr')[i].getElementsByTagName('td')[7].getAttribute("id");						
			if(edit_id!=null){
				var mess = table.getElementsByTagName('tr')[i].getElementsByTagName('td')[7].innerHTML;
				mess = mess.replace("*Save", "");
				table.getElementsByTagName('tr')[i].getElementsByTagName('td')[7].innerHTML = mess+"<div style='color: red;text-align: left;'>*Save</div>";
				document.getElementById(edit_id).style.backgroundColor="yellow";
			}
			else{
				var date = nCell[0].firstChild.value;
				var status = nCell[1].firstChild.value;
				var backlog_id = nCell[2].firstChild.value;
				var task_id = nCell[3].firstChild.value;
				var task_description = nCell[4].firstChild.value;					
				var work_status ='';
				var radioLength = nCell[5].getElementsByTagName('input').length;    	
				for (var p = 0; p < radioLength; p++){
					if(nCell[5].getElementsByTagName('input')[p].checked){
						work_status = nCell[5].getElementsByTagName('input')[p].value;
					}
				}
				var time = nCell[6].firstChild.value;
				if(doValidation(date, status, backlog_id, task_id, time, currentRowId)){
					time = getParsedTime(time);
					var selectOption = document.getElementById("userId"); 
					var userId = 0;
					if(selectOption!=null){
						userId = $('#userId option:selected').val();
					}
					taskJson.push({ "date": date, "status": status, "backlog_id": backlog_id, "task_id" :task_id,"task_description":task_description,"work_status":work_status, "time":time,"currentRowId":currentRowId, "userId":userId});							          							                                              							          				           							           							           							           							           							           							          									 								
				}
			} //Close for Else
		};	     //close for IF										   																		   
	};		//close for Loop	
	if(taskJson.length>0){
		$.ajax({
			type: "POST",
			dataType: "JSON",
			url: 'taskActionAfterAjaxSave.do?method=saveAllAjax',
			data:  { taskJson: JSON.stringify(taskJson)},
			success: function(response){	
				/* processing = true; */
				for(var i=0;i<taskJson.length;i++){						        	   
					// alert(taskJson.length);
					var capitalizeWorkStatus = null;
					if(taskJson[i].work_status=='home'){
						capitalizeWorkStatus = 'Home';
					}
					else if(taskJson[i].work_status=='office'){
						capitalizeWorkStatus = 'Office';
					}    
					var id = response[i];
					if(id=='0' && taskJson[i].work_status=='office'){
						$('#'+taskJson[i].currentRowId).html("<td><INPUT TYPE=\"text\" NAME=\"task_date\" title=\"Click to pick date from calendar.\" id=\"popupDatepicker"+i+"\" readonly=\"true\" SIZE=\"5\" MAXLENGTH=\"10\" "
								+" value=\""+taskJson[i].date+"\" onclick=\"callScriptForDisplayingCalender(this)\""
								+" STYLE=\"height:4;width: 65px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\"></td>"
								+"<td><SELECT NAME=\"status\" id=\"status\" title=\"Select appropriate category.\" onchange=\"changeDynamicTextBox(this);\" style=\"width: 120px;font-family: verdana, arial, sans-serif;font-size: 11px;\">"
								+"<option value=\""+taskJson[i].status+"\">"+taskJson[i].status+"</option>"
								+"<option value=\"Code Review\">Code Review</option>"
								+"<option value=\"Comp off\">Comp off</option>"
								+"<option value=\"Development\">Development</option>"
								+"<option value=\"Down Time\">Down Time</option>"
								+"<option value=\"Half Day\">Half Day</option>"
								+"<option value=\"KT Session\">KT Session</option>"
								+"<option value=\"Leave\">Leave</option>"
								+"<option value=\"Maintenance/Release support\">Maintenance/Release support</option>"
								+"<option value=\"Meeting\">Meeting</option>"
								+"<option value=\"Pre-Project Training\">Pre-Project Training</option>"								
								+"<option value=\"Project Management\">Project Management</option>"																				
								+"<option value=\"Public holiday\">Public holiday</option>"
								+"<option value=\"Task unassigned/Idle\">Task unassigned/Idle</option>"	
								+"<option value=\"Team Activity\">Team Activity</option>"
								+"<option value=\"Testing\">Testing</option>"														
								+"<option value=\"Training\">Training</option>"													
								+"<option value=\"Travel\">Travel</option>"
								+"</SELECT></td>"
								+"<td><INPUT TYPE=\"text\" id=\"backlog_id\" value=\""+taskJson[i].backlog_id+"\" title=\"Enter BacklogId.\" NAME=\"backlog_id\" SIZE=\"5\" MAXLENGTH=\"20\" "
								+" STYLE=\"height:4;width: 60px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\"></td>"
								+"<td><INPUT TYPE=\"text\" id=\"task_id\" value=\""+taskJson[i].task_id+"\" NAME=\"task_id\" title=\"Enter TaskId.\" SIZE=\"5\" MAXLENGTH=\"20\" "
								+" STYLE=\"height:4;width: 60px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\" ></td>"
								+"<td><textarea name=\"task_description\" title=\"Add description here.\" id=\"des_id\" rows=\"2\" cols=\"2\" value=\""+taskJson[i].task_description+"\""
								+" STYLE=\"width: 160px;height: 100px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\"></textarea></td>"
								+"<td><input type=\"radio\" title=\"Pick Effort From\"  name=\"work_status"+count+"\" value=\"office\" checked> Office"
								+"<input type=\"radio\" title=\"Pick Effort From\"  name=\"work_status"+count+"\" value=\"home\">Home</td>"
								+"<td><INPUT TYPE=\"text\" NAME=\"time\" title=\"eg:HH.MM, HH:MM\" id=\"time\"  SIZE=\"5\" MAXLENGTH=\"5\" value=\""+taskJson[i].time+"\" "
								+" STYLE=\"height:4;width: 40px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\"><div style='color: red;'>*Max upto 23Hrs</div></td>"
								+"<td><img alt=\"1px\" src=\"img/save.gif\" id=\"edit\" title=\"Click to save entry.\" SIZE=\"5\"  onClick=\"callAjax(this)\"  style=\"height: 20px;width: 20px;font-family: verdana, arial, sans-serif;font-size: 11px;cursor: pointer;\"></td>"
								+"<td><img alt=\"1\" src=\"img/minus.png\" SIZE=\"5\" title=\"Delete\" onClick='deleteRow(this);' style=\"height: 20px;width: 20px;font-family: verdana, arial, sans-serif;font-size: 11px;cursor: pointer;\"></td>");
					}
					else if(id=='0' && taskJson[i].work_status=='home'){
						$('#'+taskJson[i].currentRowId).html("<td><INPUT TYPE=\"text\" NAME=\"task_date\" title=\"Click to pick date from calendar.\" id=\"popupDatepicker"+i+"\" readonly=\"true\" SIZE=\"5\" MAXLENGTH=\"10\" "
								+" value=\""+taskJson[i].date+"\" onclick=\"callScriptForDisplayingCalender(this)\""
								+" STYLE=\"height:4;width: 65px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\"></td>"
								+"<td><SELECT NAME=\"status\" id=\"status\" title=\"Select appropriate category.\" onchange=\"changeDynamicTextBox(this);\" style=\"width: 120px;font-family: verdana, arial, sans-serif;font-size: 11px;\">"
								+"<option value=\""+taskJson[i].status+"\">"+taskJson[i].status+"</option>"
								+"<option value=\"Code Review\">Code Review</option>"
								+"<option value=\"Comp off\">Comp off</option>"
								+"<option value=\"Development\">Development</option>"
								+"<option value=\"Down Time\">Down Time</option>"
								+"<option value=\"Half Day\">Half Day</option>"
								+"<option value=\"KT Session\">KT Session</option>"
								+"<option value=\"Leave\">Leave</option>"
								+"<option value=\"Maintenance/Release support\">Maintenance/Release support</option>"
								+"<option value=\"Meeting\">Meeting</option>"
								+"<option value=\"Pre-Project Training\">Pre-Project Training</option>"								
								+"<option value=\"Project Management\">Project Management</option>"																				
								+"<option value=\"Public holiday\">Public holiday</option>"
								+"<option value=\"Task unassigned/Idle\">Task unassigned/Idle</option>"	
								+"<option value=\"Team Activity\">Team Activity</option>"
								+"<option value=\"Testing\">Testing</option>"														
								+"<option value=\"Training\">Training</option>"													
								+"<option value=\"Travel\">Travel</option>"
								+"</SELECT></td>"
								+"<td><INPUT TYPE=\"text\" id=\"backlog_id\" value=\""+taskJson[i].backlog_id+"\" title=\"Enter BacklogId.\" NAME=\"backlog_id\" SIZE=\"5\" MAXLENGTH=\"20\" "
								+" STYLE=\"height:4;width: 60px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\"></td>"
								+"<td><INPUT TYPE=\"text\" id=\"task_id\" value=\""+taskJson[i].task_id+"\" NAME=\"task_id\" title=\"Enter TaskId.\" SIZE=\"5\" MAXLENGTH=\"20\" "
								+" STYLE=\"height:4;width: 60px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\" ></td>"
								+"<td><textarea name=\"task_description\" title=\"Add description here.\" id=\"des_id\" rows=\"2\" cols=\"2\" "
								+" STYLE=\"width: 160px;height: 100px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\">"+taskJson[i].task_description+"</textarea></td>"
								+"<td><input type=\"radio\" title=\"Pick Effort From\"  name=\"work_status"+count+"\" value=\"office\" > Office"
								+"<input type=\"radio\" title=\"Pick Effort From\"  name=\"work_status"+count+"\" value=\"home\" checked>Home</td>"
								+"<td><INPUT TYPE=\"text\" NAME=\"time\" title=\"eg:HH.MM, HH:MM\" id=\"time\"  SIZE=\"5\" MAXLENGTH=\"5\" value=\""+taskJson[i].time+"\" "
								+" STYLE=\"height:4;width: 40px;border: 1 solid;margin:0;font-family: verdana, arial, sans-serif;font-size: 11px;\"><div style='color: red;'>*Max upto 23Hrs</div></td>"
								+"<td><img alt=\"1px\" src=\"img/save.gif\" id=\"edit\" title=\"Click to save entry.\" SIZE=\"5\"  onClick=\"callAjax(this)\"  style=\"height: 20px;width: 20px;font-family: verdana, arial, sans-serif;font-size: 11px;cursor: pointer;\"></td>"
								+"<td><img alt=\"1\" src=\"img/minus.png\" SIZE=\"5\" title=\"Delete\" onClick='deleteRow(this);' style=\"height: 20px;width: 20px;font-family: verdana, arial, sans-serif;font-size: 11px;cursor: pointer;\"></td>");
					}
					else{						        	 	    	 
						if(taskJson[i].status=="Leave"||taskJson[i].status=="Half Day"){	
							$('#'+taskJson[i].currentRowId).html("<td>"+taskJson[i].date+"</td><td>"+taskJson[i].status+"</td>"
									+"<td>"+taskJson[i].backlog_id+"</td><td>"+taskJson[i].task_id+"</td><td>"+taskJson[i].task_description+"</td>"
									+"<td>"+capitalizeWorkStatus+"</td><td style=\"color: red;\">"+taskJson[i].time+"</td>"
									+"<td><img src='img/edit.png' title=\"Click to edit entry.\" onclick='editRowEntry(this,"+id+")' style='cursor: pointer;'/></td>"
									+"<td><img src='img/delete1.png' title=\"Click to delete entry.\" onclick='onDelete(\"taskAction1.do?method=delete&id="+id+"\", this)' style='cursor: pointer;' /></td>");
						}
						else if(taskJson[i].status=="Public holiday"){						        	
							$('#'+taskJson[i].currentRowId).html("<td>"+taskJson[i].date+"</td><td>"+taskJson[i].status+"</td>"
									+"<td>"+taskJson[i].backlog_id+"</td><td>"+taskJson[i].task_id+"</td><td>"+taskJson[i].task_description+"</td>"
									+"<td>"+capitalizeWorkStatus+"</td><td style=\"color: #0000CD;\">"+taskJson[i].time+"</td>"
									+"<td><img src='img/edit.png' title=\"Click to edit entry.\" onclick='editRowEntry(this,"+id+")' style='cursor: pointer;'/></td>"
									+"<td><img src='img/delete1.png' title=\"Click to delete entry.\" onclick='onDelete(\"taskAction1.do?method=delete&id="+id+"\", this)' style='cursor: pointer;' /></td>");						        
						}
						else if(taskJson[i].status=="Comp off"){						        	
							$('#'+taskJson[i].currentRowId).html("<td style=\"color: #008000;\">"+taskJson[i].date+"</td><td style=\"color: #008000;\">"+taskJson[i].status+"</td>"
									+"<td style=\"color: #008000;\">"+taskJson[i].backlog_id+"</td><td style=\"color: #008000;\">"+taskJson[i].task_id+"</td><td style=\"color: #008000;\">"+taskJson[i].task_description+"</td>"
									+"<td style=\"color: #008000;\">"+capitalizeWorkStatus+"</td><td style=\"color: #008000;\">"+taskJson[i].time+"</td>"
									+"<td><img src='img/edit.png' title=\"Click to edit entry.\" onclick='editRowEntry(this,"+id+")' style='cursor: pointer;'/></td>"
									+"<td><img src='img/delete1.png' title=\"Click to delete entry.\" onclick='onDelete(\"taskAction1.do?method=delete&id="+id+"\", this)' style='cursor: pointer;' /></td>");						        
						}
						else{
							$('#'+taskJson[i].currentRowId).html("<td>"+taskJson[i].date+"</td><td>"+taskJson[i].status+"</td>"
									+"<td>"+taskJson[i].backlog_id+"</td><td>"+taskJson[i].task_id+"</td><td>"+taskJson[i].task_description+"</td>"
									+"<td>"+capitalizeWorkStatus+"</td><td>"+taskJson[i].time+"</td>"
									+"<td><img src='img/edit.png' title=\"Click to edit entry.\" onclick='editRowEntry(this,"+id+")' style='cursor: pointer;'/></td>"
									+"<td><img src='img/delete1.png' title=\"Click to delete entry.\" onclick='onDelete(\"taskAction1.do?method=delete&id="+id+"\", this)' style='cursor: pointer;' /></td>");						        
						}
						$('#'+taskJson[i].currentRowId).css("background-color","#96FFFF");					
						$('#ajax_res').html("<div style='font-family: verdana, arial, sans-serif;font-size: 11px;color: blue;text-align: center;'>Data entered successfully.</div>"); 
					}	// Close for else block
				}     // Close for FOR LOOP
				/*  processing = false; */
			},
			beforeSend: function(){
				showLoadingImage();
			},			
			complete: function(){
				hideLoadingImage();
				hideEnteredMissingdate();
				//Sort table here.
				sortMyTable(table);
			}, 
			error: function(e){	
				if(e.responseText=='session expired'){
					//document.getElementById('myDiv').style.visibility = "hidden";
					document.getElementById('save_id_buttonId').style.visibility = "hidden";
					document.getElementById('new_entry_buttonId').style.visibility = "hidden";
					document.getElementById('myMissingDates').style.visibility = "hidden";
					if($("#freeze_entry_id").length != 0){document.getElementById('freeze_entry_id').style.visibility = "hidden";}
					document.getElementById("ajax_res").innerHTML ="<div style='font-family: verdana, arial, sans-serif;font-size: 13px;color: red;text-align: center;'>"
						+"Your Session has been expired please <a href='http://pepq.calsoftlabs.com/effort/' target='_blank'>relogin</a> to retain your session.</div>"; 					        		   
				}
			}
		});
		setTimeout( function(){$("table[id=dailyEntryTable] tr:nth-child(even)").css({"background": "#ffffff"});
				$("table[id=dailyEntryTable] tr:nth-child(odd)").css({"background": "#eeeeee"});}, 1500 );
	}
}
function addRowForCurrentDate(currentObject){
	var yearMonth = $('#selectedMonthId option[value!=""]:first').val();
	var selectedYear = yearMonth.substring(0,yearMonth.indexOf('-'));
	var selectedMonth = yearMonth.substring(yearMonth.indexOf('-')+1, yearMonth.length);	
	var dateValue = currentObject.innerHTML;
	var formattedDate = selectedMonth+"/"+dateValue+"/"+selectedYear;
	// Add row on top on table.
	addRow('dailyEntryTable', formattedDate);	
}
function addAllMissingDate(current_object){
	var yearMonth = $('#selectedMonthId option[value!=""]:first').val();
	var selectedYear = yearMonth.substring(0,yearMonth.indexOf('-'));
	var selectedMonth = yearMonth.substring(yearMonth.indexOf('-')+1, yearMonth.length);	
	var allHrefDate = current_object.parentNode.id;
	var td = document.getElementById(allHrefDate);
	var hrefLength = td.getElementsByTagName('a').length;
	for(var p = 0; p<=hrefLength-2 ; p++){				// Discarding last HREF
		var formattedDate = selectedMonth+"/"+td.getElementsByTagName('a')[p].innerHTML+"/"+selectedYear;
		if(td.getElementsByTagName('a')[p].style.display!='none'){
			addRow('dailyEntryTable', formattedDate);	
		}			
	}
}

function checkSessionValidationBeforeAction(){
	$.ajax({
		type: "POST",
		url: "taskActionAfterAjaxSave.do?method=checkSessionValidation",
		data: "",
		success: function(response){
			if(response=='session expired'){
				//document.getElementById('myDiv').style.visibility = "hidden";
				document.getElementById('save_id_buttonId').style.visibility = "hidden";
				document.getElementById('new_entry_buttonId').style.visibility = "hidden";
				document.getElementById('myMissingDates').style.visibility = "hidden";
				if($("#freeze_entry_id").length != 0){document.getElementById('freeze_entry_id').style.visibility = "hidden";}
				document.getElementById("ajax_res").innerHTML ="<div style='font-family: verdana, arial, sans-serif;font-size: 13px;color: red;text-align: center;'>"
					+"Your Session has been expired please <a href='http://pepq.calsoftlabs.com/effort/' target='_blank'>relogin</a> to retain your session.</div>"; 	
				return true; 	    	              
			}
		},
		error: function(e){
			//document.getElementById('myDiv').style.visibility = "hidden";
			document.getElementById('save_id_buttonId').style.visibility = "hidden";
			document.getElementById('new_entry_buttonId').style.visibility = "hidden";
			document.getElementById('myMissingDates').style.visibility = "hidden";
			if($("#freeze_entry_id").length != 0){document.getElementById('freeze_entry_id').style.visibility = "hidden";}
			document.getElementById("ajax_res").innerHTML ="<div style='font-family: verdana, arial, sans-serif;font-size: 13px;color: red;text-align: center;'>"
				+"Your Session has been expired please <a href='http://pepq.calsoftlabs.com/effort/' target='_blank'>relogin</a> to retain your session.</div>"; 
			return true;                        
		}
	});
	return false;
}