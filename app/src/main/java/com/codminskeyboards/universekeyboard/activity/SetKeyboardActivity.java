package com.codminskeyboards.universekeyboard.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.codminskeyboards.universekeyboard.R;
import com.codminskeyboards.universekeyboard.utils.GlobalClass;

public class SetKeyboardActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;
    private InputMethodChangedReceiver inputMethodChangedReceiver;
    private boolean isKeyboardEnabled;
    private boolean isKeyboardSet;
    private Button enableKeyboardButton;
    private Button switchKeyboardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = SetKeyboardActivity.this;

        setContentView(R.layout.activity_set_keyboard);

        enableKeyboardButton = findViewById(R.id.enableKeyboardButton);
        switchKeyboardButton = findViewById(R.id.switchKeyboardButton);

        inputMethodChangedReceiver = new InputMethodChangedReceiver();

        isKeyboardEnabled = GlobalClass.KeyboardIsEnabled(context);
        isKeyboardSet = GlobalClass.KeyboardIsSet(context);

        enableKeyboardButton.setEnabled(!isKeyboardEnabled);

        if (isKeyboardEnabled)
            switchKeyboardButton.setEnabled(false);
        else
            switchKeyboardButton.setEnabled(isKeyboardSet);

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
        if (inputMethodChangedReceiver != null)
            inputMethodChangedReceiver.cancel();
        inputMethodChangedReceiver = null;
        inputMethodChangedReceiver = new InputMethodChangedReceiver();
    }

    public void switchKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
        if (inputMethodManager != null)
            inputMethodManager.showInputMethodPicker();
        else
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        boolean z = false;
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.buttonanimation);
        isKeyboardEnabled = GlobalClass.KeyboardIsEnabled(context);

        if (isKeyboardEnabled) {
            try {
                if (inputMethodChangedReceiver != null)
                    inputMethodChangedReceiver.cancel();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        isKeyboardSet = GlobalClass.KeyboardIsSet(context);
        if (!isKeyboardEnabled) {
            if (!isKeyboardEnabled)
                z = true;
            enableKeyboardButton.setEnabled(z);
            enableKeyboardButton.startAnimation(animation);
        } else if (isKeyboardSet) {
            switchKeyboardButton.clearAnimation();
            startActivity(new Intent(context, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            if (!isKeyboardSet)
                z = true;
            switchKeyboardButton.setEnabled(z);
            super.onWindowFocusChanged(hasFocus);
        } else {
            enableKeyboardButton.setEnabled(!isKeyboardEnabled);
            enableKeyboardButton.clearAnimation();
            switchKeyboardButton.startAnimation(animation);
            if (!isKeyboardSet)
                z = true;
            switchKeyboardButton.setEnabled(z);
        }
    }
}
