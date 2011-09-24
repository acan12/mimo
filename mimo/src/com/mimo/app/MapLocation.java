package com.mimo.app;

import java.io.PrintStream;
import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.mimo.app.model.adapter.DBAdapter;
import com.mimo.app.model.pojo.ActivityEvent;

public class MapLocation extends MapActivity implements OnClickListener{
	PrintStream p = System.out;
	
	public void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_maplocation);
		
		MapView mv = (MapView)findViewById(R.id.mapview);
//		LinearLayout zoomLayout = (LinearLayout)findViewById(R.id.zoom);
//		View zoomView = mv.getZoomControls();
//		zoomLayout.addView(zoomView, 
//		        new LinearLayout.LayoutParams(
//		            LayoutParams.WRAP_CONTENT, 
//		            LayoutParams.WRAP_CONTENT)); 
		mv.setBuiltInZoomControls(true);
		mv.displayZoomControls(true);
		
//		mv.setOnClickListener(this);
		double lat2= -6.195894;
		double lng2= 106.835901;
		GeoPoint point2 = getPoint(lat2, lng2);
		List<Overlay> mapOverlays = mv.getOverlays();
		Drawable drawable = getResources().getDrawable(R.drawable.cheese);
        MapOverlays itemizedoverlay = new MapOverlays(drawable, MapLocation.this);
        OverlayItem overlayitem = new OverlayItem(point2, null, "You are in Butcher Location.");
        itemizedoverlay.addOverlay(overlayitem);
        mapOverlays.add(itemizedoverlay);
        
        
	}
	
	private GeoPoint getPoint(double lat, double lng){
		return new GeoPoint((int)(lat*1000000.0), (int)(lng*1000000.0));
	}
	
	
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.mapview:
			Toast.makeText(this, "dodol", Toast.LENGTH_LONG);
			break;
		}
		
	}

}
