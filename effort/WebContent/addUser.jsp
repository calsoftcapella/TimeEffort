<%-- <%@taglib prefix="html" uri="/WEB-INF/struts-html.tld"%>

<html>
<head>

</head>

<body>
	<html:errors />
	<form name ="frm" action="addUser1.do" method="post">
		<table border=0 cellpadding=0 cellspacing=0 width=250>
			<tr>
				<td align="center">Name:<input type="text" name="userName" />
					<input type="submit" value="Add" onclick="post_value();">
				</td>

			</tr>
		</table>
	</form>
</body>
</html>

<script langauge="javascript">
	function post_value() {
		/* alert(document.frm.userName.value); */
		//opener.document.TaskAction.userId.value = document.frm.userName.value;
		window.opener.close();
	}
</script>
 --%>

<jsp:forward page="addUser1.do"/>

