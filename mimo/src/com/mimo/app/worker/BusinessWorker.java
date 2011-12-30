package com.mimo.app.worker;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import android.util.Log;

import com.mimo.app.model.Api;
import com.mimo.app.model.IApi;
import com.mimo.app.model.pojo.Business;

public class BusinessWorker extends BaseWorker implements IBusinessWorker {
	private static BusinessWorker businessWorker;
	private IApi api;

	

	protected BusinessWorker() {
		this.api = new Api();
		this.start();
	}

	public static BusinessWorker getInstance() {
		businessWorker = null;
		if (businessWorker == null ) {
			businessWorker = new BusinessWorker();
		}
		return businessWorker;
	} 

	public synchronized Business[] createApiCall() {
		Log.d("debug: call api thread is alive=>", ""+this.isAlive());
		while (this.isAlive()) {

		}
		
		stopWorker();
		return getResultWorker();
	}

	protected Business[] runWorker() {

		try {
			return api.getBusinessLocation();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected Business[] getResultWorker() {
		Business[] dataBusiness = (Business[]) this.resultWorker;

		return dataBusiness;
	}

	public void setResultWorker(Business[] dataBusiness) {
		Log.d("debug: done call api", "process done.----------------");
		this.resultWorker = dataBusiness;
	}

	/**
	 * @return the businessWorker
	 */
	public static BusinessWorker getBusinessWorker() {
		return businessWorker;
	}

	/**
	 * @param businessWorker the businessWorker to set
	 */
	public static void setBusinessWorker(BusinessWorker businessWorker) {
		BusinessWorker.businessWorker = businessWorker;
	}

}
