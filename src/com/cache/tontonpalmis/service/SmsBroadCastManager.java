package com.cache.tontonpalmis.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.widget.Toast;

public class SmsBroadCastManager {
	
	private ComponentName receiver;
	private PackageManager pm;
	private Context context;
	
	public SmsBroadCastManager(Context context){
		this.context = context;
	}
	
	public void startService(){
		receiver = new ComponentName(context, TextMessageReceiver.class);
		pm = context.getPackageManager();
		pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
		Toast.makeText(context, "Start sms service", Toast.LENGTH_LONG).show();
	}
	
	public void stopService(){
		pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
		Toast.makeText(context, "Stop sms service", Toast.LENGTH_LONG).show();
	}

}
