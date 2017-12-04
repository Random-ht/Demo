package com.example.random.model;

import android.util.Log;

import com.example.random.Entity.Result;
import com.example.random.Params.Constant;
import com.example.random.http.LoadDataListener;
import com.example.random.http.LoadDataService;

import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by John on 2017/11/14.
 */

public class UserLoginModel {

    private LoadDataService loadDataService;

    /**
     * 用户的登录
     *
     * @param url
     * @param requestBody
     * @param listener
     */
    public void UserLogin(String url, RequestBody requestBody, final LoadDataListener listener) {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        loadDataService = retrofit.create(LoadDataService.class);

        loadDataService.loginService(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result>() {
                    @Override
                    public void onCompleted() {
                        Log.i("-=-=-=-=-=-=", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("-=-=-=-=-=-=", e.getMessage());
                        listener.loadDataFail(e.getMessage());
                    }

                    @Override
                    public void onNext(Result result) {

                        if (result == null) {
                            Log.i("-=-=-=-=-=-=", "数据为空");
                            return;
                        }
                        if (Constant.SUCCESS_CODE == result.getCode()) {
                            listener.loadDataSuccess(result.getData().toString());
                            Log.i("-=-=-=-=-=-=", result.getData().toString());

                        } else {
                            Log.i("-=-=-=-=-=-=", "加载失败");

                        }
                    }
                });
    }

    /**
     * 比较
     *
     * @param url
     * @param listener
     */
    public void compare(String url, final LoadDataListener listener) {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(url)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        loadDataService = retrofit.create(LoadDataService.class);

        loadDataService.compare()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result>() {
                    @Override
                    public void onCompleted() {
                        Log.i("-=-=-=-=-=-=", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("-=-=-=-=-=-=", "onError");
                        listener.loadDataFail(e.getMessage());
                    }

                    @Override
                    public void onNext(Result result) {
                        Log.i("-=-=-=-=-=-=", result.getData().toString());
                        listener.loadDataFail(result.getData().toString());
                    }
                });
    }

    /**
     * 获取详情
     *
     * @param url
     * @param requestBody
     * @param listener
     */
    public void getDetail(String url, RequestBody requestBody, final LoadDataListener listener) {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(url)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        loadDataService = retrofit.create(LoadDataService.class);

        loadDataService.getDetail(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result>() {
                    @Override
                    public void onCompleted() {
                        Log.i("-=-=-=-=-=-=", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("-=-=-=-=-=-=", "onError");
                        listener.loadDataFail(e.getMessage());
                    }

                    @Override
                    public void onNext(Result result) {
                        Log.i("-=-=-=-=-=-=", result.getData().toString());
                        listener.loadDataFail(result.getData().toString());
                    }
                });

    }


    /**
     * 是否可升级
     * @param url
     * @param id
     * @param listener
     */
    public void getCheck(String url,String id,final LoadDataListener listener){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
         loadDataService = retrofit.create(LoadDataService.class);

        loadDataService.getCanByCrowd(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result>() {
                    @Override
                    public void onCompleted() {
                        Log.i("-=-=-=-=-=-=", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("-=-=-=-=-=-=", "onError");
                        listener.loadDataFail(e.getMessage());
                    }

                    @Override
                    public void onNext(Result result) {
                        Log.i("-=-=-=-=-=-=", result.getData().toString());
                        listener.loadDataFail(result.getData().toString());
                    }
                });



    }

}
