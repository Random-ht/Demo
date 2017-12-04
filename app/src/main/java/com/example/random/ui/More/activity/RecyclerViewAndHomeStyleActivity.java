package com.example.random.ui.More.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.random.Entity.User;
import com.example.random.R;
import com.example.random.http.LoadDataListener;
import com.example.random.model.DetailModel;
import com.example.random.ui.More.adapter.GridViewAdapter;
import com.example.random.ui.More.adapter.HeadFootRecyclerViewAdapter;
import com.example.random.ui.More.adapter.ViewPagerAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAndHomeStyleActivity extends AppCompatActivity implements LoadDataListener, OnRefreshListener, OnLoadmoreListener {

    private String[] titles = {"美食", "电影", "酒店住宿", "休闲娱乐", "外卖", "自助餐", "KTV", "机票/火车票", "周边游", "美甲美睫",
            "火锅", "生日蛋糕", "甜品饮品", "水上乐园", "汽车服务", "美发", "丽人", "景点", "足疗按摩", "运动健身", "健身", "超市", "买菜",
            "今日新单", "小吃快餐", "面膜", "洗浴/汗蒸", "母婴亲子", "生活服务", "婚纱摄影", "学习培训", "家装", "结婚", "全部分配"};

    private ViewPager viewPager;
    private LinearLayout ll_dot;
    private List<User> titlesData = new ArrayList<User>();
    private List<View> mPagerList;

    private RecyclerView recyclerView;
    private SmartRefreshLayout refreshLayout;

    private List<String> mData = new ArrayList<>();

    private DetailModel model = new DetailModel();
    private HeadFootRecyclerViewAdapter mAdapter;

    /**
     * 总的页数
     */
    private int pageCount;
    /**
     * 每一页显示的个数
     */
    private int pageSize = 10;
    /**
     * 当前显示的是第几页
     */
    private int curIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_and_home_style);
        initData();
        initView();
        initListener();
    }

    //初始化数据
    private void initData() {
        for (int i = 0; i < 30; i++) {
            mData.add("这是第" + (i + 1) + "条");
        }

        for (int i = 0; i < titles.length; i++) {
            //动态获取资源ID，第一个参数是资源名，第二个参数是资源类型例如drawable，string等，第三个参数包名
            int imageId = getResources().getIdentifier("ic_category_" + i, "mipmap", getPackageName());
            User user = new User();
            user.setNickname(titles[i]);
            user.setIconRes(imageId);
            titlesData.add(user);
        }

    }

    //初始化控件
    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
    }

    //初始化监听
    private void initListener() {

//        model.loadData(this);
        refreshLayout.setRefreshHeader(new ClassicsHeader(this));
        refreshLayout.setHeaderHeight(60);

        refreshLayout.setRefreshFooter(new ClassicsFooter(this));
        refreshLayout.setFooterHeight(60);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new HeadFootRecyclerViewAdapter(this, mData);
        recyclerView.setAdapter(mAdapter);

        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadmoreListener(this);


        View head = LayoutInflater.from(this).inflate(R.layout.item_add_head, null);
        viewPager = (ViewPager) head.findViewById(R.id.viewPager);
        ll_dot = (LinearLayout) head.findViewById(R.id.ll_dot);


        pageCount = (int) Math.ceil(mData.size() * 1.0 / pageSize);
        mPagerList = new ArrayList<View>();

        for (int i = 0; i < pageCount; i++) {
            //每个页面都是inflate出一个新实例
            GridView gridView = (GridView) LayoutInflater.from(this).inflate(R.layout.gridview, viewPager, false);
            GridViewAdapter gridViewAdapter = new GridViewAdapter(this, titlesData, i, pageSize);
            gridView.setAdapter(gridViewAdapter);
            mPagerList.add(gridView);

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int pos = position + curIndex * pageSize;
                    Toast.makeText(RecyclerViewAndHomeStyleActivity.this, titlesData.get(pos).getNickname(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        //设置适配器
        viewPager.setAdapter(new ViewPagerAdapter(mPagerList));
        //设置圆点
        setOvalLayout();
        mAdapter.addHeaderView(head);

        //添加尾布局
        View foot = LayoutInflater.from(this).inflate(R.layout.item_footer_layout, null);
        mAdapter.addFooterView(foot);

        //设置尾1点击事件
        foot.findViewById(R.id.foot1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RecyclerViewAndHomeStyleActivity.this, "脚一", Toast.LENGTH_SHORT).show();
            }
        });

        //设置尾2点击事件
        foot.findViewById(R.id.foot2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RecyclerViewAndHomeStyleActivity.this, "脚二", Toast.LENGTH_SHORT).show();
            }
        });

        mAdapter.setOnItemClick(new HeadFootRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                Toast.makeText(RecyclerViewAndHomeStyleActivity.this, "position" + position, Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void onRefresh(final RefreshLayout refreshlayout) {
        model.loadData(this);

    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        model.loadMoreData(this);
    }

    @Override
    public void loadDataSuccess(Object data) {

        List<String> tempList = (List<String>) data;
        mData.clear();
        mData.addAll(tempList);

        //判断是否还有更多数据
        if (tempList.size() < 10) {
            //设置不能上拉加载更多
            refreshLayout.setLoadmoreFinished(true);
        } else {
            refreshLayout.setLoadmoreFinished(false);
        }

        //下拉刷新完成
        refreshLayout.finishRefresh();
        //刷新完成
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadDataFail(String error_msg) {

    }

    @Override
    public void loadDataEmpty(String empty_msg) {

    }

    @Override
    public void loadMoreSuccess(Object tempList) {
        List<String> list = (List<String>) tempList;
        mData.addAll(list);

        //刷新完成
        refreshLayout.finishLoadmore();

        //判断是否还有更多数据
        if (list.size() < 10) {
            //设置不能上拉加载更多
            refreshLayout.setLoadmoreFinished(true);
        } else {
            refreshLayout.setLoadmoreFinished(false);
        }

        //刷新界面
        mAdapter.notifyDataSetChanged();
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
