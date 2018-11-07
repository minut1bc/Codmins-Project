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
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.codminskeyboards.universekeyboard.R;
import com.codminskeyboards.universekeyboard.adapter.FillDefaultColorAdapter;
import com.codminskeyboards.universekeyboard.adapter.FillWallpaperColorAdapter;
import com.codminskeyboards.universekeyboard.utils.GlobalClass;
import com.codminskeyboards.universekeyboard.utils.RecyclerItemClickListener;

public class WallpaperFragment extends Fragment {

    FillWallpaperColorAdapter fillWallpaperColorAdapter;
    FillDefaultColorAdapter fillDefaultColorAdapter;

    RecyclerView rvDefaultColor;
    ImageView backgroundImageView;
    GridView gvColor;
    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View wallpaperFragmentView = inflater.inflate(R.layout.wallpaper_fragment, container, false);

        GlobalClass globalClass = new GlobalClass(context);

        rvDefaultColor = wallpaperFragmentView.findViewById(R.id.rvDefaultColor);
        rvDefaultColor.setNestedScrollingEnabled(false);
        rvDefaultColor.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

        backgroundImageView = getActivity().findViewById(R.id.backgroundImageView);
        gvColor = wallpaperFragmentView.findViewById(R.id.gvColor);

        setColorGridView();
        getColorFromDatabase();

        return wallpaperFragmentView;
    }

    @SuppressWarnings("deprecation")  // TODO: Check if it's okay
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        context = null;
    }

    private void setColorGridView() {
        int[] colorWallpaperArrayList = GlobalClass.thArray;
        fillWallpaperColorAdapter = new FillWallpaperColorAdapter(context, colorWallpaperArrayList);
        gvColor.setAdapter(fillWallpaperColorAdapter);

        gvColor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, final int position, long id) {

                try {
                    backgroundImageView.setImageResource(GlobalClass.thumbArray[position]);
                } catch (Exception ignored) {
                }

                GlobalClass.selectwallpaper = position;
                GlobalClass.selview = 0;
                GlobalClass.tempKeyboardBgImage = GlobalClass.thumbArray[position];
                fillWallpaperColorAdapter.notifyDataSetChanged();
                fillDefaultColorAdapter.notifyDataSetChanged();
                GlobalClass.checkStartAd();
            }
        });
    }

    private void getColorFromDatabase() {
        int[] colorWallpaperArrayList = GlobalClass.colorsHorizontal;
        fillDefaultColorAdapter = new FillDefaultColorAdapter(context, colorWallpaperArrayList);
        rvDefaultColor.setAdapter(fillDefaultColorAdapter);

        rvDefaultColor.addOnItemTouchListener(
                new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        backgroundImageView.setImageResource(GlobalClass.colorsHorizontal[position]);
                        GlobalClass.selectcolor = position;
                        GlobalClass.selview = 1;
                        GlobalClass.tempKeyboardBgImage = GlobalClass.colorsHorizontal[position];
                        fillDefaultColorAdapter.notifyDataSetChanged();
                        fillWallpaperColorAdapter.notifyDataSetChanged();
                        GlobalClass.checkStartAd();
                    }
                })
        );
    }


}
