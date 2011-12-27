package com.mimo.app.worker;

import android.app.ProgressDialog;
import android.content.Context;

import com.mimo.app.model.pojo.Business;

public interface IBusinessWorker {

	public BusinessWorker getInstance();

	public Object[] createApiCall();
}
