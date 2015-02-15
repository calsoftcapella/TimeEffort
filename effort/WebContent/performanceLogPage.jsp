<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.calsoft.performance.form.UserCommentForm"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page session="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>

<html>
<head>
<link rel="icon" type="image/jpg" href="img/calsoftNew.jpg" />
<link rel="stylesheet" type="text/css" href="css/performance_log_css.css">
<script type="text/javascript" src="./js/jquery-1.7.2.min.js"></script>
<script	src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
<script type="text/javascript" src="http://malsup.github.io/jquery.blockUI.js"></script>
<script type="text/javascript">
var comment_id_count = 100;
function editRowInline(r,id){   
	var i=r.parentNode.parentNode.rowIndex;  
	var table = document.getElementById('commentsTable');
	var idVal = table.rows[i].id;
	var tableTr = table.getElementsByTagName('tr')[i];
	var nCell = tableTr.getElementsByTagName('td'); 
	var date = nCell[0].innerHTML;
	var user_Name = nCell[1].innerHTML;
	var user_Comment = nCell[2].innerHTML;
	var divlength=nCell[3].getElementsByTagName('div').length;
	var accessusers= new Array();
	var users_access_detail_after_update = "";
	for(var i=0, k=1; i<divlength;i++){
		accessusers[i]=nCell[3].getElementsByTagName('div')[i].textContent;
		users_access_detail_after_update = users_access_detail_after_update+"<div id='col"+k+"'>"+accessusers[i]+"</div>";
		k++;
	}
	$('#'+idVal).html("<td id='comment_date"+comment_id_count+"'>"+date+"</td><td id='user"+comment_id_count+"'>"+user_Name+"</td>"+
			"<td id='user_Comm"+comment_id_count+"'><textarea name=\"user_Comment\" id='user_Comm_id"+comment_id_count+"' value="+user_Comment+" rows=\"4\" cols=\"9\" STYLE=\"height:120px;border: 0 solid;margin:0;width: 190px;font-family: verdana, arial, sans-serif;font-size: 11px;border-color: black;border-style: solid;border-width: thin;width: 100%;height: 100px;\">"+user_Comment+"</textarea></td>"+
			"<td id='access"+comment_id_count+"' width=\"150px\">"+users_access_detail_after_update+"</td>"+
			"<td id='delete_id"+comment_id_count+"'><img alt=\"1px\" src=\"img/delete1.png\" id=\"delete\" title=\"Delete Comment\" SIZE=\"5\" "+
			" onClick=\"onDelete('commentLog.do?method=deleteComment&commentId="+id+"',this )\"  style=\"cursor: pointer;\"></td>"+
			"<td id='save_id"+comment_id_count+"'><img alt=\"1px\" src=\"./img/save_comment.jpg\" id=\"edit\" title=\"Save Comment\" SIZE=\"5\"  "+
			"onClick=\"onSave('commentLog.do?method=editComment&commentId="+id+"',this,"+id+" )\"  style=\"width: 0.6cm; height: 0.6cm;cursor: pointer;\"></td>");    	
    
	$("#user_Comm_id"+comment_id_count).focus();
	comment_id_count++;
    
} 	
function onSave(url,id,commentId){
	var i=id.parentNode.parentNode.rowIndex;  
	var table = document.getElementById('commentsTable');
	var idVal = table.rows[i].id;
	var tableTr = table.getElementsByTagName('tr')[i];
	var nCell = tableTr.getElementsByTagName('td');
	var date = nCell[0].innerHTML;	
	var name = nCell[1].innerHTML;
	var comm = nCell[2].firstChild.value;
	var access=nCell[3].innerHTML;
	var str=url+"&user_Comment=";
    var url2=str+comm;
    $.ajax({
      type: "POST",
      url: url2,
      data: "",
      success: function(response){
    	  $('#'+idVal).html("<td>"+date+"</td>"+
    	  "<td>"+name+"</td>"+
    	  "<td>"+comm+"</td>"+
    	  "<td  width=\"150px\">"+access+"</td>"+
    	  "<td><img src='img/delete1.png'  onclick=\"onDelete('commentLog.do?method=deleteComment&commentId="+commentId+"',this)\" style='cursor: pointer;'/></td>"+
    	  "<td><img src='img/edit.png' onclick='editRowInline(this,"+commentId+")' style='cursor: pointer;' /></td>");
        },
      beforeSend: function(){
      	  $('#comment_div_id').block({ message: '<img src="img/button_loading.gif"/>', css: { border: '3px solid #a00'} });
  	  },			
  	  complete: function(){
  		  $('#comment_div_id').unblock();
  	  },     
      error: function(e){        
      }
     });        
   }

function onDelete(url, currentObject){
	var commentDeletedFromUser = $('#user_details option:selected').text();
	var seletedPeriod = $('#periodId option:selected').val();
	var tr_element = currentObject.parentNode.parentNode.id;
	var commentField = document.getElementById(tr_element).getElementsByTagName("td")[2].innerHTML;
	var table = currentObject.parentNode.parentNode.parentNode;
	var row = table.getElementsByTagName('tr');	
	$("#"+tr_element).css("background-color","#FF9696");	
	var check = confirm("Do you want to delete the comment ??");
	if (check == true) {	
	 $.ajax({
      type: "POST",
      url: url,
      data: { commentDeletedFromUser: commentDeletedFromUser, seletedPeriod: seletedPeriod, commentField: commentField },
      success: function(response){
    	  if(row.length==1){
    		  document.getElementById("commentsTable").deleteRow(1);
    		  document.getElementById("commentsTable").deleteRow(0);
    	  }
    	  else{
           	  document.getElementById("commentsTable").deleteRow(currentObject.parentNode.parentNode.rowIndex);
    	  }
      },
      beforeSend: function(){
    	  $('#comment_div_id').block({ message: '<img src="img/button_loading.gif"/>', css: { border: '3px solid #a00'} });
	  },			
	  complete: function(){
		  $('#comment_div_id').unblock();
	  },   
      error: function(e){        
      }
     });
	 }else{
		 setTimeout( function(){$("table[id=commentsTable] tr:nth-child(even)").css({"background": "#ffffff"});
			$("table[id=commentsTable] tr:nth-child(odd)").css({"background": "#eeeeee"});}, 500 );
	 }        
   }
	
	function submitCommenturl(url, commonobj) {
		var obj=document.getElementById("specificObjective").value;
		var checkboxValidate=document.performanceLogForm.roleNames;
		if(obj.trim()!="Not given for this period to this user."){
			var formSubmit = document.forms[0];
			var comment = $('#userComments').val();
			if (comment == '') {
				$('#comment1').html("");
				$('#comment2').html("");
				$('#comment3').html("");
				$('#commentID').html("<center><font color='red' style=\"font-family:14px Calibri;\">Please give comment to save it .</font></center>");
				return false;
			} 
			else if(comment != ''){	
		  		var j=false;
		  		for(var i=0;i<checkboxValidate.length;i++){
					if(eval(checkboxValidate[i].checked)){
						j=true;					
			 	 	}
		     	}
		    	if (eval(j)) {
			   		formSubmit.action = url;
   			   		/* $('#commentID').html("<center><font color='green' style=\"font-family:14px Calibri;\">Comment given to this objectives successfully.</font></center>"); */
			   		formSubmit.submit();
			   		return true;
		   		 } 
		    	else{ 
		    		$('#comment1').html("");
					$('#comment2').html("");
					$('#comment3').html("");
			  		$('#commentID').html("<center><font color='red' style=\"font-family:14px Calibri;\">Select checkbox to whom that comment should be visible </font></center>");
			  		return false;
		    	}			
	    	}
		}
		else{
			$('#commentID').html("<center><font color='red' style=\"font-family:14px Calibri;\">Objectives are not defined to give comment.</font></center>");
			return false;
		}
	}
	function submiturl(url) {		
	       var formSubmit = document.forms["performanceLogForm"];
		   var period = $('#periodId option:selected').text();
		   var user =$('#user option:selected').text();
			if (period == 'Select') {
				$('#commentID').html("");
				$('#errorID')
						.html(
								"<center><font color='red' style=\"font-family:14px Calibri;\">Please Select Period to get Common and Specific objectives.</font></center>");
			} 
			else{
				formSubmit.action = url;
				formSubmit.submit();
			}
	}
	function callResetCheckBox(){		
		var table = document.getElementById("my_tab");
		$('#userComments').html("");
		var td = table.getElementsByTagName("td");
		for(var i=0 ; i<td.length; i++){
			var input = td[i].getElementsByTagName("input");
			input[0].removeAttribute("checked");
		}
	}
	$(document).ready(function() {
		var userIdvalue =  $("#selectedUserIdVal").val();
		var user_select_box = $("#user_details option");
		//user_select_box.removeAttr("selected");
		if(typeof userIdvalue != 'undefined'){
			for(var uId = 0 ; uId < user_select_box.length; uId++){		
				if(user_select_box[uId].value == userIdvalue){
					user_select_box[uId].selected = true;
				}
			}	
		}
	});
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>

<body text="#2B1B17">
	<div style="margin-left: 40px; margin-right: 30px;">
		<center>
			<html:errors />
		</center>
		<logic:present name="selectedUserIdValue" scope="request">
			<input type="hidden" name="selected_userId_value"
				id="selectedUserIdVal"
				value='<bean:write name="selectedUserIdValue" />' />
		</logic:present>
		<html:form action="performanceLog" method="post"
			styleId="performanceLogForm">
			<div id="errorID"></div>

			<logic:present name="userPerformance" scope="request">
				<%-- <bean:define scope="request" id="performance" name="userPerformance"
				type="com.calsoft.performance.form.PerformanceLogForm" /> --%>
				<table class="performance_log_table">
					<tr>
						<td><b>Select Period:</b>
						</td>
						 <td>		
							<logic:notEmpty name="userPerformance" scope="request">
								<html:select property="period" styleId="periodId" onchange="submiturl('performanceLog.do?method=performanceLogPage');">
									<html:option value="">Select</html:option>
									<logic:present name="objectiveQuarters" scope="session">
									 	<logic:notEmpty name="objectiveQuarters" scope="session">
									 		<logic:iterate id="objQrt" name="objectiveQuarters" scope="session">
												<html:option value="<%=(String)objQrt%>"><bean:write name="objQrt" /></html:option>
											</logic:iterate>
										</logic:notEmpty>
									</logic:present>
								</html:select>
							</logic:notEmpty>
						</td>
					</tr>
					<tr>
						<logic:present name="usersList" scope="request">
							<td style="padding-top: 20px;"><b>Select User:</b>
							</td>
						 	<td style="padding-top: 20px;">			 
						 		<html:select property="userId" styleId="user_details" onchange="submiturl('performanceLog.do?method=performanceLogPage');">
									<option value="0" style="font-family: verdana, arial, sans-serif; font-size: 11px;">Select</option>
									<logic:iterate id="user" name="usersList" scope="request"
										type="com.calsoft.user.form.UserForm">
										<option value="<bean:write name='user' property='userId' />"
											style="font-family: verdana, arial, sans-serif; font-size: 11px;">
											<bean:write name="user" property="userName" />
										</option>
									</logic:iterate>
								</html:select>
							</td>
						</logic:present>
						<logic:notPresent name="usersList" scope="request">
							<logic:present name="updatedUsersList" scope="request">
								<td style="padding-top: 20px;"><b>Select User:</b> 
								</td>
								<td style="padding-top: 20px;">
									<html:select property="userId" styleId="user_details" onchange="submiturl('performanceLog.do?method=performanceLogPage');">
										<option value="0" style="font-family: verdana, arial, sans-serif; font-size: 11px;">Select</option>
										<logic:iterate id="user" name="updatedUsersList"
											scope="request" type="com.calsoft.user.form.UserForm">
											<option value="<bean:write name='user' property='userId' />"
												style="font-family: verdana, arial, sans-serif; font-size: 11px;">
												<bean:write name="user" property="userName" />
											</option>
										</logic:iterate>
									</html:select>
								</td>
							</logic:present>
						</logic:notPresent>
					</tr>
				</table>
				<html:hidden property="id" name="userPerformance" />
				<div id="commentID"></div>
				<logic:present name="emailStatus" scope="request">
					<div id="comment1"
						style="font-family: verdana, arial, sans-serif; font-size: 12px; color: blue; text-align: center;">
						<bean:write name="emailStatus" scope="request" />
					</div>
				</logic:present>
				<logic:notPresent name="emailStatus" scope="request">
					<logic:present name="commentStatus" scope="request">
						<div id="comment2"
							style="font-family: verdana, arial, sans-serif; font-size: 12px; color: blue; text-align: center;">
							<bean:write name="commentStatus" scope="request" />
						</div>
					</logic:present>
					<logic:notPresent name="commentStatus" scope="request">
						<logic:present name="commentFail" scope="request">
							<div id="comment3"
								style="font-family: verdana, arial, sans-serif; font-size: 12px; color: red; text-align: center;">
								<bean:write name="commentFail" scope="request" />
							</div>
						</logic:present>
					</logic:notPresent>
				</logic:notPresent>
				<br>
				<br>
				<table class="hovertable">
					<logic:present name="forDisplay" scope="request">
						<thead>
							<tr>
								<td style="font-size: 12px; background-color: #DEE3FF; border-color: #FFFFFF; border-top-left-radius: 1em; border-bottom-right-radius: 1em; width: 500px;">
									&nbsp;Name:&nbsp;
									<b><bean:write name="forDisplay" property="userName" /></b>&nbsp;&nbsp;Period:&nbsp;
									<b><bean:write name="forDisplay" property="period" /></b>
								</td>
								<td style="border-top: none;border-right: none;"></td>
							</tr>
						</thead>
					</logic:present>
					<tbody>
						<tr>
							<td colspan="2">Common Objective: <br> <textarea
									style="width: 650px; height: 120px;" rows="30" cols="150"
									readonly="readonly" name="commonObjective" id="commonObjective"><bean:write name="userPerformance" property="commonObjective" />
								</textarea>
							</td>
							<!-- <td style="border: none;"></td> -->
						</tr>
						<tr>
							<td colspan="2">Specific Objective:<br> <textarea
									readonly="readonly" style="width: 650px; height: 120px;"
									rows="30" cols="150" name="specificObjective"
									id="specificObjective"><bean:write name="userPerformance" property="specificObjective" />
								</textarea>
							</td>
							<!-- <td style="border: none;"></td> -->
						</tr>
						<logic:notEmpty name="listObj" scope="request">
						<tr>
							<td colspan="2">
								<table>
									<tr><span>Comments:</span> 
										<td style="vertical-align: top;border: none;padding-top: 0px;padding-left: 0px;"><html:textarea
													style="width: 450px; height: 90px;" rows="3" cols="4"
													property="userComments"
													value="<%=request.getParameter("userComments")%>"
													styleId="userComments"></html:textarea>
										</td>
										<td style="vertical-align: top;border: thin;border-style:outset;border-color:gray;
										padding-top: 0px;">Access To:
												<div
													style="overflow: auto; height: 80px; border: 1px; border-style: solid; width: 200px; font-size: 10px;">
													<table class="my_tab" id="my_tab">
														<logic:present name="listObj" scope="request">
															<logic:iterate name="listObj" id="roleQ" scope="request">
																<logic:iterate name='roleQ' id="rolename"
																	property="roleNames">
																	<tr>
																		<td style="max-height: 5px; width: 200px;padding: 2px;"><html:multibox
																				styleId="roleNames" property="roleNames"
																				style="height: 10px;vertical-align: top;">
																				<bean:write name='rolename' property="key" />
																			</html:multibox> <bean:write name='rolename' property="value" /></td>
																	</tr>
																</logic:iterate>
															</logic:iterate>
														</logic:present>
													</table>
												</div> <br> <html:button property="save_button" value="Save"
													onclick="return submitCommenturl('performanceLog.do?method=giveComment&validate=true',this);">Save</html:button>
												<input type="button" title="clears your comment"
												onclick="callResetCheckBox()" value="Clear" />
										</td>
									</tr>
								</table>
							</td>
						</tr>
						</logic:notEmpty>
					</tbody>
				</table>
			</logic:present>
			<logic:notEmpty name="commentList" scope="request">
				<br>
				<div id="comment_div_id"
					style="width: 770px; height: 300px; overflow: auto;">
					<table id="commentsTable" border="2" class="hover_tab">
						<thead>
							<tr>
								<th style="width: 125px;"><b>Date</b></th>
								<th style="width: 120px;"><b>Logged by&nbsp; </b></th>
								<th style="width: 445px;">Comment</th>
								<th style="width: 40px;">Accessable Users</th>
								<th style="width: 60px;">Delete</th>
								<th style="width: 60px;">Edit / Save</th>
							</tr>
						</thead>
						<tbody>
							<logic:iterate id="comments" name="commentList" scope="request"
								type="com.calsoft.performance.form.UserCommentForm"
								indexId="count_comment">
								<tr id="<bean:write name="comments" property="commentId" />"
									style="height: 1cm;">
									<td style="width: 50px;" id="comment_date<%=count_comment%>"><bean:write
											name="comments" property="date" /></td>
									<td style="width: 35px;" id="user<%=count_comment%>"><bean:write
											name="comments" property="user_Name" /></td>
									<td style="width: 150px;" id="user_Comm<%=count_comment%>"><bean:write
											name="comments" property="user_Comment" /></td>
									<td style="width: 150px;" id="user_access<%=count_comment%>">
										<%
											List<String> user_info = comments
																.getCommentAccessList();
														if (!user_info.isEmpty()) {
															int user_count = 1;
															for (String valid_usernames : user_info) {
										%>
										<div id="col<%=user_count++%>">
											<%=valid_usernames%>
										</div> <%
 															}
 														}
 												%>
									</td>
									<logic:equal property="commentOwnership" name="comments"
										value="true">
										<td style="width: 35px;"><img title="Delete Comment"
											src="img/delete1.png" style="cursor: pointer;"
											onclick="onDelete('commentLog.do?method=deleteComment&commentId=<bean:write name="comments" property="commentId" />',this)" />
										</td>
										<td style="width: 35px;"><img title="Edit Comment"
											src="img/edit.png" style="cursor: pointer;"
											onclick="editRowInline(this,<bean:write name="comments" property="commentId" />);" />
										</td>
									</logic:equal>
									<logic:notEqual property="commentOwnership" name="comments"
										value="true">
										<td style="width: 35px;"><img title="Operation Denied"
											src="./img/denied.jpg" style="width: 0.6cm; height: 0.6cm;" />
										</td>
										<td style="width: 35px;"><img title="Operation Denied"
											src="./img/denied.jpg" style="width: 0.6cm; height: 0.6cm;" />
										</td>
									</logic:notEqual>
								</tr>
							</logic:iterate>
						</tbody>
					</table>
				</div>
			</logic:notEmpty>
		</html:form>
	</div>
	<br>
</body>
</html>