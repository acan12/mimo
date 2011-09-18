package com.mimo.app;

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
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
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

public class DetailActivity extends Activity implements OnClickListener{

	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_detail);
		Button bDetail = (Button)findViewById(R.id.btn_detail);
		bDetail.setOnClickListener(this);
		Button bBack = (Button)findViewById(R.id.btn_back);
		bBack.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.btn_detail:
			Intent i = new Intent(this, InputDetailActivity.class);
			startActivity(i);
			break;
		case R.id.btn_back:
			Intent intent = new Intent(this, MapDashboardActivity.class);
			startActivity(intent);
			break;
		}
	}
}
