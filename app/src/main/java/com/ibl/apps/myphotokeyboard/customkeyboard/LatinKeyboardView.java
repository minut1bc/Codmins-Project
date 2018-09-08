package com.ibl.apps.myphotokeyboard.customkeyboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.Keyboard.Key;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;
import android.view.inputmethod.InputMethodSubtype;

import com.ibl.apps.myphotokeyboard.R;
import com.ibl.apps.myphotokeyboard.utils.GlobalClass;
import com.ibl.apps.myphotokeyboard.utils.Utils;

import java.util.List;

public class LatinKeyboardView extends KeyboardView {

    static final int KEYCODE_OPTIONS = -100;
    static final int KEYCODE_LANGUAGE_SWITCH = -101;
    Drawable npdDelete;
    Drawable npdDone;
    Drawable npdShiftOff;
    Drawable npdShiftOn;
    Drawable npdSpace;
    Drawable npdDoubleSpace;

    public Paint newpaint;
    private Context context;


    public LatinKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.newpaint = new Paint();

        this.npdShiftOff = context.getResources().getDrawable(R.drawable.ic_shift_off);
        this.npdShiftOn = context.getResources().getDrawable(R.drawable.ic_shift_on);
        this.npdSpace = context.getResources().getDrawable(R.drawable.ic_space);
        this.npdDelete = context.getResources().getDrawable(R.drawable.ic_backspace);
        this.npdDone = context.getResources().getDrawable(R.drawable.ic_enter_new);
        this.npdDoubleSpace = context.getResources().getDrawable(R.drawable.ic_shift_double_on);

    }

    @Override
    protected boolean onLongPress(Key key) {
        if (key.codes[0] == Keyboard.KEYCODE_CANCEL) {
            getOnKeyboardActionListener().onKey(KEYCODE_OPTIONS, null);
            return true;
        } else {
            return super.onLongPress(key);
        }
    }

    void setSubtypeOnSpaceKey(final InputMethodSubtype subtype) {
        final LatinKeyboard keyboard = (LatinKeyboard) getKeyboard();
        //invalidateAllKeys();
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        List<Key> keys;
        keys = getKeyboard().getKeys();
        for (Key key : keys) {

            GradientDrawable npd1 = new GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM,
                    new int[]{GlobalClass.getPreferencesInt(context, GlobalClass.KEY_BG_COLOR, getResources().getColor(R.color.eight)),
                            GlobalClass.getPreferencesInt(context, GlobalClass.KEY_BG_COLOR, getResources().getColor(R.color.eight))});
            GradientDrawable npd_presed1 = new GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM,
                    new int[]{GlobalClass.getPreferencesInt(context, GlobalClass.KEY_BG_COLOR, getResources().getColor(R.color.eight)),
                            GlobalClass.getPreferencesInt(context, GlobalClass.KEY_BG_COLOR, getResources().getColor(R.color.eight))});


            npd1.setAlpha(Integer.parseInt(GlobalClass.getPreferencesString(context, GlobalClass.KEY_OPACITY, "255")));
            npd_presed1.setAlpha(Integer.parseInt(GlobalClass.getPreferencesString(context, GlobalClass.KEY_OPACITY, "255")));

            npd1.setCornerRadius(Float.parseFloat(GlobalClass.getPreferencesString(context, GlobalClass.KEY_RADIUS, "18")));
            npd_presed1.setCornerRadius(Float.parseFloat(GlobalClass.getPreferencesString(context, GlobalClass.KEY_RADIUS, "18")));

            switch (GlobalClass.getPreferencesString(context, GlobalClass.KEY_STROKE, "2")) {
                case "1":
                    npd1.setStroke(0, context.getResources().getColor(R.color.colorPrimary));
                    npd_presed1.setStroke(0, context.getResources().getColor(R.color.colorPrimary));
                    break;
                case "2":
                    npd1.setStroke(2, android.graphics.Color.WHITE);
                    npd_presed1.setStroke(2, android.graphics.Color.WHITE);
                    break;
                case "3":
                    npd1.setStroke(2, android.graphics.Color.BLACK);
                    npd_presed1.setStroke(2, android.graphics.Color.BLACK);
                    break;
                case "4":
                    npd1.setStroke(4, android.graphics.Color.BLACK);
                    npd_presed1.setStroke(4, android.graphics.Color.BLACK);
                    break;
                case "5":
                    npd1.setStroke(3, android.graphics.Color.GRAY);
                    npd_presed1.setStroke(3, android.graphics.Color.GRAY);
                    break;
            }

            npd1.setBounds(key.x + 5, key.y + 5, (key.x + key.width) - 5, (key.y + key.height) - 5);
            npd_presed1.setBounds(key.x + 5, key.y + 5, (key.x + key.width) - 5, (key.y + key.height) - 5);
            if (key.pressed) {
                npd1.draw(canvas);
            } else {
                npd_presed1.draw(canvas);
            }
            switch (key.codes[0]) {
                case Keyboard.KEYCODE_SHIFT /*-978903*/:
                    if (SoftKeyboard.mCapsLock) {
                        this.npdDoubleSpace.setColorFilter(android.graphics.Color.parseColor(GlobalClass.getPreferencesString(context, GlobalClass.FONT_COLOR, "#FFFFFF")), PorterDuff.Mode.SRC_ATOP);
                        this.npdDoubleSpace.setBounds(key.x + 40, key.y + 30, key.x + key.width - 40, key.y + key.height - 30);
                        this.npdDoubleSpace.draw(canvas);
                    } else {
                        if (!getKeyboard().isShifted()) {
                            npdShiftOff.setColorFilter(android.graphics.Color.parseColor(GlobalClass.getPreferencesString(context, GlobalClass.FONT_COLOR, "#FFFFFF")), PorterDuff.Mode.SRC_ATOP);
                            this.npdShiftOff.setBounds(key.x + 40, key.y + 30, key.x + key.width - 40, key.y + key.height - 30);
                            this.npdShiftOff.draw(canvas);
                            break;
                        } else {
                            npdShiftOn.setColorFilter(android.graphics.Color.parseColor(GlobalClass.getPreferencesString(context, GlobalClass.FONT_COLOR, "#FFFFFF")), PorterDuff.Mode.SRC_ATOP);
                            this.npdShiftOn.setBounds(key.x + 40, key.y + 30, key.x + key.width - 40, key.y + key.height - 30);
                            this.npdShiftOn.draw(canvas);
                        }
                    }

                    break;
                case 10 /*-978903*/:
                    this.npdDone.setColorFilter(android.graphics.Color.parseColor(GlobalClass.getPreferencesString(context, GlobalClass.FONT_COLOR, "#FFFFFF")), PorterDuff.Mode.SRC_ATOP);
                    this.npdDone.setBounds(key.x + 40, key.y + 30, key.x + key.width - 40, key.y + key.height - 30);
                    this.npdDone.draw(canvas);
                    break;
                case Keyboard.KEYCODE_DELETE /*-978903*/:
                    this.npdDelete.setColorFilter(android.graphics.Color.parseColor(GlobalClass.getPreferencesString(context, GlobalClass.FONT_COLOR, "#FFFFFF")), PorterDuff.Mode.SRC_ATOP);
                    this.npdDelete.setBounds(key.x + 40, key.y + 30, key.x + key.width - 40, key.y + key.height - 30);
                    this.npdDelete.draw(canvas);
                    break;
                case 32 /*-978903*/:
                    /*npdSpace.setColorFilter(android.graphics.Color.parseColor(GlobalClass.getPreferencesString(context, GlobalClass.FONT_COLOR, "#FFFFFF")), PorterDuff.Mode.SRC_ATOP);
                    this.npdSpace.setBounds(key.x , key.y, key.x + key.width, key.y + key.height);
                    this.npdSpace.draw(canvas);*/
                    break;
                default:
                    if (key.label != null) {
                        String s;
                        this.newpaint.setTextSize((float) (((int) getResources().getDimension(R.dimen.key_text_size))));
                        this.newpaint.setTypeface(Typeface.DEFAULT);
                        if (!Utils.tmpdeletefalg || !Utils.isEnglishCharacter()) {
                            s = key.label.toString();
                            if (Utils.editorisOpen) {
                                s = key.label.toString();
                            }
                        } else if (Utils.editorisOpen) {
                            s = key.label.toString();
                        } else {
                            s = key.label.toString().toUpperCase();
                        }
                        newpaint.setTextAlign(Paint.Align.CENTER);
                        newpaint.setColor(Color.parseColor(GlobalClass.getPreferencesString(context, GlobalClass.FONT_COLOR, "#FFFFFF")));

                        if (GlobalClass.getPreferencesString(context, GlobalClass.FONT_NAME, "").length() != 0 && GlobalClass.getPreferencesString(context, GlobalClass.FONT_NAME, "") != null
                                && !GlobalClass.getPreferencesString(context, GlobalClass.FONT_NAME, "").isEmpty()) {
                            try {
                                Typeface font = Typeface.createFromAsset(context.getAssets(), GlobalClass.getPreferencesString(context, GlobalClass.FONT_NAME, ""));
                                newpaint.setTypeface(font);

                            } catch (Exception ignored) {
                            }
                        }
                        canvas.drawText(s, (float) (key.x + (key.width / 2)), (float) ((key.y + (key.height / 1.8)) + ((int) getResources().getDimension(R.dimen.text_top_margin))), this.newpaint);
                    }
                    break;
            }
        }

    }

}
