package com.radsan.yldrmdankorunma_riskanalizi;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.internal.SignInHubActivity;
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

public class KayipActivity extends AppCompatActivity{

    Drawer result;
    private AccountHeader headerResult = null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_kayip);

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

                                Intent in = new Intent(KayipActivity.this, SignInActivity.class);
                                in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(in);
                                Bungee.slideRight(KayipActivity.this);
                            }

                            else if(drawerItem.getIdentifier() == 2){

                                Intent in = new Intent(KayipActivity.this, TumAnalizlerActivity.class);
                                in.putExtra("displayName", displayName);
                                in.putExtra("displayEmail", displayEmail);
                                in.putExtra("displayPhotoUrl", displayPhotoUrl);
                                startActivity(in);
                                Bungee.zoom(KayipActivity.this);
                            }

                            else if(drawerItem.getIdentifier() == 3){

                                //ayarlar
                                Intent in = new Intent(KayipActivity.this, AyarlarActivity.class);
                                in.putExtra("displayName", displayName);
                                in.putExtra("displayEmail", displayEmail);
                                in.putExtra("displayPhotoUrl", displayPhotoUrl);
                                startActivity(in);
                                Bungee.zoom(KayipActivity.this);
                            }

                            else if (drawerItem.getIdentifier() == 4){

                                FirebaseAuth.getInstance().signOut();
                                Intent i = new Intent(KayipActivity.this, SignInActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                                Bungee.slideRight(KayipActivity.this);
                            }
                        }
                        //istenilen event gerçekleştikten sonra drawer'ı kapat ->
                        return false;
                    }
                })
                .build();
        //



        //******************************************************************************************




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

                in.putExtra("displayName", displayName);
                in.putExtra("displayEmail", displayEmail);
                in.putExtra("displayPhotoUrl", displayPhotoUrl);

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
