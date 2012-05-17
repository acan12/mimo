package com.mimo.app.view.form;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.mimo.app.R;
import com.mimo.app.interfaces.IConfiguration;
import com.mimo.app.model.adapter.DBAdapter;
import com.mimo.app.model.pojo.ActivityEvent;
import com.mimo.app.model.pojo.Icons;
import com.mimo.app.view.BaseView;

public class FormInputView extends BaseView implements OnClickListener, IConfiguration{
	protected int 
			mYear,
			mMonth,
			mDay,
			mhour,
			mminute;
	protected TextView 
			mDateDisplay, 
			mDateDisplayEnd, 
			mTimeDisplay, 
			mTimeDisplayEnd;
	protected int action;
	protected Icons idIcon;
	protected ImageButton homeButton;
	
	protected void initialize(int layout){
		setContentView(layout);
		
		idIcon = new Icons();
		homeButton = (ImageButton) findViewById(R.id.home_button);
		homeButton.setOnClickListener(this);
		
		Button btn = (Button)findViewById(R.id.btn_submit);
		btn.setOnClickListener(this);
		ImageButton iconActivity = (ImageButton)findViewById(R.id.icon_activity);
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
		
	}
	
	protected ActivityEvent setActivityValue(ActivityEvent ae){
		//set activity name
		TextView eName = (TextView)findViewById(R.id.edit_name);
		ae.setName(eName.getText().toString());
		
		//set icon name
		TextView eIcon = (TextView)findViewById(R.id.icon_label);
		ae.setIcon(eIcon.getText().toString());
		
		//set activity description
		TextView eDescription = (TextView)findViewById(R.id.edit_description);
		ae.setDescription(eDescription.getText().toString());
		
		//set activity start/end date
		TextView eStartDate = (TextView)findViewById(R.id.tStarDate);
		ae.setStartDate(eStartDate.getText().toString());
		TextView eEndDate = (TextView)findViewById(R.id.tEndDate);
		ae.setEndDate(eEndDate.getText().toString());
		
		//set activity start/end time
		TextView eStartTime = (TextView)findViewById(R.id.tStartTime);
		ae.setStartTime(eStartTime.getText().toString());
		TextView eEndTime = (TextView)findViewById(R.id.tEndTime);
		ae.setEndTime(eEndTime.getText().toString());
		
		//set activity lat/lng value
		TextView eLat = (TextView)findViewById(R.id.edit_lat);
		ae.setLat(Double.parseDouble(eLat.getText().toString()));
		TextView eLng = (TextView)findViewById(R.id.edit_lng);
		ae.setLng(Double.parseDouble(eLng.getText().toString()));
		return ae;
	}
	
	protected ActivityEvent showForm(int id){
		DBAdapter db = new DBAdapter(this);
		Icons idIcon = new Icons();
		Cursor c = db.getRecord(id);
		
		ActivityEvent ae ;
		do{
			ae = new ActivityEvent();
			ae.setId(id);
			ae.setName(c.getString(c.getColumnIndex("name")));
			ae.setIcon(c.getString(c.getColumnIndex("icon")));
			ae.setDescription(c.getString(c.getColumnIndex("description")));
			ae.setStartDate(c.getString(c.getColumnIndex("st_date")));
			ae.setStartTime(c.getString(c.getColumnIndex("st_time")));
			ae.setEndDate(c.getString(c.getColumnIndex("end_date")));
			ae.setEndTime(c.getString(c.getColumnIndex("end_time")));
			ae.setLat(c.getDouble(c.getColumnIndex("lat")));
			ae.setLng(c.getDouble(c.getColumnIndex("lng")));
			
		}while(c.moveToNext());
		
		
		//set hidden value
		TextView tv= (TextView)findViewById(R.id.hidden_value);
		tv.setText(Integer.valueOf(id).toString());
		
		//set icon 
		ImageButton img =(ImageButton)findViewById(R.id.icon_activity);
		Drawable drawable = getResources().getDrawable(idIcon.getIconFromLabel(ae.getIcon()));
		img.setImageDrawable(drawable);
		
		//set id hidden activity event
//		TextView eId = (TextView)findViewById(R.id.hidden_value);
//		eId.setText(id);
		
		//set icon name
		TextView eIcon = (TextView)findViewById(R.id.icon_label);
		eIcon.setText(ae.getIcon());
		
		//set activity name
		TextView eName = (TextView)findViewById(R.id.edit_name);
		eName.setText(ae.getName());
		
		//set activity description
		TextView eDescription = (TextView)findViewById(R.id.edit_description);
		eDescription.setText(ae.getDescription());
		
		//set activity start/end date
		TextView eStartDate = (TextView)findViewById(R.id.tStarDate);
		eStartDate.setText(ae.getStartDate());

		TextView eEndDate = (TextView)findViewById(R.id.tEndDate);
		eEndDate.setText(ae.getEndDate());

		//set activity start/end time
		TextView eStartTime = (TextView)findViewById(R.id.tStartTime);
		eStartTime.setText(ae.getStartTime());
		
		TextView eEndTime = (TextView)findViewById(R.id.tEndTime);
		eEndTime.setText(ae.getEndTime());
		
		
		//set activity lat/lng value
		TextView eLat = (TextView)findViewById(R.id.edit_lat);
		eLat.setText(""+ae.getLat());
		
		TextView eLng = (TextView)findViewById(R.id.edit_lng);
		eLng.setText(""+ae.getLng());
		return ae;
	}
	
	protected ListAdapter getDialogAdapter(final String[] items, final int[] icons) {
		
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
	protected void updateDate(TextView tDate) {

	    tDate.setText(
	        new StringBuilder()
	                // Month is 0 based so add 1
//	                .append(mDay).append("/")
//	                .append(mMonth + 1).append("/")
//	                .append(mYear).append(" "));
	        
	        		.append(pad(mYear)).append("-")
	        		.append(pad(mMonth + 1)).append("-")
	        		.append(pad(mDay)).append(" "));

	}

	protected void updatetime(TextView tTime)
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

	protected DatePickerDialog.OnDateSetListener mDateSetListener =
	    new DatePickerDialog.OnDateSetListener() {

	        public void onDateSet(DatePicker view, int year, 
	                              int monthOfYear, int dayOfMonth) {
	            mYear = year;
	            mMonth = monthOfYear;
	            mDay = dayOfMonth;
	            updateDate(mDateDisplay);
	        }
	    };
	    
    protected DatePickerDialog.OnDateSetListener mDateSetListener2 =
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
    protected TimePickerDialog.OnTimeSetListener mTimeSetListener =
        new TimePickerDialog.OnTimeSetListener() {
    		@Override
    		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mhour = hourOfDay;
                mminute = minute;
                updatetime(mTimeDisplay);
            }
        };
	        
	        
    protected TimePickerDialog.OnTimeSetListener mTimeSetListener2 =
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
        case RESPONSE_OK:
        	Toast.makeText(this, "Record Saved", Toast.LENGTH_LONG).show();
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
