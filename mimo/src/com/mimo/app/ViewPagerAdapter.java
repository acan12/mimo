package com.mimo.app;

import java.util.ArrayList;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

public class ViewPagerAdapter extends PagerAdapter {

	private ArrayList<LinearLayout> views;
	
	public ViewPagerAdapter(Context context){
		views = new ArrayList<LinearLayout>();
		views.add(new ListView1Page(context));
		views.add(new ListView2Page(context));
		views.add(new ButtonPage(context));
		views.add(new TextViewPage(context));
	}
	
	@Override
	public void destroyItem(View view, int arg1, Object obj) {
		// TODO Auto-generated method stub
		((ViewPager) view).removeView((LinearLayout) obj);
	}

	@Override
	public void finishUpdate(View arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return views.size();
	}

	@Override
	public Object instantiateItem(View view, int position) {
		// TODO Auto-generated method stub
		View myView = views.get(position);
		((ViewPager) view).addView(myView);
		return myView;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		// TODO Auto-generated method stub
		return view == object;
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public Parcelable saveState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void startUpdate(View arg0) {
		// TODO Auto-generated method stub

	}

}
