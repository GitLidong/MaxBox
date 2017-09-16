package com.lidong.maxbox.database;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by ubuntu on 17-9-16.
 */

public class MyDatabaseHelper {
    public static void addData(QrcodeData data) {
        data.save();
    }

    public static List<QrcodeData> getAllData() {
        return DataSupport.findAll(QrcodeData.class);
    }
}
