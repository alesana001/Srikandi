package com.simpus.satitiv2.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simpus.satitiv2.R;
import com.simpus.satitiv2.activity.ListIbuhmlActivity;
import com.simpus.satitiv2.activity.MainActivity;
import com.simpus.satitiv2.activity.TmbhIbuHmlActivity;


public class OneFragment extends Fragment{

    CardView menulaporbumil, menuinfobumil, menubumilverif, menuinfografik;
    public OneFragment() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_one, container, false);
       //mendefinisikan cardview ketika di klik
        menulaporbumil=(CardView) view.findViewById(R.id.laporDataBumil);
        menuinfobumil=(CardView)view.findViewById(R.id.infoDataTerlapor);
        menubumilverif = (CardView)view.findViewById(R.id.infoBumilTerverifikasi);
        menuinfografik =(CardView)view.findViewById(R.id.infoGrafikBumil);

        menulaporbumil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menulaporbumil = new Intent (getActivity(), TmbhIbuHmlActivity.class);
                startActivity(menulaporbumil);
            }
        });

        menuinfobumil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menuinfobumil = new Intent (getActivity(), ListIbuhmlActivity.class);
                startActivity(menuinfobumil);
            }
        });

        return view;
    }

}
