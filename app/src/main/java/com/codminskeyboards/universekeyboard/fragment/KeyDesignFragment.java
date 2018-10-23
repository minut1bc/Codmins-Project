package com.codminskeyboards.universekeyboard.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.codminskeyboards.universekeyboard.R;
import com.codminskeyboards.universekeyboard.activity.CreateKeyboardActivity;
import com.codminskeyboards.universekeyboard.adapter.FillKeyBgAdapter;
import com.codminskeyboards.universekeyboard.utils.GlobalClass;

import de.hdodenhof.circleimageview.CircleImageView;

public class KeyDesignFragment extends Fragment {

    Context context;

    RecyclerView rvDefaultColorKeyDesign;

    CircleImageView radiusOne;
    CircleImageView radiusTwo;
    CircleImageView radiusThree;
    CircleImageView radiusFour;
    CircleImageView radiusFive;
    CircleImageView ivOpacityHundred;
    CircleImageView ivOpacitySeventyFive;
    CircleImageView ivOpacityFifty;
    CircleImageView ivOpacityTwentyFive;
    CircleImageView ivOpacityZero;
    CircleImageView ivStrokeOne;
    CircleImageView ivStrokeTwo;
    CircleImageView ivStrokeThree;
    CircleImageView ivStrokeFour;
    CircleImageView ivStrokeFive;

    ImageView ivDone;
    ImageView ivShift;
    ImageView ivCancel;

    CreateKeyboardActivity createKeyboardActivity;
    MyOnClickListener onClickListener = new MyOnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ivStrokeOne:
                    ivStrokeOne.setBorderWidth(5);
                    ivStrokeTwo.setBorderWidth(0);
                    ivStrokeThree.setBorderWidth(0);
                    ivStrokeFour.setBorderWidth(0);
                    ivStrokeFive.setBorderWidth(0);
                    ivStrokeOne.setBorderColor(getResources().getColor(R.color.pink));
                    GlobalClass.tempKeyStroke = "1";
                    setRadius();
                    break;

                case R.id.ivStrokeTwo:
                    ivStrokeOne.setBorderWidth(0);
                    ivStrokeTwo.setBorderWidth(5);
                    ivStrokeThree.setBorderWidth(0);
                    ivStrokeFive.setBorderWidth(0);
                    ivStrokeFour.setBorderWidth(0);
                    ivStrokeTwo.setBorderColor(getResources().getColor(R.color.pink));
                    GlobalClass.tempKeyStroke = "2";
                    setRadius();
                    break;

                case R.id.ivStrokeThree:
                    ivStrokeOne.setBorderWidth(0);
                    ivStrokeTwo.setBorderWidth(0);
                    ivStrokeThree.setBorderWidth(5);
                    ivStrokeFour.setBorderWidth(0);
                    ivStrokeFive.setBorderWidth(0);
                    ivStrokeThree.setBorderColor(getResources().getColor(R.color.pink));
                    GlobalClass.tempKeyStroke = "3";
                    setRadius();
                    break;

                case R.id.ivStrokeFour:
                    ivStrokeOne.setBorderWidth(0);
                    ivStrokeTwo.setBorderWidth(0);
                    ivStrokeThree.setBorderWidth(0);
                    ivStrokeFour.setBorderWidth(5);
                    ivStrokeFive.setBorderWidth(0);
                    ivStrokeFour.setBorderColor(getResources().getColor(R.color.pink));
                    GlobalClass.tempKeyStroke = "4";
                    setRadius();
                    break;

                case R.id.ivStrokeFive:
                    ivStrokeOne.setBorderWidth(0);
                    ivStrokeTwo.setBorderWidth(0);
                    ivStrokeThree.setBorderWidth(0);
                    ivStrokeFour.setBorderWidth(0);
                    ivStrokeFive.setBorderWidth(5);
                    ivStrokeFive.setBorderColor(getResources().getColor(R.color.pink));
                    GlobalClass.tempKeyStroke = "5";
                    setRadius();
                    break;

                case R.id.ivOpacityHundred:
                    ivOpacityHundred.setBorderWidth(5);
                    ivOpacitySeventyFive.setBorderWidth(0);
                    ivOpacityFifty.setBorderWidth(0);
                    ivOpacityTwentyFive.setBorderWidth(0);
                    ivOpacityZero.setBorderWidth(0);
                    ivOpacityHundred.setBorderColor(getResources().getColor(R.color.pink));
                    GlobalClass.tempKeyOpacity = "255";
                    setRadius();
                    break;

                case R.id.ivOpacitySeventyFive:
                    ivOpacityHundred.setBorderWidth(0);
                    ivOpacitySeventyFive.setBorderWidth(5);
                    ivOpacityFifty.setBorderWidth(0);
                    ivOpacityTwentyFive.setBorderWidth(0);
                    ivOpacityZero.setBorderWidth(0);
                    ivOpacitySeventyFive.setBorderColor(getResources().getColor(R.color.pink));
                    GlobalClass.tempKeyOpacity = "192";
                    setRadius();
                    break;

                case R.id.ivOpacityFifty:
                    ivOpacityHundred.setBorderWidth(0);
                    ivOpacitySeventyFive.setBorderWidth(0);
                    ivOpacityFifty.setBorderWidth(5);
                    ivOpacityTwentyFive.setBorderWidth(0);
                    ivOpacityZero.setBorderWidth(0);
                    ivOpacityFifty.setBorderColor(getResources().getColor(R.color.pink));
                    GlobalClass.tempKeyOpacity = "128";
                    setRadius();
                    break;

                case R.id.ivOpacityTwentyFive:
                    ivOpacityHundred.setBorderWidth(0);
                    ivOpacitySeventyFive.setBorderWidth(0);
                    ivOpacityFifty.setBorderWidth(0);
                    ivOpacityTwentyFive.setBorderWidth(5);
                    ivOpacityZero.setBorderWidth(0);
                    ivOpacityTwentyFive.setBorderColor(getResources().getColor(R.color.pink));
                    GlobalClass.tempKeyOpacity = "64";
                    setRadius();
                    break;

                case R.id.ivOpacityZero:
                    ivOpacityHundred.setBorderWidth(0);
                    ivOpacitySeventyFive.setBorderWidth(0);
                    ivOpacityFifty.setBorderWidth(0);
                    ivOpacityTwentyFive.setBorderWidth(0);
                    ivOpacityZero.setBorderWidth(5);
                    ivOpacityZero.setBorderColor(getResources().getColor(R.color.pink));
                    GlobalClass.tempKeyOpacity = "0";
                    setRadius();
                    break;

                case R.id.radiusOne:
                    radiusOne.setBorderWidth(5);
                    radiusTwo.setBorderWidth(0);
                    radiusThree.setBorderWidth(0);
                    radiusFour.setBorderWidth(0);
                    radiusFive.setBorderWidth(0);
                    radiusOne.setBorderColor(getResources().getColor(R.color.pink));
                    GlobalClass.tempKeyRadius = "0";
                    setRadius();
                    break;

                case R.id.radiusTwo:
                    radiusOne.setBorderWidth(0);
                    radiusTwo.setBorderWidth(5);
                    radiusThree.setBorderWidth(0);
                    radiusFour.setBorderWidth(0);
                    radiusFive.setBorderWidth(0);
                    radiusTwo.setBorderColor(getResources().getColor(R.color.pink));
                    GlobalClass.tempKeyRadius = "9";
                    setRadius();
                    break;

                case R.id.radiusThree:
                    radiusOne.setBorderWidth(0);
                    radiusTwo.setBorderWidth(0);
                    radiusThree.setBorderWidth(5);
                    radiusFour.setBorderWidth(0);
                    radiusFive.setBorderWidth(0);
                    radiusThree.setBorderColor(getResources().getColor(R.color.pink));
                    GlobalClass.tempKeyRadius = "18";
                    setRadius();
                    break;

                case R.id.radiusFour:
                    radiusOne.setBorderWidth(0);
                    radiusTwo.setBorderWidth(0);
                    radiusThree.setBorderWidth(0);
                    radiusFour.setBorderWidth(5);
                    radiusFive.setBorderWidth(0);
                    radiusFour.setBorderColor(getResources().getColor(R.color.pink));
                    GlobalClass.tempKeyRadius = "25";
                    setRadius();
                    break;

                case R.id.radiusFive:
                    radiusOne.setBorderWidth(0);
                    radiusTwo.setBorderWidth(0);
                    radiusThree.setBorderWidth(0);
                    radiusFour.setBorderWidth(0);
                    radiusFive.setBorderWidth(5);
                    radiusFive.setBorderColor(getResources().getColor(R.color.pink));
                    GlobalClass.setPreferencesString(context, GlobalClass.KEY_RADIUS, "11");
                    GlobalClass.tempKeyRadius = "34";
                    setRadius();
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View keyDesignView = inflater.inflate(R.layout.key_design_fragment, container, false);

        rvDefaultColorKeyDesign = keyDesignView.findViewById(R.id.rvDefaultColorKeyDesign);
        rvDefaultColorKeyDesign.setNestedScrollingEnabled(false);
        rvDefaultColorKeyDesign.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

        radiusOne = keyDesignView.findViewById(R.id.radiusOne);
        radiusTwo = keyDesignView.findViewById(R.id.radiusTwo);
        radiusThree = keyDesignView.findViewById(R.id.radiusThree);
        radiusFour = keyDesignView.findViewById(R.id.radiusFour);
        radiusFive = keyDesignView.findViewById(R.id.radiusFive);

        ivOpacityHundred = keyDesignView.findViewById(R.id.ivOpacityHundred);
        ivOpacitySeventyFive = keyDesignView.findViewById(R.id.ivOpacitySeventyFive);
        ivOpacityFifty = keyDesignView.findViewById(R.id.ivOpacityFifty);
        ivOpacityTwentyFive = keyDesignView.findViewById(R.id.ivOpacityTwentyFive);
        ivOpacityZero = keyDesignView.findViewById(R.id.ivOpacityZero);

        ivStrokeOne = keyDesignView.findViewById(R.id.ivStrokeOne);
        ivStrokeTwo = keyDesignView.findViewById(R.id.ivStrokeTwo);
        ivStrokeThree = keyDesignView.findViewById(R.id.ivStrokeThree);
        ivStrokeFour = keyDesignView.findViewById(R.id.ivStrokeFour);
        ivStrokeFive = keyDesignView.findViewById(R.id.ivStrokeFive);

        ivDone = createKeyboardActivity.findViewById(R.id.ivDone);
        ivShift = createKeyboardActivity.findViewById(R.id.ivShift);
        ivCancel = createKeyboardActivity.findViewById(R.id.ivCancel);

        radiusOne.setOnClickListener(onClickListener);
        radiusTwo.setOnClickListener(onClickListener);
        radiusThree.setOnClickListener(onClickListener);
        radiusFour.setOnClickListener(onClickListener);
        radiusFive.setOnClickListener(onClickListener);

        ivOpacityHundred.setOnClickListener(onClickListener);
        ivOpacitySeventyFive.setOnClickListener(onClickListener);
        ivOpacityFifty.setOnClickListener(onClickListener);
        ivOpacityTwentyFive.setOnClickListener(onClickListener);
        ivOpacityZero.setOnClickListener(onClickListener);

        ivStrokeOne.setOnClickListener(onClickListener);
        ivStrokeTwo.setOnClickListener(onClickListener);
        ivStrokeThree.setOnClickListener(onClickListener);
        ivStrokeFour.setOnClickListener(onClickListener);
        ivStrokeFive.setOnClickListener(onClickListener);

        setRadius();
        getColorFromDatabase();

        return keyDesignView;
    }

    @SuppressWarnings("deprecation")  // TODO: Check if it's okay
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        createKeyboardActivity = (CreateKeyboardActivity) activity;
        context = activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        createKeyboardActivity = null;
        context = null;
    }

    public void setRadius() {
        createKeyboardActivity.setRadius();
    }

    void getColorFromDatabase() {
        int[] colorWallpaperArrayList = GlobalClass.colorsHorizontal;

        FillKeyBgAdapter fillKeyBgAdapter = new FillKeyBgAdapter(context, colorWallpaperArrayList, createKeyboardActivity);
        rvDefaultColorKeyDesign.setAdapter(fillKeyBgAdapter);
        rvDefaultColorKeyDesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRadius();
            }
        });
    }

    public class MyOnClickListener implements View.OnClickListener {

        public void onClick(View v) {
        }
    }
}
