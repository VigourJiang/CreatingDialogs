package com.lightcone.creatingdialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements OnClickListener {

    private static final int numberTasks = 2;
    private static final String launcherTitle = "Task Description";
    private static final int launcherIcon = R.drawable.dialog_icon;
    private static int buttonPressed;
    private String[] taskDescription = new String[numberTasks];

    private static int dialogTheme;  // Integer defining the dialog theme

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Identify buttons in XML layout and attach click  listeners to each

        View button01 = findViewById(R.id.button01);
        button01.setOnClickListener(this);
        View button02 = findViewById(R.id.button02);
        button02.setOnClickListener(this);
        View button03 = findViewById(R.id.button03);
        button03.setOnClickListener(this);

        // Extract  task description strings from strings.xml and place in an array for later use

        taskDescription[0] = getString(R.string.task_description1);
        taskDescription[1] = getString(R.string.task_description2);

        // Use Material Design them if API 23 or later; Holo Light if earlier

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            dialogTheme = AlertDialog.THEME_HOLO_LIGHT;  // Deprecated with API 23
        } else {
            dialogTheme = R.style.MyDialogTheme;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            // For buttons 1 and 2, launch floating dialogs
            case R.id.button01:
                buttonPressed = 1;

                // Set alert content. If icon or title are omitted or set to null, they
                // will not appear in the Alert Dialog window.

                AlertFragment.context = this;
                AlertFragment.iconID = launcherIcon;
                AlertFragment.title = launcherTitle;
                AlertFragment.message = taskDescription[buttonPressed - 1];

                DialogFragment fragment = new AlertFragment();
                fragment.show(getSupportFragmentManager(), "Task 1");

                break;

            case R.id.button02:
                buttonPressed = 2;
                showTaskDialog(launcherTitle, taskDescription[buttonPressed - 1], launcherIcon, this);
                break;

            // For button 3, start a service that will place a notification in the task bar
            case R.id.button03:
                startTheService();
                break;
        }

    }


    /**
     * Method showTaskDialog() creates a custom launch dialog popped up by a press on
     * a button.  This dialog presents a summary of the task to the user and has buttons to either
     * launch the task or cancel the dialog. Which task to present is controlled by the value of the
     * int buttonPressed, which is stored if a button is pressed and is used in the switch
     * statement in launchTask().
     */

    private void showTaskDialog(String title, String message, int icon, Context context) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context, dialogTheme);
        builder.setMessage(message).setTitle(title).setIcon(icon);
        // Add the buttons
        builder.setPositiveButton("Select this Task", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                launchTask();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Can execute additional code here if desired
                // Default is cancellation of dialog window.
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Method launchTask() uses a switch statement to decide which task to launch.
     */

    private void launchTask() {

        // Illustrate a Toast notification
        Toast.makeText(this, "launchTask() executed", Toast.LENGTH_SHORT).show();

        switch (buttonPressed) {
            case 1:    // Launch task 1
                Intent i = new Intent(this, TaskActivity1.class);
                startActivity(i);
                break;
            case 2:    // Launch task 2
                Intent j = new Intent(this, TaskActivity2.class);
                startActivity(j);
                break;
        }
    }

    // Start a service to demo a status bar notification

    public void startTheService() {
        Intent serviceIntent = new Intent(this, MyNotificationService.class);
        this.startService(serviceIntent);
    }

    /**
     * Example of a class to create alert dialog fragments. To call from another
     * class, set the values of the static variables and then instantiate.  Example:
     *
     * AlertFragment.context = this;
     * AlertFragment.iconID = launcherIcon;
     * AlertFragment.title = launcherTitle;
     * AlertFragment.message = taskDescription[buttonPressed-1];
     * DialogFragment fragment = new AlertFragment();
     * fragment.show(getSupportFragmentManager(), "Task 1");
     */

    public static class AlertFragment extends DialogFragment {

        public static Context context;
        public static String message;
        public static String title;
        public static int iconID;
        public static int buttonPressed;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            // Use the Builder class to construct the dialog.  Use the
            // form of the builder constructor that allows a theme to be set.

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), MainActivity.dialogTheme);
            if (title != null) builder.setTitle(title);
            if (iconID != 0) builder.setIcon(iconID);
            builder.setMessage(message)
                    .setPositiveButton("Select this task", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            launchTask();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Default is to cancel the dialog window.  Can add
                            // additional commands if desired.
                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }

        // Method to launch new activity when button pressed in Alert Dialog
        private void launchTask() {
            Intent i = new Intent(context, TaskActivity1.class);
            startActivity(i);
        }
    }
}
