package rm.iamjosephvarghese.deadlock2k18;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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


    SharedPreferences sharedPreferences;
    //assume this is the user id genrated from google auth


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firestore);


//        sharedPreferences = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
//        user=sharedPreferences.getString("UID",null);
//        Log.d("user",user);


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


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });






    }






}
