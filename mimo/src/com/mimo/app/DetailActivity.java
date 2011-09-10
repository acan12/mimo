package com.mimo.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class DetailActivity extends Activity implements OnClickListener{

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_detail);
		
		Button btn = (Button)findViewById(R.id.btn_submit);
		btn.setOnClickListener(this);
		Button btncancel = (Button)findViewById(R.id.btn_cancel);
		btncancel.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()){
		case R.id.btn_submit:
			Intent i = new Intent(this, MapDashboardActivity.class);
			this.startActivity(i);
			break;
		case R.id.btn_cancel:
			Intent intent = new Intent(this, MapDashboardActivity.class);
			this.startActivity(intent);
			break;
		}
	}

}
