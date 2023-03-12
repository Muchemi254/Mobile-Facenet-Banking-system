package com.kim.android.MobileBanking;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    //Variables
    TextInputLayout regName, regaccountNo, regEmail, regPhoneNo, regPassword;
    Button regBtn;
    TextView regToLoginBtn;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //Hooks to all xml elements in activity_sign_up.xml
        regName = findViewById(R.id.reg_name);
        regaccountNo = findViewById(R.id.reg_accountNo);
        regEmail = findViewById(R.id.reg_email);
        regPhoneNo = findViewById(R.id.reg_phoneNo);
        regPassword = findViewById(R.id.reg_password);
        regBtn = findViewById(R.id.reg_btn);
        auth = FirebaseAuth.getInstance();
        regToLoginBtn = findViewById(R.id.reg_login_btn);
        //Save data in FireBase on button click
        regToLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, Login.class);
                startActivity(intent);
            }
        });



        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savedata();
            }




        });//Register Button method end

    }//onCreate Method End


    private void savedata() {
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("users");
        //Get all the values
        String name = regName.getEditText().getText().toString();
        String accountNo = regaccountNo.getEditText().getText().toString();
        String email = regEmail.getEditText().getText().toString();
        String phoneNo = regPhoneNo.getEditText().getText().toString();
        String password = regPassword.getEditText().getText().toString();
        String balance = "0";


        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(),"Please enter email!!",Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(accountNo)) {
            Toast.makeText(getApplicationContext(),"Please enter AccountNo!!",Toast.LENGTH_LONG).show();
            return;
        }
        if (!accountNo.matches("\\d{10}")) {
            Toast.makeText(getApplicationContext(),"Account No must be 10 digits!!",Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(getApplicationContext(),"Please enter name!!",Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(phoneNo)) {
            Toast.makeText(getApplicationContext(),"Please enter phoneNo!!",Toast.LENGTH_LONG).show();
            return;
        }

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_LONG).show();

                } else {
                    // Registration failed
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this);

                    // Set the message show for the Alert time
                    builder.setMessage(task.getException().getMessage());

                    // Set Alert Title
                    builder.setTitle("Registration Failed");

                    // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
                    builder.setCancelable(true);


                    // Create the Alert dialog
                    AlertDialog alertDialog = builder.create();
                    // Show the Alert Dialog box
                    alertDialog.show();
                }
            }
        });
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = currentUser.getUid();

        UserHelperClass helperClass = new UserHelperClass(name, accountNo, email, phoneNo, balance);
        reference.child(userId).setValue(helperClass);
        AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this);
        builder.setMessage("Registration successful")
                .setCancelable(false)
                .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(SignUp.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

    public static class UserHelperClass {
        String name;
        String accountNo;
        String email;
        String phoneNo;
        String balance;

        public UserHelperClass() {
        }
        public UserHelperClass(String name, String accountNo, String email, String phoneNo, String balance) {
            this.name = name;
            this.accountNo = accountNo;
            this.email = email;
            this.phoneNo = phoneNo;
            this.balance = balance;

        }


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getAccountNo() {
            return accountNo;
        }

        public void setAccountNo(String accountNo) {
            this.accountNo = accountNo;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhoneNo() {
            return phoneNo;
        }

        public void setPhoneNo(String phoneNo) {
            this.phoneNo = phoneNo;
        }


    }
}

