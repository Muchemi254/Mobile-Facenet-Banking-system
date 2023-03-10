package com.kim.android.MobileBanking;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class BankHome extends AppCompatActivity {
    Button exit, logout;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_home);

        mAuth = FirebaseAuth.getInstance();

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
    }
}