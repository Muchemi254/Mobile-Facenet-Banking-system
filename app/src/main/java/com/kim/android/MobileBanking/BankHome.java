package com.kim.android.MobileBanking;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BankHome extends AppCompatActivity {
    Button exit, logout;
    private FirebaseAuth mAuth;
    TextView TxtVusername, TxtVaccNo, TxtVbalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_home);

        mAuth = FirebaseAuth.getInstance();
        TxtVusername =findViewById(R.id.TxtVusername);
        TxtVaccNo = findViewById(R.id.TxtVaccNo);
        TxtVbalance = findViewById(R.id.TxtVbalance);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String userId = user.getUid();

        logout = findViewById(R.id.logout_btn);
        exit = findViewById(R.id.exit_btn);
        logout.setOnClickListener(view -> {
            mAuth.signOut();
            Intent intent = new Intent(BankHome.this, Login.class);
            finishAffinity();
            startActivity(intent);

        } );
        exit.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(BankHome.this);
            builder.setTitle("Exit App");
            builder.setMessage("click ok to exit application");
            builder.setCancelable(true);
            builder.setIcon(R.drawable.ic_baseline_power_settings_new_24);
            builder.setPositiveButton("ok", (dialogInterface, i) -> finishAffinity());
            AlertDialog alert = builder.create();
            alert.show();

        });

        //retrieve data from firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(userId);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String username = dataSnapshot.child("name").getValue(String.class);
                String accNo = dataSnapshot.child("accountNo").getValue(String.class);
                String balance = dataSnapshot.child("balance").getValue(String.class);
                // ...
                // set values to text view
                TxtVusername.setText(username);
                TxtVaccNo.setText(accNo);
                TxtVbalance.setText(balance);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });

    }
}