package com.codminskeyboards.universekeyboard.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.ConnectivityManager;
import android.provider.Settings;
import android.view.inputmethod.InputMethodManager;

import com.codminskeyboards.universekeyboard.R;
import com.codminskeyboards.universekeyboard.customkeyboard.SoftKeyboard;
import com.codminskeyboards.universekeyboard.model.KeyboardData;
import com.google.android.gms.ads.InterstitialAd;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class GlobalClass {

    private static SharedPreferences.Editor editor;
    private static SharedPreferences preferences;

    public static final String KEYBOARD_BG_IMAGE = "keyboardBgImage";
    public static final String KEYBOARD_COLOR_CODE = "keyboardColorCode";

    public static final String KEY_BG_COLOR = "keyBgColor";
    public static final String KEY_RADIUS = "keyRadius";
    public static final String KEY_OPACITY = "keyOpacity";
    public static final String KEY_STROKE = "keyStroke";

    public static final String FONT_COLOR = "fontColor";
    public static final String FONT_NAME = "fontName";

    public static final String SOUND_STATUS = "soundStatus";
    public static final String SOUND_NAME = "soundName";

    public static final String SELECTWALLPAPER = "backgroundPosition";
    public static final String SELECTCOLOR = "colorPosition";
    public static final String SELECTVIEW = "selectview";

    // for In-app Purchase

    // (arbitrary) request code for the purchase flow
    public static final int RC_REQUEST_REMOVE_AD = 501;
    public static final int RC_REQUEST_TEXUAL_COLOR_BG = 502;
    public static final int RC_REQUEST_THEMES_SLOTES = 503;
    public static final int RC_REQUEST_COLORS = 504;
    public static final int RC_REQUEST_FONTS = 505;
    public static final int RC_REQUEST_SOUNDS = 506;

    public static int drawableOrColor = 0;

    public static String REMOVE_AD = "universekeyboard.inapp.removead";
    public static String UNLOCK_TEXUAL_COLOR_BG = "universekeyboard.inapp.texualcolorbg";
    public static String UNLOCK_THEMES_SLOTES = "universekeyboard.inapp.theamslotes";
    public static String UNLOCK_COLORS = "universekeyboard.inapp.colors";
    public static String UNLOCK_FONTS = "universekeyboard.inapp.fonts";
    public static String UNLOCK_SOUNDS = "universekeyboard.inapp.sounds";

    public static String fontColor = null;
    public static String fontName = null;
    public static boolean soundStatus = false;
    public static int soundId = 0;
    public static int keyboardBackground = 0;
    public static int tempKeyboardColorCode = 0;
    public static int keyColor = 0;
    public static int keyOpacity = 0;
    public static int keyStroke = 0;
    public static int keyRadius = 0;
    public static int colorPosition = 0;
    public static int backgroundPosition = 0;
    public static int selectbgcolor = 7;
    public static int selectsounds = 0;
    public static int selectfonts = 0;
    public static int fontColorPosition = 1;
    private static int countAds = 0;

    public static String key_isWallPaperLock = "isWallPaperLock";
    public static String key_isFontLock = "isFontLock";
    public static String key_isColorLock = "isColorLock";
    public static String key_isAdLock = "isAdLock";
    public static String key_isSoundLock = "isSoundLock";
    public static String key_isThemeLock = "isThemeLock";
    public static String vibrationStrength = "vibrationStrength";

    public static String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqgyXolVCkrSdFsdembldwrpGHmXPSvvA7mdegRUzufvziVIS9JtVnGS20EbmFTKcPLzyfwoXPSNbwvmKHJg7RnoiqcrQ4QbtkhsHmMO7paA+akHFTPQGLHN6TW5invO33A3VBu/hxMTj9jHr9jr0tGJWj5cWITc2BkUfHcD8SFkSUca/ruQRJg3DTWMqMRqSnTeGccQJBRx+sCU8MxYlp3BwwOyvEdmeCFsnhPLHRmk3MXv/JgVr3oEQylakq3PkNvDVXbO5GHRYR8bKD2YXVZ+56FsCxT4t3sQXCQQ84zp1tKN/nFm9pDAlXqEf9T1MQFZVriBzI8XsZCraLoVrVwIDAQAB";

    private static InterstitialAd interstitialAd;

    public GlobalClass(Context context, InterstitialAd interstitialAd) {
        GlobalClass.interstitialAd = interstitialAd;

        preferences = context.getSharedPreferences(context.getPackageName(), 0);
        editor = preferences.edit();
    }

    public static void checkStartAd() {
        countAds++;
        if (countAds > 9) {
            countAds = 0;
            if (interstitialAd.isLoaded())
                interstitialAd.show();
        }

    }

    public GlobalClass(Context context) {
        preferences = context.getSharedPreferences(context.getPackageName(), 0);
        editor = preferences.edit();
    }

    public static void setPreferencesString(Context context, String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public static void setPreferencesInt(Context context, String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public static String getPreferencesString(Context context, String key, String defValue) {
        return preferences.getString(key, defValue);
    }

    public static int getPreferencesInt(Context context, String key, int defValue) {
        return preferences.getInt(key, defValue);
    }

    public static void setPreferencesBool(Context context, String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getPreferencesBool(Context context, String key, boolean defValue) {
        return preferences.getBoolean(key, defValue);
    }

    public static void setPreferencesArrayList(Context context, ArrayList<KeyboardData> keyboardDataArrayList) {
        Gson gson = new Gson();
        String json = gson.toJson(keyboardDataArrayList);
        editor.putString("keyboardArrayList", json);
        editor.commit();
    }

    public static int[] backgroundArray = {
            R.drawable.background_1,
            R.drawable.background_2,
            R.drawable.background_3,
            R.drawable.background_4,
            R.drawable.background_5,
            R.drawable.background_6,
            R.drawable.background_7,
            R.drawable.background_8,
            R.drawable.background_9,
            R.drawable.background_10,
            R.drawable.background_11,
            R.drawable.background_12,
            R.drawable.background_13,
            R.drawable.background_14,
            R.drawable.background_15,
            R.drawable.background_16,
            R.drawable.background_17,
            R.drawable.background_18,
            R.drawable.background_19,
            R.drawable.background_20,
            R.drawable.background_21,
            R.drawable.background_22,
            R.drawable.background_23,
            R.drawable.background_24,
            R.drawable.background_25,
            R.drawable.background_26,
            R.drawable.background_27,
            R.drawable.background_28,
            R.drawable.background_29,
            R.drawable.background_30,
            R.drawable.background_31,
            R.drawable.background_32,
            R.drawable.background_33,
    };
    public static int[] backgroundPreviewArray = {
            R.drawable.tc_1,
            R.drawable.tc_2,
            R.drawable.tc_3,
            R.drawable.tc_4,
            R.drawable.tc_5,
            R.drawable.tc_6,
            R.drawable.tc_7,
            R.drawable.tc_8,
            R.drawable.tc_9,
            R.drawable.tc_10,
            R.drawable.tc_11,
            R.drawable.tc_12,
            R.drawable.tc_13,
            R.drawable.tc_14,
            R.drawable.tc_15,
            R.drawable.tc_16,
            R.drawable.tc_17,
            R.drawable.tc_18,
            R.drawable.tc_19,
            R.drawable.tc_20,
            R.drawable.tc_21,
            R.drawable.tc_22,
            R.drawable.tc_23,
            R.drawable.tc_24,
            R.drawable.tc_25,
            R.drawable.tc_26,
            R.drawable.tc_27,
            R.drawable.tc_28,
            R.drawable.tc_29,
            R.drawable.tc_30,
            R.drawable.tc_31,
            R.drawable.tc_32,
            R.drawable.tc_33,
    };
    public static int[] colorsArray = {
            R.color.one,
            R.color.two,
            R.color.three,
            R.color.four,
            R.color.five,
            R.color.six,
            R.color.seven,
            R.color.eight,
            R.color.nine,
            R.color.ten,
            R.color.eleven,
            R.color.twelve,
            R.color.thirteen,
            R.color.fourteen,
            R.color.fifteen,
            R.color.sixteen,
            R.color.seventeen,
            R.color.eighteen,
            R.color.nineteen,
            R.color.twenty,
            R.color.twentyOne,
            R.color.twentyTwo,
            R.color.twentyThree,
            R.color.twentyFour,
            R.color.twentyFive,
            R.color.twentySix,
            R.color.twentySeven,
            R.color.twentyEight,
            R.color.twentyNine,
            R.color.thirty,
            R.color.thirtyOne,
            R.color.thirtyTwo,
            R.color.thirtyThree,
            R.color.thirtyFour,
            R.color.thirtyFive,
            R.color.thirtySix,
            R.color.thirtySeven,
            R.color.thirtyEight,
            R.color.thirtyNine,
            R.color.forty,
    };
    public static int[] soundsArray = {
            R.raw.balloon_snap,
            R.raw.bike_gear_shift,
            R.raw.close_cigarette_lighter,
            R.raw.double_down_click,
            R.raw.double_switch,
            R.raw.fast_camera_shutter,
            R.raw.glovebox_open,
            R.raw.heavy_switch_click,
            R.raw.heavy_switch_spring,
            R.raw.keydesign,
            R.raw.latch_lock,
            R.raw.latch_snap,
            R.raw.latch_spring,
            R.raw.lever_spring,
            R.raw.loose_click,
            R.raw.medium_camera_shutter,
            R.raw.metal_off_switch,
            R.raw.mouse_click,
            R.raw.pull_grenade_pin,
            R.raw.pull_switch,
            R.raw.ratchet,
            R.raw.rotary_switch,
            R.raw.seatbelt,
            R.raw.slide_click,
            R.raw.switch_breaker,
            R.raw.switch_flick,
            R.raw.thunk_switch,
            R.raw.tight_click,
            R.raw.toggle_switch,
            R.raw.typewriter_key,
    };

    public static void printLog(String tag, String strMessage) {
        // Log.e("----------------- ----:" + tag, "-----" + strMessage);
    }

    public static boolean isInternetOn(Context context) {
        ConnectivityManager connec = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connec != null;
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
            return true;
        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {
            return false;
        }
        return false;
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = 50;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    public static ArrayList<KeyboardData> getPreferencesArrayList(Context context) {

        Gson gson = new Gson();
        String json = preferences.getString("keyboardArrayList", "");
        Type type = new TypeToken<ArrayList<KeyboardData>>() {
        }.getType();

        ArrayList<KeyboardData> keyboardDataArrayList = gson.fromJson(json, type);

        return keyboardDataArrayList;
    }

    public static boolean KeyboardIsEnabled(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null)
            return inputMethodManager.getEnabledInputMethodList().toString().contains(context.getPackageName());
        return false;
    }

    public static boolean KeyboardIsSet(Context context) {
        try {
            return new ComponentName(context.getApplicationContext(), SoftKeyboard.class)
                    .equals(ComponentName.unflattenFromString(Settings.Secure.getString(context.getApplicationContext().getContentResolver(), "default_input_method")));
        } catch (Exception e) {
            return false;
        }
    }

}


