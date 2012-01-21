package com.mimo.app;

import com.mimo.app.interfaces.IMenuInstance;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MenuInstance extends Activity implements IMenuInstance{
	public boolean onCreateOptionsMenu(Menu menu){
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.options_menu, menu);
		return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item){
    	switch(item.getItemId()){
    	case R.id.exit:
    		finish();
    		break;
    	case R.id.setting:
    		Intent settingsActivity = new Intent(this, SettingPreferencesActivity.class);
    		startActivity(settingsActivity);
    		break;
    	}
		return true;
    }
}
