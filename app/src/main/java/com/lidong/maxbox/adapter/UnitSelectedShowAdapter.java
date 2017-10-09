package com.lidong.maxbox.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lidong.maxbox.MyApplication;
import com.lidong.maxbox.R;


/**
 * Created by ubuntu on 17-9-12.
 */

public class UnitSelectedShowAdapter extends RecyclerView.Adapter<UnitSelectedShowAdapter.ViewHolder> {


    private String [] showUnit ;
    private String [] showData;

    private void UpdateshowData(int spinnerUnit, float inputNum){
        Float [] dData = new Float[showData.length];
        float scale = (float) (inputNum/Float.parseFloat(showData[spinnerUnit]));
        for (int i = 0; i < showData.length; i++) {
            dData[i] = (float)(scale * Float.parseFloat(showData[i]));
            showData[i] = Float.toString(dData[i]);
            Log.d("gongwei", "UnitSelectedShowAdapter:"+Integer.toString(i)+"; float后数据:"+Float.toString(dData[i]));
        }
    }

    public UnitSelectedShowAdapter (int which, int spinnerUnit, float inputNum) {

        Context context = MyApplication.getContext();
        switch (which ) {
            case 0:
                showUnit = context.getResources().getStringArray(R.array.unit_length_list);
                showData = context.getResources().getStringArray(R.array.unit_length_value_list);
                break;
            case 1:
                showUnit = context.getResources().getStringArray(R.array.unit_area_list);
                showData = context.getResources().getStringArray(R.array.unit_area_value_list);
                break;
            case 2:
                showUnit = context.getResources().getStringArray(R.array.unit_weight_list);
                showData = context.getResources().getStringArray(R.array.unit_weight_value_list);
                break;
            case 3:
                showUnit = context.getResources().getStringArray(R.array.unit_volume_list);
                showData = context.getResources().getStringArray(R.array.unit_volume_value_list);
                break;
            case 4:
                showUnit = context.getResources().getStringArray(R.array.unit_temperature_list);
                showData = context.getResources().getStringArray(R.array.unit_temperature_value_list);
                break;
            case 5:
                showUnit = context.getResources().getStringArray(R.array.unit_time_list);
                showData = context.getResources().getStringArray(R.array.unit_time_value_list);
                break;
            case 6:
                showUnit = context.getResources().getStringArray(R.array.unit_pressure_list);
                showData = context.getResources().getStringArray(R.array.unit_pressure_value_list);
                break;
            case 7:
                showUnit = context.getResources().getStringArray(R.array.unit_speed_list);
                showData = context.getResources().getStringArray(R.array.unit_speed_value_list);
                break;
            case 8:
                showUnit = context.getResources().getStringArray(R.array.unit_data_list);
                showData = context.getResources().getStringArray(R.array.unit_data_value_list);
                break;
            case 9:
                showUnit = context.getResources().getStringArray(R.array.unit_radioactivity_list);
                showData = context.getResources().getStringArray(R.array.unit_radioactivity_value_list);
                break;
            default:
                break;
        }
        UpdateshowData(spinnerUnit,inputNum);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView cell_show_data,cell_show_unit;

        public ViewHolder(View itemView) {
            super(itemView);
            cell_show_data = (TextView) itemView.findViewById(R.id.cell_show_data);
            cell_show_unit = (TextView) itemView.findViewById(R.id.cell_show_unit);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_unit_show_select,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.cell_show_data.setText(showData[position]);
        holder.cell_show_unit.setText(showUnit[position]);
    }

    @Override
    public int getItemCount() {
        return showUnit.length !=0 ? showUnit.length : 0;
    }

}
