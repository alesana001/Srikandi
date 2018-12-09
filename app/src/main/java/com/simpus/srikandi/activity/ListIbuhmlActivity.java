package com.simpus.srikandi.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import com.simpus.srikandi.activity.adapters.ListIbuhmlAdapter;
import com.simpus.srikandi.activity.objects.IbuHml;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by alesana on 01/04/2018.
 */

public class ListIbuhmlActivity extends AppCompatActivity{

    RecyclerView listIbuHml;
    ArrayList<IbuHml> data;
    ListIbuhmlAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_ibuhml);

        //mengambil data dari recyclerView
        listIbuHml = (RecyclerView) findViewById(R.id.listIbuHmlView);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        getData();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        getData();
    }

    public void getData(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sedang Mengambil Data...");
        progressDialog.show();
        String url;
        String Desa =getSharedPreferences("DATA", Context.MODE_PRIVATE).getString("Desa","");
        String Jenis =getSharedPreferences("DATA", Context.MODE_PRIVATE).getString("Jenis_pelapor","");
        if(Jenis.equals("BK")||Jenis.equals("B")||Jenis.equals("BM")||Jenis.equals("L")){
            url = "http://103.71.255.66/wsKIA/info_awal_bumil_list_bidan.php?Kode_Desa="+Desa;
        }else{
            String User_id_pelapor = getSharedPreferences("DATA", Context.MODE_PRIVATE).getString("User_id_pelapor","");
            url = "http://103.71.255.66/wsKIA/info_awal_bumil_list_userbiasa.php?User_id_pelapor="+User_id_pelapor;
        }

        JsonArrayRequest jsonObjReq = new JsonArrayRequest(Request.Method.GET,
                url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("DEBUGS", response.toString());
                try {
                    data = new ArrayList<IbuHml>();
                    for (int i=0; i<response.length();i++){
                        JSONObject object = response.getJSONObject(i);
                        String No_Index =object.getString("No_Index");
                        String Tgl_Informasi = object.getString("Tgl_Informasi");
                        String Nama = object.getString("Nama");
                        String Suami = object.getString("Suami");
                        String Alamat = object.getString("Alamat");
                        String Kode_Kecamatan = object.getString("Kode_Kecamatan");
                        String Kode_Desa = object.getString("Kode_Desa");
                        String User_id_pelapor = object.getString("User_id_pelapor");
                        String Verifikasi = object.getString("Verifikasi");
                        String Lat = object.getString("Lat");
                        String Lng = object.getString("Lng");
                        String No_Telp = object.getString("No_Telp");

                        data.add(new IbuHml(No_Index,Tgl_Informasi, Nama, Suami, Alamat, Kode_Kecamatan, Kode_Desa, User_id_pelapor, Verifikasi, Lat, Lng, No_Telp));


                        //menambah data ke recycleview
                        LinearLayoutManager layoutManager= new
                                LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,
                                false);
                        listIbuHml.setHasFixedSize(true);
                        listIbuHml.setLayoutManager(layoutManager);
                        adapter = new
                                ListIbuhmlAdapter(ListIbuhmlActivity.this, data);
                        adapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                        listIbuHml.setAdapter(adapter);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("DEBUGS", "Error: " + error.getMessage());
                progressDialog.dismiss();
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                return true;
            }
        });
        return true;
    }
}
