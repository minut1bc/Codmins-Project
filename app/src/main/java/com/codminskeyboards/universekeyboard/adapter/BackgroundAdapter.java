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

public class BackgroundAdapter extends RecyclerView.Adapter<BackgroundAdapter.BackgroundViewHolder> {
    private Context context;
    private int[] backgroundArray;

    public BackgroundAdapter(Context context, int[] backgroundArray) {
        super();
        this.context = context;
        this.backgroundArray = backgroundArray;
    }

    @NonNull
    @Override
    public BackgroundViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_background_item, viewGroup, false);
        return new BackgroundViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BackgroundViewHolder holder, int position) {
        holder.backgroundItemImageView.setImageResource(backgroundArray[position]);

        if (position == GlobalClass.backgroundPosition && GlobalClass.backgroundIsDrawable)
            holder.outlineImageView.setVisibility(View.VISIBLE);
        else
            holder.outlineImageView.setVisibility(View.GONE);

        if (GlobalClass.getPreferencesBool(context, GlobalClass.key_isBackgroundLock, true)) {
            if (position > 22) {
                holder.lockImageView.setVisibility(View.VISIBLE);
                holder.lockImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
        return backgroundArray.length;
    }

    class BackgroundViewHolder extends RecyclerView.ViewHolder {
        CircleImageView backgroundItemImageView;
        ImageView outlineImageView;
        ImageView lockImageView;

        BackgroundViewHolder(View view) {
            super(view);
            backgroundItemImageView = view.findViewById(R.id.backgroundItemImageView);
            outlineImageView = view.findViewById(R.id.outlineImageView);
            lockImageView = view.findViewById(R.id.lockImageView);
        }
    }
}