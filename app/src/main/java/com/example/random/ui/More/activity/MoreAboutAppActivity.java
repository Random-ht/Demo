package com.example.random.ui.More.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.random.R;
import com.example.random.customView.MarqueeView;
import com.example.random.customView.SwitchButton;
import com.example.random.utils.DataCleanManager;

import java.util.ArrayList;
import java.util.List;

public class MoreAboutAppActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView cache;
    private String totalCacheSize = "";
    private SwitchButton switchButton;
    private MarqueeView marqueeView;
    private List<String> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_about_app);
        getSupportActionBar().hide();

        //初始化数据
        initData();
        //初始化控件
        initView();
        //初始化监听
        initListener();


    }

    /**
     * 初始化数据
     */
    private void initData() {
        try {
            totalCacheSize = DataCleanManager.getTotalCacheSize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mData = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            mData.add("这是第" + i + "条数据");
        }


    }

    /**
     * 初始化控件
     */
    private void initView() {
        cache = (TextView) findViewById(R.id.cache);
        marqueeView = (MarqueeView) findViewById(R.id.run_text_view);
        switchButton = (SwitchButton) findViewById(R.id.close_open);
        switchButton.setCheck(true);//设置默认选中

        if (TextUtils.isEmpty(totalCacheSize) || "0K".equals(totalCacheSize)) {
            cache.setText("暂无缓存");
            cache.setEnabled(false);
        } else {
            cache.setText("缓存" + totalCacheSize + "   点击可清理");
            cache.setEnabled(true);
        }

        marqueeView.setOnMargueeListener(new MarqueeView.OnMargueeListener() {
            @Override
            public void onRollOver() {
                marqueeView.setText("没有数据了");
            }
        });

    }

    /**
     * 初始化监听
     */
    private void initListener() {
        //开关按钮的点击事件
        switchButton.setOnChangedListener(new SwitchButton.OnChangedListener() {
            @Override
            public void OnChanged(View v, boolean checkState) {
                //开checkState 就是返回的true，关checkState就是返回的false
                Log.i("-=-=-=-=-", checkState + "");
            }
        });

        marqueeView.setText("这个就是内容");
        marqueeView.startScroll();

//        refreshMarquee();

    }

    private void refreshMarquee() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < mData.size(); i++) {

                }
                marqueeView.setText("这个就是内容");
                marqueeView.startScroll();
                refreshMarquee();
            }
        }, 2000);
    }

    private Handler handler = new Handler();

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cache:
                //清理缓存
                DataCleanManager.clearAllCache(this);
                try {
                    totalCacheSize = DataCleanManager.getTotalCacheSize(this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.i("-=-=-=-=-", totalCacheSize);
                cache.setText("暂无缓存");
                break;

        }
    }
}
