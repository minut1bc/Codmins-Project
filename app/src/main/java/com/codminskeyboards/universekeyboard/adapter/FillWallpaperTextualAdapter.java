package com.codminskeyboards.universekeyboard.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.codminskeyboards.universekeyboard.R;
import com.codminskeyboards.universekeyboard.subscriptionmenu.PackageActivity;
import com.codminskeyboards.universekeyboard.utils.GlobalClass;

public class FillWallpaperTextualAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    private Activity activity;
    private int[] colorWallpaperArrayList;

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
        ViewHolder holder = new ViewHolder();
        convertView = inflater.inflate(R.layout.row_wallpaper_color_item, null);

        holder.ivColorItem = convertView.findViewById(R.id.ivColorItem);
        holder.flBg = convertView.findViewById(R.id.flBg);
        holder.ivLock = convertView.findViewById(R.id.ivLock);

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
