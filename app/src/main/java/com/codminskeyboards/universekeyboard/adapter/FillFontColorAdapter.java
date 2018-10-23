package com.codminskeyboards.universekeyboard.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.StringSignature;
import com.codminskeyboards.universekeyboard.R;
import com.codminskeyboards.universekeyboard.activity.CreateKeyboardActivity;
import com.codminskeyboards.universekeyboard.activity.PremiumStoreActivity;
import com.codminskeyboards.universekeyboard.utils.GlobalClass;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class FillFontColorAdapter extends RecyclerView.Adapter<FillFontColorAdapter.ViewHolder> {
    private Context context;
    private int[] defaultColorFreeArrayList;
    private ClickListener clickListener;

    private CreateKeyboardActivity createKeyboardActivity;

    public FillFontColorAdapter(Context context, int[] defaultColorFreeArrayList, CreateKeyboardActivity createKeyboardActivity) {
        super();
        this.context = context;
        this.createKeyboardActivity = createKeyboardActivity;
        this.defaultColorFreeArrayList = defaultColorFreeArrayList;
    }

    public void setClickListener(FillKeyBgAdapter.ClickListener clickListener) {
        this.clickListener = (ClickListener) clickListener;
    }

    public interface ClickListener {
        void itemClicked(View view, int position);
    }

    @NonNull
    @Override
    public FillFontColorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_fill_color_iteme, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final FillFontColorAdapter.ViewHolder holder, int position) {

        Glide.with(context)
                .load(defaultColorFreeArrayList[position])
                .bitmapTransform(new CropCircleTransformation(context))
                .signature(new StringSignature("" + System.currentTimeMillis()))
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(defaultColorFreeArrayList[position])
                .into(holder.ivColorItem);

        if (GlobalClass.getPrefrenceBoolean(context, GlobalClass.key_isColorLock, true)) {
            if (position > 26) {
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
        if (position == GlobalClass.selectfontcolor) {
            holder.flBg.setVisibility(View.VISIBLE);
        } else {
            holder.flBg.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return defaultColorFreeArrayList.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircleImageView ivColorItem;
        FrameLayout flBg;
        CircleImageView ivLock;

        ViewHolder(final View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ivColorItem = itemView.findViewById(R.id.ivColorItem);
            flBg = itemView.findViewById(R.id.flBg);
            ivLock = itemView.findViewById(R.id.ivLock);

            ivColorItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    GlobalClass.selectfontcolor = getAdapterPosition();
                    if (getAdapterPosition() == 0) {
                        GlobalClass.tempFontColor = "#000000";
                        flBg.setVisibility(View.VISIBLE);
                    } else if (getAdapterPosition() == 1) {
                        GlobalClass.tempFontColor = "#FFFFFF";
                    } else if (getAdapterPosition() == 2) {
                        GlobalClass.tempFontColor = "#2AD2C9";
                    } else if (getAdapterPosition() == 3) {
                        GlobalClass.tempFontColor = "#D0E100";
                    } else if (getAdapterPosition() == 4) {
                        GlobalClass.tempFontColor = "#DF849B";
                    } else if (getAdapterPosition() == 5) {
                        GlobalClass.tempFontColor = "#5FB556";
                    } else if (getAdapterPosition() == 6) {
                        GlobalClass.tempFontColor = "#00F0F0";
                    } else if (getAdapterPosition() == 7) {
                        GlobalClass.tempFontColor = "#0E374C";
                    } else if (getAdapterPosition() == 8) {
                        GlobalClass.tempFontColor = "#00F000";
                    } else if (getAdapterPosition() == 9) {
                        GlobalClass.tempFontColor = "#F0E000";
                    } else if (getAdapterPosition() == 10) {
                        GlobalClass.tempFontColor = "#00A0F0";
                    } else if (getAdapterPosition() == 11) {
                        GlobalClass.tempFontColor = "#9000F0";
                    } else if (getAdapterPosition() == 12) {
                        GlobalClass.tempFontColor = "#ED1B2E";
                    } else if (getAdapterPosition() == 13) {
                        GlobalClass.tempFontColor = "#6D6E70";
                    } else if (getAdapterPosition() == 14) {
                        GlobalClass.tempFontColor = "#6D6E70";
                    } else if (getAdapterPosition() == 15) {
                        GlobalClass.tempFontColor = "#D7D7D8";
                    } else if (getAdapterPosition() == 16) {
                        GlobalClass.tempFontColor = "#B4A996";
                    } else if (getAdapterPosition() == 17) {
                        GlobalClass.tempFontColor = "#ECB731";
                    } else if (getAdapterPosition() == 18) {
                        GlobalClass.tempFontColor = "#8EC06C";
                    } else if (getAdapterPosition() == 19) {
                        GlobalClass.tempFontColor = "#537B35";
                    } else if (getAdapterPosition() == 20) {
                        GlobalClass.tempFontColor = "#C4DFF6";
                    } else if (getAdapterPosition() == 21) {
                        GlobalClass.tempFontColor = "#56A0D3";
                    } else if (getAdapterPosition() == 22) {
                        GlobalClass.tempFontColor = "#0091CD";
                    } else if (getAdapterPosition() == 23) {
                        GlobalClass.tempFontColor = "#004B79";
                    } else if (getAdapterPosition() == 24) {
                        GlobalClass.tempFontColor = "#7F181B";
                    } else if (getAdapterPosition() == 25) {
                        GlobalClass.tempFontColor = "#9F9FA3";
                    } else if (getAdapterPosition() == 26) {
                        GlobalClass.tempFontColor = "#F21772";
                    } else if (getAdapterPosition() == 27) {
                        GlobalClass.tempFontColor = "#E87F3A";
                    } else if (getAdapterPosition() == 28) {
                        GlobalClass.tempFontColor = "#6D4BE8";
                    } else if (getAdapterPosition() == 29) {
                        GlobalClass.tempFontColor = "#FF8CA8";
                    } else if (getAdapterPosition() == 30) {
                        GlobalClass.tempFontColor = "#A15BE8";
                    } else if (getAdapterPosition() == 31) {
                        GlobalClass.tempFontColor = "#81B89D";
                    } else if (getAdapterPosition() == 32) {
                        GlobalClass.tempFontColor = "#7EBFAA";
                    } else if (getAdapterPosition() == 33) {
                        GlobalClass.tempFontColor = "#D5958C";
                    } else if (getAdapterPosition() == 34) {
                        GlobalClass.tempFontColor = "#469594";
                    } else if (getAdapterPosition() == 35) {
                        GlobalClass.tempFontColor = "#469594";
                    } else if (getAdapterPosition() == 36) {
                        GlobalClass.tempFontColor = "#954E53";
                    } else if (getAdapterPosition() == 37) {
                        GlobalClass.tempFontColor = "#958A84";
                    } else if (getAdapterPosition() == 38) {
                        GlobalClass.tempFontColor = "#ECBC84";
                    } else if (getAdapterPosition() == 39) {
                        GlobalClass.tempFontColor = "#8294DE";
                    } else if (getAdapterPosition() == 40) {
                        GlobalClass.tempFontColor = "#68875D";
                    }

                    if (createKeyboardActivity != null)
                        createKeyboardActivity.setRadius();
                    notifyDataSetChanged();
                    GlobalClass.checkStartAd();
                }

            });
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) {
                clickListener.itemClicked(view, getAdapterPosition());
            }
        }
    }
}