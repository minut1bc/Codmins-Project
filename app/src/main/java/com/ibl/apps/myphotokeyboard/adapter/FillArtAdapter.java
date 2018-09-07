package com.ibl.apps.myphotokeyboard.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ibl.apps.myphotokeyboard.Interface.OnItemClickListener;
import com.ibl.apps.myphotokeyboard.R;
import com.ibl.apps.myphotokeyboard.utils.GlobalClass;

public class FillArtAdapter extends RecyclerView.Adapter<FillArtAdapter.viewHolders> {
    Context context;
    private String[] emojiArrayList;
    private OnItemClickListener clickListener;

    public FillArtAdapter(Context context, String[] emojiArrayList) {
        super();
        this.context = context;
        this.emojiArrayList = emojiArrayList;
    }

    @Override
    public viewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_art_item, null);
        viewHolders rcv = new viewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(viewHolders holder, int position) {
        if (emojiArrayList[position] != null && !emojiArrayList[position].isEmpty()) {
            holder.txtart.setText(emojiArrayList[position]);
        }

        int radius = 20;
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(GlobalClass.getPreferencesInt(context, GlobalClass.KEY_BG_COLOR, context.getResources().getColor(R.color.eight)));
        gradientDrawable.setCornerRadius(radius);

        holder.txtart.setTextColor(android.graphics.Color.parseColor(GlobalClass.getPreferencesString(context, GlobalClass.FONT_COLOR, "#FFFFFF")));
        holder.txtart.setBackground(gradientDrawable);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return this.emojiArrayList.length;
    }

    public void setClickListener(OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public class viewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txtart;

        public viewHolders(View itemView) {
            super(itemView);
            txtart = (TextView) itemView.findViewById(R.id.txtart);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getPosition());
        }
    }
}
