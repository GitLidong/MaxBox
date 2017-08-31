package com.lidong.maxbox.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lidong.maxbox.R;
import com.lidong.maxbox.myinterface.LedCallback;

import java.util.List;

/**
 * Created by ubuntu on 17-8-23.
 */

public class MyLedAdapter extends RecyclerView.Adapter<MyLedAdapter.ViewHolder>{

    private List<Integer> listBitmaps;
    private LedCallback ledCallback;
    private int lastPosition = -1;

    public MyLedAdapter(List<Integer> listBitmaps,LedCallback ledCallback){
        this.listBitmaps = listBitmaps;
        this.ledCallback = ledCallback;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView ledImage;

        public ViewHolder(View itemView) {
            super(itemView);
            ledImage= (ImageView) itemView.findViewById(R.id.cell_led_image);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_led,parent,false);
        final ViewHolder holder =new ViewHolder(view);

        holder.ledImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if(lastPosition != position){
                    ledCallback.setPostion(position);
                    ledCallback.setView(position);
                    lastPosition = position;
                    Log.i("lidong","lastPosition:  "+lastPosition);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int bitmap = listBitmaps.get(position);
        holder.ledImage.setImageResource(bitmap);
    }

    @Override
    public int getItemCount() {
        return listBitmaps.size();
    }
}
