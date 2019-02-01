package com.bw.movie.activity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
import com.bw.movie.utils.ToastUtil;
import com.bw.movie.utils.WeChatUtil;
import com.bw.movie.utils.WeiXinUtil;
import com.bw.movie.view.MyView;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.xw.repo.XEditText;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

public class LogActivity extends AppCompatActivity implements MyView, View.OnClickListener {

    private XEditText phone, pwd;
    private TextView zhuce;
    private Button login;
    private ImageView weixin, hidden;
    private CheckBox checkbox;
    private MyPresenterImpl presenter;
    private boolean isHideFirst = true;
    //退出
    private long exitTime = 0;
    private SharedPreferences sp;
    private SharedPreferences.Editor edit;
    private String mpwd1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        inView();
        pwd.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                // et.getCompoundDrawables()得到一个长度为4的数组，分别表示左右上下四张图片
                Drawable drawable = pwd.getCompoundDrawables()[2];
                //如果右边没有图片,不处理
                if (drawable == null) {
                    return false;
                }
                //如果不是按下事件,不处理
                if (motionEvent.getAction() != MotionEvent.ACTION_UP) {
                    return false;
                }
                if (motionEvent.getX() > pwd.getWidth()
                        - pwd.getPaddingRight()
                        - drawable.getIntrinsicWidth()) {
                    if (isHideFirst == true) {
                        //editText可见
                        pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        isHideFirst = false;
                    } else {
                        //editText不可见
                        pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        isHideFirst = true;
                    }
                }
                return false;
            }
        });
    }

    private void inView() {
        sp = getSharedPreferences("login", MODE_PRIVATE);
        presenter = new MyPresenterImpl(this);
        phone = findViewById(R.id.phone);
        pwd = findViewById(R.id.pwd);
        zhuce = findViewById(R.id.zhuce);
        login = findViewById(R.id.login);
        weixin = findViewById(R.id.weixin);
        weixin.setOnClickListener(this);
        checkbox = findViewById(R.id.checkbox);
        hidden = findViewById(R.id.hidden);
        weixin.setOnClickListener(this);
        login.setOnClickListener(this);
        zhuce.setOnClickListener(this);
        checkbox.setOnClickListener(this);
        if (sp.getString("name", "") != null) {
            String mName = sp.getString("name", "");
            String mPass = sp.getString("pass", "");
            boolean ji = sp.getBoolean("jizhu", false);
            phone.setText(mName);
            pwd.setText(mPass);
            checkbox.setChecked(ji);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.zhuce:
                Intent intent = new Intent(LogActivity.this, RegisteredActivity.class);
                //跳转动画
                //startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(LogActivity.this,zhuce,"shareNames").toBundle());
                startActivity(intent);
                break;
            case R.id.login:
                //获得密码
                String mphone = phone.getText().toString().trim();
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
                    mpwd1 = EncryptUtil.encrypt(mpwd);
                    map.put("phone", mphone);
                    map.put("pwd", mpwd1);
                    presenter.getpost(Contacts.LOGIN, headmap, map, LogBean.class);
                }
                break;
            //微信第三方登录
            case R.id.weixin:
                if (!WeChatUtil.wechat(this).isWXAppInstalled()) {
                    ToastUtil.Toast("请先安装应用");
                } else {
                    //  验证
                    SendAuth.Req req = new SendAuth.Req();
                    req.scope = "snsapi_userinfo";
                    req.state = "wechat_sdk_demo_test";
                    WeiXinUtil.reg(LogActivity.this).sendReq(req);
                }
                break;
        }
    }

    @Override
    public void success(Object data) {
        if (data instanceof LogBean) {
            LogBean logBean = (LogBean) data;
            LogBean.ResultBean result = logBean.getResult();
            if (logBean.getStatus().equals("1001")) {
                Toast.makeText(this, logBean.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                if (checkbox.isChecked()) {
                    String nName = phone.getText().toString().trim();
                    String nPass = pwd.getText().toString().trim();
                    edit = sp.edit();
                    String sessionId = result.getSessionId();
                    int userId = result.getUserId();
                    edit.putString("sessionId", sessionId);
                    edit.putInt("userId", userId);
                    Log.e("aaaaa", sessionId+   "            " +userId);
                    edit.putString("name", nName);
                    edit.putString("pass", nPass);
                    edit.putString("password",mpwd1);
                    edit.putBoolean("jizhu", true);
                    edit.commit();
                } else {
                    SharedPreferences.Editor edit = sp.edit();
                    edit.clear();
                    edit.commit();
                }
                Toast.makeText(this, logBean.getMessage(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LogActivity.this, FilmActivity.class));
                finish();
            }
        }
    }

    //异常
    @Override
    public void error(String error) {

    }

    public static boolean isphone(String phone) {
        return phone_pattern.matcher(phone).matches();
    }

    //手机号表达式
    private final static Pattern phone_pattern = Pattern.compile("^(13|15|18)\\d{9}$");


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            //当isShouldHideInput(v, ev)为true时，表示的是点击输入框区域，则需要显示键盘，同时显示光标，反之，需要隐藏键盘、光标
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    //处理Editext的光标隐藏、显示逻辑
                    phone.clearFocus();
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
}
