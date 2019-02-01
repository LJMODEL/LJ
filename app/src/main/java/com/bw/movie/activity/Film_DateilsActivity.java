package com.bw.movie.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.adapter.FilmDateils_RemenBean_Adapter;
import com.bw.movie.adapter.FilmDateils_actionBean_Adapter;
import com.bw.movie.adapter.FilmDateils_commingsoonBean_Adapter;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.bean.FilmRecycler_RemenBean;
import com.bw.movie.bean.FilmRecycler_actionBean;
import com.bw.movie.bean.FilmRecycler_commingsoonBean;
import com.bw.movie.bean.Film_attention_noBean;
import com.bw.movie.bean.Film_attention_yesBean;
import com.bw.movie.presenter.MyPresenterImpl;
import com.bw.movie.utils.Contacts;
import com.bw.movie.utils.ToastUtil;
import com.bw.movie.view.MyView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Film_DateilsActivity extends BaseActivity implements View.OnClickListener, MyView {
    private RadioButton radio1, radio2, radio3;
    private XRecyclerView recyclerview;
    private XRecyclerView yrecyclerview;
    private XRecyclerView zrecyclerview;
    private MyPresenterImpl presenter;
    private Map<String, Object> headmap;
    private HashMap<String, Object> map;
    private String sessionId;
    private int userId;
    private SharedPreferences sp;
    private int i=1;
    private List<FilmRecycler_RemenBean.ResultBean> result;
    private List<FilmRecycler_actionBean.ResultBean> result1;
    private List<FilmRecycler_commingsoonBean.ResultBean> result2;
    private FilmDateils_RemenBean_Adapter adapter;
    private FilmDateils_actionBean_Adapter adapter1;
    private FilmDateils_commingsoonBean_Adapter adapter2;
    private int id;
    private int id1;
    private int id2;

    /*
     * 设置布局
     * */
    @Override
    protected int setLayout() {
        return R.layout.film_recycler_list;
    }

    /*
     * 初始化控件
     * */
    @Override
    protected void initView() {
        radio1 = findViewById(R.id.radio1);
        radio1.setOnClickListener(this);
        radio2 = findViewById(R.id.radio2);
        radio2.setOnClickListener(this);
        radio3 = findViewById(R.id.radio3);
        radio3.setOnClickListener(this);
        recyclerview=findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this){
            //禁止水平滑动
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(linearLayoutManager);
        yrecyclerview=findViewById(R.id.yrecyclerview);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this){
            //禁止水平滑动
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        yrecyclerview.setLayoutManager(linearLayoutManager1);
        yrecyclerview.setLayoutManager(new GridLayoutManager(this,1));
        zrecyclerview=findViewById(R.id.zrecyclerview);
        yrecyclerview=findViewById(R.id.yrecyclerview);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this){
            //禁止水平滑动
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        zrecyclerview.setLayoutManager(linearLayoutManager2);
        zrecyclerview.setLayoutManager(new GridLayoutManager(this,1));
        yrecyclerview.setVisibility(View.GONE);
        zrecyclerview.setVisibility(View.GONE);
        recyclerview.setPullRefreshEnabled(true);
        recyclerview.setLoadingMoreEnabled(true);
        yrecyclerview.setPullRefreshEnabled(true);
        yrecyclerview.setLoadingMoreEnabled(true);
        zrecyclerview.setPullRefreshEnabled(true);
        zrecyclerview.setLoadingMoreEnabled(true);
        presenter = new MyPresenterImpl(this);

        /*
        * 热门影院的上拉下拉
        * */
        recyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                result.clear();
                i=1;
                requestData();
                presenter.get(Contacts.FILM_RECYCLER_HOT, headmap, map, FilmRecycler_RemenBean.class);
                recyclerview.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                i++;
                requestData();
                presenter.get(Contacts.FILM_RECYCLER_HOT, headmap, map, FilmRecycler_RemenBean.class);
                recyclerview.loadMoreComplete();
            }
        });
        /*
         * 正在热映的的上拉下拉
         * */
        yrecyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                result1.clear();
                i=1;
                requestData();
                presenter.get(Contacts.FILM_RECYCLER_ACTION, headmap, map, FilmRecycler_actionBean.class);
                yrecyclerview.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                i++;
                requestData();
                presenter.get(Contacts.FILM_RECYCLER_ACTION, headmap, map, FilmRecycler_actionBean.class);
                yrecyclerview.loadMoreComplete();
            }
        });
        /*
         * 即将上映的的上拉下拉
         * */
        zrecyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                result2.clear();
                i=1;
                requestData();
                presenter.get(Contacts.FILM_RECYCLER_COMMINGSOON, headmap, map, FilmRecycler_commingsoonBean.class);
                zrecyclerview.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                i++;
                requestData();
                presenter.get(Contacts.FILM_RECYCLER_COMMINGSOON, headmap, map, FilmRecycler_commingsoonBean.class);
                zrecyclerview.loadMoreComplete();
            }
        });
    }

    /*
     * 初始化数据
     * */
    @Override
    protected void initData() {
        requestData();
        presenter.get(Contacts.FILM_RECYCLER_HOT, headmap, map, FilmRecycler_RemenBean.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.radio1:
                requestData();
                presenter.get(Contacts.FILM_RECYCLER_HOT, headmap, map, FilmRecycler_RemenBean.class);
                yrecyclerview.setVisibility(View.GONE);
                zrecyclerview.setVisibility(View.GONE);
                recyclerview.setVisibility(View.VISIBLE);
                break;
            case R.id.radio2:
                requestData();
                presenter.get(Contacts.FILM_RECYCLER_ACTION, headmap, map, FilmRecycler_actionBean.class);
                recyclerview.setVisibility(View.GONE);
                zrecyclerview.setVisibility(View.GONE);
                yrecyclerview.setVisibility(View.VISIBLE);
                break;
            case R.id.radio3:
                requestData();
                presenter.get(Contacts.FILM_RECYCLER_COMMINGSOON, headmap, map, FilmRecycler_commingsoonBean.class);
                recyclerview.setVisibility(View.GONE);
                yrecyclerview.setVisibility(View.GONE);
                zrecyclerview.setVisibility(View.VISIBLE);
                break;
        }
    }
    /*
    * 请求头和请求参数的数据
    * */
    private void requestData() {
        sp = getSharedPreferences("login", MODE_PRIVATE);
        headmap = new HashMap<>();
        map = new HashMap<>();
        sessionId = sp.getString("sessionId", "");
        userId = sp.getInt("userId", 0);
        headmap.put("userId", userId);
        headmap.put("sessionId", sessionId);
        map.put("page", i + "");
        map.put("count", 5 + "");
    }

    /*
    * 成功回调
    * */
    @Override
    public void success(Object data) {
        if (data instanceof FilmRecycler_RemenBean) {
            result = ((FilmRecycler_RemenBean) data).getResult();
            adapter = new FilmDateils_RemenBean_Adapter(result,this);
            recyclerview.setAdapter(adapter);
            adapter.setOnClickLinsenter(new FilmDateils_RemenBean_Adapter.setOnClick() {
                @Override
                public void setAttention(int position) {
                    int followMovie = result.get(position).getFollowMovie();
                    id = result.get(position).getId();
                    Log.e("zzz", "setAttention: "+followMovie );
                    if (followMovie==1){
                        result.get(position).setFollowMovie(2);
                        sp = getSharedPreferences("login", MODE_PRIVATE);
                        headmap = new HashMap<>();
                        map = new HashMap<>();
                        sessionId = sp.getString("sessionId", "");
                        userId = sp.getInt("userId", 0);
                        headmap.put("userId", userId);
                        headmap.put("sessionId", sessionId);
                        map.put("movieId", id);
                        presenter.get(Contacts.ATTENTION_NO,headmap,map,Film_attention_noBean.class);
                    }else{
                        result.get(position).setFollowMovie(1);
                        sp = getSharedPreferences("login", MODE_PRIVATE);
                        headmap = new HashMap<>();
                        map = new HashMap<>();
                        sessionId = sp.getString("sessionId", "");
                        userId = sp.getInt("userId", 0);
                        headmap.put("userId", userId);
                        headmap.put("sessionId", sessionId);
                        map.put("movieId", id);
                        presenter.get(Contacts.ATTENTION_YES,headmap,map,Film_attention_yesBean.class);
                    }
                }
                @Override
                public void setData(int position) {
                    FilmRecycler_RemenBean.ResultBean resultBean = result.get(position);
                    Intent intent=new Intent(Film_DateilsActivity.this,Film_Dateils_xiangqingActivity.class);
                    intent.putExtra("list",resultBean);
                    intent.putExtra("type",1);
                    startActivity(intent);
                }
            });
            adapter.notifyDataSetChanged();
        }else if (data instanceof FilmRecycler_actionBean){
            result1 = ((FilmRecycler_actionBean) data).getResult();
            adapter1 = new FilmDateils_actionBean_Adapter(result1,this);
            yrecyclerview.setAdapter(adapter1);

            adapter1.setOnClickLinsenter(new FilmDateils_actionBean_Adapter.setOnClick() {
                @Override
                public void setAttention(int position) {
                    int followMovie = result1.get(position).getFollowMovie();
                    Toast.makeText(Film_DateilsActivity.this, ""+followMovie, Toast.LENGTH_SHORT).show();
                    id1 = result1.get(position).getId();
                    if (followMovie==1){
                        result1.get(position).setFollowMovie(2);
                        sp = getSharedPreferences("login", MODE_PRIVATE);
                        headmap = new HashMap<>();
                        map = new HashMap<>();
                        sessionId = sp.getString("sessionId", "");
                        userId = sp.getInt("userId", 0);
                        headmap.put("userId", userId);
                        headmap.put("sessionId", sessionId);
                        map.put("movieId", id1);
                        presenter.get(Contacts.ATTENTION_NO,headmap,map,Film_attention_noBean.class);
                    }else{
                        result1.get(position).setFollowMovie(1);
                        sp = getSharedPreferences("login", MODE_PRIVATE);
                        headmap = new HashMap<>();
                        map = new HashMap<>();
                        sessionId = sp.getString("sessionId", "");
                        userId = sp.getInt("userId", 0);
                        headmap.put("userId", userId);
                        headmap.put("sessionId", sessionId);
                        map.put("movieId", id1);
                        presenter.get(Contacts.ATTENTION_YES,headmap,map,Film_attention_yesBean.class);
                    }
                }

                @Override
                public void setData(int position) {
                    FilmRecycler_actionBean.ResultBean resultBean = result1.get(position);
                    Intent intent=new Intent(Film_DateilsActivity.this,Film_Dateils_xiangqingActivity.class);
                    intent.putExtra("list1",resultBean);
                    intent.putExtra("type",2);
                    startActivity(intent);
                }
            });
            adapter1.notifyDataSetChanged();
        }else if (data instanceof FilmRecycler_commingsoonBean){
            result2 = ((FilmRecycler_commingsoonBean) data).getResult();
            adapter2 = new FilmDateils_commingsoonBean_Adapter(result2,this);
            zrecyclerview.setAdapter(adapter2);
            adapter2.setOnClickLinsenter(new FilmDateils_commingsoonBean_Adapter.setOnClick() {
                @Override
                public void setAttention(int position) {
                    int followMovie = result2.get(position).getFollowMovie();
                    id2 = result2.get(position).getId();
                    if (followMovie==1){
                        result2.get(position).setFollowMovie(2);
                        sp = getSharedPreferences("login", MODE_PRIVATE);
                        headmap = new HashMap<>();
                        map = new HashMap<>();
                        sessionId = sp.getString("sessionId", "");
                        userId = sp.getInt("userId", 0);
                        headmap.put("userId", userId);
                        headmap.put("sessionId", sessionId);
                        map.put("movieId", id2);
                        presenter.get(Contacts.ATTENTION_NO,headmap,map,Film_attention_noBean.class);
                    }else{
                        result2.get(position).setFollowMovie(1);
                        sp = getSharedPreferences("login", MODE_PRIVATE);
                        headmap = new HashMap<>();
                        map = new HashMap<>();
                        sessionId = sp.getString("sessionId", "");
                        userId = sp.getInt("userId", 0);
                        headmap.put("userId", userId);
                        headmap.put("sessionId", sessionId);
                        map.put("movieId", id2);
                        presenter.get(Contacts.ATTENTION_YES,headmap,map,Film_attention_yesBean.class);
                    }
                }

                @Override
                public void setData(int position) {
                    FilmRecycler_commingsoonBean.ResultBean resultBean = result2.get(position);
                    Intent intent=new Intent(Film_DateilsActivity.this,Film_Dateils_xiangqingActivity.class);
                    intent.putExtra("list2",resultBean);
                    intent.putExtra("type",3);
                    startActivity(intent);
                }
            });
            adapter2.notifyDataSetChanged();
        }else if (data instanceof Film_attention_yesBean){
            ToastUtil.Toast(((Film_attention_yesBean) data).getMessage());
        }else if (data instanceof Film_attention_noBean){
            ToastUtil.Toast(((Film_attention_noBean) data).getMessage());
        }
    }

    /*
     * 失败回调
     * */
    @Override
    public void error(String error) {
        ToastUtil.Toast(error+"");
    }

}
