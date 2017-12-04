package com.example.random.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.random.R;
import com.gyf.barlibrary.ImmersionBar;

public class ThreeActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);

        getSupportActionBar().hide();
        ImmersionBar.with(this)
                .statusBarView(R.id.view_top)
                .statusBarDarkFont(true)
                .init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this)
                .destroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.test:
                Toast.makeText(ThreeActivity.this, "点击了", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
