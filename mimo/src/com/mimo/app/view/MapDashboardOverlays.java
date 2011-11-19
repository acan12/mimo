package com.mimo.app.view;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.ViewGroup.MarginLayoutParams;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.mimo.app.DetailActivity;
import com.mimo.app.interfaces.IApp;
import com.mimo.app.interfaces.IConfiguration;

public class MapDashboardOverlays extends BalloonItemizedOverlay<OverlayItem> implements IConfiguration, IApp{

		private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
		private Context mContext ;
		private boolean isTouched;
		
		
		public MapDashboardOverlays(Drawable defaultMarker) {
			super(boundCenterBottom(defaultMarker), null, false);

			// TODO Auto-generated constructor stub
		}
		
		public MapDashboardOverlays(Drawable defaultMarker, Context context) {
			  super(boundCenterBottom(defaultMarker), null, false);
			  this.mContext = context;
		}
		
		public MapDashboardOverlays(Drawable defaultMarker, MapView mapView, boolean setTapEnabled) {
			  super(boundCenterBottom(defaultMarker), mapView, setTapEnabled);
			  this.mContext = mapView.getContext();
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
		
		public boolean onParentTap(int index){
			super.onTap(index);
			return true;
		}
		
		@Override
		public boolean onTap(int index){
			if (mContext != null) {
				OverlayItem itemClicked = mOverlays.get(index);
				
				Intent i = new Intent(mContext, DetailActivity.class);
				i.putExtra(PARAMS_KEY, Integer.parseInt(itemClicked.getTitle().split("#")[1]));
				mContext.startActivity(i);
			} else {
				Log.e("MapOverlays", "mContext is null");
			}
			
			
			return true;
		}
		
}

