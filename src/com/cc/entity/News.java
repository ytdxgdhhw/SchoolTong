package com.cc.entity;

import java.sql.Date;

public class News implements java.io.Serializable{
	private String advertisement,author,channelType,comment,content;
	private String img,mainpagetag,shopAddress;
	private String shopName,time,title,xls;
	private int commentcount,favor,favorTag,id,mark,pagetag,pagetagflag,tag;
	private Date maxDate,mainDate;
	public String getAdvertisement() {
		return advertisement;
	}
	public void setAdvertisement(String advertisement) {
		this.advertisement = advertisement;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getChannelType() {
		return channelType;
	}
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public Date getMainDate() {
		return mainDate;
	}
	public void setMainDate(Date mainDate) {
		this.mainDate = mainDate;
	}
	public String getMainpagetag() {
		return mainpagetag;
	}
	public void setMainpagetag(String mainpagetag) {
		this.mainpagetag = mainpagetag;
	}
	public Date getMaxDate() {
		return maxDate;
	}
	public void setMaxDate(Date maxDate) {
		this.maxDate = maxDate;
	}
	public String getShopAddress() {
		return shopAddress;
	}
	public void setShopAddress(String shopAddress) {
		this.shopAddress = shopAddress;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getXls() {
		return xls;
	}
	public void setXls(String xls) {
		this.xls = xls;
	}
	public int getCommentcount() {
		return commentcount;
	}
	public void setCommentcount(int commentcount) {
		this.commentcount = commentcount;
	}
	public int getFavor() {
		return favor;
	}
	public void setFavor(int favor) {
		this.favor = favor;
	}
	public int getFavorTag() {
		return favorTag;
	}
	public void setFavorTag(int favorTag) {
		this.favorTag = favorTag;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMark() {
		return mark;
	}
	public void setMark(int mark) {
		this.mark = mark;
	}
	public int getPagetag() {
		return pagetag;
	}
	public void setPagetag(int pagetag) {
		this.pagetag = pagetag;
	}
	public int getPagetagflag() {
		return pagetagflag;
	}
	public void setPagetagflag(int pagetagflag) {
		this.pagetagflag = pagetagflag;
	}
	public int getTag() {
		return tag;
	}
	public void setTag(int tag) {
		this.tag = tag;
	}
	public News(String advertisement, String author, String channelType,
			String comment, String content, String img, Date mainDate,
			String mainpagetag, Date maxDate, String shopAddress,
			String shopName, String time, String title, String xls,
			int commentcount, int favor, int favorTag, int id, int mark,
			int pagetag, int pagetagflag, int tag) {
		super();
		this.advertisement = advertisement;
		this.author = author;
		this.channelType = channelType;
		this.comment = comment;
		this.content = content;
		this.img = img;
		this.mainDate = mainDate;
		this.mainpagetag = mainpagetag;
		this.maxDate = maxDate;
		this.shopAddress = shopAddress;
		this.shopName = shopName;
		this.time = time;
		this.title = title;
		this.xls = xls;
		this.commentcount = commentcount;
		this.favor = favor;
		this.favorTag = favorTag;
		this.id = id;
		this.mark = mark;
		this.pagetag = pagetag;
		this.pagetagflag = pagetagflag;
		this.tag = tag;
	}
	public News() {
		super();
	}
	
}
