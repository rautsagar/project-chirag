package com.project.chirag;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class database extends SQLiteOpenHelper {
	static String dbName = "chiragDB";
	static String tableName = "updates";

	database(Context context) {
		super(context, dbName, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String tableCreate = "CREATE TABLE " + tableName
				+ " (id INTEGER PRIMARY KEY,info TEXT)";
		db.execSQL(tableCreate);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int a, int b) {
	}

	public int getRows() {
		SQLiteDatabase db = this.getReadableDatabase();
		String getRowNo = "SELECT * FROM " + tableName;
		Cursor cursor = db.rawQuery(getRowNo, null);
		db.close();
		return cursor.getCount();

	}

	public int getLast() {
		SQLiteDatabase db = this.getReadableDatabase();
		String getRowNo = "SELECT MAX(id) FROM " + tableName;
		Cursor cursor = db.rawQuery(getRowNo, null);
		
		if (cursor.moveToFirst()) {
			int lastId = cursor.getInt(0);
			Log.w("errrrrror", "" + lastId);
			// int lastId=cursor.getInt(0);
			// Log.w("errrrrror",""+lastId);
			db.close();
			return lastId;
		}
		db.close();
		return 0;

	}

	public void insertData(int id, String data) {
		boolean flag = false;

		SQLiteDatabase db = this.getWritableDatabase();
		String getRowNo = "SELECT * FROM " + tableName;
		Cursor cursor = db.rawQuery(getRowNo, null);

		if (cursor.moveToFirst())
			do {
				if (cursor.getInt(0) == id)
					flag = true;

			} while (cursor.moveToNext());

		if (!flag)
			try {
				String insertable = "INSERT INTO " + tableName + " VALUES("
						+ id + ",\"" + data + "\")";
				db.execSQL(insertable);
			} finally {
				db.close();
			}
		else
			db.close();

	}

	public ArrayList<chiragData> getData() {
		ArrayList<chiragData> dbList = new ArrayList<chiragData>();
		chiragData dataCont;
		int thisId;
		String thisData;
		SQLiteDatabase db = this.getReadableDatabase();
		String getRowNo = "SELECT * FROM " + tableName;
		Cursor cursor = db.rawQuery(getRowNo, null);

		if (cursor.moveToFirst())
			do {
				thisId = cursor.getInt(0);
				thisData = cursor.getString(1);
				dataCont = new chiragData(thisId, thisData);
				dbList.add(dataCont);

			} while (cursor.moveToNext());
		db.close();
		return dbList;

	}

	public Cursor getCursor() {

		SQLiteDatabase db = this.getReadableDatabase();
		// String[] columns=new String[]{"id","info"};
		// Cursor cursor=db.query(tableName,columns, null, null, null, null,
		// null);
		String getRowNo = "SELECT rowid _id,* FROM " + tableName+" ORDER BY id DESC";
		Cursor cursor = db.rawQuery(getRowNo, null);

		return cursor;
	}

}
