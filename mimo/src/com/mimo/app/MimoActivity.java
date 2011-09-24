package com.mimo.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;


public class MimoActivity extends Activity implements OnClickListener{
	ImageButton imgButton, imgButton2;
	
	@Override
    public void onClick(View v) {
		switch(v.getId()){
			case R.id.MapButton:
				Intent i = new Intent(this, MapDashboardActivity.class);
				startActivity(i); 
				break;
			case R.id.DetailButton:
				Intent i2 = new Intent(this, DetailActivity.class);
				startActivity(i2); 
				break;
		}
		
    } 

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dashboard);
        
        imgButton = (ImageButton) findViewById(R.id.MapButton);
        imgButton.setOnClickListener(this);
        
        imgButton2 = (ImageButton) findViewById(R.id.DetailButton);
        imgButton2.setOnClickListener(this);
    }
}