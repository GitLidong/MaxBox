package com.lidong.maxbox.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lidong.maxbox.R;
import com.lidong.maxbox.adapter.MyToolsMenuAdapter;
import com.lidong.maxbox.myinterface.ToolsClickCallback;

/**
 * Created by ubuntu on 17-8-31.
 */

public class MainFragment extends Fragment{

    private int fragmentPosition;
    private ToolsClickCallback toolsClickCallback;

    public MainFragment(int fragmentPosition,ToolsClickCallback toolsClickCallback) {
        this.fragmentPosition = fragmentPosition;
        this.toolsClickCallback = toolsClickCallback;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tools_fragment,container,false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.tools_list);
        MyToolsMenuAdapter myToolsMenuAdapter = new MyToolsMenuAdapter(getContext(),fragmentPosition,toolsClickCallback);
        recyclerView.setAdapter(myToolsMenuAdapter);
        return view;
    }
}
