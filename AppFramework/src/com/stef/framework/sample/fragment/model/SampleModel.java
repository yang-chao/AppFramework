package com.stef.framework.sample.fragment.model;

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonRequest;
import com.stef.framework.base.request.GsonRequest;
import com.stef.framework.base.request.RequestManager;

public class SampleModel {

	
	public static void getSamples(Listener<SampleData> listener, ErrorListener errorListener) {
		String url = "http://c.m.163.com/nc/video/detail/V87RBQI44.html";
		GsonRequest<SampleData> request = new GsonRequest<SampleData>(Method.GET, url, 
				SampleData.class, listener, errorListener);
		RequestManager.getRequestQueue().add(request);
	}
	
	public static Request<SampleData> getSamplesRequest(Listener<SampleData> listener, ErrorListener errorListener) {
		String url = "http://c.m.163.com/nc/video/detail/V87RBQI44.html";
		return new GsonRequest<SampleData>(Method.GET, url, SampleData.class, listener, errorListener);
	}
}
