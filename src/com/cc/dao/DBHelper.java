package com.cc.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.cc.utils.SysConstant;

public class DBHelper extends SQLiteOpenHelper {
	public static final String DB_NAME="jredu.db";
	public static final int DB_VERSION=1;
	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}
	public DBHelper(Context context){
		this(context,DB_NAME,null,DB_VERSION);
	}
	 
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		//创建你的表结构 
		//db.execSQL("creat table if not exists users(id,name)");
		db.execSQL(SysConstant.CCUserTable.getCreateSQL());
		db.execSQL(SysConstant.LoginUserTable.getCreateSQL());
		db.execSQL(SysConstant.NewsItemTable.getCreateSQL());
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		onCreate(arg0);
	}
}
