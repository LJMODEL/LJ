package com.bw.movie.view;

public interface MyView<T> {
    //成功
    void success(T data);
    //失败
    void error(String error);
}
