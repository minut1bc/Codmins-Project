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

import de.hdodenhof.circleimageview.CircleImageView;

public class BackgroundAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    private Context context;
    private int[] backgroundArray;

    public BackgroundAdapter(Context context, int[] backgroundArray) {
        super();
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.backgroundArray = backgroundArray;
    }

    @Override
    public int getCount() {
        return backgroundArray.length;
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
            convertView = inflater.inflate(R.layout.row_background_item, parent, false);

        holder.backgroundItemImageView = convertView.findViewById(R.id.backgroundItemImageView);
        holder.outlineImageView = convertView.findViewById(R.id.outlineImageView);
        holder.lockImageView = convertView.findViewById(R.id.lockImageView);

        holder.backgroundItemImageView.setImageResource(backgroundArray[position]);

        if (position == GlobalClass.backgroundPosition && GlobalClass.drawableOrColor == 0)
            holder.outlineImageView.setVisibility(View.VISIBLE);
        else
            holder.outlineImageView.setVisibility(View.GONE);

        if (GlobalClass.getPreferencesBool(context, GlobalClass.key_isWallPaperLock, true)) {
            if (position > 22) {
                holder.lockImageView.setVisibility(View.VISIBLE);
                holder.lockImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, PremiumStoreActivity.class);
                        context.startActivity(intent);
                    }
                });
            } else
                holder.lockImageView.setVisibility(View.GONE);
        } else
            holder.lockImageView.setVisibility(View.GONE);

        return convertView;
    }

    private static class ViewHolder {
        CircleImageView backgroundItemImageView;
        FrameLayout outlineImageView;
        ImageView lockImageView;
    }
}