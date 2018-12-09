package com.simpus.srikandi.activity.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.simpus.srikandi.R;
import com.simpus.srikandi.activity.DetailBalitaActivity;
import com.simpus.srikandi.activity.ListBalitaActivity;

public class ListBalitaViewHolder extends RecyclerView.ViewHolder {
    TextView namaBayiView, namaIbuView, statusStuntingView;

    public ListBalitaViewHolder(View itemView){
        super(itemView);

        namaBayiView = (TextView)itemView.findViewById(R.id.namaBayiView);
        namaIbuView = (TextView)itemView.findViewById(R.id.namaIbuView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mengakmbil posisi item
                int position = getAdapterPosition();

                //membuat inten untuk menjalankan activity baru
                Intent intent = new Intent(view.getContext(), DetailBalitaActivity.class);
                intent.putExtra("No_Index_Balita_Dinas", ListBalitaAdapter.resultData.get(position).getNo_Index_Balita_Dinas());
                intent.putExtra("Nama_Bayi", ListBalitaAdapter.resultData.get(position).getNama_Bayi());
                intent.putExtra("Tanggal_Lahir", ListBalitaAdapter.resultData.get(position).getTanggal_Lahir());
                intent.putExtra("Nama_Ibu", ListBalitaAdapter.resultData.get(position).getNama_Ibu());
                intent.putExtra("Nama_Ayah", ListBalitaAdapter.resultData.get(position).getNama_Ayah());
                intent.putExtra("Kode_Wilayah", ListBalitaAdapter.resultData.get(position).getKode_Wilayah());
                //menjalankan intent
                view.getContext().startActivity(intent);

            }
        });
    }
}