package com.codminskeyboards.universekeyboard.activity;

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
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.SeekBar;
import android.widget.TextView;

import com.codminskeyboards.universekeyboard.R;
import com.codminskeyboards.universekeyboard.adapter.FillDefaultColorAdapter;
import com.codminskeyboards.universekeyboard.adapter.FillFontColorAdapter;
import com.codminskeyboards.universekeyboard.adapter.FillFontStyleAdapter;
import com.codminskeyboards.universekeyboard.adapter.FillKeyBgAdapter;
import com.codminskeyboards.universekeyboard.adapter.FillSoundEffectAdapter;
import com.codminskeyboards.universekeyboard.adapter.FillWallpaperColorAdapter;
import com.codminskeyboards.universekeyboard.database.DatabaseHelper;
import com.codminskeyboards.universekeyboard.model.FontsPaid;
import com.codminskeyboards.universekeyboard.model.KeyboardData;
import com.codminskeyboards.universekeyboard.model.NewSoundData;
import com.codminskeyboards.universekeyboard.utils.AsyncDownload;
import com.codminskeyboards.universekeyboard.utils.GlobalClass;
import com.codminskeyboards.universekeyboard.utils.MyBounceInterpolator_anim;
import com.codminskeyboards.universekeyboard.utils.RecyclerItemClickListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

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
    private TextView txtMainTitle;
    private CreateKeyboardActivity context;

    String[] fontArray = new String[0];
    private RecyclerView rvDefaultColor;
    private FillDefaultColorAdapter fillDefaultColorAdapter;
    private RecyclerView rvDefaultColorKeyDesign;
    private LinearLayout linWallpaperLayout;
    private LinearLayout linKeyDesignLayout;
    private RecyclerView rvDefaultColorFontStyle;
    private FillFontStyleAdapter fillFontStyleAdapter;
    private LinearLayout linFontStyleLayout;
    TextView vibrationValueTextView;
    private FillSoundEffectAdapter fillSoundEffectAdapter;
    static CreateKeyboardActivity createKeyboardActivity;
    private ImageView ivKeyboardBg;
    private GridView gvSoundEffect;
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
    int vibrationStrength;

    private AudioManager audioManager;
    String vibrationStrengthText;
    private LinearLayout linKeySoundLayout;
    private Vibrator vibrator;

    SoundPool soundPool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GlobalClass globalClass = new GlobalClass(CreateKeyboardActivity.this.getApplicationContext());

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.dark_gray));
        }
        setContentView(R.layout.activity_create_keyboard);
        setContent();
        setColorGridView();
        getColorFromDatabase();
        getFontFromDatabase();
        getSoundFromDatabase();

        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        vibrationValueTextView = findViewById(R.id.vibrationValueTextView);

        int initialVibrationValue = GlobalClass.getPreferencesInt(context, GlobalClass.vibrationStrength, 0);

        SeekBar seekBarVibration = findViewById(R.id.seekBarVibration);

        seekBarVibration.setProgress(initialVibrationValue);
        vibrationStrengthText = String.valueOf(initialVibrationValue) + " ms";
        vibrationValueTextView.setText(vibrationStrengthText);

        seekBarVibration.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                vibrationStrengthText = String.valueOf(progress) + " ms";
                vibrationValueTextView.setText(vibrationStrengthText);

                vibrationStrength = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
                vibrator.vibrate(progress);
                GlobalClass.setPreferencesInt(context, GlobalClass.vibrationStrength, progress);
            }
        });
    }

    private void setContent() {

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-2002759323605741/8308210294");

        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        ivHome = findViewById(R.id.ivHome);
        TextView txtApply = findViewById(R.id.txtApply);
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
        linKeySoundLayout = findViewById(R.id.linKeySoundLayout);
        gvSoundEffect = findViewById(R.id.gvSoundEffect);

        rvDefaultColor = findViewById(R.id.rvDefaultColor);
        rvDefaultColorKeyDesign = findViewById(R.id.rvDefaultColorKeyDesign);
        rvDefaultColorFontStyle = findViewById(R.id.rvDefaultColorFontStyle);

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

            // Default keyboard values
        } else {
            GlobalClass.selectwallpaper = 0;
            GlobalClass.tempKeyboardBgImage = R.drawable.theme_color1;
            GlobalClass.selectcolor = 0;
            GlobalClass.selview = 2;
            GlobalClass.tempIsColor = "no";
            GlobalClass.tempKeyboardColorCode = 0;
            GlobalClass.keyboardBitmapBack = null;
            GlobalClass.tempFontColor = "#FFFFFF";
            GlobalClass.tempKeyColor = getResources().getColor(R.color.two);
            GlobalClass.tempKeyRadius = "34";                                       // ranges between (0, 9, 18, 25, 34)
            GlobalClass.tempKeyStroke = "1";                                        // ranges between (1, 2, 3, 4, 5)
            GlobalClass.tempKeyOpacity = "64";                                      // ranges between (0, 64, 128, 192, 255)
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
                GlobalClass.tempSoundName = newSoundDataArrayList.get(position).getResourceId();
                if (position != 0) {
                    performKeySound();
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
        AdRequest adRequest = new AdRequest.Builder().build();

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

    private void performKeySound() {

        int ringerMode = audioManager.getRingerMode();

        if (ringerMode == AudioManager.RINGER_MODE_NORMAL) {
            try {
                if (soundPool == null)
                    soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);

                // audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamVolume(AudioManager.STREAM_MUSIC), 0);
                final int soundId = soundPool.load(context, GlobalClass.tempSoundName, 1);
                soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                    @Override
                    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                        soundPool.play(soundId, 1, 1, 0, 0, 1);
                    }
                });
            } catch (Exception ignored) {
            }
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
                startActivity(new Intent(getApplicationContext(), com.codminskeyboards.universekeyboard.activity.MainActivity.class));
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
                startActivity(new Intent(context, com.codminskeyboards.universekeyboard.activity.MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
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
                linKeySoundLayout.setVisibility(View.GONE);

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
                linKeySoundLayout.setVisibility(View.GONE);

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
                linKeySoundLayout.setVisibility(View.GONE);

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
                linKeySoundLayout.setVisibility(View.VISIBLE);

                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ivHome.performClick();
    }

}
