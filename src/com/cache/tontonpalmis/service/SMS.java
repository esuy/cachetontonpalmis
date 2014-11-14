package com.cache.tontonpalmis.service;

import com.cache.tontonpalmis.R;
import com.cache.tontonpalmis.profil.TestSimActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SMS {

	private String number;
	private String message;
	private TestSimActivity activity;
	private boolean sent = false;

	public SMS(String number, String message) {
		this.number = number;
		this.message = message;
	}

	public String getnumber() {
		return number;
	}

	public void setnumber(String number) {
		this.number = number;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isNumberOK() {
		if (number.startsWith("+509") && number.length() == 12) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isSmsOK(String userNumber) {
		if (number.equals(userNumber)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean sendSMSMessage(SMS sms, final TestSimActivity activity) {

		//if (!readInbox(sms, activity)) {
			String SENT = "SMS_SENT";
			String DELIVERED = "SMS_DELIVERED";
			final String messageOK = "Mesaj ou a ale";
			final String messageFAILURE = "Mesaj ou a pa ale, verifye si ou gen kob sou kont ou.";

			this.activity = activity;

			PendingIntent sentPI = PendingIntent.getBroadcast(activity, 0,
					new Intent(SENT), 0);

			PendingIntent deliveredPI = PendingIntent.getBroadcast(activity, 0,
					new Intent(DELIVERED), 0);

			// ---when the SMS has been sent---
			final String string = "deprecation";
			activity.registerReceiver(new BroadcastReceiver() {

				@Override
				public void onReceive(Context arg0, Intent arg1) {
					switch (getResultCode()) {
					case Activity.RESULT_OK:
						Toast.makeText(activity.getApplicationContext(),
								messageOK, Toast.LENGTH_LONG).show();
						sent = true;
						break;
					case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
						showDialog(messageFAILURE);
						// Toast.makeText(activity.getApplicationContext(),
						// messageFAILURE,
						// Toast.LENGTH_LONG).show();
						sent = false;
						break;
					case SmsManager.RESULT_ERROR_NO_SERVICE:
						Toast.makeText(activity.getApplicationContext(),
								"No service", Toast.LENGTH_LONG).show();
						break;
					case SmsManager.RESULT_ERROR_NULL_PDU:
						Toast.makeText(activity.getApplicationContext(),
								"Null PDU", Toast.LENGTH_LONG).show();
						break;
					case SmsManager.RESULT_ERROR_RADIO_OFF:
						Toast.makeText(activity.getApplicationContext(),
								"Radio off", Toast.LENGTH_LONG).show();
						break;

					}
				}
			}, new IntentFilter(SENT));

			// ---when the SMS has been delivered---
			activity.registerReceiver(new BroadcastReceiver() {
				@Override
				public void onReceive(Context arg0, Intent arg1) {
					switch (getResultCode()) {
					case Activity.RESULT_OK:
						Toast.makeText(activity.getApplicationContext(),
								messageOK, Toast.LENGTH_LONG).show();
						sent = true;
						break;
					case Activity.RESULT_CANCELED:
						Toast.makeText(activity.getApplicationContext(),
								messageFAILURE, Toast.LENGTH_LONG).show();
						sent = false;
						break;
					}
				}
			}, new IntentFilter(DELIVERED));

			SmsManager smsManager = SmsManager.getDefault();
			smsManager.sendTextMessage(sms.getnumber(), null, sms.getMessage(),
					sentPI, deliveredPI);
		//}
		return sent;

	}

	public void showDialog(String msg) {

		// final Dialog dialog = new Dialog(activity.getApplicationContext());
		// dialog.setContentView(R.layout.dialog);
		// dialog.setTitle("Title...");
		//
		// // set the custom dialog components - text, image and button
		// TextView text = (TextView) dialog.findViewById(R.id.message5);
		// text.setText(msg);
		// ImageView image = (ImageView) dialog.findViewById(R.id.image5);
		// image.setImageResource(R.drawable.ic_launcher);
		//
		// Button dialogButton = (Button)
		// dialog.findViewById(R.id.dialogButtonOK);
		// // if button is clicked, close the custom dialog
		// dialogButton.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// dialog.dismiss();
		// }
		// });
		//
		// dialog.show();

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				this.activity);

		// set title
		alertDialogBuilder.setTitle("CASH Tonton Palmis");

		// set dialog message
		alertDialogBuilder.setMessage(msg).setCancelable(false)
				.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// if this button is clicked, just close
						// the dialog box and do nothing
						dialog.cancel();
						dialog.dismiss();
					}
				});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
	}

	@Override
	public String toString() {
		return "SMS [number=" + number + ", message=" + message + "]";
	}

	private boolean readInbox(SMS sms, final TestSimActivity activity) {
		Uri uriSMSURI = Uri.parse("content://sms/inbox");
		Cursor cur = activity.getContentResolver().query(uriSMSURI, null, null,
				null, null);
		int i = 0;
		while (cur.moveToNext() && i <= 5) {

			if (cur.getString(2).equals(sms.getnumber())
					&& cur.getString(12).equals(sms.getMessage())) {
				return true;
			}
			i++;
		}
		return false;
	}

}
