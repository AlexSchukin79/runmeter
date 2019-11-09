package com.example.p180;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.util.SparseArray;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class HistoryActivity extends Activity {

	final String[] myArr = new String[] {"1", "2", "3", "4"};
	ListView listWiev1;
	IODataBase ioDB;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history);
		ioDB = new IODataBase(this);
		
		ioDB.createDB();
		ArrayList<Map<String, Object>> myData = ioDB.readData();
//		List<String> dataTimeList = myData.get(0);
//		List<String> myArrStrings = myData.get(1);
//		List<String> dataDistanceList = myData.get(2);
		ioDB.delete("200");
		
		ioDB.closeDB();
		
		listWiev1 = (ListView)findViewById(R.id.listView1);
		listWiev1.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		
		SimpleAdapter myAdapter = new SimpleAdapter(this, myData, R.layout.item, new String[] {"date", "distance", "time"}, 
									new int[] {R.id.textView1, R.id.textView2, R.id.textView3});
		
		listWiev1.setAdapter(myAdapter);
	}
}
