package com.mimo.app.model.pojo;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

import com.mimo.app.util.TimeUnit;

public class ActivityEvent implements Serializable {
	private int id;
	private String name;
	private String icon;
	private String description;
	private String start_date;
	private String end_date;
	private String start_time;
	private String end_time;
	private double lat;
	private double lng;
	private int status;
	private String vMessage;

	public String getMessage() {
		return (status == REMINDER_STATUS) ? EVENT_REMINDER_MESSAGE
				: EVENT_EXPIRED_MESSAGE;
	}

	public void setMessage(String message) {
		this.vMessage = message;
	}

	private int count_record;
	public static final int CLEAR_STATUS = 0;
	public static final int REMINDER_STATUS = 1;
	public static final int EXPIRED_STATUS = 2;

	public static final String EVENT_REMINDER_MESSAGE = "Event Reminder !";
	public static final String EVENT_EXPIRED_MESSAGE = "Event Expired !";

	public int getCount_record() {
		return count_record;
	}

	public void setCount_record(int count_record) {
		this.count_record = count_record;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStartDate() {
		return start_date;
	}

	public void setStartDate(String start_date) {
		this.start_date = start_date;
	}

	public String getEndDate() {
		return end_date;
	}

	public void setEndDate(String end_date) {
		this.end_date = end_date;
	}

	public String getStartTime() {
		return start_time;
	}

	public void setStartTime(String start_time) {
		this.start_time = start_time;
	}

	public String getEndTime() {
		return end_time;
	}

	public void setEndTime(String end_time) {
		this.end_time = end_time;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

	public String getElapsedTime(String date, String time) {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:m:s");
		Date dt;
		try {
			dt = f.parse(date.concat(" " + time + ":00"));

			long duration = dt.getTime() - System.currentTimeMillis();
			long days = TimeUnit.MILLISECONDS.toDays(duration);
			long hours = TimeUnit.MILLISECONDS.toHours(duration);
			long minutes = TimeUnit.MILLISECONDS.toMinutes(duration);
			long seconds = TimeUnit.MILLISECONDS.toSeconds(duration);

			if (days > 0) {
				return days + " days left";
			} else if (hours > 0) {
				return hours + " hours left";
			} else if (minutes > 0) {
				return minutes + " minutes left";
			} else if (seconds > 0) {
				return seconds + " seconds left";
			} 

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Expired";
	}

//	@Override
//	public int describeContents() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public void writeToParcel(Parcel dest, int flags) {
//		// TODO Auto-generated method stub
//		dest.writeString(icon);
//		dest.writeDouble(lat);
//		dest.writeDouble(lng);
//	}
}
