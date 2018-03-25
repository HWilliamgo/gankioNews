package com.solory.gankionews.adapter;
/*
 *
 * Created by 黄伟杰 on 2018/3/6.
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.solory.gankionews.fragment.MyFragment;

public class MyViewPagerAdapter extends FragmentStatePagerAdapter{
    private static String[] types={"福利","Android","iOS", "休息视频","拓展资源","前端"};

    private static final int pageCount = 6;
    private Context context;

    public MyViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        return MyFragment.newInstance(types[position]);
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
