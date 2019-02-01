package com.bw.movie.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.LogActivity;
import com.bw.movie.bean.Message_Bean;
import com.bw.movie.fragment.me.Feed_Activity;
import com.bw.movie.fragment.me.FocusActivity;
import com.bw.movie.fragment.me.Information_Activity;
import com.bw.movie.presenter.MyPresenterImpl;
import com.bw.movie.utils.Contacts;
import com.bw.movie.utils.ToastUtil;
import com.bw.movie.view.MyView;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class Fragment_My extends Fragment implements View.OnClickListener, MyView {

    private SharedPreferences sp;
    private ImageView imageView, attention, yijian, feed, my_music;
    private MyPresenterImpl presenter;
    private View view;
    private Map<String, Object> headmap;
    private String sessionId;
    private int userId;
    private Map<String, Object> map;
    private TextView name_my;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my, null);
        inView();
        return view;
    }

    private void inView() {
        sp = getActivity().getSharedPreferences("login", MODE_PRIVATE);
        presenter = new MyPresenterImpl(this);
        imageView = view.findViewById(R.id.message);
        attention = view.findViewById(R.id.attention);
        yijian = view.findViewById(R.id.tui);
        feed = view.findViewById(R.id.feed);
        my_music = view.findViewById(R.id.my_music);
        imageView.setOnClickListener(this);
        attention.setOnClickListener(this);
        my_music.setOnClickListener(this);
        yijian.setOnClickListener(this);
        feed.setOnClickListener(this);
        name_my = (TextView) view.findViewById(R.id.name_my);

        headmap = new HashMap<>();
        map = new HashMap<>();
        sessionId = sp.getString("sessionId", "");
        userId = sp.getInt("userId", 0);
        headmap.put("userId", userId);
        headmap.put("sessionId", sessionId);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.message:
                presenter.get(Contacts.MY_XINXI, headmap, map, Message_Bean.class);
                break;
            case R.id.attention:
                Intent intent = new Intent(getActivity(), FocusActivity.class);
                startActivity(intent);
                break;
            case R.id.tui:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("纬度电影").setMessage("确定要退出登录吗？")
                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent1 = new Intent(getActivity(), LogActivity.class);
                                startActivity(intent1);
                            }
                        });
                builder.create().show();
                break;
            case R.id.feed:
                startActivity(new Intent(getActivity(), Feed_Activity.class));
                break;
            case R.id.my_music:

                break;
        }
    }


    @Override
    public void success(Object data) {
        if (data instanceof Message_Bean) {
            Message_Bean message_bean = (Message_Bean) data;
            Message_Bean.ResultBean result = message_bean.getResult();
            name_my.setText(result.getNickName());
            Intent intent = new Intent(getActivity(), Information_Activity.class);
            intent.putExtra("info", (Serializable) result);
            startActivity(intent);
        }
    }

    @Override
    public void error(String error) {
        Log.e("我的信息", error);
        ToastUtil.Toast(error);
    }
}
