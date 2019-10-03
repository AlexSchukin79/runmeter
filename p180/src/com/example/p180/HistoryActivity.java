package com.example.p180;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class HistoryActivity extends Activity {

	ListView listWiev1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history);
		listWiev1 = (ListView)findViewById(R.id.listView1);
		listWiev1.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
	}
}
