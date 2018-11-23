package com.codminskeyboards.universekeyboard.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codminskeyboards.universekeyboard.R;
import com.codminskeyboards.universekeyboard.activity.CreateKeyboardActivity;
import com.codminskeyboards.universekeyboard.adapter.FontAdapter;
import com.codminskeyboards.universekeyboard.adapter.FontColorAdapter;
import com.codminskeyboards.universekeyboard.utils.GlobalClass;
import com.codminskeyboards.universekeyboard.utils.RecyclerItemClickListener;

public class FontFragment extends Fragment {

    Context context;

    RecyclerView fontRecyclerView;
    RecyclerView fontColorRecyclerView;

    FontAdapter fontAdapter;
    FontColorAdapter fontColorAdapter;

    CreateKeyboardActivity createKeyboardActivity;

    // String[] fontArray = new String[0];

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fontFragmentView = inflater.inflate(R.layout.font_fragment, container, false);

        fontRecyclerView = fontFragmentView.findViewById(R.id.fontRecyclerView);
        fontRecyclerView.setHasFixedSize(true);
        fontRecyclerView.setLayoutManager(new GridLayoutManager(context, GlobalClass.calculateNoOfColumns(context)));

        fontColorRecyclerView = fontFragmentView.findViewById(R.id.fontColorRecyclerView);
        fontColorRecyclerView.setHasFixedSize(true);
        fontColorRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

        getFontFromDatabase();

        setFontColorRecyclerView();

        return fontFragmentView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        createKeyboardActivity = (CreateKeyboardActivity) context;
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        createKeyboardActivity = null;
        context = null;
    }

    private void getFontFromDatabase() {
//        ArrayList<FontsPaid> fontsPaidArrayList = new ArrayList<>();
//
//        DatabaseHelper dbHelper = new DatabaseHelper(context);
//        Cursor dataCursor = dbHelper.getDataOfTable(DatabaseHelper.TABLE_FONT);
//
//        if (dataCursor != null && dataCursor.getCount() > 0) {
//            dataCursor.moveToFirst();
//            do {
//                FontsPaid fontsPaid = new FontsPaid();
//                fontsPaid.setId(dataCursor.getString(dataCursor.getColumnIndex(DatabaseHelper.KEY_FONT_ID)));
//                fontsPaid.setTitle(dataCursor.getString(dataCursor.getColumnIndex(DatabaseHelper.KEY_FONT_TITLE)));
//                fontsPaid.setFont_url(dataCursor.getString(dataCursor.getColumnIndex(DatabaseHelper.KEY_FONT_URL)));
//                fontsPaid.setPaid(dataCursor.getString(dataCursor.getColumnIndex(DatabaseHelper.KEY_FONT_IS_PAID)));
//                fontsPaidArrayList.add(fontsPaid);
//            } while (dataCursor.moveToNext());
//        }
//
//        for (int i = 0; i < fontsPaidArrayList.size(); i++) {
//            AsyncDownload asyncDownload = new AsyncDownload(createKeyboardActivity, fontsPaidArrayList.get(i));
//            asyncDownload.execute();
//        }

        setFontRecyclerView(/*fontsPaidArrayList*/);
    }

    private void setFontRecyclerView(/*final ArrayList<FontsPaid> fontsArray*/) {
        fontAdapter = new FontAdapter(context, GlobalClass.fontsArray);
        fontRecyclerView.setAdapter(fontAdapter);

//        for (int i = 0; i < fontsArray.size(); i++) {
//            if (GlobalClass.fontId.equals(fontsArray.get(i).getTitle()))
//                fontsArray.get(i).setSelected(true);
//            else
//                fontsArray.get(i).setSelected(false);
//        }

        fontRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                GlobalClass.fontPosition = position;

                createKeyboardActivity.redrawKeyboard();

//                for (int i = 0; i < fontsArray.size(); i++) {
//                    if (i == position)
//                        fontsArray.get(i).setSelected(true);
//                    else
//                        fontsArray.get(i).setSelected(false);
//                }

                fontAdapter.notifyDataSetChanged();
                GlobalClass.checkStartAd();
            }
        }));
    }

    private void setFontColorRecyclerView() {
        fontColorAdapter = new FontColorAdapter(context, GlobalClass.colorsArray);
        fontColorRecyclerView.setAdapter(fontColorAdapter);
        fontColorRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                GlobalClass.fontColorPosition = position;
                createKeyboardActivity.redrawKeyboard();
                fontColorAdapter.notifyDataSetChanged();
                GlobalClass.checkStartAd();
            }
        }));
    }
}
