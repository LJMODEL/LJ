package com.bw.movie.model;

import com.bw.movie.callback.MyCallBack;

import java.util.HashMap;
import java.util.Map;

public interface MyModel {
    //post
    void setpost(String url, Map<String,Object> headmap, Map<String,Object> map, Class clas, MyCallBack callBack);
    //get
    void setget(String url, Map<String,Object> headmap, Map<String,Object> map, Class clas, MyCallBack callBack);
}
