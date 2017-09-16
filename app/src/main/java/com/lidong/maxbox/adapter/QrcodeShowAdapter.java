package com.lidong.maxbox.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidong.maxbox.R;
import com.lidong.maxbox.database.QrcodeData;

import java.util.List;

/**
 * Created by ubuntu on 17-9-16.
 */

public class QrcodeShowAdapter extends RecyclerView.Adapter<QrcodeShowAdapter.ViewHolder>{

    private List<QrcodeData> data;

    public QrcodeShowAdapter(List<QrcodeData> data) {
        this.data = data;
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
