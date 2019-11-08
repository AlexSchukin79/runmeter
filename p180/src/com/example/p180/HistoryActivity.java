package com.example.p180;

import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.util.SparseArray;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
		SparseArray<List<String>> myData = ioDB.readData();
		List<String> myArrStrings = myData.get(1);
		ioDB.delete("200");
		
		ioDB.closeDB();
		
		listWiev1 = (ListView)findViewById(R.id.listView1);
		listWiev1.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		
		ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myArrStrings);
		
		listWiev1.setAdapter(myAdapter);
	}
}
