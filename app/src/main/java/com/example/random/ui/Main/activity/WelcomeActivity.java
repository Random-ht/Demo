package com.example.random.ui.Main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.random.R;
import com.example.random.bean.ModelSVG;
import com.gyf.barlibrary.ImmersionBar;
import com.jaredrummler.android.widget.AnimatedSvgView;
import com.yasic.library.particletextview.View.ParticleTextView;

public class WelcomeActivity extends AppCompatActivity {

    Handler handler = new Handler();
    private AnimatedSvgView mSvgView;
    private ParticleTextView particleTextView;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);  //全屏
        setContentView(R.layout.activity_welcome);
        getSupportActionBar().hide();
//        ImmersionBar.with(this).init();
        ImmersionBar.with(this)
//                .statusBarColor(R.color.colorPrimary)
//                .navigationBarColor(R.color.btn8)
                .init();


        //初始化控件
        initView();
        //初始化动画
        initAnim();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mSvgView = (AnimatedSvgView) findViewById(R.id.animated_svg_view);
        text = (TextView) findViewById(R.id.text);
//        particleTextView = (ParticleTextView) findViewById(R.id.path);//这个是设置文字动画的
    }


    /**
     * 初始化动画
     */
    private void initAnim() {

        //设置文字动画效果
//        RandomMovingStrategy strategy = new RandomMovingStrategy();
////        CornerStrategy strategy = new CornerStrategy();
////        HorizontalStrategy strategy = new HorizontalStrategy();
//        ParticleTextViewConfig config = new ParticleTextViewConfig.Builder()
//                .setRowStep(8)
//                .setColumnStep(8)
//                .setTargetText("Welcome")
//                .setReleasing(0.2)
//                .setParticleRadius(4)
//                .setMiniDistance(0.1)
//                .setTextSize(100)
//                .setMovingStrategy(strategy)
//                .instance();
//        particleTextView.setConfig(config);
//        particleTextView.startAnimation();

        //设置500毫秒后   图片开始动画
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setSvg(ModelSVG.values()[6]);//设置第几种图片效果
//                text.setVisibility(View.VISIBLE);
            }
        }, 500);

        //设置4000毫秒后  关闭欢迎界面
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
//                particleTextView.stopAnimation();//文字动画效果停止
                overridePendingTransition(0, 0);
                finish();
            }
        }, 4000);

    }

    /**
     * 设置图片动画
     *
     * @param modelSvg
     */
    private void setSvg(ModelSVG modelSvg) {
        mSvgView.setGlyphStrings(modelSvg.glyphs);
        mSvgView.setFillColors(modelSvg.colors);
        mSvgView.setViewportSize(modelSvg.width, modelSvg.height);
        mSvgView.setTraceResidueColor(0x32000000);
        mSvgView.setTraceColors(modelSvg.colors);
        mSvgView.rebuildGlyphData();
        mSvgView.start();
    }

}
