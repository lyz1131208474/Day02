package com.example.administrator.day2_demo.model;

import com.example.administrator.day2_demo.http.GoodsListener;

import java.util.Map;

/**
 * Created by Administrator on 2018/5/10.
 */

public interface IModel {
    void getDate(String url, Map<String,String> map, GoodsListener goodsListener);
}
