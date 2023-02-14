package com.sxillocc.androidchat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EmailVerification extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth firebaseAuth;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verification);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();

        if(!currentUser.isEmailVerified()) {
            currentUser.sendEmailVerification()
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(EmailVerification.this, "Verification send" +
                                        "to email:- " + currentUser.getEmail(), Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(EmailVerification.this, "Failed Sending Verification"
                                        , Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

        findViewById(R.id.resend).setOnClickListener(this);
        findViewById(R.id.next).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id==R.id.resend){
            //resend email verification
            currentUser.sendEmailVerification()
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(EmailVerification.this,"Verification send " +
                                        "to email:- "+ currentUser.getEmail(),Toast.LENGTH_LONG).show();

                            }else{
                                Toast.makeText(EmailVerification.this,"Failed Sending Verification"
                                        ,Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        if(id==R.id.next){
            //go to Main2Activity or show invalid user toast
            currentUser.reload();
            Toast.makeText(this,"Email Verified = " +
                            String.valueOf(currentUser.isEmailVerified()), Toast.LENGTH_SHORT).show();
            if(currentUser.isEmailVerified()){
                //Toast.makeText(this,"Email verified ",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EmailVerification.this,Main2Activity.class);
                startActivity(intent);
                finish();
            }else{
                //Toast.makeText(this,"Email not verified ",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
