package com.mimo.app;


import java.util.ArrayList;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mimo.app.interfaces.IApp;
import com.mimo.app.model.adapter.DBAdapter;
import com.mimo.app.model.pojo.ActivityEvent;
import com.mimo.app.model.pojo.Icons;
import com.mimo.app.model.pojo.Items;
 
public class ActivitiesListActivity extends ListActivity implements IApp{
	 

	private ProgressDialog progressDialog = null;
    private ArrayList<Items> items = null;
    private OrderAdapter adapter;
    private Runnable viewItems;
   
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activities);
        
        items = new ArrayList<Items>();
        this.adapter = new OrderAdapter(this, R.layout.row, items);
        this.setListAdapter(this.adapter);
       
        viewItems = new Runnable(){
            @Override
            public void run() {
                getItems();
            }
        };
        Thread thread =  new Thread(null, viewItems, "RuntoBackground");
        thread.start();
        progressDialog = ProgressDialog.show(ActivitiesListActivity.this,    
              "Please wait...", "Retrieving data ...", true);
        
        
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	Items o = (Items)l.getItemAtPosition(position);
    	Intent i = new Intent(this, DetailActivity.class);
    	i.putExtra(PARAMS_KEY, Integer.parseInt(o.getItemId()));
		startActivity(i); 
    }
    
    private Runnable returnRes = new Runnable() {

	    @Override
	    public void run() {
	        if(items != null && items.size() > 0){
	            adapter.notifyDataSetChanged();
	            for(int i=0;i<items.size();i++)
	            adapter.add(items.get(i));
	        }
	        progressDialog.dismiss();
	        adapter.notifyDataSetChanged();
	    }
    };
    
    private void getItems(){
	    try{
	        items = new ArrayList<Items>();
	        
	        Items o;
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
				
				o= new Items();
				o.setItemId(c.getString(c.getColumnIndex("id")));
				o.setItemName(ae.getIcon());
				o.setItemStatus(ae.getName()+", "+ae.getDescription()+" \n At "+ae.getStart_date()+" "+ae.getStart_time()+" - "+ae.getEnd_date()+" "+ae.getEnd_time());
				o.setDrawableImage(icons.getIconFromLabel(ae.getIcon()));
				
				items.add(o);
	    	}	
			
	        Thread.sleep(5000);
	        Log.i("ARRAY", ""+ items.size());
	      } catch (Exception e) {
	        Log.e("BACKGROUND_PROC", e.getMessage());
	      }
          runOnUiThread(returnRes);
	}
    
    
    class OrderAdapter extends ArrayAdapter<Items> {

        private ArrayList<Items> items;

        public OrderAdapter(Context context, int textViewResourceId, ArrayList<Items> items) {
	        super(context, textViewResourceId, items);
	        this.items = items;
        }
        
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
	        View v = convertView;
	        LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        v = vi.inflate(R.layout.row, null);
	        Items o = items.get(position);
	        if (o != null) {
	    		if(o.getDrawableImage() != 0){
	        		ImageView img =(ImageView) v.findViewById(R.id.icon);
	        		Drawable drawable = v.getResources().getDrawable(o.getDrawableImage());
	        		img.setImageDrawable(drawable);
	    		}
	            TextView tt = (TextView) v.findViewById(R.id.toptext);
	            TextView bt = (TextView) v.findViewById(R.id.bottomtext);
	            
	            if (tt != null) {
	                  tt.setText(o.getItemName());                        }
	            if(bt != null){
	                  bt.setText(o.getItemStatus());
	            }
	        }
	        return v;
        }
    }
}