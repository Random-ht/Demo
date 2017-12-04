package com.example.random.ui.Main.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.random.EventBus.EventManager;
import com.example.random.EventBus.PostEvent;
import com.example.random.R;
import com.example.random.test.FiveActivity;
import com.example.random.test.FourActivity;
import com.example.random.test.OneActivity;
import com.example.random.test.ThreeActivity;
import com.example.random.test.TwoActivity;
import com.example.random.ui.Home.fragment.HomeFragment;
import com.example.random.ui.Me.fragment.MeFragment;
import com.example.random.ui.More.fragment.MoreFragment;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //图片资源
    private Drawable homePressed;
    private Drawable homeNormal;
    private Drawable morePressed;
    private Drawable moreNormal;
    private Drawable mePressed;
    private Drawable meNormal;


    private List<Fragment> fragments = new ArrayList<>();

    private TextView homeText;
    private TextView moreText;
    private TextView meText;
    private FragmentManager manager;
    private HomeFragment homeFragment;
    private MoreFragment moreFragment;
    private MeFragment meFragment;
    //    private NoScrollViewPager viewPager;
    private DrawerLayout drawerLayout;

    private boolean meFragmentIsShow = false;//用于判断MeFragment是否显示

    private long clickMeFirstTime = 0;//第一次点击按钮的时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getSupportActionBar().hide();

        //沉浸式
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.red1)
                .statusBarDarkFont(true)
                .init();

//        initState();
        initResource();
        initData();
        initView();

        //开启一个子线程去处理一些业务，防止在主线程中运行界面卡顿
        thread.start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁沉浸式
        ImmersionBar.with(this)
                .destroy();
    }


    /**
     * 防止在主线程处理逻辑 导致页面卡顿
     */
    private Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            //上传错误日志
//            SubmitCrashServer.getInstance(MainActivity.this).SubmitCrash();
        }
    });

    /**
     * 设置状态栏沉浸式
     */
    private void initState() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }


    /**
     * 获取资源图片
     */
    private void initResource() {
        homePressed = getResources().getDrawable(R.mipmap.home_pressed);
        homeNormal = getResources().getDrawable(R.mipmap.home_normal);

        morePressed = getResources().getDrawable(R.mipmap.more_pressed);
        moreNormal = getResources().getDrawable(R.mipmap.more_normal);

        mePressed = getResources().getDrawable(R.mipmap.me_pressed);
        meNormal = getResources().getDrawable(R.mipmap.me_normal);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        //获取Fragment
        homeFragment = new HomeFragment();
        moreFragment = new MoreFragment();
        meFragment = new MeFragment();

    }

    /**
     * 初始化控件
     */
    private void initView() {

        homeText = (TextView) findViewById(R.id.home_text);
        moreText = (TextView) findViewById(R.id.more_text);
        meText = (TextView) findViewById(R.id.me_text);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        //默认选中首页
        setSelect(homeText, moreText, meText, homePressed, moreNormal, meNormal);

        manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.container, homeFragment)
                .add(R.id.container, moreFragment)
                .add(R.id.container, meFragment)
                .show(homeFragment)
                .hide(moreFragment)
                .hide(meFragment)
                .commit();


    }


    /**
     * 首页按钮和图片的设置
     *
     * @param first  被选中的文字
     * @param second 未被选中的文字
     * @param third  未被选中的文字
     * @param one    被选中的图片
     * @param two    未被选中的图片
     * @param three  未被选中的图片
     */
    private void setSelect(TextView first, TextView second, TextView third, Drawable one, Drawable two, Drawable three) {

        //被选中的图片 背景
        one.setBounds(0, 0, one.getMinimumWidth(), one.getMinimumHeight());
        first.setCompoundDrawables(null, one, null, null);
        //未被选中的图片 背景
        two.setBounds(0, 0, two.getMinimumWidth(), two.getMinimumHeight());
        second.setCompoundDrawables(null, two, null, null);
        //未被选中的图片 背景
        three.setBounds(0, 0, three.getMinimumWidth(), three.getMinimumHeight());
        third.setCompoundDrawables(null, three, null, null);

        //被选中的文字 颜色
        first.setTextColor(getResources().getColor(R.color.main_activity_button_pressed));
        //未被选中的文字 颜色
        second.setTextColor(getResources().getColor(R.color.main_activity_button_normal));
        //未被选中的文字 颜色
        third.setTextColor(getResources().getColor(R.color.main_activity_button_normal));
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.home_layout:

                //设置MeFragment没有显示
                meFragmentIsShow = false;

                //首页被选中
                setSelect(homeText, moreText, meText, homePressed, moreNormal, meNormal);
                FragmentTransaction transaction1 = manager.beginTransaction();
                transaction1.show(homeFragment)
                        .hide(moreFragment)
                        .hide(meFragment)
                        .commit();
                break;

            case R.id.more_layout:

                //设置MeFragment没有显示
                meFragmentIsShow = false;

                //更多被选中
                setSelect(moreText, homeText, meText, morePressed, homeNormal, meNormal);
                FragmentTransaction transaction2 = manager.beginTransaction();
                transaction2.show(moreFragment)
                        .hide(homeFragment)
                        .hide(meFragment)
                        .commit();

                break;

            case R.id.me_layout:

                //如果当前homeCircleFragment已经显示   双击关闭meFragment里面的父控件
                if (meFragmentIsShow) {
                    if (System.currentTimeMillis() - clickMeFirstTime > 500) {
                        clickMeFirstTime = System.currentTimeMillis();
                    } else {
                        //通知meFragment关闭所有展开的Group
                        EventManager.post(1124, new PostEvent());
                    }
                } else {
                    meFragmentIsShow = true;
                }

                //我的被选中
                setSelect(meText, homeText, moreText, mePressed, homeNormal, moreNormal);
                FragmentTransaction transaction3 = manager.beginTransaction();
                transaction3.show(meFragment)
                        .hide(moreFragment)
                        .hide(homeFragment)
                        .commit();

                break;
            case R.id.one:
                startActivity(new Intent(MainActivity.this, OneActivity.class));
                break;
            case R.id.two:
                startActivity(new Intent(MainActivity.this, TwoActivity.class));
                break;
            case R.id.three:
                startActivity(new Intent(MainActivity.this, ThreeActivity.class));
                break;
            case R.id.four:
                startActivity(new Intent(MainActivity.this, FourActivity.class));
                break;
            case R.id.five:
                startActivity(new Intent(MainActivity.this, FiveActivity.class));
                break;

        }

    }


}
