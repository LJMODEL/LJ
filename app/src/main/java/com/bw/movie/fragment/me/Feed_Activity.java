package com.bw.movie.fragment.me;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bw.movie.R;
import com.bw.movie.bean.Feedback_Bean;
import com.bw.movie.presenter.MyPresenterImpl;
import com.bw.movie.utils.Contacts;
import com.bw.movie.utils.ToastUtil;
import com.bw.movie.view.MyView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Feed_Activity extends AppCompatActivity implements MyView {

    @BindView(R.id.opinion)
    EditText opinion;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.img)
    ImageView img;
    private SharedPreferences sp;
    private String sessionId;
    private int userId;
    private MyPresenterImpl myPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_);
        ButterKnife.bind(this);
        myPresenter = new MyPresenterImpl(this);
        sp = getSharedPreferences("login", MODE_PRIVATE);
    }

    @OnClick({R.id.submit, R.id.img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.submit:
                String s = opinion.getText().toString();
                if (s.equals("")) {
                    ToastUtil.Toast("反馈意见不能为空！");
                    return;
                } else {
                    Map<String, Object> headmap = new HashMap<>();
                    Map<String, Object> map = new HashMap<>();
                    sessionId = sp.getString("sessionId", "");
                    userId = sp.getInt("userId", 0);
                    headmap.put("userId", userId);
                    headmap.put("sessionId", sessionId);
                    map.put("content", s);
                    myPresenter.getpost(Contacts.FANKUI, headmap, map, Feedback_Bean.class);
                }

                break;
            case R.id.img:
                finish();
                break;
        }
    }

    @Override
    public void success(Object data) {
        if (data instanceof Feedback_Bean) {
            ToastUtil.Toast("反馈成功！");
            //View view = View.inflate(this, R.layout.fankui, null);
            finish();
        }
    }

    @Override
    public void error(String error) {

    }
}
