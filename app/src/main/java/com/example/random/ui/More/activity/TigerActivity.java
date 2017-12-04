package com.example.random.ui.More.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.random.Entity.User;
import com.example.random.R;
import com.example.random.ui.More.adapter.TigerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TigerActivity extends AppCompatActivity {

    private GridView gridView;
    private List<User> mData = new ArrayList<>();
    private TigerAdapter tigerAdapter;

    // 对应转盘id的数组
    private int[] array = {0, 1, 2, 5, 8, 7, 6, 3};

    private int num;//数组的第几个

    private MyRunnable runnable;

    private int stopTime;//停止时间

    private int startTime;//开始时间

    private int speed;//切换速度

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiger);
        getSupportActionBar().hide();

        initData();
        initView();
        initListener();
    }

    //初始化数据
    private void initData() {
        for (int i = 0; i < 9; i++) {
            int imageId;
            if (i == 4) {
                //动态获取资源ID，第一个参数是资源名，第二个参数是资源类型例如drawable，string等，第三个参数包名
                imageId = getResources().getIdentifier("ic_category_" + 10, "mipmap", getPackageName());
            } else {
                //动态获取资源ID，第一个参数是资源名，第二个参数是资源类型例如drawable，string等，第三个参数包名
                imageId = getResources().getIdentifier("ic_category_" + i, "mipmap", getPackageName());
            }
            User user = new User();
            user.setNickname((i + 1) + "等奖");
            user.setIconRes(imageId);
            mData.add(user);
        }
    }

    //初始化控件
    private void initView() {
        gridView = (GridView) findViewById(R.id.gridView);
        //取消GridView点击效果
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        tigerAdapter = new TigerAdapter(this, mData);
        gridView.setAdapter(tigerAdapter);
    }

    //初始化监听
    private void initListener() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 4) {
                    Toast.makeText(TigerActivity.this, "点击了开始", Toast.LENGTH_SHORT).show();
                    gridView.setEnabled(false);

                    //定义一个随机数最为结束的时间，这里是2到6秒
                    stopTime = new Random().nextInt(1000 * 3) + 2000;

                    //开启线程
                    runnable = new MyRunnable();
                    new Thread(runnable).start();
                }
            }
        });
    }

    class MyRunnable implements Runnable {
        @Override
        public void run() {
            handler.sendEmptyMessage(0);  //发送消息
            //如果到达指定的时间,则停止
            if (startTime >= stopTime) {
                handler.removeCallbacks(runnable);
                //提示中奖消息
                if (array[num] < 4) {
                    String text = array[num] + 1 + "";
                    Toast.makeText(TigerActivity.this, "恭喜你中了" + text, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TigerActivity.this, "恭喜你中了" + array[num], Toast.LENGTH_SHORT).show();
                }
                gridView.setEnabled(true);//设置在滚动完之后才可以点击

                startTime = 0;
                stopTime = 0;
                speed = 10;
                return;
            }
            //每隔100毫秒运行一次     每个多少秒切换到下一个
            handler.postDelayed(runnable, speed);
            speed += 20;
            startTime += 200;
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ChangeSelectorImage(array[num]);
            num++;                 //依次下一个
            //如果到了最后一个item，则循环
            if (num >= 8) {
                num = 0;
            }
        }
    };

    /**
     * 处理图片被选中时候的颜色
     *
     * @param id 第几个图片
     */
    private void ChangeSelectorImage(int id) {
        for (int i = 0; i < gridView.getChildCount(); i++) {
            if (i == id) {
                //如果是选中的，则改变图片为数组2中的图片
                ((gridView.getChildAt(i).findViewById(R.id.layout))).setBackgroundColor(getResources().getColor(R.color.google_red));
            } else if (i == 4) {
                //如果是到了中间那个，则跳出
                continue;
            } else {
                //未选中的就设置为数组1中的图片
                ((gridView.getChildAt(i).findViewById(R.id.layout))).setBackgroundColor(getResources().getColor(R.color.white));
            }
        }
    }

}
