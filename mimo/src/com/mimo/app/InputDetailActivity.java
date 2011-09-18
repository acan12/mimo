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

public class InputDetailActivity extends Activity implements OnClickListener{

	static final int DATE_DIALOG_ID = 0;
	static final int TIME_DIALOG_ID = 1;
	static final int DATE_END_DIALOG_ID = 2;
	static final int TIME_END_DIALOG_ID = 3;
	private int mYear;
	private int mMonth;
	private int mDay;
	private int mhour;
	private int mminute;
	private Icons idIcon = new Icons();
	
	private TextView mDateDisplay, mDateDisplayEnd;
	private TextView mTimeDisplay, mTimeDisplayEnd;
	
	double lat2= -6.195894;
	double lng2= 106.835901;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_input_detail);
		
		TextView eIcon = (TextView)findViewById(R.id.icon_label);
		eIcon.setText(idIcon.getLabels()[0]);
		
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
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	  if (resultCode == Activity.RESULT_OK && requestCode == 0) {
	    String result = data.toURI();
	    Toast.makeText(this, result, Toast.LENGTH_LONG);
	  }
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
			double lat2= -6.195894;
			double lng2= 106.835901;
			Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:"+lat2+","+lng2+"?z=15"));
			this.startActivity(mapIntent);
			break;
		case R.id.btn_submit:
			
			
			ActivityEvent a = setActivity();
	        Log.d("test:", "----------name: "+a.getName());
	        Log.d("test:", "----------description: "+a.getDescription());
	        Log.d("test:", "----------startdate: "+a.getStart_date());
	        Log.d("test:", "----------lat: "+a.getLat());
	        
	        updateDB(a);
	        
	        TextView eName = (TextView)findViewById(R.id.edit_name);
	        eName.setText(a.getName());
			break;
			
		case R.id.btn_cancel:
//			Intent i = new Intent(this, DetailActivity.class);
//			i.putExtra("edit_name", "xxxxxxxx");
//			this.startActivity(i);
			
			ImageButton imb = (ImageButton)findViewById(R.id.iconActivity);
			Drawable d = imb.getDrawable();
//			Text	@string/label_field_description
			TextView tv = (TextView)findViewById(R.id.labelName);
			Log.d("word: ", tv.getText().toString());
			break;
		case R.id.iconActivity:
			
//			String[] labels = {"Entertainment", 
//					"Food/Fun", 
//					"News",
//					"Meeting",
//					"Flavour",
//					"Sport",
//					"Art",
//					"Junk Food",
//					"Space",
//					"Walking",
//					"Fitness",
//					"Holiday",
//					"Market",
//					"Community",
//					"Office"};
//			
//			final int[] icons = {
//					R.drawable.alien, 
//					R.drawable.bread, 
//					R.drawable.butcher2, 
//					R.drawable.candy,
//					R.drawable.cheese,
//					R.drawable.eggs,
//					R.drawable.farmstand,
//					R.drawable.fruits,
//					R.drawable.japanese_sweet2,
//					R.drawable.patisserie,
//					R.drawable.sandwich2,
//					R.drawable.alien, 
//					R.drawable.bread, 
//					R.drawable.butcher2, 
//					R.drawable.candy};
			
			String[] labels = idIcon.getLabels();
			final int[] icons = idIcon.getIcons();
			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
			dialog.setTitle("Choose your Activity icon");
			dialog.setAdapter(getDialogAdapter(labels, icons), new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					ImageView img =(ImageView)findViewById(R.id.iconActivity);
            		Drawable drawable = getResources().getDrawable(icons[which]);
            		img.setImageDrawable(drawable);
            		
            		//set label icon name
            		TextView eIcon = (TextView)findViewById(R.id.icon_label);
            		eIcon.setText(idIcon.getLabels()[which]);
					
				}});
			dialog.show();
		}
	}

	private ActivityEvent setActivity(){
		ActivityEvent ae = new ActivityEvent() ;
		
		//set activity name
		TextView eName = (TextView)findViewById(R.id.edit_name);
		ae.setName(eName.getText().toString());
		
		//set icon name
		TextView eIcon = (TextView)findViewById(R.id.iconActivity);
		ae.setIcon(eIcon.getText().toString());
		
		//set activity description
		TextView eDescription = (TextView)findViewById(R.id.edit_description);
		ae.setDescription(eDescription.getText().toString());
		
		
		//set activity start/end date
		TextView eStartDate = (TextView)findViewById(R.id.tStarDate);
		ae.setStart_date(eStartDate.getText().toString());
		
		TextView eEndDate = (TextView)findViewById(R.id.tEndDate);
		ae.setEnd_date(eEndDate.getText().toString());
		

		//set activity start/end time
		TextView eStartTime = (TextView)findViewById(R.id.tStartTime);
		ae.setStart_time(eStartTime.getText().toString());
		
		TextView eEndTime = (TextView)findViewById(R.id.tEndTime);
		ae.setEnd_date(eEndTime.getText().toString());
		
		
		//set activity lat/lng value
		TextView eLat = (TextView)findViewById(R.id.edit_lat);
		ae.setLat(Double.parseDouble(eLat.getText().toString()));
		TextView eLng = (TextView)findViewById(R.id.edit_lng);
		ae.setLng(Double.parseDouble(eLng.getText().toString()));
		return ae;
	}
	
	public boolean updateDB(ActivityEvent act){
		// ----------- store to Db SQLite -------
		DBAdapter db = new DBAdapter(this);
		db.open();
		long id;
		id = db.insertTitle(act);
		db.close();
		// ----------- end SQLite --------------
		return true;
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
