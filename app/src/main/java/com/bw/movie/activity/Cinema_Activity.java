package com.bw.movie.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.bw.movie.adapter.BannerAdapter;
import com.bw.movie.adapter.Cinmea_PingLun_Adapter;
import com.bw.movie.adapter.Schedule_Adapter;
import com.bw.movie.bean.CinemaXiangQing_Bean;
import com.bw.movie.bean.Cinema_DianZhan;
import com.bw.movie.bean.Cinema_PingLun_Bean;
import com.bw.movie.bean.Cinema_XIe_PingLun_Bean;
import com.bw.movie.bean.Movie_Scheduling_Bean;
import com.bw.movie.presenter.MyPresenterImpl;
import com.bw.movie.utils.Contacts;
import com.bw.movie.utils.FragmentFeedCmt;
import com.bw.movie.view.MyView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import recycler.coverflow.CoverFlowLayoutManger;
import recycler.coverflow.RecyclerCoverFlow;

public class Cinema_Activity extends AppCompatActivity implements View.OnClickListener, MyView {

    private ImageView imageView, cinemas_img, xie;
    private TextView cinemas_name, cinemas_neirong, recommend_detail_particulars, recommend_detail_comment, detail_dialog_address, detail_dialog_phone, detail_dialog_subway, detail_dialog_bus, detail_dialog_self;
    private SharedPreferences sp;
    private Map<String, Object> headmap;
    private MyPresenterImpl myPresenter;
    private Map<String, Object> map;
    private String sessionId;
    private int userId;
    private RecyclerCoverFlow banner_garrly;
    private BannerAdapter bannerAdapter;
    private Schedule_Adapter schedule_adapter;
    private FragmentFeedCmt fragmentFeedCmt;
    private int cinemaId;
    private RecyclerView recyclerView, coment_recy;
    private CinemaXiangQing_Bean.ResultBean result;
    private LinearLayout particulars_recy;
    private int i = 1;
    private Cinmea_PingLun_Adapter cinmea_pingLun_adapter;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema_);
        inView();
        inInt();
    }


    private void inView() {

        myPresenter = new MyPresenterImpl(this);
        sp = getSharedPreferences("login", MODE_PRIVATE);
        Intent intent = getIntent();
        cinemaId = intent.getIntExtra("cinemaId", 0);
        recyclerView = findViewById(R.id.recyclerview);
        imageView = findViewById(R.id.img);
        cinemas_img = findViewById(R.id.cinemas_img);
        cinemas_name = findViewById(R.id.cinemas_name);
        cinemas_neirong = findViewById(R.id.cinemas_neirong);
        banner_garrly = findViewById(R.id.banner_garrly);
        banner_garrly.setOnClickListener(this);
        cinemas_img.setOnClickListener(this);
        //档期的事务管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        schedule_adapter = new Schedule_Adapter(null, this);
        bannerAdapter = new BannerAdapter(this);
        banner_garrly.setAdapter(bannerAdapter);
        banner_garrly.smoothScrollToPosition(2);
        imageView.setOnClickListener(this);
        sessionId = sp.getString("sessionId", "");
        userId = sp.getInt("userId", 0);
        headmap = new HashMap<>();
        map = new HashMap<>();
        headmap.put("userId", userId);
        headmap.put("sessionId", sessionId);
        map.put("cinemaId", cinemaId);
        myPresenter.get(Contacts.CINEMA_XIANGQING, headmap, map, CinemaXiangQing_Bean.class);

        //滑动监听图片展示档期
        banner_garrly.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {
            @Override
            public void onItemSelected(int position) {
                Toast.makeText(Cinema_Activity.this, position + "", Toast.LENGTH_SHORT).show();
                Map<String, Object> headmapa = new HashMap<>();
                Map<String, Object> mapa = new HashMap<>();
                mapa.put("cinemasId", cinemaId);
                mapa.put("movieId", position);
                myPresenter.get(Contacts.MOVIEW_LIEBIAO, headmapa, mapa, Movie_Scheduling_Bean.class);
            }
        });
    }

    private void inInt() {
        //点击图片吐司
        /*bannerAdapter.setOnItemClick(new BannerAdapter.OnItemClick() {
            @Override
            public void clickItem(int position) {
                banner_garrly.smoothScrollToPosition(position);
                Toast.makeText(Cinema_Activity.this, position + "", Toast.LENGTH_SHORT).show();
            }
        });*/

    }


    private void inData() {
        sessionId = sp.getString("sessionId", "");
        userId = sp.getInt("userId", 0);
        headmap = new HashMap<>();
        map = new HashMap<>();
        headmap.put("userId", userId);
        headmap.put("sessionId", sessionId);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img:
                finish();
                break;
            case R.id.cinemas_img:
                showDetailsDialog();
                break;
            case R.id.recommend_detail_particulars:
                particulars_recy.setVisibility(View.VISIBLE);
                coment_recy.setVisibility(View.GONE);
                xie.setVisibility(View.GONE);
                break;
            case R.id.recommend_detail_comment:
                particulars_recy.setVisibility(View.GONE);
                coment_recy.setVisibility(View.VISIBLE);
                xie.setVisibility(View.VISIBLE);
                inData();
                map.put("cinemaId", cinemaId);
                map.put("page", i);
                map.put("count", 5);
                myPresenter.get(Contacts.CINEMA_PINGLUN, headmap, map, Cinema_PingLun_Bean.class);
                break;
            case R.id.detail_dialog_phone:
                //拨打电话
                Toast.makeText(this, "jaja", Toast.LENGTH_SHORT).show();
                String s = detail_dialog_phone.getText().toString();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + s);
                intent.setData(data);
                startActivity(intent);
                break;
            case R.id.xie:
                showAlertDialog();
                break;
        }
    }

    private void showDetailsDialog() {
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        //获得屏幕高度
        int mHeight = wm.getDefaultDisplay().getHeight();
        if (fragmentFeedCmt == null) {
            fragmentFeedCmt = FragmentFeedCmt.newInstance(123l);
        }
        //引入布局
        View view = View.inflate(this, R.layout.cinema_details, null);
        recommend_detail_particulars = view.findViewById(R.id.recommend_detail_particulars);
        recommend_detail_comment = view.findViewById(R.id.recommend_detail_comment);
        coment_recy = view.findViewById(R.id.coment_recy);
        recommend_detail_particulars.setOnClickListener(this);
        recommend_detail_comment.setOnClickListener(this);

        //找控件
        detail_dialog_address = view.findViewById(R.id.detail_dialog_address);
        detail_dialog_phone = view.findViewById(R.id.detail_dialog_phone);
        detail_dialog_subway = view.findViewById(R.id.detail_dialog_subway);
        detail_dialog_bus = view.findViewById(R.id.detail_dialog_bus);
        detail_dialog_self = view.findViewById(R.id.detail_dialog_self);
        particulars_recy = view.findViewById(R.id.particulars_recy);
        xie = view.findViewById(R.id.xie);
        xie.setVisibility(View.GONE);
        coment_recy.setVisibility(View.GONE);
        detail_dialog_address.setText(result.getAddress());
        detail_dialog_phone.setText(result.getPhone() + "");
        detail_dialog_subway.setText(result.getVehicleRoute());
        //点击电话
        detail_dialog_phone.setOnClickListener(this);
        //写评论
        xie.setOnClickListener(this);
        //recy布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        coment_recy.setLayoutManager(linearLayoutManager);

        fragmentFeedCmt.setView(view);
        //设置dialog的高度
        fragmentFeedCmt.setTrueHeight(mHeight * 4 / 5);
        fragmentFeedCmt.show(getSupportFragmentManager(), "Dialog");


    }

    @Override
    public void success(Object data) {
        if (data instanceof CinemaXiangQing_Bean) {
            CinemaXiangQing_Bean cinemaXiangQing_bean = (CinemaXiangQing_Bean) data;
            result = cinemaXiangQing_bean.getResult();
            Glide.with(this).load(result.getLogo()).into(cinemas_img);
            cinemas_name.setText(result.getName());
            cinemas_neirong.setText(result.getAddress());
        } else if (data instanceof Movie_Scheduling_Bean) {
            //滑动轮播图
            Movie_Scheduling_Bean movie_scheduling_bean = (Movie_Scheduling_Bean) data;
            List<Movie_Scheduling_Bean.ResultBean> result = movie_scheduling_bean.getResult();
            schedule_adapter.setList(result);
            recyclerView.setAdapter(schedule_adapter);
        } else if (data instanceof Cinema_PingLun_Bean) {
            //评论列表
            cinmea_pingLun_adapter = new Cinmea_PingLun_Adapter(this);
            coment_recy.setAdapter(cinmea_pingLun_adapter);
            Cinema_PingLun_Bean cinema_pingLun_bean = (Cinema_PingLun_Bean) data;
            List<Cinema_PingLun_Bean.ResultBean> result = cinema_pingLun_bean.getResult();
            cinmea_pingLun_adapter.setList(result);
        } else if (data instanceof Cinema_DianZhan) {
            //点赞
            Cinema_DianZhan cinema_dianZhan = (Cinema_DianZhan) data;
            Toast.makeText(this, cinema_dianZhan.getMessage(), Toast.LENGTH_SHORT).show();
            cinmea_pingLun_adapter.notifyDataSetChanged();
        } else if (data instanceof Cinema_XIe_PingLun_Bean) {
            //写评论
            Cinema_XIe_PingLun_Bean xIe_pingLun_bean = (Cinema_XIe_PingLun_Bean) data;
            if (((Cinema_XIe_PingLun_Bean) data).getStatus().equals("0000")) {
                cinmea_pingLun_adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
            Toast.makeText(this, xIe_pingLun_bean.getMessage(), Toast.LENGTH_SHORT).show();
        }


        //接口回调
        cinmea_pingLun_adapter.setDianItem(new Cinmea_PingLun_Adapter.DianItem() {
            @Override
            public void Item(int i) {
                inData();
                map.put("commentId", i);
                myPresenter.getpost(Contacts.CINEMA_DAINZHI, headmap, map, Cinema_DianZhan.class);

            }
        });


    }

    @Override
    public void error(String error) {

    }

    /**
     * 写评论
     */
    private void showAlertDialog() {
        dialog = new AlertDialog.Builder(this).create();
        dialog.setView(LayoutInflater.from(this).inflate(R.layout.alert_dialog, null));
        dialog.show();
        dialog.getWindow().setContentView(R.layout.alert_dialog);
        Button btnPositive = (Button) dialog.findViewById(R.id.btn_add);
        Button btnNegative = (Button) dialog.findViewById(R.id.btn_cancel);
        final EditText etContent = (EditText) dialog.findViewById(R.id.et_content);
        btnPositive.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String str = etContent.getText().toString();
                if (isNullEmptyBlank(str)) {
                    etContent.setError("输入内如不能为空");
                } else {
                    Map<String, Object> headmap = new HashMap<>();
                    Map<String, Object> map = new HashMap<>();
                    sessionId = sp.getString("sessionId", "");
                    userId = sp.getInt("userId", 0);
                    headmap.put("userId", userId);
                    headmap.put("sessionId", sessionId);
                    map.put("cinemaId", cinemaId);
                    map.put("commentContent", str);
                    dialog.dismiss();
                    myPresenter.getpost(Contacts.CINEMA_XIEGUANZHU, headmap, map, Cinema_XIe_PingLun_Bean.class);
                }
            }
        });
        btnNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
    }

    private static boolean isNullEmptyBlank(String str) {
        if (str == null || "".equals(str) || "".equals(str.trim()))
            return true;
        return false;
    }

}
