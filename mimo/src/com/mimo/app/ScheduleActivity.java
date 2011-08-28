//package com.mimo.app;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//
//import android.R.integer;
//import android.app.Activity;
//import android.app.Dialog;
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.graphics.Canvas;
//import android.graphics.Paint;
//import android.graphics.Path;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.util.Log;
//import android.view.GestureDetector;
//import android.view.GestureDetector.SimpleOnGestureListener;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.view.animation.AnimationUtils;
//import android.widget.Button;
//import android.widget.ImageButton;
//import android.widget.ListView;
//import android.widget.SimpleAdapter;
//import android.widget.TextView;
//import android.widget.Toast;
//import android.widget.ViewFlipper;
//
//
//public class ScheduleActivity extends Activity {
//	
//	@Override 
//	public void onCreate(Bundle icicle) {
//		super.onCreate(icicle);
//		setContentView(R.layout.main);
//		
//		ListView list = (ListView) findViewById(R.id);
//		ArrayList<HashMap<String, String>> myList = new ArrayList<HashMap<String, String>>();
//		HashMap<String, String> map ;
//		
//		int i =1;
//		while(i<100){
//			
//			map = new HashMap<String, String>();
//			Log.v("i: ...........", String.valueOf(i));
//			map.put("train", String.valueOf(i));
//			map.put("from", "7:30 AM");
//			map.put("to", "9:40 AM");
//			myList.add(map);
//			i++;
//			
//		}
//		
//		SimpleAdapter mSchedule = new SimpleAdapter(this, myList, R.layout.row,
//				new String[] {"train", "from", "to"}, 
//				new int[] {R.id.TRAIN_CELL, R.id.FROM_CELL, R.id.TO_CELL});
//		list.setAdapter(mSchedule);
//	}
//		
//	 
//
//}
