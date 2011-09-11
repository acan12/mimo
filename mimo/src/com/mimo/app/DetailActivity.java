package com.mimo.app;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

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

public class DetailActivity extends Activity implements OnClickListener{

	static final int DATE_DIALOG_ID = 0;
	static final int TIME_DIALOG_ID = 1;
	static final int DATE_END_DIALOG_ID = 2;
	static final int TIME_END_DIALOG_ID = 3;
	private int mYear;
	private int mMonth;
	private int mDay;
	private int mhour;
	private int mminute;
	
	private TextView mDateDisplay, mDateDisplayEnd;
	private TextView mTimeDisplay, mTimeDisplayEnd;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_detail);
		
		Button btn = (Button)findViewById(R.id.btn_submit);
		btn.setOnClickListener(this);
		Button btncancel = (Button)findViewById(R.id.btn_cancel);
		btncancel.setOnClickListener(this);
		ImageButton iconActivity = (ImageButton)findViewById(R.id.iconActivity);
		iconActivity.setOnClickListener(this);
		Button mapPicker = (Button)findViewById(R.id.bMapPicker);
		mapPicker.setOnClickListener(this);
		
		mDateDisplay =(TextView)findViewById(R.id.tStarDate);
		mTimeDisplay = (TextView) findViewById(R.id.tStartTime);
		mDateDisplayEnd =(TextView)findViewById(R.id.tEndDate);
		mTimeDisplayEnd = (TextView) findViewById(R.id.tEndTime);
		
		Button mPickDate =(Button)findViewById(R.id.bStartDatePicker);
		Button mPickTime =(Button)findViewById(R.id.bStartTimePicker);
		Button mPickDateEnd =(Button)findViewById(R.id.bEndDatePicker);
		Button mPickTimeEnd =(Button)findViewById(R.id.bEndTimePicker);
		
		//Pick Date's /Time Start click event listener
		mPickDate.setOnClickListener(this);
		mPickTime.setOnClickListener(this);
		
		//Pick Date time's End click event listener
	    mPickDateEnd.setOnClickListener(this);
	    mPickTimeEnd.setOnClickListener(this);
	    
	    final Calendar c = Calendar.getInstance();
	    mYear = c.get(Calendar.YEAR);
	    mMonth = c.get(Calendar.MONTH);
	    mDay = c.get(Calendar.DAY_OF_MONTH);
	    mhour = c.get(Calendar.HOUR_OF_DAY);
	    mminute = c.get(Calendar.MINUTE);

	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()){
		case R.id.bStartDatePicker:
			showDialog(DATE_DIALOG_ID);
			break;
		case R.id.bStartTimePicker:
			showDialog(TIME_DIALOG_ID);
			break;
		case R.id.bEndDatePicker:
			showDialog(DATE_END_DIALOG_ID);
			break;
		case R.id.bEndTimePicker:
			showDialog(TIME_END_DIALOG_ID);
			break;
		case R.id.bMapPicker:
			Intent mapIntent = new Intent(this, MapLocation.class);
			this.startActivity(mapIntent);
			break;
		case R.id.btn_submit:
			Intent i = new Intent(this, MapDashboardActivity.class);
			this.startActivity(i);
			break;
		case R.id.btn_cancel:
			Intent intent = new Intent(this, MapDashboardActivity.class);
			this.startActivity(intent);
			break;
		case R.id.iconActivity:
			String[] labels = {"Entertainment", 
					"Food/Fun", 
					"News",
					"Meeting",
					"Flavour",
					"Sport",
					"Art",
					"Junk Food",
					"Space",
					"Walking",
					"Fitness",
					"Holiday",
					"Market",
					"Community",
					"Office"};
			
			final int[] icons = {
					R.drawable.alien, 
					R.drawable.bread, 
					R.drawable.butcher2, 
					R.drawable.candy,
					R.drawable.cheese,
					R.drawable.eggs,
					R.drawable.farmstand,
					R.drawable.fruits,
					R.drawable.japanese_sweet2,
					R.drawable.patisserie,
					R.drawable.sandwich2,
					R.drawable.alien, 
					R.drawable.bread, 
					R.drawable.butcher2, 
					R.drawable.candy};
			
			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
			dialog.setTitle("Choose your Activity icon");
			dialog.setAdapter(getDialogAdapter(labels, icons), new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					ImageView img =(ImageView)findViewById(R.id.iconActivity);
            		Drawable drawable = getResources().getDrawable(icons[which]);
            		img.setImageDrawable(drawable);
					
				}});
			dialog.show();
		}
	}

	private ListAdapter getDialogAdapter(final String[] items, final int[] icons) {
		
		// TODO Auto-generated method stub
		ListAdapter adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_row_dialog, items) {
               
	        ViewHolder holder;
	        Drawable icon;
	        class ViewHolder {
	                ImageView icon;
	                TextView title;
	        }
	 
	        public View getView(int position, View convertView,ViewGroup parent) {
                final LayoutInflater inflater = (LayoutInflater) getApplicationContext()
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
                if (convertView == null) {
                        convertView = inflater.inflate(R.layout.list_row_dialog, null);
                        holder = new ViewHolder();
                        holder.icon = (ImageView) convertView.findViewById(R.id.icon_dialog);
                        holder.title = (TextView) convertView.findViewById(R.id.title);
                        
                        
                        convertView.setTag(holder);
                } else {
                        // view already defined, retrieve view holder
                        holder = (ViewHolder) convertView.getTag();
                }              
 
                
                Drawable tile = getResources().getDrawable(icons[position]); //this is an image from the drawables folder
               
                holder.title.setText(items[position]);
                holder.icon.setImageDrawable(tile);
                
 
                return convertView;
	        };
		
		};
		return adapter;
	};
	
	
	
	
	//------------------- function listener for date time picker--------------//
	private void updateDate(TextView tDate) {

	    tDate.setText(
	        new StringBuilder()
	                // Month is 0 based so add 1
	                .append(mDay).append("/")
	                .append(mMonth + 1).append("/")
	                .append(mYear).append(" "));

	}

	public void updatetime(TextView tTime)
	{
	    tTime.setText(
	            new StringBuilder()
	                    .append(pad(mhour)).append(":")
	                    .append(pad(mminute))); 
	}

	private static String pad(int c) {
        if (c >= 10)
	        return String.valueOf(c);
	    else
	    	return "0" + String.valueOf(c);
    }

	//Datepicker dialog generation  

	private DatePickerDialog.OnDateSetListener mDateSetListener =
	    new DatePickerDialog.OnDateSetListener() {

	        public void onDateSet(DatePicker view, int year, 
	                              int monthOfYear, int dayOfMonth) {
	            mYear = year;
	            mMonth = monthOfYear;
	            mDay = dayOfMonth;
	            updateDate(mDateDisplay);
	        }
	    };
	    
    private DatePickerDialog.OnDateSetListener mDateSetListener2 =
	    new DatePickerDialog.OnDateSetListener() {

	        public void onDateSet(DatePicker view, int year, 
	                              int monthOfYear, int dayOfMonth) {
	            mYear = year;
	            mMonth = monthOfYear;
	            mDay = dayOfMonth;
	            updateDate(mDateDisplayEnd);
	        }
	    };


	 // Timepicker dialog generation
	    private TimePickerDialog.OnTimeSetListener mTimeSetListener =
	        new TimePickerDialog.OnTimeSetListener() {
	    		@Override
	    		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
	                mhour = hourOfDay;
	                mminute = minute;
	                updatetime(mTimeDisplay);
	            }
	        };
	        
	        
        private TimePickerDialog.OnTimeSetListener mTimeSetListener2 =
	        new TimePickerDialog.OnTimeSetListener() {
	    		@Override
	    		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
	                mhour = hourOfDay;
	                mminute = minute;
	                updatetime(mTimeDisplayEnd);
	            }
	        };
	@Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case DATE_DIALOG_ID:
            return new DatePickerDialog(this,
                        mDateSetListener,
                        mYear, mMonth, mDay);
        case TIME_DIALOG_ID:
            return new TimePickerDialog(this,
                    mTimeSetListener, mhour, mminute, false);
            
        case DATE_END_DIALOG_ID:
        	return new DatePickerDialog(this, mDateSetListener2, mYear, mMonth, mDay); 

        case TIME_END_DIALOG_ID:
        	return new TimePickerDialog(this, mTimeSetListener2, mhour, mminute, false);
        }
        return null;
    }
}
