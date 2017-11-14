package com.example.wangqian.happinesshunter.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库创建辅助类 完成数据库的创建和升级
 */
public class DBOpenHelper extends SQLiteOpenHelper {
	public static final String DATABASE_NAME = "diary.db";
	public static final int DATABASE_VERSION = 1;

	public DBOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/**
	 * 数据库第一次被创建出来的时候才会被调用，只会调用1次 完成数据库中表的创建 SQLiteDatabase类主要是为了操作数据库的
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table diary(_id integer primary key autoincrement,title varchar(20),content varchar(1000),happy integer,createtime)");
	}

	/**
	 * 数据库的版本发生改变的时候，一般是在软件升级的时候会调用
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
