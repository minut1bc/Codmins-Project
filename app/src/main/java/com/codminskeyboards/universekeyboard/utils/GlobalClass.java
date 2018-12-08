package com.codminskeyboards.universekeyboard.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.provider.Settings;
import android.support.v4.content.res.ResourcesCompat;
import android.util.DisplayMetrics;
import android.view.inputmethod.InputMethodManager;

import com.codminskeyboards.universekeyboard.R;
import com.codminskeyboards.universekeyboard.customkeyboard.SoftKeyboard;
import com.google.android.gms.ads.InterstitialAd;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class GlobalClass {

//    // for In-app Purchase
//
//    // (arbitrary) request code for the purchase flow
//    public static final int RC_REQUEST_REMOVE_AD = 501;
//    public static final int RC_REQUEST_TEXUAL_COLOR_BG = 502;
//    public static final int RC_REQUEST_THEMES_SLOTES = 503;
//    public static final int RC_REQUEST_COLORS = 504;
//    public static final int RC_REQUEST_FONTS = 505;
//    public static final int RC_REQUEST_SOUNDS = 506;
//
//
//    public static String REMOVE_AD = "universekeyboard.inapp.removead";
//    public static String UNLOCK_TEXUAL_COLOR_BG = "universekeyboard.inapp.texualcolorbg";
//    public static String UNLOCK_THEMES_SLOTES = "universekeyboard.inapp.theamslotes";
//    public static String UNLOCK_COLORS = "universekeyboard.inapp.colors";
//    public static String UNLOCK_FONTS = "universekeyboard.inapp.fonts";
//    public static String UNLOCK_SOUNDS = "universekeyboard.inapp.sounds";
//
//    public static String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqgyXolVCkrSdFsdembldwrpGHmXPSvvA7mdegRUzufvziVIS9JtVnGS20EbmFTKcPLzyfwoXPSNbwvmKHJg7RnoiqcrQ4QbtkhsHmMO7paA+akHFTPQGLHN6TW5invO33A3VBu/hxMTj9jHr9jr0tGJWj5cWITc2BkUfHcD8SFkSUca/ruQRJg3DTWMqMRqSnTeGccQJBRx+sCU8MxYlp3BwwOyvEdmeCFsnhPLHRmk3MXv/JgVr3oEQylakq3PkNvDVXbO5GHRYR8bKD2YXVZ+56FsCxT4t3sQXCQQ84zp1tKN/nFm9pDAlXqEf9T1MQFZVriBzI8XsZCraLoVrVwIDAQAB";

    private static SharedPreferences.Editor editor;
    private static SharedPreferences preferences;

    public static final String KEYBOARD_POSITION = "keyboardPosition";

    public static int keyboardPosition = 0;

    private static int countAds = 0;

    public static int[] backgroundArray;
    public static int[] backgroundPreviewArray;
    public static int[] colorsArray;
    public static Typeface[] fontsArray;
    public static int[] soundsArray;

    public static ArrayList<KeyboardData> keyboardDataArray = new ArrayList<>();

    public static String key_isBackgroundLock = "isBackgroundLock";
    public static String key_isFontLock = "isFontLock";
    public static String key_isColorLock = "isColorLock";
    public static String key_isAdLock = "isAdLock";
    public static String key_isSoundLock = "isSoundLock";

    private static InterstitialAd interstitialAd;

    public GlobalClass(Context context) {
        preferences = context.getSharedPreferences(context.getPackageName(), 0);
        editor = preferences.edit();

        if (getPreferencesArrayList(context) != null) {
            keyboardDataArray = getPreferencesArrayList(context);
        }

        keyboardPosition = getPreferencesInt(context, KEYBOARD_POSITION, 0);
    }

    public static void checkStartAd() {
        countAds++;
        if (countAds > 9) {
            countAds = 0;
            if (interstitialAd.isLoaded())
                interstitialAd.show();
        }
    }

    public static void setInterstitialAd(InterstitialAd interstitialAd) {
        GlobalClass.interstitialAd = interstitialAd;
    }

    public static void setPreferencesInt(Context context, String key, int value) {
        editor.putInt(key, value);
        editor.apply();
    }

    public static int getPreferencesInt(Context context, String key, int defValue) {
        return preferences.getInt(key, defValue);
    }

    public static void setPreferencesBool(Context context, String key, boolean value) {
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static boolean getPreferencesBool(Context context, String key, boolean defValue) {
        return preferences.getBoolean(key, defValue);
    }

    public static void setPreferencesArrayList(Context context, ArrayList<KeyboardData> keyboardDataArrayList) {
        StringBuilder keyboardArrayList = new StringBuilder();

        for (KeyboardData keyboardData : keyboardDataArrayList) {
            keyboardArrayList.append(KeyboardData.serialize(keyboardData)).append(";");
        }

        editor.putString("keyboardArrayList", keyboardArrayList.toString());
        editor.apply();
    }

    public static ArrayList<KeyboardData> getPreferencesArrayList(Context context) {
        ArrayList<KeyboardData> keyboardDataArrayList = new ArrayList<>();

        String savedKeyboardArray = preferences.getString("keyboardArrayList", "");

        String[] keyboardsStrings = savedKeyboardArray.split(";");

        if (!savedKeyboardArray.equals("")) {
            for (String aSomething : keyboardsStrings) {
                keyboardDataArrayList.add(KeyboardData.deserialize(aSomething));
            }
        }

        return keyboardDataArrayList;
    }

    public static boolean isKeyboardEnabled(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            return inputMethodManager.getEnabledInputMethodList().toString().contains(context.getPackageName());
        }
        return false;
    }

    public static boolean isKeyboardSet(Context context) {
        return new ComponentName(context, SoftKeyboard.class)
                .equals(ComponentName.unflattenFromString(Settings.Secure
                        .getString(context.getContentResolver(), "default_input_method")));
    }

    public static void printLog(String tag, String strMessage) {
        // Log.e("----------------- ----:" + tag, "-----" + strMessage);
    }

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return (int) dpWidth / 60;      // TODO: maybe change hardcoded 60 to value dependent on given view
    }

    public static void setResourcesArrays(Context context) {
        Field[] fields = R.drawable.class.getFields();
        backgroundArray = new int[fields.length];
        backgroundPreviewArray = new int[fields.length];

        Pattern backgroundReg = Pattern.compile("background_[0-9]([0-9])?");
        Pattern prevReg = Pattern.compile("background_preview_[0-9]([0-9])?");
        Pattern colorReg = Pattern.compile("color_[0-9][0-9]?");

        int j = 0;
        int k = 0;
        for (Field field : fields) {
            if (backgroundReg.matcher(field.getName()).matches()) {
                try {
                    backgroundArray[j] = field.getInt(field);
                    j++;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            if (prevReg.matcher(field.getName()).matches()) {
                try {
                    backgroundPreviewArray[k] = field.getInt(field);
                    k++;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        int[] tempArray = new int[j];
        System.arraycopy(backgroundArray, 0, tempArray, 0, j);
        backgroundArray = tempArray.clone();

        tempArray = new int[k];
        System.arraycopy(backgroundPreviewArray, 0, tempArray, 0, k);
        backgroundPreviewArray = tempArray.clone();

        j = 0;
        fields = R.color.class.getFields();
        colorsArray = new int[fields.length];
        for (Field field : fields) {
            if (colorReg.matcher(field.getName()).matches())
                try {
                    colorsArray[j] = field.getInt(field);
                    j++;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
        }

        tempArray = new int[j];
        System.arraycopy(colorsArray, 0, tempArray, 0, j);
        colorsArray = tempArray.clone();

        fields = R.font.class.getFields();
        fontsArray = new Typeface[fields.length];
        for (int i = 0; i < fields.length; i++) {
            try {
                fontsArray[i] = ResourcesCompat.getFont(context, fields[i].getInt(fields[i]));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        fields = R.raw.class.getFields();
        soundsArray = new int[fields.length];
        for (int i = 0; i < soundsArray.length; i++) {
            try {
                soundsArray[i] = fields[i].getInt(fields[i]);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}