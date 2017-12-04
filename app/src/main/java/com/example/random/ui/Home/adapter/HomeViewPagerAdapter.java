package com.example.random.ui.Home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by John on 2017/11/16.
 */

public class HomeViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragmentList;                         //fragment列表
    private List<String> tabList;                              //tab名的列表

    public HomeViewPagerAdapter(FragmentManager fm, List<Fragment> viewPagerList, List<String> tabList) {
        super(fm);
        this.fragmentList = viewPagerList;
        this.tabList = tabList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return tabList.size();
    }

    //此方法用来显示tab上的名字
    @Override
    public CharSequence getPageTitle(int position) {

        return tabList.get(position % tabList.size());
    }
}
