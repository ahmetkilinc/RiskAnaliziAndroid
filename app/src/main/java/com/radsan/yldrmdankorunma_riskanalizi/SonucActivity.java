package com.radsan.yldrmdankorunma_riskanalizi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerUIUtils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.CDATASection;

import java.util.ArrayList;
import java.util.List;

import spencerstudios.com.bungeelib.Bungee;

public class SonucActivity extends AppCompatActivity{

    TextView tvCanKaybiDogrudanCarpmaRiski;
    TextView tvCanKaybiDolayliCarpmaRiski;
    TextView tvCanKaybiToplamRisk;

    TextView tvServisKaybiDogrudanCarpmaRiski;
    TextView tvServisKaybiDolayliCarpmaRiski;
    TextView tvServisKaybiToplamRisk;

    TextView tvKulturelMirasKaybiDogrudanCarpmaRiski;
    TextView tvKulturelMirasKaybiDolayliCarpmaRiski;
    TextView tvKulturelMirasKaybiToplamRisk;

    TextView tvEkonomikKayipDogrudanCarpmaRiski;
    TextView tvEkonomikKayipDolayliCarpmaRiski;
    TextView tvEkonomikKayipToplamRisk;

    //db için JSON parser ve dialog
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();

    private static String url_yeni_analiz = "https://ahmetkilinc.net/riskandroid/yeni_analiz.php";

    private static final String TAG_SUCCESS = "success";

    private JSONObject json;

    private String displayName;
    private String displayEmail;
    private String displayPhotoUrl;

    FirebaseUser user;

    Drawer result;
    private AccountHeader headerResult = null;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_sonuc);

        //alt aktiviteden gelen değerler ***

        displayName = getIntent().getStringExtra("displayName");
        displayEmail = getIntent().getStringExtra("displayEmail");
        displayPhotoUrl = getIntent().getStringExtra("displayPhotoUrl");

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

        //initialize and create the image loader logic
        DrawerImageLoader.init(new AbstractDrawerImageLoader() {
            @Override
            public void set(ImageView imageView, Uri uri, Drawable placeholder, String tag) {
                Glide.with(imageView.getContext()).load(uri).placeholder(placeholder).into(imageView);
            }

            @Override
            public void cancel(ImageView imageView) {

                Glide.clear(imageView);
            }

            @Override
            public Drawable placeholder(Context ctx, String tag) {
                //define different placeholders for different imageView targets
                //default tags are accessible via the DrawerImageLoader.Tags
                //custom ones can be checked via string. see the CustomUrlBasePrimaryDrawerItem LINE 111
                if (DrawerImageLoader.Tags.PROFILE.name().equals(tag)) {
                    return DrawerUIUtils.getPlaceHolder(ctx);
                } else if (DrawerImageLoader.Tags.ACCOUNT_HEADER.name().equals(tag)) {
                    return new IconicsDrawable(ctx).iconText(" ").backgroundColorRes(com.mikepenz.materialdrawer.R.color.primary).sizeDp(56);
                } else if ("customUrlItem".equals(tag)) {
                    return new IconicsDrawable(ctx).iconText(" ").backgroundColorRes(R.color.md_red_500).sizeDp(56);
                }

                //we use the default one for
                //DrawerImageLoader.Tags.PROFILE_DRAWER_ITEM.name()

                return super.placeholder(ctx, tag);
            }
        });
        //image loader logic.

        final IProfile profile = new ProfileDrawerItem().withName(displayName).withEmail(displayEmail).withIcon(displayPhotoUrl).withIdentifier(100);

        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(true)
                .withHeaderBackground(R.drawable.headerradsan)
                .addProfiles(
                        profile

                        //don't ask but google uses 14dp for the add account icon in gmail but 20dp for the normal icons (like manage account)
                        //new ProfileSettingDrawerItem().withName("Add Account").withDescription("Add new GitHub Account").withIdentifier(PROFILE_SETTING)
                        //new ProfileSettingDrawerItem().withName("Manage Account").withIcon(GoogleMaterial.Icon.gmd_settings).withIdentifier(100001)
                )
                .withSavedInstance(savedInstanceState)
                .build();


        //adding navigation drawer
        final Toolbar toolbar = findViewById(R.id.toolbar);

        new DrawerBuilder().withActivity(this).build();

        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem itemText = new PrimaryDrawerItem().withName("").withSelectable(false);

        PrimaryDrawerItem itemBasaDon = new PrimaryDrawerItem().withIdentifier(1).withName(
                R.string.navigation_item_basa_don).withSelectable(false).withIcon(R.drawable.basadon);

        PrimaryDrawerItem itemTumSonuclariGor = new PrimaryDrawerItem().withIdentifier(2).withName(
                R.string.navigation_item_tum_sonuclari_gor).withSelectable(false).withIcon(R.drawable.sonuclar);

        PrimaryDrawerItem itemAyarlar = new PrimaryDrawerItem().withIdentifier(3).withName(
                R.string.navigation_item_ayarlar).withSelectable(false).withIcon(R.drawable.ayarlar);

        PrimaryDrawerItem itemCikisYap = new PrimaryDrawerItem().withIdentifier(4).withName(
                R.string.navigation_item_cikis).withSelectable(false).withIcon(R.drawable.cikis);
        //SecondaryDrawerItem item2 = new SecondaryDrawerItem().withIdentifier(2).withName(R.string.navigation_item_settings);

        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        itemText,
                        itemBasaDon,
                        itemTumSonuclariGor,
                        new DividerDrawerItem(),
                        itemAyarlar,
                        itemCikisYap
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        if (drawerItem != null){

                            if (drawerItem.getIdentifier() == 1){

                                Intent in = new Intent(SonucActivity.this, SignInActivity.class);
                                in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(in);
                                Bungee.slideRight(SonucActivity.this);
                            }

                            else if(drawerItem.getIdentifier() == 2){

                                //tüm analizler
                                Intent in = new Intent(SonucActivity.this, TumAnalizlerActivity.class);
                                in.putExtra("displayName", displayName);
                                in.putExtra("displayEmail", displayEmail);
                                in.putExtra("displayPhotoUrl", displayPhotoUrl);
                                startActivity(in);
                                Bungee.zoom(SonucActivity.this);
                            }

                            else if(drawerItem.getIdentifier() == 3){

                                //ayarlar
                                Intent in = new Intent(SonucActivity.this, AyarlarActivity.class);
                                in.putExtra("displayName", displayName);
                                in.putExtra("displayEmail", displayEmail);
                                in.putExtra("displayPhotoUrl", displayPhotoUrl);
                                startActivity(in);
                                Bungee.zoom(SonucActivity.this);
                            }

                            else if (drawerItem.getIdentifier() == 4){

                                FirebaseAuth.getInstance().signOut();
                                Intent i = new Intent(SonucActivity.this, SignInActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                                Bungee.slideRight(SonucActivity.this);
                            }
                        }
                        //istenilen event gerçekleştikten sonra drawer'ı kapat -
                        // >
                        return false;
                    }
                })
                .build();
        //



        //******************************************************************************************





        //Activitydeki eleman declarations
        Button btnSonucBasadon = findViewById(R.id.buttonSonucBasadon);
        Button btnSonucKaydet = findViewById(R.id.buttonSonucKaydet);

        TextView tvCanKaybiKabulEdilirRisk = findViewById(R.id.textViewCanKaybiKabulEdilirRisk);
        tvCanKaybiDogrudanCarpmaRiski = findViewById(R.id.textViewCanKaybiDogrudanCarpmaRiski);
        tvCanKaybiDolayliCarpmaRiski = findViewById(R.id.textViewCanKaybiDolayliCarpmaRiski);
        tvCanKaybiToplamRisk = findViewById(R.id.textViewCanKaybiToplamRisk);

        TextView tvServisKaybiKabulEdilirRisk = findViewById(R.id.textViewServisKaybiKabulEdilirRisk);
        tvServisKaybiDogrudanCarpmaRiski = findViewById(R.id.textViewServisKaybiDogrudanCarpmaRiski);
        tvServisKaybiDolayliCarpmaRiski = findViewById(R.id.textViewServisKaybiDolayliCarpmaRiski);
        tvServisKaybiToplamRisk = findViewById(R.id.textViewServisKaybiToplamRisk);

        TextView tvKulturelMirasKaybiKabulEdilirRisk = findViewById(R.id.textViewKulturelMirasKaybiKabulEdilirRisk);
        tvKulturelMirasKaybiDogrudanCarpmaRiski = findViewById(R.id.textViewKulturelMirasKaybiDogrudanCarpmaRiski);
        tvKulturelMirasKaybiDolayliCarpmaRiski = findViewById(R.id.textViewKulturelMirasKaybiDolayliCarpmaRiski);
        tvKulturelMirasKaybiToplamRisk = findViewById(R.id.textViewKulturelMirasKaybiToplamRisk);

        TextView tvEkonomikKayipKabulEdilirRisk = findViewById(R.id.textViewEkonomikKayipKabulEdilirRisk);
        tvEkonomikKayipDogrudanCarpmaRiski = findViewById(R.id.textViewEkonomikKayipDogrudanCarpmaRiski);
        tvEkonomikKayipDolayliCarpmaRiski = findViewById(R.id.textViewEkonomikKayipDolayliCarpmaRiski);
        tvEkonomikKayipToplamRisk = findViewById(R.id.textViewEkonomikKayipToplamRisk);
        //Activity eleman declarations

        tvEkonomikKayipToplamRisk.setText(Double.toString(binaUzunluk));
        tvCanKaybiDolayliCarpmaRiski.setText(Double.toString(binaGenislik));



        btnSonucBasadon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(SonucActivity.this, SignInActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        btnSonucKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // creating new product in background thread
                new CreateNewProduct().execute();
            }
        });
    }


    class CreateNewProduct extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            pDialog = new ProgressDialog(SonucActivity.this);
            pDialog.setMessage("Analiz Sonucunuz Kaydediliyor...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {

            String a = "100";
            String b = "12";
            String c = "5";

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("a_adi", "new_analiz_adi_sonra_duzelt"));
            params.add(new BasicNameValuePair("dcr_can_kaybi", a));
            params.add(new BasicNameValuePair("dcr_servis_kaybi", b));
            params.add(new BasicNameValuePair("dcr_kulturel_miras_kaybi", c));
            params.add(new BasicNameValuePair("dcr_ekonomik_kayip", "15"));
            params.add(new BasicNameValuePair("dolayli_can_kaybi", "12"));
            params.add(new BasicNameValuePair("dolayli_servis_kaybi", "1.45"));
            params.add(new BasicNameValuePair("dolayli_kulturel_miras_kaybi", "2.88"));
            params.add(new BasicNameValuePair("dolayli_ekonomik_kayip", "77"));
            params.add(new BasicNameValuePair("tr_can_kaybi", "44"));
            params.add(new BasicNameValuePair("tr_servis_kaybi", "10"));
            params.add(new BasicNameValuePair("tr_kulturel_miras_kaybi", "5"));
            params.add(new BasicNameValuePair("tr_ekonomik_kayip", "45"));

            /*params.add(new BasicNameValuePair("a_adi", "new_analiz_adi_sonra_düzelt"));
            params.add(new BasicNameValuePair("dcr_can_kaybi", tvCanKaybiDogrudanCarpmaRiski.toString()));
            params.add(new BasicNameValuePair("dcr_servis_kaybi", tvServisKaybiDogrudanCarpmaRiski.toString()));
            params.add(new BasicNameValuePair("dcr_kulturel_miras_kaybi", tvKulturelMirasKaybiDogrudanCarpmaRiski.toString()));
            params.add(new BasicNameValuePair("dcr_ekonomik_kayip", tvEkonomikKayipDogrudanCarpmaRiski.toString()));
            params.add(new BasicNameValuePair("dolayli_can_kaybi", tvCanKaybiDolayliCarpmaRiski.toString()));
            params.add(new BasicNameValuePair("dolayli_servis_kaybi", tvServisKaybiDolayliCarpmaRiski.toString()));
            params.add(new BasicNameValuePair("dolayli_kulturel_miras_kaybi", tvKulturelMirasKaybiDolayliCarpmaRiski.toString()));
            params.add(new BasicNameValuePair("dolayli_ekonomik_kayip", tvEkonomikKayipDolayliCarpmaRiski.toString()));
            params.add(new BasicNameValuePair("tr_can_kaybi", tvCanKaybiToplamRisk.toString()));
            params.add(new BasicNameValuePair("tr_servis_kaybi", tvServisKaybiToplamRisk.toString()));
            params.add(new BasicNameValuePair("tr_kulturel_miras_kaybi", tvKulturelMirasKaybiToplamRisk.toString()));
            params.add(new BasicNameValuePair("tr_ekonomik_kayip", tvEkonomikKayipToplamRisk.toString()));*/


            // getting JSON Object
            // Note that create product url accepts POST method
            json = jsonParser.makeHttpRequest(url_yeni_analiz,
                    "POST", params);

            // check log cat for response
            Log.d("Create Response", json.toString());

            // check for success tag
            /*try {
                int success = json.getInt(TAG_SUCCESS);

                //Toast.makeText(getApplicationContext(), json.getInt(TAG_SUCCESS), Toast.LENGTH_LONG).show();

                if (success == 1) {
                    // successfully created product
                    //Intent i = new Intent(SonucActivity.this, MainActivity.class);
                    //startActivity(i);

                    //Toast.makeText(getApplicationContext(), "oo o olmuş.", Toast.LENGTH_LONG).show();

                    Log.d("a", "success = 1");

                    // closing this screen
                    finish();
                } else {
                    //Toast.makeText(SonucActivity.this, "oo o olmuş mu?.", Toast.LENGTH_LONG).show();
                    // failed to create product
                }
            } catch (JSONException e) {

                e.printStackTrace();
            }*/

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();

            try {

                int success = json.getInt(TAG_SUCCESS);
                //Toast.makeText(getApplicationContext(), json.getInt(TAG_SUCCESS), Toast.LENGTH_LONG).show();

                if (success == 1) {
                    // successfully created product
                    Intent i = new Intent(SonucActivity.this, SignInActivity.class);
                    startActivity(i);

                    Toast.makeText(getApplicationContext(), "Analiziniz Kaydedildi.", Toast.LENGTH_LONG).show();

                    // closing this screen
                    finish();
                } else {

                    Toast.makeText(getApplicationContext(), "Bir Hata Oluştu, Lütfen Tekrar Deneyiniz.", Toast.LENGTH_LONG).show();
                    // failed to create product
                }
            } catch (JSONException e) {

                e.printStackTrace();
            }
        }
    }







    @Override
    public void onBackPressed(){

        Toast.makeText(getApplicationContext(), "Sonuç Sayfasından Geri Gitmek için Lütfen Butonu Kullanınız.", Toast.LENGTH_LONG).show();
    }
}