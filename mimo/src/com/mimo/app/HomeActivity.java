package com.mimo.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.mimo.app.component.ICButton;
import com.mimo.app.interfaces.IMenuInstance;
 
 
public class HomeActivity extends MenuInstance implements IMenuInstance, OnClickListener{
	
	private ICButton mapButton, detailButton, addButton, viewPagerButton, bizButton;
	Intent intent;
	private LinearLayout mapLayout, newLayout, detailLayout, calendarLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dashboard);
          
//        mapButton 		= getComponentFactory().createButtonField(this, R.id.MapButton);
//        detailButton 	= getComponentFactory().createButtonField(this, R.id.DetailButton);
//        addButton 		= getComponentFactory().createButtonField(this, R.id.AddButton);
//        viewPagerButton = getComponentFactory().createButtonField(this, R.id.ViewPagerButton);
//        bizButton 		= getComponentFactory().createButtonField(this, R.id.BizButton);
        
        mapLayout = (LinearLayout) findViewById(R.id.map_layout);
        newLayout = (LinearLayout) findViewById(R.id.new_layout);
        detailLayout = (LinearLayout) findViewById(R.id.detail_layout);
        calendarLayout = (LinearLayout) findViewById(R.id.calendar_layout);
        
        mapLayout.setOnClickListener(this);
        newLayout.setOnClickListener(this);
        detailLayout.setOnClickListener(this);
        calendarLayout.setOnClickListener(this);
    } 
    
    @Override
    public void onClick(View v) {	
    	if(v == mapLayout){
    		intent = getIntent(this, MapDashboardActivity.class);
			startActivity(intent); 
    	} else if(v == newLayout){
    		intent = getIntent(this, ActivitiesListActivity.class);
			startActivity(intent); 
    	} else if(v == detailLayout){
    		intent = getIntent(this, InputDetailActivity.class);
			startActivity(intent); 
    	} else if(v == calendarLayout){
    		intent = getIntent(this, ViewPagerActivity.class);
			startActivity(intent);
    	}
    } 
    
}