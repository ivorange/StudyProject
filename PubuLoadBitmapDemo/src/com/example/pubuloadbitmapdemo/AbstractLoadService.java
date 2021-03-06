package com.example.pubuloadbitmapdemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class AbstractLoadService {

	private String mRequsetUrl = "";
	private HttpClient mHttpClient;

	public String setRequsetContent() throws JSONException {

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("USER", "IVORANGE");
		jsonObject.put("TIME", Calendar.getInstance().getTime());

		return jsonObject.toString();
	}

	public HttpClient getHttpClient() {

		if (mHttpClient != null) {
			return mHttpClient;
		}
		// HttpParams httpParams = new BasicHttpParams();
		// HttpConnectionParams.setConnectionTimeout(httpParams, 5000);
		// HttpConnectionParams.setSoTimeout(httpParams, 10000);
		mHttpClient = new DefaultHttpClient();
		return mHttpClient;
	}

	public void excute() {

		String content = requsetByUrlConnection();

		try {
			JSONObject jsonObject = new JSONObject(content);
			dealWidthRequset(jsonObject);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void isRequsetSuccess() {

	}

	public void dealWidthRequset(JSONObject json) {

	}

	public void setRequsetUrl(String url) {

		this.mRequsetUrl = url;
	}

	private String requsetContentByGet(String url) {

		String content = "";
		HttpGet httpGet = new HttpGet(url);
		HttpResponse httpResponse = null;
		InputStream inputStream = null;
		StringBuffer stringBuffer = new StringBuffer();
		try {
			httpResponse = new DefaultHttpClient().execute(httpGet);
			inputStream = httpResponse.getEntity().getContent();
			BufferedReader bufferReader = new BufferedReader(
					new InputStreamReader(inputStream));
			String readLine = "";
			while (null != (readLine = bufferReader.readLine())) {
				stringBuffer.append(readLine);
			}

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			try {
				inputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			content = stringBuffer.toString();
		}

		return content;
	}

	public String requsetContentByPost() {

		HttpPost httpPost = new HttpPost(this.mRequsetUrl);

		mHttpClient = getHttpClient();
		String response = "";
		// List<NameValuePair> params = new ArrayList<>();
		// params.add(new BasicNameValuePair("Name", "ivorange"));
		try {
			// httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			HttpResponse httpResponse = null;
			try {
				httpResponse = mHttpClient.execute(httpPost);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (httpResponse.getStatusLine().getStatusCode() == 200) {

				response = httpResponse.getEntity().toString();

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;

	}

	public String requsetByUrlConnection() {

		StringBuffer content = new StringBuffer();

		try {
			HttpURLConnection connection = (HttpURLConnection) new URL(
					mRequsetUrl).openConnection();
			connection.setReadTimeout(5000);
			// connection.setDoInput(true); //是否从连接中读取数据
			// connection.setDoOutput(true);//会使该请求以POST方式
			// connection.setDoInput(false); // 加了这句 再从Response 中读取时
			// 会出现java.net.ProtocolException:
			// This protocol does not support
			// input 意思是无法从 connection 中读取返回的数据因为不支持
			BufferedReader bufferReader = new BufferedReader(
					new InputStreamReader(connection.getInputStream()));
			String temp = "";
			while ((temp = bufferReader.readLine()) != null) {

				content.append(temp);
			}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return content.toString();
	}

}
