package com.example.servicetest;

import java.util.Random;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class BinderService extends Service {

	private MyBinder mBinder;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

		mBinder = new MyBinder();
		mBinder.setBinder(this);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return mBinder;
	}

	public class MyBinder extends Binder {

		private BinderService mService;

		public BinderService getService() {

			return mService;
		}

		public void setBinder(BinderService service) {

			mService = service;
		}

	}

	public String getRandomWithName() {

		int index = new Random().nextInt(100);

		return BinderService.class.getSimpleName() + "_" + index;
	}

}
