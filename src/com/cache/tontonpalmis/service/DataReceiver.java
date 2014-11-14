package com.cache.tontonpalmis.service;

import java.io.IOException;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cache.tontonpalmis.MainActivity;
import com.cache.tontonpalmis.R;
import com.cache.tontonpalmis.clientweb.TontonPalmisRestClient;
import com.cache.tontonpalmis.db.DatabaseManager;
import com.cache.tontonpalmis.profil.Profil;
import com.cache.tontonpalmis.util.Util;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class DataReceiver extends Service {
	private NotificationManager notificationManager;
	Profil profil;
	DatabaseManager db;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		db = new DatabaseManager(this);
		profil = db.getProfilByTel(db.getNumero(Util.getSerial(this)).trim());
		Log.i("tag", "service start" + db.getNumero(Util.getSerial(this)));

		new Handler().postDelayed(new Runnable() {
			public void run() {
				TontonPalmisRestClient.get("sentMessage", null,
						new JsonHttpResponseHandler() {

						});
				Log.i("tag", "test service");
			}
		}, 3000);

		// Thread qui recupere les nouvelles demandes d'amis
		new Handler().postDelayed(new Runnable() {
			public void run() {
				getnouveauContactProfil();
			}
		}, 20000);

		return super.onStartCommand(intent, flags, startId);
	}

	public void getnouveauContactProfil() {
		RequestParams params = new RequestParams();
		params.put("id", profil.getId());
		TontonPalmisRestClient.get("getnouveauContactProfil", params,
				new JsonHttpResponseHandler() {

					@Override
					public void onFailure(int statusCode, Header[] headers,
							String responseString, Throwable throwable) {
						// TODO Auto-generated method stub
						super.onFailure(statusCode, headers, responseString,
								throwable);
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							Throwable throwable, JSONArray errorResponse) {
						// TODO Auto-generated method stub
						super.onFailure(statusCode, headers, throwable,
								errorResponse);
					}

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							JSONObject response) {
						// TODO Auto-generated method stub
						super.onSuccess(statusCode, headers, response);
						Log.i("response", response.toString());
						try {
							if (response.getBoolean("success")) {
								if (!response.isNull("data")) {
									JSONArray array = response
											.getJSONArray("data");
									Profil profil = null;
									boolean test = true;
									for (int i = 0; i < array.length(); i++) {
										profil = Profil.parseJson(array
												.getJSONObject(i));
										if (test)
											test = db.isRequest(profil.getId());
										profil.setWaitting(true);
										db.insertProfil(profil);
									}
									if (!test) {
										if (array.length() > 0) {
											if (array.length() > 1) {
												showNotification(array.length()
														+ " moun vlew kom zanmi");
												playsoundNotification();
											} else {
												showNotification(profil
														.getName()
														+ " vlew kom zanmi");
												playsoundNotification();
											}

										}
									}
								}
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
	}

	private void showNotification(String msg) {
		// Set the icon, scrolling text and timestamp
		String title = msg;
		// ((msg.length() < 5) ? msg : msg.substring(0, 5)+ "...");
		@SuppressWarnings("deprecation")
		Notification notification = new Notification(R.drawable.logo, title,
				System.currentTimeMillis());
		Intent i = new Intent(this, MainActivity.class);

		// The PendingIntent to launch our activity if the user selects this
		// notification
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, i, 0);
		notification
				.setLatestEventInfo(this, "Nouvo zanmi", msg, contentIntent);
		notificationManager.notify((msg).hashCode(), notification);
	}

	private void playsoundNotification() {
		Uri defaultRingtoneUri = RingtoneManager
				.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

		MediaPlayer mediaPlayer = new MediaPlayer();

		try {
			mediaPlayer.setDataSource(this, defaultRingtoneUri);
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_NOTIFICATION);
			mediaPlayer.prepare();
			mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

				@Override
				public void onCompletion(MediaPlayer mp) {
					mp.release();
				}
			});
			mediaPlayer.start();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
