package com.example.administrator.day2_demo.model;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.day2_demo.R;
import com.example.administrator.day2_demo.model.bean.GoodsBean;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

/**
 * Created by Administrator on 2018/5/10.
 */

public class MyAdapter extends XRecyclerView.Adapter<XRecyclerView.ViewHolder> {
    private static final int TYPE0 = 0;
    private static final int TYPE1 = 1;
    private Context context;
    private List<GoodsBean.DataBean> list;

    public MyAdapter(Context context, List<GoodsBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE0) {
            View view = View.inflate(context, R.layout.item, null);
            MyViewHolder myHolder = new MyViewHolder(view);
            return myHolder;
        } else if (viewType == TYPE1) {
            View view = View.inflate(context, R.layout.item02, null);
            MyPicHolder picHolder = new MyPicHolder(view);

            return picHolder;

        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            String images = list.get(position).getImages();
            String img = images.split("\\|")[0];
            Uri uri = Uri.parse(img);
            myViewHolder.img.setImageURI(uri);
            myViewHolder.price01.setText(list.get(position).getTitle());
            myViewHolder.price02.setText("￥：" + list.get(position).getBargainPrice());
        } else if (holder instanceof MyPicHolder) {

            MyPicHolder picHolder = (MyPicHolder) holder;
            String images = list.get(position).getImages();
            String img = images.split("\\|")[0];
            Uri uri = Uri.parse(img);
            picHolder.item02_img.setImageURI(uri);
            picHolder.item02_price01.setText(list.get(position).getTitle());
            picHolder.item02_price02.setText("￥：" + list.get(position).getBargainPrice());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    @Override
    public int getItemViewType(int position) {
        if(position%2==0){
            return TYPE0;
        }else{
            return TYPE1;
        }
    }

    public class MyViewHolder extends XRecyclerView.ViewHolder {

        private final SimpleDraweeView img;
        private final TextView price01;
        private final TextView price02;

        public MyViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            price01 = itemView.findViewById(R.id.price01);
            price02 = itemView.findViewById(R.id.price02);
        }

        public SimpleDraweeView getImg() {
            return img;
        }

        public TextView getPrice01() {
            return price01;
        }

        public TextView getPrice02() {
            return price02;
        }
    }

    public class MyPicHolder extends XRecyclerView.ViewHolder {


        private final SimpleDraweeView item02_img;
        private final TextView item02_price01;
        private final TextView item02_price02;

        public MyPicHolder(View itemView) {
            super(itemView);
            item02_img = itemView.findViewById(R.id.item02_img);
            item02_price01 = itemView.findViewById(R.id.item02_price01);
            item02_price02 = itemView.findViewById(R.id.item02_price02);
        }

        public SimpleDraweeView getItem02_img() {
            return item02_img;
        }

        public TextView getItem02_price01() {
            return item02_price01;
        }

        public TextView getItem02_price02() {
            return item02_price02;
        }
    }
}
