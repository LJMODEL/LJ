package com.bw.movie;

import android.content.Intent;
import android.content.SharedPreferences;

import com.bw.movie.activity.GuideActivity;
import com.bw.movie.activity.LogActivity;
import com.bw.movie.base.BaseActivity;

public class MainActivity extends BaseActivity {
    //是否是第一次使用
    private boolean isFirstUse;
    private SharedPreferences preferences;

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(2000);//休眠3秒
                    preferences = getSharedPreferences("isFirstUse", MODE_PRIVATE);
                    isFirstUse = preferences.getBoolean("isFirstUse", true);
                    if (isFirstUse) {
                        Intent intent = new Intent(MainActivity.this, GuideActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(MainActivity.this, LogActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    //实例化Editor对象
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("isFirstUse", false);
                    //提交修改
                    editor.commit();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        //setColor(MainActivity.this,Color.BLACK); // 改变状态栏的颜色
        // setTranslucent(MainActivity.this); // 改变状态栏变成透明
    }
    @Override
    protected void initData() {
    }
}
