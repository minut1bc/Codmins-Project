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
import com.codminskeyboards.universekeyboard.model.NewSoundData;
import com.codminskeyboards.universekeyboard.utils.GlobalClass;

import java.util.ArrayList;

public class SoundAdapter extends RecyclerView.Adapter<SoundAdapter.SoundViewHolder> {
    private Context context;
    private ArrayList<NewSoundData> soundEffectArrayList;

    public SoundAdapter(Context context, ArrayList<NewSoundData> soundEffectArrayList) {
        super();
        this.context = context;
        this.soundEffectArrayList = soundEffectArrayList;
    }

    @NonNull
    @Override
    public SoundViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_sound_item, viewGroup, false);
        return new SoundViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SoundViewHolder holder, int position) {
        if (GlobalClass.getPreferencesBool(context, GlobalClass.key_isSoundLock, true)) {
            if (position > 20) {
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

        if (position == 0)
            holder.muteImageView.setVisibility(View.VISIBLE);

        if (position == GlobalClass.soundPosition)
            holder.outlineImageView.setVisibility(View.VISIBLE);
        else
            holder.outlineImageView.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return soundEffectArrayList.size();
    }

    class SoundViewHolder extends RecyclerView.ViewHolder {
        ImageView soundImageView;
        ImageView muteImageView;
        ImageView outlineImageView;
        ImageView lockImageView;

        SoundViewHolder(View view) {
            super(view);
            soundImageView = view.findViewById(R.id.soundImageView);
            muteImageView = view.findViewById(R.id.muteImageView);
            outlineImageView = view.findViewById(R.id.outlineImageView);
            lockImageView = view.findViewById(R.id.lockImageView);
        }
    }
}