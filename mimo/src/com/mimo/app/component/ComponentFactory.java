package com.mimo.app.component;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

public class ComponentFactory implements IComponentFactory{
	public ICButton createButtonField(Activity v, int keyButton){
		ICButton cb = new CButton(v, keyButton);
		return cb;
	}
}
