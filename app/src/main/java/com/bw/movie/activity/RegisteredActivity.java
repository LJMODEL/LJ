package com.bw.movie.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.bw.movie.R;
import com.bw.movie.bean.RegisteredBean;
import com.bw.movie.presenter.MyPresenterImpl;
import com.bw.movie.utils.Contacts;
import com.bw.movie.utils.EncryptUtil;
import com.bw.movie.utils.ToastUtil;
import com.bw.movie.view.MyView;
import com.xw.repo.XEditText;

import java.text.SimpleDateFormat;
import java.util.Date;
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
    XEditText datea;
    @BindView(R.id.phone)
    XEditText phone;
    @BindView(R.id.emli)
    XEditText emli;
    @BindView(R.id.password)
    XEditText password;
    @BindView(R.id.zhuce)
    Button zhuce;
    private MyPresenterImpl presenter;
    private int sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);
        ButterKnife.bind(this);
        presenter = new MyPresenterImpl(this);
    }

    @OnClick({R.id.date, R.id.zhuce})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.date:
                getData();
                break;
            case R.id.zhuce:
                String mname = name.getText().toString();
                String mgender = gender.getText().toString();
                String mdate = datea.getText().toString();
                String mphone = phone.getText().toString();
                String memli = emli.getText().toString();
                String mpassword = password.getText().toString();
                //每次判断return，只要不合格就不能继续
                if (mname.equals("") || mgender.equals("") || mdate.equals("") || mphone.equals("") || memli.equals("") || mpassword.equals("")) {
                    ToastUtil.Toast("不能为空");
                    return;
                } else if (!isphone(mphone)) {
                    ToastUtil.Toast("请重新输入手机号");
                    return;
                } else if (!isemli(memli)) {
                    ToastUtil.Toast("邮箱不正确请重新输入邮箱");
                    return;
                }
                //在这里判断男女然后返回一二
                if (mgender.equals("男")) {
                    sex = 1;
                } else if (mgender.equals("女")) {
                    sex = 2;
                }
                Map<String, Object> headmap = new HashMap<>();
                Map<String, Object> map = new HashMap<>();
                String mpwd1 = EncryptUtil.encrypt(mpassword);
                map.put("nickName", mname);
                map.put("phone", mphone);
                map.put("pwd", mpwd1);
                map.put("sex", sex);
                map.put("birthday", mdate);
                map.put("email", memli);
                map.put("pwd2", mpwd1);
                presenter.getpost(Contacts.REGISTER, headmap, map, RegisteredBean.class);
                break;
        }
    }

    /**
     * 第三方出生日期
     */
    private void getData() {
        TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                datea.setText(getTime(date));
            }
        }).setType(new boolean[]{true, true, true, false, false, false})
                .isDialog(true)
                .build();

        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
            }
        }
        pvTime.show();
    }

    /**
     * 第三方出生日期
     */
    private String getTime(Date date) {
        //可根据需要自行截取数据显示
        //impleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);

    }

    @Override
    public void success(Object data) {
        if (data instanceof RegisteredBean) {
            RegisteredBean registeredBean = (RegisteredBean) data;
            ToastUtil.Toast(registeredBean.getMessage());
            Intent intent = new Intent(RegisteredActivity.this, LogActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void error(String error) {
        ToastUtil.Toast("注册页面出问题");
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