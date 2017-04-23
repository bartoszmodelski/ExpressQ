package com.delta.expressq;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.GET_ACCOUNTS;
import static android.support.v4.app.ActivityCompat.requestPermissions;

class PermissionChecker {
    private static final int REQUEST_GET_ACCOUNT = 112;
    private MainActivity mainActivityHandle;

    private Context getApplicationContext() {
        return mainActivityHandle.getApplicationContext();
    }

    public PermissionChecker(MainActivity mainActivityHandle) {
        this.mainActivityHandle = mainActivityHandle;
    }

    public void check() {
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.M) {
            if (checkPermission()) {
                Toast.makeText(getApplicationContext(), "Permission already granted", Toast.LENGTH_LONG).show();
            } else {
                requestPermission();
            }
        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), GET_ACCOUNTS);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        requestPermissions(mainActivityHandle, new String[]{GET_ACCOUNTS, CAMERA}, REQUEST_GET_ACCOUNT);
        //requestPermissions(mainActivityHandle, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new android.support.v7.app.AlertDialog.Builder(mainActivityHandle)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
}


