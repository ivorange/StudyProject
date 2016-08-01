package com.example.alarmservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver{

	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String content = intent.getStringExtra("Name");
		Toast.makeText(context, "ƒ÷÷” ±º‰µΩ  "+content, Toast.LENGTH_LONG).show();  
	}
}
