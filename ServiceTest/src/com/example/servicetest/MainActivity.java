package com.example.servicetest;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Messenger;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.servicetest.BinderService.MyBinder;

public class MainActivity extends Activity {

	private boolean isReady = false;
	private Messenger mMessenger;
	private int mCurrentIndex = 0;
	private Button mBtnSend;
	private BinderService mBindService;

	private ServiceConnection serviceCnn = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			isReady = false;
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			isReady = true;
			mMessenger = new Messenger(service);
		}
	};

	private ServiceConnection serviceCnn2 = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			isReady = false;
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			isReady = true;
			MyBinder binder = (MyBinder) service;
			mBindService = binder.getService();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mBtnSend = (Button) findViewById(R.id.btn_send);

		mBtnSend.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				talk();
			}
		});
	}

	private void talk() {
		if (!isReady) {

			Intent intent = new Intent();
			intent.setAction("com.example.binderservice");
			// intent.setPackage("com.example.servicetest");
			// bindService(intent, serviceCnn, Context.BIND_AUTO_CREATE);
			// //ʹ��Messenger
			bindService(intent, serviceCnn2, Context.BIND_AUTO_CREATE);
			return;
		}

		// ʹ��Messenger

		// Message message = new Message();
		// message.obj = "The currentIndex = " + mCurrentIndex;
		// mCurrentIndex++;
		// try {
		// mMessenger.send(message);
		// } catch (RemoteException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		Toast.makeText(getApplicationContext(),
				mBindService.getRandomWithName(), Toast.LENGTH_SHORT).show();

	}
}
