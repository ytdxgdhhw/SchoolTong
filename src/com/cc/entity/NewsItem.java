package com.cc.entity;

public class NewsItem implements
java.io.Serializable{
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	private int id;
	private String title;
	private String img;
	private String time;
	private String content;
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getTime() {
		return time;
	}
	public NewsItem() {
		super();
	}
	public NewsItem(String title, String img, String time) {
		super();
		this.title = title;
		this.img = img;
		this.time = time;
	}

	public NewsItem(String title, String img, String time, String content) {
		super();
		this.title = title;
		this.img = img;
		this.time = time;
		this.content = content;
	}
	public void setTime(String time) {
		this.time = time;
	}
}
