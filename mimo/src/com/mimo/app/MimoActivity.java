package com.mimo.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;


public class MimoActivity extends Activity implements OnClickListener{
	ImageButton mapButton, detailButton, addButton, viewPagerButton, bizButton;
	Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dashboard);
        
          
        mapButton = (ImageButton) findViewById(R.id.MapButton);
        mapButton.setOnClickListener(this);
        
        detailButton = (ImageButton) findViewById(R.id.DetailButton);
        detailButton.setOnClickListener(this);
        
        addButton = (ImageButton) findViewById(R.id.AddButton);
        addButton.setOnClickListener(this);
        
        viewPagerButton = (ImageButton) findViewById(R.id.ViewPagerButton);
        viewPagerButton.setOnClickListener(this);
        
        bizButton = (ImageButton) findViewById(R.id.BizButton);
        bizButton.setOnClickListener(this);
    } 
    
    @Override
    public void onClick(View v) {
		switch(v.getId()){
			case R.id.MapButton:
				intent = new Intent(this, MapDashboardActivity.class);
				startActivity(intent); 
				break;
			case R.id.DetailButton:
				intent = new Intent(this, ActivitiesListActivity.class);
				startActivity(intent); 
				break;
			case R.id.AddButton:
				intent = new Intent(this, InputDetailActivity.class);
				intent.putExtra("paramaction", 1);
				startActivity(intent);
				break;
			case R.id.ViewPagerButton:
				intent = new Intent(this, ViewPagerActivity.class);
				startActivity(intent);
				break;
			case R.id.BizButton:
				intent = new Intent(this, BusinessLIstActivity.class);
				intent.putExtra("paramaction", 1);
				startActivity(intent);
				break;
		}
		
    } 
}