package com.stef.framework.base.fragment;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.stef.framework.base.request.RequestManager;

public abstract class VolleyFragment<T> extends BaseFragment implements Listener<T>, ErrorListener {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getNetworkData();
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setContentShown(false);
	}
	
	@Override
	public void onErrorResponse(VolleyError error) {
		if (!isAdded()) {
			return;
		}
		onReceiveErrorResponse(error);
	}

	@Override
	public void onResponse(T response) {
		if (!isAdded()) {
			return;
		}
		onReciveResponse(response);
	}
	
	protected void getNetworkData() {
		RequestManager.getRequestQueue().add(createRequest(this, this));
	}
	
	public abstract Request<T> createRequest(Listener<T> listener, ErrorListener errorListener);
	
	protected abstract void onReciveResponse(T response);
	
	public abstract void onReceiveErrorResponse(VolleyError error);
}
