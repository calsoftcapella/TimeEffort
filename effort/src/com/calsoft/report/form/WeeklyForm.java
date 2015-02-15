package com.calsoft.report.form;

import java.util.Date;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.calsoft.report.model.Constraints;
import com.calsoft.report.model.Deliverables;
import com.calsoft.report.model.Recruitment_status;

@SuppressWarnings("serial")
public class WeeklyForm extends ActionForm{
	private Date startDate;
    private Date endDate;
    private String statusDate ;
    private int user_id;
    private int event_id;    
	private String eventInfo;
    private String detail_mile;
    private String owner_mile;
    private String remark_mile;
    private String onDate_const;
    private String detail_const;
    private String owner_const;
    private String remark_const;
    private String eta_const;
    private String achievements;
    private String role_status;
    private String position;
    private String internalInter;
    private String apolloInter;
    private String selectedOff;
    private String joined;
    private Set<Deliverables> deliList;
    private Set<Constraints> constList;
    private Set<Recruitment_status> recrList;
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getStatusDate() {
		return statusDate;
	}
	public void setStatusDate(String statusDate) {
		this.statusDate = statusDate;
	}
	public String getEventInfo() {
		return eventInfo;
	}
	public void setEventInfo(String eventInfo) {
		this.eventInfo = eventInfo;
	}
	public String getDetail_mile() {
		return detail_mile;
	}
	public void setDetail_mile(String detail_mile) {
		this.detail_mile = detail_mile;
	}
	public String getOwner_mile() {
		return owner_mile;
	}
	public void setOwner_mile(String owner_mile) {
		this.owner_mile = owner_mile;
	}
	public String getRemark_mile() {
		return remark_mile;
	}
	public void setRemark_mile(String remark_mile) {
		this.remark_mile = remark_mile;
	}
	public String getOnDate_const() {
		return onDate_const;
	}
	public void setOnDate_const(String onDate_const) {
		this.onDate_const = onDate_const;
	}
	public String getDetail_const() {
		return detail_const;
	}
	public void setDetail_const(String detail_const) {
		this.detail_const = detail_const;
	}
	public String getOwner_const() {
		return owner_const;
	}
	public void setOwner_const(String owner_const) {
		this.owner_const = owner_const;
	}
	public String getRemark_const() {
		return remark_const;
	}
	public void setRemark_const(String remark_const) {
		this.remark_const = remark_const;
	}
	public String getEta_const() {
		return eta_const;
	}
	public void setEta_const(String eta_const) {
		this.eta_const = eta_const;
	}
	public String getAchievements() {
		return achievements;
	}
	public void setAchievements(String achievements) {
		this.achievements = achievements;
	}
	public String getRole_status() {
		return role_status;
	}
	public void setRole_status(String role_status) {
		this.role_status = role_status;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getInternalInter() {
		return internalInter;
	}
	public void setInternalInter(String internalInter) {
		this.internalInter = internalInter;
	}
	public String getApolloInter() {
		return apolloInter;
	}
	public void setApolloInter(String apolloInter) {
		this.apolloInter = apolloInter;
	}
	public String getSelectedOff() {
		return selectedOff;
	}
	public void setSelectedOff(String selectedOff) {
		this.selectedOff = selectedOff;
	}
	public String getJoined() {
		return joined;
	}
	public void setJoined(String joined) {
		this.joined = joined;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getEvent_id() {
		return event_id;
	}
	public void setEvent_id(int event_id) {
		this.event_id = event_id;
	}
    
   public Set<Deliverables> getDeliList() {
		return deliList;
	}
	public void setDeliList(Set<Deliverables> deliList) {
		this.deliList = deliList;
	}
	public Set<Constraints> getConstList() {
		return constList;
	}
	public void setConstList(Set<Constraints> constList) {
		this.constList = constList;
	}
	public Set<Recruitment_status> getRecrList() {
		return recrList;
	}
	public void setRecrList(Set<Recruitment_status> recrList) {
		this.recrList = recrList;
	}
@Override
public void reset(ActionMapping mapping, HttpServletRequest request) {
	// TODO Auto-generated method stub
	   
}
    
}
