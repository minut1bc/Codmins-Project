package com.ibl.apps.myphotokeyboard.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.ibl.apps.myphotokeyboard.R;
import com.ibl.apps.myphotokeyboard.subscriptionmenu.PackageActivity;
import com.ibl.apps.myphotokeyboard.utils.GlobalClass;

/**
 * Created by iblinfotech on 03/05/17.
 */

public class FillWallpaperTextualAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    Activity activity;
    int[] colorWallpaperArrayList;
    private ViewHolder holder;


    public FillWallpaperTextualAdapter(Activity activity, int[] colorWallpaperArrayList) {
        super();
        this.activity = activity;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.colorWallpaperArrayList = colorWallpaperArrayList;
    }

    @Override
    public int getCount() {
        return colorWallpaperArrayList.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        holder = new ViewHolder();
        convertView = inflater.inflate(R.layout.row_wallpaper_color_item, null);

        holder.ivColorItem = (ImageView) convertView.findViewById(R.id.ivColorItem);
        holder.flBg = (FrameLayout) convertView.findViewById(R.id.flBg);
        holder.ivLock = (ImageView) convertView.findViewById(R.id.ivLock);


        holder.ivColorItem.setImageResource(colorWallpaperArrayList[position]);

        if (GlobalClass.getPrefrenceBoolean(activity, GlobalClass.key_isWallPaperLock, true)) {
            if (position > 25) {
                holder.ivLock.setVisibility(View.VISIBLE);
                holder.ivLock.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(activity, PackageActivity.class);
                        activity.startActivity(i);
                    }

                });

            } else {
                holder.ivLock.setVisibility(View.GONE);
            }
        } else {
            holder.ivLock.setVisibility(View.GONE);
        }

        if (position == GlobalClass.selecttextwallpaper && GlobalClass.selview == 3) {
            GlobalClass.tempIsColor = "no";
            holder.flBg.setVisibility(View.VISIBLE);
        } else {
            holder.flBg.setVisibility(View.GONE);
        }

        return convertView;
    }

    private static class ViewHolder {
        ImageView ivColorItem;
        FrameLayout flBg;
        ImageView ivLock;
    }
}
