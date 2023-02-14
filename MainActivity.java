package com.sxillocc.androidchat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText mEmail,mPassword;
    public FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialising
        mAuth = FirebaseAuth.getInstance();
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);

        //Setting Listener
        findViewById(R.id.signup).setOnClickListener(this);
        findViewById(R.id.login).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //boolean x =  (null == currentUser);
        //Log.e("User should be null",String.valueOf(x));
        updateUI(currentUser);
    }

    //Checking Validity
    Boolean trueCredential(String email,String password){
        Boolean credits = true;
        if(TextUtils.isEmpty(email)){
            mEmail.setError("Required");
            credits = false;
        }
        if(TextUtils.isEmpty(password)){
            mPassword.setError("Required");
            credits = false;
        }
        return credits;
    }

    //updating ui
    void updateUI(FirebaseUser currentUser){
        if(currentUser!=null) {
            if(currentUser.isEmailVerified()) {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
            }
            else{
                Intent intent = new Intent(MainActivity.this,EmailVerification.class);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id==R.id.signup){
            //SignUp
            String email = mEmail.getText().toString();
            String password = mPassword.getText().toString();

            if(trueCredential(email,password)) {
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(MainActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }
        if(id==R.id.login){
            //Login
            String email = mEmail.getText().toString();
            String password = mPassword.getText().toString();
            if(trueCredential(email,password)) {
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(MainActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }

                                // ...
                            }
                        });
            }
        }
    }
}
