package com.example.random.http;

/**
 * Created by John on 2017/11/14.
 */

public interface LoadDataListener {
    void loadDataSuccess(Object data);

    void loadDataFail(String error_msg);

    void loadDataEmpty(String empty_msg);

    void loadMoreSuccess(Object tempList);
}
