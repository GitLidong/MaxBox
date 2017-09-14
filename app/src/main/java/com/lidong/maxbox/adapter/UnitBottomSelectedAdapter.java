package com.lidong.maxbox.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidong.maxbox.MyApplication;
import com.lidong.maxbox.R;
import com.lidong.maxbox.myinterface.UnitBottomClickCallback;

/**
 * Created by ubuntu on 17-9-12.
 */

public class UnitBottomSelectedAdapter extends RecyclerView.Adapter<UnitBottomSelectedAdapter.ViewHolder>{

    private UnitBottomClickCallback callback;

    private int [] selectResourceImage = {
            R.drawable.unitconverter_length, R.drawable.unitconverter_area,
            R.drawable.unitconverter_weight, R.drawable.unitconverter_volume,
            R.drawable.unitconverter_temperature, R.drawable.unitconverter_time,
            R.drawable.unitconverter_pressure, R.drawable.unitconverter_speed,
            R.drawable.unitconverter_data, R.drawable.unitconverter_radioactivity
    };

    private String [] selectResourceName =
            MyApplication.getContext().getResources().getStringArray(R.array.unit_name);

    public UnitBottomSelectedAdapter(UnitBottomClickCallback callback) {
        this.callback = callback;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public int position;

        private RelativeLayout cell_unit_select_back;
        private ImageView cell_unit_select_image;
        private TextView cell_unit_select_text;

        public ViewHolder(View itemView) {
            super(itemView);
            cell_unit_select_back = (RelativeLayout) itemView.findViewById(R.id.cell_unit_select_back);
            cell_unit_select_image = (ImageView) itemView.findViewById(R.id.cell_unit_select_image);
            cell_unit_select_text = (TextView) itemView.findViewById(R.id.cell_unit_select_text);

            cell_unit_select_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.changeView(position);
                }
            });

            cell_unit_select_back.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    callback.changeView(position);
                    return false;
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_unit_bottom_select,parent,false);
        final ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.position = position;
        holder.cell_unit_select_image.setBackgroundResource(selectResourceImage[position]);
        holder.cell_unit_select_text.setText(selectResourceName[position]);
    }


    @Override
    public int getItemCount() {
        return selectResourceImage.length;
    }
}
