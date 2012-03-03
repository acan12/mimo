package com.mimo.app.component;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.mimo.app.R;

public class CButton extends BaseComponent implements ICButton {

	private Activity view;
	private int keyButton;

	public CButton(Activity view, int keyButton) {
		this.view = view;
		this.keyButton = keyButton;
		callMethod();
	}

	public void draw() {
		ImageButton button = (ImageButton) view.findViewById(keyButton);
		button.setOnClickListener((OnClickListener) view);
	}

}
