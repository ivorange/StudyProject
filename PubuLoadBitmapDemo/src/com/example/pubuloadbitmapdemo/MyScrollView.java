package com.example.pubuloadbitmapdemo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

public class MyScrollView extends ScrollView implements OnTouchListener {

	private int mRequiredWidth;
	private int mFirstCurentHeight = 0;
	private int mSecondCurrentHeight = 0;
	private int mThirdCurrentHeight = 0;
	private int mCurrentPage;
	private int mPageSize = 15;
	private int mBitmpaCount;
	private boolean mFirstLoad;
	private int mLastTouchY = -1;
	private int mScrollViewHeight;

	private ImageLoader mImgLoader;

	private LinearLayout mFirstLayout;
	private LinearLayout mSecondLayout;
	private LinearLayout mThirdLayout;

	private Set<ImageLoadTask> mImageLoadTask;

	private List<ImageView> mAllImageViews = new ArrayList<>();

	private Handler mHandler;

	public MyScrollView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub

		initData();
	}

	public MyScrollView(Context context, AttributeSet attributeSet) {
		// TODO Auto-generated constructor stub

		super(context, attributeSet);
		initData();
	}

	public MyScrollView(Context context, AttributeSet attributeSet, int style) {
		// TODO Auto-generated constructor stub

		super(context, attributeSet, style);
		initData();
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);

		if (mFirstLoad) {

			mFirstLoad = false;
			mFirstLayout = (LinearLayout) findViewById(R.id.ll_first);
			mSecondLayout = (LinearLayout) findViewById(R.id.ll_second);
			mThirdLayout = (LinearLayout) findViewById(R.id.ll_third);

			mRequiredWidth = mFirstLayout.getMeasuredWidth();
			loadImage();
			mScrollViewHeight = this.getChildAt(0).getHeight();
		}

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		if (event.getAction() == MotionEvent.ACTION_UP) {

			mLastTouchY = this.getScrollY();
			Message message = mHandler.obtainMessage();
			message.obj = MyScrollView.this.getScrollY();
			message.sendToTarget();
		}
		return false;
	}

	private void initData() {
		// TODO Auto-generated method stub

		mScrollViewHeight = 0;
		mFirstLoad = true;
		mCurrentPage = 0;
		mImageLoadTask = new HashSet<>();
		mImgLoader = new ImageLoader();

		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);

				int scrollY = (int) msg.obj;
				if (scrollY == mLastTouchY && mImageLoadTask.size() == 0) {
					Toast.makeText(getContext(), "发送消息加载数据", Toast.LENGTH_SHORT)
							.show();
					loadImage();
					checkVisiabale();
				} else {

					Message message = new Message();
					message.obj = MyScrollView.this.getScrollY();
					mHandler.sendMessageDelayed(message, 5);

				}
			}
		};
		this.setOnTouchListener(this);
	}

	private void checkVisiabale() {

		for (int i = 0; i < mAllImageViews.size(); i++) {

			ImageView imageView = mAllImageViews.get(i);

			int header = (int) imageView.getTag(R.string.border_top);
			int bottom = (int) imageView.getTag(R.string.border_bottom);

			if (bottom > getScrollY()
					&& header < getScrollY() + mScrollViewHeight) {
				String imageUrl = (String) imageView.getTag(R.string.image_url);
				Bitmap bitmap = mImgLoader.getBitmapFromCache(imageUrl);
				if (bitmap != null) {
					imageView.setImageBitmap(bitmap);
				} else {
					ImageLoadTask task = new ImageLoadTask(imageView);
					mImageLoadTask.add(task);
					task.execute(imageUrl);
				}
			} else {
				imageView.setImageResource(R.drawable.empty_photo);
			}

		}

	}

	/**
	 * 开始加载图片
	 */
	private void loadImage() {
		// TODO Auto-generated method stub

		int startIndex = mCurrentPage * mPageSize;
		int endIndex = mCurrentPage * mPageSize + mPageSize;

		if (startIndex < Images.imageUrls.length) {

			Toast.makeText(getContext(), "正在加载图片....", Toast.LENGTH_SHORT)
					.show();
			if (endIndex > Images.imageUrls.length) {

				endIndex = Images.imageUrls.length;
			}
			for (int i = startIndex; i < endIndex; i++) {

				ImageLoadTask loadTask = new ImageLoadTask();
				mImageLoadTask.add(loadTask);
				loadTask.execute(Images.imageUrls[i]);
			}
			mCurrentPage++;
			Toast.makeText(getContext(), "当前第" + mCurrentPage + "页图片加载完毕",
					Toast.LENGTH_SHORT).show();
		} else {

			Toast.makeText(getContext(), "...........", Toast.LENGTH_SHORT)
					.show();
		}

	}

	/**
	 * 是否有SD卡
	 */
	private boolean hasSDCard() {
		// TODO Auto-generated method stub
		return Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageDirectory());
	}

	private class ImageLoadTask extends AsyncTask<String, Integer, Bitmap> {

		private String mImagurl;
		private ImageView mImageView;

		public ImageLoadTask() {
			// TODO Auto-generated constructor stub
		}

		public ImageLoadTask(ImageView imageView) {
			// TODO Auto-generated constructor stub
			mImageView = imageView;
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			// TODO Auto-generated method stub
			mImagurl = params[0];
			Bitmap bitmap = mImgLoader.getBitmapFromCache(params[0]);

			if (bitmap == null) {
				bitmap = downloadImage(params[0]);

			}

			return bitmap;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result != null) {

				int radio = result.getWidth() / mRequiredWidth;
				int scaleHeight = (int) (result.getHeight() / radio);
				addImage(mImageView, result, result.getWidth(), scaleHeight,
						mImagurl);
			}

			mImageLoadTask.remove(this);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

	}

	/**
	 * 往imageview 添加bitmap
	 */
	private void addImage(Bitmap bit, ImageView imageView) {
		// TODO Auto-generated method stub

	}

	private Bitmap downloadImage(String imageUrl) {
		// TODO Auto-generated method stub
		Bitmap bitmap = null;
		File file = new File(getContext().getFilesDir().getPath() + "/"
				+ getImageName(imageUrl));
		if (!file.exists()) {
			downloadImgaeByUrl(imageUrl);
		}
		if (imageUrl != null) {

			bitmap = mImgLoader.decodeBitmapFromPath(file.getPath(),
					mRequiredWidth);

			if (bitmap != null) {
				mImgLoader.addBitmapToLruCache(bitmap, imageUrl);
			}
		}

		return bitmap;
	}

	private void downloadImgaeByUrl(String imagePath) {
		// TODO Auto-generated method stub

		HttpURLConnection httpUrlCnn = null;
		FileOutputStream fileOutPut;
		BufferedInputStream bufferInput = null;
		BufferedOutputStream bufferOutPut = null;
		String fileName = "";

		try {

			httpUrlCnn = (HttpURLConnection) new URL(imagePath)
					.openConnection();
			httpUrlCnn.setReadTimeout(5000);
			httpUrlCnn.setConnectTimeout(5000);
			httpUrlCnn.setDoInput(true);
			httpUrlCnn.setDoOutput(true);
			fileName = getImageName(imagePath);
			bufferInput = new BufferedInputStream(httpUrlCnn.getInputStream());
			fileOutPut = getContext().openFileOutput(fileName,
					Context.MODE_PRIVATE);
			bufferOutPut = new BufferedOutputStream(fileOutPut);
			byte[] b = new byte[1024];
			int length = 0;
			while ((length = bufferInput.read(b)) != -1) {
				bufferOutPut.write(b, 0, length);
				bufferOutPut.flush();

			}

		} catch (Exception e) {
			// TODO: handle exception

			Log.d("Exception", "the exception is " + e.getMessage());
		} finally {
			try {

				if (bufferOutPut != null) {
					bufferOutPut.close();
				}

				if (bufferInput != null) {
					bufferInput.close();
				}

				if (httpUrlCnn != null) {
					httpUrlCnn.disconnect();
				}

			} catch (Exception e2) {
				// TODO: handle exception
			}

		}

		if (imagePath != null) {

			Bitmap bitmap = mImgLoader.decodeBitmapFromPath(getContext()
					.getFilesDir().getPath() + "/" + fileName, mRequiredWidth);
			if (bitmap != null) {

				mImgLoader.addBitmapToLruCache(bitmap, imagePath);

			}
		}

	}

	private void addImage(ImageView image, Bitmap bitmap, int width,
			int scaleHeight, String imageUrl) {
		// TODO Auto-generated method stub
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width,
				scaleHeight);

		if (image != null) {

			image.setImageBitmap(bitmap);

		} else {

			ImageView imageView = new ImageView(getContext());
			imageView.setLayoutParams(params);
			imageView.setScaleType(ScaleType.FIT_XY);
			imageView.setPadding(5, 5, 5, 5);
			imageView.setImageBitmap(bitmap);
			imageView.setTag(R.string.image_url, imageUrl);
			selectToAdd(imageView, scaleHeight).addView(imageView);
			mAllImageViews.add(imageView);
		}

	}

	private LinearLayout selectToAdd(ImageView imageView, int imageHeight) {

		if (mFirstCurentHeight <= mSecondCurrentHeight) {

			if (mFirstCurentHeight <= mThirdCurrentHeight) {

				imageView.setTag(R.string.border_top, mFirstCurentHeight);
				mFirstCurentHeight += imageHeight;
				imageView.setTag(R.string.border_bottom, mFirstCurentHeight);

				return mFirstLayout;
			}

			imageView.setTag(R.string.border_top, mThirdCurrentHeight);
			mThirdCurrentHeight += imageHeight;
			imageView.setTag(R.string.border_bottom, mThirdCurrentHeight);

			return mThirdLayout;
		} else {

			if (mSecondCurrentHeight <= mThirdCurrentHeight) {

				imageView.setTag(R.string.border_top, mSecondCurrentHeight);
				mSecondCurrentHeight += imageHeight;
				imageView.setTag(R.string.border_bottom, mSecondCurrentHeight);

				return mSecondLayout;
			}
			imageView.setTag(R.string.border_top, mThirdCurrentHeight);
			mThirdCurrentHeight += imageHeight;
			imageView.setTag(R.string.border_bottom, mThirdCurrentHeight);
			return mThirdLayout;
		}

	}

	private String getImageName(String imageUrl) {
		// TODO Auto-generated method stub
		int lastIndex = imageUrl.lastIndexOf("/");
		String imgName = imageUrl.substring(lastIndex + 1);

		return imgName;
	}

}
