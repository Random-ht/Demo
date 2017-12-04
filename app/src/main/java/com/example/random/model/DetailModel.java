package com.example.random.model;

import android.os.Handler;

import com.example.random.http.LoadDataListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 2017/11/17.
 */

public class DetailModel {

    int a = 0;
    private Handler handler = new Handler();

    public void loadData(final LoadDataListener listener) {

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                a = 0;
                List<String> tempData = new ArrayList<>();
                for (int i = 0; i < 20; i++) {
                    tempData.add("下拉刷新后的第" + (i + 1) + "条数据");
                }
                a = a + 1;
                listener.loadDataSuccess(tempData);
            }
        }, 2000);

    }

    public void loadMoreData(final LoadDataListener listener) {

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<String> tempData = new ArrayList<>();
                if (a <= 3) {
                    for (int i = 0; i < 20; i++) {
                        tempData.add(a + "上拉刷新后的第" + (i + 1) + "条数据");
                    }
                    a = a + 1;
                }
                listener.loadMoreSuccess(tempData);
            }
        }, 2000);

    }

}
