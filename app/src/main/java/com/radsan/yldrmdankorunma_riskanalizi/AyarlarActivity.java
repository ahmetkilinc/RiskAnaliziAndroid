package com.radsan.yldrmdankorunma_riskanalizi;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

import spencerstudios.com.bungeelib.Bungee;

public class AyarlarActivity extends AppCompatActivity {

    Drawer result;
    private AccountHeader headerResult = null;
    private static final String TAG = "AyarlarActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_ayarlar);

        final String displayName = getIntent().getStringExtra("displayName");
        final String displayEmail = getIntent().getStringExtra("displayEmail");
        final String displayPhotoUrl = getIntent().getStringExtra("displayPhotoUrl");

        Button btnProfilimiDuzenle = findViewById(R.id.buttonProfilDuzenle);
        Button btnAnalizSecSil = findViewById(R.id.buttonAnalizSecveSil);
        Button btnTumAnalizlerimiSil = findViewById(R.id.buttonTumAnalizleriSil);
        Button btnHesabiSil = findViewById(R.id.buttonHesabiSil);
        Button btnHakkinda = findViewById(R.id.buttonHakkinda);

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

                                //ilk sayfa
                                Intent in = new Intent(AyarlarActivity.this, SignInActivity.class);
                                in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(in);
                                Bungee.slideRight(AyarlarActivity.this);
                            }

                            else if(drawerItem.getIdentifier() == 2){

                                //tüm analizler
                                Intent in = new Intent(AyarlarActivity.this, TumAnalizlerActivity.class);
                                in.putExtra("displayName", displayName);
                                in.putExtra("displayEmail", displayEmail);
                                in.putExtra("displayPhotoUrl", displayPhotoUrl);
                                startActivity(in);
                                Bungee.zoom(AyarlarActivity.this);
                            }

                            else if(drawerItem.getIdentifier() == 3){

                                //ayarlar
                                result.closeDrawer();
                            }

                            else if (drawerItem.getIdentifier() == 4){

                                FirebaseAuth.getInstance().signOut();
                                Intent i = new Intent(AyarlarActivity.this, SignInActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                                Bungee.slideRight(AyarlarActivity.this);
                            }
                        }
                        //istenilen event gerçekleştikten sonra drawer'ı kapat ->
                        return false;
                    }
                })
                .build();
        //

        //******************************************************************************************

        btnProfilimiDuzenle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(AyarlarActivity.this, ProfilimiDuzenleActivity.class);
                i.putExtra("displayName", displayName);
                i.putExtra("displayEmail", displayEmail);
                i.putExtra("displayPhotoUrl", displayPhotoUrl);
                startActivity(i);
                Bungee.zoom(AyarlarActivity.this);
            }
        });

        btnHakkinda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(AyarlarActivity.this, HakkindaActivity.class);
                startActivity(i);
                Bungee.zoom(AyarlarActivity.this);
            }
        });

        btnHesabiSil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(AyarlarActivity.this)
                        .setTitle(R.string.yapi_alert_title)
                        .setMessage(R.string.yapi_alert_message)
                        .setNegativeButton(R.string.yapi_alert_no, null)
                        .setPositiveButton(R.string.yapi_alert_yes, new DialogInterface.OnClickListener(){

                            public void onClick(DialogInterface arg0, int arg1){

                                //kullanıcı evet dediyse -> firebaseden hesabı sil.
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                user.delete()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                //silme işleminde bir sıkıntı çıkmadıysa kullanıcıyı giriş sayfasına yönlendir.
                                                if (task.isSuccessful()) {

                                                    Toast.makeText(getApplicationContext(), "Hesabınız Silinmiştir.", Toast.LENGTH_LONG).show();
                                                    Intent i = new Intent(AyarlarActivity.this, SignInActivity.class);
                                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    startActivity(i);
                                                    Bungee.slideRight(AyarlarActivity.this);
                                                }
                                                //silme işlemi başarısız olduysa kullanıcıyı tekrar giriş yapması için giriş sayfasına yönlendir.
                                                else{

                                                    Toast.makeText(getApplicationContext(), "Hesabınızı Silebilmek için Lütfen Tekrar Giriş Yapınız.", Toast.LENGTH_SHORT).show();
                                                    FirebaseAuth.getInstance().signOut();
                                                    Intent i = new Intent(AyarlarActivity.this, SignInActivity.class);
                                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    startActivity(i);
                                                    Bungee.slideRight(AyarlarActivity.this);
                                                }
                                            }
                                        });


























                            }
                        }).create().show();













            }
        });

    }

    @Override
    public void onBackPressed(){

        AyarlarActivity.super.onBackPressed();
        Bungee.slideRight(AyarlarActivity.this);
    }
}
