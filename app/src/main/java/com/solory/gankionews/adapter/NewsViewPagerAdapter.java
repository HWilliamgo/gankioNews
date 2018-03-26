package com.solory.gankionews.adapter;
/*
 *
 * Created by 黄伟杰 on 2018/3/6.
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.solory.gankionews.fragment.NewsPageFragment;

public class NewsViewPagerAdapter extends FragmentStatePagerAdapter{

    private static String[] types={"Android","iOS", "休息视频","拓展资源","前端"};

    private static final int pageCount = 5;

    public NewsViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        return NewsPageFragment.newInstance(types[position]);
    }

    @Override
    public int getCount() {
        return pageCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return types[position];
    }
}
