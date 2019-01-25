package com.bw.movie.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bw.movie.R;
import com.bw.movie.presenter.MyPresenterImpl;
import com.bw.movie.view.MyView;

import java.util.HashMap;
import java.util.Map;

public class RecommendedFragment extends Fragment implements MyView {

    private MyPresenterImpl presenter;
    private SharedPreferences sp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = View.inflate(getActivity(), R.layout.recommended_fragment, null);
        inView();
        return view;
    }

    private void inView() {
        presenter= new MyPresenterImpl(this);
        Map<String, Object> headmap = new HashMap<>();
        Map<String, Object> map = new HashMap<>();

    }

    @Override
    public void success(Object data) {

    }

    @Override
    public void error(String error) {

    }
}
