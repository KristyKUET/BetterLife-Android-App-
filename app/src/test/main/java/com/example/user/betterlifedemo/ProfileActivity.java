package com.example.user.betterlifedemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity
{
    Button balancediet,chatting,signout,exercise;
    FirebaseAuth mauth;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        balancediet = (Button)findViewById(R.id.btnbalancediet);
         exercise = (Button)findViewById(R.id.btnexercise);
        chatting = (Button)findViewById(R.id.btnchatting);
        signout = (Button)findViewById(R.id.btnsignout);


        balancediet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                balance();

            }
        });


        exercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                exer();
            }
        });


        /*chatting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                FirebaseAuth.getInstance().signOut();
                srtlgn();

            }
        });


    }
    void srtlgn(){
        Intent intent = new Intent(ProfileActivity.this,login.class);
        startActivity(intent);
    }
    void exer(){
        Intent intent=new Intent(ProfileActivity.this,ExerciseActivity.class);
        startActivity(intent);
    }
    void balance()
    {
        Intent intent=new Intent(ProfileActivity.this,BalanceActivity.class);
        startActivity(intent);
    }
}
