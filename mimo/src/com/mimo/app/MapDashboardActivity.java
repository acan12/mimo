package com.mimo.app;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.mimo.app.view.MapDashboardOverlays;
import com.mimo.app.view.adapter.ListDialogAdapter;

//Test
public class MapDashboardActivity extends MapActivity implements OnClickListener, IConfiguration, IBizProfileData {
	private ActivityEvent ae;
	private GeoPoint point;
	private MapDashboardOverlays itemizedOverlay;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_mapview);
		MapView mv = (MapView) findViewById(R.id.mapview);
		initialize(mv);
		
		if(getIconButton().length >0){
			showButtonImage(getIconButton());
		}else{
			LinearLayout ll = (LinearLayout) findViewById(R.id.event_layout);
			ll.setVisibility(LinearLayout.GONE);
		}
	}
	
	@Override  
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
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

		String labelIcon = Icons.getLabels()[v.getId()];
		int value = 0;
		for(int i=0; i<getIconButton().length; i++){
			if(labelIcon.equalsIgnoreCase(getIconButton()[i].split("@")[0])){
				value = Integer.parseInt(getIconButton()[i].split("@")[1]);
			}
		}
		
		if(	value > 1 ){
			showEventDialog(mv, labelIcon);
		}else{
			ae = new ActivityEvent();
			DBAdapter db = new DBAdapter(this);
			Cursor c = db.getRecordByIcon(labelIcon);
			while(c.moveToNext()){
				ae.setIcon(labelIcon);
				ae.setId(c.getInt(c.getColumnIndex("id")));
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
	        		ae.getIcon(), "Title: \n"+ae.getName());
	        itemizedOverlay.setMapId(ae.getId());
	        itemizedOverlay.addOverlay(overlayItem);
	        itemizedOverlay.onParentTap(0);
			mapOverlays.add(itemizedOverlay);
	        final MapController mc = mv.getController();
			mc.animateTo(point);
			mc.setZoom(16);
		}
	}
	
	private void getEventToNotify(){
		DBAdapter db = new DBAdapter(this);
		List<ActivityEvent> list;
		try {
			list = db.getMostCloselyEvent();
		
			ActivityEvent ae = (ActivityEvent) list.get(0);
			pushEventNotification(ae);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void pushEventNotification(ActivityEvent aex) throws Exception{
		Icons icons = new Icons();
		NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		
		int drawabaleId = icons.getIconFromLabel(aex.getIcon());
		final Notification notification =
			new Notification(drawabaleId, 
					aex.getElapsedTime(aex.getStartDate(), aex.getStartTime()),
					System.currentTimeMillis()+20);
		
		Context context = getApplicationContext();
		CharSequence contentTitle = aex.getName()+" - "+aex.getElapsedTime(aex.getStartDate(), aex.getStartTime());
		CharSequence contentText = aex.getDescription();
		Intent notificationIntent = new Intent(this, MapDashboardActivity.class);
		
		double[] loc = {drawabaleId, aex.getLat(), aex.getLng()};
		notificationIntent.putExtra(IAPP_INTENT_ACTIVITY_OBJECT_KEY, aex);
		
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, Intent.FLAG_ACTIVITY_NEW_TASK);
		
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		
		notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
		nm.notify(1, notification);
		
		
	}

	private void showEventDialog(final MapView mv, final String key) {
		
		final Icons icons = new Icons();
		final ListDialogAdapter adapter = new ListDialogAdapter(this, getListEvent(key) );
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		   
		dialog.setTitle("Event: ");
		dialog.setAdapter(adapter, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				ActivityEvent aex = (ActivityEvent) adapter.getItemObject(which);
				TextView title = (TextView)adapter.getView(which, null, null).findViewById(R.id.title);
				TextView idevent = (TextView)adapter.getView(which, null, null).findViewById(R.id.hidden_value_id);
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
				MapDashboardOverlays itemizedOverlay = new MapDashboardOverlays(drawable, mv, true);
		        OverlayItem overlayitem = new OverlayItem(point_link, iconlabel.getText().toString(), "Title: \n"+title.getText().toString());
		        itemizedOverlay.setMapId(aex.getId());
		        itemizedOverlay.addOverlay(overlayitem);
		        itemizedOverlay.onParentTap(0);
		        mapOverlays.add(itemizedOverlay);
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
			aev.setId(c.getInt(c.getColumnIndex("id")));
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

		// Biz profile Carrefour
		int carefourIcon = Icons.getInstances().getIconFromBizLabel("Carefour");
		Drawable drawable = this.getResources().getDrawable(carefourIcon);
        itemizedOverlay = new MapDashboardOverlays(drawable, mv, false);
        for(int i=0; i< biz[CARREFOUR].length; i++){
        	GeoPoint point = getPoint(biz[CARREFOUR][i][0], biz[CARREFOUR][i][1]);
            OverlayItem overlayItem = new OverlayItem(point, null, null);		
            itemizedOverlay.addOverlay(overlayItem);
            itemizedOverlay.onParentTap(0);
    		mapOverlays.add(itemizedOverlay);
            
        }
        
        
        // Biz location 7-11
        int sevenIcon = Icons.getInstances().getIconFromBizLabel("7eleven");
        drawable = this.getResources().getDrawable(sevenIcon);
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
        
        mv.setBuiltInZoomControls(true);
	}
	
	private void initialize(MapView mv) {
		Bundle b = getIntent().getExtras();
		
		if(b != null){
			ActivityEvent aex = (ActivityEvent) getIntent().getSerializableExtra(IAPP_INTENT_ACTIVITY_OBJECT_KEY);
			Icons icons = new Icons();
			List<Overlay> mapOverlays;
			Drawable drawable = this.getResources().getDrawable(icons.getIconFromLabel(aex.getIcon()));
	        itemizedOverlay = new MapDashboardOverlays(drawable, mv, false);
	        
	        point = getPoint(aex.getLat(), aex.getLng());
	        
	        mapOverlays = mv.getOverlays();

	        drawable = this.getResources().getDrawable(new Icons().getIconFromLabel(aex.getIcon()));
	        itemizedOverlay = new MapDashboardOverlays(drawable, mv, true);
			
	        
			OverlayItem overlayItem = new OverlayItem(point, 
	        		aex.getIcon(), "Title: \n"+aex.getName());
			
			itemizedOverlay.setMapId(aex.getId());
            itemizedOverlay.addOverlay(overlayItem);
            itemizedOverlay.onParentTap(0);
            mapOverlays.add(itemizedOverlay);
            
            mv.setBuiltInZoomControls(true);
    		mv.getController().setCenter(point);
    		mv.getController().setZoom(16);
    		
		}else{
			setBizProfileToMap();
			getEventToNotify();
		}
		
	} 
	

	private String[] getIconButton(){
		ae = new ActivityEvent();
		DBAdapter db = new DBAdapter(this);
		Cursor c = db.getIconsUniqRecord();    
		int i =0;
		String[] activityIcon = new String[c.getCount()];
		while(c.moveToNext()){
			String key = c.getString(c.getColumnIndex("icon"));
			String value = c.getString(c.getColumnIndex("count_record"));
			activityIcon[i++] = key+"@"+value;
		}
		
		return activityIcon;
	} 
	
	private void showButtonImage(String[] iconLabel){
		Display display = getWindowManager().getDefaultDisplay();
		int widthDisplay = display.getWidth();
		int heightDisplay = display.getHeight();
		boolean isPortrait = (widthDisplay < heightDisplay);
		
		LinearLayout ll = (LinearLayout) findViewById(R.id.container_button_linearLayout);
		
		LayoutInflater inflater = LayoutInflater.from(this);
		Icons icons = new Icons();
		for(int i=0; i<iconLabel.length; i++){ 
			ImageButton imb = new ImageButton(this);
			ImageView sep = new ImageView(this);
			TextView t = new TextView(this);
			imb.setId(icons.getIndexFromLabel(iconLabel[i].split("@")[0]));
			imb.setImageResource(icons.getIconFromLabel(iconLabel[i].split("@")[0]));
			imb.setBackgroundColor(Color.TRANSPARENT);
			
			
			if(isPortrait){
				sep.setImageResource(R.drawable.separator);
			}else{
				sep.setImageResource(R.drawable.separator_horizontal);
				sep.setPadding(0, 0, 20, 0);
			}
			
			t.setText(iconLabel[i].split("@")[0]);
			t.setGravity(Gravity.CENTER);
			t.setTextSize(13f);
			ll.addView(imb);
			ll.addView(t);
			ll.addView(sep);
			imb.setOnClickListener(this);
		}
		
	}
	
	
	private GeoPoint getPoint(double lat, double lng){
		return new GeoPoint((int)(lat*1000000.0), (int)(lng*1000000.0));
	}
	
};