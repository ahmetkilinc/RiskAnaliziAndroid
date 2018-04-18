package com.radsan.yldrmdankorunma_riskanalizi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Spinner;
import android.widget.Toast;


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
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;

import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

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

        final IProfile profile = new ProfileDrawerItem().withName(displayName).withEmail(displayEmail).withIcon(displayPhotoUrl).withIdentifier(100);

        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(true)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(
                        profile,

                        //don't ask but google uses 14dp for the add account icon in gmail but 20dp for the normal icons (like manage account)
                        new ProfileSettingDrawerItem().withName("Add Account").withDescription("Add new GitHub Account").withIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_add).actionBar().paddingDp(5).colorRes(R.color.material_drawer_primary_text)).withIdentifier(PROFILE_SETTING),
                        new ProfileSettingDrawerItem().withName("Manage Account").withIcon(GoogleMaterial.Icon.gmd_settings).withIdentifier(100001)
                )
                .withSavedInstance(savedInstanceState)
                .build();


        //adding navigation drawer
        final Toolbar toolbar = findViewById(R.id.toolbar);

        new DrawerBuilder().withActivity(this).build();

        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.navigation_item_home);
        SecondaryDrawerItem item2 = new SecondaryDrawerItem().withIdentifier(2).withName(R.string.navigation_item_settings);

        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        item1,
                        new DividerDrawerItem(),
                        item2,
                        new SecondaryDrawerItem().withName(R.string.navigation_item_settings)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                        return true;
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
        Button cikisYap = findViewById(R.id.cikisMain);

        cikisYap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(R.string.yapi_alert_title)
                        .setMessage(R.string.yapi_alert_message)
                        .setNegativeButton(R.string.yapi_alert_no, null)
                        .setPositiveButton(R.string.yapi_alert_yes, new DialogInterface.OnClickListener(){

                            public void onClick(DialogInterface arg0, int arg1){

                                FirebaseAuth.getInstance().signOut();
                                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                                        new ResultCallback<Status>() {
                                            @Override
                                            public void onResult(Status status) {


                                            }
                                        });

                                startActivity(new Intent(MainActivity.this, SignInActivity.class));
                            }
                        }).create().show();
            }
        });

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
                        //startActivity(new Intent(MainActivity.this, SignInActivity.class));


                    }
                }).create().show();
    }
}