package com.mimo.app;

import com.mimo.app.interfaces.IMenuInstance;

import android.app.Activity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MenuActivityInstance extends Activity implements IMenuInstance{
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
    	}
		return true;
    }
}
