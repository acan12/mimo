package com.mimo.app;


import java.util.ArrayList;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.mimo.app.model.adapter.DBAdapter;
import com.mimo.app.model.pojo.ActivityEvent;
import com.mimo.app.model.pojo.Icons;

import android.*;
import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class ActivitiesListActivity extends ListActivity {
	

	private ProgressDialog m_ProgressDialog = null;
    private ArrayList<Order> m_orders = null;
    private OrderAdapter m_adapter;
    private Runnable viewOrders;
   
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activities);
        
        m_orders = new ArrayList<Order>();
        this.m_adapter = new OrderAdapter(this, R.layout.row, m_orders);
        this.setListAdapter(this.m_adapter);
       
        viewOrders = new Runnable(){
            @Override
            public void run() {
                getOrders();
            }
        };
        Thread thread =  new Thread(null, viewOrders, "RuntoBackground");
        thread.start();
        m_ProgressDialog = ProgressDialog.show(ActivitiesListActivity.this,    
              "Please wait...", "Retrieving data ...", true);
        
        
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	Order o = (Order)l.getItemAtPosition(position);
    	Intent i = new Intent(this, DetailActivity.class);
    	i.putExtra("paramid", Integer.parseInt(o.getOrderId()));
		startActivity(i); 
    }
    
    private Runnable returnRes = new Runnable() {

	    @Override
	    public void run() {
	        if(m_orders != null && m_orders.size() > 0){
	            m_adapter.notifyDataSetChanged();
	            for(int i=0;i<m_orders.size();i++)
	            m_adapter.add(m_orders.get(i));
	        }
	        m_ProgressDialog.dismiss();
	        m_adapter.notifyDataSetChanged();
	    }
    };
    
    private void getOrders(){
	    try{
	        m_orders = new ArrayList<Order>();
	        
	        Order o;
	        ActivityEvent ae;
	        DBAdapter db = new DBAdapter(this);
			Cursor c = db.getAllRecord(); //retrieve all records
			
			Icons icons = new Icons();
			while(c.moveToNext()){
				ae = new ActivityEvent();
				ae.setName(c.getString(c.getColumnIndex("name")));
				ae.setIcon(c.getString(c.getColumnIndex("icon")));
				ae.setDescription(c.getString(c.getColumnIndex("description")));
				ae.setStart_date(c.getString(c.getColumnIndex("st_date")));
				ae.setStart_time(c.getString(c.getColumnIndex("st_time")));
				ae.setEnd_date(c.getString(c.getColumnIndex("end_date")));
				ae.setEnd_time(c.getString(c.getColumnIndex("end_time")));
				ae.setLat(c.getDouble(c.getColumnIndex("lat")));
				ae.setLng(c.getDouble(c.getColumnIndex("lng")));
				
				o= new Order();
				o.setOrderId(c.getString(c.getColumnIndex("id")));
				o.setOrderName(ae.getIcon());
				o.setOrderStatus(ae.getName()+"-"+ae.getDescription()+", "+ae.getStart_date());
				o.setDrawableImage(icons.getIconFromLabel(ae.getIcon()));
				
				m_orders.add(o);
	    	}	
			
	        Thread.sleep(5000);
	        Log.i("ARRAY", ""+ m_orders.size());
	      } catch (Exception e) {
	        Log.e("BACKGROUND_PROC", e.getMessage());
	      }
          runOnUiThread(returnRes);
	}
    
    
    class OrderAdapter extends ArrayAdapter<Order> {

        private ArrayList<Order> items;

        public OrderAdapter(Context context, int textViewResourceId, ArrayList<Order> items) {
	        super(context, textViewResourceId, items);
	        this.items = items;
        }
        
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
	        View v = convertView;
	        LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        v = vi.inflate(R.layout.row, null);
	        Order o = items.get(position);
	        if (o != null) {
	    		if(o.getDrawableImage() != 0){
	        		ImageView img =(ImageView) v.findViewById(R.id.icon);
	        		Drawable drawable = v.getResources().getDrawable(o.getDrawableImage());
	        		img.setImageDrawable(drawable);
	    		}
	            TextView tt = (TextView) v.findViewById(R.id.toptext);
	            TextView bt = (TextView) v.findViewById(R.id.bottomtext);
	            
	            if (tt != null) {
	                  tt.setText("Name: "+o.getOrderName());                            }
	            if(bt != null){
	                  bt.setText("Event: "+ o.getOrderStatus());
	            }
	        }
	        return v;
        }
    }
}