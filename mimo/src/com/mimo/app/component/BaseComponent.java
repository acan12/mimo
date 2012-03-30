package com.mimo.app.component;

import android.app.Activity;

public abstract class BaseComponent extends Activity{
	
	public void callMethod(){
		draw();
	}
	
	protected abstract void draw();
}
