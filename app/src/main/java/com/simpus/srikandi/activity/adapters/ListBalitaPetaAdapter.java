package com.simpus.srikandi.activity.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simpus.srikandi.R;
import com.simpus.srikandi.activity.ListBalitagizkiaActivity;
import com.simpus.srikandi.activity.objects.Petabalita;

import java.util.ArrayList;

public class ListBalitaPetaAdapter extends RecyclerView.Adapter<ListBalitaPetaViewHolder>{
    private Context context;
    public static ArrayList<Petabalita> data;
    public  static ArrayList<Petabalita> resultData;

    public ListBalitaPetaAdapter(Context context, ArrayList<Petabalita> data){
        super();
        this.context = context;
        this.data = data;
        this.resultData = new ArrayList<>();
        this.resultData.addAll(data);
    }
    //menghubungkan ke item_list
    @Override
    public ListBalitaPetaViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_balita_gizkia, null);
        ListBalitaPetaViewHolder view = new ListBalitaPetaViewHolder(layoutView);
        return view;
    }

    @Override
    public void onBindViewHolder(ListBalitaPetaViewHolder holder, int position){
        holder.namaBayiView.setText(resultData.get(position).getNama_Bayi());
        holder.namaIbuView.setText(resultData.get(position).getNama_Ibu());
        holder.ketStuntingView.setText(resultData.get(position).getKet_Stunting());
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
                    for (Petabalita item : data) {
                        if (item.getNama_Bayi().toLowerCase().contains(text.toLowerCase())) {
                            resultData.add(item);
                        }
                    }
                }

                ((ListBalitagizkiaActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }
}

