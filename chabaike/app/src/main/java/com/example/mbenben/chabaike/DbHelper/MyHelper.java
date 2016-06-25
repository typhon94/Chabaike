package com.example.mbenben.chabaike.DbHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MyHelper extends SQLiteOpenHelper {
	private static String DB_NAME = "scholar.db";
	private static int VERSION = 1;

	public MyHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {

		String sql = "CREATE TABLE IF NOT EXISTS [urls] ([url] TEXT(30) ,[title] TEXT(40));";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
