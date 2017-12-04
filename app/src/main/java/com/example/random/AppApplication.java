package com.example.random;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.example.random.utils.CrashUtils;

/**
 * Created by John on 2017/11/24.
 */

public class AppApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //初始化异常捕获的上下文关联
        Utils.init(AppApplication.this);
        //初始化异常捕获工具类
        CrashUtils.init();

    }
}

