package com.mimo.app;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.mimo.app.interfaces.Configuration;
import com.mimo.app.model.adapter.DBAdapter;
import com.mimo.app.model.pojo.ActivityEvent;
import com.mimo.app.model.pojo.Icons;
import com.mimo.app.view.list.ListDialogView;

import android.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MapDashboardActivity extends MapActivity implements OnClickListener, Configuration {
	double lat= -6.19638013839722;
	double lng= 106.837997436523;
	
	Hashtable iconHash ;
	
	ActivityEvent ae;
	
	
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
		MapView mv = (MapView) findViewById(R.id.mapview);
		mv.setBuiltInZoomControls(true);
		mv.getController().setCenter(point);
		mv.getController().setZoom(13);
		
        // counting dynamic button image
		Hashtable h = getIconButton();
		Iterator it = h.keySet().iterator();
		String[] iconLabel = new String[h.keySet().size()];
		int i=0;
		while(it.hasNext()){
			iconLabel[i++] = (String)it.next();
		}
        reloadButtonImage(iconLabel);
	} 
	
	private Hashtable getIconButton(){
		ae = new ActivityEvent();
		DBAdapter db = new DBAdapter(this);
		Cursor c = db.getIconsUniqRecord();    
		iconHash = new Hashtable();//String[Icons.getIcons().length];
		while(c.moveToNext()){
			String key = c.getString(c.getColumnIndex("icon"));
			String value = c.getString(c.getColumnIndex("count_record"));
			iconHash.put(key, value);
		}
		
		return iconHash;
	}
	
	private void reloadButtonImage(String[] iconLabel){
		LinearLayout ll = (LinearLayout) findViewById(R.id.imgbutton_linearLayout);
		LayoutInflater inflater = LayoutInflater.from(this);
		Icons icons = new Icons();
		for(int i=0; i<iconLabel.length; i++){ 
			ImageButton imb = new ImageButton(this);
			imb.setId(icons.getIndexFromLabel(iconLabel[i]));
			imb.setImageResource(icons.getIconFromLabel(iconLabel[i]));
			inflater.inflate(R.layout.list_button, null);
			ll.addView(imb);
			imb.setOnClickListener(this);
		}
		
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
		final Icons icons = new Icons() ;
		Log.d("check click value: ","getId="+v.getId());
		String key = Icons.getLabels()[v.getId()];
		int value = Integer.parseInt(""+iconHash.get(key));
		if(	value > 1 ){
			showEventDialog(mv, icons);
		}else{
			ae = new ActivityEvent();
			DBAdapter db = new DBAdapter(this);
			Cursor c = db.getRecordByIcon(key);
			while(c.moveToNext()){
				ae.setIcon(key);
				ae.setName(c.getString(c.getColumnIndex("name")));
				ae.setDescription(c.getString(c.getColumnIndex("description")));
				ae.setLat(c.getDouble(c.getColumnIndex("lat")));
				ae.setLng(c.getDouble(c.getColumnIndex("lng")));
			}
			point = getPoint(ae.getLat(), ae.getLng());
			mv.getController().setCenter(point);
			mapOverlays = mv.getOverlays();
			drawable = this.getResources().getDrawable(new Icons().getIconFromLabel(ae.getIcon()));
			MapDashboardOverlays itemizedoverlay = new MapDashboardOverlays(drawable, this);
	        OverlayItem overlayitem = new OverlayItem(point, "coord: "+point.getLatitudeE6()+", "+point.getLongitudeE6(), "You are in Bread Location.");
	        itemizedoverlay.addOverlay(overlayitem);
	        mapOverlays.add(itemizedoverlay);
		}
	}

	private void showEventDialog(final MapView mv, final Icons icons) {
		final ListDialogView lDialog = new ListDialogView(this, null, R.layout.list_row_dialog);
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		final ListAdapter adapter = lDialog.getDialogAdapter(getListEvent(Icons.getLabels()[2]));
		
		dialog.setTitle("List of Your activities:  ");
		dialog.setAdapter(adapter, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				TextView title = (TextView)adapter.getView(which, null, null).findViewById(R.id.title);
				TextView loc = (TextView)adapter.getView(which, null, null).findViewById(R.id.hidden_value_loc);
				TextView iconlabel = (TextView)adapter.getView(which, null, null).findViewById(R.id.hidden_value_iconlabel);
				GeoPoint point_link = null;
				
				double lat = Double.parseDouble(loc.getText().toString().split(",")[0]);
				double lng = Double.parseDouble(loc.getText().toString().split(",")[1]);
				int icon = icons.getIconFromLabel(iconlabel.getText().toString());
				mv.getController().setZoom(13);
				point_link = getPoint(lat, lng);
				mv.getController().setCenter(point_link);
				
				
				List<Overlay> mapOverlays = mv.getOverlays();
				Drawable drawable = getResources().getDrawable(icon);
		        MapDashboardOverlays itemizedoverlay = new MapDashboardOverlays(drawable, MapDashboardActivity.this);
		        OverlayItem overlayitem = new OverlayItem(point_link, null, "");
		        itemizedoverlay.addOverlay(overlayitem);
		        mapOverlays.add(itemizedoverlay);
			}
		});
		
		dialog.show();
	}
	 
	private ArrayList getListEvent(String icon){
		ArrayList<ActivityEvent> mActivity= new ArrayList<ActivityEvent>();
		ActivityEvent aev = null;
		DBAdapter db = new DBAdapter(this);
		Cursor c = db.getRecordByIcon(icon);
		while(c.moveToNext()){
			aev = new ActivityEvent();
			aev.setIcon(c.getString(c.getColumnIndex("icon")));
			aev.setName(c.getString(c.getColumnIndex("name")));
			aev.setDescription(c.getString(c.getColumnIndex("description")));
			aev.setLat(c.getDouble(c.getColumnIndex("lat")));
			aev.setLng(c.getDouble(c.getColumnIndex("lng")));
			mActivity.add(aev);
		}
		return mActivity;
	}
};