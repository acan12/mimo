package com.mimo.app.component;

import java.util.ArrayList;

import com.mimo.app.BaseActivity;
import com.mimo.app.BaseListActivity;
import com.mimo.app.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

public class ComponentFactory implements IComponentFactory{
	
	public ICButton createButtonField(Activity activity, int keyButton){
		return new CButton(activity, keyButton);
	}
	
	public COrderAdapter createOrderList(BaseListActivity activity, int layout, ArrayList items){
		return new COrderAdapter(activity, layout, items, this);
	}
	
}
