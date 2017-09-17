package com.lidong.maxbox.myinterface;

/**
 * Created by ubuntu on 17-9-17.
 */

public interface QrCodeClickCallback {
    void scallQr(String imageFile);
    void shareQr(String imageFile);
    void deleteQr(String imageFile,int which);

}
