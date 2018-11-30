package com.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.gallery.base.BaseActivity;

public class SplashActivity extends BaseActivity {

    private static final String TAG = "MainActivityTAG";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

    private void runnable() {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "Runnable = " + Thread.currentThread().getName());

            }
        };
        run.run();
    }

    private void startThread() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                Log.d(TAG, "startThread = " + Thread.currentThread().getName());

            }
        }.start();
    }
}
