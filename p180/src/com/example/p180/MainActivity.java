package com.example.p180;

import java.util.Date;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

public class MainActivity extends Activity {
	 
	  private Chronometer myChronometer;
	  IODataBase iodb;
	  int  flag = 0;
	  long time;
	  String getTime;
	  Button btnStart;
	  Button btnStop;
	  Location lastLocation;
	  TextView tvEnabledGPS;
	  TextView tvStatusGPS;
	  TextView tvLocationGPS;
	  TextView tvEnabledNet;
	  TextView tvStatusNet;
	  TextView tvLocationNet;
	  float distanse;
	  private LocationManager locationManager;
	  
	  
	 
	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main);
	    tvEnabledGPS = (TextView) findViewById(R.id.tvEnabledGPS);
	    tvStatusGPS = (TextView) findViewById(R.id.tvStatusGPS);
	    tvLocationGPS = (TextView) findViewById(R.id.tvLocationGPS);
	    tvEnabledNet = (TextView) findViewById(R.id.tvEnabledNet);
	    myChronometer = (Chronometer) findViewById(R.id.chronometer1);
	    btnStart = (Button)findViewById(R.id.btnStart);
	    btnStop = (Button)findViewById(R.id.btnStop);
	    btnStop.setEnabled(false);
	 
	    locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
	    
	    iodb = new IODataBase(this);
	    if(savedInstanceState != null) {
	    	distanse = savedInstanceState.getFloat("dis",0);
	    	flag = savedInstanceState.getInt("flag");
	    	if(flag == 1) {
	    		myChronometer.setBase((Long) savedInstanceState.get("time"));
	    		myChronometer.start();
	    		btnStop.setEnabled(true);
	    		btnStart.setEnabled(false);
	    	}
	    	if (flag == 2) {
      		  time = savedInstanceState.getLong("elapsedTime");
			  myChronometer.setBase(SystemClock.elapsedRealtime() + time);
	    	}
	    }
		
	    myChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
            }
		  });
	  }
	  
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		outState.putFloat("dis", distanse);
		outState.putInt("flag", flag);
		outState.putLong("time", myChronometer.getBase());
		outState.putLong("elapsedTime", time);
		super.onSaveInstanceState(outState);
	}
	 
	  @Override
	  protected void onResume() {
	    super.onResume();
	    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
	        1000, 5, locationListener);
 
	    checkEnabled();
	  }
	 
	
	  private LocationListener locationListener = new LocationListener() {
		  int a = 0;
		LastTime timer = new LastTime(); 
	    @Override
	    public void onLocationChanged(Location location) {
	    if((lastLocation == null) || location.distanceTo(lastLocation) > timer.meter ) {
	    	lastLocation = location;
	    	}
	    
	    tvEnabledNet.setText(String.valueOf(timer.last()));
	    if(flag == 1) {
	    	 distanse += location.distanceTo(lastLocation);
	    	}
	      showLocation(location);
	    }
	 
	    @Override
	    public void onProviderDisabled(String provider) {
	      checkEnabled();
	    }
	 
	    @Override
	    public void onProviderEnabled(String provider) {
	      checkEnabled();
	      showLocation(locationManager.getLastKnownLocation(provider));
	    }
	 
	    @Override
	    public void onStatusChanged(String provider, int status, Bundle extras) {
	      if (provider.equals(LocationManager.GPS_PROVIDER)) {
	        tvStatusGPS.setText("Status: " + String.valueOf(status));
			} 
	    }
	  };
	  
	  public void vibro() {
		Vibrator vibroVibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
	    vibroVibrator.vibrate(100);
	}
	  
	  private void showLocation(Location location) {
	    if (location == null)
	      return;
	    if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
	       getTime =  String.valueOf(new Date(location.getTime()));
	       show();
		} 
	  }
	 
	  private void show() {
		tvLocationGPS.setText(getTime + "\n" + "Distance: " + distanse);
	}
	  
	  private void checkEnabled() {
	    tvEnabledGPS.setText("Enabled: "
	        + locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER));
	  }
	 
	  public void onClickStart(View view) {
		flag = 1;  
		distanse = 0;
		show();
		myChronometer.setBase(SystemClock.elapsedRealtime());
		vibro();
		myChronometer.start();
		btnStop.setEnabled(true);
		btnStart.setEnabled(false);
	  };
	  
	  public void onClickStop(View v) {
		  flag = 2;
		  time = myChronometer.getBase() - SystemClock.elapsedRealtime();
		  vibro();
		  myChronometer.stop();
		  iodb.createDB();
		  iodb.writeData( distanse / 1000, time * (-1) -7 * 60 * 60 * 1000 );
		  iodb.closeDB();
		  tvEnabledNet.setText(String.valueOf(time));
		  btnStop.setEnabled(false);
		  btnStart.setEnabled(true);
	  };
	  
	  @Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.main, menu);
			return true;
		}

		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			
			switch (item.getItemId()) {
			case R.id.item1 :
				vibro();
				Intent intent = new Intent(this, HistoryActivity.class);
				startActivity(intent);
				break;
				
			case R.id.itemStat :
				vibro();
				Intent intent2 = new Intent(this, StaticActivity.class);
				startActivity(intent2);
				
			default:
				break;
			}
			
			return super.onOptionsItemSelected(item);
		}
	}

