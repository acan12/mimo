package com.mimo.app;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.mimo.app.adapter.DBAdapter;

public class MapLocation extends MapActivity{

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_maplocation);
		
		MapView mv = (MapView)findViewById(R.id.mapview);
		LinearLayout zoomLayout = (LinearLayout)findViewById(R.id.Zoom);
		View zoomView = mv.getZoomControls();
		zoomLayout.addView(zoomView, 
		        new LinearLayout.LayoutParams(
		            LayoutParams.WRAP_CONTENT, 
		            LayoutParams.WRAP_CONTENT)); 
		mv.setBuiltInZoomControls(true);
		mv.displayZoomControls(true);
		
		
		// ----------- store to Db SQLite -------
		DBAdapter db = new DBAdapter(this);
		db.open();
		long id;
		id = db.insertTitle("FOOD / Fun", "Dinner with client community.");
		id = db.insertTitle("Entertainment", "See Movies BOX Office in MegaPlex.");
		id = db.insertTitle("Sport", "Fishing with old friends.");
		db.close();
	}
	
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
