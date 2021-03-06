package com.example.observerproject;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	private LinearLayout mllObserverContent;
	private TextView mTextAdd;
	private TextView mTextDel;
	private TextView mTextUpdateOne;
	private TextView mTextUpdateAll;
	private List<IObserverListener> mAllObservers;
	private int mCurrentIndex = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initData();
		initView();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.text_add:

			DemoTextView text = new DemoTextView(getApplicationContext());
			text.setBackgroundColor(Color.BLUE);
			text.setContent("This TextView is " + ++mCurrentIndex);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, 300);
			addObserver(text);
			mllObserverContent.addView(text, params);

			break;
		case R.id.text_del:
			if (mllObserverContent.getChildCount() > 0) {
				removerObserver((IObserverListener) mllObserverContent
						.getChildAt(mCurrentIndex));
				mllObserverContent.removeViewAt(mCurrentIndex--);
			}

			break;
		case R.id.text_update_one:

			updateObserver(mCurrentIndex);
			break;
		case R.id.text_update_all:

			updateAllObserver();
			break;

		}
	}

	private void initData() {

		mAllObservers = new ArrayList<IObserverListener>();
	}

	private void initView() {

		mllObserverContent = (LinearLayout) findViewById(R.id.ll_allobsever_content);
		mTextAdd = (TextView) findViewById(R.id.text_add);
		mTextAdd.setOnClickListener(this);
		mTextDel = (TextView) findViewById(R.id.text_del);
		mTextDel.setOnClickListener(this);
		mTextUpdateOne = (TextView) findViewById(R.id.text_update_one);
		mTextUpdateOne.setOnClickListener(this);
		mTextUpdateAll = (TextView) findViewById(R.id.text_update_all);
		mTextUpdateAll.setOnClickListener(this);
	}

	/**
	 * 增加一个观察者
	 */
	private void addObserver(IObserverListener listener) {

		mAllObservers.add(listener);
	}

	/**
	 * 删除一个观察者
	 */
	private void removerObserver(IObserverListener listener) {

		mAllObservers.remove(listener);
	}

	private void removerAllObserver() {

		mAllObservers.clear();
		mllObserverContent.removeAllViews();
	}

	private void updateObserver(int index) {

		IObserverListener observer = mAllObservers.get(index);
		observer.update();

	}

	private void updateAllObserver() {

		for (IObserverListener iterable_element : mAllObservers) {

			iterable_element.update();
		}
	}

	private class DemoTextView extends TextView implements IObserverListener {

		private String text = "";

		public DemoTextView(Context context) {
			// TODO Auto-generated constructor stub

			super(context);
		}

		public DemoTextView(Context context, AttributeSet attrset) {
			// TODO Auto-generated constructor stub

			super(context, attrset);
		}

		public void setContent(String text) {

			this.text = text;
		}

		@Override
		public void update() {
			// TODO Auto-generated method stub
			this.setText(this.text);
		}

	}

}
