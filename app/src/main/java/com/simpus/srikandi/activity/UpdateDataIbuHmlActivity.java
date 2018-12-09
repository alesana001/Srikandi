package com.simpus.srikandi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.simpus.srikandi.R;
import com.simpus.srikandi.activity.adapters.SpinnerAdapter2;
import com.simpus.srikandi.activity.objects.SpinnerItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alesana on 16/03/2018.
 */

public class UpdateDataIbuHmlActivity extends AppCompatActivity {
    private String No_Index,Lat,Lng,Tgl_Informasi,User_id_pelapor,Verifikasi;
    EditText editNamaIbuhmlView, editAlamatIbuhmlView, editNoTelpView;
    EditText  editNamaSuamiView;
    ArrayList<SpinnerItem> listKecamatan, listDesa;
    Button BtnUpdate;
    Spinner editKecamatanView, editDesaView;
    String Kode_Kecamatan, Kode_Desa;
    private SpinnerAdapter2 adapter, adapterDesa, adapterKecamatan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_ibu_hml);

         //mereferensikan komponen pada layout
        editNamaIbuhmlView = (EditText)findViewById(R.id.editNamaIbuhmlView);
        editAlamatIbuhmlView = (EditText)findViewById(R.id.editAlamatIbuhmlView);
        editKecamatanView = (Spinner)findViewById(R.id.editKecamatanView);
        editNamaSuamiView = (EditText)findViewById(R.id.editNamaSuamiView);
        editDesaView = (Spinner)findViewById(R.id.editDesaView);
        editNoTelpView = (EditText) findViewById(R.id.editNoTelpView);
        BtnUpdate = (Button) findViewById(R.id.BtnUpdate);

        //mengambil intent dari intent sebelumnya
        Bundle extras = getIntent().getExtras();

        No_Index = extras.getString("No_Index");
        Tgl_Informasi = extras.getString("Tgl_Informasi");
        String Nama = extras.getString("Nama");
        String Suami = extras.getString("Suami");
        String Alamat = extras.getString("Alamat");
        Kode_Kecamatan = extras.getString("Kode_Kecamatan");
        Kode_Desa = extras.getString("Kode_Desa");
        User_id_pelapor = extras.getString("User_id_pelapor");
        Verifikasi = extras.getString("Verifikasi");
        Lat = extras.getString("Lat");
        Lng = extras.getString("Lng");
        String No_Telp =  extras.getString("No_Telp");



        //menampilkan data yang diambil melalui Textview

        editNamaIbuhmlView.setText(Nama);
        editAlamatIbuhmlView.setText(Alamat);
        editNamaSuamiView.setText(Suami);
        editNoTelpView.setText(No_Telp);
        getKecamatan();


        //menambahkan acton jika button di klik
        BtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //lihat method public void post
                postEditDataIbu();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    public void postEditDataIbu(){
        //nama variable yang dibutuhkan
        final String Nama, alamatIbuHml,Kode_Kecamatan,kodeDesa,namaSuami,noTelp;


        No_Index = No_Index;
        Nama = editNamaIbuhmlView.getText().toString();
        alamatIbuHml = editAlamatIbuhmlView.getText().toString();
        Kode_Kecamatan =  ((SpinnerItem) editKecamatanView.getSelectedItem()).getId().toString();
        kodeDesa = ((SpinnerItem) editDesaView.getSelectedItem()).getId().toString();
        namaSuami = editNamaSuamiView.getText().toString();
        noTelp = editNoTelpView.getText().toString();

        //perhatikan method.POST dan alamat webservice
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://103.71.255.66/wsKIA/info_awal_bumil_update.php",
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        Log.d("DEBUGS", response);
                        try{
                            //mengubah string menjadi jsonObject
                            JSONObject object = new JSONObject(response);
                            //mendapatkan string dari status
                            String status = object.getString("response");
                            //jika berhasil ditambahkan
                            if(status.equalsIgnoreCase("success")){
                                //menampilkan pesan berhasil
                                Intent intent = new Intent(UpdateDataIbuHmlActivity.this,DetailIbuHmlActivity.class);
                                intent.putExtra("No_Index", No_Index);
                                intent.putExtra("Tgl_Informasi", Tgl_Informasi);
                                intent.putExtra("Nama", Nama);
                                intent.putExtra("Suami", namaSuami);
                                intent.putExtra("Alamat", alamatIbuHml);
                                intent.putExtra("Kode_Kecamatan",Kode_Kecamatan);
                                intent.putExtra("Kode_Desa",kodeDesa);
                                intent.putExtra("User_id_pelapor", User_id_pelapor);
                                intent.putExtra("Verifikasi", Verifikasi);
                                intent.putExtra("Lat", Lat);
                                intent.putExtra("Lng", Lng);
                                intent.putExtra("No_Telp", noTelp);
                                startActivity(intent);
                                Toast.makeText(UpdateDataIbuHmlActivity.this, object.getString("message"), Toast.LENGTH_LONG).show();
                                finish();
                            }else {
                                //menampilkan pesan gagal
                                Toast.makeText(UpdateDataIbuHmlActivity.this, object.getString("message"), Toast.LENGTH_LONG).show();
                            }
                        }catch (Exception e){
                            Log.d("DEBUGS", e.toString());
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Log.d("DEBUGS", error.toString());
                        Toast.makeText(UpdateDataIbuHmlActivity.this,"Sedang Mengambil Data, Pastikan Perangkat Sudah Tersambung Internet",Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                //menambahkan parameter post, nama put sama dengan nama variable pada webservice PHP
                params.put("No_Index", No_Index);
                params.put("Nama", Nama);
                params.put("Suami", namaSuami);
                params.put("Alamat", alamatIbuHml);
                params.put("Kode_Kecamatan",  ((SpinnerItem) editKecamatanView.getSelectedItem()).getId().toString());
                params.put("Kode_Desa", ((SpinnerItem) editDesaView.getSelectedItem()).getId().toString());
                params.put("No_Telp", noTelp);

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }
    public void getKecamatan(){
        final JsonArrayRequest jsonObjReq = new JsonArrayRequest(Request.Method.GET,
                "http://103.71.255.66/wsKIA/list_kecamatan.php", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("DEBUGS", response.toString());
                try {
                    listKecamatan = new ArrayList<>();
                    for(int i=0;i<response.length();i++){
                        JSONObject object = response.getJSONObject(i);
                        SpinnerItem item = new SpinnerItem(object.getString("Kode_Kecamatan"),object.getString("Kecamatan"));
                        listKecamatan.add(item);
                    }
                    adapterKecamatan = new SpinnerAdapter2(UpdateDataIbuHmlActivity.this,
                            android.R.layout.simple_spinner_item,
                            listKecamatan);

                    editKecamatanView.setAdapter(adapterKecamatan); // Set the custom adapter to the spinner
                    // You can create an anonymous listener to handle the event when is selected an spinner item
                    editKecamatanView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view,
                                                   int position, long id) {
                            // Here you get the current item (a User object) that is selected by its position
                            SpinnerItem item = adapterKecamatan.getItem(position);
                            // Here you can do the action you want to...

                            Log.d("DEBUGS ITEM ", item.getId());
                            getDesa(item.getId());
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterKecamatan) {  }
                    });
                    int i =0;
                    for(SpinnerItem e:listKecamatan){
                        if(e.getId().equals(Kode_Kecamatan)){
                            editKecamatanView.setSelection(i);
                        }
                        i++;
                    }
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
    public void getDesa(String kodeKecamatan){
        final String id_kecamatan = kodeKecamatan;
        Log.d("DEBUGS id", id_kecamatan);

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://103.71.255.66/wsKIA//list_desa.php",
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        Log.d("CREATE", response);
                        try {
                            listDesa = new ArrayList<>();
                            JSONArray data = new JSONArray(response);
                            for(int i=0;i<data.length();i++){
                                JSONObject object = data.getJSONObject(i);
                                SpinnerItem item = new SpinnerItem(object.getString("KodeKab"),object.getString("Desa"));
                                listDesa.add(item);
                            }
                            adapterDesa = new SpinnerAdapter2(UpdateDataIbuHmlActivity.this,
                                    android.R.layout.simple_spinner_item,
                                    listDesa);

                            editDesaView.setAdapter(adapterDesa); // Set the custom adapter to the spinner
                            // You can create an anonymous listener to handle the event when is selected an spinner item
                            editDesaView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view,
                                                           int position, long id) {

                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> adapterDesa) {  }
                            });

                            int i =0;
                            for(SpinnerItem e:listDesa){
                                if(e.getId().equals(Kode_Desa)){
                                    editDesaView.setSelection(i);
                                }
                                i++;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Log.d("CREATE", error.toString());
                        Toast.makeText(UpdateDataIbuHmlActivity.this,"Sedang Mengambil Data, Pastikan Perangkat Sudah Tersambung Internet",Toast.LENGTH_LONG).show();
                    }
                }){



            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                //menambahkan parameter post, nama put sama dengan nama variable pada webservice PHP
                params.put("id_kecamatan", id_kecamatan);

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }
}
