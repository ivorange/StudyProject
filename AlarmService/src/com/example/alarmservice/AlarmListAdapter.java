package com.example.alarmservice;

import java.util.ArrayList;
import java.util.List;

import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class AlarmListAdapter extends BaseAdapter{

	private List<PendingIntent> mAllPends = new ArrayList<PendingIntent>();
	private Context mCtx;
	
	public AlarmListAdapter(Context context,List<PendingIntent> mallLists) {
		// TODO Auto-generated constructor stub
		mCtx = context;
		mAllPends = mallLists;
	}
	
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mAllPends.size();
	}
	
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mAllPends.get(position);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		TextView textView = new TextView(mCtx);
		textView.setBackgroundColor(Color.BLUE);
		textView.setText("This is the Alarm "+getItemId(position));
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,200);
		textView.setLayoutParams(params);
		PendingIntent pendingIntent = (PendingIntent) getItem(position);
		textView.setTag(pendingIntent);
		return textView;
	}
	
}
