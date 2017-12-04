package com.example.random.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.random.Params.Params;
import com.example.random.Params.Urls;
import com.example.random.R;
import com.example.random.http.LoadDataListener;
import com.example.random.model.UserLoginModel;
import com.example.random.utils.LoadingDialog;

public class TestActivity extends AppCompatActivity implements View.OnClickListener, LoadDataListener {

    UserLoginModel userLoginModel = new UserLoginModel();
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        getSupportActionBar().hide();

        loadingDialog = new LoadingDialog(this, "正在加载");

    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.login:
                loadingDialog.show();
                //登录按钮
                userLoginModel.UserLogin(Urls.baseUrl, Params.login("17607183628", "123456"), this);
                break;
            case R.id.compare:
                loadingDialog.show();
                //比较
                userLoginModel.compare(Urls.baseUrl, this);
                break;
            case R.id.detail:
                loadingDialog.show();
                //查看详情
                userLoginModel.getDetail(Urls.missionUrl, Params.getDetail("2017111414314272048"), this);
                break;
            case R.id.check:
                loadingDialog.show();
                //查看详情
                userLoginModel.getCheck(Urls.baseUrl, "1", this);
                break;
            case R.id.try1:
                intent = new Intent(TestActivity.this, OneActivity.class);
                startActivity(intent);
                break;
            case R.id.try2:
                intent = new Intent(TestActivity.this, TwoActivity.class);
                startActivity(intent);
                break;
            case R.id.try3:
                intent = new Intent(TestActivity.this, ThreeActivity.class);
                startActivity(intent);
                break;
            case R.id.try4:
                intent = new Intent(TestActivity.this, FourActivity.class);
                startActivity(intent);
                break;
        }

    }

    @Override
    public void loadDataSuccess(Object data) {
        loadingDialog.dismiss();
        Toast.makeText(TestActivity.this, (String) data, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void loadDataFail(String error_msg) {
        loadingDialog.dismiss();
        Toast.makeText(TestActivity.this, error_msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadDataEmpty(String empty_msg) {
        loadingDialog.dismiss();
        Toast.makeText(TestActivity.this, empty_msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadMoreSuccess(Object tempList) {

    }
}
