package com.bw.movie.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.adapter.Nera_Adapter;
import com.bw.movie.adapter.Recommended_Adapter;
import com.bw.movie.bean.Nera_Bean;
import com.bw.movie.bean.Recommended_Bean;
import com.bw.movie.presenter.MyPresenterImpl;
import com.bw.movie.utils.Contacts;
import com.bw.movie.utils.ToastUtil;
import com.bw.movie.view.MyView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


public class Fragment_Cinema extends Fragment implements View.OnClickListener, MyView {

    private View view;
    private XRecyclerView recyclerView, yrecyclerview;
    private Button radio1, radio2;
    private MyPresenterImpl presenter;
    private SharedPreferences sp;
    private String sessionId;
    private int userId;
    private Recommended_Adapter recommended_adapter;
    private Nera_Adapter nera_adapter;
    private Map<String, Object> headmap;
    private Map<String, Object> map;
    private Map<String, Object> headmapnear;
    private Map<String, Object> mapnear;
    private int i = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cinema, null);
        inView();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        yrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        //进入默认显示推荐
        headmap = new HashMap<>();
        map = new HashMap<>();
        sessionId = sp.getString("sessionId", "");
        userId = sp.getInt("userId", 0);
        headmap.put("userId", userId);
        headmap.put("sessionId", sessionId);
        map.put("page", 1 + "");
        map.put("count", 5 + "");
        presenter.get(Contacts.RECOMMENDED, headmap, map, Recommended_Bean.class);
        inInt();
        return view;
    }


    private void inView() {

        sp = getActivity().getSharedPreferences("login", MODE_PRIVATE);
        presenter = new MyPresenterImpl(this);
        nera_adapter = new Nera_Adapter(null, getActivity());
        recyclerView = view.findViewById(R.id.recyclerview);
        yrecyclerview = view.findViewById(R.id.yrecyclerview);
        recyclerView.setLoadingMoreEnabled(true);
        recyclerView.setPullRefreshEnabled(true);
        yrecyclerview.setVisibility(View.GONE);
        radio1 = view.findViewById(R.id.radio1);
        radio2 = view.findViewById(R.id.radio2);
        radio1.setOnClickListener(this);
        radio2.setOnClickListener(this);
    }

    private void inInt() {
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                presenter.get(Contacts.RECOMMENDED, headmap, map, Recommended_Bean.class);
                recyclerView.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                i++;
                mapnear.put("page", i);
                mapnear.put("count", 5 + "");
                presenter.get(Contacts.RECOMMENDED, headmap, map, Recommended_Bean.class);
                recyclerView.loadMoreComplete();
            }
        });
        yrecyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                presenter.get(Contacts.NEAR, headmap, map, Recommended_Bean.class);
                yrecyclerview.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                i++;
                mapnear.put("page", i + "");
                mapnear.put("count", 5 + "");
                presenter.get(Contacts.NEAR, headmap, map, Recommended_Bean.class);
                yrecyclerview.loadMoreComplete();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.radio1:
                recyclerView.setVisibility(View.VISIBLE);
                yrecyclerview.setVisibility(View.GONE);
                Map<String, Object> headmap = new HashMap<>();
                Map<String, Object> map = new HashMap<>();
                sessionId = sp.getString("sessionId", "");
                userId = sp.getInt("userId", 0);
                headmap.put("userId", userId);
                headmap.put("sessionId", sessionId);
                map.put("page", 1 + "");
                map.put("count", 5 + "");
                presenter.get(Contacts.RECOMMENDED, headmap, map, Recommended_Bean.class);
                break;
            case R.id.radio2:
                recyclerView.setVisibility(View.GONE);
                yrecyclerview.setVisibility(View.VISIBLE);
                headmapnear = new HashMap<>();
                mapnear = new HashMap<>();
                sessionId = sp.getString("sessionId", "");
                userId = sp.getInt("userId", 0);
                headmapnear.put("userId", userId);
                headmapnear.put("sessionId", sessionId);
                mapnear.put("longitude", 116.2996602058 + "");
                mapnear.put("latitude", 40.0433534053 + "");
                mapnear.put("page", i + "");
                mapnear.put("count", 5 + "");
                presenter.get(Contacts.NEAR, headmapnear, mapnear, Nera_Bean.class);
                break;
        }
    }

    @Override
    public void success(Object data) {
        if (data instanceof Recommended_Bean) {
            Recommended_Bean recommended_bean = (Recommended_Bean) data;
            List<Recommended_Bean.ResultBean> result = recommended_bean.getResult();
            Recommended_Adapter adapter = new Recommended_Adapter(result, getActivity());
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else if (data instanceof Nera_Bean) {
            Nera_Bean nera_bean = (Nera_Bean) data;
            List<Nera_Bean.ResultBean> result = nera_bean.getResult();
            Nera_Adapter adapter = new Nera_Adapter(result, getActivity());
            yrecyclerview.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }
    }

    @Override
    public void error(String error) {
        ToastUtil.Toast("推荐影院error");
    }
}
