package com.cc.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cc.entity.CCUser;
import com.cc.utils.SysConstant.LoginUserTable;

public class CCUserDao {
	private DBHelper dbHelper;

	public CCUserDao(Context mContext) {
		super();
		this.dbHelper = new DBHelper(mContext);
	}
	public void saveCCUser(CCUser user){
		SQLiteDatabase db=this.dbHelper.getWritableDatabase();
		ContentValues values=new ContentValues();
		values.put(LoginUserTable.COL_ID, user.getUno());
		values.put(LoginUserTable.COL_NAME, user.getStuName());
		values.put(LoginUserTable.COL_CLZSS, user.getClassName());
		values.put(LoginUserTable.COL_MAJOR, user.getMajorName());
		db.insert(LoginUserTable.TABLE_NAME, null, values);
	}
	public void SaveOrUpdateCCUser(CCUser user){
		if(checkUser(user.getUno())){
			//¸üÐÂ
			delCCUser(user);
			
		}
		saveCCUser(user);
	}
	public void delCCUser(CCUser user){
		SQLiteDatabase db=this.dbHelper.getWritableDatabase();
		db.delete(LoginUserTable.TABLE_NAME,
				LoginUserTable.COL_ID+" =?", new String[]{user.getUno()});
	}
	public boolean checkUser(String uno){
		boolean flag=false;
		SQLiteDatabase db=this.dbHelper.getWritableDatabase();
		Cursor c=db.query(LoginUserTable.TABLE_NAME, 
				new String[]{LoginUserTable.COL_ID}, 
				LoginUserTable.COL_ID+"=?", new String[]{uno}, null, null, null);
		if(c.moveToNext()){
			flag=true;
		}else{
			flag=false;
		}
		c.close();
		db.close();
		return flag;
	}
	public CCUser findLoginUserByUno(String uno){
		SQLiteDatabase db=this.dbHelper.getReadableDatabase();
		Cursor c=db.query(LoginUserTable.TABLE_NAME, 
				new String[]{LoginUserTable.COL_ID,LoginUserTable.COL_NAME,
				LoginUserTable.COL_CLZSS,LoginUserTable.COL_MAJOR}, 
				LoginUserTable.COL_ID+"=?", new String[]{uno}, null, null, null);
		CCUser user=null;
		if(c.moveToNext()){
			user=new CCUser();
			user.setUno(c.getString(c.getColumnIndex(LoginUserTable.COL_ID)));
			user.setStuName(c.getString(c.getColumnIndex(LoginUserTable.COL_NAME)));
			user.setClassName(c.getString(c.getColumnIndex(LoginUserTable.COL_CLZSS)));
			user.setMajorName(c.getString(c.getColumnIndex(LoginUserTable.COL_MAJOR)));
		}
		c.close();
		db.close();
		return user;
	}
}
