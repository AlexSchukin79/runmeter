package com.example.p180;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class HistoryActivity extends Activity {

	final String[] myArr = new String[] {"1", "2", "3", "4"};
	ListView listWiev1;
	IODataBase ioDB;
	ArrayList<Map<String, Object>> myData;
	SimpleAdapter myAdapter;
	Map<String, Object> dataTimeList;
	List<Object> myArrStrings;
	String str;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history);
		ioDB = new IODataBase(this);
		ioDB.createDB();
		myData = ioDB.readData();
		ioDB.closeDB();
		
		listWiev1 = (ListView)findViewById(R.id.listView1);
		listWiev1.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		
		myAdapter = new SimpleAdapter(this, myData, R.layout.item, new String[] {"date", "distance", "time"}, 
									new int[] {R.id.textView1, R.id.textView2, R.id.textView3});
		
		listWiev1.setAdapter(myAdapter);
		registerForContextMenu(listWiev1);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		    super.onCreateContextMenu(menu, v, menuInfo);
		    menu.add(0, 1, 0, "”далить запись");
		  }
	
	@Override
	  public boolean onContextItemSelected(MenuItem item) {
	    if (item.getItemId() == 1) {
	      // получаем инфу о пункте списка
	      AdapterContextMenuInfo acmi = (AdapterContextMenuInfo) item.getMenuInfo();
	      dataTimeList = myData.get(acmi.position );
		  str = (String) (dataTimeList.get("id"));
	      Toast.makeText(this, ((str) + String.valueOf(acmi.position)), Toast.LENGTH_SHORT).show();
	      ioDB.createDB();
	      ioDB.delete(str);
	      ioDB.closeDB();
	      myData.remove(acmi.position);// удал€ем Map из коллекции, использу€ позицию пункта в списке
	      myAdapter.notifyDataSetChanged();// уведомл€ем, что данные изменились
	      return true;
	    
	    }
	    return super.onContextItemSelected(item);
	  }
			
			
	
}
