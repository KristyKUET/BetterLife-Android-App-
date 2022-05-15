package com.example.user.betterlifedemo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity
{
    private FirebaseAuth mAuth;
    EditText email,password;
    TextView Info;
    Button login,register;
    private int counter = 5;
    private static final String TAG = "login";
    FirebaseUser currentuser;
    private void updateUI(FirebaseUser curr)
    {
        if(curr!=null) // user already signed in
        {
            Intent intent=new Intent(this,ProfileActivity.class);
            startActivity(intent);
        }
    }
    @Override
    protected void onStart()
    {
        super.onStart();
        currentuser = mAuth.getCurrentUser();
        updateUI(currentuser);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.etEmail);
        password =(EditText)findViewById(R.id.etPassword);
        login= (Button)findViewById(R.id.btnLogin);
        register= (Button)findViewById(R.id.btnregister);
        Info = (TextView)findViewById(R.id.tvInfo);
        mAuth = FirebaseAuth.getInstance();

        //Log.d(TAG, "onCreate: hijibiji");

        login.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view)
           {
                mAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(login.this, "Log in Successful", Toast.LENGTH_SHORT).show();

                            FirebaseUser user = mAuth.getCurrentUser();

                            Intent intent=new Intent(login.this,ProfileActivity.class);
                             startActivity(intent);


                        }
                        else
                        {
                            Toast.makeText(login.this, "Log in failed", Toast.LENGTH_SHORT).show();
                            counter--;
                            Info.setText("No of attempts remaining: " + counter);

                            if(counter == 0){
                                login.setEnabled(false);
                            }
                        }

                    }
                });
           }
       });


       register.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view)
           {
                func();
           }
       });


    }
    void func()
    {
        Intent intent=new Intent(login.this, RegisterActivity.class);
        startActivity(intent);
    }
}
