package com.bw.movie.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bw.movie.R;


public class Fragment_Cinema extends Fragment implements View.OnClickListener {

    private NearFragment nearFragment;
    private RecommendedFragment recommendedFragment;
    private Button pay;
    private Button income;
    FragmentManager fm;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cinema, null);
        initview();
        return view;
    }

    private void initview() {
        pay = view.findViewById(R.id.pay);
        income = view.findViewById(R.id.income);
        pay.setOnClickListener(this);
        income.setOnClickListener(this);
        fm = getActivity().getSupportFragmentManager();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.income:
                setTabSelection(0);
                break;

            case R.id.pay:
                setTabSelection(1);
                break;
        }
    }

    private void setTabSelection(int index) {
        FragmentTransaction ft = fm.beginTransaction();
        hideFragment(ft);
        switch (index) {
            case 0:
                if (nearFragment == null) {
                    nearFragment = new NearFragment();
                    ft.add(R.id.fl, nearFragment);
                } else {
                    ft.show(nearFragment);
                }

                break;

            case 1:
                if (recommendedFragment == null) {
                    recommendedFragment = new RecommendedFragment();
                    ft.add(R.id.fl, recommendedFragment);
                }
                ft.show(recommendedFragment);
                break;
        }
        ft.commit();
    }

    //用于隐藏fragment
    private void hideFragment(FragmentTransaction ft) {
        if (nearFragment != null) {
            ft.hide(nearFragment);
        }
        if (recommendedFragment != null) {
            ft.hide(recommendedFragment);
        }
    }
}
