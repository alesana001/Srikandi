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
import com.simpus.srikandi.activity.GrafikBumilActivity;
import com.simpus.srikandi.activity.ListIbuhmlActivity;
import com.simpus.srikandi.activity.ListIbuhmlgizkiaActivity;
import com.simpus.srikandi.activity.MainActivity;
import com.simpus.srikandi.activity.TmbhIbuHmlActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;


public class OneFragment extends Fragment{

    CardView menulaporbumil, menuinfobumil, menubumilverif; //menuinfografik
    TextView totalIbuhml,totalBumilVerif;
    public OneFragment() {
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
        View view = inflater.inflate(R.layout.fragment_one, container, false);
       //mendefinisikan cardview ketika di klik
        menulaporbumil=(CardView) view.findViewById(R.id.laporDataBumil);
        menuinfobumil=(CardView)view.findViewById(R.id.infoDataTerlapor);
        menubumilverif = (CardView)view.findViewById(R.id.infoBumilTerverifikasi);
        //menuinfografik =(CardView)view.findViewById(R.id.infoGrafikBumil);
        totalIbuhml=(TextView) view.findViewById(R.id.totalIbuhml);
        totalBumilVerif=(TextView) view.findViewById(R.id.totalBumilVerif);

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

        menubumilverif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menubumilverif = new Intent(getActivity(), ListIbuhmlgizkiaActivity.class);
                startActivity(menubumilverif);
            }
        });

        /*menuinfografik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menuinfografik = new Intent(getActivity(), GrafikBumilActivity.class);
                startActivity(menuinfografik);
            }
        });
*/

        return view;
    }
    public void getData(){
        String url;
        String jenisPelapor = getActivity().getSharedPreferences("DATA", Context.MODE_PRIVATE).getString("Jenis_pelapor","");
        String Kode_Desa = getActivity().getSharedPreferences("DATA", Context.MODE_PRIVATE).getString("Desa","");
        if(jenisPelapor.equals("BK")||jenisPelapor.equals("B")||jenisPelapor.equals("BM")){
            url = "http://103.71.255.66/wsKIA/detail_total_pelaporan_bumil.php?Jenis_pelapor="+jenisPelapor+"&Kode_Desa="+Kode_Desa;
        }else{

            String User_id_pelapor = getActivity().getSharedPreferences("DATA", Context.MODE_PRIVATE).getString("User_id_pelapor","");
            url = "http://103.71.255.66/wsKIA/detail_total_pelaporan_bumil.php?Jenis_pelapor="+jenisPelapor+"&User_id_pelapor="+User_id_pelapor;
        }
        Log.d("DEBUGS", url);

        JsonArrayRequest jsonObjReq = new JsonArrayRequest(Request.Method.GET,
                url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("DEBUGS", response.toString());
                try {
                    totalIbuhml.setText(response.getJSONObject(0).getString("jumlah_ibu_hamil"));
                    totalBumilVerif.setText(response.getJSONObject(1).getString("jumlah_bumil_verif"));
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