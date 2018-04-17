package com.radsan.yldrmdankorunma_riskanalizi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import spencerstudios.com.bungeelib.Bungee;

public class MainActivity extends AppCompatActivity{

    double binaUzunluk = 0.0;
    double binaGenislik = 0.0;
    double binaCatiDuzlemiYerdenYukseklik = 0.0;
    double binaCatidakiNoktaninYerdenYuksekligi = 0.0;
    double binaToplamaAlani = 0.0;

    private FirebaseAuth mAuth;

    private GoogleApiClient mGoogleApiClient;

    String name = "";
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null ){

            name = user.getDisplayName();
        }

        else{

            startActivity(new Intent(MainActivity.this, SignInActivity.class));
        }

        final EditText etUzunluk = findViewById(R.id.editTextUzunluk);
        final EditText etGenislik = findViewById(R.id.editTextGenislik);
        final EditText etCatiDuzlemiYerdenYukseklik = findViewById(R.id.editTextCatiDuzlemininYerdenYuksekligi);
        final EditText etCatidakiNoktaninYerdenYuksekligi = findViewById(R.id.editTextCatidakiNoktanınYerdenYuksekligi);
        final EditText etToplamaAlani = findViewById(R.id.editTextToplamaAlani);

        final Spinner spYapiMalzemesi = findViewById(R.id.spinnerYapiMalzemesi);
        final Spinner spCatiMalzemesi = findViewById(R.id.spinnerCatiMalzemesi);
        final Spinner spFizikselveYanginRiski = findViewById(R.id.spinnerFizikselveYanginRiski);
        final Spinner spYapininEkranlanmasi = findViewById(R.id.spinnerYapininEkranlanmasi);
        final Spinner spKablolarinEkranlanmasi = findViewById(R.id.spinnerKablolarinEkranlanmasi);


        final String yapiMalzemesi = spYapiMalzemesi.getSelectedItem().toString();
        final String catiMalzemesi = spCatiMalzemesi.getSelectedItem().toString();
        final String fizikselveYanginRiski = spFizikselveYanginRiski.getSelectedItem().toString();
        final String yapininEkranlanmasi = spYapininEkranlanmasi.getSelectedItem().toString();
        final String kablolarinEkranlanmasi = spKablolarinEkranlanmasi.getSelectedItem().toString();

        Button btnDevam1 = findViewById(R.id.buttonDevam1);
        Button cikisYap = findViewById(R.id.cikisMain);

        mAuth = FirebaseAuth.getInstance();


        cikisYap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth.signOut();
                startActivity(new Intent(MainActivity.this, SignInActivity.class));
            }
        });

        btnDevam1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                if(TextUtils.isEmpty(etUzunluk.getText()) || TextUtils.isEmpty(etGenislik.getText()) ||
                        TextUtils.isEmpty(etCatidakiNoktaninYerdenYuksekligi.getText()) ||
                        TextUtils.isEmpty(etCatiDuzlemiYerdenYukseklik.getText()) || TextUtils.isEmpty(etToplamaAlani.getText())){

                    Toast.makeText(getApplication(), R.string.toast_uyari, Toast.LENGTH_LONG).show();
                }
                else{

                    binaUzunluk = Double.parseDouble(etUzunluk.getText().toString());
                    binaGenislik = Double.parseDouble(etGenislik.getText().toString());
                    binaCatiDuzlemiYerdenYukseklik = Double.parseDouble(etCatiDuzlemiYerdenYukseklik.getText().toString());
                    binaCatidakiNoktaninYerdenYuksekligi = Double.parseDouble(etCatidakiNoktaninYerdenYuksekligi.getText().toString());
                    binaToplamaAlani = Double.parseDouble(etToplamaAlani.getText().toString());

                    Intent in = new Intent(MainActivity.this, CevreActivity.class);
                    in.putExtra("binaUzunluk", binaUzunluk);
                    in.putExtra("binaGenislik", binaGenislik);
                    in.putExtra("binaCatiDuzlemiYerdenYukseklik", binaCatiDuzlemiYerdenYukseklik);
                    in.putExtra("binaCatidakiNoktaninYerdenYuksekligi", binaCatidakiNoktaninYerdenYuksekligi);
                    in.putExtra("binaToplamaAlani", binaToplamaAlani);

                    in.putExtra("yapiMalzemesi", yapiMalzemesi);
                    in.putExtra("catiMalzemesi", catiMalzemesi);
                    in.putExtra("fizikselveYanginRiski", fizikselveYanginRiski);
                    in.putExtra("yapininEkranlanmasi", yapininEkranlanmasi);
                    in.putExtra("kablolarinEkranlanmasi", kablolarinEkranlanmasi);

                    startActivity(in);
                    Bungee.zoom(MainActivity.this);
                }
            }
        });
    }

    @Override
    public void onBackPressed(){

        new AlertDialog.Builder(this)
                .setTitle(R.string.yapi_alert_title)
                .setMessage(R.string.yapi_alert_message)
                .setNegativeButton(R.string.yapi_alert_no, null)
                .setPositiveButton(R.string.yapi_alert_yes, new DialogInterface.OnClickListener(){

                    public void onClick(DialogInterface arg0, int arg1){

                        MainActivity.super.onBackPressed();
                        //startActivity(new Intent(MainActivity.this, SignInActivity.class));


                    }
                }).create().show();
    }
}