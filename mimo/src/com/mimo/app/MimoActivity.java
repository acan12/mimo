package com.mimo.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;


public class MimoActivity extends Activity implements OnClickListener{
	ImageButton imgButton;
	
	@Override
    public void onClick(View src) {
		Intent i = new Intent(this, MapDashboardActivity.class);
		startActivity(i); 
    } 

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dashboard);
        
        imgButton = (ImageButton) findViewById(R.id.MapButton);
        imgButton.setOnClickListener(this);
        
    }
}