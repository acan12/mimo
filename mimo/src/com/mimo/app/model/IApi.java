package com.mimo.app.model;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import com.mimo.app.model.pojo.Business;

public interface IApi {
	public Business[] getBusinessLocation() throws ClientProtocolException,
			IOException;
}
