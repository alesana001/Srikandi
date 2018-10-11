package com.simpus.satitiv2.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.simpus.satitiv2.R;
import com.simpus.satitiv2.activity.adapters.SpinnerAdapter;
import com.simpus.satitiv2.activity.objects.SpinnerItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    EditText user_idView, namaView, no_teleponView, passwordView,nipView;
    Spinner kecamatanView, desaView, jenisView;
    Button registerButton;
    ArrayList<SpinnerItem> listKecamatan, listDesa, listJenis;
    TextView loginView;
    private SpinnerAdapter adapter, adapterDesa, adapterKecamatan;
    boolean doubleBackToExitPressedOnce = false;
    LinearLayout NIpegawai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        user_idView = (EditText) findViewById(R.id.user_idView);
        nipView =(EditText) findViewById(R.id.NIPView);
        namaView = (EditText) findViewById(R.id.namaView);
        passwordView = (EditText)findViewById(R.id.passwordView);
        no_teleponView = (EditText) findViewById(R.id.teleponView);
        kecamatanView = (Spinner) findViewById(R.id.kecamatanView);
        desaView = (Spinner) findViewById(R.id.desaView);
        jenisView = (Spinner) findViewById(R.id.jenisView);

        NIpegawai=(LinearLayout) findViewById(R.id.NIpegawai);
        loginView = (TextView)findViewById(R.id.loginView);

        loginView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        registerButton = (Button) findViewById(R.id.registerButton);

        getKecamatan();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postRegister();
            }
        });

        setSpinnerJenis();
        jenisView.requestFocus();

    }

    public void setSpinnerJenis(){
        listJenis = new ArrayList<SpinnerItem>();
        listJenis.add(new SpinnerItem("B","Bidan"));
        listJenis.add(new SpinnerItem("NB","Non Bidan"));
        listJenis.add(new SpinnerItem("BK","Bidan Koordinator"));
        listJenis.add(new SpinnerItem("BM","Bidan Praktek Mandiri"));
        listJenis.add(new SpinnerItem("L","Lurah"));
        listJenis.add(new SpinnerItem("C","Camat"));
        listJenis.add(new SpinnerItem("RS","Rumah Sakit"));
        listJenis.add(new SpinnerItem("KL","Klinik"));
        listJenis.add(new SpinnerItem("LN","Lain-lain"));
        adapter = new SpinnerAdapter(RegisterActivity.this,
                android.R.layout.simple_spinner_item,
                listJenis);

        jenisView.setAdapter(adapter);

        jenisView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerItem item = adapter.getItem(position);
                if(item.getId().equals("B")||item.getId().equals("BK")||item.getId().equals("BM")){
                    NIpegawai.setVisibility(View.VISIBLE);
                    jenisView.requestFocus();
                }else{
                    NIpegawai.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void postRegister(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://103.71.255.66/wsKIA/create_ptgs.php",
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        Log.d("CREATE", response);
                        try{
                            //mengubah string menjadi jsonObject
                            JSONObject object = new JSONObject(response);
                            //mendapatkan string dari status
                            String status = object.getString("status");
                            //jika berhasil ditambahkan
                            if(status.equalsIgnoreCase("success")){
                                //menampilkan pesan berhasil
                                Toast.makeText(RegisterActivity.this,object.getString("message"),Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }else {
                                //menampilkan pesan gagal
                                Log.d("CREATE", status);
                                Toast.makeText(RegisterActivity.this, object.getString("message"), Toast.LENGTH_LONG).show();
                            }
                        }catch (Exception e){
                            Log.d("CREATE", e.toString());
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Log.d("CREATE", error.toString());
                        Toast.makeText(RegisterActivity.this,"Sedang Mengambil Data, Pastikan Perangkat Sudah Tersambung Internet",Toast.LENGTH_LONG).show();
                    }
                }){


            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                //menambahkan parameter post, nama put sama dengan nama variable pada webservice PHP
                params.put("User_id_pelapor", user_idView.getText().toString());
                params.put("Nip", nipView.getText().toString());
                params.put("Password", passwordView.getText().toString());
                params.put("Nama", namaView.getText().toString());
                params.put("No_telepon", no_teleponView.getText().toString());
                params.put("Kecamatan", ((SpinnerItem) kecamatanView.getSelectedItem()).getId().toString());
                params.put("Desa", ((SpinnerItem) desaView.getSelectedItem()).getId().toString());
                params.put("Jenis_pelapor", ((SpinnerItem) jenisView.getSelectedItem()).getId().toString());

                return params;
            }
        };
        //mengecek eror handling field
        boolean check_id = true;
        boolean check_password = true;
        boolean check_nama = true;
        boolean check_notlp = true;
        boolean check_kecamatan = true;
        boolean check_desa = true;
        boolean check_jenis = true;
        if(user_idView.getText().toString().equals("")){
            check_id = false;
        }else if(passwordView.getText().toString().equals("")){
            check_password = false;
        }else if(namaView.getText().toString().equals("")){
            check_nama = false;
        }else if(no_teleponView.getText().toString().equals("")){
            check_notlp = false;
        }else if(((SpinnerItem) kecamatanView.getSelectedItem()).getId().toString().equals("")){
            check_kecamatan = false;
        }else if(((SpinnerItem) desaView.getSelectedItem()).getId().toString().equals("")){
            check_desa = false;
        }else if(((SpinnerItem) jenisView.getSelectedItem()).getId().toString().equals("")) {
            check_jenis = false;
        }
        //mengeluarkan pesan handling
        if(!check_id){
            Toast.makeText(RegisterActivity.this,"Data NIK Belum Terisi!!",Toast.LENGTH_LONG).show();
        }else if(!check_password){
            Toast.makeText(RegisterActivity.this,"Data Password Belum Terisi!!",Toast.LENGTH_LONG).show();
        }else if(!check_nama){
            Toast.makeText(RegisterActivity.this,"Data Nama Belum Terisi!!",Toast.LENGTH_LONG).show();
        }else if(!check_notlp){
            Toast.makeText(RegisterActivity.this,"Data No HP Belum Terisi!!",Toast.LENGTH_LONG).show();
        }else if(!check_kecamatan){
            Toast.makeText(RegisterActivity.this,"Data Kecamatan Belum Terisi!!",Toast.LENGTH_LONG).show();
        }else if(!check_desa){
            Toast.makeText(RegisterActivity.this,"Data Desa Belum Terisi!!",Toast.LENGTH_LONG).show();
        }else if(!check_jenis){
            Toast.makeText(RegisterActivity.this,"Data Jenis Petugas Belum Terisi!!",Toast.LENGTH_LONG).show();
        }

        if(check_id && check_password && check_notlp && check_kecamatan && check_desa && check_jenis){
            AppController.getInstance().addToRequestQueue(stringRequest);
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Tekan tombol back sekali lagi untuk keluar", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
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
                    adapterKecamatan = new SpinnerAdapter(RegisterActivity.this,
                            android.R.layout.simple_spinner_item,
                            listKecamatan);

                    kecamatanView.setAdapter(adapterKecamatan); // Set the custom adapter to the spinner
                    // You can create an anonymous listener to handle the event when is selected an spinner item
                    kecamatanView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

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
                "http://103.71.255.66/wsKIA/list_desa.php",
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
                            adapterDesa = new SpinnerAdapter(RegisterActivity.this,
                                    android.R.layout.simple_spinner_item,
                                    listDesa);

                            desaView.setAdapter(adapterDesa); // Set the custom adapter to the spinner
                            // You can create an anonymous listener to handle the event when is selected an spinner item
                            desaView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view,
                                                           int position, long id) {
                                    // Here you get the current item (a User object) that is selected by its position
                                    SpinnerItem user = adapterDesa.getItem(position);
                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> adapterDesa) {  }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Log.d("CREATE", error.toString());
                        Toast.makeText(RegisterActivity.this,"Sedang Mengambil Data, Pastikan Perangkat Sudah Tersambung Internet",Toast.LENGTH_LONG).show();
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
