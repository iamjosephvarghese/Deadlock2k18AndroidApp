package rm.iamjosephvarghese.deadlock2k18;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("players");


        ref.child("id1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("val",dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
}
