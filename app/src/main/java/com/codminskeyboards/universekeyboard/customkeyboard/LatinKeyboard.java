package com.codminskeyboards.universekeyboard.customkeyboard;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.inputmethodservice.Keyboard;
import android.view.inputmethod.EditorInfo;

import com.codminskeyboards.universekeyboard.R;

public class LatinKeyboard extends Keyboard {

    private Key enterKey;
    private Key spaceKey;
    private Key modeChangeKey;
    private Key savedModeChangeKey;
    private Key savedLanguageSwitchKey;

    LatinKeyboard(Context context, int xmlLayoutResId) {
        super(context, xmlLayoutResId);
    }

    @Override
    protected Key createKeyFromXml(Resources res, Row parent, int x, int y, XmlResourceParser parser) {
        Key key = new LatinKey(res, parent, x, y, parser);
        if (key.codes[0] == 10)
            enterKey = key;
        else if (key.codes[0] == ' ')
            spaceKey = key;
        else if (key.codes[0] == Keyboard.KEYCODE_MODE_CHANGE) {
            modeChangeKey = key;
            savedModeChangeKey = new LatinKey(res, parent, x, y, parser);
        } else if (key.codes[0] == LatinKeyboardView.KEYCODE_LANGUAGE_SWITCH)
            savedLanguageSwitchKey = new LatinKey(res, parent, x, y, parser);
        return key;
    }

    void setImeOptions(Resources resources, int options) {
        if (enterKey == null)
            return;

        switch (options&(EditorInfo.IME_MASK_ACTION|EditorInfo.IME_FLAG_NO_ENTER_ACTION)) {
            case EditorInfo.IME_ACTION_GO:
                enterKey.iconPreview = null;
                enterKey.icon = null;
                enterKey.label = resources.getText(R.string.label_go_key);
                break;
            case EditorInfo.IME_ACTION_NEXT:
                enterKey.iconPreview = null;
                enterKey.icon = null;
                enterKey.label = resources.getText(R.string.label_next_key);
                break;
            case EditorInfo.IME_ACTION_SEARCH:
                enterKey.icon = resources.getDrawable(R.drawable.sym_keyboard_search);
                enterKey.label = null;
                break;
            case EditorInfo.IME_ACTION_SEND:
                enterKey.iconPreview = null;
                enterKey.icon = null;
                enterKey.label = resources.getText(R.string.label_send_key);
                break;
            default:
                enterKey.icon = resources.getDrawable(R.drawable.sym_keyboard_return);
                enterKey.label = null;
                break;
        }
    }

    static class LatinKey extends Keyboard.Key {
        LatinKey(Resources res, Keyboard.Row parent, int x, int y, XmlResourceParser parser) {
            super(res, parent, x, y, parser);
        }

        @Override
        public boolean isInside(int x, int y) {
            return super.isInside(x, codes[0] == KEYCODE_CANCEL ? y - 10 : y);
        }
    }
}
