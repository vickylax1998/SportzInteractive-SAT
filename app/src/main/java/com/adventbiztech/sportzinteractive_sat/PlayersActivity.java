package com.adventbiztech.sportzinteractive_sat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adventbiztech.adapter.PLayerAdapter;
import com.adventbiztech.listener.PopupListener;
import com.adventbiztech.pojo.Player;
import com.adventbiztech.sportzinteractive_sat.databinding.ActivityMainBinding;
import com.adventbiztech.util.AppController;
import com.adventbiztech.util.Keys;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PlayersActivity extends AppCompatActivity {
    ProgressDialog dialog;
    ArrayList<String> alTeam = new ArrayList<>();
    AutoCompleteTextView autoCompleteTextView;
    RecyclerView recyclerView;
    ArrayList<Player> arrayList1 = new ArrayList<>();
    ArrayList<Player> arrayList2 = new ArrayList<>();
    PLayerAdapter pLayerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);
        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        recyclerView = findViewById(R.id.recyclerView);
        AppController.initialize(getApplicationContext());
        dialog = new ProgressDialog(PlayersActivity.this);
        dialog.setMessage("Please wait Loading...");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        Intent i =getIntent();
        String url = i.getStringExtra("url");
        loadData(url);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (!alTeam.isEmpty()){
                    Log.i("vic","pos=>"+i);
                    if (i==0){
                        pLayerAdapter = new PLayerAdapter(arrayList1,getApplicationContext());
                        recyclerView.setAdapter(pLayerAdapter);
                    }else{
                        pLayerAdapter = new PLayerAdapter(arrayList2,getApplicationContext());
                        recyclerView.setAdapter(pLayerAdapter);
                    }
                }
            }
        });
        PLayerAdapter.bindListener(new PopupListener() {
            @Override
            public void click(String content) {
                showDialog(content);
            }
        });
    }
    private void showDialog(String message) {
        AppCompatButton btOK;
        TextView textView;
        ImageView tvSuccess;
        Dialog dialog = new Dialog(PlayersActivity.this);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window=dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        dialog.setContentView(R.layout.custom_main_dialog);
        textView=dialog.findViewById(R.id.textViewPContent);
        textView.setText(message);
        dialog.setCancelable(true);
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT,ActionBar.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    private void loadData(String url) {
        dialog.show();
        StringRequest request = new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
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
                        String team1_name = jsonObject2.getJSONObject(team1_no).getString("Name_Full");
                        String team2_name = jsonObject2.getJSONObject(team2_no).getString("Name_Full");
                        alTeam.add(team1_name);
                        alTeam.add(team2_name);
                        ArrayAdapter arrayAdapter1 = new ArrayAdapter<String>(getApplicationContext(),
                                R.layout.custom_list_item, R.id.text_view_list_item, alTeam);
                        autoCompleteTextView.setAdapter(arrayAdapter1);
                        autoCompleteTextView.setText(team1_name,false);
                        JSONObject jsonObject3 = jsonObject2.getJSONObject(team1_no).getJSONObject("Players");
                        for(int i = 0; i<jsonObject3.names().length(); i++){
                            JSONObject jsonObject4=jsonObject3.getJSONObject(jsonObject3.names().getString(i));
                            String p_name = jsonObject4.getString("Name_Full");
                            String p_position = jsonObject4.getString("Position");
                            String batting_style = jsonObject4.getJSONObject("Batting").getString("Style");
                            String bowling_style = jsonObject4.getJSONObject("Bowling").getString("Style");
                            Boolean isCap=false, iswk=false;
                            if (jsonObject4.has("Iscaptain")){
                                isCap=true;
                            }
                            if (jsonObject4.has("Iskeeper")){
                                iswk=true;
                            }
                            arrayList1.add(new Player(p_position,p_name,isCap,iswk,batting_style,bowling_style,team1_no));
                        }

                        JSONObject jsonObject5 = jsonObject2.getJSONObject(team2_no).getJSONObject("Players");
                        for(int i = 0; i<jsonObject5.names().length(); i++){
                            JSONObject jsonObject4=jsonObject5.getJSONObject(jsonObject5.names().getString(i));
                            String p_name = jsonObject4.getString("Name_Full");
                            String p_position = jsonObject4.getString("Position");
                            String batting_style = jsonObject4.getJSONObject("Batting").getString("Style");
                            String bowling_style = jsonObject4.getJSONObject("Bowling").getString("Style");
                            Boolean isCap=false, iswk=false;
                            if (jsonObject4.has("Iscaptain")){
                                isCap=true;
                            }
                            if (jsonObject4.has("Iskeeper")){
                                iswk=true;
                            }
                            arrayList2.add(new Player(p_position,p_name,isCap,iswk,batting_style,bowling_style,team2_no));
                        }

                        pLayerAdapter = new PLayerAdapter(arrayList1,getApplicationContext());
                        recyclerView.setAdapter(pLayerAdapter);




                    }else{
                        Toast.makeText(PlayersActivity.this, "No data", Toast.LENGTH_SHORT).show();
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