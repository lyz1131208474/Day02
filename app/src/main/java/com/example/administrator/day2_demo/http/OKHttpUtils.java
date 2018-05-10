package com.example.administrator.day2_demo.http;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 单利模式
 * 就是一个应用里面只有一个对象
 * 好处：节约内存。
 * 1.饿汉式
 * 开始对象就创建出来了
 * 2.懒汉式
 * 开始对象是null，什么时候使用，就是什么时候创建。
 */

public class OKHttpUtils {
    private static final String TAG = "OKHttpUtils----";

    private static OKHttpUtils okHttpUtils = null;

    private MyHandler myHandler = new MyHandler();
    private LoginListener onLoginListener;

    public static OKHttpUtils getInstance() {
        if (okHttpUtils == null) {
            okHttpUtils = new OKHttpUtils();
        }
        return okHttpUtils;
    }

    //get
    public void okGet(String url, String mobile, String password) {
        String url1 = url + "?mobile=" + mobile + "&password=" + password;
        //创建OKHttpClient
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new MyIntercepter()).build();
        //用request 请求，将url，和参数惊醒封装。
        Request request = new Request.Builder().url(url1).build();
        //call是请求队列，ok里面，请求队队列默认是一个。
        Call call = client.newCall(request);
        //一部请求，成功和失败的方法都是在线程，不能进行UI跟新。
        //handler   synctask
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure() returned: " + "失败--");
                Message message = myHandler.obtainMessage();
                message.what = 0;
                message.obj = e.getMessage();
                myHandler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                java.lang.IllegalStateException: closed
//                Log.d(TAG, "onResponse() returned: " + response.body().string());
                Message message = myHandler.obtainMessage();
                message.what = 1;
                message.obj = response.body().string();
                myHandler.sendMessage(message);
            }
        });
    }

    //post
    public void okPost(String url, Map<String, String> params) {
        //创建OKHttpClient
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new MyIntercepter()).build();
        FormBody.Builder builder = new FormBody.Builder();
        //遍历map集合，将参数放入body即可    遍历的核心思想就是。根据键取值
        Set<String> keys = params.keySet();
        for (String key : keys) {
            String value = params.get(key);
            //放入body
            builder.add(key, value);
        }

        FormBody body = builder.build();
        //用request 请求，将url，和参数惊醒封装。
        Request request = new Request.Builder().url(url).post(body).build();
        //call是请求队列，ok里面，请求队队列默认是一个。
        Call call = client.newCall(request);
        //一部请求，成功和失败的方法都是在线程，不能进行UI跟新。
        //handler   synctask
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure() returned: " + "失败--");
                Message message = myHandler.obtainMessage();
                message.what = 0;
                message.obj = e.getMessage();
                myHandler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                Log.d(TAG, "onResponse() returned: " + response.body().string());
                Message message = myHandler.obtainMessage();
                message.what = 1;
                message.obj = response.body().string();
                myHandler.sendMessage(message);
            }
        });
    }
    //上传

    //将输入发送到主线程，处理线程问题
    //接口回调
    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            int w = msg.what;
            switch (w) {
                case 0:
                    //失败
                    String error = (String) msg.obj;
                    //传出去
                    onLoginListener.loginError(error);
                    break;

                case 1:
                    //成功
                    String json = (String) msg.obj;
                    onLoginListener.loginSuccess(json);
                    break;
            }
        }
    }

    //
    public interface LoginListener {
        //成功
        void loginSuccess(String json);

        //失败
        void loginError(String error);
    }

    //提供一个外部访问的方法
    public void setOnLoginListener(LoginListener onLoginListener) {
        this.onLoginListener = onLoginListener;
    }

    //拦截器。实现公参数的拼接
    class MyIntercepter implements Interceptor {
        //拦截的方法。
//        chain链条，获取请求，获取相应
        @Override
        public Response intercept(Chain chain) throws IOException {
            //获取请求
            Request request = chain.request();
            RequestBody body = request.body();
            //判断如果是formbody
            if (body instanceof FormBody) {
                //创建新的body，将原来的参数，放入新的body里面，然后再讲公共参数，添加到新的body里面
                FormBody.Builder builder = new FormBody.Builder();
                //遍历旧的body
                for (int i = 0; i < ((FormBody) body).size(); i++) {
                    String key = ((FormBody) body).name(i);
                    String value = ((FormBody) body).value(i);
                    //添加
                    builder.add(key, value);
                }
                builder.add("source", "android");
                builder.add("token", "appVersion");
                FormBody newBody = builder.build();
                //创建新的请求
                Request request1 = request.newBuilder().post(newBody).build();
                //让request1去重新请求,proceed所有http请求的核心
                Response response = chain.proceed(request1);
                return response;
            }
            return null;
        }
    }

    //上传头像
    public void upLoadImage(String upload_url, File file) {
        //创建OKHttpClient
        OkHttpClient okHttpClient = new OkHttpClient();
        //创建MultiPartBody
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        //设置参数
        MediaType mediaType = MediaType.parse("image/png");
        builder.addFormDataPart("file", file.getName(), RequestBody.create(mediaType, file));
        //添加其他参数
        builder.addFormDataPart("uid", "71");

        MultipartBody body = builder.build();
        //创建请求队列
        Request request = new Request.Builder().url(upload_url).post(body).build();
        Call newCall = okHttpClient.newCall(request);
        newCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure() returned: " + "shibai---" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "onResponse() returned: " + response.body().string());
            }
        });
    }

}
