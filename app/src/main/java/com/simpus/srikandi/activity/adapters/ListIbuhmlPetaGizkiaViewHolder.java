package com.simpus.srikandi.activity.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.simpus.srikandi.R;
import com.simpus.srikandi.activity.DetailIbuHmlPetagizkiaActivity;

public class ListIbuhmlPetaGizkiaViewHolder extends RecyclerView.ViewHolder {
    TextView namaIbuView, tanggalHplView, statusRistiView;

    public ListIbuhmlPetaGizkiaViewHolder(View itemView){
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
                Intent intent = new Intent(view.getContext(), DetailIbuHmlPetagizkiaActivity.class);
                intent.putExtra("No_Index_Bumil", ListIbuhmlPetaGizkiaAdapter.resultData.get(position).getNo_Index_Bumil());
                intent.putExtra("Nama", ListIbuhmlPetaGizkiaAdapter.resultData.get(position).getNama());
                intent.putExtra("NamaSuami", ListIbuhmlPetaGizkiaAdapter.resultData.get(position).getNamaSuami());
                intent.putExtra("TanggalLahir", ListIbuhmlPetaGizkiaAdapter.resultData.get(position).getTanggalLahir());
                intent.putExtra("Lat", ListIbuhmlPetaGizkiaAdapter.resultData.get(position).getLat());
                intent.putExtra("lng", ListIbuhmlPetaGizkiaAdapter.resultData.get(position).getLng());
                intent.putExtra("StatusRisti", ListIbuhmlPetaGizkiaAdapter.resultData.get(position).getStatusRisti());
                intent.putExtra("Hpl", ListIbuhmlPetaGizkiaAdapter.resultData.get(position).getHpl());
                intent.putExtra("Nip", ListIbuhmlPetaGizkiaAdapter.resultData.get(position).getNip());

                //menjalankan intent
                view.getContext().startActivity(intent);

            }
        });
    }
}