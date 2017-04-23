package com.delta.swiftq;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Scanner
        implements ZXingScannerView.ResultHandler {
    private final MainActivity activity;
    private ZXingScannerView mScannerView = null;

    public Scanner(MainActivity activity) {
        this.activity = activity;
    }

    public void start() {
        //setContentView(R.layout.activity_scanner);
        mScannerView = new ZXingScannerView(activity);
        activity.setContentView(mScannerView);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();

    }

    public void onPause() {
        if (mScannerView != null) {
            mScannerView.stopCamera();   // Stop camera on pause
        }
    }

    @Override
    public void handleResult(Result rawResult) {
        activity.setContentView(R.layout.activity_main_listing);
        activity.onScanned(rawResult.getText());
        // If you would like to resume scanning, call this method below:
        // mScannerView.resumeCameraPreview(this);
    }



}
