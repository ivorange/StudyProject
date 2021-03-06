package com.example.pubuloadbitmapdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;

public class ImageLoader {

	private LruCache<String, Bitmap> mImageCache;

	public ImageLoader() {
		// TODO Auto-generated constructor stub

		int maxSize = (int) Runtime.getRuntime().maxMemory();

		int cacheSize = maxSize / 8;
		mImageCache = new LruCache<>(cacheSize);

	}

	/**
	 * 增加一个图片至Cache
	 */
	public void addBitmapToLruCache(Bitmap bit, String path) {
		// TODO Auto-generated method stub

		if (mImageCache.get(path) == null) {

			mImageCache.put(path, bit);
		}
	}

	/**
	 * 获取一张图片通过path
	 */
	public Bitmap getBitmapFromCache(String path) {
		// TODO Auto-generated method stub
		Bitmap bit = null;
		bit = mImageCache.get(path);

		return bit;
	}

	private static int getSamesize(BitmapFactory.Options option,
			int requiredWidth) {

		int width = option.outWidth;
		int sameSize = 1;
		if (width > requiredWidth) {

			int temp = Math.round(width / requiredWidth);
			sameSize = temp;
		}

		return sameSize;

	}

	public static Bitmap decodeBitmapFromPath(String path, int requiredWidth) {

		BitmapFactory.Options option = new BitmapFactory.Options();
		option.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, option);
		option.inSampleSize = getSamesize(option, requiredWidth);
		option.inJustDecodeBounds = false;
		Bitmap bitmap = BitmapFactory.decodeFile(path, option);
		return bitmap;
	}

}
