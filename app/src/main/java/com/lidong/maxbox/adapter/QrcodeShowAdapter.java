package com.lidong.maxbox.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidong.maxbox.MyApplication;
import com.lidong.maxbox.R;
import com.lidong.maxbox.database.QrcodeData;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created by ubuntu on 17-9-16.
 */

public class QrcodeShowAdapter extends RecyclerView.Adapter<QrcodeShowAdapter.ViewHolder>{

    private List<QrcodeData> data;
    private Context context;

    public QrcodeShowAdapter(List<QrcodeData> data) {
        this.data = data;
        context = MyApplication.getContext();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_qrcode,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.cell_qrcode_name.setText(data.get(position).getQrName());
        holder.cell_qrcode_content.setText(data.get(position).getQrContent());
        String imageFile = data.get(position).getImageFile();
        if (imageFile != null) {
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(
                        context.openFileInput(imageFile) );
                holder.cell_qrcode_image.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView cell_qrcode_image,cell_qrcode_scall,
                cell_qrcode_share,cell_qrcode_delete;

        private TextView cell_qrcode_name,cell_qrcode_content;

        public ViewHolder(View itemView) {
            super(itemView);

            cell_qrcode_image = (ImageView) itemView.findViewById(R.id.cell_qrcode_image);
            cell_qrcode_scall = (ImageView)itemView.findViewById(R.id.cell_qrcode_scall);
            cell_qrcode_share = (ImageView)itemView.findViewById(R.id.cell_qrcode_share);
            cell_qrcode_delete = (ImageView)itemView.findViewById(R.id.cell_qrcode_delete);

            cell_qrcode_name = (TextView) itemView.findViewById(R.id.cell_qrcode_name);
            cell_qrcode_content = (TextView) itemView.findViewById(R.id.cell_qrcode_content);
        }
    }
}
