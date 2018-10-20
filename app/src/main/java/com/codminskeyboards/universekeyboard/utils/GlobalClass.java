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
import com.codminskeyboards.universekeyboard.activity.CreateKeyboardActivity;
import com.codminskeyboards.universekeyboard.customkeyboard.SoftKeyboard;
import com.codminskeyboards.universekeyboard.model.KeyboardData;
import com.google.android.gms.ads.AdRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class GlobalClass {

    private static SharedPreferences.Editor editor;
    private static SharedPreferences preferences;
    private static int cntads = 0;

    public static final String IS_COLOR = "isColor";
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

    public static final String SELECTWALLPAPER = "selectwallpaper";
    public static final String SELECTCOLOR = "selectcolor";
    public static final String SELECTVIEW = "selectview";
    public static final String KEYBOARDBITMAPBACK = "keyboardbitmapback";

    // for In-app Purchase

    // (arbitrary) request code for the purchase flow
    public static final int RC_REQUEST_REMOVE_AD = 501;
    public static final int RC_REQUEST_TEXUAL_COLOR_BG = 502;
    public static final int RC_REQUEST_THEMES_SLOTES = 503;
    public static final int RC_REQUEST_COLORS = 504;
    public static final int RC_REQUEST_FONTS = 505;
    public static final int RC_REQUEST_SOUNDS = 506;

    public static int selview = 2;

    public static String REMOVE_AD = "universekeyboard.inapp.removead";
    public static String UNLOCK_TEXUAL_COLOR_BG = "universekeyboard.inapp.texualcolorbg";
    public static String UNLOCK_THEMES_SLOTES = "universekeyboard.inapp.theamslotes";
    public static String UNLOCK_COLORS = "universekeyboard.inapp.colors";
    public static String UNLOCK_FONTS = "universekeyboard.inapp.fonts";
    public static String UNLOCK_SOUNDS = "universekeyboard.inapp.sounds";

    public static String tempIsColor = null;
    public static String tempKeyRadius = null;
    public static String tempKeyStroke = null;
    public static String tempKeyOpacity = null;
    public static String tempFontColor = null;
    public static String tempFontName = null;
    public static String tempSoundStatus = null;
    public static int tempSoundName = 0;
    public static int tempKeyboardBgImage = 0;
    public static int tempKeyboardColorCode = 0;
    public static int tempKeyColor = 0;
    public static int selectcolor = 0;
    public static int selectwallpaper = 0;
    public static int selectbgcolor = 7;
    public static int selectsounds = 0;
    public static int selectfonts = 0;
    public static int selectfontcolor = 1;
    public static String keyboardBitmapBack = null;

    public static String key_isWallPaperLock = "isWallPaperLock";
    public static String key_isFontLock = "isFontLock";
    public static String key_isColorLock = "isColorLock";
    public static String key_isAdLock = "isAdLock";
    public static String key_isSoundLock = "isSoundLock";
    public static String key_isThemeLock = "isThemeLock";

    public static String vibrationStrength = "vibrationStrength";

    public static String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqgyXolVCkrSdFsdembldwrpGHmXPSvvA7mdegRUzufvziVIS9JtVnGS20EbmFTKcPLzyfwoXPSNbwvmKHJg7RnoiqcrQ4QbtkhsHmMO7paA+akHFTPQGLHN6TW5invO33A3VBu/hxMTj9jHr9jr0tGJWj5cWITc2BkUfHcD8SFkSUca/ruQRJg3DTWMqMRqSnTeGccQJBRx+sCU8MxYlp3BwwOyvEdmeCFsnhPLHRmk3MXv/JgVr3oEQylakq3PkNvDVXbO5GHRYR8bKD2YXVZ+56FsCxT4t3sQXCQQ84zp1tKN/nFm9pDAlXqEf9T1MQFZVriBzI8XsZCraLoVrVwIDAQAB";

    public static void checkStartAd() {
        cntads++;
        if (cntads >= 10) {
            cntads = 0;
            if (CreateKeyboardActivity.getInstance() != null && CreateKeyboardActivity.getInstance().mInterstitialAd != null) {
                CreateKeyboardActivity.getInstance().mInterstitialAd.loadAd(new AdRequest.Builder().build());
                CreateKeyboardActivity.getInstance().strtaDS();
                CreateKeyboardActivity.getInstance().setAdMob();
            }
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

    public static void setPrefrenceBoolean(Context context, String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getPrefrenceBoolean(Context context, String key, boolean defValue) {
        return preferences.getBoolean(key, defValue);
    }

    public static String getPrefrenceString(Context context, String key, String defValue) {
        return preferences.getString(key, defValue);
    }

    public static void setPreferencesArrayList(Context context, ArrayList<KeyboardData> keyboardDataArrayList) {
        Gson gson = new Gson();
        String json = gson.toJson(keyboardDataArrayList);
        editor.putString("keyboardArrayList", json);
        editor.commit();
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
        return ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).getEnabledInputMethodList().toString().contains(context.getPackageName());
    }

    public static boolean KeyboardIsSet(Context context) {
        try {
            return new ComponentName(context.getApplicationContext(), SoftKeyboard.class).equals(ComponentName.unflattenFromString(Settings.Secure.getString(context.getApplicationContext().getContentResolver(), "default_input_method")));
        } catch (Exception e) {
            return false;
        }
    }

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

    public static int[] thumbArray = {
            R.drawable.theme_color1,
            R.drawable.theme_color2,
            R.drawable.theme_color3,
            R.drawable.theme_color4,
            R.drawable.theme_color5,
            R.drawable.theme_color6,
            R.drawable.theme_color7,
            R.drawable.theme_color8,
            R.drawable.theme_color9,
            R.drawable.theme_color10,
            R.drawable.theme_color11,
            R.drawable.theme_color12,
            R.drawable.theme_color13,
            R.drawable.theme_color14,
            R.drawable.theme_color15,
            R.drawable.theme_color16,
            R.drawable.theme_color17,
            R.drawable.theme_color18,
            R.drawable.theme_color19,
            R.drawable.theme_color20,
            R.drawable.theme_color21,
            R.drawable.theme_color22,
            R.drawable.theme_color23,
            R.drawable.theme_color24,
            R.drawable.theme_color25,
            R.drawable.theme_color26,
            R.drawable.theme_color27,
            R.drawable.theme_color28,
            R.drawable.theme_color29,
            R.drawable.theme_color30,
            R.drawable.theme_color31,
            R.drawable.theme_color32,
            R.drawable.theme_color33,
    };

    public static int[] thArray = {
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

    public static int[] lessonClips = {
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

    public static int[] colorsHorizontal = {
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
            R.color.twentyone,
            R.color.twentytwo,
            R.color.twentythree,
            R.color.twentyfour,
            R.color.twentyfive,
            R.color.twentysix,
            R.color.twentyseven,
            R.color.twentyeight,
            R.color.twentynine,
            R.color.thirty,
            R.color.thirtyone,
            R.color.thirtytwo,
            R.color.thirtythree,
            R.color.thirtyfour,
            R.color.thirtysix,
            R.color.thirtyseven,
            R.color.thirtyeight,
            R.color.thirtynine,
            R.color.fourty,
            R.color.fourtyone,
    };

}


