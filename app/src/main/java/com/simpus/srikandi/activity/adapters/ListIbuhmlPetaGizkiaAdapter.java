package com.simpus.srikandi.activity.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simpus.srikandi.R;
import com.simpus.srikandi.activity.ListIbuhmlPetagizkiaActivity;
import com.simpus.srikandi.activity.objects.PetaBumil;

import java.util.ArrayList;

public class ListIbuhmlPetaGizkiaAdapter extends RecyclerView.Adapter<ListIbuhmlPetaGizkiaViewHolder>{
    private Context context;
    public static ArrayList<PetaBumil> data;
    public  static ArrayList<PetaBumil> resultData;

    public ListIbuhmlPetaGizkiaAdapter(Context context, ArrayList<PetaBumil> data){
        super();
        this.context = context;
        this.data = data;
        this.resultData = new ArrayList<>();
        this.resultData.addAll(data);
    }
    //menghubungkan ke item_list
    @Override
    public ListIbuhmlPetaGizkiaViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_petagizkia, null);
        ListIbuhmlPetaGizkiaViewHolder view = new ListIbuhmlPetaGizkiaViewHolder(layoutView);
        return view;
    }

    @Override
    public void onBindViewHolder(ListIbuhmlPetaGizkiaViewHolder holder, int position){
        holder.namaIbuView.setText(resultData.get(position).getNama());
        holder.statusRistiView.setText(resultData.get(position).getStatusRisti());
        holder.tanggalHplView.setText(resultData.get(position).getHpl());
    }
    @Override

    public int getItemCount() {

        return (null != resultData ? resultData.size() : 0);

    }

    public void filter(final String text) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                resultData.clear();
                if (TextUtils.isEmpty(text)) {
                    resultData.addAll(data);
                } else {
                    for (PetaBumil item : data) {
                        if (item.getNama().toLowerCase().contains(text.toLowerCase())) {
                            resultData.add(item);
                        }
                    }
                }

                ((ListIbuhmlPetagizkiaActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }
}
