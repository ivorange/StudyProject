package com.example.servicetest;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.widget.Toast;

public class MyService extends Service {

	private Handler mServiceHandler;
	private Messenger messenger = null;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

		mServiceHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				String content = (String) msg.obj;

				Toast.makeText(getApplicationContext(), content,
						Toast.LENGTH_SHORT).show();
			}
		};
		messenger = new Messenger(mServiceHandler);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub

		return messenger.getBinder();
	}

}
