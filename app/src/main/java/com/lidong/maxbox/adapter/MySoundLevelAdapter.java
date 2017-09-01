package com.lidong.maxbox.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lidong.maxbox.R;

/**
 * Created by ubuntu on 17-9-1.
 */

public class MySoundLevelAdapter extends RecyclerView.Adapter<MySoundLevelAdapter.ViewHolder>{

    private String TAG = "MySoundLevelAdapter";

    private String [] soundLevel;
    private String [] soundDetail;

    public MySoundLevelAdapter(Context context) {
        soundLevel = context.getResources().getStringArray(R.array.decibelstr);
        soundDetail = context.getResources().getStringArray(R.array.decibelInfo);
        Log.i(TAG,"MySoundLevelAdapter construct: array length "+soundDetail.length);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView cell_sound_level;
        private TextView cell_sound_info;

        public ViewHolder(View itemView) {
            super(itemView);
            cell_sound_level = (TextView) itemView.findViewById(R.id.cell_sound_level);
            cell_sound_info = (TextView) itemView.findViewById(R.id.cell_sound_info);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_sound_level,parent,false);
        final ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.cell_sound_level.setText(soundLevel[position]);
        holder.cell_sound_info.setText(soundDetail[position]);
    }

    @Override
    public int getItemCount() {
        return soundLevel != null ? soundLevel.length:0;
    }


}
