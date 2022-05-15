package com.example.user.betterlifedemo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ExerciseActivity extends AppCompatActivity {


    private TextView t1;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        title = (TextView) findViewById(R.id.title);
        t1 = (TextView) findViewById(R.id.t1);

        jsonTask jTask = new jsonTask();
        jTask.execute();

    }

    public class jsonTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            HttpURLConnection httpURLConnection = null;
            BufferedReader bufferedReader = null;
            String do1, do2, do3, do4;

            try {
                URL url = new URL("https://api.myjson.com/bins/16hmba");
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer stringBuffer = new StringBuffer();
                String line = "";

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line);
                }

                String file = stringBuffer.toString();

                JSONObject fileObject = new JSONObject(file);
                JSONArray jsonArray = fileObject.getJSONArray("For All");
                JSONObject arrayObject = jsonArray.getJSONObject(0);
                do1 = arrayObject.getString("do1");
                do2 = arrayObject.getString("do2");
                do3 = arrayObject.getString("do3");
                do4 = arrayObject.getString("do4");


                return do1 + "\n\n" + do2 + "\n\n" + do3 + "\n\n" + do4;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                httpURLConnection.disconnect();
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            t1.setText(s);
        }
    }
}