package com.radsan.yldrmdankorunma_riskanalizi;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

import spencerstudios.com.bungeelib.Bungee;

public class SignInActivity extends AppCompatActivity {

    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 0 ;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private com.google.android.gms.common.SignInButton signInButton;
    private GoogleApiClient mGoogleApiClient;
    private Button signOutButton;
    private TextView nameTextView;

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
        nameTextView = findViewById(R.id.textViewAd);

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

                    Intent i = new Intent(SignInActivity.this, MainActivity.class);
                    startActivity(i);
                    Bungee.zoom(SignInActivity.this);

                    if(user.getDisplayName() != null)
                        nameTextView.setText(user.getDisplayName().toString());
                    //emailTextView.setText(user.getEmail().toString());

                }

                else {
                    // User is signed out
                    Toast.makeText(getApplicationContext(), "Kullanıcı Giriş Yapmadı", Toast.LENGTH_SHORT).show();
                    signInButton.setVisibility(View.VISIBLE);
                    signOutButton.setVisibility(View.GONE);

                    nameTextView.setText("".toString());

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
                                //emailTextView.setText(" ".toString());
                                nameTextView.setText(" ".toString());
                            }
                        });
            }
            // ..
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

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(SignInActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

                        }



                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                       /* if (!task.isSuccessful()) {

                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(SignInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }*/
                    }
                });
    }
}
