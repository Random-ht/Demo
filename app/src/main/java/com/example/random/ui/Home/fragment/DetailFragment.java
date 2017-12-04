package com.example.random.ui.Home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.random.R;
import com.example.random.http.LoadDataListener;
import com.example.random.model.DetailModel;
import com.example.random.ui.Home.adapter.DetailRecycler2Adapter;
import com.example.random.ui.Home.adapter.DetailRecycler3Adapter;
import com.example.random.ui.Home.adapter.DetailRecyclerAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 2017/11/16.
 */

public class DetailFragment extends Fragment implements OnRefreshListener, OnLoadmoreListener
        , LoadDataListener {


    //Fragment的View加载完毕的标记
    private boolean isLoading = false;

    //Fragment对用户可见的标记
    private boolean isUIVisible;
    private String type;
    private int index;
    private View view;
    private TextView text;
    private SmartRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    private List<String> mData = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;
    private DetailModel model = new DetailModel();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_detail, null);

        Bundle bundle = getArguments();
        type = bundle.getString("type");
        index = bundle.getInt("index");

//        initData();
        initView();

        initListener();

        return view;

    }

    private void initData() {
        for (int i = 0; i < 20; i++) {
            mData.add("初始化第" + (i + 1) + "条数据");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("-=-=-=-=-=-", "onDestroy():" + type);
    }

    //setUserVisibleHint和lazyLoad两个方法是为了去除viewPager+fragment的懒加载
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见
        if (isVisibleToUser) {
            isUIVisible = true;
            lazyLoad();
        } else {
            isUIVisible = false;
        }
    }

    private void lazyLoad() {
        //这里进行双重标记判断,是因为setUserVisibleHint会多次回调,并且会在onCreateView执行前回调,必须确保onCreateView加载完毕且页面可见,才加载数据
        if (!isLoading && isUIVisible) {
            //数据加载完毕,恢复标记,防止重复加载
            isLoading = true;
            isUIVisible = false;
            model.loadData(this);
            Log.i("-=-=-=-=-=-", "获取数据");
        }
    }

    /**
     * 初始化控件
     */
    private void initView() {

        text = (TextView) view.findViewById(R.id.text);
        refreshLayout = (SmartRefreshLayout) view.findViewById(R.id.refreshLayout);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        refreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        refreshLayout.setHeaderHeight(60);

        refreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));
        refreshLayout.setFooterHeight(60);

    }

    private void initListener() {

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //下拉刷新
        refreshLayout.setOnRefreshListener(this);
        //上拉加载更多
        refreshLayout.setOnLoadmoreListener(this);

        if (index <= 3) {
            mAdapter = new DetailRecyclerAdapter(getContext(), mData);
        } else if (index > 3 && index <= 6) {
            mAdapter = new DetailRecycler2Adapter(getContext(), mData);
        } else {
            mAdapter = new DetailRecycler3Adapter(getContext(), mData);
        }

        recyclerView.setAdapter(mAdapter);


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
}
