package rm.iamjosephvarghese.deadlock2k18;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Splash extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener{


    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    Boolean loggedIn;


    SignInButton signIn;

    private static final String TAG = "Splash";
    private static final int RC_SIGN_IN = 1;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sharedPreferences = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        signIn = findViewById(R.id.sign_in_button);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        //initially setting visibility to invisible
        signIn.setVisibility(View.INVISIBLE);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();


        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

//                        Toast.makeText(Splash.this,"Error",Toast.LENGTH_LONG).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        mAuth = FirebaseAuth.getInstance();
//        getting user
        user = mAuth.getCurrentUser();
// getting UID here
        if (user != null){
            Log.d("UID",mAuth.getCurrentUser().getUid());
        }else{
            Log.d("UID","Not signed in");
        }

        ///////////


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                Toast.makeText(Splash.this, "Before Logged in.",
//                        Toast.LENGTH_SHORT).show();
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d("UID",user.getUid());
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
//                    Toast.makeText(Splash.this, "Logged in.",
//                            Toast.LENGTH_SHORT).show();

//
//
//
//                    getUID(user);
                    Log.d("UID",user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");

                }
                updateUI(user);
            }
        };

        loggedIn = sharedPreferences.getBoolean("isLoggedIn",false);
        if(loggedIn){

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {


                    Log.d("UID 2222",user.getUid());



                        Intent loginIntent = new Intent(Splash.this,Firestore.class);
                        startActivity(loginIntent);
                        finish();

                }
            },3000);

        }else {
            signIn.setVisibility(View.VISIBLE);
        }

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            Log.d(TAG,"Intent received");
            if (result.isSuccess()) {

                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                Log.d(TAG,result.getStatus().toString());
                updateUI(null);
            }
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
                        getUID();


                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(Splash.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }



    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // [END signin]

    private void signOut() {
        // Firebase sign out
        mAuth.signOut();

        // Google sign out
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        updateUI(null);
                    }
                });
    }



    private void updateUI(FirebaseUser user) {

        Log.d("here","..........");
        if (user != null) {

            Log.d("UID ui",user.getUid());

        } else {

        }
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
//        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }


    public void getUID(){
        editor.putBoolean("isLoggedIn",true);
        editor.commit();
        Log.d("Here","success");
        FirebaseAuth newAuth = FirebaseAuth.getInstance();
        FirebaseUser newUser = mAuth.getCurrentUser();

        final FirebaseFirestore db = FirebaseFirestore.getInstance();


        String uid = newUser.getUid();
        Log.d("UID.....",uid);

        String photoURL = newUser.getPhotoUrl().toString();
        Log.d("photoURL",photoURL);




        editor.putString("UID",uid);
        editor.putString("photoURL",photoURL);
        editor.commit();

        DocumentReference documentReference = db.collection("users").document(uid);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if(documentSnapshot.exists()){
                    Intent previousUser = new Intent(Splash.this,Firestore.class);
                    Log.d("Splash","if");
                    startActivity(previousUser);
                    finish();
                }else{
                    Intent newUser = new Intent(Splash.this,Collect.class);
                    Log.d("Splash","else");
                    startActivity(newUser);
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

//        Intent afterLogin = new Intent(Splash.this,Firestore.class);
//        startActivity(afterLogin);



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        editor.commit();
    }


    @Override
    protected void onPause() {
        super.onPause();
        editor.commit();
    }
}

