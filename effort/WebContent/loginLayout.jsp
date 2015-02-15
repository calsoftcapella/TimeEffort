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
       
</style>   
    </head>
    <body >       
        <table>
            <tr>
                <td>
                    <tiles:insert attribute="body" />
                </td>
            </tr>
            <tr>
                <td>
                    <tiles:insert attribute="footer" />
                </td>
            </tr>
        </table>        
    </body>
</html>
