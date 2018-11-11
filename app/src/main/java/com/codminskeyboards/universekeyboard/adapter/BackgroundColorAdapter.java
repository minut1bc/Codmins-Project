package com.codminskeyboards.universekeyboard.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
        View view = LayoutInflater.from(context).inflate(R.layout.row_background_color_item, viewGroup, false);
        return new ColorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ColorViewHolder holder, final int position) {
        holder.colorImageView.setImageResource(colorArray[position]);

        if (position == GlobalClass.colorPosition && GlobalClass.drawableOrColor == 1)
            holder.outlineImageView.setVisibility(View.VISIBLE);
        else
            holder.outlineImageView.setVisibility(View.GONE);

        if (GlobalClass.getPreferencesBool(context, GlobalClass.key_isColorLock, true)) {
            if (position > 26) {
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
    }

    @Override
    public int getItemCount() {
        return colorArray.length;
    }

    class ColorViewHolder extends RecyclerView.ViewHolder {
        CircleImageView colorImageView;
        ImageView outlineImageView;
        ImageView lockImageView;

        ColorViewHolder(View view) {
            super(view);
            colorImageView = view.findViewById(R.id.colorImageView);
            lockImageView = view.findViewById(R.id.lockImageView);
            outlineImageView = view.findViewById(R.id.outlineImageView);
        }
    }
}