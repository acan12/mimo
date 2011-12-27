package com.mimo.app;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.TextView;

public class ViewPagerActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_viewpager);
		
		ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
		ViewPagerAdapter adapter = new ViewPagerAdapter(this);
		viewPager.setAdapter(adapter);
		
		final TextView tvHeader = (TextView) findViewById(R.id.textViewHeader);
		tvHeader.setText("Page 1");

		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int page) {
				tvHeader.setText("Page " + (page + 1));
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}
}
