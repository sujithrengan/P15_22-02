package com.example.rep;

import static com.example.rep.CommonUtilities.EXTRA_MESSAGE;

import com.example.rep.R;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;

//import android.app.Notification;

public class Myreceiver extends WakefulBroadcastReceiver {

	static int numMessages = 0;
	static public String Notiftext;
	
	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	@Override
	public void onReceive(Context context, Intent intent) {

		numMessages++;

		int notificationID = 100;

		String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
		Notiftext=newMessage;

		if (newMessage != null) {
			NotificationCompat.Builder mBuilder =

			new NotificationCompat.Builder(context).setSmallIcon(R.drawable.b)
					.setContentTitle("Pragyan 2015")
					.setContentText(newMessage)
					.setDefaults(Notification.DEFAULT_ALL)
                    .setLights(Color.GREEN, 500, 500);
			
			//mBuilder.setLights(Color.BLUE, 500, 500);
			//long[] pattern = {500,500,500,500,500,500,500,500,500};
			//mBuilder.setVibrate(pattern);
			CommonUtilities.sp = context.getSharedPreferences("p15_history", Context.MODE_PRIVATE);
			CommonUtilities.count=CommonUtilities.sp.getInt("count",0);
			CommonUtilities.tcount=CommonUtilities.sp.getInt("tcount",0);
			CommonUtilities.count++;
			CommonUtilities.tcount++;
			Editor et=CommonUtilities.sp.edit();
			et.putString(String.valueOf(CommonUtilities.count),Myreceiver.Notiftext);
			et.putInt("count",CommonUtilities.count);
			et.putInt("tcount",CommonUtilities.tcount);
			et.commit();
			
			NotificationCompat.InboxStyle inboxStyle;

			inboxStyle = new NotificationCompat.InboxStyle();
			String[] events = new String[6];

			events = newMessage.split("\n");
			inboxStyle.setBigContentTitle("Event details:");

			for (int i = 0; i < events.length; i++) {

				inboxStyle.addLine(events[i]);
			}
			mBuilder.setStyle(inboxStyle);

			Intent resultIntent = new Intent(context, Notify.class);

			Bundle b = new Bundle();
			b.putString("key", newMessage);
			resultIntent.putExtras(b);

			// The stack builder object will contain an artificial back stack
			// for the
			// started Activity.
			// This ensures that navigating backward from the Activity leads out
			// of
			// your application to the Home screen.
			TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
			// Adds the back stack for the Intent (but not the Intent itself)
			stackBuilder.addParentStack(Notify.class);
			// Adds the Intent that starts the Activity to the top of the stack

			stackBuilder.addNextIntent(resultIntent);
			PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
					0, PendingIntent.FLAG_UPDATE_CURRENT);
			mBuilder.setContentIntent(resultPendingIntent);

			NotificationManager mNotificationManager = (NotificationManager) context
					.getSystemService(Context.NOTIFICATION_SERVICE);

			// Start of a loop that processes data and then notifies the user

			mBuilder.setContentText(newMessage).setNumber(numMessages);

			// Sets an ID for the notification, so it can be updated

			// mId allows you to update the notification later on.
			mBuilder.setAutoCancel(true);
			mNotificationManager.notify(notificationID, mBuilder.build());

		}
	}
}
