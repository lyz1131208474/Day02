package com.example.administrator.day2_demo.view;

import com.example.administrator.day2_demo.model.bean.GoodsBean;

import java.util.List;

/**
 * Created by Administrator on 2018/5/10.
 */

public interface IMainView {
    void ShowGoods(List<GoodsBean.DataBean> list);

    int pscid();
}
