package com.theultimatelabs.logcat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.Activity;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViews.RemoteView;


public class MainActivity extends Activity
{
	public final String TAG = "LogcatActivity";
	
	private Notification mNotification2,mNotification;

	private Notification.Builder mBuilder;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Log.v(TAG,"onCreate");
//		String ns = Context.NOTIFICATION_SERVICE;
//		NotificationManager notificationManager = (NotificationManager) getSystemService(ns);
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

		

		//sendInboxStyleNotification();

		
		
		//new NotificationUpdater().execute();
		Log.v(TAG,"build");
		Builder builder = new Notification.Builder(this);
		builder.setContentInfo("info");
		builder.setContentText("text");
		builder.setContentTitle("title");
		Log.v(TAG,"notify");
		getNotificationManager().notify(0, builder.build());
		
		Context ctx = this;
		Intent notificationIntent = new Intent(ctx, MainActivity.class);
		PendingIntent contentIntent = PendingIntent.getActivity(ctx,
		        0, notificationIntent,
		        PendingIntent.FLAG_CANCEL_CURRENT);

		NotificationManager nm = (NotificationManager) ctx
		        .getSystemService(Context.NOTIFICATION_SERVICE);
		
		RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.notification);
		
		
		builder = new Notification.Builder(ctx);
		Log.v(TAG,"build");
		builder.setContentIntent(contentIntent)
					.setSmallIcon(R.drawable.ic_launcher)
		            .setTicker("ticker")
		            .setWhen(System.currentTimeMillis())
		            .setAutoCancel(true)
		            .setContentTitle("title")
		            .setContentText("text")
					.setContent(contentView);
		Notification n = builder.build();
		n.bigContentView = new RemoteViews(getPackageName(), R.layout.notification);
		Log.v(TAG,n.toString());
		Log.v(TAG,"notify");
		nm.notify(5, n);
		
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
					
					Notification.InboxStyle notificationBuilder2 = new Notification.InboxStyle(mBuilder);
					
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
