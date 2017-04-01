package com.mobile.library.storage.sqlite;

import android.database.sqlite.SQLiteDatabase;
/**
 * 
* @ClassName: OnSqliteUpdateListener 
* @Description: 数据库更新 
* @author lhy
* @date 2014-10-9 下午2:35:02 
*
 */
public interface OnSqliteUpdateListener {
	public void onSqliteUpdateListener(SQLiteDatabase db, int oldVersion, int newVersion);
}
