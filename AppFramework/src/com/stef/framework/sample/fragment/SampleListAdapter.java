package com.stef.framework.sample.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.stef.framework.R;
import com.stef.framework.base.cache.ImageCacheManager;
import com.stef.framework.sample.fragment.model.Sample;
import com.stef.framework.sample.fragment.model.SampleData;

public class SampleListAdapter extends ArrayAdapter<Sample> {

	private LayoutInflater mInflater;
	private SampleData mData;
	
	public SampleListAdapter(Context context, SampleData data) {
		super(context, R.layout.sample_adapter_pulltorefresh_1);
		mInflater = LayoutInflater.from(context);
		mData = data;
	}
	
	@Override
	public int getCount() {
		return mData == null ? 0 : mData.getSamples().size();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.sample_adapter_pulltorefresh_1, null);
			holder.image = (NetworkImageView) convertView.findViewById(R.id.image);
			holder.title = (TextView) convertView.findViewById(R.id.title);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		Sample sample = mData.getSamples().get(position);
		holder.title.setText(sample.getTitle());
		holder.image.setImageUrl(sample.getImageUrl(), ImageCacheManager.getInstance().getImageLoader());
		
		return convertView;
	}
	
	class ViewHolder {
		private TextView title;
		private NetworkImageView image;
	}

}
