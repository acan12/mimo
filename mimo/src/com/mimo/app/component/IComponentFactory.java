package com.mimo.app.component;

import android.app.Activity;
import android.view.View;
import android.widget.ImageButton;

public interface IComponentFactory {
	ICButton createButtonField(Activity view, int keyButton);
}
