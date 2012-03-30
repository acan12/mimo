package com.mimo.app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mimo.app.interfaces.IApp;
import com.mimo.app.interfaces.IConfiguration;
import com.mimo.app.model.adapter.DBAdapter;
import com.mimo.app.model.pojo.ActivityEvent;
import com.mimo.app.view.form.FormInputView;

//Test
public class InputDetailActivity extends FormInputView implements IConfiguration, IApp{
	
	private ActivityEvent a;
	private int paramid;

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		Bundle bundle = getIntent().getExtras();
		action = bundle.getInt("paramaction");
		paramid = bundle.getInt(PARAMS_KEY);
		
		double paramlat = bundle.getDouble("paramlat");
		double paramlng = bundle.getDouble("paramlng");
		p.println("paramlat :  "+paramlat);
		p.println("paramlng :  "+paramlng);
		a = new ActivityEvent();
		
		switch(action){
		case DB_CREATE: // create
			initialize(R.layout.layout_form_new);
			a.setId(0);
			
			break;
		case DB_UPDATE: // update
			initialize(R.layout.layout_form_detail);
			showForm(paramid);  
			
			break;
		}
		TextView tLat = (TextView)findViewById(R.id.edit_lat);
		TextView tLng = (TextView)findViewById(R.id.edit_lng);
		
		if(paramlat!=0&&paramlng!=0){
			tLat.setText(""+paramlat);
			tLng.setText(""+paramlng);
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
			Intent mapIntent = new Intent(this, MapLocationActivity.class);
			p.println("......get id:"+paramid);
			mapIntent.putExtra(PARAMS_KEY, paramid);
			this.startActivity(mapIntent);
			 
			break; 
		case R.id.btn_submit:
			a =new ActivityEvent();
			DBAdapter db = new DBAdapter(this);
			switch(action){
			case DB_CREATE: // create
				setActivityValue(a);
				db.insertRecord(a);
				onCreateDialog(RESPONSE_OK);
				
				break;
			case DB_UPDATE: // update
				//set id activity event
				TextView t = (TextView)findViewById(R.id.hidden_value);
				a.setId(Integer.parseInt(t.getText().toString()));
				setActivityValue(a);
				db.updateRecord(a);
				onCreateDialog(RESPONSE_OK);
				
				break;
			}
			break;
		case R.id.btn_cancel:
			switch(action){
			case DB_CREATE:
				Intent i = new Intent(this, HomeActivity.class);
				this.startActivity(i);
				break;
			case DB_UPDATE:
				Intent i2 = new Intent(this, HomeActivity.class);
				this.startActivity(i2);
				break;
			}
			
			break;
		case R.id.icon_activity:
			
			String[] labels = idIcon.getLabels();
			final int[] icons = idIcon.getIcons();
			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
			dialog.setTitle("Choose your Activity icon");
			dialog.setAdapter(getDialogAdapter(labels, icons), new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					ImageView img =(ImageView)findViewById(R.id.icon_activity);
            		Drawable drawable = getResources().getDrawable(icons[which]);
            		img.setImageDrawable(drawable);
            		
            		//set label icon name
            		TextView eIcon = (TextView)findViewById(R.id.icon_label);
            		eIcon.setText(idIcon.getLabels()[which]);
					
				}});
			dialog.show();
		}
	}
	
}                      