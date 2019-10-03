package com.example.p180;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class IODataBase {
	Context ctx; 
	DBHelper dbHelper;
	SQLiteDatabase dbDatabase;
	
	public IODataBase(Context context) {// constructor
		this.ctx = context;
	}
	
	public void createDB() {
		// TODO Auto-generated method stub
		dbHelper = new DBHelper(ctx);
		Toast.makeText(ctx, " run create base", Toast.LENGTH_LONG).show();
	}
	
	void closeDB() {
		// TODO Auto-generated method stub
		Toast.makeText(ctx, "close base", Toast.LENGTH_LONG).show();
		dbHelper.close();
	}
	
	public void writeData( double distance, long time) {
		
		dbDatabase = dbHelper.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("date", "01.10.19");
		cv.put("distance", distance);
		cv.put("time", time);
		long id = dbDatabase.insert("myTable", null, cv);
		Toast.makeText(ctx, "id = " + id, Toast.LENGTH_LONG).show();
	}
	
	public void readData() {
		
		Cursor cursor = dbDatabase.query("myTable", null, null, null, null, null, null);
		if(cursor.moveToFirst()) {
			
		}
	}
}
