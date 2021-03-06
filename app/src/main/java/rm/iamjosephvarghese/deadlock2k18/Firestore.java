package rm.iamjosephvarghese.deadlock2k18;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bogdwellers.pinchtozoom.ImageMatrixTouchHandler;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.hash.Hashing;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import java.nio.charset.Charset;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Firestore extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private FirebaseUser user;

    String currentHash,previousHash;

    String photoURL;
    String levelString;
    int level;

    PhotoView imageView;
//    ImageView imageView;
    TextView levelText;
    EditText answer;

    Button submit;

    String toBeHashed,generatedHash;
    String noSpace;


    SharedPreferences sharedPreferences;
    //assume this is the user id generated from Google Auth


    MaterialDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firestore);

        Typeface bebas = Typeface.createFromAsset(getAssets(),  "fonts/bebasneue.ttf");

        sharedPreferences = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);


        imageView = findViewById(R.id.imageView);
        levelText = findViewById(R.id.levelText);
        submit = findViewById(R.id.submit);
        answer = findViewById(R.id.answer);

        levelText.setTypeface(bebas);
        submit.setTypeface(bebas);
        answer.setTypeface(bebas);

        submit.setVisibility(View.INVISIBLE);
        answer.setVisibility(View.INVISIBLE);

        builder = new MaterialDialog.Builder(Firestore.this)
                .title("Correct Answer!")
                .content("Loading Next Question")
                .progress(true,0)
                .progressIndeterminateStyle(true)
                .cancelable(false);
//        dialog = builder.build();


        final SweetAlertDialog dialog = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Correct")
                .setContentText("Your Answer Is Correct!")
                .hideConfirmButton();

        final SweetAlertDialog dialogError = new SweetAlertDialog(this,SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Incorrect")
                .setContentText("Please Try Again!")
                .hideConfirmButton();

        final SweetAlertDialog comingDialog = new SweetAlertDialog(this,SweetAlertDialog.NORMAL_TYPE)
                .setTitleText("Coming Soon!")
                .setContentText("Next Level Will be Updated")
                .hideConfirmButton();


        final SweetAlertDialog loadingDialog = new SweetAlertDialog(this,SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText("Loading")
                .setContentText("Loading Question")
                .hideConfirmButton();


        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference userRef = db.collection("users").document(user.getUid());

        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                currentHash = documentSnapshot.get("currentHash").toString();
                Log.d("currentHash",currentHash);
                previousHash = documentSnapshot.get("previousHash").toString();
                Log.d("previousHash",previousHash);


                DocumentReference questionRef = db.collection("q").document("questions").collection(currentHash).document(previousHash);
                questionRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {



                        if(documentSnapshot.get("photoURL") == null){

                            Log.d("null","null");

                            comingDialog.show();

                            submit.setVisibility(View.INVISIBLE);
                            answer.setVisibility(View.INVISIBLE);
                            photoURL = "";
                            Glide.with(getApplicationContext()).load(photoURL).placeholder(R.drawable.comingsoon).into(imageView);

                        }else{

                            loadingDialog.show();

                            Log.d("not null","not null");

                            photoURL = documentSnapshot.get("photoURL").toString();
                            Log.d("photoUrl",photoURL);

                            Glide.with(getApplicationContext()).load(photoURL).placeholder(R.drawable.comingsoon)
                                    .listener(new RequestListener<String, GlideDrawable>() {
                                        @Override
                                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                            loadingDialog.dismiss();
                                            return false;
                                        }

                                        @Override
                                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                            loadingDialog.dismiss();
                                            return false;
                                        }
                                    })
                                    .into(imageView);

                            submit.setVisibility(View.VISIBLE);
                            answer.setVisibility(View.VISIBLE);

                        }


                        levelString = documentSnapshot.get("level").toString();
                        level = Integer.parseInt(levelString);



                        levelText.setText("Level " + levelString);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!answer.getText().toString().equals("")){

                    String lowerCase = answer.getText().toString().toLowerCase();
                    String[] parts = lowerCase.split(" ");
                    noSpace = TextUtils.join("",parts);
//                    toBeHashed = answer.getText().toString() + photoURL + currentHash;
                    toBeHashed = noSpace + photoURL + currentHash;

                    Log.d("tobehashed",toBeHashed);

                    generatedHash = Hashing.sha256()
                            .hashString(toBeHashed, Charset.forName("UTF-8"))
                            .toString();


                    final DocumentReference logRef = db.collection("logs").document();
                    final DocumentReference userUpdateRef = db.collection("users").document(user.getUid());

                    DocumentReference checkRef = db.collection("q").document("questions").collection(generatedHash).document(currentHash);

                    checkRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()){

                                Log.d("Answer","correct");



                                WriteBatch batch = db.batch();
                                batch.set(logRef,new LogData(user.getUid(),answer.getText().toString(),level,user.getDisplayName(),user.getEmail(),sharedPreferences.getString("mobno",null),new Date()));


                                batch.update(userUpdateRef,"previousHash",currentHash);
                                batch.update(userUpdateRef,"currentHash",generatedHash);
                                batch.update(userUpdateRef,"currentLevel",level);



                                batch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("batch","success");
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("batch","error");
                                        Log.d("batch",e.toString());
                                    }
                                });


                                Log.d("refresh","");


                                dialog.show();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        dialog.dismiss();
                                       finish();
                                       startActivity(getIntent());
                                    }
                                },3000);

                            }else{
                                Log.d("Answer","incorrect");
                                logRef.set(new LogData(user.getUid(),answer.getText().toString(),level,user.getDisplayName(),user.getEmail(),sharedPreferences.getString("mobno",null),new Date())).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("incorrectLog","success");

                                        dialogError.show();
                                        Log.d("after","show");

                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                Log.d("inside","run");
                                                dialogError.dismiss();
                                            }
                                        },3000);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("incorrectLog","error");
                                    }
                                });
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });

                    Log.d("generatedHash",generatedHash);
                }
            }
        });

    }

}
