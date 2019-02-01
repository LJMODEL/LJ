package com.bw.movie.fragment.me;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.bw.movie.R;
import com.bw.movie.adapter.Cinema_Adapter;
import com.bw.movie.adapter.Movie_Adapter;
import com.bw.movie.bean.Cinema_Bean;
import com.bw.movie.bean.Focus_Bean;
import com.bw.movie.bean.Recommended_Bean;
import com.bw.movie.presenter.MyPresenterImpl;
import com.bw.movie.utils.Contacts;
import com.bw.movie.view.MyView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FocusActivity extends AppCompatActivity implements MyView, View.OnClickListener {
    private Button radio1, radio2;
    private MyPresenterImpl myPresenter;
    private RecyclerView xRecyclerView, yrecyclerview;
    private Movie_Adapter movie_adapter;
    private Cinema_Adapter cinema_adapter;
    private SharedPreferences sp;
    private int i = 1;
    private Map<String, Object> headmap;
    private Map<String, Object> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus);
        inView();
        inData();
    }


    private void inView() {
        sp = getSharedPreferences("login", MODE_PRIVATE);
        cinema_adapter = new Cinema_Adapter(null, this);
        movie_adapter = new Movie_Adapter(null, this);
        myPresenter = new MyPresenterImpl(this);
        xRecyclerView = findViewById(R.id.recyclerview);
        yrecyclerview = findViewById(R.id.yrecyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        xRecyclerView.setLayoutManager(linearLayoutManager);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        yrecyclerview.setLayoutManager(linearLayoutManager1);
        radio1 = findViewById(R.id.radio1);
        radio2 = findViewById(R.id.radio2);
        radio1.setOnClickListener(this);
        radio2.setOnClickListener(this);

    }

    private void inData() {
        //进入默认显示推荐
        headmap = new HashMap<>();
        map = new HashMap<>();
        String sessionId = sp.getString("sessionId", "");
        int userId = sp.getInt("userId", 0);
        headmap.put("userId", userId);
        headmap.put("sessionId", sessionId);
        map.put("page", i);
        map.put("count", 5 + "");
        myPresenter.get(Contacts.MY_DAINYING, headmap, map, Focus_Bean.class);
    }


    @Override
    public void success(Object data) {
        if (data instanceof Focus_Bean) {
            Focus_Bean focus_bean = (Focus_Bean) data;
            List<Focus_Bean.ResultBean> result = focus_bean.getResult();
            movie_adapter.setList(result);
            xRecyclerView.setAdapter(movie_adapter);
        } else if (data instanceof Cinema_Bean) {
            Cinema_Bean cinema_bean = (Cinema_Bean) data;
            List<Cinema_Bean.ResultBean> result = cinema_bean.getResult();
            cinema_adapter.setList(result);
            yrecyclerview.setAdapter(cinema_adapter);
        }

    }

    @Override
    public void error(String error) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.radio1:
                xRecyclerView.setVisibility(View.VISIBLE);
                yrecyclerview.setVisibility(View.GONE);
                inData();
                break;
            case R.id.radio2:
                xRecyclerView.setVisibility(View.GONE);
                yrecyclerview.setVisibility(View.VISIBLE);
                Map<String, Object> headmapnear = new HashMap<>();
                Map<String, Object> mapnear = new HashMap<>();
                String sessionId = sp.getString("sessionId", "");
                int userId = sp.getInt("userId", 0);
                headmapnear.put("userId", userId);
                headmapnear.put("sessionId", sessionId);
                mapnear.put("page", i);
                mapnear.put("count", 5 + "");
                myPresenter.get(Contacts.CHAXUN_YINGYUAN, headmapnear, mapnear, Cinema_Bean.class);
                break;
        }
    }
}
