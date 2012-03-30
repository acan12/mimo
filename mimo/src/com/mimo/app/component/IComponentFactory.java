package com.mimo.app.component;

import java.util.ArrayList;

import com.mimo.app.BaseListActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageButton;

public interface IComponentFactory {
	ICButton createButtonField(Activity view, int keyButton);
	
	COrderAdapter createOrderList(BaseListActivity activity, int layout, ArrayList items);
}
