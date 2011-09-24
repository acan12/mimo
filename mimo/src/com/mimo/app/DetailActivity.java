package com.mimo.app;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.mimo.app.adapter.DBAdapter;
import com.mimo.app.pojo.ActivityEvent;
import com.mimo.app.pojo.Icons;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.WebIconDatabase.IconListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class DetailActivity extends Activity implements OnClickListener, Configuration, Serializable{

	
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_detail);
		reloadForm(5);
		
		Button bDetail = (Button)findViewById(R.id.btn_detail);
		bDetail.setOnClickListener(this);
		Button bBack = (Button)findViewById(R.id.btn_back);
		bBack.setOnClickListener(this);
	}
	
	private void reloadForm(int id){
		DBAdapter db = new DBAdapter(this);
		db.open();
		Cursor c = db.getRecord(id);
		
		ActivityEvent ae ;
		do{
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
			
		}while(c.moveToNext());
		
//		setActivity(ae);
		db.close();
		Icons idIcon = new Icons();
		String[] labels = idIcon.getLabels();
		final int[] icons = idIcon.getIcons();
		
		//set icon 
		ImageButton img =(ImageButton)findViewById(R.id.icon_activity);
		Drawable drawable = getResources().getDrawable(idIcon.getIconFromLabel(ae.getIcon()));
		img.setImageDrawable(drawable);
		
		//set icon name
		TextView eIcon = (TextView)findViewById(R.id.icon_label);
		eIcon.setText(ae.getIcon());
		
		//set activity name
		TextView eName = (TextView)findViewById(R.id.labelName);
		eName.setText("Name : "+ae.getName());
		
		//set activity description
		TextView eDescription = (TextView)findViewById(R.id.labelDescription);
		eDescription.setText("Description : "+ae.getDescription());
		
		//set activity start/end date
		TextView eStartDate = (TextView)findViewById(R.id.labelStart);
		eStartDate.setText("Start : "+ae.getStart_date()+", "+ae.getStart_time());

		TextView eEndDate = (TextView)findViewById(R.id.labelEnd);
		eEndDate.setText("End : "+ae.getEnd_date()+", "+ae.getEnd_time());

		
		
		//set activity lat/lng value
		TextView eLat = (TextView)findViewById(R.id.labelLocation);
		eLat.setText("Location : "+ae.getLat()+", "+ae.getLng());
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.btn_detail:
			TextView tv = (TextView)findViewById(R.id.hidden_value);
			
			
			Intent i = new Intent(this, InputDetailActivity.class);
			i.putExtra("param1", ACTION_UPDATE);
//			i.putExtra("param1", ACTION_ADD);
			i.putExtra("paramid", Integer.parseInt(tv.getText().toString()));
			startActivity(i);
			break;
		case R.id.btn_back:
			Intent intent = new Intent(this, MapDashboardActivity.class);
			startActivity(intent);
			break;
		}
	}

}
