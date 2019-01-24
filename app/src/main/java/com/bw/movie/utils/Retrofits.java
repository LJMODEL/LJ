package com.bw.movie.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * <p>文件描述：<p>
 * <p>作者：${adai}<p>
 * <p>创建时间：2019/1/23 9:00<p>
 * <p>更改时间：2019/1/23 9:00<p>
 * <p>版本号：1<p>
 */
public class Retrofits {
    private MyApiService myApiService;
    public Retrofits() {
            HttpLoggingInterceptor loggingInterceptor =new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient okHttpClient =new OkHttpClient.Builder()
                    .readTimeout(20,TimeUnit.SECONDS)
                    .connectTimeout(20,TimeUnit.SECONDS)
                    .writeTimeout(20,TimeUnit.SECONDS)
                    .addInterceptor(loggingInterceptor)
                    .retryOnConnectionFailure(true)
                    .build();
            Retrofit retrofit =new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
    //                存放的头文件
                    .baseUrl(Contacts.BASE_URL)
                    .client(okHttpClient)
                    .build();
            myApiService =retrofit.create(MyApiService.class);
        }

        public static  Retrofits getInstance(){
            return RetroHolder.OK_UTIL;
        }

        static class RetroHolder{
            private static final Retrofits OK_UTIL =new Retrofits();
        }

        //封装Get方式  这里面采用构造者模式  就是调用这个方法有返回自己本身这个对象
        public Retrofits get(String url,Map<String,Object> headmap,Map<String,Object> map) {
            //这个订阅事件（处理网络请求）放生那个线程
            //Schedulers 线程调度器
            myApiService.get(url,headmap,map).subscribeOn(Schedulers.io())//io就是子线程
                    //在主线程调用
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
            return Retrofits.getInstance();
        }

//        封装一个post  的请求方式
    public Retrofits post(String url,Map<String,Object> headmap,Map<String,Object> map){

        myApiService.post(url,headmap,map).subscribeOn(Schedulers.io())//io就是子线程
                //在主线程调用
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        return Retrofits.getInstance();
    }

//    封装一个 上传头像
    public Retrofits image(String url, Map<String,Object> headmap,Map<String,Object> map,List<Object> list){
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (list.size()==1) {
            for (int i = 0; i < list.size(); i++) {
                File file = new File((String) list.get(i));
                builder.addFormDataPart("image", file.getName(),RequestBody.create(MediaType.parse("multipart/octet-stream"),file));
            }
        }
        myApiService.img(url,headmap,builder.build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        return Retrofits.getInstance();
    }

        private Observer<ResponseBody> observer =new Observer<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if(onclick!=null){
                    onclick.error(e.getMessage());
                }
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                if(onclick!=null){
                    try {
                        onclick.success(responseBody.string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };


    public interface onclick{
        void success(String strjson);
        void error(String error);
    }
    public onclick onclick;
    public void getonclcked(onclick onclick){
        this.onclick =onclick;
    }
}