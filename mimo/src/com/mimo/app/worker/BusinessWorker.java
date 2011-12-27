package com.mimo.app.worker;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import android.app.ProgressDialog;
import android.util.Log;

import com.mimo.app.model.Api;
import com.mimo.app.model.IApi;
import com.mimo.app.model.pojo.Business;

public class BusinessWorker extends BaseWorker implements IBusinessWorker {
	private static BusinessWorker businessWorker;
	private IApi api;
	private Object[] resultWorker;

	public BusinessWorker() {
		this.api = new Api();
	}

	public BusinessWorker getInstance() {
		if (businessWorker == null) {
			businessWorker = new BusinessWorker();
		}
		return businessWorker;
	}

	public Object[] createApiCall() {
		this.start();
		while (this.isAlive()) {

		}
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
		Business[] result = (Business[]) this.resultWorker;

		return result;
	}

	@Override
	public void setResultWorker(Object[] data) {
		Business[] result = (Business[]) data;
		this.resultWorker = data;
	}

}
