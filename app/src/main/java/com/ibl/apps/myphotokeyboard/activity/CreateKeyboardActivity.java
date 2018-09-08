package com.ibl.apps.myphotokeyboard.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.ibl.apps.myphotokeyboard.BuildConfig;
import com.ibl.apps.myphotokeyboard.R;
import com.ibl.apps.myphotokeyboard.adapter.FillDefaultColorAdapter;
import com.ibl.apps.myphotokeyboard.adapter.FillFontColorAdapter;
import com.ibl.apps.myphotokeyboard.adapter.FillFontStyleAdapter;
import com.ibl.apps.myphotokeyboard.adapter.FillKeyBgAdapter;
import com.ibl.apps.myphotokeyboard.adapter.FillSoundEffectAdapter;
import com.ibl.apps.myphotokeyboard.adapter.FillWallpaperColorAdapter;
import com.ibl.apps.myphotokeyboard.adapter.FillWallpaperTextualAdapter;
import com.ibl.apps.myphotokeyboard.database.DatabaseHelper;
import com.ibl.apps.myphotokeyboard.model.FontsPaid;
import com.ibl.apps.myphotokeyboard.model.KeyboardData;
import com.ibl.apps.myphotokeyboard.model.NewSoundData;
import com.ibl.apps.myphotokeyboard.utils.AsyncDownload;
import com.ibl.apps.myphotokeyboard.utils.CustomTextViewBold;
import com.ibl.apps.myphotokeyboard.utils.GlobalClass;
import com.ibl.apps.myphotokeyboard.utils.MyBounceInterpolator_anim;
import com.ibl.apps.myphotokeyboard.utils.RecyclerItemClickListener;
import com.ibl.apps.myphotokeyboard.utils.Utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CreateKeyboardActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView ivHome;
    private ImageView ivWallpaper;
    private LinearLayout linWallpaper;
    private ImageView ivKeyDesign;
    private LinearLayout linKeyDesign;
    private ImageView ivFontStyle;
    private LinearLayout linFontStyle;
    private ImageView ivSoundEffect;
    private LinearLayout linSoundEffect;
    private CustomTextViewBold txtMainTitle;
    private CreateKeyboardActivity context;

    String[] fontArray = new String[0];
    private FillWallpaperTextualAdapter fillWallpaperTextualAdapter;
    private RecyclerView rvDefaultColor;
    private FillDefaultColorAdapter fillDefaultColorAdapter;
    private RecyclerView rvDefaultColorKeyDesign;
    private LinearLayout linWallpaperLayout;
    private LinearLayout linKeyDesignLayout;
    private RecyclerView rvDefaultColorFontStyle;
    private FillFontStyleAdapter fillFontStyleAdapter;
    private LinearLayout linFontStyleLayout;
    private FillSoundEffectAdapter fillSoundEffectAdapter;
    private LinearLayout linSoundEffectLayout;
    public static File mFileTemp;
    public static final String TEMP_PHOTO_FILE_NAME = "temp_photo.jpeg";
    public static final int FONT_RESULT_CODE = 100;
    public static final int RESULT_FROM_CAMERA = 99;
    public static final int RESULT_FROM_GALLERY = 98;
    static CreateKeyboardActivity createKeyboardActivity;
    private ImageView ivKeyboardBg;
    private CircleImageView radiusOne;
    private CircleImageView radiusTwo;
    private CircleImageView radiusThree;
    private CircleImageView radiusFour;
    private CircleImageView radiusFive;
    private CircleImageView ivOpacityHundred;
    private CircleImageView ivOpacitySeventyFive;
    private CircleImageView ivOpacityFifty;
    private CircleImageView ivOpacityTwentyFive;
    private CircleImageView ivOpacityZero;
    private LinearLayout linFirstRowKeyboard;
    private LinearLayout linTwoRowKeyboard;
    private LinearLayout linThreeRowKeyboard;
    private LinearLayout linFourRowKeyboard;
    private CircleImageView ivStrokeOne;
    private CircleImageView ivStrokeTwo;
    private CircleImageView ivStrokeThree;
    private CircleImageView ivStrokeFour;
    private CircleImageView ivStrokeFive;
    private ImageView ivDone;
    private ImageView ivShift;
    private ImageView ivCancel;
    public InterstitialAd mInterstitialAd;
    public com.google.android.gms.ads.InterstitialAd mInterstitial;
    private boolean isEdit = false;
    private int editPosition;
    private FillWallpaperColorAdapter fillWallpaperColorAdapter;
    ArrayList<KeyboardData> keyboardDataArrayList = new ArrayList<>();
    Animation myAnim;
    MyBounceInterpolator_anim interpolator;
    ArrayList<NewSoundData> newSoundDataArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.dark_gray));
        }
        setContentView(R.layout.activity_create_keyboard);
        setContent();
        setColorGridView();
        setTextualGridView();
        getColorFromDatabase();
        getFontFromDatabase();
        getSoundFromDatabase();
    }

    private void setContent() {

        mInterstitialAd = new InterstitialAd(this);
        //mInterstitialAd.setAdUnitId("ca-app-pub-2086017583390552/5535404813");
        //mInterstitialAd.setAdUnitId("ca-app-pub-1041813022220163/7928924396");
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        Toolbar toolbar = findViewById(R.id.toolbar);
        ivHome = findViewById(R.id.ivHome);
        CustomTextViewBold txtApply = findViewById(R.id.txtApply);
        txtMainTitle = findViewById(R.id.txtMainTitle);

        ivWallpaper = findViewById(R.id.ivWallpaper);
        linWallpaper = findViewById(R.id.linWallpaper);
        ivKeyDesign = findViewById(R.id.ivKeyDesign);
        linKeyDesign = findViewById(R.id.linKeyDesign);
        ivFontStyle = findViewById(R.id.ivFontStyle);
        linFontStyle = findViewById(R.id.linFontStyle);
        ivSoundEffect = findViewById(R.id.ivSoundEffect);
        linSoundEffect = findViewById(R.id.linSoundEffect);

        linWallpaperLayout = findViewById(R.id.linWallpaperLayout);
        linKeyDesignLayout = findViewById(R.id.linKeyDesignLayout);
        linFontStyleLayout = findViewById(R.id.linFontStyleLayout);
        linSoundEffectLayout = findViewById(R.id.linSoundEffectLayout);

        rvDefaultColor = findViewById(R.id.rvDefaultColor);
        rvDefaultColorKeyDesign = findViewById(R.id.rvDefaultColorKeyDesign);
        rvDefaultColorFontStyle = findViewById(R.id.rvDefaultColorFontStyle);
        LinearLayout linCamera = findViewById(R.id.linCamera);
        LinearLayout linGallery = findViewById(R.id.linGallery);

        ivKeyboardBg = findViewById(R.id.ivKeyboardBg);

        // id for apply radius
        radiusOne = findViewById(R.id.radiusOne);
        radiusTwo = findViewById(R.id.radiusTwo);
        radiusThree = findViewById(R.id.radiusThree);
        radiusFour = findViewById(R.id.radiusFour);
        radiusFive = findViewById(R.id.radiusFive);

        // id for apply opacity
        ivOpacityHundred = findViewById(R.id.ivOpacityHundred);
        ivOpacitySeventyFive = findViewById(R.id.ivOpacitySeventyFive);
        ivOpacityFifty = findViewById(R.id.ivOpacityFifty);
        ivOpacityTwentyFive = findViewById(R.id.ivOpacityTwentyFive);
        ivOpacityZero = findViewById(R.id.ivOpacityZero);

        // id for get keyboard key id
        linFirstRowKeyboard = findViewById(R.id.linFirstRowKeyboard);
        linTwoRowKeyboard = findViewById(R.id.linTwoRowKeyboard);
        linThreeRowKeyboard = findViewById(R.id.linThreeRowKeyboard);
        linFourRowKeyboard = findViewById(R.id.linFourRowKeyboard);

        //id for apply stroke
        ivStrokeOne = findViewById(R.id.ivStrokeOne);
        ivStrokeTwo = findViewById(R.id.ivStrokeTwo);
        ivStrokeThree = findViewById(R.id.ivStrokeThree);
        ivStrokeFour = findViewById(R.id.ivStrokeFour);
        ivStrokeFive = findViewById(R.id.ivStrokeFive);

        ivDone = findViewById(R.id.ivDone);
        ivShift = findViewById(R.id.ivShift);
        ivCancel = findViewById(R.id.ivCancel);

        // set the fill data using row inflater in Default color
        rvDefaultColor.setNestedScrollingEnabled(false);
        rvDefaultColor.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

        rvDefaultColorKeyDesign.setNestedScrollingEnabled(false);
        rvDefaultColorKeyDesign.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

        rvDefaultColorFontStyle.setNestedScrollingEnabled(false);
        rvDefaultColorFontStyle.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

        // set toolbar
        setSupportActionBar(toolbar);

        String state = Environment.getExternalStorageState();
        if ("mounted".equals(state)) {
            mFileTemp = new File(Environment.getExternalStorageDirectory(), TEMP_PHOTO_FILE_NAME);
        } else {
            mFileTemp = new File(getFilesDir(), TEMP_PHOTO_FILE_NAME);
        }

        // set context
        context = CreateKeyboardActivity.this;
        createKeyboardActivity = this;
        if (GlobalClass.getPreferencesArrayList(context) != null) {
            keyboardDataArrayList = GlobalClass.getPreferencesArrayList(context);
        }

        try {
            fontArray = context.getAssets().list("fonts");
        } catch (IOException e) {
            e.printStackTrace();
        }


        if (getIntent().getBooleanExtra("isEdit", false)) {
            isEdit = true;
            editPosition = getIntent().getIntExtra("position", 0);
            if (GlobalClass.getPreferencesString(getApplicationContext(), GlobalClass.KEYBOARDBITMAPBACK, null) != null) {
                byte[] decodedString = Base64.decode(GlobalClass.getPreferencesString(getApplicationContext(), GlobalClass.KEYBOARDBITMAPBACK, null), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                ivKeyboardBg.setImageBitmap(decodedByte);
                GlobalClass.selview = 0;

            } else {
                ivKeyboardBg.setImageResource(GlobalClass.getPreferencesInt(getApplicationContext(), GlobalClass.KEYBOARD_BG_IMAGE, 0));
                GlobalClass.selectwallpaper = GlobalClass.getPreferencesInt(getApplicationContext(), GlobalClass.SELECTWALLPAPER, 0);
                GlobalClass.selecttextwallpaper = GlobalClass.getPreferencesInt(getApplicationContext(), GlobalClass.SELECTTEXTWALLPAPER, 0);
                GlobalClass.selectcolor = GlobalClass.getPreferencesInt(getApplicationContext(), GlobalClass.SELECTCOLOR, 0);
                GlobalClass.selview = GlobalClass.getPreferencesInt(getApplicationContext(), GlobalClass.SELECTVIEW, 0);
            }
            GlobalClass.tempKeyRadius = GlobalClass.getPreferencesString(getApplicationContext(), GlobalClass.KEY_RADIUS, "18");
            GlobalClass.tempKeyStroke = GlobalClass.getPreferencesString(getApplicationContext(), GlobalClass.KEY_STROKE, "2");
            GlobalClass.tempKeyOpacity = GlobalClass.getPreferencesString(getApplicationContext(), GlobalClass.KEY_OPACITY, "255");
            GlobalClass.tempFontName = GlobalClass.getPreferencesString(getApplicationContext(), GlobalClass.FONT_NAME, "Abel_Regular.ttf");
            GlobalClass.tempSoundStatus = GlobalClass.getPreferencesString(getApplicationContext(), GlobalClass.SOUND_STATUS, "off");
            GlobalClass.tempSoundName = GlobalClass.getPreferencesInt(getApplicationContext(), GlobalClass.SOUND_NAME, R.raw.balloon_snap);
            GlobalClass.selectbgcolor = getColorPos(GlobalClass.getPreferencesInt(getApplicationContext(), GlobalClass.KEY_BG_COLOR, 7));
            GlobalClass.selectfontcolor = getColorPos(android.graphics.Color.parseColor(GlobalClass.getPreferencesString(getApplicationContext(), GlobalClass.FONT_COLOR, "#FFFFFF")));
            GlobalClass.selectsounds = getSoundPos(GlobalClass.tempSoundName);

            String remove = "fonts/";
            String fontName = removeWords(GlobalClass.tempFontName, remove);
            GlobalClass.selectfonts = getFontPos(fontName);

        } else {
            GlobalClass.selectwallpaper = 0;
            GlobalClass.selecttextwallpaper = 0;
            GlobalClass.tempKeyboardBgImage = R.drawable.theme_color1;
            GlobalClass.selectcolor = 0;
            GlobalClass.selview = 2;
            GlobalClass.tempIsColor = "no";
            GlobalClass.tempKeyboardColorCode = 0;
            GlobalClass.keyboardBitmapBack = null;
            GlobalClass.tempFontColor = "#FFFFFF";
            GlobalClass.tempKeyColor = getResources().getColor(R.color.eight);
            GlobalClass.tempKeyRadius = "18";
            GlobalClass.tempKeyStroke = "2";
            GlobalClass.tempKeyOpacity = "255";
            GlobalClass.tempFontName = "";
            GlobalClass.tempSoundStatus = "off";
            GlobalClass.tempSoundName = 0;
            GlobalClass.selectbgcolor = 7;
            GlobalClass.selectfontcolor = 1;
            GlobalClass.selectsounds = 0;
            GlobalClass.selectfonts = 0;
            ivKeyboardBg.setImageResource(GlobalClass.tempKeyboardBgImage);
        }

        setRadius();

        myAnim = AnimationUtils.loadAnimation(this, R.anim.button);
        interpolator = new MyBounceInterpolator_anim(0.2, 20);
        myAnim.setInterpolator(interpolator);

        // set listener
        ivHome.setOnClickListener(this);
        txtApply.setOnClickListener(this);

        ivWallpaper.setOnClickListener(this);
        ivKeyDesign.setOnClickListener(this);
        ivFontStyle.setOnClickListener(this);
        ivSoundEffect.setOnClickListener(this);

        linGallery.setOnClickListener(this);
        linCamera.setOnClickListener(this);

        // set listener for radius
        radiusOne.setOnClickListener(this);
        radiusTwo.setOnClickListener(this);
        radiusThree.setOnClickListener(this);
        radiusFour.setOnClickListener(this);
        radiusFive.setOnClickListener(this);

        // set listener for radius
        ivOpacityHundred.setOnClickListener(this);
        ivOpacitySeventyFive.setOnClickListener(this);
        ivOpacityFifty.setOnClickListener(this);
        ivOpacityTwentyFive.setOnClickListener(this);
        ivOpacityZero.setOnClickListener(this);

        // set listener for stroke
        ivStrokeOne.setOnClickListener(this);
        ivStrokeTwo.setOnClickListener(this);
        ivStrokeThree.setOnClickListener(this);
        ivStrokeFour.setOnClickListener(this);
        ivStrokeFive.setOnClickListener(this);
    }

    private void setColorGridView() {
        int[] colorWallpaperArrayList = GlobalClass.thArray;
        GridView gvColor = findViewById(R.id.gvColor);
        fillWallpaperColorAdapter = new FillWallpaperColorAdapter(this, colorWallpaperArrayList);
        gvColor.setAdapter(fillWallpaperColorAdapter);

        gvColor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, final int position, long id) {

                ivKeyboardBg.setImageResource(GlobalClass.thumbArray[position]);
                GlobalClass.selectwallpaper = position;
                GlobalClass.selview = 2;
                GlobalClass.setPreferencesString(context, GlobalClass.IS_COLOR, "no");
                GlobalClass.tempKeyboardBgImage = GlobalClass.thumbArray[position];
                GlobalClass.tempIsColor = "no";
                fillWallpaperColorAdapter.notifyDataSetChanged();
                fillWallpaperTextualAdapter.notifyDataSetChanged();
                fillDefaultColorAdapter.notifyDataSetChanged();
                GlobalClass.checkStartAd();
            }
        });
    }

    private void setTextualGridView() {
        int[] colorWallpaperArrayList = GlobalClass.texArray;
        GridView gvTextual = findViewById(R.id.gvTextual);
        fillWallpaperTextualAdapter = new FillWallpaperTextualAdapter(this, colorWallpaperArrayList);
        gvTextual.setAdapter(fillWallpaperTextualAdapter);


        gvTextual.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, final int position, long id) {

                ivKeyboardBg.setImageResource(GlobalClass.textureArray[position]);
                GlobalClass.selecttextwallpaper = position;
                GlobalClass.selview = 3;
                GlobalClass.setPreferencesString(context, GlobalClass.IS_COLOR, "no");
                GlobalClass.tempKeyboardBgImage = GlobalClass.textureArray[position];
                GlobalClass.tempIsColor = "no";
                fillWallpaperColorAdapter.notifyDataSetChanged();
                fillWallpaperTextualAdapter.notifyDataSetChanged();
                fillDefaultColorAdapter.notifyDataSetChanged();
                GlobalClass.checkStartAd();
            }
        });
    }

    private void getColorFromDatabase() {
        int[] colorWallpaperArrayList = GlobalClass.colorsHorizontal;
        fillDefaultColorAdapter = new FillDefaultColorAdapter(context, colorWallpaperArrayList);
        rvDefaultColor.setAdapter(fillDefaultColorAdapter);

        rvDefaultColor.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        ivKeyboardBg.setImageResource(GlobalClass.colorsHorizontal[position]);
                        GlobalClass.selectcolor = position;
                        GlobalClass.selview = 1;
                        GlobalClass.setPreferencesString(context, GlobalClass.IS_COLOR, "yes");
                        GlobalClass.tempKeyboardBgImage = GlobalClass.colorsHorizontal[position];
                        fillDefaultColorAdapter.notifyDataSetChanged();
                        fillWallpaperColorAdapter.notifyDataSetChanged();
                        fillWallpaperTextualAdapter.notifyDataSetChanged();
                        GlobalClass.checkStartAd();
                    }
                })
        );

        FillKeyBgAdapter fillKeyBgAdapter = new FillKeyBgAdapter(context, colorWallpaperArrayList);
        rvDefaultColorKeyDesign.setAdapter(fillKeyBgAdapter);
        rvDefaultColorKeyDesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRadius();
            }
        });

        FillFontColorAdapter fillFontColorAdapter = new FillFontColorAdapter(context, colorWallpaperArrayList);
        rvDefaultColorFontStyle.setAdapter(fillFontColorAdapter);

    }

    private void getSoundFromDatabase() {
        GridView gvSoundEffect = findViewById(R.id.gvSoundEffect);
        int[] freeSoundArray = GlobalClass.lessonClips;
        for (int aFreeSoundArray : freeSoundArray) {
            newSoundDataArrayList.add(new NewSoundData(aFreeSoundArray, false));
        }

        //--------- fill sound data-------
        fillSoundEffectAdapter = new FillSoundEffectAdapter(this, newSoundDataArrayList);
        gvSoundEffect.setAdapter(fillSoundEffectAdapter);

        gvSoundEffect.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                GlobalClass.selectsounds = position;
                if (position != 0) {
                    GlobalClass.tempSoundName = newSoundDataArrayList.get(position).getResourceId();
                    beep(10);
                    GlobalClass.tempSoundStatus = "on";
                } else {
                    GlobalClass.tempSoundStatus = "off";
                }
                fillSoundEffectAdapter.notifyDataSetChanged();
                GlobalClass.checkStartAd();
            }
        });
    }

    private void getFontFromDatabase() {
        ArrayList<FontsPaid> fontsPaidArrayList = new ArrayList<>();
        DatabaseHelper dbHelper = new DatabaseHelper(CreateKeyboardActivity.this);
        Cursor dataCursor = dbHelper.getDataOfTable(DatabaseHelper.TABLE_FONT);

        if (dataCursor != null && dataCursor.getCount() > 0) {
            dataCursor.moveToFirst();
            do {
                FontsPaid fontsPaid = new FontsPaid();
                fontsPaid.setId(dataCursor.getString(dataCursor.getColumnIndex(DatabaseHelper.KEY_FONT_ID)));
                fontsPaid.setTitle(dataCursor.getString(dataCursor.getColumnIndex(DatabaseHelper.KEY_FONT_TITLE)));
                fontsPaid.setFont_url(dataCursor.getString(dataCursor.getColumnIndex(DatabaseHelper.KEY_FONT_URL)));
                fontsPaid.setPaid(dataCursor.getString(dataCursor.getColumnIndex(DatabaseHelper.KEY_FONT_IS_PAID)));
                fontsPaidArrayList.add(fontsPaid);

            } while (dataCursor.moveToNext());
        }

        for (int i = 0; i < fontsPaidArrayList.size(); i++) {
            AsyncDownload asyncDownload = new AsyncDownload(context, fontsPaidArrayList.get(i));
            asyncDownload.execute();
        }
        setFontStyleGridView(fontsPaidArrayList);
    }

    private void setFontStyleGridView(final ArrayList<FontsPaid> fontStyleArrayList) {
        GridView gvFont = findViewById(R.id.gvFont);


        fillFontStyleAdapter = new FillFontStyleAdapter(this, fontArray);
        gvFont.setAdapter(fillFontStyleAdapter);

        if (GlobalClass.tempFontName != null && !GlobalClass.tempFontName.isEmpty()) {
            for (int i = 0; i < fontStyleArrayList.size(); i++) {
                if (GlobalClass.tempFontName.equals(fontStyleArrayList.get(i).getTitle())) {
                    fontStyleArrayList.get(i).setSelected(true);
                } else {
                    fontStyleArrayList.get(i).setSelected(false);
                }
            }
        }

        gvFont.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                GlobalClass.selectfonts = position;

                if (CreateKeyboardActivity.getInstance() != null) {
                    if (fontArray[position] != null) {
                        GlobalClass.tempFontName = "fonts/" + fontArray[position];
                        CreateKeyboardActivity.getInstance().setRadius();
                    }
                }

                for (int i = 0; i < fontStyleArrayList.size(); i++) {
                    if (i == position) {
                        fontStyleArrayList.get(i).setSelected(true);
                    } else {
                        fontStyleArrayList.get(i).setSelected(false);
                    }
                }
                fillFontStyleAdapter.notifyDataSetChanged();
                GlobalClass.checkStartAd();
            }
        });
    }

    public void setRadius() {
        switch (GlobalClass.tempKeyRadius) {
            case "0":
                radiusOne.setBorderWidth(5);
                radiusOne.setBorderColor(getResources().getColor(R.color.pink));
                break;
            case "9":
                radiusTwo.setBorderWidth(5);
                radiusTwo.setBorderColor(getResources().getColor(R.color.pink));
                break;
            case "18":
                radiusThree.setBorderWidth(5);
                radiusThree.setBorderColor(getResources().getColor(R.color.pink));
                break;
            case "25":
                radiusFour.setBorderWidth(5);
                radiusFour.setBorderColor(getResources().getColor(R.color.pink));
                break;
            case "34":
                radiusFive.setBorderWidth(5);
                radiusFive.setBorderColor(getResources().getColor(R.color.pink));
                break;
        }

        switch (GlobalClass.tempKeyStroke) {
            case "1":
                ivStrokeOne.setBorderWidth(5);
                ivStrokeOne.setBorderColor(getResources().getColor(R.color.pink));
                break;
            case "2":
                ivStrokeTwo.setBorderWidth(5);
                ivStrokeTwo.setBorderColor(getResources().getColor(R.color.pink));
                break;
            case "3":
                ivStrokeThree.setBorderWidth(5);
                ivStrokeThree.setBorderColor(getResources().getColor(R.color.pink));
                break;
            case "4":
                ivStrokeFour.setBorderWidth(5);
                ivStrokeFour.setBorderColor(getResources().getColor(R.color.pink));
                break;
            case "5":
                ivStrokeFive.setBorderWidth(5);
                ivStrokeFive.setBorderColor(getResources().getColor(R.color.pink));
                break;
        }


        switch (GlobalClass.tempKeyOpacity) {
            case "255":
                ivOpacityHundred.setBorderWidth(5);
                ivOpacityHundred.setBorderColor(getResources().getColor(R.color.pink));
                break;
            case "192":
                ivOpacitySeventyFive.setBorderWidth(5);
                ivOpacitySeventyFive.setBorderColor(getResources().getColor(R.color.pink));
                break;
            case "128":
                ivOpacityFifty.setBorderWidth(5);
                ivOpacityFifty.setBorderColor(getResources().getColor(R.color.pink));
                break;
            case "64":
                ivOpacityTwentyFive.setBorderWidth(5);
                ivOpacityTwentyFive.setBorderColor(getResources().getColor(R.color.pink));
                break;
            case "0":
                ivOpacityZero.setBorderWidth(5);
                ivOpacityZero.setBorderColor(getResources().getColor(R.color.pink));
                break;
        }


        final Drawable imgDone = getResources().getDrawable(R.drawable.ic_enter_new);
        imgDone.setColorFilter(android.graphics.Color.parseColor(GlobalClass.tempFontColor), PorterDuff.Mode.SRC_ATOP);
        ivDone.setImageDrawable(imgDone);

        final Drawable imgShift = getResources().getDrawable(R.drawable.ic_shift_on);
        imgShift.setColorFilter(android.graphics.Color.parseColor(GlobalClass.tempFontColor), PorterDuff.Mode.SRC_ATOP);
        ivShift.setImageDrawable(imgShift);

        final Drawable imgCancel = getResources().getDrawable(R.drawable.ic_backspace);
        imgCancel.setColorFilter(android.graphics.Color.parseColor(GlobalClass.tempFontColor), PorterDuff.Mode.SRC_ATOP);
        ivCancel.setImageDrawable(imgCancel);

        GradientDrawable npd1;
        for (int i = 0; i < linFirstRowKeyboard.getChildCount(); i++) {
            final View mChild = linFirstRowKeyboard.getChildAt(i);
            if (mChild instanceof ImageView || mChild instanceof TextView) {
                npd1 = new GradientDrawable(
                        GradientDrawable.Orientation.TOP_BOTTOM,
                        new int[]{GlobalClass.tempKeyColor,
                                GlobalClass.tempKeyColor});
                npd1.setBounds(mChild.getLeft() + 5, mChild.getTop() + 5, mChild.getRight() - 5, mChild.getBottom() - 5);

                npd1.setCornerRadius(Float.parseFloat(GlobalClass.tempKeyRadius));
                npd1.setAlpha(Integer.parseInt(GlobalClass.tempKeyOpacity));

                switch (GlobalClass.tempKeyStroke) {
                    case "1":
                        npd1.setStroke(0, context.getResources().getColor(R.color.colorPrimary));
                        break;
                    case "2":
                        npd1.setStroke(2, android.graphics.Color.WHITE);
                        break;
                    case "3":
                        npd1.setStroke(2, android.graphics.Color.BLACK);
                        break;
                    case "4":
                        npd1.setStroke(4, android.graphics.Color.BLACK);
                        break;
                    case "5":
                        npd1.setStroke(3, getResources().getColor(R.color.gray));
                        break;
                }

                mChild.setBackground(npd1);

                if (mChild instanceof TextView) {
                    ((TextView) mChild).setTextColor(android.graphics.Color.parseColor(GlobalClass.tempFontColor));
                    if (GlobalClass.tempFontName.length() != 0 && GlobalClass.tempFontName != null
                            && !GlobalClass.tempFontName.isEmpty()) {
                        try {

                            Typeface font = Typeface.createFromAsset(this.getAssets(), GlobalClass.tempFontName);
                            ((TextView) mChild).setTypeface(font);
                        } catch (Exception ignored) {
                        }
                    }
                }
            }
        }
        for (int i = 0; i < linTwoRowKeyboard.getChildCount(); i++) {
            final View mChild = linTwoRowKeyboard.getChildAt(i);
            if (mChild instanceof ImageView || mChild instanceof TextView) {
                // Recursively attempt another ViewGroup.
                npd1 = new GradientDrawable(
                        GradientDrawable.Orientation.TOP_BOTTOM,
                        new int[]{GlobalClass.tempKeyColor,
                                GlobalClass.tempKeyColor});
                npd1.setBounds(mChild.getLeft() + 5, mChild.getTop() + 5, mChild.getRight() - 5, mChild.getBottom() - 5);

                npd1.setCornerRadius(Float.parseFloat(GlobalClass.tempKeyRadius));
                npd1.setAlpha(Integer.parseInt(GlobalClass.tempKeyOpacity));

                switch (GlobalClass.tempKeyStroke) {
                    case "1":
                        npd1.setStroke(0, context.getResources().getColor(R.color.colorPrimary));
                        break;
                    case "2":
                        npd1.setStroke(2, android.graphics.Color.WHITE);
                        break;
                    case "3":
                        npd1.setStroke(2, android.graphics.Color.BLACK);
                        break;
                    case "4":
                        npd1.setStroke(4, android.graphics.Color.BLACK);
                        break;
                    case "5":
                        npd1.setStroke(3, getResources().getColor(R.color.gray));
                        break;
                }

                mChild.setBackground(npd1);

                if (mChild instanceof TextView) {
                    ((TextView) mChild).setTextColor(android.graphics.Color.parseColor(GlobalClass.tempFontColor));
                    if (GlobalClass.tempFontName.length() != 0 && GlobalClass.tempFontName != null
                            && !GlobalClass.tempFontName.isEmpty()) {
                        try {
                            Typeface font = Typeface.createFromAsset(this.getAssets(), GlobalClass.tempFontName);
                            ((TextView) mChild).setTypeface(font);
                        } catch (Exception ignored) {

                        }
                    }
                }
            }
        }
        for (int i = 0; i < linThreeRowKeyboard.getChildCount(); i++) {
            final View mChild = linThreeRowKeyboard.getChildAt(i);
            if (mChild instanceof ImageView || mChild instanceof TextView) {
                // Recursively attempt another ViewGroup.
                npd1 = new GradientDrawable(
                        GradientDrawable.Orientation.TOP_BOTTOM,
                        new int[]{GlobalClass.tempKeyColor,
                                GlobalClass.tempKeyColor});
                npd1.setBounds(mChild.getLeft() + 5, mChild.getTop() + 5, mChild.getRight() - 5, mChild.getBottom() - 5);

                npd1.setCornerRadius(Float.parseFloat(GlobalClass.tempKeyRadius));
                npd1.setAlpha(Integer.parseInt(GlobalClass.tempKeyOpacity));

                switch (GlobalClass.tempKeyStroke) {
                    case "1":
                        npd1.setStroke(0, context.getResources().getColor(R.color.colorPrimary));
                        break;
                    case "2":
                        npd1.setStroke(2, android.graphics.Color.WHITE);
                        break;
                    case "3":
                        npd1.setStroke(2, android.graphics.Color.BLACK);
                        break;
                    case "4":
                        npd1.setStroke(4, android.graphics.Color.BLACK);
                        break;
                    case "5":
                        npd1.setStroke(3, getResources().getColor(R.color.gray));
                        break;
                }

                mChild.setBackground(npd1);

                if (mChild instanceof TextView) {
                    ((TextView) mChild).setTextColor(android.graphics.Color.parseColor(GlobalClass.tempFontColor));
                    if (GlobalClass.tempFontName.length() != 0 && GlobalClass.tempFontName != null
                            && !GlobalClass.tempFontName.isEmpty()) {
                        try {
                            Typeface font = Typeface.createFromAsset(this.getAssets(), GlobalClass.tempFontName);
                            ((TextView) mChild).setTypeface(font);
                        } catch (Exception ignored) {
                        }
                    }
                }
            }
        }

        for (int i = 0; i < linFourRowKeyboard.getChildCount(); i++) {
            final View mChild = linFourRowKeyboard.getChildAt(i);
            if (mChild instanceof ImageView || mChild instanceof TextView) {
                npd1 = new GradientDrawable(
                        GradientDrawable.Orientation.TOP_BOTTOM,
                        new int[]{GlobalClass.tempKeyColor,
                                GlobalClass.tempKeyColor});
                npd1.setBounds(mChild.getLeft() + 5, mChild.getTop() + 5, mChild.getRight() - 5, mChild.getBottom() - 5);
                npd1.setCornerRadius(Float.parseFloat(GlobalClass.tempKeyRadius));
                npd1.setAlpha(Integer.parseInt(GlobalClass.tempKeyOpacity));

                switch (GlobalClass.tempKeyStroke) {
                    case "1":
                        npd1.setStroke(0, context.getResources().getColor(R.color.colorPrimary));
                        break;
                    case "2":
                        npd1.setStroke(2, android.graphics.Color.WHITE);
                        break;
                    case "3":
                        npd1.setStroke(2, android.graphics.Color.BLACK);
                        break;
                    case "4":
                        npd1.setStroke(4, android.graphics.Color.BLACK);
                        break;
                    case "5":
                        npd1.setStroke(3, getResources().getColor(R.color.gray));
                        break;
                }

                mChild.setBackground(npd1);

                if (mChild instanceof TextView) {
                    ((TextView) mChild).setTextColor(android.graphics.Color.parseColor(GlobalClass.tempFontColor));
                    if (GlobalClass.tempFontName.length() != 0 && GlobalClass.tempFontName != null
                            && !GlobalClass.tempFontName.isEmpty()) {
                        try {
                            Typeface font = Typeface.createFromAsset(this.getAssets(), GlobalClass.tempFontName);
                            ((TextView) mChild).setTypeface(font);
                        } catch (Exception ignored) {

                        }
                    }
                }
            }
        }

        GlobalClass.checkStartAd();

    }

    public void strtaDS() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Log.i("Ads", "onAdLoaded");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Log.i("Ads", "onAdFailedToLoad");
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
                Log.i("Ads", "onAdOpened");
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                Log.i("Ads", "onAdLeftApplication");
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the interstitial ad is closed.
                Log.i("Ads", "onAdClosed");

            }
        });
    }

    public void setAdMob() {
        final LinearLayout adContainer = findViewById(R.id.adContainer);
        final AdView mAdView = new AdView(this);
        mAdView.setAdSize(AdSize.SMART_BANNER);
//        mAdView.setAdUnitId(GlobalClass.getPreferenceString(ImageViewActivity.this, getString(R.string.android_banner), ""));

//        adContainer.addView(mAdView);
//        AdRequest adRequest = new AdRequest.Builder().build();
        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);

        mInterstitial = new InterstitialAd(this);
        mInterstitial.setAdUnitId(GlobalClass.getPrefrenceString(CreateKeyboardActivity.this, getString(R.string.android_inst), ""));
        mInterstitial.loadAd(adRequest);
        mInterstitial.setAdListener(new AdListener() {
            public void onAdLoaded() {
                if (mInterstitial.isLoaded()) {
                }
            }
        });
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                adContainer.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdClosed() {
                adContainer.setVisibility(View.GONE);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                adContainer.setVisibility(View.GONE);
            }
        });
    }

    public void setKeyboardBackground(Bitmap bitmap) {

        GlobalClass.selectwallpaper = -1;
        GlobalClass.selectbgcolor = 7;
        GlobalClass.selectfontcolor = 1;
        GlobalClass.selectsounds = 0;
        GlobalClass.selectfonts = 0;

        ivKeyboardBg.setImageBitmap(bitmap);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        GlobalClass.keyboardBitmapBack = Base64.encodeToString(byteArray, Base64.DEFAULT);

        fillDefaultColorAdapter.notifyDataSetChanged();
        fillWallpaperColorAdapter.notifyDataSetChanged();
        fillWallpaperTextualAdapter.notifyDataSetChanged();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RESULT_FROM_GALLERY /*98*/:
                    GlobalClass.printLog("Gallery higher version", "----if-----");
                    new AnonymousClass2photoSave(data).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    break;
                case RESULT_FROM_CAMERA /*99*/:
                    GlobalClass.printLog("camera version", "----if-----");

                    new AsyncTask<Void, Void, Void>() {
                        ProgressDialog pd;

                        protected void onPreExecute() {
                            this.pd = new ProgressDialog(CreateKeyboardActivity.this, 5);
                            this.pd.setMessage("Please Wait");
                            this.pd.show();
                            super.onPreExecute();
                        }

                        protected Void doInBackground(Void... params) {
                            try {
                                BitmapFactory.Options opts = new BitmapFactory.Options();
                                opts.inJustDecodeBounds = true;
                                Bitmap selectedImage = BitmapFactory.decodeFile(CreateKeyboardActivity.mFileTemp.getAbsolutePath(), opts);
                                opts.inJustDecodeBounds = false;
                                selectedImage = BitmapFactory.decodeFile(CreateKeyboardActivity.mFileTemp.getAbsolutePath(), opts);
                                FileOutputStream fos = new FileOutputStream(CreateKeyboardActivity.mFileTemp);
                                selectedImage.compress(Bitmap.CompressFormat.JPEG, CreateKeyboardActivity.FONT_RESULT_CODE, fos);
                                fos.flush();
                                fos.close();
                            } catch (Exception e1) {
                                e1.printStackTrace();
                                GlobalClass.printLog("Camera click exception", "-------------" + e1.getMessage());
                            }
                            return null;
                        }

                        protected void onPostExecute(Void result) {
                            this.pd.dismiss();
                            Intent newIntent = new Intent(CreateKeyboardActivity.this, CropActivity.class);
                            CreateKeyboardActivity.this.startActivityForResult(newIntent, 7);
                        }
                    }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    break;
            }
        }
    }

    private void beep(int volume) {

        AudioManager manager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        MediaPlayer player;

        try {
            String filename = GlobalClass.tempSoundName + ".mp3";

            File file = new File(filename);

            if (file.exists()) {
                if (GlobalClass.tempSoundName != 0) {
                    player = MediaPlayer.create(context, GlobalClass.tempSoundName);
                    assert manager != null;
                    manager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
                    player.start();

                    player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            mp.release();
                        }
                    });
                } else {
                    file = new File(filename);

                    if (file.exists()) {
                        player = MediaPlayer.create(context, Uri.fromFile(file));
                        assert manager != null;
                        manager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
                        player.start();

                        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                mp.release();
                            }
                        });
                    } else {
                        Toast.makeText(context, "Some problem play key tone.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        } catch (Exception ignored) {

        }
    }

    private int getColorPos(int colorCode) {
        for (int i = 0; i < GlobalClass.colorsHorizontal.length; i++) {
            if (getResources().getColor(GlobalClass.colorsHorizontal[i]) == colorCode) {
                return i;
            }
        }
        return -1;
    }

    private int getSoundPos(int soundCode) {
        for (int i = 0; i < GlobalClass.lessonClips.length; i++) {
            if (GlobalClass.lessonClips[i] == soundCode) {
                return i;
            }
        }
        return 0;
    }

    private int getFontPos(String fontCode) {
        for (int i = 0; i < fontArray.length; i++) {
            if (fontArray[i].equals(fontCode)) {
                return i;
            }
        }
        return 0;
    }

    class AnonymousClass2photoSave extends AsyncTask<Void, Void, Void> {

        ProgressDialog pd;
        private final Intent val$data;

        AnonymousClass2photoSave(Intent intent) {
            GlobalClass.printLog("AnonymousClass2photoSave ", "----construction-----");

            this.val$data = intent;
        }

        protected void onPreExecute() {
            GlobalClass.printLog("AnonymousClass2photoSave ", "----onPreExecute-----");

            this.pd = new ProgressDialog(CreateKeyboardActivity.this, 5);
            this.pd.setMessage("Please Wait");
            this.pd.show();
            super.onPreExecute();
        }


        protected Void doInBackground(Void... params) {
            Uri selectedImageUri = this.val$data.getData();
            String realPath = null;
            try {
                realPath = Utils.getRealPathFromURI(CreateKeyboardActivity.this.getApplicationContext(), selectedImageUri);
            } catch (Exception e) {
                try {
                    realPath = Utils.getPathFromUriLolipop(CreateKeyboardActivity.this.getApplicationContext(), selectedImageUri);
                } catch (Exception e2) {
                    Toast.makeText(CreateKeyboardActivity.this.getApplicationContext(), "Load Image failed. Try again", Toast.LENGTH_SHORT).show();
                }
            }
            try {
                BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inJustDecodeBounds = true;
                Bitmap selectedImage;
                opts.inJustDecodeBounds = false;
                selectedImage = BitmapFactory.decodeFile(realPath, opts);

                FileOutputStream fos = new FileOutputStream(CreateKeyboardActivity.mFileTemp);
                selectedImage.compress(Bitmap.CompressFormat.JPEG, CreateKeyboardActivity.FONT_RESULT_CODE, fos);
                fos.flush();
                fos.close();
            } catch (Exception e3) {
                GlobalClass.printLog("AnonymousClass2photoSave exception", "----doInBackground-----" + e3.getMessage());
            }
            return null;
        }

        protected void onPostExecute(Void result) {

            GlobalClass.printLog("AnonymousClass2photoSave ", "----onPostExecute-----");

            this.pd.dismiss();
            Intent newIntent1 = new Intent(CreateKeyboardActivity.this, CropActivity.class);
            CreateKeyboardActivity.this.startActivityForResult(newIntent1, 7);
            super.onPostExecute(result);
        }
    }

    public static String removeWords(String word, String remove) {
        return word.replace(remove, "");
    }

    public static CreateKeyboardActivity getInstance() {
        return createKeyboardActivity;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivStrokeOne:
                ivStrokeOne.setBorderWidth(5);
                ivStrokeTwo.setBorderWidth(0);
                ivStrokeThree.setBorderWidth(0);
                ivStrokeFour.setBorderWidth(0);
                ivStrokeFive.setBorderWidth(0);
                ivStrokeOne.setBorderColor(getResources().getColor(R.color.pink));
                GlobalClass.tempKeyStroke = "1";

                setRadius();
                break;

            case R.id.ivStrokeTwo:
                ivStrokeOne.setBorderWidth(0);
                ivStrokeTwo.setBorderWidth(5);
                ivStrokeThree.setBorderWidth(0);
                ivStrokeFive.setBorderWidth(0);
                ivStrokeFour.setBorderWidth(0);
                ivStrokeTwo.setBorderColor(getResources().getColor(R.color.pink));
                GlobalClass.tempKeyStroke = "2";
                setRadius();
                break;
            case R.id.ivStrokeThree:
                ivStrokeOne.setBorderWidth(0);
                ivStrokeTwo.setBorderWidth(0);
                ivStrokeThree.setBorderWidth(5);
                ivStrokeFour.setBorderWidth(0);
                ivStrokeFive.setBorderWidth(0);
                ivStrokeThree.setBorderColor(getResources().getColor(R.color.pink));
                GlobalClass.tempKeyStroke = "3";
                setRadius();
                break;
            case R.id.ivStrokeFour:
                ivStrokeOne.setBorderWidth(0);
                ivStrokeTwo.setBorderWidth(0);
                ivStrokeThree.setBorderWidth(0);
                ivStrokeFour.setBorderWidth(5);
                ivStrokeFive.setBorderWidth(0);
                ivStrokeFour.setBorderColor(getResources().getColor(R.color.pink));
                GlobalClass.tempKeyStroke = "4";
                setRadius();
                break;
            case R.id.ivStrokeFive:
                ivStrokeOne.setBorderWidth(0);
                ivStrokeTwo.setBorderWidth(0);
                ivStrokeThree.setBorderWidth(0);
                ivStrokeFour.setBorderWidth(0);
                ivStrokeFive.setBorderWidth(5);
                ivStrokeFive.setBorderColor(getResources().getColor(R.color.pink));
                GlobalClass.tempKeyStroke = "5";
                setRadius();
                break;
            case R.id.ivOpacityHundred:
                ivOpacityHundred.setBorderWidth(5);
                ivOpacitySeventyFive.setBorderWidth(0);
                ivOpacityFifty.setBorderWidth(0);
                ivOpacityTwentyFive.setBorderWidth(0);
                ivOpacityZero.setBorderWidth(0);
                ivOpacityHundred.setBorderColor(getResources().getColor(R.color.pink));
                GlobalClass.tempKeyOpacity = "255";
                setRadius();
                break;
            case R.id.ivOpacitySeventyFive:
                ivOpacityHundred.setBorderWidth(0);
                ivOpacitySeventyFive.setBorderWidth(5);
                ivOpacityFifty.setBorderWidth(0);
                ivOpacityTwentyFive.setBorderWidth(0);
                ivOpacityZero.setBorderWidth(0);
                ivOpacitySeventyFive.setBorderColor(getResources().getColor(R.color.pink));
                GlobalClass.tempKeyOpacity = "192";
                setRadius();
                break;
            case R.id.ivOpacityFifty:
                ivOpacityHundred.setBorderWidth(0);
                ivOpacitySeventyFive.setBorderWidth(0);
                ivOpacityFifty.setBorderWidth(5);
                ivOpacityTwentyFive.setBorderWidth(0);
                ivOpacityZero.setBorderWidth(0);
                ivOpacityFifty.setBorderColor(getResources().getColor(R.color.pink));
                GlobalClass.tempKeyOpacity = "128";
                setRadius();
                break;
            case R.id.ivOpacityTwentyFive:
                ivOpacityHundred.setBorderWidth(0);
                ivOpacitySeventyFive.setBorderWidth(0);
                ivOpacityFifty.setBorderWidth(0);
                ivOpacityTwentyFive.setBorderWidth(5);
                ivOpacityZero.setBorderWidth(0);
                ivOpacityTwentyFive.setBorderColor(getResources().getColor(R.color.pink));
                GlobalClass.tempKeyOpacity = "64";
                setRadius();
                break;
            case R.id.ivOpacityZero:
                ivOpacityHundred.setBorderWidth(0);
                ivOpacitySeventyFive.setBorderWidth(0);
                ivOpacityFifty.setBorderWidth(0);
                ivOpacityTwentyFive.setBorderWidth(0);
                ivOpacityZero.setBorderWidth(5);
                ivOpacityZero.setBorderColor(getResources().getColor(R.color.pink));
                GlobalClass.tempKeyOpacity = "0";
                setRadius();
                break;
            case R.id.radiusOne:
                radiusOne.setBorderWidth(5);
                radiusTwo.setBorderWidth(0);
                radiusThree.setBorderWidth(0);
                radiusFour.setBorderWidth(0);
                radiusFive.setBorderWidth(0);
                radiusOne.setBorderColor(getResources().getColor(R.color.pink));
                GlobalClass.tempKeyRadius = "0";
                setRadius();
                break;
            case R.id.radiusTwo:
                radiusOne.setBorderWidth(0);
                radiusTwo.setBorderWidth(5);
                radiusThree.setBorderWidth(0);
                radiusFour.setBorderWidth(0);
                radiusFive.setBorderWidth(0);
                radiusTwo.setBorderColor(getResources().getColor(R.color.pink));
                GlobalClass.tempKeyRadius = "9";
                setRadius();
                break;
            case R.id.radiusThree:
                radiusOne.setBorderWidth(0);
                radiusTwo.setBorderWidth(0);
                radiusThree.setBorderWidth(5);
                radiusFour.setBorderWidth(0);
                radiusFive.setBorderWidth(0);
                radiusThree.setBorderColor(getResources().getColor(R.color.pink));
                GlobalClass.tempKeyRadius = "18";
                setRadius();
                break;
            case R.id.radiusFour:
                radiusOne.setBorderWidth(0);
                radiusTwo.setBorderWidth(0);
                radiusThree.setBorderWidth(0);
                radiusFour.setBorderWidth(5);
                radiusFive.setBorderWidth(0);
                radiusFour.setBorderColor(getResources().getColor(R.color.pink));
                GlobalClass.tempKeyRadius = "25";
                setRadius();
                break;
            case R.id.radiusFive:
                radiusOne.setBorderWidth(0);
                radiusTwo.setBorderWidth(0);
                radiusThree.setBorderWidth(0);
                radiusFour.setBorderWidth(0);
                radiusFive.setBorderWidth(5);
                radiusFive.setBorderColor(getResources().getColor(R.color.pink));
                GlobalClass.setPreferencesString(context, GlobalClass.KEY_RADIUS, "11");
                GlobalClass.tempKeyRadius = "34";
                setRadius();
                break;
            case R.id.ivHome:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                strtaDS();
                break;
            case R.id.txtApply:
                KeyboardData keyboardData = new KeyboardData();
                keyboardData.setIsColor(GlobalClass.tempIsColor);
                keyboardData.setKeyboardBgImage(GlobalClass.tempKeyboardBgImage);
                keyboardData.setKeyboardColorCode(GlobalClass.tempKeyboardColorCode);
                keyboardData.setKeyBgColor(GlobalClass.tempKeyColor);
                keyboardData.setKeyRadius(GlobalClass.tempKeyRadius);
                keyboardData.setKeyStroke(GlobalClass.tempKeyStroke);
                keyboardData.setKeyOpacity(GlobalClass.tempKeyOpacity);
                keyboardData.setFontColor(GlobalClass.tempFontColor);
                keyboardData.setFontName(GlobalClass.tempFontName);
                keyboardData.setSoundStatus(GlobalClass.tempSoundStatus);
                keyboardData.setSoundName(GlobalClass.tempSoundName);
                keyboardData.setSelectwallpaper(GlobalClass.selectwallpaper);
                keyboardData.setSelecttextwallpaper(GlobalClass.selecttextwallpaper);
                keyboardData.setSelectcolor(GlobalClass.selectcolor);
                keyboardData.setSelview(GlobalClass.selview);
                keyboardData.setBitmapback(GlobalClass.keyboardBitmapBack);

                if (isEdit) {
                    boolean status = keyboardDataArrayList.get(editPosition).isSelected();
                    keyboardDataArrayList.remove(editPosition);
                    keyboardData.setSelected(status);
                    keyboardDataArrayList.add(editPosition, keyboardData);
                } else {
                    keyboardDataArrayList.add(0, keyboardData);
                }
                GlobalClass.setPreferencesArrayList(context, keyboardDataArrayList);
                finish();
                startActivity(new Intent(context, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                strtaDS();
                break;

            case R.id.ivWallpaper:

                //set title color
                txtMainTitle.setTextColor(getResources().getColor(R.color.green));

                linWallpaper.setVisibility(View.VISIBLE);
                linKeyDesign.setVisibility(View.GONE);
                linFontStyle.setVisibility(View.GONE);
                linSoundEffect.setVisibility(View.GONE);

                ivWallpaper.setVisibility(View.GONE);
                ivKeyDesign.setVisibility(View.VISIBLE);
                ivFontStyle.setVisibility(View.VISIBLE);
                ivSoundEffect.setVisibility(View.VISIBLE);

                linWallpaperLayout.setVisibility(View.VISIBLE);
                linKeyDesignLayout.setVisibility(View.GONE);
                linFontStyleLayout.setVisibility(View.GONE);
                linSoundEffectLayout.setVisibility(View.GONE);

                break;

            case R.id.ivKeyDesign:

                //set title color
                txtMainTitle.setTextColor(getResources().getColor(R.color.pink));

                linWallpaper.setVisibility(View.GONE);
                linKeyDesign.setVisibility(View.VISIBLE);
                linFontStyle.setVisibility(View.GONE);
                linSoundEffect.setVisibility(View.GONE);

                ivWallpaper.setVisibility(View.VISIBLE);
                ivKeyDesign.setVisibility(View.GONE);
                ivFontStyle.setVisibility(View.VISIBLE);
                ivSoundEffect.setVisibility(View.VISIBLE);

                linWallpaperLayout.setVisibility(View.GONE);
                linKeyDesignLayout.setVisibility(View.VISIBLE);
                linFontStyleLayout.setVisibility(View.GONE);
                linSoundEffectLayout.setVisibility(View.GONE);

                break;

            case R.id.ivFontStyle:

                //set title color
                txtMainTitle.setTextColor(getResources().getColor(R.color.dark_red));

                linWallpaper.setVisibility(View.GONE);
                linKeyDesign.setVisibility(View.GONE);
                linFontStyle.setVisibility(View.VISIBLE);
                linSoundEffect.setVisibility(View.GONE);

                ivWallpaper.setVisibility(View.VISIBLE);
                ivKeyDesign.setVisibility(View.VISIBLE);
                ivFontStyle.setVisibility(View.GONE);
                ivSoundEffect.setVisibility(View.VISIBLE);

                linWallpaperLayout.setVisibility(View.GONE);
                linKeyDesignLayout.setVisibility(View.GONE);
                linFontStyleLayout.setVisibility(View.VISIBLE);
                linSoundEffectLayout.setVisibility(View.GONE);

                break;
            case R.id.ivSoundEffect:

                //set title colors
                txtMainTitle.setTextColor(getResources().getColor(R.color.orange));

                linWallpaper.setVisibility(View.GONE);
                linKeyDesign.setVisibility(View.GONE);
                linFontStyle.setVisibility(View.GONE);
                linSoundEffect.setVisibility(View.VISIBLE);

                ivWallpaper.setVisibility(View.VISIBLE);
                ivKeyDesign.setVisibility(View.VISIBLE);
                ivFontStyle.setVisibility(View.VISIBLE);
                ivSoundEffect.setVisibility(View.GONE);

                linWallpaperLayout.setVisibility(View.GONE);
                linKeyDesignLayout.setVisibility(View.GONE);
                linFontStyleLayout.setVisibility(View.GONE);
                linSoundEffectLayout.setVisibility(View.VISIBLE);

                break;

            case R.id.linCamera:
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra("output", FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", CreateKeyboardActivity.mFileTemp));
                CreateKeyboardActivity.this.startActivityForResult(intent, CreateKeyboardActivity.RESULT_FROM_CAMERA);
                break;
            case R.id.linGallery:
                Intent photoPickerIntent = new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                photoPickerIntent.setType("image/*");
                CreateKeyboardActivity.this.startActivityForResult(photoPickerIntent, CreateKeyboardActivity.RESULT_FROM_GALLERY);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ivHome.performClick();
    }

}
