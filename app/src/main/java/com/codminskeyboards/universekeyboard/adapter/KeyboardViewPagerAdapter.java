package com.codminskeyboards.universekeyboard.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codminskeyboards.universekeyboard.BuildConfig;
import com.codminskeyboards.universekeyboard.R;
import com.codminskeyboards.universekeyboard.activity.CreateKeyboardActivity;
import com.codminskeyboards.universekeyboard.utils.GlobalClass;
import com.codminskeyboards.universekeyboard.utils.KeyboardData;

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
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View instantiateItem(@NonNull final ViewGroup viewGroup, final int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        View view = inflater.inflate(R.layout.layout_keyboard, viewGroup, false);

        ImageView backgroundImageView = view.findViewById(R.id.backgroundImageView);
        ConstraintLayout keysLayout = view.findViewById(R.id.keysLayout);

        ImageView enterImageView = view.findViewById(R.id.enterImageView);
        ImageView spaceImageView = view.findViewById(R.id.spaceImageView);
        ImageView shiftImageView = view.findViewById(R.id.shiftImageView);
        ImageView backspaceImageView = view.findViewById(R.id.emojiBackspaceImageView);
        ImageView emojiImageView = view.findViewById(R.id.emojiImageView);

        if (BuildConfig.VERSION_CODE >= 21)
            view.setClipToOutline(true);

        if (keyboardArrayList.get(position).getBackgroundIsDrawable()) {
            backgroundImageView.setImageResource(GlobalClass.backgroundArray[keyboardArrayList.get(position).getBackgroundPosition()]);
        } else {
            backgroundImageView.setImageResource(GlobalClass.colorsArray[keyboardArrayList.get(position).getBackgroundColorPosition()]);
        }

        int fontColor = context.getResources().getColor(GlobalClass.colorsArray[keyboardArrayList.get(position).getFontColorPosition()]);
        int keyColor = context.getResources().getColor(GlobalClass.colorsArray[keyboardArrayList.get(position).getKeyColorPosition()]);

        shiftImageView.setColorFilter(fontColor);
        enterImageView.setColorFilter(fontColor);
        backspaceImageView.setColorFilter(fontColor);
        emojiImageView.setColorFilter(fontColor);
        spaceImageView.setColorFilter(fontColor);


        GradientDrawable keyBackground;
        for (int i = 0; i < keysLayout.getChildCount(); i++) {
            final View child = keysLayout.getChildAt(i);

            GlobalClass.printLog("call the setRadius", "----if---------" + i);
            if (child instanceof ImageView || child instanceof TextView) {
                keyBackground = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
                        new int[]{keyColor, keyColor});
                keyBackground.setBounds(child.getLeft() + 5, child.getTop() + 5,
                        child.getRight() - 5, child.getBottom() - 5);
                keyBackground.setCornerRadius(keyboardArrayList.get(position).getKeyRadius());
                keyBackground.setAlpha(keyboardArrayList.get(position).getKeyOpacity());

                switch (keyboardArrayList.get(position).getKeyStroke()) {
                    case 1:
                        keyBackground.setStroke(0, context.getResources().getColor(R.color.colorPrimary));
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
                        keyBackground.setStroke(3, Color.GRAY);
                        break;
                }

                child.setBackground(keyBackground);

                if (child instanceof TextView) {
                    ((TextView) child).setTextColor(fontColor);
                    ((TextView) child).setTextSize(10);
                    ((TextView) child).setTypeface(GlobalClass.fontsArray[keyboardArrayList.get(position).getFontPosition()]);
                }
            }
        }

        viewGroup.addView(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalClass.backgroundIsDrawable = keyboardArrayList.get(position).getBackgroundIsDrawable();
                GlobalClass.backgroundPosition = keyboardArrayList.get(position).getBackgroundPosition();
                GlobalClass.backgroundColorPosition = keyboardArrayList.get(position).getBackgroundColorPosition();
                GlobalClass.keyRadius = keyboardArrayList.get(position).getKeyRadius();
                GlobalClass.keyStroke = keyboardArrayList.get(position).getKeyStroke();
                GlobalClass.keyOpacity = keyboardArrayList.get(position).getKeyOpacity();
                GlobalClass.keyColorPosition = keyboardArrayList.get(position).getKeyColorPosition();
                GlobalClass.fontPosition = keyboardArrayList.get(position).getFontPosition();
                GlobalClass.fontColorPosition = keyboardArrayList.get(position).getFontColorPosition();
                GlobalClass.vibrationValue = keyboardArrayList.get(position).getVibrationValue();
                GlobalClass.soundPosition = keyboardArrayList.get(position).getSoundPosition();
                GlobalClass.soundOn = keyboardArrayList.get(position).getSoundOn();

                GlobalClass.setPreferencesBool(context, GlobalClass.BACKGROUND_IS_DRAWABLE, keyboardArrayList.get(position).getBackgroundIsDrawable());
                GlobalClass.setPreferencesInt(context, GlobalClass.BACKGROUND_POSITION, keyboardArrayList.get(position).getBackgroundPosition());
                GlobalClass.setPreferencesInt(context, GlobalClass.BACKGROUND_COLOR_POSITION, keyboardArrayList.get(position).getBackgroundColorPosition());
                GlobalClass.setPreferencesInt(context, GlobalClass.KEY_RADIUS, keyboardArrayList.get(position).getKeyRadius());
                GlobalClass.setPreferencesInt(context, GlobalClass.KEY_STROKE, keyboardArrayList.get(position).getKeyStroke());
                GlobalClass.setPreferencesInt(context, GlobalClass.KEY_OPACITY, keyboardArrayList.get(position).getKeyOpacity());
                GlobalClass.setPreferencesInt(context, GlobalClass.KEY_COLOR_POSITION, keyboardArrayList.get(position).getKeyColorPosition());
                GlobalClass.setPreferencesInt(context, GlobalClass.FONT_POSITION, keyboardArrayList.get(position).getFontPosition());
                GlobalClass.setPreferencesInt(context, GlobalClass.FONT_COLOR_POSITION, keyboardArrayList.get(position).getFontColorPosition());
                GlobalClass.setPreferencesInt(context, GlobalClass.VIBRATION_VALUE, keyboardArrayList.get(position).getVibrationValue());
                GlobalClass.setPreferencesInt(context, GlobalClass.SOUND_POSITION, keyboardArrayList.get(position).getSoundPosition());
                GlobalClass.setPreferencesBool(context, GlobalClass.SOUND_ON, keyboardArrayList.get(position).getSoundOn());

                Intent intent = new Intent(context, CreateKeyboardActivity.class);
                intent.putExtra("isEdit", true);
                intent.putExtra("position", position);
                context.startActivity(intent);
            }
        });
        return view;
    }

}
