package com.bw.movie.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.bw.movie.adapter.Film_review_adapter;
import com.bw.movie.adapter.Fore_show_adapter;
import com.bw.movie.adapter.Fore_show_picture_adapter;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.bean.FilmRecycler_RemenBean;
import com.bw.movie.bean.FilmRecycler_actionBean;
import com.bw.movie.bean.FilmRecycler_commingsoonBean;
import com.bw.movie.bean.Film_details_QueryBean;
import com.bw.movie.bean.Query_ReviewBean;
import com.bw.movie.presenter.MyPresenterImpl;
import com.bw.movie.utils.Contacts;
import com.bw.movie.utils.FragmentFeedCmt;
import com.bw.movie.utils.ToastUtil;
import com.bw.movie.view.MyView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Film_Dateils_xiangqingActivity extends BaseActivity implements View.OnClickListener, MyView {

    private TextView name_details;
    private ImageView image_details;
    private ImageView details_guanzhu;
    private Button film_details_xiangqing;
    //弹框
    private FragmentFeedCmt mMBottomSheetDialogFragment;
    private Button film_details_foreshow;
    private Button film_details_still;
    private Button film_review;
    private ImageView film_back;
    private Button film_pay;
    private MyPresenterImpl presenter;
    private Map<String, Object> headmap;
    private HashMap<String, Object> map;
    private String sessionId;
    private int userId;
    private SharedPreferences sp;
    private FilmRecycler_RemenBean.ResultBean list;
    private FilmRecycler_actionBean.ResultBean list1;
    private FilmRecycler_commingsoonBean.ResultBean list2;
    private ImageView img_detail_mv;
    private TextView tv_type_mv;
    private TextView tv_director_mv;
    private TextView tv_time_mv;
    private TextView tv_place_mv;
    private ImageView diss_log;
    private TextView actor_name;
    private TextView content_mv_detail;
    private int type;
    private Film_details_QueryBean.ResultBean result;
    private View view;
    private View view1;
    private View view2;
    private View view3;
    private RecyclerView foreshow_recy;
    private ImageView diss_log_foreshow;
    private List<Film_details_QueryBean.ResultBean.ShortFilmListBean> shortFilmList=new ArrayList<>();
    private RecyclerView foreshow_picture_recy;
    private RecyclerView foreshow_review_recy;
    private ImageView diss_log_foreshow_picture;
    private ImageView film_review_diss;
    private List<Query_ReviewBean.ResultBean> list_review=new ArrayList<>();
    private Film_review_adapter adapter;
    /*private int list_type;
    private FilmRecycler_RemenBean.ResultBean beanlist1;
    private FilmRecycler_actionBean.ResultBean beanlist2;
    private FilmRecycler_commingsoonBean.ResultBean beanlist3;*/
    protected boolean flag=true;
    //手指上下滑动时的最小速度
    private static final int YSPEED_MIN = 1000;

    //手指向右滑动时的最小距离
    private static final int XDISTANCE_MIN = 100;

    //手指向上滑或下滑时的最小距离
    private static final int YDISTANCE_MIN = 100;

    //记录手指按下时的横坐标。
    private float xDown;

    //记录手指按下时的纵坐标。
    private float yDown;

    //记录手指移动时的横坐标。
    private float xMove;

    //记录手指移动时的纵坐标。
    private float yMove;

    //用于计算手指滑动的速度。
    private VelocityTracker mVelocityTracker;

    @Override
    protected int setLayout() {
        return R.layout.film_details;
    }

    @Override
    protected void initView() {
        name_details = findViewById(R.id.name_details);
        image_details = findViewById(R.id.image_details);

        film_details_xiangqing = findViewById(R.id.film_details_xiangqing);
        film_details_xiangqing.setOnClickListener(this);

        film_details_foreshow = findViewById(R.id.film_details_foreshow);
        film_details_foreshow.setOnClickListener(this);

        film_details_still = findViewById(R.id.film_details_still);
        film_details_still.setOnClickListener(this);

        film_review = findViewById(R.id.film_review);
        film_review.setOnClickListener(this);

        film_back = findViewById(R.id.film_back);
        film_back.setOnClickListener(this);

        film_pay = findViewById(R.id.film_pay);
        film_pay.setOnClickListener(this);

        details_guanzhu = findViewById(R.id.details_guanzhu);
        details_guanzhu.setOnClickListener(this);

        presenter = new MyPresenterImpl(this);
        /*beanlist1 = (FilmRecycler_RemenBean.ResultBean) getIntent().getSerializableExtra("list1");
        beanlist2 = (FilmRecycler_actionBean.ResultBean) getIntent().getSerializableExtra("list2");
        beanlist3 = (FilmRecycler_commingsoonBean.ResultBean) getIntent().getSerializableExtra("list3");
        list_type = getIntent().getIntExtra("list_type",0);*/
    }

    @Override
    protected void initData() {
        list = (FilmRecycler_RemenBean.ResultBean) getIntent().getSerializableExtra("list");
        type = getIntent().getIntExtra("type", 0);
        list1 = (FilmRecycler_actionBean.ResultBean) getIntent().getSerializableExtra("list1");
        list2 = (FilmRecycler_commingsoonBean.ResultBean) getIntent().getSerializableExtra("list2");

        if (list instanceof FilmRecycler_RemenBean.ResultBean) {
            name_details.setText(list.getName());
            Glide.with(this).load(list.getImageUrl()).into(image_details);
            int followMovie = list.getFollowMovie();
            if (followMovie == 1) {
                details_guanzhu.setImageResource(R.mipmap.com_icon_collection_selected_hdpi);
            } else {
                details_guanzhu.setImageResource(R.mipmap.com_icon_collection_default_hdpi);
            }
        } else if (list1 instanceof FilmRecycler_actionBean.ResultBean) {
            name_details.setText(list1.getName());
            Glide.with(this).load(list1.getImageUrl()).into(image_details);
            int followMovie = list1.getFollowMovie();
            if (followMovie == 1) {
                details_guanzhu.setImageResource(R.mipmap.com_icon_collection_selected_hdpi);
            } else {
                details_guanzhu.setImageResource(R.mipmap.com_icon_collection_default_hdpi);
            }
        } else if (list2 instanceof FilmRecycler_commingsoonBean.ResultBean) {
            name_details.setText(list2.getName());
            Glide.with(this).load(list2.getImageUrl()).into(image_details);
            int followMovie = list2.getFollowMovie();
            if (followMovie == 1) {
                details_guanzhu.setImageResource(R.mipmap.com_icon_collection_selected_hdpi);
            } else {
                details_guanzhu.setImageResource(R.mipmap.com_icon_collection_default_hdpi);
            }
        }/*else if (list_type==3){
            name_details.setText(list2.getName());
            Glide.with(this).load(list2.getImageUrl()).into(image_details);
            int followMovie = list2.getFollowMovie();
            if (followMovie == 1) {
                details_guanzhu.setImageResource(R.mipmap.com_icon_collection_selected_hdpi);
            } else {
                details_guanzhu.setImageResource(R.mipmap.com_icon_collection_default_hdpi);
            }
        }else if (list_type==2){
            name_details.setText(list1.getName());
            Glide.with(this).load(list1.getImageUrl()).into(image_details);
            int followMovie = list1.getFollowMovie();
            if (followMovie == 1) {
                details_guanzhu.setImageResource(R.mipmap.com_icon_collection_selected_hdpi);
            } else {
                details_guanzhu.setImageResource(R.mipmap.com_icon_collection_default_hdpi);
            }
        } else if (list_type==1){
            name_details.setText(list.getName());
            Glide.with(this).load(list.getImageUrl()).into(image_details);
            int followMovie = list.getFollowMovie();
            if (followMovie == 1) {
                details_guanzhu.setImageResource(R.mipmap.com_icon_collection_selected_hdpi);
            } else {
                details_guanzhu.setImageResource(R.mipmap.com_icon_collection_default_hdpi);
            }
        }*/
        //接口查询的数据
        sp = getSharedPreferences("login", MODE_PRIVATE);
        sessionId = sp.getString("sessionId", "");
        userId = sp.getInt("userId", 0);
        headmap = new HashMap<>();
        map = new HashMap<>();
        headmap.put("userId", userId);
        headmap.put("sessionId", sessionId);
        if (type == 1) {
            int id = list.getId();
            map.put("movieId", id);
            Log.e("id", id + "");
        } else if (type == 2) {
            int id = list1.getId();
            map.put("movieId", id);
            Log.e("id", id + "");
        } else if (type == 3) {
            int id = list2.getId();
            map.put("movieId", id);
            Log.e("id", id + "");
        }
        presenter.get(Contacts.FILMQUERY, headmap, map, Film_details_QueryBean.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //电影
            case R.id.film_details_xiangqing:
                showData_xiangqiang();//赋值
                break;
            //预告
            case R.id.film_details_foreshow:
                showData_foreshow();
                break;
            //剧照
            case R.id.film_details_still:
                showData_pictrue();
                break;
            //影评
            case R.id.film_review:

                showData_review();
                break;
            //返回
            case R.id.film_back:
                finish();
                break;
            //购买
            case R.id.film_pay:
                Intent intent=new Intent(this,Film_change_cimenaActivity.class);
                if (type == 1) {
                    int id = list.getId();
                    intent.putExtra("movieId",id);
                    intent.putExtra("name",list.getName());
                    Log.e("id", id + "");
                } else if (type == 2) {
                    int id = list1.getId();
                    intent.putExtra("movieId",id);
                    intent.putExtra("name",list1.getName());
                    Log.e("id", id + "");
                } else if (type == 3) {
                    int id = list2.getId();
                    intent.putExtra("movieId",id);
                    intent.putExtra("name",list2.getName());
                    Log.e("id", id + "");
                }
                startActivity(intent);
                break;
        }
    }
    /*
    *
    * 影评
    * */
    private void showData_review() {
        map.put("page",1);
        map.put("count",10);
        presenter.get(Contacts.QUERY_REVIEW,headmap,map,Query_ReviewBean.class);
        view3 = View.inflate(this, R.layout.film_review, null);
        foreshow_review_recy = view3.findViewById(R.id.foreshow_review_recy);
        foreshow_review_recy.setLayoutManager(new GridLayoutManager(this,1));
        film_review_diss = view3.findViewById(R.id.film_review_diss);
        film_review_diss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMBottomSheetDialogFragment.dismiss();
            }
        });
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        //获得屏幕高度
        int mHeight = wm.getDefaultDisplay().getHeight();
        if (mMBottomSheetDialogFragment == null) {
            mMBottomSheetDialogFragment = FragmentFeedCmt.newInstance(123l);
        }
        //设置dialog的布局(传入参数View inflate = inflater.inflate(view, container);)
        mMBottomSheetDialogFragment.setView(view3);
        //设置dialog的高度
        mMBottomSheetDialogFragment.setTrueHeight(mHeight * 4 / 5);
        mMBottomSheetDialogFragment.show(getSupportFragmentManager(), "Dialog");
    }
    /*
     *
     * 剧照
     * */
    private void showData_pictrue() {
        view2 = View.inflate(this, R.layout.fore_show_picture, null);
        foreshow_picture_recy = view2.findViewById(R.id.foreshow_picture_recy);
        foreshow_picture_recy.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        diss_log_foreshow_picture = view2.findViewById(R.id.diss_log_foreshow_picture);
        diss_log_foreshow_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMBottomSheetDialogFragment.dismiss();
            }
        });
        Fore_show_picture_adapter adapter= new Fore_show_picture_adapter(result,this);
        foreshow_picture_recy.setAdapter(adapter);
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        //获得屏幕高度
        int mHeight = wm.getDefaultDisplay().getHeight();
        if (mMBottomSheetDialogFragment == null) {
            mMBottomSheetDialogFragment = FragmentFeedCmt.newInstance(123l);
        }
        //设置dialog的布局(传入参数View inflate = inflater.inflate(view, container);)
        mMBottomSheetDialogFragment.setView(view2);
        //设置dialog的高度
        mMBottomSheetDialogFragment.setTrueHeight(mHeight * 4 / 5);
        mMBottomSheetDialogFragment.show(getSupportFragmentManager(), "Dialog");
    }
    /*
     *
     * 预告片
     * */
    private void showData_foreshow() {
        view1 = View.inflate(this, R.layout.foreshow, null);
        foreshow_recy = view1.findViewById(R.id.foreshow_recy);
        foreshow_recy.setLayoutManager(new GridLayoutManager(this,1));
        diss_log_foreshow = view1.findViewById(R.id.diss_log_foreshow);
        diss_log_foreshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMBottomSheetDialogFragment.dismiss();
            }
        });
        Fore_show_adapter adapter=new Fore_show_adapter(shortFilmList,this);
        foreshow_recy.setAdapter(adapter);
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        //获得屏幕高度
        int mHeight = wm.getDefaultDisplay().getHeight();
        if (mMBottomSheetDialogFragment == null) {
            mMBottomSheetDialogFragment = FragmentFeedCmt.newInstance(123l);
        }
        //设置dialog的布局(传入参数View inflate = inflater.inflate(view, container);)
        mMBottomSheetDialogFragment.setView(view1);
        //设置dialog的高度
        mMBottomSheetDialogFragment.setTrueHeight(mHeight * 4 / 5);
        mMBottomSheetDialogFragment.show(getSupportFragmentManager(), "Dialog");
    }
    /*
     *
     * 详情
     * */
    private void showData_xiangqiang() {
        view = View.inflate(this, R.layout.dialog_bottom_sheet, null);
        img_detail_mv = view.findViewById(R.id.img_detail_mv);
        Glide.with(this).load(result.getImageUrl()).into(img_detail_mv);
        tv_type_mv = view.findViewById(R.id.tv_type_mv);
        tv_type_mv.setText(result.getMovieTypes() + "");
        tv_director_mv = view.findViewById(R.id.tv_director_mv);
        tv_director_mv.setText(result.getDirector() + "");
        tv_time_mv = view.findViewById(R.id.tv_time_mv);
        tv_time_mv.setText(result.getDuration() + "");
        tv_place_mv = view.findViewById(R.id.tv_place_mv);
        tv_place_mv.setText(result.getPlaceOrigin() + "");
        diss_log = view.findViewById(R.id.diss_log);
        diss_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMBottomSheetDialogFragment.dismiss();
            }
        });
        actor_name = view.findViewById(R.id.actor_name);
        actor_name.setText(result.getStarring() + "");
        content_mv_detail = view.findViewById(R.id.content_mv_detail);
        content_mv_detail.setText(result.getSummary() + "");
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        //获得屏幕高度
        int mHeight = wm.getDefaultDisplay().getHeight();
        if (mMBottomSheetDialogFragment == null) {
            mMBottomSheetDialogFragment = FragmentFeedCmt.newInstance(123l);
        }
        //设置dialog的布局(传入参数View inflate = inflater.inflate(view, container);)
        mMBottomSheetDialogFragment.setView(view);
        //设置dialog的高度
        mMBottomSheetDialogFragment.setTrueHeight(mHeight * 4 / 5);
        mMBottomSheetDialogFragment.show(getSupportFragmentManager(), "Dialog");
    }

    @Override
    public void success(Object data) {
        //电影详情
        if (data instanceof Film_details_QueryBean) {
            result = ((Film_details_QueryBean) data).getResult();
            shortFilmList = result.getShortFilmList();
        }else if(data instanceof Query_ReviewBean){
            list_review = ((Query_ReviewBean) data).getResult();
            adapter = new Film_review_adapter(list_review,this);
            foreshow_review_recy.setAdapter(adapter);
        }
    }

    @Override
    public void error(String error) {
        Log.e("查询详情失败", error);
        ToastUtil.Toast(error);
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        //如果为真的话   实现右滑删除activity
        if (flag){
            createVelocityTracker(event);
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    xDown = event.getRawX();
                    yDown = event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    xMove = event.getRawX();
                    yMove= event.getRawY();
                    //滑动的距离
                    int distanceX = (int) (xMove - xDown);
                    int distanceY= (int) (yMove - yDown);
                    //获取顺时速度
                    int ySpeed = getScrollVelocity();
                    //关闭Activity需满足以下条件：
                    //1.x轴滑动的距离>XDISTANCE_MIN
                    //2.y轴滑动的距离在YDISTANCE_MIN范围内
                    //3.y轴上（即上下滑动的速度）<XSPEED_MIN，如果大于，则认为用户意图是在上下滑动而非左滑结束Activity
                    if(distanceX > XDISTANCE_MIN &&(distanceY<YDISTANCE_MIN&&distanceY>-YDISTANCE_MIN)&& ySpeed < YSPEED_MIN) {
                        finish();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    recycleVelocityTracker();
                    break;
                default:
                    break;
            }
        }
        return super.dispatchTouchEvent(event);
    }

    /**
     * 创建VelocityTracker对象，并将触摸界面的滑动事件加入到VelocityTracker当中。
     *
     * @param event
     *
     */
    private void createVelocityTracker(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }

    /**
     * 回收VelocityTracker对象。
     */
    private void recycleVelocityTracker() {
        mVelocityTracker.recycle();
        mVelocityTracker = null;
    }

    /**
     *
     * @return 滑动速度，以每秒钟移动了多少像素值为单位。
     */
    private int getScrollVelocity() {
        mVelocityTracker.computeCurrentVelocity(1000);
        int velocity = (int) mVelocityTracker.getYVelocity();
        return Math.abs(velocity);
    }
}
