package com.bw.movie.presenter;

import android.util.Log;

import com.bw.movie.callback.MyCallBack;
import com.bw.movie.model.MyModelImpl;
import com.bw.movie.view.MyView;

import java.util.Map;

public class MyPresenterImpl implements MyPresenter{
    private MyView view;
    private MyModelImpl model;

    public MyPresenterImpl(MyView view) {
        this.view = view;
        model=new MyModelImpl();
    }
    /*
     * 登录
     * */
    @Override
    public void getlogin(String url, Map<String, Object> headmap, Map<String, Object> map, Class clas) {
        model.setlogin(url, headmap, map, clas, new MyCallBack() {
            @Override
            public void success(Object data) {
                view.success(data);
            }

            @Override
            public void error(String error) {
                Log.e("登录错误","登录错误:"+error);
                view.error(error);
            }
        });
    }
    /*
     * 注册
     * */
    @Override
    public void getregister(String url, Map<String, Object> headmap, Map<String, Object> map, Class clas) {
        model.setregister(url, headmap, map, clas, new MyCallBack() {
            @Override
            public void success(Object data) {
                view.success(data);
            }

            @Override
            public void error(String error) {
                Log.e("注册错误","注册错误:"+error);
                view.error(error);
            }
        });
    }
}
