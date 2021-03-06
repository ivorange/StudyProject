package com.example.pubuloadbitmapdemo;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoadBitmapService extends AbstractLoadService {

	private static List<PersonBean> mAllPerson = new ArrayList<>();
	private static LoadBitmapService mLoadBitmapInstance;

	public LoadBitmapService() {
		// TODO Auto-generated constructor stub

	}

	public static LoadBitmapService getInstance() {

		if (mLoadBitmapInstance == null) {

			mLoadBitmapInstance = new LoadBitmapService();
		}

		return mLoadBitmapInstance;
	}

	@Override
	public void dealWidthRequset(JSONObject json) {
		// TODO Auto-generated method stub
		super.dealWidthRequset(json);

		try {
			JSONArray jsonArray = json.getJSONArray("people");

			for (int i = 0; i < jsonArray.length(); i++) {

				JSONObject jsonObject = jsonArray.getJSONObject(i);
				PersonBean person = new PersonBean();
				person.setmEmail(jsonObject.optString("email").toString());
				person.setmFirstName(jsonObject.optString("firstName"));
				person.setmLastName(jsonObject.optString("lastName"));
				addData(person);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private synchronized void addData(PersonBean bean) {

		mAllPerson.add(bean);
	}

	public List<PersonBean> getAllPersonBean() {

		return mAllPerson;
	}
}
