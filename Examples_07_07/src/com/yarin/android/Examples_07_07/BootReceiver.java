package com.yarin.android.Examples_07_07;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

/**
 * �����㲥������
 * 
 * @author way
 * 
 */
public class BootReceiver extends BroadcastReceiver {
	private static final String TAG = "BootReceiver";
	private Context mContext;
	int mClockId;
	public static final String SET_ALARM_INTENT = "com.way.note.SET_ALARM";

	private static final int SECOND = 1000000;

	private List<Long> alarmTimes = new ArrayList<Long>();

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.v(TAG, "onReceiver");
		mContext = context;

		String action = intent.getAction();
		if ("android.intent.action.BOOT_COMPLETED".equals(action)
				|| SET_ALARM_INTENT.equals(action) 
				|| "android.intent.action.USER_PRESENT".equals(action)) {
			System.out.println("1111111111111111111111111111111111111111111111111");
			SharedPreferences preferences = mContext.getSharedPreferences("TEST", Context.MODE_PRIVATE);
			long time = preferences.getLong("time", 0);
			if(time == 0){
				return;
			}
			System.out.println("aaaaa"+time);
			/* ����Intent��PendingIntent��������Ŀ����� */
			Intent intent2 = new Intent(mContext,
					CallAlarm.class);
			intent2.putExtra("_id", 0);
			PendingIntent pendingIntent = PendingIntent
					.getBroadcast(mContext, 0,
							intent2, 0);
			AlarmManager am;
			/* ��ȡ���ӹ����ʵ�� */
			am = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
			/* �������� */
			am.cancel(pendingIntent);
			am.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
			/* ���������� */
//			am.setRepeating(AlarmManager.RTC_WAKEUP, System
//					.currentTimeMillis()
//					+ (10 * 1000), (24 * 60 * 60 * 1000),
//					pendingIntent);
//			NoteDataManager dataManger = NoteDataManagerImpl
//					.getNoteDataManger(context);
//			List<NoteItem> clockAlarmItems = dataManger
//					.getColckAlarmItemsFromDB();
//			alarmTimes.clear();
//			for (NoteItem item : clockAlarmItems) {
//				long alarmClock = 0L;
//				try {
//					alarmClock = item.clockItem.getClockLong();
//				} catch (ParseException e) {
//					e.printStackTrace();
//				}
//				Log.v(TAG, "alarmClock-->" + alarmClock + " currentTime-->"
//						+ System.currentTimeMillis());
//				if (isAboveNow(alarmClock) && isNotSameClock(alarmClock)) {
//					Log.v(TAG, "setAlarm-->" + item);
//					ClockUtils.setAlarmClock(mContext, item);
//					alarmTimes.add(alarmClock);
//				} else {
//					Log.v(TAG, "cancle-->" + item);
//					item.alarmEnable = false;
//					dataManger.updateItem(item);
//				}
//			}
		}
	}

	public boolean isAboveNow(long alarmClock) {
		return (alarmClock - System.currentTimeMillis() > SECOND * 0);
	}

	public boolean isNotSameClock(long alarmClock) {
		for (Long alarmHaved : alarmTimes) {
			if (alarmHaved == alarmClock) {
				return false;
			}
		}
		return true;
	}
}
