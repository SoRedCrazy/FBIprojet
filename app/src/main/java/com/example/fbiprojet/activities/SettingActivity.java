package com.example.fbiprojet.activities;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;
import androidx.preference.PreferenceManager;

import com.example.fbiprojet.R;
import com.example.fbiprojet.fragments.SettingsFragment;

public class SettingActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{
    SharedPreferences sharedPreferences;

    /**
     * ajout du fragement Setting et change le listener des preferences
     * @param savedInstanceState
     */
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.idFrameLayout, new SettingsFragment())
                .commit();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this::onSharedPreferenceChanged);

    }

    /**
     * modification suite au clique sur les preferences
     * @param sharedPreferences
     * @param key
     */
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

                if(key.endsWith("darkmode")) {
                    boolean darkmode = sharedPreferences.getBoolean("darkmode", false);
                    if (darkmode) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    }
                }
                if(key.endsWith("langue")) {
                    String langue = sharedPreferences.getString("langue", "en-EN");
                    if(langue.equals("fr-FR")){
                        LocaleListCompat appLocale = LocaleListCompat.forLanguageTags("fr-FR");
                        AppCompatDelegate.setApplicationLocales(appLocale);
                    }
                    if(langue.equals("en-US")){
                        LocaleListCompat appLocale = LocaleListCompat.forLanguageTags("en-EN");
                        AppCompatDelegate.setApplicationLocales(appLocale);
                    }
                }

            }

    }