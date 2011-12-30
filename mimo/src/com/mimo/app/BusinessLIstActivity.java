package com.mimo.app;

import java.util.ArrayList;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mimo.app.interfaces.IMenuInstance;
import com.mimo.app.model.pojo.Business;
import com.mimo.app.model.pojo.Icons;
import com.mimo.app.model.pojo.Items;
import com.mimo.app.worker.BusinessWorker;
import com.mimo.app.worker.IBusinessWorker;

public class BusinessLIstActivity extends ListActivity implements
		IMenuInstance, OnClickListener {
	private ProgressDialog progressDialog = null;
	private ArrayList<Items> items = null;
	private OrderAdapter adapter;
	private Runnable viewItems;

	private Business[] dataBusiness;
	private IBusinessWorker businessWorker;
	private Intent intent;
	private ImageButton homeButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_biz);

		// initialize component ui
		homeButton = (ImageButton) findViewById(R.id.home_button);
		homeButton.setOnClickListener(this);

		items = new ArrayList<Items>();
		this.adapter = new OrderAdapter(this, R.layout.row, items);
		this.setListAdapter(this.adapter);

		
		viewItems = new Runnable() {
			@Override
			public void run() {
				getOrders();

			}
		};
		Thread uiThread = new Thread(null, viewItems, "RuntoBackground");
		uiThread.start();
		progressDialog = ProgressDialog.show(BusinessLIstActivity.this,
				"Please wait...", "Retrieving data ...", true);

	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.home_button:
			intent = new Intent(this, MimoActivity.class);
			startActivity(intent);
			break;
		}
	}

	// handle menu ui
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.biz_options_menu, menu);
		return true;
	}

//	public boolean onOptionsItemSelected(MenuItem item) {
//		switch (item.getItemId()) {
//		case R.id.reload:
//			BusinessWorker.setBusinessWorker(null);
//			startActivity(getIntent());
//
//			break;
//		}
//		return true;
//	}

	private Business[] readApiData() {
		businessWorker = BusinessWorker.getInstance();
		dataBusiness = businessWorker.createApiCall();
		return dataBusiness;
	}

	private Runnable returnRes = new Runnable() {

		@Override
		public void run() {
			if (items != null && items.size() > 0) {
				adapter.notifyDataSetChanged();
				for (int i = 0; i < items.size(); i++)
					adapter.add(items.get(i));
			}
			progressDialog.dismiss();
			adapter.notifyDataSetChanged();
		}
	};

	private void getOrders() {
		readApiData();

		Items o;
		try {
			items = new ArrayList<Items>();

			for (int j = 0; j < dataBusiness.length; j++) {
				Business biz = dataBusiness[j];
				o = new Items();
				o.setItemId("" + j);
				o.setItemName(biz.getBizname());
				o.setItemStatus(biz.getDescription());
				o.setDrawableImage(Icons.getInstances().getIconFromBizLabel(
						biz.getBizname()));
				items.add(o);
			}

			Log.i("ARRAY", "" + items.size());
		} catch (Exception e) {
			Log.e("BACKGROUND_PROC", e.getMessage());
		}
		runOnUiThread(returnRes);
	}

	class OrderAdapter extends ArrayAdapter<Items> {

		private ArrayList<Items> items;

		public OrderAdapter(Context context, int textViewResourceId,
				ArrayList<Items> items) {
			super(context, textViewResourceId, items);
			this.items = items;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
	}

}
