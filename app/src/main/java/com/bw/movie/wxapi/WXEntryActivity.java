package com.bw.movie.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.bw.movie.activity.FilmActivity;
import com.bw.movie.bean.LBean;
import com.bw.movie.presenter.MyPresenterImpl;
import com.bw.movie.utils.Contacts;
import com.bw.movie.utils.ToastUtil;
import com.bw.movie.utils.WeiXinUtil;
import com.bw.movie.view.MyView;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * date:2019/1/27
 * author:李凯
 * function：微信Activity
 */
public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler, MyView {

    private static final String TAG = "WXEntryActivity";

    private MyPresenterImpl presenter;
    String code;
    private boolean mSuccess;
    private Map<String, Object> headmap;
    private HashMap<String, Object> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WeiXinUtil.reg(WXEntryActivity.this).handleIntent(getIntent(), this);
        //自动登录
    }
    @Override
    public void onReq(BaseReq baseReq) {
        Log.e(TAG,"1111111");
    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    @Override
    public void onResp(final BaseResp baseResp) {
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //得到code
                        code = ((SendAuth.Resp) baseResp).code;
                        headmap = new HashMap<>();
                        map = new HashMap<>();
                        map.put("code",code);
                        Log.d("fantasychongwxlogin", code.toString()+ "");
                        presenter = new MyPresenterImpl(WXEntryActivity.this);
                        presenter.getpost(Contacts.WX_LOGIN,headmap,map,LBean.class);
                    }
                });
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED://用户拒绝授权
                finish();
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                //取消
                break;
        }
    }
    @Override
    public void success(Object data) {
        if (data instanceof LBean){
            String message = ((LBean) data).getMessage();
            ToastUtil.Toast(message);
            Intent intent1 = new Intent(WXEntryActivity.this, FilmActivity.class);
            //封装code
            startActivity(intent1);
            if (mSuccess) {
                Intent intent = new Intent(WXEntryActivity.this, FilmActivity.class);
                //封装code
                startActivity(intent);
            }else {
                mSuccess=true;
                if (Contacts.WX_LOGIN.equals(message)) {
                    //SpUtil.put(Contacts.mIsLogin,true);
                    Intent intent = new Intent(WXEntryActivity.this, FilmActivity.class);
                    //封装code
                    startActivity(intent);
                }
            }
        }
    }
    @Override
    public void error(String msg) {
        Log.e("微信登录",msg);
        ToastUtil.Toast(msg);
    }
}
