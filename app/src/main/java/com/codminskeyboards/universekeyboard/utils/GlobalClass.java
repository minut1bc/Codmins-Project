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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class GlobalClass {

    private static SharedPreferences.Editor editor;
    private static SharedPreferences preferences;

    public static final String BACKGROUND_IS_DRAWABLE = "backgroundIsDrawable";
    public static final String BACKGROUND_POSITION = "backgroundPosition";
    public static final String BACKGROUND_COLOR_POSITION = "backgroundColorPosition";
    public static final String KEY_RADIUS = "keyRadius";
    public static final String KEY_STROKE = "keyStroke";
    public static final String KEY_OPACITY = "keyOpacity";
    public static final String KEY_COLOR_POSITION = "keyColorPosition";
    public static final String FONT_POSITION = "fontPosition";
    public static final String FONT_COLOR_POSITION = "fontColorPosition";
    public static final String VIBRATION_VALUE = "vibrationValue";
    public static final String SOUND_POSITION = "soundPosition";
    public static final String SOUND_ON = "soundOn";

    // for In-app Purchase

    // (arbitrary) request code for the purchase flow
    public static final int RC_REQUEST_REMOVE_AD = 501;
    public static final int RC_REQUEST_TEXUAL_COLOR_BG = 502;
    public static final int RC_REQUEST_THEMES_SLOTES = 503;
    public static final int RC_REQUEST_COLORS = 504;
    public static final int RC_REQUEST_FONTS = 505;
    public static final int RC_REQUEST_SOUNDS = 506;


    public static String REMOVE_AD = "universekeyboard.inapp.removead";
    public static String UNLOCK_TEXUAL_COLOR_BG = "universekeyboard.inapp.texualcolorbg";
    public static String UNLOCK_THEMES_SLOTES = "universekeyboard.inapp.theamslotes";
    public static String UNLOCK_COLORS = "universekeyboard.inapp.colors";
    public static String UNLOCK_FONTS = "universekeyboard.inapp.fonts";
    public static String UNLOCK_SOUNDS = "universekeyboard.inapp.sounds";

    public static boolean backgroundIsDrawable = true;
    public static int backgroundPosition = 0;
    public static int backgroundColorPosition = 0;
    public static int keyRadius = 0;
    public static int keyStroke = 0;
    public static int keyOpacity = 0;
    public static int keyColorPosition = 1;
    public static int fontPosition = 0;
    public static int fontColorPosition = 1;
    public static int vibrationValue = 0;
    public static int soundPosition = 0;
    public static boolean soundOn = false;

    private static int countAds = 0;

    public static int[] backgroundArray;
    public static int[] backgroundPreviewArray;
    public static int[] colorsArray;
    public static Typeface[] fontsArray;
    public static int[] soundsArray;

    public static ArrayList<KeyboardData> keyboardDataArray = new ArrayList<>();
    public static KeyboardData[] testKeyboardDataArray;

    public static String key_isBackgroundLock = "isBackgroundLock";
    public static String key_isFontLock = "isFontLock";
    public static String key_isColorLock = "isColorLock";
    public static String key_isAdLock = "isAdLock";
    public static String key_isSoundLock = "isSoundLock";

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
        Gson gson = new Gson();
        String json = gson.toJson(keyboardDataArrayList);
        editor.putString("keyboardArrayList", json);
        editor.apply();
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

    public static void setResourcesArrays(Context context) {
        Field[] fields = R.drawable.class.getFields();
        backgroundArray = new int[fields.length];
        backgroundPreviewArray = new int[fields.length];
        int j = 0;
        int k = 0;
        for (Field field : fields) {
            if (field.getName().matches("background_[0-9]([0-9])?")) {
                try {
                    backgroundArray[j] = field.getInt(field);
                    j++;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            if (field.getName().matches("background_preview_[0-9]([0-9])?")) {
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
            if (field.getName().matches("color_[0-9][0-9]?"))
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

//    public static int[] backgroundArray = {
//            R.drawable.background_01,
//            R.drawable.background_02,
//            R.drawable.background_03,
//            R.drawable.background_04,
//            R.drawable.background_05,
//            R.drawable.background_06,
//            R.drawable.background_07,
//            R.drawable.background_08,
//            R.drawable.background_09,
//            R.drawable.background_10,
//            R.drawable.background_11,
//            R.drawable.background_12,
//            R.drawable.background_13,
//            R.drawable.background_14,
//            R.drawable.background_15,
//            R.drawable.background_16,
//            R.drawable.background_17,
//            R.drawable.background_18,
//            R.drawable.background_19,
//            R.drawable.background_20,
//            R.drawable.background_21,
//            R.drawable.background_22,
//            R.drawable.background_23,
//            R.drawable.background_24,
//            R.drawable.background_25,
//            R.drawable.background_26,
//            R.drawable.background_27,
//            R.drawable.background_28,
//            R.drawable.background_29,
//            R.drawable.background_30,
//            R.drawable.background_31,
//            R.drawable.background_32,
//            R.drawable.background_33,
//    };
//
//    public static int[] backgroundPreviewArray = {
//            R.drawable.background_preview_01,
//            R.drawable.background_preview_02,
//            R.drawable.background_preview_03,
//            R.drawable.background_preview_04,
//            R.drawable.background_preview_05,
//            R.drawable.background_preview_06,
//            R.drawable.background_preview_07,
//            R.drawable.background_preview_08,
//            R.drawable.background_preview_09,
//            R.drawable.background_preview_10,
//            R.drawable.background_preview_11,
//            R.drawable.background_preview_12,
//            R.drawable.background_preview_13,
//            R.drawable.background_preview_14,
//            R.drawable.background_preview_15,
//            R.drawable.background_preview_16,
//            R.drawable.background_preview_17,
//            R.drawable.background_preview_18,
//            R.drawable.background_preview_19,
//            R.drawable.background_preview_20,
//            R.drawable.background_preview_21,
//            R.drawable.background_preview_22,
//            R.drawable.background_preview_23,
//            R.drawable.background_preview_24,
//            R.drawable.background_preview_25,
//            R.drawable.background_preview_26,
//            R.drawable.background_preview_27,
//            R.drawable.background_preview_28,
//            R.drawable.background_preview_29,
//            R.drawable.background_preview_30,
//            R.drawable.background_preview_31,
//            R.drawable.background_preview_32,
//            R.drawable.background_preview_33,
//    };
//
//    public static int[] colorsArray = {
//            R.color.color_1,
//            R.color.color_2,
//            R.color.color_03,
//            R.color.color_04,
//            R.color.color_05,
//            R.color.color_06,
//            R.color.color_07,
//            R.color.color_08,
//            R.color.color_09,
//            R.color.color_10,
//            R.color.color_11,
//            R.color.color_12,
//            R.color.color_13,
//            R.color.color_14,
//            R.color.color_15,
//            R.color.color_16,
//            R.color.color_17,
//            R.color.color_18,
//            R.color.color_19,
//            R.color.color_20,
//            R.color.color_21,
//            R.color.color_22,
//            R.color.color_23,
//            R.color.color_24,
//            R.color.color_25,
//            R.color.color_26,
//            R.color.color_27,
//            R.color.color_28,
//            R.color.color_29,
//            R.color.color_30,
//            R.color.color_31,
//            R.color.color_32,
//            R.color.color_33,
//            R.color.color_34,
//            R.color.color_35,
//            R.color.color_36,
//            R.color.color_37,
//            R.color.color_38,
//            R.color.color_39,
//            R.color.color_40,
//    };
//
//    public static int[] fontsArray = {
//            R.font.abel_regular,
//            R.font.abraham_lincoln,
//            R.font.american_typewriter_bold_android,
//            R.font.angelina,
//            R.font.bitter_regular,
//            R.font.blackout_midnight,
//            R.font.blanch_caps,
//            R.font.blanch_caps_inline,
//            R.font.bushcraft_distress,
//            R.font.cabinsketch_bold,
//            R.font.capsuula,
//            R.font.chezvous_regular,
//            R.font.comic_zine_ot,
//            R.font.curely_free_typeface,
//            R.font.doppialinea,
//            R.font.droidiga,
//            R.font.droidsans,
//            R.font.edo,
//            R.font.fff_tusj_bold,
//            R.font.font_pack_sketchnote_square,
//            R.font.ftanchoryard_regular,
//            R.font.gooddog_cool,
//            R.font.gothic,
//            R.font.gothic_bold,
//            R.font.gothicb,
//            R.font.gothicbi,
//            R.font.gothicci,
//            R.font.grutchshaded,
//            R.font.handygeorge_2,
//            R.font.intro_inline,
//            R.font.langdon,
//            R.font.linden_hill,
//            R.font.lora_bold,
//            R.font.love_of_love_by_oubyc,
//            R.font.matesc_regular,
//            R.font.monday_medium,
//            R.font.newscycle_regular,
//            R.font.oldstyle_1,
//            R.font.orbitron_black,
//            R.font.radley_regular,
//            R.font.ribbon_v2_2011,
//            R.font.sevillana_regular,
//            R.font.sketchetik_light,
//            R.font.sofia_regular,
//            R.font.trend_hm_slab_five,
//    };
//
//    public static int[] soundsArray = {
//            R.raw.balloon_snap,
//            R.raw.bike_gear_shift,
//            R.raw.close_cigarette_lighter,
//            R.raw.double_down_click,
//            R.raw.double_switch,
//            R.raw.fast_camera_shutter,
//            R.raw.glovebox_open,
//            R.raw.heavy_switch_click,
//            R.raw.heavy_switch_spring,
//            R.raw.keydesign,
//            R.raw.latch_lock,
//            R.raw.latch_snap,
//            R.raw.latch_spring,
//            R.raw.lever_spring,
//            R.raw.loose_click,
//            R.raw.medium_camera_shutter,
//            R.raw.metal_off_switch,
//            R.raw.mouse_click,
//            R.raw.pull_grenade_pin,
//            R.raw.pull_switch,
//            R.raw.ratchet,
//            R.raw.rotary_switch,
//            R.raw.seatbelt,
//            R.raw.slide_click,
//            R.raw.switch_breaker,
//            R.raw.switch_flick,
//            R.raw.thunk_switch,
//            R.raw.tight_click,
//            R.raw.toggle_switch,
//            R.raw.typewriter_key,
//    };

}


