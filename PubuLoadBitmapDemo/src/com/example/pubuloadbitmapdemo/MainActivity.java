package com.example.pubuloadbitmapdemo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;

public class MainActivity extends Activity {

	private final static String URL = "http://myweb-10021579.cos.myqcloud.com/myphone/myJson.txt";
	private LoadBitmapService mLoadBitmapService;
	private LoadTask loadTask;
	private static List<PersonBean> mAllPerson = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initData();

	}

	private void initData() {
		// TODO Auto-generated method stub
		loadTask = new LoadTask();
		loadTask.execute(URL);
	}

	private void setData() {

		mAllPerson = LoadBitmapService.getInstance().getAllPersonBean();
		for (int i = 0; i < mAllPerson.size(); i++) {

			PersonBean personBean = mAllPerson.get(i);
			System.out.println("the first name is :"
					+ personBean.getmFirstName());
			System.out
					.println("the last name is :" + personBean.getmLastName());
			System.out.println("the email is :" + personBean.getmEmail());
			System.out.println("-------------------------------");
		}
	}

	public class LoadTask extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			LoadBitmapService.getInstance().setRequsetUrl(params[0]);
			LoadBitmapService.getInstance().excute();

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			setData();
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

	}

}
