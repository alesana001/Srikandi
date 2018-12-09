package com.simpus.srikandi.activity.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.simpus.srikandi.R;
import com.simpus.srikandi.activity.DetailBalitagizkiaActivity;

public class ListBalitaPetaViewHolder extends RecyclerView.ViewHolder {
    TextView namaBayiView, namaIbuView, ketStuntingView;

    public ListBalitaPetaViewHolder(View itemView){
        super(itemView);

        namaBayiView = (TextView)itemView.findViewById(R.id.namaBayiView);
        namaIbuView = (TextView)itemView.findViewById(R.id.namaIbuView);
        ketStuntingView=(TextView)itemView.findViewById(R.id.ketStuntingView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mengakmbil posisi item
                int position = getAdapterPosition();

                //membuat inten untuk menjalankan activity baru
                Intent intent = new Intent(view.getContext(), DetailBalitagizkiaActivity.class);
                intent.putExtra("No_Index_Balita_Dinas", ListBalitaPetaAdapter.resultData.get(position).getNo_Index_Balita_Dinas());
                intent.putExtra("Nama_Bayi", ListBalitaPetaAdapter.resultData.get(position).getNama_Bayi());
                intent.putExtra("Tanggal_Lahir", ListBalitaPetaAdapter.resultData.get(position).getTanggal_Lahir());
                intent.putExtra("Nama_Ibu", ListBalitaPetaAdapter.resultData.get(position).getNama_Ibu());
                intent.putExtra("Nama_Ayah", ListBalitaPetaAdapter.resultData.get(position).getNama_Ayah());
                intent.putExtra("Lat", ListBalitaPetaAdapter.resultData.get(position).getLat());
                intent.putExtra("lng", ListBalitaPetaAdapter.resultData.get(position).getLng());
                intent.putExtra("Ket_Stunting", ListBalitaPetaAdapter.resultData.get(position).getKet_Stunting());
                intent.putExtra("Ket_Gibur", ListBalitaPetaAdapter.resultData.get(position).getKet_Gibur());
                intent.putExtra("Nip", ListBalitaPetaAdapter.resultData.get(position).getNip());
                intent.putExtra("Kode_Desa", ListBalitaPetaAdapter.resultData.get(position).getKode_Desa());
                //menjalankan intent
                view.getContext().startActivity(intent);

            }
        });
    }
}
