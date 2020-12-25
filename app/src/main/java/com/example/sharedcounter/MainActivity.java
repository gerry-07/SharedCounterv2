package com.example.sharedcounter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import android.os.AsyncTask;


import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;




public class MainActivity<prublic> extends AppCompatActivity {



    TextView showvalue;
    int counter = 0;
    View view;
    TextView bier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view=this.getWindow().getDecorView();
        view.setBackgroundResource(R.color.black);

        showvalue = (TextView) findViewById(R.id.counterValue);
        bier = (TextView) findViewById(R.id.textView);

        new GetJSON().execute();

    }




        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }



            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL("http://any-timer.com/count_service.php");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;

                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }

                    return sb.toString().trim();
                } catch (Exception e) {
                    return String.valueOf(1);
                }
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    loadIntoTextView(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }





    private void loadIntoTextView(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        JSONObject obj = jsonArray.getJSONObject(0);
        String ttlbier = obj.getString("aantal");
        bier.setText(ttlbier);

    }

    public void countIN (View View){
        counter++;
        showvalue.setText(Integer.toString(counter));

    }
    public void countDE (View View){
        counter--;
        showvalue.setText(Integer.toString(counter));
    }


    public void laadIN (View View){

        showvalue.setText(counter);


    }





}

