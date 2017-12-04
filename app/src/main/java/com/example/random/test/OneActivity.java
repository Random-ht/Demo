package com.example.random.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.random.Entity.User;
import com.example.random.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 动态添加 布局
 */
public class OneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_actvity);
        getSupportActionBar().hide();

        List<User> mData = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            User user = new User();
            user.setNickname("第" + (i + 1) + "个名字");
            user.setUid("第" + (i + 1) + "个UID");
            mData.add(user);
        }

        LinearLayout layout = (LinearLayout) findViewById(R.id.add_layout);

        for (int i = 0; i < mData.size(); i++) {
            final User user = mData.get(i);
            View view = LayoutInflater.from(this).inflate(R.layout.layout_add, null);
            TextView content = (TextView) view.findViewById(R.id.content);
            content.setText(user.getNickname());

            layout.addView(view);
            content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(OneActivity.this, user.getUid(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}
