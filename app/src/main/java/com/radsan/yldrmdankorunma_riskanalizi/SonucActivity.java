package com.radsan.yldrmdankorunma_riskanalizi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.CDATASection;

public class SonucActivity extends AppCompatActivity{

    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_sonuc);

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

        //kayip turu 2-den gelen değerler
        final String yanginNedeniyleKulturelMiras = getIntent().getStringExtra("yanginNedeniyleKulturelMiras");
        final String ekonomikZarar = getIntent().getStringExtra("ekonomikZarar");
        final String yanginNedeniyleEkonomikKayip = getIntent().getStringExtra("yanginNedeniyleEkonomikKayip");
        final String asiriGerilimNedeniyleEkonomikKayip = getIntent().getStringExtra("asiriGerilimNedeniyleEkonomikKayip");
        final String adimGerilimiEtkisi = getIntent().getStringExtra("adimGerilimiEtkisi");
        final String kabulEdilirEkonomikRisk = getIntent().getStringExtra("kabulEdilirEkonomikRisk");
        //kayip turu 2-den gelen değerler
        //alt aktiviteden gelen değerler ***


        //Activitydeki eleman declarations
        Button btnSonucGeri = findViewById(R.id.buttonGeriSonuc);
        Button btnSonucDevam = findViewById(R.id.buttonDevamSonuc);

        TextView tvCanKaybiKabulEdilirRisk = findViewById(R.id.textViewCanKaybiKabulEdilirRisk);
        TextView tvCanKaybiDogrudanCarpmaRiski = findViewById(R.id.textViewCanKaybiDogrudanCarpmaRiski);
        TextView tvCanKaybiDolayliCarpmaRiski = findViewById(R.id.textViewCanKaybiDolayliCarpmaRiski);
        TextView tvCanKaybiToplamRisk = findViewById(R.id.textViewCanKaybiToplamRisk);

        TextView tvServisKaybiKabulEdilirRisk = findViewById(R.id.textViewServisKaybiKabulEdilirRisk);
        TextView tvServisKaybiDogrudanCarpmaRiski = findViewById(R.id.textViewServisKaybiDogrudanCarpmaRiski);
        TextView tvServisKaybiDolayliCarpmaRiski = findViewById(R.id.textViewServisKaybiDolayliCarpmaRiski);
        TextView tvServisKaybiToplamRisk = findViewById(R.id.textViewServisKaybiToplamRisk);

        TextView tvKulturelMirasKaybiKabulEdilirRisk = findViewById(R.id.textViewKulturelMirasKaybiKabulEdilirRisk);
        TextView tvKulturelMirasKaybiDogrudanCarpmaRiski = findViewById(R.id.textViewKulturelMirasKaybiDogrudanCarpmaRiski);
        TextView tvKulturelMirasKaybiDolayliCarpmaRiski = findViewById(R.id.textViewKulturelMirasKaybiDolayliCarpmaRiski);
        TextView tvKulturelMirasKaybiToplamRisk = findViewById(R.id.textViewKulturelMirasKaybiToplamRisk);

        TextView tvEkonomikKayipKabulEdilirRisk = findViewById(R.id.textViewEkonomikKayipKabulEdilirRisk);
        TextView tvEkonomikKayipDogrudanCarpmaRiski = findViewById(R.id.textViewEkonomikKayipDogrudanCarpmaRiski);
        TextView tvEkonomikKayipDolayliCarpmaRiski = findViewById(R.id.textViewEkonomikKayipDolayliCarpmaRiski);
        TextView tvEkonomikKayipToplamRisk = findViewById(R.id.textViewEkonomikKayipToplamRisk);
        //Activity eleman declarations

        tvEkonomikKayipToplamRisk.setText(Double.toString(binaUzunluk));
        tvCanKaybiDolayliCarpmaRiski.setText(Double.toString(binaGenislik));


        Button btnSignout = findViewById(R.id.buttonSignout);

        btnSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user = null;

                startActivity(new Intent(SonucActivity.this, SignInActivity.class))   ;
            }
        });
    }



    @Override
    public void onBackPressed(){

        Toast.makeText(getApplicationContext(), "Sonuç Sayfasından Geri Gitmek için Lütfen Butonu Kullanınız.", Toast.LENGTH_LONG).show();
    }
}