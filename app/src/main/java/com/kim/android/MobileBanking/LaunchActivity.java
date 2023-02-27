package com.kim.android.MobileBanking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        // Create a new thread to delay the main activity
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Delay the main activity for 3 seconds
                try {
                    Thread.sleep(2900);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Use a handler to run code on the main thread
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        // Start the main activity
                        startActivity(new Intent(LaunchActivity.this, Login.class));
                        finish();
                    }
                });
            }
        }).start();
    }
}