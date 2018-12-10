package com.simpus.srikandi.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

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
import com.simpus.srikandi.activity.adapters.ListIbuhmlAdapter;
import com.simpus.srikandi.activity.adapters.ListIbuhmlPetaAdapter;
import com.simpus.srikandi.activity.objects.BumilblmPeta;
import com.simpus.srikandi.activity.objects.IbuHml;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class ListIbuhmlPetaActivity extends AppCompatActivity {


        RecyclerView listIbuHmlPeta;
        ArrayList<BumilblmPeta> data;
        ListIbuhmlPetaAdapter adapter;


        @Override
        protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_list_ibuhml_peta);

            //mengambil data dari recyclerView
            listIbuHmlPeta = (RecyclerView) findViewById(R.id.listIbuHmlPetaView);

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
                url = "http://gizikia.dinkes.surakarta.go.id/srikandi_php_file/list_Ibu_hamil_peta.php?Kode_Desa="+Desa;
            }else{
                String User_id_pelapor = getSharedPreferences("DATA", Context.MODE_PRIVATE).getString("User_id_pelapor","");
                url = "http://gizikia.dinkes.surakarta.go.id/srikandi_php_file/info_awal_bumil_list_userbiasa.php?User_id_pelapor="+User_id_pelapor;
            }

            JsonArrayRequest jsonObjReq = new JsonArrayRequest(Request.Method.GET,
                    url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    Log.d("DEBUGS", response.toString());
                    try {
                        data = new ArrayList<BumilblmPeta>();
                        for (int i=0; i<response.length();i++){
                            JSONObject object = response.getJSONObject(i);
                            String No_Index_Bumil_Dinas =object.getString("No_Index_Bumil_Dinas");
                            String Nama = object.getString("Nama");
                            String Suami = object.getString("Suami");
                            String Tgl_Lahir = object.getString("Tgl_Lahir");
                            String HTP = object.getString("HTP");
                            String Kode_Desa = object.getString("Kode_Desa");

                            data.add(new BumilblmPeta(No_Index_Bumil_Dinas, Nama, Suami, Tgl_Lahir, HTP,Kode_Desa));

                            //menambah data ke recycleview
                            LinearLayoutManager layoutManager= new
                                    LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,
                                    false);
                            listIbuHmlPeta.setHasFixedSize(true);
                            listIbuHmlPeta.setLayoutManager(layoutManager);
                            adapter = new
                                    ListIbuhmlPetaAdapter(com.simpus.srikandi.activity.ListIbuhmlPetaActivity.this, data);
                            adapter.notifyDataSetChanged();
                            progressDialog.dismiss();
                            listIbuHmlPeta.setAdapter(adapter);


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