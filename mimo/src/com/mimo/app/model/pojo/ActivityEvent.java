package com.mimo.app.model.pojo;



import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.mimo.app.util.TimeUnit;

import android.util.Log;
import android.widget.DatePicker;

public class ActivityEvent {
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
	private int diffDay;
	private String descDiffDay;

	public String getMessage() {
		return (status == REMINDER_STATUS) ? EVENT_REMINDER_MESSAGE : EVENT_EXPIRED_MESSAGE;
	}
	public void setMessage(String message) {
		this.vMessage = message;
	}
	private int count_record;
	public static final int CLEAR_STATUS 		= 0;
	public static final int REMINDER_STATUS 	= 1;
	public static final int EXPIRED_STATUS 	= 2;
	
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
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getStatus() {
		return status;
	}
	
	public int getDiffDay(){
		return diffDay;
	}
	
	public void setDiffDay(int diffDay){
		this.diffDay = diffDay;
	}
	
	public String getElapsedTime(String date, String time){
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:m:s");
		Date dt;
		try {
			dt = f.parse(date.concat(" "+time+":00"));
		

			long duration = System.currentTimeMillis() - dt.getTime();
			long days = TimeUnit.MILLISECONDS.toDays(duration);
			long hours = TimeUnit.MILLISECONDS.toHours(duration);
			long minutes = TimeUnit.MILLISECONDS.toMinutes(duration);
			long seconds = TimeUnit.MILLISECONDS.toSeconds(duration);
			
			if(days > 0){
				return days + " days left";
			} else if(hours > 0){
				return hours + " hours left";
			} else if(minutes > 0){
				return minutes + " minutes left";
			} else if(seconds > 0){
				return seconds + " seconds left";
			}
			
		
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
}
