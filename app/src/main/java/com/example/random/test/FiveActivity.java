package com.example.random.test;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.random.R;

public class FiveActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout ll_parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five);
        getSupportActionBar().hide();
        initView();

    }

    private void initView() {
        ll_parent = (LinearLayout) findViewById(R.id.ll_parent);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.show_pop:
                ShowPop();
                break;
        }
    }

    private void ShowPop() {
        View view = LayoutInflater.from(this).inflate(R.layout.show_pop, null);
        TextView content = (TextView) view.findViewById(R.id.content);

        final PopupWindow pop = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);

        // 使其聚集 ，要想监听菜单里控件的事件就必须要调用此方法
//        pop.setFocusable(true);
//        // 设置允许在外点击消失
//        pop.setOutsideTouchable(false);
//        // 设置背景，这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
//        pop.setBackgroundDrawable(new BitmapDrawable());
//        //软键盘不会挡着popupWindow
//        pop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//        pop.setAnimationStyle(R.style.showPopupAnimation);
//        //设置菜单显示的位置
//        pop.showAtLocation(ll_parent, Gravity.BOTTOM, 0, 0);

        pop.setOutsideTouchable(true);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setAnimationStyle(R.style.showPopupAnimation);
        pop.showAtLocation(view, Gravity.CENTER, 0, 0);

        content.setText("见了");
        content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
            }
        });
    }
}
