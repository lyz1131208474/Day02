package com.example.administrator.day2_demo.view;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.administrator.day2_demo.R;
import com.example.administrator.day2_demo.model.IModelImpl;
import com.example.administrator.day2_demo.model.MyAdapter;
import com.example.administrator.day2_demo.model.bean.GoodsBean;
import com.example.administrator.day2_demo.presenter.IPresenterImpl;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements IMainView{
    private XRecyclerView xr;
    private static final String TAG = "MainActivity";
    private MyAdapter adapter;
    private int pscid = 1;
    private Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        xr = findViewById(R.id.recy);

        xr.setLayoutManager(new LinearLayoutManager(this));
        IPresenterImpl presenter = new IPresenterImpl();
        presenter.getGoods(new IModelImpl(),this);
        xr.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                MainActivity.this.pscid++;
                IPresenterImpl presenter = new IPresenterImpl();
                presenter.getGoods(new IModelImpl(),MainActivity.this);
                xr.refreshComplete();
            }

            @Override
            public void onLoadMore() {

                MainActivity.this.pscid++;
                IPresenterImpl presenter = new IPresenterImpl();
                presenter.getGoods(new IModelImpl(),MainActivity.this);

            }
        });

    }

    @Override
    public void ShowGoods(List<GoodsBean.DataBean> list) {
        adapter = new MyAdapter(MainActivity.this,list);
        xr.setAdapter(adapter);
        Log.d(TAG, "ShowGoods: "+list.size());
    }

    @Override
    public int pscid() {
        return MainActivity.this.pscid;
    }
}
