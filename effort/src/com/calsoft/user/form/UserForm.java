package com.calsoft.user.form;

import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.calsoft.factory.Factory;
import com.calsoft.user.model.ProjectDetailModel;
import com.calsoft.user.service.UserService;

public class UserForm extends ActionForm implements Comparable<UserForm>{
	
	private static final long serialVersionUID = 1L;
    private String newPassword;
	private String passWord;
	private String mail;
	private String confirmPassword;
	private int userId;
	private String userName;
	private String status;
	private String defpass; //Global
	private String user_role;
	private String commObj;
	private String specObj;
	private Date freeze_timesheet;
	private String freeze_timesheet_entry;
	private String exit_date;
	private String contact_no1;
	private String contact_no2;
	private String team;
	private String apollo_manager;
	private String skypeId;
	private String location;
	private String apollo_id;
	private Date start_date;
	private String start_dateString;
	private int user_role_id;
	private List<UserRoleForm> all_roles;
	private int project_id;
	private List<ProjectDetailModel> project_id_list;
	
	public String getCommObj() {
		return commObj;
	}
	public void setCommObj(String commObj) {
		this.commObj = commObj;
	}
	public String getSpecObj() {
		return specObj;
	}
	public void setSpecObj(String specObj) {
		this.specObj = specObj;
	}
	public String getUser_role() {
		return user_role;
	}
	public void setUser_role(String user_role) {
		this.user_role = user_role;
	}
	public String getNewPassword(){
		 return newPassword;
	}
	public void setNewPassword(String newPassword){
		 this.newPassword = newPassword;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public String getDefpass() {
		return defpass;
	}
	public void setDefpass(String defpass) {
		this.defpass = defpass;
	}	
	public String getContact_no1() {
		return contact_no1;
	}
	public void setContact_no1(String contact_no1) {
		this.contact_no1 = contact_no1;
	}
	public String getContact_no2() {
		return contact_no2;
	}
	public void setContact_no2(String contact_no2) {
		this.contact_no2 = contact_no2;
	}
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public String getApollo_manager() {
		return apollo_manager;
	}
	public void setApollo_manager(String apollo_manager) {
		this.apollo_manager = apollo_manager;
	}
	public String getSkypeId() {
		return skypeId;
	}
	public void setSkypeId(String skypeId) {
		this.skypeId = skypeId;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getFreeze_timesheet_entry() {
		return freeze_timesheet_entry;
	}
	public void setFreeze_timesheet_entry(String freeze_timesheet_entry) {
		this.freeze_timesheet_entry = freeze_timesheet_entry;
	}
	public String getExit_date() {
		return exit_date;
	}
	public void setExit_date(String exit_date) {
		this.exit_date = exit_date;
	}
	public String getApollo_id() {
		return apollo_id;
	}
	public void setApollo_id(String apollo_id) {
		this.apollo_id = apollo_id;
	}
	public Date getStart_date() {
		return start_date;
	}
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}
	public String getStart_dateString() {
		return start_dateString;
	}
	public void setStart_dateString(String start_dateString) {
		this.start_dateString = start_dateString;
	}
	public int getUser_role_id() {
		return user_role_id;
	}
	public void setUser_role_id(int user_role_id) {
		this.user_role_id = user_role_id;
	}
	public List<UserRoleForm> getAll_roles() {
		return all_roles;
	}
	public void setAll_roles(List<UserRoleForm> all_roles) {
		this.all_roles = all_roles;
	}
	public int getProject_id() {
		return project_id;
	}
	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}
	public List<ProjectDetailModel> getProject_id_list() {
		return project_id_list;
	}
	public void setProject_id_list(List<ProjectDetailModel> project_id_list) {
		this.project_id_list = project_id_list;
	}
	@Override
	public ActionErrors validate(ActionMapping mapping,HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		// validation for Login page starts //
		String replace=null;
		if(getStatus()==null&&getUserId()==1){
			if(passWord=="" || passWord==null){
				errors.add(passWord, new ActionMessage("error.password"));
			}
			if(newPassword==null||newPassword=="")
				errors.add(newPassword,new ActionMessage("error.newpassword.blank"));
			if(confirmPassword==null||confirmPassword=="")
				errors.add(confirmPassword,new ActionMessage("error.confirmpassword.blank"));
			if(newPassword!=null&&confirmPassword!=null&&!newPassword.equals(confirmPassword)){
				errors.add(newPassword, new ActionMessage("error.password.notmatch"));
			}			
			if((newPassword!=null&&!newPassword.isEmpty())&&(confirmPassword!=null&&!confirmPassword.isEmpty())&&newPassword.length()<5){
				errors.add(newPassword, new ActionMessage("error.password.minsize"));
			}
			if(newPassword!=null&&confirmPassword!=null&&newPassword.length()>12){
				errors.add(newPassword, new ActionMessage("error.password.maxsize"));
			}
			HttpSession session=request.getSession();
			String welcome=(String)session.getAttribute("userName");
			if(welcome!=null)
			replace=welcome.replaceFirst("Welcome, ", "");
			request.setAttribute("profilename",replace);
		}
		else if(getStatus()==null){
			if (mail == "" || mail==null){
				errors.add(mail, new ActionMessage("error.mailId"));
			}				
			else if(mail!=null){			
				Pattern p=Pattern.compile("[a-zA-Z0-9._]*+\\@+([a-zA-Z]{2,12})\\.+([a-zA-Z]{2,4})"); 
				Matcher m=p.matcher(mail);
				boolean b=m.matches();
				if(!b||mail.indexOf(".")+1==mail.indexOf("@") || mail.indexOf("_")+1==mail.indexOf("@"))
					errors.add(mail, new ActionMessage("error.mailId.invalid"));			               
			}	
			if(passWord=="" || passWord==null){
				errors.add(passWord, new ActionMessage("error.password"));
			}
			else if(passWord!=null){
				if(passWord.length()<5 && passWord.length() >0){
					errors.add(passWord, new ActionMessage("error.password.minsize"));
					
				}
				if(passWord.length()>12){
					errors.add(passWord, new ActionMessage("error.password.maxsize"));
				}
			}
		}
		// validation for Login page Ends //
		else {
			//  validation for Admin page
			if (getMail() == null || getMail() == "")
				errors.add(mail, new ActionMessage("error.invalid.email"));
			else {
				if (!getMail().contains("@") || getMail().startsWith(".")
						|| getMail().startsWith("*")
						|| getMail().startsWith("#")
						|| getMail().startsWith("$"))
					errors.add(mail, new ActionMessage("error.invalid.email"));

				else {
					StringTokenizer tokenizer = new StringTokenizer(getMail(),
					"@");
					String emailid = null;
					String domain = null;
					while (tokenizer.hasMoreTokens()) {
						try {
							emailid = tokenizer.nextToken();
							domain = tokenizer.nextToken();
							if (emailid.length() < 1 || domain.startsWith(".")
									|| domain.endsWith("."))

								errors.add(mail, new ActionMessage(
								"error.invalid.email"));
						} 
						catch (Exception e) {
							errors.add(mail, new ActionMessage("error.invalid.email"));
						}
					}
				}
			}
			if (getUserName() == null || getUserName() == "")
				errors.add("userName",
						new ActionMessage("error.empty.user"));
			else {
				if (getUserName().startsWith(" "))
					errors.add(userName, new ActionMessage(
					"error.space.user"));
				else {
					String test[] = { "0", "1", "2", "3", "4", "5", "6",
							"7", "8", "9" };
					for (int i = 0; i < 10; i++) {
						String sequence = test[i];
						if (userName.startsWith(sequence)) {
							errors.add("userName", new ActionMessage(
							"error.number.user"));
							break;
						}
					}
				}
			}					
		}
		UserService service = Factory.getUserService();
		java.util.List<UserForm> list = null;
		try {
			list = service.getUserNames();
			request.setAttribute("userlist", list);
		} 
		catch (Exception e) {
			e.printStackTrace();
			String errorMessage="Server encountered some problem please try again after some time or contact administrator";
			request.setAttribute("errorMessage",errorMessage);
		}
		return errors;
	}
	@Override
	public int compareTo(UserForm o) {
		// Compare based on userId
		return ((Integer)getUserId()).compareTo(o.getUserId());
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + userId;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserForm other = (UserForm) obj;
		if (userId != other.userId)
			return false;
		return true;
	}
	public Date getFreeze_timesheet() {
		return freeze_timesheet;
	}
	public void setFreeze_timesheet(Date freeze_timesheet) {
		this.freeze_timesheet = freeze_timesheet;
	}
	
}


