package com.calsoft.report.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Properties;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jxl.WorkbookSettings;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import com.calsoft.factory.Factory;
import com.calsoft.leave.form.LeaveForm;
import com.calsoft.leave.service.LeaveService;
import com.calsoft.leave.service.factory.LeaveServiceFactory;
import com.calsoft.report.form.ReportForm;
import com.calsoft.report.model.Report;
import com.calsoft.report.service.ReportService;
import com.calsoft.report.service.ReportServiceFactory;
import com.calsoft.task.form.TaskForm;
import com.calsoft.task.service.SaveTaskService;
import com.calsoft.task.service.factory.SaveTaskServiceFactory;
import com.calsoft.user.form.UserForm;
import com.calsoft.user.service.UserService;

@SuppressWarnings({"unchecked","rawtypes"})
public class ReportAction extends DispatchAction implements Job{

	private static final Logger logger = Logger.getLogger("ReportAction");
	private static String glassfishInstanceRootPropertyName = "com.sun.aas.instanceRoot";
	private static String glassfishDomainConfigurationFolderName = "/applications/effort";
	private WritableCellFormat table_header;
	private WritableCellFormat table_content;
	private WritableCellFormat table_content_red;
	private WritableCellFormat table_content_blue;
	private WritableCellFormat table_content_green;
	private WritableCellFormat table_header_pink;
	private WritableCellFormat table_content_light_gray;
	//private static String glassfishDomainConfigurationFolderName = "/eclipseApps/effort";
	// Read a given file from glassfish Domain Configuration folder
	public ActionForward displayReport(ActionMapping map,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		logger.info("You Are Inside displayReport method of ReportAction class");
		HttpSession s1 = request.getSession();
		if(s1.getAttribute("userName")!=null && s1.getAttribute("user_id") != null)   {
			String year="";
			String month="";
			int user_id_from_session = Integer.parseInt(s1.getAttribute("user_id").toString());
			List<UserForm> selectedUserList = new ArrayList<UserForm>();
			try{
				String[] allocatedResource=request.getParameterValues("userId");
				String[] detailedTeam = request.getParameterValues("team");
				s1.removeAttribute("selectedDate");
				String reportMonthYear=request.getParameter("month-settings");
				int i1 = reportMonthYear.length();
				int j = reportMonthYear.indexOf("/");
				int k = reportMonthYear.lastIndexOf("/");
				month = reportMonthYear.substring(j+1,k);
				year = reportMonthYear.substring(k+1,i1);	
				ReportService reportservice=ReportServiceFactory.getReportService();
				List<ReportForm> reportList = null;
				if(allocatedResource!=null){
					for(int i=0;i<allocatedResource.length;i++){
						UserForm userform=new UserForm();
						int userId = Integer.parseInt(allocatedResource[i]);
						userform.setUserId(userId);
						selectedUserList.add(userform);
					}
					reportList = reportservice.getReportData(year,month,allocatedResource);
				}
				else if(detailedTeam!= null){
					DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
					Calendar cal = Calendar.getInstance();
					cal.setTime(df.parse(reportMonthYear));
					cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH)); 
					reportList = reportservice.getReportDataTeamWise(year,month,detailedTeam, cal, user_id_from_session);
				}
				s1.setAttribute("reportListForExcel", reportList);
				ReportForm reportForm=(ReportForm)form;
				String location = reportForm.getLocation();
				int client_resource_id = reportForm.getClient_resource_ids();
				reportForm.setReportDataList(reportList);
				request.setAttribute("reportList", reportList);
				List<String> dayList=new ArrayList<String>();
				Calendar calendar = Calendar.getInstance();
				int date = 1;
				calendar.set(Integer.parseInt(year), Integer.parseInt(month)-1, date);
				int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
				for(int i=1;i<=days;i++){
					calendar.set(Integer.parseInt(year), Integer.parseInt(month)-1,i);
					dayList.add(calendar.getTime().toString().substring(0,3));
				}
				List<String> dayListForDates = new ArrayList<String>();
				DateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
				DateFormat df2 = new SimpleDateFormat("M/d");
				Calendar cal = Calendar.getInstance();
				cal.setTime(df1.parse(reportMonthYear));
				for(int i=1;i<=days;i++){
					cal.set(Integer.parseInt(year), Integer.parseInt(month)-1,i);
					if(!(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)){
						dayListForDates.add(df2.format(cal.getTime()));
					}
					else{
						dayListForDates.add(df2.format(cal.getTime())+" ");
					}
				} 				
				request.setAttribute("dayList", dayList);					// Setted for JSP
				request.setAttribute("dayListForDates", dayListForDates);	// Setted for JSP
				s1.setAttribute("dayList", dayList);						// Setted for excel
				s1.setAttribute("dayListForDates", dayListForDates);		// Setted for excel 
				request.setAttribute("reportType", "timeSheetDashBoard");
				s1.setAttribute("selectedDate", reportMonthYear);
				request.setAttribute("selectedUserList",selectedUserList);
				request.setAttribute("locationDetail", location);
				request.setAttribute("selectedClientResource", client_resource_id);
			}
			catch(Exception e){
				e.printStackTrace();
				throw new Exception();
			}
			return map.findForward("displayReport");
		}
		else
			throw new Exception();
	}
	public ActionForward getUserDetails(ActionMapping map,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		HttpSession s1 = request.getSession();
		if(s1.getAttribute("userName")!=null){
			try{
				logger.info("You Are Currently In getUserDetails method:");
				UserService userService;
				userService=Factory.getUserService();
				List<UserForm> userFormList = userService.getUserNames();
				UserForm userForm=new UserForm();
				userForm.setUserId(0);
				String displaySelectBox="display";
				userForm.setUserName("Select");
				userFormList.add(0, userForm);
				request.setAttribute("displaySelectBox", displaySelectBox);
				request.setAttribute("list", userFormList);

			}catch(Exception e){
				logger.error(e);
				throw new Exception();
			}
			return map.findForward("getUserDetails");
		}
		else 
			throw new Exception();
	}
	public ActionForward getUserAccessDetails(ActionMapping map,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		HttpSession s1 = request.getSession();
		if(s1.getAttribute("userName")!=null){
			try{
				logger.info("You Are Currently In getUserAccessDetails method:");
				UserService userService;
				userService=Factory.getUserService();
				List<UserForm> userFormList = userService.getUserNames();
				UserForm userForm=new UserForm();
				userForm.setUserId(0);
				String displaySelectBox="display";
				userForm.setUserName("Select");
				userFormList.add(0, userForm);
				request.setAttribute("displaySelectBox", displaySelectBox);
				request.setAttribute("list", userFormList);

			}catch(Exception e){
				logger.error(e);
				throw new Exception();
			}
			return map.findForward("getUserAccessDetails");
		}
		else 
			throw new Exception();
	}

	public ActionForward getAllocationList(ActionMapping map,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		HttpSession s1 = request.getSession();
		if(s1.getAttribute("userName")!=null){
			try{
				int userId=Integer.parseInt(request.getParameter("userId"));
				ReportService reportservice=ReportServiceFactory.getReportService();
				List<List<Report>> combinedList=reportservice.getUserReportAllocation(userId);
				Iterator<List<Report>> itr= combinedList.iterator();
				int count=1;
				List<Report> unallocatedList=null;
				List<Report> allocatedList=null;
				while(itr.hasNext()){
					if(count==1){
						allocatedList=itr.next();
					}
					if(count==2){
						unallocatedList=itr.next();
					}
					count++;
				}
				UserService userService;
				userService=Factory.getUserService();
				UserForm userForm=userService.getUsernameFromId(userId);
				String userName=userForm.getUserName();
				UserForm userformNew=new UserForm();
				userformNew.setUserId(userId);
				userformNew.setUserName(userName);
				List<UserForm> userFormList = userService.getUserNames();
				Iterator< UserForm> itr1=userFormList.iterator();
				List<UserForm> userFormUpdatedList=new ArrayList<UserForm>();
				userFormUpdatedList.add(userformNew);
				while(itr1.hasNext()){
					UserForm userFormValue=itr1.next();
					if(!(userFormValue.getUserName().equals(userName))){
						userFormUpdatedList.add(userFormValue);
					}
				}
				request.setAttribute("list",userFormUpdatedList);
				request.setAttribute("combinedList", combinedList);
				request.setAttribute("allocatedList",allocatedList);
				request.setAttribute("unallocatedList",unallocatedList);

			}catch(Exception e){
				e.printStackTrace();
				throw new Exception();
			}
			return map.findForward("getAllocationList");
		}
		else
			throw new Exception();
	}
	public ActionForward getAllocationListForResource(ActionMapping map,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		HttpSession s1 = request.getSession();
		if(s1.getAttribute("userName")!=null){
			try{
				int userId=Integer.parseInt(request.getParameter("userId"));
				ReportService reportservice=ReportServiceFactory.getReportService();
				List<List<Report>> combinedList=reportservice.getUserReportAllocation(userId);
				Iterator<List<Report>> itr= combinedList.iterator();
				int count=1;
				List<Report> unallocatedList=null;
				List<Report> allocatedList=null;
				while(itr.hasNext()){
					if(count==1){
						allocatedList=itr.next();
					}
					if(count==2){
						unallocatedList=itr.next();
					}
					count++;
				}
				UserService userService;
				userService=Factory.getUserService();
				UserForm userForm=userService.getUsernameFromId(userId);
				String userName=userForm.getUserName();
				UserForm userformNew=new UserForm();
				userformNew.setUserId(userId);
				userformNew.setUserName(userName);
				List<UserForm> userFormList = userService.getUserNames();
				Iterator< UserForm> itr1=userFormList.iterator();
				List<UserForm> userFormUpdatedList=new ArrayList<UserForm>();
				userFormUpdatedList.add(userformNew);
				while(itr1.hasNext()){
					UserForm userFormValue=itr1.next();
					if(!(userFormValue.getUserName().equals(userName))){
						userFormUpdatedList.add(userFormValue);
					}
				}
				request.setAttribute("list",userFormUpdatedList);
				request.setAttribute("combinedList", combinedList);
				request.setAttribute("allocatedList",allocatedList);
				request.setAttribute("unallocatedList",unallocatedList);

			}catch(Exception e){
				e.printStackTrace();
				throw new Exception();
			}
			return map.findForward("getAllocationListForResource");
		}
		else
			throw new Exception();
	}


	public ActionForward addResources(ActionMapping map,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		HttpSession s1 = request.getSession();
		if(s1.getAttribute("userName")!=null){
			try{
				int userId=Integer.parseInt(request.getParameter("userId"));  // Selected user id for request
				String[] allocatedResource=request.getParameterValues("userIdValueAdd");
				ReportService reportservice=ReportServiceFactory.getReportService();
				if(allocatedResource!=null){				
					reportservice.addResources(allocatedResource,userId);
				}
				List<List<Report>> combinedList=reportservice.getUserReportAllocation(userId);
				Iterator<List<Report>> itr = combinedList.iterator();
				int count=1;
				List<Report> unallocatedList=null;
				List<Report> allocatedList=null;
				while(itr.hasNext()){
					if(count==1){
						allocatedList=itr.next();
					}
					if(count==2){
						unallocatedList=itr.next();
					}
					count++;
				}
				UserService userService;
				userService=Factory.getUserService();
				UserForm userForm=userService.getUsernameFromId(userId);
				String userName=userForm.getUserName();
				UserForm userformNew=new UserForm();
				userformNew.setUserId(userId);
				userformNew.setUserName(userName);
				List<UserForm> userFormList = userService.getUserNames();
				Iterator< UserForm> itr1=userFormList.iterator();
				List<UserForm> userFormUpdatedList=new ArrayList<UserForm>();
				userFormUpdatedList.add(userformNew);
				while(itr1.hasNext()){
					UserForm userFormValue=itr1.next();
					if(!(userFormValue.getUserName().equals(userName))){
						userFormUpdatedList.add(userFormValue);
					}
				}
				request.setAttribute("allocatedList",allocatedList);
				request.setAttribute("combinedList", combinedList);
				request.setAttribute("unallocatedList",unallocatedList);
				request.setAttribute("list",userFormUpdatedList);
			}catch(Exception e){
				e.printStackTrace();
				throw new Exception();
			}
			return map.findForward("addResources");
		}
		else
			throw new Exception();
	}
	public ActionForward addResourcesAccess(ActionMapping map,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		HttpSession s1 = request.getSession();
		if(s1.getAttribute("userName")!=null){
			try{
				int userId=Integer.parseInt(request.getParameter("userId"));  // Selected user id for request
				String[] allocatedResource=request.getParameterValues("userIdValueAdd");
				if(allocatedResource!=null)
					System.out.println("Allocted user id size is:"+allocatedResource.length);
				ReportService reportservice=ReportServiceFactory.getReportService();
				reportservice.addResources(allocatedResource,userId);
				List<List<Report>> combinedList=reportservice.getUserReportAllocation(userId);
				Iterator<List<Report>> itr= combinedList.iterator();
				int count=1;
				List<Report> unallocatedList=null;
				List<Report> allocatedList=null;
				while(itr.hasNext()){
					if(count==1){
						allocatedList=itr.next();
					}
					if(count==2){
						unallocatedList=itr.next();
					}
					count++;
				}
				UserService userService;
				userService=Factory.getUserService();
				UserForm userForm=userService.getUsernameFromId(userId);
				String userName=userForm.getUserName();
				UserForm userformNew=new UserForm();
				userformNew.setUserId(userId);
				userformNew.setUserName(userName);
				List<UserForm> userFormList = userService.getUserNames();
				Iterator< UserForm> itr1=userFormList.iterator();
				List<UserForm> userFormUpdatedList=new ArrayList<UserForm>();
				userFormUpdatedList.add(userformNew);
				//HttpSession session=request.getSession();
				while(itr1.hasNext()){
					UserForm userFormValue=itr1.next();
					if(!(userFormValue.getUserName().equals(userName))){
						userFormUpdatedList.add(userFormValue);
					}
				}
				request.setAttribute("allocatedList",allocatedList);
				request.setAttribute("combinedList", combinedList);
				request.setAttribute("unallocatedList",unallocatedList);
				request.setAttribute("list",userFormUpdatedList);
			}catch(Exception e){
				e.printStackTrace();
				throw new Exception();
			}
			return map.findForward("addResourcesAccess");
		}
		else
			throw new Exception();
	}

	public ActionForward deleteResources(ActionMapping map,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		HttpSession s1 = request.getSession();
		if(s1.getAttribute("userName")!=null){
			try{
				int userId=Integer.parseInt(request.getParameter("userId"));
				String[] allocatedResource=request.getParameterValues("userIdValueRemove");
				ReportService reportservice=ReportServiceFactory.getReportService();
				reportservice.deleteResources(allocatedResource,userId);
				List<List<Report>> combinedList=reportservice.getUserReportAllocation(userId);
				Iterator<List<Report>> itr= combinedList.iterator();
				int count=1;
				List<Report> unallocatedList=null;
				List<Report> allocatedList=null;
				while(itr.hasNext()){
					if(count==1){
						allocatedList=itr.next();
					}
					if(count==2){
						unallocatedList=itr.next();
					}
					count++;
				}
				UserService userService;
				userService=Factory.getUserService();
				UserForm userForm=userService.getUsernameFromId(userId);
				String userName=userForm.getUserName();
				UserForm userformNew=new UserForm();
				userformNew.setUserId(userId);
				userformNew.setUserName(userName);
				List<UserForm> userFormList = userService.getUserNames();
				Iterator< UserForm> itr1=userFormList.iterator();
				List<UserForm> userFormUpdatedList=new ArrayList<UserForm>();
				userFormUpdatedList.add(userformNew);
				while(itr1.hasNext()){
					UserForm userFormValue=itr1.next();
					if(!(userFormValue.getUserName().equals(userName))){
						userFormUpdatedList.add(userFormValue);
					}
				}
				request.setAttribute("allocatedList",allocatedList);
				request.setAttribute("combinedList", combinedList);
				request.setAttribute("unallocatedList",unallocatedList);
				request.setAttribute("list",userFormUpdatedList);
			}catch(Exception e){
				e.printStackTrace();
				throw new Exception();
			}
			return map.findForward("deleteResources");
		}
		else
			throw new Exception();
	}
	public ActionForward deleteResourcesAccess(ActionMapping map,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		HttpSession s1 = request.getSession();
		if(s1.getAttribute("userName")!=null){
			try{
				int userId=Integer.parseInt(request.getParameter("userId"));
				String[] allocatedResource=request.getParameterValues("userIdValueRemove");
				ReportService reportservice=ReportServiceFactory.getReportService();
				reportservice.deleteResources(allocatedResource,userId);
				List<List<Report>> combinedList=reportservice.getUserReportAllocation(userId);
				Iterator<List<Report>> itr= combinedList.iterator();
				int count=1;
				List<Report> unallocatedList=null;
				List<Report> allocatedList=null;
				while(itr.hasNext()){
					if(count==1){
						allocatedList=itr.next();
					}
					if(count==2){
						unallocatedList=itr.next();
					}
					count++;
				}
				UserService userService;
				userService=Factory.getUserService();
				UserForm userForm=userService.getUsernameFromId(userId);
				String userName=userForm.getUserName();
				UserForm userformNew=new UserForm();
				userformNew.setUserId(userId);
				userformNew.setUserName(userName);
				List<UserForm> userFormList = userService.getUserNames();
				Iterator< UserForm> itr1=userFormList.iterator();
				List<UserForm> userFormUpdatedList=new ArrayList<UserForm>();
				userFormUpdatedList.add(userformNew);
				while(itr1.hasNext()){
					UserForm userFormValue=itr1.next();
					if(!(userFormValue.getUserName().equals(userName))){
						userFormUpdatedList.add(userFormValue);
					}
				}
				request.setAttribute("allocatedList",allocatedList);
				request.setAttribute("combinedList", combinedList);
				request.setAttribute("unallocatedList",unallocatedList);
				request.setAttribute("list",userFormUpdatedList);
			}catch(Exception e){
				e.printStackTrace();
				throw new Exception();
			}
			return map.findForward("deleteResourcesAccess");
		}
		else
			throw new Exception();
	}

	public ActionForward getUserAccessMapping(ActionMapping map,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		HttpSession s1 = request.getSession();
		if(s1.getAttribute("userName")!=null){
			try{
				int userId=Integer.parseInt(request.getParameter("userId"));
				ReportService reportservice=ReportServiceFactory.getReportService();
				List<List<Report>> combinedList=reportservice.getUserReportAllocation(userId);
				Iterator<List<Report>> itr= combinedList.iterator();
				int count=1;
				List<Report> unallocatedList=null;
				List<Report> allocatedList=null;
				while(itr.hasNext()){
					if(count==1){
						allocatedList=itr.next();
					}
					if(count==2){
						unallocatedList=itr.next();
					}
					count++;
				}
				UserService userService;
				userService=Factory.getUserService();
				UserForm userForm=userService.getUsernameFromId(userId);
				String userName=userForm.getUserName();
				UserForm userformNew=new UserForm();
				userformNew.setUserId(userId);
				userformNew.setUserName(userName);
				List<UserForm> userFormList = userService.getUserNames();
				Iterator< UserForm> itr1=userFormList.iterator();
				List<UserForm> userFormUpdatedList=new ArrayList<UserForm>();
				userFormUpdatedList.add(userformNew);
				while(itr1.hasNext()){
					UserForm userFormValue=itr1.next();
					if(!(userFormValue.getUserName().equals(userName))){
						userFormUpdatedList.add(userFormValue);
					}
				}
				//get the list of users with the list of resources
				Iterator<Report> allocatedIterator=allocatedList.iterator();
				int counter=1;
				List<Report> updatedAllocatedList=new ArrayList<Report>();
				while(allocatedIterator.hasNext()){
					Report report=allocatedIterator.next();
					if(counter==1){
						report.setParentUserName(userName);
						//System.out.println("parent user name is:"+report.getParentUserName());
					}
					updatedAllocatedList.add(report);
					counter++;
				}
				request.setAttribute("list",userFormUpdatedList);
				request.setAttribute("combinedList", combinedList);
				request.setAttribute("allocatedList",allocatedList);
				request.setAttribute("updatedallocatedList",updatedAllocatedList);
				request.setAttribute("unallocatedList",unallocatedList);

			}catch(Exception e){
				e.printStackTrace();
				throw new Exception();
			}
			return map.findForward("getUserAccessMapping");
		}
		else
			throw new Exception();
	}

	public ActionForward getUserAccessMappingDetailedList(ActionMapping map,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		HttpSession s1 = request.getSession();
		if(s1.getAttribute("userName")!=null){
			try{
				int userId=Integer.parseInt(request.getParameter("userId"));
				ReportService reportservice=ReportServiceFactory.getReportService();
				List<List<Report>> combinedList=reportservice.getUserReportAllocation(userId);
				Iterator<List<Report>> itr= combinedList.iterator();
				int count=1;
				List<Report> unallocatedList=null;
				List<Report> allocatedList=null;
				while(itr.hasNext()){
					if(count==1){
						allocatedList=itr.next();
					}
					if(count==2){
						unallocatedList=itr.next();
					}
					count++;
				}
				UserService userService;
				userService=Factory.getUserService();
				UserForm userForm=userService.getUsernameFromId(userId);
				String userName=userForm.getUserName();
				UserForm userformNew=new UserForm();
				userformNew.setUserId(userId);
				userformNew.setUserName(userName);
				List<UserForm> userFormList = userService.getUserNames();
				Iterator< UserForm> itr1=userFormList.iterator();
				List<UserForm> userFormUpdatedList=new ArrayList<UserForm>();
				userFormUpdatedList.add(userformNew);
				while(itr1.hasNext())
				{
					UserForm userFormValue=itr1.next();
					if(!(userFormValue.getUserName().equals(userName)))
					{
						userFormUpdatedList.add(userFormValue);
					}
				}
				//get the list of users with the list of resources
				Iterator<Report> allocatedIterator=allocatedList.iterator();
				int counter=1;
				List<Report> updatedAllocatedList=new ArrayList<Report>();
				while(allocatedIterator.hasNext())
				{
					Report report=allocatedIterator.next();
					if(counter==1)
					{
						report.setParentUserName(userName);
						//System.out.println("parent user name is:"+report.getParentUserName());
					}
					updatedAllocatedList.add(report);
					counter++;
				}
				request.setAttribute("list",userFormUpdatedList);
				request.setAttribute("combinedList", combinedList);
				request.setAttribute("allocatedList",allocatedList);
				request.setAttribute("updatedallocatedList",updatedAllocatedList);
				request.setAttribute("unallocatedList",unallocatedList);

			}catch(Exception e)
			{
				e.printStackTrace();
				throw new Exception();
			}
			return map.findForward("getUserAccessMappingDetailedList");
		}
		else
			throw new Exception();
	}

	//get the allocation mapping for all users
	public ActionForward getAllUserAccessMapping(ActionMapping map,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		HttpSession s1 = request.getSession();
		if(s1.getAttribute("userName")!=null){
			try{
				int userId=Integer.parseInt(request.getParameter("userId"));
				ReportService reportservice=ReportServiceFactory.getReportService();
				List<List<Report>> combinedList=reportservice.getUserReportAllocation(userId);
				//get all user access mapping
				List<List<Report>> allocationMappingAllUsers=reportservice.getUserReportAllocation();
				//logger.info("all user allocation list size is:"+allocationMappingAllUsers.size());
				Iterator<List<Report>> itr= combinedList.iterator();
				int count=1;
				List<Report> unallocatedList=null;
				List<Report> allocatedList=null;
				while(itr.hasNext()){
					if(count==1){
						allocatedList=itr.next();
					}
					if(count==2){
						unallocatedList=itr.next();
					}
					count++;
				}
				UserService userService;
				userService=Factory.getUserService();
				UserForm userForm=userService.getUsernameFromId(userId);
				String userName=userForm.getUserName();
				UserForm userformNew=new UserForm();
				userformNew.setUserId(userId);
				userformNew.setUserName(userName);
				List<UserForm> userFormList = userService.getUserNames();
				Iterator< UserForm> itr1=userFormList.iterator();
				List<UserForm> userFormUpdatedList=new ArrayList<UserForm>();
				userFormUpdatedList.add(userformNew);
				while(itr1.hasNext()){
					UserForm userFormValue=itr1.next();
					if(!(userFormValue.getUserName().equals(userName))){
						userFormUpdatedList.add(userFormValue);
					}
				}
				//get the list of users with the list of resources
				List<Report> updatedAllocationListAllUser=new ArrayList<Report>();
				Report allocationListAllUser=null;
				Iterator<List<Report>> allocationAllUserIterator= allocationMappingAllUsers.iterator();
				while(allocationAllUserIterator.hasNext()){
					List<Report> allocationListUser=allocationAllUserIterator.next();
					allocationListAllUser=new Report();
					Iterator<Report> allocatedIterator=allocationListUser.iterator();
					int counter=1;
					List<Report> updatedAllocatedList=new ArrayList<Report>();
					while(allocatedIterator.hasNext()){
						Report report=allocatedIterator.next();
						if(counter==1){
							report.setParentUserName(report.getParentUserName());
						}
						updatedAllocatedList.add(report);
						counter++;
					}
					allocationListAllUser.setAllUserAllocationList(updatedAllocatedList);
					updatedAllocationListAllUser.add(allocationListAllUser);
				}
				request.setAttribute("list",userFormUpdatedList);
				request.setAttribute("combinedList", combinedList);
				request.setAttribute("allocatedList",allocatedList);
				request.setAttribute("updatedAllocationListAllUser",updatedAllocationListAllUser);
				request.setAttribute("unallocatedList",unallocatedList);

			}catch(Exception e){
				e.printStackTrace();
				throw new Exception();
			}
			return map.findForward("getUserAccessMapping");
		}
		else
			throw new Exception();
	}
	public ActionForward getAllUserAccessMappingDetails(ActionMapping map,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		HttpSession s1 = request.getSession();
		if(s1.getAttribute("userName")!=null){
			try{
				int userId=Integer.parseInt(request.getParameter("userId"));
				ReportService reportservice=ReportServiceFactory.getReportService();
				List<List<Report>> combinedList=reportservice.getUserReportAllocation(userId);
				//get all user access mapping
				List<List<Report>> allocationMappingAllUsers=reportservice.getUserReportAllocation();
				Iterator<List<Report>> itr= combinedList.iterator();
				int count=1;
				List<Report> unallocatedList=null;
				List<Report> allocatedList=null;
				while(itr.hasNext()){
					if(count==1){
						allocatedList=itr.next();
					}
					if(count==2){
						unallocatedList=itr.next();
					}
					count++;
				}
				UserService userService;
				userService=Factory.getUserService();
				UserForm userForm=userService.getUsernameFromId(userId);
				String userName=userForm.getUserName();
				UserForm userformNew=new UserForm();
				userformNew.setUserId(userId);
				userformNew.setUserName(userName);
				List<UserForm> userFormList = userService.getUserNames();
				Iterator< UserForm> itr1=userFormList.iterator();
				List<UserForm> userFormUpdatedList=new ArrayList<UserForm>();
				userFormUpdatedList.add(userformNew);
				while(itr1.hasNext()){
					UserForm userFormValue=itr1.next();
					if(!(userFormValue.getUserName().equals(userName))){
						userFormUpdatedList.add(userFormValue);
					}
				}
				//get the list of users with the list of resources
				List<Report> updatedAllocationListAllUser=new ArrayList<Report>();
				Report allocationListAllUser=null;
				Iterator<List<Report>> allocationAllUserIterator= allocationMappingAllUsers.iterator();
				while(allocationAllUserIterator.hasNext()){
					List<Report> allocationListUser=allocationAllUserIterator.next();
					allocationListAllUser=new Report();
					Iterator<Report> allocatedIterator=allocationListUser.iterator();
					int counter=1;
					List<Report> updatedAllocatedList=new ArrayList<Report>();
					while(allocatedIterator.hasNext()){
						Report report=allocatedIterator.next();
						if(counter==1){
							report.setParentUserName(report.getParentUserName());
						}
						updatedAllocatedList.add(report);
						counter++;
					}
					allocationListAllUser.setAllUserAllocationList(updatedAllocatedList);
					updatedAllocationListAllUser.add(allocationListAllUser);
				}
				request.setAttribute("list",userFormUpdatedList);
				request.setAttribute("combinedList", combinedList);
				request.setAttribute("allocatedList",allocatedList);
				request.setAttribute("updatedAllocationListAllUser",updatedAllocationListAllUser);
				request.setAttribute("unallocatedList",unallocatedList);

			}catch(Exception e){
				e.printStackTrace();
				throw new Exception();
			}
			return map.findForward("getAllUserAccessMappingDetails");
		}
		else
			throw new Exception();
	}
	public ActionForward getUserReportDetails(ActionMapping map,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		//get the resources that are allocated for a particular user 
		HttpSession s1 = request.getSession();
		s1.removeAttribute("selectedDate");
		s1.removeAttribute("conList");
		s1.removeAttribute("userList");
		s1.removeAttribute("conListUpdate");
		if(s1.getAttribute("user_id")!=null){
			try{
				int userId=Integer.parseInt(s1.getAttribute("user_id").toString());
				UserService userService;
				userService=Factory.getUserService();
				Calendar cal = Calendar.getInstance();				
				cal.setTime(Calendar.getInstance().getTime()); // Current Date 
				List<UserForm> allocatedUserList=userService.getAllocatedResourcesBasedOnStartAndExitDate(userId, cal);
				String user_role_id = userService.getUserRoleId(userId);
				if(user_role_id != null && user_role_id.equals("1005")){
					List<String> conList = userService.getTeamFromContact();				
					s1.setAttribute("conList",conList);
				}
				// Give Access for Location-Wise Report.
				else if(user_role_id.equals("1007")){
					List<ReportForm> clientListInfo = userService.getAllClientInformationForClientBasedReport();
					s1.setAttribute("listForClientResourceDetail", clientListInfo);
					s1.setAttribute("allowedForLocationWiseReport", "allowedForLocationWiseReport");
				}
				s1.setAttribute("userList",allocatedUserList);
				request.setAttribute("reportType", "noReport");
			}
			catch(Exception e){
				e.printStackTrace();
				throw new Exception();
			}
			return map.findForward("getUserReportDetails");
		}
		else
			throw new Exception();
	}
	public ActionForward getDetailedTimesheet(ActionMapping map,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		//get the resources that are allocated for a particular user 
		HttpSession s1 = request.getSession();
		if(s1.getAttribute("user_id")!=null){
			try{
				int user_id = Integer.parseInt(s1.getAttribute("user_id").toString());
				ReportForm reportForm = (ReportForm) form;	
				String location = reportForm.getLocation();
				int clientResourceId = reportForm.getClient_resource_ids();
				String year="";
				String month="";
				String[] allocatedResource=request.getParameterValues("userId");
				String[] detailsTeam =request.getParameterValues("team");
				s1.setAttribute("allocatedResource", allocatedResource);
				s1.removeAttribute("selectedDate");
				String reportMonthYear=request.getParameter("month-settings");
				int i = reportMonthYear.length();
				int j = reportMonthYear.indexOf("/");
				int k = reportMonthYear.lastIndexOf("/");
				month = reportMonthYear.substring(j+1,k);
				year = reportMonthYear.substring(k+1,i);
				SaveTaskService saveTaskService = SaveTaskServiceFactory.getSaveTaskService();
				List<TaskForm> taskFormList = null;
				if(allocatedResource != null){
					taskFormList=saveTaskService.getTaskDetailsUser(allocatedResource, year, month);
				}
				else if(detailsTeam != null){
					taskFormList = saveTaskService.getTaskDetailsTeamWise(detailsTeam, year, month, user_id );
				}
				s1.setAttribute("taskFormListExcel", taskFormList);				
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				Calendar cal = Calendar.getInstance();
				cal.setTime(df.parse(reportMonthYear));
				cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
				s1.setAttribute("selectedDate", reportMonthYear);
				request.setAttribute("taskFormList", taskFormList);
				request.setAttribute("reportType", "dashBoardReport");
				request.setAttribute("monthYearString",reportMonthYear);
				request.setAttribute("locationDetail", location);
				request.setAttribute("selectedClientResource", clientResourceId);
			}
			catch(Exception e){
				e.printStackTrace();
				throw new Exception();
			}
			return map.findForward("getDetailedTimesheet");
		}
		else
			throw new Exception();
	}
	public ActionForward getLeaveReport(ActionMapping map,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		//get the resources that are allocated for a particular user 
		logger.info("From ReportAction getLeaveReport Method ");
		HttpSession s1 = request.getSession();
		if(s1.getAttribute("userName")!=null){
			try{
				int user_id_from_session = Integer.parseInt(s1.getAttribute("user_id").toString());
				ReportForm reportForm = (ReportForm) form;	
				String location = reportForm.getLocation();
				int clientResourceId = reportForm.getClient_resource_ids();
				String year="";
				String month="";
				String reportMonthYear=request.getParameter("month-settings");
				StringTokenizer tokenizer=new StringTokenizer(reportMonthYear,"/");
				while(tokenizer.hasMoreElements()){
					tokenizer.nextToken();
					month=tokenizer.nextToken();
					year=tokenizer.nextToken();
				}
				String[] monthName = {"Jan", "Feb",
						"Mar", "Apr", "May", "Jun", "Jul",
						"Aug", "Sep", "Oct", "Nov",
						"Dec"
				};
				String monthString = monthName[Integer.parseInt(month)-1];
				String dateString=monthString+"-"+year;				
				String[] allocatedResource=request.getParameterValues("userId");
				String[] teamDetails = request.getParameterValues("team");
				s1.removeAttribute("selectedDate");
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				Calendar cal = Calendar.getInstance();
				cal.setTime(df.parse(reportMonthYear));
				cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH)); 
				LeaveService leaveService = LeaveServiceFactory.getLeaveService();
				if(allocatedResource!=null){	
					List<LeaveForm> leaveFormList=leaveService.getLeaveDetailsUser(allocatedResource, dateString);
					request.setAttribute("leaveFormList",leaveFormList);
					s1.setAttribute("leaveFormListExcel",leaveFormList);
				}
				else if(teamDetails!= null){					
					List<LeaveForm> leaveFormList=leaveService.getLeaveDetailsTeamWise(teamDetails, dateString, user_id_from_session);
					request.setAttribute("leaveFormList",leaveFormList);
					s1.setAttribute("leaveFormListExcel",leaveFormList);
				}				
				request.setAttribute("reportType", "leaveReport");
				request.setAttribute("locationDetail", location);
				request.setAttribute("selectedClientResource", clientResourceId);
				s1.setAttribute("selectedDate", reportMonthYear);
			}
			catch(Exception e){
				e.printStackTrace();
				throw new Exception();
			}
			return map.findForward("getDetailedTimesheet");
		}
		else
			throw new Exception();
	}
	//excel leave  report  
	public void generateLeaveReportExcel(ActionMapping map,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		//get the resources that are allocated for a particular user 
		HttpSession sess = request.getSession();
		if(sess.getAttribute("userName")!=null){
			try{
				List<LeaveForm> leaveFormList= (List<LeaveForm>)sess.getAttribute("leaveFormListExcel");
				request.setAttribute("leaveFormList",leaveFormList);			
				OutputStream out = response.getOutputStream();				
				WorkbookSettings wbSettings = new WorkbookSettings();
				wbSettings.setLocale(new Locale("en", "EN"));				
				WritableWorkbook workbook = jxl.Workbook.createWorkbook(out, wbSettings);			
				workbook.createSheet("Leave Report", 0);			
				WritableSheet excelSheet = workbook.getSheet(0);				
				excelSheet.setColumnView(0, 20);
				excelSheet.setColumnView(1, 20);
				excelSheet.setColumnView(2, 20);

				//Getting different fonts. 
				getColorFonts();

				Iterator<LeaveForm> leaveFormIterator=leaveFormList.iterator();	
				int count=0;
				while(leaveFormIterator.hasNext()){
					LeaveForm leaveForm=leaveFormIterator.next();
					List<LeaveForm> leaveformList=leaveForm.getLeaveFormList();
					if(leaveformList!=null&&leaveformList.size()>0){
						ListIterator<LeaveForm> leaveIterator=leaveformList.listIterator();
						excelSheet.setRowView(count, 20*20);
						addCaption(excelSheet, 0, count, "Resource Name", table_header);
						addCaption(excelSheet, 1, count, "Leave Availed On", table_header);
						addCaption(excelSheet, 2, count, "Updated On", table_header);
						count++;
						while(leaveIterator.hasNext()){
							LeaveForm leaveFormData=leaveIterator.next();
							excelSheet.setRowView(count, 30*20);
							addLabel(excelSheet, 0, count, leaveFormData.getUserName(), table_content);
							addLabel(excelSheet, 1, count, leaveFormData.getSelectMonth(), table_content);
							addLabel(excelSheet, 2, count, leaveFormData.getUpdatedDateString(), table_content);
							count++;
						}											
						excelSheet.setRowView(count, 500);
						Label label = new Label(0, count, "");
						excelSheet.addCell(label);
						count++;
					}
				}				
				response.setContentType("application/vnd.ms-excel");
				Calendar cal = Calendar.getInstance();
				DateFormat df = new SimpleDateFormat("dd_MMMM_yy_HH-mm");
				String file_name = df.format(cal.getTime());
				response.setHeader("Content-Disposition", "inline; filename=" +"LeaveReport_"+file_name+".xls");				
				workbook.write();
				workbook.close();			
				out.flush();
				out.close();
			}catch(Exception e){
				e.printStackTrace();
				throw new Exception();
			}
		}
		else
			throw new Exception();
	}
	//Generate Time Sheet reports
	public void generateDetailedTimesheetReport(ActionMapping map,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		//get the resources that are allocated for a particular user 
		HttpSession s1 = request.getSession();
		if(s1.getAttribute("userName")!=null){
			try{
				List<TaskForm> taskFormList = (List<TaskForm>) s1.getAttribute("taskFormListExcel");			
				OutputStream out = response.getOutputStream();				
				WorkbookSettings wbSettings = new WorkbookSettings();
				wbSettings.setLocale(new Locale("en", "EN"));				
				WritableWorkbook workbook = jxl.Workbook.createWorkbook(out, wbSettings);			
				workbook.createSheet("Detailed Timesheet Report", 0);			
				WritableSheet excelSheet = workbook.getSheet(0);				
				excelSheet.setColumnView(0, 20);
				excelSheet.setColumnView(1, 20);
				excelSheet.setColumnView(2, 20);
				excelSheet.setColumnView(3, 20);
				excelSheet.setColumnView(4, 20);
				excelSheet.setColumnView(5, 20);
				excelSheet.setColumnView(6, 20);
				excelSheet.setColumnView(7, 20);

				//Getting different fonts. 
				getColorFonts();

				int count = 0;
				Iterator<TaskForm> detailedtask_list = taskFormList.iterator();
				while(detailedtask_list.hasNext()){
					TaskForm taskForm = detailedtask_list.next();
					List<TaskForm> taskFormListValue = taskForm.getTaskFormList();
					if(taskFormListValue != null && taskFormListValue.size() > 0){
						ListIterator<TaskForm> taskIterator = taskFormListValue.listIterator();	
						excelSheet.setRowView(count, 30*20);
						addCaption(excelSheet, 0, count, "Username", table_header);
						addCaption(excelSheet, 1, count, "Date", table_header);
						addCaption(excelSheet, 2, count, "Category", table_header);
						addCaption(excelSheet, 3, count, "Backlog ID", table_header);
						addCaption(excelSheet, 4, count, "Task ID", table_header);
						addCaption(excelSheet, 5, count, "Description", table_header);
						addCaption(excelSheet, 6, count, "Efforts From", table_header);
						addCaption(excelSheet, 7, count, "Time Spend in Hrs", table_header);
						count++;
						while(taskIterator.hasNext()){
							TaskForm taskFormData=taskIterator.next();
							String username = taskFormData.getUserName();
							String taskDate = taskFormData.getTask_date();
							String status = taskFormData.getStatus();
							String back_log = taskFormData.getBacklog_id();
							String task_log = taskFormData.getTask_id();
							String task_desc= taskFormData.getTask_description();
							String wk_status = taskFormData.getWork_status();
							Double time = Double.parseDouble(taskFormData.getTime());
							if(wk_status!=null && wk_status.equalsIgnoreCase("office")){
								wk_status = "Office";
							}
							else if(wk_status!=null && wk_status.equalsIgnoreCase("home")){
								wk_status = "Home";
							}	
							excelSheet.setRowView(count, 20*20);
							if(taskFormData.getStatus().equalsIgnoreCase("Comp off")){
								addLabel(excelSheet, 0, count, username, table_content_green);
								addLabel(excelSheet, 1, count, taskDate, table_content_green);
								addLabel(excelSheet, 2, count, status, table_content_green);
								addLabel(excelSheet, 3, count, back_log, table_content_green);
								addLabel(excelSheet, 4, count, task_log, table_content_green);
								addLabel(excelSheet, 5, count, task_desc, table_content_green);
								addLabel(excelSheet, 6, count, wk_status, table_content_green);

							}
							else{
								addLabel(excelSheet, 0, count, username, table_content);
								addLabel(excelSheet, 1, count, taskDate, table_content);
								addLabel(excelSheet, 2, count, status, table_content);
								addLabel(excelSheet, 3, count, back_log, table_content);
								addLabel(excelSheet, 4, count, task_log, table_content);
								addLabel(excelSheet, 5, count, task_desc, table_content);
								addLabel(excelSheet, 6, count, wk_status, table_content);
							}
							if(status!=null && (status.equalsIgnoreCase("Half Day") || status.equalsIgnoreCase("Leave"))){
								addNumber(excelSheet, 7, count, time, table_content_red);
							}
							else if(taskFormData!=null && (taskFormData.getStatus()).equalsIgnoreCase("Public holiday")){
								addNumber(excelSheet, 7, count, time, table_content_blue);
							}	
							else if(taskFormData!=null && (taskFormData.getStatus()).equalsIgnoreCase("Comp off")){
								addNumber(excelSheet, 7, count, time, table_content_green);
							}
							else{								
								addNumber(excelSheet, 7, count, time, table_content);
							}
							count++;
						}																												
						count++;						
					}						
				}				
				response.setContentType("application/vnd.ms-excel");
				Calendar cal = Calendar.getInstance();
				DateFormat df = new SimpleDateFormat("dd_MMMM_yy_HH-mm");
				String file_name = df.format(cal.getTime());
				response.setHeader("Content-Disposition", "inline; filename=" +"DetailedTimesheetReport_"+file_name+".xls");				
				workbook.write();
				workbook.close();			
				out.flush();
				out.close();
			}catch(Exception e){
				e.printStackTrace();
				throw new Exception();
			}
		}
		else
			throw new Exception();
	}
	
	public void generateReportTimeSheetDashBoard(ActionMapping map,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		HttpSession sess =  request.getSession();
		if(sess.getAttribute("userName") !=null && sess.getAttribute("selectedDate") != null){			
			String month_year = (String) sess.getAttribute("selectedDate");
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			DateFormat df_days = new SimpleDateFormat("EEE");
			DateFormat df_dates = new SimpleDateFormat("M/d");

			Calendar cal = Calendar.getInstance();
			cal.setTime(df.parse(month_year));
			cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
			int dayCountInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH) ;		

			try{
				List<ReportForm> reportList = (List<ReportForm>) sess.getAttribute("reportListForExcel");

				OutputStream out = response.getOutputStream();				
				WorkbookSettings wbSettings = new WorkbookSettings();
				wbSettings.setLocale(new Locale("en", "EN"));				
				WritableWorkbook workbook = jxl.Workbook.createWorkbook(out, wbSettings);			
				workbook.createSheet("Timesheet Dashboard Report", 0);			
				WritableSheet excelSheet = workbook.getSheet(0);
				//Getting all font color.
				getColorFonts();
				excelSheet.setColumnView(0, 20);
				excelSheet.setRowView(0, 20*20);
				addCaption(excelSheet, 0, 0, "Day", table_header);
				addCaption(excelSheet, 0, 1, "Username", table_header);
				int count = 1;
				for (int mon_date = 1; mon_date <= dayCountInMonth; mon_date++) {
					cal.set(Calendar.DAY_OF_MONTH, mon_date);
					// Setting column width for Day and Date column.
					excelSheet.setColumnView(count, 7);
					if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
						addCaption(excelSheet, count, 0, df_days.format(cal.getTime()), table_header_pink);
						addCaption(excelSheet, count, 1, df_dates.format(cal.getTime()), table_header_pink);	
					}
					else{
						addCaption(excelSheet, count, 0, df_days.format(cal.getTime()), table_header);
						addCaption(excelSheet, count, 1, df_dates.format(cal.getTime()), table_header);
					}
					count++;
				}			
				addCaption(excelSheet, count, 0, "Total Time", table_header);
				addCaption(excelSheet, count, 1, "In Hrs", table_header);

				int row_count = 2;				
				Iterator<ReportForm> timeSheetDashBoardIterator=reportList.iterator();
				while(timeSheetDashBoardIterator.hasNext()){
					int col_count = 0;
					ReportForm reportFormValue = timeSheetDashBoardIterator.next();
					addLabel(excelSheet, col_count++, row_count, reportFormValue.getUserName(), table_content_light_gray);
					
					if(reportFormValue.getTime1()!="" &&  reportFormValue.getTime1().charAt(reportFormValue.getTime1().length()-3) ==' '){
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime1(), table_content_green);
						//r2c2.setCellStyle(cellComp);
						//r2c2.setCellValue(reportFormValue.getTime1());
					}
					else if(reportFormValue.getTime1()!="" &&  reportFormValue.getTime1().charAt(reportFormValue.getTime1().length()-2) ==' '){
						//r2c2.setCellStyle(cellSt11);
						//r2c2.setCellValue(reportFormValue.getTime1());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime1(), table_content_blue);
					}
					else if(reportFormValue.getTime1()!="" &&  reportFormValue.getTime1().charAt(reportFormValue.getTime1().length()-1) ==' '){
						//r2c2.setCellStyle(cellSt);
						//r2c2.setCellValue(reportFormValue.getTime1());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime1(), table_content_red);
					}
					else{
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime1(), table_content);
					}
				
					if(reportFormValue.getTime2()!="" && reportFormValue.getTime2().charAt(reportFormValue.getTime2().length()-3) ==' '){
						//r2c3.setCellStyle(cellComp);
						//r2c3.setCellValue(reportFormValue.getTime2());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime2(), table_content_green);
						
					}
					else if(reportFormValue.getTime2()!="" && reportFormValue.getTime2().charAt(reportFormValue.getTime2().length()-2) ==' '){
						//r2c3.setCellStyle(cellSt11);
						//r2c3.setCellValue(reportFormValue.getTime2());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime2(), table_content_blue);
					}
					else if(reportFormValue.getTime2()!="" && reportFormValue.getTime2().charAt(reportFormValue.getTime2().length()-1) ==' '){
						//r2c3.setCellStyle(cellSt);
						//r2c3.setCellValue(reportFormValue.getTime2());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime2(), table_content_red);
					}
					else{
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime2(), table_content);
					}

					if(reportFormValue.getTime3()!="" && reportFormValue.getTime3().charAt(reportFormValue.getTime3().length()-3) ==' '){
						//r2c4.setCellStyle(cellComp);
						//r2c4.setCellValue(reportFormValue.getTime3());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime3(), table_content_green);
					}
					else if(reportFormValue.getTime3()!="" && reportFormValue.getTime3().charAt(reportFormValue.getTime3().length()-2) ==' '){
						//r2c4.setCellStyle(cellSt11);
						//r2c4.setCellValue(reportFormValue.getTime3());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime3(), table_content_blue);
					}
					else if(reportFormValue.getTime3()!="" && reportFormValue.getTime3().charAt(reportFormValue.getTime3().length()-1) ==' '){
						//r2c4.setCellStyle(cellSt);
						//r2c4.setCellValue(reportFormValue.getTime3());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime3(), table_content_red);
					}
					else{
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime3(), table_content);
					}
					
					if(reportFormValue.getTime4()!="" && reportFormValue.getTime4().charAt(reportFormValue.getTime4().length()-3) ==' '){
						//r2c5.setCellStyle(cellComp);
						//r2c5.setCellValue(reportFormValue.getTime4());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime4(), table_content_green);
					}
					else if(reportFormValue.getTime4()!="" && reportFormValue.getTime4().charAt(reportFormValue.getTime4().length()-2) ==' '){
						//r2c5.setCellStyle(cellSt11);
						//r2c5.setCellValue(reportFormValue.getTime4());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime4(), table_content_blue);
					}
					else if(reportFormValue.getTime4()!="" && reportFormValue.getTime4().charAt(reportFormValue.getTime4().length()-1) ==' '){
						//r2c5.setCellStyle(cellSt);
						//r2c5.setCellValue(reportFormValue.getTime4());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime4(), table_content_red);
					}
					else{
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime4(), table_content);
					}
					
					if(reportFormValue.getTime5()!="" && reportFormValue.getTime5().charAt(reportFormValue.getTime5().length()-3) ==' '){
						//r2c6.setCellStyle(cellComp);
						//r2c6.setCellValue(reportFormValue.getTime5());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime5(), table_content_green);
					}
					else if(reportFormValue.getTime5()!="" && reportFormValue.getTime5().charAt(reportFormValue.getTime5().length()-2) ==' '){
						//r2c6.setCellStyle(cellSt11);
						//r2c6.setCellValue(reportFormValue.getTime5());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime5(), table_content_blue);
					}
					else if(reportFormValue.getTime5()!="" && reportFormValue.getTime5().charAt(reportFormValue.getTime5().length()-1) ==' '){
						//r2c6.setCellStyle(cellSt);
						//r2c6.setCellValue(reportFormValue.getTime5());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime5(), table_content_red);
					}
					else{
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime5(), table_content);
					}
					
					if(reportFormValue.getTime6()!="" && reportFormValue.getTime6().charAt(reportFormValue.getTime6().length()-3) ==' '){
						//r2c7.setCellStyle(cellComp);
						//r2c7.setCellValue(reportFormValue.getTime6());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime6(), table_content_green);
					}
					else if(reportFormValue.getTime6()!="" && reportFormValue.getTime6().charAt(reportFormValue.getTime6().length()-2) ==' '){
						//r2c7.setCellStyle(cellSt11);
						//r2c7.setCellValue(reportFormValue.getTime6());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime6(), table_content_blue);
					}
					else if(reportFormValue.getTime6()!="" && reportFormValue.getTime6().charAt(reportFormValue.getTime6().length()-1) ==' '){
						//r2c7.setCellStyle(cellSt);
						//r2c7.setCellValue(reportFormValue.getTime6());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime6(), table_content_red);
					}
					else{
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime6(), table_content);
					}

					if(reportFormValue.getTime7()!="" && reportFormValue.getTime7().charAt(reportFormValue.getTime7().length()-3) ==' '){
						//r2c8.setCellStyle(cellComp);
						//r2c8.setCellValue(reportFormValue.getTime7());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime7(), table_content_green);
					}
					else if(reportFormValue.getTime7()!="" && reportFormValue.getTime7().charAt(reportFormValue.getTime7().length()-2) ==' '){
						//r2c8.setCellStyle(cellSt11);
						//r2c8.setCellValue(reportFormValue.getTime7());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime7(), table_content_blue);
					}
					else if(reportFormValue.getTime7()!="" && reportFormValue.getTime7().charAt(reportFormValue.getTime7().length()-1) ==' '){
						//r2c8.setCellStyle(cellSt);
						//r2c8.setCellValue(reportFormValue.getTime7());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime7(), table_content_red);
					}
					else{
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime7(), table_content);
					}

					if(reportFormValue.getTime8()!="" && reportFormValue.getTime8().charAt(reportFormValue.getTime8().length()-3) ==' '){
						//r2c9.setCellStyle(cellComp);
						//r2c9.setCellValue(reportFormValue.getTime8());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime8(), table_content_green);
					}
					else if(reportFormValue.getTime8()!="" && reportFormValue.getTime8().charAt(reportFormValue.getTime8().length()-2) ==' '){
						//r2c9.setCellStyle(cellSt11);
						//r2c9.setCellValue(reportFormValue.getTime8());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime8(), table_content_blue);
					}
					else if(reportFormValue.getTime8()!="" && reportFormValue.getTime8().charAt(reportFormValue.getTime8().length()-1) ==' '){
						//r2c9.setCellStyle(cellSt);
						//r2c9.setCellValue(reportFormValue.getTime8());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime8(), table_content_red);
					}
					else{
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime8(), table_content);
					}

					if(reportFormValue.getTime9()!="" && reportFormValue.getTime9().charAt(reportFormValue.getTime9().length()-3) ==' '){
						//r2c10.setCellStyle(cellComp);
						//r2c10.setCellValue(reportFormValue.getTime9());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime9(), table_content_green);
					}
					else if(reportFormValue.getTime9()!="" && reportFormValue.getTime9().charAt(reportFormValue.getTime9().length()-2) ==' '){
						//r2c10.setCellStyle(cellSt11);
						//r2c10.setCellValue(reportFormValue.getTime9());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime9(), table_content_blue);
					}
					else if(reportFormValue.getTime9()!="" && reportFormValue.getTime9().charAt(reportFormValue.getTime9().length()-1) ==' '){
						//r2c10.setCellStyle(cellSt);
						//r2c10.setCellValue(reportFormValue.getTime9());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime9(), table_content_red);
					}
					else{
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime9(), table_content);
					}
					
					if(reportFormValue.getTime10()!="" && reportFormValue.getTime10().charAt(reportFormValue.getTime10().length()-3) ==' '){
						//r2c11.setCellStyle(cellComp);
						//r2c11.setCellValue(reportFormValue.getTime10());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime10(), table_content_green);
					}
					else if(reportFormValue.getTime10()!="" && reportFormValue.getTime10().charAt(reportFormValue.getTime10().length()-2) ==' '){
						//r2c11.setCellStyle(cellSt11);
						//r2c11.setCellValue(reportFormValue.getTime10());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime10(), table_content_blue);
					}
					else if(reportFormValue.getTime10()!="" && reportFormValue.getTime10().charAt(reportFormValue.getTime10().length()-1) ==' '){
						//r2c11.setCellStyle(cellSt);
						//r2c11.setCellValue(reportFormValue.getTime10());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime10(), table_content_red);
					}
					else{
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime10(), table_content);
					}
					
					if(reportFormValue.getTime11()!="" && reportFormValue.getTime11().charAt(reportFormValue.getTime11().length()-3) ==' '){
						//r2c12.setCellStyle(cellComp);
						//r2c12.setCellValue(reportFormValue.getTime11());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime11(), table_content_green);
					}
					else if(reportFormValue.getTime11()!="" && reportFormValue.getTime11().charAt(reportFormValue.getTime11().length()-2) ==' '){
						//r2c12.setCellStyle(cellSt11);
						//r2c12.setCellValue(reportFormValue.getTime11());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime11(), table_content_blue);
					}
					else if(reportFormValue.getTime11()!="" && reportFormValue.getTime11().charAt(reportFormValue.getTime11().length()-1) ==' '){
						//r2c12.setCellStyle(cellSt);
						//r2c12.setCellValue(reportFormValue.getTime11());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime11(), table_content_red);
					}
					else{
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime11(), table_content);
					}

					if(reportFormValue.getTime12()!="" && reportFormValue.getTime12().charAt(reportFormValue.getTime12().length()-3) ==' '){
						//r2c13.setCellStyle(cellComp);
						//r2c13.setCellValue(reportFormValue.getTime12());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime12(), table_content_green);
					}
					else if(reportFormValue.getTime12()!="" && reportFormValue.getTime12().charAt(reportFormValue.getTime12().length()-2) ==' '){
						//r2c13.setCellStyle(cellSt11);
						//r2c13.setCellValue(reportFormValue.getTime12());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime12(), table_content_blue);
					}
					else if(reportFormValue.getTime12()!="" && reportFormValue.getTime12().charAt(reportFormValue.getTime12().length()-1) ==' '){
						//r2c13.setCellStyle(cellSt);
						//r2c13.setCellValue(reportFormValue.getTime12());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime12(), table_content_red);
					}
					else{
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime12(), table_content);
					}

					if(reportFormValue.getTime13()!="" && reportFormValue.getTime13().charAt(reportFormValue.getTime13().length()-3) ==' '){
						//r2c14.setCellStyle(cellComp);
						//r2c14.setCellValue(reportFormValue.getTime13());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime13(), table_content_green);
					}
					else if(reportFormValue.getTime13()!="" && reportFormValue.getTime13().charAt(reportFormValue.getTime13().length()-2) ==' '){
						//r2c14.setCellStyle(cellSt11);
						//r2c14.setCellValue(reportFormValue.getTime13());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime13(), table_content_blue);
					}
					else if(reportFormValue.getTime13()!="" && reportFormValue.getTime13().charAt(reportFormValue.getTime13().length()-1) ==' '){
						//r2c14.setCellStyle(cellSt);
						//r2c14.setCellValue(reportFormValue.getTime13());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime13(), table_content_red);
					}
					else{
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime13(), table_content);
					}
				
					if(reportFormValue.getTime14()!="" && reportFormValue.getTime14().charAt(reportFormValue.getTime14().length()-3) ==' '){
						//r2c15.setCellStyle(cellComp);
						//r2c15.setCellValue(reportFormValue.getTime14());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime14(), table_content_green);
					}
					else if(reportFormValue.getTime14()!="" && reportFormValue.getTime14().charAt(reportFormValue.getTime14().length()-2) ==' '){
						//r2c15.setCellStyle(cellSt11);
						//r2c15.setCellValue(reportFormValue.getTime14());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime14(), table_content_blue);
					}
					else if(reportFormValue.getTime14()!="" && reportFormValue.getTime14().charAt(reportFormValue.getTime14().length()-1) ==' '){
						//r2c15.setCellStyle(cellSt);
						//r2c15.setCellValue(reportFormValue.getTime14());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime14(), table_content_red);
					}
					else{
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime14(), table_content);
					}

					if(reportFormValue.getTime15()!="" && reportFormValue.getTime15().charAt(reportFormValue.getTime15().length()-3) ==' '){
						//r2c16.setCellStyle(cellComp);
						//r2c16.setCellValue(reportFormValue.getTime15());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime15(), table_content_green);
					}
					else if(reportFormValue.getTime15()!="" && reportFormValue.getTime15().charAt(reportFormValue.getTime15().length()-2) ==' '){
						//r2c16.setCellStyle(cellSt11);
						//r2c16.setCellValue(reportFormValue.getTime15());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime15(), table_content_blue);
					}
					else if(reportFormValue.getTime15()!="" && reportFormValue.getTime15().charAt(reportFormValue.getTime15().length()-1) ==' '){
						//r2c16.setCellStyle(cellSt);
						//r2c16.setCellValue(reportFormValue.getTime15());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime15(), table_content_red);
					}
					else{
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime15(), table_content);
					}
					if(reportFormValue.getTime16()!="" && reportFormValue.getTime16().charAt(reportFormValue.getTime16().length()-3) ==' '){
						//r2c17.setCellStyle(cellComp);
						//r2c17.setCellValue(reportFormValue.getTime16());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime16(), table_content_green);
					}
					else if(reportFormValue.getTime16()!="" && reportFormValue.getTime16().charAt(reportFormValue.getTime16().length()-2) ==' '){
						//r2c17.setCellStyle(cellSt11);
						//r2c17.setCellValue(reportFormValue.getTime16());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime16(), table_content_blue);
					}
					else if(reportFormValue.getTime16()!="" && reportFormValue.getTime16().charAt(reportFormValue.getTime16().length()-1) ==' '){
						//r2c17.setCellStyle(cellSt);
						//r2c17.setCellValue(reportFormValue.getTime16());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime16(), table_content_red);
					}
					else{
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime16(), table_content);
					}

					if(reportFormValue.getTime17()!="" && reportFormValue.getTime17().charAt(reportFormValue.getTime17().length()-3) ==' '){
						//r2c18.setCellStyle(cellComp);
						//r2c18.setCellValue(reportFormValue.getTime17());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime17(), table_content_green);
					}
					else if(reportFormValue.getTime17()!="" && reportFormValue.getTime17().charAt(reportFormValue.getTime17().length()-2) ==' '){
						//r2c18.setCellStyle(cellSt11);
						//r2c18.setCellValue(reportFormValue.getTime17());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime17(), table_content_blue);
					}
					else if(reportFormValue.getTime17()!="" && reportFormValue.getTime17().charAt(reportFormValue.getTime17().length()-1) ==' '){
						//r2c18.setCellStyle(cellSt);
						//r2c18.setCellValue(reportFormValue.getTime17());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime17(), table_content_red);
					}
					else{
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime17(), table_content);
					}
					
					if(reportFormValue.getTime18()!="" && reportFormValue.getTime18().charAt(reportFormValue.getTime18().length()-3) ==' '){
						//r2c19.setCellStyle(cellComp);
						//r2c19.setCellValue(reportFormValue.getTime18());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime18(), table_content_green);
					}
					else if(reportFormValue.getTime18()!="" && reportFormValue.getTime18().charAt(reportFormValue.getTime18().length()-2) ==' '){
						//r2c19.setCellStyle(cellSt11);
						//r2c19.setCellValue(reportFormValue.getTime18());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime18(), table_content_blue);
					}
					else if(reportFormValue.getTime18()!="" && reportFormValue.getTime18().charAt(reportFormValue.getTime18().length()-1) ==' '){
						//r2c19.setCellStyle(cellSt);
						//r2c19.setCellValue(reportFormValue.getTime18());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime18(), table_content_red);
					}
					else{
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime18(), table_content);
					}

					if(reportFormValue.getTime19()!="" && reportFormValue.getTime19().charAt(reportFormValue.getTime19().length()-3) ==' '){
						//r2c20.setCellStyle(cellComp);
						//r2c20.setCellValue(reportFormValue.getTime19());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime19(), table_content_green);
					}
					else if(reportFormValue.getTime19()!="" && reportFormValue.getTime19().charAt(reportFormValue.getTime19().length()-2) ==' '){
						//r2c20.setCellStyle(cellSt11);
						//r2c20.setCellValue(reportFormValue.getTime19());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime19(), table_content_blue);
					}
					else if(reportFormValue.getTime19()!="" && reportFormValue.getTime19().charAt(reportFormValue.getTime19().length()-1) ==' '){
						//r2c20.setCellStyle(cellSt);
						//r2c20.setCellValue(reportFormValue.getTime19());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime19(), table_content_red);
					}
					else{
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime19(), table_content);
					}

					if(reportFormValue.getTime20()!="" && reportFormValue.getTime20().charAt(reportFormValue.getTime20().length()-3) ==' '){
						//r2c21.setCellStyle(cellComp);
						//r2c21.setCellValue(reportFormValue.getTime20());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime20(), table_content_green);
					}
					else if(reportFormValue.getTime20()!="" && reportFormValue.getTime20().charAt(reportFormValue.getTime20().length()-2) ==' '){
						//r2c21.setCellStyle(cellSt11);
						//r2c21.setCellValue(reportFormValue.getTime20());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime20(), table_content_blue);
					}
					else if(reportFormValue.getTime20()!="" && reportFormValue.getTime20().charAt(reportFormValue.getTime20().length()-1) ==' '){
						//r2c21.setCellStyle(cellSt);
						//r2c21.setCellValue(reportFormValue.getTime20());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime20(), table_content_red);
					}
					else{
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime20(), table_content);
					}
					
					if(reportFormValue.getTime21()!="" && reportFormValue.getTime21().charAt(reportFormValue.getTime21().length()-3) ==' '){
						//r2c22.setCellStyle(cellComp);
						//r2c22.setCellValue(reportFormValue.getTime21());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime21(), table_content_green);
					}
					else if(reportFormValue.getTime21()!="" && reportFormValue.getTime21().charAt(reportFormValue.getTime21().length()-2) ==' '){
						//r2c22.setCellStyle(cellSt11);
						//r2c22.setCellValue(reportFormValue.getTime21());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime21(), table_content_blue);
					}
					else if(reportFormValue.getTime21()!="" && reportFormValue.getTime21().charAt(reportFormValue.getTime21().length()-1) ==' '){
						//r2c22.setCellStyle(cellSt);
						//r2c22.setCellValue(reportFormValue.getTime21());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime21(), table_content_red);
					}
					else{
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime21(), table_content);
					}
					
					if(reportFormValue.getTime22()!="" && reportFormValue.getTime22().charAt(reportFormValue.getTime22().length()-3) ==' '){
						//r2c23.setCellStyle(cellComp);
						//r2c23.setCellValue(reportFormValue.getTime22());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime22(), table_content_green);
					}
					else if(reportFormValue.getTime22()!="" && reportFormValue.getTime22().charAt(reportFormValue.getTime22().length()-2) ==' '){
						//r2c23.setCellStyle(cellSt11);
						//r2c23.setCellValue(reportFormValue.getTime22());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime22(), table_content_blue);
					}
					else if(reportFormValue.getTime22()!="" && reportFormValue.getTime22().charAt(reportFormValue.getTime22().length()-1) ==' '){
						//r2c23.setCellStyle(cellSt);
						//r2c23.setCellValue(reportFormValue.getTime22());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime22(), table_content_red);
					}
					else{
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime22(), table_content);
					}
					
					if(reportFormValue.getTime23()!="" && reportFormValue.getTime23().charAt(reportFormValue.getTime23().length()-3) ==' '){
						//r2c24.setCellStyle(cellComp);
						//r2c24.setCellValue(reportFormValue.getTime23());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime23(), table_content_green);
					}
					else if(reportFormValue.getTime23()!="" && reportFormValue.getTime23().charAt(reportFormValue.getTime23().length()-2) ==' '){
						//r2c24.setCellStyle(cellSt11);
						//r2c24.setCellValue(reportFormValue.getTime23());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime23(), table_content_blue);
					}
					else if(reportFormValue.getTime23()!="" && reportFormValue.getTime23().charAt(reportFormValue.getTime23().length()-1) ==' '){
						//r2c24.setCellStyle(cellSt);
						//r2c24.setCellValue(reportFormValue.getTime23());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime23(), table_content_red);
					}
					else{
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime23(), table_content);
					}

					if(reportFormValue.getTime24()!="" && reportFormValue.getTime24().charAt(reportFormValue.getTime24().length()-3) ==' '){
						//r2c25.setCellStyle(cellComp);
						//r2c25.setCellValue(reportFormValue.getTime24());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime24(), table_content_green);
					}
					else if(reportFormValue.getTime24()!="" && reportFormValue.getTime24().charAt(reportFormValue.getTime24().length()-2) ==' '){
						//r2c25.setCellStyle(cellSt11);
						//r2c25.setCellValue(reportFormValue.getTime24());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime24(), table_content_blue);
					}
					else if(reportFormValue.getTime24()!="" && reportFormValue.getTime24().charAt(reportFormValue.getTime24().length()-1) ==' '){
						//r2c25.setCellStyle(cellSt);
						//r2c25.setCellValue(reportFormValue.getTime24());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime24(), table_content_red);
					}
					else{
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime24(), table_content);
					}

					if(reportFormValue.getTime25()!="" && reportFormValue.getTime25().charAt(reportFormValue.getTime25().length()-3) ==' '){
						//r2c26.setCellStyle(cellComp);
						//r2c26.setCellValue(reportFormValue.getTime25());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime25(), table_content_green);
					}
					else if(reportFormValue.getTime25()!="" && reportFormValue.getTime25().charAt(reportFormValue.getTime25().length()-2) ==' '){
						//r2c26.setCellStyle(cellSt11);
						//r2c26.setCellValue(reportFormValue.getTime25());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime25(), table_content_blue);
					}
					else if(reportFormValue.getTime25()!="" && reportFormValue.getTime25().charAt(reportFormValue.getTime25().length()-1) ==' '){
						//r2c26.setCellStyle(cellSt);
						//r2c26.setCellValue(reportFormValue.getTime25());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime25(), table_content_red);
					}
					else{
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime25(), table_content);
					}
				
					if(reportFormValue.getTime26()!="" && reportFormValue.getTime26().charAt(reportFormValue.getTime26().length()-3) ==' '){
						//r2c27.setCellStyle(cellComp);
						//r2c27.setCellValue(reportFormValue.getTime26());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime26(), table_content_green);
					}
					else if(reportFormValue.getTime26()!="" && reportFormValue.getTime26().charAt(reportFormValue.getTime26().length()-2) ==' '){
						//r2c27.setCellStyle(cellSt11);
						//r2c27.setCellValue(reportFormValue.getTime26());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime26(), table_content_blue);
					}
					else if(reportFormValue.getTime26()!="" && reportFormValue.getTime26().charAt(reportFormValue.getTime26().length()-1) ==' '){
						//r2c27.setCellStyle(cellSt);
						//r2c27.setCellValue(reportFormValue.getTime26());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime26(), table_content_red);
					}
					else{
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime26(), table_content);
					}
	
					if(reportFormValue.getTime27()!="" && reportFormValue.getTime27().charAt(reportFormValue.getTime27().length()-3) ==' '){
						//r2c28.setCellStyle(cellComp);
						//r2c28.setCellValue(reportFormValue.getTime27());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime27(), table_content_green);
					}
					else if(reportFormValue.getTime27()!="" && reportFormValue.getTime27().charAt(reportFormValue.getTime27().length()-2) ==' '){
						//r2c28.setCellStyle(cellSt11);
						//r2c28.setCellValue(reportFormValue.getTime27());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime27(), table_content_blue);
					}
					else if(reportFormValue.getTime27()!="" && reportFormValue.getTime27().charAt(reportFormValue.getTime27().length()-1) ==' '){
						//r2c28.setCellStyle(cellSt);
						//r2c28.setCellValue(reportFormValue.getTime27());
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime27(), table_content_red);
					}
					else{
						addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime27(), table_content);
					}
					if(dayCountInMonth == 28){
						
						if(reportFormValue.getTime28()!="" && reportFormValue.getTime28().charAt(reportFormValue.getTime28().length()-3) ==' '){
							//r2c29.setCellStyle(cellComp);
							//r2c29.setCellValue(reportFormValue.getTime28());
							addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime28(), table_content_green);
						}
						else if(reportFormValue.getTime28()!="" && reportFormValue.getTime28().charAt(reportFormValue.getTime28().length()-2) ==' '){
							//r2c29.setCellStyle(cellSt11);
							//r2c29.setCellValue(reportFormValue.getTime28());
							addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime28(), table_content_blue);
						}
						else if(reportFormValue.getTime28()!="" && reportFormValue.getTime28().charAt(reportFormValue.getTime28().length()-1) ==' '){
							//r2c29.setCellStyle(cellSt);
							//r2c29.setCellValue(reportFormValue.getTime28());
							addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime28(), table_content_red);
						}
						else{
							addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime28(), table_content);
						}
					}
					else if(dayCountInMonth == 29){

						if(reportFormValue.getTime28()!="" && reportFormValue.getTime28().charAt(reportFormValue.getTime28().length()-3) ==' '){
							//r2c29.setCellStyle(cellComp);
							//r2c29.setCellValue(reportFormValue.getTime28());
							addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime28(), table_content_green);
						}
						else if(reportFormValue.getTime28()!="" && reportFormValue.getTime28().charAt(reportFormValue.getTime28().length()-2) ==' '){
							//r2c29.setCellStyle(cellSt11);
							//r2c29.setCellValue(reportFormValue.getTime28());
							addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime28(), table_content_blue);
						}
						else if(reportFormValue.getTime28()!="" && reportFormValue.getTime28().charAt(reportFormValue.getTime28().length()-1) ==' '){
							//r2c29.setCellStyle(cellSt);
							//r2c29.setCellValue(reportFormValue.getTime28());
							addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime28(), table_content_red);
						}
						else{
							addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime28(), table_content);
						}
						
						if(reportFormValue.getTime29()!="" && reportFormValue.getTime29().charAt(reportFormValue.getTime29().length()-3) ==' '){
							//r2c30.setCellStyle(cellComp);
							//r2c30.setCellValue(reportFormValue.getTime29());
							addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime29(), table_content_green);
						}
						else if(reportFormValue.getTime29()!="" && reportFormValue.getTime29().charAt(reportFormValue.getTime29().length()-2) ==' '){
							//r2c30.setCellStyle(cellSt11);
							//r2c30.setCellValue(reportFormValue.getTime29());
							addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime29(), table_content_blue);
						}
						else if(reportFormValue.getTime29()!="" && reportFormValue.getTime29().charAt(reportFormValue.getTime29().length()-1) ==' '){
							//r2c30.setCellStyle(cellSt);
							//r2c30.setCellValue(reportFormValue.getTime29());
							addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime29(), table_content_red);
						}
						else{
							addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime29(), table_content);
						}
					}
					else if(dayCountInMonth == 30){
				
						if(reportFormValue.getTime28()!="" && reportFormValue.getTime28().charAt(reportFormValue.getTime28().length()-3) ==' '){
							//r2c29.setCellStyle(cellComp);
							//r2c29.setCellValue(reportFormValue.getTime28());
							addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime28(), table_content_green);
						}
						else if(reportFormValue.getTime28()!="" && reportFormValue.getTime28().charAt(reportFormValue.getTime28().length()-2) ==' '){
							//r2c29.setCellStyle(cellSt11);
							//r2c29.setCellValue(reportFormValue.getTime28());
							addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime28(), table_content_blue);
						}
						else if(reportFormValue.getTime28()!="" && reportFormValue.getTime28().charAt(reportFormValue.getTime28().length()-1) ==' '){
							//r2c29.setCellStyle(cellSt);
							//r2c29.setCellValue(reportFormValue.getTime28());
							addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime28(), table_content_red);
						}
						else{
							addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime28(), table_content);
						}
						
						if(reportFormValue.getTime29()!="" && reportFormValue.getTime29().charAt(reportFormValue.getTime29().length()-3) ==' '){
							//r2c30.setCellStyle(cellComp);
							//r2c30.setCellValue(reportFormValue.getTime29());
							addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime29(), table_content_green);
						}
						else if(reportFormValue.getTime29()!="" && reportFormValue.getTime29().charAt(reportFormValue.getTime29().length()-2) ==' '){
							//r2c30.setCellStyle(cellSt11);
							//r2c30.setCellValue(reportFormValue.getTime29());
							addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime29(), table_content_blue);
						}
						else if(reportFormValue.getTime29()!="" && reportFormValue.getTime29().charAt(reportFormValue.getTime29().length()-1) ==' '){
							//r2c30.setCellStyle(cellSt);
							//r2c30.setCellValue(reportFormValue.getTime29());
							addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime29(), table_content_red);
						}
						else{
							addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime29(), table_content);
						}
						
						if(reportFormValue.getTime30()!="" && reportFormValue.getTime30().charAt(reportFormValue.getTime30().length()-3) ==' '){
							//r2c31.setCellStyle(cellComp);
							//r2c31.setCellValue(reportFormValue.getTime30());
							addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime30(), table_content_green);
						}
						else if(reportFormValue.getTime30()!="" && reportFormValue.getTime30().charAt(reportFormValue.getTime30().length()-2) ==' '){
							//r2c31.setCellStyle(cellSt11);
							//r2c31.setCellValue(reportFormValue.getTime30());
							addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime30(), table_content_blue);
						}
						else if(reportFormValue.getTime30()!="" && reportFormValue.getTime30().charAt(reportFormValue.getTime30().length()-1) ==' '){
							//r2c31.setCellStyle(cellSt);
							//r2c31.setCellValue(reportFormValue.getTime30());
							addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime30(), table_content_red);
						}
						else{
							addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime30(), table_content);
						}
					}
					else if( dayCountInMonth == 31){
						if(reportFormValue.getTime28()!="" && reportFormValue.getTime28().charAt(reportFormValue.getTime28().length()-3) ==' '){
							//r2c29.setCellStyle(cellComp);
							//r2c29.setCellValue(reportFormValue.getTime28());
							addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime28(), table_content_green);
						}
						else if(reportFormValue.getTime28()!="" && reportFormValue.getTime28().charAt(reportFormValue.getTime28().length()-2) ==' '){
							//r2c29.setCellStyle(cellSt11);
							//r2c29.setCellValue(reportFormValue.getTime28());
							addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime28(), table_content_blue);
						}
						else if(reportFormValue.getTime28()!="" && reportFormValue.getTime28().charAt(reportFormValue.getTime28().length()-1) ==' '){
							//r2c29.setCellStyle(cellSt);
							//r2c29.setCellValue(reportFormValue.getTime28());
							addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime28(), table_content_red);
						}
						else{
							addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime28(), table_content);
						}
						
						if(reportFormValue.getTime29()!="" && reportFormValue.getTime29().charAt(reportFormValue.getTime29().length()-3) ==' '){
							//r2c30.setCellStyle(cellComp);
							//r2c30.setCellValue(reportFormValue.getTime29());
							addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime29(), table_content_green);
						}
						else if(reportFormValue.getTime29()!="" && reportFormValue.getTime29().charAt(reportFormValue.getTime29().length()-2) ==' '){
							//r2c30.setCellStyle(cellSt11);
							//r2c30.setCellValue(reportFormValue.getTime29());
							addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime29(), table_content_blue);
						}
						else if(reportFormValue.getTime29()!="" && reportFormValue.getTime29().charAt(reportFormValue.getTime29().length()-1) ==' '){
							//r2c30.setCellStyle(cellSt);
							//r2c30.setCellValue(reportFormValue.getTime29());
							addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime29(), table_content_red);
						}
						else{
							addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime29(), table_content);
						}
						
						if(reportFormValue.getTime30()!="" && reportFormValue.getTime30().charAt(reportFormValue.getTime30().length()-3) ==' '){
							//r2c31.setCellStyle(cellComp);
							//r2c31.setCellValue(reportFormValue.getTime30());
							addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime30(), table_content_green);
						}
						else if(reportFormValue.getTime30()!="" && reportFormValue.getTime30().charAt(reportFormValue.getTime30().length()-2) ==' '){
							//r2c31.setCellStyle(cellSt11);
							//r2c31.setCellValue(reportFormValue.getTime30());
							addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime30(), table_content_blue);
						}
						else if(reportFormValue.getTime30()!="" && reportFormValue.getTime30().charAt(reportFormValue.getTime30().length()-1) ==' '){
							//r2c31.setCellStyle(cellSt);
							//r2c31.setCellValue(reportFormValue.getTime30());
							addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime30(), table_content_red);
						}
						else{
							addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime30(), table_content);
						}
						
						if(reportFormValue.getTime31()!="" && reportFormValue.getTime31().charAt(reportFormValue.getTime31().length()-3) ==' '){
							//r2c32.setCellStyle(cellComp);
							//r2c32.setCellValue(reportFormValue.getTime31());
							addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime31(), table_content_green);
						}
						else if(reportFormValue.getTime31()!="" && reportFormValue.getTime31().charAt(reportFormValue.getTime31().length()-2) ==' '){
							//r2c32.setCellStyle(cellSt11);
							//r2c32.setCellValue(reportFormValue.getTime31());
							addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime31(), table_content_blue);
						}
						else if(reportFormValue.getTime31()!="" && reportFormValue.getTime31().charAt(reportFormValue.getTime31().length()-1) ==' '){
							//r2c32.setCellStyle(cellSt);
							//r2c32.setCellValue(reportFormValue.getTime31());
							addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime31(), table_content_red);
						}
						else{
							addLabel(excelSheet, col_count++, row_count, reportFormValue.getTime31(), table_content);
						}
					}
					
					if(reportFormValue.getTotalTime()!=null){
						addNumber(excelSheet, col_count++, row_count, reportFormValue.getTotalTime(), table_content);
					}else{
						addLabel(excelSheet, col_count++, row_count, "", table_content);
					}
					row_count++;
				}
				response.setContentType("application/vnd.ms-excel");
				Calendar calendar = Calendar.getInstance();
				DateFormat df1 = new SimpleDateFormat("dd_MMMM_yy_HH-mm");
				String file_name = df1.format(calendar.getTime());
				response.setHeader("Content-Disposition", "inline; filename=" +"TaskDetailsReport_"+file_name+".xls");				
				workbook.write();
				workbook.close();			
				out.flush();
				out.close();
			}catch(Exception e){
				e.printStackTrace();
				throw new Exception();
			}
		}
		else
			throw new Exception();
	}
	public void exportAllUserAccessMapping(ActionMapping map,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		//get the resources that are allocated for a particular user 
		FileOutputStream fos = null;
		HttpSession sess =  request.getSession();
		if(sess.getAttribute("userName")!=null){
			try{
				ReportService reportservice=ReportServiceFactory.getReportService();
				//get all user access mapping
				List<List<Report>> allocationMappingAllUsers=reportservice.getUserReportAllocation();
				//get the list of users with the list of resources
				List<Report> updatedAllocationListAllUser=new ArrayList<Report>();
				Report allocationListAllUser=null;
				Iterator<List<Report>> allocationAllUserIterator= allocationMappingAllUsers.iterator();
				while(allocationAllUserIterator.hasNext()){
					List<Report> allocationListUser=allocationAllUserIterator.next();
					allocationListAllUser=new Report();
					Iterator<Report> allocatedIterator=allocationListUser.iterator();
					int counter=1;
					List<Report> updatedAllocatedList=new ArrayList<Report>();
					while(allocatedIterator.hasNext()){
						Report report=allocatedIterator.next();
						if(counter==1){
							report.setParentUserName(report.getParentUserName());
						}
						updatedAllocatedList.add(report);
						counter++;
					}
					allocationListAllUser.setAllUserAllocationList(updatedAllocatedList);
					updatedAllocationListAllUser.add(allocationListAllUser);
				}
				//Write Code for generate excel report for access mapping for all user
				Workbook wb = new XSSFWorkbook();
				//CreationHelper createHelper = wb.getCreationHelper();	
				Sheet sheet = wb.createSheet("AllUserAccessMappingDetails");
				//generate the header row
				Row row1 = sheet.createRow(0);
				Cell r1c1 = row1.createCell((short) 0);
				r1c1.setCellValue("User");
				Cell r1c2 = row1.createCell((short) 1);
				r1c2.setCellValue("Access To");
				int count=1;
				Iterator<Report> usermappingIterator=updatedAllocationListAllUser.iterator();	
				while(usermappingIterator.hasNext()){
					Report report=usermappingIterator.next();
					List<Report> userMappingList=report.getAllUserAllocationList();
					if(userMappingList!=null&&userMappingList.size()>0){
						Iterator<Report> userAccessMappingIterator=userMappingList.iterator();
						while(userAccessMappingIterator.hasNext()){
							Row row = sheet.createRow(count);
							Report report1=userAccessMappingIterator.next();
							Cell r2c1 = row.createCell((short) 0);
							r2c1.setCellValue(report1.getParentUserName());
							Cell r2c2 = row.createCell((short) 1);
							r2c2.setCellValue(report1.getUserName());
							count++;
						}
						count++;
					}
				}	// To write out the workbook into a file we need to create an output
				response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				Calendar cal = Calendar.getInstance();
				DateFormat df = new SimpleDateFormat("dd_MMMM_yy_HH-mm");
				String file_name = df.format(cal.getTime());
				response.setHeader("Content-Disposition", "inline; filename=" +"AllUserAccessMapping_"+file_name+".xlsx");
				OutputStream out = response.getOutputStream();
				wb.write(out);
				out.flush();
				out.close();
			}catch(Exception e){
				e.printStackTrace();
				throw new Exception();
			}
			finally {
				if (fos != null) {
					try {
						fos.flush();
						fos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		else
			throw new Exception();
		//return map.findForward("getDetailedTimesheet");
	}	
	//Added for User contact info	
	public ActionForward getContactNumbers(ActionMapping map,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{     
		logger.info(" getContactNumbersFromRepotAction ");
		HttpSession sess =  request.getSession();
		if(sess.getAttribute("userName")!=null){
			try{
				UserService userService = Factory.getUserService();
				String reportMonthYear=request.getParameter("month-settings");
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				int admin_id = userService.getAdminUserId();
				ReportForm reportForm = (ReportForm) form;	
				String location = reportForm.getLocation();			
				int clientResourceId = reportForm.getClient_resource_ids();
				
				Calendar cal = Calendar.getInstance();				
				cal.setTime(Calendar.getInstance().getTime()); // Current Date 
				cal.setTime(df.parse(reportMonthYear));
				cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
				List<UserForm> allocatedUserList = userService.getAllocatedResourcesBasedOnStartAndExitDate(admin_id, cal);				
				sess.removeAttribute("selectedDate");							
				// Getting the list of Contact detail
				List<Object> listContact = userService.getContactDetails(allocatedUserList);					
				request.setAttribute("listContact",listContact);
				sess.setAttribute("listContact",listContact);
				sess.setAttribute("selectedDate", reportMonthYear);
				request.setAttribute("reportType", "dashBoardReport");
				request.setAttribute("monthYearString",reportMonthYear);
				request.setAttribute("locationDetail", location);
				request.setAttribute("selectedClientResource", clientResourceId);
			}
			catch(Exception e){
				e.printStackTrace();
				throw new Exception();
			}
			return map.findForward("getDetailedTimesheet");
		}
		else
			throw new Exception();
	}
	// Excel for User Contact Information.
	public void generateContactReportExcel(ActionMapping map,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		HttpSession sess =  request.getSession();
		if(sess.getAttribute("userName")!=null){
			try{
				List<Object> listContact = (List<Object>)sess.getAttribute("listContact");
				request.setAttribute("listContact",listContact);				
				OutputStream out = response.getOutputStream();				
				WorkbookSettings wbSettings = new WorkbookSettings();
				wbSettings.setLocale(new Locale("en", "EN"));				
				WritableWorkbook workbook = jxl.Workbook.createWorkbook(out, wbSettings);			
				workbook.createSheet("Contact Details Sheet", 0);			
				WritableSheet excelSheet = workbook.getSheet(0);				
				excelSheet.setColumnView(0, 20);
				excelSheet.setColumnView(1, 20);
				excelSheet.setColumnView(2, 20);
				excelSheet.setColumnView(3, 20);
				excelSheet.setColumnView(4, 20);

				int row = 0; int heightInPoints = 20*20;
				excelSheet.setRowView(row, heightInPoints);	 

				//Getting different fonts. 
				getColorFonts();

				addCaption(excelSheet, 0, 0, "Resource", table_header);
				addCaption(excelSheet, 1, 0, "Team", table_header);
				addCaption(excelSheet, 2, 0, "Apollo Manager", table_header);
				addCaption(excelSheet, 3, 0, "Mobile Number", table_header);
				addCaption(excelSheet, 4, 0, "Skype ID", table_header);

				Iterator itr = listContact.iterator();
				int count = 1;
				while(itr.hasNext()){
					List list = (List)itr.next();								   								 							        
					if(list.size()==5){		
						String[] str = (String[])list.get(1);
						String contact1 = str[0];
						String contact2 = "";
						if(str[1]!=null){
							contact2 = str[1];
						}
						String username =(String) list.get(0);
						String team = (String)list.get(2);
						String manager =(String) list.get(3);
						String skype =(String) list.get(4); 

						excelSheet.setRowView(count, 30*20);
						addLabel(excelSheet, 0, count, username, table_content);
						addLabel(excelSheet, 1, count, team, table_content);
						addLabel(excelSheet, 2, count, manager, table_content);
						if(contact2 == ""){
							addLabel(excelSheet, 3, count, contact1, table_content);
						}
						else{
							addLabel(excelSheet, 3, count, contact1+"\n"+contact2, table_content);
						}
						addLabel(excelSheet, 4, count, skype, table_content);				
					}									   							       
					else if(list.size()==1){
						excelSheet.setRowView(count, 30*20);
						addLabel(excelSheet, 0, count, (String)list.get(0), table_content);
						addLabel(excelSheet, 1, count, "---", table_content);
						addLabel(excelSheet, 2, count, "---", table_content);
						addLabel(excelSheet, 3, count, "---", table_content);
						addLabel(excelSheet, 4, count, "---", table_content);
					}
					count++;
				}	
				excelSheet.setRowView(count, 1);
				Label label = new Label(0, count, "");
				excelSheet.addCell(label);				
				//response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				response.setContentType("application/vnd.ms-excel");
				Calendar cal = Calendar.getInstance();
				DateFormat df = new SimpleDateFormat("dd_MMMM_yy_HH-mm");
				String file_name = df.format(cal.getTime());
				response.setHeader("Content-Disposition", "inline; filename=" +"ContactDetails_"+file_name+".xls");	
				workbook.write();
				workbook.close();			
				out.flush();
				out.close();
			}
			catch(Exception e){
				e.printStackTrace();
				throw new Exception();
			}
		}
		else
			throw new Exception();
	}	

	private void addCaption(WritableSheet sheet, int column, int row, String s, WritableCellFormat table_header) throws RowsExceededException, WriteException {
		Label label;
		label = new Label(column, row, s, table_header);
		sheet.addCell(label);
	}
	private void addNumber(WritableSheet sheet, int column, int row, Double double_val, WritableCellFormat table_content) throws WriteException, RowsExceededException {
		Number number;
		number = new Number(column, row, double_val, table_content);
		sheet.addCell(number);
	}
	private void addLabel(WritableSheet sheet, int column, int row, String s, WritableCellFormat table_content) throws WriteException, RowsExceededException {
		Label label;
		label = new Label(column, row, s, table_content);
		sheet.addCell(label);
	}

	// Added for Team Wise Report for Account Manager
	public ActionForward getResourceTeamWise(ActionMapping map,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		HttpSession s1 =  request.getSession();
		if(s1.getAttribute("userName")!=null){
			try{
				int userId=Integer.parseInt(s1.getAttribute("user_id").toString());
				ReportForm repForm = (ReportForm) form;
				UserService userService;
				userService=Factory.getUserService();
				List<UserForm> allocatedUserList=userService.getAllocatedResourcesTeamWise(userId, repForm);
				List<String> conList = userService.getTeamFromContact();
				request.setAttribute("userList",allocatedUserList);  // Changes Required Here!!
				s1.setAttribute("conListUpdate",conList);
				request.setAttribute("reportType", "noReport");
			}
			catch(Exception e){
				e.printStackTrace();
				throw new Exception();
			}						
			return map.findForward("getUserReportDetails");
		}
		else
			throw new Exception();
	}	
	// Added for Exception DashBoard for Account Manager
	public ActionForward getExceptionDashboard(ActionMapping map,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		logger.info("You Are Inside getExceptionDashboard method of ReportAction");
		HttpSession s1 = request.getSession();
		if(s1.getAttribute("userName")!=null){
			String year="";
			String month="";
			List<UserForm> selectedUserList = new ArrayList<UserForm>();
			try{				
				int userId=Integer.parseInt(s1.getAttribute("user_id").toString());
				UserService userService;
				userService=Factory.getUserService();
				String reportMonthYear=request.getParameter("month-settings");
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				Calendar cal = Calendar.getInstance();
				cal.setTime(df.parse(reportMonthYear));
				cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH)); 
				List<UserForm> allocatedUserList=userService.getAllocatedResourcesBasedOnStartAndExitDate(userId, cal);
				s1.removeAttribute("selectedDate");
				int i1 = reportMonthYear.length();
				int j = reportMonthYear.indexOf("/");
				int k = reportMonthYear.lastIndexOf("/");
				month = reportMonthYear.substring(j+1,k);
				year = reportMonthYear.substring(k+1,i1);			
				ReportService reportservice=ReportServiceFactory.getReportService();
				List<ReportForm> reportList = null;
				reportList = reportservice.getExceptionDashboard(year,month,allocatedUserList);				
				s1.setAttribute("reportListForExceptionBoard", reportList);
				ReportForm reportForm=(ReportForm)form;
				reportForm.setReportDataList(reportList);
				request.setAttribute("reportListException", reportList);
				List<String> dayList=new ArrayList<String>();
				Calendar calendar = Calendar.getInstance();
				int date = 1;
				calendar.set(Integer.parseInt(year), Integer.parseInt(month)-1, date);
				int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
				for(int i=1;i<=days;i++){
					calendar.set(Calendar.DAY_OF_MONTH, i);
					dayList.add(calendar.getTime().toString().substring(0,3));
				}				
				List<String> dayListForDates = new ArrayList<String>();
				DateFormat df2 = new SimpleDateFormat("M/d");
				for(int i=1;i<=days;i++){
					cal.set(Calendar.DAY_OF_MONTH,i);
					if(!(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)){
						dayListForDates.add(df2.format(cal.getTime()));
					}
					else{
						dayListForDates.add(df2.format(cal.getTime())+" ");
					}
				} 				
				request.setAttribute("dayList", dayList);
				request.setAttribute("dayListForDates", dayListForDates);
				s1.setAttribute("dayList", dayList);
				s1.setAttribute("dayListForDates", dayListForDates);
				s1.setAttribute("selectedDate", reportMonthYear);
				request.setAttribute("selectedUserList",selectedUserList);
			}
			catch(Exception e){
				e.printStackTrace();
				throw new Exception();
			}
			return map.findForward("displayReport");
		}
		else
			throw new Exception();
	}	

	public void generateReportExceptionDashBoard(ActionMapping map,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		HttpSession sess =  request.getSession();
		if(sess.getAttribute("userName")!=null){
			FileOutputStream fos = null;
			try{
				List<String> dayList = (List)sess.getAttribute("dayList");
				List<String> dayListForDates = (List)sess.getAttribute("dayListForDates");
				int dayCountInMonth = 0 ;
				if(dayListForDates != null){
					dayCountInMonth = dayListForDates.size();
				}
				List<ReportForm> reportList = (List<ReportForm>) sess.getAttribute("reportListForExceptionBoard");
				ReportForm reportForm=(ReportForm)form;
				reportForm.setReportDataList(reportList);
				Workbook wb = new XSSFWorkbook();
				//CreationHelper createHelper = wb.getCreationHelper();
				Iterator<ReportForm> timeSheetDashBoardIterator=reportList.iterator();	
				/*HSSFSheet sheet = workbook.createSheet("Sheet1");*/
				Sheet sheet = wb.createSheet("Exception DashBoard Report");
				CellStyle cellSt =  wb.createCellStyle();			
				Font font = wb.createFont();			
				font.setColor(Font.COLOR_RED);			
				cellSt.setFont(font); 
				CellStyle cellSt1 =  wb.createCellStyle();			
				cellSt1.setFillForegroundColor(IndexedColors.PINK.getIndex());
				cellSt1.setFillPattern(CellStyle.FINE_DOTS);
				/*setColor(HSSFColor.GREEN.index);*/                                                 /*r1c1.setCellStyle(cellSt);*/
				Row row1 = sheet.createRow((short) 0);
				Cell r1c1 = row1.createCell(0);			
				r1c1.setCellValue("Username");
				Iterator<String> itr = dayList.iterator();
				Iterator<String> itrForDateFormat = dayListForDates.iterator();
				int rowCount = 1;
				while (itr.hasNext() && itr.hasNext()) {
					String string = (String) itr.next();
					String dateValue = (String) itrForDateFormat.next();
					Cell r1c2 = row1.createCell((short) rowCount);
					if(string.equalsIgnoreCase("Sun")||string.equalsIgnoreCase("Sat")){				
						r1c2.setCellValue(dateValue);					
						r1c2.setCellStyle(cellSt1);
					}
					else{					
						r1c2.setCellValue(dateValue);
					}
					rowCount++;
				}
				int count=1;			
				XSSFFont font1 = (XSSFFont)wb.createFont();
				CellStyle cellSt11 =  wb.createCellStyle();
				font1.setColor(new XSSFColor(new java.awt.Color(0, 0, 255)));
				cellSt11.setFont(font1); 			
				while(timeSheetDashBoardIterator.hasNext()){
					ReportForm reportFormValue=timeSheetDashBoardIterator.next();
					Row row = sheet.createRow((short)count);
					Cell r2c1 = row.createCell((short) 0);
					r2c1.setCellValue(reportFormValue.getUserName());
					Cell r2c2 = row.createCell((short) 1);
					r2c2.setCellValue(reportFormValue.getTime1());
					Cell r2c3 = row.createCell((short) 2);
					r2c3.setCellValue(reportFormValue.getTime2());
					Cell r2c4 = row.createCell((short) 3);
					r2c4.setCellValue(reportFormValue.getTime3());
					Cell r2c5 = row.createCell((short) 4);
					r2c5.setCellValue(reportFormValue.getTime4());
					Cell r2c6 = row.createCell((short) 5);
					r2c6.setCellValue(reportFormValue.getTime5());
					Cell r2c7 = row.createCell((short) 6);
					r2c7.setCellValue(reportFormValue.getTime6());
					Cell r2c8 = row.createCell((short) 7);
					r2c8.setCellValue(reportFormValue.getTime7());
					Cell r2c9= row.createCell((short) 8);
					r2c9.setCellValue(reportFormValue.getTime8());
					Cell r2c10 = row.createCell((short) 9);
					r2c10.setCellValue(reportFormValue.getTime9());
					Cell r2c11= row.createCell((short) 10);
					r2c11.setCellValue(reportFormValue.getTime10());
					Cell r2c12 = row.createCell((short) 11);
					r2c12.setCellValue(reportFormValue.getTime11());
					Cell r2c13 = row.createCell((short) 12);
					r2c13.setCellValue(reportFormValue.getTime12());
					Cell r2c14= row.createCell((short)13);
					r2c14.setCellValue(reportFormValue.getTime13());
					Cell r2c15 = row.createCell((short) 14);
					r2c15.setCellValue(reportFormValue.getTime14());
					Cell r2c16 = row.createCell((short) 15);
					r2c16.setCellValue(reportFormValue.getTime15());
					Cell r2c17 = row.createCell((short) 16);
					r2c17.setCellValue(reportFormValue.getTime16());
					Cell r2c18= row.createCell((short) 17);
					r2c18.setCellValue(reportFormValue.getTime17());
					Cell r2c19 = row.createCell((short)18);
					r2c19.setCellValue(reportFormValue.getTime18());
					Cell r2c20 = row.createCell((short) 19);
					r2c20.setCellValue(reportFormValue.getTime19());
					Cell r2c21 = row.createCell((short) 20);
					r2c21.setCellValue(reportFormValue.getTime20());
					Cell r2c22 = row.createCell((short) 21);
					r2c22.setCellValue(reportFormValue.getTime21());
					Cell r2c23 = row.createCell((short) 22);
					r2c23.setCellValue(reportFormValue.getTime22());
					Cell r2c24 = row.createCell((short) 23);
					r2c24.setCellValue(reportFormValue.getTime23());
					Cell r2c25 = row.createCell((short) 24);
					r2c25.setCellValue(reportFormValue.getTime24());
					Cell r2c26 = row.createCell((short) 25);
					r2c26.setCellValue(reportFormValue.getTime25());
					Cell r2c27 = row.createCell((short) 26);
					r2c27.setCellValue(reportFormValue.getTime26());
					Cell r2c28 = row.createCell((short) 27);
					r2c28.setCellValue(reportFormValue.getTime27());
					if(dayCountInMonth == 28){
						Cell r2c29 = row.createCell((short) 28);
						r2c29.setCellValue(reportFormValue.getTime28());						
					}else if(dayCountInMonth ==29){
						Cell r2c29 = row.createCell((short) 28);
						r2c29.setCellValue(reportFormValue.getTime28());
						Cell r2c30 = row.createCell((short) 29);
						r2c30.setCellValue(reportFormValue.getTime29());
					}else if(dayCountInMonth == 30){
						Cell r2c29 = row.createCell((short) 28);
						r2c29.setCellValue(reportFormValue.getTime28());
						Cell r2c30 = row.createCell((short) 29);
						r2c30.setCellValue(reportFormValue.getTime29());
						Cell r2c31 = row.createCell((short) 30);					
						r2c31.setCellValue(reportFormValue.getTime30());
					}else{
						Cell r2c29 = row.createCell((short) 28);
						r2c29.setCellValue(reportFormValue.getTime28());
						Cell r2c30 = row.createCell((short) 29);
						r2c30.setCellValue(reportFormValue.getTime29());
						Cell r2c31 = row.createCell((short) 30);					
						r2c31.setCellValue(reportFormValue.getTime30());
						Cell r2c32 = row.createCell((short) 31);
						r2c32.setCellValue(reportFormValue.getTime31());
					}					
					count++;
				}				
				response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				Calendar cal = Calendar.getInstance();
				DateFormat df = new SimpleDateFormat("dd_MMMM_yy_HH-mm");
				String file_name = df.format(cal.getTime());
				response.setHeader("Content-Disposition", "inline; filename=" +"ExceptionDashboardReport_"+file_name+".xlsx");
				OutputStream out = response.getOutputStream();
				wb.write(out);
				out.flush();
				out.close();
			}catch(Exception e){
				e.printStackTrace();
				throw new Exception();
			}
			finally {
				if (fos != null) {
					try {
						fos.flush();
						fos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		else
			throw new Exception();
	}

	public ActionForward getFaquestion(ActionMapping map,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		HttpSession s1 =  request.getSession();
		s1.removeAttribute("selectedDate");
		s1.removeAttribute("conList");
		s1.removeAttribute("userList");
		s1.removeAttribute("conListUpdate");
		if(s1.getAttribute("userName")!=null){				
			return map.findForward("faQuestion");
		}
		else
			throw new Exception();
	}	

	public ActionForward getUserReportDetailsMonthWise(ActionMapping map,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		HttpSession s1 =  request.getSession();
		if(s1.getAttribute("userName")!=null){	
			try{
				int userId=Integer.parseInt(s1.getAttribute("user_id").toString());				
				UserService userService;
				userService=Factory.getUserService();
				String reportMonthYear=request.getParameter("month-set");
				DateFormat df = new SimpleDateFormat("M/yyyy");
				Calendar cal = Calendar.getInstance();
				cal.setTime(df.parse(reportMonthYear));
				cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH)); 
				List<UserForm> allocatedUserList=userService.getAllocatedResourcesBasedOnStartAndExitDate(userId, cal);			
				String role = userService.getUserRoleId(userId);				
				if(role!=null && role.equalsIgnoreCase("1005")){
					List<String> conList = userService.getTeamFromContact();				
					s1.setAttribute("conList",conList);
				}
				DateFormat dformat = new SimpleDateFormat("MM/yyyy");
				s1.setAttribute("selectedDate", "20/"+dformat.format(dformat.parse(reportMonthYear)));
				s1.setAttribute("userList",allocatedUserList);
				request.setAttribute("reportType", "noReport");
			}
			catch(Exception e){
				e.printStackTrace();
				throw new Exception();
			}			
			return map.findForward("getUserReportDetails");
		}
		else
			throw new Exception();
	}	
	public ActionForward getResourceLocationWise(ActionMapping map,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		HttpSession s1 =  request.getSession();
		UserService userService;
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		List<UserForm> allocatedUserList = null;
		if(s1.getAttribute("userName") != null){	
			try{
				int userId = Integer.parseInt(s1.getAttribute("user_id").toString());
				ReportForm reportForm = (ReportForm) form;	
				String location = reportForm.getLocation();
				String monthYear = request.getParameter("month-settings");	
				int client_resource_id = reportForm.getClient_resource_ids();
				userService=Factory.getUserService();
				if(location != null && monthYear != null){				
					cal.setTime(df.parse(monthYear));
					cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
					if(client_resource_id == 0){
						allocatedUserList = userService.getAllocatedResourcesBasedOnExitDateAndLocation(userId, cal, location);	
					}else{
						allocatedUserList = userService.getAllocatedResourcesBasedOnExitDateAndLocation(client_resource_id, cal, location);	
					}						
					s1.setAttribute("userList",allocatedUserList);
					request.setAttribute("locationDetail", location);
					request.setAttribute("selectedClientResource", client_resource_id);
				} 
				else{
					throw new Exception();
				}
			}
			catch(Exception e){
				e.printStackTrace();
				throw new Exception();
			}			
			return map.findForward("getUserReportDetails");
		}
		else
			throw new Exception();
	}

	@Override
	public void execute(JobExecutionContext context)throws JobExecutionException{	
		// Method for generating Report on every friday for current Week Time entry for all Resource Mapped under Raghavi.
		try{
			File f1 = readFileFromGlassfishDomainConfigFolder("getInformation.properties");
			logger.info("check file exist or not from Report Action weekly remindar "+f1.exists());
			if(f1.exists()){
				Properties prop = new Properties();
				prop.load(new FileInputStream(f1));
				UserService userService=Factory.getUserService();
				int userId = userService.getAdminUserId();
				Calendar cal = Calendar.getInstance();								
				List<UserForm> allocatedUserList = userService.getAllocatedResourcesDetailsForRemindarMailBasedOnRelievingDate(userId, cal);
				ReportService reportservice=ReportServiceFactory.getReportService();				
				List<ReportForm> reportList = reportservice.getResourceDetailWhoMissedEntry(allocatedUserList);
				List<String> listForLog = new ArrayList<String>();
				String message = null;
				if(!reportList.isEmpty()){
					message = reportservice.sendWeeklyReminderMail(reportList, cal, prop);
					listForLog.add(message);
				}
				logger.info("Printing list containing weekly mailing deatils  : "+listForLog);
			}
		}
		catch(Exception e){
			StringWriter stack = new StringWriter();
			e.printStackTrace(new PrintWriter(stack));
			logger.error("Exception from Report Action while running weekly reminder mailing: "+stack.toString());
		}
	}

	private static File readFileFromGlassfishDomainConfigFolder( final String fileName ) throws FileNotFoundException{
		// Instance Root folder
		final String instanceRoot = System.getProperty( glassfishInstanceRootPropertyName );	    
		logger.info(" instanceRoot Value from ReportAction "+instanceRoot);    
		if (instanceRoot == null){
			throw new FileNotFoundException( "Cannot find Glassfish instanceRoot. Is the com.sun.aas.instanceRoot system property set?" );
		}
		// Instance Root + /config folder
		File configurationFolder = new File( instanceRoot + File.separator + glassfishDomainConfigurationFolderName );
		logger.info("configurationFolder from ReportAction "+configurationFolder);	    
		File configFile = new File( configurationFolder, fileName );
		// return the given file
		return configFile;
	}
	public ActionForward getDefaulterListDetails(ActionMapping map,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		logger.info("Printing from Report Action getDefaulterListDetails Method.");
		HttpSession s1 = request.getSession();
		ReportService reportservice = null; 
		if(s1.getAttribute("userName")!=null){
			try{
				ReportForm reportForm = (ReportForm) form;	
				String location = reportForm.getLocation();
				int clientResourceId = reportForm.getClient_resource_ids();
				String reportMonthYear = request.getParameter("month-settings");
				String[] allocatedResource=request.getParameterValues("userId");		
				s1.removeAttribute("selectedDate");				
				/*Added for invalid request*/
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");			
				Calendar cal = Calendar.getInstance();
				cal.setTime(df.parse(reportMonthYear));					
				Calendar current_time = Calendar.getInstance();					
				cal.set(Calendar.DAY_OF_MONTH, current_time.get(Calendar.DAY_OF_MONTH));
				if((current_time.get(Calendar.MONTH) == cal.get(Calendar.MONTH) && current_time.get(Calendar.DAY_OF_MONTH) == 1 ) || (cal.get(Calendar.MONTH) > current_time.get(Calendar.MONTH)) ){
					request.setAttribute("invalidRequest", "invalidRequest");
				}
				/*Update ends here!*/
				else{
					reportservice = ReportServiceFactory.getReportService();
					List<ReportForm> listOfDefaulters = new ArrayList<ReportForm>();
					listOfDefaulters = reportservice.getDefaulterListDetails(allocatedResource, cal);
					s1.setAttribute("listOfDefaultersForExcel", listOfDefaulters); // Setted for excelsheet.
					request.setAttribute("defaulterListDetails", listOfDefaulters);
				}				
				request.setAttribute("locationDetail", location);
				request.setAttribute("selectedClientResource", clientResourceId);
				s1.setAttribute("selectedDate", reportMonthYear);	
			}
			catch (Exception e) {
				logger.error(e);
				throw new Exception();
			}
		}
		else{
			throw new Exception();
		}
		return map.findForward("getDetailedTimesheet");
	}

	public void generateDefaulterReportExcel(ActionMapping map,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		//get the defaulter ExcelSheet report.
		HttpSession sess = request.getSession();
		if(sess.getAttribute("userName")!=null){
			try{
				List<ReportForm> defaulterReportList = (List<ReportForm>)sess.getAttribute("listOfDefaultersForExcel");
				if(defaulterReportList != null && !defaulterReportList.isEmpty()){
					OutputStream out = response.getOutputStream();				
					WorkbookSettings wbSettings = new WorkbookSettings();
					wbSettings.setLocale(new Locale("en", "EN"));				
					WritableWorkbook workbook = jxl.Workbook.createWorkbook(out, wbSettings);			
					workbook.createSheet("Defaulter Resource Report", 0);			
					WritableSheet excelSheet = workbook.getSheet(0);				
					excelSheet.setColumnView(0, 35);
					excelSheet.setColumnView(1, 35);

					int row = 0; int heightInPoints = 20*20;
					excelSheet.setRowView(row, heightInPoints);	 

					//Getting different fonts. 
					getColorFonts();

					addCaption(excelSheet, 0, 0, "Resource Name", table_header);
					addCaption(excelSheet, 1, 0, "Period", table_header);				

					Iterator<ReportForm> itr = defaulterReportList.iterator();
					int count = 1;
					while (itr.hasNext()) {
						ReportForm report = (ReportForm) itr.next();
						excelSheet.setRowView(count, 30*20);
						addLabel(excelSheet, 0, count, report.getUserName(), table_content);
						addLabel(excelSheet, 1, count, report.getPeriod(), table_content);
						count++;
					}				
					response.setContentType("application/vnd.ms-excel");
					Calendar cal = Calendar.getInstance();
					DateFormat df = new SimpleDateFormat("dd_MMMM_yy_HH-mm");
					String file_name = df.format(cal.getTime());
					response.setHeader("Content-Disposition", "inline; filename=" +"DefaulterResourceList_"+file_name+".xls");
					workbook.write();
					workbook.close();			
					out.flush();
					out.close();
				}
			}catch(Exception e){
				e.printStackTrace();
				throw new Exception();
			}
		}
		else
			throw new Exception();
	}

	public ActionForward getCompOffReport(ActionMapping map,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		logger.info("Printing from Report Action getCompOffReport Method.");
		HttpSession s1 = request.getSession();
		ReportService reportservice = null; 
		if(s1.getAttribute("userName")!=null){
			try{
				ReportForm reportForm = (ReportForm) form;
				String location = reportForm.getLocation();
				int clientResourceId = reportForm.getClient_resource_ids();
				String[] allocatedResource = request.getParameterValues("userId");
				String reportMonthYear = request.getParameter("month-settings");
				reportservice = ReportServiceFactory.getReportService();
				String path = request.getServletContext().getRealPath("getInformation.properties");
				File holidayInfo = new File(path);
				Properties props = new Properties();
				if(holidayInfo.exists()){
					props.load(new FileInputStream(path));
				}							
				List<ReportForm> listForCompOffReport = null;
				if( allocatedResource != null && reportMonthYear != null){
					String year = reportMonthYear.substring(reportMonthYear.lastIndexOf('/')+1, reportMonthYear.length());
					String month = reportMonthYear.substring(reportMonthYear.indexOf('/')+1, reportMonthYear.lastIndexOf('/'));				
					listForCompOffReport = reportservice.getCompOffReport(allocatedResource, year, month, props);
				}
				request.setAttribute("CompOffReport", listForCompOffReport);
				request.setAttribute("locationDetail", location);
				request.setAttribute("selectedClientResource", clientResourceId);
				s1.setAttribute("CompOffReport", listForCompOffReport);     // Setted for excelsheet.
				s1.setAttribute("selectedDate", reportMonthYear);			// Overriding selectedDate with Selected month for UserReport JSP.
			}
			catch(Exception e){
				e.printStackTrace();
				throw new Exception();
			}
			return map.findForward("displayReport");
		}
		else
			throw new Exception();
	}	
	// Excel for generating Comp off Report.
	public void generateCompOffReportExcel(ActionMapping map,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		FileOutputStream fos = null;
		HttpSession sess =  request.getSession();
		if(sess.getAttribute("userName")!=null){
			try{
				List<ReportForm> compOffReportList = (List<ReportForm>)sess.getAttribute("CompOffReport");

				OutputStream out = response.getOutputStream();				
				WorkbookSettings wbSettings = new WorkbookSettings();
				wbSettings.setLocale(new Locale("en", "EN"));				
				WritableWorkbook workbook = jxl.Workbook.createWorkbook(out, wbSettings);			
				workbook.createSheet("CompOff Report", 0);			
				WritableSheet excelSheet = workbook.getSheet(0);				
				excelSheet.setColumnView(0, 20);
				excelSheet.setColumnView(1, 15);
				excelSheet.setColumnView(2, 20);
				excelSheet.setColumnView(3, 20);
				excelSheet.setColumnView(4, 20);
				excelSheet.setColumnView(5, 20);
				excelSheet.setColumnView(6, 15);
				excelSheet.setRowView(0, 40*20);	 

				//Getting different fonts. 
				getColorFonts();

				addCaption(excelSheet, 0, 0, "Username", table_header);
				addCaption(excelSheet, 1, 0, "Total Hours Spent", table_header);
				addCaption(excelSheet, 2, 0, "Billed Hours=No. working days inthe month*8", table_header);
				addCaption(excelSheet, 3, 0, "Additional Hours Spent= Total Hours-Billed Hours", table_header);
				addCaption(excelSheet, 4, 0, "Comp off availed in hours", table_header);
				addCaption(excelSheet, 5, 0, "Balance credits in hours", table_header);
				addCaption(excelSheet, 6, 0, "Total Down Time", table_header);

				int count=1;			
				Iterator<ReportForm> itr = compOffReportList.iterator();
				//Added
				while(itr.hasNext()){
					ReportForm report_form = itr.next();	
					excelSheet.setRowView(count, 30*20);
					addLabel(excelSheet, 0, count, report_form.getUserName(), table_content);
					addNumber(excelSheet, 1, count, report_form.getTotalTime(), table_content);
					addNumber(excelSheet, 2, count, report_form.getBilled_hours(), table_content);
					addNumber(excelSheet, 3, count, report_form.getAdditional_hours(), table_content);
					addNumber(excelSheet, 4, count, report_form.getCompOff_availed_hrs(), table_content);
					addNumber(excelSheet, 5, count, report_form.getComp_bal(), table_content);
					addNumber(excelSheet, 6, count, report_form.getTotal_down_time(), table_content);					
					count++;
				}  			
				response.setContentType("application/vnd.ms-excel");
				Calendar cal = Calendar.getInstance();
				DateFormat df = new SimpleDateFormat("dd_MMMM_yy_HH-mm");
				String file_name = df.format(cal.getTime());
				response.setHeader("Content-Disposition", "inline; filename=" +"CompOff_Report_"+file_name+".xls");				
				workbook.write();
				workbook.close();			
				out.flush();
				out.close();
			}
			catch(Exception e){
				e.printStackTrace();
				throw new Exception();
			}
			finally{
				if (fos != null) {
					try {
						fos.flush();
						fos.close();
					} 
					catch (IOException e){
						e.printStackTrace();
					}
				}			
			}
		}
		else
			throw new Exception();
	}	

	public ActionForward freezeTimesheet(ActionMapping map,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		// Freezing timesheet for selected resources.
		logger.info("Printing from Report Action freezeTimesheet Method.");
		HttpSession s1 = request.getSession();
		ReportService reportservice = null; 
		if(s1.getAttribute("userName")!=null){
			try{
				ReportForm reportForm = (ReportForm) form;	
				String location = reportForm.getLocation();
				int clientResourceId = reportForm.getClient_resource_ids(); 
				String reportMonthYear = request.getParameter("month-settings");		
				String[] allocatedResource=request.getParameterValues("userId");		
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				Calendar cal = Calendar.getInstance();
				cal.setTime(df.parse(reportMonthYear));
				cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
				reportservice = ReportServiceFactory.getReportService();
				String freezingStatus = reportservice.freezeTimesheet(allocatedResource, cal);
				if(freezingStatus.equalsIgnoreCase("Timesheet freezed successfully.")){
					request.setAttribute("unfreezeTimesheet", "unfreezeTimesheet");
				}
				request.setAttribute("freezingMessage", freezingStatus);
				request.setAttribute("locationDetail", location);
				request.setAttribute("selectedClientResource", clientResourceId);
				request.setAttribute("selectedAllocatedResource", allocatedResource);
				s1.setAttribute("selectedDate", reportMonthYear);	
			}
			catch (Exception e) {
				logger.error(e);
				throw new Exception();
			}
		}
		else{
			throw new Exception();
		}
		return map.findForward("getDetailedTimesheet");
	}
	public ActionForward unfreezeTimesheet(ActionMapping map,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		// unfreezeTimesheet timesheet for selected resources.
		logger.info("Printing from ReportAction unfreezeTimesheet Method.");
		HttpSession s1 = request.getSession();
		ReportService reportservice = null; 
		if(s1.getAttribute("userName")!=null){
			try{
				ReportForm reportForm = (ReportForm) form;	
				String location = reportForm.getLocation();
				int client_resource_id = reportForm.getClient_resource_ids();
				String reportMonthYear = request.getParameter("month-settings");
				String[] allocatedResource=request.getParameterValues("userId");		
				reportservice = ReportServiceFactory.getReportService();
				String freezingStatus = reportservice.unfreezeTimesheet(allocatedResource, reportMonthYear);
				request.setAttribute("freezingMessage", freezingStatus);
				request.setAttribute("locationDetail", location);
				request.setAttribute("selectedClientResource", client_resource_id);
				s1.setAttribute("selectedDate", reportMonthYear);	
				request.setAttribute("selectedAllocatedResource", allocatedResource);
			}
			catch (Exception e) {
				logger.error(e);
				throw new Exception();
			}
		}
		else{
			throw new Exception();
		}
		return map.findForward("getDetailedTimesheet");
	}

	public void checkFreezingEntryStatusForSelctedResource(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {	
		logger.info("You are inside checkFreezingEntryStatusForSelctedResource method");
		HttpSession session = request.getSession();
		String selectedResource = request.getParameter("selectedUser");	
		String selectedMonthDate = request.getParameter("selectedMonthForFreezing");
		DateFormat df =new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		cal.setTime(df.parse(selectedMonthDate));
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		String[] splittingUserId = selectedResource.split(",");
		if(session.getAttribute("user_id")!=null)  {
			ReportService reportservice=ReportServiceFactory.getReportService();
			String freezingStatus = "unfreezed";
			try{
				freezingStatus = reportservice.checkFreezingEntryStatusForSelctedResource(splittingUserId, cal);
				response.getWriter().print(freezingStatus);	
				logger.info("Freezing Status from checkFreezingEntryStatusForSelctedResource "+freezingStatus);
			}
			catch(Exception e){
				e.printStackTrace();
				response.getWriter().print("session expired");		
			}
		}
		else
			response.getWriter().print("session expired");
	}

	public ActionForward getTimestampForTimeEntries(ActionMapping map,ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception{
		// unfreezeTimesheet timesheet for selected resources.
		logger.info("Printing from ReportAction getTimestampForTimeEntry Method.");
		HttpSession s1 = request.getSession();
		ReportService reportservice = null; 
		if(s1.getAttribute("userName")!=null){
			try{
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				ReportForm reportForm = (ReportForm) form;	
				String location = reportForm.getLocation();
				int clientResourceId = reportForm.getClient_resource_ids();
				String reportMonthYear = request.getParameter("month-settings");				
				String[] allocatedResource=request.getParameterValues("userId");
				reportservice = ReportServiceFactory.getReportService();
				List<TaskForm> taskform_list = new ArrayList<TaskForm>();
				Calendar cal = Calendar.getInstance();
				if(allocatedResource != null && reportMonthYear != null){					
					cal.setTime(df.parse(reportMonthYear));
					taskform_list = reportservice.getTimestampForTimeEntries(allocatedResource, cal);
				}
				request.setAttribute("time_entry_updateStatus", taskform_list);				
				s1.setAttribute("time_entry_updateStatus", taskform_list);				
				request.setAttribute("locationDetail", location);
				request.setAttribute("selectedClientResource", clientResourceId);
				s1.setAttribute("selectedDate", reportMonthYear);	
				//request.setAttribute("selectedAllocatedResource", allocatedResource);
			}
			catch (Exception e) {
				logger.error(e);
				throw new Exception();
			}
		}
		else{
			throw new Exception();
		}
		return map.findForward("getDetailedTimesheet");
	}

	public void generateExcelForTimeEntryTracking(ActionMapping map,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		HttpSession sess =  request.getSession();
		if(sess.getAttribute("userName")!=null){
			try{
				List<TaskForm> timeEntryTrackingList = (List<TaskForm>)sess.getAttribute("time_entry_updateStatus");								
				OutputStream out = response.getOutputStream();				
				WorkbookSettings wbSettings = new WorkbookSettings();
				wbSettings.setLocale(new Locale("en", "EN"));				
				WritableWorkbook workbook = jxl.Workbook.createWorkbook(out, wbSettings);			
				workbook.createSheet("Entry Tracking Report", 0);			
				WritableSheet excelSheet = workbook.getSheet(0);				
				excelSheet.setColumnView(0, 20);
				excelSheet.setColumnView(1, 20);
				excelSheet.setColumnView(2, 20);
				excelSheet.setColumnView(3, 20);
				excelSheet.setColumnView(4, 20);

				int row = 0;
				int heightInPoints = 25*20;
				excelSheet.setRowView(row, heightInPoints);	 

				//Getting different fonts. 
				getColorFonts();

				addCaption(excelSheet, 0, 0, "Username", table_header);
				addCaption(excelSheet, 1, 0, "Task Date", table_header);
				addCaption(excelSheet, 2, 0, "Category", table_header);
				addCaption(excelSheet, 3, 0, "Task Description", table_header);
				addCaption(excelSheet, 4, 0, "Task Added/Updated On", table_header);

				int count=1;			
				Iterator<TaskForm> itr = timeEntryTrackingList.iterator();
				//Added
				while(itr.hasNext()){
					TaskForm task_form = itr.next();
					String status = task_form.getStatus();	
					String time_entry = task_form.getEntry_time();
					if(time_entry == null){
						time_entry = "";
					}
					excelSheet.setRowView(count, 30*20);
					addLabel(excelSheet, 0, count, task_form.getUserName(), table_content);
					addLabel(excelSheet, 1, count, task_form.getTask_date(), table_content);
					addLabel(excelSheet, 2, count, task_form.getStatus(), table_content);
					addLabel(excelSheet, 3, count, task_form.getTask_description(), table_content);
					if(status.equalsIgnoreCase("Leave")){
						addLabel(excelSheet, 4, count, time_entry+" (L)", table_content_red);
					}
					else if(status.equalsIgnoreCase("Half Day")){
						addLabel(excelSheet, 4, count, time_entry+" (H)", table_content_red);
					}
					else if(status.equalsIgnoreCase("Public holiday")){
						addLabel(excelSheet, 4, count, time_entry, table_content_blue);
					}
					else if(status.equalsIgnoreCase("Comp off")){
						addLabel(excelSheet, 4, count, time_entry, table_content_green);
					}
					else{
						addLabel(excelSheet, 4, count, time_entry, table_content);
					}
					count++;
				}
				response.setContentType("application/vnd.ms-excel");
				Calendar cal = Calendar.getInstance();
				DateFormat df = new SimpleDateFormat("dd_MMMM_yy_HH-mm");
				String file_name = df.format(cal.getTime());
				response.setHeader("Content-Disposition", "inline; filename=" +"TimeEntryTrackingReport_"+file_name+".xls");	
				workbook.write();
				workbook.close();			
				out.flush();
				out.close();
			}
			catch(Exception e){
				e.printStackTrace();
				throw new Exception();
			}
		}
		else
			throw new Exception();
	}	
	private void getColorFonts() throws WriteException{
		WritableFont tableContent = new WritableFont(WritableFont.createFont("verdana, arial, sans-serif"), 9);
		table_content = new WritableCellFormat(tableContent);
		table_content.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.PALE_BLUE);
		table_content.setWrap(true);

		WritableFont tableHeader = new WritableFont(WritableFont.createFont("verdana, arial, sans-serif"), 9, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE);
		table_header = new WritableCellFormat(tableHeader);
		table_header.setBackground(Colour.GREY_25_PERCENT);		
		table_header.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.PALE_BLUE);
		table_header.setWrap(true);

		WritableFont tableContentInRedFont = new WritableFont(WritableFont.createFont("verdana, arial, sans-serif"), 9);
		tableContentInRedFont.setColour(Colour.RED);
		table_content_red = new WritableCellFormat(tableContentInRedFont);
		table_content_red.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.PALE_BLUE);
		table_content_red.setWrap(true);

		WritableFont tableContentInBlueFont = new WritableFont(WritableFont.createFont("verdana, arial, sans-serif"), 9);
		tableContentInBlueFont.setColour(Colour.BLUE2);
		table_content_blue = new WritableCellFormat(tableContentInBlueFont);
		table_content_blue.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.PALE_BLUE);
		table_content_blue.setWrap(true);

		WritableFont tableContentInGreenFont = new WritableFont(WritableFont.createFont("verdana, arial, sans-serif"), 9);
		tableContentInGreenFont.setColour(Colour.GREEN);
		table_content_green = new WritableCellFormat(tableContentInGreenFont);
		table_content_green.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.PALE_BLUE);
		table_content_green.setWrap(true);

		WritableFont tableContentInLightGrayFont =  new WritableFont(WritableFont.createFont("verdana, arial, sans-serif"), 9);		
		table_content_light_gray = new WritableCellFormat(tableContentInLightGrayFont);
		table_content_light_gray.setBackground(Colour.WHITE);
		table_content_light_gray.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.PALE_BLUE);
		table_content_light_gray.setWrap(true);

		WritableFont tableContentInPinkFont = new WritableFont(WritableFont.createFont("verdana, arial, sans-serif"), 9, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE);	
		table_header_pink = new WritableCellFormat(tableContentInPinkFont);
		table_header_pink.setBackground(Colour.ROSE);
		table_header_pink.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.PALE_BLUE);
		table_header_pink.setWrap(true);
	}
	public ActionForward getResourceClientWise(ActionMapping map,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		HttpSession s1 =  request.getSession();
		UserService userService;
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		List<UserForm> allocatedUserList = null;
		if(s1.getAttribute("user_id")!=null){	
			try{
				int userId = (Integer) s1.getAttribute("user_id");
				ReportForm reportForm = (ReportForm) form;	
				String location = reportForm.getLocation();
				String monthYear = request.getParameter("month-settings");
				int client_resource_id = reportForm.getClient_resource_ids();
				userService = Factory.getUserService();
				if(monthYear != null){					
					cal.setTime(df.parse(monthYear));
					cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
					if(client_resource_id != 0){
						if(location != ""){
							allocatedUserList = userService.getAllocatedResourcesBasedOnExitDateAndLocation(client_resource_id, cal, location);
						}else{
							allocatedUserList = userService.getAllocatedResourcesBasedOnStartAndExitDate(client_resource_id, cal);
						}					
					}else{
						if(location != ""){
							allocatedUserList = userService.getAllocatedResourcesBasedOnExitDateAndLocation(userId, cal, location);
						}else{
							allocatedUserList = userService.getAllocatedResourcesBasedOnStartAndExitDate(userId, cal);
						}	
					}					 			
					s1.setAttribute("userList", allocatedUserList);
					request.setAttribute("locationDetail", location);
					request.setAttribute("selectedClientResource", client_resource_id);
				}
			}
			catch(Exception e){
				e.printStackTrace();
				throw new Exception();
			}			
			return map.findForward("getUserReportDetails");
		}
		else
			throw new Exception();
	}
	
	// Added for Weekly Status Report	
	/*public ActionForward getWeeklyStatus(ActionMapping map,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		HttpSession sess =  request.getSession();
		if(sess.getAttribute("userName")!=null){     


		  return map.findForward("weeklyStatusPage");
		}
	   else
		  throw new Exception();
	}
	public ActionForward getWeeklyStatusForm(ActionMapping map,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		HttpSession sess =  request.getSession();
		if(sess.getAttribute("userName")!=null){  
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			System.out.println(startDate+"...."+endDate);
			sess.setAttribute("startDate", startDate);
			sess.setAttribute("endDate", endDate);
			return map.findForward("weeklyStatusForm");
		}
		else
			throw new Exception();
	}
	public ActionForward saveWeeklyForm(ActionMapping map,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		HttpSession sess =  request.getSession();
		if(sess.getAttribute("userName")!=null){  
			WeeklyForm formOfTheWeek = (WeeklyForm) form;
			String startDate = (String)sess.getAttribute("startDate");
			String endDate = (String) sess.getAttribute("endDate");
			int userId = Integer.parseInt(sess.getAttribute("user_id").toString());

			 String[] details_miles = request.getParameterValues("detail_mile");
			 String[] owner_mile = request.getParameterValues("owner_mile");
			 String[] remark_mile = request.getParameterValues("remark_mile");
			 int totalRow = details_miles.length;
			 List<Deliverables> deliv = new ArrayList<Deliverables>();
			 for (int i = 0; i < totalRow; i++) {
				 Deliverables d1 = new Deliverables();
				 d1.setDetails_miles(details_miles[i]);
				 d1.setOwner_mile(owner_mile[i]);
				 d1.setRemark_mile(remark_mile[i]);
				 deliv.add(d1);				
			 }

			 String[] onDate_const = request.getParameterValues("onDate_const");
			 String[] detail_const = request.getParameterValues("detail_const");
			 String[] owner_const = request.getParameterValues("owner_const");
			 String[] remark_const = request.getParameterValues("remark_const");
			 String[] eta_const = request.getParameterValues("eta_const");
			 List<Constraints> constr = new ArrayList<Constraints>();
			 int rowConst = onDate_const.length;
			 for (int i = 0; i < rowConst; i++) {
				 Constraints c1 = new Constraints();
				 c1.setOnDate_const(onDate_const[i]);
				 c1.setDetail_const(detail_const[i]);
				 c1.setOwner_const(owner_const[i]);
				 c1.setRemark_const(remark_const[i]);
				 c1.setEta_const(eta_const[i]);
				 constr.add(c1);				
			}

			 String[] role_status = request.getParameterValues("role_status");
			 String[] position = request.getParameterValues("position");
			 String[] internalInter = request.getParameterValues("internalInter");
			 String[] apolloInter = request.getParameterValues("apolloInter");
			 String[] selectedOff = request.getParameterValues("selectedOff");
			 String[] joined = request.getParameterValues("joined");
			 List<Recruitment_status> recrut = new ArrayList<Recruitment_status>();
			 int rowRecrut = role_status.length;
			 for (int i = 0; i < rowRecrut; i++) {
				 Recruitment_status r1 = new Recruitment_status();
				 r1.setRole_status(role_status[i]);
				 r1.setPosition(position[i]);
				 r1.setInternalInter(internalInter[i]);
				 r1.setApolloInter(apolloInter[i]);
				 r1.setSelectedOff(selectedOff[i]);
				 r1.setJoined(joined[i]);
				 recrut.add(r1);				
			}	

			 ReportService reportservice=ReportServiceFactory.getReportService();			 
			 String weeklySave = reportservice.saveWeeklyStatus(formOfTheWeek, userId, startDate, endDate, deliv, constr, recrut);	

			 WeeklyForm weekReportData = reportservice.getEditableWeeklyForm(startDate, endDate,userId);

			 sess.setAttribute("weeklyObject", weekReportData);
			 request.setAttribute("weeklyObject", weekReportData);
 			 System.out.println("Getting Size of List"+deliv.size()+", "+constr.size()+", "+recrut.size());			
 			 request.setAttribute("saveStatus", weeklySave+" week "+startDate+"-"+endDate);					
			 String[] ondate =  request.getParameterValues("onDate_const");			
			 for (String string : ondate) {
				System.out.println(string+" Check date");
			 }
			 return map.findForward("weeklyStatusForm");
		}
		else
			throw new Exception();
	}	*/
}
