package com.codminskeyboards.universekeyboard.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.codminskeyboards.universekeyboard.fragment.FontFragment;
import com.codminskeyboards.universekeyboard.fragment.KeyDesignFragment;
import com.codminskeyboards.universekeyboard.fragment.SoundFragment;
import com.codminskeyboards.universekeyboard.fragment.WallpaperFragment;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        if (i == 0) {
            return new WallpaperFragment();
        } else if (i == 1) {
            return new KeyDesignFragment();
        } else if (i == 2) {
            return new FontFragment();
        } else {
            return new SoundFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
