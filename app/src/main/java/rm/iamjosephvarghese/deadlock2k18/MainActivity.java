package rm.iamjosephvarghese.deadlock2k18;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    int level;
    String url;

    ImageView questionImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Firebase.setAndroidContext(this);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("players");
       // DatabaseReference imageRef = database.getReference("images");
       // DatabaseReference answerRef = database.getReference("answers");


        questionImage = findViewById(R.id.questionImage);


        readData(ref.child("id1"), new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {

                level = dataSnapshot.getValue(Integer.class);
                Log.d("level",Integer.toString(level));
                gotLevel();
            }

            @Override
            public void onStart() {

                Log.d("OnStart","started");
            }

            @Override
            public void onFailure() {

                Log.d("Onfaliure","faliure");
            }
        });



    }



    public interface OnGetDataListener {
        //make new interface for call back
        void onSuccess(DataSnapshot dataSnapshot);
        void onStart();
        void onFailure();
    }

    public void readData(DatabaseReference ref, final OnGetDataListener listener) {
        listener.onStart();

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailure();
            }
        });

    }


    public void gotLevel(){

        final FirebaseDatabase database = FirebaseDatabase.getInstance();


        DatabaseReference imageRef = database.getReference("images");




        readData(imageRef.child("1"), new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                url = dataSnapshot.getValue(String.class);
                Log.d("url",url);
                gotUrl(url);
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure() {

            }
        });



    }



    public void gotUrl(String url){
        Glide.with(this).load(url).into(questionImage);

    }
}
