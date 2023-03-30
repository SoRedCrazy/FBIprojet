package com.example.fbiprojet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class FavoriteActivity extends AppCompatActivity {
    SharedPreferences sharedpreferences;

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
        sharedpreferences = getSharedPreferences("session", Context.MODE_PRIVATE);
        if(sharedpreferences != null){
            Intent it = new Intent(this , ConnexionActivity.class);
            startActivity(it);
        }
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
