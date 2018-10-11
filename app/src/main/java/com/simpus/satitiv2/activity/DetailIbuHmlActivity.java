package com.simpus.satitiv2.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import  com.simpus.satitiv2.activity.adapters.SpinnerAdapter;
import  com.simpus.satitiv2.activity.objects.SpinnerItem;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.simpus.satitiv2.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.simpus.satitiv2.activity.adapters.ListIbuhmlAdapter;
import com.simpus.satitiv2.activity.objects.IbuHml;

import static  com.simpus.satitiv2.activity.adapters.ListIbuhmlAdapter.data;

/**
 * Created by alesana on 05/03/2018.
 */

public class DetailIbuHmlActivity extends AppCompatActivity implements OnMapReadyCallback {
    //komponen pada layout detailbayi
    TextView namaIbuhmlView,verifikasiDataView, namaSuamiView, alamatIbuhmlView, kecamatanView,desaView, noTelpView;
    private GoogleMap mMap;
    private String latitude, longitude, No_Index;
    Button btnDapatArahIbuHml;
    CheckBox checkboxVerifikasi;
    LinearLayout verifikasiData;

    //menambah action menu
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
                getData();
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

                                Toast.makeText(DetailIbuHmlActivity.this, object.getString("message"), Toast.LENGTH_LONG).show();
                                finish();
                            }else {
                                //menampilkan pesan gagal

                                Toast.makeText(DetailIbuHmlActivity.this, object.getString("message"), Toast.LENGTH_LONG).show();
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
                        Toast.makeText(DetailIbuHmlActivity.this,"Sedang Mengambil Data, Harap Ulangi Membuka Halaman ini",Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                //menambahkan parameter post, nama put sama dengan nama variable pada webservice PHP
                params.put("No_Index", No_Index);
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

                                Toast.makeText(DetailIbuHmlActivity.this, object.getString("message"), Toast.LENGTH_LONG).show();
                                finish();
                            }else {
                                //menampilkan pesan gagal
                                System.out.print("hnh= "+lokasiLat+"kkkkk= "+lokasiLong);
                                Toast.makeText(DetailIbuHmlActivity.this, object.getString("message"), Toast.LENGTH_LONG).show();
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
                        Toast.makeText(DetailIbuHmlActivity.this,"Sedang Mengambil Data, Harap Ulangi Membuka Halaman ini",Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                //menambahkan parameter post, nama put sama dengan nama variable pada webservice PHP
                params.put("No_Index", No_Index);
                params.put("Lat", lokasiLat);
                params.put("Lng", lokasiLong);
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
                                Toast.makeText(DetailIbuHmlActivity.this,object.getString("message"),Toast.LENGTH_LONG).show();
                                finish();
                            }else {
                                //menampilkan pesan gagal
                                Toast.makeText(DetailIbuHmlActivity.this, object.getString("message"), Toast.LENGTH_LONG).show();
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
                        Toast.makeText(DetailIbuHmlActivity.this,"Sedang Mengambil Data, Harap Ulangi Membuka Halaman ini",Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                //menambahkan parameter post, nama put sama dengan nama variable pada webservice PHP
                params.put("No_Index", No_Index);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);

    }

    private String Tgl_Informasi,Nama,Suami,Alamat,Kode_Kecamatan,Kode_Desa,User_id_pelapor,Verifikasi,Lat,Lng,No_Telp;
    @Override
    protected void onCreate(Bundle savedInstaceState){
        super.onCreate(savedInstaceState);
        setContentView(R.layout.activity_detail_ibuhml);


        //mereferensikan komponen pada layout
        namaIbuhmlView = (TextView)findViewById(R.id.namaIbuhmlView);
        verifikasiDataView = (TextView)findViewById(R.id.verifikasiDataView);
        namaSuamiView = (TextView)findViewById(R.id.namaSuamiView);
        alamatIbuhmlView = (TextView)findViewById(R.id.alamatIbuhmlView);
        kecamatanView = (TextView)findViewById(R.id.kecamatanView);
        desaView = (TextView)findViewById(R.id.desaView);
        btnDapatArahIbuHml = (Button) findViewById(R.id.btnDapatArahIbuHml);
        verifikasiData =(LinearLayout) findViewById(R.id.verifikasiData);
        checkboxVerifikasi=(CheckBox)  findViewById(R.id.checkboxVerifikasi);
        noTelpView = (TextView) findViewById(R.id.noTelpView);

        //mengambil intent dari intent sebelumnya
        Bundle extras = getIntent().getExtras();
        No_Index = extras.getString("No_Index");
        Tgl_Informasi = extras.getString("Tgl_Informasi");
        Nama = extras.getString("Nama");
        Suami = extras.getString("Suami");
        Alamat = extras.getString("Alamat");
        Kode_Kecamatan = extras.getString("Kode_Kecamatan");
        Kode_Desa = extras.getString("Kode_Desa");
        User_id_pelapor = extras.getString("User_id_pelapor");
        Verifikasi = extras.getString("Verifikasi");
        Lat = extras.getString("Lat");
        Lng = extras.getString("Lng");
        No_Telp = extras.getString("No_Telp");

        //menampilkan data yang diambil melalui Textview
        namaIbuhmlView.setText(Nama);

        String jenisPelapor = getSharedPreferences("DATA", Context.MODE_PRIVATE).getString("Jenis_pelapor","");
        if(jenisPelapor.equals("B")||jenisPelapor.equals("BK")||jenisPelapor.equals("BM")){
            checkboxVerifikasi.setEnabled(true);
        }else{
            checkboxVerifikasi.setEnabled(false);
        }

        if(Verifikasi.equals("S")){
            verifikasiDataView.setText("Sudah Terverifikasi");
            verifikasiDataView.setTextColor(Color.parseColor("#00B0FF"));
            checkboxVerifikasi.setChecked(true);
            checkboxVerifikasi.setEnabled(false);
        }else{
            verifikasiDataView.setText("Belum Terverifikasi");
            verifikasiDataView.setTextColor(Color.parseColor("#F44336"));
        }



        //handling checkbox
        checkboxVerifikasi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    showAlertDialogVerif();
                }
            }
        });

        namaSuamiView.setText(Suami);
        alamatIbuhmlView.setText(Alamat);
        noTelpView.setText(No_Telp);


        getKecamatan(Kode_Kecamatan);
        getDesa(Kode_Desa);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //menambahkan fungsi maps
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.maptampilibuhamilView);


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
    public void getKecamatan(final String KodeKecamatan){
        final String id_kecamatan = KodeKecamatan;
        Log.d("TEST KECAMATAN", Kode_Kecamatan);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://103.71.255.66/wsKIA/list_detail_kecamatan.php",
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        Log.d("CREATE", response);
                        try {
                            JSONArray data = new JSONArray(response);
                            JSONObject object = data.getJSONObject(0);
                            kecamatanView.setText(object.getString("Kecamatan"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Log.d("CREATE", error.toString());
                        Toast.makeText(DetailIbuHmlActivity.this,"Sedang Mengambil Data, Harap Ulangi Membuka Halaman ini",Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                //menambahkan parameter post, nama put sama dengan nama variable pada webservice PHP
                params.put("id_kecamatan", Kode_Kecamatan);

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    public void getDesa(final String Kode_Desa){
        final String id_desa = Kode_Desa;
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://103.71.255.66/wsKIA/list_detail_desa.php",
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        Log.d("CREATE", response);
                        try {
                            JSONArray data = new JSONArray(response);
                            JSONObject object = data.getJSONObject(0);
                            desaView.setText(object.getString("Desa"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Log.d("CREATE", error.toString());
                        Toast.makeText(DetailIbuHmlActivity.this,"Sedang Mengambil Data, Harap Ulangi Membuka Halaman ini",Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                //menambahkan parameter post, nama put sama dengan nama variable pada webservice PHP
                params.put("id_desa", Kode_Desa);

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }


    public void getData(){
  /*      JsonArrayRequest jsonObjReq = new JsonArrayRequest(Request.Method.GET,
                "http://galih.infiniteuny.id/list_ibu_hamil.php", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("DEBUGS", response.toString());
                try {
                    data = new ArrayList<IbuHml>();
                    for (int i=0; i<response.length();i++){
                        JSONObject object = response.getJSONObject(i);
                        String id_ibuhml =object.getString("id_ibuhml");
                        String no_kohort_ibuhml = object.getString("no_kohort_ibuhml");
                        String nama_ibuhml = object.getString("nama_ibuhml");
                        String nama_suami = object.getString("nama_suami");
                        String hpl = object.getString("hpl");
                        String tgl_lahir = object.getString("tgl_lahir");
                        String no_hp = object.getString("no_hp");
                        String alamat_ibuhml = object.getString("alamat_ibuhml");
                        String set_lokasi_lat = object.getString("set_lokasi_lat");
                        String set_lokasi_long = object.getString("set_lokasi_long");
                        String ket_ibuhml = object.getString("ket_ibuhml");
                        String id_desa = object.getString("id_desa");
                        data.add(new IbuHml(id_ibuhml,no_kohort_ibuhml, nama_ibuhml, nama_suami, hpl, tgl_lahir, no_hp, alamat_ibuhml, set_lokasi_lat, set_lokasi_long, ket_ibuhml, id_desa));
*/



                        Intent intent = new Intent(DetailIbuHmlActivity.this,UpdateDataIbuHmlActivity.class);
                        intent.putExtra("No_Index", No_Index);
                        intent.putExtra("Tgl_Informasi", Tgl_Informasi);
                        intent.putExtra("Nama", Nama);
                        intent.putExtra("Suami", Suami);
                        intent.putExtra("Alamat", Alamat);
                        intent.putExtra("Kode_Kecamatan", Kode_Kecamatan);
                        intent.putExtra("Kode_Desa", Kode_Desa);
                        intent.putExtra("User_id_pelapor", User_id_pelapor);
                        intent.putExtra("Verifikasi", Verifikasi);
                        intent.putExtra("Lat", Lat);
                        intent.putExtra("Lng", Lng);
                        intent.putExtra("No_Telp", No_Telp);

                        //menjalankan intent
                        startActivity(intent);
                        finish();
                        //intent.putExtras(extra);

                        //startActivity(intent);


                    /*}
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("DEBUGS", "Error: " + error.getMessage());
            }
        });
// Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);*/
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
        Log.d("lat",latitude);
        LatLng indonesia = new LatLng(Double.parseDouble(Lat),Double.parseDouble(Lng));
        mMap.addMarker(new MarkerOptions().position(indonesia).title("Lokasi Ibu Hamil"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(indonesia,15F));
        btnDapatArahIbuHml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://maps.google.com/maps?saddr="+latitude+","+longitude+"&daddr="+Lat.toString()+","+Lng.toString();
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
