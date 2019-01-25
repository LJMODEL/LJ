package com.bw.movie.presenter;

import android.content.Intent;
import android.util.Log;

import com.bw.movie.callback.MyCallBack;
import com.bw.movie.model.MyModelImpl;
import com.bw.movie.view.MyView;

import java.util.Map;

public class MyPresenterImpl implements MyPresenter {
    private MyView view;
    private MyModelImpl model;

    public MyPresenterImpl(MyView view) {
        this.view = view;
        model = new MyModelImpl();
    }

    /*
     * post
     * */
    @Override
    public void getpost(String url, Map<String, Object> headmap, Map<String, Object> map, Class clas) {
        model.setpost(url, headmap, map, clas, new MyCallBack() {
            @Override
            public void success(Object data) {
                view.success(data);
            }

            @Override
            public void error(String error) {
                view.error(error);
            }
        });
    }
    /*
     * get
     * */
    @Override
    public void get(String url, Map<String, Object> headmap, Map<String, Object> map, Class clas) {
        model.setget(url, headmap, map, clas, new MyCallBack() {

            @Override
            public void success(Object data) {
                view.success(data);
            }

            @Override
            public void error(String error) {
                view.error(error);
            }
        });
    }
}
