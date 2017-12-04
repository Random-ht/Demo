package com.example.random.ui.More.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.random.Entity.User;
import com.example.random.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 2017/12/4.
 */

public class TigerAdapter extends BaseAdapter {

    public TigerAdapter(Context context, List<User> mData) {
        this.context = context;
        this.mData = mData;
    }

    private Context context;
    private List<User> mData = new ArrayList<>();

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_tiger, null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.layout = (LinearLayout) convertView.findViewById(R.id.layout);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position == 4) {
            holder.imageView.setImageResource(R.mipmap.start_tiger);
            holder.layout.setBackgroundColor(context.getResources().getColor(R.color.google_red));
            holder.name.setVisibility(View.GONE);
        } else {
            holder.name.setVisibility(View.VISIBLE);
            holder.layout.setBackgroundColor(context.getResources().getColor(R.color.white));
            holder.imageView.setImageResource(mData.get(position).getIconRes());
            holder.name.setText(mData.get(position).getNickname());
        }

        return convertView;
    }

    class ViewHolder {
        ImageView imageView;
        TextView name;
        LinearLayout layout;
    }
}
