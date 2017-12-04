package com.example.random.ui.More.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.random.R;
import com.example.random.ui.More.activity.AutoScrollActivity;
import com.example.random.ui.More.activity.MoreAboutAppActivity;
import com.example.random.ui.More.activity.RecyclerViewAddHeadActivity;
import com.example.random.ui.More.activity.RecyclerViewAndHomeStyleActivity;
import com.example.random.ui.More.activity.TigerActivity;
import com.example.random.ui.More.activity.WaiMaiHomeStyleActivity;

/**
 * Created by John on 2017/11/15.
 */

public class MoreFragment extends Fragment implements View.OnClickListener {

    private View view;
    private TextView more_config;
    private TextView home_style;
    private TextView auto_scroll;
    private TextView addHead;
    private TextView recyclerView_and_home_style;
    private TextView tiger;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_more, null);

        initView();
        initListener();
        return view;
    }

    /**
     * 初始化控件
     */
    private void initView() {
        more_config = (TextView) view.findViewById(R.id.more_config);
        home_style = (TextView) view.findViewById(R.id.home_style);
        auto_scroll = (TextView) view.findViewById(R.id.auto_scroll);
        addHead = (TextView) view.findViewById(R.id.recyclerView_add_head);
        recyclerView_and_home_style = (TextView) view.findViewById(R.id.recyclerView_and_home_style);
        tiger = (TextView) view.findViewById(R.id.tiger);

    }

    /**
     * 初始化监听
     */
    private void initListener() {
        more_config.setOnClickListener(this);
        home_style.setOnClickListener(this);
        auto_scroll.setOnClickListener(this);
        addHead.setOnClickListener(this);
        recyclerView_and_home_style.setOnClickListener(this);
        tiger.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.more_config:
                startActivity(new Intent(getContext(), MoreAboutAppActivity.class));
                break;
            case R.id.auto_scroll:
                startActivity(new Intent(getActivity(), AutoScrollActivity.class));
                break;
            case R.id.home_style:
                startActivity(new Intent(getActivity(), WaiMaiHomeStyleActivity.class));
                break;
            case R.id.recyclerView_add_head:
                startActivity(new Intent(getActivity(), RecyclerViewAddHeadActivity.class));
                break;
            case R.id.recyclerView_and_home_style:
                startActivity(new Intent(getActivity(), RecyclerViewAndHomeStyleActivity.class));
                break;
            case R.id.tiger:
                startActivity(new Intent(getActivity(), TigerActivity.class));
                break;
        }
    }
}
