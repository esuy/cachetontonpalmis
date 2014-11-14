package com.cache.tontonpalmis.profil;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.cache.tontonpalmis.IntroActivity;
import com.cache.tontonpalmis.MainActivity;
import com.cache.tontonpalmis.R;
import com.cache.tontonpalmis.clientweb.ProfilWebClient;
import com.cache.tontonpalmis.clientweb.TontonPalmisRestClient;
import com.cache.tontonpalmis.db.DatabaseManager;
import com.cache.tontonpalmis.util.STATUS;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class ProfilActivity extends Activity {
	
	
	String tel;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profil);
		
		
	     tel = this.getIntent().getStringExtra("tel");
	     final String operator = this.getIntent().getStringExtra("operator");
			final String simId = this.getIntent().getStringExtra("simId");
			final EditText editNom = (EditText)findViewById(R.id.ediNom);
			final EditText editZone = (EditText)findViewById(R.id.editZone);
			final EditText editPin = (EditText)findViewById(R.id.editPin);
			final RadioButton rbtn_homme = (RadioButton)findViewById(R.id.rbtn_homme);
			final RadioButton rbtn_femme = (RadioButton)findViewById(R.id.rbtn_femme);
			Button btnCreaProfil = (Button)findViewById(R.id.creerProfil);
			final DatePicker datePicker1 = (DatePicker)findViewById(R.id.datePicker1);
			btnCreaProfil.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					Profil profil = new Profil();
					profil.setName(editNom.getText().toString());
					profil.setTel(tel);
					profil.setZone(editZone.getText().toString());
					profil.setPassword("hfgjsd");
					profil.setOperator(operator);
					profil.setPin(editPin.getText().toString());
					profil.setStatus("online");
					if(rbtn_homme.isChecked())
						profil.setSexe(rbtn_homme.getText().toString());
					if(rbtn_femme.isChecked())
						profil.setSexe(rbtn_femme.getText().toString());
					profil.setDate_naiss(datePicker1.getYear()+"-"+(datePicker1.getMonth()+1)+"-"+datePicker1.getDayOfMonth());
					ProfilWebClient.postNewProfil(profil,simId,ProfilActivity.this);
//					Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//					startActivity(intent);
				}
			});
	}
	
	
	
}
