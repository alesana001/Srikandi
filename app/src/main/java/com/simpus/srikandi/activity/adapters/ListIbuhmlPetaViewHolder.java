package com.simpus.srikandi.activity.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.simpus.srikandi.R;
import com.simpus.srikandi.activity.DetailIbuHmlPetaActivity;

    public class ListIbuhmlPetaViewHolder extends RecyclerView.ViewHolder {
        TextView namaIbuView, tanggalHplView, statusRistiView;

        public ListIbuhmlPetaViewHolder(View itemView){
            super(itemView);

            namaIbuView = (TextView)itemView.findViewById(R.id.namaIbuView);
            tanggalHplView = (TextView)itemView.findViewById(R.id.tanggalHplView);
            statusRistiView= (TextView)itemView.findViewById(R.id.statusRistiView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //mengakmbil posisi item
                    int position = getAdapterPosition();

                    //membuat inten untuk menjalankan activity baru
                    Intent intent = new Intent(view.getContext(), DetailIbuHmlPetaActivity.class);
                    intent.putExtra("No_Index_Bumil_Dinas", ListIbuhmlPetaAdapter.resultData.get(position).getNo_Index_Bumil_Dinas());
                    intent.putExtra("Nama", ListIbuhmlPetaAdapter.resultData.get(position).getNama());
                    intent.putExtra("Suami", ListIbuhmlPetaAdapter.resultData.get(position).getSuami());
                    intent.putExtra("Tgl_Lahir", ListIbuhmlPetaAdapter.resultData.get(position).getTgl_Lahir());
                    intent.putExtra("HTP", ListIbuhmlPetaAdapter.resultData.get(position).getHTP());
                    intent.putExtra("Kode_Desa", ListIbuhmlPetaAdapter.resultData.get(position).getKode_Desa());

                    //menjalankan intent
                    view.getContext().startActivity(intent);

                }
            });
        }
    }