package com.example.p180;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
		//Toast.makeText(ctx, " run create base", Toast.LENGTH_LONG).show();
	}
	
	void closeDB() {
		// TODO Auto-generated method stub
		Toast.makeText(ctx, "close base", Toast.LENGTH_LONG).show();
		dbHelper.close();
	}
	
	public void writeData( float distance, long time) {
		
		DecimalFormat df = new DecimalFormat("0.00");
		String dateString = new SimpleDateFormat("dd-MM-yyyy HH:mm",Locale.getDefault()).format(new Date());
		String timeString = new SimpleDateFormat("H:mm:ss", Locale.getDefault()).format(new Date(time));
		dbDatabase = dbHelper.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("date", dateString);
		cv.put("distance", df.format(distance));
		cv.put("time", timeString);
		long id = dbDatabase.insert("myTable", null, cv);
		Toast.makeText(ctx, timeString, Toast.LENGTH_LONG).show();
	}
	
	public ArrayList<Map<String, Object>> readData() {
		
		dbDatabase = dbHelper.getReadableDatabase();
		Cursor cursor = dbDatabase.query("myTable", null, null, null, null, null, null);
		List<String> distance = new ArrayList<String>();
		List<String> dateArrayList = new ArrayList<String>();
		List<String> timeArrayList = new ArrayList<String>();
		
		int distanceColumn = 0;
		int timeColumn = 0;
		int dateColumn = 0;
		
		ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			    Map<String, Object> m;
			    
		if(cursor.moveToFirst()) {
			dateColumn = cursor.getColumnIndex("date");
			distanceColumn = cursor.getColumnIndex("distance");
		    timeColumn = cursor.getColumnIndex("time"); 
		}
		 do {
//			distance.add(Integer.toString(cursor.getInt(distanceColumn)));
//			dateArrayList.add(Integer.toString(cursor.getInt(dateColumn)));
//			timeArrayList.add(Integer.toString(cursor.getInt(timeColumn)));
			
			    
			      m = new HashMap<String, Object>();
			      m.put("distance", cursor.getString(distanceColumn));
			      m.put("date", cursor.getString(dateColumn));
			      m.put("time", cursor.getString(timeColumn));
			      data.add(m);
			    
		}while(cursor.moveToNext());
		cursor.close();
//		SparseArray<List<String>> datArray = new SparseArray<List<String>>();
//		datArray.append(0, timeArrayList);
//		datArray.append(1, dateArrayList);
//		datArray.append(2, distance);
//		
		return data; 
	}
	
	public void delete(String delString) {
		dbDatabase = dbHelper.getWritableDatabase();
		dbDatabase.delete("myTable", "time = " + delString, null);
	}
}
