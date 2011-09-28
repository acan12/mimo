package com.mimo.app;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.mimo.app.model.adapter.DBAdapter;
import com.mimo.app.model.pojo.ActivityEvent;

public class MapLocation extends MapActivity{
	PrintStream p = System.out;
	
	public void onCreate(Bundle savedInstanceState){
		
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.layout_maplocation);
//		
//		MapView mv = (MapView)findViewById(R.id.mapview);
//		mv.setBuiltInZoomControls(true);
//		mv.displayZoomControls(true);
//		
//		double lat2= -6.195894;
//		double lng2= 106.835901;
//		GeoPoint point2 = getPoint(lat2, lng2);
//		List<Overlay> mapOverlays = mv.getOverlays();
//		Drawable drawable = getResources().getDrawable(R.drawable.cheese);
//        MapOverlays itemizedoverlay = new MapOverlays(drawable, MapLocation.this);
//        OverlayItem overlayitem = new OverlayItem(point2, null, "You are in Butcher Location.");
//        itemizedoverlay.addOverlay(overlayitem);
//        mapOverlays.add(itemizedoverlay);
		
		super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_maplocation);
        
        final EditText name = (EditText) findViewById(R.id.placename);
        final Geocoder coder = new Geocoder(getApplicationContext());
        final TextView results = (TextView) findViewById(R.id.result);
        final Button mapLoc = (Button)findViewById(R.id.map_it);
        
        final MapView map = (MapView) findViewById(R.id.mapview);
        map.setSatellite(false);
        final MapController mapControl = map.getController();
        mapControl.setZoom(17);

        map.setBuiltInZoomControls(true);
        
        Button geocode = (Button) findViewById(R.id.geocode);
        geocode.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String placeName = name.getText().toString();
                try {
                    List<Address> geocodeResults = coder.getFromLocationName(placeName, 3);
                    Iterator<Address> locations = geocodeResults.iterator();

                    String locInfo = "Results:\n";
                    double lat = 0f;
                    double lon = 0f;
                    while (locations.hasNext()) {
                        Address loc = locations.next();
                        locInfo += String.format("Location: %f, %f", loc.getLatitude(), loc.getLongitude());
                        lat = loc.getLatitude();
                        lon = loc.getLongitude();
                    }
                    results.setText(locInfo);
                    
                    final String geoURI = String.format("geo:%f,%f", lat, lon  );
                    
                    mapLoc.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            Uri geo = Uri.parse(geoURI);
                            Intent geoMap = new Intent(Intent.ACTION_VIEW, geo);
                            startActivity(geoMap);
                        }
                        
                    });
                    mapLoc.setVisibility(View.VISIBLE);
                    
                    GeoPoint newPoint = new GeoPoint((int)(lat * 1E6), (int)(lon*1E6));
                    mapControl.animateTo(newPoint);
                    
                    // add a view at this point
                    MapView.LayoutParams mapMarkerParams = new MapView.LayoutParams(
                            LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 
                            newPoint, MapView.LayoutParams.TOP_LEFT );
                    ImageView mapMarker = new ImageView(getApplicationContext());
                    mapMarker.setImageResource(R.drawable.marker_green);
                    map.addView(mapMarker, mapMarkerParams);
                    Drawable dm = getResources().getDrawable(R.drawable.alien);
                    MapOverlayLayers x = new MapOverlayLayers(getApplicationContext(), dm);
                    map.getOverlays().add(x);

                } catch (IOException e) {
                    Log.e("Mapping", "Failed to get location info", e);
                }

            }

        });
        
        // check to see if we were launched with a browser intent... e.g. "geoname://loc/yellowstone"
        Intent launchIntent = getIntent();
        if (launchIntent != null) {
            String action = launchIntent.getAction();
            Uri data = launchIntent.getData();
            Log.v("Mapping", "Intent action = " + action);
            if (data != null ) {
                Log.v("Mapping", "Intent Uri = " + data.toString());
                
                name.setText(data.getLastPathSegment());
                geocode.performClick();
            }
        }
	}
	
	 
	 
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}


class MapOverlayLayers extends ItemizedOverlay<OverlayItem>{

	private Context mContext ;
	private ArrayList items = new ArrayList();
	
	public MapOverlayLayers(Context aContext, Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));
		mContext = aContext;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event, MapView mapView) 
	{   
	      //---when user lifts his finger---
//	      if (event.getAction() == 1) {                
//	          GeoPoint p = mapView.getProjection().fromPixels(
//	              (int) event.getX(),
//	              (int) event.getY());
//	              Toast.makeText(this.mContext, 
//	                  p.getLatitudeE6() / 1E6 + "," + 
//	                  p.getLongitudeE6() /1E6 , 
//	                  Toast.LENGTH_SHORT).show();
//	//          List<Overlay> mapOverlays = mapView.getOverlays();
//	//          Drawable drawable = this..getResources().getDrawable(R.drawable.cheese);
//	//          MapOverlays itemizedoverlay = new MapOverlays(drawable, this.mContext);
//	//          OverlayItem overlayitem = new OverlayItem(p, null, "You are in Butcher Location.");
//	//          itemizedoverlay.addOverlay(overlayitem);
//	//          mapOverlays.add(itemizedoverlay);
//	      }                            
	      return false;
	}

	@Override
	protected OverlayItem createItem(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}   
	
	
	
}
