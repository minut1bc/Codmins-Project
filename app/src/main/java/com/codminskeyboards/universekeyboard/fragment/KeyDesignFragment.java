package com.codminskeyboards.universekeyboard.fragment;

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

import com.codminskeyboards.universekeyboard.R;
import com.codminskeyboards.universekeyboard.activity.CreateKeyboardActivity;
import com.codminskeyboards.universekeyboard.adapter.KeyColorAdapter;
import com.codminskeyboards.universekeyboard.utils.GlobalClass;
import com.codminskeyboards.universekeyboard.utils.RecyclerItemClickListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class KeyDesignFragment extends Fragment {

    Context context;

    RecyclerView keyColorRecyclerView;
    KeyColorAdapter keyColorAdapter;

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

    CreateKeyboardActivity createKeyboardActivity;

    View.OnClickListener onClickListener = new View.OnClickListener() {
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
                    GlobalClass.keyStroke = 1;
                    setRadius();
                    break;

                case R.id.ivStrokeTwo:
                    ivStrokeOne.setBorderWidth(0);
                    ivStrokeTwo.setBorderWidth(5);
                    ivStrokeThree.setBorderWidth(0);
                    ivStrokeFive.setBorderWidth(0);
                    ivStrokeFour.setBorderWidth(0);
                    ivStrokeTwo.setBorderColor(getResources().getColor(R.color.pink));
                    GlobalClass.keyStroke = 2;
                    setRadius();
                    break;

                case R.id.ivStrokeThree:
                    ivStrokeOne.setBorderWidth(0);
                    ivStrokeTwo.setBorderWidth(0);
                    ivStrokeThree.setBorderWidth(5);
                    ivStrokeFour.setBorderWidth(0);
                    ivStrokeFive.setBorderWidth(0);
                    ivStrokeThree.setBorderColor(getResources().getColor(R.color.pink));
                    GlobalClass.keyStroke = 3;
                    setRadius();
                    break;

                case R.id.ivStrokeFour:
                    ivStrokeOne.setBorderWidth(0);
                    ivStrokeTwo.setBorderWidth(0);
                    ivStrokeThree.setBorderWidth(0);
                    ivStrokeFour.setBorderWidth(5);
                    ivStrokeFive.setBorderWidth(0);
                    ivStrokeFour.setBorderColor(getResources().getColor(R.color.pink));
                    GlobalClass.keyStroke = 4;
                    setRadius();
                    break;

                case R.id.ivStrokeFive:
                    ivStrokeOne.setBorderWidth(0);
                    ivStrokeTwo.setBorderWidth(0);
                    ivStrokeThree.setBorderWidth(0);
                    ivStrokeFour.setBorderWidth(0);
                    ivStrokeFive.setBorderWidth(5);
                    ivStrokeFive.setBorderColor(getResources().getColor(R.color.pink));
                    GlobalClass.keyStroke = 5;
                    setRadius();
                    break;

                case R.id.ivOpacityHundred:
                    ivOpacityHundred.setBorderWidth(5);
                    ivOpacitySeventyFive.setBorderWidth(0);
                    ivOpacityFifty.setBorderWidth(0);
                    ivOpacityTwentyFive.setBorderWidth(0);
                    ivOpacityZero.setBorderWidth(0);
                    ivOpacityHundred.setBorderColor(getResources().getColor(R.color.pink));
                    GlobalClass.keyOpacity = 255;
                    setRadius();
                    break;

                case R.id.ivOpacitySeventyFive:
                    ivOpacityHundred.setBorderWidth(0);
                    ivOpacitySeventyFive.setBorderWidth(5);
                    ivOpacityFifty.setBorderWidth(0);
                    ivOpacityTwentyFive.setBorderWidth(0);
                    ivOpacityZero.setBorderWidth(0);
                    ivOpacitySeventyFive.setBorderColor(getResources().getColor(R.color.pink));
                    GlobalClass.keyOpacity = 192;
                    setRadius();
                    break;

                case R.id.ivOpacityFifty:
                    ivOpacityHundred.setBorderWidth(0);
                    ivOpacitySeventyFive.setBorderWidth(0);
                    ivOpacityFifty.setBorderWidth(5);
                    ivOpacityTwentyFive.setBorderWidth(0);
                    ivOpacityZero.setBorderWidth(0);
                    ivOpacityFifty.setBorderColor(getResources().getColor(R.color.pink));
                    GlobalClass.keyOpacity = 128;
                    setRadius();
                    break;

                case R.id.ivOpacityTwentyFive:
                    ivOpacityHundred.setBorderWidth(0);
                    ivOpacitySeventyFive.setBorderWidth(0);
                    ivOpacityFifty.setBorderWidth(0);
                    ivOpacityTwentyFive.setBorderWidth(5);
                    ivOpacityZero.setBorderWidth(0);
                    ivOpacityTwentyFive.setBorderColor(getResources().getColor(R.color.pink));
                    GlobalClass.keyOpacity = 64;
                    setRadius();
                    break;

                case R.id.ivOpacityZero:
                    ivOpacityHundred.setBorderWidth(0);
                    ivOpacitySeventyFive.setBorderWidth(0);
                    ivOpacityFifty.setBorderWidth(0);
                    ivOpacityTwentyFive.setBorderWidth(0);
                    ivOpacityZero.setBorderWidth(5);
                    ivOpacityZero.setBorderColor(getResources().getColor(R.color.pink));
                    GlobalClass.keyOpacity = 0;
                    setRadius();
                    break;

                case R.id.radiusOne:
                    radiusOne.setBorderWidth(5);
                    radiusTwo.setBorderWidth(0);
                    radiusThree.setBorderWidth(0);
                    radiusFour.setBorderWidth(0);
                    radiusFive.setBorderWidth(0);
                    radiusOne.setBorderColor(getResources().getColor(R.color.pink));
                    GlobalClass.keyRadius = 0;
                    setRadius();
                    break;

                case R.id.radiusTwo:
                    radiusOne.setBorderWidth(0);
                    radiusTwo.setBorderWidth(5);
                    radiusThree.setBorderWidth(0);
                    radiusFour.setBorderWidth(0);
                    radiusFive.setBorderWidth(0);
                    radiusTwo.setBorderColor(getResources().getColor(R.color.pink));
                    GlobalClass.keyRadius = 9;
                    setRadius();
                    break;

                case R.id.radiusThree:
                    radiusOne.setBorderWidth(0);
                    radiusTwo.setBorderWidth(0);
                    radiusThree.setBorderWidth(5);
                    radiusFour.setBorderWidth(0);
                    radiusFive.setBorderWidth(0);
                    radiusThree.setBorderColor(getResources().getColor(R.color.pink));
                    GlobalClass.keyRadius = 18;
                    setRadius();
                    break;

                case R.id.radiusFour:
                    radiusOne.setBorderWidth(0);
                    radiusTwo.setBorderWidth(0);
                    radiusThree.setBorderWidth(0);
                    radiusFour.setBorderWidth(5);
                    radiusFive.setBorderWidth(0);
                    radiusFour.setBorderColor(getResources().getColor(R.color.pink));
                    GlobalClass.keyRadius = 25;
                    setRadius();
                    break;

                case R.id.radiusFive:
                    radiusOne.setBorderWidth(0);
                    radiusTwo.setBorderWidth(0);
                    radiusThree.setBorderWidth(0);
                    radiusFour.setBorderWidth(0);
                    radiusFive.setBorderWidth(5);
                    radiusFive.setBorderColor(getResources().getColor(R.color.pink));
                    GlobalClass.setPreferencesInt(context, GlobalClass.KEY_RADIUS, 11);
                    GlobalClass.keyRadius = 34;
                    setRadius();
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View keyDesignView = inflater.inflate(R.layout.key_design_fragment, container, false);

        keyColorRecyclerView = keyDesignView.findViewById(R.id.keyColorRecyclerView);
        keyColorRecyclerView.setNestedScrollingEnabled(false);
        keyColorRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        createKeyboardActivity = (CreateKeyboardActivity) context;
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        createKeyboardActivity = null;
        context = null;
    }

    public void setRadius() {
        switch (GlobalClass.keyRadius) {
            case 0:
                radiusOne.setBorderWidth(5);
                radiusOne.setBorderColor(getResources().getColor(R.color.pink));
                break;
            case 9:
                radiusTwo.setBorderWidth(5);
                radiusTwo.setBorderColor(getResources().getColor(R.color.pink));
                break;
            case 18:
                radiusThree.setBorderWidth(5);
                radiusThree.setBorderColor(getResources().getColor(R.color.pink));
                break;
            case 25:
                radiusFour.setBorderWidth(5);
                radiusFour.setBorderColor(getResources().getColor(R.color.pink));
                break;
            case 34:
                radiusFive.setBorderWidth(5);
                radiusFive.setBorderColor(getResources().getColor(R.color.pink));
                break;
        }

        switch (GlobalClass.keyStroke) {
            case 1:
                ivStrokeOne.setBorderWidth(5);
                ivStrokeOne.setBorderColor(getResources().getColor(R.color.pink));
                break;
            case 2:
                ivStrokeTwo.setBorderWidth(5);
                ivStrokeTwo.setBorderColor(getResources().getColor(R.color.pink));
                break;
            case 3:
                ivStrokeThree.setBorderWidth(5);
                ivStrokeThree.setBorderColor(getResources().getColor(R.color.pink));
                break;
            case 4:
                ivStrokeFour.setBorderWidth(5);
                ivStrokeFour.setBorderColor(getResources().getColor(R.color.pink));
                break;
            case 5:
                ivStrokeFive.setBorderWidth(5);
                ivStrokeFive.setBorderColor(getResources().getColor(R.color.pink));
                break;
        }

        switch (GlobalClass.keyOpacity) {
            case 255:
                ivOpacityHundred.setBorderWidth(5);
                ivOpacityHundred.setBorderColor(getResources().getColor(R.color.pink));
                break;
            case 192:
                ivOpacitySeventyFive.setBorderWidth(5);
                ivOpacitySeventyFive.setBorderColor(getResources().getColor(R.color.pink));
                break;
            case 128:
                ivOpacityFifty.setBorderWidth(5);
                ivOpacityFifty.setBorderColor(getResources().getColor(R.color.pink));
                break;
            case 64:
                ivOpacityTwentyFive.setBorderWidth(5);
                ivOpacityTwentyFive.setBorderColor(getResources().getColor(R.color.pink));
                break;
            case 0:
                ivOpacityZero.setBorderWidth(5);
                ivOpacityZero.setBorderColor(getResources().getColor(R.color.pink));
                break;
        }

        createKeyboardActivity.redrawKeyboard();
    }

    void getColorFromDatabase() {
        keyColorAdapter = new KeyColorAdapter(context, GlobalClass.colorsArray);
        keyColorRecyclerView.setAdapter(keyColorAdapter);
        keyColorRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                GlobalClass.selectbgcolor = position;
                GlobalClass.keyColor = context.getResources().getColor(GlobalClass.colorsArray[position]);
                if (createKeyboardActivity != null)
                    createKeyboardActivity.redrawKeyboard();

                keyColorAdapter.notifyDataSetChanged();
                GlobalClass.checkStartAd();
            }
        }));
    }
}
