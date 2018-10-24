package com.codminskeyboards.universekeyboard.fragment;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.codminskeyboards.universekeyboard.R;
import com.codminskeyboards.universekeyboard.activity.CreateKeyboardActivity;
import com.codminskeyboards.universekeyboard.adapter.FillFontColorAdapter;
import com.codminskeyboards.universekeyboard.adapter.FillFontStyleAdapter;
import com.codminskeyboards.universekeyboard.database.DatabaseHelper;
import com.codminskeyboards.universekeyboard.model.FontsPaid;
import com.codminskeyboards.universekeyboard.utils.AsyncDownload;
import com.codminskeyboards.universekeyboard.utils.GlobalClass;

import java.io.IOException;
import java.util.ArrayList;

public class FontFragment extends Fragment {

    Context context;

    GridView gvFont;
    FillFontStyleAdapter fillFontStyleAdapter;
    String[] fontArray = new String[0];

    CreateKeyboardActivity createKeyboardActivity;

    private RecyclerView rvDefaultColorFontStyle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fontFragmentView = inflater.inflate(R.layout.font_fragment, container, false);

        GlobalClass globalClass = new GlobalClass(context);

        gvFont = fontFragmentView.findViewById(R.id.gvFont);
        rvDefaultColorFontStyle = fontFragmentView.findViewById(R.id.rvDefaultColorFontStyle);
        rvDefaultColorFontStyle.setNestedScrollingEnabled(false);
        rvDefaultColorFontStyle.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

        try {
            fontArray = context.getAssets().list("fonts");
        } catch (IOException e) {
            e.printStackTrace();
        }

        getFontFromDatabase();
        getColorFromDatabase();

        return fontFragmentView;
    }

    @SuppressWarnings("deprecation")  // TODO: Check if it's okay
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        createKeyboardActivity = (CreateKeyboardActivity) activity;
        context = activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        createKeyboardActivity = null;
        context = null;
    }

    private void getFontFromDatabase() {
        ArrayList<FontsPaid> fontsPaidArrayList = new ArrayList<>();
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        Cursor dataCursor = dbHelper.getDataOfTable(DatabaseHelper.TABLE_FONT);

        if (dataCursor != null && dataCursor.getCount() > 0) {
            dataCursor.moveToFirst();
            do {
                FontsPaid fontsPaid = new FontsPaid();
                fontsPaid.setId(dataCursor.getString(dataCursor.getColumnIndex(DatabaseHelper.KEY_FONT_ID)));
                fontsPaid.setTitle(dataCursor.getString(dataCursor.getColumnIndex(DatabaseHelper.KEY_FONT_TITLE)));
                fontsPaid.setFont_url(dataCursor.getString(dataCursor.getColumnIndex(DatabaseHelper.KEY_FONT_URL)));
                fontsPaid.setPaid(dataCursor.getString(dataCursor.getColumnIndex(DatabaseHelper.KEY_FONT_IS_PAID)));
                fontsPaidArrayList.add(fontsPaid);

            } while (dataCursor.moveToNext());
        }

        for (int i = 0; i < fontsPaidArrayList.size(); i++) {
            AsyncDownload asyncDownload = new AsyncDownload(createKeyboardActivity, fontsPaidArrayList.get(i));
            asyncDownload.execute();
        }
        setFontStyleGridView(fontsPaidArrayList);
    }

    private void setFontStyleGridView(final ArrayList<FontsPaid> fontStyleArrayList) {
        fillFontStyleAdapter = new FillFontStyleAdapter(context, fontArray);
        gvFont.setAdapter(fillFontStyleAdapter);

        if (GlobalClass.tempFontName != null && !GlobalClass.tempFontName.isEmpty()) {
            for (int i = 0; i < fontStyleArrayList.size(); i++) {
                if (GlobalClass.tempFontName.equals(fontStyleArrayList.get(i).getTitle())) {
                    fontStyleArrayList.get(i).setSelected(true);
                } else {
                    fontStyleArrayList.get(i).setSelected(false);
                }
            }
        }

        gvFont.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                GlobalClass.selectfonts = position;

                if (createKeyboardActivity != null) {
                    if (fontArray[position] != null) {
                        GlobalClass.tempFontName = "fonts/" + fontArray[position];
                        createKeyboardActivity.redrawKeyboard();
                    }
                }

                for (int i = 0; i < fontStyleArrayList.size(); i++) {
                    if (i == position) {
                        fontStyleArrayList.get(i).setSelected(true);
                    } else {
                        fontStyleArrayList.get(i).setSelected(false);
                    }
                }
                fillFontStyleAdapter.notifyDataSetChanged();
                GlobalClass.checkStartAd();
            }
        });
    }

    private void getColorFromDatabase() {
        int[] colorWallpaperArrayList = GlobalClass.colorsHorizontal;

        FillFontColorAdapter fillFontColorAdapter = new FillFontColorAdapter(context, colorWallpaperArrayList, createKeyboardActivity);
        rvDefaultColorFontStyle.setAdapter(fillFontColorAdapter);
    }
}
