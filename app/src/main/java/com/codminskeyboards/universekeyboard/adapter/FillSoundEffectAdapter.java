package com.codminskeyboards.universekeyboard.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.codminskeyboards.universekeyboard.R;
import com.codminskeyboards.universekeyboard.activity.PackageActivity;
import com.codminskeyboards.universekeyboard.model.NewSoundData;
import com.codminskeyboards.universekeyboard.utils.GlobalClass;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FillSoundEffectAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    private Context context;
    private ArrayList<NewSoundData> soundEffectArrayList;

    public FillSoundEffectAdapter(Activity context, ArrayList<NewSoundData> soundEffectArrayList) {
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

        holder.ivSoundEffectItem = convertView.findViewById(R.id.ivSoundEffectItem);
        holder.ivSoundEffectItem1 = convertView.findViewById(R.id.ivSoundEffectItem1);
        holder.flBg = convertView.findViewById(R.id.flBg);
        holder.ivLock = convertView.findViewById(R.id.ivLock);

        if (GlobalClass.getPrefrenceBoolean(context, GlobalClass.key_isSoundLock, true)) {
            if (position > 20) {
                holder.ivLock.setVisibility(View.VISIBLE);
                holder.ivLock.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, PackageActivity.class);
                        context.startActivity(intent);
                    }
                });
            } else {
                holder.ivLock.setVisibility(View.GONE);
            }

        } else {
            holder.ivLock.setVisibility(View.GONE);
        }

        if (position == 0) {
            holder.ivSoundEffectItem1.setVisibility(View.VISIBLE);
        } else {
            holder.ivSoundEffectItem1.setVisibility(View.GONE);
        }

        if (position == GlobalClass.selectsounds) {
            holder.flBg.setVisibility(View.VISIBLE);
        } else {
            holder.flBg.setVisibility(View.GONE);
        }
        if (soundEffectArrayList.get(position).isSelected()) {
            holder.ivSoundEffectItem.setBorderColor(context.getResources().getColor(R.color.orange));
            holder.ivSoundEffectItem.setBorderWidth(1);
        } else {
            holder.ivSoundEffectItem.setBorderWidth(0);
        }

        return convertView;
    }

    private static class ViewHolder {
        CircleImageView ivSoundEffectItem;
        CircleImageView ivSoundEffectItem1;
        FrameLayout flBg;
        ImageView ivLock;
    }

}
