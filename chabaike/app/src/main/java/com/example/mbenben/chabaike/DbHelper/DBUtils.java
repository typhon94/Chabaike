package com.example.mbenben.chabaike.DbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class DBUtils {
	private Context context;
	private SQLiteDatabase db;
	private MyHelper helper;

	public DBUtils(Context context) {
		this.context = context;
		helper = new MyHelper(context);
	}


	public long insert(ContentValues values) {
		db = helper.getWritableDatabase();
		return db.insert("urls",
				null,
				values);
	}


	public int delete(String whereClause, String[] whereArgs) {
		db = helper.getWritableDatabase();
		return db.delete("urls",
				whereClause,
				whereArgs);
	}


	public int update(ContentValues values, String whereClause, String[] whereArgs) {
		db = helper.getWritableDatabase();
		return db.update("urls",
				values,
				whereClause,
				whereArgs);
	}
	//query (String table, String[] columns, String selection, String[] selectionArgs,
	//String groupBy, String having,String orderBy,String limit)

	public Cursor queryAll() {
		db = helper.getReadableDatabase();
		return db.query("urls", null, null, null, null, null, null);
	}
	

	public Cursor queryWhere(String selection, String[] selectionArgs) {
		db = helper.getReadableDatabase();
		return db.query("urls", null, selection, selectionArgs, null, null, null);
	}
	

	public Cursor queryField(String[] columns) {
		db = helper.getReadableDatabase();
		return db.query("urls", columns, null, null, null, null, null);
	}
}
