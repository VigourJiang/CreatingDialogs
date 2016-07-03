package com.lightcone.creatingdialogs;

import android.app.Activity;
import android.os.Bundle;

// The class is TaskActivity2 is invoked whenever Task2 is chosen from the button menu.

public class TaskActivity2 extends Activity {

    /** onCreate is called when the activity is first created. */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taskactivity2);
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