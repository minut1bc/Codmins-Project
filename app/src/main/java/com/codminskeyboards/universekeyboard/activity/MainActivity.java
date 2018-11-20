package com.codminskeyboards.universekeyboard.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codminskeyboards.universekeyboard.R;
import com.codminskeyboards.universekeyboard.adapter.KeyboardViewPagerAdapter;
import com.codminskeyboards.universekeyboard.utils.GlobalClass;
import com.codminskeyboards.universekeyboard.utils.KeyboardData;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.kila.apprater_dialog.lars.AppRater;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //    IabHelper iabHelper;
    private Context context;
    private ViewPager viewPager;
    private KeyboardViewPagerAdapter keyboardViewPagerAdapter;
    ArrayList<KeyboardData> keyboardDataArrayList = new ArrayList<>();
    CircleIndicator circleIndicator;
    private ConstraintLayout keyboardDataLayout;
    boolean doubleBackToExitPressedOnce = false;
    boolean isThemeSlotPurchased = false;
    private String TAG = "Grid View Activity";
    private AdView adView;
    private ImageView addKeyboardImageView;
    private ImageView createKeyboardImageView;
    private ImageView applyImageView;
    private int requestCode;

    // Called when consumption is complete
//    IabHelper.OnConsumeFinishedListener consumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
//        public void onConsumeFinished(Purchase purchase, IabResult result) {
//            GlobalClass.printLog(TAG, "Consumption finished. Purchase: " + purchase + ", result: " + result);
//
//          if we were disposed of in the meantime, quit.
//          if (iabHelper == null) return;

            // We know this is the "gas" sku because it's the only one we consume,
            // so we don't check which sku was consumed. If you have more than one
            // sku, you probably should check...
//            if (result.isSuccess()) {
                // successfully consumed, so we apply the effects of the item in our
                // game world's logic, which in our case means filling the gas tank a bit
//                GlobalClass.printLog(TAG, "Consumption successful. Provisioning.");
//            } else {
//                complain("Error while consuming: " + result);
//            }
////            updateUi();
//
//            GlobalClass.printLog(TAG, "End consumption flow.");
//        }
//    };

    // Listener that's called when we finish querying the items and subscriptions we own
//    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
//        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
//            GlobalClass.printLog(TAG, "Query inventory finished.");
//
//            // Have we been disposed of in the meantime? If so, quit.
//            if (iabHelper == null) return;
//
//            // Is it a failure?
//            if (result.isFailure()) {
//                complain("Failed to query inventory: " + result);
//                return;
//            }
//
//            GlobalClass.printLog(TAG, "Query inventory was successful.");
//
//            /*
//             * Check for items we own. Notice that for each purchase, we check
//             * the developer payload to see if it's correct! See
//             * verifyDeveloperPayload().
//             */
//
//            // Do we have the Purchase for 50 Graphs?
//            Purchase getThemeSlotPurchase = inventory.getPurchase(GlobalClass.UNLOCK_THEMES_SLOTES);
//            isThemeSlotPurchased = (getThemeSlotPurchase != null && verifyDeveloperPayload(getThemeSlotPurchase));
//            GlobalClass.printLog(TAG, "User " + (isThemeSlotPurchased ? "HAS" : "DOES NOT HAVE") + " Purchase 50 graphs.");
//
//            GlobalClass.printLog("getFiftyGraphs====================", "" + getThemeSlotPurchase);
//            if (getThemeSlotPurchase != null && verifyDeveloperPayload(getThemeSlotPurchase)) {
//                iabHelper.consumeAsync(getThemeSlotPurchase, consumeFinishedListener);
//            }
//            GlobalClass.printLog(TAG, "Initial inventory query finished; enabling main UI.");
//        }
//    };
//
//    // Callback for when a purchase is finished
//    IabHelper.OnIabPurchaseFinishedListener purchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
//        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
//
//            GlobalClass.printLog(TAG, "Purchase finished: " + result + ", purchase: " + purchase);
//
//            // if we were disposed of in the meantime, quit.
//            if (iabHelper == null) return;
//
//            if (result.isSuccess()) {
//                updateData();
//            }
//
//            if (result.isFailure()) {
//                complain("Error purchasing: " + result);
//                return;
//            }
//
//            if (!verifyDeveloperPayload(purchase)) {
//                complain("Error purchasing. Authenticity verification failed.");
//                return;
//            }
//
//            updateData();
//            GlobalClass.printLog(TAG, "purchase Data- - - " + purchase);
//            iabHelper.consumeAsync(purchase, consumeFinishedListener);
//
//        }
//    };
//
//    // Verifies the developer payload of a purchase.
//    boolean verifyDeveloperPayload(Purchase purchase) {
//        String payload = purchase.getDeveloperPayload();
//
//        /*
//         * TODO: verify that the developer payload of the purchase is correct. It will be
//         * the same one that you sent when initiating the purchase.
//         *
//         * WARNING: Locally generating a random string when starting a purchase and
//         * verifying it here might seem like a good approach, but this will fail in the
//         * case where the user purchases an item on one device and then uses your app on
//         * a different device, because on the other device you will not have access to the
//         * random string you originally generated.
//         *
//         * So a good developer payload has these characteristics:
//         *
//         * 1. If two different users purchase an item, the payload is different between them,
//         *    so that one user's purchase can't be replayed to another user.
//         *
//         * 2. The payload must be such that you can verify it even when the app wasn't the
//         *    one who initiated the purchase flow (so that items purchased by the user on
//         *    one device work on other devices owned by the user).
//         *
//         * Using your own server to store and verify developer payloads across app
//         * installations is recommended.
//         */
//
//        return true;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = MainActivity.this;

        Crashlytics.log("Activity created");
        Crashlytics.log(Log.ERROR, "tag", "Message");

        setContentView(R.layout.activity_main);

        new AppRater.DefaultBuilder(context, getPackageName())
                .showDefault()
                .daysToWait(0)
                .timesToLaunch(1)
                .title("Rate " + getResources().getString(R.string.app_name))
                .appLaunched();

        setContent();

        GlobalClass.setResourcesArrays();

        if (GlobalClass.getPreferencesBool(context, GlobalClass.key_isAdLock, true))
            setAdMob();
    }


    private void updateData() {
        if (requestCode == GlobalClass.RC_REQUEST_THEMES_SLOTES)
            GlobalClass.setPreferencesBool(context, getString(R.string.theme_slot_purchased), true);
    }

    private void setAdMob() {
        adView = findViewById(R.id.adView);
        adView.loadAd(new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build());
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                adView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdClosed() {
                adView.setVisibility(View.GONE);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                adView.setVisibility(View.GONE);
            }
        });

    }

    void complain(String message) {
        GlobalClass.printLog(TAG, "**** TrivialDrive Error: " + message);
    }

    private void setContent() {
        InterstitialAd interstitialAd = new InterstitialAd(context);
        interstitialAd.setAdUnitId(getResources().getString(R.string.interstitial_full_screen));
        interstitialAd.loadAd(new AdRequest.Builder().build());

        viewPager = findViewById(R.id.viewPager);
        viewPager.setPageMarginDrawable(R.drawable.drawable_bg);

        TextView premiumTextView = findViewById(R.id.premiumTextView);
        TextView moreTextView = findViewById(R.id.moreTextView);
        keyboardDataLayout = findViewById(R.id.keyboardDataLayout);
        applyImageView = findViewById(R.id.applyImageView);
        circleIndicator = findViewById(R.id.circleIndicator);
        ImageView deleteImageView = findViewById(R.id.deleteImageView);
        createKeyboardImageView = findViewById(R.id.createKeyboardImageView);
        addKeyboardImageView = findViewById(R.id.addKeyboardImageView);

//        iabHelper = new IabHelper(context, GlobalClass.base64EncodedPublicKey);
//
//        // enable debug logging (for a production application, you should set this to false).
//        iabHelper.enableDebugLogging(true);
//        iabHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
//            public void onIabSetupFinished(IabResult result) {
//                if (!result.isSuccess()) {
//                    // Oh noes, there was a problem.
//                    complain("Problem setting up in-app billing: " + result);
//                    return;
//                }
//
//                // Have we been disposed of in the meantime? If so, quit.
//                if (iabHelper == null)
//                    return;
//                iabHelper.queryInventoryAsync(mGotInventoryListener);
//            }
//        });

        keyboardDataArrayList = GlobalClass.getPreferencesArrayList(context);

        if (keyboardDataArrayList != null) {
            if (keyboardDataArrayList.size() != 0) {
                for (int i = 0; i < keyboardDataArrayList.size(); i++)
                    if (keyboardDataArrayList.get(i).isSelected()) {
                        GlobalClass.setPreferencesInt(context, GlobalClass.KEYBOARD_BACKGROUND, keyboardDataArrayList.get(i).getKeyboardBackground());
                        GlobalClass.setPreferencesInt(context, GlobalClass.KEY_COLOR, keyboardDataArrayList.get(i).getKeyColor());
                        GlobalClass.setPreferencesInt(context, GlobalClass.KEY_RADIUS, keyboardDataArrayList.get(i).getKeyRadius());
                        GlobalClass.setPreferencesInt(context, GlobalClass.KEY_STROKE, keyboardDataArrayList.get(i).getKeyStroke());
                        GlobalClass.setPreferencesInt(context, GlobalClass.KEY_OPACITY, keyboardDataArrayList.get(i).getKeyOpacity());
                        GlobalClass.setPreferencesInt(context, GlobalClass.FONT_COLOR, keyboardDataArrayList.get(i).getFontColor());
                        GlobalClass.setPreferencesInt(context, GlobalClass.FONT_NAME, keyboardDataArrayList.get(i).getFontId());
                        GlobalClass.setPreferencesBool(context, GlobalClass.SOUND_STATUS, keyboardDataArrayList.get(i).getSoundStatus());
                        GlobalClass.setPreferencesInt(context, GlobalClass.SOUND_ID, keyboardDataArrayList.get(i).getSoundId());
                        GlobalClass.setPreferencesInt(context, GlobalClass.VIBRATION_VALUE, keyboardDataArrayList.get(i).getVibrationValue());
                    }

                keyboardViewPagerAdapter = new KeyboardViewPagerAdapter(context, keyboardDataArrayList);

                viewPager.setAdapter(keyboardViewPagerAdapter);
                circleIndicator.setViewPager(viewPager);
                createKeyboardImageView.setVisibility(View.GONE);
                keyboardDataLayout.setVisibility(View.VISIBLE);
                addKeyboardImageView.setVisibility(View.VISIBLE);

                if (keyboardDataArrayList.size() == 1) {
                    keyboardDataArrayList.get(0).setSelected(true);
                    applyImageView.setImageDrawable(getResources().getDrawable(R.drawable.btn_apply));
                    GlobalClass.setPreferencesInt(context, GlobalClass.KEYBOARD_BACKGROUND, keyboardDataArrayList.get(viewPager.getCurrentItem()).getKeyboardBackground());
                    GlobalClass.setPreferencesInt(context, GlobalClass.KEY_COLOR, keyboardDataArrayList.get(viewPager.getCurrentItem()).getKeyColor());
                    GlobalClass.setPreferencesInt(context, GlobalClass.KEY_RADIUS, keyboardDataArrayList.get(viewPager.getCurrentItem()).getKeyRadius());
                    GlobalClass.setPreferencesInt(context, GlobalClass.KEY_STROKE, keyboardDataArrayList.get(viewPager.getCurrentItem()).getKeyStroke());
                    GlobalClass.setPreferencesInt(context, GlobalClass.KEY_OPACITY, keyboardDataArrayList.get(viewPager.getCurrentItem()).getKeyOpacity());
                    GlobalClass.setPreferencesInt(context, GlobalClass.FONT_COLOR, keyboardDataArrayList.get(viewPager.getCurrentItem()).getFontColor());
                    GlobalClass.setPreferencesInt(context, GlobalClass.FONT_NAME, keyboardDataArrayList.get(viewPager.getCurrentItem()).getFontId());
                    GlobalClass.setPreferencesBool(context, GlobalClass.SOUND_STATUS, keyboardDataArrayList.get(viewPager.getCurrentItem()).getSoundStatus());
                    GlobalClass.setPreferencesInt(context, GlobalClass.SOUND_ID, keyboardDataArrayList.get(viewPager.getCurrentItem()).getSoundId());
                    GlobalClass.setPreferencesInt(context, GlobalClass.VIBRATION_VALUE, keyboardDataArrayList.get(viewPager.getCurrentItem()).getVibrationValue());
                    GlobalClass.setPreferencesInt(context, GlobalClass.BACKGROUND_POSITION, keyboardDataArrayList.get(viewPager.getCurrentItem()).getBackgroundPosition());
                    GlobalClass.setPreferencesInt(context, GlobalClass.COLOR_POSITION, keyboardDataArrayList.get(viewPager.getCurrentItem()).getColorPosition());
                    GlobalClass.setPreferencesInt(context, GlobalClass.DRAWABLE_OR_COLOR, keyboardDataArrayList.get(viewPager.getCurrentItem()).getDrawableOrColor());
                } else
                    applyImageView.setImageDrawable(getResources().getDrawable(R.drawable.btn_disable));

            } else {
                createKeyboardImageView.setVisibility(View.VISIBLE);
                keyboardDataLayout.setVisibility(View.GONE);
                addKeyboardImageView.setVisibility(View.GONE);
            }
        } else {
            keyboardDataArrayList = new ArrayList<>();
            createKeyboardImageView.setVisibility(View.VISIBLE);
            keyboardDataLayout.setVisibility(View.GONE);
            addKeyboardImageView.setVisibility(View.GONE);
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                if (keyboardDataArrayList.get(i).isSelected())
                    applyImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_apply));
                else
                    applyImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_disable));
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });

        premiumTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, PremiumStoreActivity.class));
            }
        });

        moreTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://play.google.com/store/apps/developer?id=Codmins+Keyboards");
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
            }
        });

        applyImageView.setOnClickListener(this);

        deleteImageView.setOnClickListener(this);

        createKeyboardImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addKeyboardImageView.performClick();
            }
        });

        addKeyboardImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDefaultValue();

                Intent intent = new Intent(context, CreateKeyboardActivity.class);
                intent.putExtra("isEdit", false);
                startActivity(intent);

                GlobalClass.checkStartAd();

                finish();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.applyImageView:
                if (GlobalClass.isKeyboardEnabled(context) && GlobalClass.isKeyboardSet(context)) {
                    if (keyboardDataArrayList.get(viewPager.getCurrentItem()).isSelected()) {
                        keyboardDataArrayList.get(viewPager.getCurrentItem()).setSelected(false);
                        applyImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_disable));

                        setDefaultValue();

                        GlobalClass.backgroundPosition = 0;
                        GlobalClass.keyboardBackground = R.drawable.background_01;
                        GlobalClass.colorPosition = 0;
                        GlobalClass.drawableOrColor = 0;
                        GlobalClass.fontColor = getResources().getColor(R.color.color_02);
                        GlobalClass.keyColor = getResources().getColor(R.color.color_02);
                        GlobalClass.keyRadius = 34;
                        GlobalClass.keyStroke = 1;
                        GlobalClass.keyOpacity = 64;
                        GlobalClass.fontId = R.font.abel_regular;
                        GlobalClass.soundStatus = false;
                        GlobalClass.soundId = 0;
                        GlobalClass.keyColorPosition = 1;
                        GlobalClass.fontColorPosition = 1;
                        GlobalClass.soundPosition = 0;
                        GlobalClass.fontPosition = 0;

                        GlobalClass.setPreferencesInt(context, GlobalClass.KEYBOARD_BACKGROUND, GlobalClass.keyboardBackground);
                        GlobalClass.setPreferencesInt(context, GlobalClass.KEY_COLOR, GlobalClass.keyColor);
                        GlobalClass.setPreferencesInt(context, GlobalClass.KEY_RADIUS, GlobalClass.keyRadius);
                        GlobalClass.setPreferencesInt(context, GlobalClass.KEY_STROKE, GlobalClass.keyStroke);
                        GlobalClass.setPreferencesInt(context, GlobalClass.KEY_OPACITY, GlobalClass.keyOpacity);
                        GlobalClass.setPreferencesInt(context, GlobalClass.FONT_COLOR, GlobalClass.fontColor);
                        GlobalClass.setPreferencesInt(context, GlobalClass.FONT_NAME, GlobalClass.fontId);
                        GlobalClass.setPreferencesBool(context, GlobalClass.SOUND_STATUS, GlobalClass.soundStatus);
                        GlobalClass.setPreferencesInt(context, GlobalClass.SOUND_ID, GlobalClass.soundId);
                        GlobalClass.setPreferencesInt(context, GlobalClass.VIBRATION_VALUE, GlobalClass.vibrationValue);
                        GlobalClass.setPreferencesInt(context, GlobalClass.BACKGROUND_POSITION, GlobalClass.backgroundPosition);
                        GlobalClass.setPreferencesInt(context, GlobalClass.COLOR_POSITION, GlobalClass.colorPosition);
                        GlobalClass.setPreferencesInt(context, GlobalClass.DRAWABLE_OR_COLOR, GlobalClass.drawableOrColor);
                    } else {
                        for (int i = 0; i < keyboardDataArrayList.size(); i++) {
                            if (i == viewPager.getCurrentItem())
                                keyboardDataArrayList.get(i).setSelected(true);
                            else
                                keyboardDataArrayList.get(i).setSelected(false);
                        }
                        applyImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_apply));

                        GlobalClass.setPreferencesInt(context, GlobalClass.KEYBOARD_BACKGROUND, keyboardDataArrayList.get(viewPager.getCurrentItem()).getKeyboardBackground());
                        GlobalClass.setPreferencesInt(context, GlobalClass.KEY_COLOR, keyboardDataArrayList.get(viewPager.getCurrentItem()).getKeyColor());
                        GlobalClass.setPreferencesInt(context, GlobalClass.KEY_RADIUS, keyboardDataArrayList.get(viewPager.getCurrentItem()).getKeyRadius());
                        GlobalClass.setPreferencesInt(context, GlobalClass.KEY_STROKE, keyboardDataArrayList.get(viewPager.getCurrentItem()).getKeyStroke());
                        GlobalClass.setPreferencesInt(context, GlobalClass.KEY_OPACITY, keyboardDataArrayList.get(viewPager.getCurrentItem()).getKeyOpacity());
                        GlobalClass.setPreferencesInt(context, GlobalClass.FONT_COLOR, keyboardDataArrayList.get(viewPager.getCurrentItem()).getFontColor());
                        GlobalClass.setPreferencesInt(context, GlobalClass.FONT_NAME, keyboardDataArrayList.get(viewPager.getCurrentItem()).getFontId());
                        GlobalClass.setPreferencesBool(context, GlobalClass.SOUND_STATUS, keyboardDataArrayList.get(viewPager.getCurrentItem()).getSoundStatus());
                        GlobalClass.setPreferencesInt(context, GlobalClass.SOUND_ID, keyboardDataArrayList.get(viewPager.getCurrentItem()).getSoundId());
                        GlobalClass.setPreferencesInt(context, GlobalClass.VIBRATION_VALUE, keyboardDataArrayList.get(viewPager.getCurrentItem()).getVibrationValue());
                        GlobalClass.setPreferencesInt(context, GlobalClass.BACKGROUND_POSITION, keyboardDataArrayList.get(viewPager.getCurrentItem()).getBackgroundPosition());
                        GlobalClass.setPreferencesInt(context, GlobalClass.COLOR_POSITION, keyboardDataArrayList.get(viewPager.getCurrentItem()).getColorPosition());
                        GlobalClass.setPreferencesInt(context, GlobalClass.DRAWABLE_OR_COLOR, keyboardDataArrayList.get(viewPager.getCurrentItem()).getDrawableOrColor());
                    }
                } else
                    startActivity(new Intent(context, SetKeyboardActivity.class));

                GlobalClass.setPreferencesArrayList(context, keyboardDataArrayList);
                break;

            case R.id.deleteImageView:
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you want to delete your custom keyboard ?")
                        .setCancelable(true)
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (keyboardDataArrayList.get(viewPager.getCurrentItem()).isSelected()) {
                                    applyImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_disable));
                                    if (keyboardDataArrayList.size() != 0) {
                                        keyboardDataArrayList.remove(viewPager.getCurrentItem());
                                        GlobalClass.setPreferencesArrayList(context, keyboardDataArrayList);
                                        keyboardViewPagerAdapter = new KeyboardViewPagerAdapter(context, keyboardDataArrayList);
                                        viewPager.setAdapter(keyboardViewPagerAdapter);
                                        keyboardViewPagerAdapter.notifyDataSetChanged();
                                    }

                                    //setDefaultValue();
                                    if (keyboardDataArrayList.size() != 0) {
                                        createKeyboardImageView.setVisibility(View.GONE);
                                        keyboardDataLayout.setVisibility(View.VISIBLE);
                                        addKeyboardImageView.setVisibility(View.VISIBLE);
                                    } else {
                                        createKeyboardImageView.setVisibility(View.VISIBLE);
                                        keyboardDataLayout.setVisibility(View.GONE);
                                        addKeyboardImageView.setVisibility(View.GONE);
                                    }

                                    GlobalClass.backgroundPosition = 0;
                                    GlobalClass.keyboardBackground = R.drawable.background_01;
                                    GlobalClass.colorPosition = 0;
                                    GlobalClass.drawableOrColor = 0;
                                    GlobalClass.fontColor = getResources().getColor(R.color.color_02);
                                    GlobalClass.keyColor = getResources().getColor(R.color.color_08);
                                    GlobalClass.keyRadius = 18;
                                    GlobalClass.keyStroke = 2;
                                    GlobalClass.keyOpacity = 255;
                                    GlobalClass.fontId = R.font.abel_regular;
                                    GlobalClass.soundStatus = false;
                                    GlobalClass.soundId = 0;
                                    GlobalClass.keyColorPosition = 1;
                                    GlobalClass.fontColorPosition = 1;
                                    GlobalClass.soundPosition = 0;
                                    GlobalClass.fontPosition = 0;

                                    GlobalClass.setPreferencesInt(context, GlobalClass.KEYBOARD_BACKGROUND, GlobalClass.keyboardBackground);
                                    GlobalClass.setPreferencesInt(context, GlobalClass.KEY_COLOR, GlobalClass.keyColor);
                                    GlobalClass.setPreferencesInt(context, GlobalClass.KEY_RADIUS, GlobalClass.keyRadius);
                                    GlobalClass.setPreferencesInt(context, GlobalClass.KEY_STROKE, GlobalClass.keyStroke);
                                    GlobalClass.setPreferencesInt(context, GlobalClass.KEY_OPACITY, GlobalClass.keyOpacity);
                                    GlobalClass.setPreferencesInt(context, GlobalClass.FONT_COLOR, GlobalClass.fontColor);
                                    GlobalClass.setPreferencesInt(context, GlobalClass.FONT_NAME, GlobalClass.fontId);
                                    GlobalClass.setPreferencesBool(context, GlobalClass.SOUND_STATUS, GlobalClass.soundStatus);
                                    GlobalClass.setPreferencesInt(context, GlobalClass.SOUND_ID, GlobalClass.soundId);
                                    GlobalClass.setPreferencesInt(context, GlobalClass.VIBRATION_VALUE, GlobalClass.vibrationValue);
                                    GlobalClass.setPreferencesInt(context, GlobalClass.BACKGROUND_POSITION, GlobalClass.backgroundPosition);
                                    GlobalClass.setPreferencesInt(context, GlobalClass.COLOR_POSITION, GlobalClass.colorPosition);
                                    GlobalClass.setPreferencesInt(context, GlobalClass.DRAWABLE_OR_COLOR, GlobalClass.drawableOrColor);
                                } else {
                                    if (keyboardDataArrayList.size() != 0) {
                                        keyboardDataArrayList.remove(viewPager.getCurrentItem());
                                        GlobalClass.setPreferencesArrayList(context, keyboardDataArrayList);
                                        keyboardViewPagerAdapter = new KeyboardViewPagerAdapter(context, keyboardDataArrayList);
                                        viewPager.setAdapter(keyboardViewPagerAdapter);
                                        keyboardViewPagerAdapter.notifyDataSetChanged();
                                    }

                                    //setDefaultValue();
                                    if (keyboardDataArrayList.size() != 0) {
                                        createKeyboardImageView.setVisibility(View.GONE);
                                        keyboardDataLayout.setVisibility(View.VISIBLE);
                                        addKeyboardImageView.setVisibility(View.VISIBLE);
                                    } else {
                                        createKeyboardImageView.setVisibility(View.VISIBLE);
                                        keyboardDataLayout.setVisibility(View.GONE);
                                        addKeyboardImageView.setVisibility(View.GONE);

                                        GlobalClass.backgroundPosition = 0;
                                        GlobalClass.keyboardBackground = R.drawable.background_01;
                                        GlobalClass.colorPosition = 0;
                                        GlobalClass.drawableOrColor = 0;
                                        GlobalClass.fontColor = getResources().getColor(R.color.color_02);
                                        GlobalClass.keyColor = getResources().getColor(R.color.color_08);
                                        GlobalClass.keyRadius = 18;
                                        GlobalClass.keyStroke = 2;
                                        GlobalClass.keyOpacity = 255;
                                        GlobalClass.fontId = R.font.abel_regular;
                                        GlobalClass.soundStatus = false;
                                        GlobalClass.soundId = 0;
                                        GlobalClass.keyColorPosition = 1;
                                        GlobalClass.fontColorPosition = 1;
                                        GlobalClass.soundPosition = 0;
                                        GlobalClass.fontPosition = 0;

                                        GlobalClass.setPreferencesInt(context, GlobalClass.KEYBOARD_BACKGROUND, GlobalClass.keyboardBackground);
                                        GlobalClass.setPreferencesInt(context, GlobalClass.KEY_COLOR, GlobalClass.keyColor);
                                        GlobalClass.setPreferencesInt(context, GlobalClass.KEY_RADIUS, GlobalClass.keyRadius);
                                        GlobalClass.setPreferencesInt(context, GlobalClass.KEY_STROKE, GlobalClass.keyStroke);
                                        GlobalClass.setPreferencesInt(context, GlobalClass.KEY_OPACITY, GlobalClass.keyOpacity);
                                        GlobalClass.setPreferencesInt(context, GlobalClass.FONT_COLOR, GlobalClass.fontColor);
                                        GlobalClass.setPreferencesInt(context, GlobalClass.FONT_NAME, GlobalClass.fontId);
                                        GlobalClass.setPreferencesBool(context, GlobalClass.SOUND_STATUS, GlobalClass.soundStatus);
                                        GlobalClass.setPreferencesInt(context, GlobalClass.SOUND_ID, GlobalClass.soundId);
                                        GlobalClass.setPreferencesInt(context, GlobalClass.VIBRATION_VALUE, GlobalClass.vibrationValue);
                                        GlobalClass.setPreferencesInt(context, GlobalClass.BACKGROUND_POSITION, GlobalClass.backgroundPosition);
                                        GlobalClass.setPreferencesInt(context, GlobalClass.COLOR_POSITION, GlobalClass.colorPosition);
                                        GlobalClass.setPreferencesInt(context, GlobalClass.DRAWABLE_OR_COLOR, GlobalClass.drawableOrColor);
                                    }
                                }
                                circleIndicator.setViewPager(viewPager);

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
                break;
        }
    }

    public void setDefaultValue() {
        GlobalClass.keyboardBackground = GlobalClass.getPreferencesInt(context, GlobalClass.KEYBOARD_BACKGROUND, R.drawable.background_01);
        GlobalClass.keyColor = GlobalClass.getPreferencesInt(context, GlobalClass.KEY_COLOR, getResources().getColor(R.color.color_08));
        GlobalClass.keyRadius = GlobalClass.getPreferencesInt(context, GlobalClass.KEY_RADIUS, 18);
        GlobalClass.keyStroke = GlobalClass.getPreferencesInt(context, GlobalClass.KEY_STROKE, 2);
        GlobalClass.keyOpacity = GlobalClass.getPreferencesInt(context, GlobalClass.KEY_OPACITY, 255);
        GlobalClass.fontColor = GlobalClass.getPreferencesInt(context, GlobalClass.FONT_COLOR, R.color.color_02);
        GlobalClass.fontId = GlobalClass.getPreferencesInt(context, GlobalClass.FONT_NAME, R.font.abel_regular);
        GlobalClass.soundStatus = GlobalClass.getPreferencesBool(context, GlobalClass.SOUND_STATUS, false);
        GlobalClass.soundId = GlobalClass.getPreferencesInt(context, GlobalClass.SOUND_ID, R.raw.balloon_snap);
        GlobalClass.vibrationValue = GlobalClass.getPreferencesInt(context, GlobalClass.VIBRATION_VALUE, 0);
        GlobalClass.backgroundPosition = GlobalClass.getPreferencesInt(context, GlobalClass.BACKGROUND_POSITION, 0);
        GlobalClass.colorPosition = GlobalClass.getPreferencesInt(context, GlobalClass.COLOR_POSITION, 0);
        GlobalClass.drawableOrColor = GlobalClass.getPreferencesInt(context, GlobalClass.DRAWABLE_OR_COLOR, 0);

        GlobalClass.setPreferencesInt(context, GlobalClass.KEYBOARD_BACKGROUND, GlobalClass.keyboardBackground);
        GlobalClass.setPreferencesInt(context, GlobalClass.KEY_COLOR, GlobalClass.keyColor);
        GlobalClass.setPreferencesInt(context, GlobalClass.KEY_RADIUS, GlobalClass.keyRadius);
        GlobalClass.setPreferencesInt(context, GlobalClass.KEY_STROKE, GlobalClass.keyStroke);
        GlobalClass.setPreferencesInt(context, GlobalClass.KEY_OPACITY, GlobalClass.keyOpacity);
        GlobalClass.setPreferencesInt(context, GlobalClass.FONT_COLOR, GlobalClass.fontColor);
        GlobalClass.setPreferencesInt(context, GlobalClass.FONT_NAME, GlobalClass.fontId);
        GlobalClass.setPreferencesBool(context, GlobalClass.SOUND_STATUS, GlobalClass.soundStatus);
        GlobalClass.setPreferencesInt(context, GlobalClass.SOUND_ID, GlobalClass.soundId);
        GlobalClass.setPreferencesInt(context, GlobalClass.VIBRATION_VALUE, GlobalClass.vibrationValue);
        GlobalClass.setPreferencesInt(context, GlobalClass.BACKGROUND_POSITION, GlobalClass.backgroundPosition);
        GlobalClass.setPreferencesInt(context, GlobalClass.COLOR_POSITION, GlobalClass.colorPosition);
        GlobalClass.setPreferencesInt(context, GlobalClass.DRAWABLE_OR_COLOR, GlobalClass.drawableOrColor);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

}