package com.ibl.apps.myphotokeyboard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ibl.apps.myphotokeyboard.R;

public class FillEmojiAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    Context context;
    private String[] emojiArrayList;

    public FillEmojiAdapter(Context context, String[] emojiArrayList) {
        super();
        this.context = context;
        this.emojiArrayList = emojiArrayList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return emojiArrayList.length;
    }

    @Override
    public Object getItem(int position) {
        return emojiArrayList[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        convertView = inflater.inflate(R.layout.row_emoji_item, null);

        holder.txtEmoji = convertView.findViewById(R.id.txtEmoji);

        if (emojiArrayList[position] != null && !emojiArrayList[position].isEmpty())
            holder.txtEmoji.setText(emojiArrayList[position]);

        return convertView;
    }

    private static class ViewHolder {
        TextView txtEmoji;
    }
}
