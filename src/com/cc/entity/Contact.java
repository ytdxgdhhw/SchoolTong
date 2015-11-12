package com.cc.entity;

import java.util.List;

public class Contact implements java.io.Serializable{
	private String id;
	private String name;
	private int type;
	private boolean isSelected;
	private List<Contact> nodes;
	public List<Contact> getNodes() {
		return nodes;
	}
	public void setNodes(List<Contact> nodes) {
		this.nodes = nodes;
	}
	public static final int ALL_MAJOR=1;//代表专业的一级结点
	public static final int ALL_ROLE=2;//代表职务的一级结点
	public static final int MAJOR=3;//代表专业节点
	public static final int ROLE=4;//代表职务节点
	public static final int CLZSS=5;//代表班级节点
	public static final int STUDENT=6;//代表班级节点
	public boolean isSelected() {
		return isSelected;
	}
	public Contact(String id, String name, int type) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
	}
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Contact(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public Contact() {
		super();
	}
	
	 
}
