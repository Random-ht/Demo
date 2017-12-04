package com.example.random.ui.Home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.random.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by John on 2017/11/17.
 */

public class DetailRecycler3Adapter extends RecyclerView.Adapter<DetailRecycler3Adapter.ViewHolder> {

    private Context context;
    private List<String> mData = new ArrayList<>();

    public DetailRecycler3Adapter(Context context, List<String> mData) {
        this.context = context;
        this.mData = mData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_detail3_layout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.content.setText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_content)
        TextView content;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
