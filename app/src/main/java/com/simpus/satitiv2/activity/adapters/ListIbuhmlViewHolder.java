package   com.simpus.satitiv2.activity.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


import   com.simpus.satitiv2.activity.DetailIbuHmlActivity;
import com.simpus.satitiv2.R;

/**
 * Created by alesana on 05/03/2018.
 */

public class ListIbuhmlViewHolder extends RecyclerView.ViewHolder{
    TextView namaIbuView, alamatIbuhmlView, verifikasiDataView;

    public ListIbuhmlViewHolder(View itemView){
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
                intent.putExtra("No_Index", ListIbuhmlAdapter.resultData.get(position).getNo_Index());
                intent.putExtra("Tgl_Informasi", ListIbuhmlAdapter.resultData.get(position).getTgl_Informasi());
                intent.putExtra("Nama", ListIbuhmlAdapter.resultData.get(position).getNama());
                intent.putExtra("Suami", ListIbuhmlAdapter.resultData.get(position).getSuami());
                intent.putExtra("Alamat", ListIbuhmlAdapter.resultData.get(position).getAlamat());
                intent.putExtra("Kode_Kecamatan", ListIbuhmlAdapter.resultData.get(position).getKode_Kecamatan());
                intent.putExtra("Kode_Desa", ListIbuhmlAdapter.resultData.get(position).getKode_Desa());
                intent.putExtra("User_id_pelapor", ListIbuhmlAdapter.resultData.get(position).getUser_id_pelapor());
                intent.putExtra("Verifikasi", ListIbuhmlAdapter.resultData.get(position).getVerifikasi());
                intent.putExtra("Lat", ListIbuhmlAdapter.resultData.get(position).getLat());
                intent.putExtra("Lng", ListIbuhmlAdapter.resultData.get(position).getLng());
                intent.putExtra("No_Telp", ListIbuhmlAdapter.resultData.get(position).getNo_Telp());

                //menjalankan intent
                view.getContext().startActivity(intent);

            }
        });
    }
}
