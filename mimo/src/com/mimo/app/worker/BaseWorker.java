package com.mimo.app.worker;

import com.mimo.app.model.pojo.Business;

import android.util.Log;

public class BaseWorker extends Thread{
	private Object[] resultWorker = null;
	
	protected void stopWorker(){
		this.stop();
	}
	
	public void run(){
		this.setResultWorker(runWorker());
	}
	
	protected Object[] runWorker(){
		return null;
	}
	 
	
	
	/**
	 * @return the object
	 */
	protected Object[] getResultWorker() {
		return resultWorker;
	}

	/**
	 * @param object the object to set
	 */
	public void setResultWorker(Object[] data) {
		Business[] result = (Business[])data;
		Log.d("debug Object=", ""+result.length);
		Log.d("debug Object=", ""+result[0].getBizname());
		this.resultWorker = data;
	}
}
