package com.cc.entity;

import java.util.ArrayList;
import java.util.List;

public class DataManager {
	private  List<Channel> mData;
	//只要New
	/**
	 * 初始化
	 */
	public DataManager(){
		mData=new ArrayList<Channel>();
		Channel schoolChannel=new Channel("1","校园活动","0");
		//schoolChannel=new Channel("2","思想引领","0");
		mData.add(schoolChannel);
		schoolChannel.getChildren().add(new Channel("5", "社会实践", "1"));
		schoolChannel.getChildren().add(new Channel("6", "素质拓展", "1"));
		schoolChannel.getChildren().add(new Channel("7", "志愿服务", "1"));
		schoolChannel.getChildren().add(new Channel("8", "科技创新", "1"));
		schoolChannel=new Channel("2","思想引领","0");
		mData.add(schoolChannel);
		schoolChannel.getChildren().add(new Channel("9", "党课培训", "2"));
		schoolChannel.getChildren().add(new Channel("10", "共青团教育", "2"));
		schoolChannel.getChildren().add(new Channel("11", "心灵分享", "2"));
		schoolChannel=new Channel("3","就业招聘","0");
		mData.add(schoolChannel);
		schoolChannel.getChildren().add(new Channel("12", "招聘信息", "3"));
		schoolChannel.getChildren().add(new Channel("13", "通知文件", "3"));
		schoolChannel.getChildren().add(new Channel("14", "就业政策", "3"));
		schoolChannel.getChildren().add(new Channel("15", "就业情况", "3"));
		schoolChannel.getChildren().add(new Channel("17", "创业情况", "3"));
		schoolChannel=new Channel("4","考研出国","0");
		mData.add(schoolChannel);
		schoolChannel.getChildren().add(new Channel("18", "考研分析", "4"));
		schoolChannel.getChildren().add(new Channel("19", "考研动态", "4"));
		schoolChannel.getChildren().add(new Channel("20", "留学分享", "4"));

	} 
	public Channel getSchoolChannel(){
		return this.mData.get(0); 
	}
	public Channel getSchoolChannel1(){
		return this.mData.get(1); 
	}
	public Channel getSchoolChannel2(){
		return this.mData.get(2); 
	}
	public Channel getSchoolChannel3(){
		return this.mData.get(3); 
	}
	
	
}
