<%-- 
    Document   : baseLayout
    Created on : Dec 19, 2008, 1:28:41 AM
    Author     : eswar@vaannila.com
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title><tiles:getAsString name="title" ignore="true" /></title>
<style type="text/css">
body {
	font: normal 14px Calibri, Calibri, Calibri;
}
a.LINK {color: #003CCD}
</style>
            
    </head>
    <body style="margin-top: 0%; height: 500px; vertical-align: top; " bgcolor="#A6A3A2" >
        <table  border="0" align="center" style="border-width:3px;border-style:solid;border-color: gray;" bgcolor="white" height="800">
            <tr>
                <td height="70"  width="850" style="border-bottom-color:red;border-bottom: 90px;">
                    <tiles:insert attribute="header" ignore="true" /><hr color="red" size="3%" width="100%">
                </td>
            </tr>
            <tr>
                <td  height="755" width="850" style="border-top-color:red;border-top: 90px;vertical-align: top;">
                    <tiles:insert attribute="body" />
                </td>
            </tr>
            <tr>
                <td height="45"  width="850">
                    <tiles:insert attribute="footer" />
                </td>
            </tr>
        </table>
    </body>
</html>
