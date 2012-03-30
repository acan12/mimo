package com.mimo.app;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
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
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
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
import com.mimo.app.view.list.ListDialogView;

//Test
public class MapDashboardActivity extends MapActivity implements OnClickListener, IConfiguration, IBizProfileData {
	private Hashtable iconHash;
	private ActivityEvent ae;
	private GeoPoint point;
	private MapDashboardOverlays itemizedOverlay;
	private int EVENT_NOTIFY_ID = 1;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_mapview);
		MapView mv = (MapView) findViewById(R.id.mapview);
		initialize(mv);
		
        // counting dynamic button image
		Hashtable h = getIconButton();
		Iterator it = h.keySet().iterator();
		String[] iconLabel = new String[h.keySet().size()];
		int i=0;
		while(it.hasNext()){
			iconLabel[i++] = (String)it.next();
		}
        
		if(iconLabel.length >0){
			showButtonImage(iconLabel);
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
	        		ae.getIcon()+" #"+ae.getId(), "Title: \n"+ae.getName());
	        
	        itemizedOverlay.addOverlay(overlayItem);
	        itemizedOverlay.onParentTap(0);
			mapOverlays.add(itemizedOverlay);
	        final MapController mc = mv.getController();
			mc.animateTo(point);
			mc.setZoom(16);
		}
	}
	
	
/****************************************
 * Custom method : add by developer used
 ****************************************/
	
	private void getEventToNotify() throws Exception{
		DBAdapter db = new DBAdapter(this);
		Log.d("....................tesss masuk db most ", "masuk notify");
		List<ActivityEvent> list = db.getMostCloselyEvent();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			ActivityEvent ae = (ActivityEvent)it.next();
			pushEventNotification(ae);
		}
	}
	
	private void pushEventNotification(ActivityEvent ae) throws Exception{
		Icons icons = new Icons();
//		ae = (ActivityEvent) getEventToNotify();
		NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		int drawabaleId = icons.getIconFromLabel(ae.getIcon());
		final Notification notification =
			new Notification(drawabaleId, 
					ae.getMessage(),
					System.currentTimeMillis()+20);
		
		Context context = getApplicationContext();
		CharSequence contentTitle = ae.getName();
		CharSequence contentText = ae.getDescription();
		Intent notificationIntent = new Intent(this, MapDashboardActivity.class);
		
		double[] loc = {drawabaleId, ae.getLat(), ae.getLng()};
		notificationIntent.putExtra("pLocation", loc);
		
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

		notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
	
		nm.notify(ae.getStatus(), notification);
		
	}

	private void showEventDialog(final MapView mv, final String key) {
		
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
				MapDashboardOverlays itemizedoverlay = new MapDashboardOverlays(drawable, mv, true);
		        OverlayItem overlayitem = new OverlayItem(point_link, "label: "+iconlabel.getText().toString()+" #"+idevent.getText(), "Title: \n"+title.getText().toString());
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
			double[] loc = b.getDoubleArray("pLocation");
			if(loc.length>0){
				List<Overlay> mapOverlays;
				Drawable drawable = this.getResources().getDrawable((int)loc[0]);
		        itemizedOverlay = new MapDashboardOverlays(drawable, mv, false);
		        
				point = getPoint(loc[1], loc[2]);
				mapOverlays = mv.getOverlays();
				OverlayItem overlayItem = new OverlayItem(point, null, null);		
	            itemizedOverlay.addOverlay(overlayItem);
	            itemizedOverlay.onParentTap(0);
	            mapOverlays.add(itemizedOverlay);
	            
	            mv.setBuiltInZoomControls(true);
	    		mv.getController().setCenter(point);
	    		mv.getController().setZoom(13);
	    		
	    		NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	    		nm.cancel(1);
			}
		}else{
			setBizProfileToMap();
		}
		
		try{
			getEventToNotify();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
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
			imb.setId(icons.getIndexFromLabel(iconLabel[i]));
			imb.setImageResource(icons.getIconFromLabel(iconLabel[i]));
			imb.setBackgroundColor(Color.TRANSPARENT);
			
			
			if(isPortrait){
				sep.setImageResource(R.drawable.separator);
			}else{
				sep.setImageResource(R.drawable.separator_horizontal);
				sep.setPadding(0, 0, 20, 0);
			}
			
			t.setText(iconLabel[i]);
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