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
import android.widget.TextView;
import android.widget.Toast;

import com.codminskeyboards.universekeyboard.R;
import com.codminskeyboards.universekeyboard.utils.GlobalClass;

public class SetKeyboardActivity extends AppCompatActivity implements View.OnClickListener {

    private InputMethodChangedReceiver imChange;
    private boolean isKeyboardEnabled;
    private boolean isKeyboardSet;
    private TextView txtEnableKeyboard;
    private TextView txtSwitchKeyboard;

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
        txtEnableKeyboard = findViewById(R.id.txtEnableKeyboard);
        txtSwitchKeyboard = findViewById(R.id.txtSwitchKeyboard);

        imChange = new InputMethodChangedReceiver();

        isKeyboardEnabled = GlobalClass.KeyboardIsEnabled(SetKeyboardActivity.this);
        isKeyboardSet = GlobalClass.KeyboardIsSet(SetKeyboardActivity.this);

        txtEnableKeyboard.setEnabled(!isKeyboardEnabled);
        if (isKeyboardEnabled) {
            txtSwitchKeyboard.setEnabled(false);
        } else {
            txtSwitchKeyboard.setEnabled(isKeyboardSet);
        }
        txtEnableKeyboard.setOnClickListener(this);
        txtSwitchKeyboard.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtEnableKeyboard:
                enableKeyboard();
                break;
            case R.id.txtSwitchKeyboard:
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
        TextView button;
        if (!isKeyboardEnabled) {
            button = txtEnableKeyboard;
            if (!isKeyboardEnabled) {
                z = true;
            }
            button.setEnabled(z);
            txtEnableKeyboard.startAnimation(shake);
        } else if (isKeyboardSet) {
            txtSwitchKeyboard.clearAnimation();
            startActivity(new Intent(SetKeyboardActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            button = txtSwitchKeyboard;
            if (!isKeyboardSet) {
                z = true;
            }
            button.setEnabled(z);
            super.onWindowFocusChanged(hasFocus);
        } else {
            boolean z2;
            TextView button2 = txtEnableKeyboard;
            z2 = !isKeyboardEnabled;
            button2.setEnabled(z2);
            txtEnableKeyboard.clearAnimation();
            txtSwitchKeyboard.startAnimation(shake);
            button = txtSwitchKeyboard;
            if (!isKeyboardSet) {
                z = true;
            }
            button.setEnabled(z);
        }
    }
}
