package com.simpus.srikandi.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.simpus.srikandi.activity.adapters.SpinnerAdapter;
import com.simpus.srikandi.activity.adapters.SpinnerAdapter2;
import com.simpus.srikandi.activity.objects.SpinnerItem;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.simpus.srikandi.R;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class TmbhIbuHmlActivity extends AppCompatActivity {
    EditText namaIbuhmlView,verifikasiDataView, namaSuamiView, alamatIbuhmlView, noTelpView;
    String Tgl_Informasi,Nama,Suami, latitude, longitude, noTelp;
    String Alamat,lokasiLat,lokasiLong;
    String User_id_pelapor, Verifikasi;
    SpinnerItem Kode_kecamatan, Kode_Desa;
    Button Btntmbhdata;
    Spinner kecamatanView, desaView;
    private GoogleMap mMap;
    //private String latitude, longitude, Tgl_Informasi;
    ArrayList<SpinnerItem> listKecamatan, listDesa;
    private SpinnerAdapter2 adapter, adapterDesa, adapterKecamatan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tmbh_ibu_hml);

        //menginisialisasi editText dan Button
        namaIbuhmlView = (EditText)findViewById(R.id.tmbhIbuhmlView);
        alamatIbuhmlView = (EditText)findViewById(R.id.tmbhAlamatView);
        namaSuamiView = (EditText)findViewById(R.id.namaSuamiView);;
        kecamatanView = (Spinner)findViewById(R.id.tmbhKecamatanView);
        desaView = (Spinner)findViewById(R.id.tmbhDesaView);
        noTelpView=(EditText) findViewById(R.id.noTelpView);
        Btntmbhdata= (Button) findViewById(R.id.Btntmbhdata);

       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        //memanggil method kecamatan
        getKecamatan();

        //menambahkan fungsi maps
        //SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                //.findFragmentById(R.id.mapTmbhIbuHamil);


        //membuat gps tracker
        GPSTracker gpsTracker = new GPSTracker(this);

        if (gpsTracker.getIsGPSTrackingEnabled())
        {
            latitude = String.valueOf(gpsTracker.latitude);
            longitude = String.valueOf(gpsTracker.longitude);

        }
        else
        {
            gpsTracker.showSettingsAlert();
        }


        // cara pemanggilannya seperti ini
       //Tgl_Informasi = ((SpinnerItem) kecamatanView.getSelectedItem()).getId().toString();
        //User_id_pelapor = getSharedPreferences("DATA", Context.MODE_PRIVATE).getString("User_id_pelapor","");



        //menambahkan acton jika button di klik
        Btntmbhdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //lihat method public void post
                //Tgl_Informasi = getCurrentDate().toString();
                Tgl_Informasi = getCurrentDate().toString();
                Nama = namaIbuhmlView.getText().toString();
                Suami = namaSuamiView.getText().toString();
                Alamat = alamatIbuhmlView.getText().toString();
                noTelp = noTelpView.getText().toString();
                lokasiLat = latitude;
                lokasiLong = longitude;
                User_id_pelapor = getSharedPreferences("DATA", Context.MODE_PRIVATE).getString("User_id_pelapor","");
                Verifikasi = "B";
                postDataIbu();
                //Toast.makeText(getBaseContext(),Tgl_Informasi,Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public String getCurrentDate(){
        final Calendar c = Calendar.getInstance();
        int year, month, day;
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DATE);
        return year + "-" + (month+1) + "-" + day;
    }

    public void postDataIbu(){
        //nama variable yang dibutuhkan
//        final String Tgl_Informasi,Nama,Suami;
//        final String Alamat,lokasiLat,lokasiLong;
//        final String User_id_pelapor, Verifikasi;
//        final SpinnerItem Kode_kecamatan, Kode_Desa;

        //perhatikan method.POST dan alamat webservice
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://103.71.255.66/wsKIA/info_awal_bumil_create.php",
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
                                progressDialog.dismiss();
                                Toast.makeText(TmbhIbuHmlActivity.this,object.getString("message"),Toast.LENGTH_LONG).show();
                                finish();
                            }else {
                                //menampilkan pesan gagal
                                progressDialog.dismiss();
                                Toast.makeText(TmbhIbuHmlActivity.this, object.getString("message"), Toast.LENGTH_LONG).show();
                            }
                        }catch (Exception e){
                            Log.d("CREATE", e.toString());
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Log.d("CREATE", error.toString());
                        progressDialog.dismiss();
                        Toast.makeText(TmbhIbuHmlActivity.this,"Sedang Mengambil Data, Pastikan Perangkat Sudah Tersambung Internet",Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                //menambahkan parameter post, nama put sama dengan nama variable pada webservice PHP

                params.put("Tgl_Informasi", Tgl_Informasi);
                params.put("Nama", Nama);
                params.put("Suami", Suami);
                params.put("Alamat", Alamat);
                params.put("Kode_Kecamatan", ((SpinnerItem) kecamatanView.getSelectedItem()).getId().toString());
                params.put("Kode_Desa", ((SpinnerItem) desaView.getSelectedItem()).getId().toString());
               // params.put("Kode_kecamatan", "Kecamatan");
                //params.put("Kode_Desa", "Desa");
                params.put("User_id_pelapor", User_id_pelapor);
                params.put("Verifikasi", Verifikasi);
                params.put("Lat", lokasiLat);
                params.put("Lng", lokasiLong);
                params.put("No_Telp", noTelp);

                return params;
            }
        };
        //mengecek eror handling field
        boolean check_nama = true;
        boolean check_alamat = true;
        boolean check_kecamatan = true;
        boolean check_desa = true;
        boolean check_namasuami = true;
        boolean check_notelp = true;

        if(namaIbuhmlView.getText().toString().equals("")){
            check_nama = false;
        }else if(alamatIbuhmlView.getText().toString().equals("")){
            check_alamat = false;
        }else if(((SpinnerItem) kecamatanView.getSelectedItem()).getId().toString().equals("")){
            check_kecamatan = false;
        }else if(desaView.getSelectedItem()==null){
            check_desa = false;
        }else if(namaSuamiView.getText().toString().equals("")) {
            check_namasuami = false;
        }else if(noTelpView.getText().toString().equals("")) {
            check_notelp = false;
        }

        //mengeluarkan pesan handling
        if(!check_nama){
            progressDialog.dismiss();
            Toast.makeText(TmbhIbuHmlActivity.this,"Data Nama Belum Terisi!!",Toast.LENGTH_LONG).show();
        }else if(!check_alamat){
            progressDialog.dismiss();
            Toast.makeText(TmbhIbuHmlActivity.this,"Data Alamat Belum Terisi!!",Toast.LENGTH_LONG).show();
        }else if(!check_kecamatan){
            progressDialog.dismiss();
            Toast.makeText(TmbhIbuHmlActivity.this,"Data Kecamatan Belum Terisi!!",Toast.LENGTH_LONG).show();
        }else if(!check_desa){
            progressDialog.dismiss();
            Toast.makeText(TmbhIbuHmlActivity.this,"Data Desa Belum Terisi!!",Toast.LENGTH_LONG).show();
        }else if(!check_namasuami){
            progressDialog.dismiss();
            Toast.makeText(TmbhIbuHmlActivity.this,"Data Nama Suami Belum Terisi!!",Toast.LENGTH_LONG).show();
        }else if(!check_notelp){
            progressDialog.dismiss();
            Toast.makeText(TmbhIbuHmlActivity.this,"Data Nomor Telepon Belum Terisi!!",Toast.LENGTH_LONG).show();
        }

        if(check_nama && check_namasuami && check_alamat && check_kecamatan && check_desa && check_notelp){
            AppController.getInstance().addToRequestQueue(stringRequest);
        }
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
                    adapterKecamatan = new SpinnerAdapter2(TmbhIbuHmlActivity.this,
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
                            adapterDesa = new SpinnerAdapter2(TmbhIbuHmlActivity.this,
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
                        Toast.makeText(TmbhIbuHmlActivity.this,"Sedang Mengambil Data, Pastikan Perangkat Sudah Tersambung Internet",Toast.LENGTH_LONG).show();
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