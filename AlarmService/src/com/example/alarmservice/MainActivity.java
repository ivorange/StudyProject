package com.example.alarmservice;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

public class MainActivity extends RoboActivity {

	@InjectView(R.id.btn_add)
	private Button mBtnAdd;
	@InjectView(R.id.btn_del)
	private Button mBtnDel;
	@InjectView(R.id.list_alarm_content)
	private ListView mListContent;

	private Calendar mCalendar;
	private List<PendingIntent> mAllPendings;
	private int mCurrentIndex= 0;
	private AlarmListAdapter mAlarmAdapter;
	AlarmManager alarmManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initData();
		initView();
	}
	
	private void initData() {
		// TODO Auto-generated method stub
		alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		mAllPendings = new ArrayList<PendingIntent>();
		mAlarmAdapter = new AlarmListAdapter(getApplicationContext(), mAllPendings);
	}

	private void initView() {
		// TODO Auto-generated method stub
		mCalendar = Calendar.getInstance();
		mCalendar.setTimeInMillis(System.currentTimeMillis());

		final int hour = mCalendar.get(Calendar.HOUR_OF_DAY);
		final int mMinute = mCalendar.get(Calendar.MINUTE);
		mBtnAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
					
					@Override
					public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
						// TODO Auto-generated method stub
						mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
						mCalendar.set(Calendar.MINUTE, minute);
						mCalendar.set(Calendar.SECOND, 0);
						mCalendar.set(Calendar.MILLISECOND, 0);
						
						addNewAlarm();
					}
				}, hour, mMinute, DateFormat.is24HourFormat(MainActivity.this)).show();

			}
		});
		mListContent.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "you click the "+position+"item", Toast.LENGTH_LONG).show();
				PendingIntent pendingIntent = mAllPendings.get(position);
				alarmManager.cancel(pendingIntent);
				
				mAllPendings.remove(pendingIntent);
				mAlarmAdapter.notifyDataSetChanged();
			}
			
			
		});
	
		mListContent.setAdapter(mAlarmAdapter);
	}

	private void addNewAlarm() {
		// TODO Auto-generated method stub
		
		Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
		intent.setAction("action1" + mCurrentIndex);
		intent.putExtra("Name", "this is the action "+mCurrentIndex);
		PendingIntent pi = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);
		
		alarmManager.set(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis(), pi);
		
		mCurrentIndex++;
		mAllPendings.add(pi);
		mAlarmAdapter.notifyDataSetChanged();
		
	}
}
