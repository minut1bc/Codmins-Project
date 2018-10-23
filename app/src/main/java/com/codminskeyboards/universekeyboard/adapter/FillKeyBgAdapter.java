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

import static com.codminskeyboards.universekeyboard.utils.GlobalClass.tempKeyColor;

public class FillKeyBgAdapter extends RecyclerView.Adapter<FillKeyBgAdapter.ViewHolder> {
    private Context context;
    private int[] defaultColorFreeArrayList;
    private ClickListener clickListener;

    private CreateKeyboardActivity createKeyboardActivity;

    public FillKeyBgAdapter(Context context, int[] defaultColorFreeArrayList, CreateKeyboardActivity createKeyboardActivity) {
        super();
        this.context = context;
        this.createKeyboardActivity = createKeyboardActivity;
        this.defaultColorFreeArrayList = defaultColorFreeArrayList;
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface ClickListener {
        void itemClicked(View view, int position);

    }

    @NonNull
    @Override
    public FillKeyBgAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_fill_color_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final FillKeyBgAdapter.ViewHolder holder, int position) {

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

        if (position == GlobalClass.selectbgcolor) {
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
                public void onClick(View v) {
                    GlobalClass.selectbgcolor = getAdapterPosition();
                    if (getAdapterPosition() == 0) {
                        tempKeyColor = context.getResources().getColor(R.color.one);
                    } else if (getAdapterPosition() == 1) {
                        tempKeyColor = context.getResources().getColor(R.color.two);
                    } else if (getAdapterPosition() == 2) {
                        tempKeyColor = context.getResources().getColor(R.color.three);
                    } else if (getAdapterPosition() == 3) {
                        tempKeyColor = context.getResources().getColor(R.color.four);
                    } else if (getAdapterPosition() == 4) {
                        tempKeyColor = context.getResources().getColor(R.color.five);
                    } else if (getAdapterPosition() == 5) {
                        tempKeyColor = context.getResources().getColor(R.color.six);
                    } else if (getAdapterPosition() == 6) {
                        tempKeyColor = context.getResources().getColor(R.color.seven);
                    } else if (getAdapterPosition() == 7) {
                        tempKeyColor = context.getResources().getColor(R.color.eight);
                    } else if (getAdapterPosition() == 8) {
                        tempKeyColor = context.getResources().getColor(R.color.nine);
                    } else if (getAdapterPosition() == 9) {
                        tempKeyColor = context.getResources().getColor(R.color.ten);
                    } else if (getAdapterPosition() == 10) {
                        tempKeyColor = context.getResources().getColor(R.color.eleven);
                    } else if (getAdapterPosition() == 11) {
                        tempKeyColor = context.getResources().getColor(R.color.twelve);
                    } else if (getAdapterPosition() == 12) {
                        tempKeyColor = context.getResources().getColor(R.color.thirteen);
                    } else if (getAdapterPosition() == 13) {
                        tempKeyColor = context.getResources().getColor(R.color.fourteen);
                    } else if (getAdapterPosition() == 14) {
                        tempKeyColor = context.getResources().getColor(R.color.fifteen);
                    } else if (getAdapterPosition() == 15) {
                        tempKeyColor = context.getResources().getColor(R.color.sixteen);
                    } else if (getAdapterPosition() == 16) {
                        tempKeyColor = context.getResources().getColor(R.color.seventeen);
                    } else if (getAdapterPosition() == 17) {
                        tempKeyColor = context.getResources().getColor(R.color.eighteen);
                    } else if (getAdapterPosition() == 18) {
                        tempKeyColor = context.getResources().getColor(R.color.nineteen);
                    } else if (getAdapterPosition() == 19) {
                        tempKeyColor = context.getResources().getColor(R.color.twenty);
                    } else if (getAdapterPosition() == 20) {
                        tempKeyColor = context.getResources().getColor(R.color.twentyone);
                    } else if (getAdapterPosition() == 21) {
                        tempKeyColor = context.getResources().getColor(R.color.twentytwo);
                    } else if (getAdapterPosition() == 22) {
                        tempKeyColor = context.getResources().getColor(R.color.twentythree);
                    } else if (getAdapterPosition() == 23) {
                        tempKeyColor = context.getResources().getColor(R.color.twentyfour);
                    } else if (getAdapterPosition() == 24) {
                        tempKeyColor = context.getResources().getColor(R.color.twentyfive);
                    } else if (getAdapterPosition() == 25) {
                        tempKeyColor = context.getResources().getColor(R.color.twentysix);
                    } else if (getAdapterPosition() == 26) {
                        tempKeyColor = context.getResources().getColor(R.color.twentyseven);
                    } else if (getAdapterPosition() == 27) {
                        tempKeyColor = context.getResources().getColor(R.color.twentyeight);
                    } else if (getAdapterPosition() == 28) {
                        tempKeyColor = context.getResources().getColor(R.color.twentynine);
                    } else if (getAdapterPosition() == 29) {
                        tempKeyColor = context.getResources().getColor(R.color.thirty);
                    } else if (getAdapterPosition() == 30) {
                        tempKeyColor = context.getResources().getColor(R.color.thirtyone);
                    } else if (getAdapterPosition() == 31) {
                        tempKeyColor = context.getResources().getColor(R.color.thirtytwo);
                    } else if (getAdapterPosition() == 32) {
                        tempKeyColor = context.getResources().getColor(R.color.thirtythree);
                    } else if (getAdapterPosition() == 33) {
                        tempKeyColor = context.getResources().getColor(R.color.thirtyfour);
                    } else if (getAdapterPosition() == 34) {
                        tempKeyColor = context.getResources().getColor(R.color.thirtysix);
                    } else if (getAdapterPosition() == 35) {
                        tempKeyColor = context.getResources().getColor(R.color.thirtyseven);
                    } else if (getAdapterPosition() == 36) {
                        tempKeyColor = context.getResources().getColor(R.color.thirtyeight);
                    } else if (getAdapterPosition() == 37) {
                        tempKeyColor = context.getResources().getColor(R.color.thirtynine);
                    } else if (getAdapterPosition() == 38) {
                        tempKeyColor = context.getResources().getColor(R.color.fourty);
                    } else if (getAdapterPosition() == 39) {
                        tempKeyColor = context.getResources().getColor(R.color.fourtyone);
                    }
                    if (createKeyboardActivity != null) {
                        createKeyboardActivity.setRadius();
                    }

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