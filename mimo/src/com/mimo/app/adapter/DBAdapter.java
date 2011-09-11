package com.mimo.app.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter {
	public static final String KEY_ROWID = "_id";
	public static final String KEY_TITLE = "title";
	public static final String KEY_NAME = "name";
	private static final String TAG = "DBAdapter";

	private static final String DATABASE_NAME = "mimodb";
	private static final String DATABASE_TABLE = "activities";
	public static final int DATABASE_VERSION = 1;
	
	private static final String DATABASE_CREATE =
		"create table activities (_id integer primary key autoincrement, "
		+"title text not null,"
		+"name text not null, "
		+"description text );";
	
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
			db.execSQL("DROP TABLE IF EXISTS titles");
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
	
	public long insertTitle(String title, String name){
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_TITLE, title);
		initialValues.put(KEY_NAME, name);
		return db.insert(DATABASE_TABLE, null, initialValues);
		
	}
	
	public boolean deleteTitle(long rowId){
		return db.delete(DATABASE_TABLE, KEY_ROWID + "="+rowId, null) > 0;
	}
		
	public Cursor getAllTitles(){
		
		return db.query(DATABASE_TABLE, new String[]{
				KEY_ROWID,
				KEY_TITLE,
				KEY_NAME
		}, null, null, null, null, null);
		
	}
	
	public Cursor getTitle(long rowId) throws SQLException{
		Cursor mCursor = 
			db.query(true, DATABASE_TABLE, new String[]{
					KEY_ROWID,
					KEY_TITLE,
					KEY_NAME
			}, KEY_ROWID + "=" + rowId, null, null, null, null, null);
		if (mCursor != null){
			mCursor.moveToFirst();
		}
		return mCursor;
	}
	
	public boolean updateTitle(long rowId, String title, String name){
		ContentValues args = new ContentValues();
		args.put(KEY_TITLE, title);
		args.put(KEY_NAME, name);
		return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
	}
}
