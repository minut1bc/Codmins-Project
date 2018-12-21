package com.codminskeyboards.universekeyboard.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codminskeyboards.universekeyboard.R;
import com.codminskeyboards.universekeyboard.activity.CreateKeyboardActivity;
import com.codminskeyboards.universekeyboard.activity.MainActivity;
import com.codminskeyboards.universekeyboard.utils.GlobalClass;
import com.codminskeyboards.universekeyboard.utils.KeyboardData;

public class KeyboardFragment extends Fragment {

    int position;

    public static KeyboardFragment newInstance(int position) {
        KeyboardFragment f = new KeyboardFragment();
        f.position = position;
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View keyboardFragmentView = inflater.inflate(R.layout.fragment_keyboard, container, false);

        ImageView backgroundImageView = keyboardFragmentView.findViewById(R.id.backgroundImageView);
        ConstraintLayout keysLayout = keyboardFragmentView.findViewById(R.id.keysLayout);
        final KeyboardData keyboardData = GlobalClass.keyboardDataArray.get(position);

        if (keyboardData.getBackgroundIsDrawable()) {
            backgroundImageView.setImageResource(GlobalClass.backgroundArray[keyboardData.getBackgroundPosition()]);
        } else {
            backgroundImageView.setImageResource(GlobalClass.colorsArray[keyboardData.getBackgroundColorPosition()]);
        }

        int fontColor = getResources().getColor(GlobalClass.colorsArray[keyboardData.getFontColorPosition()]);
        int keyColor = getResources().getColor(GlobalClass.colorsArray[keyboardData.getKeyColorPosition()]);

        GradientDrawable keyBackground;
        for (int i = 0; i < keysLayout.getChildCount(); i++) {
            final View child = keysLayout.getChildAt(i);

            GlobalClass.printLog("call the setRadius", "----if---------" + i);
            if (child instanceof ImageView || child instanceof TextView) {
                keyBackground = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
                        new int[]{keyColor, keyColor});
                keyBackground.setBounds(child.getLeft() + 5, child.getTop() + 5,
                        child.getRight() - 5, child.getBottom() - 5);
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
                        keyBackground.setStroke(3, Color.GRAY);
                        break;
                }

                child.setBackground(keyBackground);

                if (child instanceof TextView) {
                    ((TextView) child).setTextColor(fontColor);
                    ((TextView) child).setTextSize(10);
                    ((TextView) child).setTypeface(GlobalClass.fontsArray[keyboardData.getFontPosition()]);
                }

                if (child instanceof ImageView) {
                    ((ImageView) child).setColorFilter(fontColor);
                }
            }
        }

        keyboardFragmentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CreateKeyboardActivity.class);
                intent.putExtra("position", position);
                ((MainActivity) getContext()).startActivityForResult(intent, 1);
            }
        });

        return keyboardFragmentView;
    }
}
