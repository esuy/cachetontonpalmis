package com.cache.tontonpalmis.util;

import android.content.Context;
import android.telephony.TelephonyManager;

public class Util {
	public static String phone(String sequence){
		sequence = new StringBuffer(sequence).reverse().toString();
		sequence = sequence.substring(0, 8);
		sequence = new StringBuffer(sequence).reverse().toString();
		System.out.println(sequence);
		return sequence;
	}
	public static String getSerial(Context context){
		String serial = "";
		//Getting the Object of TelephonyManager 
        TelephonyManager tManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        //Getting the SIM card ID
        serial = tManager.getSimSerialNumber();
        return serial;
	}
}
