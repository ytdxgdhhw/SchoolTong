package com.cc.entity;

import java.util.ArrayList;
import java.util.List;

public class DataManager {
	private  List<Channel> mData;
	//ֻҪNew
	/**
	 * ��ʼ��
	 */
	public DataManager(){
		mData=new ArrayList<Channel>();
		Channel schoolChannel=new Channel("1","У԰�","0");
		//schoolChannel=new Channel("2","˼������","0");
		mData.add(schoolChannel);
		schoolChannel.getChildren().add(new Channel("5", "���ʵ��", "1"));
		schoolChannel.getChildren().add(new Channel("6", "������չ", "1"));
		schoolChannel.getChildren().add(new Channel("7", "־Ը����", "1"));
		schoolChannel.getChildren().add(new Channel("8", "�Ƽ�����", "1"));
		schoolChannel=new Channel("2","˼������","0");
		mData.add(schoolChannel);
		schoolChannel.getChildren().add(new Channel("9", "������ѵ", "2"));
		schoolChannel.getChildren().add(new Channel("10", "�����Ž���", "2"));
		schoolChannel.getChildren().add(new Channel("11", "�������", "2"));
		schoolChannel=new Channel("3","��ҵ��Ƹ","0");
		mData.add(schoolChannel);
		schoolChannel.getChildren().add(new Channel("12", "��Ƹ��Ϣ", "3"));
		schoolChannel.getChildren().add(new Channel("13", "֪ͨ�ļ�", "3"));
		schoolChannel.getChildren().add(new Channel("14", "��ҵ����", "3"));
		schoolChannel.getChildren().add(new Channel("15", "��ҵ���", "3"));
		schoolChannel.getChildren().add(new Channel("17", "��ҵ���", "3"));
		schoolChannel=new Channel("4","���г���","0");
		mData.add(schoolChannel);
		schoolChannel.getChildren().add(new Channel("18", "���з���", "4"));
		schoolChannel.getChildren().add(new Channel("19", "���ж�̬", "4"));
		schoolChannel.getChildren().add(new Channel("20", "��ѧ����", "4"));

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
