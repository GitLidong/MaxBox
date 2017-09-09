package com.lidong.maxbox.manager;

import android.app.Activity;

import com.lidong.maxbox.activity.CompassMainActivity;
import com.lidong.maxbox.activity.FlashlightActivity;
import com.lidong.maxbox.activity.LedActivity;
import com.lidong.maxbox.activity.LevelActivity;
import com.lidong.maxbox.activity.MagnifierActivity;
import com.lidong.maxbox.activity.MirrorActivity;
import com.lidong.maxbox.activity.ProtractorActivity;
import com.lidong.maxbox.activity.RulerActivity;
import com.lidong.maxbox.activity.SizeActivity;
import com.lidong.maxbox.activity.SoundMetelActivity;

/**
 * Created by ubuntu on 17-8-31.
 */

public class ActivityFactory {

    public static Activity createActivityByMenuAndPosition(int whichMenu,int position){
        Activity activity = null;
        if (whichMenu == 0) {
            switch (position) {
                case 0:
                    activity = new LedActivity();
                    break;
                case 1:
                    break;
                case 2:
                    activity = new SoundMetelActivity();
                    break;
                case 3:
                    activity = new LevelActivity();
                    break;
                case 4:
                    activity = new CompassMainActivity();
                    break;
                case 5:
                    activity = new FlashlightActivity();
                    break;
                default:
                    break;
            }
        }
        else if(whichMenu ==1) {
            switch (position) {
                case 0:
                    activity = new MirrorActivity();
                    break;
                case 1:
                    activity=new MagnifierActivity();
                    break;
                case 2:
                    activity = new RulerActivity();
                    break;
                case 3:
                    activity=new ProtractorActivity();
                    break;
                case 4:
                    break;
                case 5:
                    activity = new SizeActivity();
                    break;
                default:
                    break;
            }
        }
        return activity;
    }
}
