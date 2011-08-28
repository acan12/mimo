package com.mimo.app;

import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.*;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class MimoActivity extends Activity {
	private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private GestureDetector gestureDetector;
    View.OnTouchListener gestureListener;

	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		final ViewFlipper viewFlipper = (ViewFlipper)findViewById(R.id.viewFlipper);
		Button nextButton = (Button) this.findViewById(R.id.nextButton);
		nextButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				viewFlipper.setInAnimation(MimoActivity.this, R.anim.view_transition_in_left);
				viewFlipper.setOutAnimation(MimoActivity.this, R.anim.view_transition_out_left);
				viewFlipper.showNext();
			}
		});
		
		Button previousButton = (Button) this.findViewById(R.id.previousButton);
		previousButton.setOnClickListener(new OnClickListener()
		{
		 
			@Override
		    public void onClick(View v) {
				viewFlipper.setInAnimation(MimoActivity.this, R.anim.view_transition_in_right);
				viewFlipper.setOutAnimation(MimoActivity.this, R.anim.view_transition_out_right);
	            viewFlipper.showPrevious();
			} 
		});
		
		gestureDetector = new GestureDetector(new MyGestureDetector());

		viewFlipper.setOnTouchListener(new View.OnTouchListener() {

	        @Override
	        public boolean onTouch(View v, MotionEvent event) {
	            if (gestureDetector.onTouchEvent(event)) {
	                return false;
	            }
	        	
	        	
	        	return true;
	        }

			
	  });

	}
	
	class MyGestureDetector extends SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
    		final ViewFlipper viewFlipper = (ViewFlipper)findViewById(R.id.viewFlipper);

            try {
            	viewFlipper.setInAnimation(MimoActivity.this, R.anim.view_transition_in_left);
				viewFlipper.setOutAnimation(MimoActivity.this, R.anim.view_transition_out_left);
				viewFlipper.showNext();
				
                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                    return false;
                // right to left swipe
                if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    Toast.makeText(MimoActivity.this, "Left Swipe", Toast.LENGTH_SHORT).show();
                }  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    Toast.makeText(MimoActivity.this, "Right Swipe", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                // nothing
            }
            return false;
        }

    }

}