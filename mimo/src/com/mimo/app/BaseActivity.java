package com.mimo.app;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.mimo.app.component.ComponentFactory;
import com.mimo.app.component.IComponentFactory;

public class BaseActivity extends Activity {
	protected IComponentFactory componentFactory;
	protected Intent intent;

	/**
	 * @return the componentFactory
	 */
	protected IComponentFactory getComponentFactory() {
		if (componentFactory == null) {
			this.componentFactory = new ComponentFactory();
		}
		return componentFactory;
	}
	
	protected Intent getIntent(Activity activity, Class clazz){
		intent = null;
		return new Intent(activity, clazz);
	}

}
