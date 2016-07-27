package com.example.refreshdemo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.logging.Log;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity implements IrefreshDataListener {

	private DemoListView mDemoListView;
	private CommentAdapter mCommentAdapter;
	private List<CommentBean> mAllComents;
	private int mCurrentIndex = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		initView();
		initData();
		bindData();

	}

	private void initView() {
		// TODO Auto-generated method stub

		mDemoListView = (DemoListView) findViewById(R.id.demolist_content);
		mDemoListView.setListener(this);
	}

	private void initData() {

		// TODO Auto-generated method stub
		mAllComents = new ArrayList<CommentBean>();

		for (int i = 0; i < 15; i++) {

			CommentBean comment = new CommentBean();
			comment.setmID(i + "");
			comment.setmContent("This is the " + i);
			comment.setmDate(Calendar.getInstance().getTime() + "");
			mAllComents.add(comment);
			mCurrentIndex = i;
		}

		mCommentAdapter = new CommentAdapter(getApplicationContext(), mAllComents);
	}

	private void bindData() {
		// TODO Auto-generated method stub

		mDemoListView.setAdapter(mCommentAdapter);

	}

	private void loadData() {

		for (int i = 1; i <= 15; i++) {

			CommentBean comment = new CommentBean();
			comment.setmID(i + "");
			comment.setmContent("This is the " + i);
			comment.setmDate(Calendar.getInstance().getTime() + "");
			mAllComents.add(comment);
		}
		
		mCommentAdapter.notifyDataSetChanged();
		mDemoListView.loadComplete();
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub

		loadData();
		
	}

}
