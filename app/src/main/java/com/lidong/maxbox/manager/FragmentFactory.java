package com.lidong.maxbox.manager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lidong.maxbox.R;
import com.lidong.maxbox.views.LedAndroid;
import com.lidong.maxbox.views.LedBean;
import com.lidong.maxbox.views.LedBeer;
import com.lidong.maxbox.views.LedHappy;
import com.lidong.maxbox.views.LedHeart;
import com.lidong.maxbox.views.LedMail;
import com.lidong.maxbox.views.LedSos;

/**
 * Created by ubuntu on 17-8-28.
 */

public class FragmentFactory extends Fragment{

    private int fragmentID;

    public FragmentFactory(int layoutID){
        this.fragmentID = layoutID;

    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.led_feagment,container,false);
        layout.addView(createViewById(fragmentID));
        return layout;
    }

    private View createViewById(int fragmentID) {
        View view = null;
        switch (fragmentID) {
            case 0:
                view = new LedAndroid(getContext());
                break;
            case 1:
                view = new LedHappy(getContext());
                break;
            case 2:
                view = new LedMail(getContext());
                break;
            case 3:
                view = new LedHeart(getContext());
                break;
            case 4:
                view = new LedSos(getContext());
                break;
            case 5:
                view = new LedBeer(getContext());
                break;
            case 6:
                view = new LedBean(getContext());
                break;
        }
        return view;
    }
}
