function checkFreezingStatusForSelectedResource(){
		$('#checkUser').html('');
		var user_select_box = $('#userId option');
		var selectedMonth = $('#month-settings_id').val();
		//alert(selectedMonth);
		var selectedUser = new Array();
		var count = 0;
		for(var uId = 0 ; uId < user_select_box.length ;  uId++){
			if(user_select_box[uId].selected == true){
				selectedUser[count++] = user_select_box[uId].value;
			}
		}
		var parentForFreezeButton = "";
		if(document.getElementById("freezeButtonId")!=null){
			parentForFreezeButton = document.getElementById("freezeButtonId").parentNode;
		}
		var ajaxRespose = "";
		if(selectedUser.length > 0 && (document.getElementById("freezeButtonId")!=null && selectedMonth !='')){
			$.ajax({
				type: "POST",
				url: 'displayReport.do?method=checkFreezingEntryStatusForSelctedResource',
				data: 'selectedUser='+selectedUser+'&selectedMonthForFreezing='+selectedMonth+'',
				success: function(response){
					ajaxRespose = response;
				},
				beforeSend: function(){
					$('#loading_image_for_button').show();
				},			
				complete: function(){
					$('#loading_image_for_button').hide();
					if(ajaxRespose=='session expired'){
					}
					else if(ajaxRespose=='freezed'){
						parentForFreezeButton.innerHTML="<input type=\"button\" id=\"freezeButtonId\" onclick=\"submitReport1('displayReport.do?method=unfreezeTimesheet');\""
									+"value=\"Unfreeze Timesheet\" style=\"width: 200px; font-family: verdana, arial, sans-serif; font-size: 11px;\" class=\"changeButtonUI\"/>"
									+" <img  id=\"loading_image_for_button\" src=\"img/button_loading.gif\" style=\"vertical-align: bottom;display: none;height: 30px;padding-left: 0px;\">";
					}
					else if(ajaxRespose=='unfreezed'){
						parentForFreezeButton.innerHTML="<input type=\"button\" id=\"freezeButtonId\""
							+"onclick=\"submitReportForFreezing('displayReport.do?method=freezeTimesheet');\""
							+"value=\"Freeze Timesheet\" style=\"width: 200px; font-family: verdana, arial, sans-serif; font-size: 11px;\" class=\"changeButtonUI\"/>"
							+" <img  id=\"loading_image_for_button\" src=\"img/button_loading.gif\" style=\"vertical-align: bottom;display: none;height: 30px;padding-left: 0px;\">";
					}
				}, 
				error: function(e){
				}
			});
		}
	}
	function resourceSelectionScript(){
		var selectedResource = new Array();
		var count = 0;
		$("#pickSelectedResourceId").children("p").each(function(e,v){
			selectedResource[count++] = $(v).text();						
		});
		var user_select_box = $("#userId option");
		if(selectedResource.length > 0){
			user_select_box.removeAttr("selected");
			for(var uId = 0 ; uId < user_select_box.length; uId++){
				for ( var ar = 0; ar < selectedResource.length; ar++) {
					if(user_select_box[uId].value == selectedResource[ar]){
						user_select_box[uId].selected = true;
					}
				}
			}
		}
	}
	function submitReportManager(url) {
		var formSubmit = document.forms[0];
		formSubmit.action = url;
		var selectedUserId = document.getElementById('userId').value;
		var selectedteam = document.getElementById('team').value;
		if (selectedUserId == '' && selectedteam == '') {
			document.getElementById('checkUser').innerHTML = "<font style='font-family: verdana, arial, sans-serif;font-size: 11px;color:red'>"
					+ 'Please select a Team or Resource.' + "</font>";
		} else {
			formSubmit.submit();
		}
	}
	function submitReport1(url) {
		document.getElementById('messageIds').innerHTML = "";
		var formSubmit = document.forms[0];
		formSubmit.action = url;
		var selectedUserId = document.getElementById('userId').value;
		if (selectedUserId == '') {
			document.getElementById('checkUser').innerHTML = "<font style='font-family: verdana, arial, sans-serif;font-size: 11px;color:red'>"
					+ 'Please select a Resource.' + "</font>";
		} else {
			formSubmit.submit();
		}

	}
	function submitReportForFreezing(url) {
		document.getElementById('messageIds').innerHTML = "";
		var formSubmit = document.forms[0];
		formSubmit.action = url;
		var selectedUserId = document.getElementById('userId').value;
		if (selectedUserId == '') {
			document.getElementById('checkUser').innerHTML = "<font style='font-family: verdana, arial, sans-serif;font-size: 11px;color:red'>"
					+ 'Please select a Resource.' + "</font>";
		} else {
			var check = confirm("Do you want to freeze timesheet?");
			if (check == true) {
			  formSubmit.submit();
			}
		}

	}
	function submitReport(url) {
		var formSubmit = document.forms[0];
		formSubmit.action = url;
		formSubmit.submit();
	}
	function pickSelectedClientResourceFromDropDown(){
		if (typeof $("#hidded_client_id").val() != "undefined"){
			var hidden_client_id = $("#hidded_client_id").val();
			var client_selection_box = $("#client_option_id option");
			for ( var ar = 0; ar < client_selection_box.length; ar++) {
				if(client_selection_box[ar].value == hidden_client_id){
					client_selection_box[ar].selected = true;
				}
			}
		}
	}
	function pickLocationFromAction() {
		if (document.getElementById('locFromAction') != null) {
			var defaultLocation = document.getElementById('locFromAction').value;
			var selectOption = document.getElementById("location");
			for ( var i = 0; i < selectOption.length; i++) {
				if (selectOption.options[i].value == defaultLocation) {
					selectOption.options[i].selected = "true";
				}
			}
		}
	}
	function datatable_checker(){
		$("#dash_id").focus();
		if($("#dataTable1 tr").length == 2){
			$("#timesheet_dash").hide();
			$("#messageIds").html("No Data Found");
			
		}
	}
	function listbox_selectall(listID, isSelect) {
		var listbox = document.getElementById(listID);
		for ( var count = 0; count < listbox.options.length; count++) {
			listbox.options[count].selected = isSelect;
		}
		checkFreezingStatusForSelectedResource();
	}
	function getDetailsBasedOnLocation() {
		submitReport("displayReport.do?method=getResourceLocationWise");
	}
	function getDetailsBasedSelectedClient(currentObject) {
		submitReport("displayReport.do?method=getResourceClientWise");
	}