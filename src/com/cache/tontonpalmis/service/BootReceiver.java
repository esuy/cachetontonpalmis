package com.cache.tontonpalmis.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(final Context context, final Intent bootintent) {
		Intent mServiceIntent = new Intent();
		mServiceIntent.setAction("com.cache.tontonpalmis.service.DataReceiver");
		context.startService(mServiceIntent);
	}
}
