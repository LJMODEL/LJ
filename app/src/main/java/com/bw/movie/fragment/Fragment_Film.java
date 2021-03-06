package com.bw.movie.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.Film_DateilsActivity;
import com.bw.movie.activity.Film_Dateils_xiangqingActivity;
import com.bw.movie.adapter.BannerAdapter;
import com.bw.movie.adapter.FilmRecycler_RemenBean_Adapter;
import com.bw.movie.adapter.FilmRecycler_actionBean_Adapter;
import com.bw.movie.adapter.FilmRecycler_commingsoonBean_Adapter;
import com.bw.movie.bean.FilmRecycler_RemenBean;
import com.bw.movie.bean.FilmRecycler_actionBean;
import com.bw.movie.bean.FilmRecycler_commingsoonBean;
import com.bw.movie.presenter.MyPresenterImpl;
import com.bw.movie.utils.Contacts;
import com.bw.movie.utils.ToastUtil;
import com.bw.movie.view.MyView;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import recycler.coverflow.CoverFlowLayoutManger;
import recycler.coverflow.RecyclerCoverFlow;

import static android.content.Context.MODE_PRIVATE;


public class Fragment_Film extends Fragment implements MyView, View.OnClickListener {
    private RecyclerView recy_remen;
    private RecyclerView recy_shangying;
    private RecyclerView recy_jijiang;
    private MyPresenterImpl presenter;
    private SharedPreferences sp;
    private String sessionId;
    private int userId;
    private Map<String, Object> headmap;
    private HashMap<String, Object> map;
    private TextView tv_search;
    private LinearLayout ll_search;
    boolean isExpand = false;
    private TransitionSet mSet;
    private EditText et_search_content;
    private RecyclerCoverFlow banner_garrly;
    private ProgressBar movie_total_shi;
    private RelativeLayout movie_total_zhi;
    private ImageView jinru;
    private ImageView jinru1;
    private ImageView jinru2;
    private BannerAdapter bannerAdapter;
    private FilmRecycler_RemenBean_Adapter adapter;
    private FilmRecycler_actionBean_Adapter adapter1;
    private FilmRecycler_commingsoonBean_Adapter adapter2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_film, null);
        initView(view);
        headmap = new HashMap<>();
        map = new HashMap<>();
        sessionId = sp.getString("sessionId", "");
        userId = sp.getInt("userId", 0);
        headmap.put("userId", userId);
        headmap.put("sessionId", sessionId);
        map.put("page", 1 + "");
        map.put("count", 5 + "");
        presenter.get(Contacts.FILM_RECYCLER_HOT, headmap, map, FilmRecycler_RemenBean.class);
        return view;
    }
    //初始化
    private void initView(View view) {
        sp = getActivity().getSharedPreferences("login", MODE_PRIVATE);
        recy_remen = view.findViewById(R.id.recy_remen);
        recy_shangying = view.findViewById(R.id.recy_shangying);
        recy_jijiang = view.findViewById(R.id.recy_jijiang);
        LinearLayoutManager ms = new LinearLayoutManager(getActivity());
        ms.setOrientation(LinearLayoutManager.HORIZONTAL);
        LinearLayoutManager ms1 = new LinearLayoutManager(getActivity());
        ms1.setOrientation(LinearLayoutManager.HORIZONTAL);
        LinearLayoutManager ms2 = new LinearLayoutManager(getActivity());
        ms2.setOrientation(LinearLayoutManager.HORIZONTAL);
        recy_remen.setLayoutManager(ms);
        recy_shangying.setLayoutManager(ms1);
        recy_jijiang.setLayoutManager(ms2);
        presenter = new MyPresenterImpl(this);
        tv_search = view.findViewById(R.id.tv_search);
        tv_search.setOnClickListener(this);
        ll_search = view.findViewById(R.id.ll_search);
        ll_search.setOnClickListener(this);
        et_search_content = view.findViewById(R.id.et_search_content);
        et_search_content.setOnClickListener(this);
        banner_garrly = view.findViewById(R.id.banner_garrly);
        banner_garrly.setOnClickListener(this);
        bannerAdapter = new BannerAdapter(getContext());
        banner_garrly.setAdapter(bannerAdapter);
        banner_garrly.smoothScrollToPosition(2);

        movie_total_shi = view.findViewById(R.id.movie_total_shi);
        movie_total_shi.setOnClickListener(this);
        final int width = movie_total_shi.getWidth();
        movie_total_zhi = view.findViewById(R.id.movie_total_zhi);
        movie_total_zhi.setOnClickListener(this);
        final int width1 = movie_total_zhi.getWidth();
        //拿到中间显示的图片下标
        bannerAdapter.setOnItemClick(new BannerAdapter.OnItemClick() {
            @Override
            public void clickItem(int position) {
                banner_garrly.smoothScrollToPosition(position);
            }
        });
        banner_garrly.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {
            @Override
            public void onItemSelected(int position) {

            }
        });
        jinru = (ImageView) view.findViewById(R.id.jinru);
        jinru.setOnClickListener(this);
        jinru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tiao();
            }
        });
        jinru1 = (ImageView) view.findViewById(R.id.jinru1);
        jinru1.setOnClickListener(this);
        jinru1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tiao();
            }
        });
        jinru2 = (ImageView) view.findViewById(R.id.jinru2);
        jinru2.setOnClickListener(this);
        jinru2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tiao();
            }
        });
    }
    //跳转
    private void tiao() {
        startActivity(new Intent(getActivity(),Film_DateilsActivity.class));
    }

    @Override
    public void success(Object data) {
        if (data instanceof FilmRecycler_RemenBean) {
            final List<FilmRecycler_RemenBean.ResultBean> result = ((FilmRecycler_RemenBean) data).getResult();
            adapter = new FilmRecycler_RemenBean_Adapter(result, getActivity());
            recy_remen.setAdapter(adapter);
            recy_remen.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    outRect.left = 20;
                }
            });
            adapter.setOnItemClick(new FilmRecycler_RemenBean_Adapter.setOnItem() {
                @Override
                public void setlocition(int position) {
                    ToastUtil.Toast(result.get(position).getName());
                    /*FilmRecycler_RemenBean.ResultBean resultBean = result.get(position);
                    Intent intent=new Intent(getActivity(),Film_Dateils_xiangqingActivity.class);
                    intent.putExtra("beanlist1", resultBean);
                    intent.putExtra("list_type",1);
                    startActivity(intent);*/
                }
            });
            presenter.get(Contacts.FILM_RECYCLER_ACTION, headmap, map, FilmRecycler_actionBean.class);
        } else if (data instanceof FilmRecycler_actionBean) {
            final List<FilmRecycler_actionBean.ResultBean> result = ((FilmRecycler_actionBean) data).getResult();
            adapter1 = new FilmRecycler_actionBean_Adapter(result, getActivity());
            recy_shangying.setAdapter(adapter1);
            recy_shangying.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    outRect.left = 20;
                }
            });
            adapter1.setOnItemClick(new FilmRecycler_actionBean_Adapter.setOnItem() {
                @Override
                public void setlocition(int position) {
                    ToastUtil.Toast(result.get(position).getName());
                    /*FilmRecycler_actionBean.ResultBean resultBean = result.get(position);
                    Intent intent=new Intent(getActivity(),Film_Dateils_xiangqingActivity.class);
                    intent.putExtra("beanlist2", resultBean);
                    intent.putExtra("list_type",2);
                    startActivity(intent);*/
                }
            });
            presenter.get(Contacts.FILM_RECYCLER_COMMINGSOON, headmap, map, FilmRecycler_commingsoonBean.class);
        } else if (data instanceof FilmRecycler_commingsoonBean) {
            final List<FilmRecycler_commingsoonBean.ResultBean> result = ((FilmRecycler_commingsoonBean) data).getResult();
            adapter2 = new FilmRecycler_commingsoonBean_Adapter(result, getActivity());
            recy_jijiang.setAdapter(adapter2);
            recy_jijiang.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    outRect.left = 20;
                }
            });
            adapter2.setOnItemClick(new FilmRecycler_commingsoonBean_Adapter.setOnItem() {
                @Override
                public void setlocition(int position) {
                    ToastUtil.Toast(result.get(position).getName());
                    /*FilmRecycler_commingsoonBean.ResultBean resultBean = result.get(position);
                    Intent intent=new Intent(getActivity(),Film_Dateils_xiangqingActivity.class);
                    intent.putExtra("beanlist3", resultBean);
                    intent.putExtra("list_type",3);
                    startActivity(intent);*/
                }
            });
        }
    }

    @Override
    public void error(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_search:
                if (!isExpand) {
                    expand();
                    isExpand = true;
                } else if (isExpand) {
                    reduce();
                    isExpand = false;
                }
                break;
            case R.id.tv_search:
                if (!isExpand) {
                    expand();
                    isExpand = true;
                } else if (isExpand) {
                    reduce();
                    isExpand = false;
                }
                break;
        }
    }

    private void expand() {
        //设置伸展状态时的布局
        //etSearchContent.setHint("搜索你想搜索的内容");
        et_search_content.setVisibility(View.VISIBLE);
        tv_search.setVisibility(View.VISIBLE);
        RelativeLayout.LayoutParams LayoutParams = (RelativeLayout.LayoutParams) ll_search.getLayoutParams();
        LayoutParams.width = dip2px(220);
        LayoutParams.setMargins(dip2px(0), dip2px(32), dip2px(0), dip2px(0));
        ll_search.setLayoutParams(LayoutParams);
        //开始动画
        beginDelayedTransition(ll_search);
    }

    private void reduce() {
        //设置收缩状态时的布局
        et_search_content.setText("");
        et_search_content.setVisibility(View.GONE);
        tv_search.setVisibility(View.GONE);
        RelativeLayout.LayoutParams LayoutParams = (RelativeLayout.LayoutParams) ll_search.getLayoutParams();
        LayoutParams.width = dip2px(60);
        LayoutParams.setMargins(dip2px(0), dip2px(32), dip2px(0), dip2px(0));
        ll_search.setLayoutParams(LayoutParams);
        //开始动画
        beginDelayedTransition(ll_search);
    }

    @SuppressLint("NewApi")
    void beginDelayedTransition(ViewGroup view) {
        mSet = new AutoTransition();
        mSet.setDuration(300);
        TransitionManager.beginDelayedTransition(view, mSet);
    }

    private int dip2px(float dpVale) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpVale * scale + 0.5f);
    }

}
