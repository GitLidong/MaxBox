package com.lidong.maxbox.database;

import android.content.Context;
import android.util.Log;

import com.lidong.maxbox.MyApplication;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by ubuntu on 17-9-16.
 * 该类的静态方法用于删除数据库中的文件
 * 及 对应的 com.lidong.maxbox/files/ 下保存的文件
 */

public class MyDatabaseHelper {

    private static final String TAG = "MyDatabaseHelper";
    private static  Context context = MyApplication.getContext();

    public static void addData(QrcodeData data) {
        data.save();
        Log.i(TAG,"addData succeed ! ");
    }

    public static List<QrcodeData> getAllData() {
        Log.i(TAG,"getAllData succeed ! ");
        return DataSupport.findAll(QrcodeData.class);
    }

    public static void deleteData(String imageFile) {
        DataSupport.deleteAll(QrcodeData.class,"imagefile == ?", imageFile);

        context.deleteFile(imageFile);

        Log.i(TAG,"deleteData by imageFile succeed ! ");
        Log.i(TAG,"delete file "+imageFile+" succeed ! ");
    }

    public static void deleteAllData() {
        DataSupport.deleteAll(QrcodeData.class);

        String [] images = context.fileList();
        for (String image : images){
            context.deleteFile(image);
        }

        Log.i(TAG,"deleteAllData succeed ! ");
        Log.i(TAG,"delete all files succeed ! ");
    }

    public static QrcodeData getQrcodeData(String imageFile) {
        List<QrcodeData> qrcodeData = DataSupport.where("imagefile == ?",imageFile).find(QrcodeData.class);
        Log.i(TAG,"getQrcodeData by imageFile succeed ! ");
        return qrcodeData.get(0);
    }
}
