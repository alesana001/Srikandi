package com.simpus.srikandi.activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.simpus.srikandi.R;
import com.simpus.srikandi.activity.objects.SpinnerItem;


import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private String User_id_pelapor;
    Button loginButton, registerButton;
    EditText user_idView, passwordView;
    SharedPreferences sharedPreferences;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user_idView = (EditText) findViewById(R.id.user_idView);
        passwordView = (EditText) findViewById(R.id.passwordView);
        loginButton = (Button) findViewById(R.id.loginButton);
        registerButton = (Button) findViewById(R.id.RegisterButton);

        sharedPreferences = getSharedPreferences("DATA", Context.MODE_PRIVATE);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);

                startActivity(intent);
                finish();
            }
        });



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!user_idView.getText().toString().equals("") || !passwordView.getText().toString().equals(""))
                {
                    postLogin();
                }
                else
                {
                    Toast.makeText(LoginActivity.this,"NIK atau Password belum terisi", Toast.LENGTH_LONG).show();
                }


            }
        });
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

    public void postLogin(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://gizikia.dinkes.surakarta.go.id/srikandi_php_file/login_ptgs.php",
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
                                //Menyimpan data Login
                                JSONObject loginData = object.getJSONObject("data");
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putBoolean("login", true);
                                editor.putString("id_petugas", loginData.getString("id_petugas"));
                                editor.putString("User_id_pelapor", loginData.getString("User_id_pelapor"));
                                editor.putString("Nama", loginData.getString("Nama"));
                                editor.putString("No_telepon", loginData.getString("No_telepon"));
                                editor.putString("Kecamatan", loginData.getString("Kecamatan"));
                                editor.putString("Desa", loginData.getString("Desa"));
                                editor.putString("Jenis_pelapor", loginData.getString("Jenis_pelapor"));
                                editor.putString("Nip", loginData.getString("Nip"));

                                editor.commit();

                                //menampilkan pesan berhasil
                                Toast.makeText(LoginActivity.this,object.getString("message"),Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(intent);
                                progressDialog.dismiss();
                                finish();
                            }else {
                                //menampilkan pesan gagal
                                Log.d("CREATE", status);
                                progressDialog.dismiss();
                                Toast.makeText(LoginActivity.this, object.getString("message"), Toast.LENGTH_LONG).show();
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
                        Toast.makeText(LoginActivity.this,"Sedang Mengambil Data, Pastikan Perangkat Sudah Tersambung Internet",Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }){


            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                //menambahkan parameter post, nama put sama dengan nama variable pada webservice PHP
                params.put("user_id_pelapor", user_idView.getText().toString());
                params.put("password", passwordView.getText().toString());

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }
}
