package com.ibl.apps.myphotokeyboard.activity;

import android.content.Intent;
import android.os.Bundle;

import com.ibl.apps.myphotokeyboard.R;
import com.ibl.apps.myphotokeyboard.utils.GlobalClass;

import permission.auron.com.marshmallowpermissionhelper.ActivityManagePermission;
import permission.auron.com.marshmallowpermissionhelper.PermissionResult;
import permission.auron.com.marshmallowpermissionhelper.PermissionUtils;

public class SplashActivity extends ActivityManagePermission {

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
                    askForPermission();
                }
            }
        };
        splashThread.start();
    }

    private void askForPermission() {
        askCompactPermissions(new String[]{PermissionUtils.Manifest_CAMERA, PermissionUtils.Manifest_WRITE_EXTERNAL_STORAGE, PermissionUtils.Manifest_READ_EXTERNAL_STORAGE}, new PermissionResult() {
                    @Override
                    public void permissionGranted() {
                        if (GlobalClass.KeyboardIsEnabled(SplashActivity.this) && GlobalClass.KeyboardIsSet(SplashActivity.this)) {
                            startActivity(new Intent(SplashActivity.this, MainActivity.class));
                            finish();
                        } else {
                            startActivity(new Intent(SplashActivity.this, SetKeyboardActivity.class));
                            finish();
                        }

                    }

                    @Override
                    public void permissionDenied() {
                    }

                    @Override
                    public void permissionForeverDenied() {
                    }
                }
        );
    }
}


