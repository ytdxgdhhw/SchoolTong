package com.cc.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cc.entity.NewsItem;
import com.cc.utils.SysConstant.NewsItemTable;

public class NewsItemDao {
	private DBHelper dbHelper;

	public NewsItemDao(Context context) {
		super();
		this.dbHelper=new DBHelper(context);
		// TODO Auto-generated constructor stub
	}
	public void addNewsItem(NewsItem item){
		SQLiteDatabase dataBase=dbHelper.getWritableDatabase();
		String insertSQL = "INSERT INTO " + NewsItemTable.TABLE_NAME;
		insertSQL += "(";
		insertSQL += NewsItemTable.COL_TITLE;
		insertSQL += ",";
		insertSQL += NewsItemTable.COL_IMG;
		insertSQL += ",";
		insertSQL += NewsItemTable.COL_TIME;
		insertSQL += ",";
		insertSQL += NewsItemTable.COL_CONTENT;
		insertSQL += ") VALUES(?,?,?,?)";
		try {
			dataBase.execSQL(
					insertSQL,
					new String[] { item.getTitle(), item.getImg(),
							item.getTime(),item.getContent() });
		} catch (Exception e) {

		} finally {
		dataBase.close();
		}
		
	}
	public void addNewsItem(List<NewsItem> items){
		for (NewsItem u : items) {
			addNewsItem(u);
		}
	}
	public boolean checkNewsItem(String code){
		return true;
	}
	public List<NewsItem> findNewsItemByPageTag(String pageTag){
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		String querySQL = "SELECT ";
		querySQL += NewsItemTable.COL_ID + ",";
		querySQL += NewsItemTable.COL_TITLE + ",";
		querySQL += NewsItemTable.COL_IMG + ",";
		querySQL += NewsItemTable.COL_TIME + ",";
		querySQL += NewsItemTable.COL_CONTENT;
		querySQL += " FROM ";
		querySQL += NewsItemTable.TABLE_NAME;
		Cursor c = db.rawQuery(querySQL, null);
		List<NewsItem> list=new ArrayList<NewsItem>();
		while (c.moveToNext()) {
			// 取数据
			NewsItem user = new NewsItem();
			// 取数据库当前行的id值
			// 取ID列在表中的索引
			int idIndex = c.getColumnIndex(NewsItemTable.COL_ID);
			// 根据索引，去当前行的id列的值
			int id = c.getInt(idIndex);
			user.setId(id);
			String title = c.getString(c.getColumnIndex(NewsItemTable.COL_TITLE));
			user.setTitle(title);
			user.setImg(c.getString(c.getColumnIndex(NewsItemTable.COL_IMG)));
			user.setTime(c.getString(c.getColumnIndex(NewsItemTable.COL_TIME)));
			user.setContent(c.getString(c.getColumnIndex(NewsItemTable.COL_CONTENT)));
			list.add(user);
		}
		db.close();
		return list;
	}
}
