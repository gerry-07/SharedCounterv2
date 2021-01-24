package com.example.sharedcounter;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import android.os.AsyncTask;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    //int //totaal bier zodat het public wodt.

    TextView showvalue;
    int counter = 0;
    String user="kwik";
    String pass="kwik1";

    View view;
    Calendar calendar = Calendar.getInstance();


    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view=this.getWindow().getDecorView();
        view.setBackgroundResource(R.color.black);

        showvalue = (TextView) findViewById(R.id.counterValue);




        mToastRunnable.run();
        //new GetJSON().execute();

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
                //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
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
        //Log.d(json, "hoi" + String.valueOf(obj));
        String ttlbier = obj.getString("totaal");
        //Log.d(json, "Aantal = " + currentDate);

        showvalue.setText(ttlbier);

    }




    private Runnable mToastRunnable = new Runnable() {
        @Override
        public void run() {
            new GetJSON().execute();
            mHandler.postDelayed(this, 500);
        }
    };





    public void countIN (View View){

        String url = "http://any-timer.com/Upone.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       // Toast.makeText(MainActivity.this,response.trim(),Toast.LENGTH_LONG).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<String, String>();
                params.put("data",user);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);
        new GetJSON().execute();
    }

    public void countDE (View View){

        String url = "http://any-timer.com/Downone.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Toast.makeText(MainActivity.this,response.trim(),Toast.LENGTH_LONG).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<String, String>();
                params.put("data",user);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);
        new GetJSON().execute();

    }


}
