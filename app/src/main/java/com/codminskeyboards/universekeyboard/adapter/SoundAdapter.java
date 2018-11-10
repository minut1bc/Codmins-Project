package com.codminskeyboards.universekeyboard.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.codminskeyboards.universekeyboard.R;
import com.codminskeyboards.universekeyboard.activity.PremiumStoreActivity;
import com.codminskeyboards.universekeyboard.model.NewSoundData;
import com.codminskeyboards.universekeyboard.utils.GlobalClass;

import java.util.ArrayList;

public class SoundAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    private Context context;
    private ArrayList<NewSoundData> soundEffectArrayList;

    public SoundAdapter(Context context, ArrayList<NewSoundData> soundEffectArrayList) {
        super();
        this.context = context;
        this.soundEffectArrayList = soundEffectArrayList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return soundEffectArrayList.size();
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
            convertView = inflater.inflate(R.layout.row_sound_effect_color_item, parent, false);

        holder.soundImageView = convertView.findViewById(R.id.soundImageView);
        holder.muteImageView = convertView.findViewById(R.id.muteImageView);
        holder.circleOutlineImageView = convertView.findViewById(R.id.outlineImageView);
        holder.lockImageView = convertView.findViewById(R.id.lockImageView);

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
            holder.circleOutlineImageView.setVisibility(View.VISIBLE);
        else
            holder.circleOutlineImageView.setVisibility(View.GONE);

        return convertView;
    }

    private static class ViewHolder {
        ImageView soundImageView;
        ImageView muteImageView;
        ImageView circleOutlineImageView;
        ImageView lockImageView;
    }

}
