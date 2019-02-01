package com.bw.movie.utils;

import android.content.Context;

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * 作者: 程龙
 * 时间: 2019/1/26 19:45
 * 邮箱: m18210100439@163.com
 * 作用: 微信
 */
public class WeChatUtil {
    private static String APPID="wxb3852e6a6b7d9516";

    public static IWXAPI wechat(Context context){
        IWXAPI api = WXAPIFactory.createWXAPI(context, APPID, true);
        api.registerApp(APPID);
        return api;
    }
}
