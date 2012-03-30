package com.mimo.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
 
 
public class HomeActivity extends Activity implements OnClickListener{
	
	private Intent intent;
	private LinearLayout mapLayout, calendarLayout, bizLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dashboard);
          
        mapLayout = (LinearLayout) findViewById(R.id.map_layout);
        calendarLayout = (LinearLayout) findViewById(R.id.calendar_layout);
        bizLayout = (LinearLayout) findViewById(R.id.biz_layout);
        
        mapLayout.setOnClickListener(this);
        calendarLayout.setOnClickListener(this);
        bizLayout.setOnClickListener(this);
    } 
    
    @Override
    public void onClick(View v) {	
    	if(v == mapLayout){
    		intent = new Intent(this, MapDashboardActivity.class);
			startActivity(intent); 
    	} else if(v == calendarLayout){
    		intent = new Intent(this, ViewPagerActivity.class);
			startActivity(intent);
    	} else if(v == bizLayout){
    		intent = new Intent(this, BusinessLIstActivity.class);
			startActivity(intent);
    	}
    	
    }
}