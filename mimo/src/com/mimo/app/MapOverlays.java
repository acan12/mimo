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
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class MapOverlays extends ItemizedOverlay<OverlayItem>{

	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context mContext ;
	public MapOverlays(Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));

		// TODO Auto-generated constructor stub
	}
	public MapOverlays(Drawable defaultMarker, Context context) {
		  super(boundCenterBottom(defaultMarker));
		  mContext = context;
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
	
	public boolean onTap(int index){
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
//        if (event.getAction() == 1) {                
//            GeoPoint p = mapView.getProjection().fromPixels(
//                (int) event.getX(),
//                (int) event.getY());
////                Toast.makeText(this.mContext, 
////                    p.getLatitudeE6() / 1E6 + "," + 
////                    p.getLongitudeE6() /1E6 , 
////                    Toast.LENGTH_SHORT).show();
//            List<Overlay> mapOverlays = mapView.getOverlays();
//            Drawable drawable = this.mContext.getResources().getDrawable(R.drawable.cheese);
//            MapOverlays itemizedoverlay = new MapOverlays(drawable, this.mContext);
//            OverlayItem overlayitem = new OverlayItem(p, null, "You are in Butcher Location.");
//            itemizedoverlay.addOverlay(overlayitem);
//            mapOverlays.add(itemizedoverlay);
//        }                            
//        return false;
//    }   
}
