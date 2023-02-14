package com.sxillocc.androidchat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    EditText mMessage;
    FloatingActionButton mSignOut, mSend;
    RecyclerView mMessages;
    RecyclerView.Adapter mAdapter;
    FirebaseUser mUser;
    FirebaseAuth mAuth;
    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("message");
    ArrayList<Messagee> msgList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //getting current user
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        //initialise variables
        mMessage = findViewById(R.id.message);
        mSignOut = findViewById(R.id.exit);
        mSend = findViewById(R.id.send);
        mMessages = findViewById(R.id.rv_messages);
        msgList = new ArrayList<>();

        //show RecyclerView
        mAdapter = new CustomAdapter(msgList);
        mMessages.setLayoutManager(new LinearLayoutManager(this));
        mMessages.setAdapter(mAdapter);

        //show messages in begin and each time when list update
        showAndUpdateMessages();

        //set listener on buttons
        mSend.setOnClickListener(this);
        mSignOut.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id==R.id.exit){
            //sign-out
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(Main2Activity.this,MainActivity.class);
            startActivity(intent);
            finish();
            //;
        }
        if(id==R.id.send){
            String message = mMessage.getText().toString();
            if(TextUtils.isEmpty(message)){
                //do nothing
            }else{
                //push message along with email
                String email = mUser.getEmail();
                Messagee msg = new Messagee(email,message);
                myRef.push().setValue(msg);
            }
        }
    }

    void showAndUpdateMessages(){
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Messagee current = dataSnapshot.getValue(Messagee.class);
                Log.e("xyz",dataSnapshot.getValue().toString());
                msgList.add(current);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    
}
