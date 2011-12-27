package com.mimo.app.model;

import java.io.IOException;
import java.util.Vector;

import org.apache.http.client.ClientProtocolException;

import android.util.Log;

import com.google.gson.Gson;
import com.mimo.app.model.httputil.HttpUtil;
import com.mimo.app.model.httputil.HttpUtil.Method;
import com.mimo.app.model.pojo.Business;

public class Api extends BaseModel implements IApi {
	private static final String PREFIX_URL = "http://mimocore.heroku.com/api/v1/";
	private final String BIZ_API = "biz.json";

	private HttpUtil httpUtil;
	private String apiUrl;

	private Gson gson = null;

	public Api() {
		this.apiUrl = PREFIX_URL;
		this.gson = new Gson();
	}

	public Business[] getBusinessLocation() throws ClientProtocolException,
			IOException {
		
		Log.d("debug: ", "------ run worker api call");
		
		httpUtil = new HttpUtil(apiUrl+BIZ_API, Method.GET);
		String responses = httpUtil.sendRequest();
		
		Business[] data = gson.fromJson(responses, Business[].class);
		Log.d("debug data=", ""+data.length);
		Log.d("debug data=", ""+data[0].getBizname());
		return data;
	}

}
