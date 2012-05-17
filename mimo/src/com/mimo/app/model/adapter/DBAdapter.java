package com.mimo.app.model.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mimo.app.model.BaseModel;
import com.mimo.app.model.pojo.ActivityEvent;

public class DBAdapter extends BaseModel {
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
	public static final String KEY_STATUS = "status";
	private static final String TAG = "DBAdapter";
	private static final int OFFSET_DAY = 1;

	private ActivityEvent ae;

	private static final String DATABASE_NAME = "mimodb";
	private static final String DATABASE_TABLE = "activities";
	public static final int DATABASE_VERSION = 1;

	private static final String DATABASE_CREATE = "create table activities (id integer primary key autoincrement, "
			+ "name text not null, "
			+ "icon text not null,"
			+ "description text,"
			+ "st_date text,"
			+ "st_time text,"
			+ "end_date text,"
			+ "end_time text,"
			+ "lat double,"
			+ "lng double," + "status integer default 0);";

	private final Context context;
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;

	public DBAdapter(Context ctx) {
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {

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

	public DBAdapter open() throws SQLException {
		db = DBHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		DBHelper.close();
	}

	public long insertRecord(ActivityEvent act) {
		this.open();
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_NAME, act.getName());
		initialValues.put(KEY_ICON, act.getIcon());
		initialValues.put(KEY_DESCRIPTION, act.getDescription());
		initialValues.put(KEY_START_DATE, act.getStartDate());
		initialValues.put(KEY_END_DATE, act.getEndDate());
		initialValues.put(KEY_START_TIME, act.getStartTime());
		initialValues.put(KEY_END_TIME, act.getEndTime());
		initialValues.put(KEY_LAT, act.getLat());
		initialValues.put(KEY_LNG, act.getLng());
		long rows_affected = db.insert(DATABASE_TABLE, null, initialValues);
		this.close();
		return rows_affected;
	}

	public boolean deleteRecord(long rowId) {
		this.open();
		boolean rows_affected = db.delete(DATABASE_TABLE, KEY_ROWID + "="
				+ rowId, null) > 0;
		this.close();
		return rows_affected;
	}

	public List<ActivityEvent> getMostCloselyEvent() throws Exception {
		List<ActivityEvent> list = new ArrayList<ActivityEvent>();
		this.open();
		Cursor c = db.rawQuery(
				"SELECT * FROM activities where " +
				"(julianday(strftime('%Y-%m-%d', st_date) ) -  julianday(strftime('%Y-%m-%d', 'now'))  )  >= 0 " +
				"order by st_date, st_time limit 1", null);

		while (c.moveToNext()) {
			ae = new ActivityEvent();
			ae.setId(c.getInt(c.getColumnIndex("id")));
			ae.setIcon(c.getString(c.getColumnIndex("icon")));
			ae.setName(c.getString(c.getColumnIndex("name")));
			ae.setDescription(c.getString(c.getColumnIndex("description")));
			ae.setLat(c.getDouble(c.getColumnIndex("lat")));
			ae.setLng(c.getDouble(c.getColumnIndex("lng")));
			
			ae.setStartDate(c.getString(c.getColumnIndex("st_date")));
			ae.setStartTime(c.getString(c.getColumnIndex("st_time")));
			ae.setStatus(c.getInt(c.getColumnIndex("status")));

			
			list.add(ae);
		}
		return list;
	}

	public Cursor getIconsUniqRecord() {
		this.open();
		Cursor c = db
				.rawQuery(
						"select count(*) as count_record, icon " +
						"from activities group by icon order by st_date, st_time",
						null);
		return c;
	}

	public Cursor getAllRecord() {
		this.open();
		 Cursor c = db.query(DATABASE_TABLE, new String[]{
		 KEY_ROWID,
		 KEY_NAME,
		 KEY_ICON,
		 KEY_DESCRIPTION,
		 KEY_START_DATE,
		 KEY_START_TIME,
		 KEY_END_DATE,
		 KEY_END_TIME,
		 KEY_LAT,
		 KEY_LNG,
		 KEY_STATUS
		 }, null, null, null, null, KEY_START_DATE+","+KEY_START_TIME);
		return c;
	}

	public Cursor getRecord(long rowId) throws SQLException {
		this.open();
		Cursor mCursor = db.query(true, DATABASE_TABLE, new String[] {
				KEY_ROWID, KEY_NAME, KEY_ICON, KEY_DESCRIPTION, KEY_START_DATE,
				KEY_START_TIME, KEY_END_DATE, KEY_END_TIME, KEY_LAT, KEY_LNG,
				KEY_STATUS }, KEY_ROWID + "=" + rowId, null, null, null, KEY_START_DATE+","+KEY_START_TIME,
				null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		this.close();
		return mCursor;
	}

	public Cursor getRecordByIcon(String iconLabel) throws SQLException {
		this.open();
		Cursor mCursor = db.query(true, DATABASE_TABLE, new String[] {
				KEY_ROWID, KEY_NAME, KEY_ICON, KEY_DESCRIPTION, KEY_START_DATE,
				KEY_START_TIME, KEY_END_DATE, KEY_END_TIME, KEY_LAT, KEY_LNG,
				KEY_STATUS }, KEY_ICON + "='" + iconLabel + "'", null, null,
				null, KEY_START_DATE+","+KEY_START_TIME, null);
		return mCursor;
	}

	public boolean updateRecord(ActivityEvent act) {
		this.open();
		ContentValues args = new ContentValues();
		args.put(KEY_NAME, act.getName());
		args.put(KEY_ICON, act.getIcon());
		args.put(KEY_DESCRIPTION, act.getDescription());
		args.put(KEY_START_DATE, act.getStartDate());
		args.put(KEY_END_DATE, act.getEndDate());
		args.put(KEY_START_TIME, act.getStartTime());
		args.put(KEY_END_TIME, act.getEndTime());
		args.put(KEY_LAT, act.getLat());
		args.put(KEY_LNG, act.getLng());
		boolean rows_affected = db.update(DATABASE_TABLE, args, KEY_ROWID + "="
				+ act.getId(), null) > 0;
		this.close();
		return rows_affected;
	}

	public boolean updateStatus(int rowid, int status) {
		this.open();
		ContentValues args = new ContentValues();
		args.put(KEY_STATUS, status);
		db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowid, null);
		this.close();
		return true;
	}
}
