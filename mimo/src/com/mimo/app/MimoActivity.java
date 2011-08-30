package com.mimo.app;


import java.util.ArrayList;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.*;
import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
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

public class MimoActivity extends ListActivity {
	

	private ProgressDialog m_ProgressDialog = null;
    private ArrayList<Order> m_orders = null;
    private OrderAdapter m_adapter;
    private Runnable viewOrders;
   
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        m_orders = new ArrayList<Order>();
        this.m_adapter = new OrderAdapter(this, R.layout.row, m_orders);
                setListAdapter(this.m_adapter);
       
        viewOrders = new Runnable(){
            @Override
            public void run() {
                getOrders();
            }
        };
    Thread thread =  new Thread(null, viewOrders, "MagentoBackground");
        thread.start();
        m_ProgressDialog = ProgressDialog.show(MimoActivity.this,    
              "Please wait...", "Retrieving data ...", true);
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
//            Order o1 = new Order();
//            o1.setOrderName("SF services");
//            o1.setOrderStatus("Pending");
//            Order o2 = new Order();
//            o2.setOrderName("SF Advertisement");
//            o2.setOrderStatus("Completed");
//            m_orders.add(o1);
//            m_orders.add(o2);
            
            Order o;
            for(int i=0; i<20; i++){
            	o= new Order();
            	o.setOrderName("SF services");
                o.setOrderStatus("Pending");
                if(i % 2 == 1){
                	o.setDrawableImage(R.drawable.alien);
                }
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
	                if (v == null) {
	                    LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	                    v = vi.inflate(R.layout.row, null);
	                }
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
	                              bt.setText("Status: "+ o.getOrderStatus());
	                        }
	                }
	                return v;
	        }
	}
}