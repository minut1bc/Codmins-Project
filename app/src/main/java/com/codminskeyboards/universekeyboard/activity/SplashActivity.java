package com.codminskeyboards.universekeyboard.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.codminskeyboards.universekeyboard.R;
import com.codminskeyboards.universekeyboard.utils.GlobalClass;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        GlobalClass globalClass = new GlobalClass(SplashActivity.this);

        Thread splashThread = new Thread() {
            public void run() {
                synchronized (this) {
                    try {
                        wait(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    
                    if (GlobalClass.KeyboardIsEnabled(SplashActivity.this) && GlobalClass.KeyboardIsSet(SplashActivity.this)) {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                    } else {
                        startActivity(new Intent(SplashActivity.this, SetKeyboardActivity.class));
                        finish();
                    }
                }
            }
        };
        splashThread.start();
    }
}


