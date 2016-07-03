package com.lightcone.creatingdialogs;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

// This service will simply run for NOTIFY_INTERVAL_MS/1000 seconds, send a notification
// to the task bar, and then terminate. When the notice bar is clicked, the activity
// TaskActivity3 will be launched and the notification icon removed from the notification bar.

public class MyNotificationService extends Service {

    public static final String TAG = "NOTIFY";

    // ID for notification. Make public, static, so can access from
    // TaskActivity3 to cancel notice

    public static final int NOTIFY_ID = 1;

    private static final long NOTIFY_INTERVAL_MS = 5000;
    private BackgroundThread background;
    public Intent savedIntent;
    public static long refTimeMS;
    public Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context  = getApplicationContext();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        background.interrupt();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand( intent, flags, startId);
        Log.i(TAG,"Starting the service");
        savedIntent = intent;
        // Get an initial reference time in milliseconds
        refTimeMS = System.currentTimeMillis();
        doThreadStart();
        // Want this service to continue running until explicitly stopped, so return sticky.
        return START_STICKY;
    }

    // We won't bind anything to our service, so just return null for onBind
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // Start a background thread as an instance of the class BackgroundThread
    private void doThreadStart() {
        background = new BackgroundThread();
        background.start();
        String text = "Service will run for "+(NOTIFY_INTERVAL_MS/1000)+" s, ";
        text += "post notification to the task bar, and then stop.";
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    // Method to create and place the notification in the status bar
    //   See http://developer.android.com/guide/topics/ui/notifiers/notifications.html
    //   See http://developer.android.com/reference/android/app/NotificationManager.html
    //   See http://developer.android.com/design/patterns/notifications.html
    // Will use a PendingIntent to cause the activity TaskActivity3 to be launched
    // when the notification is clicked.

    private void createStatusBarNotification(){

        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);

        int icon = R.mipmap.ic_launcher;

        CharSequence contentTitle = "Status Bar Notification";
        CharSequence contentText = "Press to launch Task 3";

        // PendingIntent to be executed when notification is clicked
        Intent notificationIntent = new Intent(this, TaskActivity3.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        // Create the notification
        Notification notification = new Notification.Builder(context)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setSmallIcon(icon)
                .setContentIntent(contentIntent)
                .build();

        mNotificationManager.notify(NOTIFY_ID, notification);
    }


    // Class to run background thread by overriding  the run() method that is
    // inherited from Thread.  Meaningful work, like retrieving a Web page, could be
    // done on this thread.  We will just let it run for NOTIFY_INTERVAL_MS/1000 seconds
    // and then display a status bar notification and terminate the service.

    private class BackgroundThread extends Thread {
        public void run() {
            Log.i(TAG, "  Begin background thread");

            long elapsedMS = 0;

            // Loop for NOTIFY_INTERVAL_MS/1000 seconds
            while((elapsedMS) < NOTIFY_INTERVAL_MS){
                elapsedMS = System.currentTimeMillis() - refTimeMS;
            }

            // Create the status bar notification
            createStatusBarNotification();

            // Stop this service
            Log.i(TAG,"Stopping the service");
            stopSelf();
        }
    }
}
