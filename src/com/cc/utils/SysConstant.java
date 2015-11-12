package com.cc.utils;

public final class SysConstant {
	/**
	 * 用户表相关的常量
	 * @author wei
	 *
	 */
	public static final class CCUserTable{
		public static final String TABLE_NAME="ccuser";
		public static final String COL_ID="ID";
		public static final String COL_NAME="STUNAME";
		public static final String COL_CLZSS="CLZSS";
		public static final String COL_MAJOR="MAJOR";
		
		public static String getCreateSQL(){
			StringBuilder sb =new StringBuilder();
			sb.append("CREATE TABLE IF NOT EXISTS ");
			sb.append(TABLE_NAME);
			sb.append("(");
			//定义列  
			//定义ID
			sb.append(COL_ID);
			sb.append(" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,");
			//定义第二列
			sb.append(COL_NAME);   
			sb.append(" varchar(30) not null,");
			sb.append(COL_CLZSS);
			sb.append(" varchar(20),");
			sb.append(COL_MAJOR);
			sb.append(" varchar(20)");		
			sb.append(")");
			return sb.toString();   
		}
	}
	public static final class LoginUserTable{
		public static final String TABLE_NAME="userinfotable";
		public static final String COL_ID="uno";
		public static final String COL_NAME="className";
		public static final String COL_CLZSS="stuName";
		public static final String COL_MAJOR="majorNO";
		
		public static String getCreateSQL(){
			StringBuilder sb =new StringBuilder();
			sb.append("CREATE TABLE IF NOT EXISTS ");
			sb.append(TABLE_NAME);
			sb.append("(");
			//定义列  
			//定义ID
			sb.append(COL_ID);
			sb.append(" varchar(20) PRIMARY KEY ,");
			//定义第二列
			sb.append(COL_NAME);   
			sb.append(" varchar(30) not null,");
			sb.append(COL_CLZSS);
			sb.append(" varchar(20),");
			sb.append(COL_MAJOR);
			sb.append(" varchar(20)");		
			sb.append(")");
			return sb.toString();   
		}
	}
	public static final class NewsItemTable{
		public static final String TABLE_NAME="newsitemtable";
		public static final String COL_ID="id";
		public static final String COL_TITLE="title";
		public static final String COL_IMG="img";
		public static final String COL_TIME="time";
		public static final String COL_CONTENT="content";
		
		public static String getCreateSQL(){
			StringBuilder sb =new StringBuilder();
			sb.append("CREATE TABLE IF NOT EXISTS ");
			sb.append(TABLE_NAME);
			sb.append("(");
			//定义列  
			//定义ID
			sb.append(COL_ID);
			sb.append(" varchar(20) PRIMARY KEY ,");
			//定义第二列
			sb.append(COL_TITLE);   
			sb.append(" varchar(30) not null,");
			sb.append(COL_IMG);
			sb.append(" varchar(20),");
			sb.append(COL_TIME);
			sb.append(" varchar(20),");
			sb.append(COL_CONTENT);
			sb.append(" varchar(20)");		
			sb.append(")");
			return sb.toString();   
		}
	}
}
