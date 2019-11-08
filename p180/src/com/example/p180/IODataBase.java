package com.example.p180;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.SparseArray;
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
	
	public SparseArray<List<String>> readData() {
		
		dbDatabase = dbHelper.getReadableDatabase();
		Cursor cursor = dbDatabase.query("myTable", null, null, null, null, null, null);
		List<String> distance = new ArrayList<String>();
		List<String> dateArrayList = new ArrayList<String>();
		List<String> timeArrayList = new ArrayList<String>();
		
		int distanceColumn = 0;
		int timeColumn = 0;
		int dateColumn = 0;
		
		if(cursor.moveToFirst()) {
			dateColumn = cursor.getColumnIndex("date");
			distanceColumn = cursor.getColumnIndex("distance");
		    timeColumn = cursor.getColumnIndex("time"); 
		}
		while(cursor.moveToNext()) {
			distance.add(Integer.toString(cursor.getInt(distanceColumn)));
			dateArrayList.add(Integer.toString(cursor.getInt(dateColumn)));
			timeArrayList.add(Integer.toString(cursor.getInt(timeColumn)));
		}
		cursor.close();
		SparseArray<List<String>> datArray = new SparseArray<List<String>>();
		datArray.append(0, timeArrayList);
		datArray.append(1, dateArrayList);
		datArray.append(2, distance);
		
		return datArray; 
	}
	
	public void delete(String delString) {
		dbDatabase = dbHelper.getWritableDatabase();
		dbDatabase.delete("myTable", "time = " + delString, null);
	}
}
