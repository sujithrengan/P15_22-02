package com.example.rep;

import java.io.IOException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;


import com.example.rep.APost;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import static com.example.rep.CommonUtilities.SENDER_ID;
import static com.example.rep.CommonUtilities.SERVER_URL;
import static com.example.rep.CommonUtilities.TAG;
import static com.example.rep.CommonUtilities.displayMessage;

public final class ServerUtilities {

	public static void register(final Context context, final String regId) {

		String serverUrl = SERVER_URL;
		Map<String, String> params = new HashMap<String, String>();
		params.put("regId", regId);
		
		try {
			post(serverUrl, params);
		} catch (IOException e) {
			e.printStackTrace();
		}

		//String message = context.getString(R.string.server_registered);
		//CommonUtilities.displayMessage(context, message);
		return;
	}

	public static void unregister(final Context context, final String regId) {

		String serverUrl = SERVER_URL + "/unregister";
		Map<String, String> params = new HashMap<String, String>();
		params.put("regId", regId);
		try {
			post(serverUrl, params);
		} catch (IOException e) {
			e.printStackTrace();
		}

		//String message = context.getString(R.string.server_unregistered);
		//CommonUtilities.displayMessage(context, message);
	}

	private static void post(String endpoint, Map<String, String> params)
			throws IOException {

		StringBuilder bodyBuilder = new StringBuilder();
		Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, String> param = iterator.next();
			bodyBuilder.append(param.getKey()).append('=')
					.append(param.getValue());
			if (iterator.hasNext()) {
				bodyBuilder.append('&');
			}
		}
		String body = bodyBuilder.toString();

		new APost().execute(body, endpoint);

	}

}
