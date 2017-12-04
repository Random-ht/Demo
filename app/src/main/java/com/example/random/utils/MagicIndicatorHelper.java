package com.example.random.utils;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.random.R;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView;

import java.util.List;

/**
 * Created by John on 2017/11/17.
 */

public class MagicIndicatorHelper {
    private static MagicIndicatorHelper helper;

    public static MagicIndicatorHelper getInstance() {
        if (helper == null) {
            if (helper == null) {
                synchronized (MagicIndicatorHelper.class) {
                    helper = new MagicIndicatorHelper();
                }
            }
        }
        return helper;
    }

    /**
     * 设置tab标签
     *
     * @param context         上下文
     * @param mMagicIndicator MagicIndicator
     * @param tabList         标签集合
     * @param viewPager       ViewPager
     */
    public void setMagicIndicator(Context context, MagicIndicator mMagicIndicator, final List<?> tabList, final ViewPager viewPager) {
        mMagicIndicator.setBackgroundColor(Color.parseColor("#FFFFFF"));//设置tab背景颜色
        CommonNavigator mCommonNavigator = new CommonNavigator(context);
        mCommonNavigator.setSkimOver(true);
        mCommonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return tabList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ClipPagerTitleView clipPagerTitleView = new ClipPagerTitleView(context);
                clipPagerTitleView.setText((String) tabList.get(index));
                clipPagerTitleView.setTextColor(Color.BLACK);//设置没选中字体颜色
                clipPagerTitleView.setClipColor(Color.RED);//设置选中的颜色
                clipPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(index);
                    }
                });
                return clipPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;
            }
        });

        mMagicIndicator.setNavigator(mCommonNavigator);
        ViewPagerHelper.bind(mMagicIndicator, viewPager);

    }

}
