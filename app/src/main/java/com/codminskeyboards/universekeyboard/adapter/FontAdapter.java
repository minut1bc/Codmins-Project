package com.codminskeyboards.universekeyboard.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codminskeyboards.universekeyboard.R;
import com.codminskeyboards.universekeyboard.activity.PremiumStoreActivity;
import com.codminskeyboards.universekeyboard.utils.GlobalClass;

public class FontAdapter extends RecyclerView.Adapter<FontAdapter.FontViewHolder> {
    private Context context;
    private Typeface[] fontsArray;

    public FontAdapter(Context context, Typeface[] fontsArray) {
        super();
        this.context = context;
        this.fontsArray = fontsArray;
    }

    @NonNull
    @Override
    public FontViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_font_item, viewGroup, false);
        return new FontViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return fontsArray.length;
    }

    @Override
    public void onBindViewHolder(@NonNull FontViewHolder holder, int position) {

        if (GlobalClass.getPreferencesBool(context, GlobalClass.key_isFontLock, true)) {
            if (position > 33) {
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

        if (position == GlobalClass.fontPosition)
            holder.outlineImageView.setVisibility(View.VISIBLE);
        else
            holder.outlineImageView.setVisibility(View.GONE);

        holder.fontTextView.setTypeface(fontsArray[position]);
    }

    class FontViewHolder extends RecyclerView.ViewHolder {
        TextView fontTextView;
        ImageView outlineImageView;
        ImageView lockImageView;

        FontViewHolder(View view) {
            super(view);
            fontTextView = view.findViewById(R.id.fontTextView);
            outlineImageView = view.findViewById(R.id.outlineImageView);
            lockImageView = view.findViewById(R.id.lockImageView);
        }
    }
}
