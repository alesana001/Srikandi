package com.simpus.srikandi.activity.adapters;


import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simpus.srikandi.R;
import com.simpus.srikandi.activity.ListIbuhmlActivity;
import com.simpus.srikandi.activity.ListIbuhmlPetaActivity;
import com.simpus.srikandi.activity.objects.BumilblmPeta;

import java.util.ArrayList;

public class ListIbuhmlPetaAdapter extends RecyclerView.Adapter<com.simpus.srikandi.activity.adapters.ListIbuhmlPetaViewHolder> {
    private Context context;
    public static ArrayList<BumilblmPeta> data;
    public  static ArrayList<BumilblmPeta> resultData;

    public ListIbuhmlPetaAdapter(Context context, ArrayList<BumilblmPeta> data){
        super();
        this.context = context;
        this.data = data;
        this.resultData = new ArrayList<>();
        this.resultData.addAll(data);
    }
    //menghubungkan ke item_list_ibuhml
    @Override
    public com.simpus.srikandi.activity.adapters.ListIbuhmlPetaViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_petagizkia, null);
        com.simpus.srikandi.activity.adapters.ListIbuhmlPetaViewHolder view = new com.simpus.srikandi.activity.adapters.ListIbuhmlPetaViewHolder(layoutView);
        return view;
    }


    @Override
    public void onBindViewHolder(ListIbuhmlPetaViewHolder holder, int position){
        holder.namaIbuView.setText(resultData.get(position).getNama());
        holder.tanggalHplView.setText(resultData.get(position).getHTP());
    }

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
                    for (BumilblmPeta item : data) {
                        if (item.getNama().toLowerCase().contains(text.toLowerCase())) {
                            resultData.add(item);
                    }
                    }
                }

                ((ListIbuhmlPetaActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }
}
