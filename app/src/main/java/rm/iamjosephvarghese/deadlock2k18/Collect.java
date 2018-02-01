package rm.iamjosephvarghese.deadlock2k18;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

public class Collect extends AppCompatActivity {





    int level = 0;
    String previousHash = "16d63cfb10cbe791c4502c6d4af173462a43785d6cfedfa5e931115e006abd9e";
    String currentHash = "dd4afcb2dcb9a1f9e93348f2c49a9fee3e3a79936ed86760cc15b87be47cbe23";



    EditText college,mobno;


    Button submit;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    String UID;

    TextView textView;

    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);

        Log.d("collect","............");


        Typeface bebas = Typeface.createFromAsset(getAssets(),  "fonts/bebasneue.ttf");


        college = findViewById(R.id.college);
        mobno = findViewById(R.id.mobno);
        textView = findViewById(R.id.textView);


        college.setTypeface(bebas);
        mobno.setTypeface(bebas);
        textView.setTypeface(bebas);


        submit = findViewById(R.id.submit);

        submit.setTypeface(bebas);



        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();


        sharedPreferences = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        UID=sharedPreferences.getString("UID",null);
        Log.d("UID",UID);


        final DocumentReference userRef = db.collection("users").document(UID);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                TODO:mobno length always 10??????
                if(!college.getText().toString().equals("") && !mobno.getText().toString().equals("")){

                    userRef.set(new User(user.getDisplayName(),college.getText().toString(),user.getEmail(),user.getPhotoUrl().toString(),currentHash,previousHash,mobno.getText().toString(),level))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("New User","success");


//                                    TODO: adding details to shared prferences to fetch later....mobono null in other activity
                                    editor.putString("email",user.getEmail());
                                    editor.putString("mobno",mobno.getText().toString());
                                    Log.d("mobno",mobno.getText().toString());
                                    editor.putString("college",college.getText().toString());
                                    editor.commit();

                                    Intent mainIntent = new Intent(Collect.this,Firestore.class);
                                    startActivity(mainIntent);
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("New User","error");
                            Log.d("Please Submit Again","................");

                        }
                    });

                }else{
                    Toast.makeText(Collect.this, "Please Enter the Required Details!", Toast.LENGTH_SHORT).show();
                }
            }
        });


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


