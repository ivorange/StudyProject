package com.yarin.android.Examples_07_07;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

public class Activity01 extends Activity implements OnClickListener{
	Button mButton1;
	Button mButton2;
	Button mButton3;
	Button date;
	Button time;

	TextView mTextView;
	TextView mTextView2;
	SimpleDateFormat dateFormat;
	Calendar calendar;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);
		calendar = Calendar.getInstance();
		dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		mTextView = (TextView) findViewById(R.id.TextView01);
		mTextView2 = (TextView) findViewById(R.id.TextView02);
		mButton1 = (Button) findViewById(R.id.Button01);
		mButton2 = (Button) findViewById(R.id.Button02);
		mButton3 = (Button) findViewById(R.id.Button03);
		date = (Button) findViewById(R.id.date);
		time = (Button) findViewById(R.id.time);
		mButton1.setOnClickListener(this);
		mButton2.setOnClickListener(this);
		mButton3.setOnClickListener(this);
		date.setOnClickListener(this);
		time.setOnClickListener(this);
	}

	/* 格式化字符串(7:3->07:03) */
	private String format(int x) {
		String s = "" + x;
		if (s.length() == 1)
			s = "0" + s;
		return s;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.date:
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH);
			int day = calendar.get(Calendar.DAY_OF_MONTH);
			new DatePickerDialog(Activity01.this, new DatePickerDialog.OnDateSetListener() {
				
				@Override
				public void onDateSet(DatePicker view, int year, int monthOfYear,
						int dayOfMonth) {
					// TODO Auto-generated method stub
					calendar.set(Calendar.YEAR, year);
					calendar.set(Calendar.MONTH, monthOfYear);
					calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
				}
			}, year, month, day).show();
			break;
		case R.id.time:
			int mHour = calendar.get(Calendar.HOUR_OF_DAY);
			int mMinute = calendar.get(Calendar.MINUTE);
			new TimePickerDialog(Activity01.this, new TimePickerDialog.OnTimeSetListener() {
				
				@Override
				public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
					// TODO Auto-generated method stub
					calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
					calendar.set(Calendar.MINUTE, minute);
					calendar.set(Calendar.SECOND, 0);
					calendar.set(Calendar.MILLISECOND, 0);
				}
			}, mHour, mMinute, DateFormat.is24HourFormat(Activity01.this)).show();
			break;
		case R.id.Button01:
			SharedPreferences preferences = getSharedPreferences("TEST", MODE_PRIVATE);
			Editor edit = preferences.edit();
			edit.putLong("time", calendar
					.getTimeInMillis());
			edit.commit();
			/* 建立Intent和PendingIntent，来调用目标组件 */
			Intent intent = new Intent(Activity01.this,
					CallAlarm.class);
			intent.putExtra("_id", 0);
			PendingIntent pendingIntent = PendingIntent
					.getBroadcast(Activity01.this, 0,
							intent, 0);
			AlarmManager am;
			/* 获取闹钟管理的实例 */
			am = (AlarmManager) getSystemService(ALARM_SERVICE);
			/* 设置闹钟 */
			am.cancel(pendingIntent);
			am.set(AlarmManager.RTC_WAKEUP, calendar
					.getTimeInMillis(), pendingIntent);
			/* 设置周期闹 */
//			am.setRepeating(AlarmManager.RTC_WAKEUP, System
//					.currentTimeMillis()
//					+ (10 * 1000), (24 * 60 * 60 * 1000),
//					pendingIntent);
//			String tmpS = "设置闹钟时间为" + format(hourOfDay)
//					+ ":" + format(minute);
			Date dd = new Date(calendar.getTimeInMillis());
			mTextView.setText(dateFormat.format(dd));
			break;
		case R.id.Button02:
			Intent intent1 = new Intent(Activity01.this, CallAlarm.class);
			PendingIntent pendingIntent1 = PendingIntent.getBroadcast(
					Activity01.this, 0, intent1, 0);
			AlarmManager am1;
			/* 获取闹钟管理的实例 */
			am1 = (AlarmManager) getSystemService(ALARM_SERVICE);
			/* 取消 */
			am1.cancel(pendingIntent1);
			mTextView.setText("闹钟已取消！");
			break;
		case R.id.Button03:
//			SharedPreferences preferences2 = getSharedPreferences("TEST", MODE_PRIVATE);
//			Editor edit2 = preferences2.edit();
//			edit2.putLong("time", calendar
//					.getTimeInMillis());
//			edit2.commit();
//			/* 建立Intent和PendingIntent，来调用目标组件 */
//			Intent intent2 = new Intent(Activity01.this,
//					CallAlarm.class);
//			intent2.putExtra("tag", 1);
//			PendingIntent pendingIntent2 = PendingIntent
//					.getBroadcast(Activity01.this, 1,
//							intent2, 0);
//			AlarmManager am2;
//			/* 获取闹钟管理的实例 */
//			am2 = (AlarmManager) getSystemService(ALARM_SERVICE);
//			/* 设置闹钟 */
//			am2.set(AlarmManager.RTC_WAKEUP, calendar
//					.getTimeInMillis(), pendingIntent2);
////			/* 设置周期闹 */
////			am.setRepeating(AlarmManager.RTC_WAKEUP, System
////					.currentTimeMillis()
////					+ (10 * 1000), (24 * 60 * 60 * 1000),
////					pendingIntent);
////			String tmpS = "设置闹钟时间为" + format(hourOfDay)
////					+ ":" + format(minute);
//			Date dd2 = new Date(calendar.getTimeInMillis());
//			mTextView2.setText(dateFormat.format(dd2));
			
			try {
	            Intent i = new Intent();
	            ComponentName cn = null;
	            if (Integer.parseInt(Build.VERSION.SDK) >= 8) {
	                cn = new ComponentName("com.android.calendar",
	                        "com.android.calendar.LaunchActivity");

	            } else {
	                cn = new ComponentName("com.google.android.calendar",
	                        "com.android.calendar.LaunchActivity");
	            }
	            i.setComponent(cn);
	            startActivity(i);
	        } catch (ActivityNotFoundException e) {
	            // TODO: handle exception
	        }
			break;
		default:
			break;
		}
	}
}
