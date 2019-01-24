package com.bw.movie.activity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.bw.movie.view.MyView;
import com.xw.repo.XEditText;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

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
    //退出
    private long exitTime = 0;

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

    /**
     * 返回两次退出
     */
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

    /**
     * 点击软键盘外部键盘收缩
     */
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
