package com.codminskeyboards.universekeyboard.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.codminskeyboards.universekeyboard.R;
import com.codminskeyboards.universekeyboard.activity.PremiumStoreActivity;
import com.codminskeyboards.universekeyboard.utils.GlobalClass;

import de.hdodenhof.circleimageview.CircleImageView;

public class BackgroundColorAdapter extends RecyclerView.Adapter<BackgroundColorAdapter.ColorViewHolder> {
    private Context context;
    private int[] colorArray;

    public BackgroundColorAdapter(Context context, int[] colorArray) {
        super();
        this.context = context;
        this.colorArray = colorArray;
    }

    @NonNull
    @Override
    public ColorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_keyborad_bg_color_item, viewGroup, false);
        return new ColorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ColorViewHolder holder, final int position) {
        holder.ivColorItem.setImageResource(colorArray[position]);

        if (position == GlobalClass.colorPosition && GlobalClass.drawableOrColor == 1)
            holder.flBg.setVisibility(View.VISIBLE);
        else
            holder.flBg.setVisibility(View.GONE);

        if (GlobalClass.getPreferencesBool(context, GlobalClass.key_isColorLock, true)) {
            if (position > 26) {
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
    }

    @Override
    public int getItemCount() {
        return colorArray.length;
    }

    class ColorViewHolder extends RecyclerView.ViewHolder {
        CircleImageView ivColorItem;
        FrameLayout flBg;
        CircleImageView ivLock;

        ColorViewHolder(View view) {
            super(view);
            ivColorItem = view.findViewById(R.id.ivColorItem);
            ivLock = view.findViewById(R.id.ivLock);
            flBg = view.findViewById(R.id.flBg);
        }
    }
}