package com.codminskeyboards.universekeyboard.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.codminskeyboards.universekeyboard.fragment.BackgroundFragment;
import com.codminskeyboards.universekeyboard.fragment.FontFragment;
import com.codminskeyboards.universekeyboard.fragment.KeyDesignFragment;
import com.codminskeyboards.universekeyboard.fragment.SoundFragment;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new BackgroundFragment();
            case 1:
                return new KeyDesignFragment();
            case 2:
                return new FontFragment();
            case 3:
                return new SoundFragment();
        }
        return new BackgroundFragment();
    }

    @Override
    public int getCount() {
        return 4;
    }


}
