package com.example.user.betterlifedemo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity
{
    private EditText userName, userPassword, userEmail, userAge,userHeight,userWeight;
    private Button regButton;
    String email, name, age, password,height,weight;
    private FirebaseAuth mauth;
    FirebaseDatabase db;
    Integer u=0;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mauth = FirebaseAuth.getInstance();

        setupUIViews();

        regButton.setOnClickListener(new View.OnClickListener()
        {

            FirebaseDatabase db = FirebaseDatabase.getInstance();
            //String s = Integer.toString(u);
            @Override
            public void onClick(View view)
            {
                if(validate())
                {
                    mauth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if(task.isSuccessful())
                            {
                                DatabaseReference ui = db.getReference().child("user")
                                        .child(mauth.getCurrentUser().getUid());

                                ui.child("name").setValue(name);
                                ui.child("age").setValue(age);
                                ui.child("height").setValue(height);
                                ui.child("weight").setValue(weight);
                                ui.child("email").setValue(email);
                                ui.child("password").setValue(password);

                                Toast.makeText(RegisterActivity.this, "Registration suceesful ", Toast.LENGTH_SHORT).show();
                                func2();
                            }
                            FirebaseUser user = mauth.getCurrentUser();
                           // Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();

                        }

                    });

                    /*DatabaseReference ui = db.getReference().child("user")
                            .child(mauth.getCurrentUser().getUid());

                    ui.child("name").setValue(name);
                    ui.child("age").setValue(age);
                    ui.child("height").setValue(height);
                    ui.child("weight").setValue(weight);
                    ui.child("email").setValue(email);
                    ui.child("password").setValue(password);

                    Toast.makeText(RegisterActivity.this, "Registration suceesful ", Toast.LENGTH_SHORT).show();
                    func2();*/


                }
                else
                {
                    Toast.makeText(RegisterActivity.this, "Please fill the fields ", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }
    public void func2()
    {
        Intent intent=new Intent(RegisterActivity.this,ProfileActivity.class);
        startActivity(intent);
    }




    private void setupUIViews(){
        userName = (EditText)findViewById(R.id.etUserName);
        userPassword = (EditText)findViewById(R.id.etUserPassword);
        userEmail = (EditText)findViewById(R.id.etUserEmail);
        regButton = (Button)findViewById(R.id.btnRegister);
        userAge = (EditText)findViewById(R.id.etAge);
        userHeight= (EditText)findViewById(R.id.etUserHeight);
        userWeight= (EditText)findViewById(R.id.etUserWeight);
    }

    public boolean validate()
    {
        name = userName.getText().toString();
        email = userEmail.getText().toString();
        password = userPassword.getText().toString();
        age = userAge.getText().toString();
        height = userHeight.getText().toString();
        weight= userWeight.getText().toString();

        if(name.isEmpty() || email.isEmpty() || password.isEmpty() || age.isEmpty() ||height.isEmpty() || weight.isEmpty())
        {
            return false;
        }
        else
        {
            return true;
        }

    }


}
