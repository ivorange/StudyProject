package com.example.refreshdemo;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CommentAdapter extends BaseAdapter{
	
	private Context mCtx;
	private LayoutInflater mLayoutInflater;
	private List<CommentBean> mComments;
	
	public CommentAdapter(Context context,List<CommentBean> mAllComments) {
		// TODO Auto-generated constructor stub
		
		mCtx = context;
		mLayoutInflater = LayoutInflater.from(mCtx);
		mComments = mAllComments;
	}
	
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mComments.size();
	}
	
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mComments.get(position);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		
		if (convertView == null) {
			
			viewHolder = new ViewHolder();
			convertView = mLayoutInflater.inflate(R.layout.list_comment_layout, parent,false);
			viewHolder.mTextViewId = (TextView) convertView.findViewById(R.id.text_id);
			viewHolder.mTextContents = (TextView) convertView.findViewById(R.id.text_content);
			viewHolder.mTextDate = (TextView) convertView.findViewById(R.id.text_date);
			
			
			
			convertView.setTag(viewHolder);
		}else {
			
			viewHolder = (ViewHolder) convertView.getTag();
			
		}
		
		CommentBean commentBean = (CommentBean) getItem(position);
		
		viewHolder.mTextViewId.setText(commentBean.getmID());
		viewHolder.mTextContents.setText(commentBean.getmContent());
		viewHolder.mTextDate.setText(commentBean.getmDate());
		
		return convertView;
	}
	
	public class ViewHolder{
		
		TextView mTextViewId;
		TextView mTextContents;
		TextView mTextDate;
	
	
	}

}
