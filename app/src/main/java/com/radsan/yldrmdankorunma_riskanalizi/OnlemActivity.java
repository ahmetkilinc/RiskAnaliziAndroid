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

public class OnlemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_onlem);

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

        //servis hatlarından gelen değerler
        final int iletkenServisSayisiYerUstu = getIntent().getIntExtra("iletkenServisSayisiYerUstu", 0);
        final int iletkenServisSayisiYerAlti = getIntent().getIntExtra("iletkenServisSayisiYerAlti", 0);

        final String yapiyaGelenHattinTuru = getIntent().getStringExtra("yapiyaGelenHattinTuru");
        final String kablolarinEkranlanmasiServis = getIntent().getStringExtra("kablolarinEkranlanmasiServis");
        final String ogAgTrafoVarligi = getIntent().getStringExtra("ogAgTrafoVarligi");
        final String yapiyaGelenHattinTuruServisYerUstu = getIntent().getStringExtra("yapiyaGelenHattinTuruServisYerUstu");
        final String yapiyaGelenHattinTuruServisYerAlti = getIntent().getStringExtra("yapiyaGelenHattinTuruServisYerAlti");
        //servis hatlarından gelen değerler

        //alt aktiviteden gelen değerler ***


        Spinner spYildirimdanKorunmaDuzeyi = findViewById(R.id.spinnerYildirimdanKorunmaDuzeyi);
        Spinner spYangindanKorunmaTuru = findViewById(R.id.spinnerYangindanKorunmaTuru);
        Spinner spAsiriGerilimdenKorunmaTuru = findViewById(R.id.spinnerAsiriGerilimdenKorunmaTuru);

        final String yildirimdanKorunmaDuzeyi = spYildirimdanKorunmaDuzeyi.getSelectedItem().toString();
        final String yangindanKorunmaTuru = spYangindanKorunmaTuru.getSelectedItem().toString();
        final String asiriGerilimdenKorunmaTuru = spAsiriGerilimdenKorunmaTuru.getSelectedItem().toString();

        Button btnGeri4 = findViewById(R.id.buttonGeri4);
        Button btnDevam4 = findViewById(R.id.buttonDevam4);

        btnGeri4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OnlemActivity.super.onBackPressed();
                Bungee.slideRight(OnlemActivity.this);
            }
        });

        btnDevam4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(OnlemActivity.this, KayipActivity.class);

                in.putExtra("yildirimdanKorunmaDuzeyi", yildirimdanKorunmaDuzeyi);
                in.putExtra("yangindanKorunmaTuru", yangindanKorunmaTuru);
                in.putExtra("asiriGerilimdenKorunmaTuru", asiriGerilimdenKorunmaTuru);

                //alt aktiviteden gelenleri gönder

                //servis hatlarından gelenleri gönder
                in.putExtra("iletkenServisSayisiYerUstu", iletkenServisSayisiYerUstu);
                in.putExtra("iletkenServisSayisiYerAlti", iletkenServisSayisiYerAlti);

                in.putExtra("yapiyaGelenHattinTuru", yapiyaGelenHattinTuru);
                in.putExtra("kablolarinEkranlanmasiServis", kablolarinEkranlanmasiServis);
                in.putExtra("ogAgTrafoVarligi", ogAgTrafoVarligi);
                in.putExtra("yapiyaGelenHattinTuruServisYerUstu", yapiyaGelenHattinTuruServisYerUstu);
                in.putExtra("yapiyaGelenHattinTuruServisYerAlti", yapiyaGelenHattinTuruServisYerAlti);
                //servis hatlarından gelenleri gönder

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
                Bungee.zoom(OnlemActivity.this);
            }
        });
    }

    @Override
    public void onBackPressed(){

        OnlemActivity.super.onBackPressed();
        Bungee.slideRight(OnlemActivity.this);
    }
}
