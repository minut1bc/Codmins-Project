package com.codminskeyboards.universekeyboard.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codminskeyboards.universekeyboard.R;
import com.codminskeyboards.universekeyboard.adapter.MyFragmentPagerAdapter;
import com.codminskeyboards.universekeyboard.model.KeyboardData;
import com.codminskeyboards.universekeyboard.utils.GlobalClass;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.io.IOException;
import java.util.ArrayList;

public class CreateKeyboardActivity extends AppCompatActivity implements View.OnClickListener {

    public InterstitialAd interstitialAd;
    private ImageView homeImageView;

    String[] fontArray = new String[0];
    private TextView titleTextView;
    private Context context;
    private ConstraintLayout keysLayout;
    private boolean isEdit = false;
    private int editPosition;
    ArrayList<KeyboardData> keyboardDataArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = CreateKeyboardActivity.this;

        setContentView(R.layout.activity_create_keyboard);

        setContent();

        redrawKeyboard();

        setViewPager();
    }

    private void setViewPager() {
        ViewPager fragmentViewPager = findViewById(R.id.fragmentViewPager);
        final TabLayout tabLayout = findViewById(R.id.tabLayout);

        MyFragmentPagerAdapter viewPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        fragmentViewPager.setAdapter(viewPagerAdapter);
        fragmentViewPager.setOffscreenPageLimit(4);

        tabLayout.setupWithViewPager(fragmentViewPager);

        TabLayout.Tab firstTab = tabLayout.getTabAt(0);
        TabLayout.Tab secondTab = tabLayout.getTabAt(1);
        TabLayout.Tab thirdTab = tabLayout.getTabAt(2);
        TabLayout.Tab fourthTab = tabLayout.getTabAt(3);

        if (firstTab != null && secondTab != null && thirdTab != null && fourthTab != null) {
            firstTab.setIcon(R.drawable.ic_wallpaper);
            secondTab.setIcon(R.drawable.ic_keydesign);
            thirdTab.setIcon(R.drawable.ic_font_style);
            fourthTab.setIcon(R.drawable.ic_soundeffect);
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(fragmentViewPager) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);
                int tabPosition = tab.getPosition();
                switch (tabPosition) {
                    case 0:
                        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.green));
                        titleTextView.setTextColor(getResources().getColor(R.color.green));
                        break;
                    case 1:
                        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.pink));
                        titleTextView.setTextColor(getResources().getColor(R.color.pink));
                        break;
                    case 2:
                        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.dark_red));
                        titleTextView.setTextColor(getResources().getColor(R.color.dark_red));
                        break;
                    case 3:
                        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.orange));
                        titleTextView.setTextColor(getResources().getColor(R.color.orange));
                        break;
                }
            }
        });
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.green));
        titleTextView.setTextColor(getResources().getColor(R.color.green));
    }

    public void redrawKeyboard() {
        int fontColor = Color.parseColor(GlobalClass.tempFontColor);
        GradientDrawable keyGradientDrawable;
        for (int i = 0; i < keysLayout.getChildCount(); i++) {
            View child = keysLayout.getChildAt(i);
            if (child instanceof ImageView || child instanceof TextView) {
                keyGradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{GlobalClass.tempKeyColor, GlobalClass.tempKeyColor});
                keyGradientDrawable.setBounds(child.getLeft() + 5, child.getTop() + 5, child.getRight() - 5, child.getBottom() - 5);
                keyGradientDrawable.setCornerRadius(GlobalClass.tempKeyRadius);
                keyGradientDrawable.setAlpha(GlobalClass.tempKeyOpacity);

                switch (GlobalClass.tempKeyStroke) {
                    case 1:
                        keyGradientDrawable.setStroke(0, getResources().getColor(R.color.colorPrimary));
                        break;
                    case 2:
                        keyGradientDrawable.setStroke(2, Color.WHITE);
                        break;
                    case 3:
                        keyGradientDrawable.setStroke(2, Color.BLACK);
                        break;
                    case 4:
                        keyGradientDrawable.setStroke(4, Color.BLACK);
                        break;
                    case 5:
                        keyGradientDrawable.setStroke(3, getResources().getColor(R.color.gray));
                        break;
                }

                child.setBackground(keyGradientDrawable);

                if (child instanceof TextView) {
                    ((TextView) child).setTextColor(fontColor);
                    try {
                        ((TextView) child).setTypeface(Typeface.createFromAsset(getAssets(), GlobalClass.tempFontName));
                    } catch (Exception ignored) {
                    }
                }

                if (child instanceof ImageView)
                    ((ImageView) child).setColorFilter((fontColor), PorterDuff.Mode.SRC_ATOP);
            }
        }
    }

    public void startAds() {
        if (interstitialAd.isLoaded())
            interstitialAd.show();
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
        interstitialAd = new InterstitialAd(context);
        interstitialAd.setAdUnitId(getResources().getString(R.string.interstitial_full_screen));
        interstitialAd.loadAd(new AdRequest.Builder().build());

        GlobalClass globalClass = new GlobalClass(context, interstitialAd);

        homeImageView = findViewById(R.id.homeImageView);
        TextView applyTextView = findViewById(R.id.applyTextView);
        titleTextView = findViewById(R.id.titleTextView);
        keysLayout = findViewById(R.id.keysLayout);
        ImageView backgroundImageView = findViewById(R.id.backgroundImageView);

        homeImageView.setOnClickListener(this);
        applyTextView.setOnClickListener(this);

        if (GlobalClass.getPreferencesArrayList(context) != null) {
            keyboardDataArrayList = GlobalClass.getPreferencesArrayList(context);
        }

        try {
            fontArray = getAssets().list("fonts");
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (getIntent().getBooleanExtra("isEdit", false)) {
            isEdit = true;
            editPosition = getIntent().getIntExtra("position", 0);

            backgroundImageView.setImageResource(GlobalClass.getPreferencesInt(context, GlobalClass.KEYBOARD_BG_IMAGE, 0));
            GlobalClass.selectwallpaper = GlobalClass.getPreferencesInt(context, GlobalClass.SELECTWALLPAPER, 0);
            GlobalClass.selectcolor = GlobalClass.getPreferencesInt(context, GlobalClass.SELECTCOLOR, 0);
            GlobalClass.selview = GlobalClass.getPreferencesInt(context, GlobalClass.SELECTVIEW, 0);
            GlobalClass.tempKeyRadius = GlobalClass.getPreferencesInt(context, GlobalClass.KEY_RADIUS, 18);
            GlobalClass.tempKeyStroke = GlobalClass.getPreferencesInt(context, GlobalClass.KEY_STROKE, 2);
            GlobalClass.tempKeyOpacity = GlobalClass.getPreferencesInt(context, GlobalClass.KEY_OPACITY, 255);
            GlobalClass.tempFontName = GlobalClass.getPreferencesString(context, GlobalClass.FONT_NAME, "Abel_Regular.ttf");
            GlobalClass.soundStatus = GlobalClass.getPreferencesBool(context, GlobalClass.SOUND_STATUS, false);
            GlobalClass.soundId = GlobalClass.getPreferencesInt(context, GlobalClass.SOUND_NAME, R.raw.balloon_snap);
            GlobalClass.selectbgcolor = getColorPos(GlobalClass.getPreferencesInt(context, GlobalClass.KEY_BG_COLOR, 7));
            GlobalClass.selectfontcolor = getColorPos(android.graphics.Color.parseColor(GlobalClass.getPreferencesString(context, GlobalClass.FONT_COLOR, "#FFFFFF")));
            GlobalClass.selectsounds = getSoundPos(GlobalClass.soundId);

            String remove = "fonts/";
            String fontName = removeWords(GlobalClass.tempFontName, remove);
            GlobalClass.selectfonts = getFontPos(fontName);

        } else {        // Default keyboard values
            GlobalClass.selectwallpaper = 0;
            GlobalClass.tempKeyboardBgImage = R.drawable.background_1;
            GlobalClass.selectcolor = 0;
            GlobalClass.selview = 0;
            GlobalClass.tempKeyboardColorCode = 0;
            GlobalClass.tempFontColor = "#FFFFFF";
            GlobalClass.tempKeyColor = getResources().getColor(R.color.two);
            GlobalClass.tempKeyRadius = 34;                                       // ranges between (0, 9, 18, 25, 34)
            GlobalClass.tempKeyStroke = 1;                                        // ranges between (1, 2, 3, 4, 5)
            GlobalClass.tempKeyOpacity = 64;                                      // ranges between (0, 64, 128, 192, 255)
            GlobalClass.tempFontName = "";
            GlobalClass.soundStatus = false;
            GlobalClass.soundId = 0;
            GlobalClass.selectbgcolor = 7;
            GlobalClass.selectfontcolor = 1;
            GlobalClass.selectsounds = 0;
            GlobalClass.selectfonts = 0;
            backgroundImageView.setImageResource(GlobalClass.tempKeyboardBgImage);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.homeImageView:
                startActivity(new Intent(context, MainActivity.class));
                startAds();
                break;

            case R.id.applyTextView:
                KeyboardData keyboardData = new KeyboardData();
                keyboardData.setKeyboardBgImage(GlobalClass.tempKeyboardBgImage);
                keyboardData.setKeyboardColorCode(GlobalClass.tempKeyboardColorCode);
                keyboardData.setKeyBgColor(GlobalClass.tempKeyColor);
                keyboardData.setKeyRadius(GlobalClass.tempKeyRadius);
                keyboardData.setKeyStroke(GlobalClass.tempKeyStroke);
                keyboardData.setKeyOpacity(GlobalClass.tempKeyOpacity);
                keyboardData.setFontColor(GlobalClass.tempFontColor);
                keyboardData.setFontName(GlobalClass.tempFontName);
                keyboardData.setSoundStatus(GlobalClass.soundStatus);
                keyboardData.setSoundName(GlobalClass.soundId);
                keyboardData.setSelectwallpaper(GlobalClass.selectwallpaper);
                keyboardData.setSelectcolor(GlobalClass.selectcolor);
                keyboardData.setSelview(GlobalClass.selview);

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
                startAds();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        homeImageView.performClick();
    }

}
