package com.muhaiminur.firebase;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Firebase_Image extends AppCompatActivity {


    FirebaseFirestore db;

    FirebaseDatabase database;
    DatabaseReference myRef;
    DatabaseReference image_list;
    @BindView(R.id.firebase_image)
    ImageView firebaseImage;
    @BindView(R.id.firebase_database)
    TextView firebaseDatabase;
    @BindView(R.id.firebase_firestore)
    TextView firebaseFirestore;

    String Firestore = "";
    String Database = "";
    @BindView(R.id.firebase_databse2)
    TextView firebaseDatabse2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase__image);
        ButterKnife.bind(this);
        db = FirebaseFirestore.getInstance();
        db.collection("image_list")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("FIREBASE IMAGE", document.getId() + " => " + document.getData());
                                Log.d("second", "second");
                                Firestore = Firestore + document.getData().toString();
                            }
                            firebaseFirestore.setText(Firestore);
                        } else {
                            Log.w("FIREBASE IMAGE", "Error getting documents.", task.getException());
                        }
                    }
                });

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    try {
                        String value = ds.getValue(String.class);
                        Log.d("FIREBASE IMAGE 2", "Value is: " + value);
                        Glide.with(Firebase_Image.this)
                                .load(value)
                                .into(firebaseImage);
                        Database = Database + "\n" + value;
                    } catch (Exception e) {
                        Log.d("Error Line Number", Log.getStackTraceString(e));
                    }
                }
                firebaseDatabase.setText(Database);
                //String value =(String) dataSnapshot.getValue();
                //Log.d("FIREBASE IMAGE 2", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("FIREBASE IMAGE 2", "Failed to read value.", error.toException());
            }
        });


        image_list=database.getReference().child("image_list");
        image_list.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String database="";
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    try {
                        String value = ds.getValue(String.class);
                        database=database+"\n"+value;
                        Log.d("IMAGE_DATABASE 2", "Value is: " + value);
                        firebaseDatabse2.setText(database);
                    } catch (Exception e) {
                        Log.d("Error Line Number", Log.getStackTraceString(e));
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("IMAGE_DATABASE 2", "Failed to read value.", error.toException());
            }
        });
    }
}
