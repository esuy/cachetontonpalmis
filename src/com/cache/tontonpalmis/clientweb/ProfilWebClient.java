package com.cache.tontonpalmis.clientweb;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.cache.tontonpalmis.MainActivity;
import com.cache.tontonpalmis.db.DatabaseManager;
import com.cache.tontonpalmis.profil.Profil;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class ProfilWebClient {
	public static void postNewProfil(final Profil profil,final String serial,final Context context){
		RequestParams params = new RequestParams();
		params.put("numero", profil.getTel());
		params.put("nom", profil.getName());
		params.put("lieu", profil.getZone());
		params.put("operateur", profil.getOperator());
		params.put("password", profil.getPassword());
		params.put("status", profil.getStatus());
		params.put("pin", profil.getPin());
		params.put("sexe", profil.getSexe());
		params.put("age", profil.getDate_naiss());
		
		Log.i("numero", profil.getTel()+"...");
		Log.i("nom", profil.getName()+"...");
		Log.i("lieu", profil.getZone()+"...");
		Log.i("operateur", profil.getOperator()+"...");
		Log.i("password", profil.getPassword()+"...");
		Log.i("staus", profil.getStatus()+"...");
		Log.i("pin", profil.getPin()+"...");
		Log.i("sexe", profil.getSexe());
		Log.i("Naissance", profil.getDate_naiss());
		TontonPalmisRestClient.post("getCreerProfil", params, new JsonHttpResponseHandler() {

			
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				// TODO Auto-generated method stub
				super.onFailure(statusCode, headers, responseString, throwable);
				Log.i("error", responseString);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse) {
				// TODO Auto-generated method stub
				super.onFailure(statusCode, headers, throwable, errorResponse);
				Toast.makeText(context, "faillure response "+statusCode,
						   Toast.LENGTH_LONG).show();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, response);
				Toast.makeText(context, "Succes response "+response.toString(),
				   Toast.LENGTH_LONG).show();
				Log.i("success", response.toString());
				DatabaseManager db =new DatabaseManager(context);
				db.insertSerial_number(serial, profil.getTel());
				db.insertProfil(profil);
				Intent intent = new Intent(context,MainActivity.class);
				context.startActivity(intent);
			}
			
			
            
        });
	}
	
}
