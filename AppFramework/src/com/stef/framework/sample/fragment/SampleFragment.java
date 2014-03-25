package com.stef.framework.sample.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.stef.framework.R;
import com.stef.framework.base.cache.ImageCacheManager;
import com.stef.framework.base.fragment.VolleyFragment;
import com.stef.framework.base.request.GsonRequest;
import com.stef.framework.sample.fragment.model.SampleData;


public class SampleFragment extends VolleyFragment<SampleData> {
	
	@Override
	public View onCreateFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.sample_fragment_1, container, false);
	}

	@Override
	public Request<SampleData> createRequest(Listener<SampleData> listener, ErrorListener errorListener) {
		return new GsonRequest<SampleData>(Method.GET, "http://t.c.m.163.com/nc/video/detail/V87RBQI44.html", 
				SampleData.class, listener, errorListener);
	}

	@Override
	protected void onReciveResponse(SampleData response) {
		if (response == null) {
			showEmpty(true);
		} else {
			TextView title = (TextView) getView().findViewById(R.id.title);
			NetworkImageView cover = (NetworkImageView) getView().findViewById(R.id.cover);
			
			title.setText(response.getTitle());
			cover.setImageUrl(response.getCover(), ImageCacheManager.getInstance().getImageLoader());
			
			setContentShown(true);
		}
	}

	@Override
	public void onReceiveErrorResponse(VolleyError error) {
		showEmpty(true);
	}

	
	
}
