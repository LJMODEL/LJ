package com.bw.movie.model;

import android.util.Log;

import com.bw.movie.bean.LogBean;
import com.bw.movie.callback.MyCallBack;
import com.bw.movie.utils.Retrofits;
import com.google.gson.Gson;

import java.util.Map;

public class MyModelImpl implements MyModel{
    /*
    * 登录
    * */
    @Override
    public void setlogin(String url, Map<String, Object> headmap, Map<String, Object> map, final Class clas, final MyCallBack callBack) {
        Retrofits.getInstance().post(url,headmap,map).getonclcked(new Retrofits.onclick() {
            @Override
            public void success(String strjson) {
                Gson gson=new Gson();
                Object o = gson.fromJson(strjson, clas);
                callBack.success(o);
            }

            @Override
            public void error(String error) {
                Log.e("登录错误", "登录错误:"+error);
                callBack.error(error);
            }
        });
    }
    /*
     * 注册
     * */
    @Override
    public void setregister(String url, Map<String, Object> headmap, Map<String, Object> map, final Class clas, final MyCallBack callBack) {
        Retrofits.getInstance().post(url, headmap, map).getonclcked(new Retrofits.onclick() {
            @Override
            public void success(String strjson) {
                Gson gson=new Gson();
                Object o = gson.fromJson(strjson, clas);
                callBack.success(o);
            }

            @Override
            public void error(String error) {
                Log.e("注册错误", "注册错误:"+error);
                callBack.error(error);
            }
        });
    }
}
