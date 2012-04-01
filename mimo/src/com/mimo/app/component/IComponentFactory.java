package com.mimo.app.component;

import java.util.ArrayList;

import android.app.Activity;

import com.mimo.app.BaseListActivity;

public interface IComponentFactory {
	ICButton createButtonField(Activity view, int keyButton);
	
	COrderAdapter createOrderList(BaseListActivity activity, int layout, ArrayList items);
}
