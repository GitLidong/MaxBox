package com.lidong.maxbox.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidong.maxbox.R;

/**
 * Created by ubuntu on 17-9-16.
 */

public class QrcodeShowAdapter extends RecyclerView.Adapter<QrcodeShowAdapter.ViewHolder>{

    QrcodeShowAdapter() {

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
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
