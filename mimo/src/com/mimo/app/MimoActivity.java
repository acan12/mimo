package com.mimo.app;

import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.*;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;

public class MimoActivity extends MapActivity {
	double lat= -6.19638013839722;
	double lng= 106.837997436523;
	double lat2= -6.195894;
	double lng2= 106.835901;
	
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		
		setContentView(R.layout.main);
		GeoPoint point = getPoint(lat, lng);
		GeoPoint point2 = getPoint(lat2, lng2);
		MapView mv = (MapView) findViewById(R.id.mapview);
		mv.setBuiltInZoomControls(true);
		mv.getController().setCenter(point);
		mv.getController().setZoom(15);
		
		List<Overlay> mapOverlays = mv.getOverlays();
        Drawable drawable = this.getResources().getDrawable(R.drawable.alien);
        MapOverlays itemizedoverlay = new MapOverlays(drawable, this);
        OverlayItem overlayitem = new OverlayItem(point, "Halo, Apa kabar!", "I'm in Jakarta!");
        itemizedoverlay.addOverlay(overlayitem);
        mapOverlays.add(itemizedoverlay);
        
        Drawable drawable2 = this.getResources().getDrawable(R.drawable.alien);
        MapOverlays itemizedoverlay2 = new MapOverlays(drawable2, this);
        OverlayItem overlayitem2 = new OverlayItem(point2, "Halo, Apa kabar!", "I'm in Jakarta!");
        itemizedoverlay2.addOverlay(overlayitem2);
        mapOverlays.add(itemizedoverlay2);
        
       
	} 
	
	private GeoPoint getPoint(double lat, double lng){
		return new GeoPoint((int)(lat*1000000.0), (int)(lng*1000000.0));
	}
}