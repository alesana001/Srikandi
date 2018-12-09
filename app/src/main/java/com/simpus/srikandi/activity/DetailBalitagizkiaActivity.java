package com.simpus.srikandi.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.simpus.srikandi.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetailBalitagizkiaActivity extends AppCompatActivity implements OnMapReadyCallback {

    //komponen pada layout detail bumil peta gizkia
    TextView namaBayiView, verifikasiDataView, nomorIndexBalitaView, tglLahirView, namaIbuView,namaAyahView, ketStuntingView, ketGiziView;
    private GoogleMap mMap;
    private String latitude, longitude, No_Index_Balita;
    Button btnDapatArahIbuHml;
    CheckBox checkboxVerifikasi;
    LinearLayout verifikasiData;
    /*//menambah action menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_ubah_ibuhamil,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menu_update:

                return true;
            case R.id.menu_uplokasi:
                showAlertDialogUplokasi();
                return true;
            case R.id.menu_delete:
                showAlertDialog();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void showAlertDialogVerif() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Apakah Anda Yakin?");
        alertDialogBuilder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        editverifikasi();
                    }
                });

        alertDialogBuilder.setNegativeButton("Tidak",new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                checkboxVerifikasi.setChecked(false);
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    private void editverifikasi() {
        //nama variable yang dibutuhkan
        final String dataVerifikasi;

        dataVerifikasi = "S";



        //perhatikan method.POST dan alamat webservice
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://103.71.255.66/wsKIA/info_awal_bumil_verifikasi_data.php",
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

                                Toast.makeText(DetailBalitagizkiaActivity.this, object.getString("message"), Toast.LENGTH_LONG).show();
                                finish();
                            }else {
                                //menampilkan pesan gagal

                                Toast.makeText(DetailBalitagizkiaActivity.this, object.getString("message"), Toast.LENGTH_LONG).show();
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
                        Toast.makeText(DetailBalitagizkiaActivity.this,"Sedang Mengambil Data, Harap Ulangi Membuka Halaman ini",Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                //menambahkan parameter post, nama put sama dengan nama variable pada webservice PHP
                params.put("No_Index_Balita_Dinas", No_Index_Balita_Dinas);
                params.put("Verifikasi", dataVerifikasi);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    private void showAlertDialogUplokasi() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Apakah Anda Yakin?");
        alertDialogBuilder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        editlokasidata();
                    }
                });

        alertDialogBuilder.setNegativeButton("Tidak",new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    private void editlokasidata() {
        //nama variable yang dibutuhkan
        final String lokasiLat,lokasiLong;

        lokasiLat = latitude;
        lokasiLong = longitude;


        //perhatikan method.POST dan alamat webservice
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://103.71.255.66/wsKIA/info_awal_bumil_update_lokasi.php",
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        Log.d("DEBUGS", response);
                        try{
                            //mengubah string menjadi jsonObject
                            JSONObject object = new JSONObject(response);
                            //mendapatkan string dari status
                            String status = object.getString("response");
                            Log.d("cek cok", lokasiLat);
                            //jika berhasil ditambahkan
                            if(status.equalsIgnoreCase("success")){

                                Toast.makeText(DetailBalitagizkiaActivity.this, object.getString("message"), Toast.LENGTH_LONG).show();
                                finish();
                            }else {
                                //menampilkan pesan gagal
                                System.out.print("hnh= "+lokasiLat+"kkkkk= "+lokasiLong);
                                Toast.makeText(DetailBalitagizkiaActivity.this, object.getString("message"), Toast.LENGTH_LONG).show();
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
                        Toast.makeText(DetailBalitagizkiaActivity.this,"Sedang Mengambil Data, Harap Ulangi Membuka Halaman ini",Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                //menambahkan parameter post, nama put sama dengan nama variable pada webservice PHP
                params.put("No_Index_Balita_Dinas", No_Index_Balita_Dinas);
                params.put("Lat", lokasiLat);
                params.put("lng", lokasiLong);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }


    private void showAlertDialog() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Apakah Anda yakin?");
        alertDialogBuilder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        deldata();
                    }
                });

        alertDialogBuilder.setNegativeButton("Tidak",new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void deldata() {
        //nama variable yang dibutuhkan

        //perhatikan method.POST dan alamat webservice
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://103.71.255.66/wsKIA/info_awal_bumil_delete.php",
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        Log.d("DELETE", response);
                        try{
                            //mengubah string menjadi jsonObject
                            JSONObject object = new JSONObject(response);
                            //mendapatkan string dari status
                            String status = object.getString("status");
                            //jika berhasil ditambahkan
                            if(status.equalsIgnoreCase("success")){
                                //menampilkan pesan berhasil
                                Toast.makeText(DetailBalitagizkiaActivity.this,object.getString("message"),Toast.LENGTH_LONG).show();
                                finish();
                            }else {
                                //menampilkan pesan gagal
                                Toast.makeText(DetailBalitagizkiaActivity.this, object.getString("message"), Toast.LENGTH_LONG).show();
                            }
                        }catch (Exception e){
                            Log.d("DELETE", e.toString());
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Log.d("DELETE", error.toString());
                        Toast.makeText(DetailBalitagizkiaActivity.this,"Sedang Mengambil Data, Harap Ulangi Membuka Halaman ini",Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                //menambahkan parameter post, nama put sama dengan nama variable pada webservice PHP
                params.put("No_Index_Balita_Dinas", No_Index_Balita_Dinas);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);

    }*/

    private String No_Index_Balita_Dinas, Nama_Bayi,Tanggal_Lahir,Nama_Ibu,Nama_Ayah,Ket_Stunting,Ket_Gibur,Nip,Lat,lng,Kode_Desa;
    @Override
    protected void onCreate(Bundle savedInstaceState){
        super.onCreate(savedInstaceState);
        setContentView(R.layout.activity_detail_balita_gizkia);


        //mereferensikan komponen pada layout
        namaBayiView = (TextView)findViewById(R.id.namaBayiView);
        verifikasiDataView = (TextView)findViewById(R.id.verifikasiDataView);
        nomorIndexBalitaView = (TextView)findViewById(R.id.nomorIndexBalitaView);
        tglLahirView = (TextView)findViewById(R.id.tglLahirView);
        namaIbuView = (TextView)findViewById(R.id.namaIbuView);
        namaAyahView = (TextView)findViewById(R.id.namaAyahView);
        btnDapatArahIbuHml = (Button) findViewById(R.id.btnDapatArahIbuHml);
        verifikasiData =(LinearLayout) findViewById(R.id.verifikasiData);
        checkboxVerifikasi=(CheckBox)  findViewById(R.id.checkboxVerifikasi);
        ketStuntingView = (TextView) findViewById(R.id.ketStuntingView);
        ketGiziView = (TextView) findViewById(R.id.ketGiziView);

        //mengambil intent dari intent sebelumnya
        Bundle extras = getIntent().getExtras();
        No_Index_Balita_Dinas = extras.getString("No_Index_Balita_Dinas");
        Nama_Bayi = extras.getString("Nama_Bayi");
        Tanggal_Lahir = extras.getString("Tanggal_Lahir");
        Nama_Ibu = extras.getString("Nama_Ibu");
        Nama_Ayah = extras.getString("Nama_Ayah");
        Lat = extras.getString("Lat");
        lng = extras.getString("lng");
        Ket_Stunting = extras.getString("Ket_Stunting");
        Ket_Gibur = extras.getString("Ket_Gibur");
        Nip = extras.getString("Nip");
        Kode_Desa = extras.getString("Kode_Desa");

        //menampilkan data yang diambil melalui Textview
        namaBayiView.setText(Nama_Bayi);

        String jenisPelapor = getSharedPreferences("DATA", Context.MODE_PRIVATE).getString("Jenis_pelapor","");
        if(jenisPelapor.equals("B")||jenisPelapor.equals("BK")||jenisPelapor.equals("BM")){
            checkboxVerifikasi.setEnabled(true);
        }else{
            checkboxVerifikasi.setEnabled(false);
        }

        verifikasiDataView.setText("Sudah Terpetakan");
        verifikasiDataView.setTextColor(Color.parseColor("#00B0FF"));
        checkboxVerifikasi.setChecked(true);
        checkboxVerifikasi.setEnabled(false);




       /* //handling checkbox
        checkboxVerifikasi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    showAlertDialogVerif();
                }
            }
        });*/

        nomorIndexBalitaView.setText(No_Index_Balita_Dinas);
        tglLahirView.setText(Tanggal_Lahir);
        namaIbuView.setText(Nama_Ibu);
        namaAyahView.setText(Nama_Ayah);
        ketStuntingView.setText(Ket_Stunting);
        ketGiziView.setText(Ket_Gibur);




        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //menambahkan fungsi maps
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.maptampilbalitaView);


        //membuat gps tracker
        GPSTracker gpsTracker = new GPSTracker(this);

        if (gpsTracker.getIsGPSTrackingEnabled())
        {
            latitude = String.valueOf(gpsTracker.latitude);
            longitude = String.valueOf(gpsTracker.longitude);
            mapFragment.getMapAsync(this);
        }
        else
        {
            gpsTracker.showSettingsAlert();
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }




    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
        Log.d("lat",latitude);
        LatLng indonesia = new LatLng(Double.parseDouble(Lat),Double.parseDouble(lng));
        mMap.addMarker(new MarkerOptions().position(indonesia).title("Lokasi Ibu Hamil"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(indonesia,15F));
        btnDapatArahIbuHml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://maps.google.com/maps?saddr="+latitude+","+longitude+"&daddr="+Lat.toString()+","+lng.toString();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
            }
        });

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
