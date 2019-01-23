package com.bw.movie.utils;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.bw.movie.app.MyApp;

public class ToastUtil {

    public static Toast mToast;

    public static void Toast(String msg) {
        mToast=Toast.makeText(MyApp.context, msg, Toast.LENGTH_SHORT);
        mToast.show();
    }
    /**
     *toast取消
     */
    public static void cancel(){
        if(mToast != null){
            mToast.cancel();
            mToast = null;
        }

    }


}
