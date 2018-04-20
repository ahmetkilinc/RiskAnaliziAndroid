package com.radsan.yldrmdankorunma_riskanalizi;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
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

import spencerstudios.com.bungeelib.Bungee;

public class ServisActivity extends AppCompatActivity {

    Drawer result;
    private AccountHeader headerResult = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_servis);

        //alt aktiviteden gelen değerler ***

        final String displayName = getIntent().getStringExtra("displayName");
        final String displayEmail = getIntent().getStringExtra("displayEmail");
        final String displayPhotoUrl = getIntent().getStringExtra("displayPhotoUrl");

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

                                startActivity(new Intent(ServisActivity.this, SignInActivity.class));
                            }

                            else if(drawerItem.getIdentifier() == 2){

                                startActivity(new Intent(ServisActivity.this, TumAnalizlerActivity.class));
                                Bungee.zoom(ServisActivity.this);
                            }

                            else if(drawerItem.getIdentifier() == 3){

                                //ayarlar
                                startActivity(new Intent(ServisActivity.this, AyarlarActivity.class));
                                Bungee.zoom(ServisActivity.this);
                            }

                            else if (drawerItem.getIdentifier() == 4){

                                FirebaseAuth.getInstance().signOut();
                                startActivity(new Intent(ServisActivity.this, SignInActivity.class));
                                Bungee.slideRight(ServisActivity.this);
                            }
                        }
                        //istenilen event gerçekleştikten sonra drawer'ı kapat ->
                        return false;
                    }
                })
                .build();
        //



        //******************************************************************************************




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

                    in.putExtra("displayName", displayName);
                    in.putExtra("displayEmail", displayEmail);
                    in.putExtra("displayPhotoUrl", displayPhotoUrl);

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
