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

public class CreateKeyboardActivity extends AppCompatActivity {

    public InterstitialAd interstitialAd;
    private TextView titleTextView;
    private Context context;
    private ConstraintLayout keysLayout;
    public static KeyboardData keyboardData;
    private int editPosition;
    private boolean isEdit;

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

        GlobalClass.setInterstitialAd(interstitialAd);

        ImageView homeImageView = findViewById(R.id.homeImageView);
        TextView applyTextView = findViewById(R.id.applyTextView);
        titleTextView = findViewById(R.id.titleTextView);
        keysLayout = findViewById(R.id.keysLayout);
        ImageView backgroundImageView = findViewById(R.id.backgroundImageView);

        isEdit = getIntent().getBooleanExtra("isEdit", false);

        keyboardData = KeyboardData.deserialize(getIntent().getStringExtra("keyboardData"));

        editPosition = getIntent().getIntExtra("position", 0);

        if (keyboardData.getBackgroundIsDrawable()) {
            backgroundImageView.setImageResource(GlobalClass.backgroundArray[keyboardData.getBackgroundPosition()]);
        } else {
            backgroundImageView.setImageResource(GlobalClass.colorsArray[keyboardData.getBackgroundColorPosition()]);
        }

        homeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, MainActivity.class));
                //startAds();
            }
        });

        applyTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalClass.keyboardPosition = editPosition;

                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("isEdit", isEdit);
                intent.putExtra("keyboardData", KeyboardData.serialize(keyboardData));
                setResult(RESULT_OK, intent);
                // startAds();
                finish();
            }
        });
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
        int fontColor = getResources().getColor(GlobalClass.colorsArray[keyboardData.getFontColorPosition()]);
        int keyColor = getResources().getColor(GlobalClass.colorsArray[keyboardData.getKeyColorPosition()]);
        GradientDrawable keyBackground;
        for (int i = 0; i < keysLayout.getChildCount(); i++) {
            View child = keysLayout.getChildAt(i);
            if (child instanceof ImageView || child instanceof TextView) {
                keyBackground = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
                        new int[]{keyColor, keyColor});
                keyBackground.setBounds(child.getLeft() + 5, child.getTop() + 5, child.getRight() - 5, child.getBottom() - 5);
                keyBackground.setCornerRadius(keyboardData.getKeyRadius());
                keyBackground.setAlpha(keyboardData.getKeyOpacity());

                switch (keyboardData.getKeyStroke()) {
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
                    ((TextView) child).setTypeface(GlobalClass.fontsArray[keyboardData.getFontPosition()]);
                }

                if (child instanceof ImageView) {
                    ((ImageView) child).setColorFilter(fontColor, PorterDuff.Mode.SRC_ATOP);
                }
            }
        }
    }

   // public void startAds() {
    //    if (interstitialAd.isLoaded())
    //        interstitialAd.show();
   // }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // startAds();
    }
}
