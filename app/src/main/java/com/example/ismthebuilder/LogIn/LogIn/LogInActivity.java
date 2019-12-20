package com.example.ismthebuilder.LogIn.LogIn;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ismthebuilder.LogIn.Constructor.ConstructorActivity;
import com.example.ismthebuilder.LogIn.SharedPref.SharedPref;
import com.example.ismthebuilder.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LogInActivity extends AppCompatActivity
{
    @BindView(R.id.login_email)
    EditText email;
    @BindView(R.id.login_password)
    EditText password;
    @BindView(R.id.login_login)
    Button signup;

    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference("users");
    SharedPref sharedPref;
    ValueEventListener listener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        sharedPref = new SharedPref(this);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate())
                    signIn();
                else
                    Toast.makeText(LogInActivity.this, "Incorrect format", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void signIn() {
        listener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String mail = email.getText().toString();
                String pass = password.getText().toString();

                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    if(mail.equals(ds.child("email").getValue().toString()) && pass.equals(ds.child("password").getValue().toString())) {
                        sharedPref.setEmail(ds.child("email").getValue().toString());
                        sharedPref.setAccessLevel(ds.child("level").getValue().toString());
                        EnterApp();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void EnterApp()
    {
        if(sharedPref.getAccessLevel().equals("gov")){
            Toast.makeText(this, "Use The wesite for Government access", Toast.LENGTH_SHORT).show();
        }
        else
        {
            startActivity(new Intent(this, ConstructorActivity.class));
            finish();
        }
    }

    private boolean validate()
    {
        if(email.getText().toString().isEmpty() || password.getText().toString().isEmpty())
            return false;
        else
            return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (databaseReference != null && listener != null) {
            databaseReference.removeEventListener(listener);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (databaseReference != null && listener != null) {
            databaseReference.removeEventListener(listener);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (databaseReference != null && listener != null) {
            databaseReference.removeEventListener(listener);
        }
    }
}