package com.example.random.ui.Home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.random.R;

import com.example.random.ui.Home.adapter.HomeViewPagerAdapter;
import com.example.random.utils.MagicIndicatorHelper;
import com.example.random.customView.NoScrollViewPager;

import net.lucode.hackware.magicindicator.MagicIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 2017/11/15.
 */

public class HomeFragment extends Fragment {

    private View view;
    private MagicIndicator mMagicIndicator;

    private NoScrollViewPager viewPager;
    private List<String> tabList;
    private List<Fragment> fragmentList;
    private HomeViewPagerAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_home, null);

        initData();
        initView();
        initListener();

        return view;
    }

    private void initData() {
        tabList = new ArrayList<>();
        fragmentList = new ArrayList<>();
        tabList.add("关注");
        tabList.add("头条");
        tabList.add("社会");
        tabList.add("国内");
        tabList.add("国际");
        tabList.add("娱乐");
        tabList.add("体育");
        tabList.add("军事");
        tabList.add("科技");
        tabList.add("财经");
        tabList.add("时尚");

        for (int i = 0; i < tabList.size(); i++) {
            DetailFragment fragment = new DetailFragment();
            Bundle bundle = new Bundle();
            bundle.putString("type", tabList.get(i));
            bundle.putInt("index", i);
            fragment.setArguments(bundle);
            fragmentList.add(fragment);
        }

    }

    private void initView() {
//        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        mMagicIndicator = (MagicIndicator) view.findViewById(R.id.tabLayout);
        viewPager = (NoScrollViewPager) view.findViewById(R.id.viewPager);
    }

    private void initListener() {

        mAdapter = new HomeViewPagerAdapter(getChildFragmentManager(), fragmentList, tabList);
        viewPager.setAdapter(mAdapter);

        if (tabList != null && tabList.size() > 0) {
            viewPager.setOffscreenPageLimit(tabList.size());//设置缓存个数，预加载个数
        }
//        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
//        tabLayout.setupWithViewPager(viewPager);

        MagicIndicatorHelper.getInstance().setMagicIndicator(getContext(), mMagicIndicator, tabList, viewPager);
        viewPager.setCurrentItem(1);//默认选中第二条
    }


}
