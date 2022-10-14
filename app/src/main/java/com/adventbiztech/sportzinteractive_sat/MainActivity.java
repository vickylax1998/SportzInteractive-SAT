package com.adventbiztech.sportzinteractive_sat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.adventbiztech.sportzinteractive_sat.databinding.ActivityMainBinding;
import com.adventbiztech.util.AppController;
import com.adventbiztech.util.Keys;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;

public class MainActivity extends AppCompatActivity {
ActivityMainBinding binding;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        AppController.initialize(getApplicationContext());
        dialog = new ProgressDialog(MainActivity.this);
        dialog.setMessage("Please wait Loading...");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        loadMatch1();
        binding.textViewMatch1Players.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,PlayersActivity.class);
                i.putExtra("url",Keys.MATCH_ONE_URL);
                startActivity(i);
            }
        });
        binding.textViewMatch2Players.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,PlayersActivity.class);
                i.putExtra("url",Keys.MATCH_TWO_URL);
                startActivity(i);
            }
        });

    }

    private void loadMatch1() {
        dialog.show();
        StringRequest request = new StringRequest(Request.Method.GET, Keys.MATCH_ONE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                Log.i("vic", "response=>" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject!=null){
                        JSONObject jsonObject1=jsonObject.getJSONObject("Matchdetail");
                        JSONObject jsonObject2=jsonObject.getJSONObject("Teams");
                        String team1_no = jsonObject1.getString("Team_Home");
                        String team2_no = jsonObject1.getString("Team_Away");
                        String date = jsonObject1.getJSONObject("Match").getString("Date");
                        String time = jsonObject1.getJSONObject("Match").getString("Time");
                        String venue = jsonObject1.getJSONObject("Venue").getString("Name");
                        String team1_name = jsonObject2.getJSONObject(team1_no).getString("Name_Full");
                        String team2_name = jsonObject2.getJSONObject(team2_no).getString("Name_Full");
                        binding.textViewMatch1Date.setText("Date : "+date);
                        binding.textViewMatch1Time.setText("Time : "+time);
                        binding.textViewMatch1Venue.setText(venue);
                        binding.textViewMatch1Team1.setText(team1_name);
                        binding.textViewMatch1Team2.setText(team2_name);
                        loadMatch2();
                    }else{
                        Toast.makeText(MainActivity.this, "No data", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "Please check your internet connection", Toast.LENGTH_SHORT).show();
            }
        });
        AppController.getInstance().add(request);

    }

    private void loadMatch2() {
        dialog.show();
        StringRequest request = new StringRequest(Request.Method.GET, Keys.MATCH_TWO_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                Log.i("vic", "response=>" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject!=null){
                        JSONObject jsonObject1=jsonObject.getJSONObject("Matchdetail");
                        JSONObject jsonObject2=jsonObject.getJSONObject("Teams");
                        String team1_no = jsonObject1.getString("Team_Home");
                        String team2_no = jsonObject1.getString("Team_Away");
                        String date = jsonObject1.getJSONObject("Match").getString("Date");
                        String time = jsonObject1.getJSONObject("Match").getString("Time");
                        String venue = jsonObject1.getJSONObject("Venue").getString("Name");
                        String team1_name = jsonObject2.getJSONObject(team1_no).getString("Name_Full");
                        String team2_name = jsonObject2.getJSONObject(team2_no).getString("Name_Full");
                        binding.textViewMatch2Date.setText("Date : "+date);
                        binding.textViewMatch2Time.setText("Time : "+time);
                        binding.textViewMatch2Venue.setText(venue);
                        binding.textViewMatch2Team1.setText(team1_name);
                        binding.textViewMatch2Team2.setText(team2_name);
                    }else{
                        Toast.makeText(MainActivity.this, "No data", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "Please check your internet connection", Toast.LENGTH_SHORT).show();
            }
        });
        AppController.getInstance().add(request);
    }
}