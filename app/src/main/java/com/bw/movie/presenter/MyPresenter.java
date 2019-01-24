package com.bw.movie.presenter;

import com.bw.movie.callback.MyCallBack;

import java.util.Map;

public interface MyPresenter {
    //登录
    void getlogin(String url, Map<String,Object> headmap, Map<String,Object> map, Class clas);
    //注册
    void getregister(String url, Map<String,Object> headmap, Map<String,Object> map, Class clas);
}
