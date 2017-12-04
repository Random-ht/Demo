package com.example.random.utils;

/**
 * Created by Administrator on 2017/1/4.
 */

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.random.R;


public class LoadingDialog extends Dialog {
    private String title;
    private TextView tv;
    private ProgressBar progressBar;

    public LoadingDialog(Context context) {
        super(context, R.style.loadingDialogStyle);
    }

    private LoadingDialog(Context context, int theme) {
        super(context, theme);
    }

    public LoadingDialog(Context context, String title) {
        super(context, R.style.loadingDialogStyle);
        this.title = title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);
        //屏蔽触摸dialog外部  dialog消失事件
        setCanceledOnTouchOutside(false);
//        setCancelable(false);
        tv = (TextView) this.findViewById(R.id.tv);
        progressBar = (ProgressBar) this.findViewById(R.id.progressBar1);
        
        if (TextUtils.isEmpty(title)){
            tv.setVisibility(View.GONE);
        }else {
            tv.setVisibility(View.VISIBLE);
            tv.setText(title + "...");
        }

    }
}
