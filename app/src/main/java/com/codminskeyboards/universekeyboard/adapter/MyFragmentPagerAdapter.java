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
        switch (i) {
            case 0:
                return new WallpaperFragment();
            case 1:
                return new KeyDesignFragment();
            case 2:
                return new FontFragment();
            case 3:
                return new SoundFragment();
            default:
                return new WallpaperFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }


}
