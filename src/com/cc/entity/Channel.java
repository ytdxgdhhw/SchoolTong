package com.cc.entity;

import java.util.ArrayList;
import java.util.List;

public class Channel implements java.io.Serializable{
	private String cId;
	private String cTitle;
	private String cPid;
	
	private List<Channel> children;
 	public List<Channel> getChildren() {
		return children;
	}
	public String getcId() {
		return cId;
	}
	public void setcId(String cId) {
		this.cId = cId;
	}
	public String getcTitle() {
		return cTitle;
	}
	public void setcTitle(String cTitle) {
		this.cTitle = cTitle;
	}
	public String getcPid() {
		return cPid;
	}
	public void setcPid(String cPid) {
		this.cPid = cPid;
	}
	public Channel() {
		super();
		this.children=new ArrayList<Channel>();
	}
	public Channel(String cId, String cTitle, String cPid) {
		super();
		this.cId = cId;
		this.cTitle = cTitle;
		this.cPid = cPid;
		this.children=new ArrayList<Channel>();
	}
}
