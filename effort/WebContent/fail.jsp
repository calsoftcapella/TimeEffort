<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
<script type="text/javascript">
function submitReport(url)
{
	var formSubmit=document.forms[0];
	formSubmit.action=url;
	formSubmit.submit();
		
	
	}
</script>
</head >
<body>
<center>
<html:errors/><br><br><br>
<html:form action="displayReport" method="post">
</html:form>
</center>
</body>
</html>