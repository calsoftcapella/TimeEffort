package com.calsoft.user.form;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

@SuppressWarnings("serial")
public class ClientFeedbackForm extends ActionForm{
    private int feedbackId;
	private String to_list;
	private String subject_line = "";
	private String share_button = "";
	private FormFile uploded_file;
	private String body_content;
	private String formatted_body_content;
	private String file_loc;
	private String username;
	public String getTo_list() {
		return to_list;
	}
	public void setTo_list(String to_list) {
		this.to_list = to_list;
	}
	public String getSubject_line() {
		return subject_line;
	}
	public void setSubject_line(String subject_line) {
		this.subject_line = subject_line;
	}
	public String getShare_button() {
		return share_button;
	}
	public void setShare_button(String share_button) {
		this.share_button = share_button;
	}	
	public FormFile getUploded_file() {
		return uploded_file;
	}
	public void setUploded_file(FormFile uploded_file) {
		this.uploded_file = uploded_file;
	}
	public String getBody_content() {
		return body_content;
	}
	public void setBody_content(String body_content) {
		this.body_content = body_content;
	}
	public String getFormatted_body_content() {
		return formatted_body_content;
	}
	public void setFormatted_body_content(String formatted_body_content) {
		this.formatted_body_content = formatted_body_content;
	}
	public String getFile_loc() {
		return file_loc;
	}
	public void setFile_loc(String file_loc) {
		this.file_loc = file_loc;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getFeedbackId() {
		return feedbackId;
	}
	public void setFeedbackId(int feedbackId) {
		this.feedbackId = feedbackId;
	}
	
	/*@Override
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors  errors = new ActionErrors();
		try{
			FormFile file = getUploded_file();
			System.out.println("Priting uploaded file detail"+file+"+" );
			if(file != null && !file.equals("")){
				if(file.getFileSize()== 0){
					errors.add("common.file.err", new ActionMessage("error.common.file.required"));
					return errors;
				}		    	    
				if(!("image/png".equals(file.getContentType())) || !("image/jpg".equals(file.getContentType()))){
					errors.add("common.file.err.ext", new ActionMessage("error.common.file.format"));
					return errors;
				}		    	
				if(file.getFileSize() > 1024000){ //1000kb
					errors.add("common.file.err.size", new ActionMessage("error.common.file.size.limit", 1024000));
					return errors;
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return errors;
	}*/
}
