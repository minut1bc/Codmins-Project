package com.codminskeyboards.universekeyboard.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.util.DisplayMetrics;
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

    public static final String KEYBOARD_BACKGROUND = "keyboardBackground";

    public static final String KEY_COLOR = "keyColor";
    public static final String KEY_RADIUS = "keyRadius";
    public static final String KEY_OPACITY = "keyOpacity";
    public static final String KEY_STROKE = "keyStroke";

    public static final String FONT_COLOR = "fontColor";
    public static final String FONT_NAME = "fontId";

    public static final String SOUND_STATUS = "soundStatus";
    public static final String SOUND_ID = "soundId";

    public static final String BACKGROUND_POSITION = "backgroundPosition";
    public static final String COLOR_POSITION = "colorPosition";
    public static final String DRAWABLE_OR_COLOR = "drawableOrColor";
    public static final String VIBRATION_VALUE = "vibrationValue";

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

    public static int fontColor = 0;
    public static int fontId = R.font.abel_regular;
    public static boolean soundStatus = false;
    public static int soundId = 0;
    public static int keyboardBackground = 0;
    public static int keyColor = 0;
    public static int keyOpacity = 0;
    public static int keyStroke = 0;
    public static int keyRadius = 0;
    public static int colorPosition = 0;
    public static int backgroundPosition = 0;
    public static int keyColorPosition = 1;
    public static int soundPosition = 0;
    public static int fontPosition = 0;
    public static int fontColorPosition = 1;
    private static int countAds = 0;

    public static String key_isWallPaperLock = "isWallPaperLock";
    public static String key_isFontLock = "isFontLock";
    public static String key_isColorLock = "isColorLock";
    public static String key_isAdLock = "isAdLock";
    public static String key_isSoundLock = "isSoundLock";
    public static String key_isThemeLock = "isThemeLock";

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

    public static void setPreferencesInt(Context context, String key, int value) {
        editor.putInt(key, value);
        editor.commit();
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

    public static ArrayList<KeyboardData> getPreferencesArrayList(Context context) {
        Gson gson = new Gson();
        String json = preferences.getString("keyboardArrayList", "");
        Type type = new TypeToken<ArrayList<KeyboardData>>() {
        }.getType();

        ArrayList<KeyboardData> keyboardDataArrayList = gson.fromJson(json, type);

        return keyboardDataArrayList;
    }

    public static boolean isKeyboardEnabled(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null)
            return inputMethodManager.getEnabledInputMethodList().toString().contains(context.getPackageName());
        return false;
    }

    public static boolean isKeyboardSet(Context context) {
        return new ComponentName(context, SoftKeyboard.class)
                .equals(ComponentName.unflattenFromString(Settings.Secure.getString(context.getContentResolver(), "default_input_method")));
    }

    public static void printLog(String tag, String strMessage) {
        // Log.e("----------------- ----:" + tag, "-----" + strMessage);
    }

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return (int) dpWidth / 60;      // TODO: maybe change hardcoded 60 to value dependent on given view
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

    public static int[] fontsArray = {
            R.font.abel_regular,
            R.font.abraham_lincoln,
            R.font.american_typewriter_bold_android,
            R.font.angelina,
            R.font.bitter_regular,
            R.font.blackout_midnight,
            R.font.blanch_caps,
            R.font.blanch_caps_inline,
            R.font.bushcraft_distress,
            R.font.cabinsketch_bold,
            R.font.capsuula,
            R.font.chezvous_regular,
            R.font.comic_zine_ot,
            R.font.curely_free_typeface,
            R.font.doppialinea,
            R.font.droidiga,
            R.font.droidsans,
            R.font.edo,
            R.font.fff_tusj_bold,
            R.font.font_pack_sketchnote_square,
            R.font.ftanchoryard_regular,
            R.font.gooddog_cool,
            R.font.gothic,
            R.font.gothic_bold,
            R.font.gothicb,
            R.font.gothicbi,
            R.font.gothicci,
            R.font.grutchshaded,
            R.font.handygeorge_2,
            R.font.intro_inline,
            R.font.langdon,
            R.font.linden_hill,
            R.font.lora_bold,
            R.font.love_of_love_by_oubyc,
            R.font.matesc_regular,
            R.font.monday_medium,
            R.font.newscycle_regular,
            R.font.oldstyle_1,
            R.font.orbitron_black,
            R.font.radley_regular,
            R.font.ribbon_v2_2011,
            R.font.sevillana_regular,
            R.font.sketchetik_light,
            R.font.sofia_regular,
            R.font.trend_hm_slab_five,
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

}


