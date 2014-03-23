package com.stef.framework.base.application;

import android.app.Application;
import android.graphics.Bitmap.CompressFormat;

import com.stef.framework.base.cache.ImageCacheManager;
import com.stef.framework.base.cache.ImageCacheManager.CacheType;
import com.stef.framework.base.request.RequestManager;

public class BaseApplication extends Application {
	
	private static int DISK_IMAGECACHE_SIZE = 1024*1024*10;
	private static CompressFormat DISK_IMAGECACHE_COMPRESS_FORMAT = CompressFormat.PNG;
	private static int DISK_IMAGECACHE_QUALITY = 100;  //PNG is lossless so quality is ignored but must be provided
	
	@Override
	public void onCreate() {
		super.onCreate();

		initVolley();
	}

	/**
	 * Intialize the request manager and the image cache 
	 */
	private void initVolley() {
		RequestManager.init(this);
		createImageCache();
	}
	
	/**
	 * Create the image cache. Uses Memory Cache by default. Change to Disk for a Disk based LRU implementation.  
	 */
	private void createImageCache(){
		ImageCacheManager.getInstance().init(this,
				this.getPackageCodePath()
				, DISK_IMAGECACHE_SIZE
				, DISK_IMAGECACHE_COMPRESS_FORMAT
				, DISK_IMAGECACHE_QUALITY
				, CacheType.MEMORY);
	}
}
