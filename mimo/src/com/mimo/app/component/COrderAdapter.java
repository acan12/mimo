package com.mimo.app.component;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mimo.app.BaseListActivity;
import com.mimo.app.R;
import com.mimo.app.model.pojo.Items;

public class COrderAdapter extends ArrayAdapter<Items> {
	private ArrayList<Items> items;
	private Context context;
	private BaseListActivity activity;
	private ProgressDialog progressDialog = null;
	private ComponentFactory componentFactory;
	private COrderAdapter adapter;
	private Runnable viewItems;

	public COrderAdapter(BaseListActivity activity, int textViewResourceId,
			ArrayList<Items> items, ComponentFactory componentFactory) {
		super(activity.getApplicationContext(), textViewResourceId, items);
		this.items = items;
		this.activity = activity;
		this.context = activity.getApplicationContext();
		this.componentFactory = componentFactory;
		this.adapter = this;
	}
	
	public void setItems(ArrayList<Items> items){
		this.items = items;
	}
	
	public ArrayList<Items> getItems(){
		return items;
	}
	
	public Runnable getResultThread(){
		return resultThread();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		v = vi.inflate(R.layout.row, null);
		Items o = items.get(position);
		if (o != null) {
			if (o.getDrawableImage() != 0) {
				ImageView img = (ImageView) v.findViewById(R.id.icon);
				Drawable drawable = v.getResources().getDrawable(
						o.getDrawableImage());
				img.setImageDrawable(drawable);
			}
			TextView tt = (TextView) v.findViewById(R.id.toptext);
			TextView bt = (TextView) v.findViewById(R.id.bottomtext);

			if (tt != null) {
				tt.setText(o.getItemName());
			}
			if (bt != null) {
				bt.setText(o.getItemStatus());
			}
		}
		return v;
	}
	
	public void runThread() {
		viewItems = new Runnable() {
			@Override
			public void run() {
				activity.getResult();
			}
		};
		
		Thread uiThread = new Thread(null, viewItems, "RuntoBackground");
		uiThread.start();
	}
	
	private Runnable returnRes = new Runnable() {
		@Override
		public void run() { 
			if (items != null && items.size() > 0) {
				 adapter.notifyDataSetChanged();
				for (int i = 0; i < items.size(); i++)
					adapter.add(items.get(i));
			}
			adapter.notifyDataSetChanged();
		}
	};
	
	public Runnable resultThread(){
		return returnRes;
	}
	
}