package com.bw.movie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bw.movie.base.BaseActivity;

public class MainActivity extends BaseActivity {

    private Button btn;

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        //setColor(MainActivity.this,Color.BLACK); // 改变状态栏的颜色
        setTranslucent(MainActivity.this); // 改变状态栏变成透明
        btn = bindView(R.id.btn);
    }

    @Override
    protected void initData() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "按钮被触发", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
