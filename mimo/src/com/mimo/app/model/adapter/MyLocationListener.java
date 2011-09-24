package com.mimo.app.model.adapter;

import com.google.android.maps.MapActivity;

import android.R;
import android.app.Activity;
import android.os.Bundle;

public class MyLocationListener extends MapActivity{
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.layout);
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
};
