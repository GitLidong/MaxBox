package com.lidong.maxbox.activity.QrcodeSonAcitvity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.lidong.maxbox.manager.MyActivity;
import com.lidong.maxbox.myinterface.QrCodeClickCallback;
import com.lidong.maxbox.util.ImageInfoBean;

import org.litepal.LitePal;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created by ubuntu on 17-9-13.
 * 这个界面用于展示所有的已经添加二维码
 * 可以增 删 查
 */

public class QrCodeCreateActivity extends MyActivity implements View.OnClickListener{
    private String TAG = "QrCodeCreateActivity";
    private Button back;
    private Button add;

    private RecyclerView recyclerViewShowQrcode;
    private QrcodeShowAdapter qrcodeShowAdapter;

    private List<QrcodeData> qrcodeDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
                Intent intent = new Intent(QrCodeCreateActivity.this,QRcodeActivity.class);
                startActivity(intent);
                break;
            case R.id.add:
                Intent intent2 = new Intent(QrCodeCreateActivity.this,QrcodePickActivity.class);
                startActivity(intent2);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(QrCodeCreateActivity.this,QRcodeActivity.class);
        startActivity(intent);
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
            try {
                shareQrcode(imageFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
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
        if (temp.getQrName().equals("Text") || temp.getQrName().equals("Url")) {
            infoBean.setDescription("Content:"+temp.getQrContent());
        } else if (temp.getQrName().equals("Email")) {
           infoBean.setDescription("Address:"+ temp.getQrContent());
        } else {
            infoBean.setDescription(temp.getQrContent());
        }

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

    /*
     * 用于分享提示,当分享时候，会在本地的相册下面生成该二维码图片，
     * 对比其他软件的分析，觉得该图片不用做删除处理，用户可以自行删除。
     */
    private void shareQrcode(String imageFile) throws FileNotFoundException {
        QrcodeData temp = MyDatabaseHelper.getQrcodeData(imageFile);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        Bitmap bitmap = BitmapFactory.decodeStream(this.openFileInput(temp.getImageFile()));
        Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null,null));
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.putExtra(Intent.EXTRA_SUBJECT, "OurMaxTools-"+temp.getQrName());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(intent, getTitle()));
    }
}
