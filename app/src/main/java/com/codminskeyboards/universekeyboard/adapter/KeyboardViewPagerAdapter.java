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
import com.codminskeyboards.universekeyboard.activity.MainActivity;
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

        final KeyboardData currentKeyboard = keyboardArrayList.get(position);

        ImageView backgroundImageView = view.findViewById(R.id.backgroundImageView);
        ConstraintLayout keysLayout = view.findViewById(R.id.keysLayout);

        if (BuildConfig.VERSION_CODE >= 21)
            view.setClipToOutline(true);

        if (currentKeyboard.getBackgroundIsDrawable()) {
            backgroundImageView.setImageResource(GlobalClass.backgroundArray[currentKeyboard.getBackgroundPosition()]);
        } else {
            backgroundImageView.setImageResource(GlobalClass.colorsArray[currentKeyboard.getBackgroundColorPosition()]);
        }

        int fontColor = context.getResources().getColor(GlobalClass.colorsArray[currentKeyboard.getFontColorPosition()]);
        int keyColor = context.getResources().getColor(GlobalClass.colorsArray[currentKeyboard.getKeyColorPosition()]);

        GradientDrawable keyBackground;
        for (int i = 0; i < keysLayout.getChildCount(); i++) {
            final View child = keysLayout.getChildAt(i);

            GlobalClass.printLog("call the setRadius", "----if---------" + i);
            if (child instanceof ImageView || child instanceof TextView) {
                keyBackground = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
                        new int[]{keyColor, keyColor});
                keyBackground.setBounds(child.getLeft() + 5, child.getTop() + 5,
                        child.getRight() - 5, child.getBottom() - 5);
                keyBackground.setCornerRadius(currentKeyboard.getKeyRadius());
                keyBackground.setAlpha(currentKeyboard.getKeyOpacity());

                switch (currentKeyboard.getKeyStroke()) {
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
                    ((TextView) child).setTypeface(GlobalClass.fontsArray[currentKeyboard.getFontPosition()]);
                }

                if (child instanceof ImageView) {
                    ((ImageView) child).setColorFilter(fontColor);
                }
            }
        }

        viewGroup.addView(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CreateKeyboardActivity.class);
                intent.putExtra("isEdit", true);
                intent.putExtra("keyboardData", KeyboardData.serialize(currentKeyboard));
                intent.putExtra("position", position);
                ((MainActivity) context).startActivityForResult(intent, 1);
            }
        });
        return view;
    }
}