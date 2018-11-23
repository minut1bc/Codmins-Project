package com.codminskeyboards.universekeyboard.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
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
import com.codminskeyboards.universekeyboard.utils.GlobalClass;
import com.codminskeyboards.universekeyboard.utils.KeyboardData;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;

public class CreateKeyboardActivity extends AppCompatActivity {

    public InterstitialAd interstitialAd;
    private ImageView homeImageView;
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

        setViewPager();

        redrawKeyboard();
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

        homeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, MainActivity.class));
                startAds();
            }
        });

        applyTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardData keyboardData = new KeyboardData();
                keyboardData.setBackgroundIsDrawable(GlobalClass.backgroundIsDrawable);
                keyboardData.setBackgroundPosition(GlobalClass.backgroundPosition);
                keyboardData.setBackgroundColorPosition(GlobalClass.backgroundColorPosition);
                keyboardData.setKeyRadius(GlobalClass.keyRadius);
                keyboardData.setKeyStroke(GlobalClass.keyStroke);
                keyboardData.setKeyOpacity(GlobalClass.keyOpacity);
                keyboardData.setKeyColorPosition(GlobalClass.keyColorPosition);
                keyboardData.setFontPosition(GlobalClass.fontPosition);
                keyboardData.setFontColorPosition(GlobalClass.fontColorPosition);
                keyboardData.setVibrationValue(GlobalClass.vibrationValue);
                keyboardData.setSoundPosition(GlobalClass.soundPosition);
                keyboardData.setSoundOn(GlobalClass.soundOn);

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
            }
        });

        if (GlobalClass.getPreferencesArrayList(context) != null) {
            keyboardDataArrayList = GlobalClass.getPreferencesArrayList(context);
        }

        if (getIntent().getBooleanExtra("isEdit", false)) {
            isEdit = true;
            editPosition = getIntent().getIntExtra("position", 0);

            GlobalClass.backgroundIsDrawable = GlobalClass.getPreferencesBool(context, GlobalClass.BACKGROUND_IS_DRAWABLE, true);
            GlobalClass.backgroundPosition = GlobalClass.getPreferencesInt(context, GlobalClass.BACKGROUND_POSITION, 0);
            GlobalClass.backgroundColorPosition = GlobalClass.getPreferencesInt(context, GlobalClass.BACKGROUND_COLOR_POSITION, 0);
            GlobalClass.keyRadius = GlobalClass.getPreferencesInt(context, GlobalClass.KEY_RADIUS, 34);
            GlobalClass.keyStroke = GlobalClass.getPreferencesInt(context, GlobalClass.KEY_STROKE, 1);
            GlobalClass.keyOpacity = GlobalClass.getPreferencesInt(context, GlobalClass.KEY_OPACITY, 64);
            GlobalClass.keyColorPosition = GlobalClass.getPreferencesInt(context, GlobalClass.KEY_COLOR_POSITION, 1);
            GlobalClass.fontPosition = GlobalClass.getPreferencesInt(context, GlobalClass.FONT_POSITION, 0);
            GlobalClass.fontColorPosition = GlobalClass.getPreferencesInt(context, GlobalClass.FONT_COLOR_POSITION, 1);
            GlobalClass.vibrationValue = GlobalClass.getPreferencesInt(context, GlobalClass.VIBRATION_VALUE, 0);
            GlobalClass.soundPosition = GlobalClass.getPreferencesInt(context, GlobalClass.SOUND_POSITION, 0);
            GlobalClass.soundOn = GlobalClass.getPreferencesBool(context, GlobalClass.SOUND_ON, false);

            if (GlobalClass.backgroundIsDrawable) {
                backgroundImageView.setImageResource(GlobalClass.backgroundArray[GlobalClass.backgroundPosition]);
            } else {
                backgroundImageView.setImageResource(GlobalClass.colorsArray[GlobalClass.backgroundColorPosition]);
            }

        } else {        // Default keyboard values
            GlobalClass.backgroundIsDrawable = true;
            GlobalClass.backgroundPosition = 0;
            GlobalClass.backgroundColorPosition = 0;
            GlobalClass.keyRadius = 34;                                       // ranges between (0, 9, 18, 25, 34)
            GlobalClass.keyStroke = 1;                                        // ranges between (1, 2, 3, 4, 5)
            GlobalClass.keyOpacity = 64;                                      // ranges between (0, 64, 128, 192, 255)
            GlobalClass.keyColorPosition = 1;
            GlobalClass.fontPosition = 0;
            GlobalClass.fontColorPosition = 1;
            GlobalClass.vibrationValue = 0;
            GlobalClass.soundPosition = 0;
            GlobalClass.soundOn = false;

            backgroundImageView.setImageResource(GlobalClass.backgroundArray[0]);       //TODO: Check if you can remove it
        }
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
        int fontColor = getResources().getColor(GlobalClass.colorsArray[GlobalClass.fontColorPosition]);
        int keyColor = getResources().getColor(GlobalClass.colorsArray[GlobalClass.keyColorPosition]);
        GradientDrawable keyBackground;
        for (int i = 0; i < keysLayout.getChildCount(); i++) {
            View child = keysLayout.getChildAt(i);
            if (child instanceof ImageView || child instanceof TextView) {
                keyBackground = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
                        new int[]{keyColor, keyColor});
                keyBackground.setBounds(child.getLeft() + 5, child.getTop() + 5, child.getRight() - 5, child.getBottom() - 5);
                keyBackground.setCornerRadius(GlobalClass.keyRadius);
                keyBackground.setAlpha(GlobalClass.keyOpacity);

                switch (GlobalClass.keyStroke) {
                    case 1:
                        keyBackground.setStroke(0, getResources().getColor(R.color.colorPrimary));
                        break;
                    case 2:
                        keyBackground.setStroke(2, Color.WHITE);
                        break;
                    case 3:
                        keyBackground.setStroke(2, Color.BLACK);
                        break;
                    case 4:
                        keyBackground.setStroke(4, Color.BLACK);
                        break;
                    case 5:
                        keyBackground.setStroke(3, getResources().getColor(R.color.gray));
                        break;
                }

                child.setBackground(keyBackground);

                if (child instanceof TextView) {
                    ((TextView) child).setTextColor(fontColor);
                    ((TextView) child).setTypeface(GlobalClass.fontsArray[GlobalClass.fontPosition]);
                }

                if (child instanceof ImageView) {
                    ((ImageView) child).setColorFilter(fontColor, PorterDuff.Mode.SRC_ATOP);
                }
            }
        }
    }

    public void startAds() {
        if (interstitialAd.isLoaded())
            interstitialAd.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        homeImageView.performClick();
    }

}
