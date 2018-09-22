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
import com.codminskeyboards.universekeyboard.model.NewColorData;
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
    public static final String SELECTTEXTWALLPAPER = "selecttextwallpaper";
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
    public static int selecttextwallpaper = 0;
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

    public static String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqgyXolVCkrSdFsdembldwrpGHmXPSvvA7mdegRUzufvziVIS9JtVnGS20EbmFTKcPLzyfwoXPSNbwvmKHJg7RnoiqcrQ4QbtkhsHmMO7paA+akHFTPQGLHN6TW5invO33A3VBu/hxMTj9jHr9jr0tGJWj5cWITc2BkUfHcD8SFkSUca/ruQRJg3DTWMqMRqSnTeGccQJBRx+sCU8MxYlp3BwwOyvEdmeCFsnhPLHRmk3MXv/JgVr3oEQylakq3PkNvDVXbO5GHRYR8bKD2YXVZ+56FsCxT4t3sQXCQQ84zp1tKN/nFm9pDAlXqEf9T1MQFZVriBzI8XsZCraLoVrVwIDAQAB";
    private static ArrayList<NewColorData> newColorDataArrayList = new ArrayList<>();

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

    public static int[] textureArray = {
            R.drawable.theme1,
            R.drawable.theme2,
            R.drawable.theme3,
            R.drawable.theme4,
            R.drawable.theme5,
            R.drawable.theme6,
            R.drawable.theme7,
            R.drawable.theme8,
            R.drawable.theme9,
            R.drawable.theme10,
            R.drawable.theme11,
            R.drawable.theme12,
            R.drawable.theme13,
            R.drawable.theme14,
            R.drawable.theme15,
            R.drawable.theme16,
            R.drawable.theme17,
            R.drawable.theme18,
            R.drawable.theme19,
            R.drawable.theme20,
            R.drawable.theme21,
            R.drawable.theme22,
            R.drawable.theme23,
            R.drawable.theme24,
            R.drawable.theme25,
            R.drawable.theme26,
            R.drawable.theme27,
            R.drawable.theme28,
            R.drawable.theme29,
            R.drawable.theme30,
            R.drawable.theme31,
            R.drawable.theme32,
            R.drawable.theme33,
            R.drawable.theme34,
            R.drawable.theme35,
            R.drawable.theme36,
    };

    public GlobalClass(Context context) {
        preferences = context.getSharedPreferences(context.getPackageName(), 0);
        editor = preferences.edit();

        newColorDataArrayList.add(new NewColorData(0, R.color.one, true, false));
        newColorDataArrayList.add(new NewColorData(1, R.color.two, false, false));
        newColorDataArrayList.add(new NewColorData(2, R.color.three, false, false));
        newColorDataArrayList.add(new NewColorData(3, R.color.four, false, false));
        newColorDataArrayList.add(new NewColorData(4, R.color.five, false, false));
        newColorDataArrayList.add(new NewColorData(5, R.color.six, false, false));
        newColorDataArrayList.add(new NewColorData(6, R.color.seven, false, false));
        newColorDataArrayList.add(new NewColorData(7, R.color.eight, false, false));
        newColorDataArrayList.add(new NewColorData(8, R.color.nine, false, false));
        newColorDataArrayList.add(new NewColorData(9, R.color.ten, false, false));
        newColorDataArrayList.add(new NewColorData(10, R.color.eleven, false, false));
        newColorDataArrayList.add(new NewColorData(11, R.color.twelve, false, false));
        newColorDataArrayList.add(new NewColorData(12, R.color.thirteen, false, false));
        newColorDataArrayList.add(new NewColorData(13, R.color.fourteen, false, false));
        newColorDataArrayList.add(new NewColorData(14, R.color.fifteen, false, false));
        newColorDataArrayList.add(new NewColorData(15, R.color.sixteen, false, false));
        newColorDataArrayList.add(new NewColorData(16, R.color.seventeen, false, false));
        newColorDataArrayList.add(new NewColorData(17, R.color.eighteen, false, false));
        newColorDataArrayList.add(new NewColorData(18, R.color.nineteen, false, false));
        newColorDataArrayList.add(new NewColorData(19, R.color.twenty, false, false));
        newColorDataArrayList.add(new NewColorData(20, R.color.twentyone, false, false));
        newColorDataArrayList.add(new NewColorData(21, R.color.twentytwo, false, false));
        newColorDataArrayList.add(new NewColorData(22, R.color.twentythree, false, false));
        newColorDataArrayList.add(new NewColorData(23, R.color.twentyfour, false, false));
        newColorDataArrayList.add(new NewColorData(24, R.color.twentyfive, false, false));
        newColorDataArrayList.add(new NewColorData(25, R.color.twentysix, false, false));
        newColorDataArrayList.add(new NewColorData(26, R.color.twentyseven, false, false));
        newColorDataArrayList.add(new NewColorData(27, R.color.twentyeight, false, true));
        newColorDataArrayList.add(new NewColorData(28, R.color.twentynine, false, true));
        newColorDataArrayList.add(new NewColorData(29, R.color.thirty, false, true));
        newColorDataArrayList.add(new NewColorData(30, R.color.thirtyone, false, true));
        newColorDataArrayList.add(new NewColorData(31, R.color.thirtyone, false, true));
        newColorDataArrayList.add(new NewColorData(32, R.color.thirtytwo, false, true));
        newColorDataArrayList.add(new NewColorData(33, R.color.thirtythree, false, true));
        newColorDataArrayList.add(new NewColorData(34, R.color.thirtyfour, false, true));
        newColorDataArrayList.add(new NewColorData(36, R.color.thirtysix, false, true));
        newColorDataArrayList.add(new NewColorData(37, R.color.thirtyseven, false, true));
        newColorDataArrayList.add(new NewColorData(38, R.color.thirtyeight, false, true));
        newColorDataArrayList.add(new NewColorData(39, R.color.thirtynine, false, true));
        newColorDataArrayList.add(new NewColorData(40, R.color.fourty, false, true));
        newColorDataArrayList.add(new NewColorData(41, R.color.fourtyone, false, true));
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

    public static int[] texArray = {
            R.drawable.t_1,
            R.drawable.t_2,
            R.drawable.t_3,
            R.drawable.t_4,
            R.drawable.t_5,
            R.drawable.t_6,
            R.drawable.t_7,
            R.drawable.t_8,
            R.drawable.t_9,
            R.drawable.t_10,
            R.drawable.t_11,
            R.drawable.t_12,
            R.drawable.t_13,
            R.drawable.t_14,
            R.drawable.t_15,
            R.drawable.t_16,
            R.drawable.t_17,
            R.drawable.t_18,
            R.drawable.t_19,
            R.drawable.t_20,
            R.drawable.t_21,
            R.drawable.t_22,
            R.drawable.t_23,
            R.drawable.t_24,
            R.drawable.t_25,
            R.drawable.t_26,
            R.drawable.t_27,
            R.drawable.t_28,
            R.drawable.t_29,
            R.drawable.t_30,
            R.drawable.t_31,
            R.drawable.t_32,
            R.drawable.t_33,
            R.drawable.t_34,
            R.drawable.t_35,
            R.drawable.t_36,
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


