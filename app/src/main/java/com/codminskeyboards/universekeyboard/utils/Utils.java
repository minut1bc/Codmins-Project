package com.codminskeyboards.universekeyboard.utils;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.codminskeyboards.universekeyboard.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.xmlpull.v1.XmlPullParser;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;

public class Utils {

    private static final int KEYCODE_SHIFT_OFF = -1;
    private static final int KEYCODE_SPACE = 32;
    private static ArrayList<String> BackupTemplatsArray;
    private static int CurrentFontStyle;
    private static int CurrentLang;
    public static final int EMAIL_CODE = -5242;
    private static int DynamicKeyboardHeight;
    public static final int KEYCODE_ALPHABETS = -2830;
    public static final int KEYCODE_ALPHABETS1 = -2831;
    public static final int KEYCODE_ART = -5020;
    public static final int KEYCODE_BACK = -2256;
    public static final int KEYCODE_CHOOSE = -2254;
    public static final int KEYCODE_CLEARALL = -2252;
    public static final int KEYCODE_COPY = -2260;
    public static final int KEYCODE_CUT = -2261;
    public static final int KEYCODE_DELETE = -2264;
    public static final int KEYCODE_EMOJI = -5000;
    public static final int KEYCODE_END = -2259;
    public static final int KEYCODE_GIF = -3500;
    public static final int KEYCODE_HOME = -2257;
    public static final int KEYCODE_MOVEDOWN = -2258;
    public static final int KEYCODE_MOVELEFT = -2253;
    public static final int KEYCODE_MOVERIGHT = -2255;
    public static final int KEYCODE_MOVEUP = -2251;
    public static final int KEYCODE_NUMBERS = -6002;
    public static final int KEYCODE_NUMBERS1 = -6003;
    public static final int KEYCODE_PASTE = -2262;
    public static final int KEYCODE_SELECTALLTEXT = -2250;
    private static int DynamicKeyboardHeightLandScape;
    private static int HourNotification;
    public static final int KEYCODE_SYMBOLS = -1762;
    public static final int KEYCODE_SYMBOLS1 = -1763;
    public static final int KEYCODE_SYMBOLS2 = -1764;
    public static final int KEYCODE_SYMBOLS21 = -1765;
    public static final int KEYCODE_TAB = -2263;
    public static final int KEYCODE_TMP = 978907;
    private static int MinuteNotification = 0;
    public static final int NEXT_GO1 = -97890;
    public static final int NEXT_GO2 = -9789001;
    public static final int NEXT_GO3 = -972550;
    private static int NOTIFICATION_ID = 0;
    private static int Noti_ID = 0;
    public static final int PREVIOUS_GO0 = -978901;
    public static final int PREVIOUS_GO1 = -978902;
    public static final int PREVIOUS_GO2 = -9789020;
    public static final int SHIFT_CODE = -978903;
    public static final int START = -99255;
    public static final int STOP = -97255;
    private static int SecondNotification;
    private static int SelectedFancyFont;
    private static ArrayList<String> SuggestionData;
    private static boolean SuggestionView;
    private static ArrayList<String> SuggestionWords;
    private static String SwipeWords;
    private static String THEME_PREFS;
    private static ArrayList<String> TemplatsArray;
    private static ArrayList<Integer> addViewPosition;
    private static long afterthisdayShowNotificationForPotrait;
    private static String appDataPath;
    private static String[] arrayStr;
    private static String backupPath;
    private static ArrayList<String> backupWord;
    private static int checkheight;
    private static View commonView;
    private static int defaultBgColor;
    private static boolean deleteFlg;
    private static boolean dictionaryisLoad;
    public static boolean editorisOpen;
    private static boolean emojisupport;
    private static String fb_link;
    private static int fisrtCharCode;
    private static boolean fiveminNofication;
    private static int flg_lang_change;
    private static int folderNo;
    private static ArrayList<String> fontFromAsset;
    private static String[] fontFromAsset1;
    private static int f59h;
    private static int hintColorCode;
    private static long iferaseNoficationThenCallMilliSecond;
    private static boolean isApplyFancy;
    private static boolean isAutoSpellEnable;
    private static boolean isCapsOn;
    private static boolean isColorCodeChange;
    private static boolean isComeFromBootingActivity;
    private static boolean isContactOpen;
    private static boolean isContactReadPermission;
    public static boolean isFromBoot;
    private static boolean isLandScapePhotoSet;
    private static boolean isMoving;
    private static boolean isPhotoSet;
    private static boolean isPreviewEnabled;
    private static boolean isSearchOpen;
    private static boolean isSoundOn;
    private static boolean isStatic;
    private static boolean isSwipe;
    private static boolean isUpHoneycomb;
    private static boolean isVibrateOn;
    private static boolean islandscapebgcolorchange;
    private static boolean ispotraitbgcolorchange;
    private static int keypadtextSize;
    private static ArrayList<String> langueges;
    private static int lastCharCode;
    private static String lastPackName;
    private static float mFxVolume;
    //    public static KeypadAdapter mainAdapter;
    private static ArrayList<String> msgAppPackageName;
    private static String[] msgapp;
    private static long onDayMilisecond;
    private static String onlineSelectedThemePackageName;
    private static boolean onlineThemeSelected;
    private static boolean online_flg;
    private static boolean packageisLoad;
    private static String[] packages;
    public static PopupWindow popup;
    private static boolean popupAnim;
    public static PopupWindow popupnew;
    private static int previewX;
    private static int previewY;
    private static int progress;
    private static int progressDefault;
    private static int progressDefaultLand;
    private static ArrayList<String> resulttemp;
    private static String rootPath;
    private static String selectLangName;
    private static String selectedCateGory;
    private static int selectedLang;
    private static int selectedOnlineThemeNo;
    private static int selectedThemeNo;
    private static int setDefaultProgress;
    private static int setDefaultProgressLandScape;
    private static long sevendayMillisecond;
    private static ArrayList<String> socialPackages;
    private static String storePath;
    private static int suggestiontextsize;
    private static int swipeColorCode;
    private static boolean swipeEnable;
    private static int[] swipe_colorCodes;
    private static String tempTemplateItem;
    private static boolean templateActivityisOpen;
    private static int textColorCode;
    private static Timer timer;
    public static boolean tmpDelFlag;
    private static int tmp_flg;
    public static boolean tmpdeletefalg;
    private static int totalOnlineThemes;
    private static int transparentKey;
    private static int transparentTopbg;
    private static int f60w;
    private static boolean wordExist;
    public TextView tt;

    static {
        fb_link = "https://www.facebook.com/pages/My-Photo-Keyboard/897404793664732?skip_nax_wizard=true&ref_type=logout_gear";
        editorisOpen = false;
        f60w = 0;
        f59h = 0;
        tmpdeletefalg = false;
        emojisupport = false;
        NOTIFICATION_ID = 1193131;
        isComeFromBootingActivity = false;
        suggestiontextsize = 18;
        fiveminNofication = false;
        isLandScapePhotoSet = false;
        timer = new Timer();
        keypadtextSize = 0;
        addViewPosition = new ArrayList();
        onDayMilisecond = 86400000;
        iferaseNoficationThenCallMilliSecond = 86400000;
        afterthisdayShowNotificationForPotrait = System.currentTimeMillis();
        sevendayMillisecond = 518400000;
        HourNotification = 20;
        MinuteNotification = 59;
        SecondNotification = 59;
        Noti_ID = 1;
        packageisLoad = false;
        flg_lang_change = 0;
        tmp_flg = 0;
        online_flg = false;
        selectedLang = 0;
        CurrentLang = 0;
        CurrentFontStyle = 0;
        THEME_PREFS = "THEME_PREFS";
        selectLangName = "English";
        langueges = new ArrayList();
        SuggestionWords = new ArrayList(25000);
        SelectedFancyFont = 0;
        isApplyFancy = false;
        mFxVolume = 0.3f;
        selectedCateGory = "category0";
        progress = 10;
        SuggestionView = true;
        isSoundOn = true;
        isVibrateOn = false;
        isPreviewEnabled = true;
        isAutoSpellEnable = true;
        isCapsOn = true;
        deleteFlg = false;
        selectedThemeNo = 0;
        selectedOnlineThemeNo = 0;
        commonView = null;
        dictionaryisLoad = false;
        isPhotoSet = false;
        isSearchOpen = false;
        isContactOpen = false;
        isContactReadPermission = false;
        DynamicKeyboardHeight = KEYCODE_SHIFT_OFF;
        setDefaultProgress = KEYCODE_SHIFT_OFF;
        progressDefault = 0;
        progressDefaultLand = 0;
        DynamicKeyboardHeightLandScape = KEYCODE_SHIFT_OFF;
        setDefaultProgressLandScape = KEYCODE_SHIFT_OFF;
        previewX = 0;
        previewY = 0;
        checkheight = 0;
        isSwipe = false;
        isMoving = false;
        SwipeWords = null;
        fisrtCharCode = 0;
        lastCharCode = 0;
        swipeColorCode = KEYCODE_SHIFT_OFF;
        lastPackName = XmlPullParser.NO_NAMESPACE;
        templateActivityisOpen = false;
        backupWord = new ArrayList();
        fontFromAsset1 = new String[]{"Default", "font/style1.ttf", "font/style2.ttf", "font/style3.ttf", "font/style4.ttf", "font/style5.ttf", "font/style6.ttf", "font/style7.ttf", "font/style8.ttf", "font/style9.otf", "font/style10.ttf", "font/style11.ttf", "font/style12.ttf", "font/style13.ttf", "font/style14.ttf", "font/style15.ttf", "font/style16.ttf", "font/style17.ttf", "font/style18.ttf", "font/style19.ttf", "font/style20.ttf", "font/style21.ttf", "font/style22.otf", "font/style23.otf", "font/style24.otf", "font/style25.ttf", "font/style26.ttf"};
        defaultBgColor = ViewCompat.MEASURED_STATE_MASK;
        ispotraitbgcolorchange = false;
        islandscapebgcolorchange = false;
        transparentKey = MotionEventCompat.ACTION_MASK;
        transparentTopbg = MotionEventCompat.ACTION_MASK;
        fontFromAsset = new ArrayList();
        packages = new String[]{"com.whatsapp", "com.facebook.orca", "com.snapchat.android", "com.google.android.talk", "jp.naver.line.android", "com.viber.voip", "com.skype.raider", "com.twitter.android", "com.bsb.hike"};
        socialPackages = new ArrayList(Arrays.asList(packages));
        msgapp = new String[]{"com.google.android.apps.messaging", "com.jb.gosms", "com.concentriclivers.mms.com.android.mms", "fr.slvn.mms", "com.android.mms", "com.sonyericsson.conversations", "com.asus.message"};
        msgAppPackageName = new ArrayList(Arrays.asList(msgapp));
        tempTemplateItem = XmlPullParser.NO_NAMESPACE;
        swipe_colorCodes = new int[]{-16752540, -14521120, -16757440, -16742021, -8825528, -12434878, -16757440, -14521120, -14521120, -14521120, -14983648, -2818048, -16752540, -14273992, -720809, -14606047, -12434878, -12434878, -16540683, -14606047, -14606047};
        wordExist = true;
        SuggestionData = null;
        arrayStr = new String[]{"Hello! How are you?", "Very well thank you, and you?", "I am fine.", "I am glad to see you.", "Come in please.", "Please have something cold.", "Come for a walk please.", "I'll be glad to do so.", "I have heard a lot about you.", "Look who is it?", "Are you surprised to see me?", "Ok see you again.", "Must you go now?", "Have a pleasant journey.", "God bless you.", "May luck be with you.", "Please convey my regards to father.", "I was there but returned last week.", "Its been a long time since we met.", "Thanks a lot.", "Thanks for your advice.", "Thanks for your invitation.", "I'm very grateful to you.", "Thanks for the gift.", "This is very costly.", "You are very kind.", "Not at all, it's my pleasure.", "Wish you a happy new year.", "Hartley felicitation on your birthday.", "Many many happy returns of the day.", "Congratulations on your success.", "Congratulations on your wedding.", "Wish you all the best.", "I won't be able to come.", "I don't want to come.", "I am sorry to refuse.", "They won't agree to this.", "It's not possible.", "It can't be arranged.", "She does not like it.", "How can I disobey you?", "I won't be able to do as you wish.", "You don't agree with me, would you?", "How to Make other people believe", "Don't you believe it?", "It's impossible.", "It's only a rumour.", "It's only a hearsay rumour.", "You can fully rely on them.", "I have full faith in him.", "Please wait.", "Please come back.", "Let it be.", "Please come here.", "Please reply.", "Please wake him up.", "Hope to hear from you.", "Will you do me a favour?", "Let me work", "Let them relax.", "Will you please open the door?", "Please give me a pencil and paper.", "Can you see me day after tomorrow?", "Could I ask you to move a little?", "You don't forget to write me, will you?", "All are requested to reach in time.", "Please do come day after tomorrow."};
        TemplatsArray = new ArrayList();
        BackupTemplatsArray = new ArrayList();
        resulttemp = null;
        textColorCode = KEYCODE_SHIFT_OFF;
        hintColorCode = KEYCODE_SHIFT_OFF;
        totalOnlineThemes = 5;
        isStatic = true;
    }


    public static String getRealPathFromURI(Context c, Uri contentURI) {
        Cursor cursor = c.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        }
        cursor.moveToFirst();
        String result = cursor.getString(cursor.getColumnIndex("_data"));
        cursor.close();
        return result;
    }

    public static String getPathFromUriLolipop(Context c, Uri selectedImage) {
        Cursor cursor = c.getContentResolver().query(selectedImage, new String[]{"_data"}, null, null, null);
        assert cursor != null;
        int columnindex = cursor.getColumnIndexOrThrow("_data");
        cursor.moveToFirst();
        String file_path = cursor.getString(columnindex);
        cursor.close();
        return file_path;
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            int halfHeight = height / 2;
            int halfWidth = width / 2;
            while (halfHeight / inSampleSize > reqHeight && halfWidth / inSampleSize > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public static ArrayList<String> getSuggestion(String word) {
        try {
            int i;
            if (word.length() != 1 && !word.equals(XmlPullParser.NO_NAMESPACE)) {
                ArrayList<String> result = SuggestionData;
                SuggestionData = null;
                SuggestionData = new ArrayList();
                if (result != null) {
                    for (i = 0; i < result.size(); i++) {
                        if (result.get(i).toLowerCase().startsWith(word.toLowerCase())) {
                            SuggestionData.add(result.get(i));
                        }
                        if (!wordExist) {
                            break;
                        }
                    }
                }
                if (SuggestionData.size() <= 0) {
                    for (i = 0; i < SuggestionWords.size(); i++) {
                        if (SuggestionWords.get(i).toLowerCase().startsWith(word.toLowerCase())) {
                            SuggestionData.add(SuggestionWords.get(i));
                        }
                        if (!wordExist) {
                            break;
                        }
                    }
                }
            } else {
                SuggestionData = null;
                SuggestionData = new ArrayList();
                for (i = 0; i < SuggestionWords.size(); i++) {
                    if (SuggestionWords.get(i).toLowerCase().startsWith(word.toLowerCase())) {
                        SuggestionData.add(SuggestionWords.get(i));
                    }
                    if (!wordExist) {
                        break;
                    }
                }
            }
            wordExist = SuggestionData.size() > 0;
            return SuggestionData;
        } catch (Exception e) {
            Log.d("main", "Exxxxxxxxxxxxxxxxxx11111111");
            return new ArrayList();
        }
    }

    public static Method getMethod(Class<?> targetClass, String name, Class<?>... parameterTypes) {
        Method method = null;
        if (!(targetClass == null || TextUtils.isEmpty(name))) {
            try {
                method = targetClass.getMethod(name, parameterTypes);
            } catch (SecurityException ignored) {
            } catch (NoSuchMethodException ignored) {
            }
        }
        return method;
    }

    public static Object invoke(Object receiver, Object defaultValue, Method method, Object... args) {
        if (method != null) {
            try {
                defaultValue = method.invoke(receiver, args);
            } catch (Exception e) {
               // Log.e("Exception", "Exception in invoke: " + e.getClass().getSimpleName());
            }
        }
        return defaultValue;
    }

    public static void setPhoto(Context c, int val) {
//        unsetAlarm(c);
        DisplayMetrics dm = c.getResources().getDisplayMetrics();
        f60w = dm.widthPixels;
        f59h = dm.heightPixels;
        File file = new File(String.valueOf(c.getFilesDir().getAbsolutePath()) + "/keyboard_image.png");
        try {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            Bitmap bmp = BitmapFactory.decodeStream(c.getAssets().open("background/" + c.getAssets().list("background")[val]), new Rect(0, 0, 0, 0), opts);
            opts.inSampleSize = calculateInSampleSize(opts, f60w, (int) c.getResources().getDimension(R.dimen.keyboard_height));
            opts.inJustDecodeBounds = false;
            Bitmap.createScaledBitmap(BitmapFactory.decodeStream(c.getAssets().open("background/" + c.getAssets().list("background")[val]), new Rect(0, 0, 0, 0), opts), f60w, (int) c.getResources().getDimension(R.dimen.keyboard_height), false).compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(file));
        } catch (Exception ignored) {
        }
    }

    public static int DpToPx(Context c, int dp) {
        return (int) TypedValue.applyDimension(1, (float) dp, c.getResources().getDisplayMetrics());
    }

    private static void ADDTempWordAsType() {
        for (String anArrayStr : arrayStr) {
            if (!TemplatsArray.contains(anArrayStr)) {
                TemplatsArray.add(anArrayStr);
            }
        }
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int pxToDp(float px) {
        return (int) (px / (((float) Resources.getSystem().getDisplayMetrics().densityDpi) / 160.0f));
    }

    public static int dpToPx(int dp) {
        return (int) (((float) dp) * Resources.getSystem().getDisplayMetrics().density);
    }

    public static ArrayList<String> getListTemplats(String wordTemp) {
        ArrayList<String> result = new ArrayList();
        for (int i = 0; i < TemplatsArray.size(); i++) {
            if (TemplatsArray.get(i).toLowerCase().startsWith(wordTemp.toLowerCase())) {
                result.add(TemplatsArray.get(i));
            }
        }
        return result;
    }

    public static ArrayList<String> getSwipeSuggestion(String word, String last) {
        ArrayList<String> result = new ArrayList();
        int i = 0;
        while (i < SuggestionWords.size()) {
            try {
                if (word.length() < 1 || last.length() < 1) {
                    if (word.length() < 1 || last.length() > 0) {
                        if (SuggestionWords.get(i).toLowerCase().endsWith(last.toLowerCase())) {
                            result.add(SuggestionWords.get(i));
                        }
                    } else if (SuggestionWords.get(i).toLowerCase().startsWith(word.toLowerCase())) {
                        result.add(SuggestionWords.get(i));
                    }
                } else if (SuggestionWords.get(i).toLowerCase().startsWith(word.toLowerCase()) && SuggestionWords.get(i).toLowerCase().endsWith(last.toLowerCase())) {
                    result.add(SuggestionWords.get(i));
                }
                i++;
            } catch (Exception e) {
                Log.d("main", "Exxxxxxxxxxxxxxxxxx");
            }
        }
        return result;
    }

    @TargetApi(11)
    public static void setStaticVariables(Context c) {
        isUpHoneycomb = true;
        DisplayMetrics dm = c.getResources().getDisplayMetrics();
        f60w = dm.widthPixels;
        f59h = dm.heightPixels;
        SharedPreferences prefs = c.getSharedPreferences(THEME_PREFS, 1);
        rootPath = c.getFilesDir().getAbsolutePath();
        appDataPath = Environment.getExternalStorageDirectory() + "/Android/data/" + c.getPackageName();
        packageisLoad = prefs.getBoolean("pkgload", false);
        SuggestionView = prefs.getBoolean("suggestionEnable", true);
        isSoundOn = prefs.getBoolean("soundEnable", true);
        emojisupport = prefs.getBoolean("emojisupport", false);
        isVibrateOn = prefs.getBoolean("vibEnable", false);
        isPreviewEnabled = prefs.getBoolean("prevEnable", true);
        isAutoSpellEnable = prefs.getBoolean("isAutoSpellEnable", true);
        isCapsOn = prefs.getBoolean("capsEnable", true);
//        progress = prefs.getInt(NotificationCompatApi21.CATEGORY_PROGRESS, 10);
        SelectedFancyFont = prefs.getInt("SelectedFancyFont", 0);
        isContactReadPermission = prefs.getBoolean("isContactReadPermission", false);
        keypadtextSize = prefs.getInt("keypadtextSize", 0);
        storePath = prefs.getString("storePath", String.valueOf(c.getFilesDir().getAbsolutePath()) + "/gifdata");
        backupPath = storePath + "/Dictionary/";
        File backup = new File(backupPath);
        if (!backup.exists()) {
            backup.mkdirs();
        }
        File myphotoFolder = new File(storePath);
//        if (!myphotoFolder.exists()) {
//            myphotoFolder.mkdirs();
//            if (isUpHoneycomb) {
//                new StickerStoreTask(c).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
//            } else {
//                new StickerStoreTask(c).execute(new Void[0]);
//            }
//        }
        mFxVolume = prefs.getFloat("soundLevel", 0.3f);
        DynamicKeyboardHeight = prefs.getInt("keyboardHeight", KEYCODE_SHIFT_OFF);
        progressDefault = prefs.getInt("progressDefault", 0);
        DynamicKeyboardHeightLandScape = prefs.getInt("keyboardHeightLand", KEYCODE_SHIFT_OFF);
        progressDefaultLand = prefs.getInt("progressDefaultLand", 0);
        totalOnlineThemes = prefs.getInt("totalOnlineThemes", 5);
        onlineThemeSelected = prefs.getBoolean("onlineThemeSelected", false);
        isPhotoSet = prefs.getBoolean("isPhotoSet", false);
        isStatic = prefs.getBoolean("staticTheme", true);
        popupAnim = prefs.getBoolean("popupAnim", false);
        swipeEnable = prefs.getBoolean("swipeEnable", true);
        onlineSelectedThemePackageName = prefs.getString("onlineSelectedThemePackageName", XmlPullParser.NO_NAMESPACE);
        swipeColorCode = prefs.getInt("swipeColorCode", KEYCODE_SHIFT_OFF);
        textColorCode = prefs.getInt("textColorCode", KEYCODE_SHIFT_OFF);
        hintColorCode = prefs.getInt("hintColorCode", KEYCODE_SHIFT_OFF);
        CurrentFontStyle = prefs.getInt("CurrFontStyle", 0);
        defaultBgColor = prefs.getInt("defaultBgColor", ViewCompat.MEASURED_STATE_MASK);
        ispotraitbgcolorchange = prefs.getBoolean("ispotraitbgcolorchange", false);
        islandscapebgcolorchange = prefs.getBoolean("islandscapebgcolorchange", false);
        transparentKey = prefs.getInt("KeyTrans", MotionEventCompat.ACTION_MASK);
        transparentTopbg = prefs.getInt("transparentTopbg", MotionEventCompat.ACTION_MASK);
        langueges = new ArrayList();
//        langueges = getArray("SelectedLanguages", prefs);
        CurrentLang = prefs.getInt("CurrLang", 0);
        selectedLang = prefs.getInt("SelectLang", 0);
        folderNo = prefs.getInt("folderPosition", 0);
        selectLangName = prefs.getString("SelectLangName", "English");
        Set<String> tempSet = new HashSet();
        if (TemplatsArray.size() <= 0) {
            ADDTempWordAsType();
        }
        isColorCodeChange = prefs.getBoolean("isColorCodeChange", false);
        tempSet.addAll(TemplatsArray);
        if (isUpHoneycomb) {
            Set<String> set = prefs.getStringSet("templates", tempSet);
            TemplatsArray = null;
            TemplatsArray = new ArrayList(set);
        } else {
            String arrayString = prefs.getString("templates", TextUtils.join(",", tempSet));
            TemplatsArray = null;
            TemplatsArray = new ArrayList(Arrays.asList(TextUtils.split(arrayString, ",")));
        }
        Set<String> backuptempSet = new HashSet();
//        if (isUpHoneycomb) {
//            set = prefs.getStringSet("BackupTemplatsArray", backuptempSet);
//            BackupTemplatsArray = null;
//            BackupTemplatsArray = new ArrayList(set);
//        } else {
//            arrayString = prefs.getString("BackupTemplatsArray", TextUtils.join(",", backuptempSet));
//            BackupTemplatsArray = null;
//            BackupTemplatsArray = new ArrayList(Arrays.asList(TextUtils.split(arrayString, ",")));
//        }
        isLandScapePhotoSet = prefs.getBoolean("isLandScapePhotoSet", false);
        suggestiontextsize = prefs.getInt("suggetiontextsize", 18);
        flg_lang_change = prefs.getInt("flg_lang_change", 0);
//        FancyFont.FancyFontList = getArrayFancy("FancyFontList", prefs);
//        fontFromAsset = getArrayFont("allfonts", prefs);
//        try {
//            FancyFont.stringarr = (ArrayList) ObjectSerializer.deserialize(prefs.getString("FancyList", ObjectSerializer.serialize(FancyFont.stringarr)));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        EmojiUtils.heart_unicode = getmojiArt("heart_unicode", prefs);
    }

    public static ArrayList<String> getArray(String key, SharedPreferences prefs) {
        ArrayList<String> array = new ArrayList();
        String jArrayString = prefs.getString(key, "NOPREFSAVED");
        if (jArrayString.matches("NOPREFSAVED")) {
            Log.d("main", "lang:  No prefs");
            return getDefaultArray();
        }
        try {
            JSONArray jArray = new JSONArray(jArrayString);
            for (int i = 0; i < jArray.length(); i++) {
                array.add(jArray.getString(i));
                Log.d("main", "lang: " + jArray.getString(i));
            }
            return array;
        } catch (JSONException e) {
            Log.d("main", "Json exception");
            return getDefaultArray();
        }
    }

//    public static ArrayList<String> getArrayFancy(String key, SharedPreferences prefs) {
//        ArrayList<String> array = new ArrayList();
//        String jArrayString = prefs.getString(key, "NOPREFSAVED");
//        if (jArrayString.matches("NOPREFSAVED")) {
//            Log.d("main", "lang:  No prefs");
//            return getDefaultArrayFancy();
//        }
//        try {
//            JSONArray jArray = new JSONArray(jArrayString);
//            for (int i = 0; i < jArray.length(); i++) {
//                array.add(jArray.getString(i));
//                Log.d("main", "lang: " + jArray.getString(i));
//            }
//            return array;
//        } catch (JSONException e) {
//            Log.d("main", "Json exception");
//            return getDefaultArrayFancy();
//        }
//    }
//
//    public static ArrayList<String> getmojiArt(String key, SharedPreferences prefs) {
//        ArrayList<String> array = new ArrayList();
//        String jArrayString = prefs.getString(key, "NOPREFSAVED");
//        if (jArrayString.matches("NOPREFSAVED")) {
//            Log.d("main", "lang:  No prefs");
//            return getDefaultEmojiArt();
//        }
//        try {
//            JSONArray jArray = new JSONArray(jArrayString);
//            for (int i = 0; i < jArray.length(); i++) {
//                array.add(jArray.getString(i));
//                Log.d("main", "lang: " + jArray.getString(i));
//            }
//            return array;
//        } catch (JSONException e) {
//            Log.d("main", "Json exception");
//            return getDefaultEmojiArt();
//        }
//    }

    public static ArrayList<String> getArrayFont(String key, SharedPreferences prefs) {
        ArrayList<String> array = new ArrayList();
        String jArrayString = prefs.getString(key, "NOPREFSAVED");
        if (jArrayString.matches("NOPREFSAVED")) {
            Log.d("main", "lang:  No prefs");
            return getDefaultArrayFont();
        }
        try {
            JSONArray jArray = new JSONArray(jArrayString);
            for (int i = 0; i < jArray.length(); i++) {
                array.add(jArray.getString(i));
                Log.d("main", "lang: " + jArray.getString(i));
            }
            return array;
        } catch (JSONException e) {
            Log.d("main", "Json exception");
            return getDefaultArrayFont();
        }
    }

    private static ArrayList<String> getDefaultArrayFont() {
        return new ArrayList();
    }

//    private static ArrayList<String> getDefaultArrayFancy() {
//        return FancyFont.FancyFontList;
//    }

    private static ArrayList<String> getDefaultArray() {
        ArrayList<String> tmplist = new ArrayList();
        tmplist.add("English.0");
        tmplist.add("English(AZERTY).1");
        tmplist.add("English(QWERTZ).2");
        return tmplist;
    }

//    private static ArrayList<String> getDefaultEmojiArt() {
//        return new ArrayList(Arrays.asList(EmojiUtils.heart_unicode1));
//    }

    public static float dp2px(Resources resources, float dp) {
        return (dp * resources.getDisplayMetrics().density) + 0.5f;
    }

    public static float sp2px(Resources resources, float sp) {
        return sp * resources.getDisplayMetrics().scaledDensity;
    }

    public static boolean inPrivateImeOptions(String packageName, String key, EditorInfo editorInfo) {
        if (editorInfo == null) {
            return false;
        }
        if (packageName != null) {
            key = String.valueOf(packageName) + "." + key;
        }
        return containsInCsv(key, editorInfo.privateImeOptions);
    }

    private static boolean containsInCsv(String key, String csv) {
        if (csv == null) {
            return false;
        }
        for (String option : csv.split(",")) {
            if (option.equals(key)) {
                return true;
            }
        }
        return false;
    }

    public static int pxFromDp(Context context, float dp) {
        return (int) (context.getResources().getDisplayMetrics().density * dp);
    }

//    public static void unsetAlarm(Context c) {
//        ((AlarmManager) c.getSystemService(NotificationCompatApi21.CATEGORY_ALARM)).cancel(PendingIntent.getService(c, 0, new Intent(c, ShowNotification.class), 0));
//        Log.v("StopAlaram", "cancelling notification");
//    }
//
//    public static void startAlarm(Context c) {
//        Calendar calendar = new GregorianCalendar();
//        calendar.set(11, 0);
//        calendar.set(12, 0);
//        calendar.set(13, 0);
//        Intent myIntent = new Intent(c, MyNewNotifReceiver.class);
//        myIntent.putExtra("fromAlarm", true);
//        myIntent.putExtra("isOnline", onlineThemeSelected);
//        ((AlarmManager) c.getSystemService(NotificationCompatApi21.CATEGORY_ALARM)).setRepeating(1, calendar.getTimeInMillis(), 86400000, PendingIntent.getBroadcast(c, 0, myIntent, 0));
//    }

//    public static void stopAlarm(Context c) {
//        ((AlarmManager) c.getSystemService(NotificationCompatApi21.CATEGORY_ALARM)).cancel(PendingIntent.getBroadcast(c, 0, new Intent(c, MyNewNotifReceiver.class), 0));
//    }

//    public static void getMobileData(Context c) {
//        String[] splitdata;
//        int i;
//        try {
//            Cursor cur = c.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
//            if (cur.getCount() > 0) {
//                while (cur.moveToNext()) {
//                    String name = cur.getString(cur.getColumnIndex(Contact.NAME));
//                    if (name.contains(" ")) {
//                        splitdata = name.split(" ");
//                        for (i = 0; i < splitdata.length; i++) {
//                            if (!SuggestionWords.contains(splitdata[i])) {
//                                SuggestionWords.add(splitdata[i]);
//                            }
//                        }
//                    } else if (!SuggestionWords.contains(name)) {
//                        SuggestionWords.add(name);
//                    }
//                }
//            }
//        } catch (Exception e) {
//        }
//        try {
//            Cursor cursor = c.getContentResolver().query(UserDictionary.Words.CONTENT_URI, null, null, null, null);
//            while (cursor.moveToNext()) {
//                String word = cursor.getString(cursor.getColumnIndex("word"));
//                if (word.contains(" ")) {
//                    splitdata = word.split(" ");
//                    for (i = 0; i < splitdata.length; i++) {
//                        if (!SuggestionWords.contains(splitdata[i])) {
//                            SuggestionWords.add(splitdata[i]);
//                        }
//                    }
//                } else if (!SuggestionWords.contains(word)) {
//                    SuggestionWords.add(word);
//                }
//            }
//        } catch (Exception e2) {
//        }
//    }

    public static boolean isEnglishCharacter() {
        return CurrentLang == 0 || CurrentLang == 1 || CurrentLang == 2 || CurrentLang == 4 || CurrentLang == 5 || CurrentLang == 6 || CurrentLang == 7 || CurrentLang == 8 || CurrentLang == 9 || CurrentLang == 10 || CurrentLang == 11 || CurrentLang == 12 || CurrentLang == 14 || CurrentLang == 15 || CurrentLang == 18 || CurrentLang == 19 || CurrentLang == 20 || CurrentLang == 24 || CurrentLang == 25 || CurrentLang == 26 || CurrentLang == 28 || CurrentLang == 29 || CurrentLang == 30 || CurrentLang == 31 || CurrentLang == KEYCODE_SPACE || CurrentLang == 33 || CurrentLang == 34 || CurrentLang == 35 || CurrentLang == 36 || CurrentLang == 38 || CurrentLang == 39 || CurrentLang == 40 || CurrentLang == 42 || CurrentLang == 47 || CurrentLang == 48;
    }

    public static boolean isEnglish() {
        return CurrentLang == 0 || CurrentLang == 1 || CurrentLang == 2 || CurrentLang == 5 || CurrentLang == 6 || CurrentLang == 7 || CurrentLang == 9 || CurrentLang == 10 || CurrentLang == 11 || CurrentLang == 18 || CurrentLang == 19 || CurrentLang == 20 || CurrentLang == 24 || CurrentLang == 25 || CurrentLang == 28 || CurrentLang == 29 || CurrentLang == 33 || CurrentLang == 30 || CurrentLang == 34 || CurrentLang == 36 || CurrentLang == 42;
    }

    public static String getCurrentProcess(Context context) {
        try {
            if (Build.VERSION.SDK_INT >= 21) {
                return getProcessMoreThanLol(context);
            }
            return getProcessOld(context);
        } catch (Exception e) {
            return XmlPullParser.NO_NAMESPACE;
        }
    }

    private static String getProcessMoreThanLol(Context context) {
        ActivityManager.RunningAppProcessInfo currentInfo = null;
        Field field = null;
        try {
            field = ActivityManager.RunningAppProcessInfo.class.getDeclaredField("processState");
        } catch (Exception ignored) {
        }
        for (ActivityManager.RunningAppProcessInfo app : ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getRunningAppProcesses()) {
            if (app.importance == 100 && app.importanceReasonCode == 0) {
                Integer state = null;
                try {
                    assert field != null;
                    state = field.getInt(app);
                } catch (Exception ignored) {
                }
                if (state != null && state == 2) {
                    currentInfo = app;
                    break;
                }
            }
        }
        assert currentInfo != null;
        return currentInfo.processName;
    }

    private static String getProcessOld(Context context) {
        List<ActivityManager.RunningTaskInfo> runningTask = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getRunningTasks(1);
        if (runningTask != null) {
            return runningTask.get(0).topActivity.getPackageName();
        }
        return null;
    }

}
