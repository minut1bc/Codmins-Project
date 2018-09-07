package com.ibl.apps.myphotokeyboard.adapter;

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

import de.hdodenhof.circleimageview.CircleImageView;

public class FillWallpaperColorAdapater extends BaseAdapter {
    private final LayoutInflater inflater;
    Context context;
    private ViewHolder holder;
    int[] colorWallpaperArrayList;

    public FillWallpaperColorAdapater(Context context, int[] colorWallpaperArrayList) {
        super();
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        holder.ivColorItem = (CircleImageView) convertView.findViewById(R.id.ivColorItem);
        holder.flBg = (FrameLayout) convertView.findViewById(R.id.flBg);
        holder.ivLock = (ImageView) convertView.findViewById(R.id.ivLock);

        holder.ivColorItem.setImageResource(colorWallpaperArrayList[position]);

        if (position == GlobalClass.selectwallpaper && GlobalClass.selview == 2) {
            GlobalClass.tempIsColor = "no";
            holder.flBg.setVisibility(View.VISIBLE);
        } else {
            holder.flBg.setVisibility(View.GONE);
        }

        if (GlobalClass.getPrefrenceBoolean(context, GlobalClass.key_isWallPaperLock, true)) {

            if (position > 22) {
                holder.ivLock.setVisibility(View.VISIBLE);
                holder.ivLock.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(context, PackageActivity.class);
                        context.startActivity(i);
                    }
                });
            } else {
                holder.ivLock.setVisibility(View.GONE);
            }


        } else {
            holder.ivLock.setVisibility(View.GONE);
        }


        return convertView;
    }


    private static class ViewHolder {
        CircleImageView ivColorItem;
        FrameLayout flBg;
        ImageView ivLock;
    }
}
