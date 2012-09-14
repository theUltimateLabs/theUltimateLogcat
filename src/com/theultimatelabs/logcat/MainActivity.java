package com.theultimatelabs.logcat;

import android.app.*;
import android.app.Notification.*;
import android.content.*;
import android.os.*;
import android.util.*;
import android.widget.*;
import java.io.*;

import java.lang.Process;


public class MainActivity extends Activity
{
	public final String TAG = "LogcatActivity";
	
	private Notification mNotification2,mNotification;

	private Notification.Builder mBuilder;

	private NotificationManager mNotificationManager;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Log.v(TAG,"onCreate");
		
		Log.v(TAG,"build");
		mBuilder = new Notification.Builder(this);
		mBuilder.setContentInfo("info");
		mBuilder.setContentText("text");
		mBuilder.setContentTitle("title");
		Log.v(TAG,"notify");
		getNotificationManager().notify(0, mBuilder.build());
		
		Context ctx = this;
		Intent notificationIntent = new Intent(ctx, MainActivity.class);
		PendingIntent contentIntent = PendingIntent.getActivity(ctx,
		        0, notificationIntent,
		        PendingIntent.FLAG_CANCEL_CURRENT);

		mNotificationManager = (NotificationManager) ctx
		        .getSystemService(Context.NOTIFICATION_SERVICE);
		
		RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.notification);
		
		
		mBuilder = new Notification.Builder(ctx);
		Log.v(TAG,"build");
		mBuilder.setContentIntent(contentIntent)
					.setSmallIcon(R.drawable.ic_launcher)
		            .setTicker("ticker")
		            .setWhen(System.currentTimeMillis())
		            .setAutoCancel(true)
		            .setContentTitle("title")
		            .setContentText("text")
					.setContent(contentView);
		Notification mNotification = mBuilder.build();
		mNotification.bigContentView = new RemoteViews(getPackageName(), R.layout.notification);
		Log.v(TAG,"notify");
		mNotificationManager.notify(5, mNotification);
		mNotification.bigContentView.setTextViewText(R.id.text0,"HELLO");
		//TextView text0 = (TextView) this.findViewById(R.id.text0);
		//text0.setText("hello");
		mNotificationManager.notify(5, mNotification);
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
				
				try {
					Process process = Runtime.getRuntime().exec("logcat -d");
					BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(process.getInputStream()));

					String 
					String line;
					while ((line = bufferedReader.readLine()) != null) {
						log.append(line);
					}
					RemoteViews remoteView = new RemoteViews(getPackageName(), R.layout.notification);	
					remoteView.setTextViewText(R.id.text0, log.toString());
					mNotification = mBuilder.build();
					mNotification.bigContentView = remoteView;
					mNotificationManager.notify(5, mNotification);
				} catch (IOException e) {
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
