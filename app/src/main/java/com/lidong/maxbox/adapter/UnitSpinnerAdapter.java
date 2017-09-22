package com.lidong.maxbox.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lidong.maxbox.R;

import java.util.List;

/**
 * Created by ubuntu on 17-9-11.
 */

public class UnitSpinnerAdapter extends ArrayAdapter<String>{

    private String TAG = "UnitSpinnerAdapter";

    private Context context;
    private String [] unitSpinnerArray;
    private TextView unitText;

    public UnitSpinnerAdapter(Context context, int resource,
                              int textViewResourceId, int whichUnit,List<String> objects){
        super(context,resource,textViewResourceId,objects);
        this.context = context;

        switch (whichUnit) {
            case 0:
                unitSpinnerArray = context.getResources().getStringArray(R.array.unit_length_list);
                break;
            case 1:
                unitSpinnerArray = context.getResources().getStringArray(R.array.unit_area_list);
                break;
            case 2:
                unitSpinnerArray = context.getResources().getStringArray(R.array.unit_weight_list);
                break;
            case 3:
                unitSpinnerArray = context.getResources().getStringArray(R.array.unit_volume_list);
                break;
            case 4:
                unitSpinnerArray = context.getResources().getStringArray(R.array.unit_temperature_list);
                break;
            case 5:
                unitSpinnerArray = context.getResources().getStringArray(R.array.unit_time_list);
                break;
            case 6:
                unitSpinnerArray = context.getResources().getStringArray(R.array.unit_pressure_list);
                break;
            case 7:
                unitSpinnerArray = context.getResources().getStringArray(R.array.unit_speed_list);
                break;
            case 8:
                unitSpinnerArray = context.getResources().getStringArray(R.array.unit_data_list);
                break;
            case 9:
                unitSpinnerArray = context.getResources().getStringArray(R.array.unit_radioactivity_list);
                break;
            default:
                break;
        }
    }

    @Override
    public int getCount() {
        return unitSpinnerArray.length != 0 ? unitSpinnerArray.length : 0;
    }

    @Override
    public String getItem(int position) {
        return unitSpinnerArray[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.cell_unit,null);
        if (convertView != null) {
            unitText = (TextView) convertView.findViewById(R.id.unit_text);
            unitText.setText(unitSpinnerArray[position]);
        }
        return convertView;
    }
}
