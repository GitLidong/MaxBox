package com.lidong.maxbox.manager;

import android.app.Activity;

import com.lidong.maxbox.activity.CompassMainActivity;
import com.lidong.maxbox.activity.LedActivity;
import com.lidong.maxbox.activity.LevelActivity;

/**
 * Created by ubuntu on 17-8-31.
 */

public class ActivityFactory {

    public static Activity createActivityByMenuAndPosition(int whichMenu,int posiiton){
        Activity activity = null;
        if (whichMenu == 0) {
            switch (posiiton) {
                case 0:
                    activity = new LedActivity();
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    activity = new LevelActivity();
                    break;
                case 4:
                    activity = new CompassMainActivity();
                    break;
                case 5:
                    break;
                default:
                    break;
            }
        }
        else if(whichMenu ==1) {
            switch (posiiton) {
                case 0:
                    activity = new LedActivity();
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                default:
                    break;
            }
        }
        return activity;
    }
}