package com.stef.framework.sample.fragment;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.stef.framework.sample.fragment.model.SampleData;
import com.stef.framework.sample.fragment.model.SampleModel;

import uk.co.senab.actionbarpulltorefresh.extras.actionbarcompat.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class PullToRefreshListFragment extends ListFragment implements OnRefreshListener, 
	Listener<SampleData>, ErrorListener {

	private final String TAG = getClass().getName();
	
	private PullToRefreshLayout mPullToRefreshLayout;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SampleModel.getSamples(this, this);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		ViewGroup viewGroup = (ViewGroup) view;

		// As we're using a ListFragment we create a PullToRefreshLayout
		// manually
		mPullToRefreshLayout = new PullToRefreshLayout(viewGroup.getContext());

		// We can now setup the PullToRefreshLayout
		ActionBarPullToRefresh
				.from(getActivity())
				// We need to insert the PullToRefreshLayout into the Fragment's
				// ViewGroup
				.insertLayoutInto(viewGroup)
				// Here we mark just the ListView and it's Empty View as
				// pullable
				.theseChildrenArePullable(android.R.id.list, android.R.id.empty)
				.listener(this).setup(mPullToRefreshLayout);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		setListShown(false);
	}
	

	@Override
	public void onRefreshStarted(View view) {
		// Hide the list
		setListShown(false);

		SampleModel.getSamples(this, this);
	}

	@Override
	public void onErrorResponse(VolleyError error) {
		Log.e(TAG, "Data failed to load");
		mPullToRefreshLayout.setRefreshComplete();
	}

	@Override
	public void onResponse(SampleData response) {
		setListAdapter(new PullToRefreshListAdapter(getActivity(), response));
		setListShown(true);
		mPullToRefreshLayout.setRefreshComplete();
	}
}
