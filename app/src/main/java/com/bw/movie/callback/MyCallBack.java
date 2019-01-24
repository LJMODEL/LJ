package com.bw.movie.callback;

public interface MyCallBack<T> {
    //成功
    void success(T data);
    //失败
    void error(String error);
}
