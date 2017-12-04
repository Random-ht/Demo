package com.example.random.ui.Me.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.random.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by John on 2017/11/23.
 */

public class ExpandableListViewAdapter extends BaseExpandableListAdapter {

    private Context context;//上下文
    private List<String> parentList = new ArrayList<>();//父集合
    private Map<String, List<String>> childSet = new HashMap<>();//子数据的map集合

    public ExpandableListViewAdapter(Context context, List<String> parentList, Map<String, List<String>> childSet) {
        this.context = context;
        this.parentList = parentList;
        this.childSet = childSet;
    }

    @Override
    public int getGroupCount() {
        //获取父item的个数
        return parentList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        //获取父item 的子item
        return childSet.get(parentList.get(groupPosition)).size();
    }

    //  获得某个父项
    @Override
    public Object getGroup(int groupPosition) {
        //获取父item
        return parentList.get(groupPosition);
    }

    //  获得某个父项的某个子项
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        //获取每一个父item的  子item
        return childSet.get(parentList.get(groupPosition)).get(childPosition);
    }

    //  获得某个父项的id
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    //  获得某个父项的某个子项的id
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    //  按函数的名字来理解应该是是否具有稳定的id，这个函数目前一直都是返回false，没有去改动过
    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * 获取显示指定组的视图对象。
     *
     * @param groupPosition 组位置（决定返回哪个视图）
     * @param isExpanded    该组是展开状态还是伸缩状态
     * @param convertView   重用已有的视图对象。注意：在使用前你应该检查一下这个视图对象是否非空并且这个对象的类型是否合适。
     *                      由此引伸出，如果该对象不能被转换并显示正确的数据，
     *                      这个方法就会调用getGroupView(int, boolean, View, ViewGroup)来创建一个视图(View)对象。
     * @param parent        返回的视图对象始终依附于的视图组
     * @return 返回指定组的视图对象
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_expandable_list_parent, null);
        }
        convertView.setTag(R.layout.item_expandable_list_parent, groupPosition);
        convertView.setTag(R.layout.item_expandable_list_child, -1);
        TextView text = (TextView) convertView.findViewById(R.id.patent_content);
        text.setText(parentList.get(groupPosition));

        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);

        //判断这一组是否展开  true表示展开  false表示没展开
        if (isExpanded) {
            imageView.setBackgroundResource(R.mipmap.to_bottom);
        } else {
            imageView.setBackgroundResource(R.mipmap.to_right);
        }

        return convertView;
    }

    /**
     * @param groupPosition
     * @param childPosition 子元素位置（决定返回哪个视图）
     * @param isLastChild   子元素是否处于组中的最后一个
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_expandable_list_child, null);
        }
        convertView.setTag(R.layout.item_expandable_list_parent, groupPosition);
        convertView.setTag(R.layout.item_expandable_list_child, childPosition);
        TextView text = (TextView) convertView.findViewById(R.id.child_content);
        text.setText(childSet.get(parentList.get(groupPosition)).get(childPosition));
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "点到了父item" + groupPosition + "，子item" + childPosition + "内置的textView",
                        Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

    //  子项是否可选中，如果需要设置子项的点击事件，需要返回true
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
