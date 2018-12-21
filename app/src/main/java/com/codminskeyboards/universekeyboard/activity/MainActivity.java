package com.codminskeyboards.universekeyboard.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
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

import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends AppCompatActivity {

//    IabHelper iabHelper;
//    boolean isThemeSlotPurchased = false;
//    private String TAG = "Grid View Activity";
//    private int requestCode;

    private Context context;
    private ViewPager viewPager;
    private KeyboardViewPagerAdapter keyboardViewPagerAdapter;
    CircleIndicator circleIndicator;
    private ConstraintLayout keyboardDataLayout;
    boolean doubleBackToExitPressedOnce = false;
    private AdView adView;
    private ImageView addKeyboardImageView;
    private ImageView createKeyboardImageView;
    private ImageView applyImageView;

//    Called when consumption is complete
//    IabHelper.OnConsumeFinishedListener consumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
//        public void onConsumeFinished(Purchase purchase, IabResult result) {
//            GlobalClass.printLog(TAG, "Consumption finished. Purchase: " + purchase + ", result: " + result);
//
//          if we were disposed of in the meantime, quit.
//          if (iabHelper == null) return;
//
//     We know this is the "gas" sku because it's the only one we consume,
//     so we don't check which sku was consumed. If you have more than one
//     sku, you probably should check...
//            if (result.isSuccess()) {
//     successfully consumed, so we apply the effects of the item in our
//     game world's logic, which in our case means filling the gas tank a bit
//                GlobalClass.printLog(TAG, "Consumption successful. Provisioning.");
//            } else {
//                complain("Error while consuming: " + result);
//            }
////            updateUi();
//
//            GlobalClass.printLog(TAG, "End consumption flow.");
//        }
//    };
//
//    Listener that's called when we finish querying the items and subscriptions we own
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

        if (GlobalClass.colorsArray == null) {
            GlobalClass.setResourcesArrays(context);
        }

        new AppRater.DefaultBuilder(context, getPackageName())
                .showDefault()
                .daysToWait(0)
                .timesToLaunch(1)
                .title("Rate " + getResources().getString(R.string.app_name))
                .appLaunched();

        setContent();

        //MobileAds.initialize(this, "ca-app-pub-2002759323605741~1307687673" );
        if (GlobalClass.getPreferencesBool(context, GlobalClass.key_isAdLock, true))
            setAdMob();
    }


//    private void updateData() {
//        if (requestCode == GlobalClass.RC_REQUEST_THEMES_SLOTES)
//            GlobalClass.setPreferencesBool(context, getString(R.string.theme_slot_purchased), true);
//    }

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

//    void complain(String message) {
//        GlobalClass.printLog(TAG, "**** TrivialDrive Error: " + message);
//    }

    private void setContent() {
        InterstitialAd interstitialAd = new InterstitialAd(context);
        interstitialAd.setAdUnitId(getResources().getString(R.string.interstitial_full_screen));
        interstitialAd.loadAd(new AdRequest.Builder().build());

        viewPager = findViewById(R.id.keyboardViewPager);
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

        keyboardViewPagerAdapter = new KeyboardViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(keyboardViewPagerAdapter);
        viewPager.setCurrentItem(GlobalClass.keyboardPosition);
        circleIndicator.setViewPager(viewPager);

        if (GlobalClass.keyboardDataArray.size() == 0) {
            createKeyboardImageView.setVisibility(View.VISIBLE);
            keyboardDataLayout.setVisibility(View.GONE);
            addKeyboardImageView.setVisibility(View.GONE);
        } else {
            createKeyboardImageView.setVisibility(View.GONE);
            keyboardDataLayout.setVisibility(View.VISIBLE);
            addKeyboardImageView.setVisibility(View.VISIBLE);
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                if (viewPager.getCurrentItem() == GlobalClass.keyboardPosition) {
                    applyImageView.setImageDrawable(getResources().getDrawable(R.drawable.btn_apply));
                } else {
                    applyImageView.setImageDrawable(getResources().getDrawable(R.drawable.btn_disable));
                }
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

        applyImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GlobalClass.isKeyboardEnabled(context) && GlobalClass.isKeyboardSet(context)) {
                    if (viewPager.getCurrentItem() == GlobalClass.keyboardPosition) {
                        applyImageView.setImageDrawable(getResources().getDrawable(R.drawable.btn_disable));
                    } else {
                        GlobalClass.keyboardPosition = viewPager.getCurrentItem();
                        applyImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_apply));
                    }
                } else {
                    startActivity(new Intent(context, SetKeyboardActivity.class));
                }

                GlobalClass.setPreferencesArrayList(context, GlobalClass.keyboardDataArray);
                GlobalClass.setPreferencesInt(context, GlobalClass.KEYBOARD_POSITION, GlobalClass.keyboardPosition);
            }
        });

        deleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                                if (GlobalClass.keyboardDataArray.size() > 1) {
                                    createKeyboardImageView.setVisibility(View.GONE);
                                    keyboardDataLayout.setVisibility(View.VISIBLE);
                                    addKeyboardImageView.setVisibility(View.VISIBLE);

                                    if (viewPager.getCurrentItem() > 0) {
                                        GlobalClass.keyboardPosition = viewPager.getCurrentItem() - 1;
                                    } else {
                                        GlobalClass.keyboardPosition = 0;
                                    }

                                    GlobalClass.keyboardDataArray.remove(viewPager.getCurrentItem());
                                    GlobalClass.setPreferencesArrayList(context, GlobalClass.keyboardDataArray);
                                    GlobalClass.setPreferencesInt(context, GlobalClass.KEYBOARD_POSITION, GlobalClass.keyboardPosition);
                                    keyboardViewPagerAdapter = new KeyboardViewPagerAdapter(getSupportFragmentManager());
                                    viewPager.setAdapter(keyboardViewPagerAdapter);
                                    circleIndicator.setViewPager(viewPager);
                                    keyboardViewPagerAdapter.notifyDataSetChanged();

                                    applyImageView.setImageDrawable(getResources().getDrawable(R.drawable.btn_apply));

                                    viewPager.setCurrentItem(GlobalClass.keyboardPosition);
                                } else {
                                    createKeyboardImageView.setVisibility(View.VISIBLE);
                                    keyboardDataLayout.setVisibility(View.GONE);
                                    addKeyboardImageView.setVisibility(View.GONE);

                                    GlobalClass.keyboardDataArray.clear();
                                    GlobalClass.setPreferencesArrayList(context, GlobalClass.keyboardDataArray);
                                }
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        createKeyboardImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addKeyboardImageView.performClick();
            }
        });

        addKeyboardImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CreateKeyboardActivity.class);
                intent.putExtra("position", -1);
                startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    KeyboardData keyboardData = KeyboardData.deserialize(data.getStringExtra("keyboardData"));
                    if (data.getBooleanExtra("isEdit", false)) {
                        GlobalClass.keyboardDataArray.set(GlobalClass.keyboardPosition, keyboardData);
                    } else {
                        GlobalClass.keyboardDataArray.add(GlobalClass.keyboardPosition, keyboardData);
                    }

                    keyboardViewPagerAdapter = new KeyboardViewPagerAdapter(getSupportFragmentManager());
                    viewPager.setAdapter(keyboardViewPagerAdapter);
                    viewPager.setCurrentItem(GlobalClass.keyboardPosition);
                    circleIndicator.setViewPager(viewPager);
                    keyboardViewPagerAdapter.notifyDataSetChanged();

                    GlobalClass.setPreferencesArrayList(context, GlobalClass.keyboardDataArray);
                    GlobalClass.setPreferencesInt(context, GlobalClass.KEYBOARD_POSITION, GlobalClass.keyboardPosition);

                    if (GlobalClass.keyboardDataArray.size() == 0) {
                        createKeyboardImageView.setVisibility(View.VISIBLE);
                        keyboardDataLayout.setVisibility(View.GONE);
                        addKeyboardImageView.setVisibility(View.GONE);
                    } else {
                        createKeyboardImageView.setVisibility(View.GONE);
                        keyboardDataLayout.setVisibility(View.VISIBLE);
                        addKeyboardImageView.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
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