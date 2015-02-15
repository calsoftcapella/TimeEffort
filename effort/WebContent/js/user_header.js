function onClickLeave(url) {
	var submitForm = document.forms[0];
	submitForm.action = url;
	submitForm.submit();
}
function onClickTask(url) {
	var submitForm = document.forms[0];
	submitForm.action = url;
	submitForm.submit();
} 
function DateAndTime(){
	var currentDate = new Date();
	var day = currentDate.getDate();
	var month = currentDate.getMonth() + 1;
	var year = currentDate.getFullYear();
	//document.write();		  
	var currentTime = new Date();
	var hours = currentTime.getHours();
	var minutes = currentTime.getMinutes();
	var sec = currentTime.getSeconds();
	var suffix = "AM";
	if (hours >= 12) {
		suffix = "PM";
		hours = hours - 12;
	}
	if (hours == 0) {
		hours = 12;
	}
	if (minutes < 10)
		minutes = "0" + minutes;	
	if (sec < 10)
		sec = "0" + sec;
	$('#date_time').html("<b>" + day + "/" + month + "/" + year + "</b>,   "+"<b>" + hours + ":" + minutes + ":"+sec+" " + suffix + "</b>");
}
$(document).ready(function() {
	var A=document.links.length;
	var currURL=window.location;
	var str = currURL.toString();
	var strForCurURL = str.substring(0, str.lastIndexOf('.'));
	for(var i=0; i<=A-1; i++){
		var el = document.links[i];
		if(typeof document.links[i] != "undefined") {
			el.style.color="#003CCD";
			var currHREF = el.href;
			el.getAttribute("id");		
			if(el.getAttribute("id")=='log'){
				el.style.color="#44157D";
			}
			var hrstr = currHREF.toString();
			var strForCurHREF = hrstr.substring(0, hrstr.lastIndexOf('.'));
			var myVar = str.substring(str.lastIndexOf("/")+1, str.length);
			if(myVar=='userLoginAction.do?method=Login' && typeof $("#access_info_id").val() == "undefined"){			
				var  strURL = 'userHomeAction';
				var myStrForCurHREF = strForCurHREF.substring(strForCurHREF.lastIndexOf("/")+1, strForCurHREF.length);
				if(strURL== myStrForCurHREF){
					el.style.color="#B28C81";
					el.style.textDecoration="none";	
					el.style.cursor='text';
				}
			}
			else if(myVar=='userLoginAction.do?method=Login' && typeof $("#access_info_id").val() != "undefined"){			
				var  strURL = 'displayReport';
				var myStrForCurHREF = strForCurHREF.substring(strForCurHREF.lastIndexOf("/")+1, strForCurHREF.length);
				if(strURL== myStrForCurHREF){
					el.style.color="#B28C81";
					el.style.textDecoration="none";	
					el.style.cursor='text';
				}
			}
			if(strForCurURL==strForCurHREF||strForCurURL+"1"==strForCurHREF||strForCurURL.replace("444", "3")==strForCurHREF||strForCurURL==strForCurHREF+"1"){
				el.style.color="#B28C81";
				el.style.textDecoration="none";
				el.style.cursor='text';						
			}
		}
	}
});
function changeLinkCSS(el){
	var newClass='testLink2';
	if(!(el.className==newClass)){
		el.className=newClass;
	}
}