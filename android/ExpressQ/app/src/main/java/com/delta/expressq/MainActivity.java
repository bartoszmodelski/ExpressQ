package com.delta.swiftq;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private PermissionChecker permissionChecker;
    private String url = "https://calm-scrubland-82156.herokuapp.com/json";
    private ConnectionManager cm;
    private List<Order> orders = new ArrayList<Order>();
    private Scanner scanner = null;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

        permissionChecker = new PermissionChecker(this);
        permissionChecker.check();
    }

    public void login(View view) {
        String username = ((EditText) findViewById(R.id.editText2)).getText().toString().trim();
        String APIkey = ((EditText) findViewById(R.id.editText3)).getText().toString().trim();

        if (username.isEmpty()) {
            showToast("Username cannot be empty.");
            return;
        } else if (APIkey.isEmpty()) {
            showToast("APIkey cannot be empty.");
            return;
        }

        try {
            cm = new ConnectionManager(url, username, APIkey, this,
                    MainActivity.class.getMethod("onResponseUpcomingOrders", List.class),
                    MainActivity.class.getMethod("onResponseOrder", Order.class),
                    MainActivity.class.getMethod("onResponseCorrectCredentials"));
        } catch (NoSuchMethodException e) {
            showToast("NSME Java Reflection Error: please contact SwiftQ team. " +
                    "Content of exception: " + e.getMessage());
        }
        cm.requestCheckCredentials();
    }

    //This method is called directly by the timer
    //and runs in the same thread as the timer.
    private void handleTimer() {
        //We call the method that will work with the UI
        //through the runOnUiThread method.
        this.runOnUiThread(tick);
    }


    //This method runs in the same thread as the UI.
    private Runnable tick = new Runnable() {
        public void run() {
            updateListing();
        }
    };

    public void changeStatus(String id, String status) {
        cm.requestUpdateStatus(id, status);
    }

    public synchronized void onResponseUpcomingOrders(List<Order> result) {
        orders = result;

        final ListView listView = (ListView) findViewById(R.id.listView);
        CustomAdapter adapter = new CustomAdapter(this, orders);

        listView.setAdapter(adapter);
    }

    public void onResponseCorrectCredentials() {
        continueWithLoggingIn();
    }

    private void continueWithLoggingIn() {
        startListing();
        scanner = new Scanner(this);
        timer = new Timer();
        //timer.scheduleAtFixedRate(new Worker(this), 0, 1000); //30 seconds

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handleTimer();
            }
        }, 0, 15 * 1000);
    }

    public void onResponseOrder(Order order) {
        new AlertDialog.Builder(this)
                .setTitle("Details")
                .setMessage(order.toString())
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setIconAttribute(android.R.attr.dialogIcon)
                .show();
    }

    public synchronized void updateListing() {
        cm.requestUpcomingOrders();
    }

    private void startListing() {
        setContentView(R.layout.activity_main_listing);
        cm.requestUpcomingOrders();
    }

    private boolean scanning = false;

    public void scan(View v) {
        scanning = true;
        scanner.start();
    }

    public void onScanned(String text) {
        scanning = false;
        cm.requestOrder(text);
        startListing();
    }

    private boolean exit = false;

    @Override
    public void onBackPressed() {
        if (scanning) {
            startListing();
            scanning = false;
        } else if (exit) {
            finish(); // finish activity
        } else {
            showToast("Press Back again to Exit.");
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);
        }
    }

    public void showToast(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

}
