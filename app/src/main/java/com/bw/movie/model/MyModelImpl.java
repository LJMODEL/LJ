package com.bw.movie.model;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.bw.movie.bean.BindWeiXinBean;
import com.bw.movie.bean.CinemaXiangQing_Bean;
import com.bw.movie.bean.Cinema_Bean;
import com.bw.movie.bean.Cinema_DianZhan;
import com.bw.movie.bean.Cinema_Fuzzy_Bean;
import com.bw.movie.bean.Cinema_PingLun_Bean;
import com.bw.movie.bean.Cinema_XIe_PingLun_Bean;
import com.bw.movie.bean.Cinema_XiHuan_Bean;
import com.bw.movie.bean.Cinemae_QuXiao_Guan_Bean;
import com.bw.movie.bean.DecideWeiXinBean;
import com.bw.movie.bean.Feedback_Bean;
import com.bw.movie.bean.FilmAttentionBean;
import com.bw.movie.bean.FilmRecycler_RemenBean;
import com.bw.movie.bean.FilmRecycler_actionBean;
import com.bw.movie.bean.FilmRecycler_commingsoonBean;
import com.bw.movie.bean.Film_attention_noBean;
import com.bw.movie.bean.Film_attention_yesBean;
import com.bw.movie.bean.Film_details_QueryBean;
import com.bw.movie.bean.Focus_Bean;
import com.bw.movie.bean.LBean;
import com.bw.movie.bean.LogBean;
import com.bw.movie.bean.Message_Bean;
import com.bw.movie.bean.Modify_Bean;
import com.bw.movie.bean.Movie_Scheduling_Bean;
import com.bw.movie.bean.Nera_Bean;
import com.bw.movie.bean.Query_ReviewBean;
import com.bw.movie.bean.Recommended_Bean;
import com.bw.movie.bean.RegisteredBean;
import com.bw.movie.callback.MyCallBack;
import com.bw.movie.utils.Retrofits;
import com.google.gson.Gson;

import java.util.Map;

public class MyModelImpl implements MyModel {
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

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
        } else if (clas == LBean.class) {
            /**
             * 微信登录
             */
            Retrofits.getInstance().post(url, headmap, map).getonclcked(new Retrofits.onclick() {
                @Override
                public void success(String strjson) {
                    Gson gson = new Gson();
                    Object o = gson.fromJson(strjson, clas);
                    callBack.success(o);
                }

                @Override
                public void error(String error) {
                    Log.e("微信登录错误", "微信登录错误:" + error);
                    callBack.error(error);
                }
            });
        } else if (clas == BindWeiXinBean.class) {
            /**
             * 微信登录绑定
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
                    Log.e("微信登录绑定错误", "微信登录绑定错误:" + error);
                    callBack.error(error);
                }
            });
        } else if (clas == Feedback_Bean.class) {
            /*
             * 反馈意见
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
                    Log.e("反馈", "反馈错误:" + error);
                    callBack.error(error);
                }
            });
        } else if (clas == Modify_Bean.class) {
            /*
             * 修改密码
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
                    Log.e("反馈", "反馈错误:" + error);
                    callBack.error(error);
                }
            });
        } else if (clas == Cinema_DianZhan.class) {
            /*
             * 影院点赞
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
                    Log.e("反馈", "反馈错误:" + error);
                    callBack.error(error);
                }
            });
        } else if (clas == Cinema_XIe_PingLun_Bean.class) {
            /*
             * 影院写评论
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
                    Log.e("反馈", "反馈错误:" + error);
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
    public void setget(final String url, final Map<String, Object> headmap, final Map<String, Object> map, final Class clas, final MyCallBack callBack) {
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
        } else if (clas == FilmRecycler_actionBean.class) {
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
        } else if (clas == FilmRecycler_commingsoonBean.class) {
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
        } else if (clas == Recommended_Bean.class) {
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
        } else if (clas == Film_attention_yesBean.class) {
            /**
             * 电影关注
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
                    Log.e("电影关注错误", "电影关注错误:" + error);
                    callBack.error(error);
                }
            });
        } else if (clas == Film_attention_noBean.class) {
            /**
             * 电影取消关注
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
                    Log.e("电影取消关注错误", "电影取消关注错误:" + error);
                    callBack.error(error);
                }
            });
        } else if (clas == FilmAttentionBean.class) {
            /**
             * 电影关注查询
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
                    Log.e("电影关注查询错误", "电影关注查询错误:" + error);
                    callBack.error(error);
                }
            });
        } else if (clas == Film_details_QueryBean.class) {
            /**
             * 电影详情查询
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
                    Log.e("电影查询错误", "电影查询错误:" + error);
                    callBack.error(error);
                }
            });
        } else if (clas == DecideWeiXinBean.class) {
            /**
             * 微信登录是否绑定
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
                    Log.e("微信登录是否绑定错误", "微信登录是否绑定错误:" + error);
                    callBack.error(error);
                }
            });
        } else if (clas == Message_Bean.class) {
            /**
             * 我的信息
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
                    Log.e("我的信息错误", ":我的信息" + error);
                    callBack.error(error);
                }
            });
        } else if (clas == Focus_Bean.class) {
            /**
             * 我的關注
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
                    Log.e("我的關注错误", ":我的关注" + error);
                    callBack.error(error);
                }
            });
        } else if (clas == Cinema_Bean.class) {
            /**
             * 关注电影
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
                    Log.e("关注电影错误", "关注电影" + error);
                    callBack.error(error);
                }
            });
        } else if (clas == Query_ReviewBean.class) {
            /**
             * 查询影片评论
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
                    Log.e("查询影片评论错误", "查询影片评论错误:" + error);
                    callBack.error(error);
                }
            });
        } else if (clas == CinemaXiangQing_Bean.class) {
            /**
             * 电影院详情
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
                    Log.e("电影院详情错误", "电影院详情" + error);
                    callBack.error(error);
                }
            });
        } else if (clas == Movie_Scheduling_Bean.class) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    /**
                     * 電影檔期
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
                            Log.e("電影檔期错误", ":電影檔期" + error);
                            callBack.error(error);
                        }
                    });
                }
            }, 100);

        } else if (clas == Cinema_PingLun_Bean.class) {
            /**
             * 影院评论展示列表
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
                    Log.e("影院评论展示列表错误", ":影院评论展示列表" + error);
                    callBack.error(error);
                }
            });
        } else if (clas == Cinema_XiHuan_Bean.class) {
            /**
             * 影院关注
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
                    Log.e("影院关注错误", ":影院关注" + error);
                    callBack.error(error);
                }
            });
        } else if (clas == Cinemae_QuXiao_Guan_Bean.class) {
            /**
             * 取消影院关注
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
                    Log.e("影院关注错误", ":取消影院关注" + error);
                    callBack.error(error);
                }
            });
        } else if (clas == Cinema_Fuzzy_Bean.class) {
            /**
             * 影院模糊查询
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
                    Log.e("影院关注错误", ":取消影院关注" + error);
                    callBack.error(error);
                }
            });
        }
    }
}