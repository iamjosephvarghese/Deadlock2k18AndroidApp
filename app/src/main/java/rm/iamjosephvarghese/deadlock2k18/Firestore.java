package rm.iamjosephvarghese.deadlock2k18;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.security.MessageDigest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Firestore extends AppCompatActivity {




    SharedPreferences sharedPreferences;
    //assume this is the user id genrated from google auth
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firestore);


        sharedPreferences = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        user=sharedPreferences.getString("UID",null);
        Log.d("user",user);



        final FirebaseFirestore db = FirebaseFirestore.getInstance();


        DocumentReference documentReference = db.collection("leaderboard").document(user);

        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    Log.d("User","already in leaderboard");
                }else{
                    db.collection("leaderboard");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Error","checking if user present in leaderboard");
            }
        });






//        Map<String,Object> playerDetails = new HashMap<>();
//        playerDetails.put("created",new Date());
//
//        db.collection("users").document("1").set(playerDetails).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                Log.d("Success","..........");
//
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.d("Error","............");
//            }
//        });





    }
}
