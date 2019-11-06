package com.example.p180;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HistoryActivity extends Activity {

	final String[] myArr = new String[] {"1", "2", "3", "4"};
	ListView listWiev1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history);
		
		Bundle extrasBundle = getIntent().getExtras();
		ArrayList<String> myArrStrings =(ArrayList<String>) getIntent().getSerializableExtra("mass"); 
		listWiev1 = (ListView)findViewById(R.id.listView1);
		listWiev1.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		
		ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myArrStrings);
		
		listWiev1.setAdapter(myAdapter);
	}
}
