package com.example.random.ui.More.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.random.R;
import com.example.random.customView.AutoPollRecyclerView;
import com.example.random.ui.More.adapter.AutoPollAdapter;

import java.util.ArrayList;
import java.util.List;

public class AutoScrollActivity extends AppCompatActivity {

    private AutoPollRecyclerView recyclerView;
    private AutoPollAdapter mAdapter;

    private List<String> mData = new ArrayList<>();
    private TextView more;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_scroll);
        getSupportActionBar().hide();

        initData();
        initView();
        initListener();

    }

    /**
     * 初始化数据
     */
    private void initData() {
        for (int i = 0; i < 30; i++) {
            mData.add("这是第" + (i + 1) + "条数据");
        }
    }

    /**
     * 初始化控件
     */
    private void initView() {
        recyclerView = (AutoPollRecyclerView) findViewById(R.id.recyclerView);
    }


    /**
     * 初始化监听
     */
    private void initListener() {

        //布局管理器设置水平，则就是水平方向滚动， 设置垂直则就是垂直方向滚动
        recyclerView.setLayoutManager(new LinearLayoutManager(AutoScrollActivity.this, LinearLayoutManager.VERTICAL, false));

        mAdapter = new AutoPollAdapter(AutoScrollActivity.this, mData);
        recyclerView.setAdapter(mAdapter);

        //调用start()之后才会滚动,也可以在此做判断当满足你的需求的时候再滚动,但是还要在adapter的getItemCount()和onBindViewHolder()做相应的处理
        if (true) recyclerView.start();


        //设置点击事件
        mAdapter.setOnItemClickListener(new AutoPollAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                Toast.makeText(AutoScrollActivity.this, "点击了第" + (position + 1) + "个条目", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void OnContentClick(int position) {
                Toast.makeText(AutoScrollActivity.this, "点击了第" + (position + 1) + "条内容", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
