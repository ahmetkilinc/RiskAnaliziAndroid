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

public class KayipActivity2 extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_kayip2);

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

        //kayip turu 1-den gelen değerler
        final String yasamsalTehlikeler = getIntent().getStringExtra("yasamsalTehlikeler");
        final String yangindanCanKaybi = getIntent().getStringExtra("yangindanCanKaybi");
        final String asiriGerilimdenCanKaybi = getIntent().getStringExtra("asiriGerilimdenCanKaybi");
        final String yangindanZararGorecekServis = getIntent().getStringExtra("yangindanZararGorecekServis");
        final String asiriGerilimdenZararGorecekServis = getIntent().getStringExtra("asiriGerilimdenZararGorecekServis");
        //kayip turu 1-den gelen değerler
        //alt aktiviteden gelen değerler ***


        Spinner spYanginNedeniyleKulturelMirasKaybi = findViewById(R.id.spinnerYanginNedeniyleKulturelMirasKaybi);
        Spinner spEkonomikZarar = findViewById(R.id.spinnerEkonomikZarar);
        Spinner spYanginNedeniyleEkonomikKayip = findViewById(R.id.spinnerYanginNedeniyleEkonomikKayip);
        Spinner spAsiriGerilimNedeniyleEkonomikKayip = findViewById(R.id.spinnerAsiriGerilimNedeniyleEkonomikKayip);
        Spinner spAdimGerilimiEtkisi = findViewById(R.id.spinnerAdimGerilimiEtkisi);
        Spinner spKabulEdilirEkonomikRisk = findViewById(R.id.spinnerKabulEdilirEkonomikRisk);

        final String yanginNedeniyleKulturelMiras = spYanginNedeniyleKulturelMirasKaybi.getSelectedItem().toString();
        final String ekonomikZarar = spEkonomikZarar.getSelectedItem().toString();
        final String yanginNedeniyleEkonomikKayip = spYanginNedeniyleEkonomikKayip.getSelectedItem().toString();
        final String asiriGerilimNedeniyleEkonomikKayip = spAsiriGerilimNedeniyleEkonomikKayip.getSelectedItem().toString();
        final String adimGerilimiEtkisi = spAdimGerilimiEtkisi.getSelectedItem().toString();
        final String kabulEdilirEkonomikRisk = spKabulEdilirEkonomikRisk.getSelectedItem().toString();

        Button btnGeri6 = findViewById(R.id.buttonGeri6);
        Button btnDevam6 = findViewById(R.id.buttonDevam6);

        btnGeri6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                KayipActivity2.super.onBackPressed();
                Bungee.slideRight(KayipActivity2.this);
            }
        });

        btnDevam6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(KayipActivity2.this, SonucActivity.class);

                in.putExtra("yanginNedeniyleKulturelMiras", yanginNedeniyleKulturelMiras);
                in.putExtra("ekonomikZarar", ekonomikZarar);
                in.putExtra("yanginNedeniyleEkonomikKayip", yanginNedeniyleEkonomikKayip);
                in.putExtra("asiriGerilimNedeniyleEkonomikKayip", asiriGerilimNedeniyleEkonomikKayip);
                in.putExtra("adimGerilimiEtkisi", adimGerilimiEtkisi);
                in.putExtra("kabulEdilirEkonomikRisk", kabulEdilirEkonomikRisk);

                //alt aktiviteden gelenleri gönder
                //kayip türü 1-den gelenleri gönder
                in.putExtra("yasamsalTehlikeler", yasamsalTehlikeler);
                in.putExtra("yangindanCanKaybi", yangindanCanKaybi);
                in.putExtra("asiriGerilimdenCanKaybi", asiriGerilimdenCanKaybi);
                in.putExtra("yangindanZararGorecekServis", yangindanZararGorecekServis);
                in.putExtra("asiriGerilimdenZararGorecekServis", asiriGerilimdenZararGorecekServis);
                //kayip türü 1-den gelenleri gönder

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
                Bungee.zoom(KayipActivity2.this);
            }
        });
    }
}