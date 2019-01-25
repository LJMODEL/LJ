package com.bw.movie.model;

import android.util.Log;

import com.bw.movie.bean.FilmRecycler_RemenBean;
import com.bw.movie.bean.FilmRecycler_actionBean;
import com.bw.movie.bean.FilmRecycler_commingsoonBean;
import com.bw.movie.bean.LogBean;
import com.bw.movie.bean.Nera_Bean;
import com.bw.movie.bean.Recommended_Bean;
import com.bw.movie.bean.RegisteredBean;
import com.bw.movie.callback.MyCallBack;
import com.bw.movie.utils.Retrofits;
import com.google.gson.Gson;

import java.util.Map;

public class MyModelImpl implements MyModel {
    /**
     * post请求
     *
     * @param url
     * @param headmap
     * @param map
     * @param clas
     * @param callBack
     */
    @Override
    public void setpost(String url, Map<String, Object> headmap, Map<String, Object> map, final Class clas, final MyCallBack callBack) {
        if (clas == LogBean.class) {
            /*
             * 登录
             * */
            Retrofits.getInstance().post(url, headmap, map).getonclcked(new Retrofits.onclick() {
                @Override
                public void success(String strjson) {
                    Gson gson = new Gson();
                    Object o = gson.fromJson(strjson, clas);
                    callBack.success(o);
                }

                @Override
                public void error(String error) {
                    Log.e("登录错误", "登录错误:" + error);
                    callBack.error(error);
                }
            });
        } else if (clas == RegisteredBean.class) {
            /*
             * 注册
             * */
            Retrofits.getInstance().post(url, headmap, map).getonclcked(new Retrofits.onclick() {
                @Override
                public void success(String strjson) {
                    Gson gson = new Gson();
                    Object o = gson.fromJson(strjson, clas);
                    callBack.success(o);
                }

                @Override
                public void error(String error) {
                    Log.e("注册错误", "注册错误:" + error);
                    callBack.error(error);
                }
            });
        }
    }


    /**
     * get请求
     *
     * @param url
     * @param headmap
     * @param map
     * @param clas
     * @param callBack
     */
    @Override
    public void setget(String url, Map<String, Object> headmap, Map<String, Object> map, final Class clas, final MyCallBack callBack) {
        if (clas == FilmRecycler_RemenBean.class) {
            Retrofits.getInstance().get(url, headmap, map).getonclcked(new Retrofits.onclick() {
                @Override
                public void success(String strjson) {
                    Gson gson = new Gson();
                    Object o = gson.fromJson(strjson, clas);
                    callBack.success(o);
                }

                @Override
                public void error(String error) {
                    Log.e("列表错误", "列表错误:" + error);
                    callBack.error(error);
                }
            });
        }else if (clas==FilmRecycler_actionBean.class){
            Retrofits.getInstance().get(url, headmap, map).getonclcked(new Retrofits.onclick() {
                @Override
                public void success(String strjson) {
                    Gson gson = new Gson();
                    Object o = gson.fromJson(strjson, clas);
                    callBack.success(o);
                }

                @Override
                public void error(String error) {
                    Log.e("列表错误1", "列表错误:" + error);
                    callBack.error(error);
                }
            });
        }else if (clas==FilmRecycler_commingsoonBean.class){
            Retrofits.getInstance().get(url, headmap, map).getonclcked(new Retrofits.onclick() {
                @Override
                public void success(String strjson) {
                    Gson gson = new Gson();
                    Object o = gson.fromJson(strjson, clas);
                    callBack.success(o);
                }

                @Override
                public void error(String error) {
                    Log.e("列表错误2", "列表错误:" + error);
                    callBack.error(error);
                }
            });
        }
        else if (clas == Recommended_Bean.class) {
            /**
             * 推荐影院
             */
            Retrofits.getInstance().get(url, headmap, map).getonclcked(new Retrofits.onclick() {
                @Override
                public void success(String strjson) {
                    Gson gson = new Gson();
                    Object o = gson.fromJson(strjson, clas);
                    callBack.success(o);
                }

                @Override
                public void error(String error) {
                    Log.e("推荐影院错误", "推荐影院错误:" + error);
                    callBack.error(error);
                }
            });
        } else if (clas == Nera_Bean.class) {
            /**
             * 附近影院
             */
            Retrofits.getInstance().get(url, headmap, map).getonclcked(new Retrofits.onclick() {
                @Override
                public void success(String strjson) {
                    Gson gson = new Gson();
                    Object o = gson.fromJson(strjson, clas);
                    callBack.success(o);
                }

                @Override
                public void error(String error) {
                    Log.e("附近影院错误", "附近影院错误:" + error);
                    callBack.error(error);
                }
            });
        }
    }
}