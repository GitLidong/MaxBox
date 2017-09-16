package com.lidong.maxbox.database;

import org.litepal.crud.DataSupport;

/**
 * Created by ubuntu on 17-9-16.
 */

public class QrcodeData extends DataSupport{

    private int id;
    private String imageFile;
    private String qrName;
    private String qrContent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    public String getQrName() {
        return qrName;
    }

    public void setQrName(String qrName) {
        this.qrName = qrName;
    }

    public String getQrContent() {
        return qrContent;
    }

    public void setQrContent(String qrContent) {
        this.qrContent = qrContent;
    }
}
