package com.mimo.app.view.detail;

import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mimo.app.R;
import com.mimo.app.model.adapter.DBAdapter;
import com.mimo.app.model.pojo.ActivityEvent;
import com.mimo.app.model.pojo.Icons;
import com.mimo.app.view.BaseView;

public class DetailView extends BaseView implements OnClickListener{

	protected Icons idIcon;
	
	protected void initialize(int layout){
		setContentView(layout);
		idIcon = new Icons();
		Button bDetail = (Button)findViewById(R.id.btn_edit);
		bDetail.setOnClickListener(this);
		Button bMap = (Button)findViewById(R.id.btn_map);
		bMap.setOnClickListener(this);
		Button bDelete = (Button)findViewById(R.id.btn_delete);
		bDelete.setOnClickListener(this);
	}
	
	protected void showDetail(int id){
		DBAdapter db = new DBAdapter(this);
		Cursor c = db.getRecord(id); 
		
		ActivityEvent ae ;
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
			
		
		String[] labels = idIcon.getLabels();
		final int[] icons = idIcon.getIcons();
		
		//set hidden value
		TextView tv= (TextView)findViewById(R.id.hidden_value);
		tv.setText(Integer.valueOf(id).toString());
		
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
		eStartDate.setText("Start : "+ae.getStartDate()+", "+ae.getStartTime());

		TextView eEndDate = (TextView)findViewById(R.id.labelEnd);
		eEndDate.setText("End : "+ae.getEndDate()+", "+ae.getEndTime());

		
		
		//set activity lat/lng value
		TextView eLat = (TextView)findViewById(R.id.labelLocation);
		eLat.setText("Location : "+ae.getLat()+", "+ae.getLng());
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
