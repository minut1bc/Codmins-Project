package com.codminskeyboards.universekeyboard.customkeyboard;

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

import com.codminskeyboards.universekeyboard.R;
import com.codminskeyboards.universekeyboard.utils.GlobalClass;

import java.util.List;

public class LatinKeyboardView extends KeyboardView {

    Context context;

    static final int KEYCODE_OPTIONS = -100;
    static final int KEYCODE_LANGUAGE_SWITCH = -101;
    static final int KEYCODE_EMOJI = -102;

    GradientDrawable keyBackground;
    GradientDrawable keyPressedBackground;

    Drawable capsLockDrawable = getResources().getDrawable(R.drawable.ic_shift_double_on);
    Drawable shiftOffDrawable = getResources().getDrawable(R.drawable.ic_shift_off);
    Drawable shiftOnDrawable = getResources().getDrawable(R.drawable.ic_shift_on);
    Drawable enterDrawable = getResources().getDrawable(R.drawable.ic_enter_new);
    Drawable deleteDrawable = getResources().getDrawable(R.drawable.ic_backspace);
    Drawable spaceDrawable = getResources().getDrawable(R.drawable.ic_space);
    Drawable emojiDrawable = getResources().getDrawable(R.drawable.ic_smile);

    Paint paint = new Paint();

    public LatinKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        int keyBgColor = GlobalClass.getPreferencesInt(context, GlobalClass.KEY_BG_COLOR, getResources().getColor(R.color.eight));

        keyBackground = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{keyBgColor, keyBgColor});
        keyPressedBackground = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{keyBgColor, keyBgColor});
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

        int fontColor = Color.parseColor(GlobalClass.getPreferencesString(context, GlobalClass.FONT_COLOR, "#FFFFFF"));
        int keyOpacity = GlobalClass.getPreferencesInt(context, GlobalClass.KEY_OPACITY, 255);
        float keyRadius = GlobalClass.getPreferencesInt(context, GlobalClass.KEY_RADIUS, 18);
        int keyStroke = GlobalClass.getPreferencesInt(context, GlobalClass.KEY_STROKE, 2);
        String fontName = GlobalClass.getPreferencesString(context, GlobalClass.FONT_NAME, "");
        int tint = 0x77000000;

        for (Key key : keys) {

            keyBackground.setAlpha(keyOpacity);
            //keyPressedBackground.setAlpha(keyOpacity);            // TODO: Maybe look into variable opacity tint
            keyPressedBackground.setColorFilter(tint, PorterDuff.Mode.SRC_ATOP);

            keyBackground.setCornerRadius(keyRadius);
            keyPressedBackground.setCornerRadius(keyRadius);

            switch (keyStroke) {
                case 1:
                    keyBackground.setStroke(0, getResources().getColor(R.color.colorPrimary));
                    keyPressedBackground.setStroke(0, getResources().getColor(R.color.colorPrimary));
                    break;
                case 2:
                    keyBackground.setStroke(2, Color.WHITE);
                    keyPressedBackground.setStroke(2, Color.WHITE);
                    break;
                case 3:
                    keyBackground.setStroke(2, Color.BLACK);
                    keyPressedBackground.setStroke(2, Color.BLACK);
                    break;
                case 4:
                    keyBackground.setStroke(4, Color.BLACK);
                    keyPressedBackground.setStroke(4, Color.BLACK);
                    break;
                case 5:
                    keyBackground.setStroke(3, Color.GRAY);
                    keyPressedBackground.setStroke(3, Color.GRAY);
                    break;
            }

            keyBackground.setBounds(key.x + 5, key.y + 5, (key.x + key.width) - 5, (key.y + key.height) - 5);
            keyPressedBackground.setBounds(key.x + 5, key.y + 5, (key.x + key.width) - 5, (key.y + key.height) - 5);
            if (!key.pressed) {
                keyBackground.draw(canvas);
            } else {
                keyPressedBackground.draw(canvas);
            }
            switch (key.codes[0]) {
                case Keyboard.KEYCODE_SHIFT /*-978903*/:
                    if (SoftKeyboard.capsLock) {
                        capsLockDrawable.setColorFilter(fontColor, PorterDuff.Mode.SRC_ATOP);
                        capsLockDrawable.setBounds(key.x + 40, key.y + 30, key.x + key.width - 40, key.y + key.height - 30);
                        capsLockDrawable.draw(canvas);
                    } else {
                        if (!getKeyboard().isShifted()) {
                            shiftOffDrawable.setColorFilter(fontColor, PorterDuff.Mode.SRC_ATOP);
                            shiftOffDrawable.setBounds(key.x + 40, key.y + 30, key.x + key.width - 40, key.y + key.height - 30);
                            shiftOffDrawable.draw(canvas);
                            break;
                        } else {
                            shiftOnDrawable.setColorFilter(fontColor, PorterDuff.Mode.SRC_ATOP);
                            shiftOnDrawable.setBounds(key.x + 40, key.y + 30, key.x + key.width - 40, key.y + key.height - 30);
                            shiftOnDrawable.draw(canvas);
                        }
                    }

                    break;
                case 10 /*-978903*/:
                    enterDrawable.setColorFilter(fontColor, PorterDuff.Mode.SRC_ATOP);
                    enterDrawable.setBounds(key.x + 40, key.y + 30, key.x + key.width - 40, key.y + key.height - 30);
                    enterDrawable.draw(canvas);
                    break;
                case Keyboard.KEYCODE_DELETE /*-978903*/:
                    deleteDrawable.setColorFilter(fontColor, PorterDuff.Mode.SRC_ATOP);
                    deleteDrawable.setBounds(key.x + 40, key.y + 30, key.x + key.width - 40, key.y + key.height - 30);
                    deleteDrawable.draw(canvas);
                    break;
                case 32 /*-978903*/:
                    /*spaceDrawable.setColorFilter(fontColor, PorterDuff.Mode.SRC_ATOP);
                    spaceDrawable.setBounds(key.x , key.y, key.x + key.width, key.y + key.height);
                    spaceDrawable.draw(canvas);*/
                    break;
                case -102:
                    emojiDrawable.setColorFilter(fontColor, PorterDuff.Mode.SRC_ATOP);
                    emojiDrawable.setBounds(key.x + 20, key.y + 35, key.x + key.width - 20, key.y + key.height - 35);
                    emojiDrawable.draw(canvas);
                default:
                    if (key.label != null) {
                        String keyLabel = key.label.toString();
                        paint.setTextSize(getResources().getDimension(R.dimen.key_text_size));
                        paint.setTypeface(Typeface.DEFAULT);
                        paint.setTextAlign(Paint.Align.CENTER);
                        paint.setColor(fontColor);
                        Typeface font = Typeface.DEFAULT;

                        if (!fontName.isEmpty())
                            font = Typeface.createFromAsset(context.getAssets(), fontName);
                        paint.setTypeface(font);

                        canvas.drawText(keyLabel, key.x + (key.width / 2), (float) (key.y + (key.height / 1.8) + getResources().getDimension(R.dimen.text_top_margin)), paint);
                    }
                    break;
            }
        }
    }
}