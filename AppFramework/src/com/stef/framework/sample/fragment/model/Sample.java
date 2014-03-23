package com.stef.framework.sample.fragment.model;

import com.google.gson.annotations.SerializedName;

public class Sample {
	
	private String title;
	
	@SerializedName("cover")
	private String imageUrl;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

}
