package com.example.random.ui.More.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.random.R;

import java.util.List;

/**
 * Created by John on 2017/11/21.
 */

public class AutoPollAdapter extends RecyclerView.Adapter<AutoPollAdapter.BaseViewHolder> {
    private final Context mContext;
    private final List<String> mData;

    public AutoPollAdapter(Context context, List<String> list) {
        this.mContext = context;
        this.mData = list;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
        BaseViewHolder holder = new BaseViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, final int position) {
//        String data = mData.get(position % mData.size());
//        String data = mData.get(position);
//        holder.content.setText(data);
        String data;
        if (mData.size() > 0) {
            data = mData.get(position % mData.size());
        } else {
            data = mData.get(position);
        }
        holder.content.setText(data);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnItemClick(position % mData.size());
            }
        });

        holder.content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnContentClick(position % mData.size());
            }
        });

    }

    /**
     * 如果想无限循环滚动，则返回的是Integer.MAX_VALUE   在onBindViewHolder中取值的时候也要做相应的处理
     * 但是有一个缺点，就是在没有数据的时候就会报错（角标越界异常）
     * 所以在返回  Integer.MAX_VALUE  的时候需要判断mData的size是否为0，
     * 如果为0 的话就 return mData.size() == 0 ? 0 : Integer.MAX_VALUE;
     *
     * @return
     */
    @Override
    public int getItemCount() {
//        return Integer.MAX_VALUE;
//        return mData.size();
        return mData.size() == 0 ? 0 : Integer.MAX_VALUE;
    }

    class BaseViewHolder extends RecyclerView.ViewHolder {
        TextView content;
        LinearLayout layout;

        public BaseViewHolder(View itemView) {
            super(itemView);
            content = (TextView) itemView.findViewById(R.id.content);
            layout = (LinearLayout) itemView.findViewById(R.id.layout);
        }
    }


    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void OnItemClick(int position);

        void OnContentClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
