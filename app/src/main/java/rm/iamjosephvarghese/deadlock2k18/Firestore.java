package rm.iamjosephvarghese.deadlock2k18;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.security.MessageDigest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Firestore extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firestore);


        FirebaseFirestore db = FirebaseFirestore.getInstance();





        Map<String,Object> playerDetails = new HashMap<>();
        playerDetails.put("created",new Date());

        db.collection("users").document("1").set(playerDetails).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("Success","..........");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Error","............");
            }
        });





    }
}
