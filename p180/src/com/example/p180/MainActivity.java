package com.example.p180;

import java.util.Date;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;

public class MainActivity extends Activity {
	 
	  private Chronometer myChronometer = null;
	  DBHelper dbHelper;
	  int  flag = 0;
	  long time;
	  String getTime;
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
	 
	    locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
	    
	    dbHelper = new DBHelper(this);
	    if(savedInstanceState != null) {
	    	distanse = savedInstanceState.getFloat("dis",0);
	    	flag = savedInstanceState.getInt("flag");
	    	if(flag == 1) {
	    		myChronometer.setBase((Long) savedInstanceState.get("time"));
	    		myChronometer.start();
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
	    // dbHelper.close();
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
	  };
	  
	  public void onClickStop(View v) {
		  flag = 2;
		  time = myChronometer.getBase() - SystemClock.elapsedRealtime();
		  vibro();
		  myChronometer.stop();
		  dbHelper.close();
	  };
	}

