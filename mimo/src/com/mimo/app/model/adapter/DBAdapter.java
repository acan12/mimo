package com.mimo.app.model.adapter;

import com.mimo.app.model.BaseModel;
import com.mimo.app.model.pojo.ActivityEvent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter extends BaseModel{
	public static final String KEY_ROWID = "id";
	public static final String KEY_NAME = "name";
	public static final String KEY_ICON = "icon";
	public static final String KEY_DESCRIPTION = "description";
	public static final String KEY_START_DATE = "st_date";
	public static final String KEY_END_DATE = "end_date";
	public static final String KEY_START_TIME = "st_time";
	public static final String KEY_END_TIME = "end_time";
	public static final String KEY_LAT = "lat";
	public static final String KEY_LNG = "lng";
	private static final String TAG = "DBAdapter";

	private static final String DATABASE_NAME = "mimodb";
	private static final String DATABASE_TABLE = "activities";
	public static final int DATABASE_VERSION = 1;
	
	private static final String DATABASE_CREATE =
		"create table activities (id integer primary key autoincrement, "+
		"name text not null, "+
		"icon text not null,"+
		"description text," +
		"st_date text," +
		"st_time text," +
		"end_date text," +
		"end_time text," +
		"lat double," +
		"lng double );";
	
	private final Context context;
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;
	
	public DBAdapter (Context ctx){
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}
	
	private static class DatabaseHelper extends SQLiteOpenHelper{

		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL(DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS name");
			onCreate(db);
		}
		
	}
	
	public DBAdapter open() throws SQLException{
		db = DBHelper.getWritableDatabase();
		return this;
	}
	
	public void close(){
		DBHelper.close();
	}
	
	public long insertRecord(ActivityEvent act){
		this.open();
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_NAME, act.getName());
		initialValues.put(KEY_ICON, act.getIcon());
		initialValues.put(KEY_DESCRIPTION, act.getDescription());
		initialValues.put(KEY_START_DATE, act.getStart_date());
		initialValues.put(KEY_END_DATE, act.getEnd_date());
		initialValues.put(KEY_START_TIME, act.getStart_time());
		initialValues.put(KEY_END_TIME, act.getEnd_time());
		initialValues.put(KEY_LAT, act.getLat());
		initialValues.put(KEY_LNG, act.getLng());
		long rows_affected = db.insert(DATABASE_TABLE, null, initialValues);
		this.close();
		return rows_affected;
	}
	
	public boolean deleteRecord(long rowId){
		this.open();
		boolean rows_affected = db.delete(DATABASE_TABLE, KEY_ROWID + "="+rowId, null) > 0;
		this.close();
		return rows_affected;
	}
		
	public Cursor getAllRecord(){
		this.open();
		return db.query(DATABASE_TABLE, new String[]{
				KEY_ROWID,
				KEY_NAME,
				KEY_ICON,
				KEY_DESCRIPTION,
				KEY_START_DATE,
				KEY_START_TIME,
				KEY_END_DATE,
				KEY_END_TIME,
				KEY_LAT,
				KEY_LNG
		}, null, null, null, null, null);
	}
	
	public Cursor getRecord(long rowId) throws SQLException{
		this.open();
		Cursor mCursor = 
			db.query(true, DATABASE_TABLE, new String[]{
					KEY_ROWID,
					KEY_NAME,
					KEY_ICON,
					KEY_DESCRIPTION,
					KEY_START_DATE,
					KEY_START_TIME,
					KEY_END_DATE,
					KEY_END_TIME,
					KEY_LAT,
					KEY_LNG
			}, KEY_ROWID + "=" + rowId, null, null, null, null, null);
		if (mCursor != null){
			mCursor.moveToFirst();
		}
		this.close();
		return mCursor;
	}
	
	public boolean updateRecord(ActivityEvent act){
		this.open();
		ContentValues args = new ContentValues();
		args.put(KEY_NAME, act.getName());
		args.put(KEY_ICON, act.getIcon());
		args.put(KEY_DESCRIPTION, act.getDescription());
		args.put(KEY_START_DATE, act.getStart_date());
		args.put(KEY_END_DATE, act.getEnd_date());
		args.put(KEY_START_TIME, act.getStart_time());
		args.put(KEY_END_TIME, act.getEnd_time());
		args.put(KEY_LAT, act.getLat());
		args.put(KEY_LNG, act.getLng());
		boolean rows_affected = db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + act.getId(), null) > 0;
		this.close();
		return rows_affected;
	}
}
                