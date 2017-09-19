package com.lidong.maxbox.activity.QrcodeSonAcitvity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.lidong.maxbox.R;
import com.lidong.maxbox.activity.QRcodeActivity;
import com.lidong.maxbox.adapter.QrcodeShowAdapter;
import com.lidong.maxbox.database.MyDatabaseHelper;
import com.lidong.maxbox.database.QrcodeData;
import com.lidong.maxbox.myinterface.QrCodeClickCallback;
import com.lidong.maxbox.util.ImageInfoBean;

import org.litepal.LitePal;

import java.util.List;

/**
 * Created by ubuntu on 17-9-13.
 * 这个界面用于展示所有的已经添加二维码
 * 可以增 删 查
 */

public class QrCodeCreateActivity extends Activity implements View.OnClickListener{
    private String TAG = "QrCodeCreateActivity";
    private Button back;
    private Button add;

    private RecyclerView recyclerViewShowQrcode;
    private QrcodeShowAdapter qrcodeShowAdapter;

    private List<QrcodeData> qrcodeDataList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enqrcode);
        initDatabase();
        initView();
    }

    /*
     * 添加 onResume() 方法，解决按back键返回数据无法加载问题
     */
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,TAG+"  onResume constructed! ");
        qrcodeDataList = MyDatabaseHelper.getAllData();
        if (qrcodeDataList.size() >0) {
            qrcodeShowAdapter = new QrcodeShowAdapter(qrcodeDataList);
            qrcodeShowAdapter.setQrCodeClickListener(qrCodeClickCallback);

            recyclerViewShowQrcode.setAdapter(qrcodeShowAdapter);
        }
    }

    private void initView() {
        back = (Button) findViewById(R.id.back);
        add = (Button) findViewById(R.id.add);
        back.setOnClickListener(this);
        add.setOnClickListener(this);

        recyclerViewShowQrcode = (RecyclerView) findViewById(R.id.recycleview_showdata);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewShowQrcode.setLayoutManager(linearLayoutManager);
    }

    private void initDatabase() {
        /*
        * 使用第三方库 litepal，具体使用参照
        *  https://github.com/LitePalFramework/LitePal
         */
        LitePal.getDatabase();
        Log.i(TAG,"LitaPal create database success ,or database exists already !!");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.add:
                Intent intent2 = new Intent(QrCodeCreateActivity.this,QrcodePickActivity.class);
                startActivity(intent2);
                break;
        }
    }

    /*
     * 自定义接口，用于回调 RecyclerView 的 Adapter的点击事件
     */
    private QrCodeClickCallback qrCodeClickCallback = new QrCodeClickCallback() {
        @Override
        public void scallQr(String imageFile) {
            scalllQrCode(imageFile);
        }

        @Override
        public void shareQr(String imageFile) {

        }

        @Override
        public void deleteQr(String imageFile, int whichItem) {
            whenDeleteShowDialog(imageFile,whichItem);
        }
    };

    /*
     * 用于查看 二维码的 界面跳转，数据传递
     */
    private void scalllQrCode(String imageFile) {
        QrcodeData temp = MyDatabaseHelper.getQrcodeData(imageFile);
        Intent intent = new Intent(QrCodeCreateActivity.this,DisplayQrcodeActivity.class);
        ImageInfoBean infoBean = new ImageInfoBean();
        infoBean.setName(temp.getQrName());
        infoBean.setDescription("Content:"+temp.getQrContent());
        infoBean.setUri(temp.getImageFile());
        intent.putExtra("type",temp.getQrName());
        intent.putExtra("encode_text", infoBean);
        intent.putExtra("jump",1);
        startActivity(intent);
    }

    /*
     * 用于删除提示的 dialog
     */
    private void whenDeleteShowDialog(final String imageFile,final int whichItem) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Delete")
                .setMessage("Are you sure to delete the selected items?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MyDatabaseHelper.deleteData(imageFile);
                        qrcodeDataList.remove(whichItem);
                        qrcodeShowAdapter.notifyItemRemoved(whichItem);
                        qrcodeShowAdapter.notifyItemRangeChanged(whichItem,qrcodeDataList.size()-whichItem);
                        Log.i(TAG,"qrcodeDataList index at "+whichItem+" was removed and notify adapter refresh !");
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        alertDialog.show();
    }
}
