<html>
<head>
<script type="text/javascript">
function submitUser()
{
	alert("User already Exist");
	document.auto.submit(); 
	}

</script>
</head>
<body onload="submitUser();">
<form name="auto" action="userRetrieveAction.do"></form>
</body>
</html>
