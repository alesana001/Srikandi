package com.simpus.srikandi.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.simpus.srikandi.R;
import com.simpus.srikandi.activity.AppController;
import com.simpus.srikandi.activity.ListIbuhmlPetaActivity;
import com.simpus.srikandi.activity.ListIbuhmlPetagizkiaActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;


public class TwoFragment extends Fragment{

    CardView menuinfodatabumil, menuinfopetabumil;
    TextView totalIbuhml, totalBumilPeta;
    public TwoFragment() {
        // Required empty public constructor
    }
    @Override
    public void onStart() {
        super.onStart();
        getData();
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        menuinfodatabumil=(CardView) view.findViewById(R.id.infoDataBumil);
        menuinfopetabumil=(CardView)view.findViewById(R.id.infoDataPeta);
        totalIbuhml=(TextView) view.findViewById(R.id.totalIbuhml);
        totalBumilPeta=(TextView) view.findViewById(R.id.totalBumilPeta);

        menuinfodatabumil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menulaporbumil = new Intent (getActivity(), ListIbuhmlPetaActivity.class);
                startActivity(menulaporbumil);
            }
        });

        menuinfopetabumil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menuinfobumil = new Intent (getActivity(), ListIbuhmlPetagizkiaActivity.class);
                startActivity(menuinfobumil);
            }
        });
        return view;
    }
    public void getData(){
        String url;
        String jenisPelapor = getActivity().getSharedPreferences("DATA", Context.MODE_PRIVATE).getString("Jenis_pelapor","");
        String Kode_Desa = getActivity().getSharedPreferences("DATA", Context.MODE_PRIVATE).getString("Desa","");
        String Nip = getActivity().getSharedPreferences("DATA", Context.MODE_PRIVATE).getString("Nip","");
        url = "http://gizikia.dinkes.surakarta.go.id/srikandi_php_file/detail_total_pemetaanbumil.php?Kode_Desa="+Kode_Desa+"&Nip="+Nip;

        Log.d("DEBUGS", url);

        JsonArrayRequest jsonObjReq = new JsonArrayRequest(Request.Method.GET,
                url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("DEBUGS", response.toString());
                try {
                    totalIbuhml.setText(response.getJSONObject(0).getString("jumlah_ibu_hamil"));
                    totalBumilPeta.setText(response.getJSONObject(1).getString("jumlah_bumil_peta"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("DEBUGS", "Error: " + error.getMessage());
            }
        }) {
            @Override
            protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
                try {
                    Cache.Entry cacheEntry = HttpHeaderParser.parseCacheHeaders(response);
                    if (cacheEntry == null) {
                        cacheEntry = new Cache.Entry();
                    }
                    final long cacheHitButRefreshed = 0 * 60 * 1000; // in 3 minutes cache will be hit, but also refreshed on background
                    final long cacheExpired = 24 * 60 * 60 * 1000; // in 24 hours this cache entry expires completely
                    long now = System.currentTimeMillis();
                    final long softExpire = now + cacheHitButRefreshed;
                    final long ttl = now + cacheExpired;
                    cacheEntry.data = response.data;
                    cacheEntry.softTtl = softExpire;
                    cacheEntry.ttl = ttl;
                    String headerValue;
                    headerValue = response.headers.get("Date");
                    if (headerValue != null) {
                        cacheEntry.serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue);
                    }
                    headerValue = response.headers.get("Last-Modified");
                    if (headerValue != null) {
                        cacheEntry.lastModified = HttpHeaderParser.parseDateAsEpoch(headerValue);
                    }
                    cacheEntry.responseHeaders = response.headers;
                    final String jsonString = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers));
                    return Response.success(new JSONArray(jsonString), cacheEntry);
                } catch (UnsupportedEncodingException | JSONException e) {
                    return Response.error(new ParseError(e));
                }
            }

            @Override
            protected void deliverResponse(JSONArray response) {
                super.deliverResponse(response);
            }

            @Override
            public void deliverError(VolleyError error) {
                super.deliverError(error);
            }

            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                return super.parseNetworkError(volleyError);
            }
        };

// Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

}
