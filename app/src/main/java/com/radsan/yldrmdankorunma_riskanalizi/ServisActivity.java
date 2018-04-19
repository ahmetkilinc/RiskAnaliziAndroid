package com.radsan.yldrmdankorunma_riskanalizi;

import android.content.Intent;
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

import spencerstudios.com.bungeelib.Bungee;

public class ServisActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_servis);

        //alt aktiviteden gelen değerler ***
        //yapidan gelen değerler
        final double binaUzunluk = getIntent().getDoubleExtra("binaUzunluk", 0.0);
        final double binaGenislik = getIntent().getDoubleExtra("binaGenislik", 0.0);
        final double binaCatiDuzlemiYerdenYukseklik = getIntent().getDoubleExtra("binaCatiDuzlemiYerdenYukseklik", 0.0);
        final double binaCatidakiNoktaninYerdenYuksekligi = getIntent().getDoubleExtra("binaCatidakiNoktaninYerdenYuksekligi", 0.0);
        final double binaToplamaAlani = getIntent().getDoubleExtra("binaToplamaAlani", 0.0);

        final String yapiMalzemesi = getIntent().getStringExtra("yapiMalzemesi");
        final String catiMalzemesi = getIntent().getStringExtra("catiMalzemesi");
        final String fizikselveYanginRiski = getIntent().getStringExtra("fizikselveYanginRiski");
        final String yapininEkranlanmasi = getIntent().getStringExtra("yapininEkranlanmasi");
        final String kablolarinEkranlanmasi = getIntent().getStringExtra("kablolarinEkranlanmasi");
        //yapidan gelen değerler

        //çevre koşullarından gelen değerler
        final String konumFaktoru = getIntent().getStringExtra("konumFaktoru");
        final String cevreFaktoru = getIntent().getStringExtra("cevreFaktoru");
        final String yillikGokGurultuluGunSayisi = getIntent().getStringExtra("yillikGokGurultuluGunSayisi");
        //çevre koşullarından gelen değerler

        //alt aktiviteden gelen değerler ***


        final EditText etIletkenServisSayisi = findViewById(R.id.editTextIletkenServisSayisi);
        final EditText etIletkenServisSayisiYeralti = findViewById(R.id.editTextIletkenServisSayisiYeralti);

        Spinner spYapiyaGelenHattinTuru = findViewById(R.id.spinnerYapiyaGelenHattinTuru);
        Spinner spKablolarinEkranlanmasi = findViewById(R.id.spinnerKablolarinEkranlanmasiServis);
        Spinner spOgAgTrafoVarligi = findViewById(R.id.spinnerOgAgTrafoVarligi);
        Spinner spYapiyaGelenHattinTuruServisYerUstu = findViewById(R.id.spinnerYapiyaGelenHattinTuruServisYerUstu);
        Spinner spYapiyaGelenHattinTuruServisYerAlti = findViewById(R.id.spinnerYapiyaGelenHattinTuruServisYerAlti);

        final String yapiyaGelenHattinTuru = spYapiyaGelenHattinTuru.getSelectedItem().toString();
        final String kablolarinEkranlanmasiServis = spKablolarinEkranlanmasi.getSelectedItem().toString();
        final String ogAgTrafoVarligi = spOgAgTrafoVarligi.getSelectedItem().toString();
        final String yapiyaGelenHattinTuruServisYerUstu = spYapiyaGelenHattinTuruServisYerUstu.getSelectedItem().toString();
        final String yapiyaGelenHattinTuruServisYerAlti = spYapiyaGelenHattinTuruServisYerAlti.getSelectedItem().toString();

        Button btnGeri3 = findViewById(R.id.buttonGeri3);
        Button btnDevam3 = findViewById(R.id.buttonDevam3);

        btnDevam3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(etIletkenServisSayisi.getText()) || TextUtils.isEmpty(etIletkenServisSayisiYeralti.getText())){

                    Toast.makeText(getApplication(), R.string.toast_uyari, Toast.LENGTH_LONG).show();
                }

                else{

                    int iletkenServisSayisiYerUstu = Integer.parseInt(etIletkenServisSayisi.getText().toString());
                    int iletkenServisSayisiYerAlti = Integer.parseInt(etIletkenServisSayisi.getText().toString());



                    Intent in = new Intent(ServisActivity.this, OnlemActivity.class);

                    in.putExtra("iletkenServisSayisiYerUstu", iletkenServisSayisiYerUstu);
                    in.putExtra("iletkenServisSayisiYerAlti", iletkenServisSayisiYerAlti);

                    in.putExtra("yapiyaGelenHattinTuru", yapiyaGelenHattinTuru);
                    in.putExtra("kablolarinEkranlanmasiServis", kablolarinEkranlanmasiServis);
                    in.putExtra("ogAgTrafoVarligi", ogAgTrafoVarligi);
                    in.putExtra("yapiyaGelenHattinTuruServisYerUstu", yapiyaGelenHattinTuruServisYerUstu);
                    in.putExtra("yapiyaGelenHattinTuruServisYerAlti", yapiyaGelenHattinTuruServisYerAlti);

                    //alt aktiviteden gelenleri gönder
                    //Cevre koşullarından gelenleri gönder
                    in.putExtra("konumFaktoru", konumFaktoru);
                    in.putExtra("cevreFaktoru", cevreFaktoru);
                    in.putExtra("yillikGokGurultuluGunSayisi", yillikGokGurultuluGunSayisi);
                    //Cevre koşullarından gelenleri gönder
                    //yapidan gelenleri gönder
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
                    //yapidan gelenleri gönder
                    //alt aktiviteden gelenleri gönder

                    startActivity(in);
                    Bungee.zoom(ServisActivity.this);
                }
            }
        });

        btnGeri3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ServisActivity.super.onBackPressed();
                Bungee.slideRight(ServisActivity.this);
            }
        });
    }

    @Override
    public void onBackPressed(){

        ServisActivity.super.onBackPressed();
        Bungee.slideRight(ServisActivity.this);
    }
}
