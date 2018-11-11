package com.codminskeyboards.universekeyboard.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.PagerAdapter;
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

        backgroundImageView.setImageResource(keyboardArrayList.get(position).getKeyboardBackground());

        int fontColor = keyboardArrayList.get(position).getFontColor();
        shiftImageView.setColorFilter(fontColor);
        enterImageView.setColorFilter(fontColor);
        backspaceImageView.setColorFilter(fontColor);
        emojiImageView.setColorFilter(fontColor);
        spaceImageView.setColorFilter(fontColor);

        int keyColor = keyboardArrayList.get(position).getKeyColor();

        GradientDrawable keyBackground;
        for (int i = 0; i < keysLayout.getChildCount(); i++) {
            final View child = keysLayout.getChildAt(i);

            GlobalClass.printLog("call the setRadius", "----if---------" + i);
            if (child instanceof ImageView || child instanceof TextView) {
                keyBackground = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{keyColor, keyColor});
                keyBackground.setBounds(child.getLeft() + 5, child.getTop() + 5, child.getRight() - 5, child.getBottom() - 5);

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
                    ((TextView) child).setTypeface(ResourcesCompat.getFont(context, GlobalClass.getPreferencesInt(context, GlobalClass.FONT_NAME, R.font.abel_regular)));
                }
            }
        }

        viewGroup.addView(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalClass.keyboardBackground = keyboardArrayList.get(position).getKeyboardBackground();
                GlobalClass.keyColor = keyboardArrayList.get(position).getKeyColor();
                GlobalClass.keyRadius = keyboardArrayList.get(position).getKeyRadius();
                GlobalClass.keyStroke = keyboardArrayList.get(position).getKeyStroke();
                GlobalClass.keyOpacity = keyboardArrayList.get(position).getKeyOpacity();
                GlobalClass.fontColor = keyboardArrayList.get(position).getFontColor();
                GlobalClass.fontId = keyboardArrayList.get(position).getFontId();
                GlobalClass.soundStatus = keyboardArrayList.get(position).getSoundStatus();
                GlobalClass.soundId = keyboardArrayList.get(position).getSoundId();
                GlobalClass.backgroundPosition = keyboardArrayList.get(position).getBackgroundPosition();
                GlobalClass.colorPosition = keyboardArrayList.get(position).getColorPosition();
                GlobalClass.drawableOrColor = keyboardArrayList.get(position).getDrawableOrColor();

                GlobalClass.setPreferencesInt(context, GlobalClass.KEYBOARD_BACKGROUND, keyboardArrayList.get(position).getKeyboardBackground());
                GlobalClass.setPreferencesInt(context, GlobalClass.KEY_COLOR, keyboardArrayList.get(position).getKeyColor());
                GlobalClass.setPreferencesInt(context, GlobalClass.KEY_RADIUS, keyboardArrayList.get(position).getKeyRadius());
                GlobalClass.setPreferencesInt(context, GlobalClass.KEY_STROKE, keyboardArrayList.get(position).getKeyStroke());
                GlobalClass.setPreferencesInt(context, GlobalClass.KEY_OPACITY, keyboardArrayList.get(position).getKeyOpacity());
                GlobalClass.setPreferencesInt(context, GlobalClass.FONT_COLOR, keyboardArrayList.get(position).getFontColor());
                GlobalClass.setPreferencesInt(context, GlobalClass.FONT_NAME, keyboardArrayList.get(position).getFontId());
                GlobalClass.setPreferencesBool(context, GlobalClass.SOUND_STATUS, keyboardArrayList.get(position).getSoundStatus());
                GlobalClass.setPreferencesInt(context, GlobalClass.SOUND_ID, keyboardArrayList.get(position).getSoundId());
                GlobalClass.setPreferencesInt(context, GlobalClass.BACKGROUND_POSITION, keyboardArrayList.get(position).getBackgroundPosition());
                GlobalClass.setPreferencesInt(context, GlobalClass.COLOR_POSITION, keyboardArrayList.get(position).getColorPosition());
                GlobalClass.setPreferencesInt(context, GlobalClass.DRAWABLE_OR_COLOR, keyboardArrayList.get(position).getDrawableOrColor());

                Intent intent = new Intent(context, CreateKeyboardActivity.class);
                intent.putExtra("isEdit", true);
                intent.putExtra("position", position);
                context.startActivity(intent);
            }
        });
        return view;
    }

}
