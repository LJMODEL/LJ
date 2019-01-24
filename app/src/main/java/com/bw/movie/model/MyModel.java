package com.bw.movie.model;

import com.bw.movie.callback.MyCallBack;

import java.util.HashMap;
import java.util.Map;

public interface MyModel {
    //登录
    void setlogin(String url, Map<String,Object> headmap, Map<String,Object> map, Class clas, MyCallBack callBack);
    //注册
    void setregister(String url, Map<String,Object> headmap, Map<String,Object> map, Class clas, MyCallBack callBack);
}
