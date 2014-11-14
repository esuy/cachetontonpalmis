package com.cache.tontonpalmis;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cache.tontonpalmis.clientweb.TontonPalmisRestClient;
import com.cache.tontonpalmis.db.DatabaseManager;
import com.cache.tontonpalmis.profil.Profil;
import com.cache.tontonpalmis.profil.TestSimActivity;
import com.cache.tontonpalmis.util.Util;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class IntroActivity extends Activity {
	
	Profil profil;
	JSONObject response = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intro);
		DatabaseManager db = new DatabaseManager(this);
		String numero = db.getNumero(Util.getSerial(this));
		if(numero.compareTo("")!=0){
			getProfil(numero);
		}else{
			Intent i = new Intent(getApplicationContext(),TestSimActivity.class);
			startActivity(i);
		}
	}
	
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		DatabaseManager db = new DatabaseManager(this);
		String numero = db.getNumero(Util.getSerial(this));
		if(numero.compareTo("")!=0){
			getProfil(numero);
		}else{
			Intent i = new Intent(getApplicationContext(),TestSimActivity.class);
			startActivity(i);
		}
	}



	
	
	public void getProfil(String numero){
		RequestParams params = new RequestParams();
		params.put("numero", numero);
		Log.i("NUMERO", numero);
		TontonPalmisRestClient.get("getProfilByTel", params,new JsonHttpResponseHandler() {

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				// TODO Auto-generated method stub
				Toast.makeText(IntroActivity.this, "Message : "+responseString, 100000).show();
				//super.onFailure(statusCode, headers, responseString, throwable);
				
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONArray errorResponse) {
				// TODO Auto-generated method stub
				super.onFailure(statusCode, headers, throwable, errorResponse);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, response);
				IntroActivity.this.response = response;
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				if(response!=null)
					parsingResponse(response);
			}
			
			
			
		});
	}
	
	private void parsingResponse(JSONObject response){
		if(!response.isNull("success")){
			try {
				if(response.getBoolean("success")){
					if(!response.isNull("data")){
						JSONObject data = response.getJSONObject("data");
						profil = Profil.parseJson(data);
						Intent i = new Intent(getApplicationContext(),MainActivity.class);
						i.putExtra("profil", profil);
						new DatabaseManager(this).insertProfil(profil);
						startActivity(i);
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
}
