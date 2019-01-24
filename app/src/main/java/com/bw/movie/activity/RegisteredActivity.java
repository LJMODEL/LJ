package com.bw.movie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.bw.movie.R;
import com.bw.movie.bean.RegisteredBean;
import com.bw.movie.presenter.MyPresenterImpl;
import com.bw.movie.utils.Contacts;
import com.bw.movie.utils.EncryptUtil;
import com.bw.movie.utils.ToastUtil;
import com.bw.movie.view.MyView;
import com.xw.repo.XEditText;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisteredActivity extends AppCompatActivity implements MyView {


    @BindView(R.id.name)
    XEditText name;
    @BindView(R.id.gender)
    XEditText gender;
    @BindView(R.id.date)
    XEditText date;
    @BindView(R.id.phone)
    XEditText phone;
    @BindView(R.id.emli)
    XEditText emli;
    @BindView(R.id.password)
    XEditText password;
    @BindView(R.id.zhuce)
    Button zhuce;
    private String s;
    private MyPresenterImpl presenter;
    private int sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);
        ButterKnife.bind(this);
        presenter = new MyPresenterImpl(this);
    }


    @OnClick(R.id.zhuce)
    public void onViewClicked() {
        String mname = name.getText().toString();
        String mgender = gender.getText().toString();
        String mdate = date.getText().toString();
        String mphone = phone.getText().toString();
        String memli = emli.getText().toString();
        String mpassword = password.getText().toString();

        if (mname.equals("") || mgender.equals("") || mdate.equals("") || mphone.equals("") || memli.equals("") || mpassword.equals("")) {
            ToastUtil.Toast("不能为空");
        } else if (!isphone(mphone)) {
            ToastUtil.Toast("请重新输入手机号");
        } else if (!isemli(memli)) {
            ToastUtil.Toast("邮箱不正确请重新输入邮箱");
        }
        if (mgender.equals("男")) {
            sex = 1;
        } else if (mgender.equals("女")) {
            sex = 2;
        }
        ToastUtil.Toast("注册");
        Map<String, Object> headmap = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        String mpwd1 = EncryptUtil.encrypt(mpassword);
        map.put("nickName", mname);
        map.put("phone", mphone);
        map.put("pwd", mpwd1);
        map.put("sex", sex);
        map.put("birthday", mdate);
        map.put("email", memli);
        map.put("pwd2",mpwd1);
        presenter.getregister(Contacts.REGISTER, headmap, map, RegisteredBean.class);
    }

    @Override
    public void success(Object data) {
        if (data instanceof RegisteredBean) {
            RegisteredBean registeredBean = (RegisteredBean) data;
            ToastUtil.Toast(registeredBean.getStatus());
            Intent intent = new Intent(RegisteredActivity.this,LogActivity.class);
            startActivity(intent);
        }
    }
    @Override
    public void error(String error) {
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

    /**
     * 验证邮箱是否正确
     *
     * @param
     * @return boolean
     */
    public static boolean isemli(String phone) {
        return email_pattern.matcher(phone).matches();
    }

    //手机号表达式
    private final static Pattern phone_pattern = Pattern.compile("^(13|15|18)\\d{9}$");
    //邮箱表达式
    private final static Pattern email_pattern = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");

}