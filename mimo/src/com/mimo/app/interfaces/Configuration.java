package com.mimo.app.interfaces;

import java.io.PrintStream;

import com.mimo.app.model.pojo.ActivityEvent;

public interface Configuration {
	static final int ACTION_ADD = 1;
	static final int ACTION_UPDATE = 2;
	
	static final int DB_CREATE =1;
	static final int DB_UPDATE =2;
	static final int DB_DELETE =3;
	static final int DB_SELECT =4;
	
	static final int DATE_DIALOG_ID = 0;
	static final int TIME_DIALOG_ID = 1;
	static final int DATE_END_DIALOG_ID = 2;
	static final int TIME_END_DIALOG_ID = 3;
	
	static final int RESPONSE_OK = 200;
	
	static final PrintStream p = System.out ;
}
