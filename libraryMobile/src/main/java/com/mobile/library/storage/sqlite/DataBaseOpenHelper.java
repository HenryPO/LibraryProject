package com.mobile.library.storage.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据库工具类
 *
 * @author lihy
 * 2017-03-31
 */
public class DataBaseOpenHelper extends SQLiteOpenHelper {
    private static Map<String, DataBaseOpenHelper> dbMaps = new HashMap<String, DataBaseOpenHelper>();
    private OnSqliteUpdateListener onSqliteUpdateListener;
    /**
     * 建表语句列表
     */
    private List<String>           createTableList;
    private String                 nowDbName;

    private DataBaseOpenHelper(Context context, String dbName, int dbVersion, List<String> tableSqls) {
        super(context, dbName, null, dbVersion);
        nowDbName = dbName;
        createTableList = new ArrayList<String>();
        createTableList.addAll(tableSqls);
    }

    /**
     * 获取数据库实例
     *
     * @param context   当前环境
     * @param dbName    数据库名称
     * @param dbVersion 数据库版本
     * @param tableSqls 建表语句
     * @return 数据库工具类
     * @author lihy
     * 2017-03-31
     */
    public static DataBaseOpenHelper getInstance(Context context, String dbName, int dbVersion,
                                                 List<String> tableSqls) {
        DataBaseOpenHelper dataBaseOpenHelper = dbMaps.get(dbName);
        if (dataBaseOpenHelper == null) {
            dataBaseOpenHelper = new DataBaseOpenHelper(context, dbName, dbVersion, tableSqls);
        }
        dbMaps.put(dbName, dataBaseOpenHelper);
        return dataBaseOpenHelper;
    }

    ;

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (String sqlString : createTableList) {
            db.execSQL(sqlString);
        }
    }

    /**
     * Sql写入
     *
     * @param sql 语句
     * @param bindArgs 参数
     * @author lihy
     * 2017-03-31
     */
    public void execSQL(String sql, Object[] bindArgs) {
        DataBaseOpenHelper dataBaseOpenHelper = dbMaps.get(nowDbName);
        synchronized (dataBaseOpenHelper) {
            SQLiteDatabase database = dataBaseOpenHelper.getWritableDatabase();
            database.execSQL(sql, bindArgs);
        }
    }

    /**
     * sql查询
     *
     * @param sql 语句
     * @param bindArgs 参数
     * @return 游标
     * @author lihy
     * 2017-03-31
     */
    public Cursor rawQuery(String sql, String[] bindArgs) {
        DataBaseOpenHelper dataBaseOpenHelper = dbMaps.get(nowDbName);
        synchronized (dataBaseOpenHelper) {
            SQLiteDatabase database = dataBaseOpenHelper.getReadableDatabase();
            Cursor         cursor   = database.rawQuery(sql, bindArgs);
            return cursor;
        }
    }

    /**
     * 插入数据
     *
     * @param table 表名
     * @param contentValues 数据
     * @author lihy
     * 2017-03-31
     */
    public void insert(String table, ContentValues contentValues) {
        DataBaseOpenHelper dataBaseOpenHelper = dbMaps.get(nowDbName);
        synchronized (dataBaseOpenHelper) {
            SQLiteDatabase database = dataBaseOpenHelper.getWritableDatabase();
            database.insert(table, null, contentValues);
        }
    }

    /**
     * 更新
     *
     * @param table 表名
     * @param values 数据
     * @param whereClause 条件
     * @param whereArgs 条件参数
     * @author lihy
     * 2017-03-31
     */
    public void update(String table, ContentValues values, String whereClause, String[] whereArgs) {
        DataBaseOpenHelper dataBaseOpenHelper = dbMaps.get(nowDbName);
        synchronized (dataBaseOpenHelper) {
            SQLiteDatabase database = dataBaseOpenHelper.getWritableDatabase();
            database.update(table, values, whereClause, whereArgs);
        }
    }

    /**
     * 删除
     *
     * @param table 表名
     * @param whereClause 条件
     * @param whereArgs 条件参数
     * @author lihy
     * 2017-03-31
     */
    public void delete(String table, String whereClause, String[] whereArgs) {
        DataBaseOpenHelper dataBaseOpenHelper = dbMaps.get(nowDbName);
        synchronized (dataBaseOpenHelper) {
            SQLiteDatabase database = dataBaseOpenHelper.getWritableDatabase();
            database.delete(table, whereClause, whereArgs);
        }
    }

    /**
     * 查
     *
     * @param table 表名
     * @param columns 查询列
     * @param selection 查询语句
     * @param selectionArgs 查询参数
     * @param groupBy 汇总字段
     * @param having 包含字段
     * @param orderBy 排序字段
     * @return 游标
     * @author lihy
     * 2017-03-31
     */
    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy,
                        String having, String orderBy) {
        DataBaseOpenHelper dataBaseOpenHelper = dbMaps.get(nowDbName);
        synchronized (dataBaseOpenHelper) {
            SQLiteDatabase database = dataBaseOpenHelper.getReadableDatabase();
            // Cursor cursor = database.rawQuery("select * from "
            // + TableName.TABLE_NAME_USER + " where userId =" + userId, null);
            Cursor cursor = database.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
            return cursor;
        }
    }

    /**
     * 查
     *
     * @param table 表名
     * @param columns 查询列
     * @param selection 查询语句
     * @param selectionArgs 查询参数
     * @param groupBy 汇总字段
     * @param having 包含字段
     * @param orderBy 排序字段
     * @param limit 限制字段
     * @return 游标
     * @author lihy
     * 2017-03-31
     */
    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy,
                        String having, String orderBy, String limit) {
        DataBaseOpenHelper dataBaseOpenHelper = dbMaps.get(nowDbName);
        synchronized (dataBaseOpenHelper) {
            SQLiteDatabase database = dataBaseOpenHelper.getReadableDatabase();
            // Cursor cursor = database.rawQuery("select * from "
            // + TableName.TABLE_NAME_USER + " where userId =" + userId, null);
            Cursor cursor = database.query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
            return cursor;
        }
    }

    /**
     * 查询，方法重载,table表名，sqlString条件
     *
     * @param tableName 表名
     * @param sqlString 条件
     * @return 游标
     * @author lihy
     * 2017-03-31
     */
    public Cursor query(String tableName, String sqlString) {
        DataBaseOpenHelper dataBaseOpenHelper = dbMaps.get(nowDbName);
        synchronized (dataBaseOpenHelper) {
            SQLiteDatabase database = dataBaseOpenHelper.getReadableDatabase();
            Cursor         cursor   = database.rawQuery("select * from " + tableName + " " + sqlString, null);

            return cursor;
        }
    }

    /**
     * 清空数据库
     * @author lihy
     * 2017-03-31
    */
    public void clear() {
        DataBaseOpenHelper dataBaseOpenHelper = (DataBaseOpenHelper) dbMaps.get(this.nowDbName);
        dataBaseOpenHelper.close();
        dbMaps.remove(dataBaseOpenHelper);
    }

    /**
     * onUpgrade()方法在数据库版本每次发生变化时都会把用户手机上的数据库表删除，然后再重新创建。<br/>
     * 一般在实际项目中是不能这样做的，正确的做法是在更新数据库表结构时，还要考虑用户存放于数据库中的数据不会丢失,从版本几更新到版本几。(非
     * Javadoc)
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        if (onSqliteUpdateListener != null) {
            onSqliteUpdateListener.onSqliteUpdateListener(db, arg1, arg2);
        }
    }

    public void setOnSqliteUpdateListener(OnSqliteUpdateListener onSqliteUpdateListener) {
        this.onSqliteUpdateListener = onSqliteUpdateListener;
    }
}
