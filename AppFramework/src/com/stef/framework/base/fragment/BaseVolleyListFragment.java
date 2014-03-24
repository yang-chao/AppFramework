package com.stef.framework.base.fragment;

import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.stef.framework.base.request.RequestManager;

import android.os.Bundle;
import android.support.v4.app.ListFragment;

public abstract class BaseVolleyListFragment<T> extends ListFragment implements Listener<T>, ErrorListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getNetworkData();
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setListShown(false);
	}
	
	@Override
	public void onErrorResponse(VolleyError error) {
		onReceiveErrorResponse(error);
	}

	@Override
	public void onResponse(T response) {
		setListShown(true);
		onReciveResponse(response);
	}
	
	protected void getNetworkData() {
		RequestManager.getRequestQueue().add(createRequest(this, this));
	}
	
	public abstract Request<T> createRequest(Listener<T> listener, ErrorListener errorListener);
	
	protected abstract void onReciveResponse(T response);
	
	public abstract void onReceiveErrorResponse(VolleyError error);
}
