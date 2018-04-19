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

public class KayipActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_kayip);

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

        //koruma önlemlerinden gelen değerler
        final String yildirimdanKorunmaDuzeyi = getIntent().getStringExtra("yildirimdanKorunmaDuzeyi");
        final String yangindanKorunmaTuru = getIntent().getStringExtra("yangindanKorunmaTuru");
        final String asiriGerilimdenKorunmaTuru = getIntent().getStringExtra("asiriGerilimdenKorunmaTuru");
        //koruma önlemlerinden gelen değerler
        //alt aktiviteden gelen değerler ***



        Spinner spYasamsalTehlikeler = findViewById(R.id.spinnerYasamsalTehlikeler);
        Spinner spYangindanCanKaybi = findViewById(R.id.spinnerYangindanCanKaybi);
        Spinner spAsiriGerilimdenCanKaybi = findViewById(R.id.spinnerAsiriGerilimdenCanKaybi);
        Spinner spYangindanZararGorecekServis = findViewById(R.id.spinnerYangindanZararGorecekServis);
        Spinner spAsiriGerilimdenZararGorecekServis = findViewById(R.id.spinnerAsiriGerilimdenZararGorecekServis);

        final String yasamsalTehlikeler = spYasamsalTehlikeler.getSelectedItem().toString();
        final String yangindanCanKaybi = spYangindanCanKaybi.getSelectedItem().toString();
        final String asiriGerilimdenCanKaybi = spAsiriGerilimdenCanKaybi.getSelectedItem().toString();
        final String yangindanZararGorecekServis = spYangindanZararGorecekServis.getSelectedItem().toString();
        final String asiriGerilimdenZararGorecekServis = spAsiriGerilimdenZararGorecekServis.getSelectedItem().toString();

        Button btnGeri5 = findViewById(R.id.buttonGeri5);
        Button btnDevam5 = findViewById(R.id.buttonDevam5);

        btnGeri5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                KayipActivity.super.onBackPressed();
                Bungee.slideRight(KayipActivity.this);
            }
        });

        btnDevam5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(KayipActivity.this, KayipActivity2.class);

                in.putExtra("yasamsalTehlikeler", yasamsalTehlikeler);
                in.putExtra("yangindanCanKaybi", yangindanCanKaybi);
                in.putExtra("asiriGerilimdenCanKaybi", asiriGerilimdenCanKaybi);
                in.putExtra("yangindanZararGorecekServis", yangindanZararGorecekServis);
                in.putExtra("asiriGerilimdenZararGorecekServis", asiriGerilimdenZararGorecekServis);

                //alt aktiviteden gelenleri gönder

                //korunma önlemlerinden gelenleri gönder
                in.putExtra("yildirimdanKorunmaDuzeyi", yildirimdanKorunmaDuzeyi);
                in.putExtra("yangindanKorunmaTuru", yangindanKorunmaTuru);
                in.putExtra("asiriGerilimdenKorunmaTuru", asiriGerilimdenKorunmaTuru);
                //korunma önlemlerinden gelenleri gönder

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
                Bungee.zoom(KayipActivity.this);
            }
        });
    }

    @Override
    public void onBackPressed(){

        KayipActivity.super.onBackPressed();
        Bungee.slideRight(KayipActivity.this);
    }
}
