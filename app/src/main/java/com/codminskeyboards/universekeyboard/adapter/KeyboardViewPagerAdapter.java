package com.codminskeyboards.universekeyboard.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.codminskeyboards.universekeyboard.fragment.KeyboardFragment;
import com.codminskeyboards.universekeyboard.utils.GlobalClass;

public class KeyboardViewPagerAdapter extends FragmentStatePagerAdapter {

    public KeyboardViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return GlobalClass.keyboardDataArray.size();
    }

    @Override
    public Fragment getItem(int i) {
        return KeyboardFragment.init(i);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
    }

}