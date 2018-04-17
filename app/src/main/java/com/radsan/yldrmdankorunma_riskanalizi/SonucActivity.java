package com.radsan.yldrmdankorunma_riskanalizi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SonucActivity extends AppCompatActivity{

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

        double Ra = 0.0, Rb = 0.0, Rc = 0.0, Rm = 0.0 , Ru = 0.0, Rw = 0.0, Rz = 0.0;

        double Rv = 0.0;

        double R1 = Ra + Rb + Rc + Rm + Ru + Rw + Rz;

        double R2 = Rb + Rc + Rm + Rv + Rw + Rz;

        double R3 = Rb + Rv;

        double R4 = Ra + Rb + Rc + Rm + Ru + Rv + Rw + Rz;

        //Rd = yapıya yıldırım düşmesinden dolayı yapı için risk

        double Rd = Ra + Rb + Rc;

        //RI = yapıya düşmeyen yıldırımdan dolayı yapı için risk

        double RI = Rm + Ru + Rv + Rw + Rz;

        //4.3.2 hasar tipine göre risk bileşenlerinin bileşimi

        double Rs = 0.0, Rf = 0.0, Ro = 0.0;

        double R = Rs + Rf + Ro;

        //Rs = canlıların zarar görmesinden (D1) kaynaklanan risk

        Rs = Ra + Ru;

        //Rf = yapıya fiziki hasardan (D2) kaynaklanan risk

        Rf = Rb + Rv;

        //Ro = iç sistemlerin arızalanmasından (D3) kaynaklanan risk

        Ro = Rm + Rc + Rw + Rz;

        //katlanılabilir risk = Rt

        String kayipTipi = "";
        double Rty = 0.0;

        if("insan hayatının kaybı veya kalıcı yaralanmalar".equals(kayipTipi)){

            Rty = Math.pow(10, -5);
        }

        else if ("kamu hizmeti kaybı".equals(kayipTipi)){

            Rty = Math.pow(10, -3);
        }

        else{

            Rty = Math.pow(10, -3);
        }

        //6.2 yapıya düşen yıldırımdan kaynaklanan risk bileşenlerinin değerlendirilmesi (S1)

        double Nd = 0.0, Pa = 0.0, La = 0.0, Pb = 0.0, Lb = 0.0, Pc = 0.0, Lc = 0.0;

        //canlıların zarar görmesi ile ilgili bileşen (D1)
        Ra = Nd * Pa * La;

        //fiziki hasar ile ilgili bileşen (D2)
        Rb = Nd * Pb * Lb;

        //iç sistemlerin arızalanması ile ilgili bileşen (D3)
        Rc = Nd * Pc * Lc;


        //6.3 yapının yakınına düşen yıldırımdan kaynaklanan risk bileşenlerinin değerlendirilmesi

        //iç sistemlerin arızalanması ile ilgili bileşen (D3)

        double Nm = 0.0, Pm = 0.0, Lm = 0.0;

        Rm = Nm * Pm * Lm;

        //6.4 yapıya bağlı hizmet tesisatına düşen yıldırımdan kaynaklanan risk bileşenlerinin değerlendirilmesi

        double Nl = 0.0, Nda = 0.0, Pw = 0.0, Lw = 0.0, Pu = 0.0, Lu = 0.0, Pv = 0.0, Lv = 0.0;

        //canlıların zarar görmesi ile ilgili bileşen (D1)
        Ru = (Nl + Nda) * Pu * Lu;

        //fiziki hasar ile ilgili bileşen (D2)
        Rv = (Nl + Nda) * Pv * Lv;

        //iç sistemlerin arızalanması ile ilgili bileşen (D3)
        Rw = (Nl + Nda) * Pw * Lw;

        //6.5 yapıya bağlı bir hizmet tesisatının yakınına düşen yıldırımdan kaynaklanan risk bileşenlerinin değerlendirilmesi (S4)

        //iç sistemlerin arızalanması ile ilgili bileşen

        double Ni = 0.0, Pz = 0.0, Lz = 0.0;

        Rz = (Ni - Nl) * Pz * Lz;

        String h = "";
        String hk = "";

        String [] hasar = new String[3];
        String [] hasarKaynagi = new String[4];

        hasar[0] = "D1 Canlıların zarar görmesi";
        hasar[1] = "D2 Fiziki hasar";
        hasar[2] = "D3 Elektrikli ve elektronik sistemlerin arızalanması";
        hasar[3] = "Hasar kaynağına gçre ortaya çıkan risk";

        hasarKaynagi[0] = "S1 Yapıya yıldırım düşmesi";
        hasarKaynagi[1] = "S2 Yapının yakınına yıldırım düşmesi";
        hasarKaynagi[2] = "S3 Yapıya giren hizmet tesisatına yıldırım düşmesi";
        hasarKaynagi[3] = "S4 Hizmet tesisatının yakınına yıldırım düşmesi";
        hasarKaynagi[4] = "Hasar tipine göre ortaya çıkan risk";

        if(hasar[0].equals(h)){

            if(hasarKaynagi[0].equals(hk)){

                Ra = Nd * Pa * Lz;
            }

            else if (hasarKaynagi[2].equals(hk)){

                Ru = (Nl + Nda) * Pu * Lz;
            }

            else if (hasarKaynagi[4].equals(hk)){

                Ra = Ra + Ru;
            }
        }

        else if(hasar[1].equals(h)){

            if(hasarKaynagi[0].equals(hk)){

                Rb = Nd * Pb * Lv;
            }

            else if((hasarKaynagi[2].equals(hk))){

                Rv = (Nl + Nda) * Pv * Lw;
            }

            else if(hasarKaynagi[4].equals(hk)){

                Rf = Rb + Rv;
            }
        }

        else if(hasar[2].equals(h)){

            if(hasarKaynagi[0].equals(hk)){

                Rc = Nd * Pc * Lu;
            }

            else if (hasarKaynagi[1].equals(hk)){

                Rm = Nm * Pm * Lu;
            }

            else if(hasarKaynagi[2].equals(hk)){

                Rw = (Nl + Nda) * Pv * Lw;
            }

            else if (hasarKaynagi[3].equals(hk)){

                Rz = (Ni - Nl) * Pz * Lu;
            }

            else{

                Ro = Rc + Rm + Rw + Rz;
            }
        }

        else{

            if(hasarKaynagi[0].equals(hk)){

                Rd = Ra + Rb + Rc;
            }

            else if(hasarKaynagi[1].equals(hk) || hasarKaynagi[2].equals(hk) || hasarKaynagi[3].equals(hk)){

                RI = Rm + Ru + Rv + Rw + Rz;
            }
        }

        //6.8 bölümlere (Zs) ayrılmış yapıların risk bileşenlerinin değerlendirilmesi

        //tek bölümlü yapılar

        double Pc1 = 0.0, Pc2 = 0.0, Pc3 = 0.0;

        double Pm1 = 0.0, Pm2 = 0.0, Pm3 = 0.0;

        Pc = 1 - (1 - Pc1) * (1 - Pc2) * (1 - Pc3);

        Pm = 1 - (1 - Pm1) * (1 - Pm2) * (1 - Pm3);

        //7.3 hizmet tesisatına düşen yıldırımdan kaynaklanan risk bileşenlerinin değerlendirilmesi (S3)

        //fiziki hasar ile ilgili bileşen (D2)
        Rv = Nl * Pv * Lv;

        //bağlı cihazların arızalanması ile ilgili bileşen (D3)
        Rw = Nl * Pw * Lw;

        //7.4 hizmet tesisatının bağlı olduğu yapıya düşen yıldırımdan kaynaklanan risk bileşenlerinin değerlendirilmesi (S1)

        //Fiziki hasar ile ilgili bileşen (D2)

        Rb = Nd * Pb * Lb;

        //iç sistemlerin arızalanması ile ilgili bileşen (D3)

        Rc = Nd * Pc * Lc;
    }



    @Override
    public void onBackPressed(){

        Toast.makeText(getApplicationContext(), "Sonuç Sayfasından Geri Gitmek için Lütfen Butonu Kullanınız.", Toast.LENGTH_LONG).show();
    }
}