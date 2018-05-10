package com.example.administrator.day2_demo.model;

import com.example.administrator.day2_demo.http.GoodsListener;
import com.example.administrator.day2_demo.http.OKHttpUtils;

import java.util.Map;

/**
 * Created by Administrator on 2018/5/10.
 */

public class IModelImpl implements IModel {
    @Override
    public void getDate(String url, Map<String, String> map, final GoodsListener goodsListener) {
        OKHttpUtils okHttpUtils=new OKHttpUtils();
        okHttpUtils.okPost(url,map);
        okHttpUtils.setOnLoginListener(new OKHttpUtils.LoginListener() {
            @Override
            public void loginSuccess(String json) {
                goodsListener.GoodsSuccess(json);
            }

            @Override
            public void loginError(String error) {
                goodsListener.GoodsError(error);
            }
        });
    }
}
