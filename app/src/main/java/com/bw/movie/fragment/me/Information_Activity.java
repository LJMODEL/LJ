package com.bw.movie.fragment.me;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.bw.movie.activity.LogActivity;
import com.bw.movie.bean.Message_Bean;
import com.bw.movie.bean.Modify_Bean;
import com.bw.movie.presenter.MyPresenterImpl;
import com.bw.movie.utils.Contacts;
import com.bw.movie.utils.EncryptUtil;
import com.bw.movie.utils.ImageUtil;
import com.bw.movie.utils.ToastUtil;
import com.bw.movie.view.MyView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Information_Activity extends AppCompatActivity implements View.OnClickListener, MyView {

    private ImageView imageView, touxiang, chongzhi;
    private TextView name, sex, text5, phone;
    private Message_Bean.ResultBean infoList = new Message_Bean.ResultBean();
    private SharedPreferences sp;
    private String sessionId;
    private int userId;
    private MyPresenterImpl myPresenter;
    private View view;
    private Button camera, cancel, photo, photoJanqie;
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_);
        inView();
    }

    private void inView() {
        myPresenter = new MyPresenterImpl(this);
        sp = getSharedPreferences("login", MODE_PRIVATE);
        infoList = (Message_Bean.ResultBean) getIntent().getSerializableExtra("info");
        imageView = findViewById(R.id.img);
        touxiang = findViewById(R.id.touxiang);
        name = findViewById(R.id.name);
        sex = findViewById(R.id.sex);
        text5 = findViewById(R.id.text5);
        phone = findViewById(R.id.phone);
        chongzhi = findViewById(R.id.chongzhi);
        chongzhi.setOnClickListener(this);
        imageView.setOnClickListener(this);
        touxiang.setOnClickListener(this);
        Glide.with(this).load(infoList.getHeadPic()).into(touxiang);
        name.setText(infoList.getNickName());
        if (infoList.getSex() == 1) {
            sex.setText("男");
        } else if (infoList.getSex() == 2) {
            sex.setText("女");
        }
        long birthday = infoList.getBirthday();
        String s = getloToDate(birthday);
        text5.setText(s);
        phone.setText(infoList.getPhone() + "");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.touxiang:
                view = LayoutInflater.from(this).inflate(R.layout.pop, null);
                popupWindow = new PopupWindow(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setContentView(view);
                popupWindow.setBackgroundDrawable(new ColorDrawable());
                popupWindow.setOutsideTouchable(true);
                popupWindow.setClippingEnabled(true);
                popupWindow.setTouchable(true);
                popupWindow.setFocusable(true);
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        popupWindow.dismiss();
                    }
                });
                camera = (Button) view.findViewById(R.id.camera);
                cancel = (Button) view.findViewById(R.id.cancel);
                photo = (Button) view.findViewById(R.id.photo);
                photoJanqie = (Button) view.findViewById(R.id.photoJanqie);
                camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 【1】相机的 隐式回传意图
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        // 【2】添加意图
                        intent.addCategory("android.intent.category.DEFAULT");
                        // 【3】回传（注意：请求码要和判断的一样）
                        startActivityForResult(intent, 0);
                    }
                });
                //点击从相册获取照片
                photo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // [1]设置相册的意图（权限）
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        // [2]设置显式MIME数据类型
                        intent.setType("image/*");
                        // [3]跳转回传
                        startActivityForResult(intent, 1);

                    }
                });
                photoJanqie.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // （1）设置相册的意图（权限）
                        Intent intent2 = new Intent(Intent.ACTION_PICK);
                        // （2）设置显式MIME数据类型
                        intent2.setType("image/*");
                        //（3）跳转回传
                        startActivityForResult(intent2, 2);
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                popupWindow.showAsDropDown(v, 0, 0);

                break;
            case R.id.img:
                finish();
                break;
            case R.id.chongzhi:
                showAlertDialog();
                break;
        }
    }

    /**
     * 相机成功的回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                Bitmap bitmap = data.getParcelableExtra("data");
                //FrescoUtils.setTo(headPath, MyBckground);
                popupWindow.dismiss();
                break;
            //调用相册的回调
            case 1:
                if (resultCode == Activity.RESULT_OK) {
                    Map<String, Object> headmap = new HashMap<>();
                    Map<String, Object> map = new HashMap<>();
                    sessionId = sp.getString("sessionId", "");
                    userId = sp.getInt("userId", 0);
                    headmap.put("userId", userId);
                    headmap.put("sessionId", sessionId);
                    Uri uri = data.getData();
                    if (uri != null) {
                        String path = ImageUtil.getPath(this, uri);
                        if (path != null) {
                            //转换为file类型
                            File file = new File(path);
                            map.put("image", file);
                            //调用P层
                        }
                    }
                }
                break;
        }
    }

    /**
     * 转换时间的
     *
     * @param lo
     * @return
     */
    public static String getloToDate(long lo) {
        Date date = new Date(lo);
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        return sd.format(date);
    }

    /**
     * 修改密码
     */
    private void showAlertDialog() {
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
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
                    String password = sp.getString("password", "");
                    String mpwd1 = EncryptUtil.encrypt(str);

                    Map<String, Object> headmap = new HashMap<>();
                    Map<String, Object> map = new HashMap<>();
                    sessionId = sp.getString("sessionId", "");
                    userId = sp.getInt("userId", 0);
                    headmap.put("userId", userId);
                    headmap.put("sessionId", sessionId);
                    map.put("oldPwd", password);
                    map.put("newPwd", mpwd1);
                    map.put("newPwd2", mpwd1);
                    dialog.dismiss();
                    myPresenter.getpost(Contacts.XIUGAI_MIMA, headmap, map, Modify_Bean.class);

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

    @Override
    public void success(Object data) {
        if (data instanceof Modify_Bean) {
            ToastUtil.Toast("修改成功");

            Intent i = new Intent(this, LogActivity.class);
            startActivity(i);
        }
    }


    @Override
    public void error(String error) {

    }
}
