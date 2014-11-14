package com.cache.tontonpalmis.clientweb;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

public class TontonPalmisRestClient {
	private static final String BASE_URL = "http://grl.alwaysdata.net/ctpApi/APIv1/";

	  private static AsyncHttpClient client = new AsyncHttpClient();

	  public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		  Log.i("Request", getAbsoluteUrl(url));
		  client.get(getAbsoluteUrl(url), params, responseHandler);
		  
	  }

	  public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		  Log.i("Request", getAbsoluteUrl(url));
		  client.post(getAbsoluteUrl(url), params, responseHandler);
	  }

	  private static String getAbsoluteUrl(String relativeUrl) {
	      return BASE_URL + relativeUrl;
	  }
}
