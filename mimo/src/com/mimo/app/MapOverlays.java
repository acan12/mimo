package com.mimo.app;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.graphics.drawable.Drawable;
import android.sax.StartElementListener;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.mimo.app.interfaces.IConfiguration;

public class MapOverlays extends ItemizedOverlay<OverlayItem> implements IConfiguration{

	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context mContext ;
	private boolean isTouched;
	private boolean isTapMarker;
	private boolean isTap=false;
	private int idActivity;
	
	public MapOverlays(Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));

		// TODO Auto-generated constructor stub
	}
	
	public MapOverlays(Drawable defaultMarker, Context context, boolean setTouchedEnabled) {
		  super(boundCenterBottom(defaultMarker));
		  this.mContext = context;
		  this.isTouched = setTouchedEnabled;
	}
	
	public MapOverlays(Drawable defaultMarker, Context context, boolean setTouchedEnabled, boolean setTapMarkerEnabled) {
		  super(boundCenterBottom(defaultMarker));
		  this.mContext = context;
		  this.isTouched = setTouchedEnabled;
		  this.isTapMarker = true;
	}
	
	public MapOverlays(Drawable defaultMarker, Context context, boolean setTouchedEnabled, boolean setTapMarkerEnabled, boolean setTapEnabled, int id) {
		  super(boundCenterBottom(defaultMarker));
		  this.mContext = context;
		  this.isTouched = setTouchedEnabled;
		  this.isTapMarker = true;
		  this.isTap = setTapEnabled;
		  this.idActivity = id;
	}
	
	@Override
	protected OverlayItem createItem(int i) {
		// TODO Auto-generated method stub
		return mOverlays.get(i);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return mOverlays.size();

	}

	public void addOverlay(OverlayItem overlay) {
	    mOverlays.add(overlay);
	    populate();
	}
	
	@Override
	public boolean onTap(final GeoPoint gp, MapView mapView){
		if(isTap){
			String coord = gp.getLatitudeE6() / 1E6 + "," +gp.getLongitudeE6() /1E6;
			
			AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
			dialog.setTitle("Activity Location");
			dialog.setMessage("You choose this location ("+coord+")");
			dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	        	   Intent i = new Intent(mContext, InputDetailActivity.class);
	        	   p.println("----tap overlay----paramid:"+idActivity);
	        	   if(idActivity == 0) 
	        	   {i.putExtra("paramaction", DB_CREATE); }
	        	   else
	        	   {i.putExtra("paramaction", DB_UPDATE);}
	        	   p.println("----from mapoverlay lat:"+gp.getLatitudeE6()/1E6);
	        	   p.println("----from mapoverlay lon:"+gp.getLongitudeE6()/1E6);
	        	   
	        	   i.putExtra("paramid", idActivity);
	        	   i.putExtra("paramlat", gp.getLatitudeE6() / 1E6);
	        	   i.putExtra("paramlng", gp.getLongitudeE6() / 1E6);
	   			   mContext.startActivity(i);
	           }
	        })
	        .setNegativeButton("No", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	                dialog.cancel();
	           }
	        });
			dialog.show();	
				
		};
		Log.d("test value :", "isTapMarker="+isTapMarker);
//		else if(isTapMarker){
//			Intent i = new Intent(mContext, ActivitiesListActivity.class);
//			mContext.startActivity(i);
//		}
		
		return true;
	}
	
	@Override
	public boolean onTap(int index){
		Log.d("MapOverlays", "mContext is nullxxxxxxxxxxxxxxx");
		if (mContext != null) {
			Intent i = new Intent(mContext, ActivitiesListActivity.class);
			mContext.startActivity(i);
		} else {
			Log.e("MapOverlays", "mContext is null");
		}
		
		
		
//		OverlayItem item = mOverlays.get(index);
//		AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
//		dialog.setTitle(item.getTitle());
//		dialog.setMessage(item.getSnippet());
//		dialog.setPositiveButton("Close", new OnClickListener(){
//			
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				// TODO Auto-generated method stub
//				dialog.dismiss();
//			}
//		});
//		dialog.show();
		return true;
	}
	
	
//	@Override
//    public boolean onTouchEvent(MotionEvent event, MapView mapView) 
//    {   
//        //---when user lifts his finger---
//        if (event.getAction() == 1 && isTouched) {                
//            GeoPoint p = mapView.getProjection().fromPixels(
//                (int) event.getX(),
//                (int) event.getY());
//            
//    		AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
//    		dialog.setTitle("Suggestion Location: ");
//    		dialog.setMessage("Set Location coordinate: "+
//    				p.getLatitudeE6() / 1E6 + "," + 
//                    p.getLongitudeE6() /1E6 );
//    		dialog.setPositiveButton("Close", new OnClickListener(){
//    			
//    			@Override
//    			public void onClick(DialogInterface dialog, int which) {
//    				// TODO Auto-generated method stub
//    				dialog.dismiss();
//    			}
//    		});
//    		dialog.show();
//           
////            List<Overlay> mapOverlays = mapView.getOverlays();
////            Drawable drawable = this.mContext.getResources().getDrawable(R.drawable.cheese);
////            MapOverlays itemizedoverlay = new MapOverlays(drawable, this.mContext);
////            OverlayItem overlayitem = new OverlayItem(p, null, "You are in Butcher Location.");
////            itemizedoverlay.addOverlay(overlayitem);
////            mapOverlays.add(itemizedoverlay);
//        }                            
//        return false;
//    }   
}
