package com.bw.movie.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.adapter.FilmRecycler_RemenBean_Adapter;
import com.bw.movie.bean.FilmRecycler_RemenBean;
import com.bw.movie.presenter.MyPresenterImpl;
import com.bw.movie.utils.Contacts;
import com.bw.movie.view.MyView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


public class Fragment_Film extends Fragment implements MyView {
    private RecyclerView recy_remen;
    private RecyclerView recy_shangying;
    private RecyclerView recy_jijiang;
    private MyPresenterImpl presenter;
    private SharedPreferences sp;
    private String sessionId;
    private int userId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_film, null);
        initView(view);
        Map<String, Object> headmap = new HashMap<>();
        HashMap<String, Object> map = new HashMap<>();
        sessionId = sp.getString("sessionId", "");
        userId = sp.getInt("userId", 0);
        headmap.put("userId", userId);
        headmap.put("sessionId", sessionId);
        map.put("page", 1 + "");
        map.put("count", 5 + "");
        presenter.get(Contacts.FILM_RECYCLER, headmap, map, FilmRecycler_RemenBean.class);
        return view;
    }

    private void initView(View view) {
        sp = getActivity().getSharedPreferences("login", MODE_PRIVATE);
        recy_remen = (RecyclerView) view.findViewById(R.id.recy_remen);
        LinearLayoutManager ms = new LinearLayoutManager(getActivity());
        ms.setOrientation(LinearLayoutManager.HORIZONTAL);
        recy_remen.setLayoutManager(ms);
        recy_shangying = (RecyclerView) view.findViewById(R.id.recy_shangying);
        recy_jijiang = (RecyclerView) view.findViewById(R.id.recy_jijiang);
        presenter = new MyPresenterImpl(this);
    }

    @Override
    public void success(Object data) {
        if (data instanceof FilmRecycler_RemenBean) {
            List<FilmRecycler_RemenBean.ResultBean> result = ((FilmRecycler_RemenBean) data).getResult();
            FilmRecycler_RemenBean_Adapter adapter = new FilmRecycler_RemenBean_Adapter(result, getActivity());
            recy_remen.setAdapter(adapter);
        }
    }

    @Override
    public void error(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }
}
