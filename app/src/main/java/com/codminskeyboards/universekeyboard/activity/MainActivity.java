package com.codminskeyboards.universekeyboard.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.kobakei.ratethisapp.RateThisApp;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;
import permission.auron.com.marshmallowpermissionhelper.ActivityManagePermission;
import permission.auron.com.marshmallowpermissionhelper.PermissionResult;
import permission.auron.com.marshmallowpermissionhelper.PermissionUtils;

public class MainActivity extends ActivityManagePermission implements View.OnClickListener {

    private ImageView ivCreateKeyboard;
    private Context context;
    private ViewPager viewPager;
    private KeyboardViewPagerAdapter keyboardViewPagerAdapter;
    ArrayList<KeyboardData> keyboardDataArrayList = new ArrayList<>();
    private ImageView ivNoData;
    private LinearLayout linKeyboardData;
    private ImageView ivApply;
    boolean isThemeSlotPurchased = false;
    private int mRequestCode;
    private String TAG = "Grid View Activity";
    private AdView mAdView;
    IabHelper mHelper;
    CircleIndicator indicator;

    // Called when consumption is complete
    IabHelper.OnConsumeFinishedListener consumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
        public void onConsumeFinished(Purchase purchase, IabResult result) {
            GlobalClass.printLog(TAG, "Consumption finished. Purchase: " + purchase + ", result: " + result);

            // if we were disposed of in the meantime, quit.
            if (mHelper == null) return;

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
            if (mHelper == null) return;

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
                mHelper.consumeAsync(getThemeSlotPurchase, consumeFinishedListener);
            }
            GlobalClass.printLog(TAG, "Initial inventory query finished; enabling main UI.");
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

    // Callback for when a purchase is finished
    IabHelper.OnIabPurchaseFinishedListener purchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {

            GlobalClass.printLog(TAG, "Purchase finished: " + result + ", purchase: " + purchase);

            // if we were disposed of in the meantime, quit.
            if (mHelper == null) return;

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
            mHelper.consumeAsync(purchase, consumeFinishedListener);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.dark_gray));
        }
        Crashlytics.log("Activity created");
        Crashlytics.log(Log.ERROR, "tag", "Message");

        // set context
        context = MainActivity.this;

        // get the permission
        askForPermission();
        setContentView(R.layout.activity_main);
        RateThisApp.init(new RateThisApp.Config(3, 5));
        RateThisApp.setCallback(new RateThisApp.Callback() {
            @Override
            public void onYesClicked() {
                //Toast.makeText(MainActivity.this, "Yes event", Toast.LENGTH_SHORT).show();
                //Rate now event
            }

            @Override
            public void onNoClicked() {
                //Toast.makeText(MainActivity.this, "No event", Toast.LENGTH_SHORT).show();
                // No thanks text
            }

            @Override
            public void onCancelClicked() {
                //Toast.makeText(MainActivity.this, "Cancel event", Toast.LENGTH_SHORT).show();
                //Later text
            }
        });
        RateThisApp.showRateDialog(MainActivity.this, R.style.MyAlertDialogStyle2);
        setContent();
        if (GlobalClass.getPrefrenceBoolean(context, GlobalClass.key_isAdLock, true)) {
            setAdMob();
        }
    }

    private void updateData() {
        if (mRequestCode == GlobalClass.RC_REQUEST_THEMES_SLOTES) {
            GlobalClass.setPrefrenceBoolean(MainActivity.this, getString(R.string.theme_slot_purchased), true);
        }
    }

    private void setAdMob() {
        mAdView = findViewById(R.id.ads);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                mAdView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdClosed() {
                mAdView.setVisibility(View.GONE);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                mAdView.setVisibility(View.GONE);
            }

        });

    }

    void complain(String message) {
        GlobalClass.printLog(TAG, "**** TrivialDrive Error: " + message);
        //alert("Error: " + message);
    }

    private void setContent() {

        InterstitialAd mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-2002759323605741/8308210294");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        ivCreateKeyboard = findViewById(R.id.ivCreateKeyboard);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setPageMarginDrawable(R.drawable.drawable_bg);

        ImageView ivDelete = findViewById(R.id.ivDelete);
        ivNoData = findViewById(R.id.ivNoData);
        linKeyboardData = findViewById(R.id.linKeyboardData);
        ivApply = findViewById(R.id.ivApply);
        LinearLayout linPackage = findViewById(R.id.linPackage);
        LinearLayout linGuide = findViewById(R.id.linGuide);

        mHelper = new IabHelper(MainActivity.this, GlobalClass.base64EncodedPublicKey);

        // enable debug logging (for a production application, you should set this to false).
        mHelper.enableDebugLogging(true);
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess()) {
                    // Oh noes, there was a problem.
                    complain("Problem setting up in-app billing: " + result);
                    return;
                }

                // Have we been disposed of in the meantime? If so, quit.
                if (mHelper == null) return;
                mHelper.queryInventoryAsync(mGotInventoryListener);
            }
        });
        ivCreateKeyboard.setOnClickListener(this);
        ivDelete.setOnClickListener(this);
        ivNoData.setOnClickListener(this);
        ivApply.setOnClickListener(this);
        linGuide.setOnClickListener(this);
        linPackage.setOnClickListener(this);

        keyboardDataArrayList = GlobalClass.getPreferencesArrayList(context);

        if (keyboardDataArrayList != null) {
            if (keyboardDataArrayList.size() != 0) {

                for (int i = 0; i < keyboardDataArrayList.size(); i++) {
                    if (keyboardDataArrayList.get(i).isSelected()) {
                        GlobalClass.setPreferencesString(context, GlobalClass.IS_COLOR, keyboardDataArrayList.get(i).getIsColor());
                        GlobalClass.setPreferencesInt(context, GlobalClass.KEYBOARD_COLOR_CODE, keyboardDataArrayList.get(i).getKeyboardColorCode());
                        GlobalClass.setPreferencesInt(context, GlobalClass.KEYBOARD_BG_IMAGE, keyboardDataArrayList.get(i).getKeyboardBgImage());
                        GlobalClass.setPreferencesInt(context, GlobalClass.KEY_BG_COLOR, keyboardDataArrayList.get(i).getKeyBgColor());
                        GlobalClass.setPreferencesString(context, GlobalClass.KEY_RADIUS, keyboardDataArrayList.get(i).getKeyRadius());
                        GlobalClass.setPreferencesString(context, GlobalClass.KEY_STROKE, keyboardDataArrayList.get(i).getKeyStroke());
                        GlobalClass.setPreferencesString(context, GlobalClass.KEY_OPACITY, keyboardDataArrayList.get(i).getKeyOpacity());
                        GlobalClass.setPreferencesString(context, GlobalClass.FONT_COLOR, keyboardDataArrayList.get(i).getFontColor());
                        GlobalClass.setPreferencesString(context, GlobalClass.FONT_NAME, keyboardDataArrayList.get(i).getFontName());
                        GlobalClass.setPreferencesString(context, GlobalClass.SOUND_STATUS, keyboardDataArrayList.get(i).getSoundStatus());
                        GlobalClass.setPreferencesInt(context, GlobalClass.SOUND_NAME, keyboardDataArrayList.get(i).getSoundName());
                    }
                }

                keyboardViewPagerAdapter = new KeyboardViewPagerAdapter(context, keyboardDataArrayList);
                indicator = findViewById(R.id.indicator);
                viewPager.setAdapter(keyboardViewPagerAdapter);
                indicator.setViewPager(viewPager);
                ivNoData.setVisibility(View.GONE);
                linKeyboardData.setVisibility(View.VISIBLE);
                ivCreateKeyboard.setVisibility(View.VISIBLE);

                if (keyboardDataArrayList.size() == 1) {
                    keyboardDataArrayList.get(0).setSelected(true);
                    ivApply.setImageDrawable(getResources().getDrawable(R.drawable.btn_apply));
                    GlobalClass.setPreferencesString(context, GlobalClass.IS_COLOR, keyboardDataArrayList.get(viewPager.getCurrentItem()).getIsColor());
                    GlobalClass.setPreferencesInt(context, GlobalClass.KEYBOARD_COLOR_CODE, keyboardDataArrayList.get(viewPager.getCurrentItem()).getKeyboardColorCode());
                    GlobalClass.setPreferencesInt(context, GlobalClass.KEYBOARD_BG_IMAGE, keyboardDataArrayList.get(viewPager.getCurrentItem()).getKeyboardBgImage());
                    GlobalClass.setPreferencesInt(context, GlobalClass.KEY_BG_COLOR, keyboardDataArrayList.get(viewPager.getCurrentItem()).getKeyBgColor());
                    GlobalClass.setPreferencesString(context, GlobalClass.KEY_RADIUS, keyboardDataArrayList.get(viewPager.getCurrentItem()).getKeyRadius());
                    GlobalClass.setPreferencesString(context, GlobalClass.KEY_STROKE, keyboardDataArrayList.get(viewPager.getCurrentItem()).getKeyStroke());
                    GlobalClass.setPreferencesString(context, GlobalClass.KEY_OPACITY, keyboardDataArrayList.get(viewPager.getCurrentItem()).getKeyOpacity());
                    GlobalClass.setPreferencesString(context, GlobalClass.FONT_COLOR, keyboardDataArrayList.get(viewPager.getCurrentItem()).getFontColor());
                    GlobalClass.setPreferencesString(context, GlobalClass.FONT_NAME, keyboardDataArrayList.get(viewPager.getCurrentItem()).getFontName());
                    GlobalClass.setPreferencesString(context, GlobalClass.SOUND_STATUS, keyboardDataArrayList.get(viewPager.getCurrentItem()).getSoundStatus());
                    GlobalClass.setPreferencesInt(context, GlobalClass.SOUND_NAME, keyboardDataArrayList.get(viewPager.getCurrentItem()).getSoundName());
                    GlobalClass.setPreferencesInt(context, GlobalClass.SELECTWALLPAPER, keyboardDataArrayList.get(viewPager.getCurrentItem()).getSelectwallpaper());
                    GlobalClass.setPreferencesInt(context, GlobalClass.SELECTTEXTWALLPAPER, keyboardDataArrayList.get(viewPager.getCurrentItem()).getSelecttextwallpaper());
                    GlobalClass.setPreferencesInt(context, GlobalClass.SELECTCOLOR, keyboardDataArrayList.get(viewPager.getCurrentItem()).getSelectcolor());
                    GlobalClass.setPreferencesInt(context, GlobalClass.SELECTVIEW, keyboardDataArrayList.get(viewPager.getCurrentItem()).getSelview());
                    GlobalClass.setPreferencesString(context, GlobalClass.KEYBOARDBITMAPBACK, keyboardDataArrayList.get(viewPager.getCurrentItem()).getBitmapback());
                } else {
                    ivApply.setImageDrawable(getResources().getDrawable(R.drawable.btn_disable));
                }

            } else {
                ivNoData.setVisibility(View.VISIBLE);
                linKeyboardData.setVisibility(View.GONE);
                ivCreateKeyboard.setVisibility(View.GONE);
            }
        } else {
            keyboardDataArrayList = new ArrayList<>();
            ivNoData.setVisibility(View.VISIBLE);
            linKeyboardData.setVisibility(View.GONE);
            ivCreateKeyboard.setVisibility(View.GONE);
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                if (keyboardDataArrayList.get(i).isSelected()) {
                    ivApply.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_apply));
                } else {

                    ivApply.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_disable));
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {


            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linGuide:
                Uri uri = Uri.parse("https://play.google.com/store/apps/developer?id=Codmins+Keyboards"); // missing 'http://' will cause crashed
                Intent intent6 = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent6);
                break;
            case R.id.linPackage:
                startActivity(new Intent(MainActivity.this, PackageActivity.class));
                break;
            case R.id.ivApply:
                if (GlobalClass.KeyboardIsEnabled(MainActivity.this) && GlobalClass.KeyboardIsSet(MainActivity.this)) {
                    if (keyboardDataArrayList.get(viewPager.getCurrentItem()).isSelected()) {
                        keyboardDataArrayList.get(viewPager.getCurrentItem()).setSelected(false);
                        ivApply.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_disable));
                        setDefaultValue();

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

                        GlobalClass.setPreferencesString(context, GlobalClass.IS_COLOR, GlobalClass.tempIsColor);
                        GlobalClass.setPreferencesInt(context, GlobalClass.KEYBOARD_COLOR_CODE, GlobalClass.tempKeyboardColorCode);
                        GlobalClass.setPreferencesInt(context, GlobalClass.KEYBOARD_BG_IMAGE, GlobalClass.tempKeyboardBgImage);
                        GlobalClass.setPreferencesInt(context, GlobalClass.KEY_BG_COLOR, GlobalClass.tempKeyColor);
                        GlobalClass.setPreferencesString(context, GlobalClass.KEY_RADIUS, GlobalClass.tempKeyRadius);
                        GlobalClass.setPreferencesString(context, GlobalClass.KEY_STROKE, GlobalClass.tempKeyStroke);
                        GlobalClass.setPreferencesString(context, GlobalClass.KEY_OPACITY, GlobalClass.tempKeyOpacity);
                        GlobalClass.setPreferencesString(context, GlobalClass.FONT_COLOR, GlobalClass.tempFontColor);
                        GlobalClass.setPreferencesString(context, GlobalClass.FONT_NAME, GlobalClass.tempFontName);
                        GlobalClass.setPreferencesString(context, GlobalClass.SOUND_STATUS, GlobalClass.tempSoundStatus);
                        GlobalClass.setPreferencesInt(context, GlobalClass.SOUND_NAME, GlobalClass.tempSoundName);
                        GlobalClass.setPreferencesInt(context, GlobalClass.SELECTWALLPAPER, GlobalClass.selectwallpaper);
                        GlobalClass.setPreferencesInt(context, GlobalClass.SELECTTEXTWALLPAPER, GlobalClass.selecttextwallpaper);
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
                        ivApply.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_apply));

                        GlobalClass.setPreferencesString(context, GlobalClass.IS_COLOR, keyboardDataArrayList.get(viewPager.getCurrentItem()).getIsColor());
                        GlobalClass.setPreferencesInt(context, GlobalClass.KEYBOARD_COLOR_CODE, keyboardDataArrayList.get(viewPager.getCurrentItem()).getKeyboardColorCode());
                        GlobalClass.setPreferencesInt(context, GlobalClass.KEYBOARD_BG_IMAGE, keyboardDataArrayList.get(viewPager.getCurrentItem()).getKeyboardBgImage());
                        GlobalClass.setPreferencesInt(context, GlobalClass.KEY_BG_COLOR, keyboardDataArrayList.get(viewPager.getCurrentItem()).getKeyBgColor());
                        GlobalClass.setPreferencesString(context, GlobalClass.KEY_RADIUS, keyboardDataArrayList.get(viewPager.getCurrentItem()).getKeyRadius());
                        GlobalClass.setPreferencesString(context, GlobalClass.KEY_STROKE, keyboardDataArrayList.get(viewPager.getCurrentItem()).getKeyStroke());
                        GlobalClass.setPreferencesString(context, GlobalClass.KEY_OPACITY, keyboardDataArrayList.get(viewPager.getCurrentItem()).getKeyOpacity());
                        GlobalClass.setPreferencesString(context, GlobalClass.FONT_COLOR, keyboardDataArrayList.get(viewPager.getCurrentItem()).getFontColor());
                        GlobalClass.setPreferencesString(context, GlobalClass.FONT_NAME, keyboardDataArrayList.get(viewPager.getCurrentItem()).getFontName());
                        GlobalClass.setPreferencesString(context, GlobalClass.SOUND_STATUS, keyboardDataArrayList.get(viewPager.getCurrentItem()).getSoundStatus());
                        GlobalClass.setPreferencesInt(context, GlobalClass.SOUND_NAME, keyboardDataArrayList.get(viewPager.getCurrentItem()).getSoundName());
                        GlobalClass.setPreferencesInt(context, GlobalClass.SELECTWALLPAPER, keyboardDataArrayList.get(viewPager.getCurrentItem()).getSelectwallpaper());
                        GlobalClass.setPreferencesInt(context, GlobalClass.SELECTTEXTWALLPAPER, keyboardDataArrayList.get(viewPager.getCurrentItem()).getSelecttextwallpaper());
                        GlobalClass.setPreferencesInt(context, GlobalClass.SELECTCOLOR, keyboardDataArrayList.get(viewPager.getCurrentItem()).getSelectcolor());
                        GlobalClass.setPreferencesInt(context, GlobalClass.SELECTVIEW, keyboardDataArrayList.get(viewPager.getCurrentItem()).getSelview());
                        GlobalClass.setPreferencesString(context, GlobalClass.KEYBOARDBITMAPBACK, keyboardDataArrayList.get(viewPager.getCurrentItem()).getBitmapback());
                    }
                } else {
                    startActivity(new Intent(MainActivity.this, com.codminskeyboards.universekeyboard.activity.SetKeyboardActivity.class));
                }
                GlobalClass.setPreferencesArrayList(context, keyboardDataArrayList);
                break;
            case R.id.ivDelete:
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

                                    ivApply.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_disable));
                                    if (keyboardDataArrayList.size() != 0) {
                                        keyboardDataArrayList.remove(viewPager.getCurrentItem());
                                        GlobalClass.setPreferencesArrayList(context, keyboardDataArrayList);
                                        keyboardViewPagerAdapter = new KeyboardViewPagerAdapter(context, keyboardDataArrayList);
                                        viewPager.setAdapter(keyboardViewPagerAdapter);
                                        keyboardViewPagerAdapter.notifyDataSetChanged();

                                    }
                                    //setDefaultValue();
                                    if (keyboardDataArrayList.size() != 0) {
                                        ivNoData.setVisibility(View.GONE);
                                        linKeyboardData.setVisibility(View.VISIBLE);
                                        ivCreateKeyboard.setVisibility(View.VISIBLE);
                                    } else {
                                        ivNoData.setVisibility(View.VISIBLE);
                                        linKeyboardData.setVisibility(View.GONE);
                                        ivCreateKeyboard.setVisibility(View.GONE);
                                    }

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

                                    GlobalClass.setPreferencesString(context, GlobalClass.IS_COLOR, GlobalClass.tempIsColor);
                                    GlobalClass.setPreferencesInt(context, GlobalClass.KEYBOARD_COLOR_CODE, GlobalClass.tempKeyboardColorCode);
                                    GlobalClass.setPreferencesInt(context, GlobalClass.KEYBOARD_BG_IMAGE, GlobalClass.tempKeyboardBgImage);
                                    GlobalClass.setPreferencesInt(context, GlobalClass.KEY_BG_COLOR, GlobalClass.tempKeyColor);
                                    GlobalClass.setPreferencesString(context, GlobalClass.KEY_RADIUS, GlobalClass.tempKeyRadius);
                                    GlobalClass.setPreferencesString(context, GlobalClass.KEY_STROKE, GlobalClass.tempKeyStroke);
                                    GlobalClass.setPreferencesString(context, GlobalClass.KEY_OPACITY, GlobalClass.tempKeyOpacity);
                                    GlobalClass.setPreferencesString(context, GlobalClass.FONT_COLOR, GlobalClass.tempFontColor);
                                    GlobalClass.setPreferencesString(context, GlobalClass.FONT_NAME, GlobalClass.tempFontName);
                                    GlobalClass.setPreferencesString(context, GlobalClass.SOUND_STATUS, GlobalClass.tempSoundStatus);
                                    GlobalClass.setPreferencesInt(context, GlobalClass.SOUND_NAME, GlobalClass.tempSoundName);
                                    GlobalClass.setPreferencesInt(context, GlobalClass.SELECTWALLPAPER, GlobalClass.selectwallpaper);
                                    GlobalClass.setPreferencesInt(context, GlobalClass.SELECTTEXTWALLPAPER, GlobalClass.selecttextwallpaper);
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
                                        ivNoData.setVisibility(View.GONE);
                                        linKeyboardData.setVisibility(View.VISIBLE);
                                        ivCreateKeyboard.setVisibility(View.VISIBLE);
                                    } else {
                                        ivNoData.setVisibility(View.VISIBLE);
                                        linKeyboardData.setVisibility(View.GONE);
                                        ivCreateKeyboard.setVisibility(View.GONE);

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

                                        GlobalClass.setPreferencesString(context, GlobalClass.IS_COLOR, GlobalClass.tempIsColor);
                                        GlobalClass.setPreferencesInt(context, GlobalClass.KEYBOARD_COLOR_CODE, GlobalClass.tempKeyboardColorCode);
                                        GlobalClass.setPreferencesInt(context, GlobalClass.KEYBOARD_BG_IMAGE, GlobalClass.tempKeyboardBgImage);
                                        GlobalClass.setPreferencesInt(context, GlobalClass.KEY_BG_COLOR, GlobalClass.tempKeyColor);
                                        GlobalClass.setPreferencesString(context, GlobalClass.KEY_RADIUS, GlobalClass.tempKeyRadius);
                                        GlobalClass.setPreferencesString(context, GlobalClass.KEY_STROKE, GlobalClass.tempKeyStroke);
                                        GlobalClass.setPreferencesString(context, GlobalClass.KEY_OPACITY, GlobalClass.tempKeyOpacity);
                                        GlobalClass.setPreferencesString(context, GlobalClass.FONT_COLOR, GlobalClass.tempFontColor);
                                        GlobalClass.setPreferencesString(context, GlobalClass.FONT_NAME, GlobalClass.tempFontName);
                                        GlobalClass.setPreferencesString(context, GlobalClass.SOUND_STATUS, GlobalClass.tempSoundStatus);
                                        GlobalClass.setPreferencesInt(context, GlobalClass.SOUND_NAME, GlobalClass.tempSoundName);
                                        GlobalClass.setPreferencesInt(context, GlobalClass.SELECTWALLPAPER, GlobalClass.selectwallpaper);
                                        GlobalClass.setPreferencesInt(context, GlobalClass.SELECTTEXTWALLPAPER, GlobalClass.selecttextwallpaper);
                                        GlobalClass.setPreferencesInt(context, GlobalClass.SELECTCOLOR, GlobalClass.selectcolor);
                                        GlobalClass.setPreferencesInt(context, GlobalClass.SELECTVIEW, GlobalClass.selview);
                                        GlobalClass.setPreferencesString(context, GlobalClass.KEYBOARDBITMAPBACK, GlobalClass.keyboardBitmapBack);
                                    }

                                }
                                indicator.setViewPager(viewPager);

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
                break;
            case R.id.ivCreateKeyboard:
                setDefaultValue();
                Intent intent1 = new Intent(context, CreateKeyboardActivity.class);
                intent1.putExtra("isEdit", false);
                startActivity(intent1);
                GlobalClass.checkStartAd();
                finish();
                break;

            case R.id.ivNoData:
                ivCreateKeyboard.performClick();
        }

    }

    public void setDefaultValue() {

        if (GlobalClass.getPreferencesString(getApplicationContext(), GlobalClass.IS_COLOR, "") == null) {
            GlobalClass.tempIsColor = "no";
        } else {
            GlobalClass.tempIsColor = GlobalClass.getPreferencesString(getApplicationContext(), GlobalClass.IS_COLOR, "no");
        }

        if (GlobalClass.getPreferencesInt(getApplicationContext(), GlobalClass.KEYBOARD_COLOR_CODE, 0) == 0) {
            GlobalClass.tempKeyboardColorCode = R.color.one;
        } else {
            GlobalClass.tempKeyboardColorCode = GlobalClass.getPreferencesInt(getApplicationContext(), GlobalClass.KEYBOARD_COLOR_CODE, 0);
        }

        if (GlobalClass.getPreferencesInt(getApplicationContext(), GlobalClass.KEYBOARD_BG_IMAGE, 0) == 0) {
            GlobalClass.tempKeyboardBgImage = R.drawable.theme_color1;
        } else {
            GlobalClass.tempKeyboardBgImage = GlobalClass.getPreferencesInt(getApplicationContext(), GlobalClass.KEYBOARD_BG_IMAGE, 0);
        }

        if (GlobalClass.getPreferencesInt(getApplicationContext(), GlobalClass.KEY_BG_COLOR, 0) == 0) {
            GlobalClass.tempKeyColor = getResources().getColor(R.color.eight);
        } else {
            GlobalClass.tempKeyColor = GlobalClass.getPreferencesInt(getApplicationContext(), GlobalClass.KEY_BG_COLOR, 0);
        }

        if (GlobalClass.getPreferencesString(getApplicationContext(), GlobalClass.KEY_RADIUS, "") == null) {
            GlobalClass.tempKeyRadius = "18";
        } else {
            GlobalClass.tempKeyRadius = GlobalClass.getPreferencesString(getApplicationContext(), GlobalClass.KEY_RADIUS, "18");
        }

        if (GlobalClass.getPreferencesString(getApplicationContext(), GlobalClass.KEY_STROKE, "") == null) {
            GlobalClass.tempKeyStroke = "2";
        } else {
            GlobalClass.tempKeyStroke = GlobalClass.getPreferencesString(getApplicationContext(), GlobalClass.KEY_STROKE, "2");
        }

        if (GlobalClass.getPreferencesString(getApplicationContext(), GlobalClass.KEY_OPACITY, "") == null) {
            GlobalClass.tempKeyOpacity = "255";
        } else {
            GlobalClass.tempKeyOpacity = GlobalClass.getPreferencesString(getApplicationContext(), GlobalClass.KEY_OPACITY, "255");
        }

        if (GlobalClass.getPreferencesString(getApplicationContext(), GlobalClass.FONT_COLOR, "") == null) {
            GlobalClass.tempFontColor = "#FFFFFF";
        } else {
            GlobalClass.tempFontColor = GlobalClass.getPreferencesString(getApplicationContext(), GlobalClass.FONT_COLOR, "#FFFFFF");
        }

        if (GlobalClass.getPreferencesString(getApplicationContext(), GlobalClass.FONT_NAME, "") == null) {
            GlobalClass.tempFontName = "";
        } else {
            GlobalClass.tempFontName = GlobalClass.getPreferencesString(getApplicationContext(), GlobalClass.FONT_NAME, "");
        }

        if (GlobalClass.getPreferencesString(getApplicationContext(), GlobalClass.SOUND_STATUS, "") == null) {
            GlobalClass.tempSoundStatus = "off";
        } else {
            GlobalClass.tempSoundStatus = GlobalClass.getPreferencesString(getApplicationContext(), GlobalClass.SOUND_STATUS, "off");
        }

        if (GlobalClass.getPreferencesInt(getApplicationContext(), GlobalClass.SOUND_NAME, 0) == 0) {
            GlobalClass.tempSoundName = 0;
        } else {
            GlobalClass.tempSoundName = GlobalClass.getPreferencesInt(getApplicationContext(), GlobalClass.SOUND_NAME, R.raw.balloon_snap);
        }

        if (GlobalClass.getPreferencesInt(getApplicationContext(), GlobalClass.SELECTWALLPAPER, 0) == 0) {
            GlobalClass.selectwallpaper = 0;
        } else {
            GlobalClass.selectwallpaper = GlobalClass.getPreferencesInt(getApplicationContext(), GlobalClass.SELECTWALLPAPER, 0);
        }

        if (GlobalClass.getPreferencesInt(getApplicationContext(), GlobalClass.SELECTTEXTWALLPAPER, 0) == 0) {
            GlobalClass.selecttextwallpaper = 0;
        } else {
            GlobalClass.selecttextwallpaper = GlobalClass.getPreferencesInt(getApplicationContext(), GlobalClass.SELECTTEXTWALLPAPER, 0);
        }

        if (GlobalClass.getPreferencesInt(getApplicationContext(), GlobalClass.SELECTCOLOR, 0) == 0) {
            GlobalClass.selectcolor = 0;
        } else {
            GlobalClass.selectcolor = GlobalClass.getPreferencesInt(getApplicationContext(), GlobalClass.SELECTCOLOR, 0);
        }

        if (GlobalClass.getPreferencesInt(getApplicationContext(), GlobalClass.SELECTVIEW, 0) == 0) {
            GlobalClass.selview = 2;
        } else {
            GlobalClass.selview = GlobalClass.getPreferencesInt(getApplicationContext(), GlobalClass.SELECTVIEW, 0);
        }

        if (GlobalClass.getPreferencesString(getApplicationContext(), GlobalClass.KEYBOARDBITMAPBACK, null) == null) {
            GlobalClass.keyboardBitmapBack = null;
        } else {
            GlobalClass.keyboardBitmapBack = GlobalClass.getPreferencesString(getApplicationContext(), GlobalClass.KEYBOARDBITMAPBACK, null);
        }

        if (GlobalClass.selectbgcolor == 0) {
            GlobalClass.selectbgcolor = 7;
        }
        if (GlobalClass.selectfontcolor == 0) {
            GlobalClass.selectfontcolor = 1;
        }
        if (GlobalClass.selectsounds == 0) {
            GlobalClass.selectsounds = 0;
        }
        if (GlobalClass.selectfonts == 0) {
            GlobalClass.selectfonts = 0;
        }

        GlobalClass.setPreferencesString(context, GlobalClass.IS_COLOR, GlobalClass.tempIsColor);
        GlobalClass.setPreferencesInt(context, GlobalClass.KEYBOARD_COLOR_CODE, GlobalClass.tempKeyboardColorCode);
        GlobalClass.setPreferencesInt(context, GlobalClass.KEYBOARD_BG_IMAGE, GlobalClass.tempKeyboardBgImage);
        GlobalClass.setPreferencesInt(context, GlobalClass.KEY_BG_COLOR, GlobalClass.tempKeyColor);
        GlobalClass.setPreferencesString(context, GlobalClass.KEY_RADIUS, GlobalClass.tempKeyRadius);
        GlobalClass.setPreferencesString(context, GlobalClass.KEY_STROKE, GlobalClass.tempKeyStroke);
        GlobalClass.setPreferencesString(context, GlobalClass.KEY_OPACITY, GlobalClass.tempKeyOpacity);
        GlobalClass.setPreferencesString(context, GlobalClass.FONT_COLOR, GlobalClass.tempFontColor);
        GlobalClass.setPreferencesString(context, GlobalClass.FONT_NAME, GlobalClass.tempFontName);
        GlobalClass.setPreferencesString(context, GlobalClass.SOUND_STATUS, GlobalClass.tempSoundStatus);
        GlobalClass.setPreferencesInt(context, GlobalClass.SOUND_NAME, GlobalClass.tempSoundName);
        GlobalClass.setPreferencesInt(context, GlobalClass.SELECTWALLPAPER, GlobalClass.selectwallpaper);
        GlobalClass.setPreferencesInt(context, GlobalClass.SELECTTEXTWALLPAPER, GlobalClass.selecttextwallpaper);
        GlobalClass.setPreferencesInt(context, GlobalClass.SELECTCOLOR, GlobalClass.selectcolor);
        GlobalClass.setPreferencesInt(context, GlobalClass.SELECTVIEW, GlobalClass.selview);
        GlobalClass.setPreferencesString(context, GlobalClass.KEYBOARDBITMAPBACK, GlobalClass.keyboardBitmapBack);
    }

    private void askForPermission() {
        askCompactPermissions(new String[]{PermissionUtils.Manifest_WRITE_EXTERNAL_STORAGE, PermissionUtils.Manifest_READ_EXTERNAL_STORAGE}, new PermissionResult() {
            @Override
            public void permissionGranted() {
            }

            @Override
            public void permissionDenied() {
            }

            @Override
            public void permissionForeverDenied() {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

}