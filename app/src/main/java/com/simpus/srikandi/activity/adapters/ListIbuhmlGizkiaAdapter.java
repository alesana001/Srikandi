package com.simpus.srikandi.activity.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import  com.simpus.srikandi.activity.ListIbuhmlActivity;
import  com.simpus.srikandi.activity.ListIbuhmlgizkiaActivity;
import  com.simpus.srikandi.R;
import  com.simpus.srikandi.activity.objects.IbuHml;

import java.util.ArrayList;


/**
 * Created by alesana on 04/03/2018.
 */

public class ListIbuhmlGizkiaAdapter extends RecyclerView.Adapter<ListIbuhmlGizkiaViewHolder> {
    private Context context;
    public static ArrayList<IbuHml> data;
    public  static ArrayList<IbuHml> resultData;

    public ListIbuhmlGizkiaAdapter(Context context, ArrayList<IbuHml> data){
        super();
        this.context = context;
        this.data = data;
        this.resultData = new ArrayList<>();
        this.resultData.addAll(data);
    }
    //menghubungkan ke item_list_ibuhml
    @Override
    public ListIbuhmlGizkiaViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_ibuhml, null);
        ListIbuhmlGizkiaViewHolder view = new ListIbuhmlGizkiaViewHolder(layoutView);
        return view;
    }

    @Override
    public void onBindViewHolder(ListIbuhmlGizkiaViewHolder holder, int position){
        holder.namaIbuView.setText(resultData.get(position).getNama());
        holder.alamatIbuhmlView.setText(resultData.get(position).getAlamat());
        if(resultData.get(position).getVerifikasi().equals("S")){
            holder.verifikasiDataView. setText("Sudah Terverifikasi");
            holder.verifikasiDataView.setTextColor(Color.parseColor("#00B0FF"));
        }else{
            holder.verifikasiDataView. setText("Belum Terverifikasi");
            holder.verifikasiDataView.setTextColor(Color.parseColor("#F44336"));
        }

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
                    for (IbuHml item : data) {
                        if (item.getNama().toLowerCase().contains(text.toLowerCase())) {
                            resultData.add(item);
                        }
                    }
                }

                ((ListIbuhmlgizkiaActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }
}
