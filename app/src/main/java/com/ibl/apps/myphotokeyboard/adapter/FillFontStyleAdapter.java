package com.ibl.apps.myphotokeyboard.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ibl.apps.myphotokeyboard.R;
import com.ibl.apps.myphotokeyboard.subscriptionmenu.PackageActivity;
import com.ibl.apps.myphotokeyboard.utils.GlobalClass;

import de.hdodenhof.circleimageview.CircleImageView;

public class FillFontStyleAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
   // private final GlobalClass globalClass;
    Context context;
    private String[] fontArray;
//    private ArrayList<FontsPaid> fontStyleArrayList = new ArrayList<>();
    //String [] fontStyleArrayList =  GlobalClass.fonts;
    private ViewHolder holder;


    public FillFontStyleAdapter(Context context, String[] fontArray) {
        super();
        this.context = context;
        this.fontArray = fontArray;
//        this.fontStyleArrayList = fontStyleArrayList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      //  globalClass = new GlobalClass(context);
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
        holder = new ViewHolder();
        convertView = inflater.inflate(R.layout.row_font_style_item, null);

        holder.txtFontItem = convertView.findViewById(R.id.txtFontItem);
        holder.cIvBg = convertView.findViewById(R.id.cIvBg);
        holder.ivLock = convertView.findViewById(R.id.ivLock);

        if (GlobalClass.getPrefrenceBoolean(context,GlobalClass.key_isFontLock,true)) {
            if (position > 33) {
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
        }else {
            holder.ivLock.setVisibility(View.GONE);
        }

        if(position==GlobalClass.selectfonts){
//            Toast.makeText(context, "Kirtii  "+defaultColorFreeArrayList[position], Toast.LENGTH_SHORT).show();

//            if (i == defaultColorFreeArrayList[position]) {
            holder.cIvBg.setVisibility(View.VISIBLE);
        } else {
            holder.cIvBg.setVisibility(View.GONE);
        }
//        holder.cIvBg.setBorderColor(context.getResources().getColor(R.color.dark_red));
//        holder.cIvBg.setBorderWidth(1);
//        holder.txtFontItem.setBackgroundResource(Integer.parseInt(GlobalClass.fonts[position]));
//
//
        /*Glide.with(context)
                .load(GlobalClass.fonts[position])
                .bitmapTransform(new CropCircleTransformation(context))
                .signature(new StringSignature("" + System.currentTimeMillis()))
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.txtFontItem);
*/

//        Log.e("FillFontStyleAdapter","txtFontItem"+GlobalClass.fonts[position]);
        Typeface cfont = Typeface.createFromAsset(context.getAssets(), "fonts/" + fontArray[position]);
        holder.txtFontItem.setTypeface(cfont);




//        try {
//            File SDCardRoot = Environment.getExternalStorageDirectory().getAbsoluteFile();
//            File newFile = new File(SDCardRoot.getAbsolutePath() + "/" + context.getString(R.string.app_name) + "/");
//            String filename = fontStyleArrayList.get(position).getTitle() + ".ttf";
//
//            File file = new File(newFile, filename);
//
//            if (file.exists()) {
//                Typeface font = Typeface.createFromFile(file);
////                holder.txtFontItem.setTypeface(font);
//            } else {
//                file = new File(SDCardRoot, filename);
//                Typeface font = Typeface.createFromFile(file);
////                holder.txtFontItem.setTypeface(font);
//            }
//        } catch (Exception e) {
//            GlobalClass.printLog("Exception ", "-----------FillFontStyleAdapter-----------------" + e.getMessage());
//        }
//
//        if (fontArray.get(position).isSelected()) {



//        } else {
//            holder.cIvBg.setBorderWidth(0);
//        }
//        if (fontStyleArrayList.get(position).isPaid().equals("true"))
//            holder.ivLock.setVisibility(View.VISIBLE);
//        else
//            holder.ivLock.setVisibility(View.GONE);

        return convertView;
    }

    private static class ViewHolder {
        TextView txtFontItem;
        CircleImageView cIvBg;
        ImageView ivLock;
    }
}
