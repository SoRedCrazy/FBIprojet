package com.example.fbiprojet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

import kotlinx.coroutines.flow.SharingStarted;

public class FavoriteActivity extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    DBHandler handler;
    ArrayList<Wanted> wanteds;
    GridView wanted_grid;
    /**
     * Preparation du layout plus verifie si connecter
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        Toolbar toolbar=findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        handler = new DBHandler(this);
        wanted_grid = (GridView) findViewById(R.id.wanted_grid);
        wanteds = new ArrayList<>();
        sharedpreferences = getSharedPreferences("session", Context.MODE_PRIVATE);
        if(sharedpreferences == null){
            Intent it = new Intent(this , ConnexionActivity.class);
            startActivity(it);
        } else {
            ArrayList<String> likes = handler.getlikes(sharedpreferences.getInt("username",-1));
            for(String s : likes) {
                System.out.println(s);
                StringBuilder correct = new StringBuilder(s);
                correct.deleteCharAt(0); correct.deleteCharAt(correct.length()-1);
                String url = "https://api.fbi.gov/@wanted-person/"+correct;
                Ion.with(this)
                        .load(url)
                        .setLogging("ION_LOGS", Log.DEBUG)
                        .asJsonObject()
                        .setCallback((e, result) -> {
                            if (result != null){
                                System.out.println(result.get("title"));
                                wanteds.add(decodeJson(result));
                            }
                            WantedAdapter adapter = new WantedAdapter(FavoriteActivity.this, wanteds);
                            wanted_grid.setAdapter(adapter);
                        });
            }
        }
        wanted_grid.setOnItemClickListener((adapterView, view, i, l) -> {
            Bundle b = new Bundle();
            b.putSerializable("wanted",wanteds.get(i));
            Intent toWanted = new Intent(FavoriteActivity.this, WantedInfoActivity.class)
                    .putExtras(b);
            startActivity(toWanted);
        });
    }

    private Wanted decodeJson(JsonObject jso) {
        Wanted res = new Wanted();
        String titre = jso.get("title").toString();
        String desc = jso.get("description").toString();
        String warn = jso.get("warning_message").toString();
        String remarques = jso.get("remarks").toString();
        String nationalite = jso.get("nationality").toString();

        if(titre.equals("null")) {
            titre = "Aucun titre";
        }
        res.setTitle(titre);

        if(desc.equals("null")) {
            desc = "Aucune description";
        }
        res.setDescription(desc);

        if(warn.equals("null")) {
            warn = "Aucun avertissement";
        }
        res.setWarning(warn);

        if(remarques.equals("null")) {
            remarques = "Aucune remarque";
        }
        res.setRemarques(remarques);

        if(nationalite.equals("null")) {
            nationalite = "Nationalité non renseignée";
        }
        res.setNationalite(nationalite);

        JsonArray categories = jso.get("subjects").getAsJsonArray();
        for(JsonElement c : categories) {
            res.addCategorie(c.toString());
        }

        res.setImages(new ImagesWanted(jso.get("images").getAsJsonArray()));
        res.setUid(jso.get("uid").toString());
        return res;
    }

    /**
     * Ajoute les boutons du menu dans la toolbar
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favorite_menu,menu);
        return true;
    }

    /**
     * ajoute les action des items des tool bars
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent i;
        switch (item.getItemId()){
            case R.id.action_home:
                i = new Intent(this,HomeActivity.class);
                startActivity(i);

                return true;
            case R.id.action_settings:
                i = new Intent(this,SettingActivity.class);
                startActivity(i);
                return true;

            case R.id.action_internet:
                i = new Intent(this,InternetActivity.class);
                startActivity(i);
                return true;

            case R.id.action_phone:
                Uri tel= Uri.parse("tel:911");
                i = new Intent(Intent.ACTION_DIAL,tel);
                startActivity(i);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * deconnect l'utilisateur et le renvoie a la connection
     * @param v
     */
    public void run(View v){
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
        Intent it = new Intent(this , ConnexionActivity.class);
        startActivity(it);
    }
}
