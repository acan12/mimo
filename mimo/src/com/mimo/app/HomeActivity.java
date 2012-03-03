package com.mimo.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.mimo.app.component.ICButton;
import com.mimo.app.interfaces.IMenuInstance;
 
 
public class HomeActivity extends MenuInstance implements IMenuInstance, OnClickListener{
	
	private ICButton mapButton, detailButton, addButton, viewPagerButton, bizButton;
	Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dashboard);
          
        mapButton 		= getComponentFactory().createButtonField(this, R.id.MapButton);
        detailButton 	= getComponentFactory().createButtonField(this, R.id.DetailButton);
        addButton 		= getComponentFactory().createButtonField(this, R.id.AddButton);
        viewPagerButton = getComponentFactory().createButtonField(this, R.id.ViewPagerButton);
        bizButton 		= getComponentFactory().createButtonField(this, R.id.BizButton);
    } 
    
    @Override
    public void onClick(View v) {
		switch(v.getId()){
			case R.id.MapButton:
				intent = getIntent(this, MapDashboardActivity.class);
				startActivity(intent); 
				break;
			case R.id.DetailButton:
				intent = getIntent(this, ActivitiesListActivity.class);
				startActivity(intent); 
				break;
			case R.id.AddButton:
				intent = getIntent(this, InputDetailActivity.class);
				intent.putExtra("paramaction", 1);
				startActivity(intent);
				break;
			case R.id.ViewPagerButton:
				intent = getIntent(this, ViewPagerActivity.class);
				startActivity(intent);
				break;
			case R.id.BizButton:
				intent = getIntent(this, BusinessLIstActivity.class);
				intent.putExtra("paramaction", 1);
				startActivity(intent);
				break;
		}
		
    } 
    
}