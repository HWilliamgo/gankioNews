package com.solory.gankionews.adapter;
/*
 *
 * Created by 黄伟杰 on 2018/3/25.
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.solory.gankionews.fragment.AccountFragment;
import com.solory.gankionews.fragment.HomeFragment;
import com.solory.gankionews.fragment.PictureFragment;

public class HomeViewPagerAdapter extends FragmentStatePagerAdapter {
    private Context context;
    private static final int pageCount = 3;
    private static final String[] titles = new String[]{"首页", "图片", "我的"};

    public HomeViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();
            case 1:
                return new PictureFragment();
            case 2:
                return new AccountFragment();
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return pageCount;
    }
}
