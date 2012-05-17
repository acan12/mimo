package com.mimo.app;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;

import com.mimo.app.component.COrderAdapter;
import com.mimo.app.component.IComponentFactory;
import com.mimo.app.interfaces.IApp;
import com.mimo.app.model.adapter.DBAdapter;
import com.mimo.app.model.pojo.ActivityEvent;
import com.mimo.app.model.pojo.Icons;
import com.mimo.app.model.pojo.Items;

public class EventListActivity extends BaseListActivity implements IApp,
		OnClickListener {

	private ProgressDialog progressDialog = null;
	private ArrayList<Items> items = null;
	private COrderAdapter adapter;
	private ImageButton homeButton;
	private IComponentFactory componentFactory;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_activities);

		// initialize component ui
		homeButton = (ImageButton) findViewById(R.id.home_button);
		homeButton.setOnClickListener(this);

		items = new ArrayList<Items>();
		this.componentFactory = getComponentFactory();
		this.adapter = componentFactory.createOrderList(this, R.layout.row,
				items);

		this.setListAdapter(this.adapter);
		adapter.runThread();
		progressDialog = showDialogProgress();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Items o = (Items) l.getItemAtPosition(position);
		Intent i = new Intent(this, DetailActivity.class);
		i.putExtra(PARAMS_KEY, Integer.parseInt(o.getItemId()));
		startActivity(i);
	}

	@Override
	protected void getData() {
		// TODO Auto-generated method stub
		try {
			items = new ArrayList<Items>();

			buildDataComponent(null);
			Thread.sleep(5000);
			Log.i("ARRAY", "" + items.size());
		} catch (Exception e) {
			Log.e("BACKGROUND_PROC", e.getMessage());
		}

		adapter.setItems(items);
		runOnUiThread(adapter.resultThread());

		progressDialog.dismiss();
	}

	@Override
	protected void buildDataComponent(Object[] data) {
		// TODO Auto-generated method stub
		Items o;
		ActivityEvent ae;
		DBAdapter db = new DBAdapter(this);
		Cursor c = db.getAllRecord(); // retrieve all records

		Icons icons = new Icons();
		while (c.moveToNext()) {
 
			ae = new ActivityEvent();
			ae.setName(c.getString(c.getColumnIndex("name")));
			ae.setIcon(c.getString(c.getColumnIndex("icon")));
			ae.setDescription(c.getString(c.getColumnIndex("description")));
			ae.setStartDate(c.getString(c.getColumnIndex("st_date")));
			ae.setStartTime(c.getString(c.getColumnIndex("st_time")));
			ae.setEndDate(c.getString(c.getColumnIndex("end_date")));
			ae.setEndTime(c.getString(c.getColumnIndex("end_time")));
			ae.setLat(c.getDouble(c.getColumnIndex("lat")));
			ae.setLng(c.getDouble(c.getColumnIndex("lng")));
			
			o = new Items();
			o.setItemId(c.getString(c.getColumnIndex("id")));
			o.setItemName(ae.getIcon());

			String description = ae.getName() + ", " + ae.getDescription()
					+ "\nAt: \n " + ae.getStartDate() + " " + ae.getStartTime()
					+ " - " + ae.getEndDate() + " " + ae.getEndTime()
					+ "\n" + ae.getElapsedTime(ae.getStartDate(), ae.getStartTime()) ;
			o.setItemStatus(description);
			o.setDrawableImage(icons.getIconFromLabel(ae.getIcon()));

			items.add(o);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == homeButton) {
			intent = new Intent();
			intent.setClass(this, HomeActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}

	}
}