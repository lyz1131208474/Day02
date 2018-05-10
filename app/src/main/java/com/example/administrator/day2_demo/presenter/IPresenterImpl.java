package com.example.administrator.day2_demo.presenter;

import com.example.administrator.day2_demo.http.GoodsListener;
import com.example.administrator.day2_demo.http.Httpfig;
import com.example.administrator.day2_demo.model.IModel;
import com.example.administrator.day2_demo.model.bean.GoodsBean;
import com.example.administrator.day2_demo.view.IMainView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/10.
 */

public class IPresenterImpl implements IPresenter {
    @Override
    public void getGoods(IModel iModel, final IMainView iMainView) {
        Map<String,String> map=new HashMap<>();
        map.put("pscid",iMainView.pscid()+"");
        iModel.getDate(Httpfig.goods_url, map, new GoodsListener() {
            @Override
            public void GoodsSuccess(String json) {
                Gson gson=new Gson();
                GoodsBean goodsBean = gson.fromJson(json, GoodsBean.class);
                List<GoodsBean.DataBean> data = goodsBean.getData();
                iMainView.ShowGoods(data);
            }

            @Override
            public void GoodsError(String error) {

            }
        });
    }
}
