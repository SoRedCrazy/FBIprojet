package com.example.fbiprojet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class ConnexionActivity extends AppCompatActivity {
    DBHandler db;
    SharedPreferences sharedpreferences;
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favorite_menu,menu);
        return true;
    }

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
        }

        return super.onOptionsItemSelected(item);
    }

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