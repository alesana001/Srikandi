package  com.simpus.srikandi.activity.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import  com.simpus.srikandi.activity.DetailIbuHmlActivity;
import  com.simpus.srikandi.R;

/**
 * Created by alesana on 05/03/2018.
 */

public class ListIbuhmlGizkiaViewHolder extends RecyclerView.ViewHolder{
    TextView namaIbuView, alamatIbuhmlView, verifikasiDataView;

    public ListIbuhmlGizkiaViewHolder(View itemView){
        super(itemView);

        namaIbuView  = (TextView)itemView.findViewById(R.id.namaIbuView);
        alamatIbuhmlView = (TextView)itemView.findViewById(R.id.alamatIbuhmlView);
        verifikasiDataView = (TextView)itemView.findViewById(R.id.verifikasiDataView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mengambil posisi item
                int position = getAdapterPosition();
                //membuat inten untuk menjalankan activity baru
                Intent intent = new Intent(view.getContext(), DetailIbuHmlActivity.class);
                intent.putExtra("No_Index", ListIbuhmlGizkiaAdapter.resultData.get(position).getNo_Index());
                intent.putExtra("Tgl_Informasi", ListIbuhmlGizkiaAdapter.resultData.get(position).getTgl_Informasi());
                intent.putExtra("Nama", ListIbuhmlGizkiaAdapter.resultData.get(position).getNama());
                intent.putExtra("Suami", ListIbuhmlGizkiaAdapter.resultData.get(position).getSuami());
                intent.putExtra("Alamat", ListIbuhmlGizkiaAdapter.resultData.get(position).getAlamat());
                intent.putExtra("Kode_Kecamatan", ListIbuhmlGizkiaAdapter.resultData.get(position).getKode_Kecamatan());
                intent.putExtra("Kode_Desa", ListIbuhmlGizkiaAdapter.resultData.get(position).getKode_Desa());
                intent.putExtra("User_id_pelapor", ListIbuhmlGizkiaAdapter.resultData.get(position).getUser_id_pelapor());
                intent.putExtra("Verifikasi", ListIbuhmlGizkiaAdapter.resultData.get(position).getVerifikasi());
                intent.putExtra("Lat", ListIbuhmlGizkiaAdapter.resultData.get(position).getLat());
                intent.putExtra("Lng", ListIbuhmlGizkiaAdapter.resultData.get(position).getLng());
                intent.putExtra("No_Telp", ListIbuhmlGizkiaAdapter.resultData.get(position).getNo_Telp());

                //menjalankan intent
                view.getContext().startActivity(intent);

            }
        });
    }
}
