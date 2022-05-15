package com.example.user.betterlifedemo;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class BalanceActivity extends AppCompatActivity
{
    TextView myText;
    FirebaseAuth mauth;
     FirebaseDatabase db ;
     int index;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);

        mauth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        DatabaseReference ui = db.getReference().child("user").child(mauth.getCurrentUser().getUid());

        ui.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String s = (String) dataSnapshot.child("age").getValue();
                int i = Integer.parseInt(s);

                if (i >= 1 && i <= 10) {
                    index = 0;
                }

                else if (i >= 10 && i <= 18)
                {
                    index=1;
                }

                else
                {
                    index=2;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        myText = (TextView) findViewById(R.id.textViewId);
        new MyTask().execute("https://api.myjson.com/bins/le9te");


    }




    private class MyTask extends AsyncTask<String, String, String> {


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            myText.setText(s);
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection httpURLConnection=null;
            BufferedReader bufferedReader=null;
            InputStream inputStream=null;
            StringBuffer stringBuffer=null;

            StringBuffer finalresult=new StringBuffer();
            try {
                URL url= new URL("https://api.myjson.com/bins/le9te");
                try {
                    httpURLConnection =(HttpURLConnection)url.openConnection();
                    inputStream=httpURLConnection.getInputStream();
                    bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                    stringBuffer=new StringBuffer();
                    String line;
                    while ((line=bufferedReader.readLine())!=null)
                        stringBuffer.append(line);
                    String file=stringBuffer.toString();

                    JSONObject jsonObj = null;
                    try {
                        jsonObj = new JSONObject(file);

                    StringBuilder re = new StringBuilder();
                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.optJSONArray("Needed Elements");


                    for (int j = 0; j < contacts.length(); j++)
                    {
                        if(index == j)
                        {
                            JSONObject c = contacts.optJSONObject(j);

                            String callories = c.optString("Consuming Callories");
                            String protein = c.optString("Protein");
                            String carbo = c.optString("Carbohydrate");
                            String vitamin = c.optString("Vitamin");
                            String water = c.optString("Water");

                            String res = "\n\n"+"Callories:"+callories+"\n"+"Protien: "+protein+"\n"+"Carbohydred: "+carbo+"\n"+"Vitamin"+vitamin+"\n"+"Water"+water;
                            finalresult.append(res);

                        }





                    }} catch (JSONException e) {
                        e.printStackTrace();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            finally {
                httpURLConnection.disconnect();
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            return finalresult.toString();
        }
    }

    /*private class MyTask extends AsyncTask<String, String, String> {


        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            JSONObject jsonObj = null;
            try {
                jsonObj = new JSONObject(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            StringBuilder re = new StringBuilder();
            // Getting JSON Array node
            JSONArray contacts = jsonObj.optJSONArray("Needed Elements");


            for (int j = 0; j < contacts.length(); j++)
            {
                if(index == j)
                {
                    JSONObject c = contacts.optJSONObject(j);

                    String callories = c.optString("Consuming Callories");
                    String protein = c.optString("Protein");
                    String carbo = c.optString("Carbohydrate");
                    String vitamin = c.optString("Vitamin");
                    String water = c.optString("Water");

                    String res = "\n\n"+callories+"\n"+protein+"\n"+carbo+"\n"+vitamin+"\n"+water;
                    re.append(res);

                }

                            }
            myText.setText(re.toString());

        }
        @Override
        protected String doInBackground(String... strings) {
            StringBuilder result = new StringBuilder();

            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line;
                while ((line = reader.readLine()) != null)
                {
                    result.append(line);
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }
            return result.toString();
        }

    }*/

}



