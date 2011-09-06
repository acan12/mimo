package com.mimo.app;

import java.lang.reflect.Array;
import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MapDashboardActivity extends MapActivity implements OnClickListener {
	double lat= -6.19638013839722;
	double lng= 106.837997436523;
	double lat2= -6.195894;
	double lng2= 106.835901;
	
	double lat3 = -6.24984979629517;
	double lng3 = 106.78800201416;
	
	double lat4 = -6.198254;
	double lng4 = 106.841086;
	
	
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		
		setContentView(R.layout.layout_mapview);
		GeoPoint point = getPoint(lat, lng);
		GeoPoint point2 = getPoint(lat2, lng2);
		MapView mv = (MapView) findViewById(R.id.mapview);
		mv.setBuiltInZoomControls(true);
		mv.getController().setCenter(point);
		mv.getController().setZoom(13);
		
		List<Overlay> mapOverlays = mv.getOverlays();
        Drawable drawable = this.getResources().getDrawable(R.drawable.alien);
        MapOverlays itemizedoverlay = new MapOverlays(drawable, this);
        OverlayItem overlayitem = new OverlayItem(point, "Halo, Alien!", "I'm in Jakarta!");
        itemizedoverlay.addOverlay(overlayitem);
        mapOverlays.add(itemizedoverlay);
        
        Drawable drawable2 = this.getResources().getDrawable(R.drawable.alien);
        MapOverlays itemizedoverlay2 = new MapOverlays(drawable2, this);
        OverlayItem overlayitem2 = new OverlayItem(point2, "Halo, Alien 2!", "I'm in Bandung!");
        itemizedoverlay2.addOverlay(overlayitem2);
        mapOverlays.add(itemizedoverlay2);
        
       ImageButton imgbtn = (ImageButton)findViewById(R.id.ListLink);
       imgbtn.setOnClickListener(this);
       
       ImageButton imgbtn2 = (ImageButton)findViewById(R.id.bread_loc);
       imgbtn2.setOnClickListener(this);
       
       ImageButton imgbtn3 = (ImageButton)findViewById(R.id.butcher_loc);
       imgbtn3.setOnClickListener(this);
	} 
	
	private GeoPoint getPoint(double lat, double lng){
		return new GeoPoint((int)(lat*1000000.0), (int)(lng*1000000.0));
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		final MapView mv = (MapView) findViewById(R.id.mapview);
		mv.setBuiltInZoomControls(true);
		mv.getController().setZoom(15);
		List<Overlay> mapOverlays ;
		GeoPoint point;
		Drawable drawable;
		
		String[] items = {"airplanes", "animals", "cars", "colors", "flowers", "letters", "monsters", "numbers", "shapes", "smileys", "sports", "stars", "smileys", "sports", "stars"};
		
		
		switch (v.getId()){
			case R.id.ListLink:

				AlertDialog.Builder dialog = new AlertDialog.Builder(this);
				dialog.setTitle("List of Your activities:  ");
				dialog.setAdapter(getDialogAdapter(items), new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						GeoPoint point2 = null;
						int icon = R.drawable.alien;
						if(which == 1){
							mv.getController().setZoom(17);
							point2 = getPoint(lat3, lng3);
							mv.getController().setCenter(point2);
							icon = R.drawable.bread;
						}else if(which == 2){
							mv.getController().setZoom(17);
							point2 = getPoint(lat, lng);
							mv.getController().setCenter(point2);
							icon = R.drawable.cheese;
						}else{
							mv.getController().setZoom(17);
							point2 = getPoint(lat2, lng2);
							mv.getController().setCenter(point2);
							
						}
						
						List<Overlay> mapOverlays = mv.getOverlays();
						Drawable drawable = getResources().getDrawable(icon);
				        MapOverlays itemizedoverlay = new MapOverlays(drawable, MapDashboardActivity.this);
				        OverlayItem overlayitem = new OverlayItem(point2, null, "You are in Butcher Location.");
				        itemizedoverlay.addOverlay(overlayitem);
				        mapOverlays.add(itemizedoverlay);
						
					}});
				dialog.show();

			break;
			case R.id.bread_loc:
				point = getPoint(lat3, lng3);
				mv.getController().setCenter(point);
				mapOverlays = mv.getOverlays();
				drawable = this.getResources().getDrawable(R.drawable.bread);
		        MapOverlays itemizedoverlay = new MapOverlays(drawable, this);
		        OverlayItem overlayitem = new OverlayItem(point, "coord: "+point.getLatitudeE6()+", "+point.getLongitudeE6(), "You are in Bread Location.");
		        itemizedoverlay.addOverlay(overlayitem);
		        mapOverlays.add(itemizedoverlay);
			break;
			case R.id.butcher_loc:
				point = getPoint(lat4, lng4);
				mv.getController().setCenter(point);
				mapOverlays = mv.getOverlays();
				drawable = this.getResources().getDrawable(R.drawable.butcher2);
		        MapOverlays itemizedoverlay2 = new MapOverlays(drawable, this);
		        OverlayItem overlayitem2 = new OverlayItem(point, "coord: "+point.getLatitudeE6()+", "+point.getLongitudeE6(), "You are in Butcher Location.");
		        itemizedoverlay2.addOverlay(overlayitem2);
		        mapOverlays.add(itemizedoverlay2);
			break;
		}
		
		
	}

	private ListAdapter getDialogAdapter(final String[] items) {
		// TODO Auto-generated method stub
		final int[] icons = {
						R.drawable.alien, 
						R.drawable.bread, 
						R.drawable.butcher2, 
						R.drawable.candy,
						R.drawable.cheese,
						R.drawable.eggs,
						R.drawable.farmstand,
						R.drawable.fruits,
						R.drawable.japanese_sweet2,
						R.drawable.patisserie,
						R.drawable.sandwich2,
						R.drawable.alien, 
						R.drawable.bread, 
						R.drawable.butcher2, 
						R.drawable.candy};
		ListAdapter adapter = new ArrayAdapter<String>(
                getApplicationContext(), R.layout.list_row_dialog, items) {
               
	        ViewHolder holder;
	        Drawable icon;
	 
	        class ViewHolder {
	                ImageView icon;
	                TextView title;
	        }
	 
	        public View getView(int position, View convertView,
	                        ViewGroup parent) {
	                final LayoutInflater inflater = (LayoutInflater) getApplicationContext()
	                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	 
	                if (convertView == null) {
	                        convertView = inflater.inflate(
	                                        R.layout.list_row_dialog, null);
	 
	                        holder = new ViewHolder();
	                        holder.icon = (ImageView) convertView
	                                        .findViewById(R.id.icon_dialog);
	                        holder.title = (TextView) convertView
	                                        .findViewById(R.id.title);
	                        convertView.setTag(holder);
	                } else {
	                        // view already defined, retrieve view holder
	                        holder = (ViewHolder) convertView.getTag();
	                }              
	 
	                
	                Drawable tile = getResources().getDrawable(icons[position]); //this is an image from the drawables folder
	               
	                holder.title.setText(items[position]);
	                holder.icon.setImageDrawable(tile);
	 
	                
            	
	                return convertView;
	        };
		
		};
		return adapter;
	};
};