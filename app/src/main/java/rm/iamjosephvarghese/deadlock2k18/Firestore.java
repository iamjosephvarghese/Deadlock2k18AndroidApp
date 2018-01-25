package rm.iamjosephvarghese.deadlock2k18;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.security.MessageDigest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Firestore extends AppCompatActivity {



    private FirebaseAuth mAuth;
    private FirebaseUser user;


    String currentHash,previousHash;

    String photoURL;
    int level;

    ImageView  imageView;
    TextView levelText;

    Button submit;


    SharedPreferences sharedPreferences;
    //assume this is the user id genrated from google auth


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firestore);


//        sharedPreferences = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
//        user=sharedPreferences.getString("UID",null);
//        Log.d("user",user);

        imageView = findViewById(R.id.imageView);
        levelText = findViewById(R.id.levelText);
        submit = findViewById(R.id.submit);

        submit.setVisibility(View.INVISIBLE);


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
                        photoURL = documentSnapshot.get("photoURL").toString();
                        Log.d("photoUrl",photoURL);
//                        TODO: fetching level error...need to be implemented
//                        level is currentLevel
//                        level = (Integer) documentSnapshot.get("currentLevel");

                        Glide.with(getApplicationContext()).load(photoURL).into(imageView);
//                        levelText.setText("Level" + Integer.toString(level));

                        submit.setVisibility(View.VISIBLE);

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






    }





}
