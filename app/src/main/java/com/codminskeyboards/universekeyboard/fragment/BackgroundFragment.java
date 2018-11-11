package com.codminskeyboards.universekeyboard.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.codminskeyboards.universekeyboard.R;
import com.codminskeyboards.universekeyboard.activity.CreateKeyboardActivity;
import com.codminskeyboards.universekeyboard.adapter.BackgroundAdapter;
import com.codminskeyboards.universekeyboard.adapter.BackgroundColorAdapter;
import com.codminskeyboards.universekeyboard.utils.GlobalClass;
import com.codminskeyboards.universekeyboard.utils.RecyclerItemClickListener;

public class BackgroundFragment extends Fragment {

    BackgroundAdapter backgroundAdapter;
    BackgroundColorAdapter backgroundColorAdapter;

    RecyclerView backgroundColorRecyclerView;
    ImageView backgroundImageView;
    RecyclerView backgroundRecyclerView;
    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View backgroundFragmentView = inflater.inflate(R.layout.background_fragment, container, false);

        backgroundImageView = ((CreateKeyboardActivity) context).findViewById(R.id.backgroundImageView);

        backgroundRecyclerView = backgroundFragmentView.findViewById(R.id.backgroundRecyclerView);
        backgroundColorRecyclerView = backgroundFragmentView.findViewById(R.id.backgroundColorRecyclerView);
        backgroundAdapter = new BackgroundAdapter(context, GlobalClass.backgroundPreviewArray);
        backgroundColorAdapter = new BackgroundColorAdapter(context, GlobalClass.colorsArray);

        backgroundColorRecyclerView.setHasFixedSize(true);
        backgroundColorRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        backgroundColorRecyclerView.setAdapter(backgroundColorAdapter);

        backgroundColorRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        backgroundImageView.setImageResource(GlobalClass.colorsArray[position]);
                        GlobalClass.colorPosition = position;
                        GlobalClass.drawableOrColor = 1;
                        GlobalClass.keyboardBackground = GlobalClass.colorsArray[position];
                        backgroundColorAdapter.notifyDataSetChanged();
                        backgroundAdapter.notifyDataSetChanged();
                        GlobalClass.checkStartAd();
                    }
                })
        );

        backgroundRecyclerView.setHasFixedSize(true);
        backgroundRecyclerView.setLayoutManager(new GridLayoutManager(context, GlobalClass.calculateNoOfColumns(context)));
        backgroundRecyclerView.setAdapter(backgroundAdapter);

        backgroundRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                backgroundImageView.setImageResource(GlobalClass.backgroundArray[position]);
                GlobalClass.backgroundPosition = position;
                GlobalClass.drawableOrColor = 0;
                GlobalClass.keyboardBackground = GlobalClass.backgroundArray[position];
                backgroundAdapter.notifyDataSetChanged();
                backgroundColorAdapter.notifyDataSetChanged();
                GlobalClass.checkStartAd();
            }
        }));

        return backgroundFragmentView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        context = null;
    }
}