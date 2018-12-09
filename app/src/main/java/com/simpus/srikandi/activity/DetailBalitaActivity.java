package com.simpus.srikandi.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.simpus.srikandi.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetailBalitaActivity extends AppCompatActivity {

    //komponen pada layout detail bumil peta
    TextView namaBalitaView, verifikasiDataView, nomorIndexBalitaView, tglLahirView, namaIbuView, namaAyahView;
    Spinner statusStuntingView, statusGiziView;
    private GoogleMap mMap;
    private String latitude, longitude, No_Index_Balita_Dinas;
    Button btnDapatArahIbuHml;
    //CheckBox checkboxVerifikasi;
    LinearLayout verifikasiData;

    private String Nama_Bayi,Tanggal_Lahir, Nama_Ibu,Nama_Ayah,Kode_Wilayah,Ket_Stunting,Lat,lng, Nip, Ket_Gibur;
    @Override
    protected void onCreate(Bundle savedInstaceState){
        super.onCreate(savedInstaceState);
        setContentView(R.layout.activity_detail_balita);

        //mereferensikan komponen pada layout
        namaBalitaView = (TextView)findViewById(R.id.namaBalitaView);
        verifikasiDataView = (TextView)findViewById(R.id.verifikasiDataView);
        nomorIndexBalitaView = (TextView)findViewById(R.id.nomorIndexBalitaView);
        tglLahirView = (TextView)findViewById(R.id.tglLahirView);
        namaIbuView = (TextView)findViewById(R.id.namaIbuView);
        statusStuntingView = (Spinner)findViewById(R.id.statusStuntingView);
        statusGiziView = (Spinner)findViewById(R.id.statusGiziView);
        btnDapatArahIbuHml = (Button) findViewById(R.id.btnDapatArahIbuHml);
        verifikasiData =(LinearLayout) findViewById(R.id.verifikasiData);
//        checkboxVerifikasi=(CheckBox)  findViewById(R.id.checkboxVerifikasi);
        namaAyahView = (TextView) findViewById(R.id.namaAyahView);

        //mengambil intent dari intent sebelumnya
        Bundle extras = getIntent().getExtras();
        No_Index_Balita_Dinas = extras.getString("No_Index_Balita_Dinas");
        Nama_Bayi = extras.getString("Nama_Bayi");
        Tanggal_Lahir = extras.getString("Tanggal_Lahir");
        Nama_Ibu = extras.getString("Nama_Ibu");
        Nama_Ayah = extras.getString("Nama_Ayah");
        Kode_Wilayah= extras.getString("Kode_Wilayah");


        //mendefinisikan Spinner
        String [] ket_Stunting={"Non Stunting","Stunting"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,ket_Stunting);
        statusStuntingView.setAdapter(adapter);

        //mendefinisikan Spinner
        String [] ket_Gizi={"Lebih","Baik","Kurang","Buruk"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,ket_Gizi);
        statusGiziView.setAdapter(adapter1);

        //menampilkan data yang diambil melalui Textview
        namaBalitaView.setText(Nama_Bayi);


        Nip = getSharedPreferences("DATA", Context.MODE_PRIVATE).getString("User_id_pelapor","");
        String jenisPelapor = getSharedPreferences("DATA", Context.MODE_PRIVATE).getString("Jenis_pelapor","");
        /*if(jenisPelapor.equals("B")||jenisPelapor.equals("BK")||jenisPelapor.equals("BM")){
            checkboxVerifikasi.setEnabled(true);
        }else{
            checkboxVerifikasi.setEnabled(false);
        }*/



        //handling checkbox
       /* checkboxVerifikasi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    showAlertDialogVerif();
                }
            }
        });*/

        tglLahirView.setText(Tanggal_Lahir);
        nomorIndexBalitaView.setText(No_Index_Balita_Dinas);
        namaIbuView.setText(Nama_Ibu);
        namaAyahView.setText(Nama_Ayah);




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
        Ket_Stunting = statusStuntingView.getSelectedItem().toString();
        Ket_Gibur =statusGiziView.getSelectedItem().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://103.71.255.66/wsKIA/databayipeta_create.php",
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
                                Toast.makeText(DetailBalitaActivity.this,object.getString("message"),Toast.LENGTH_LONG).show();
                                finish();
                            }else {
                                //menampilkan pesan gagal
                                Toast.makeText(DetailBalitaActivity.this, object.getString("message"), Toast.LENGTH_LONG).show();
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
                        Toast.makeText(DetailBalitaActivity.this,"Sedang Mengambil Data, Pastikan Perangkat Sudah Tersambung Internet",Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                //menambahkan parameter post, nama put sama dengan nama variable pada webservice PHP

                params.put("No_Index_Balita_Dinas", No_Index_Balita_Dinas);
                params.put("Nama_Bayi", Nama_Bayi);
                params.put("Tanggal_Lahir", Tanggal_Lahir);
                params.put("Nama_Ibu", Nama_Ibu);
                params.put("Nama_Ayah", Nama_Ayah);
                params.put("Lat", latitude);
                params.put("lng", longitude);
                params.put("Ket_Stunting",Ket_Stunting );
                params.put("Ket_Gibur",Ket_Gibur );
                params.put("Nip", Nip);
                params.put("Kode_Desa",Kode_Wilayah );


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


