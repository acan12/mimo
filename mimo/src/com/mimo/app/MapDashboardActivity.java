package com.mimo.app;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.mimo.app.interfaces.IBizProfileData;
import com.mimo.app.interfaces.IConfiguration;
import com.mimo.app.model.adapter.DBAdapter;
import com.mimo.app.model.pojo.ActivityEvent;
import com.mimo.app.model.pojo.Icons;
import com.mimo.app.view.BalloonLayout;
import com.mimo.app.view.list.ListDialogView;

import android.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
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
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MapDashboardActivity extends MapActivity implements OnClickListener, IConfiguration, IBizProfileData {
	double lat= -6.19638013839722;
	double lng= 106.837997436523;
	
	Hashtable iconHash;
	ActivityEvent ae;
	
	MapDashboardOverlays itemizedOverlay;
	
	@Override  
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.layout_mapview);
		MapView mv = (MapView) findViewById(R.id.mapview);
		
		
		
		
			
		
		GeoPoint point = getPoint(lat, lng);
		mv.setBuiltInZoomControls(true);
		mv.getController().setCenter(point);
		mv.getController().setZoom(13);
		
		
		
		Bundle b = getIntent().getExtras();
		if(b != null){
			double[] domap = b.getDoubleArray("doMap");
			if(domap.length>0){
				Drawable drawable = this.getResources().getDrawable(R.drawable.alien);
		        itemizedOverlay = new MapDashboardOverlays(drawable, mv, false);
		        
				point = getPoint(domap[1], domap[2]);
	            OverlayItem overlayItem = new OverlayItem(point, null, null);		
	            itemizedOverlay.addOverlay(overlayItem);
	            itemizedOverlay.onParentTap(0);
	            final MapController mc = mv.getController();
	    		mc.animateTo(point);
	    		mc.setZoom(13);
			}
		}
		
		setBizProfileToMap();
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
		iconHash = new Hashtable();
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
			showEventDialog(mv, key);
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
			
			mapOverlays = mv.getOverlays();
			drawable = this.getResources().getDrawable(new Icons().getIconFromLabel(ae.getIcon()));
	        
	        itemizedOverlay = new MapDashboardOverlays(drawable, mv, true);
	        OverlayItem overlayItem = new OverlayItem(point, 
	        		"label: "+ae.getIcon(), "Title: \n"+ae.getName());		
	        itemizedOverlay.addOverlay(overlayItem);
	        itemizedOverlay.onParentTap(0);
			mapOverlays.add(itemizedOverlay);
	        final MapController mc = mv.getController();
			mc.animateTo(point);
			mc.setZoom(16);
		}
	}
	
	private void sendStatusNotification(){
		NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		
		final Notification notification =
			new Notification(R.drawable.alien,"Hallo! ",System.currentTimeMillis()+5);
		
		Context context = getApplicationContext();
		CharSequence contentTitle = "My notification";
		CharSequence contentText = "Hello World!";
		Intent notificationIntent = new Intent(this, MapDashboardActivity.class);
		double[] loc = {R.drawable.alien, -6.278, 106.73};
		notificationIntent.putExtra("doMap", loc);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

		notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
	
		final int HELLO_ID = 1;

		nm.notify(HELLO_ID, notification);
	}

	private void showEventDialog(final MapView mv, final String key) {
		sendStatusNotification();
		
		final Icons icons = new Icons();
		final ListDialogView lDialog = new ListDialogView(this, null, R.layout.list_row_dialog);
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		final ListAdapter adapter = lDialog.getDialogAdapter(getListEvent(key));
		   
		dialog.setTitle("Event: ");
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
				MapDashboardOverlays itemizedoverlay = new MapDashboardOverlays(drawable, mv, true);
		        OverlayItem overlayitem = new OverlayItem(point_link, "label: "+iconlabel.getText().toString(), "Title: \n"+title.getText().toString());
		        itemizedoverlay.addOverlay(overlayitem);
		        itemizedoverlay.onParentTap(0);
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
	
	private void setBizProfileToMap(){
		final MapView mv = (MapView) findViewById(R.id.mapview);
		List<Overlay> mapOverlays ;
		mapOverlays = mv.getOverlays();
		Drawable drawable = this.getResources().getDrawable(R.drawable.alien);
        itemizedOverlay = new MapDashboardOverlays(drawable, mv, false);
        
        for(int i=0; i< biz[CARREFOUR].length; i++){
        	GeoPoint point = getPoint(biz[CARREFOUR][i][0], biz[CARREFOUR][i][1]);
            OverlayItem overlayItem = new OverlayItem(point, null, null);		
            itemizedOverlay.addOverlay(overlayItem);
            itemizedOverlay.onParentTap(0);
    		mapOverlays.add(itemizedOverlay);
            
        }
        
        drawable = this.getResources().getDrawable(R.drawable.farmstand);
        itemizedOverlay = new MapDashboardOverlays(drawable, mv, false);
        for(int i=0; i< biz[SEVEN_ELEVEN].length; i++){
        	GeoPoint point = getPoint(biz[SEVEN_ELEVEN][i][0], biz[SEVEN_ELEVEN][i][1]);
            OverlayItem overlayItem = new OverlayItem(point, null, null);		
            itemizedOverlay.addOverlay(overlayItem);
            itemizedOverlay.onParentTap(0);
    		mapOverlays.add(itemizedOverlay);
            final MapController mc = mv.getController();
    		mc.animateTo(point);
    		mc.setZoom(13);
        }
        
	}
};