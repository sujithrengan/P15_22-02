package com.example.rep;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;

public final class CommonUtilities {

	static final String SERVER_URL = "http://10.0.0.5/xampp/gcm_server_php/register.php";
	static final String SENDER_ID = "1022857879053";
	static final String TAG = "My Google Cloud Messaging";
	static final String DISPLAY_MESSAGE_ACTION = "com.example.rep.DISPLAY_MESSAGE";
	static final String EXTRA_MESSAGE = "message";
	public static SharedPreferences sp;
	
	public static int count;
	public static int tcount;
	
	
	static void displayMessage(Context context, String message) {
		Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
		intent.putExtra(EXTRA_MESSAGE, message);
		context.sendBroadcast(intent);
	}
}
//1022857879053