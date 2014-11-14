package com.cache.tontonpalmis.profil;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cache.tontonpalmis.MainActivity;
import com.cache.tontonpalmis.R;
import com.cache.tontonpalmis.clientweb.TontonPalmisRestClient;
import com.cache.tontonpalmis.db.DatabaseManager;
import com.cache.tontonpalmis.service.SMS;
import com.cache.tontonpalmis.service.SmsBroadCastManager;
import com.cache.tontonpalmis.util.Util;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TestSimActivity extends Activity {
	private SMS sms;
	private String msg;
	static EditText tel;
	static String simId;
	private static SmsBroadCastManager manager;
	private static TestSimActivity testSimActivity;
	static String telnumber;
	static String operator;
	static Profil profil;
	static JSONObject response = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_sim);
		try{
			Button btntestSim = (Button)findViewById(R.id.btntestSim);
			tel = (EditText)findViewById(R.id.ediNom);
			testSimActivity = this;
			manager = new SmsBroadCastManager(this);
			manager.startService();
			btntestSim.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
//					Intent i = new Intent(getApplicationContext(),ProfilActivity.class);
//					startActivity(i);
					phoneNumber();
					if(telnumber==null || telnumber.compareTo("")==0){
						//telnumber = "+50938662809";
						msg = "Test SIM";
						sms = new SMS("+509"+tel.getText().toString().trim(),msg);
			            if(sms.isNumberOK()){
			            	Toast.makeText(getApplicationContext(), "Message sending process", Toast.LENGTH_LONG).show();
			            	if(sms.sendSMSMessage(sms,TestSimActivity.this)){
			            	    Toast.makeText(getApplicationContext(), "Message sent", Toast.LENGTH_LONG).show();
			            	}        
			            }else{
			            	Toast.makeText(getApplicationContext(), "Invalid Number or empty field", Toast.LENGTH_LONG).show();
			            }
						
						/*manager.stopService();
						Intent intent = new Intent(testSimActivity.getApplicationContext(),ProfilActivity.class);
						intent.putExtra("tel", telnumber);
						intent.putExtra("operator", operator);
						intent.putExtra("simId", simId);
						testSimActivity.startActivity(intent);*/
						
					}else{
						telnumber = "+509"+Util.phone(telnumber);
						manager.stopService();
						/*Intent intent = new Intent(testSimActivity.getApplicationContext(),ProfilActivity.class);
						intent.putExtra("tel", telnumber);
						intent.putExtra("operator", operator);
						intent.putExtra("simId", simId);
						testSimActivity.startActivity(intent);*/
						getProfil(telnumber);
					}
					
		        }	
			});
		}catch(Exception ex){
			Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
		}
		
	}
	
	public void phoneNumber(){
		//Getting the Object of TelephonyManager 
        TelephonyManager tManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        //Getting the SIM card ID
        simId=tManager.getSimSerialNumber();
        
        //Getting Phone Number
        telnumber = tManager.getLine1Number();
        operator = tManager.getNetworkOperatorName();
        Log.i("Operator/Tel", operator+"/"+telnumber);
        //Getting IMEI Number of Devide
        String Imei=tManager.getDeviceId();
	}
	
	public static void readSMS(SMS sms){
		if(sms.isSmsOK("+509"+tel.getText().toString())){
			manager.stopService();
			 
			telnumber = "+509"+tel.getText().toString();
			
			/*Intent intent = new Intent(testSimActivity.getApplicationContext(),ProfilActivity.class);
			intent.putExtra("tel", sms.getnumber());
			intent.putExtra("operator", operator);
			intent.putExtra("simId", simId);
			testSimActivity.startActivity(intent);*/
			getProfil(sms.getnumber());
		}else{
			
		}
	}

	
	public static void getProfil(String numero){
		RequestParams params = new RequestParams();
		params.put("numero", numero);
		Log.i("NUMERO", numero);
		Log.i("processing...", "existing profil");
		TontonPalmisRestClient.get("getProfilByTel", params,new JsonHttpResponseHandler() {

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				// TODO Auto-generated method stub
				Toast.makeText(testSimActivity.getApplicationContext(), "Message : "+responseString, 100000).show();
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
				//testSimActivity.response = response;
				parsingResponse(response);
				Log.i("response", response.toString());
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				/*if(testSimActivity.response!=null)
					parsingResponse(testSimActivity.response);
				else
					Log.i("response null", "........");*/
			}
		});
	}
	
	private static void parsingResponse(JSONObject response){
		Log.i("parsing data", ".........");
		if(!response.isNull("success")){
			try {
				if(response.getBoolean("success")){
					if(!response.isNull("data")){
						JSONObject data = response.getJSONObject("data");
						profil = Profil.parseJson(data);
						Intent i = new Intent(testSimActivity.getApplicationContext(),MainActivity.class);
						i.putExtra("profil", profil);
						DatabaseManager db = new DatabaseManager(testSimActivity.getApplicationContext());
						db.insertSerial_number(simId, profil.getTel());
						db.insertProfil(profil);
						testSimActivity.startActivity(i);
					}
				}else{
					Intent intent = new Intent(testSimActivity.getApplicationContext(),ProfilActivity.class);
					intent.putExtra("tel", telnumber);
					intent.putExtra("operator", operator);
					intent.putExtra("simId", simId);
					testSimActivity.startActivity(intent);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
}
