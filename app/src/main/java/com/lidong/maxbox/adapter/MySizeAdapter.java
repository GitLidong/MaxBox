package com.lidong.maxbox.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lidong.maxbox.R;

/**
 * Created by ubuntu on 17-9-6.
 */

public class MySizeAdapter extends RecyclerView.Adapter<MySizeAdapter.ViewHolder> {

    public int which;
    private View view;
    private ViewHolder vh;
    public String[][] datas = null;
    public MySizeAdapter(int which, String[][] datas) {
        this.which = which;
        this.datas = datas;
    }
    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (which == 0) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.five,
                    viewGroup,false);
        } else if(which == 1) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.six,
                    viewGroup,false);
        } else if (which == 2) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.two,
                    viewGroup,false);
        }
        vh = new ViewHolder(view,which);
        return vh;
    }
    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        if (which == 0) {
                    viewHolder.textView1.setText(datas[position][0]);
                    viewHolder.textView2.setText(datas[position][1]);
                    viewHolder.textView3.setText(datas[position][2]);
                    viewHolder.textView4.setText(datas[position][3]);
                    viewHolder.textView5.setText(datas[position][4]);
        } else if (which == 1){
            viewHolder.textView1.setText(datas[position][0]);
            viewHolder.textView2.setText(datas[position][1]);
            viewHolder.textView3.setText(datas[position][2]);
            viewHolder.textView4.setText(datas[position][3]);
            viewHolder.textView5.setText(datas[position][4]);
            viewHolder.textView6.setText(datas[position][5]);
        } else if (which == 2) {
            viewHolder.textView1.setText(datas[position][0]);
            viewHolder.textView2.setText(datas[position][1]);
        }
    }
    //获取数据的数量
    @Override
    public int getItemCount() {
        return datas.length;
    }
    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public int which;
        public TextView textView1;
        public TextView textView2;
        public TextView textView3;
        public TextView textView4;
        public TextView textView5;
        public TextView textView6;
        public ViewHolder(View view,int which){
            super(view);
            if (which == 0) {
                textView1 = (TextView) view.findViewById(R.id.tex1_);
                textView2 = (TextView) view.findViewById(R.id.tex2_);
                textView3 = (TextView) view.findViewById(R.id.tex3_);
                textView4 = (TextView) view.findViewById(R.id.tex4_);
                textView5 = (TextView) view.findViewById(R.id.tex5_);
            } else if (which == 1) {
                textView1 = (TextView) view.findViewById(R.id.tex1);
                textView2 = (TextView) view.findViewById(R.id.tex2);
                textView3 = (TextView) view.findViewById(R.id.tex3);
                textView4 = (TextView) view.findViewById(R.id.tex4);
                textView5 = (TextView) view.findViewById(R.id.tex5);
                textView6 = (TextView) view.findViewById(R.id.tex6);
            } else if (which == 2) {
                textView1 = (TextView) view.findViewById(R.id.tex1_j);
                textView2 = (TextView) view.findViewById(R.id.tex2_j);
            }
        }
    }

}
