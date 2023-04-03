package com.example.fbiprojet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/**
 * Gere la inscription et la connexion de du layout activity_connexion_register.xml
 *
 *
 *
 */

public class ConnexionActivity extends AppCompatActivity {
    DBHandler db;
    SharedPreferences sharedpreferences;

    /**
     * Lance activity ajoute la tool bars puis se connecter a la base de donnée verifie si on est aps deja connecté
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion_register);
        Toolbar toolbar=findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        db= new DBHandler(this);

        sharedpreferences = getSharedPreferences("session", Context.MODE_PRIVATE);
        String s =sharedpreferences.getString("user_name",null);
        if(s != null){
            Intent it = new Intent(ConnexionActivity.this , FavoriteActivity.class);
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
     * regarde si l'utilisateur existe si non on l'ajoute si oui on verifie le mots de passe.
     * @param v
     *
     */
    public void run(View v){
        EditText edtname = (EditText) findViewById(R.id.name);
        EditText edtpassord = (EditText) findViewById(R.id.password);

        user use = db.getUser(edtname.getText().toString());

        if(use==null){
            int id= (int) db.addUser(edtname.getText().toString(),edtpassord.getText().toString());

            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("user_name", edtname.getText().toString());
            editor.putInt("user_id", id);
            editor.commit();

            Intent it = new Intent(ConnexionActivity.this , FavoriteActivity.class);
            startActivity(it);
        }else {
            if(use.getPassword().equals(edtpassord.getText().toString())){
                Intent it = new Intent(ConnexionActivity.this , FavoriteActivity.class);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("user_name", use.getName());
                editor.putInt("user_id", use.getId());
                editor.commit();
                startActivity(it);
            }else{
                Toast.makeText(this, "mauvais mots de passe", Toast.LENGTH_LONG).show();

            }
        }


    }
}