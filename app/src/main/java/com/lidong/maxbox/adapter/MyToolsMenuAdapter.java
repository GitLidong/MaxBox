package com.lidong.maxbox.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidong.maxbox.R;
import com.lidong.maxbox.myinterface.ToolsClickCallback;

/**
 * Created by ubuntu on 17-8-31.
 */

public class MyToolsMenuAdapter extends RecyclerView.Adapter<MyToolsMenuAdapter.ViewHolder>{

    private int whichMenu;
    private String[] mMenuOneItemNames;
    private String[] mMenuTwoItemNames;

    private final int[] menu_one_bg = new int[]{
            R.drawable.led_icon,
            R.drawable.ic_menu_code,
            R.drawable.ic_menu_decibel,
            R.drawable.ic_menu_level,
            R.drawable.ic_menu_compass,
            R.drawable.ic_menu_flashlight,
    };

    private final int[] menu_two_bg = new int[]{
            R.drawable.ic_menu_mirror,
            R.drawable.ic_menu_magnifier,
            R.drawable.ic_menu_scale,
            R.drawable.ic_menu_protractor,
            R.drawable.ic_menu_unit,
            R.drawable.ic_menu_size,
    };

    private ToolsClickCallback toolsClickCallback;

    public MyToolsMenuAdapter(Context context, int whichMenu, ToolsClickCallback toolsClickCallback) {
        this.whichMenu = whichMenu;
        this.toolsClickCallback = toolsClickCallback;
        mMenuOneItemNames = context.getResources().getStringArray(R.array.menu_one);
        mMenuTwoItemNames = context.getResources().getStringArray(R.array.menu_two);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;
        public int position;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.tool_image);
            textView = (TextView) itemView.findViewById(R.id.tool_name);

            itemView.setOnClickListener(onClick);
            itemView.setOnLongClickListener(onLongClick);
        }

        private View.OnClickListener onClick =new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("MyToolsMenuAdapter","menu position :" +whichMenu+" "+position);
                toolsClickCallback.switchActivity(whichMenu,position);
            }
        };

        private View.OnLongClickListener onLongClick = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String shortcutName = whichMenu==0? mMenuOneItemNames[position]:mMenuTwoItemNames[position];
                int shortcutIcon = whichMenu == 0 ? menu_one_bg[position]:menu_two_bg[position];
                toolsClickCallback.makeShortcut(shortcutName,shortcutIcon);
                //return true消耗了事件，不会继续传递
                return true;
            }
        };
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_tools,parent,false);
        final ViewHolder holder =new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.position = position;
        if( whichMenu ==0 ) {
            holder.imageView.setImageResource(menu_one_bg[position]);
            holder.textView.setText(mMenuOneItemNames[position]);
        }else{
            holder.imageView.setImageResource(menu_two_bg[position]);
            holder.textView.setText(mMenuTwoItemNames[position]);
        }
    }

    @Override
    public int getItemCount() {
        return menu_one_bg.length;
    }

}
