package com.example.downloadandoptpic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class MainActivity extends Activity {

	private String mCurrentFilePath = "";
	private ImageView mImage;
	private static String path = "http://img.my.csdn.net/uploads/201309/01/1378037235_3453.jpg";
	private Thread mDownThread;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mImage = (ImageView) findViewById(R.id.img_centent);
		mDownThread = new Thread(new DownLoadPicService(path));
		mDownThread.start();
		try {
			mDownThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mImage.setImageBitmap(scaleBitmap(50, 50));
	}

	private void downLoadPicture(String url) {

		HttpURLConnection httpURLConnection = null;
		InputStream inputStream = null;
		FileOutputStream fileOutputStream = null;
		String title = mkFile(url);
		try {
			httpURLConnection = (HttpURLConnection) new URL(url)
					.openConnection();
			httpURLConnection.setReadTimeout(10000);
			httpURLConnection.setConnectTimeout(10000);
			inputStream = httpURLConnection.getInputStream();

			fileOutputStream = this.openFileOutput(title, Context.MODE_PRIVATE);
			byte[] temp = new byte[1024];
			int index = 0;
			while ((index = inputStream.read(temp)) != -1) {
				fileOutputStream.write(temp, 0, index);
				fileOutputStream.flush();
			}
			Log.d("11", "11");

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			try {
				fileOutputStream.close();
				inputStream.close();
				httpURLConnection.disconnect();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	private String mkFile(String url) {
		// TODO Auto-generated method stub

		String title = "";
		int index = url.lastIndexOf("/");
		title = url.substring(index + 1);

		String path = getFilesDir().getPath();
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}

		mCurrentFilePath = path + "/" + title;
		return title;
	}

	private Bitmap scaleBitmap(int reqWidth, int reqHeight) {

		Bitmap bitmap = null;

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(mCurrentFilePath, options);
		int width = options.outWidth;
		int height = options.outHeight;
		int radioWidth = Math.round((float) width / (float) reqWidth);
		int redioHeight = Math.round((float) height / (float) reqHeight);

		int size = radioWidth < redioHeight ? radioWidth : redioHeight;

		options.inJustDecodeBounds = false;
		options.inSampleSize = size;

		bitmap = BitmapFactory.decodeFile(mCurrentFilePath, options);

		return bitmap;
	}

	private class DownLoadPicService implements Runnable {

		private String url;

		public DownLoadPicService(String url) {
			this.url = url;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			downLoadPicture(url);

		}

	}

}
