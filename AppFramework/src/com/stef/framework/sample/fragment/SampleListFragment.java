package com.stef.framework.sample.fragment;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.stef.framework.base.fragment.VolleyListFragment;
import com.stef.framework.sample.fragment.model.SampleData;
import com.stef.framework.sample.fragment.model.SampleModel;

public class SampleListFragment extends VolleyListFragment<SampleData> {
	
	private final String TAG = getClass().getName();

	@Override
	public Request<SampleData> createRequest(Listener<SampleData> listener, ErrorListener errorListener) {
		return SampleModel.getSamplesRequest(listener, errorListener);
	}

	@Override
	protected void onReciveResponse(SampleData response) {
		setListAdapter(new SampleListAdapter(getActivity(), response));
	}

	@Override
	public void onReceiveErrorResponse(VolleyError error) {
		Log.e(TAG, "Data failed to load");
	}

}
