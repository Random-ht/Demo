package com.example.random.ui.Me.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.random.EventBus.EventManager;
import com.example.random.EventBus.PostEvent;
import com.example.random.R;
import com.example.random.ui.Me.adapter.ExpandableListViewAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by John on 2017/11/15.
 */

public class MeFragment extends Fragment {

    private View view;
    private ExpandableListView listView;
    private ExpandableListViewAdapter mAdapter;
    private Map<String, List<String>> childSet;
    private List<String> parentList;
    private SmartRefreshLayout refreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_me, null);

        EventManager.register(this);

        //初始化数据
        initData();
        //初始化控件
        initView();
        //初始化监听
        initListener();

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventManager.unregister(this);
    }

    /**
     * 初始化数据
     */
    private void initData() {

        parentList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            parentList.add("父item第" + i + "个");
        }

        childSet = new HashMap<>();
        List<String> childList1 = new ArrayList<>();
        List<String> childList2 = new ArrayList<>();
        List<String> childList3 = new ArrayList<>();
        List<String> childList4 = new ArrayList<>();
        List<String> childList5 = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            childList1.add("第一个子" + i);
        }
        for (int i = 0; i < 5; i++) {
            childList2.add("第二个子" + i);
        }
        for (int i = 0; i < 8; i++) {
            childList3.add("第三个子" + i);
        }
        for (int i = 0; i < 3; i++) {
            childList4.add("第四个子" + i);
        }
        for (int i = 0; i < 5; i++) {
            childList5.add("第五个子" + i);
        }

        childSet.put(parentList.get(0), childList1);
        childSet.put(parentList.get(1), childList2);
        childSet.put(parentList.get(2), childList3);
        childSet.put(parentList.get(3), childList4);
        childSet.put(parentList.get(4), childList5);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        listView = (ExpandableListView) view.findViewById(R.id.expandableList);
        refreshLayout = (SmartRefreshLayout) view.findViewById(R.id.refreshLayout);
    }

    /**
     * 初始化监听
     */
    private void initListener() {

        refreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        refreshLayout.setHeaderHeight(60);

        refreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));
        refreshLayout.setFooterHeight(60);

        refreshLayout.setEnableRefresh(true);//是否启用下拉刷新功能
        refreshLayout.setEnableLoadmore(false);//是否启用上拉加载功能  true表示启用该功能  默认是true   false不启动

        //下拉刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                //下拉刷新
                handler.sendEmptyMessage(1);
            }
        });


        mAdapter = new ExpandableListViewAdapter(getContext(), parentList, childSet);
        listView.setAdapter(mAdapter);


        //默认让所有的列表展开
//        for (int i = 0; i < mAdapter.getGroupCount(); i++) {
//            listView.expandGroup(i);
//        }

        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(getContext(), "点击父item第" + groupPosition + "个" + "的第" + childPosition + "子item", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String content = "";
                //在适配器里面setTag用于这里的区分
                if ((int) view.getTag(R.layout.item_expandable_list_child) == -1) {
                    content = "父类第" + view.getTag(R.layout.item_expandable_list_parent) + "项" + "被长按了";
                } else {
                    content = "父类第" + view.getTag(R.layout.item_expandable_list_parent) + "项" + "中的"
                            + "子类第" + view.getTag(R.layout.item_expandable_list_child) + "项" + "被长按了";
                }
                Toast.makeText(getContext(), content, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    //这个map集合是用于保存   在刷新数据之前哪些父item是展开的
    Map<Integer, Boolean> isSelected = new HashMap<>();


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:

                    //刷新之前  有哪些父item是展开的   并保存到Map集合中
                    for (int i = 0; i < parentList.size(); i++) {
                        if (listView.isGroupExpanded(i)) {
                            isSelected.put(i, true);
                        } else {
                            isSelected.put(i, false);
                        }
                    }

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Map<List<String>, Map<String, List<String>>> refreshed = refreshed();
                            //遍历map集合获取键和值
                            Set<Map.Entry<List<String>, Map<String, List<String>>>> entries = refreshed.entrySet();
                            List<String> key = new ArrayList<String>();
                            Map<String, List<String>> value = new HashMap<String, List<String>>();
                            for (Map.Entry<List<String>, Map<String, List<String>>> map : entries) {
                                key = map.getKey();
                                value = map.getValue();

                            }

                            parentList = key;//为了在多次刷新的时候更改map集合的数据

                            mAdapter = new ExpandableListViewAdapter(getContext(), key, value);
                            listView.setAdapter(mAdapter);

                            //如果刷新出来的父item的size大于存值的map集合的size，则直接下一步，如果小于，则需要遍历map的时候就要控制遍历的次数，不然会角标越界
                            if (key.size() >= isSelected.size()) {
                                //从map集合取值   判断哪些父item是展开的
                                for (int i = 0; i < isSelected.size(); i++) {
                                    if (isSelected.get(i)) {
                                        listView.expandGroup(i);//展开对应的父item
                                    }
                                }
                            } else {
                                //从map集合取值   判断哪些父item是展开的
                                for (int i = 0; i < key.size(); i++) {
                                    if (isSelected.get(i)) {
                                        listView.expandGroup(i);//展开对应的父item
                                    }
                                }
                            }


                            mAdapter.notifyDataSetChanged();

                            refreshLayout.finishRefresh();
                            refreshLayout.setLoadmoreFinished(true);

                        }
                    }, 2000);
                    break;
            }
        }
    };

    //刷新之后获取的数据
    private Map<List<String>, Map<String, List<String>>> refreshed() {

        List<String> refreshParent = new ArrayList<String>();
        for (int i = 0; i < 6; i++) {
            refreshParent.add("刷新后的父item" + i);
        }

        Map<String, List<String>> refreshChildSet = new HashMap<>();
        List<String> childList1 = new ArrayList<>();
        List<String> childList2 = new ArrayList<>();
        List<String> childList3 = new ArrayList<>();
        List<String> childList4 = new ArrayList<>();
        List<String> childList5 = new ArrayList<>();
        List<String> childList6 = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            childList1.add("刷新后的第一个子" + i);
        }
        for (int i = 0; i < 5; i++) {
            childList2.add("刷新后的第二个子" + i);
        }
        for (int i = 0; i < 8; i++) {
            childList3.add("刷新后的第三个子" + i);
        }
        for (int i = 0; i < 3; i++) {
            childList4.add("刷新后的第四个子" + i);
        }
        for (int i = 0; i < 5; i++) {
            childList5.add("刷新后的第五个子" + i);
        }
        for (int i = 0; i < 6; i++) {
            childList6.add("刷新后的第六个子" + i);
        }

        refreshChildSet.put(refreshParent.get(0), childList1);
        refreshChildSet.put(refreshParent.get(1), childList2);
        refreshChildSet.put(refreshParent.get(2), childList3);
        refreshChildSet.put(refreshParent.get(3), childList4);
        refreshChildSet.put(refreshParent.get(4), childList5);
        refreshChildSet.put(refreshParent.get(5), childList6);

        Map<List<String>, Map<String, List<String>>> map = new HashMap<>();
        map.put(refreshParent, refreshChildSet);
        return map;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(PostEvent event) {
        switch (event.what) {
            case 1124:
                //关闭所有展开的group   从MainActivity传递过来的  双击关闭所有展开的父item
                for (int i = 0; i < parentList.size(); i++) {
                    if (listView.isGroupExpanded(i)) {
                        listView.collapseGroup(i);
                    }
                }
                break;
        }
    }


}
