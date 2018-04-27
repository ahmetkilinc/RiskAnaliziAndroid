package com.radsan.yldrmdankorunma_riskanalizi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import spencerstudios.com.bungeelib.Bungee;

public class SignInActivity extends AppCompatActivity {

    //db için JSON parser ve dialog
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    private static String url_yeni_kullanici = "https://ahmetkilinc.net/riskandroid/yeni_kullanici.php";
    private static final String TAG_SUCCESS = "success";
    private JSONObject json;

    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 0 ;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private com.google.android.gms.common.SignInButton signInButton;
    private GoogleApiClient mGoogleApiClient;

    private Button signOutButton;
    private Button btnYeniRiskAnalizi;
    private Button btnTumAnalizleriGor;
    private TextView tvName;
    private TextView tvEposta;
    private ImageView ivProfilPic;

    private String displayName = "";
    private String displayEmail = "";
    private String displayPhotoUrl;

    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_sign_in);

        signInButton = findViewById(R.id.sign_in_button);
        signOutButton = findViewById(R.id.sign_out_button);
        btnYeniRiskAnalizi = findViewById(R.id.buttonYeniRiskAnalizi);
        btnTumAnalizleriGor = findViewById(R.id.buttonTumAnalizleriGor);
        tvName = findViewById(R.id.textViewAd);
        tvEposta = findViewById(R.id.textViewEposta);
        ivProfilPic = findViewById(R.id.imageViewProfilePic);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)

                .enableAutoManage(this , new GoogleApiClient.OnConnectionFailedListener() {

                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                } /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                user = firebaseAuth.getCurrentUser();
                if (user != null) {

                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    signInButton.setVisibility(View.GONE);
                    signOutButton.setVisibility(View.VISIBLE);
                    btnYeniRiskAnalizi.setVisibility(View.VISIBLE);
                    btnTumAnalizleriGor.setVisibility(View.VISIBLE);
                    ivProfilPic.setVisibility(View.VISIBLE);

                    displayName = user.getDisplayName();
                    displayEmail = user.getEmail();
                    displayPhotoUrl = user.getPhotoUrl().toString();

                    if(user.getDisplayName() != null){

                        tvName.setText(user.getDisplayName().toString());
                        tvEposta.setText(user.getEmail().toString());
                        Glide.with(getApplicationContext())
                                .load(displayPhotoUrl)
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true)
                                .into(ivProfilPic);
                    }
                }

                else {

                    // User is signed out
                    signInButton.setVisibility(View.VISIBLE);
                    signOutButton.setVisibility(View.GONE);
                    btnTumAnalizleriGor.setVisibility(View.GONE);
                    btnYeniRiskAnalizi.setVisibility(View.GONE);
                    ivProfilPic.setVisibility(View.GONE);

                    tvName.setText("".toString());
                    tvEposta.setText("".toString());

                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        signInButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                signIn();
            }
        });

        btnYeniRiskAnalizi.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Intent i = new Intent(SignInActivity.this, MainActivity.class);
                i.putExtra("displayName", displayName);
                i.putExtra("displayEmail", displayEmail);
                i.putExtra("displayPhotoUrl", displayPhotoUrl);
                System.out.println(displayPhotoUrl);
                startActivity(i);
                Bungee.zoom(SignInActivity.this);
            }
        });

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {

                            @Override
                            public void onResult(Status status) {

                                signInButton.setVisibility(View.VISIBLE);
                                signOutButton.setVisibility(View.GONE);
                                btnTumAnalizleriGor.setVisibility(View.GONE);
                                btnYeniRiskAnalizi.setVisibility(View.GONE);
                                ivProfilPic.setVisibility(View.GONE);
                                //emailTextView.setText(" ".toString());
                                tvName.setText(" ".toString());
                                tvEposta.setText(" ".toString());
                            }
                        });
            }
            // ..
        });

        btnTumAnalizleriGor.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent in = new Intent(SignInActivity.this, TumAnalizlerActivity.class);
                in.putExtra("displayName", displayName);
                in.putExtra("displayEmail", displayEmail);
                in.putExtra("displayPhotoUrl", displayPhotoUrl);
                startActivity(in);
                Bungee.zoom(SignInActivity.this);
            }
        });
    }

    private void signIn() {

        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        //Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {

            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {

                //Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);

            } else {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
    }

    @Override
    public void onStart() {

        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {

        super.onStop();
        if (mAuthListener != null) {

            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        if (task.isSuccessful()){

                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            new CreateNewUser().execute();
                        }
                        else{

                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(SignInActivity.this, "Internet bağlantınızı kontrol ettikten sonra tekrar deneyiniz.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    class CreateNewUser extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            pDialog = new ProgressDialog(SignInActivity.this);
            pDialog.setMessage("Analiz Sonucunuz Kaydediliyor...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating user
         * */
        protected String doInBackground(String... args) {

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("kullanici_ad", displayName));
            params.add(new BasicNameValuePair("kullanici_email", displayEmail));
            params.add(new BasicNameValuePair("kullanici_foto", displayPhotoUrl));

            // getting JSON Object
            // Note that create product url accepts POST method
            json = jsonParser.makeHttpRequest(url_yeni_kullanici,
                    "POST", params);

            // check log cat for response
            Log.d("Create Response", json.toString());

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
                    //Intent i = new Intent(SonucActivity.this, SignInActivity.class);
                    //startActivity(i);

                    Log.d("hoooo:", "giriş yapıldı. kayıt yapıldı.");
                    Toast.makeText(getApplicationContext(), "Başarıyla Giriş Yaptınız.", Toast.LENGTH_LONG).show();

                    // closing this screen
                    //finish();
                } else if (success == 2){

                    Toast.makeText(getApplicationContext(), "Giriş Başarılı!", Toast.LENGTH_LONG).show();
                    Log.d("hoooo:", "giriş yapıldı ama kayıt yapılmadı.");
                    // failed to create product
                }

                else{

                    Log.d("hoooo:", "giriş yapıldı, ne kayıt???.");
                    Toast.makeText(getApplicationContext(), "Kendi sunucularımızda bir aksilik yaşıyoruz, uygulamayı bu şekilde kullanmaya devam ederseniz aldığınız analiz sonuvu malesef bize ulaşmayacaktır.", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {

                e.printStackTrace();
            }
        }
    }
}
