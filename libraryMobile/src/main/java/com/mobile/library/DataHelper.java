package com.mobile.library;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;

import com.mobile.library.storage.sharedprefrences.SharePreferenceHelper;
import com.mobile.library.storage.sqlite.DataBaseOpenHelper;
import com.mobile.library.storage.sqlite.OnSqliteUpdateListener;

import java.util.List;

/**
 * 持久化数据模块外部调用入口
 */
public class DataHelper {

	private static DataHelper self;
	private SharePreferenceHelper preferenceHelper;
	private DataBaseOpenHelper dataBaseOpenHelper;

	public static DataHelper getInstance() {
		if (self == null) {
			self = new DataHelper();
		}
		return self;
	}

	private DataHelper() {
		preferenceHelper = new SharePreferenceHelper();
	}

	public int getVersionNumber() {
		return preferenceHelper.getVersionNumber();
	}

	public void setVersionNumber(int versionNumber) {
		preferenceHelper.setVersionNumber(versionNumber);
	}

	public SharedPreferences getSharedPreferences(Context context) {
		return preferenceHelper.getSharedPreferences(context);
	}
	public DataBaseOpenHelper getDBInstance(Context context, String dbName, int dbVersion, List<String> tableSqls) {
		dataBaseOpenHelper = DataBaseOpenHelper.getInstance(context, dbName, dbVersion, tableSqls);
		return dataBaseOpenHelper;
	}

	public void execSQL(String sql, Object[] bindArgs) {
		dataBaseOpenHelper.execSQL(sql, bindArgs);
	}

	public Cursor rawQuery(String sql, String[] bindArgs) {
		return dataBaseOpenHelper.rawQuery(sql, bindArgs);
	}

	public void insert(String table, ContentValues contentValues) {
		dataBaseOpenHelper.insert(table, contentValues);
	}
	public void delete(String table, String whereClause, String[] whereArgs) {
		dataBaseOpenHelper.delete(table, whereClause, whereArgs);
	}

	public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
		return dataBaseOpenHelper.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
	}
	public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy,
			String limit) {
		return dataBaseOpenHelper.query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
	}

	public Cursor query(String tableName, String sqlString) {
		return dataBaseOpenHelper.query(tableName, sqlString);
	}

	public void update(String table, ContentValues values, String whereClause, String[] whereArgs) {
		dataBaseOpenHelper.update(table, values, whereClause, whereArgs);
	}
	public void clear() {
		dataBaseOpenHelper.clear();
	}

	public void setOnSqliteUpdateListener(OnSqliteUpdateListener onSqliteUpdateListener) {
		dataBaseOpenHelper.setOnSqliteUpdateListener(onSqliteUpdateListener);
	}
}
