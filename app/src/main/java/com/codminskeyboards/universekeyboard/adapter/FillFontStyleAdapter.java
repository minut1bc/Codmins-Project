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

import de.hdodenhof.circleimageview.CircleImageView;

public class FillFontStyleAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    Context context;
    private String[] fontArray;


    public FillFontStyleAdapter(Context context, String[] fontArray) {
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

        holder.txtFontItem = convertView.findViewById(R.id.txtFontItem);
        holder.cIvBg = convertView.findViewById(R.id.cIvBg);
        holder.ivLock = convertView.findViewById(R.id.ivLock);

        if (GlobalClass.getPrefrenceBoolean(context, GlobalClass.key_isFontLock, true)) {
            if (position > 33) {
                holder.ivLock.setVisibility(View.VISIBLE);
                holder.ivLock.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, PremiumStoreActivity.class);
                        context.startActivity(intent);
                    }

                });
            } else {
                holder.ivLock.setVisibility(View.GONE);
            }
        } else {
            holder.ivLock.setVisibility(View.GONE);
        }

        if (position == GlobalClass.selectfonts) {
            holder.cIvBg.setVisibility(View.VISIBLE);
        } else {
            holder.cIvBg.setVisibility(View.GONE);
        }
        Typeface cfont = Typeface.createFromAsset(context.getAssets(), "fonts/" + fontArray[position]);
        holder.txtFontItem.setTypeface(cfont);

        return convertView;
    }

    private static class ViewHolder {
        TextView txtFontItem;
        CircleImageView cIvBg;
        ImageView ivLock;
    }
}
