package com.codminskeyboards.universekeyboard.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codminskeyboards.universekeyboard.R;
import com.codminskeyboards.universekeyboard.adapter.MyFragmentPagerAdapter;
import com.codminskeyboards.universekeyboard.model.KeyboardData;
import com.codminskeyboards.universekeyboard.utils.GlobalClass;
import com.codminskeyboards.universekeyboard.utils.MyBounceInterpolator_anim;
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
    private TextView txtMainTitle;
    private CreateKeyboardActivity context;

    String[] fontArray = new String[0];

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
    private ConstraintLayout keyboardKeysLayout;
    private CircleImageView ivStrokeOne;
    private CircleImageView ivStrokeTwo;
    private CircleImageView ivStrokeThree;
    private CircleImageView ivStrokeFour;
    private CircleImageView ivStrokeFive;
    private ImageView ivDone;
    private ImageView ivShift;
    private ImageView ivCancel;

    static CreateKeyboardActivity createKeyboardActivity;
    public InterstitialAd mInterstitialAd;
    public com.google.android.gms.ads.InterstitialAd mInterstitial;
    private boolean isEdit = false;
    private int editPosition;
    Animation myAnim;
    MyBounceInterpolator_anim interpolator;
    ArrayList<KeyboardData> keyboardDataArrayList = new ArrayList<>();

    public static CreateKeyboardActivity getInstance() {        //TODO: remove need for getInstance()
        return createKeyboardActivity;
    }

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

        ViewPager viewPager = findViewById(R.id.viewPagerCreateKeyboardActivity);
        MyFragmentPagerAdapter viewPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
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
        for (int i = 0; i < keyboardKeysLayout.getChildCount(); i++) {
            final View mChild = keyboardKeysLayout.getChildAt(i);
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

    private void setContent() {

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-2002759323605741/8308210294");

        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        ivHome = findViewById(R.id.ivHome);
        TextView txtApply = findViewById(R.id.txtApply);
        txtMainTitle = findViewById(R.id.txtMainTitle);

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

        // id for the preview keyboard layout
        keyboardKeysLayout = findViewById(R.id.keyboardKeysLayout);

        //id for apply stroke
        ivStrokeOne = findViewById(R.id.ivStrokeOne);
        ivStrokeTwo = findViewById(R.id.ivStrokeTwo);
        ivStrokeThree = findViewById(R.id.ivStrokeThree);
        ivStrokeFour = findViewById(R.id.ivStrokeFour);
        ivStrokeFive = findViewById(R.id.ivStrokeFive);

        ivDone = findViewById(R.id.ivDone);
        ivShift = findViewById(R.id.ivShift);
        ivCancel = findViewById(R.id.ivCancel);

        ImageView ivKeyboardBg = findViewById(R.id.ivKeyboardBg);

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

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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

                break;

            case R.id.ivKeyDesign:

                //set title color
                txtMainTitle.setTextColor(getResources().getColor(R.color.pink));

                break;

            case R.id.ivFontStyle:

                //set title color
                txtMainTitle.setTextColor(getResources().getColor(R.color.dark_red));

                break;
            case R.id.ivSoundEffect:

                //set title colors
                txtMainTitle.setTextColor(getResources().getColor(R.color.orange));

                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ivHome.performClick();
    }

}
