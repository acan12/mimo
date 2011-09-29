package com.mimo.app;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.mimo.app.interfaces.Configuration;
import com.mimo.app.model.adapter.DBAdapter;
import com.mimo.app.model.pojo.ActivityEvent;
import com.mimo.app.model.pojo.Icons;
import com.mimo.app.view.BaseView;
import com.mimo.app.view.detail.DetailView;
import com.mimo.app.view.form.FormInputView;

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

public class DetailActivity extends DetailView implements Configuration {
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState); 
		
		Bundle bundle = getIntent().getExtras();
		int paramid = bundle.getInt("paramid");
		initialize(R.layout.layout_detail);
		showDetail(paramid);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){ 
		case R.id.btn_detail:
			TextView tv = (TextView)findViewById(R.id.hidden_value);
			Intent i = new Intent(this, InputDetailActivity.class);
			i.putExtra("paramaction", ACTION_UPDATE);
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
