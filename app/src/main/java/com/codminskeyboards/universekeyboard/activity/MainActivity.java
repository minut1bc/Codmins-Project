package com.codminskeyboards.universekeyboard.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.codminskeyboards.universekeyboard.R;
import com.codminskeyboards.universekeyboard.adapter.KeyboardViewPagerAdapter;
import com.codminskeyboards.universekeyboard.billing.IabHelper;
import com.codminskeyboards.universekeyboard.billing.IabResult;
import com.codminskeyboards.universekeyboard.billing.Inventory;
import com.codminskeyboards.universekeyboard.billing.Purchase;
import com.codminskeyboards.universekeyboard.model.KeyboardData;
import com.codminskeyboards.universekeyboard.utils.GlobalClass;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.kila.apprater_dialog.lars.AppRater;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    IabHelper iabHelper;
    private Context context;
    private ViewPager viewPager;
    private KeyboardViewPagerAdapter keyboardViewPagerAdapter;
    ArrayList<KeyboardData> keyboardDataArrayList = new ArrayList<>();
    CircleIndicator circleIndicator;
    private LinearLayout linKeyboardData;
    boolean doubleBackToExitPressedOnce = false;
    boolean isThemeSlotPurchased = false;
    private String TAG = "Grid View Activity";
    // Called when consumption is complete
    IabHelper.OnConsumeFinishedListener consumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
        public void onConsumeFinished(Purchase purchase, IabResult result) {
            GlobalClass.printLog(TAG, "Consumption finished. Purchase: " + purchase + ", result: " + result);

            // if we were disposed of in the meantime, quit.
            if (iabHelper == null) return;

            // We know this is the "gas" sku because it's the only one we consume,
            // so we don't check which sku was consumed. If you have more than one
            // sku, you probably should check...
            if (result.isSuccess()) {
                // successfully consumed, so we apply the effects of the item in our
                // game world's logic, which in our case means filling the gas tank a bit
                GlobalClass.printLog(TAG, "Consumption successful. Provisioning.");
            } else {
                complain("Error while consuming: " + result);
            }
//            updateUi();

            GlobalClass.printLog(TAG, "End consumption flow.");
        }
    };
    // Listener that's called when we finish querying the items and subscriptions we own
    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
            GlobalClass.printLog(TAG, "Query inventory finished.");

            // Have we been disposed of in the meantime? If so, quit.
            if (iabHelper == null) return;

            // Is it a failure?
            if (result.isFailure()) {
                complain("Failed to query inventory: " + result);
                return;
            }

            GlobalClass.printLog(TAG, "Query inventory was successful.");

            /*
             * Check for items we own. Notice that for each purchase, we check
             * the developer payload to see if it's correct! See
             * verifyDeveloperPayload().
             */

            // Do we have the Purchase for 50 Graphs?
            Purchase getThemeSlotPurchase = inventory.getPurchase(GlobalClass.UNLOCK_THEMES_SLOTES);
            isThemeSlotPurchased = (getThemeSlotPurchase != null && verifyDeveloperPayload(getThemeSlotPurchase));
            GlobalClass.printLog(TAG, "User " + (isThemeSlotPurchased ? "HAS" : "DOES NOT HAVE") + " Purchase 50 graphs.");

            GlobalClass.printLog("getFiftyGraphs====================", "" + getThemeSlotPurchase);
            if (getThemeSlotPurchase != null && verifyDeveloperPayload(getThemeSlotPurchase)) {
                iabHelper.consumeAsync(getThemeSlotPurchase, consumeFinishedListener);
            }
            GlobalClass.printLog(TAG, "Initial inventory query finished; enabling main UI.");
        }
    };
    private ImageView addKeyboardImageView;
    private ImageView createKeyboardImageView;
    private ImageView applyImageView;
    private int requestCode;
    // Callback for when a purchase is finished
    IabHelper.OnIabPurchaseFinishedListener purchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {

            GlobalClass.printLog(TAG, "Purchase finished: " + result + ", purchase: " + purchase);

            // if we were disposed of in the meantime, quit.
            if (iabHelper == null) return;

            if (result.isSuccess()) {
                updateData();
            }

            if (result.isFailure()) {
                complain("Error purchasing: " + result);
                return;
            }

            if (!verifyDeveloperPayload(purchase)) {
                complain("Error purchasing. Authenticity verification failed.");
                return;
            }

            updateData();
            GlobalClass.printLog(TAG, "purchase Data- - - " + purchase);
            iabHelper.consumeAsync(purchase, consumeFinishedListener);

        }
    };


    // Verifies the developer payload of a purchase.
    boolean verifyDeveloperPayload(Purchase purchase) {
        String payload = purchase.getDeveloperPayload();

        /*
         * TODO: verify that the developer payload of the purchase is correct. It will be
         * the same one that you sent when initiating the purchase.
         *
         * WARNING: Locally generating a random string when starting a purchase and
         * verifying it here might seem like a good approach, but this will fail in the
         * case where the user purchases an item on one device and then uses your app on
         * a different device, because on the other device you will not have access to the
         * random string you originally generated.
         *
         * So a good developer payload has these characteristics:
         *
         * 1. If two different users purchase an item, the payload is different between them,
         *    so that one user's purchase can't be replayed to another user.
         *
         * 2. The payload must be such that you can verify it even when the app wasn't the
         *    one who initiated the purchase flow (so that items purchased by the user on
         *    one device work on other devices owned by the user).
         *
         * Using your own server to store and verify developer payloads across app
         * installations is recommended.
         */

        return true;
    }

    private AdView adView;

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

        addKeyboardImageView = findViewById(R.id.addKeyboardImageView);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setPageMarginDrawable(R.drawable.drawable_bg);

        ImageView deleteImageView = findViewById(R.id.deleteImageView);
        createKeyboardImageView = findViewById(R.id.createKeyboardImageView);
        linKeyboardData = findViewById(R.id.linKeyboardData);
        applyImageView = findViewById(R.id.applyImageView);
        TextView premiumTextView = findViewById(R.id.premiumTextView);
        TextView moreTextView = findViewById(R.id.moreTextView);

        iabHelper = new IabHelper(context, GlobalClass.base64EncodedPublicKey);

        // enable debug logging (for a production application, you should set this to false).
        iabHelper.enableDebugLogging(true);
        iabHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess()) {
                    // Oh noes, there was a problem.
                    complain("Problem setting up in-app billing: " + result);
                    return;
                }

                // Have we been disposed of in the meantime? If so, quit.
                if (iabHelper == null) return;
                iabHelper.queryInventoryAsync(mGotInventoryListener);
            }
        });

        addKeyboardImageView.setOnClickListener(this);
        deleteImageView.setOnClickListener(this);
        createKeyboardImageView.setOnClickListener(this);
        applyImageView.setOnClickListener(this);
        moreTextView.setOnClickListener(this);
        premiumTextView.setOnClickListener(this);

        keyboardDataArrayList = GlobalClass.getPreferencesArrayList(context);

        if (keyboardDataArrayList != null) {
            if (keyboardDataArrayList.size() != 0) {
                for (int i = 0; i < keyboardDataArrayList.size(); i++)
                    if (keyboardDataArrayList.get(i).isSelected()) {
                        GlobalClass.setPreferencesInt(context, GlobalClass.KEYBOARD_COLOR_CODE, keyboardDataArrayList.get(i).getKeyboardColorCode());
                        GlobalClass.setPreferencesInt(context, GlobalClass.KEYBOARD_BG_IMAGE, keyboardDataArrayList.get(i).getKeyboardBgImage());
                        GlobalClass.setPreferencesInt(context, GlobalClass.KEY_BG_COLOR, keyboardDataArrayList.get(i).getKeyBgColor());
                        GlobalClass.setPreferencesInt(context, GlobalClass.KEY_RADIUS, keyboardDataArrayList.get(i).getKeyRadius());
                        GlobalClass.setPreferencesInt(context, GlobalClass.KEY_STROKE, keyboardDataArrayList.get(i).getKeyStroke());
                        GlobalClass.setPreferencesInt(context, GlobalClass.KEY_OPACITY, keyboardDataArrayList.get(i).getKeyOpacity());
                        GlobalClass.setPreferencesString(context, GlobalClass.FONT_COLOR, keyboardDataArrayList.get(i).getFontColor());
                        GlobalClass.setPreferencesString(context, GlobalClass.FONT_NAME, keyboardDataArrayList.get(i).getFontName());
                        GlobalClass.setPreferencesBool(context, GlobalClass.SOUND_STATUS, keyboardDataArrayList.get(i).getSoundStatus());
                        GlobalClass.setPreferencesInt(context, GlobalClass.SOUND_NAME, keyboardDataArrayList.get(i).getSoundName());
                    }

                keyboardViewPagerAdapter = new KeyboardViewPagerAdapter(context, keyboardDataArrayList);
                circleIndicator = findViewById(R.id.circleIndicator);
                viewPager.setAdapter(keyboardViewPagerAdapter);
                circleIndicator.setViewPager(viewPager);
                createKeyboardImageView.setVisibility(View.GONE);
                linKeyboardData.setVisibility(View.VISIBLE);
                addKeyboardImageView.setVisibility(View.VISIBLE);

                if (keyboardDataArrayList.size() == 1) {
                    keyboardDataArrayList.get(0).setSelected(true);
                    applyImageView.setImageDrawable(getResources().getDrawable(R.drawable.btn_apply));
                    GlobalClass.setPreferencesInt(context, GlobalClass.KEYBOARD_COLOR_CODE, keyboardDataArrayList.get(viewPager.getCurrentItem()).getKeyboardColorCode());
                    GlobalClass.setPreferencesInt(context, GlobalClass.KEYBOARD_BG_IMAGE, keyboardDataArrayList.get(viewPager.getCurrentItem()).getKeyboardBgImage());
                    GlobalClass.setPreferencesInt(context, GlobalClass.KEY_BG_COLOR, keyboardDataArrayList.get(viewPager.getCurrentItem()).getKeyBgColor());
                    GlobalClass.setPreferencesInt(context, GlobalClass.KEY_RADIUS, keyboardDataArrayList.get(viewPager.getCurrentItem()).getKeyRadius());
                    GlobalClass.setPreferencesInt(context, GlobalClass.KEY_STROKE, keyboardDataArrayList.get(viewPager.getCurrentItem()).getKeyStroke());
                    GlobalClass.setPreferencesInt(context, GlobalClass.KEY_OPACITY, keyboardDataArrayList.get(viewPager.getCurrentItem()).getKeyOpacity());
                    GlobalClass.setPreferencesString(context, GlobalClass.FONT_COLOR, keyboardDataArrayList.get(viewPager.getCurrentItem()).getFontColor());
                    GlobalClass.setPreferencesString(context, GlobalClass.FONT_NAME, keyboardDataArrayList.get(viewPager.getCurrentItem()).getFontName());
                    GlobalClass.setPreferencesBool(context, GlobalClass.SOUND_STATUS, keyboardDataArrayList.get(viewPager.getCurrentItem()).getSoundStatus());
                    GlobalClass.setPreferencesInt(context, GlobalClass.SOUND_NAME, keyboardDataArrayList.get(viewPager.getCurrentItem()).getSoundName());
                    GlobalClass.setPreferencesInt(context, GlobalClass.SELECTWALLPAPER, keyboardDataArrayList.get(viewPager.getCurrentItem()).getSelectwallpaper());
                    GlobalClass.setPreferencesInt(context, GlobalClass.SELECTCOLOR, keyboardDataArrayList.get(viewPager.getCurrentItem()).getSelectcolor());
                    GlobalClass.setPreferencesInt(context, GlobalClass.SELECTVIEW, keyboardDataArrayList.get(viewPager.getCurrentItem()).getSelview());
                    GlobalClass.setPreferencesString(context, GlobalClass.KEYBOARDBITMAPBACK, keyboardDataArrayList.get(viewPager.getCurrentItem()).getBitmapback());
                } else
                    applyImageView.setImageDrawable(getResources().getDrawable(R.drawable.btn_disable));

            } else {
                createKeyboardImageView.setVisibility(View.VISIBLE);
                linKeyboardData.setVisibility(View.GONE);
                addKeyboardImageView.setVisibility(View.GONE);
            }
        } else {
            keyboardDataArrayList = new ArrayList<>();
            createKeyboardImageView.setVisibility(View.VISIBLE);
            linKeyboardData.setVisibility(View.GONE);
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

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.moreTextView:
                Uri uri = Uri.parse("https://play.google.com/store/apps/developer?id=Codmins+Keyboards");
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
                break;
            case R.id.premiumTextView:
                startActivity(new Intent(context, PremiumStoreActivity.class));
                break;
            case R.id.applyImageView:
                if (GlobalClass.KeyboardIsEnabled(context) && GlobalClass.KeyboardIsSet(context)) {
                    if (keyboardDataArrayList.get(viewPager.getCurrentItem()).isSelected()) {
                        keyboardDataArrayList.get(viewPager.getCurrentItem()).setSelected(false);
                        applyImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_disable));

                        setDefaultValue();

                        GlobalClass.selectwallpaper = 0;
                        GlobalClass.tempKeyboardBgImage = R.drawable.background_1;
                        GlobalClass.selectcolor = 0;
                        GlobalClass.selview = 0;
                        GlobalClass.tempKeyboardColorCode = 0;
                        GlobalClass.keyboardBitmapBack = null;
                        GlobalClass.tempFontColor = "#FFFFFF";
                        GlobalClass.tempKeyColor = getResources().getColor(R.color.eight);
                        GlobalClass.tempKeyRadius = 34;
                        GlobalClass.tempKeyStroke = 1;
                        GlobalClass.tempKeyOpacity = 64;
                        GlobalClass.tempFontName = "";
                        GlobalClass.soundStatus = false;
                        GlobalClass.soundId = 0;
                        GlobalClass.selectbgcolor = 7;
                        GlobalClass.selectfontcolor = 1;
                        GlobalClass.selectsounds = 0;
                        GlobalClass.selectfonts = 0;

                        GlobalClass.setPreferencesInt(context, GlobalClass.KEYBOARD_COLOR_CODE, GlobalClass.tempKeyboardColorCode);
                        GlobalClass.setPreferencesInt(context, GlobalClass.KEYBOARD_BG_IMAGE, GlobalClass.tempKeyboardBgImage);
                        GlobalClass.setPreferencesInt(context, GlobalClass.KEY_BG_COLOR, GlobalClass.tempKeyColor);
                        GlobalClass.setPreferencesInt(context, GlobalClass.KEY_RADIUS, GlobalClass.tempKeyRadius);
                        GlobalClass.setPreferencesInt(context, GlobalClass.KEY_STROKE, GlobalClass.tempKeyStroke);
                        GlobalClass.setPreferencesInt(context, GlobalClass.KEY_OPACITY, GlobalClass.tempKeyOpacity);
                        GlobalClass.setPreferencesString(context, GlobalClass.FONT_COLOR, GlobalClass.tempFontColor);
                        GlobalClass.setPreferencesString(context, GlobalClass.FONT_NAME, GlobalClass.tempFontName);
                        GlobalClass.setPreferencesBool(context, GlobalClass.SOUND_STATUS, GlobalClass.soundStatus);
                        GlobalClass.setPreferencesInt(context, GlobalClass.SOUND_NAME, GlobalClass.soundId);
                        GlobalClass.setPreferencesInt(context, GlobalClass.SELECTWALLPAPER, GlobalClass.selectwallpaper);
                        GlobalClass.setPreferencesInt(context, GlobalClass.SELECTCOLOR, GlobalClass.selectcolor);
                        GlobalClass.setPreferencesInt(context, GlobalClass.SELECTVIEW, GlobalClass.selview);
                        GlobalClass.setPreferencesString(context, GlobalClass.KEYBOARDBITMAPBACK, GlobalClass.keyboardBitmapBack);
                    } else {
                        for (int i = 0; i < keyboardDataArrayList.size(); i++) {
                            if (i == viewPager.getCurrentItem()) {
                                keyboardDataArrayList.get(i).setSelected(true);
                            } else {
                                keyboardDataArrayList.get(i).setSelected(false);
                            }
                        }
                        applyImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_apply));

                        GlobalClass.setPreferencesInt(context, GlobalClass.KEYBOARD_COLOR_CODE, keyboardDataArrayList.get(viewPager.getCurrentItem()).getKeyboardColorCode());
                        GlobalClass.setPreferencesInt(context, GlobalClass.KEYBOARD_BG_IMAGE, keyboardDataArrayList.get(viewPager.getCurrentItem()).getKeyboardBgImage());
                        GlobalClass.setPreferencesInt(context, GlobalClass.KEY_BG_COLOR, keyboardDataArrayList.get(viewPager.getCurrentItem()).getKeyBgColor());
                        GlobalClass.setPreferencesInt(context, GlobalClass.KEY_RADIUS, keyboardDataArrayList.get(viewPager.getCurrentItem()).getKeyRadius());
                        GlobalClass.setPreferencesInt(context, GlobalClass.KEY_STROKE, keyboardDataArrayList.get(viewPager.getCurrentItem()).getKeyStroke());
                        GlobalClass.setPreferencesInt(context, GlobalClass.KEY_OPACITY, keyboardDataArrayList.get(viewPager.getCurrentItem()).getKeyOpacity());
                        GlobalClass.setPreferencesString(context, GlobalClass.FONT_COLOR, keyboardDataArrayList.get(viewPager.getCurrentItem()).getFontColor());
                        GlobalClass.setPreferencesString(context, GlobalClass.FONT_NAME, keyboardDataArrayList.get(viewPager.getCurrentItem()).getFontName());
                        GlobalClass.setPreferencesBool(context, GlobalClass.SOUND_STATUS, keyboardDataArrayList.get(viewPager.getCurrentItem()).getSoundStatus());
                        GlobalClass.setPreferencesInt(context, GlobalClass.SOUND_NAME, keyboardDataArrayList.get(viewPager.getCurrentItem()).getSoundName());
                        GlobalClass.setPreferencesInt(context, GlobalClass.SELECTWALLPAPER, keyboardDataArrayList.get(viewPager.getCurrentItem()).getSelectwallpaper());
                        GlobalClass.setPreferencesInt(context, GlobalClass.SELECTCOLOR, keyboardDataArrayList.get(viewPager.getCurrentItem()).getSelectcolor());
                        GlobalClass.setPreferencesInt(context, GlobalClass.SELECTVIEW, keyboardDataArrayList.get(viewPager.getCurrentItem()).getSelview());
                        GlobalClass.setPreferencesString(context, GlobalClass.KEYBOARDBITMAPBACK, keyboardDataArrayList.get(viewPager.getCurrentItem()).getBitmapback());
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
                                        linKeyboardData.setVisibility(View.VISIBLE);
                                        addKeyboardImageView.setVisibility(View.VISIBLE);
                                    } else {
                                        createKeyboardImageView.setVisibility(View.VISIBLE);
                                        linKeyboardData.setVisibility(View.GONE);
                                        addKeyboardImageView.setVisibility(View.GONE);
                                    }

                                    GlobalClass.selectwallpaper = 0;
                                    GlobalClass.tempKeyboardBgImage = R.drawable.background_1;
                                    GlobalClass.selectcolor = 0;
                                    GlobalClass.selview = 0;
                                    GlobalClass.tempKeyboardColorCode = 0;
                                    GlobalClass.keyboardBitmapBack = null;
                                    GlobalClass.tempFontColor = "#FFFFFF";
                                    GlobalClass.tempKeyColor = getResources().getColor(R.color.eight);
                                    GlobalClass.tempKeyRadius = 18;
                                    GlobalClass.tempKeyStroke = 2;
                                    GlobalClass.tempKeyOpacity = 255;
                                    GlobalClass.tempFontName = "";
                                    GlobalClass.soundStatus = false;
                                    GlobalClass.soundId = 0;
                                    GlobalClass.selectbgcolor = 7;
                                    GlobalClass.selectfontcolor = 1;
                                    GlobalClass.selectsounds = 0;
                                    GlobalClass.selectfonts = 0;

                                    GlobalClass.setPreferencesInt(context, GlobalClass.KEYBOARD_COLOR_CODE, GlobalClass.tempKeyboardColorCode);
                                    GlobalClass.setPreferencesInt(context, GlobalClass.KEYBOARD_BG_IMAGE, GlobalClass.tempKeyboardBgImage);
                                    GlobalClass.setPreferencesInt(context, GlobalClass.KEY_BG_COLOR, GlobalClass.tempKeyColor);
                                    GlobalClass.setPreferencesInt(context, GlobalClass.KEY_RADIUS, GlobalClass.tempKeyRadius);
                                    GlobalClass.setPreferencesInt(context, GlobalClass.KEY_STROKE, GlobalClass.tempKeyStroke);
                                    GlobalClass.setPreferencesInt(context, GlobalClass.KEY_OPACITY, GlobalClass.tempKeyOpacity);
                                    GlobalClass.setPreferencesString(context, GlobalClass.FONT_COLOR, GlobalClass.tempFontColor);
                                    GlobalClass.setPreferencesString(context, GlobalClass.FONT_NAME, GlobalClass.tempFontName);
                                    GlobalClass.setPreferencesBool(context, GlobalClass.SOUND_STATUS, GlobalClass.soundStatus);
                                    GlobalClass.setPreferencesInt(context, GlobalClass.SOUND_NAME, GlobalClass.soundId);
                                    GlobalClass.setPreferencesInt(context, GlobalClass.SELECTWALLPAPER, GlobalClass.selectwallpaper);
                                    GlobalClass.setPreferencesInt(context, GlobalClass.SELECTCOLOR, GlobalClass.selectcolor);
                                    GlobalClass.setPreferencesInt(context, GlobalClass.SELECTVIEW, GlobalClass.selview);
                                    GlobalClass.setPreferencesString(context, GlobalClass.KEYBOARDBITMAPBACK, GlobalClass.keyboardBitmapBack);

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
                                        linKeyboardData.setVisibility(View.VISIBLE);
                                        addKeyboardImageView.setVisibility(View.VISIBLE);
                                    } else {
                                        createKeyboardImageView.setVisibility(View.VISIBLE);
                                        linKeyboardData.setVisibility(View.GONE);
                                        addKeyboardImageView.setVisibility(View.GONE);

                                        GlobalClass.selectwallpaper = 0;
                                        GlobalClass.tempKeyboardBgImage = R.drawable.background_1;
                                        GlobalClass.selectcolor = 0;
                                        GlobalClass.selview = 0;
                                        GlobalClass.tempKeyboardColorCode = 0;
                                        GlobalClass.keyboardBitmapBack = null;
                                        GlobalClass.tempFontColor = "#FFFFFF";
                                        GlobalClass.tempKeyColor = getResources().getColor(R.color.eight);
                                        GlobalClass.tempKeyRadius = 18;
                                        GlobalClass.tempKeyStroke = 2;
                                        GlobalClass.tempKeyOpacity = 255;
                                        GlobalClass.tempFontName = "";
                                        GlobalClass.soundStatus = false;
                                        GlobalClass.soundId = 0;
                                        GlobalClass.selectbgcolor = 7;
                                        GlobalClass.selectfontcolor = 1;
                                        GlobalClass.selectsounds = 0;
                                        GlobalClass.selectfonts = 0;

                                        GlobalClass.setPreferencesInt(context, GlobalClass.KEYBOARD_COLOR_CODE, GlobalClass.tempKeyboardColorCode);
                                        GlobalClass.setPreferencesInt(context, GlobalClass.KEYBOARD_BG_IMAGE, GlobalClass.tempKeyboardBgImage);
                                        GlobalClass.setPreferencesInt(context, GlobalClass.KEY_BG_COLOR, GlobalClass.tempKeyColor);
                                        GlobalClass.setPreferencesInt(context, GlobalClass.KEY_RADIUS, GlobalClass.tempKeyRadius);
                                        GlobalClass.setPreferencesInt(context, GlobalClass.KEY_STROKE, GlobalClass.tempKeyStroke);
                                        GlobalClass.setPreferencesInt(context, GlobalClass.KEY_OPACITY, GlobalClass.tempKeyOpacity);
                                        GlobalClass.setPreferencesString(context, GlobalClass.FONT_COLOR, GlobalClass.tempFontColor);
                                        GlobalClass.setPreferencesString(context, GlobalClass.FONT_NAME, GlobalClass.tempFontName);
                                        GlobalClass.setPreferencesBool(context, GlobalClass.SOUND_STATUS, GlobalClass.soundStatus);
                                        GlobalClass.setPreferencesInt(context, GlobalClass.SOUND_NAME, GlobalClass.soundId);
                                        GlobalClass.setPreferencesInt(context, GlobalClass.SELECTWALLPAPER, GlobalClass.selectwallpaper);
                                        GlobalClass.setPreferencesInt(context, GlobalClass.SELECTCOLOR, GlobalClass.selectcolor);
                                        GlobalClass.setPreferencesInt(context, GlobalClass.SELECTVIEW, GlobalClass.selview);
                                        GlobalClass.setPreferencesString(context, GlobalClass.KEYBOARDBITMAPBACK, GlobalClass.keyboardBitmapBack);
                                    }
                                }
                                circleIndicator.setViewPager(viewPager);

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
                break;

            case R.id.addKeyboardImageView:
                setDefaultValue();

                Intent intent = new Intent(context, CreateKeyboardActivity.class);
                intent.putExtra("isEdit", false);
                startActivity(intent);

                GlobalClass.checkStartAd();

                finish();
                break;

            case R.id.createKeyboardImageView:
                addKeyboardImageView.performClick();
        }
    }

    public void setDefaultValue() {
        GlobalClass.tempKeyboardColorCode = GlobalClass.getPreferencesInt(context, GlobalClass.KEYBOARD_COLOR_CODE, R.color.one);
        GlobalClass.tempKeyboardBgImage = GlobalClass.getPreferencesInt(context, GlobalClass.KEYBOARD_BG_IMAGE, R.drawable.background_1);
        GlobalClass.tempKeyColor = GlobalClass.getPreferencesInt(context, GlobalClass.KEY_BG_COLOR, getResources().getColor(R.color.eight));
        GlobalClass.tempKeyRadius = GlobalClass.getPreferencesInt(context, GlobalClass.KEY_RADIUS, 18);
        GlobalClass.tempKeyStroke = GlobalClass.getPreferencesInt(context, GlobalClass.KEY_STROKE, 2);
        GlobalClass.tempKeyOpacity = GlobalClass.getPreferencesInt(context, GlobalClass.KEY_OPACITY, 255);
        GlobalClass.tempFontColor = GlobalClass.getPreferencesString(context, GlobalClass.FONT_COLOR, "#FFFFFF");
        GlobalClass.tempFontName = GlobalClass.getPreferencesString(context, GlobalClass.FONT_NAME, "");
        GlobalClass.soundStatus = GlobalClass.getPreferencesBool(context, GlobalClass.SOUND_STATUS, false);
        GlobalClass.soundId = GlobalClass.getPreferencesInt(context, GlobalClass.SOUND_NAME, R.raw.balloon_snap);
        GlobalClass.selectwallpaper = GlobalClass.getPreferencesInt(context, GlobalClass.SELECTWALLPAPER, 0);
        GlobalClass.selectcolor = GlobalClass.getPreferencesInt(context, GlobalClass.SELECTCOLOR, 0);
        GlobalClass.selview = GlobalClass.getPreferencesInt(context, GlobalClass.SELECTVIEW, 0);
        GlobalClass.keyboardBitmapBack = GlobalClass.getPreferencesString(context, GlobalClass.KEYBOARDBITMAPBACK, null);

        if (GlobalClass.selectbgcolor == 0)
            GlobalClass.selectbgcolor = 7;

        if (GlobalClass.selectfontcolor == 0)
            GlobalClass.selectfontcolor = 1;

        GlobalClass.setPreferencesInt(context, GlobalClass.KEYBOARD_COLOR_CODE, GlobalClass.tempKeyboardColorCode);
        GlobalClass.setPreferencesInt(context, GlobalClass.KEYBOARD_BG_IMAGE, GlobalClass.tempKeyboardBgImage);
        GlobalClass.setPreferencesInt(context, GlobalClass.KEY_BG_COLOR, GlobalClass.tempKeyColor);
        GlobalClass.setPreferencesInt(context, GlobalClass.KEY_RADIUS, GlobalClass.tempKeyRadius);
        GlobalClass.setPreferencesInt(context, GlobalClass.KEY_STROKE, GlobalClass.tempKeyStroke);
        GlobalClass.setPreferencesInt(context, GlobalClass.KEY_OPACITY, GlobalClass.tempKeyOpacity);
        GlobalClass.setPreferencesString(context, GlobalClass.FONT_COLOR, GlobalClass.tempFontColor);
        GlobalClass.setPreferencesString(context, GlobalClass.FONT_NAME, GlobalClass.tempFontName);
        GlobalClass.setPreferencesBool(context, GlobalClass.SOUND_STATUS, GlobalClass.soundStatus);
        GlobalClass.setPreferencesInt(context, GlobalClass.SOUND_NAME, GlobalClass.soundId);
        GlobalClass.setPreferencesInt(context, GlobalClass.SELECTWALLPAPER, GlobalClass.selectwallpaper);
        GlobalClass.setPreferencesInt(context, GlobalClass.SELECTCOLOR, GlobalClass.selectcolor);
        GlobalClass.setPreferencesInt(context, GlobalClass.SELECTVIEW, GlobalClass.selview);
        GlobalClass.setPreferencesString(context, GlobalClass.KEYBOARDBITMAPBACK, GlobalClass.keyboardBitmapBack);
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