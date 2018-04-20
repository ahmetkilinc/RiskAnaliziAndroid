package com.radsan.yldrmdankorunma_riskanalizi;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;

import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerUIUtils;

import spencerstudios.com.bungeelib.Bungee;

public class MainActivity extends AppCompatActivity{

    private static final int PROFILE_SETTING = 100000;

    double binaUzunluk = 0.0;
    double binaGenislik = 0.0;
    double binaCatiDuzlemiYerdenYukseklik = 0.0;
    double binaCatidakiNoktaninYerdenYuksekligi = 0.0;
    double binaToplamaAlani = 0.0;

    public FirebaseAuth mAuth;
    public FirebaseAuth.AuthStateListener mAuthListener;

    private GoogleApiClient mGoogleApiClient;
    public FirebaseUser user;

    String name = "";

    private AccountHeader headerResult = null;
    Drawer result;

    private String displayName = "";
    private String displayEmail = "";
    private String displayPhotoUrl = "";


    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        displayName = getIntent().getStringExtra("displayName");
        displayEmail = getIntent().getStringExtra("displayEmail");
        displayPhotoUrl = getIntent().getStringExtra("displayPhotoUrl");

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this , new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                user = firebaseAuth.getCurrentUser();

                //displayName = user.getDisplayName();
                //displayEmail = user.getEmail();
                //displayPhotoUrl = user.getPhotoUrl().toString();

                if (user != null) {


                }

                else {
                    // User is signed out
                    Toast.makeText(getApplicationContext(), "Kullanıcı Giriş Yapmadı", Toast.LENGTH_SHORT).show();
                }
                // ...
            }
        };

        //navigation drawer header

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

                                startActivity(new Intent(MainActivity.this, SignInActivity.class));
                                Bungee.slideRight(MainActivity.this);
                            }

                            else if(drawerItem.getIdentifier() == 2){

                                Intent in = new Intent(MainActivity.this, TumAnalizlerActivity.class);
                                in.putExtra("displayName", displayName);
                                in.putExtra("displayEmail", displayEmail);
                                in.putExtra("displayPhotoUrl", displayPhotoUrl);
                                startActivity(in);
                                Bungee.zoom(MainActivity.this);
                            }

                            else if(drawerItem.getIdentifier() == 3){

                                //ayarlar
                                Intent in = new Intent(MainActivity.this, AyarlarActivity.class);
                                in.putExtra("displayName", displayName);
                                in.putExtra("displayEmail", displayEmail);
                                in.putExtra("displayPhotoUrl", displayPhotoUrl);
                                startActivity(in);
                                Bungee.zoom(MainActivity.this);
                            }

                            else if (drawerItem.getIdentifier() == 4){

                                FirebaseAuth.getInstance().signOut();
                                Intent i = new Intent(MainActivity.this, SignInActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                                Bungee.slideRight(MainActivity.this);
                            }
                        }
                        //istenilen event gerçekleştikten sonra drawer'ı kapat ->
                        return false;
                    }
                })
                .build();
        //

        final EditText etUzunluk = findViewById(R.id.editTextUzunluk);
        final EditText etGenislik = findViewById(R.id.editTextGenislik);
        final EditText etCatiDuzlemiYerdenYukseklik = findViewById(R.id.editTextCatiDuzlemininYerdenYuksekligi);
        final EditText etCatidakiNoktaninYerdenYuksekligi = findViewById(R.id.editTextCatidakiNoktanınYerdenYuksekligi);
        final EditText etToplamaAlani = findViewById(R.id.editTextToplamaAlani);

        final Spinner spYapiMalzemesi = findViewById(R.id.spinnerYapiMalzemesi);
        final Spinner spCatiMalzemesi = findViewById(R.id.spinnerCatiMalzemesi);
        final Spinner spFizikselveYanginRiski = findViewById(R.id.spinnerFizikselveYanginRiski);
        final Spinner spYapininEkranlanmasi = findViewById(R.id.spinnerYapininEkranlanmasi);
        final Spinner spKablolarinEkranlanmasi = findViewById(R.id.spinnerKablolarinEkranlanmasi);


        final String yapiMalzemesi = spYapiMalzemesi.getSelectedItem().toString();
        final String catiMalzemesi = spCatiMalzemesi.getSelectedItem().toString();
        final String fizikselveYanginRiski = spFizikselveYanginRiski.getSelectedItem().toString();
        final String yapininEkranlanmasi = spYapininEkranlanmasi.getSelectedItem().toString();
        final String kablolarinEkranlanmasi = spKablolarinEkranlanmasi.getSelectedItem().toString();

        Button btnDevam1 = findViewById(R.id.buttonDevam1);

        btnDevam1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                if(TextUtils.isEmpty(etUzunluk.getText()) || TextUtils.isEmpty(etGenislik.getText()) ||
                        TextUtils.isEmpty(etCatidakiNoktaninYerdenYuksekligi.getText()) ||
                        TextUtils.isEmpty(etCatiDuzlemiYerdenYukseklik.getText()) || TextUtils.isEmpty(etToplamaAlani.getText())){

                    Toast.makeText(getApplication(), R.string.toast_uyari, Toast.LENGTH_LONG).show();
                }
                else{

                    binaUzunluk = Double.parseDouble(etUzunluk.getText().toString());
                    binaGenislik = Double.parseDouble(etGenislik.getText().toString());
                    binaCatiDuzlemiYerdenYukseklik = Double.parseDouble(etCatiDuzlemiYerdenYukseklik.getText().toString());
                    binaCatidakiNoktaninYerdenYuksekligi = Double.parseDouble(etCatidakiNoktaninYerdenYuksekligi.getText().toString());
                    binaToplamaAlani = Double.parseDouble(etToplamaAlani.getText().toString());

                    Intent in = new Intent(MainActivity.this, CevreActivity.class);

                    in.putExtra("displayName", displayName);
                    in.putExtra("displayEmail", displayEmail);
                    in.putExtra("displayPhotoUrl", displayPhotoUrl);

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

                    startActivity(in);
                    Bungee.zoom(MainActivity.this);
                }
            }
        });
    }

    @Override
    public void onBackPressed(){

        new AlertDialog.Builder(this)
                .setTitle(R.string.yapi_alert_title)
                .setMessage(R.string.yapi_alert_message)
                .setNegativeButton(R.string.yapi_alert_no, null)
                .setPositiveButton(R.string.yapi_alert_yes, new DialogInterface.OnClickListener(){

                    public void onClick(DialogInterface arg0, int arg1){

                        MainActivity.super.onBackPressed();
                        Bungee.slideRight(MainActivity.this);
                    }
                }).create().show();
    }
}