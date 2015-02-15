package com.calsoft.user.form;

import org.apache.struts.action.ActionForm;

@SuppressWarnings("serial")
public class AppraisalForm extends ActionForm{
	private String commObjective;
	private String specObjective;
	public String getCommObjective() {
		return commObjective;
	}
	public void setCommObjective(String commObjective) {
		this.commObjective = commObjective;
	}
	public String getSpecObjective() {
		return specObjective;
	}
	public void setSpecObjective(String specObjective) {
		this.specObjective = specObjective;
	}	
}
