package com.example.random.ui.More.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.random.Entity.User;
import com.example.random.R;
import com.example.random.ui.More.adapter.GridViewAdapter;
import com.example.random.ui.More.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class WaiMaiHomeStyleActivity extends AppCompatActivity {

    private String[] titles = {"美食", "电影", "酒店住宿", "休闲娱乐", "外卖", "自助餐", "KTV", "机票/火车票", "周边游", "美甲美睫",
            "火锅", "生日蛋糕", "甜品饮品", "水上乐园", "汽车服务", "美发", "丽人", "景点", "足疗按摩", "运动健身", "健身", "超市", "买菜",
            "今日新单", "小吃快餐", "面膜", "洗浴/汗蒸", "母婴亲子", "生活服务", "婚纱摄影", "学习培训", "家装", "结婚", "全部分配"};

    private ViewPager viewPager;
    private LinearLayout ll_dot;
    private List<User> mData = new ArrayList<User>();
    private List<View> mPagerList;

    /**
     * 总的页数
     */
    private int pageCount;
    /**
     * 每一页显示的个数
     */
    private int pageSize = 12;
    /**
     * 当前显示的是第几页
     */
    private int curIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wai_mai_home_style);
        getSupportActionBar().hide();
        initData();
        initView();

    }

    //初始化数据
    private void initData() {

        for (int i = 0; i < titles.length; i++) {
            //动态获取资源ID，第一个参数是资源名，第二个参数是资源类型例如drawable，string等，第三个参数包名
            int imageId = getResources().getIdentifier("ic_category_" + i, "mipmap", getPackageName());
            User user = new User();
            user.setNickname(titles[i]);
            user.setIconRes(imageId);
            mData.add(user);
        }
    }

    //初始化控件
    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        ll_dot = (LinearLayout) findViewById(R.id.ll_dot);


        pageCount = (int) Math.ceil(mData.size() * 1.0 / pageSize);
        mPagerList = new ArrayList<View>();

        for (int i = 0; i < pageCount; i++) {
            //每个页面都是inflate出一个新实例
            GridView gridView = (GridView) LayoutInflater.from(this).inflate(R.layout.gridview, viewPager, false);
            gridView.setAdapter(new GridViewAdapter(this, mData, i, pageSize));
            mPagerList.add(gridView);

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int pos = position + curIndex * pageSize;
                    Toast.makeText(WaiMaiHomeStyleActivity.this, mData.get(pos).getNickname(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        //设置适配器
        viewPager.setAdapter(new ViewPagerAdapter(mPagerList));
        //设置圆点
        setOvalLayout();
    }

    /**
     * 设置圆点
     */
    public void setOvalLayout() {
        for (int i = 0; i < pageCount; i++) {
            ll_dot.addView(LayoutInflater.from(this).inflate(R.layout.dot, null));
        }
        // 默认显示第一页
        ll_dot.getChildAt(0).findViewById(R.id.v_dot)
                .setBackgroundResource(R.drawable.dot_selected);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageSelected(int position) {
                // 取消圆点选中
                ll_dot.getChildAt(curIndex)
                        .findViewById(R.id.v_dot)
                        .setBackgroundResource(R.drawable.dot_normal);
                // 圆点选中
                ll_dot.getChildAt(position)
                        .findViewById(R.id.v_dot)
                        .setBackgroundResource(R.drawable.dot_selected);
                curIndex = position;
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }
}
