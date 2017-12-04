package com.example.random.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.random.R;
import com.example.random.customView.AnimotionPopupWindow;

import java.util.ArrayList;
import java.util.List;

public class FourActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.zhifubao:

                break;
            case R.id.weixin:

                break;
            case R.id.pop_window:
                final List<String> list = new ArrayList<>();
                list.add("修改资料");
                list.add("切换账号");
                list.add("登录账号");
                list.add("退出账号");

                AnimotionPopupWindow popupWindow = new AnimotionPopupWindow(this, list);
                popupWindow.show();
                popupWindow.setAnimotionPopupWindowOnClickListener(new AnimotionPopupWindow.AnimotionPopupWindowOnClickListener() {
                    @Override
                    public void onPopWindowClickListener(int position) {
                        Toast.makeText(FourActivity.this, "点击了:" + list.get(position), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }
}
