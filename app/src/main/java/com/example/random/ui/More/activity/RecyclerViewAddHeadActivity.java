package com.example.random.ui.More.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.random.R;
import com.example.random.ui.More.adapter.HeadFootRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAddHeadActivity extends AppCompatActivity {

    private List<String> mData = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_add_head);
        getSupportActionBar().hide();
        initData();
        initView();
        initListener();
    }

    //初始化数据
    private void initData() {
        for (int i = 0; i < 30; i++) {
            mData.add("这是第" + (i + 1) + "条");
        }
    }

    //初始化控件
    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }

    //初始化监听
    private void initListener() {
//        LinearLayoutManager manager = new GridLayoutManager(this, 2);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        HeadFootRecyclerViewAdapter mAdapter = new HeadFootRecyclerViewAdapter(this, mData);
        recyclerView.setAdapter(mAdapter);

        //添加尾布局
        View foot = LayoutInflater.from(this).inflate(R.layout.item_footer_layout, null);
        mAdapter.addFooterView(foot);

        //设置尾1点击事件
        foot.findViewById(R.id.foot1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RecyclerViewAddHeadActivity.this, "脚一", Toast.LENGTH_SHORT).show();
            }
        });

        //设置尾2点击事件
        foot.findViewById(R.id.foot2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RecyclerViewAddHeadActivity.this, "脚二", Toast.LENGTH_SHORT).show();
            }
        });

        //添加头布局
        View head = LayoutInflater.from(this).inflate(R.layout.item_header_layout, null);
        mAdapter.addHeaderView(head);

        //设置头1点击事件
        head.findViewById(R.id.head1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RecyclerViewAddHeadActivity.this, "头一", Toast.LENGTH_SHORT).show();
            }
        });

        //设置头2点击事件
        head.findViewById(R.id.head2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RecyclerViewAddHeadActivity.this, "头二", Toast.LENGTH_SHORT).show();
            }
        });

        mAdapter.setOnItemClick(new HeadFootRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                Toast.makeText(RecyclerViewAddHeadActivity.this, "条目" + position, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
