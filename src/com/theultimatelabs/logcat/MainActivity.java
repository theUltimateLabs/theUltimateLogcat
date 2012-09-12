package com.theultimatelabs.logcat;

import android.app.*;
import android.content.*;
import android.os.*;
import android.util.*;
import android.widget.*;
import java.io.*;
import java.util.*;

import java.lang.Process;


public class MainActivity extends Activity
{

	private Notification mNotification2,mNotification;

	private Notification.Builder mBuilder;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager notificationManager = (NotificationManager) getSystemService(ns);
//		NotificationManager notificationManager = getNotificationManager();
//		Notification.Builder notiBuilder = new Notification.Builder(this);
//		notiBuilder.setContentInfo("TITLE");
//		notiBuilder.

//		new Notification.InboxStyle(notiBuilder).build();

//		Notification noti = new Notification.InboxStyle(
//			new Notification.Builder()
//			.setContentTitle("title“)
//			.setContentText("content")
//			.addLine(str1)
//			.addLine(str2)
//			.setContentTitle("")
//			.setSummaryText("+3 more")
//			.build();

		Notification.Builder build = new Notification.Builder(this)
            .setContentTitle("New mail from me")
            .setContentText("subject")
            .setTicker("New email with photo")
            .addAction(
			android.R.drawable.ic_btn_speak_now,
			"Speak!",
			PendingIntent.getActivity(getApplicationContext(), 0,
									  getIntent(), 0, null))
            .addAction(
			android.R.drawable.ic_dialog_map,
			"Maps",
			PendingIntent.getActivity(getApplicationContext(), 0,
									  getIntent(), 0, null))
            .addAction(
			android.R.drawable.ic_dialog_info,
			"Info",
			PendingIntent.getActivity(getApplicationContext(), 0,
									  getIntent(), 0, null));

		mNotification = new Notification.InboxStyle(build).build();

		Intent notificationIntent = new Intent(this, MainActivity.class);

		notificationManager.notify(1, mNotification);

		sendInboxStyleNotification();

		mNotification2 = new Notification();
		RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.notification);
		//contentView.setImageViewResource(R.id.image, R.drawable.notification_image);
		contentView.setTextViewText(R.id.title, "Custom notification");
		contentView.setTextViewText(R.id.text, "This is a custom layout");
		mNotification2.contentView = contentView;

		getNotificationManager().notify(3, mNotification2);

    }

	public void sendInboxStyleNotification()
	{
		PendingIntent pi = getPendingIntent();
		mBuilder = new Notification.Builder(this)
			.setContentTitle("IS Notification")
			.setContentText("Inbox Style notification!!")
			.setSmallIcon(R.drawable.ic_launcher);
		//	.addAction(R.drawable.ic_launcher_web, "show activity", pi);

		mNotification = new Notification.InboxStyle(mBuilder)
			.addLine("1First message").addLine("Second message")
			.addLine("2Thrid message").addLine("Fourth Message")
			.addLine("3First message").addLine("Second message")
			.addLine("4Thrid message").addLine("Fourth Message")
			.addLine("5First message").addLine("Second message")
			.addLine("6Thrid message").addLine("Fourth Message").build();
		// Put the auto cancel notification flag
		mNotification.flags |= Notification.FLAG_AUTO_CANCEL;
		NotificationManager notificationManager = getNotificationManager();
		notificationManager.notify(0, mNotification);
		new NotificationUpdater().execute();
	}

	private class NotificationUpdater extends AsyncTask <Void,Void,Void>
	{

		protected Void doInBackground(Void[] p1)
		{
			Integer count=0;
			while (true)
			{
				Log.v("tuls", count.toString());
				count++;
				try
				{
					Thread.sleep(500);
				}

				catch (InterruptedException e)
				{}
				try
				{
					
					Notification.InboxStyle notificationBuilder2 = new Notification.InboxStyle();
					Notification.Builder notificationBuilder1 = new Notification.Builder(getApplicationContext());
					RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.notification);
					//notificationBuilder.setContent(contentView);
					notificationBuilder1.setContentTitle("title");
					notificationBuilder1.setContentInfo("info");
					Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
					PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, 0);
					//notificationBuilder.setContentIntent(contentIntent);
					
					Process process = Runtime.getRuntime().exec("logcat -d");
					BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(process.getInputStream()));
					StringBuilder log=new StringBuilder();
					ArrayList<String> lines = new ArrayList<String>();
					String line;

					while ((line = bufferedReader.readLine()) != null)
					{
						//lines.add(lines);
						lines.add(0,line);
					}
					for(int i=0;i<7;i++){
						notificationBuilder2.addLine(lines.get(7-1-i));
					}
					//getNotificationManager().notify(1, notificationBuilder1.build());
					getNotificationManager().notify(2, notificationBuilder2.build());
					//process = Runtime.getRuntime().exec("logcat -c");

				}
				catch (IOException e)
				{
				}



			}

		}


	}

	public PendingIntent getPendingIntent()
	{
		return null;// PendingIntent.getActivity(this, 0, new Intent(this,HandleNotificationActivity.class), 0);
	}

	public NotificationManager getNotificationManager()
	{
		return (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	}
}
