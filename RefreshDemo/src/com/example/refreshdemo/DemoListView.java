package com.example.refreshdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

public class DemoListView extends ListView implements OnScrollListener {

	private View mHeadView;
	private int mHeanHeight;
	private boolean mRemark = false;
	private int mOriginalY;
	private int mFirstItemIndex;
	private int mScrollState;
	private final int NONE = 0;
	private final int PULL = 1;
	private final int RELEASE = 2;
	private final int REFRESHING = 3;
	private int mCurrentState;
	private TextView mTipTextView;

	private IrefreshDataListener mRefreshListener;

	public DemoListView(Context context) {
		// TODO Auto-generated constructor stub
		super(context);
		initData();
		initView(context);
	}

	public DemoListView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		initData();
		initView(context);
	}

	public DemoListView(Context context, AttributeSet attributeSet, int style) {
		// TODO Auto-generated constructor stub
		super(context, attributeSet, style);
		initData();
		initView(context);
	}

	public void setListener(IrefreshDataListener listen) {

		this.mRefreshListener = listen;
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		mFirstItemIndex = firstVisibleItem;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		mScrollState = scrollState;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub

		switch (ev.getAction()) {

		case MotionEvent.ACTION_DOWN:
			if (mFirstItemIndex == 0) {
				mOriginalY = (int) ev.getY();
				mRemark = true;
			}
			break;
		case MotionEvent.ACTION_MOVE:

			onMove(ev);
			break;

		case MotionEvent.ACTION_UP:

			if (mCurrentState == RELEASE) {
				
				mRefreshListener.refresh();
			}
			
			
			break;

		}

		return super.onTouchEvent(ev);
	}

	private void onMove(MotionEvent ev) {
		// TODO Auto-generated method stub

		if (!mRemark) {
			return;
		}
		int tempLength = (int) ev.getY() - mOriginalY;
		int topPadding = tempLength - mHeanHeight;

		switch (mCurrentState) {

		case NONE:

			if (tempLength > 0) {
				pullRefreshTextView();
				mCurrentState = PULL;
			}

			break;
		case PULL:
			
			measurePadding(topPadding);
			if (tempLength > mHeanHeight + 30 && mScrollState == SCROLL_STATE_TOUCH_SCROLL) {
				releaseRefreshTextView();
				mCurrentState = RELEASE;

			}
			break;
		case RELEASE:
			if (tempLength <= mHeanHeight * 2) {
				measurePadding(topPadding);
			}
			
			if (tempLength < mHeanHeight + 30) {
				pullRefreshTextView();
				mCurrentState = PULL;

			} else if (tempLength <= 0) {

				mCurrentState = NONE;
				mRemark = false;
			}

			break;
		}

	}

	private void initView(Context context) {

		LayoutInflater inflater = LayoutInflater.from(context);
		mHeadView = inflater.inflate(R.layout.footer_content_layout, null);
		mTipTextView = (TextView) mHeadView.findViewById(R.id.text_footer_content);
		
		

	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		measureLayout(mHeadView);
		
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
		mHeanHeight = mHeadView.getMeasuredHeight();
		measurePadding(-mHeanHeight);
		this.addHeaderView(mHeadView);
		this.setOnScrollListener(this);
	}

	private void initData() {

		mHeanHeight = 0;
		mOriginalY = 0;

	}

	private void measureLayout(View view) {

		ViewGroup.LayoutParams params = view.getLayoutParams();

		if (params == null) {

			params = new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

		}

		int width = ViewGroup.getChildMeasureSpec(0, 0, params.width);
		int height;
		height = params.height;
		if (height > 0) {

			height = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);

		} else {

			height = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		}

		view.measure(width, height);

	}

	private void measurePadding(int padding) {
		// TODO Auto-generated method stub
		mHeadView.setPadding(mHeadView.getPaddingLeft(), padding, mHeadView.getPaddingRight(),
				mHeadView.getPaddingBottom());
		mHeadView.invalidate();
	}

	private void releaseRefreshTextView() {
		// TODO Auto-generated method stub

		mTipTextView.setText("释放刷新!!!");
	}

	private void pullRefreshTextView() {

		mTipTextView.setText("下拉刷新!!!");
	}
	
	public void loadComplete(){
		
		mCurrentState = NONE;
		mRemark = false;
		measurePadding(-mHeanHeight);
		
	}

}
