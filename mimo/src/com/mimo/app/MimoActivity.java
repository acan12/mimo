package com.mimo.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;


public class MimoActivity extends Activity implements OnClickListener{
	ImageButton imgButton, imgButton2, imgButton3, imgButton4;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dashboard);
        
        imgButton = (ImageButton) findViewById(R.id.MapButton);
        imgButton.setOnClickListener(this);
        
        imgButton2 = (ImageButton) findViewById(R.id.DetailButton);
        imgButton2.setOnClickListener(this);
        
        imgButton3 = (ImageButton) findViewById(R.id.AddButton);
        imgButton3.setOnClickListener(this);
        
        imgButton4 = (ImageButton) findViewById(R.id.MapTestButton);
        imgButton4.setOnClickListener(this);
    }
    
    @Override
    public void onClick(View v) {
		switch(v.getId()){
			case R.id.MapButton:
				Intent i = new Intent(this, MapDashboardActivity.class);
				startActivity(i); 
				break;
			case R.id.MapTestButton:
				Intent intent = new Intent(this, MapLocation.class);
				startActivity(intent); 
				break;
			case R.id.DetailButton:
				Intent i2 = new Intent(this, ActivitiesListActivity.class);
				startActivity(i2); 
				break;
			case R.id.AddButton:
				Intent i3 = new Intent(this, InputDetailActivity.class);
				i3.putExtra("paramaction", 1);
				startActivity(i3);
		}
		
    } 
}