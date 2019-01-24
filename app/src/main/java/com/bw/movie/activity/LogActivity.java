package com.bw.movie.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.bean.LogBean;
import com.bw.movie.presenter.MyPresenterImpl;
import com.bw.movie.utils.Contacts;
import com.bw.movie.utils.EncryptUtil;
import com.bw.movie.utils.SpUtil;
import com.bw.movie.view.MyView;
import com.xw.repo.XEditText;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LogActivity extends BaseActivity implements MyView {

    @BindView(R.id.phone)
    XEditText phone;
    @BindView(R.id.pwd)
    XEditText pwd;
    @BindView(R.id.zhuce)
    TextView zhuce;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.weixin)
    ImageView weixin;
    @BindView(R.id.checkbox)
    CheckBox checkbox;
    private MyPresenterImpl presenter;

    @Override
    protected int setLayout() {
        return R.layout.activity_log;
    }

    @Override
    protected void initView() {
        presenter = new MyPresenterImpl(this);
    }

    @Override
    protected void initData() {
        /*if (SpUtil.getBoolean("jizhu", false)) {
            String mName = SpUtil.getString("name", "");
            String mPass = SpUtil.getString("pass", "");
            boolean jizhu = SpUtil.getBoolean("jizhu", true);
            phone.setText(mName+"");
            pwd.setText(mPass+"");
            checkbox.setChecked(jizhu);
        }*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.zhuce, R.id.login, R.id.weixin, R.id.checkbox})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.zhuce:
                Intent intent = new Intent(LogActivity.this, RegisteredActivity.class);
                //跳转动画
                //startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(LogActivity.this,zhuce,"shareNames").toBundle());
                startActivity(intent);
                finish();
                break;
            case R.id.login:
                //获得密码
                String mphone = phone.getText().toString();
                String mpwd = pwd.getText().toString();
                if (mphone.equals("")) {
                    Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
                } else if (!isphone(mphone)) {
                    Toast.makeText(this, "手机号不合格请重新输入", Toast.LENGTH_SHORT).show();
                    return;
                } else if (mpwd.equals("")) {
                    Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Map<String, Object> headmap = new HashMap<>();
                    Map<String, Object> map = new HashMap<>();
                    String mpwd1 = EncryptUtil.encrypt(mpwd);
                    map.put("phone", mphone);
                    map.put("pwd", mpwd1);
                    presenter.getlogin(Contacts.LOGIN, headmap, map, LogBean.class);
                }
                break;
            case R.id.weixin:
                break;
            case R.id.checkbox:

                break;
        }
    }

    /**
     * 验证手机是否正确
     *
     * @param
     * @return boolean
     */
    public static boolean isphone(String phone) {
        return phone_pattern.matcher(phone).matches();
    }

    //手机号表达式
    private final static Pattern phone_pattern = Pattern.compile("^(13|15|18)\\d{9}$");

    @Override
    public void success(Object data) {
        if (data instanceof LogBean) {
            LogBean logBean = (LogBean) data;
            if (logBean.getStatus().equals("1001")) {
                Toast.makeText(this, logBean.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                if (checkbox.isChecked()) {
                    String nName = phone.getText().toString().trim();
                    String nPass = pwd.getText().toString().trim();
                    SpUtil.put("name", nName);
                    SpUtil.put("pass", nPass);
                    SpUtil.put("jizhu", true);
                } else {
                    SpUtil.remove();
                }
                Toast.makeText(this, logBean.getMessage(), Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(LogActivity.this, ShouYe_Activity.class));
                //finish();
            }
        }
    }

    @Override
    public void error(String error) {
        Log.e("zzz", "error: " + error);
    }


}
