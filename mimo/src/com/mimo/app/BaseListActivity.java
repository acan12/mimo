package com.mimo.app;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;

import com.mimo.app.component.ComponentFactory;
import com.mimo.app.component.IComponentFactory;

public abstract class BaseListActivity extends ListActivity {
	protected IComponentFactory componentFactory;
	protected Intent intent;

	public BaseListActivity() {

	}

	/**
	 * @return componentFactory instance
	 */
	protected IComponentFactory getComponentFactory() {
		if (componentFactory == null) {
			this.componentFactory = new ComponentFactory();
		}
		return componentFactory;
	}

	protected Intent getIntent(Activity activity, Class clazz) {
		intent = null;
		return new Intent(activity, clazz);
	}

	public void getResult() {
		getData();
	}

	public ProgressDialog showDialogProgress() {
		return showDialogProgress("Please wait...", "Retrieving data ....");
	}

	public ProgressDialog showDialogProgress(String title, String content) {
		return ProgressDialog.show(this, title, content, true);
	}

	protected abstract void getData();

	protected abstract void buildDataComponent(Object[] data);
}
