package com.codminskeyboards.universekeyboard.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codminskeyboards.universekeyboard.BuildConfig;
import com.codminskeyboards.universekeyboard.R;
import com.codminskeyboards.universekeyboard.activity.CreateKeyboardActivity;
import com.codminskeyboards.universekeyboard.model.KeyboardData;
import com.codminskeyboards.universekeyboard.utils.GlobalClass;

import java.util.ArrayList;

public class KeyboardViewPagerAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<KeyboardData> keyboardArrayList;

    public KeyboardViewPagerAdapter(Context context, ArrayList<KeyboardData> keyboardArrayList) {
        this.context = context;
        this.keyboardArrayList = keyboardArrayList;
    }

    @Override
    public int getCount() {
        return keyboardArrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @SuppressLint("ResourceType")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View instantiateItem(@NonNull final ViewGroup container, final int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.layout_keyboard, null);

        ImageView previewKeyboardBackgroundImageView = view.findViewById(R.id.backgroundImageView);
        ConstraintLayout keyboardKeysLayout = view.findViewById(R.id.keysLayout);

        ImageView enterImageView = view.findViewById(R.id.enterImageView);
        ImageView spaceImageView = view.findViewById(R.id.spaceImageView);
        ImageView shiftImageView = view.findViewById(R.id.shiftImageView);
        ImageView backspaceImageView = view.findViewById(R.id.emojiBackspaceImageView);
        ImageView emojiImageView = view.findViewById(R.id.emojiImageView);

        if (BuildConfig.VERSION_CODE >= 21)
            view.setClipToOutline(true);

        if (keyboardArrayList.get(position).getBitmapback() != null) {
            byte[] decodedString = Base64.decode(keyboardArrayList.get(position).getBitmapback(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            previewKeyboardBackgroundImageView.setImageBitmap(decodedByte);
        } else {
            previewKeyboardBackgroundImageView.setImageResource(keyboardArrayList.get(position).getKeyboardBgImage());
        }

        int fontColor = Color.parseColor(keyboardArrayList.get(position).getFontColor());
        shiftImageView.setColorFilter(fontColor);
        enterImageView.setColorFilter(fontColor);
        backspaceImageView.setColorFilter(fontColor);
        emojiImageView.setColorFilter(fontColor);

        GradientDrawable npd1;
        for (int i = 0; i < keyboardKeysLayout.getChildCount(); i++) {
            final View mChild = keyboardKeysLayout.getChildAt(i);

            GlobalClass.printLog("call the setRadius", "----if---------" + i);
            if (mChild instanceof ImageView || mChild instanceof TextView) {
                // Recursively attempt another ViewGroup.
                npd1 = new GradientDrawable(
                        GradientDrawable.Orientation.TOP_BOTTOM,
                        new int[]{keyboardArrayList.get(position).getKeyBgColor(),
                                keyboardArrayList.get(position).getKeyBgColor()});
                npd1.setBounds(mChild.getLeft() + 5, mChild.getTop() + 5, mChild.getRight() - 5, mChild.getBottom() - 5);

                npd1.setCornerRadius(keyboardArrayList.get(position).getKeyRadius());
                npd1.setAlpha(keyboardArrayList.get(position).getKeyOpacity());

                switch (keyboardArrayList.get(position).getKeyStroke()) {
                    case 1:
                        npd1.setStroke(0, context.getResources().getColor(R.color.colorPrimary));
                        break;
                    case 2:
                        npd1.setStroke(2, android.graphics.Color.WHITE);
                        break;
                    case 3:
                        npd1.setStroke(2, android.graphics.Color.BLACK);
                        break;
                    case 4:
                        npd1.setStroke(4, android.graphics.Color.BLACK);

                        GlobalClass.printLog("click on four", "---------apply stroke----------");
                        break;
                    case 5:
                        npd1.setStroke(3, android.graphics.Color.GRAY);
                        GlobalClass.printLog("click on five", "---------apply stroke----------");
                        break;
                }

                mChild.setBackground(npd1);

                if (mChild instanceof TextView) {
                    ((TextView) mChild).setTextColor(android.graphics.Color.parseColor(keyboardArrayList.get(position).getFontColor()));
                    ((TextView) mChild).setTextSize(10);

                    if (keyboardArrayList.get(position).getFontName().length() != 0 && keyboardArrayList.get(position).getFontName() != null
                            && !keyboardArrayList.get(position).getFontName().isEmpty()) {
                        try {
                            Typeface font = Typeface.createFromAsset(context.getAssets(), keyboardArrayList.get(position).getFontName());
                            ((TextView) mChild).setTypeface(font);
                        } catch (Exception ignored) {

                        }
                    }
                }
            } else GlobalClass.printLog("call the setRadius", "------else-------" + i);
        }

        container.addView(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GlobalClass.tempIsColor = keyboardArrayList.get(position).getIsColor();
                GlobalClass.tempKeyboardBgImage = keyboardArrayList.get(position).getKeyboardBgImage();
                GlobalClass.tempKeyboardColorCode = keyboardArrayList.get(position).getKeyboardColorCode();
                GlobalClass.tempKeyColor = keyboardArrayList.get(position).getKeyBgColor();
                GlobalClass.tempKeyRadius = keyboardArrayList.get(position).getKeyRadius();
                GlobalClass.tempKeyStroke = keyboardArrayList.get(position).getKeyStroke();
                GlobalClass.tempKeyOpacity = keyboardArrayList.get(position).getKeyOpacity();
                GlobalClass.tempFontColor = keyboardArrayList.get(position).getFontColor();
                GlobalClass.tempFontName = keyboardArrayList.get(position).getFontName();
                GlobalClass.tempSoundStatus = keyboardArrayList.get(position).getSoundStatus();
                GlobalClass.tempSoundName = keyboardArrayList.get(position).getSoundName();
                GlobalClass.selectwallpaper = keyboardArrayList.get(position).getSelectwallpaper();
                GlobalClass.selectcolor = keyboardArrayList.get(position).getSelectcolor();
                GlobalClass.selview = keyboardArrayList.get(position).getSelview();
                GlobalClass.keyboardBitmapBack = keyboardArrayList.get(position).getBitmapback();

                GlobalClass.setPreferencesString(context, GlobalClass.IS_COLOR, keyboardArrayList.get(position).getIsColor());
                GlobalClass.setPreferencesInt(context, GlobalClass.KEYBOARD_COLOR_CODE, keyboardArrayList.get(position).getKeyboardColorCode());
                GlobalClass.setPreferencesInt(context, GlobalClass.KEYBOARD_BG_IMAGE, keyboardArrayList.get(position).getKeyboardBgImage());
                GlobalClass.setPreferencesInt(context, GlobalClass.KEY_BG_COLOR, keyboardArrayList.get(position).getKeyBgColor());
                GlobalClass.setPreferencesFloat(context, GlobalClass.KEY_RADIUS, keyboardArrayList.get(position).getKeyRadius());
                GlobalClass.setPreferencesInt(context, GlobalClass.KEY_STROKE, keyboardArrayList.get(position).getKeyStroke());
                GlobalClass.setPreferencesInt(context, GlobalClass.KEY_OPACITY, keyboardArrayList.get(position).getKeyOpacity());
                GlobalClass.setPreferencesString(context, GlobalClass.FONT_COLOR, keyboardArrayList.get(position).getFontColor());
                GlobalClass.setPreferencesString(context, GlobalClass.FONT_NAME, keyboardArrayList.get(position).getFontName());
                GlobalClass.setPreferencesString(context, GlobalClass.SOUND_STATUS, keyboardArrayList.get(position).getSoundStatus());
                GlobalClass.setPreferencesInt(context, GlobalClass.SOUND_NAME, keyboardArrayList.get(position).getSoundName());
                GlobalClass.setPreferencesInt(context, GlobalClass.SELECTWALLPAPER, keyboardArrayList.get(position).getSelectwallpaper());
                GlobalClass.setPreferencesInt(context, GlobalClass.SELECTCOLOR, keyboardArrayList.get(position).getSelectcolor());
                GlobalClass.setPreferencesInt(context, GlobalClass.SELECTVIEW, keyboardArrayList.get(position).getSelview());
                GlobalClass.setPreferencesString(context, GlobalClass.KEYBOARDBITMAPBACK, keyboardArrayList.get(position).getBitmapback());

                Intent intent = new Intent(context, CreateKeyboardActivity.class);
                intent.putExtra("isEdit", true);
                intent.putExtra("position", position);
                context.startActivity(intent);
            }
        });
        return view;
    }

}
