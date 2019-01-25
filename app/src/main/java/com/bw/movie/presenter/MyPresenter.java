package com.bw.movie.presenter;

import com.bw.movie.callback.MyCallBack;

import java.util.Map;

public interface MyPresenter {
    //post
    void getpost(String url, Map<String,Object> headmap, Map<String,Object> map, Class clas);
    //get
    void get(String url, Map<String,Object> headmap, Map<String,Object> map, Class clas);
}
