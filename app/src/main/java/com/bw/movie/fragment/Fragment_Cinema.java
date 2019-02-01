package com.bw.movie.fragment;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.adapter.Fuzzy_Adapter;
import com.bw.movie.adapter.Nera_Adapter;
import com.bw.movie.adapter.Recommended_Adapter;
import com.bw.movie.bean.Cinema_Fuzzy_Bean;
import com.bw.movie.bean.Cinema_XiHuan_Bean;
import com.bw.movie.bean.Cinemae_QuXiao_Guan_Bean;
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

    private TransitionSet mSet;
    private ImageView imageView;
    private View view;
    private RecyclerView recyclerView, yrecyclerview;
    private Button radio1, radio2;
    private MyPresenterImpl presenter;
    private SharedPreferences sp;
    private String sessionId;
    private int userId;
    private Recommended_Adapter recommended_adapter;
    private Nera_Adapter nera_adapter;
    private Fuzzy_Adapter fuzzy_adapter;
    private Map<String, Object> headmap;
    private Map<String, Object> map;
    private Map<String, Object> headmapnear;
    private Map<String, Object> mapnear;
    private int i = 1;
    boolean isExpand = false;
    private LinearLayout ll_search;
    private EditText et_search_content;
    private TextView tv_search;
    private String content;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cinema, null);
        inView();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        yrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        inData();
        return view;
    }


    private void inView() {

        sp = getActivity().getSharedPreferences("login", MODE_PRIVATE);
        presenter = new MyPresenterImpl(this);
        nera_adapter = new Nera_Adapter(null, getActivity());
        recyclerView = view.findViewById(R.id.recyclerview);
        yrecyclerview = view.findViewById(R.id.yrecyclerview);

        yrecyclerview.setVisibility(View.GONE);
        radio1 = view.findViewById(R.id.radio1);
        radio2 = view.findViewById(R.id.radio2);
        ll_search = view.findViewById(R.id.ll_search);
        et_search_content = view.findViewById(R.id.et_search_content);
        tv_search = view.findViewById(R.id.tv_search);
        tv_search.setOnClickListener(this);
        et_search_content.setOnClickListener(this);
        ll_search.setOnClickListener(this);
        radio1.setOnClickListener(this);
        radio2.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.radio1:
                recyclerView.setVisibility(View.VISIBLE);
                yrecyclerview.setVisibility(View.GONE);
                inData();
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
                //经纬度
                mapnear.put("longitude", 116.2996602058 + "");
                mapnear.put("latitude", 40.0433534053 + "");
                mapnear.put("page", i + "");
                mapnear.put("count", 5 + "");
                presenter.get(Contacts.NEAR, headmapnear, mapnear, Nera_Bean.class);
                break;
            case R.id.ll_search:
                if (!isExpand) {
                    expand();
                    isExpand = true;
                } else if (isExpand) {
                    reduce();
                    isExpand = false;
                }
                break;
            case R.id.tv_search:
                content = et_search_content.getText().toString();
                if (content.equals("")) {
                    Toast.makeText(getActivity(), "输入内容不能为空！", Toast.LENGTH_SHORT).show();
                } else {
                    inuzzy();
                }
                break;
        }
    }


    @Override
    public void success(Object data) {
        if (data instanceof Recommended_Bean) {
            Recommended_Bean recommended_bean = (Recommended_Bean) data;
            List<Recommended_Bean.ResultBean> result = recommended_bean.getResult();
            recommended_adapter = new Recommended_Adapter(result, getActivity());
            recyclerView.setAdapter(recommended_adapter);
        } else if (data instanceof Nera_Bean) {
            Nera_Bean nera_bean = (Nera_Bean) data;
            List<Nera_Bean.ResultBean> result = nera_bean.getResult();
            nera_adapter = new Nera_Adapter(result, getActivity());
            yrecyclerview.setAdapter(nera_adapter);
        } else if (data instanceof Cinema_XiHuan_Bean) {
            Cinema_XiHuan_Bean cinema_xiHuan_bean = (Cinema_XiHuan_Bean) data;
            Toast.makeText(getActivity(), cinema_xiHuan_bean.getMessage(), Toast.LENGTH_SHORT).show();
            recommended_adapter.notifyDataSetChanged();
        } else if (data instanceof Cinema_Fuzzy_Bean) {
            Cinema_Fuzzy_Bean cinema_fuzzy_bean = (Cinema_Fuzzy_Bean) data;
            List<Cinema_Fuzzy_Bean.ResultBean> result = cinema_fuzzy_bean.getResult();
            fuzzy_adapter = new Fuzzy_Adapter(result, getActivity());
            recyclerView.setAdapter(fuzzy_adapter);
        }
        //接口回调
        recommended_adapter.setDianItem(new Recommended_Adapter.LikeItem() {
            @Override
            public void Item(int i, int j) {
                if (j == 2) {
                    inInten(i);
                    j = 1;
                } else {
                    inNotIntern(i);
                    j = 2;
                }
            }
        });
        nera_adapter.setDianItem(new Nera_Adapter.FuJinItem() {
            @Override
            public void Item(int i, int j) {
                if (j == 2) {
                    inInten(i);
                    j = 1;
                } else {
                    inNotIntern(i);
                    j = 2;
                }
            }
        });

        recommended_adapter.notifyDataSetChanged();
        nera_adapter.notifyDataSetChanged();
    }

    @Override
    public void error(String error) {
        ToastUtil.Toast("推荐影院error");
    }

    //开始动画
    private void expand() {
        //设置伸展状态时的布局
        //etSearchContent.setHint("搜索你想搜索的内容");
        et_search_content.setVisibility(View.VISIBLE);
        tv_search.setVisibility(View.VISIBLE);
        RelativeLayout.LayoutParams LayoutParams = (RelativeLayout.LayoutParams) ll_search.getLayoutParams();
        LayoutParams.width = dip2px(220);
        LayoutParams.setMargins(dip2px(0), dip2px(32), dip2px(0), dip2px(0));
        ll_search.setLayoutParams(LayoutParams);
        //开始动画
        beginDelayedTransition(ll_search);
    }

    private void reduce() {
        //设置收缩状态时的布局
        et_search_content.setText("");
        et_search_content.setVisibility(View.GONE);
        tv_search.setVisibility(View.GONE);
        RelativeLayout.LayoutParams LayoutParams = (RelativeLayout.LayoutParams) ll_search.getLayoutParams();
        LayoutParams.width = dip2px(60);
        LayoutParams.setMargins(dip2px(0), dip2px(30), dip2px(0), dip2px(0));
        ll_search.setLayoutParams(LayoutParams);
        //开始动画
        beginDelayedTransition(ll_search);
    }

    @SuppressLint("NewApi")
    void beginDelayedTransition(ViewGroup view) {
        mSet = new AutoTransition();
        mSet.setDuration(300);
        TransitionManager.beginDelayedTransition(view, mSet);
    }

    private int dip2px(float dpVale) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpVale * scale + 0.5f);
    }


    private void inData() {
        //进入默认显示推荐
        headmap = new HashMap<>();
        map = new HashMap<>();
        sessionId = sp.getString("sessionId", "");
        userId = sp.getInt("userId", 0);
        headmap.put("userId", userId);
        headmap.put("sessionId", sessionId);
        map.put("page", 1 + "");
        map.put("count", 6 + "");
        presenter.get(Contacts.RECOMMENDED, headmap, map, Recommended_Bean.class);
    }

    private void inInten(int i) {
        //影院关注
        headmap = new HashMap<>();
        map = new HashMap<>();
        sessionId = sp.getString("sessionId", "");
        userId = sp.getInt("userId", 0);
        headmap.put("userId", userId);
        headmap.put("sessionId", sessionId);
        map.put("cinemaId", i);
        presenter.get(Contacts.CINEMA_GUANZHU, headmap, map, Cinema_XiHuan_Bean.class);
    }

    private void inNotIntern(int i) {
        //影院取消关注
        headmap = new HashMap<>();
        map = new HashMap<>();
        sessionId = sp.getString("sessionId", "");
        userId = sp.getInt("userId", 0);
        headmap.put("userId", userId);
        headmap.put("sessionId", sessionId);
        map.put("cinemaId", i);
        presenter.get(Contacts.CINEMA_QUANXIAOGUANZHU, headmap, map, Cinemae_QuXiao_Guan_Bean.class);
    }

    private void inuzzy() {
        //影院模糊搜索
        headmap = new HashMap<>();
        map = new HashMap<>();
        map.put("page", 1);
        map.put("count", 10);
        map.put("cinemaName", content);
        presenter.get(Contacts.CINEMA_MOHU, headmap, map, Cinema_Fuzzy_Bean.class);
    }
}
