package com.cache.tontonpalmis.service;

import com.cache.tontonpalmis.profil.TestSimActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class TextMessageReceiver extends BroadcastReceiver{
	
	private String from;
	private String message;
	private SMS smsform;
	
	public void onReceive(Context context, Intent intent)
	{
		Bundle bundle=intent.getExtras();
		
		Object[] messages=(Object[])bundle.get("pdus");
		SmsMessage[] sms=new SmsMessage[messages.length];
		
		Log.e("SMS","Read SMS");
		
		for(int n=0;n<messages.length;n++){
			sms[n]=SmsMessage.createFromPdu((byte[]) messages[n]);
		}
		
		for(SmsMessage msg:sms){
			from = msg.getOriginatingAddress();
			message = msg.getMessageBody();			
		}
		Toast.makeText(context, "Message : "+message, 100000).show();
		smsform = new SMS(from,message);
		TestSimActivity.readSMS(smsform);
	}
}
