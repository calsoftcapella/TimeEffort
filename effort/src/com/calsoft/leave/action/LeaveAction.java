package com.calsoft.leave.action;

import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.WorkbookSettings;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import com.calsoft.factory.Factory;
import com.calsoft.leave.form.LeaveForm;
import com.calsoft.leave.model.Leave;
import com.calsoft.leave.service.LeaveService;
import com.calsoft.leave.service.factory.LeaveServiceFactory;
import com.calsoft.report.model.Report;
import com.calsoft.report.service.ReportService;
import com.calsoft.report.service.ReportServiceFactory;
import com.calsoft.user.form.UserForm;
import com.calsoft.user.service.UserService;
/*Action class for LeaveEntry Module*/

@SuppressWarnings("unchecked")
public class LeaveAction extends DispatchAction {
	private static final Logger logger = Logger.getLogger("Leave Action");
	private WritableCellFormat table_header;
	private WritableCellFormat table_content;
	private WritableCellFormat table_content_red;
	private WritableCellFormat table_content_blue;
	private WritableCellFormat table_content_green;
	private WritableCellFormat table_header_pink;
	private WritableCellFormat table_content_light_gray;
	
	public ActionForward saveDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession sess =  request.getSession();
		if(sess.getAttribute("userName")!=null){
			try {
				int userId = (Integer) sess.getAttribute("user_id");
				LeaveForm leaveForm = (LeaveForm) form;
				LeaveService leaveService = LeaveServiceFactory.getLeaveService();
				String selectYear = null;
				String selectMonth = null;
				String[] checkDate = null;
				String selectMonthYear = null;
				List<LeaveForm> leaveList = null;
				if (request.getParameter("iYear") != null&& request.getParameter("iMonth") != null){
					selectYear = request.getParameter("iYear");
					selectMonth = request.getParameter("iMonth");
					int selectM = Integer.parseInt(selectMonth);
					selectM = selectM + 1;
					selectMonth = new Integer(selectM).toString();
					String[] monthName = { "Jan", "Feb", "Mar", "Apr", "May",
							"Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
					if (selectMonth.equals("1")) {
						selectMonth = monthName[0];
					}
					if (selectMonth.equals("2")) {
						selectMonth = monthName[1];
					}
					if (selectMonth.equals("3")) {
						selectMonth = monthName[2];
					}
					if (selectMonth.equals("4")) {
						selectMonth = monthName[3];
					}
					if (selectMonth.equals("5")) {
						selectMonth = monthName[4];
					}
					if (selectMonth.equals("6")) {
						selectMonth = monthName[5];
					}
					if (selectMonth.equals("7")) {
						selectMonth = monthName[6];
					}
					if (selectMonth.equals("8")) {
						selectMonth = monthName[7];
					}
					if (selectMonth.equals("9")) {
						selectMonth = monthName[8];
					}
					if (selectMonth.equals("10")) {
						selectMonth = monthName[9];
					}
					if (selectMonth.equals("11")) {
						selectMonth = monthName[10];
					}
					if (selectMonth.equals("12")) {
						selectMonth = monthName[11];
					}
					selectMonthYear = selectMonth + "-" + selectYear;
					checkDate = request.getParameterValues("checkDate");
					leaveForm.setSelectMonth(selectMonthYear);
					leaveService.saveLeave(leaveForm, request, checkDate, selectMonthYear);
					List<String> leaveDate = leaveService.getLeaveDate(selectMonthYear, request);
					request.setAttribute("leaveDateList", leaveDate);
					leaveList = leaveService.getLeave(selectMonthYear, request);
					request.setAttribute("leaveFormList", leaveList);
					// Hiding Get Team Leave Button fron User
					UserService userService = Factory.getUserService();
					List<UserForm> allocatedUserList = userService.getAllocatedResourcesDetailsBasedOnRelievingDate(userId, Calendar.getInstance());
					if(!allocatedUserList.isEmpty() && allocatedUserList.size() > 1){
						request.setAttribute("validForTeamLeaveDetail", "validForTeamLeaveDetail");
					}	
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e);
				throw new Exception();
			}
			return mapping.findForward("getLeaveDetails");
		}
		else
			throw new Exception();
	}

	/*
	 * saveDashBoard() method :Performing the save operation from dashBoard page
	 * of Leave Entry
	 */

	public ActionForward saveDashBoard(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession sess =  request.getSession();
		if(sess.getAttribute("userName")!=null){
			try {
				int userId = (Integer) sess.getAttribute("user_id");
				LeaveForm leaveForm = (LeaveForm) form;
				LeaveService leaveService = LeaveServiceFactory.getLeaveService();
				String selectYear = null;
				String selectMonth = null;
				String[] checkDateNew = null;
				String selectMonthYear = null;
				String dateSeparatedByComma = null;
				if (request.getParameter("iYear") != null && request.getParameter("iMonth") != null) {
					selectYear = request.getParameter("iYear");
					selectMonth = request.getParameter("iMonth");
					int selectM = Integer.parseInt(selectMonth);
					selectM = selectM + 1;
					selectMonth = new Integer(selectM).toString();					
					String[] monthName = { "Jan", "Feb", "Mar", "Apr", "May",
							"Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
					if (selectMonth.equals("1")) {
						selectMonth = monthName[0];
					}
					if (selectMonth.equals("2")) {
						selectMonth = monthName[1];
					}
					if (selectMonth.equals("3")) {
						selectMonth = monthName[2];
					}
					if (selectMonth.equals("4")) {
						selectMonth = monthName[3];
					}
					if (selectMonth.equals("5")) {
						selectMonth = monthName[4];
					}
					if (selectMonth.equals("6")) {
						selectMonth = monthName[5];
					}
					if (selectMonth.equals("7")) {
						selectMonth = monthName[6];
					}
					if (selectMonth.equals("8")) {
						selectMonth = monthName[7];
					}
					if (selectMonth.equals("9")) {
						selectMonth = monthName[8];
					}
					if (selectMonth.equals("10")) {
						selectMonth = monthName[9];
					}
					if (selectMonth.equals("11")) {
						selectMonth = monthName[10];
					}
					if (selectMonth.equals("12")) {
						selectMonth = monthName[11];
					}
					selectMonthYear = selectMonth + "-" + selectYear;
					checkDateNew = request.getParameterValues("checkDate");
					leaveForm.setSelectMonth(selectMonthYear);
					leaveService.saveLeave(leaveForm, request, checkDateNew, selectMonthYear);
					List<Integer> leaveDateNew = new ArrayList<Integer>();
					List<String> leaveDate = leaveService.getLeaveDate(selectMonthYear, request);					
					Iterator<String> dateItr = leaveDate.iterator();
					while (dateItr.hasNext()) {
						String stringDate = dateItr.next().toString();
						leaveDateNew.add(new Integer(stringDate));
					}
					Collections.sort(leaveDateNew);
					List<Leave> leaveUpdatedList = new ArrayList<Leave>();
					List<Leave> dashBoardList = leaveService.getDashBoardList(selectMonthYear, request);
					for (int pk = 0; pk < leaveDateNew.size(); pk++) {
						dateSeparatedByComma = dateSeparatedByComma +","+leaveDateNew.get(pk);
					}
					if (dateSeparatedByComma != null) {
						dateSeparatedByComma = dateSeparatedByComma.replaceAll("null,", "");
						Iterator<Leave> itr = dashBoardList.iterator();
						while (itr.hasNext()) {
							Leave leaveValue = itr.next();
							leaveValue.setLeave_month(selectMonthYear);
							leaveValue.setLeave_date(dateSeparatedByComma);
							leaveUpdatedList.add(leaveValue);
						}
					}
					request.setAttribute("leaveList", leaveUpdatedList);
					request.setAttribute("leaveDate", leaveDate);
					// Hiding Get Team Leave Button fron User
					UserService userService = Factory.getUserService();
					List<UserForm> allocatedUserList = userService.getAllocatedResourcesDetailsBasedOnRelievingDate(userId, Calendar.getInstance());
					if(!allocatedUserList.isEmpty() && allocatedUserList.size() > 1){
						request.setAttribute("validForTeamLeaveDetail", "validForTeamLeaveDetail");
					}	
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e);
				throw new Exception();
			}
			return mapping.findForward("getLeaveDashBoard");
		}
		else
			throw new Exception();
	}
	/*
	 * getLeaveDetails() method :Retriving the all records for selected month of
	 * Leave Entry
	 */
	public ActionForward getLeaveDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession sess =  request.getSession();
		if(sess.getAttribute("user_id")!=null){
			try {
				int userId = (Integer) sess.getAttribute("user_id");
				LeaveService leaveService = LeaveServiceFactory.getLeaveService();
				String selectYear = null;
				String selectMonth = null;
				String selectMonthYear = null;
				List<LeaveForm> leaveList = null;
				selectYear = request.getParameter("iYear");
				selectMonth = request.getParameter("iMonth");
				int selectM = Integer.parseInt(selectMonth);
				selectM = selectM + 1;
				selectMonth = new Integer(selectM).toString();
				String[] monthName = { "Jan", "Feb", "Mar", "Apr", "May", "Jun",
						"Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
				if (selectMonth.equals("1")) {
					selectMonth = monthName[0];
				}
				if (selectMonth.equals("2")) {
					selectMonth = monthName[1];
				}
				if (selectMonth.equals("3")) {
					selectMonth = monthName[2];
				}
				if (selectMonth.equals("4")) {
					selectMonth = monthName[3];
				}
				if (selectMonth.equals("5")) {
					selectMonth = monthName[4];
				}
				if (selectMonth.equals("6")) {
					selectMonth = monthName[5];
				}
				if (selectMonth.equals("7")) {
					selectMonth = monthName[6];
				}
				if (selectMonth.equals("8")) {
					selectMonth = monthName[7];
				}
				if (selectMonth.equals("9")) {
					selectMonth = monthName[8];
				}
				if (selectMonth.equals("10")) {
					selectMonth = monthName[9];
				}
				if (selectMonth.equals("11")) {
					selectMonth = monthName[10];
				}
				if (selectMonth.equals("12")) {
					selectMonth = monthName[11];
				}
				selectMonthYear = selectMonth + "-" + selectYear;				
				List<String> leaveDate = leaveService.getLeaveDate(selectMonthYear, request);
				UserService userService = Factory.getUserService();
				UserForm userDetail = userService.getUsernameFromId(userId);
				Date freezeDate = userDetail.getFreeze_timesheet();
				Calendar cal = Calendar.getInstance();
				if(selectMonthYear!=null){
					DateFormat d_format = new SimpleDateFormat("dd-MMM-yyyy");
					cal.setTime(d_format.parse("01-"+selectMonthYear));
				}
				if(freezeDate != null){
					Calendar calWithFreezingDate = Calendar.getInstance();
					calWithFreezingDate.setTime(freezeDate);	
					cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
					cal.set(Calendar.HOUR_OF_DAY, 0);
					cal.set(Calendar.MINUTE, 0);
					cal.set(Calendar.SECOND, 0);
					cal.set(Calendar.MILLISECOND, 0);
					//Checking if freezing date exceeds than currentDate then blocking new entry.
					if(calWithFreezingDate.after(cal) || calWithFreezingDate.equals(cal)){
						request.setAttribute("disablingSaveAndClear", "disablingSaveAndClear");
					}
				}			
				request.setAttribute("leaveDateList", leaveDate);
				leaveList = leaveService.getLeave(selectMonthYear, request);
				request.setAttribute("leaveFormList", leaveList);
				
				// Hiding Get Team Leave Button fron User
				List<UserForm> allocatedUserList = userService.getAllocatedResourcesDetailsBasedOnRelievingDate(userId, Calendar.getInstance());
				if(!allocatedUserList.isEmpty() && allocatedUserList.size() > 1){
					request.setAttribute("validForTeamLeaveDetail", "validForTeamLeaveDetail");
				}	
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e);
				throw new Exception();
			}
			return mapping.findForward("getLeaveDetails");
		}
		else
			throw new Exception();
	}

	/*
	 * getLeaveDashBoard() method :Retriving specific records for selected month
	 * of Leave Entry
	 */

	public ActionForward getLeaveDashBoard(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception {
		HttpSession sess =  request.getSession();
		if(sess.getAttribute("userName")!=null){
			int userId = (Integer) sess.getAttribute("user_id");
			try {
				sess.removeAttribute("selectedDate");	
				sess.removeAttribute("conList");
				sess.removeAttribute("userList");
				sess.removeAttribute("conListUpdate");
				LeaveService leaveService = LeaveServiceFactory.getLeaveService();
				String selectYear = null;
				String selectMonth = null;
				String selectMonthYear = null;
				String dateSeparatedByComma = null;
				selectYear = request.getParameter("iYear");
				selectMonth = request.getParameter("iMonth");
				String getCompleteLeaveyear = request.getParameter("iYearComplete");
				String getCompleteLeaveMonth = request.getParameter("iMonthComplete");
				String[] monthName = { "Jan", "Feb", "Mar", "Apr", "May", "Jun",
						"Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
				if (selectMonth == null){
					Calendar ca = new GregorianCalendar();
					int iTMonth = ca.get(Calendar.MONTH);
					iTMonth = iTMonth + 1;
					int iTYear = ca.get(Calendar.YEAR);
					selectMonth = new Integer(iTMonth).toString();
					selectYear = new Integer(iTYear).toString();
					if (selectMonth.equals("1")) {
						selectMonth = monthName[0];
					}
					if (selectMonth.equals("2")) {
						selectMonth = monthName[1];
					}
					if (selectMonth.equals("3")) {
						selectMonth = monthName[2];
					}
					if (selectMonth.equals("4")) {
						selectMonth = monthName[3];
					}
					if (selectMonth.equals("5")) {
						selectMonth = monthName[4];
					}
					if (selectMonth.equals("6")) {
						selectMonth = monthName[5];
					}
					if (selectMonth.equals("7")) {
						selectMonth = monthName[6];
					}
					if (selectMonth.equals("8")) {
						selectMonth = monthName[7];
					}
					if (selectMonth.equals("9")) {
						selectMonth = monthName[8];
					}
					if (selectMonth.equals("10")) {
						selectMonth = monthName[9];
					}
					if (selectMonth.equals("11")) {
						selectMonth = monthName[10];
					}
					if (selectMonth.equals("12")) {
						selectMonth = monthName[11];
					}
					selectMonthYear = selectMonth + "-" + selectYear;
				} 
				else if(getCompleteLeaveyear!=null && getCompleteLeaveyear.length()>2){     //Added for getting entire resources leave detail for complete year.
					List<LeaveForm> dashBoardList = leaveService.getDashBoardList(getCompleteLeaveyear, userId);
					DateFormat df = new SimpleDateFormat("MMMM");
					DateFormat df1 = new SimpleDateFormat("MMM");
					Date month = df.parse(getCompleteLeaveMonth);
					String formattedDate = df1.format(month);
					List<String> leaveDate =  leaveService.getLeaveDate(formattedDate+"-"+getCompleteLeaveyear, request);
					
					UserService userService = Factory.getUserService();
					UserForm userDetail = userService.getUsernameFromId(userId);
					Date freezeDate = userDetail.getFreeze_timesheet();
					Calendar cal = Calendar.getInstance();
					DateFormat d_format = new SimpleDateFormat("dd-MMMM-yyyy");
					selectMonthYear = getCompleteLeaveMonth+"-"+getCompleteLeaveyear;
					cal.setTime(d_format.parse("01-"+selectMonthYear));
					if(freezeDate != null){
						Calendar calWithFreezingDate = Calendar.getInstance();
						calWithFreezingDate.setTime(freezeDate);	
						cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
						cal.set(Calendar.HOUR_OF_DAY, 0);
						cal.set(Calendar.MINUTE, 0);
						cal.set(Calendar.SECOND, 0);
						cal.set(Calendar.MILLISECOND, 0);
						//Checking if freezing date exceeds than currentDate then blocking new entry.
						if(calWithFreezingDate.after(cal) || calWithFreezingDate.equals(cal)){
							request.setAttribute("disablingSaveAndClear", "disablingSaveAndClear");
						}
					}	
					// Hiding Get Team Leave Button fron User
					List<UserForm> allocatedUserList = userService.getAllocatedResourcesDetailsBasedOnRelievingDate(userId, Calendar.getInstance());
					if(!allocatedUserList.isEmpty() && allocatedUserList.size() > 1){
						request.setAttribute("validForTeamLeaveDetail", "validForTeamLeaveDetail");
					}	
					
					request.setAttribute("leaveDate", leaveDate);
					request.setAttribute("leaveListForYear", dashBoardList);
					sess.setAttribute("leaveListForYear", dashBoardList);
					return mapping.findForward("getLeaveDashBoard");
				}																
				else {
					int selectM = Integer.parseInt(selectMonth);					
					selectM = selectM + 1;
					selectMonth = new Integer(selectM).toString();

					if (selectMonth.equals("1")) {
						selectMonth = monthName[0];
					}
					if (selectMonth.equals("2")) {
						selectMonth = monthName[1];
					}
					if (selectMonth.equals("3")) {
						selectMonth = monthName[2];
					}
					if (selectMonth.equals("4")) {
						selectMonth = monthName[3];
					}
					if (selectMonth.equals("5")) {
						selectMonth = monthName[4];
					}
					if (selectMonth.equals("6")) {
						selectMonth = monthName[5];
					}
					if (selectMonth.equals("7")) {
						selectMonth = monthName[6];
					}
					if (selectMonth.equals("8")) {
						selectMonth = monthName[7];
					}
					if (selectMonth.equals("9")) {
						selectMonth = monthName[8];
					}
					if (selectMonth.equals("10")) {
						selectMonth = monthName[9];
					}
					if (selectMonth.equals("11")) {
						selectMonth = monthName[10];
					}
					if (selectMonth.equals("12")) {
						selectMonth = monthName[11];
					}
					selectMonthYear = selectMonth + "-" + selectYear;
				}
				List<Integer> leaveDateNew = new ArrayList<Integer>();
				List<String> leaveDate =  leaveService.getLeaveDate(selectMonthYear, request);
				Iterator<String> dateItr = leaveDate.iterator();
				while (dateItr.hasNext()) {
					String stringDate = dateItr.next().toString();
					leaveDateNew.add(new Integer(stringDate));
				}
				Collections.sort(leaveDateNew);
				List<Leave> leaveUpdatedList = new ArrayList<Leave>();
				List<Leave> dashBoardList = leaveService.getDashBoardList(selectMonthYear, request);
				for (int pk = 0; pk < leaveDateNew.size(); pk++) {
					dateSeparatedByComma = dateSeparatedByComma + ","+ leaveDateNew.get(pk);
				}
				if (dateSeparatedByComma != null) {
					dateSeparatedByComma = dateSeparatedByComma.replaceAll("null,","");
					Iterator<Leave> itr = dashBoardList.iterator();
					while (itr.hasNext()) {
						Leave leaveValue = itr.next();
						leaveValue.setLeave_month(selectMonthYear);
						leaveValue.setLeave_date(dateSeparatedByComma);
						leaveUpdatedList.add(leaveValue);
					}
				}
				UserService userService = Factory.getUserService();
				UserForm userDetail = userService.getUsernameFromId(userId);
				Date freezeDate = userDetail.getFreeze_timesheet();
				Calendar cal = Calendar.getInstance();
				if(selectMonthYear!=null){
					DateFormat d_format = new SimpleDateFormat("dd-MMM-yyyy");
					cal.setTime(d_format.parse("01-"+selectMonthYear));
				}
				if(freezeDate != null){
					Calendar calWithFreezingDate = Calendar.getInstance();
					calWithFreezingDate.setTime(freezeDate);	
					cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
					cal.set(Calendar.HOUR_OF_DAY, 0);
					cal.set(Calendar.MINUTE, 0);
					cal.set(Calendar.SECOND, 0);
					cal.set(Calendar.MILLISECOND, 0);
					//Checking if freezing date exceeds than currentDate then blocking new entry.
					if(calWithFreezingDate.after(cal) || calWithFreezingDate.equals(cal)){
						request.setAttribute("disablingSaveAndClear", "disablingSaveAndClear");
					}
				}
				// Hiding Get Team Leave Button fron User
				List<UserForm> allocatedUserList = userService.getAllocatedResourcesDetailsBasedOnRelievingDate(userId, Calendar.getInstance());
				if(!allocatedUserList.isEmpty() && allocatedUserList.size() > 1){
					request.setAttribute("validForTeamLeaveDetail", "validForTeamLeaveDetail");
				}	
				request.setAttribute("leaveList", leaveUpdatedList);
				request.setAttribute("leaveDate", leaveDate);
			} 
			catch (Exception e){
				e.printStackTrace();
				logger.error(e);
				throw new Exception();
			}
			return mapping.findForward("getLeaveDashBoard");
		}
		else
			throw new Exception();
	}

	/*
	 * clearCheckedDate() method :Clear checkboxes which are checked for
	 * selected month of Leave Entry
	 */

	public ActionForward clearCheckedDate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession sess =  request.getSession();
		if(sess.getAttribute("userName")!=null){
			try {
				int userId = (Integer) sess.getAttribute("user_id");
				LeaveForm leaveForm = (LeaveForm) form;
				LeaveService leaveService = LeaveServiceFactory.getLeaveService();
				String selectYear = null;
				String selectMonth = null;
				String[] checkDate = null;
				String selectMonthYear = null;
				List<LeaveForm> leaveList = null;
				if (request.getParameter("checkDate") != null && request.getParameter("iYear") != null && request.getParameter("iMonth") != null) {
					selectYear = request.getParameter("iYear");
					selectMonth = request.getParameter("iMonth");
					int selectM = Integer.parseInt(selectMonth);
					selectM = selectM + 1;
					selectMonth = new Integer(selectM).toString();
					String[] monthName = { "Jan", "Feb", "Mar", "Apr", "May",
							"Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
					if (selectMonth.equals("1")) {
						selectMonth = monthName[0];
					}
					if (selectMonth.equals("2")) {
						selectMonth = monthName[1];
					}
					if (selectMonth.equals("3")) {
						selectMonth = monthName[2];
					}
					if (selectMonth.equals("4")) {
						selectMonth = monthName[3];
					}
					if (selectMonth.equals("5")) {
						selectMonth = monthName[4];
					}
					if (selectMonth.equals("6")) {
						selectMonth = monthName[5];
					}
					if (selectMonth.equals("7")) {
						selectMonth = monthName[6];
					}
					if (selectMonth.equals("8")) {
						selectMonth = monthName[7];
					}
					if (selectMonth.equals("9")) {
						selectMonth = monthName[8];
					}
					if (selectMonth.equals("10")) {
						selectMonth = monthName[9];
					}
					if (selectMonth.equals("11")) {
						selectMonth = monthName[10];
					}
					if (selectMonth.equals("12")) {
						selectMonth = monthName[11];
					}
					selectMonthYear = selectMonth + "-" + selectYear;
					checkDate = request.getParameterValues("checkDate");
					leaveForm.setSelectMonth(selectMonthYear);
					leaveService.clearCheck(leaveForm, request, checkDate);
				}
				List<String> leaveDate = leaveService.getLeaveDate(selectMonthYear, request);
				request.setAttribute("leaveDateList", leaveDate);
				leaveList = leaveService.getLeave(selectMonthYear, request);
				request.setAttribute("leaveFormList", leaveList);
				// Hiding Get Team Leave Button fron User
				UserService userService = Factory.getUserService();
				List<UserForm> allocatedUserList = userService.getAllocatedResourcesDetailsBasedOnRelievingDate(userId, Calendar.getInstance());
				if(!allocatedUserList.isEmpty() && allocatedUserList.size() > 1){
					request.setAttribute("validForTeamLeaveDetail", "validForTeamLeaveDetail");
				}	
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e);
				throw new Exception();
			}
			return mapping.findForward("getLeaveDetails");
		}
		else
			throw new Exception();
	}

	/*
	 * clearCheckedDateDashBoard() method :Removing records which are checked
	 * for selected month of Leave Entry in Dash Board
	 */

	public ActionForward clearCheckedDateDashBoard(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession sess =  request.getSession();
		if(sess.getAttribute("userName")!=null){
			try {
				int userId = (Integer) sess.getAttribute("user_id");
				LeaveForm leaveForm = (LeaveForm) form;
				LeaveService leaveService = LeaveServiceFactory.getLeaveService();
				String selectYear = null;
				String selectMonth = null;
				String[] checkDate = null;
				String selectMonthYear = null;
				String dateSeparatedByComma = null;
				if (request.getParameter("checkDate") != null && request.getParameter("iYear") != null && request.getParameter("iMonth") != null) {
					selectYear = request.getParameter("iYear");
					selectMonth = request.getParameter("iMonth");
					int selectM = Integer.parseInt(selectMonth);
					selectM = selectM + 1;
					selectMonth = new Integer(selectM).toString();
					String[] monthName = { "Jan", "Feb", "Mar", "Apr", "May",
							"Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
					if (selectMonth.equals("1")) {
						selectMonth = monthName[0];
					}
					if (selectMonth.equals("2")) {
						selectMonth = monthName[1];
					}
					if (selectMonth.equals("3")) {
						selectMonth = monthName[2];
					}
					if (selectMonth.equals("4")) {
						selectMonth = monthName[3];
					}
					if (selectMonth.equals("5")) {
						selectMonth = monthName[4];
					}
					if (selectMonth.equals("6")) {
						selectMonth = monthName[5];
					}
					if (selectMonth.equals("7")) {
						selectMonth = monthName[6];
					}
					if (selectMonth.equals("8")) {
						selectMonth = monthName[7];
					}
					if (selectMonth.equals("9")) {
						selectMonth = monthName[8];
					}
					if (selectMonth.equals("10")) {
						selectMonth = monthName[9];
					}
					if (selectMonth.equals("11")) {
						selectMonth = monthName[10];
					}
					if (selectMonth.equals("12")) {
						selectMonth = monthName[11];
					}
					selectMonthYear = selectMonth + "-" + selectYear;
					checkDate = request.getParameterValues("checkDate");
					leaveForm.setSelectMonth(selectMonthYear);
					leaveService.clearCheck(leaveForm, request, checkDate);
				}
				//List leaveList = leaveService.getLeave(selectMonthYear, request);
				List<Integer> leaveDateNew = new ArrayList<Integer>();
				List<String> leaveDate =leaveService.getLeaveDate(selectMonthYear, request);
				Iterator<String> dateItr = leaveDate.iterator();
				while (dateItr.hasNext()) {
					String stringDate = dateItr.next().toString();
					leaveDateNew.add(new Integer(stringDate));

				}
				Collections.sort(leaveDateNew);
				List<Leave> leaveUpdatedList = new ArrayList<Leave>();
				List<Leave> dashBoardList = leaveService.getDashBoardList(selectMonthYear, request);
				for (int pk = 0; pk < leaveDateNew.size(); pk++) {
					dateSeparatedByComma = dateSeparatedByComma + ","
							+ leaveDateNew.get(pk);
				}
				if (dateSeparatedByComma != null) {
					dateSeparatedByComma = dateSeparatedByComma.replaceAll("null,","");
					Iterator<Leave> itr = dashBoardList.iterator();
					while (itr.hasNext()) {
						Leave leaveValue = itr.next();
						leaveValue.setLeave_month(selectMonthYear);
						leaveValue.setLeave_date(dateSeparatedByComma);
						leaveUpdatedList.add(leaveValue);
					}
				}
				request.setAttribute("leaveList", leaveUpdatedList);
				request.setAttribute("leaveDate", leaveDate);
				// Hiding Get Team Leave Button fron User
				UserService userService = Factory.getUserService();
				List<UserForm> allocatedUserList = userService.getAllocatedResourcesDetailsBasedOnRelievingDate(userId, Calendar.getInstance());
				if(!allocatedUserList.isEmpty() && allocatedUserList.size() > 1){
					request.setAttribute("validForTeamLeaveDetail", "validForTeamLeaveDetail");
				}	
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e);
				throw new Exception();
			}
			return mapping.findForward("clearCheckedDateDashBoard");
		}
		else
			throw new Exception();
	}

	/*
	 * saveTeamDetail() method :Performing the save operation from
	 * getTeamLeaveDetail page of Leave Entry
	 */

	public ActionForward saveTeamDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession sess =  request.getSession();
		if(sess.getAttribute("userName")!=null){
			try {
				LeaveForm leaveForm = (LeaveForm) form;
				LeaveService leaveService = LeaveServiceFactory.getLeaveService();
				String selectYear = null;
				String selectMonth = null;
				String[] checkDate = null;
				String selectMonthYear = null;
				if (/*request.getParameter("checkDate") != null
					&&*/ request.getParameter("iYear") != null
					&& request.getParameter("iMonth") != null) {
					selectYear = request.getParameter("iYear");
					selectMonth = request.getParameter("iMonth");
					int selectM = Integer.parseInt(selectMonth);
					selectM = selectM + 1;
					selectMonth = new Integer(selectM).toString();
					String[] monthName = { "Jan", "Feb", "Mar", "Apr", "May",
							"Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
					if (selectMonth.equals("1")) {
						selectMonth = monthName[0];
					}
					if (selectMonth.equals("2")) {
						selectMonth = monthName[1];
					}
					if (selectMonth.equals("3")) {
						selectMonth = monthName[2];
					}
					if (selectMonth.equals("4")) {
						selectMonth = monthName[3];
					}
					if (selectMonth.equals("5")) {
						selectMonth = monthName[4];
					}
					if (selectMonth.equals("6")) {
						selectMonth = monthName[5];
					}
					if (selectMonth.equals("7")) {
						selectMonth = monthName[6];
					}
					if (selectMonth.equals("8")) {
						selectMonth = monthName[7];
					}
					if (selectMonth.equals("9")) {
						selectMonth = monthName[8];
					}
					if (selectMonth.equals("10")) {
						selectMonth = monthName[9];
					}
					if (selectMonth.equals("11")) {
						selectMonth = monthName[10];
					}
					if (selectMonth.equals("12")) {
						selectMonth = monthName[11];
					}
					selectMonthYear = selectMonth + "-" + selectYear;
					checkDate = request.getParameterValues("checkDate");
					leaveForm.setSelectMonth(selectMonthYear);
					leaveService.saveLeave(leaveForm, request, checkDate,selectMonthYear);
					List<String> leaveDate = leaveService.getLeaveDate(selectMonthYear, request);
					request.setAttribute("leaveDateList", leaveDate);
					int userId = Integer.parseInt(sess.getAttribute("user_id").toString());
					ReportService reportservice = ReportServiceFactory.getReportService();
					List<List<Report>> combinedList = reportservice.getUserReportAllocation(userId);
					Iterator<List<Report>> itr = combinedList.iterator();
					List<Report> allocatedList = null;
					if (itr.hasNext()) {
						allocatedList = itr.next();
					}
					List<LeaveForm> leaveFormList = leaveService.getTeamLeaveDetail(allocatedList, selectMonthYear);
					request.setAttribute("leaveFormList", leaveFormList);
					// Hiding Get Team Leave Button fron User
					UserService userService = Factory.getUserService();
					List<UserForm> allocatedUserList = userService.getAllocatedResourcesDetailsBasedOnRelievingDate(userId, Calendar.getInstance());
					if(!allocatedUserList.isEmpty() && allocatedUserList.size() > 1){
						request.setAttribute("validForTeamLeaveDetail", "validForTeamLeaveDetail");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e);
				throw new Exception();
			}
			return mapping.findForward("getTeamLeaveDetail");
		}
		else
			throw new Exception();
	}

	/*
	 * getTeamLeaveDetail() method :Performing the retrieving operation from
	 * getTeamLeaveDetail page of Leave Entry
	 */
	public ActionForward getTeamLeaveDetail(ActionMapping map, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession sess =  request.getSession();
		if(sess.getAttribute("userName")!=null){
			try {
				LeaveService leaveService = LeaveServiceFactory.getLeaveService();
				String selectYear = null;
				String selectMonth = null;
				String selectMonthYear = null;
				List<LeaveForm> leaveList = null;
				selectYear = request.getParameter("iYear");
				selectMonth = request.getParameter("iMonth");
				int selectM = Integer.parseInt(selectMonth);
				selectM = selectM + 1;
				selectMonth = new Integer(selectM).toString();
				String[] monthName = { "Jan", "Feb", "Mar", "Apr", "May", "Jun",
						"Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
				if (selectMonth.equals("1")) {
					selectMonth = monthName[0];
				}
				if (selectMonth.equals("2")) {
					selectMonth = monthName[1];
				}
				if (selectMonth.equals("3")) {
					selectMonth = monthName[2];
				}
				if (selectMonth.equals("4")) {
					selectMonth = monthName[3];
				}
				if (selectMonth.equals("5")) {
					selectMonth = monthName[4];
				}
				if (selectMonth.equals("6")) {
					selectMonth = monthName[5];
				}
				if (selectMonth.equals("7")) {
					selectMonth = monthName[6];
				}
				if (selectMonth.equals("8")) {
					selectMonth = monthName[7];
				}
				if (selectMonth.equals("9")) {
					selectMonth = monthName[8];
				}
				if (selectMonth.equals("10")) {
					selectMonth = monthName[9];
				}
				if (selectMonth.equals("11")) {
					selectMonth = monthName[10];
				}
				if (selectMonth.equals("12")) {
					selectMonth = monthName[11];
				}
				selectMonthYear = selectMonth + "-" + selectYear;
				List<String> leaveDate = leaveService.getLeaveDate(selectMonthYear, request);
				request.setAttribute("leaveDateList", leaveDate);
				leaveList = leaveService.getLeave(selectMonthYear, request);
				request.setAttribute("leaveFormList", leaveList);

				int userId = Integer.parseInt(sess.getAttribute("user_id").toString());
				ReportService reportservice = ReportServiceFactory.getReportService();
				List<List<Report>> combinedList = reportservice
						.getUserReportAllocation(userId);
				Iterator<List<Report>> itr = combinedList.iterator();
				List<Report> allocatedList = null;
				if(itr.hasNext()){
					allocatedList = itr.next();
				}						
				List<LeaveForm> leaveFormList = leaveService.getTeamLeaveDetail(allocatedList, selectMonthYear);
					
				UserService userService = Factory.getUserService();
				UserForm userDetail = userService.getUsernameFromId(userId);
				Date freezeDate = userDetail.getFreeze_timesheet();
				Calendar cal = Calendar.getInstance();
				if(selectMonthYear!=null){
					DateFormat d_format = new SimpleDateFormat("dd-MMM-yyyy");
					cal.setTime(d_format.parse("01-"+selectMonthYear));
				}
				if(freezeDate != null){
					Calendar calWithFreezingDate = Calendar.getInstance();
					calWithFreezingDate.setTime(freezeDate);	
					cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
					cal.set(Calendar.HOUR_OF_DAY, 0);
					cal.set(Calendar.MINUTE, 0);
					cal.set(Calendar.SECOND, 0);
					cal.set(Calendar.MILLISECOND, 0);
					//Checking if freezing date exceeds than currentDate then blocking new entry.
					if(calWithFreezingDate.after(cal) || calWithFreezingDate.equals(cal)){
						request.setAttribute("disablingSaveAndClear", "disablingSaveAndClear");
					}
				}			
				request.setAttribute("leaveFormList", leaveFormList);
			} catch (Exception e) {
				e.printStackTrace();
				new Exception();
			}
			return map.findForward("getTeamLeaveDetail");
		}
		else throw new Exception();
	}

	/*
	 * clearTeamCheckedDate() method :Performing the removing operation from
	 * getTeamLeaveDetail page of Leave Entry
	 */

	public ActionForward clearTeamCheckedDate(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession sess =  request.getSession();
		if(sess.getAttribute("userName")!=null){
			try {
				LeaveForm leaveForm = (LeaveForm) form;
				LeaveService leaveService = LeaveServiceFactory.getLeaveService();
				String selectYear = null;
				String selectMonth = null;
				String[] checkDate = null;
				String selectMonthYear = null;
				if (request.getParameter("checkDate") != null
						&& request.getParameter("iYear") != null
						&& request.getParameter("iMonth") != null) {
					selectYear = request.getParameter("iYear");
					selectMonth = request.getParameter("iMonth");
					int selectM = Integer.parseInt(selectMonth);
					selectM = selectM + 1;
					selectMonth = new Integer(selectM).toString();
					String[] monthName = { "Jan", "Feb", "Mar", "Apr", "May",
							"Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
					if (selectMonth.equals("1")) {
						selectMonth = monthName[0];
					}
					if (selectMonth.equals("2")) {
						selectMonth = monthName[1];
					}
					if (selectMonth.equals("3")) {
						selectMonth = monthName[2];
					}
					if (selectMonth.equals("4")) {
						selectMonth = monthName[3];
					}
					if (selectMonth.equals("5")) {
						selectMonth = monthName[4];
					}
					if (selectMonth.equals("6")) {
						selectMonth = monthName[5];
					}
					if (selectMonth.equals("7")) {
						selectMonth = monthName[6];
					}
					if (selectMonth.equals("8")) {
						selectMonth = monthName[7];
					}
					if (selectMonth.equals("9")) {
						selectMonth = monthName[8];
					}
					if (selectMonth.equals("10")) {
						selectMonth = monthName[9];
					}
					if (selectMonth.equals("11")) {
						selectMonth = monthName[10];
					}
					if (selectMonth.equals("12")) {
						selectMonth = monthName[11];
					}
					selectMonthYear = selectMonth + "-" + selectYear;
					checkDate = request.getParameterValues("checkDate");
					leaveForm.setSelectMonth(selectMonthYear);
					leaveService.clearCheck(leaveForm, request, checkDate);
				}
				List<String> leaveDate = leaveService.getLeaveDate(selectMonthYear, request);
				request.setAttribute("leaveDateList", leaveDate);
				List<LeaveForm> leaveList = leaveService.getLeave(selectMonthYear, request);
				request.setAttribute("leaveFormList", leaveList);
				int userId = Integer.parseInt(sess.getAttribute("user_id").toString());
				ReportService reportservice = ReportServiceFactory.getReportService();
				List<List<Report>> combinedList = reportservice.getUserReportAllocation(userId);
				Iterator<List<Report>> itr = combinedList.iterator();
				List<Report> allocatedList = null;
				if(itr.hasNext()) {
					allocatedList = itr.next();
				}
				List<LeaveForm> leaveFormList = leaveService.getTeamLeaveDetail(allocatedList, selectMonthYear);
				request.setAttribute("leaveFormList", leaveFormList);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e);
				throw new Exception();
			}
			return mapping.findForward("getTeamLeaveDetail");
		}
		else throw new Exception();
	}

	public void generateCompleteLeaveReport(ActionMapping map,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		//get the resources that are allocated for a particular user 
		HttpSession sess = request.getSession();
		if(sess.getAttribute("userName")!=null){
			try{
				List<LeaveForm> leaveFormList= (List<LeaveForm>)sess.getAttribute("leaveListForYear");
				OutputStream out = response.getOutputStream();				
				WorkbookSettings wbSettings = new WorkbookSettings();
				wbSettings.setLocale(new Locale("en", "EN"));				
				WritableWorkbook workbook = jxl.Workbook.createWorkbook(out, wbSettings);			
				workbook.createSheet("Yearly Leave Report", 0);			
				WritableSheet excelSheet = workbook.getSheet(0);				
				excelSheet.setColumnView(0, 30);
				excelSheet.setColumnView(1, 20);
				excelSheet.setColumnView(2, 20);

				int row = 0;
				int heightInPoints = 25*15;
				excelSheet.setRowView(row, heightInPoints);	 
				getColorFonts();
				addCaption(excelSheet, 0, 0, "Resource Name", table_header);
				addCaption(excelSheet, 1, 0, "Leave Date", table_header);
				addCaption(excelSheet, 2, 0, "Type", table_header);
				Iterator<LeaveForm> leaveFormIterator=leaveFormList.iterator();
				int count=1;
				while(leaveFormIterator.hasNext()){
					LeaveForm leaveForm=leaveFormIterator.next();				
					excelSheet.setRowView(count, 30*12);
					addLabel(excelSheet, 0, count, leaveForm.getUserName(), table_content);
					addLabel(excelSheet, 1, count, leaveForm.getUpdatedDateString(), table_content);
					addLabel(excelSheet, 2, count, leaveForm.getWork_category(), table_content);
					count++;
				}
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition", "inline; filename=" +"LeaveReport.xls");
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
	private void addCaption(WritableSheet sheet, int column, int row, String s, WritableCellFormat table_header) throws RowsExceededException, WriteException {
		Label label;
		label = new Label(column, row, s, table_header);
		sheet.addCell(label);
	}
	private void addLabel(WritableSheet sheet, int column, int row, String s, WritableCellFormat table_content) throws WriteException, RowsExceededException {
		Label label;
		label = new Label(column, row, s, table_content);
		sheet.addCell(label);
	}
}
