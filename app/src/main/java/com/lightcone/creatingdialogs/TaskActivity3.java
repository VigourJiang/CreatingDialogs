package com.lightcone.creatingdialogs;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

// The class TaskActivity3 is launched from a notification that
// we place in the notification bar (see MyNotificationService.java).

public class TaskActivity3 extends Activity {

    public static final String TAG = "NOTIFY";

    /** onCreate is called when the activity is first created. */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taskactivity3);

        // Cancel the notification icon in the task bar by first getting a NotificationManager
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
        // and then using the static id NOTIFY_ID from MyNotificationService
        // to cancel the display of the icon in the status bar.
        int id = MyNotificationService.NOTIFY_ID;
        Log.i(TAG, "Notification: ID="+id+" canceled");
        mNotificationManager.cancel(id);
    }

    /** onPause is called when the activity is going to background. */

    @Override
    public void onPause() {
        super.onPause();
    }

    /** onResume is called when the activity is going to foreground. */

    @Override
    public void onResume(){
        super.onResume();
    }
}