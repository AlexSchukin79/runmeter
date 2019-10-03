package com.example.p180;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

class DBHelper extends SQLiteOpenHelper {
	Context cxtContext;
	
    public DBHelper(Context context) {
      // конструктор суперкласса
      super(context, "myDB", null, 1);
      this.cxtContext = context;
      Toast.makeText(cxtContext, "construct base", Toast.LENGTH_LONG).show();
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
      // создаем таблицу с полями
      db.execSQL("create table myTable ("
          + "date numeric,"
          + "distance real,"
          + "time integer" + ");");
      Toast.makeText(cxtContext, "create base", Toast.LENGTH_LONG).show();
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	//DBHelpedb.execSQL("DROP TABLE IF EXISTS" + "myTable");
    	Toast.makeText(cxtContext, "Upgrade  base", Toast.LENGTH_LONG).show();
    }
  }