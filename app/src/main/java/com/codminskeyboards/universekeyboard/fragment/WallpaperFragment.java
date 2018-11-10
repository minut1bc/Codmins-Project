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
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.codminskeyboards.universekeyboard.R;
import com.codminskeyboards.universekeyboard.activity.CreateKeyboardActivity;
import com.codminskeyboards.universekeyboard.adapter.BackgroundColorAdapter;
import com.codminskeyboards.universekeyboard.adapter.WallpaperAdapter;
import com.codminskeyboards.universekeyboard.utils.GlobalClass;
import com.codminskeyboards.universekeyboard.utils.RecyclerItemClickListener;

public class WallpaperFragment extends Fragment {

    WallpaperAdapter wallpaperAdapter;
    BackgroundColorAdapter backgroundColorAdapter;

    RecyclerView backgroundColorRecyclerView;
    ImageView backgroundImageView;
    GridView wallpaperGridView;
    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View wallpaperFragmentView = inflater.inflate(R.layout.wallpaper_fragment, container, false);

        backgroundColorRecyclerView = wallpaperFragmentView.findViewById(R.id.backgroundColorRecyclerView);
        backgroundColorRecyclerView.setNestedScrollingEnabled(false);
        backgroundColorRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

        backgroundImageView = ((CreateKeyboardActivity) context).findViewById(R.id.backgroundImageView);
        wallpaperGridView = wallpaperFragmentView.findViewById(R.id.wallpaperGridView);

        setWallpaperGridView();
        setColorRecyclerView();

        return wallpaperFragmentView;
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

    private void setWallpaperGridView() {
        wallpaperAdapter = new WallpaperAdapter(context, GlobalClass.wallpaperPreviewArray);
        wallpaperGridView.setAdapter(wallpaperAdapter);

        wallpaperGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, final int position, long id) {
                backgroundImageView.setImageResource(GlobalClass.wallpaperArray[position]);
                GlobalClass.wallpaperPosition = position;
                GlobalClass.drawableOrColor = 0;
                GlobalClass.background = GlobalClass.wallpaperArray[position];
                wallpaperAdapter.notifyDataSetChanged();
                backgroundColorAdapter.notifyDataSetChanged();
                GlobalClass.checkStartAd();
            }
        });
    }

    private void setColorRecyclerView() {
        backgroundColorAdapter = new BackgroundColorAdapter(context, GlobalClass.colorsArray);
        backgroundColorRecyclerView.setAdapter(backgroundColorAdapter);

        backgroundColorRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        backgroundImageView.setImageResource(GlobalClass.colorsArray[position]);
                        GlobalClass.colorPosition = position;
                        GlobalClass.drawableOrColor = 1;
                        GlobalClass.background = GlobalClass.colorsArray[position];
                        backgroundColorAdapter.notifyDataSetChanged();
                        wallpaperAdapter.notifyDataSetChanged();
                        GlobalClass.checkStartAd();
                    }
                })
        );
    }
}
