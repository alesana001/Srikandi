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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import com.simpus.srikandi.activity.objects.SpinnerItem;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetailIbuHmlPetaActivity extends AppCompatActivity {

    //komponen pada layout detail bumil peta
    TextView namaIbuhmlView, verifikasiDataView, nomorIndexView, namaSuamiView, tanggalLahirView, hplView;
    Spinner statusRistiView;
    private GoogleMap mMap;
    private String latitude, longitude, No_index_Bumil;
    Button btnDapatArahIbuHml;
//    CheckBox checkboxVerifikasi;
    LinearLayout verifikasiData;


    private String No_Index_Bumil, Nama,NamaSuami,TanggalLahir,StatusRisti,Hpl,Verifikasi,Lat,lng, Nip;
    @Override
    protected void onCreate(Bundle savedInstaceState){
        super.onCreate(savedInstaceState);
        setContentView(R.layout.activity_detail_ibuhml_peta);

        //mereferensikan komponen pada layout
        namaIbuhmlView = (TextView)findViewById(R.id.namaIbuhmlView);
        verifikasiDataView = (TextView)findViewById(R.id.verifikasiDataView);
        namaSuamiView = (TextView)findViewById(R.id.namaSuamiView);
        nomorIndexView = (TextView)findViewById(R.id.nomorIndexView);
        tanggalLahirView = (TextView)findViewById(R.id.tanggalLahirView);
        statusRistiView = (Spinner)findViewById(R.id.statusRistiView);
        btnDapatArahIbuHml = (Button) findViewById(R.id.btnDapatArahIbuHml);
        verifikasiData =(LinearLayout) findViewById(R.id.verifikasiData);
//        checkboxVerifikasi=(CheckBox)  findViewById(R.id.checkboxVerifikasi);
        hplView = (TextView) findViewById(R.id.hplView);

        //mengambil intent dari intent sebelumnya
        Bundle extras = getIntent().getExtras();
        No_Index_Bumil = extras.getString("No_Index_Bumil_Dinas");
        TanggalLahir = extras.getString("Tgl_Lahir");
        Nama = extras.getString("Nama");
        NamaSuami = extras.getString("Suami");
        Hpl = extras.getString("HTP");


        //mendefinisikan Spinner
        String [] daftarRisti={"Non Risti","Ringan", "Sedang", "Berat"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,daftarRisti);
        statusRistiView.setAdapter(adapter);
        //menampilkan data yang diambil melalui Textview
        namaIbuhmlView.setText(Nama);

        Nip = getSharedPreferences("DATA", Context.MODE_PRIVATE).getString("User_id_pelapor","");
        String jenisPelapor = getSharedPreferences("DATA", Context.MODE_PRIVATE).getString("Jenis_pelapor","");
        /*if(jenisPelapor.equals("B")||jenisPelapor.equals("BK")||jenisPelapor.equals("BM")){
            checkboxVerifikasi.setEnabled(true);
        }else{
            checkboxVerifikasi.setEnabled(false);
        }

        //handling checkbox
        checkboxVerifikasi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    showAlertDialogVerif();
                }
            }
        });*/

        namaSuamiView.setText(NamaSuami);
        nomorIndexView.setText(No_Index_Bumil);
        tanggalLahirView.setText(TanggalLahir);
        hplView.setText(Hpl);




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

        }
        else
        {
            gpsTracker.showSettingsAlert();
        }

        btnDapatArahIbuHml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postdataPeta();
            }
        });
    }

    public void postdataPeta() {
        StatusRisti = statusRistiView.getSelectedItem().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://gizikia.dinkes.surakarta.go.id/srikandi_php_file/databumilpeta_create.php",
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
                                Toast.makeText(DetailIbuHmlPetaActivity.this,object.getString("message"),Toast.LENGTH_LONG).show();
                                finish();
                            }else {
                                //menampilkan pesan gagal
                                Toast.makeText(DetailIbuHmlPetaActivity.this, object.getString("message"), Toast.LENGTH_LONG).show();
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
                        Toast.makeText(DetailIbuHmlPetaActivity.this,"Sedang Mengambil Data, Pastikan Perangkat Sudah Tersambung Internet",Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                //menambahkan parameter post, nama put sama dengan nama variable pada webservice PHP
                params.put("No_Index_Bumil", No_Index_Bumil);
                params.put("TanggalLahir", TanggalLahir);
                params.put("Nama", Nama);
                params.put("NamaSuami", NamaSuami);
                params.put("StatusRisti",StatusRisti );
                params.put("Hpl", Hpl);
                params.put("Lat", latitude);
                params.put("lng", longitude);
                params.put("Nip", Nip);

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}