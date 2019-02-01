package com.bw.movie.activity;

import android.animation.ObjectAnimator;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bw.movie.R;
import com.bw.movie.fragment.Fragment_Cinema;
import com.bw.movie.fragment.Fragment_Film;
import com.bw.movie.fragment.Fragment_My;

public class FilmActivity extends AppCompatActivity {
    private FrameLayout frame;
    private FragmentManager manager;
    private RadioButton main_rg_rb1;
    private RadioButton main_rg_rb2;
    private RadioButton main_rg_rb3;
    private RadioGroup main_rg;
    private Fragment[] mFragments;
    private static final String HOME_FRAGMENT_KEY = "homeFragment";
    private static final String DASHBOARD_FRAGMENT_KEY = "DashboardFragment";
    private static final String NOTICE_FRAGMENT_KEY = "NoticeFragment";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film);
        initView();
        mFragments = new Fragment[3];
        mFragments[0] = new Fragment_Film();
        mFragments[1] = new Fragment_Cinema();
        mFragments[2] = new Fragment_My();
        manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame, mFragments[0], "0");
        transaction.commit();
        main_rg_rb1.setScaleX(1);
        main_rg_rb1.setScaleY(1);
        main_rg_rb2.setScaleX((float) 0.8);
        main_rg_rb2.setScaleY((float) 0.8);
        main_rg_rb3.setScaleX((float) 0.8);
        main_rg_rb3.setScaleY((float) 0.8);

        main_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                //找到所选图标的标签并转换为整数类型放到下面的方法中
                int i = Integer.parseInt(rb.getTag().toString().trim());
                showAndHideFragment(i);
                switch (checkedId) {
                    case R.id.main_rg_rb1:
                        main_rg_rb1.setScaleX(1);
                        main_rg_rb1.setScaleY(1);
                        main_rg_rb2.setScaleX((float) 0.8);
                        main_rg_rb2.setScaleY((float) 0.8);
                        main_rg_rb3.setScaleX((float) 0.8);
                        main_rg_rb3.setScaleY((float) 0.8);
                        ObjectAnimator ra = ObjectAnimator.ofFloat(main_rg_rb1, "rotation", 0f, 360f);
                        ra.setDuration(1000);
                        ra.start();
                        break;
                    case R.id.main_rg_rb2:
                        main_rg_rb2.setScaleX(1);
                        main_rg_rb2.setScaleY(1);
                        main_rg_rb1.setScaleX((float) 0.8);
                        main_rg_rb1.setScaleY((float) 0.8);
                        main_rg_rb3.setScaleX((float) 0.8);
                        main_rg_rb3.setScaleY((float) 0.8);
                        ObjectAnimator ra1 = ObjectAnimator.ofFloat(main_rg_rb2, "rotation", 0f, 360f);
                        ra1.setDuration(1000);
                        ra1.start();
                        break;
                    case R.id.main_rg_rb3:
                        main_rg_rb3.setScaleX(1);
                        main_rg_rb3.setScaleY(1);
                        main_rg_rb2.setScaleX((float) 0.8);
                        main_rg_rb2.setScaleY((float) 0.8);
                        main_rg_rb1.setScaleX((float) 0.8);
                        main_rg_rb1.setScaleY((float) 0.8);
                        ObjectAnimator ra2 = ObjectAnimator.ofFloat(main_rg_rb3, "rotation", 0f, 360f);
                        ra2.setDuration(1000);
                        ra2.start();
                        break;
                }
            }
        });
    }

    private void initView() {
        frame = (FrameLayout) findViewById(R.id.frame);
        main_rg_rb1 = (RadioButton) findViewById(R.id.main_rg_rb1);
        main_rg_rb2 = (RadioButton) findViewById(R.id.main_rg_rb2);
        main_rg_rb3 = (RadioButton) findViewById(R.id.main_rg_rb3);
        main_rg = (RadioGroup) findViewById(R.id.main_rg);
        //main_rg.setOnClickListener(this);
    }

    //展示和隐藏Fragment的方法
    private void showAndHideFragment(int position) {
        FragmentTransaction transaction = manager.beginTransaction();
        //如果没有fragment就在framelayout里面加上
        if (!mFragments[position].isAdded()) {
            transaction.add(R.id.frame, mFragments[position], "" + position);
        }
        //把所有的fragment设为隐藏
        for (Fragment fragment : mFragments) {
            transaction.hide(fragment);
        }
        //把选中的设为显示
        transaction.show(mFragments[position]);
        transaction.commit();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        if (mFragments[0] != null) {
            getSupportFragmentManager().putFragment(outState, HOME_FRAGMENT_KEY, mFragments[0]);
        } else if (mFragments[1] != null) {
            getSupportFragmentManager().putFragment(outState, DASHBOARD_FRAGMENT_KEY, mFragments[1]);
        } else if (mFragments[2] != null) {
            getSupportFragmentManager().putFragment(outState, NOTICE_FRAGMENT_KEY, mFragments[2]);
        }
        super.onSaveInstanceState(outState);
    }
}
