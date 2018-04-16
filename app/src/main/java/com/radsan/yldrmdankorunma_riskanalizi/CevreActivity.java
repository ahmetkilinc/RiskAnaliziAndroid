package com.radsan.yldrmdankorunma_riskanalizi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Spinner;

import spencerstudios.com.bungeelib.Bungee;

public class CevreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_cevre);

        //alt aktiviteden gelen değerler ***

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

        //alt aktiviteden gelen değerler ***

        Spinner spKonumFaktoru = findViewById(R.id.spinnerKonumFaktoru);
        Spinner spCevreFaktoru = findViewById(R.id.spinnerCevreFaktoru);
        Spinner spYillikGokGurultuluGunSayisi = findViewById(R.id.spinnerYillikGokGurultuluGun);


        final String konumFaktoru = spKonumFaktoru.getSelectedItem().toString();
        final String cevreFaktoru = spCevreFaktoru.getSelectedItem().toString();
        final String yillikGokGurultuluGunSayisi = spYillikGokGurultuluGunSayisi.getSelectedItem().toString();

        Button btnDevam = findViewById(R.id.buttonDevam2);
        Button btnGeri = findViewById(R.id.buttonGeri2);

        btnDevam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(CevreActivity.this, ServisActivity.class);

                in.putExtra("konumFaktoru", konumFaktoru);
                in.putExtra("cevreFaktoru", cevreFaktoru);
                in.putExtra("yillikGokGurultuluGunSayisi", yillikGokGurultuluGunSayisi);

                //alt aktiviteden gelenleri gönder
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
                //alt aktiviteden gelenleri gönder
                startActivity(in);
                Bungee.zoom(CevreActivity.this);
            }
        });

        btnGeri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CevreActivity.super.onBackPressed();
                Bungee.slideRight(CevreActivity.this);
            }
        });
    }
}