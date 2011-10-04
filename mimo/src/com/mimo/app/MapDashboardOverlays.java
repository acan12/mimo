package com.mimo.app;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;
import com.mimo.app.interfaces.Configuration;

public class MapDashboardOverlays extends ItemizedOverlay<OverlayItem> implements Configuration{

		private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
		private Context mContext ;
		private boolean isTouched;
		
		
		public MapDashboardOverlays(Drawable defaultMarker) {
			super(boundCenterBottom(defaultMarker));

			// TODO Auto-generated constructor stub
		}
		
		public MapDashboardOverlays(Drawable defaultMarker, Context context) {
			  super(boundCenterBottom(defaultMarker));
			  this.mContext = context;
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
		public boolean onTap(int index){
			Log.d("MapOverlays", "mContext is nullxxxxxxxxxxxxxxx");
			if (mContext != null) {
				Intent i = new Intent(mContext, ActivitiesListActivity.class);
				mContext.startActivity(i);
			} else {
				Log.e("MapOverlays", "mContext is null");
			}
			
			
			return true;
		}
		
}

