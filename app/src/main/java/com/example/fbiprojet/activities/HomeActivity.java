package com.example.fbiprojet.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.os.LocaleListCompat;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.fbiprojet.models.ImagesWanted;
import com.example.fbiprojet.R;
import com.example.fbiprojet.models.Wanted;
import com.example.fbiprojet.adapters.WantedAdapter;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    private ArrayList<Wanted> wanteds;
    GridView grid_view;
    Button button;
    private int page;
    SeekBar nbres_sb;
    TextView titre_et;
    TextView nbres_et;
    TextView nb_page;
    Button bt_prec;
    Button bt_suiv;

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        savedInstanceState.putSerializable("wanteds", wanteds);
        savedInstanceState.putInt("vis_prec", bt_prec.getVisibility());

        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);

        wanteds = (ArrayList<Wanted>) savedInstanceState.getSerializable("wanteds");
        WantedAdapter adapter = new WantedAdapter(HomeActivity.this, wanteds);
        grid_view.setAdapter(adapter);
        bt_prec.setVisibility(savedInstanceState.getInt("vis_prec"));
        bt_suiv.setVisibility(savedInstanceState.getInt("vis_prec"));
    }
    /**
     * Preparation du layout plus verfie les parametre de preference
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar=findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        grid_view = (GridView) findViewById(R.id.wanted_grid);
        button = (Button) findViewById(R.id.rechercher);
        wanteds = new ArrayList<Wanted>();
        page = 1;

        button.setOnClickListener(view -> search(view));

        nbres_sb = (SeekBar) findViewById(R.id.seekBar);
        nbres_et = (TextView) findViewById(R.id.nbres_et);
        nb_page = (TextView) findViewById(R.id.nb_page);
        nb_page.setText(String.valueOf(page));

        nbres_sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            /**
             * Fonction qui surveille la modification de la seekbar
             * et modifie le nombre d'éléments à retourner de l'API
             * @param seekBar
             * @param i
             * @param b
             */
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Integer nb = nbres_sb.getProgress();
                nbres_et.setText(nb.toString());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        bt_prec = (Button) findViewById(R.id.bt_prec);
        bt_suiv = (Button) findViewById(R.id.bt_suiv);
        titre_et = (EditText) findViewById(R.id.titre_et);

        bt_prec.setOnClickListener(v -> {
            if (page > 1){
                page--;
                nb_page.setText(String.valueOf(page));
                search(v);
            }
        });

        bt_suiv.setOnClickListener(v -> {
            page++;
            nb_page.setText(String.valueOf(page));
            search(v);
        });

        grid_view.setOnItemClickListener((adapterView, view, i, l) -> {
            Bundle b = new Bundle();
            b.putSerializable("wanted",wanteds.get(i));
            Intent toWanted = new Intent(HomeActivity.this, WantedInfoActivity.class)
                    .putExtras(b);
            startActivity(toWanted);
        });

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean darkmode = sharedPreferences.getBoolean("darkmode", false);
        if (darkmode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        String langue = sharedPreferences.getString("langue", "en-EN");
        if(langue.equals("fr-FR")){
            LocaleListCompat appLocale = LocaleListCompat.forLanguageTags("fr-FR");
            AppCompatDelegate.setApplicationLocales(appLocale);
        }if(langue.equals("en-US")){
            LocaleListCompat appLocale = LocaleListCompat.forLanguageTags("en-EN");
            AppCompatDelegate.setApplicationLocales(appLocale);
        }
    }
    /**
     * Ajoute les boutons du menu dans la toolbar
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home,menu);
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
            case R.id.action_favorite:
                i = new Intent(this, ConnexionActivity.class);
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
     * Fonction qui exécute la requête via l'API FBI
     * Elle récupère le nombre d'éléments voulus et la page voulue,
     * décode le résultat avec decodeJson et envoie les éléments à l'adapter
     * @param v
     */
    public void search(View v) {
        Integer nb = nbres_sb.getProgress();
        wanteds.clear();

        String url = "https://api.fbi.gov/@wanted?pageSize="+nb.toString()+"&page="+String.valueOf(page)+"&sort_on=modified&sort_order=desc";

        if (titre_et.getText().toString().length() != 0){
            url = url + "&title=" + titre_et.getText().toString();
        }

        System.out.println(url);

        Ion.with(v.getContext())
                .load(url)
                .setLogging("ION_LOGS", Log.DEBUG)
                .asJsonObject()
                .setCallback((e, result) -> {
                    if (result != null){
//                        System.out.println(result.get("items").getAsJsonArray().size());
                        JsonArray jsowanteds = result.get("items").getAsJsonArray();
                        for (int i = 0; i < jsowanteds.size(); i++){
                            JsonObject res = jsowanteds.get(i).getAsJsonObject();
                            wanteds.add(decodeJson(res));
//                            System.out.println(res.get("title"));
                        }
                    }

                    WantedAdapter adapter = new WantedAdapter(HomeActivity.this, wanteds);
                    grid_view.setAdapter(adapter);

                });
        bt_prec.setVisibility(View.VISIBLE);
        bt_suiv.setVisibility(View.VISIBLE);
    }

    /**
     * Fonction qui transforme un objet JsonObject reçu via l'API
     * et le traite pour renvoyer un objet Wanted
     * @param jso objet JsonObject reçu de l'API à traiter
     * @return un objet Wanted
     */
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
}