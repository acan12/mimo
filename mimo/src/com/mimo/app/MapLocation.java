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
	double lat = -6.198254;
	double lng = 106.841086;
	
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		Bundle bundle = getIntent().getExtras();
		int paramid = bundle.getInt("paramid");
		p.println("in maplocation id:"+paramid);
		setContentView(R.layout.layout_maplocation);
		GeoPoint point = getPoint(lat, lng);
		
		final EditText name = (EditText) findViewById(R.id.placename);
		final TextView hidden = (TextView)findViewById(R.id.hidden_value);
        final Geocoder coder = new Geocoder(getApplicationContext());
        final Button mapLoc = (Button)findViewById(R.id.next_map);     
		final MapView mv = (MapView)findViewById(R.id.mapview);
		
		mv.setBuiltInZoomControls(true);
		mv.getController().setCenter(point);
		mv.getController().setZoom(17);
		
		List<Overlay> mapOverlays = mv.getOverlays();
        Drawable drawable = this.getResources().getDrawable(R.drawable.alien);
        MapOverlays itemizedoverlay = new MapOverlays(drawable, this, false, false, true, paramid);
        OverlayItem overlayitem = new OverlayItem(point, "", "");
        itemizedoverlay.addOverlay(overlayitem);
        mapOverlays.add(itemizedoverlay);
		
        Button geocode = (Button) findViewById(R.id.geocode);        
        geocode.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final double[] lat = new double[10];
				final double[] lon = new double[10];
				String locInfo = "Results:\n";   
				int i = 0;
				
				String placeName = name.getText().toString();   
				try{
					List<Address> geocodeResults = coder.getFromLocationName(placeName, 3);
					Iterator<Address> locations = geocodeResults.iterator();  
					
					while (locations.hasNext()) {
						if(i>0){mapLoc.setVisibility(View.VISIBLE); }else{
							mapLoc.setVisibility(View.INVISIBLE);
						}
						Address loc = locations.next();
						locInfo += String.format("Location: %f, %f \n", loc.getLatitude(), loc.getLongitude());   
						lat[i] = loc.getLatitude();
						lon[i] = loc.getLongitude();
						i++;
					} 
					
					p.println("-----------result loc:"+locInfo);
					GeoPoint newPoint = new GeoPoint((int)(lat[0]* 1E6), (int)(lon[0]*1E6));
					mv.getController().animateTo(newPoint);
					setMarkerOnMapLocation(mv, newPoint);
					hidden.setText(""+(i-1));
					
					final int i2 = i;
					mapLoc.setOnClickListener(new View.OnClickListener() {
                       public void onClick(View v) {
                    	  int ix = Integer.parseInt((String)hidden.getText());
                    	  
                    	  GeoPoint newPoint = new GeoPoint((int)(lat[ix]* 1E6), (int)(lon[ix]*1E6));
       				   	  mv.getController().animateTo(newPoint);
       				   	  setMarkerOnMapLocation(mv, newPoint);
       				   	  if(ix == 0){ix = i2;}
       				   	  hidden.setText(""+(--ix));
                       }
                       
                    });  

						
				}catch(IOException e){
					Log.e("Mapping", "Failed to get location info", e); 
				}
				
				
			}

			private void setMarkerOnMapLocation(final MapView mv,
					GeoPoint newPoint) {
				MapView.LayoutParams mapMarkerParams = new MapView.LayoutParams(
				          LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 
				          newPoint, MapView.LayoutParams.TOP_LEFT );
				ImageView mapMarker = new ImageView(getApplicationContext());
				mapMarker.setImageResource(R.drawable.marker_green);
				mv.addView(mapMarker, mapMarkerParams);
			}  
        	
        });
	}
	
	private GeoPoint getPoint(double lat, double lng){
		return new GeoPoint((int)(lat*1000000.0), (int)(lng*1000000.0));
	}
	 
	
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}
