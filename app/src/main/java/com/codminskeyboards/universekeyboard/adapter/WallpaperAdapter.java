package com.codminskeyboards.universekeyboard.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.codminskeyboards.universekeyboard.R;
import com.codminskeyboards.universekeyboard.activity.PremiumStoreActivity;
import com.codminskeyboards.universekeyboard.utils.GlobalClass;

public class WallpaperAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    private Context context;
    private int[] wallpaperArray;

    public WallpaperAdapter(Context context, int[] wallpaperArray) {
        super();
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.wallpaperArray = wallpaperArray;
    }

    @Override
    public int getCount() {
        return wallpaperArray.length;
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

        if (convertView == null)
            convertView = inflater.inflate(R.layout.row_wallpaper_color_item, parent, false);

        holder.ivColorItem = convertView.findViewById(R.id.ivColorItem);
        holder.flBg = convertView.findViewById(R.id.flBg);
        holder.ivLock = convertView.findViewById(R.id.ivLock);

        holder.ivColorItem.setImageResource(wallpaperArray[position]);

        if (position == GlobalClass.wallpaperPosition && GlobalClass.drawableOrColor == 0)
            holder.flBg.setVisibility(View.VISIBLE);
        else
            holder.flBg.setVisibility(View.GONE);

        if (GlobalClass.getPreferencesBool(context, GlobalClass.key_isWallPaperLock, true)) {
            if (position > 22) {
                holder.ivLock.setVisibility(View.VISIBLE);
                holder.ivLock.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, PremiumStoreActivity.class);
                        context.startActivity(intent);
                    }
                });
            } else
                holder.ivLock.setVisibility(View.GONE);
        } else
            holder.ivLock.setVisibility(View.GONE);

        return convertView;
    }

    private static class ViewHolder {
        ImageView ivColorItem;
        FrameLayout flBg;
        ImageView ivLock;
    }
}