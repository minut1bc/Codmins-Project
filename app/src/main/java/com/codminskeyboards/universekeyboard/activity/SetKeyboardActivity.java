package com.codminskeyboards.universekeyboard.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.codminskeyboards.universekeyboard.R;
import com.codminskeyboards.universekeyboard.utils.GlobalClass;

public class SetKeyboardActivity extends AppCompatActivity implements View.OnClickListener {

    private InputMethodChangedReceiver imChange;
    private boolean isKeyboardEnabled;
    private boolean isKeyboardSet;
    private Button enableKeyboardButton;
    private Button switchKeyboardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.dark_gray));
        }

        setContentView(R.layout.activity_set_keyboard);
        setContent();
    }

    private void setContent() {
        enableKeyboardButton = findViewById(R.id.enableKeyboardButton);
        switchKeyboardButton = findViewById(R.id.switchKeyboardButton);

        imChange = new InputMethodChangedReceiver();

        isKeyboardEnabled = GlobalClass.KeyboardIsEnabled(SetKeyboardActivity.this);
        isKeyboardSet = GlobalClass.KeyboardIsSet(SetKeyboardActivity.this);

        enableKeyboardButton.setEnabled(!isKeyboardEnabled);
        if (isKeyboardEnabled) {
            switchKeyboardButton.setEnabled(false);
        } else {
            switchKeyboardButton.setEnabled(isKeyboardSet);
        }
        enableKeyboardButton.setOnClickListener(this);
        switchKeyboardButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.enableKeyboardButton:
                enableKeyboard();
                break;
            case R.id.switchKeyboardButton:
                GlobalClass.printLog("SetKeyboardActivity", "-------txtSwitchKeyboard---------");
                switchKeyboard();
                break;
        }
    }

    public void enableKeyboard() {
        startActivityForResult(new Intent("android.settings.INPUT_METHOD_SETTINGS"), 0);
        if (imChange != null) {
            imChange.cancel();
        }
        imChange = null;
        imChange = new InputMethodChangedReceiver();
    }

    public void switchKeyboard() {
        InputMethodManager imeManager = (InputMethodManager) SetKeyboardActivity.this.getSystemService(INPUT_METHOD_SERVICE);
        if (imeManager != null) {
            imeManager.showInputMethodPicker();
        } else {
            Toast.makeText(SetKeyboardActivity.this, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        boolean z = false;
        Animation shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.buttonanimation);
        isKeyboardEnabled = GlobalClass.KeyboardIsEnabled(SetKeyboardActivity.this);

        if (isKeyboardEnabled) {
            try {
                if (imChange != null) {
                    imChange.cancel();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        isKeyboardSet = GlobalClass.KeyboardIsSet(SetKeyboardActivity.this);
        Button button;
        if (!isKeyboardEnabled) {
            button = enableKeyboardButton;
            if (!isKeyboardEnabled) {
                z = true;
            }
            button.setEnabled(z);
            enableKeyboardButton.startAnimation(shake);
        } else if (isKeyboardSet) {
            switchKeyboardButton.clearAnimation();
            startActivity(new Intent(SetKeyboardActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            button = switchKeyboardButton;
            if (!isKeyboardSet) {
                z = true;
            }
            button.setEnabled(z);
            super.onWindowFocusChanged(hasFocus);
        } else {
            boolean z2;
            TextView button2 = enableKeyboardButton;
            z2 = !isKeyboardEnabled;
            button2.setEnabled(z2);
            enableKeyboardButton.clearAnimation();
            switchKeyboardButton.startAnimation(shake);
            button = switchKeyboardButton;
            if (!isKeyboardSet) {
                z = true;
            }
            button.setEnabled(z);
        }
    }
}
