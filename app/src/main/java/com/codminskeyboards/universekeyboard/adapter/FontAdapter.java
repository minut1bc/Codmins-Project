package com.codminskeyboards.universekeyboard.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codminskeyboards.universekeyboard.R;
import com.codminskeyboards.universekeyboard.activity.PremiumStoreActivity;
import com.codminskeyboards.universekeyboard.utils.GlobalClass;

public class FontAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    private Context context;
    private String[] fontArray;

    public FontAdapter(Context context, String[] fontArray) {
        super();
        this.context = context;
        this.fontArray = fontArray;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return fontArray.length;
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
            convertView = inflater.inflate(R.layout.row_font_style_item, parent, false);

        holder.fontTextView = convertView.findViewById(R.id.fontTextView);
        holder.outlineImageView = convertView.findViewById(R.id.outlineImageView);
        holder.lockImageView = convertView.findViewById(R.id.lockImageView);

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

        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/" + fontArray[position]);
        holder.fontTextView.setTypeface(font);

        return convertView;
    }

    private static class ViewHolder {
        TextView fontTextView;
        ImageView outlineImageView;
        ImageView lockImageView;
    }
}
