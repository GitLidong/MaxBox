package com.lidong.maxbox.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lidong.maxbox.R;

/**
 * Created by ubuntu on 17-8-22.
 */

public class MainFragment2 extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment2,container,false);
        return view;
    }
}
