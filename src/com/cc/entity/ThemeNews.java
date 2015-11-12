package com.cc.entity;

public class ThemeNews implements java.io.Serializable{
	private String subject_date,subject_detail,subject_title,subject_url;
	private int subject_id;
	public String getSubject_date() {
		return subject_date;
	}
	public void setSubject_date(String subject_date) {
		this.subject_date = subject_date;
	}
	public String getSubject_detail() {
		return subject_detail;
	}
	public void setSubject_detail(String subject_detail) {
		this.subject_detail = subject_detail;
	}
	public String getSubject_title() {
		return subject_title;
	}
	public void setSubject_title(String subject_title) {
		this.subject_title = subject_title;
	}
	public String getSubject_url() {
		return subject_url;
	}
	public void setSubject_url(String subject_url) {
		this.subject_url = subject_url;
	}
	public int getSubject_id() {
		return subject_id;
	}
	public void setSubject_id(int subject_id) {
		this.subject_id = subject_id;
	}
	public ThemeNews(String subject_date, String subject_detail,
			String subject_title, String subject_url, int subject_id) {
		super();
		this.subject_date = subject_date;
		this.subject_detail = subject_detail;
		this.subject_title = subject_title;
		this.subject_url = subject_url;
		this.subject_id = subject_id;
	}
	public ThemeNews() {
		super();
	}
	
}
