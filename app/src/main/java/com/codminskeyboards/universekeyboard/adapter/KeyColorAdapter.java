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
import com.codminskeyboards.universekeyboard.activity.CreateKeyboardActivity;
import com.codminskeyboards.universekeyboard.activity.PremiumStoreActivity;
import com.codminskeyboards.universekeyboard.utils.GlobalClass;

import de.hdodenhof.circleimageview.CircleImageView;

public class KeyColorAdapter extends RecyclerView.Adapter<KeyColorAdapter.ViewHolder> {
    private Context context;
    private int[] colorFreeArray;

    public KeyColorAdapter(Context context, int[] colorFreeArray) {
        super();
        this.context = context;
        this.colorFreeArray = colorFreeArray;
    }

    @NonNull
    @Override
    public KeyColorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_key_color_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final KeyColorAdapter.ViewHolder holder, int position) {
        holder.colorImageView.setImageResource(colorFreeArray[position]);

        if (position == CreateKeyboardActivity.keyboardData.getKeyColorPosition())
            holder.circleOutlineImageView.setVisibility(View.VISIBLE);
        else
            holder.circleOutlineImageView.setVisibility(View.GONE);

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
        return colorFreeArray.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView colorImageView;
        ImageView circleOutlineImageView;
        ImageView lockImageView;

        ViewHolder(final View itemView) {
            super(itemView);
            colorImageView = itemView.findViewById(R.id.colorImageView);
            circleOutlineImageView = itemView.findViewById(R.id.outlineImageView);
            lockImageView = itemView.findViewById(R.id.lockImageView);
        }
    }
}