package com.example.p180;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;

public class MainActivity extends Activity {
	 
	  private Chronometer myChronometer;
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
	    tvStatusNet = (TextView) findViewById(R.id.tvStatusNet);
	    myChronometer = (Chronometer) findViewById(R.id.chronometer1);
	 
	    locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
	    if(savedInstanceState != null) {
	    	distanse = savedInstanceState.getFloat("dis",0);
	    }
		
	    myChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long elapsedMillis = SystemClock.elapsedRealtime() - myChronometer.getBase();
            }
		  });
	     
	  }
	  
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		outState.putFloat("dis", distanse);
		super.onSaveInstanceState(outState);
	}
	 
	  @Override
	  protected void onResume() {
	    super.onResume();
	    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
	        1000, 5, locationListener);
		/*
		 * locationManager.requestLocationUpdates( LocationManager.NETWORK_PROVIDER,
		 * 1000, 10, locationListener);
		 */
	    checkEnabled();
	  }
	 
	/*
	 * @Override protected void onPause() { super.onPause();
	 * locationManager.removeUpdates(locationListener); }
	 */

	  private LocationListener locationListener = new LocationListener() {
	 
	    @Override
	    public void onLocationChanged(Location location) {
	    if((lastLocation == null)
	    		//|| location.distanceTo(lastLocation) > 10
	    		) {
	    	lastLocation = location;
	    }
	    distanse += location.distanceTo(lastLocation);
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
			} /*
				 * else if (provider.equals(LocationManager.NETWORK_PROVIDER)) {
				 * tvStatusNet.setText("Status: " + String.valueOf(status)); }
				 */
	    }
	  };
	 
	  private void showLocation(Location location) {
	    if (location == null)
	      return;
	    if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
	      tvLocationGPS.setText(String.valueOf(new Date(location.getTime())) + "\n"+
	    		  				"Distance: " + distanse);
		} /*
			 * else if (location.getProvider().equals( LocationManager.NETWORK_PROVIDER)) {
			 * tvLocationNet.setText(formatLocation(location)); }
			 */
	  }
	 
	  private String formatLocation(Location location) {
	    if (location == null)
	      return "";
	    return String.format(
	        "Coordinates: lat = %1$.4f, lon = %2$.4f, time = %3$tF %3$tT,"
	        + " accuracy = %4$.1f : distance =%5$s ",
	        location.getLatitude(), location.getLongitude(), new Date(
	            location.getTime()), location.getAccuracy(), distanse);
	  }
	 
	  private void checkEnabled() {
	    tvEnabledGPS.setText("Enabled: "
	        + locationManager
	            .isProviderEnabled(LocationManager.GPS_PROVIDER));
	    tvEnabledNet.setText("Enabled: "
	        + locationManager
	            .isProviderEnabled(LocationManager.NETWORK_PROVIDER));
	  }
	 
	  public void onClickLocationSettings(View view) {
		/*
		 * startActivity(new Intent(
		 * android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
		 */	  
		distanse = 0;
		myChronometer.setBase(SystemClock.elapsedRealtime());
		myChronometer.start();
	  };
	  
		 
	}

